# Initial `index.md` Template

Use this file as the starting point when `./specification/index.md` does not exist yet. Replace placeholder text with repository-specific values, and remove entries for Specification files that are not present.

## Copyable Template

````md
# Specification Index

This file routes later SDD tasks to the minimum relevant Specification files.
Read this file first, then load only the Specification files whose manifests match the current task.

## Loading Policy

- `L0`: always-load Specification files
- `L1`: task-type Specification files
- `L2`: domain Specification files
- `L3`: optional or operational Specification files

## Entries

### `constitution/constitution.md`
```json
{
  "path": "constitution/constitution.md",
  "name": "project-constitution",
  "description": "Non-negotiable project rules and coding constraints that AI should load before planning, implementing, refactoring, or reviewing any change in this repository.",
  "always_load": true,
  "domains": [],
  "triggers": [
    "global rules",
    "coding constraints",
    "project-wide change"
  ],
  "priority": "L0"
}
```

### `architecture/architecture.md`
```json
{
  "path": "architecture/architecture.md",
  "name": "project-architecture",
  "description": "High-level project architecture, framework, module split, and layer mapping that AI should load when designing, changing, or reviewing cross-module or architectural work.",
  "always_load": false,
  "domains": [],
  "triggers": [
    "architecture change",
    "cross-module change",
    "layering",
    "framework decision"
  ],
  "priority": "L1"
}
```

### `architecture/DDD.md`
```json
{
  "path": "architecture/DDD.md",
  "name": "ddd-implementation",
  "description": "DDD constraints and current implementation structure that AI should load when working on domain modeling, aggregates, repositories, or DDD layer responsibilities.",
  "always_load": false,
  "domains": [],
  "triggers": [
    "ddd",
    "aggregate",
    "entity",
    "value object",
    "repository",
    "domain service",
    "bounded context"
  ],
  "priority": "L1"
}
```

### `tool/tool.md`
```json
{
  "path": "tool/tool.md",
  "name": "project-tools",
  "description": "Reusable project utilities, helper patterns, and preferred third-party tools that AI should load when choosing shared helpers or recurring technical solutions.",
  "always_load": false,
  "domains": [],
  "triggers": [
    "utility reuse",
    "validator",
    "mapper",
    "tracing",
    "id generation",
    "helper selection"
  ],
  "priority": "L1"
}
```

### `domain/merchant.md`
```json
{
  "path": "domain/merchant.md",
  "name": "domain-merchant",
  "description": "Domain-specific model, constraints, and collaborators for Merchant; load this file when the task mainly affects merchant business logic or model behavior.",
  "always_load": false,
  "domains": [
    "merchant",
    "商户"
  ],
  "triggers": [
    "merchant",
    "商户",
    "merchant review",
    "merchant onboarding"
  ],
  "priority": "L2"
}
```

### `domain/<another-domain>.md`
```json
{
  "path": "domain/<another-domain>.md",
  "name": "domain-<another-domain>",
  "description": "Domain-specific model, constraints, and collaborators for <Another Domain>; load this file when the task mainly affects this domain's business logic or model.",
  "always_load": false,
  "domains": [
    "<another-domain>"
  ],
  "triggers": [
    "<another-domain>"
  ],
  "priority": "L2"
}
```
````

## Initialization Guidance

- Keep `constitution/constitution.md` as `always_load: true`.
- Keep only existing Specification files in the first real version of `index.md`.
- Remove placeholder domain entries that do not correspond to real files.
- Add one manifest block per real `domain/*.md` file.
- Use frontmatter `name` and `description` from each Specification file whenever available.
- Keep entries ordered by `L0`, `L1`, `L2`, then `L3`.

## First-Pass Checklist

When creating the first real `index.md`:

1. Copy the template.
2. Delete placeholder entries for missing files.
3. Replace example domain entries with real domain Specification files.
4. Refresh `domains` and `triggers` from the actual Specification text.
5. Save the file as `./specification/index.md`.
