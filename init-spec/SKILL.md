---
name: init-spec
description: 为 Java 项目初始化 markdown Specification 文件，尤其适用于基于 DDD 的系统，使这些文件能够在后续 Spec-Driven Development (SDD) 中作为上下文使用。适用于需要分析现有项目、识别其架构、领域、可复用工具与领域行为，并在 `./specification` 下创建首批文件时，包括 constitution.md、architecture.md、DDD.md、domain/*.md 与 tool.md。
---

# Init Spec

只负责初始化项目的 Specification。不要在这个 skill 中实现业务代码。

使用这个 skill 时，需要先检查当前仓库，提炼稳定的项目事实，再创建后续 SDD 工作会依赖的初始 markdown Specification 文件。使用中文输出 markdown Specification 文件的内容，但是保留必要的专业术语、技术名词原文，对于项目中的专有名词，使用项目中出现的原文。

## Workflow

1. 使用 `./specification` 作为固定的 Specification 根目录。
2. 在编写任何 Specification 文件之前，先检查项目。
   对于 Java 项目，至少检查：
   - `pom.xml`、`build.gradle*`、`settings.gradle*`
   - 多模块目录结构
   - `src/main/java`
   - package 名称与 module 名称
   - 如果存在则检查 `.gitmodules`
   - 已有的、定义编码规范或架构约束的文档
3. 判断项目是否使用 DDD，或是否具有明显的 DDD 特征。
   当仓库中清晰体现出以下任一信号时，可认为存在 DDD：
   - 明确的分层，例如 `domain`、`application`、`infrastructure`、`interfaces`
   - module 与 DDD 分层之间存在对应关系
   - bounded context 或领域 package 具有清晰的业务边界
   - 出现 aggregate root、entity、value object、repository、domain service 等概念
4. 识别具体领域。
   只统计能够从代码库或现有文档中明确识别出的领域。
   优先使用 bounded context 或顶层业务领域，而不是技术性 package。
   如果证据不足，不要编造细节，而应在生成文档中标记为 `Pending Clarification`。
5. 根据领域数量判断项目规模。
   - 大于 3 个领域：large project
   - 3 个及以下领域：small project
6. 在完成项目规模判断后，询问用户选择初始化模式，除非用户已经明确指定。
   推荐规则如下：
   - large project：推荐 step-by-step initialization
   - small project：推荐 all-at-once initialization
7. 在 `./specification` 下生成 Specification 文件。
   按照 [references/spec-templates.md](references/spec-templates.md) 中定义的文件映射与模板执行。
9. 对于 domain Specification 文件，必须增加 `Domain Behaviors` 章节。
   领域行为应优先从 domain entity、aggregate root 以及其他具有明确业务行为的 domain model 类型的 public 方法中提取。
   当 application 层中紧密相关的 command executor 能更清晰地揭示真实的写侧业务流程时，可以补充从这些 command executor 中提取行为。
   不要把 query interface、query method、query executor、只读 assembler 或其他纯读侧行为算作领域行为。
10. 每生成一个 Specification 文件后，都要检查它是否包含带有具体未解问题的 `Pending Clarification` 章节。
    如果包含：
    - 立即收集这些未解决项
    - 将它们转换成简短、直接、面向用户的提问
    - 只询问那些对完善当前 Specification 文件确实必要的问题
    - 在用户回答后，回写同一个 Specification 文件，把澄清后的事实整理到合适的章节中
    - 对已经解决的 `Pending Clarification` 条目进行缩减、修订或移除
    如果某个歧义可以在初始化阶段通过提问解决，就不要只把它作为被动备注留在文档里。
11. 如果用户选择 step-by-step initialization，则创建并维护 `./specification/TODO.md`。
    每生成并澄清完一个 Specification 文件后，都要更新 checklist，以便后续运行能够从当前状态继续。

## Specification Metadata

除 `TODO.md` 外，每一个生成出来的 Specification markdown 文件都必须以 YAML frontmatter 开头。

使用与 Agent Skill metadata 相同的轻量模式：

- `name`：该 Specification 文件稳定且简短的标识符
- `description`：用一句简洁的话同时说明：
  - 这个 Specification 文件包含什么内容
  - 在后续 SDD 工作中，什么时候应该把它加载进 AI 上下文

Metadata 规则：
- 保持 frontmatter 简短
- 只使用 `name` 和 `description`
- `description` 应优先服务于路由与 progressive disclosure，而不是追求冗长完整
- 优先使用仓库里的项目术语，而不是泛化表达
- 除非用户明确要求，否则不要增加额外字段

## Required Interaction

在用户尚未提前回答时，需要进行以下提问：

1. 完成项目检查后：`Detected <N> domains and classified this as a <large/small> project. Recommended mode: <step-by-step/all-at-once> initialization. Continue with that mode?`
2. 当生成出的某个 Specification 文件包含未解决的 `Pending Clarification` 项时：基于这些项提出聚焦的澄清问题，并在该文件被视为完成前先完成澄清。

如果用户已经给出了初始化模式，就不要重复询问。尊重用户的明确选择。

## File Map

按以下规则生成文件。

### `./specification/constitution/constitution.md`

始终生成此文件。

写一份简短的 constitution，记录 AI 在该仓库中开发时必须始终遵守的规则。

要求：
- 保持简洁
- 规则最好不超过 10 条
- 包含 YAML frontmatter metadata
- 规则必须来源于真实项目，而不是泛化的最佳实践
- 只记录那些证据稳定、置信度高的规则
- 如果某条重要规则很可能成立但尚未完全证实，应放入简短的 `Pending Clarification` 章节，而不是当作硬性规则陈述
- 生成后，如果存在明确的 `Pending Clarification` 项，要向用户提问并把确认后的结果回写到该文件

### `./specification/architecture/architecture.md`

始终生成此文件。

应覆盖：
- 项目的 framework 与核心 runtime stack
- 整体分层或 module 划分
- 如果使用 DDD，说明 modules 或 packages 与 DDD layers 的映射关系
- 如果存在 `.gitmodules`，记录 Git submodules
- 包含 YAML frontmatter metadata
- 生成后，如果存在明确的 `Pending Clarification` 项，要向用户提问并把确认后的结果回写到该文件

不要把这个文件写成功能流程说明或时序图。保持架构视角。

### `./specification/architecture/DDD.md`

只有在项目存在 DDD，或强烈体现出 DDD 特征时，才生成此文件。

应覆盖：
- 该项目必须遵守的 DDD 规则
- 当前 DDD 实现状态
- 当代码库中能够识别出来时，记录当前的 layers、bounded contexts、aggregates、entities、value objects、repositories 与 domain services
- 包含 YAML frontmatter metadata
- 生成后，如果存在明确的 `Pending Clarification` 项，要向用户提问并把确认后的结果回写到该文件

### `./specification/domain/${specific-domain}.md`

只有在存在 DDD，或项目清晰表现出类似 DDD 的领域边界时，才为每个具体领域生成一个文件。

要求：
- 用仓库中真实存在的领域名替换 `${specific-domain}`，例如 `merchant`、`terminal`，或项目中识别出的其他领域
- 每个领域生成一个文件
- 只描述该领域，不要描述整个系统
- 名称必须来源于仓库，而不是自行杜撰
- 增加 `Domain Behaviors` 章节，用于总结非查询类的领域行为
- 包含 YAML frontmatter metadata
- 生成后，如果存在明确的 `Pending Clarification` 项，要向用户提问并把确认后的结果回写到该文件

对于 `Domain Behaviors`：
- 主要从 domain entity、aggregate root 以及其他具有明确业务行为的 domain model 类型的 public 方法中提取行为
- 当 application 层里紧密相关的 command executor 更能体现围绕该 domain model 的真实写侧业务流程时，可以补充使用这些 command executor
- 对每个行为，简要记录：
  - 行为名称
  - 主要来源或执行主体，例如 `ChannelSettlementDO.initiateSettlement(...)` 或 `AcceptSettlementDocumentCmdExe.execute(...)`
  - 它做了什么
  - 它存在的原因，或产生了什么业务效果
  - 用 1 到 3 个简短要点概括其主要逻辑或状态流转
- 不要把 query interface、query method、query executor、只读 assembler 或其他纯读侧行为算作领域行为
- 优先记录那些会修改状态、推进流程、执行业务规则、触发下游动作，或协调领域副作用的行为

### `./specification/tool/tool.md`

始终生成此文件。

记录那些被重复使用、能够解决常见问题的 reusable utility 或 helper method。

重点关注如下工具：
- 内部 utility class 或 helper package
- 团队反复使用的 framework wrapper
- 已经成为项目标准工具的 third-party library

对每个工具，说明：
- 它解决什么问题
- 它通常出现在哪些场景或位置
- 在什么情况下应优先选用它
- 生成后，如果存在明确的 `Pending Clarification` 项，要向用户提问并把确认后的结果回写到该文件

只收录那些被推荐使用或重复使用的工具。不要列出一次性 helper。
包含 YAML frontmatter metadata。

## Step-by-Step Mode

当用户选择 step-by-step initialization 时：

1. 在生成第一个 Specification 文件之前，创建 `./specification/TODO.md`。
2. 将所有计划生成的文件列成 checklist。
3. 仅在某个可选文件确实适用时，才把它列入 checklist。
4. 对每个已生成文件，只要所需答案是用户能够提供的，就应先与用户完成该文件的 `Pending Clarification` 澄清，再标记为完成。
5. 每生成并澄清完一个文件后，更新 checklist。
6. 后续运行时重新读取 `TODO.md`，并从尚未完成的项继续。

除非用户另有要求，默认按以下顺序执行：
1. `constitution/constitution.md`
2. `architecture/architecture.md`
3. 如果适用，再生成 `architecture/DDD.md`
4. `domain/*.md`
5. `tool/tool.md`

## Writing Rules

- 优先记录来自仓库的事实，而不是假设。
- 优先输出简洁、易维护的 markdown，而不是穷尽式长文。
- 严格使用代码或文档中已经存在的项目术语。
- 总结 method 级行为时，要同时写出所属类型与 method 名称，例如 `ChannelSettlementDO.reviewDocument(...)`
- 对不确定信息使用 `Pending Clarification` 明确标记。
- 把 `Pending Clarification` 视为临时工作区，而不是用户可回答歧义的最终归宿。
- 当用户回答完澄清问题后，应立即更新对应的 Specification 文件，而不是只把答案留在聊天上下文中。
- 避免在 Specification 文件中复制大段代码。
- frontmatter 的 `description` 要简短，并对 progressive disclosure 真正有帮助。
- `constitution.md` 要特别短而稳定。
- `architecture.md` 要聚焦结构，而不是行为。
- `tool.md` 要聚焦重复使用模式，而不是罗列所有构建依赖。
- `domain/*.md` 要聚焦业务模型与业务行为，而不是读侧查询目录。

## Discovery Hints

构建初始 Specification 集合时，可参考以下实际仓库证据：

- Frameworks 与 runtime：检查 Maven 或 Gradle 构建文件
- 分层：检查 module 名称、package 名称，以及 adapter 或 infrastructure 目录
- 领域：检查 domain 层子目录、bounded-context module、aggregate package 或业务语义明显的 namespace
- Git submodules：检查 `.gitmodules`
- reusable tools：检查 common utility package，以及重复出现的 import 或调用点
- 领域行为：优先检查 domain object 的 public 方法，再检查相关 application command executor，以总结写侧、状态变化型行为，并排除 query 行为
- 澄清项：在完成每个文件草稿后，扫描其 `Pending Clarification` 章节，并把每个具体未解决项转成聚焦的用户提问

如果仓库处于混合态或过渡态，应如实记录当前实现，而不是强行套入理想化的 DDD 解释。

## Completion

只有在以下条件全部满足后，才能视为完成：
- 已使用 `./specification` 作为 Specification 根目录
- 已完成项目规模判断
- 用户已选择 step-by-step 或 all-at-once 模式，或用户此前已明确给出
- 对所选模式下适用的所有 Specification 文件都已生成
- 那些用户能够回答的、具体的 `Pending Clarification` 项，已经在初始化过程中被提问，并把答案回写到相应文件中
- 如果使用了 step-by-step 模式，`TODO.md` 已被创建并持续更新
