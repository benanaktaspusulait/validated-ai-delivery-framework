package com.vaiddf.api.impl;

import com.vaiddf.core.model.harness.HarnessMaturityAssessment;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class HarnessMaturityService {

    private final CopyOnWriteArrayList<HarnessMaturityAssessment> assessments = new CopyOnWriteArrayList<>();

    public HarnessMaturityAssessment store(HarnessMaturityAssessment assessment) {
        assessments.add(assessment);
        return assessment;
    }

    public List<HarnessMaturityAssessment> findAll() {
        return assessments.stream()
            .sorted(Comparator.comparing(HarnessMaturityAssessment::assessedAt).reversed())
            .toList();
    }

    public HarnessMaturityAssessment findLatest() {
        return assessments.stream()
            .max(Comparator.comparing(HarnessMaturityAssessment::assessedAt))
            .orElse(null);
    }
}
