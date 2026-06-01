---
name: project-architecture
description: High-level runtime stack, module split, and DDD layer mapping that AI should load when changing module boundaries or architectural structure.
---

# Project Architecture

## Overview
`ddd-demo-one` 是一个基于 Spring Boot 的 Maven 多模块 DDD 示例项目，围绕 `catalog`、`customer`、`order` 三个业务领域组织代码。

## Framework And Runtime
- Java: `17`
- Framework: `Spring Boot 3.3.5`
- Build system: `Maven` 聚合工程
- Packaging: 根模块 `pom` 聚合，运行入口位于 `demo-start`

## Module And Layering
- `demo-domain`
  当前领域层，包含 `catalog`、`customer`、`order` 的领域模型、仓储接口，以及 `shared/domain` 中的共享领域异常。
- `demo-app`
  当前应用层，包含各领域的 `ApplicationService`、命令对象与 DTO，也包含跨接口复用的 `TraceIdUtil`。
- `demo-infrastructure`
  当前基础设施层，提供仓储接口的内存实现与 `DemoDataInitializer` 启动初始化数据。
- `demo-adapter`
  当前接口适配层，提供 REST Controller、请求对象、Jakarta Validation 校验和全局异常处理。
- `demo-start`
  当前启动层，提供 Spring Boot 主类并装配可运行应用。

## DDD Layer Mapping
- Domain: `demo-domain/src/main/java/com/wayne/ddddemo/*/domain`
- Application: `demo-app/src/main/java/com/wayne/ddddemo/*/application`
- Infrastructure: `demo-infrastructure/src/main/java/com/wayne/ddddemo/*/infrastructure`
- Interfaces/Adapters: `demo-adapter/src/main/java/com/wayne/ddddemo/*/interfaces`
- Bootstrap: `demo-start/src/main/java/com/wayne/ddddemo`

## Git Submodules
- 当前仓库未发现 `.gitmodules`，没有 Git 子模块依赖。

## Notes
- 当前架构是教学/演示性质的轻量实现，基础设施层默认使用内存仓储而非持久化数据库。
- 领域边界主要按顶层业务包 `catalog`、`customer`、`order` 划分，而不是按技术类型横向集中。
