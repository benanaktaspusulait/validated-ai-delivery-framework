# Use Cases

Three adoption scenarios showing how the framework is introduced, which signals matter most and where the gates bite. They are illustrative archetypes, not real organisations.

## 1. Financial services firm (high security, strict audit)

```text
Context: regulated domain, heavy audit, low tolerance for unreviewed AI-generated code in payment and auth paths.
Primary concern: AI output reaching sensitive code without adequate human validation.
```

| Aspect | How the framework applies |
|---|---|
| Rollout pace | Deliberately slow; long Observation Mode; enforcement only after strong trust |
| Signals that matter most | Contextual risk score, security-sensitive-path policies, post-merge defect linkage, override audit |
| Policies emphasised | Mandatory AppSec + senior review on sensitive paths; metadata required; prompt-leakage scanning |
| Governance | Quarterly forum with compliance present; full retention and audit trail |
| Risk if mishandled | Treating estimates as proof in an audit; mitigated by confidence labelling |

```text
Adoption band is expected to be low and that is acceptable; a regulated team choosing caution is a rational decision, not immaturity.
```

## 2. Fast-growing SaaS (delivery pressure, heavy AI use)

```text
Context: high AI adoption, strong velocity pressure, risk of hidden review and quality debt accumulating quietly.
Primary concern: AI output outpacing the team's capacity to review and test it.
```

| Aspect | How the framework applies |
|---|---|
| Rollout pace | Faster; move through phases as soon as gates pass |
| Signals that matter most | AI Review Debt age ratio, Cognitive Load Index, Validated Delivery Trend, dynamic AI WIP |
| Policies emphasised | Large-PR warnings and split recommendations; WIP recommendation to protect reviewers |
| Governance | Lightweight; emphasis on developer-friendly, in-PR guidance |
| Risk if mishandled | Alert fatigue and a punitive feel; mitigated by experiment mode and feedback loops |

```text
The value story here is catching review debt before it becomes a delivery and quality problem, while keeping the developer touch light.
```

## 3. Public-sector organisation (low AI maturity, change resistance)

```text
Context: cautious culture, limited AI adoption, significant change resistance and procurement constraints.
Primary concern: trust, transparency and avoiding any perception of surveillance.
```

| Aspect | How the framework applies |
|---|---|
| Rollout pace | Very gradual; extended pilot; heavy emphasis on communication |
| Signals that matter most | Psychological safety pulse first; basic adoption and review-debt signals later |
| Policies emphasised | None initially; observation and education before any warning |
| Governance | Strong privacy basis, clear data scope, visible non-negotiable rules |
| Risk if mishandled | Fear-driven rejection; mitigated by training, transparency and observation-first rollout |

```text
Success here is measured first by trust and understanding, not by adoption percentage.
```

## Cross-scenario pattern

```text
All three start in Observation Mode and graduate through the same gates.
The difference is pace and which signals lead, not a different product.
In every case, confidence labelling and team-level-only reporting are constant.
```
