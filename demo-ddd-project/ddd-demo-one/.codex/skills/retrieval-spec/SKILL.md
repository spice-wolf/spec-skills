---
name: retrieval-spec
description: Route and load the minimum necessary Specification files for Spec-Driven Development (SDD). Use when Codex receives a project task or requirement, must first read `./specification/index.md`, then apply constitution-mandatory loading plus a two-phase retrieval flow to select architecture, DDD, tool, and domain Specification files before solving the task.
---

# Retrieval Spec

Route Specification context first, then solve the task. Do not eagerly read the whole Specification tree.

Use this skill to implement `constitution` mandatory loading plus two-phase execution:
- phase 1: read `./specification/index.md` and choose the minimum relevant Specification files
- phase 2: read the chosen Specification files and use them to solve the user's request

## Workflow

1. Determine the Specification root.
   If the user did not provide it, ask for it and recommend `./specification`.

2. Read `./specification/index.md` first.
   Do not read other Specification files before the index unless `index.md` is missing.
   The expected index structure is described in `indexing-spec/references/index-template.md` and `indexing-spec/references/index-format.md`.

3. Extract candidate manifest entries from the index.
   Use the fields `path`, `name`, `description`, `always_load`, `domains`, `triggers`, and `priority`.

4. Apply the minimum loading strategy from [references/minimum-loading.md](references/minimum-loading.md).

5. Produce a short routing decision before reading the selected files.
   Include:
   - mandatory files
   - task-type files
   - domain files
   - a short reason for each selected file

6. Read only the selected Specification files.
   If the task later proves broader than expected, load one additional file at a time rather than expanding to all files.

7. Solve the user's task using the loaded Specification context.

## Two-Phase Retrieval

### Phase 1: Route

Use only:
- the user request
- `./specification/index.md`

During routing:
- always include files whose manifest has `always_load: true`
- prefer the smallest set that can safely guide the task
- match task intent against `triggers`, `domains`, `description`, and `priority`
- if multiple domain files match, include only the domains truly touched by the request

### Phase 2: Execute

After selecting files:
- read the chosen Specification files
- summarize any constraints that materially affect the task
- continue with requirement analysis, design, implementation, or review

## Minimum Loading Strategy

Implement at least these three layers.

### L0 Mandatory

Always load:
- `constitution/constitution.md`

If the index marks another file with `always_load: true`, load it too, but treat this as an exception rather than the default.

### L1 Task-Type

Load based on task category:

- architecture tasks
  - typically load `architecture/architecture.md`
  - signals: architecture, module split, layering, framework choice, cross-module change, integration boundary

- DDD modeling tasks
  - typically load `architecture/DDD.md`
  - signals: aggregate, entity, value object, repository, domain service, bounded context, domain model, DDD rule

- tool reuse tasks
  - typically load `tool/tool.md`
  - signals: utility reuse, validator, mapper, tracing, ID generation, helper selection, common technical pattern

Load none of these if the task does not need them.

### L2 Domain

Load the relevant `domain/*.md` files based on the request.

Examples:
- merchant-related work -> `domain/merchant.md`
- terminal-related work -> `domain/terminal.md`
- multi-domain collaboration -> load only the specific participating domain files

Domain matching should use:
- explicit domain names in the user request
- aliases from the manifest `domains`
- business phrases from `triggers`
- clues from the task wording

## Routing Heuristics

- `新增商户审核功能` usually routes to:
  - `constitution/constitution.md`
  - `domain/merchant.md`
  - and optionally `architecture/DDD.md` if the task changes aggregates, repositories, or domain services

- a pure framework upgrade usually routes to:
  - `constitution/constitution.md`
  - `architecture/architecture.md`

- adding a shared validator usually routes to:
  - `constitution/constitution.md`
  - `tool/tool.md`
  - and one or more domain files only if the validator is domain-specific

## Failure Handling

If `./specification/index.md` is missing, stale, or clearly inconsistent:
- stop relying on retrieval assumptions
- ask to run `$indexing-spec`
- if the user still wants to continue, state that retrieval confidence is reduced

If a needed file is referenced in `index.md` but missing on disk:
- report the gap
- continue with the remaining loaded files only if the missing file is not critical

If routing is ambiguous:
- prefer the smaller safe set
- ask a short clarifying question only when the ambiguity materially changes the solution

## Writing Rules

- Do not read all domain files by default.
- Do not bypass `index.md` unless it is missing.
- Keep routing explanations short and explicit.
- Load additional Specification files only when the current set is insufficient.
- Respect `always_load` as the highest-priority signal.

## Completion

Finish only after:
- `./specification/index.md` was consulted first
- the minimum relevant Specification files were selected
- those files were actually read
- the user task was addressed using the retrieved Specification context
