---
name: domain-catalog
description: Catalog domain model and constraints for product management; load this file when changing product business logic or catalog-facing APIs.
---

# Catalog Domain

## Responsibility
`catalog` 领域负责商品目录维护、商品定价以及商品是否可用的状态表达。

## Main Model
- Aggregate roots
  `Product`
- Entities
  `Product` 以 `id` 标识身份，包含 `name`、`price`、`active` 状态。
- Value objects
  当前未看到显式值对象。

## Domain Services And Repositories
- Domain services
  当前未看到独立领域服务。
- Repositories
  `ProductRepository` 提供按 `id` 查询、查询全部和保存能力。

## Upstream And Downstream
- Upstream
  目录商品由应用层通过 `CatalogApplicationService` 注册。
- Downstream
  `order` 领域创建订单行时需要读取商品信息并校验商品是否处于激活状态。

## Constraints
- 商品 `id` 不能为空。
- 商品名称不能为空。
- 商品价格必须大于 `0`。
- 商品可变更价格，但新价格仍需大于 `0`。
- 已停用商品不能被订单创建流程接受。

## 待确认
- 当前未看到分类、库存、上下架时间等目录扩展模型。
