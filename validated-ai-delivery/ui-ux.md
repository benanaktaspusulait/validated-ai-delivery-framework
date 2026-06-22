# UI/UX and Visualisation

Dashboards, model output visualisation and reporting interfaces.

## Dashboard types

| Dashboard | Audience | Content |
|---|---|---|
| ML Operations | ML team | Model health, drift, alerts, costs |
| Model Performance | ML + Product | Accuracy trends, A/B results, user feedback |
| Executive | Leadership | ROI, risk posture, rollout status |
| Fairness | Compliance | Bias metrics, disparity ratios, audit trail |
| Incident | On-call | Active incidents, timeline, RCA status |

## Visualisation tools

| Tool | Best for | Cost |
|---|---|---|
| Grafana | Operational dashboards, alerting | Open-source |
| Streamlit | Quick ML app prototyping | Open-source |
| Dash (Plotly) | Interactive data apps | Open-source |
| Superset | BI-style dashboards | Open-source |
| Looker / Tableau | Enterprise BI | Paid |

## Model output visualisation

```text
Prediction dashboards:
  - Prediction distribution (histogram).
  - Confidence distribution (how confident is the model).
  - Error analysis (where does the model fail).
  - Feature importance (SHAP summary plot).

Fairness dashboards:
  - Group-level metrics (bar chart by protected group).
  - Disparity ratios (traffic light: green/yellow/red).
  - Trend over time (is fairness improving or degrading).

Drift dashboards:
  - Feature distribution comparison (train vs. production).
  - PSI or KS statistic trend.
  - Alert status (active/resolved).
```

## Design principles

```text
1. Show the metric, not just the number — trends matter more than snapshots.
2. Highlight anomalies — draw attention to what changed.
3. Provide context — baseline, threshold, comparison.
4. Enable drill-down — from summary to detail.
5. Keep it simple — one dashboard, one purpose.
```
