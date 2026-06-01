---
name: owasp-secure-coding-review
description: Assess application designs, feature proposals, architecture decisions, code changes, APIs, data flows, and implementation plans against OWASP Secure Coding Practices. Use when Codex needs to judge whether a system, function, endpoint, workflow, or code path is sufficiently aligned with OWASP secure coding guidance; identify gaps, risks, and missing controls; turn OWASP checklist items into review comments, coding requirements, remediation tasks, or secure-by-design recommendations.
---

# OWASP Secure Coding Review

Use this skill to perform evidence-based OWASP security reviews during design, review, and implementation work.

Treat the source PDF as a secure-coding baseline, not a full certification framework. If the target handles modern auth, cloud, mobile, supply-chain, or privacy-heavy workloads, call out when additional standards or framework-specific guidance are still needed.

## Review Workflow

### 1. Classify the request

Decide which of these modes fits best:

- Design review: architecture, workflow, trust boundary, feature proposal, API contract, storage plan
- Code review: implementation diff, file, function, endpoint, middleware, job, script
- Coding guidance: ask whether a planned approach is safe enough before coding
- Remediation planning: turn a known security concern into concrete fixes and validation steps

### 2. Build the security context first

Before judging compliance, identify:

- Entry points: request params, headers, cookies, files, redirects, imported data, messages, DB content
- Trust boundaries: browser, mobile app, backend, worker, database, external service, filesystem
- Sensitive assets: credentials, session tokens, personal data, financial data, internal configs, source
- Control points: validation, encoding, authn, authz, session, crypto, logging, storage, transport
- Risky capabilities: file upload, dynamic execution, redirects, admin actions, background jobs, external calls

If important context is missing, say so explicitly and mark the affected checks as `unknown`, not `pass`.

### 3. Select applicable OWASP domains

Start from the 14 domains in [references/owasp-checklist.md](references/owasp-checklist.md). Review all that apply; do not force irrelevant categories.

Use [references/review-questions.md](references/review-questions.md) to quickly pressure-test a design or implementation.

### 4. Evaluate by evidence

For each applicable domain:

- Mark `pass`, `partial`, `fail`, or `unknown`
- Cite the concrete evidence from the design, code, or behavior
- Explain the abuse case or failure mode
- Recommend the smallest practical fix that meaningfully reduces risk

Prefer specific findings over generic OWASP summaries.

### 5. Produce a review-ready output

Default output structure:

```md
Overall verdict: <aligned | partially aligned | not aligned | unknown>

Scope reviewed:
- <artifact / feature / files / endpoints>

Applicable OWASP domains:
- <domain>: <pass|partial|fail|unknown> - <one-line rationale>

Findings:
1. <severity> <title>
   OWASP area: <domain>
   Evidence: <specific code/design detail>
   Risk: <what can go wrong>
   Recommendation: <concrete fix>

Missing information:
- <what is needed to complete the assessment>

Suggested next steps:
- <tests, code changes, design changes, validation tasks>
```

When the user asks for a code review, keep findings first and prioritize real vulnerabilities, broken assumptions, privilege mistakes, insecure defaults, and missing validations.

## Review Heuristics

- Prefer server-side enforcement over client-side checks.
- Treat all external and cross-boundary data as untrusted until validated.
- Look for centralized, reusable controls instead of per-endpoint ad hoc defenses.
- Ask whether the control fails securely when dependencies, config, or validation break.
- Check whether sensitive operations require re-authentication, stronger authorization, or stronger session binding.
- Distinguish input validation from output encoding. Many systems do one and forget the other.
- Look for indirect attack paths: redirects, stored data, uploaded files, logs, admin tools, batch jobs, support scripts.
- Call out missing evidence. "No sign of X" is not the same as "X is implemented."

## Mapping Guidance

Typical domain mapping:

- API or form handling: input validation, output encoding, authn, session, access control, logging
- Admin or internal tooling: authn, access control, logging, data protection, system configuration
- File import/export or upload: input validation, file management, data protection, malware scanning
- Database-facing features: database security, access control, logging, data protection
- Background workers or integration jobs: authn to external systems, secrets handling, transport security, logging
- Native or low-level code: memory management and general coding practices

## Source Material And Limits

This skill is based on the local PDF [OWASP_en.pdf](P:\Project\023 - OWASP\OWASP_en.pdf), which is the OWASP Secure Coding Practices Quick Reference Guide, Version 2.0, November 2010.

Use [references/source-and-limits.md](references/source-and-limits.md) when the request needs the source scope, assumptions, or limitations.
