---
name: indexing-spec
description: 维护 `./specification/index.md` 中用于 Spec-Driven Development (SDD) 的 Specification manifest index。当 Specification markdown 文件被创建、更新、删除，或其 YAML frontmatter 发生变化时使用，尤其适用于仅 incremental refresh 受影响的 manifest entries，而不是重建整个 index。
---

# Indexing Spec

仅维护项目的 Specification index。不要把解决业务需求作为这个 skill 的一部分。

使用这个 skill 保持 `./specification/index.md` 准确，以便后续 retrieval 能够用最小 context 判断需要加载哪些 Specification 文件。

## 目标

为 `./specification` 中除 `index.md` 自身之外的每个 Specification 文件保留一条 manifest entry。

每条 manifest entry 必须包含：
- `path`
- `name`
- `description`
- `always_load`
- `domains`
- `triggers`
- `priority`

每条 entry 都以 JSON 表示。遵循 [references/index-format.md](references/index-format.md) 中的 index format。
当 `./specification/index.md` 尚不存在时，从 [references/index-template.md](references/index-template.md) 开始。

## Workflow

1. 确定 Specification root。
   如果用户没有提供，询问用户并推荐 `./specification`。

2. 找出已变更的 Specification 文件。
   按以下顺序优先进行 incremental detection：
   - 用户明确点名的文件
   - `git diff --name-only` 或 `git status --short`
   - 在 `./specification` 内进行有针对性的 filesystem scan

3. 从 indexing 中排除 `index.md`。
   包含 root 下其他 Specification markdown 文件，包括 `TODO.md` 等 operational files，但前提是它们已经存在于 Specification root 下。

4. 读取受影响的 Specification 文件和当前的 `./specification/index.md`。
   只有当现有 index 缺失、损坏或明显过期时，才检查未变更的 Specification 文件。

5. 为每个受影响的文件构建或刷新一条 manifest entry。
   优先使用文件 frontmatter，然后使用 path、headings 和简洁的 body evidence 填充 frontmatter 未提供的字段。

6. 在可能时仅更新受影响的 manifest entries。
   保留未变更的 entries，并保持稳定排序。

7. 移除已删除 Specification 文件对应的 manifest entries。

8. 写入更新后的 `./specification/index.md`。

## Manifest Derivation Rules（manifest 推导规则）

### `path`

- 使用相对于 `./specification` 的 path
- 始终使用 forward slashes

### `name`

- 优先使用 YAML frontmatter 中的 `name`
- 如果文件没有 frontmatter，根据其角色和文件名推导一个稳定的 name

### `description`

- 优先使用 YAML frontmatter 中的 `description`
- 如果缺失，推导一个简短、面向 routing 的 summary，说明：
  - 文件包含什么
  - 在 SDD 期间何时应加载它

### `always_load`

- 仅 `constitution/constitution.md` 使用 `true`
- 所有其他 Specification 文件使用 `false`，除非用户明确希望另一个文件也全局加载

### `domains`

- 使用 business domains，而不是 technical layers
- 对于 `domain/*.md`，当文件中出现具体 domain name 和明显 aliases 时，将它们包含进来
- 对于 `constitution.md`、`architecture.md` 或 `tool.md` 等 global files，除非文件有意限定到特定 domains，否则使用空数组

### `triggers`

- 记录有助于将未来 tasks route 到该文件的短语
- 优先使用文件中已经可见的 domain terms、business capabilities 和 task categories
- 避免 `code`、`bugfix` 或 `task` 等泛化 triggers

### `priority`

使用 loading tiers，而不是任意数字：
- `L0`: always-load constitutional rules
- `L1`: task-type files，例如 architecture、DDD 和 tool guidance
- `L2`: domain files，例如 `domain/merchant.md` 和 `domain/terminal.md`
- `L3`: optional 或 operational Specification files，通常不属于 minimum load set

## Incremental Update Policy（增量更新策略）

默认使用 incremental updates。

满足以下条件时，只更新已变更的 entries：
- 当前 `index.md` 存在
- entry format 有效
- 能够高置信度识别 changed files

仅在以下情况下 fallback 到 full rebuild：
- `index.md` 不存在
- manifest schema 已变更
- 大量 Specification 文件被移动或重命名
- 当前 index 与 filesystem 明显不一致

当 `index.md` 缺失时：
- 基于 [references/index-template.md](references/index-template.md) 创建它
- 用已经真实存在的 Specification 文件推导出的 manifests 替换 placeholder entries

## Indexable File Guidance（可索引文件指南）

推导 manifests 时应用以下默认值：

- `constitution/constitution.md`
  - `always_load: true`
  - `priority: "L0"`

- `architecture/architecture.md`
  - `always_load: false`
  - `priority: "L1"`
  - 添加与 architecture、module split、layering、framework、cross-module changes 相关的 triggers

- `architecture/DDD.md`
  - `always_load: false`
  - `priority: "L1"`
  - 添加与 aggregate、entity、value object、repository、domain service、bounded context、DDD 相关的 triggers

- `tool/tool.md`
  - `always_load: false`
  - `priority: "L1"`
  - 添加与 utility reuse、helper selection、validator、mapper、tracing、ID generation、common tooling 相关的 triggers

- `domain/*.md`
  - `always_load: false`
  - `priority: "L2"`
  - domains 应反映具体的 bounded context 或 business domain

- `TODO.md` 等 operational files
  - `always_load: false`
  - `priority: "L3"`
  - 除非用户明确希望这些文件参与 retrieval，否则保持 triggers 稀疏

## Writing Rules（写入规则）

- 即使 entries 是 JSON，也要保持 `index.md` human-readable。
- 优先为每个 Specification 文件使用一个 JSON block，而不是一个巨大的 JSON blob。
- 保持 manifest entries 简洁且 routing-friendly。
- 保留与当前变更无关的现有有效 entries。
- 不要在没有 evidence 的情况下编造 domains 或 triggers。
- 如果 evidence 较弱，宁可让 `domains` 或 `triggers` 保持较小，也不要进行推测。

## Completion（完成条件）

只有在满足以下条件后才结束：
- `./specification/index.md` 存在
- 所有受影响的 Specification 文件都已反映在 index 中
- 已删除文件已从 index 中移除
- 只要 incremental update 可行，未变更 entries 就保持 intact
