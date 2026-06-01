---
name: project-tools
description: Reusable helpers, exception patterns, and preferred framework utilities that AI should load when selecting shared technical solutions in this project.
---

# Project Tools

## Overview
本文件记录当前仓库里已经形成复用习惯或可作为默认方案的工具类、模式与框架能力。

## Recommended Tools

### DomainException
- Type: internal utility
- Purpose: 统一表达领域规则冲突和业务校验失败。
- Prefer When: 在领域模型或应用服务中发现明确业务违规，需要向外层返回可理解的业务错误时。
- Typical Usage: `demo-domain` 的实体构造与状态流转校验，`demo-app` 的重复校验与跨领域前置检查。

### TraceIdUtil
- Type: internal utility
- Purpose: 为返回 DTO 生成简单追踪标识。
- Prefer When: 需要保持与现有 DTO 输出一致，或在演示接口中补充轻量追踪字段时。
- Typical Usage: `CatalogApplicationService`、`CustomerApplicationService`、`OrderApplicationService` 在 DTO 映射时调用。

### InMemory*Repository
- Type: internal utility pattern
- Purpose: 为领域仓储提供基于 `ConcurrentHashMap` 的轻量内存实现，便于演示和本地运行。
- Prefer When: 需要快速验证领域行为、保持示例工程可运行，且尚未引入真实持久化时。
- Typical Usage: `demo-infrastructure` 中的 `InMemoryProductRepository`、`InMemoryCustomerRepository`、`InMemoryOrderRepository`。

### GlobalExceptionHandler
- Type: internal utility
- Purpose: 把 `DomainException`、参数校验异常和未知异常统一转换为 REST 错误响应。
- Prefer When: 为新的 REST 接口补齐统一异常输出，而不是在单个控制器里重复编写 `try/catch`。
- Typical Usage: `demo-adapter/shared/interfaces` 下集中处理控制器抛出的异常。

### Jakarta Validation On Request Records
- Type: third-party library pattern
- Purpose: 在适配层用声明式注解校验入参，再转换为应用层命令对象。
- Prefer When: 新增 REST 请求对象时需要校验非空、列表项有效性等输入约束。
- Typical Usage: `RegisterProductRequest`、`RegisterCustomerRequest`、`CreateOrderRequest` 及其子项请求对象。

## Notes
- `DemoDataInitializer` 是启动阶段的演示数据装载组件，适合本地示例场景，但不应替代正式业务初始化流程。
- 当前仓库没有看到更复杂的共享映射器、ID 生成器或持久化抽象工具；如后续引入，应在本文件补充。
