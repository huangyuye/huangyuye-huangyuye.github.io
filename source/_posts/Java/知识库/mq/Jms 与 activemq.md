## 背景



### JMS提出背景

不同**系统之间**进行**信息交换**是开发中常见的场景，那如何进行信息交换呢？

SUN公司提出了一种面向消息的中间件服务--即**JMS规范**（标准）



`常用的几种信息交互技术**(httpClient、hessian、dubbo、jms、webservice 五种).**`

`消息中间件 MOM：message-oriented middleware`



### JMS概述

**JMS** 即 **Java 消息服务**（Java Message Service得简称），是Java EE 的标准/规范之一。

JMS只是Java EE中定义的是两个应用程序之间进行异步通信的API，它为标准消息协议和消息服务提供了一组通用接口。它自身并不是一个消息服务系统，而是消息传送服务的一个**抽象**

- 该规范（标准）指出：消息的发送应该是**异步**的、非阻塞的。
- 作用：消除系统瓶颈，去耦合，提高系统的整体可伸缩性和灵活性
  - 异步：主次业务拆分，不需要同步阻塞
    - 强弱依赖梳理能将非关键调用链路的操作异步化并提升整体系统的吞吐能力
  - 解耦：上游不需要根据下游变动而调整
    - 新模块进来可以做到代码改动量最小
  - 削峰：同一时刻的流量得到缓冲
    - 设置流量缓冲池，可以让后端系统按照自身吞吐能力进行消费

```
JavaEE 有13种核心技术规范，JMS是其中之一
```



### JMS副作用

- 中间加了个消息队列，如果消息队列挂了怎么办呢？是不是 降低了系统的【可用性】 ？
- 要保证HA(高可用)？是不是要搞集群？那么我 整个系统的【复杂度】是不是上升了 ？
- 万一我发送方发送失败了，然后执行重试，这样就可能产生重复的消息。或者我消费端处理失败了，请求重发，这样也会产生重复的消息。【重复消息】
- 如何解决消息的 顺序消费 问题 呢？【消费顺序】
- 如何解决 分布式事务 问题呢
- 如何解决 消息堆积 的问题呢？



### ActiveMQ概述

ActiveMQ是JMS标准/规范的具体实现，采用Java语言开发。（Apache开源消息服务器）

ActiveMQ与JMS的关系：

- JMS定义了一组有关**消息传送**的规范和标准，Apache ActiveMQ是JMS规范的具体实现
- JMS只是定义了一组接口，如同JDBC抽象了关系数据库访问、JPA抽象了对象与关系数据库映射、JNDI抽象了命名目录服务访问



### JMS 消息队列的工作模式

- 点对点（point to point）
- 发布/订阅（publish/subscribe，topic）



#### p2p

p2p工作模式下的特点：

- 每个消息只能有一个消费者

- 消息的生产者和消费者之间没有时间上的相关性

- 消息被消费后队列中不会再存储



#### pub/sub

pub/sub工作模式下的特点：

- 每个消息可以有多个消费者
- 生产者和消费者有时间上的相关性
  - 要先订阅，后发布
  - JMS规范允许客户创建持久订阅，该方式允许消费者消费它在未处于**激活状态**时发送的消息



### JMS 组成元素

- provider / broker
  - 实现JMS接口和规范的消息中间件，即MQ服务器
- producer
- consumer
- message
  - 消息头
  - 消息属性
  - 消息体



## ActiveMQ



### 环境搭建

- 运行环境支持：Java环境变量配置
- 配置文件指定：`./activemq start xbean:file:/${absolutePath}/${activemq-conf}.xml`
- 默认端口：管理端口-8161，消息服务broker连接端口-61616
- 用户管理，用户组管理
- 消息持久化
- 传输协议



#### 简单搭建

#### 集群搭建



### API对象说明（编程模板）

以下的 JMS 对象以apache activemq实现进行举例说明



#### ConnectionFactory

通过创建ConnectionFactory建立到ActveMQ(消息服务器)的连接

构造方法：`ActiveMQConnectionFactory(String userName, String password, String brokerURL)`



#### Connection

ConnectionFactory负责返回可以与底层消息传递系统进行通信的Connection实现。

