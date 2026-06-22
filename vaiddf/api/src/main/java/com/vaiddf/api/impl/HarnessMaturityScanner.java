package com.vaiddf.api.impl;

import com.vaiddf.core.model.harness.HarnessMaturityAssessment;
import com.vaiddf.core.model.harness.HarnessMaturityEvidence;
import com.vaiddf.core.model.harness.HarnessMaturityLevel;
import com.vaiddf.core.model.harness.HarnessMaturityRecommendation;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class HarnessMaturityScanner {

    private static final int MAX_DEPTH = 5;
    private static final int MAX_FILES = 1000;

    public HarnessMaturityAssessment scan(String repositoryName, String repositoryPath) {
        Path root = Path.of(repositoryPath);
        if (!Files.isDirectory(root)) {
            throw new IllegalArgumentException("Path is not a directory: " + repositoryPath);
        }

        List<HarnessMaturityEvidence> evidence = new ArrayList<>();
        evidence.add(checkCoreInstructionFile(root));
        evidence.add(checkOverridePolicy(root));
        evidence.add(checkSharedSkillRegistry(root));
        evidence.add(checkLocalWrapperPattern(root));
        evidence.add(checkWorkflowLanes(root));
        evidence.add(checkQualityHooks(root));
        evidence.add(checkFreshnessDriftCheck(root));
        evidence.add(checkAgentSpecialization(root));
        evidence.add(checkHarnessOwner(root));
        evidence.add(checkHarnessChangeReviewed(root));

        int presentCount = (int) evidence.stream().filter(HarnessMaturityEvidence::present).count();
        double coverage = (double) presentCount / evidence.size();
        HarnessMaturityLevel level = HarnessMaturityLevel.fromPresentDimensions(presentCount);

        List<HarnessMaturityRecommendation> recommendations = evidence.stream()
            .filter(e -> !e.present())
            .map(e -> new HarnessMaturityRecommendation(
                e.dimension(),
                e.recommendation(),
                e.confidence() == HarnessMaturityEvidence.Confidence.HIGH
                    ? HarnessMaturityRecommendation.Priority.HIGH
                    : HarnessMaturityRecommendation.Priority.MEDIUM
            ))
            .toList();

        return new HarnessMaturityAssessment(
            UUID.randomUUID().toString(),
            repositoryName,
            repositoryPath,
            presentCount,
            evidence.size(),
            coverage,
            level,
            level.getNumericScore(),
            evidence,
            recommendations,
            Instant.now()
        );
    }

    private HarnessMaturityEvidence checkCoreInstructionFile(Path root) {
        String[] patterns = {"AGENTS.md", ".agents/AGENTS.md", "CLAUDE.md", ".codex/AGENTS.md",
            ".github/copilot-instructions.md"};
        return scanForFiles(root, "Core instruction file", patterns,
            "Add an AGENTS.md file with agent instructions",
            HarnessMaturityEvidence.Confidence.HIGH);
    }

    private HarnessMaturityEvidence checkOverridePolicy(Path root) {
        String[] keywords = {"override", "local wins", "core first", "inherit", "extends"};
        return scanForContent(root, "Repo-local override policy", keywords,
            "Document override policy in AGENTS.md",
            HarnessMaturityEvidence.Confidence.MEDIUM);
    }

    private HarnessMaturityEvidence checkSharedSkillRegistry(Path root) {
        String[] patterns = {"skills/", ".codex/skills/", ".agents/skills/",
            "scripts/agent/", "agent-scripts/"};
        return scanForDirectories(root, "Shared skill or script registry", patterns,
            "Create a skills/ directory for shared agent scripts",
            HarnessMaturityEvidence.Confidence.HIGH);
    }

    private HarnessMaturityEvidence checkLocalWrapperPattern(Path root) {
        String[] keywords = {"wrapper", "delegate", "core", "shared"};
        return scanForContentInPath(root, "Local wrapper pattern", "scripts/", keywords,
            "Add wrapper scripts that delegate to shared skills",
            HarnessMaturityEvidence.Confidence.MEDIUM);
    }

    private HarnessMaturityEvidence checkWorkflowLanes(Path root) {
        String[] patterns = {".github/workflows/", ".agents/workflows/", "workflows/"};
        return scanForDirectories(root, "Workflow lanes", patterns,
            "Set up GitHub Actions workflows for agent tasks",
            HarnessMaturityEvidence.Confidence.HIGH);
    }

    private HarnessMaturityEvidence checkQualityHooks(Path root) {
        String[] patterns = {"pre-commit", ".githooks/", ".husky/", ".claude/hooks/",
            ".codex/hooks/"};
        return scanForFiles(root, "Quality hooks", patterns,
            "Add pre-commit hooks or quality gates",
            HarnessMaturityEvidence.Confidence.HIGH);
    }

    private HarnessMaturityEvidence checkFreshnessDriftCheck(Path root) {
        String[] keywords = {"freshness", "drift", "sync", "checksum", "stale"};
        return scanForContent(root, "Freshness or drift check", keywords,
            "Add drift detection scripts for agent harness",
            HarnessMaturityEvidence.Confidence.MEDIUM);
    }

    private HarnessMaturityEvidence checkAgentSpecialization(Path root) {
        String[] keywords = {"codex", "claude", "copilot", "cursor"};
        List<String> found = new ArrayList<>();
        for (String keyword : keywords) {
            if (Files.exists(root.resolve(keyword)) ||
                Files.exists(root.resolve("." + keyword))) {
                found.add(keyword);
            }
        }
        boolean present = found.size() > 1;
        return new HarnessMaturityEvidence(
            "Agent-specific specialization",
            present,
            present ? HarnessMaturityEvidence.Confidence.HIGH : HarnessMaturityEvidence.Confidence.LOW,
            found,
            present ? "Multiple agent runtimes configured" : "Single or no agent runtime",
            "Configure multiple agent runtimes for specialization"
        );
    }

    private HarnessMaturityEvidence checkHarnessOwner(Path root) {
        String[] patterns = {"CODEOWNERS", "OWNERS", "MAINTAINERS"};
        return scanForFiles(root, "Harness owner and backup", patterns,
            "Add CODEOWNERS file to define harness ownership",
            HarnessMaturityEvidence.Confidence.HIGH);
    }

    private HarnessMaturityEvidence checkHarnessChangeReviewed(Path root) {
        String[] patterns = {"pull_request_template.md", ".github/workflows/review.yml",
            "CODEOWNERS"};
        return scanForFiles(root, "Harness change reviewed", patterns,
            "Add PR templates or review workflows",
            HarnessMaturityEvidence.Confidence.MEDIUM);
    }

    private HarnessMaturityEvidence scanForFiles(Path root, String dimension,
            String[] patterns, String recommendation,
            HarnessMaturityEvidence.Confidence confidence) {
        List<String> found = new ArrayList<>();
        for (String pattern : patterns) {
            Path path = root.resolve(pattern);
            if (Files.exists(path)) {
                found.add(pattern);
            }
        }
        boolean present = !found.isEmpty();
        return new HarnessMaturityEvidence(dimension, present, confidence, found,
            present ? "Found: " + String.join(", ", found) : "Not found",
            recommendation);
    }

    private HarnessMaturityEvidence scanForDirectories(Path root, String dimension,
            String[] patterns, String recommendation,
            HarnessMaturityEvidence.Confidence confidence) {
        List<String> found = new ArrayList<>();
        for (String pattern : patterns) {
            Path path = root.resolve(pattern);
            if (Files.isDirectory(path)) {
                found.add(pattern);
            }
        }
        boolean present = !found.isEmpty();
        return new HarnessMaturityEvidence(dimension, present, confidence, found,
            present ? "Found: " + String.join(", ", found) : "Not found",
            recommendation);
    }

    private HarnessMaturityEvidence scanForContent(Path root, String dimension,
            String[] keywords, String recommendation,
            HarnessMaturityEvidence.Confidence confidence) {
        List<String> found = new ArrayList<>();
        try {
            Path instructions = root.resolve("AGENTS.md");
            if (Files.exists(instructions)) {
                String content = Files.readString(instructions).toLowerCase();
                for (String keyword : keywords) {
                    if (content.contains(keyword.toLowerCase())) {
                        found.add(keyword);
                    }
                }
            }
        } catch (IOException e) {
            // ignore
        }
        boolean present = !found.isEmpty();
        return new HarnessMaturityEvidence(dimension, present, confidence, found,
            present ? "Found keywords: " + String.join(", ", found) : "Not found in instructions",
            recommendation);
    }

    private HarnessMaturityEvidence scanForContentInPath(Path root, String dimension,
            String subPath, String[] keywords, String recommendation,
            HarnessMaturityEvidence.Confidence confidence) {
        List<String> found = new ArrayList<>();
        Path scriptsDir = root.resolve(subPath);
        if (Files.isDirectory(scriptsDir)) {
            try (Stream<Path> files = Files.walk(scriptsDir, 2)) {
                files.filter(Files::isRegularFile).forEach(file -> {
                    try {
                        String content = Files.readString(file).toLowerCase();
                        for (String keyword : keywords) {
                            if (content.contains(keyword.toLowerCase())) {
                                found.add(keyword + " in " + root.relativize(file));
                            }
                        }
                    } catch (IOException e) {
                        // ignore
                    }
                });
            } catch (IOException e) {
                // ignore
            }
        }
        boolean present = !found.isEmpty();
        return new HarnessMaturityEvidence(dimension, present, confidence, found,
            present ? "Found: " + String.join(", ", found) : "Not found",
            recommendation);
    }
}
