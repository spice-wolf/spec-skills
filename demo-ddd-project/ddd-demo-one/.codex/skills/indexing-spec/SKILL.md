---
name: indexing-spec
description: Maintain the Specification manifest index at `./specification/index.md` for Spec-Driven Development (SDD). Use when Specification markdown files are created, updated, deleted, or when their YAML frontmatter changes, especially to incrementally refresh only the affected manifest entries instead of rebuilding the whole index.
---

# Indexing Spec

Maintain the project's Specification index only. Do not solve business requirements as part of this skill.

Use this skill to keep `./specification/index.md` accurate so later retrieval can decide which Specification files to load with minimal context.

## Goal

Keep one manifest entry per Specification file in `./specification` except `index.md` itself.

Each manifest entry must contain:
- `path`
- `name`
- `description`
- `always_load`
- `domains`
- `triggers`
- `priority`

Represent each entry as JSON. Follow the index format in [references/index-format.md](references/index-format.md).
When `./specification/index.md` does not exist yet, start from [references/index-template.md](references/index-template.md).

## Workflow

1. Determine the Specification root.
   If the user did not provide it, ask for it and recommend `./specification`.

2. Find the changed Specification files.
   Prefer incremental detection in this order:
   - files explicitly named by the user
   - `git diff --name-only` or `git status --short`
   - a focused filesystem scan inside `./specification`

3. Exclude `index.md` from indexing.
   Include other Specification markdown files under the root, including operational files such as `TODO.md` only if they already exist under the Specification root.

4. Read the affected Specification files and the current `./specification/index.md`.
   Only inspect unchanged Specification files if the existing index is missing, corrupt, or clearly stale.

5. For each affected file, build or refresh one manifest entry.
   Prefer file frontmatter first, then use path, headings, and concise body evidence to fill fields that frontmatter does not provide.

6. Update only the affected manifest entries when possible.
   Preserve unchanged entries and keep a stable ordering.

7. Remove manifest entries for deleted Specification files.

8. Write the updated `./specification/index.md`.

## Manifest Derivation Rules

### `path`

- Use the path relative to `./specification`
- Always use forward slashes

### `name`

- Prefer the YAML frontmatter `name`
- If the file has no frontmatter, derive a stable name from its role and filename

### `description`

- Prefer the YAML frontmatter `description`
- If missing, derive a short routing-oriented summary that explains:
  - what the file contains
  - when it should be loaded during SDD

### `always_load`

- `true` only for `constitution/constitution.md`
- `false` for all other Specification files unless the user explicitly wants another globally loaded file

### `domains`

- Use business domains, not technical layers
- For `domain/*.md`, include the concrete domain name and obvious aliases when they are present in the file
- For global files such as `constitution.md`, `architecture.md`, or `tool.md`, use an empty array unless the file is intentionally scoped to specific domains

### `triggers`

- Record short phrases that help route future tasks to this file
- Prefer domain terms, business capabilities, and task categories already visible in the file
- Avoid generic triggers such as `code`, `bugfix`, or `task`

### `priority`

Use loading tiers rather than arbitrary numbers:
- `L0`: always-load constitutional rules
- `L1`: task-type files such as architecture, DDD, and tool guidance
- `L2`: domain files such as `domain/merchant.md` and `domain/terminal.md`
- `L3`: optional or operational Specification files that are usually not part of the minimum load set

## Incremental Update Policy

Default to incremental updates.

Update only changed entries when:
- the current `index.md` exists
- the entry format is valid
- changed files can be identified with high confidence

Fallback to full rebuild only when:
- `index.md` does not exist
- the manifest schema changed
- many Specification files were moved or renamed
- the current index is clearly inconsistent with the filesystem

When `index.md` is missing:
- create it from [references/index-template.md](references/index-template.md)
- replace placeholder entries with manifests derived from the real Specification files that already exist

## Indexable File Guidance

Apply these defaults when deriving manifests:

- `constitution/constitution.md`
  - `always_load: true`
  - `priority: "L0"`

- `architecture/architecture.md`
  - `always_load: false`
  - `priority: "L1"`
  - add triggers related to architecture, module split, layering, framework, cross-module changes

- `architecture/DDD.md`
  - `always_load: false`
  - `priority: "L1"`
  - add triggers related to aggregate, entity, value object, repository, domain service, bounded context, DDD

- `tool/tool.md`
  - `always_load: false`
  - `priority: "L1"`
  - add triggers related to utility reuse, helper selection, validator, mapper, tracing, ID generation, common tooling

- `domain/*.md`
  - `always_load: false`
  - `priority: "L2"`
  - domains should reflect the concrete bounded context or business domain

- operational files such as `TODO.md`
  - `always_load: false`
  - `priority: "L3"`
  - keep triggers sparse unless the user explicitly wants these files to participate in retrieval

## Writing Rules

- Keep `index.md` human-readable even though entries are JSON.
- Prefer one JSON block per Specification file rather than one giant JSON blob.
- Keep manifest entries concise and routing-friendly.
- Preserve existing valid entries that are unrelated to the current change.
- Do not invent domains or triggers without evidence.
- If evidence is weak, keep `domains` or `triggers` small instead of speculative.

## Completion

Finish only after:
- `./specification/index.md` exists
- all affected Specification files are reflected in the index
- deleted files are removed from the index
- unchanged entries remain intact whenever incremental update was possible