当一个Connection被创建时，它的传输默认是关闭的，必须使用start方法开启。

一个Connection可以建立一个或多个的Session。

当一个程序执行完成后，必须关闭之前创建的Connection，否则ActiveMQ不能释放资源，关闭一个Connection同样也关闭了Session，MessageProducer和MessageConsumer。



#### Session

从Connection中创建一个或者多个Session。

```java
// transacted 为使用事务标识， acknowledgeMode 为签收模式(常用为自动签收和手动签收)
Session createSession(boolean transacted, int acknowledgeMode)
```

Session是一个发送或接收消息的线程，可以使用Session创建MessageProducer，MessageConsumer和Message。

Session可以被事务化，也可以不被事务化。



#### Destination

Destination是一个客户端用来指定生产消息目标和消费消息来源的对象。

在PTP模式中，Destination被称作Queue即队列；在Pub/Sub模式，Destination被称作Topic即主题。



#### Message

Message 由以下几部分组成：消息头，属性和消息体



#### MessageProducer

是一个由Session创建的对象，用来向Destination发送消息。

```java
// deliveryMode：是否持久化,取值范围-接口javax.jms.DeliveryMode{1,2}
// priority：消息的优先级（0-9 默认是 4）
// timeToLive：消息的存活时间
void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive);
```



#### MessageConsumer

是一个由Session创建的对象，用来从Destination接收消息。

```java
// destination：第一个方法适合消费主题消息和队列消息
// messageSelector 为消息选择器
// noLocal 标志默认为 false，当设置为 true 时限制消费者只能接收和自己相同的连接(Connection)所发布的消息，此标志只适用于主题，不适用于队列；
MessageConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal);
// name 标识订阅主题所对应的订阅名称，持久订阅时需要设置此参数。
TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector, boolean noLocal);
```



**消息的同步/异步接收**

```
// 客户端同步接收：receive()/receive(long timeout)receiveNoWait()
// 客户端异步接收：注册一个实现 MessageListener 接口的对象到 MessageConsumer
```



### 开发示例

#### 生产者-消息生产

```java
    /**
     * 通过 JNDI 构建连接工厂 || 用JNDI 得到目标队列或主题对象，即Destination 对象
     *
     * @return
     */
    public static ConnectionFactory buildFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://127.0.0.1:61616"); // MQ broker服务器地址
        connectionFactory.setUserName("admin"); // MQ账号
        connectionFactory.setPassword("admin"); // MQ密码
        return connectionFactory;
    }
	
    /**
     * 发送
     */
    public static void send() throws JMSException {
        // 1.通过 JNDI 构建连接工厂 || 用JNDI 得到目标队列或主题对象，即Destination 对象
        ConnectionFactory factory = buildFactory();
        // 2.创建连接
        Connection connection = factory.createConnection();
        // 3.打开连接
        connection.start();
        // 4.创建session param1=事务是否开启 param2=消息确认机制  如果开启事务，第二个参数无用，且需要一个提交事务的操作 
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 通过 JNDI 创建 destination
        // 5.创建消息队列
        Destination topic = session.createTopic("topic.shop");
        // 6.创建生产者
        MessageProducer producer1 = session.createProducer(topic);
        // 7.创建消息
        TextMessage topicMsg = session.createTextMessage("topic");
        topicMsg.setStringProperty("companyId", "10000146");
        // 8.发送消息到消息队列
        producer1.send(topicMsg);
        // 9.关闭连接
        session.close();
        connection.close();
    }
```



#### 消费者-消费消息

```java
    /**
     * 消息接收
     */
    public static void receive() throws JMSException {
        // 1.通过 JNDI 构建连接工厂 || 用JNDI 得到目标队列或主题对象，即Destination 对象
        ConnectionFactory factory = new ActiveMQConnectionFactory();
        // 2.创建连接
        Connection connection = factory.createConnection();
        // 3. 设置客户端ID
        connection.setClientID("ecrp-sg");
        // 3.打开连接
        connection.start();
        // 4.创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 通过 JNDI 创建 destination
        // 5.创建目标地址(通道)
        Topic topic = session.createTopic("topic.shop");
        // 6.创建消费者
        MessageConsumer consumer = session.createConsumer(topic);
        // 7.阻塞接收消息 或 建立消息监听
        // Message message = consumer.receive();
        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收的消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("类型错误！");
            }
        });
    }
```



