# ddd-demo-two

一个简洁的 Spring Boot DDD 多模块示例项目，包含 6 个领域：

- `catalog`：商品目录
- `customer`：客户
- `inventory`：库存
- `order`：订单
- `payment`：支付
- `shipping`：发货

项目采用按层拆分的 Maven 多模块结构：

- `demo-domain`：领域层，聚合、实体、仓储接口、领域规则
- `demo-app`：应用层，应用服务、命令对象、DTO
- `demo-infrastructure`：基础设施层，内存仓储实现、初始化数据
- `demo-adapter`：接口适配层，REST 控制器、请求对象、异常处理
- `demo-start`：启动层，Spring Boot 启动类与运行配置

这个 demo 用一个简单的电商履约流程串起 6 个领域：

1. 目录领域维护商品
2. 客户领域维护客户
3. 库存领域保存可售数量
4. 订单领域创建订单并扣减库存
5. 支付领域为订单记账并推动订单变为已支付
6. 发货领域创建运单并推动订单变为已发货

## 运行

```bash
mvn -pl demo-start -am spring-boot:run
```

## 示例接口

### 查看商品

```bash
curl http://localhost:8080/api/catalog/products
```

### 查看库存

```bash
curl http://localhost:8080/api/inventory/items
```

### 创建订单

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "O-1000",
    "customerId": "C-100",
    "items": [
      { "productId": "P-100", "quantity": 2 },
      { "productId": "P-300", "quantity": 1 }
    ]
  }'
```

### 记录支付

```bash
curl -X POST http://localhost:8080/api/payments \
  -H "Content-Type: application/json" \
  -d '{
    "paymentId": "PAY-1000",
    "orderId": "O-1000",
    "amount": 125.90,
    "method": "CARD"
  }'
```

### 创建发货

```bash
curl -X POST http://localhost:8080/api/shipments \
  -H "Content-Type: application/json" \
  -d '{
    "shipmentId": "S-1000",
    "orderId": "O-1000",
    "address": "Shanghai Pudong Demo Road 88",
    "carrier": "DHL",
    "trackingNumber": "TRACK-1000"
  }'
```
