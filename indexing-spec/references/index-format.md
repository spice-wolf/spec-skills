# Specification Index Format

Store `./specification/index.md` as markdown with one JSON manifest block per Specification file. This keeps the file readable and makes incremental updates easier than rewriting one large array.

## Recommended Structure

```md
# Specification Index

This file routes later SDD tasks to the minimum relevant Specification files.

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
```

## Field Semantics

- `path`: relative path from `./specification`
- `name`: stable identifier, usually from YAML frontmatter
- `description`: short routing summary
- `always_load`: whether retrieval must always include this file
- `domains`: relevant business domains or aliases
- `triggers`: phrases that help task-to-spec matching
- `priority`: `L0`, `L1`, `L2`, or `L3`

## Ordering

Keep entries ordered by:
1. `priority`
2. path category
3. lexical path

Suggested priority order:
1. `L0`
2. `L1`
3. `L2`
4. `L3`

## Incremental Update Rules

- Replace only the JSON block for changed files whenever possible.
- Add new sections for new files.
- Remove sections for deleted files.
- Preserve surrounding prose and unaffected entries.
