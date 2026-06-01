---
name: domain-customer
description: Customer domain model and credit-related constraints; load this file when changing customer rules, customer APIs, or order eligibility checks.
---

# Customer Domain

## Responsibility
`customer` 领域负责客户基本资料、客户等级、信用额度和客户是否有资格下单的判断。

## Main Model
- Aggregate roots
  `Customer`
- Entities
  `Customer` 以 `id` 标识身份，包含 `name`、`level`、`creditLimit` 和 `active` 状态。
- Value objects
  当前未看到显式值对象；`CustomerLevel` 是客户等级枚举。

## Domain Services And Repositories
- Domain services
  当前未看到独立领域服务。
- Repositories
  `CustomerRepository` 提供按 `id` 查询、查询全部和保存能力。

## Upstream And Downstream
- Upstream
  客户由 `CustomerApplicationService` 注册并输出客户 DTO。
- Downstream
  `order` 领域创建订单前会校验客户存在、客户激活状态和客户额度是否足够。

## Constraints
- 客户 `id` 不能为空。
- 客户名称不能为空。
- 客户等级不能为空。
- 信用额度不能为负数。
- 只有激活客户且额度大于等于订单总额时，客户才可以下单。

## 待确认
- 当前未看到客户地址、联系人或额度变更历史等扩展模型。
