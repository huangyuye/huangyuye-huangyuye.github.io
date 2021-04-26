---
categories:
  - Java
  - 知识库
  - Java
---
## spring 技术体系演变

1. spring
2. springboot
3. spring cloud netflix
4. spring cloud alibaba （微服务开发的一站式解决方案）



## nacos 

- 配置管理

- 服务管理

服务注册中心 + 配置中心（涉及CAP理论）



## feign

feign = ribbon + hystricx

feign 对微服务的调用实际上也是类似使用 restTemplate 进行http 调用，只是进行了封装。



## gateway & feign

gateway(API路由管理方式)： 提供统一的路由方式，基于filter链的方式提供网关基本功能（如安全、监控/指标，和限流）



## sentinal 

以流量为切入点，从流量控制、熔断降级、系统负载保护等多维度保护服务的稳定性

sentinal的限流配置可以与nacos配合使用（除此之外，sentinal还有很多配置的持久化方式）



## fescar

即seata，分布式事务解决方案



## skywalking

服务链路追踪

常见的**APM**系统对比：zipkin vs pinpoint vs skywalking （Application Performance Management应用性能管理）



## gateway、sentinal、feign的关系和配合？？

