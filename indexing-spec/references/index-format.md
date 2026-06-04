# Specification Index Format

将 `./specification/index.md` 存储为 markdown，并为每个 Specification 文件保留一个 JSON manifest block。这样可以保持文件可读，也让 incremental updates 比重写一个大型 array 更容易。

## Recommended Structure（推荐结构）

```md
# Specification Index

此文件将后续 SDD tasks route 到最小相关 Specification 文件集合。

## Entries

### `constitution/constitution.md`
```json
{
  "path": "constitution/constitution.md",
  "name": "project-constitution",
  "description": "AI 在对此 repository 中任何 change 进行 planning、implementing、refactoring 或 reviewing 之前都应加载的不可协商项目规则和 coding constraints。",
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
  "description": "当 AI 设计、修改或 review cross-module 或 architectural work 时应加载的 high-level project architecture、framework、module split 和 layer mapping。",
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
  "description": "Merchant 的 domain-specific model、constraints 和 collaborators；当 task 主要影响 merchant business logic 或 model behavior 时加载此文件。",
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

## Field Semantics（字段语义）

- `path`: 相对于 `./specification` 的 path
- `name`: 稳定 identifier，通常来自 YAML frontmatter
- `description`: 简短的 routing summary
- `always_load`: retrieval 是否必须始终包含该文件
- `domains`: 相关 business domains 或 aliases
- `triggers`: 帮助 task-to-spec matching 的短语
- `priority`: `L0`、`L1`、`L2` 或 `L3`

## Ordering

按以下顺序排列 entries：
1. `priority`
2. path category
3. lexical path

建议的 priority order：
1. `L0`
2. `L1`
3. `L2`
4. `L3`

## Incremental Update Rules（增量更新规则）

- 在可能时仅替换 changed files 对应的 JSON block。
- 为 new files 添加新的 sections。
- 移除 deleted files 对应的 sections。
- 保留周围 prose 和未受影响的 entries。
