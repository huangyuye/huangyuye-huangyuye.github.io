# MYSQL相关



## 开发规范

- 数据库中用到的临时表以tmp为前缀并以日期为后缀;
- 数据库中用到的备份表以bak为前缀并以日期为后缀
- 所有的数据库对象名称禁止使用MySQL保留关键字
- 在不同的库或表中，要保证所有存储相同数据的列名和列类型必须一致
- 控制单表数据量的大小，建议控制在500万行以内。
可以采用历史数据归档(常见于日志表)和分库分表的方式控制单表数据的大小。
- 谨慎使用MySQL分区表，避免跨分区查询，否则效率很低。
分区表在逻辑上表现为一个表，但是在物理上将数据存储在多个文件。最好能将分区表的不同分区文件存储在不同的磁盘阵列上。
- 表中的列不要太多，尽量做到冷热数据分离，减小表的宽度。
减少表的宽度，可以让一页内存中容纳更多的行，进而减少磁盘IO，更有效的利用缓存。
- 经常一起使用的列尽量放到一个表中，避免过多的关联操作
- 优先为表中的每一列选择符合存储需要的最小的数据类型;
列的字段类型越大，建立索引占据的空间就越大，导致一个页中的索引越少，造成IO次数增加，影响性能
- 每个字段尽可能具有NOT NULL属性
- 使用数字类型存储IP地址，用INET_ATON、INET_NTOA可以在IP地址和数字类型间转换
- VARCHAR类型的长度应该尽可能短。VARCHAR类型虽然在硬盘上是动态长度的，但是在内存中占用的空间是固定的最大长度.
- 限制每张表上的索引数量，建议单张表索引不超过5个
- 禁止给表中的每一列都建立单独的索引。设计良好的联合索引比每一列上的单独索引效率要高出很多。
- 每个Innodb表都必须有一个主键，且不使用更新频繁的列作为主键，不使用多列主键。不使用UUID、MD5、字符串列作为主键。最好选择值的顺序是连续增长的列作为主键，所以建议选择使用自增ID列作为主键
- 建议在下面的列上建立索引：
在SELECT，UPDATE，DELETE语句的WHERE从句上的列。在ORDER BY，GROUP BY，DISTINCT上的列。多表JOIN的关联列。
- 区分度最高的列放在联合索引的最左侧。使用频繁的列放在联合索引的最左侧。
- 避免重复的索引，如：index(a,b,c)，index(a,b)，index(a) ;
重复的和冗余的索引会降低查询效率，因为MySQL查询优化器会不知道该使用哪个索引。
- 不要依靠外键保证参照完整性，在业务端实现参照完整性的要求。建立外键约束后的表在插入数据时要进行数据参照完整性检查，这会导致消耗数据库性能。虽然不使用MySql自带的外键，但一定在表与表之间的关联键上建立索引。
- 避免数据类型的隐式转换。隐式转换会导致索引失效。
- 避免使用子查询，子查询会产生临时表，临时表没有任何索引，数据量大时严重影响效率。建议把子查询转化成关联查询
- 使用IN代替OR
- 禁止在where从句中对列进行函数转换和计算，会导致索引无效
- 用HASH进行散表，表名后缀使用十进制数，下标从  0  开始
- 使用 UNSIGNED 存储非负整数

- int使用固定4个字节存储，int(11)与int(4)只是显示宽度的区别
  
    - int(M): M indicates the maximum display width for integer types.
    
- 使用 timestamp 存储时间？

- 使用 INT UNSIGNED 存储 IPV4 ？存储ip最好用 int 存储而非 char(15) 

- 使用 VARBINARY 存储大小写敏感的变长字符串？

- 不允许使用 ENUM 

- 避免使用 NULL 字段， NULL 字段很难查询优化，NULL字段的索引需要额外空间，NULL 非常影响索引的查询效率，NULL字段的复合索引无效

