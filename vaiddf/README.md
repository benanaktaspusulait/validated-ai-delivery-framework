# VAIDDF — Validated AI Delivery Framework

**AI Delivery Control Plane for Regulated Java Enterprises**

## Quick Start

```bash
# Build all modules
./mvnw clean install

# Run the API server
./mvnw -pl api quarkus:dev

# Run CLI
java -jar cli/target/vaiddf-cli-0.1.0-SNAPSHOT.jar --help

# Docker Compose demo
docker compose up
```

## Project Structure

```
vaiddf/
├── pom.xml                    (parent)
├── core/                      (domain model, annotations, SPI)
├── api/                       (REST API, Quarkus)
├── cli/                       (picocli CLI)
├── ui/                        (Quarkus + Qute frontend)
├── policy-engine/             (policy evaluation)
├── extensions/
│   ├── drift/                 (drift monitoring - PSI)
│   └── fairness/              (fairness checking)
├── dist/                      (Docker images)
└── legacy/                    (deprecated Python code)
```

## Core Concepts

### Governance Annotations

```java
@ModelGoverned(
    registry = "credit-risk",
    driftCheck = true,
    fairnessRequired = true,
    maxDriftPSI = 0.2
)
@Path("/predict")
public class CreditRiskPredictor { ... }
```

### Plugin SPI

```java
public interface DriftDetector {
    String name();
    DriftResult check(double[] reference, double[] current);
}
```

## CLI Commands

```bash
vaiddf init --name my-project
vaiddf deploy --model credit-risk-v2 --env prod
vaiddf audit --since 2026-01-01 --model credit-risk
vaiddf policy --list
vaiddf policy --evaluate credit-risk-prod
```

## Non-Negotiable Rules

- No individual AI productivity ranking
- No raw prompt storage
- No hard enforcement before Phase 4
- No blocking enforcement except from high-confidence metrics (score >= 90)
- No causal claims stronger than data supports

## License

Apache 2.0
