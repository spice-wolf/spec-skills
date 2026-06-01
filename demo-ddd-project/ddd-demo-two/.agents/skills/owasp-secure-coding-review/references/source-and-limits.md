# Source And Limits

## Source

- Primary source: [OWASP_en.pdf](P:\Project\023 - OWASP\OWASP_en.pdf)
- Document title: `OWASP Secure Coding Practices Quick Reference Guide`
- Version: `2.0`
- Date: `November 2010`

## How To Use This Source

- Treat it as a technology-agnostic secure-coding baseline.
- Use it to identify missing controls, unsafe assumptions, and review questions.
- Translate checklist items into concrete requirements for the specific framework, language, or architecture under review.

## Important Limits

- This source predates many modern platform defaults and attack patterns.
- It is not a replacement for framework-specific secure coding guidance.
- It is not a complete threat model, pentest, or compliance certification.
- It should not be used as proof that a system is fully secure just because no issue was found in a limited review.

## When To Escalate Beyond This Skill

Supplement this baseline when the request involves:

- Modern identity protocols, federated auth, or complex MFA flows
- Cloud IAM, container orchestration, or infrastructure-as-code risk
- Browser isolation, CSP, modern frontend supply-chain concerns, or dependency provenance
- Privacy, retention, regulatory obligations, or data residency constraints
- Cryptographic design choices beyond basic control review
- Mobile, native, embedded, or high-assurance systems

In those cases, keep using this skill for baseline hygiene, but say explicitly that additional standards, reviews, or product-specific guidance are required.