- 单张表中索引数量不超过5个，单个索引中的字段数不超过5个

- 表必须有主键

- 尽量不选择字符串列作为主键，不使用 UUID   MD5   HASH 这些作为主键(数值太离散了)，默认使⽤非空的唯一键作为主键

- 多表JOIN的字段注意核⼼SQL优先考虑覆盖索引？

- 研发要经常使用 explain ，如果发现索引选择性差，必须让他们学会使用hint

- 能不用 NOT IN 就不用 NOTIN ，坑太多了。。会把空和NULL给查出来？不使用负向查询，如 NOT IN /  LIKE 

- 视图以view_开头，事件以event_开头，触发器以trig_开头，存储过程以proc_开头，函数以func_开头，应用上面禁用

- 普通索引以idx_各个列名简称，唯一索引以uk_各个列名简称命名，中间用_隔开。如idx_col1_col2_col3(col1,col2,col3)，如果列过长，用简写

    

### 参考连接

- [《互联网MySQL开发规范》](https://www.cnblogs.com/zzsdream/p/6652923.html)



## 数据库配置相关：（详细）

- https://tech.youzan.com/shu-ju-ku-lian-jie-chi-pei-zhi/



1. mysql对json的支持：
    - 可通过给json对象属性建立虚拟列后创建索引
    - https://blog.csdn.net/wuzuodingfeng/article/details/53693209
2. 数据库设计 https://www.cnblogs.com/waj6511988/p/7027127.html
    - 字段不可拆分
    - 表字段依赖主键
    - 表字段与主键直接而非间接相关
        - 第 一范式和第二范式在于有没有分出两张表，第二范式是说一张表中包含了所种不同的实体属性，那么要必须分成多张表，第三范式是要求已经分成了多张表，那么一张表中只能有另一张表中的id（主键），而不能有其他的任何信息（其他的信息一律用主键在另一表查询）。
        - https://www.cnblogs.com/RunForLove/p/5693986.html
        - 不同组件间的表需要外键关联也尽量不要创建外键关联，而只是记录关联表的一个主键，确保组件对应的表之间的独立性，为系统或表结构的重构提供可能性。
        - 采用领域模型驱动的方式和自顶向下的思路进行数据库设计，首先分析系统业务，根据职责定义对象。
        - https://www.imooc.com/article/35403
        - 主键外键
            - https://blog.csdn.net/bingqingsuimeng/article/details/51595560



1. 查看表字段：desc table_name;
2. 查看表索引  
3. explain：https://www.cnblogs.com/xuanzhi201111/p/4175635.html
```
mysql> explain select * from servers;
+----+-------------+---------+------+---------------+------+---------+------+------+-------+
| id | select_type | table   | type | possible_keys | key  | key_len | ref  | rows | Extra |
+----+-------------+---------+------+---------------+------+---------+------+------+-------+
|  1 | SIMPLE      | servers | ALL  | NULL          | NULL | NULL    | NULL |    1 | NULL  |
+----+-------------+---------+------+---------------+------+---------+------+------+-------+
1 row in set (0.03 sec)
```
- id: SQL执行的顺序的标识,SQL从大到小的执行
- select_type:
    ```
        (1) SIMPLE(简单SELECT,不使用UNION或子查询等)

        (2) PRIMARY(查询中若包含任何复杂的子部分,最外层的select被标记为PRIMARY)
        
        (3) UNION(UNION中的第二个或后面的SELECT语句)
        
        (4) DEPENDENT UNION(UNION中的第二个或后面的SELECT语句，取决于外面的查询)
        
        (5) UNION RESULT(UNION的结果)
        
        (6) SUBQUERY(子查询中的第一个SELECT)
        
        (7) DEPENDENT SUBQUERY(子查询中的第一个SELECT，取决于外面的查询)
        
        (8) DERIVED(派生表的SELECT, FROM子句的子查询)
    ```
- table:
  
    - 显示这一行的数据是关于哪张表的，有时不是真实的表名字,看到的是derivedx(x是个数字,我的理解是第几步执行的结果) (derived->派生表的SELECT, FROM子句的子查询)
- type:表示MySQL在表中找到所需行的方式，又称“访问类型”。
```
常用的类型有： ALL, index,  range, ref, eq_ref, const, system, NULL（从左到右，性能从差到好）

ALL：Full Table Scan， MySQL将遍历全表以找到匹配的行

index: Full Index Scan，index与ALL区别为index类型只遍历索引树

range:只检索给定范围的行，使用一个索引来选择行

ref: 表示上述表的连接匹配条件，即哪些列或常量被用于查找索引列上的值

eq_ref: 类似ref，区别就在使用的索引是唯一索引，对于每个索引键值，表中只有一条记录匹配，简单来说，就是多表连接中使用primary key或者 unique key作为关联条件

const、system: 当MySQL对查询某部分进行优化，并转换为一个常量时，使用这些类型访问。如将主键置于where列表中，MySQL就能将该查询转换为一个常量,system是const类型的特例，当查询的表只有一行的情况下，使用system

NULL: MySQL在优化过程中分解语句，执行时甚至不用访问表或索引，例如从一个索引列里选取最小值可以通过单独索引查找完成。
```
---
## mysql中InnoDB引擎中页的概念
- page头部保存了两个指针，分别指向前一个Page和后一个Page，头部还有Page的类型信息和用来唯一标识Page的编号。根据这个指针分布可以想象到Page链接起来就是一个双向链表
- https://segmentfault.com/a/1190000008545713

---
### 20190307
1. 问题：

```
2019-03-06 17:38:01.726 [http-nio-30002-exec-10] INFO  c.n.e.c.d.DataSourceTransactionManager - GET事务。。。
2019-03-06 17:38:01.727 [http-nio-30002-exec-10] DEBUG c.n.e.c.d.DataSourceTransactionManager - Creating new transaction with name [com.nascent.deposit.open.impl.OpenApplicationAuthServiceImpl.saveOpenApplicationAuth]: PROPAGATION_NESTED,ISOLATION_DEFAULT; '',-java.lang.Exception
2019-03-06 17:38:01.727 [http-nio-30002-exec-10] INFO  c.n.e.c.d.DataSourceTransactionManager - 事务开始。。。
2019-03-06 17:38:01.730 [http-nio-30002-exec-10] DEBUG c.n.e.c.d.DataSourceTransactionManager - Acquired Connection [com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl@263fdf14] for JDBC transaction
2019-03-06 17:38:01.730 [http-nio-30002-exec-10] DEBUG c.n.e.c.d.DataSourceTransactionManager - Switching JDBC Connection [com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl@263fdf14] to manual commit
2019-03-06 17:38:01.739 [http-nio-30002-exec-10] DEBUG o.s.jdbc.core.JdbcTemplate - Executing SQL update and returning generated keys
2019-03-06 17:38:01.740 [http-nio-30002-exec-10] DEBUG o.s.jdbc.core.JdbcTemplate - Executing prepared SQL statement
2019-03-06 17:38:01.758 [http-nio-30002-exec-10] DEBUG o.s.jdbc.core.JdbcTemplate - SQL update affected 1 rows and returned 1 keys
2019-03-06 17:38:01.762 [http-nio-30002-exec-10] DEBUG o.s.jdbc.core.JdbcTemplate - Executing SQL update and returning generated keys
2019-03-06 17:38:01.762 [http-nio-30002-exec-10] DEBUG o.s.jdbc.core.JdbcTemplate - Executing prepared SQL statement
2019-03-06 17:38:01.766 [http-nio-30002-exec-10] DEBUG o.s.jdbc.core.JdbcTemplate - SQL update affected 1 rows and returned 1 keys

2019-03-06 17:38:01.769 [http-nio-30002-exec-10] DEBUG c.n.e.c.d.DataSourceTransactionManager - Initiating transaction commit
2019-03-06 17:38:01.769 [http-nio-30002-exec-10] DEBUG c.n.e.c.d.DataSourceTransactionManager - Committing JDBC transaction on Connection [com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl@263fdf14]
2019-03-06 17:38:01.779 [http-nio-30002-exec-10] DEBUG c.n.e.c.d.DataSourceTransactionManager - Releasing JDBC Connection [com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl@263fdf14] after transaction
2019-03-06 17:38:01.779 [http-nio-30002-exec-10] DEBUG c.n.e.c.d.DataSourceTransactionManager - Resuming suspended transaction after completion of inner transaction
2019-03-06 17:38:01.779 [http-nio-30002-exec-10] DEBUG c.n.e.c.d.DataSourceTransactionManager - Initiating transaction commit
2019-03-06 17:38:01.779 [http-nio-30002-exec-10] DEBUG c.n.e.c.d.DataSourceTransactionManager - Committing JDBC transaction on Connection [com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl@259eed36]

2019-03-06 17:38:01.789 [http-nio-30002-exec-10] ERROR c.n.d.o.i.r.GlobalExceptionHandler - No value for key [{
	CreateTime:"2019-03-06 16:41:29",
	ActiveCount:0,
	PoolingCount:3,
	CreateCount:3,
	DestroyCount:0,
	CloseCount:11,
	ConnectCount:11,
	Connections:[
		{ID:1605226490, ConnectTime:"2019-03-06 16:41:29", UseCount:0, LastActiveTime:"2019-03-06 16:41:29"},
		{ID:1078491788, ConnectTime:"2019-03-06 16:41:29", UseCount:0, LastActiveTime:"2019-03-06 16:41:29"},
		{ID:641720084, ConnectTime:"2019-03-06 16:41:29", UseCount:11, LastActiveTime:"2019-03-06 17:38:01"", CachedStatementCount:7}
	]
}

[
	{
	ID:1605226490, 
	poolStatements:[
		]
	},
	{
	ID:1078491788, 
	poolStatements:[
		]
	},
	{
	ID:641720084, 
	poolStatements:[
		{hitCount:6,sql:"SELECT  group_id,app_key,app_secret  from dp_open_application WHERE 1=1 
and app_key = ? and state = 1"	},
		{hitCount:0,sql:"select * from dp_sys_database where 1=1
and group_id = ?
 limit 1"	},
		{hitCount:0,sql:"select * from dp_open_application where 1=1
and app_key = ?
and group_id = ?"	},
		{hitCount:0,sql:"select * from dp_sys_deposit_account where 1=1
and deposit_key = ?
and group_id = ?"	},
		{hitCount:0,sql:"select * from dp_open_application_auth where 1=1
and deposit_account = ?
and app_key = ?
and group_id = ?"	},
		{hitCount:0,sql:"insert into `dp_open_application_auth`(`access_token`, `update_time`, `app_key`, `create_time`, `group_id`, `deposit_key`, `deposit_secret`, `expire_time`, `state`, `deposit_account`, `app_secret`) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"	},
		{hitCount:0,sql:"insert into `dp_open_auth_record`(`access_token`, `update_time`, `app_key`, `create_time`, `group_id`, `state`, `deposit_account`) values(?, ?, ?, ?, ?, ?, ?)"	}
		]
	}
]] bound to thread [http-nio-30002-exec-10]
```
- DataSourceTransactionManager
- AbstractPlatformTransactionManager
- TransactionSynchronizationManager

---
- 事务问题：
```
@Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    protected boolean verifyTransactionAmount(P in, BigDecimal currentModifyAmount)
-->> 锁

1.查询是否锁表

show OPEN TABLES where In_use > 0;

2.查询进程（如果您有SUPER权限，您可以看到所有线程。否则，您只能看到您自己的线程）

show processlist

3.杀死进程id（就是上面命令的id列）

kill id
1：查看当前的事务
SELECT * FROM INFORMATION_SCHEMA.INNODB_TRX;
2：查看当前锁定的事务
SELECT * FROM INFORMATION_SCHEMA.INNODB_LOCKS;
3：查看当前等锁的事务
SELECT * FROM INFORMATION_SCHEMA.INNODB_LOCK_WAITS;
```


---
- mysql 5个key：https://blog.csdn.net/liushuijinger/article/details/12832017
    - 元组（tuple）是关系数据库中的基本概念，关系是一张表，表中的每行（即数据库中的每条记录）就是一个元组，每列就是一个属性。 在二维表里，元组也称为行。
    ```
    超键(super key):在关系中能唯一标识元组的属性集称为关系模式的超键

    候选键(candidate key):不含有多余属性的超键称为候选键
    
    主键(primary key):用户选作元组标识的一个候选键程序主键
    
    外键(foreign key)如果关系模式R1中的某属性集不是R1的主键，而是另一个关系R2的主键则该属性集是关系模式R1的外键。
    ```
### mysql key
```
Candidate key 候选键
Primary Key   主键
Foreign Key   外键
Alternate Key 替代键(也是候选键)
Composite Key 组合键
Example:

STUDENT {SID, FNAME, LNAME, COURSEID}

Here in STUDENT table keys are:

Candidate keys are SID or FNAME+LAME

Primary Key: SID

Foreign Key: COURSEID

Alternate Key:  FNAME+LAME

Composite Key:  FNAME+LAME

Candidate Key
Candidate keys are those keys which is candidate for primary key of a table. In simple words we can understand that such type of keys which full fill all the requirements of primary key which is not null and have unique records is a candidate for primary key. So thus type of key is known as candidate key. Every table must have at least one candidate key but at the same time can have several.

Primary Key
Such type of candidate key which is chosen as a primary key for table is known as primary key. Primary keys are used to identify tables. There is only one primary key per table. In SQL Server when we create primary key to any table then a clustered index is automatically created to that column.

Foreign Key
Foreign key are those keys which is used to define relationship between two tables. When we want to implement relationship between two tables then we use concept of foreign key. It is also known as referential integrity. We can create more than one foreign key per table.  foreign key is generally a primary key from one table that appears as a field in another where the first table has a relationship to the second. In other words, if we had a table A with a primary key X that linked to a table B where X was a field in B, then X would be a foreign key in B.

Alternate Key
If any table have more than one candidate key, then after choosing primary key from those candidate key, rest of candidate keys are known as an alternate key of  that table. Like here we can take a very simple example to understand the concept of alternate key. Suppose we have a table named Employee which has two columns EmpID and EmpMail, both have not null attributes and unique value. So both columns are treated as candidate key. Now we make EmpID as a primary key to that table then EmpMail is known as alternate key.

Composite Key
When we create keys on more than one column then that key is known as composite key. Like here we can take an example to understand this feature. I have a table Student which has two columns Sid and SrefNo and we make primary key on these two column. Then this key is known as composite key.

```

- mysql 搭建配置
    - 大小写敏感：show variables like "lower%"
    - 字符编码 utf8mb4 
        - utf8编码的超集，兼容utf8，并且能存储4字节的表情字符。
            - mysql版本最低不能低于5.5.3+
            - 驱动最低不能低于5.1.13
        - SHOW VARIABLES LIKE 'character_set_%'; 
        - my.ini：
        ```
        [client] 
        default-character-set = utf8mb4 
        [mysql] 
        default-character-set = utf8mb4 
        [mysqld]
        character-set-server = utf8mb4
        collation-server = utf8mb4_unicode_ci
        character-set-client-handshake = FALSE
        init_connect='SET NAMES utf8mb4'
        ```
        - set names utf8mb4
        ```
        对于mysql字符集我们不想出现乱码情况有一个经验就是character_set_client、character_set_connection、character_set_results字符尽量设置成一致的。
        set names utf8mb4 等同于对上面三个参数的配置成utf8mb4
        ```
        - collation 
            - 每个character set会对应一定数量的collation
        - skip-character-set-client-handshake=1 跳过mysql程序起动时的字符参数设置 ，使用服务器端字符集设置
    - group_concat 长度 group_concat_max_len
        - show variables like "group_concat_max_len";
        - SET GLOBAL group_concat_max_len = 1024;
        - MySQL配置文件(my.ini) 写入 group_concat_max_len = -1

---
## mysql 相关问题
### 覆盖索引
索引分类：
1. 主键索引
    - 索引结构：存储了整行数据
2. 非主键索引
    - 非主键索引中存储的值为主键id
    - 索引流程：非主键索引树上找到索引节点 -> 根据索引节点存储的主键ID在主键索引书上找到对应主键的索引节点 -> 从主键索引节点中获取字段值

- 在上述流程2中从非主键索引树搜索回到主键索引树搜索的过程称为：**回表**。那么应该如何优化？
    - 覆盖索引（covering index ，或称为索引覆盖）即从非主键索引中就能查到的记录，而不需要查询主键索引中的记录，避免了回表的产生减少了树的搜索次数，显著提升性能
    - 覆盖索引避免了回表现象的产生，从而减少树的搜索次数，显著提升查询性能，所以使用覆盖索引是性能优化的一种手段
- 如何使用覆盖索引？
    - 建立联合索引，此时节点索引上就包含了多个字段的信息，可以在非主键节点直接返回数据表字段
- 如何确定是否使用了覆盖索引？
    - explain sql，若Extra中Using index表明我们成功使用了覆盖索引
- 若数据表没用主键的场景会怎么样?
    - 使用不了主键索引，查询会进行全表扫描
    - 影响数据插入性能，插入数据需要生成ROW_ID，而生成的ROW_ID是全局共享的，并发会导致锁竞争，影响性能
    - 如果没有合适的字段来作为主键，可以设置一个业务无关的的代理主键，可以是自增ID，也可以是UUID（建议使用自增ID，性能较好）
- MySQL数据表使用InnoDB作为存储引擎的时候，数据结构就是使用B+树，而数据本身存储在主键索引上，也就是通常所说的聚簇索引，也就是每个表都需要有个聚簇索引树。

### pt-online-schema-change
- https://segmentfault.com/a/1190000014924677
### 避免将可索引的列设为 可NULL
```
要尽量避免 NULL
要尽可能地把字段定义为 NOT NULL。即使应用程序无须保存 NULL（没有值），也有许多表包含了可空列（Nullable Column）,这仅仅是因为它为默认选项。除非真的要保存 NULL，否则就把列定义为 NOT NULL。
MySQL难以优化引用了可空列的查询，它会使索引、索引统计和值更加复杂。可空列需要更多的储存空间，还需要在MySQL内部进行特殊处理。当可空列被索引的时候，每条记录都需要一个额外的字节，还可能导致 MyISAM 中固定大小的索引(例如一个整数列上的索引)变成可变大小的索引。
即使要在表中储存「没有值」的字段，还是有可能不使用 NULL 的。考虑使用 0、特殊值或空字符串来代替它。
把 NULL 列改为 NOT NULL 带来的性能提升很小，所以除非确定它引入了问题，否则就不要把它当作优先的优化措施。然后，如果计划对列进行索引，就要尽量避免把它设置为可空。
```
### 最左侧原则
- https://blog.csdn.net/LJFPHP/article/details/90056936
### mysql 数据页
- https://segmentfault.com/a/1190000008545713

### 尽量做到冷热数据分离



https://www.cnblogs.com/mfryf/archive/2012/02/13/2349758.html