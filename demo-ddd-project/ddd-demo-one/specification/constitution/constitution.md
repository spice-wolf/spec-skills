---
name: project-constitution
description: Repository-wide engineering rules and stable layering constraints that AI should load before planning, implementing, refactoring, or reviewing work in this project.
---

# Project Constitution

## Purpose
约束本仓库后续 SDD 与代码变更时必须遵守的稳定规则，避免破坏当前 DDD 多模块结构。

## Rules
1. 保持 Maven 多模块分层方向：`demo-start` 负责启动装配，`demo-adapter` 负责接口适配，`demo-infrastructure` 负责技术实现，`demo-app` 负责应用编排，`demo-domain` 负责核心领域模型。
2. 领域规则与不变量必须优先放在领域模型中维护；违反业务规则时沿用 `com.wayne.ddddemo.shared.domain.DomainException` 表达领域错误。
3. `demo-domain` 只承载业务模型、仓储接口和共享领域概念，不应引入 Web、Controller 或基础设施实现细节。
4. `demo-app` 负责用应用服务编排仓储和跨领域查询，并向外输出 DTO；不要把 HTTP 请求对象或控制器逻辑下沉到应用层。
5. `demo-adapter` 负责 REST 控制器、请求对象、参数校验和异常到 HTTP 的映射；请求对象应转换为应用层命令后再进入应用服务。
6. `demo-infrastructure` 负责实现领域层定义的仓储接口；当前项目的持久化基线是内存仓储实现，不要在规范中假定已有数据库模型。
7. 新业务能力应优先归属现有 `catalog`、`customer`、`order` 三个领域之一；跨领域规则由应用层协调，避免直接在适配层拼装业务逻辑。
8. 保持当前技术基线：Java 17、Spring Boot 3.3.x、Maven 聚合构建。

## 待确认
- 是否会在后续演进中引入数据库、消息或其他外部基础设施；当前仓库中尚未体现这类稳定约束。
