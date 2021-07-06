















## 

## Nacos 的关键特性



### 服务发现和服务健康监测

支持基于 DNS 和基于 RPC 的服务发现

### 动态配置服务

### 动态 DNS 服务

### 服务及其元数据管理

### 



## Nacos配置文件



### 配置文件加载配置

以下是我司某应用的启动类配置，配置了N个配置文件的加载

注意 `shared-configs[i]` ： i 从0开始连续配置，前后配置相同的属性key会进行覆盖

```java
Properties props = System.getProperties();
# 基础启动类：ecrp项目通用配置
props.setProperty("spring.cloud.nacos.config.shared-configs[0].data-id", NacosConstant.getNacosConfigShareName("ecrp-sca-common", profile, "yml"));
# 扩展启动类：导购项目通用配置
props.setProperty("spring.cloud.nacos.config.shared-configs[1].data-id", "ecrp-sca-sg-application-" + profile + ".yml");
props.setProperty("spring.cloud.nacos.config.shared-configs[2].data-id", "ecrp-sca-sg-common-" + profile + ".yml");
```



### 关于spring配置文件加载

[Spring Boot 外部化配置实战解析](https://zhuanlan.zhihu.com/p/48030077)

MutablePropertySources 用于将多个配置集合统一加载，包括：

- 系统属性、系统环境变量、命令行变量、应用自定义变量等

```java
MutablePropertySources propertySources = environment.getPropertySources();
propertySources.addFirst(new SimpleCommandLinePropertySource(args));
propertySources.addLast(new MapPropertySource("systemProperties", environment.getSystemProperties()));
propertySources.addLast(new SystemEnvironmentPropertySource("systemEnvironment", environment.getSystemEnvironment()));
Properties props = System.getProperties();
props.setProperty("spring.application.name", appName);
// todo ...
```



### 关于 bootstrap.yml

[application.yml与bootstrap.yml的区别](https://www.jianshu.com/p/c955c44ae534)



`bootstrap.yml` 用来程序引导时执行，应用于更加早期配置信息读取。可以理解成系统级别的一些参数配置，这些参数一般是不会变动的。一旦bootStrap.yml 被加载，则内容不会被覆盖。

Bootstrap 属性有高优先级，默认情况下，它们不会被本地配置覆盖。



### Nacos如何验证配置文件正常读取

- NascentApplication#createSpringApplicationBuilder
  - ServiceLoader.load(LauncherService.class).forEach(launcherList::add); // 我司封装的配置文件扩展类
  - spring.profiles.active = ${profile} // 确认环境配置正确
  - spring.cloud.nacos.discovery.server-addr = ${nacosAddress} // 确认使用正确的nacos地址



### Nacos 配置本地快照

`windows系统：C:\用户\{用户名}\nacos\config`



### Nacos 配置文件分层

**NameSpace > Group > DataId**

- Namespace：代表不同的环境，如：开发、测试， 生产等；
  - spring.cloud.nacos.config.namespace=b3404bc0-d7dc-4855-b519-570ed34b62d7
    - 指定命名空间，默认使用public命名空间
- Group：代表某个项目，如：XX物流项目，XX教育项目；
  - spring.cloud.nacos.config. group: DEFAULT_GROUP 
    - 分 组，默认为 DEFAULT_GROUP 
- DataId：每个项目下往往有若干个应用，每个配置集(DataId)是一个应用的主配置文件



### Nacos配置自动更新

通过 Spring Cloud 原生注解 `@RefreshScope` 实现配置自动更新。

#### todo 。。。（该注解与扩展配置refresh属性配置区别？）

《@RefreshScope那些事》：https://www.jianshu.com/p/188013dd3d02



### Nacos公共配置/扩展配置

多应用共享配置，即多个DataId之间的配置共享

#### 扩展配置

- 通过配置 `spring.cloud.nacos.config.ext-config[n].data-id` 来支持多个配置集。
- 通过配置 `spring.cloud.nacos.config.ext-config[n].group` 来定制配置组。如果未指定，则使用默认组。
- 通过配置 `spring.cloud.nacos.config.ext-config[n].refresh` 来控制该配置集是否支持配置的动态刷新。默认情况下不支持。

#### 共享配置

通过共享配置集的方式也可以实现公共配置的功能，唯一的区别就是共享配置集无法设置组信息，只获取 DEFAULT_GROUP

```yaml
  # 共享配置集
  spring.cloud.nacos.config
    shared-dataids: ext-config-common01.yaml,ext-config-common02.yaml,ext-config-common03.yaml # 多个配置集逗号隔开
    refreshable-dataids: ext-config-common01.yaml # 指定哪个配置集支持动态刷新
```

#### 配置优先级

Spring Cloud Alibaba Nacos Config 提供了三种从 Nacos 拉取配置的功能：

- A：通过内部相关规则（应用名、配置内容的数据格式等）自动生成相关的 Data Id 配置；
  - `${prefix}-${spring.profile.active}.${file-extension}`
  - `prefix` 默认为`spring.application.name` 的值，也可以通过配置项 `spring.cloud.nacos.config.prefix`来配置。
- B：通过配置 `spring.cloud.nacos.config.ext-config[n].data-id` 来支持多个配置集。同时配置多个配置集时，优先级关系根据 `n` 的值决定，值越大，优先级越高；
- C：通过配置 `spring.cloud.nacos.config.shared-dataids` 配置多个共享配置集；

　

​	**当三种方式同时使用时，优先级关系为：A > B > C。**



## 其他

### Spring SPI 机制

**场景：**

服务提供方定义统一标准的接口服务，但不进行实现。在某个业务流程中应用该接口服务，可以理解为在确定的业务流程中加入了hook由服务消费端定制化接口服务的实现。具体步骤如下

- 定义接口，并在某流程中应用

- 配置SPI文件

  - SPI文件路径

    ```shell
    resources
    | -- META-INF
    | -- -- services 
    | -- -- -- ${interface.name} # 接口的全路径名称
    ```

  - SPI文件内容

    - `${interface.name}` 文件内容为接口实现类的全路径

- 代码中使用

```java
ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
```



参考链接：[《高级开发必须理解的Java中SPI机制》](https://www.jianshu.com/p/46b42f7f593c)



### Nacos 实现应用版本控制

[《Nacos：实现版本控制调度，灰度版本隔离》](https://blog.csdn.net/suxingrui/article/details/103791284)

#### todo 。。。



### ECRP 开放平台 Feign 配置

相关配置类：

```java
OpenApiFeignClientAutoConfiguration
OpenApiSpringEncoder
```



### 智慧导购-微服务记录



#### nacos读取问题

- `OpenApiSpringEncoder`：Assert.notNull(this.appKey, "ecrp.open.api.feign.appKey must be not null");
  - `ecrp.open.api.feign.appKey`（配置时使用"-"分割单词或驼峰标识均可）
  - 启动时需要compile项目才能保证正常扫描父模块 `ecrp-sca-sg-starter` 的 `LauncherServiceImpl`

#### feign 问题

- com.netflix.client.ClientException: Load balancer does not have available server for client: ecrp-sca-open-customer 



## 参考链接

[《Spring Cloud Alibaba 参考文档》](https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/zh-cn/index.html#_%E4%BB%8B%E7%BB%8D)

[《nacos官网》](https://nacos.io/zh-cn/docs/what-is-nacos.html)

