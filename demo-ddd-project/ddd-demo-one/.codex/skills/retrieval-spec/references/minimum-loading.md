# Minimum Loading Strategy

Use this strategy to keep SDD context small but sufficient.

## Layer Model

### L0 Mandatory

Always load:

- `constitution/constitution.md`

Reason:
- this file defines non-negotiable project rules
- it should constrain every later plan, implementation, refactor, and review

### L1 Task-Type

Load task-type files only when the request needs them.

#### `architecture/architecture.md`

Load when the task involves:
- architecture changes
- module boundaries
- framework or runtime choices
- integration boundaries
- cross-module coordination

#### `architecture/DDD.md`

Load when the task involves:
- aggregates
- entities
- value objects
- repositories
- domain services
- bounded contexts
- DDD rule compliance

#### `tool/tool.md`

Load when the task involves:
- choosing reusable helpers
- validators
- mappers
- tracing
- ID generation
- common cross-cutting utilities

### L2 Domain

Load only the relevant `domain/*.md` files.

Examples:
- merchant tasks -> `domain/merchant.md`
- terminal tasks -> `domain/terminal.md`
- merchant-terminal collaboration -> both files

Use these signals:
- exact domain names
- aliases listed in `domains`
- business phrases listed in `triggers`
- verbs and nouns in the request that clearly imply one domain

## Two-Phase Example

User request:
`帮我完成新增商户审核功能的需求`

Phase 1 routing decision:
- `constitution/constitution.md`
  - reason: mandatory L0
- `domain/merchant.md`
  - reason: domain match on `merchant` / `商户`
- `architecture/DDD.md`
  - deferred unless the task requires aggregate or repository changes

Phase 2 execution:
- read the selected files
- apply the constraints and domain model
- continue with requirement analysis and implementation

## Escalation Rule

When the selected files are insufficient:
1. add one more L1 file if the gap is architectural or DDD-related
2. add one more L2 file if another domain becomes involved
3. avoid jumping to full-tree loading unless the task is truly cross-cutting