#### 消息选择器

1. 生产者生产消息

   ```java
   ... 创建连接、session、目的地、创建生产者
   // 设置消息属性
   message.setStringProperty("companyId", "10000146");
   ... 消息发往目的地、关闭流
   ```

2. 消费者消费消息

   ```java
   ... 创建连接、session、目的地
   // 创建消费者时设置消息过滤器
   session.createConsumer(topic,"companyId in ('10000146')");
   ... 消息发往目的地、关闭流
   ```



**关于消息选择器的介绍**

```markdown
以下内容摘自：《MQ 系列之 ActiveMQ 基本使用》https://cloud.tencent.com/developer/article/1707858
   JMS 提供了一种机制，使用它，消息服务可根据消息选择器中的标准来执行消息过滤。生产者可在消息中放入应用程序特有的属性，而消费者可使用基于这些属性的选择标准来表明对消息是否感兴趣。这就简化了客户端的工作，并避免了向不需要这些消息的消费者传送消息的开销。然而，它也使得处理选择标准的消息服务增加了一些额外开销。
   消息选择器是用于 MessageConsumer 的过滤器，可以用来过滤传入消息的属性和消息头部分(但不过滤消息体)，并确定是否将实际消费该消息。按照 JMS 文档的说法，消息选择器是一些字符串，它们基于某种语法，而这种语法是 SQL-92 的子集。可以将消息选择器作为 MessageConsumer 创建的一部分。例如：public final String SELECTOR = "JMSType = 'TOPIC_PUBLISHER'";该选择器检查了传入消息的 JMSType 属性，并确定了这个属性的值是否等于 TOPIC_PUBLISHER。如果相等，则消息被消费；如果不相等，那么消息会被忽略。
```



#### 消息优先级

#### 事务控制

创建session时，开启事务，则”签收模式“参数无效；消息的消费需要一个提交事务的操作



#### 同步/异步



#### 安全控制插件

用户验证、授权(角色)（仅读权限）



#### 并发容器



#### 消息持久化与持久化方式

#### 异步发送消息并接收回调

useAsynSend



#### 消息回复

#### 连接-缓存与池

#### 外部事务管理器

#### 消息转换器



## 关于连接工厂

### 缓存与池

- org.apache.activemq.ActiveMQConnectionFactory

  - 通过连接工厂池，可以将Connection，Session等都放在池里面，用的时候直接返回池里面的内容，无需临时建立连接，节约开销

  ```java
  <!-- 真正可以产生Connection的ConnectionFactory，由对应的 JMS服务厂商提供 -->
  	<bean id="targetConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
  		<property name="brokerURL" value="tcp://localhost:61616" />
  	</bean>
   
  	<!-- 通过往PooledConnectionFactory注入一个ActiveMQConnectionFactory可以用来将Connection，Session和MessageProducer池化这样可以大大减少我们的资源消耗， -->
  	<bean id="pooledConnectionFactory" class="org.apache.activemq.pool.PooledConnectionFactory">
  		<property name="connectionFactory" ref="targetConnectionFactory" />
  		<property name="maxConnections" value="10" />
  	</bean>
   
  	<bean id="connectionFactory"
  		class="org.springframework.jms.connection.SingleConnectionFactory">
  		<property name="targetConnectionFactory" ref="pooledConnectionFactory" />
  	</bean>
  ```

  

- org.springframework.xxx.CachingConnectionFactory (继承了SingleConnectionFactory)

  - SingleConnectionFactory保证每次返回的都是同一个连接
  - CachingConnectionFactory继承了SingleConnectionFactory在保证同一连接的同时，增加了缓存的功能，可以缓存Session以及生产者，消费者。

  ```java
  <!-- 真正可以产生Connection的ConnectionFactory，由对应的 JMS服务厂商提供 -->
  	<bean id="targetConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
  		<property name="brokerURL" value="tcp://localhost:61616" />
  	</bean>
  <bean id="connectionFactory"
  		class="org.springframework.jms.connection.SingleConnectionFactory">
  		<property name="targetConnectionFactory" ref="targetConnectionFactory" />
  	</bean>
  ```

