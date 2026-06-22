name: Clarification Request
description: Ask for clarification on an ambiguous or unclear section
title: "[CLARIFY] "
labels: ["question"]
body:
  - type: textarea
    id: section
    attributes:
      label: Section
      description: Which document and section is unclear?
      placeholder: "docs/risk-policy-engine.md section 3.2"
    validations:
      required: true
  - type: textarea
    id: question
    attributes:
      label: Question
      description: What is unclear?
    validations:
      required: true
