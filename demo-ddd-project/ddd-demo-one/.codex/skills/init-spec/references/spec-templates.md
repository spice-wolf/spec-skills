# Specification Templates

Use these templates as concise starting points. Adapt headings to the actual repository, and omit empty sections.

## Metadata Convention

Every Specification markdown file except `TODO.md` should begin with YAML frontmatter:

```md
---
name: stable-spec-name
description: Briefly say what this Specification contains and when AI should load it as context during later SDD work.
---
```

Guidance:
- Keep only `name` and `description`.
- Keep `description` to one sentence when possible.
- Optimize `description` for routing and progressive disclosure, not for completeness.
- Use project terminology from the repository.

## `constitution/constitution.md`

```md
---
name: project-constitution
description: Non-negotiable project rules and coding constraints that AI should load before planning, implementing, refactoring, or reviewing any change in this repository.
---

# Project Constitution

## Purpose
一句话说明这份宪法文件的用途。

## Rules
1. 规则 1
2. 规则 2
3. 规则 3

## 待确认
- 仅记录高价值但证据仍不足的规则
```

Guidance:
- Keep the rule list to 10 items or fewer.
- Write only stable rules that AI should always follow in later development work.
- Prefer repository-specific constraints over generic engineering advice.
- This file should usually be loaded for any repository-wide engineering task.

## `architecture/architecture.md`

```md
---
name: project-architecture
description: High-level project architecture, framework, module split, and layer mapping that AI should load when designing, changing, or reviewing cross-module or architectural work.
---

# Project Architecture

## Overview
一句话说明项目目标与整体结构。

## Framework And Runtime
- Java version
- framework or platform
- build system

## Module And Layering
- module or package split
- layer responsibilities
- if DDD is used, map modules/packages to DDD layers

## Git Submodules
- submodule name and purpose

## Notes
- transitional architecture or pending clarification
```

Guidance:
- Keep the document architectural.
- Do not describe business process flows in detail.
- Use this file when the task touches module boundaries, layering, or framework-level decisions.

## `architecture/DDD.md`

```md
---
name: ddd-implementation
description: DDD constraints and current implementation structure that AI should load when working on domain modeling, aggregates, repositories, or DDD layer responsibilities.
---

# DDD Implementation

## Status
说明项目为何被判定为使用 DDD，或者 DDD 特征体现在哪里。

## Project DDD Rules
- 必须遵守的 DDD 约束

## Layer Mapping
- domain
- application
- infrastructure
- interfaces or adapters

## Bounded Contexts
- context name and responsibility

## Core Model
- aggregate roots
- entities
- value objects
- domain services
- repositories

## 待确认
- 当前还无法从代码中确认的 DDD 信息
```

Guidance:
- Generate this file only when DDD is present or strongly implied.
- Record the current state, even if the implementation is partial or inconsistent.
- Load this file when tasks involve domain boundaries, domain model evolution, or DDD rule compliance.

## `domain/<domain-name>.md`

```md
---
name: domain-<domain-name>
description: Domain-specific model, constraints, and collaborators for <Domain Name>; load this file when the task mainly affects this domain's business logic or model.
---

# <Domain Name> Domain

## Responsibility
一句话说明这个领域负责什么业务能力。

## Main Model
- aggregate roots
- entities
- value objects

## Domain Services And Repositories
- services
- repositories

## Upstream And Downstream
- important collaborators, external systems, or adjacent domains

## Constraints
- invariants, business rules, or implementation limits

## 待确认
- still unclear items
```

Guidance:
- Generate one file per concrete domain.
- Use the repository's actual domain names.
- Keep the scope limited to the current domain.
- Load only the relevant domain files instead of all domain files at once whenever possible.

## `tool/tool.md`

```md
---
name: project-tools
description: Reusable project utilities, helper patterns, and preferred third-party tools that AI should load when choosing shared helpers or recurring technical solutions.
---

# Project Tools

## Overview
一句话说明本文件记录的是项目中的常用工具能力。

## Recommended Tools
### Tool Name
- Type: internal utility or third-party library
- Purpose: solves what problem
- Prefer When: when to use it first
- Typical Usage: modules or scenarios where it appears repeatedly

## Notes
- exceptions or migration notes
```

Guidance:
- Prefer repeated internal utilities, wrappers, validators, tracing helpers, id helpers, mapping helpers, or standard third-party libraries used across modules.
- Exclude one-off helpers and generic dependencies with no clear usage pattern.
- Load this file when implementing common cross-cutting concerns or selecting an existing helper instead of inventing a new one.

## `${specification-root}/TODO.md`

```md
# Specification TODO

- [ ] constitution/constitution.md
- [ ] architecture/architecture.md
- [ ] architecture/DDD.md
- [ ] domain/domain-a.md
- [ ] domain/domain-b.md
- [ ] tool/tool.md
```

Guidance:
- Include only applicable files.
- Replace placeholder domain names with real domain names.
- Update the checklist after each generated file.