- 配置 Spring JmsTemplate

  ```java
  	<!-- Spring提供的JMS工具类，它可以进行消息发送、接收等 -->
  	<bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
  		<!-- 这个connectionFactory对应的是我们定义的Spring提供的那个ConnectionFactory对象 -->
  		<property name="connectionFactory" ref="connectionFactory" />
  	</bean>
  ```

  

**todo ...**



## Spring 框架集成JMS

在Spring框架中使用JMS传递消息有两种方式：

**JMS template** 和 **message listener container**，前者用于同步收发消息，后者用于异步收发消息。



### 开发示例

#### JMS template

1. 注册连接工厂实例 ActiveMQConnectionFactory(brokerUrl, username, password)

   ```xml
   <bean class="org.apache.activemq.ActiveMQConnectionFactory">
       <!--消息服务连接信息 -->
       <property name="brokerURL">
           <value>tcp://127.0.0.1:61616</value>
       </property>
       <property name="userName">
           <value>admin</value>
       </property>
       <property name="password">
           <value>admin</value>
       </property>
   </bean>
   ```

2. 注册目的地实例 （ActiveMQQueue / ActiveMQTopic）

   ```xml
   <!-- queue目的地配置 -->
   <bean id="destination" class="org.apache.activemq.command.ActiveMQQueue">
       <constructor-arg index="0" value="spring-queue" />
   </bean>
   ```

3. 注册 JmsTemplate(Spring) 实例

   ```xml
   <!-- spring 使用jmsTemplate来实现消息的发送和接受 -->
   <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
       <property name="connectionFactory" ref="jmsFactory"></property>
       <!--目的地的设置，使用spring来使用activemq时，使用queue还是topic很方便，在这里引用不同地址就ok了 -->
       <property name="defaultDestination" ref="destination"></property>
       <!--转换器，我们自己可以继承重写这个类的方法 ，这里使用spring提供的默认方法 -->
       <property name="messageConverter">
           <bean
                 class="org.springframework.jms.support.converter.SimpleMessageConverter" />
       </property>
   </bean>
   ```

4. 消息生产者

   ```java
   // 获取JmsTemplate对象
   JmsTemplate jt = (JmsTemplate) ctx.getBean("jmsTemplate");
   // 调用方法，发送消息
   jt.send(new MessageCreator() {
       // 消息的产生，返回消息发送消息
       public Message createMessage(Session s) throws JMSException {
           TextMessage msg = s
               .createTextMessage("Spring send msg ----> Hello activeMQ5");
           return msg;
       }
   });
   ```

5. 消息消费者

   ```java
   JmsTemplate jt = (JmsTemplate) ctx.getBean("jmsTemplate");
   //接收消息
   String msg = (String) jt.receiveAndConvert();
   ```



#### message listener container

由于需要模拟消息生产，所以同样需要执行上一小节《**JMS template**》内的所有配置步骤（除消息消费者）

额外需要配置的内容为

1. 自定消息监听器 ? implements MessageListener

   ```java
   public class MyMessageListener implements MessageListener {
   	public void onMessage(Message arg0) {
   		try {
   			String message = ((TextMessage) arg0).getText();
   		} catch (JMSException e) {
   			// TODO Auto-generated catch block
   		}
   	}
   }
   ```

2. 注册消息监听器实例

   ```java
   <bean id="myMessageListener" class="xxx.MyMessageListener">
   ```

3. 注册消息监听器容器实例

   ```java
   <bean id="jmsContainer" class="org.springframework.jms.listener.DefaultMessageListenerContainer">
   	<property name="connectionFactory" ref="jmsFactory" />
   	<property name="destination" ref="destination" />
   	<property name="messageListener" ref="myMessageListener" />
   </bean>
   ```



#### 两种 message listener container

- DefaultMessageListenerContainer
  - 允许期间动态调整监听线程的数量
  - 允许和XA Transactions的集成

- SimpleMessageListenerContainer

相同点：都允许指定数量的并发监听线程

建议：对于使用本地事务管理器和不需要基于可变负载的线程、会话、连接调整的简单消息传递应用，使用SimpleMessageListenerContainer。



