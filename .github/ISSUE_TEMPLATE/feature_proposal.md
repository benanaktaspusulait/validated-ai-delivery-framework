name: Feature Proposal
description: Propose a new capability, metric, policy or documentation section
title: "[FEATURE] "
labels: ["enhancement"]
body:
  - type: textarea
    id: problem
    attributes:
      label: Problem
      description: What problem does this solve?
      placeholder: "As a [role], I need [capability] so that [outcome]"
    validations:
      required: true
  - type: textarea
    id: solution
    attributes:
      label: Proposed solution
      description: How should this be implemented?
    validations:
      required: true
  - type: dropdown
    id: scope
    attributes:
      label: Scope
      options:
        - Documentation
        - Framework concept
        - Implementation task
        - Template
