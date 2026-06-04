# Initial `index.md` Template

当 `./specification/index.md` 尚不存在时，使用此文件作为 starting point。将 placeholder text 替换为 repository-specific values，并移除不存在的 Specification 文件对应的 entries。

## Copyable Template（可复制模板）

````md
# Specification Index

此文件将后续 SDD tasks route 到最小相关 Specification 文件集合。
先读取此文件，然后只加载 manifests 与当前 task 匹配的 Specification 文件。

## Loading Policy

- `L0`: always-load Specification files
- `L1`: task-type Specification files
- `L2`: domain Specification files
- `L3`: optional 或 operational Specification files

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

### `architecture/DDD.md`
```json
{
  "path": "architecture/DDD.md",
  "name": "ddd-implementation",
  "description": "当 AI 处理 domain modeling、aggregates、repositories 或 DDD layer responsibilities 时应加载的 DDD constraints 和当前 implementation structure。",
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
  "description": "当 AI 选择 shared helpers 或 recurring technical solutions 时应加载的 reusable project utilities、helper patterns 和 preferred third-party tools。",
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

### `domain/<another-domain>.md`
```json
{
  "path": "domain/<another-domain>.md",
  "name": "domain-<another-domain>",
  "description": "<Another Domain> 的 domain-specific model、constraints 和 collaborators；当 task 主要影响此 domain 的 business logic 或 model 时加载此文件。",
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

## Initialization Guidance（初始化指南）

- 保持 `constitution/constitution.md` 为 `always_load: true`。
- 在第一个真实版本的 `index.md` 中，只保留已经存在的 Specification 文件。
- 移除不对应真实文件的 placeholder domain entries。
- 为每个真实的 `domain/*.md` 文件添加一个 manifest block。
- 只要可用，就使用每个 Specification 文件中的 frontmatter `name` 和 `description`。
- 保持 entries 按 `L0`、`L1`、`L2`、然后 `L3` 排序。

## First-Pass Checklist（首轮检查清单）

创建第一个真实的 `index.md` 时：

1. 复制 template。
2. 删除 missing files 对应的 placeholder entries。
3. 用真实的 domain Specification 文件替换 example domain entries。
4. 根据实际 Specification text 刷新 `domains` 和 `triggers`。
5. 将文件保存为 `./specification/index.md`。