### 异步消息监听器的三种方式配置

- 实现javax.jms.MessageListener接口（**JMS规范中定义的一个接口**）

- 实现Spring的SessionAwareMessageListener（Spring**提供**）

  - 不是标准的JMS MessageListener
  - SessionAwareMessageListener的设计就是为了方便我们在接收到消息后发送一个回复的消息
  - 提供了一个处理接收到的消息的onMessage方法，可以同时接收两个参数，一个是表示当前接收到的消息Message，另一个就是可以用来发送消息的Session对象

- 捆绑一个标准POJO到Spring的MessageListenerAdapter类上

  - 实现了MessageListener接口和SessionAwareMessageListener接口
  - 默认消息处理方法为 `handleMessage`，可以通过属性 `defaultListenerMethod` 修改
  - 主要作用是：1.将接收到的消息进行类型转换，然后通过反射的形式把它交给一个普通的Java类进行处理。2.自定义反射类。3.自动回复发送者消息

  ```xml
  <-- 新建自定义监听器java类：有两个方法handleMessage和receiveMessage，其代码如下（参数可以是五种消息类型，根据作用1可知这是自动根据反射转换的 -->
  
  <!--以下是使用MessageListenerAdapter监听器相关==============================================================================-->
  
      <!-- 消息监听适配器 第一种方法通过构造方法参数设置 -->
      <!--<bean id="messageListenerAdapter" class="org.springframework.jms.listener.adapter.MessageListenerAdapter">
          <constructor-arg>
              <bean class="com.easylab.jms.consumer.myListenner"/>
          </constructor-arg>
      </bean>-->
  
      <!-- 消息监听适配器 第二种方法通过delegate属性设置 -->
      <bean id="messageListenerAdapter" class="org.springframework.jms.listener.adapter.MessageListenerAdapter">
          <property name="delegate">
              <bean class="com.easylab.jms.consumer.myListenner"/>
          </property>
          <!--默认接受到消息后调用哪个方法-->
          <property name="defaultListenerMethod" value="receiveMessage"/>
          <!--设置监听器回复消息的队列（用的以前的点对点队列），也可以通过发送者发送方中的方法setJMSReplyTo设置-->
          <property name="defaultResponseDestination" ref="queueDestination"/>
      </bean>
  
      <!-- 消息监听适配器对应的监听容器 -->
      <bean id="messageListenerAdapterContainer" class="org.springframework.jms.listener.DefaultMessageListenerContainer">
          <property name="connectionFactory" ref="connectionFactory"/>
          <property name="destination" ref="adapterQueue"/>
          <property name="messageListener" ref="messageListenerAdapter"/><!-- 使用MessageListenerAdapter来作为消息监听器 -->
      </bean>
  
      <!-- 用于测试消息监听适配器的队列目的地 -->
      <bean id="adapterQueue" class="org.apache.activemq.command.ActiveMQQueue">
          <constructor-arg>
              <value>adapterQueue</value>
          </constructor-arg>
      </bean>
  ```

  



参考自：https://blog.csdn.net/moonsheep_liu/article/details/6684948



## Spring Boot 项目引入activemq（一）

以下步骤完全使用springboot/spring的支持，不额外管理或配置连接工厂、监听容器、生产者、消费者



### 依赖引入

引入activemq相关依赖，配合 spring-boot-autoconfigure 使用 （**@onConditional条件注册**）

`org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration`



```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-activemq</artifactId>
</dependency>
```



### 配置文件配置

`ActiveMQProperties.class, JmsProperties.class`

```
spring:
    activemq:
        # max-connections: 10   #连接池最大连接数
        # idle-timeout: 30000   #空闲的连接过期时间，默认为30秒
      brokerUrl: tcp://ts-ecloud-mq.vecrp.com:61617
      user: admin
      password: Nascent@2019
      pubSubDomain: true # 发布订阅模式
      pool:
        enabled: false # 先不使用连接池
    jms: # jmsTemplate
      pubSubDomain: true # 发布订阅模式
      cache:
        enabled: false
      template:
        deliveryMode: PERSISTENT # 持久化
```



### @JmsListener 监听器注册

