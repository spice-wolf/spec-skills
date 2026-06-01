---
name: domain-order
description: Order domain workflow, model, and cross-domain constraints; load this file when changing order creation, status transitions, or order-related APIs.
---

# Order Domain

## Responsibility
`order` 领域负责订单创建、订单明细汇总、支付与发货状态流转。

## Main Model
- Aggregate roots
  `Order`
- Entities
  `Order` 以 `id` 标识身份，包含 `customerId`、`createdAt`、`status` 和多个 `OrderLine`。
- Value objects
  当前未显式声明值对象；`OrderStatus` 是状态枚举。`OrderLine` 是订单内部子对象，承载商品、单价与数量信息。

## Domain Services And Repositories
- Domain services
  当前未看到独立领域服务。
- Repositories
  `OrderRepository` 提供按 `id` 查询、查询全部和保存能力。

## Upstream And Downstream
- Upstream
  订单创建依赖 `customer` 领域提供客户信息，依赖 `catalog` 领域提供商品信息和商品可用状态。
- Downstream
  当前仓库中未看到订单向其他外部系统发布事件或驱动下游流程。

## Constraints
- 订单 `id` 不能为空。
- `customerId` 不能为空。
- 订单至少包含一个订单行。
- 订单总金额由全部 `OrderLine` 小计累加得到。
- 只有 `CREATED` 状态订单可以支付。
- 只有 `PAID` 状态订单可以发货。
- 创建订单前必须确认客户存在、客户已激活、客户额度足够，且订单中的商品存在且处于激活状态。

## 待确认
- 当前未看到取消订单、退款、库存预占等后续订单生命周期规则。
