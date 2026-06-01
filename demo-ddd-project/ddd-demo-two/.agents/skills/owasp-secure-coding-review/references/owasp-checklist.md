# OWASP Secure Coding Checklist

Source baseline: `OWASP_en.pdf` in the workspace, OWASP Secure Coding Practices Quick Reference Guide, Version 2.0, November 2010.

Use this file as a compact domain-by-domain review aid. Summaries below are paraphrased from the PDF and optimized for practical design and code review.

## 1. Input Validation

- Validate on a trusted server-side system.
- Inventory data sources and treat cross-boundary data as untrusted, including DB content, files, headers, cookies, and redirects.
- Centralize validation logic where practical.
- Canonicalize before validating and use explicit character-set handling such as UTF-8.
- Reject invalid input rather than trying to repair it silently.
- Validate type, range, length, and allowed characters.
- Prefer allowlists over blocklists.
- Handle dangerous edge cases explicitly: null bytes, newline injection, and path traversal sequences.
- If hazardous characters must be allowed, pair that with downstream controls such as output encoding and safe APIs.

## 2. Output Encoding

- Perform encoding on a trusted system.
- Use standard, tested encoders for each output context.
- Encode untrusted data before sending it to an interpreter or client context.
- Use contextual encoding, not one universal escape routine.
- Sanitize untrusted data used in SQL, XML, LDAP, or OS command contexts.

## 3. Authentication And Password Management

- Require authentication for all non-public resources.
- Enforce authentication on the server side through centralized, tested services.
- Fail securely and keep admin/account-management flows at least as strong as the primary login flow.
- Store passwords as strong salted one-way hashes on a trusted system.
- Do not leak whether username or password was incorrect.
- Protect credentials used for external services and never store them in source code.
- Send credentials only via appropriate protected channels.
- Enforce policy-driven length, complexity, reset, expiry, lockout, and reuse controls.
- Use stronger controls such as re-authentication or MFA for high-risk operations and accounts.
- Change vendor defaults and inspect third-party auth code carefully.

## 4. Session Management

- Use framework or server session controls instead of inventing custom schemes.
- Generate session identifiers on a trusted system with strong randomness.
- Restrict cookie domain/path and terminate sessions fully on logout.
- Enforce inactivity timeout and periodic termination where risk warrants it.
- Regenerate session identifiers after login, re-authentication, and relevant security-state changes.
- Do not expose session identifiers in URLs, logs, or error messages.
- Protect server-side session state from unauthorized access.
- Use `Secure` and `HttpOnly` cookie attributes when applicable.
- Add anti-CSRF tokens for sensitive operations.

## 5. Access Control

- Make authorization decisions using trusted server-side state.
- Centralize authorization checks and fail securely.
- Deny access if policy or security configuration is unavailable.
- Enforce authorization on every request and every protected operation.
- Protect URLs, functions, objects, services, files, and data attributes from unauthorized access.
- Keep server-side rules aligned with what the UI presents.
- Never rely on the `Referer` header as the only access-control check.
- Revalidate permissions for long-lived sessions when privileges may have changed.
- Support account disablement, session termination, and least-privilege service accounts.

## 6. Cryptographic Practices

- Perform cryptographic operations on a trusted system.
- Protect master secrets and keys from unauthorized access.
- Fail securely if crypto modules fail.
- Use cryptographically secure randomness for secrets, tokens, filenames, GUIDs, and similar values that must be unguessable.
- Use validated, well-vetted cryptographic modules and establish a key-management policy.

## 7. Error Handling And Logging

- Do not leak sensitive details in error responses.
- Avoid exposing stack traces or debug output to users.
- Use generic user-facing errors and custom error pages.
- Handle application errors in application logic, not just server defaults.
- Deny access by default when security-control error handling is triggered.
- Log security-relevant successes and failures on a trusted system.
- Avoid logging secrets, session IDs, passwords, or unnecessary internals.
- Ensure untrusted log data cannot execute in log viewers.
- Log validation failures, auth failures, access-control failures, tampering, invalid sessions, exceptions, admin actions, TLS failures, and crypto failures.
- Protect log integrity and support log analysis.

## 8. Data Protection

- Apply least privilege to functionality, data, and system information.
- Protect and promptly purge temporary or cached sensitive data.
- Encrypt highly sensitive stored data with well-vetted algorithms.
- Prevent server-side source-code disclosure.
- Do not store secrets or sensitive configuration in cleartext on clients.
- Remove sensitive comments and unnecessary documentation from production artifacts.
- Keep sensitive information out of GET parameters.
- Disable autocomplete or client caching where sensitive data is involved.
- Support secure deletion or removal when sensitive data is no longer needed.

## 9. Communication Security

- Encrypt transmission of sensitive information, typically with TLS.
- Use valid, correctly installed certificates.
- Never fall back from failed secure transport to insecure transport.
- Use TLS for authenticated areas and other sensitive connections, including external integrations.
- Standardize on a single well-configured TLS implementation where possible.
- Specify connection character encodings.
- Avoid leaking sensitive parameters through `Referer` headers to external sites.

## 10. System Configuration

- Keep servers, frameworks, and components on approved and patched versions.
- Turn off directory listing and remove unnecessary functionality, files, and test code.
- Run services with least privilege.
- Fail securely on exceptions.
- Reduce disclosure in `robots.txt` and response headers.
- Allow only required HTTP methods and handle them consistently.
- Disable unnecessary extended HTTP methods such as unused WebDAV features.
- Keep security configuration auditable and human-readable.
- Maintain asset inventory and change control.
- Isolate development from production appropriately.

## 11. Database Security

- Use strongly typed parameterized queries.
- Combine safe query construction with input validation and output encoding.
- Use least-privilege DB accounts and secure credentials.
- Keep connection strings out of source code and store them securely.
- Use stored procedures when they reduce exposure and permissions on base tables.
- Close DB connections promptly.
- Remove default passwords, sample content, unnecessary features, and unneeded accounts.
- Separate DB credentials by trust level and responsibility.

## 12. File Management

- Never pass user input directly into dynamic include or unsafe redirect behavior.
- Require authentication before upload where appropriate.
- Restrict allowed file types to business-required types only.
- Validate uploaded type by file content or headers, not only extension.
- Store uploads outside the executable web context.
- Prevent upload and execution of server-interpretable files where not required.
- Turn off execution privileges on upload directories.
- Prefer allowlisted filenames or mapped indexes for referencing existing files.
- Never send absolute file paths to clients.
- Keep application files and resources read-only where possible.
- Scan uploads for malware.

## 13. Memory Management

- Control untrusted input and output carefully.
- Check buffer sizes and boundaries rigorously.
- Be cautious with copy and concatenation functions and string termination behavior.
- Truncate overly long input before unsafe operations.
- Close resources explicitly.
- Use non-executable stacks where available.
- Avoid known dangerous functions.
- Free allocated memory on all exit paths.

## 14. General Coding Practices

- Prefer tested managed code and built-in safe APIs over custom low-level implementations.
- Avoid issuing OS commands directly, especially through shells.
- Verify integrity of interpreted code, libraries, executables, and configuration with hashes or checksums where appropriate.
- Prevent race conditions with locking or synchronization where needed.
- Initialize variables explicitly.
- Raise privileges as late as possible and drop them as soon as possible.
- Watch for numeric and type-conversion errors.
- Do not pass user input to dynamic execution functions.
- Prevent users from generating or modifying executable code unless explicitly required and tightly controlled.
- Review third-party code and dependencies for necessity and safety.
- Use signed and protected update mechanisms for auto-update flows.