```java
@JmsListener(destination = "topic.employees", // 监听目的地
        id = "ecrp-sg:employee", // 消息监听容器的ID
        selector = "${topic.employee.selector}", // 选择器，用于过滤不同的消息（这里使用配置文件方式动态指定）
        subscription = "持久化订阅")	// 持久化订阅的备注信息
public void onGuideMessage(ActiveMQMessage message) throws JMSException {
    // 消息来源公司
    String groupId = message.getStringProperty(CloudPlatformMessagePropertyName.COMPANY_ID);
    TextMessage textMessage = (TextMessage) message;
    log.info("[消息推送][员工] 推送员工数据：{}", textMessage.getText());
}
```



### @EnableJms 启用JMS

让Spring自动扫描JMS相关的Bean，并加载JMS配置文件`jms.properties`

自动扫描带有`@JmsListener`的Bean方法，并为其创建一个`MessageListener`把它包装起来。



### 项目启动时自定义监听器配置

```java
@Configuration
public class DynamicPropertySettingConfiguration {

    @Autowired
    AbstractEnvironment environment;
    @Autowired
    private
    ApplicationContext applicationContext;

    /**
     * 属性配置
     */
    private static final Map<String, Object> SOURCE_MAP = new ConcurrentHashMap<>(64);

    /**
     * 动态属性配置名称（仅用于区分当前配置集合所属分类而已）
     */
    public static final String DYNAMIC_CONFIG = "dynamicSetting";

    @PostConstruct
    public void init() {
        // 更新属性配置
        reloadPropertySource();
        // 加载到spring环境配置
        environment.getPropertySources().addFirst(new MapPropertySource(DYNAMIC_CONFIG, SOURCE_MAP));
        // 注册jms监听器实例（该方式仅能在容器启动时注册监听器到Spring维护的监听容器中管理，spring项目启动完成后修改注册监听器不会再影响spring监听容器，所以当前该代码示例仅用于spring项目启动之前需要动态获取待注册容器，但是spring项目启动之后不需要动态更新容器的场景。）
        // 其实可以省略registerJmsListenerBean()，直接在监听器注解中使用配置文件属性指定，项目启动时根据需要重写配置文件属性值即可实现
        registerJmsListenerBean();
    }
    /**
     * 更新属性配置
     */
    public boolean reloadPropertySource() {
        List<SgGroupDao> sgGroupDaos = SgGroupDao.dao().findAllGroup(SystemConstant.STATE_1);
        String groupIds = Joiner.on(",").join(sgGroupDaos.stream()
                .map(item -> "'" + item.getGroupId() + "'").collect(Collectors.toList()));
        String selectorExpression = "companyId in (" + groupIds + ") ";
        // 更新属性配置
        if (!Objects.equals(SOURCE_MAP.get(DynamicConstant.DYNAMIC_GROUP_ID_LIST), groupIds)) {
            SOURCE_MAP.put(DynamicConstant.DYNAMIC_GROUP_ID_LIST, groupIds);
            SOURCE_MAP.put(DynamicConstant.DYNAMIC_SHOP_SELECTOR, selectorExpression);
            SOURCE_MAP.put(DynamicConstant.DYNAMIC_EMPLOYEE_SELECTOR, selectorExpression);
            return true;
        }
        return false;
    }

    /**
     * 注册jms监听器实例
     */
    public void registerJmsListenerBean() {
        DefaultListableBeanFactory beanFactory =
                (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        // 若存在监听器则移除再重新注册
        if (beanFactory.containsBean(DynamicConstant.DYNAMIC_JMS_LISTENER)) {
            beanFactory.removeBeanDefinition(DynamicConstant.DYNAMIC_JMS_LISTENER);
        }
        BeanDefinitionBuilder definitionBuilder =
        BeanDefinitionBuilder.genericBeanDefinition(CloudPlatformMessageListener.class);
        // 注册监听器实例
        beanFactory.registerBeanDefinition(DynamicConstant.DYNAMIC_JMS_LISTENER,
                definitionBuilder.getBeanDefinition());
    }
    
    /**
     * 动态注册jms消息监听器
     */
//    @Scheduled(fixedRate = 10000)
//    public void dynamicRegisterJmsListenerBean() {
//        // 更新属性配置成功才重新注册监听器
//        if (reloadPropertySource()) {
//            // 注册jms监听器实例
//            registerJmsListenerBean();
//        }
//    }
}

public class DynamicConstant {
    /**
     * 动态监听器命名
     */
    public static final String DYNAMIC_JMS_LISTENER = "cloudPlatformMessageListener";
    /**
     * 动态公司ID集合
     */
    public static final String DYNAMIC_GROUP_ID_LIST = "dynamic_group_id_list";
    /**
     * 动态门店选择器
     */
    public static final String DYNAMIC_SHOP_SELECTOR = "topic.shop.selector";
    /**
     * 动态员工选择器
     */
    public static final String DYNAMIC_EMPLOYEE_SELECTOR = "topic.employee.selector";

}
```



