## Powerdesigner 使用

### 数据表创建

#### 1. 创建数据库表Table

#### 2. Table配置页面相关如下

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154057.png)

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154216.png)



<!-- more -->



### 常用配置技巧

#### 1.  配置commoent、domain列的启用

​            \1. Tools -> Display Preferences -> 

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154119.png)

​       	

#### 2. 设置 table配置页面使用的列属性(快捷键Ctrl + U)

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154123.png)

#### 3. 添加comment、domain列属性

​      (在建立表时，如果很多字段的data type是一样的类型，一样的长度，那么可以建立domain，方便统一管理)

​            \- 系统主键

​            \- 时间格式

​            \- 字符串格式

​            \- 用户编号

![img](D:\AppData\YoudaoNote\qqB53912A331D409871E525BE52BFB5C3B\9c70b9754960485490c7e5ddc8068b61\clipboard.png)

#### 新建表，addColumns导入其他表中的字段，可以修改，但是两张表的修改不同步(如会员卡号，订单编号，字段类型和长度一致)

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154128.png)

#### 复制列 -> 共用其他表中的字段，当前表引用后不允许修改，只有被引用的表才能修改，且修改同步到所有引用到的表(如会员卡号，订单编号，字段类型和长度一致)

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154204.png)

#### 配置字段属性：选中指定行，点击上侧工具栏按钮 / 快捷键 / 直接双击

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154132.png)

####  配置字段类型无符号

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154134.png)

#### 查看当前表的建表语句或者整个空间的建表语句

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154139.png)

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154156.png)

#### 直接导出或预览查看

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154149.png)

#### 创建索引

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154145.png)

#### 配置字段中name和code不同步

Tools→General Options→Dialog→Name to Code mirroring（取消勾选）→最后确定即可



#### 配置版本控制，多人协作：repository

**a. 创建/定义repository （repository -> definition）**

**b. 初始化repository 默认登陆账号为大写ADMIN 密码空 repository -> connection**

**c. 设置用户和群组及操作权限repository->Users**

**参考链接**：http://www.doc88.com/p-806805418378.html

http://www.bubuko.com/infodetail-1083930.html

**d. 修改数据库charset为utf-8：**https://blog.csdn.net/zzcchunter/article/details/83650748

[http://www.makaidong.com/%E5%8D%9A%E5%AE%A2%E5%9B%AD%E6%90%9C/10098.shtml](http://www.makaidong.com/博客园搜/10098.shtml)

e. checkin pdm / merge pdm



![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/clipboard.png)
