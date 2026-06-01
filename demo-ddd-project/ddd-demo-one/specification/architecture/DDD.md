---
name: ddd-implementation
description: Current DDD constraints, layer responsibilities, and domain-model structure that AI should load when working on aggregates, repositories, or bounded contexts.
---

# DDD Implementation

## Status
项目具有明确的 DDD 特征：模块按 `domain / app / infrastructure / adapter / start` 分层，业务按 `catalog`、`customer`、`order` 三个边界上下文拆分，领域层暴露仓储接口并在模型内维护业务约束。

## Project DDD Rules
- 领域对象自行维护状态和不变量，例如商品价格、客户额度、订单状态流转等规则不应外移到控制器。
- 仓储接口定义在领域层，具体实现放在基础设施层。
- 应用层负责跨聚合或跨领域编排，例如创建订单时同时检查客户与商品数据。
- 接口层只做协议适配、参数校验与异常映射，不直接承载领域决策。
- 共享领域错误统一使用 `DomainException`，由适配层转换为 HTTP 响应。

## Layer Mapping
- Domain: `Product`、`Customer`、`Order` 及其相关枚举、子对象、仓储接口。
- Application: `CatalogApplicationService`、`CustomerApplicationService`、`OrderApplicationService` 以及命令/DTO。
- Infrastructure: `InMemoryProductRepository`、`InMemoryCustomerRepository`、`InMemoryOrderRepository`、`DemoDataInitializer`。
- Interfaces: `CatalogController`、`CustomerController`、`OrderController`、请求对象和 `GlobalExceptionHandler`。

## Bounded Contexts
- `catalog`
  负责商品目录与商品可售状态。
- `customer`
  负责客户资料、客户等级与信用额度。
- `order`
  负责订单创建、支付、发货与订单金额汇总。

## Core Model
- Aggregate roots
  `catalog` 的 `Product`、`customer` 的 `Customer`、`order` 的 `Order`。
- Entities
  顶层领域对象均以 `id` 作为身份判断；`OrderLine` 是订单内部子对象，但当前代码中未显式区分它是实体还是值对象。
- Value objects
  当前没有显式命名为值对象的类型；`CustomerLevel`、`OrderStatus` 是枚举型领域概念。
- Domain services
  当前代码中未看到独立领域服务，业务编排主要由应用服务承担。
- Repositories
  `ProductRepository`、`CustomerRepository`、`OrderRepository`。

## 待确认
- `OrderLine` 在后续建模中是否会被稳定定义为值对象。
- 当前示例中尚未看到领域事件、工厂、规范（Specification Pattern）或防腐层实现。
