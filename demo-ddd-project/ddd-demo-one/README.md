# ddd-demo-one

一个简洁的 Spring Boot DDD 多模块示例项目，包含 3 个领域：

- `catalog`：商品目录领域
- `customer`：客户领域
- `order`：订单领域

项目采用按层拆分的 Maven 多模块结构：

- `demo-domain`：领域层，聚合、实体、仓储接口、领域规则
- `demo-app`：应用层，应用服务、命令对象、DTO
- `demo-infrastructure`：基础设施层，仓储实现、初始化数据
- `demo-adapter`：接口适配层，REST 控制器、请求对象、异常处理
- `demo-start`：启动层，Spring Boot 启动类与运行配置

## 运行

```bash
mvn -pl demo-start -am spring-boot:run
```

## 示例接口

### 查看商品

```bash
curl http://localhost:8080/api/catalog/products
```

### 查看客户

```bash
curl http://localhost:8080/api/customers
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

### 支付与发货

```bash
curl -X POST http://localhost:8080/api/orders/O-1000/pay
curl -X POST http://localhost:8080/api/orders/O-1000/deliver
```
