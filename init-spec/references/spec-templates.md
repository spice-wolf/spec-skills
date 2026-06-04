# Specification Templates

这些模板用于作为简洁的起点。请根据实际仓库情况调整标题，并省略空章节。

## Metadata Convention

除 `TODO.md` 外，每个 Specification markdown 文件都应以 YAML frontmatter 开头：

```md
---
name: stable-spec-name
description: 简要说明这个 Specification 包含什么，以及在后续 SDD 工作中什么时候应该把它加载为上下文。
---
```

说明：
- 只保留 `name` 和 `description`
- `description` 尽量保持为一句话
- `description` 应优先服务于路由和 progressive disclosure，而不是追求完整性
- 使用仓库中的项目术语

## `constitution/constitution.md`

```md
---
name: project-constitution
description: 记录该仓库中的硬性项目规则与编码约束；在规划、实现、重构或 review 任意改动前应加载此文件。
---

# Project Constitution

## Purpose
用一句话说明这份 constitution 的用途。

## Rules
1. 规则 1
2. 规则 2
3. 规则 3

## Pending Clarification
- 重要但尚未完全证实的规则
```

说明：
- 规则总数最好不超过 10 条
- 只写那些 AI 在后续开发中必须始终遵守的稳定规则
- 优先记录仓库特有的约束，而不是泛化的工程建议
- 这个文件通常应该在任何仓库级工程任务中被加载
- 文件生成后，应将其中具体的 `Pending Clarification` 项转化为聚焦的用户提问，并在得到回答后回写到文件中，同时减少或移除已解决条目

## `architecture/architecture.md`

```md
---
name: project-architecture
description: 记录高层项目架构、framework、module 划分与 layer 映射；在设计、修改或 review 跨 module 或架构层面的工作时应加载此文件。
---

# Project Architecture

## Overview
用一句话概括项目目标与整体结构。

## Framework And Runtime
- Java version
- framework 或 platform
- build system

## Module And Layering
- module 或 package 划分
- 每层职责
- 如果使用 DDD，说明 modules/packages 与 DDD layers 的映射关系

## Git Submodules
- submodule 名称与用途

## Notes
- 过渡态架构信息或待补充说明
```

说明：
- 保持文档的架构视角
- 不要详细描述业务流程
- 当任务涉及 module 边界、分层或 framework 级决策时，应加载此文件

## `architecture/DDD.md`

```md
---
name: ddd-implementation
description: 记录 DDD 约束与当前实现结构；在处理 domain modeling、aggregate、repository 或 DDD 分层职责时应加载此文件。
---

# DDD Implementation

## Status
说明为什么判断该项目使用了 DDD，或 DDD 特征具体体现在哪里。

## Project DDD Rules
- 必须遵守的 DDD 规则

## Layer Mapping
- domain
- application
- infrastructure
- interfaces 或 adapters

## Bounded Contexts
- context 名称与职责

## Core Model
- aggregate roots
- entities
- value objects
- domain services
- repositories

## Pending Clarification
- 尚未能从代码库中完全确认的 DDD 信息
```

说明：
- 只有在项目存在 DDD 或强烈体现 DDD 特征时才生成此文件
- 即使实现是部分的或不一致的，也要如实记录当前状态
- 当任务涉及领域边界、domain model 演进或 DDD 规则符合性时，应加载此文件
- 文件生成后，应将其中具体的 `Pending Clarification` 项转化为聚焦的用户提问，并在得到回答后回写到文件中，同时减少或移除已解决条目

## `domain/<domain-name>.md`

```md
---
name: domain-<domain-name>
description: 记录 <Domain Name> 的 domain model、约束、协作者以及写侧行为；当任务主要影响该领域的业务逻辑或 model 时应加载此文件。
---

# <Domain Name> Domain

## Responsibility
用一句话说明该领域负责什么业务能力。

## Main Model
- aggregate roots
- entities
- value objects

## Domain Behaviors
### Behavior Name
- Source: `OwningType.method(...)` 或 `CommandExecutor.method(...)`
- What It Does: 简要描述该行为做了什么
- Business Effect: 说明这个行为为什么存在，或它带来了什么业务效果
- Main Logic:
  - 逻辑要点 1
  - 逻辑要点 2

## Domain Services And Repositories
- services
- repositories

## Upstream And Downstream
- 重要协作者、外部系统或相邻领域

## Constraints
- 不变量、业务规则或实现限制

## Pending Clarification
- 仍然不清楚的事项
```

说明：
- 每个具体领域生成一个文件
- 使用仓库中的真实领域名称
- 范围只限定在当前领域
- 需要时只加载相关领域文件，而不是一次性加载所有领域文件
- `Domain Behaviors` 应优先从 public domain methods 提取，再由紧密相关的 application command executor 补充
- 不要把 query interface、query method、query executor 或其他只读行为放进 `Domain Behaviors`
- 文件生成后，应将其中具体的 `Pending Clarification` 项转化为聚焦的用户提问，并在得到回答后回写到文件中，同时减少或移除已解决条目

## `tool/tool.md`

```md
---
name: project-tools
description: 记录可复用的项目 utility、helper pattern 与推荐使用的 third-party tools；当需要选择共享 helper 或重复性技术方案时应加载此文件。
---

# Project Tools

## Overview
用一句话说明这里记录的是哪些可复用工具与模式。

## Recommended Tools
### Tool Name
- Type: internal utility 或 third-party library
- Purpose: 它解决什么问题
- Prefer When: 什么情况下应优先使用它
- Typical Usage: 它通常在哪些 module 或场景中重复出现

## Notes
- 例外情况或迁移说明
```

说明：
- 优先记录那些被多个 module 重复使用的 internal utility、wrapper、validator、tracing helper、id helper、mapping helper，或已经成为标准方案的 third-party library
- 排除一次性 helper，以及没有清晰使用模式的通用依赖
- 当需要实现通用横切能力，或在已有 helper 与新方案之间做选择时，应加载此文件
- 如果存在 `Pending Clarification` 章节，文件生成后应将其中具体条目转化为聚焦的用户提问，并在得到回答后回写到文件中，同时减少或移除已解决条目

## `./specification/TODO.md`

```md
# Specification TODO

- [ ] constitution/constitution.md
- [ ] architecture/architecture.md
- [ ] architecture/DDD.md
- [ ] domain/domain-a.md
- [ ] domain/domain-b.md
- [ ] tool/tool.md
```

说明：
- 只包含实际适用的文件
- 用真实的领域名替换占位符领域名
- 每生成并澄清完一个文件后更新 checklist
