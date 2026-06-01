# Specification Index

This file routes later SDD tasks to the minimum relevant Specification files.
Read this file first, then load only the Specification files whose manifests match the current task.

## Loading Policy

- `L0`: always-load Specification files
- `L1`: task-type Specification files
- `L2`: domain Specification files
- `L3`: optional or operational Specification files

## Entries

### `constitution/constitution.md`
```json
{
  "path": "constitution/constitution.md",
  "name": "project-constitution",
  "description": "Repository-wide engineering rules and stable layering constraints that AI should load before planning, implementing, refactoring, or reviewing work in this project.",
  "always_load": true,
  "domains": [],
  "triggers": [
    "project-wide change",
    "engineering rules",
    "layering constraints",
    "module boundary"
  ],
  "priority": "L0"
}
```

### `architecture/DDD.md`
```json
{
  "path": "architecture/DDD.md",
  "name": "ddd-implementation",
  "description": "Current DDD constraints, layer responsibilities, and domain-model structure that AI should load when working on aggregates, repositories, or bounded contexts.",
  "always_load": false,
  "domains": [],
  "triggers": [
    "ddd",
    "aggregate",
    "repository",
    "bounded context",
    "domain model",
    "application service"
  ],
  "priority": "L1"
}
```

### `architecture/architecture.md`
```json
{
  "path": "architecture/architecture.md",
  "name": "project-architecture",
  "description": "High-level runtime stack, module split, and DDD layer mapping that AI should load when changing module boundaries or architectural structure.",
  "always_load": false,
  "domains": [],
  "triggers": [
    "architecture change",
    "multi-module structure",
    "layering",
    "spring boot",
    "cross-module change"
  ],
  "priority": "L1"
}
```

### `tool/tool.md`
```json
{
  "path": "tool/tool.md",
  "name": "project-tools",
  "description": "Reusable helpers, exception patterns, and preferred framework utilities that AI should load when selecting shared technical solutions in this project.",
  "always_load": false,
  "domains": [],
  "triggers": [
    "DomainException",
    "TraceIdUtil",
    "in-memory repository",
    "validation",
    "exception mapping"
  ],
  "priority": "L1"
}
```

### `domain/catalog.md`
```json
{
  "path": "domain/catalog.md",
  "name": "domain-catalog",
  "description": "Catalog domain model and constraints for product management; load this file when changing product business logic or catalog-facing APIs.",
  "always_load": false,
  "domains": [
    "catalog",
    "product"
  ],
  "triggers": [
    "catalog",
    "product",
    "pricing",
    "product activation",
    "catalog api"
  ],
  "priority": "L2"
}
```

### `domain/customer.md`
```json
{
  "path": "domain/customer.md",
  "name": "domain-customer",
  "description": "Customer domain model and credit-related constraints; load this file when changing customer rules, customer APIs, or order eligibility checks.",
  "always_load": false,
  "domains": [
    "customer"
  ],
  "triggers": [
    "customer",
    "credit limit",
    "customer level",
    "customer api",
    "order eligibility"
  ],
  "priority": "L2"
}
```

### `domain/order.md`
```json
{
  "path": "domain/order.md",
  "name": "domain-order",
  "description": "Order domain workflow, model, and cross-domain constraints; load this file when changing order creation, status transitions, or order-related APIs.",
  "always_load": false,
  "domains": [
    "order"
  ],
  "triggers": [
    "order",
    "order creation",
    "order status",
    "payment",
    "shipment"
  ],
  "priority": "L2"
}
```
