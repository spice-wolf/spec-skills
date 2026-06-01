# OWASP Review Questions

Use these questions to quickly decide what to inspect and what evidence is still missing.

## Universal Questions

- What are the entry points, trust boundaries, and sensitive assets?
- Which components can submit data to this flow, directly or indirectly?
- Which checks happen only on the client, and which are enforced on the server?
- What happens when validation, auth, config, crypto, or logging dependencies fail?
- Which decisions rely on caller-controlled state, hidden fields, headers, or URLs?
- What data could reach logs, caches, URLs, or temporary files unintentionally?

## Design Review Questions

- What can an attacker do if they bypass the UI and call the backend directly?
- Which requests need authentication, authorization, re-authentication, or MFA?
- Where are trust boundaries crossed between browser, backend, worker, DB, and third parties?
- How are sessions created, rotated, expired, and invalidated?
- Which data is sensitive in transit, at rest, in logs, and in caches?
- Are file upload, redirect, template rendering, or command-execution paths involved?
- What security invariants must hold for the feature to be considered safe?

## Code Review Questions

- Is all untrusted input validated for type, length, range, and allowed format?
- Is output encoded for the exact sink or rendering context?
- Are auth and access-control decisions centralized and enforced on every protected request?
- Are secrets, tokens, or connection strings exposed in code, configs, logs, or clients?
- Are database calls parameterized and least-privilege?
- Are uploads, filesystem paths, and redirects strictly constrained?
- Are errors generic to the user but detailed enough for operators in protected logs?
- Are security-sensitive actions logged without leaking secrets?

## Remediation Questions

- What is the minimum change that removes the risky behavior?
- Can a shared guard, middleware, validator, or helper fix this class of issue centrally?
- What regression tests or abuse-case tests should be added?
- What evidence will prove the fix is actually effective?