###  JmsListenerAnnotationBeanPostProcessor Bean后置处理器

配合 `JmsListenerEndpointRegistrar` 、`JmsListenerEndpointRegistry` 注册消息监听容器，并启动消息监听器。

@JmsListener注解的函数都会被解析成一个消息处理器并交给Spring管理。





## Spring Boot 项目引入activemq（二）



#### 动态注册消息监听器容器

不使用Spring管理的消息监听容器，项目自行维护管理，步骤如下



1. 定义消息监听类（员工/门店），确认 destination 目的地

   - `? extends AbstractMessageListener`

   ```java
   public abstract class AbstractMessageListener implements MessageListener {
       /**
        * 目的地
        */
       public abstract String destination();
       /**
        * 是否使用发布订阅模式
        */
       public abstract boolean pubSubDomain();
   }
   ```

   

2. 定时器-定时获取有效集团信息，并注册消息监听器 `DynamicMessageListenerContainerConfiguration`

   ```java
   /**
    * 动态监听容器配置类，注册不同公司的消息监听器
    *
    * @author: yuye.huang
    * @since: 2021/1/11
    */
   @Slf4j
   @Configuration
   public class DynamicMessageListenerContainerConfiguration {
   
       /**
        * 需要动态注册的监听器
        */
       private final List<Class<? extends AbstractMessageListener>> messageListenerList
               = new ArrayList<Class<? extends AbstractMessageListener>>() {
           private static final long serialVersionUID = -2351177971062352088L;
   
           {
               add(SgGuideMessageListener.class);
               add(SgShopMessageListener.class);
           }
       };
   
       /**
        * 动态注册消息监听容器，查询有效的公司集合注册监听器
        */
       @PostConstruct
       public void dynamicRegisterMessageListenerContainer() {
           // 查询有效的公司集合
           List<SgGroupDao> sgGroupDaos = SgGroupDao.dao().findAllGroup(SystemConstant.STATE_1);
           // 注册消息监听容器
           refreshMessageListenerContainer(
                   sgGroupDaos.stream().map(SgGroupDO::getGroupId).collect(Collectors.toList()));
       }
   
       /**
        * 动态注册jms消息监听器
        */
       @Scheduled(initialDelay = 3000L, fixedRate = 10000L)
       public void dynamicRegisterJmsListenerBean() {
           dynamicRegisterMessageListenerContainer();
       }
   
       /**
        * 监听容器集合
        *
        * <p> k-clientId v-监听器 </p>
        */
       private final Map<String, MessageListenerContainer> listenerContainers = new ConcurrentHashMap<>();
       /**
        * 暂停的监听容器集合（clientId）
        */
       private final List<String> stoppedContainers = new ArrayList<>();
   
       /**
        * 刷新消息监听器容器列表
        *
        * @param groupIds 公司ID
        */
       public void refreshMessageListenerContainer(List<Long> groupIds) {
           // 公司被删除，暂停对应的监听器容器
           for (Map.Entry<String, MessageListenerContainer> entry : listenerContainers.entrySet()) {
               if (!stoppedContainers.contains(entry.getKey())
                       && !groupIds.contains(resolveGroupIdFromClientId(entry.getKey()))) {
                   stopMessageListener(entry.getKey());
               }
           }
           // 新增公司，注册监听器容器
           groupIds.forEach(groupId -> {
               // 注册公司[员工\门店消息]监听器
               for (Class<? extends AbstractMessageListener> aClass : messageListenerList) {
                   AbstractMessageListener messageListener;
                   try {
                       messageListener = aClass.newInstance();
                   } catch (InstantiationException | IllegalAccessException e) {
                       log.error("[动态JMS监听] 监听器实例创建失败：[{}]", aClass.getName());
                       continue;
                   }
                   String clientId = buildClientId(messageListener.destination(), groupId);
                   // 创建监听器容器
                   if (!listenerContainers.containsKey(clientId)) {
                       registerMessageListenerContainer(groupId, messageListener);
                       continue;
                   }
                   // 重新启动被关闭的监听容器
                   if (stoppedContainers.contains(clientId)) {
                       log.info("[动态JMS监听] 重启监听器：[{}]", clientId);
                       listenerContainers.get(clientId).start();
                       stoppedContainers.remove(clientId);
                   }
               }
           });
       }
   
       /**
        * 注册消息监听容器：根据不同公司创建selector
        *
        * @param groupId                公司ID
        * @param sgGuideMessageListener 消息监听器
        */
       private void registerMessageListenerContainer(Long groupId, AbstractMessageListener sgGuideMessageListener) {
           registerMessageListenerContainer(sgGuideMessageListener,
                   sgGuideMessageListener.destination(),
                   buildClientId(sgGuideMessageListener.destination(), groupId),
                   " companyId = '" + groupId + "'",
                   sgGuideMessageListener.pubSubDomain());
       }
   
       /**
        * 构建消息监听容器
        *
        * @param clientId        客户端ID
        * @param messageListener 消息监听器
        * @param messageSelector 消息选择器
        */
       public void registerMessageListenerContainer(MessageListener messageListener, String destination,
                                                    String clientId, String messageSelector, boolean pubSubDomain) {
           if (listenerContainers.containsKey(clientId)) {
               return;
           }
           log.info("[动态JMS监听] 注册监听器：[{}]", clientId);
           DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
           listenerContainers.put(clientId, messageListenerContainer);
           messageListenerContainer.setConnectionFactory(SpringContext.me().getBean(ActiveMQConnectionFactory.class));
           messageListenerContainer.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
           messageListenerContainer.setConcurrency("1");
           messageListenerContainer.setMessageListener(messageListener);
           messageListenerContainer.setPubSubDomain(pubSubDomain);
           messageListenerContainer.setDestination(pubSubDomain
                   ? new ActiveMQTopic(destination) : new ActiveMQQueue(destination));
           messageListenerContainer.setClientId(clientId);
           messageListenerContainer.setMessageSelector(messageSelector);
           messageListenerContainer.afterPropertiesSet();
           messageListenerContainer.start();
       }
   
   
       /**
        * 停止监听容器
        *
        * @param clientId 客户端ID
        */
       public void stopMessageListener(String clientId) {
           log.info("[动态JMS监听] 停止并移除监听：[{}]", clientId);
           MessageListenerContainer messageListenerContainer = listenerContainers.get(clientId);
           messageListenerContainer.stop(() -> stoppedContainers.add(clientId));
       }
   
       /**
        * 构建客户端ID
        *
        * @param destination 目的地
        * @param groupId     公司ID
        * @return
        */
       private String buildClientId(String destination, Long groupId) {
           return destination + DynamicConstant.CLIENT_ID_SPLIT_CHAR + groupId;
       }
   
       /**
        * 从客户端ID从解析出公司ID
        *
        * @param clientId 客户端ID
        * @return
        */
       private Long resolveGroupIdFromClientId(String clientId) {
           return Long.valueOf(clientId.substring(
                   clientId.lastIndexOf(DynamicConstant.CLIENT_ID_SPLIT_CHAR)
                           + DynamicConstant.CLIENT_ID_SPLIT_CHAR.length()));
       }
   }
   ```

   

3. 根据不同集团、订阅主题生成不同的 clientId 并创建不同的消息监听器  refreshMessageListenerContainer(List<Long> groupIds)

```java
clientId生成规则：${destination} + "::" + ${groupId}
```

4. 若集团状态被删除，则暂停对应的消息监听器.

```java
messageListenerContainer.stop()
```

5. 若集团状态从删除变更为正常，则重新启动对应的消息监听器

```java
messageListenerContainer.start()
```

