## 业务



#### 组内排序

分组查询每组的前n条记录，要求：需要按某个字段分组，且每组只能取一条记录；按某个字段倒序

1. **子查询：**先全表排序作为子查询，然后分组

   ```sql
   1. select * from (select * from table2 order by age desc) as a group by a.table1_id
   
   2. select a.* from table2 as a where age = (select max(age) from table2 where a.table1_id=table1_id)
   
   3. select a.* from table2 as a where not exists (select * from table2 where table1_id=a.table1_id and age>a.age)
   
   4. select a.* from table2 as a where exists (select count(*) from table2 where table1_id=a.table1_id and age>a.age having count(*)=0)
   ```

2. **group_concat(id order bydatedesc) + SUBSTRING_INDEX**

   ```sql
   SELECT * FROM `test` WHERE id IN(SELECT SUBSTRING_INDEX(GROUP_CONCAT(id ORDER BY `date` DESC)
   ```



- 关于exists的解释：只要exists引导的子句有结果集返回，则表示连接条件为真
  - 参考链接：https://zhuanlan.zhihu.com/p/20005249



#### 删除重复数据

```sql
delete from sg_guide_shop where id in (select id from sg_guide_shop group by guide_id,shop_id having count(id) > 1) and id not in (select min(id) from sg_guide_shop group by guide_id,shop_id  having count(id)>1);
```



#### utf8mb4编码

将数据表字段修改为utf8mb4 及将数据表编码改为utf8mb4

```sql
ALTER TABLE sg_login_log MODIFY COLUMN operate_name VARCHAR(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT "操作人";

ALTER TABLE sg_login_log CONVERT TO CHARACTER SET utf8mb4;
```



####  统计各日期的业务数据，没有数据的日期补零

```sql
# 1. 生成辅助表，生成N条记录，每条记录用于辅助生成1天的数据
drop table if  exists sg_base_datetime_help;
CREATE TABLE `sg_base_datetime_help` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `temp` tinyint(4) default null COMMENT '临时字段，生成数据后删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日期辅助表，用于生成n条数据';
# 先生成一条数据
insert into sg_base_datetime_help(temp) select now() from dual;
# 下面两条语句执行N次, 生成4k-1w条数据(4096即可)
insert into sg_base_datetime_help(temp) select now() from sg_base_datetime_help;
select count(1) from sg_base_datetime_help;
# 删除临时字段
alter table sg_base_datetime_help drop column temp;

2. 使用辅助表作为左关联主表，主表sql
SELECT
	@cdate := DATE_FORMAT(date_add( @cdate, INTERVAL - 1 DAY ), '%Y-%m-%d') as date
FROM
	(SELECT @cdate := date_add(#{endTime}, interval + 1 day) FROM sg_base_datetime_help) tmp1 WHERE @cdate >#{startTime};
```



## 元信息



#### 查询数据库数据表情况

如数据行数、自增id号；注意`information_schema`的`auto_increment`不一定为实际值!

```sql
SELECT TABLE_CATALOG,
       TABLE_SCHEMA,
       TABLE_NAME,
       TABLE_TYPE,
       ENGINE,
       VERSION,
       ROW_FORMAT,
       TABLE_ROWS,
       AVG_ROW_LENGTH,
       DATA_LENGTH,
       MAX_DATA_LENGTH,
       INDEX_LENGTH,
       DATA_FREE,
       AUTO_INCREMENT,
       CREATE_TIME,
       UPDATE_TIME,
       CHECK_TIME,
       CHARACTER_SET_NAME,
       TABLE_COLLATION,
       CHECKSUM,
       CREATE_OPTIONS,
       TABLE_COMMENT
FROM information_schema.TABLES
         LEFT JOIN information_schema.COLLATION_CHARACTER_SET_APPLICABILITY ON TABLE_COLLATION = COLLATION_NAME
WHERE TABLE_SCHEMA = 'ecrp_sg_test' AND TABLE_TYPE = 'BASE TABLE';
```



#### 查询存在某字段的数据表

```sql
select table_name,column_name from information_schema.columns 
where table_schema = #{tableSchema} 
and column_name in ('group_id',"groupid",'brand_id', "groupid", "source_id");
```



#### 查询数据库查询进程

```sql
select table_name,column_name from information_schema.PROCESSLIST; 
```

