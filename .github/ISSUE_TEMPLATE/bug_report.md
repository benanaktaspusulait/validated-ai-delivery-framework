name: Bug Report
description: Report a factual error, broken link, or contradiction between documents
title: "[BUG] "
labels: ["bug", "documentation"]
body:
  - type: textarea
    id: description
    attributes:
      label: Description
      description: Describe the issue clearly
      placeholder: "What is the problem?"
    validations:
      required: true
  - type: textarea
    id: location
    attributes:
      label: Location
      description: Which file and line number?
      placeholder: "docs/metrics-catalogue.md line 42"
    validations:
      required: true
  - type: textarea
    id: expected
    attributes:
      label: Expected behavior
      description: What should it say or do?
    validations:
      required: true
