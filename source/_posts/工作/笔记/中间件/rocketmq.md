### 概念

- Topic：消息主题，存储在服务端
- 消息属性：生产者可以为消息定义的属性，包含Message Key和Tag
- Group：一类生产者或消费者，这类生产者或消费者通常生产或消费同一类消息，且消息发布或订阅的逻辑一致。（不同group的消费者互不干扰，相同group的消费者在集群消费模式下分摊消息）
- Producer和Consumer对象为线程安全的，可以在多线程间进行共享，避免每个线程创建一个实例。
- 消息队列RocketMQ版包含Broker和Name Server等节点，其中Broker节点负责将Topic的路由信息上报至Name Server节点
- Group ID不可跨协议使用。（HTTP/TCP）

#### 消息类型

普通、顺序、定时和延时、事务

#### 发送方式

同步可靠、异步可靠、Oneway方式

#### 消费方式

集群、广播

#### 消息获取方式

消息队列RocketMQ版支持以下两种消息获取方式：

- Push：消息由消息队列RocketMQ版推送至Consumer。Push方式下，消息队列RocketMQ版还支持批量消费功能，可以将批量消息统一推送至Consumer进行消费。更多信息，请参见批量消费。
- Pull：消息由Consumer主动从消息队列RocketMQ版拉取。

#### 消息特性

消息重试、至少投递一次

#### 消息重复场景

生产者发送、投递到消费者、负载均衡时消息重复（包括但不限于网络抖动、Broker重启以及消费者应用重启）

消息幂等处理：因为不同的Message ID对应的消息内容可能相同，有可能出现冲突（重复）的情况，所以真正安全的幂等处理，不建议以Message ID作为处理依据。最好的方式是以业务唯一标识作为幂等处理的关键依据，而业务的唯一标识可以通过消息Key设置。

#### 消息堆积和延迟问题

SDK客户端使用Push模式消费消息时，分为以下两个阶段

- 获取消息，SDK客户端通过长轮询批量拉取的方式从消息队列RocketMQ版服务端获取消息，将拉取到的消息缓存到本地缓冲队列中。
- 提交消费线程，SDK客户端将本地缓存的消息提交到消费线程中，使用业务消费逻辑进行处理。

消息堆积的主要瓶颈在于本地客户端的消费能力，即消费耗时和消费并发度。

##### 如何避免消息堆积和延迟？

- 梳理消息的消费耗时
- 设置消息的消费并发度

#### 消息负载均衡策略

发布方消息负载均衡策略&订阅方消息负载均衡策略

- 一个topic中默认8个逻辑Queue，consumer订阅消费分摊多个queue



### 场景

1. 分布式缓存同步（构建分布式缓存）：使用消息队列RocketMQ版的广播消费模式，那么这条消息会被所有节点消费一次，相当于把价格信息同步到需要的每台机器上，取代缓存的作用。
2. 顺序收发：
   1. 分区消息：根据Sharding Key来进行分区，实现顺序消费
3. 削峰填谷、异步解耦、分布式事务一致性、大数据分析



### 问题

1. 如何保证生产者和消费者事务一致性？
   - 使用事务消息只能保证消息发送跟发送方本地事务一致
2. 与Name Server集群中的其中一个节点（随机）建立长连接（Keep-alive），长连接如何实现的？
3. 阿里rocketmq服务，http协议与tcp协议区别
   - http协议支持公网，tcp不支持
   - http协议的支持很少，且不支持spring/springboot集成

### 相关文章

- [使用 rocketmq-spring-boot-starter 来配置、发送和消费 RocketMQ 消息](https://developer.aliyun.com/article/783774)
- [rocketMq - 并发消费过程](https://www.jianshu.com/p/fdd7a8ab90fb)
- [RocketMQ第七章：手把手教老婆实现-广播消息生产者和消费者](https://juejin.cn/post/6955444151085334559)
- [02 RocketMQ 核心概念扫盲篇](http://learn.lianglianglee.com/%E4%B8%93%E6%A0%8F/RocketMQ%20%E5%AE%9E%E6%88%98%E4%B8%8E%E8%BF%9B%E9%98%B6%EF%BC%88%E5%AE%8C%EF%BC%89/02%20RocketMQ%20%E6%A0%B8%E5%BF%83%E6%A6%82%E5%BF%B5%E6%89%AB%E7%9B%B2%E7%AF%87.md)
- [RocketMQ——消息ACK机制及消费进度管理](https://jaskey.github.io/blog/2017/01/25/rocketmq-consume-offset-management/)
- [消息中间件— RocketMQ消费者简介(集群、广播消费，推模式，拉模式)](https://www.codenong.com/cs106180416/)
- [阿里云Rocket MQ Java Http SDK发送消费消息示例Demo](https://developer.aliyun.com/article/772981)
- [RocketMQ——广播消费模式与集群消费模式](https://blog.csdn.net/gwd1154978352/article/details/80802674)
- [消息队列（四）阿里RocketMQ](https://juejin.cn/post/6844903854102822920)

