## 参考链接

- [Docker 基础 : 数据管理](https://www.cnblogs.com/sparkdev/p/6216154.html)
- [Docker 数据卷之进阶篇](https://www.cnblogs.com/sparkdev/p/8504050.html)
- [你必须知道的Docker数据卷](https://mp.weixin.qq.com/s?__biz=MzAwNTMxMzg1MA==&mid=2654075892&idx=7&sn=616d544f2b09f2a68620ba047ec4476a)



## docker 容器内的数据管理

在使用容器的过程中，会有几个常见的场景，如查看容器内应用产生的数据，或者想要快速修改容器中应用的某个配置文件，除了进入容器进行操作这种方式，还有什么高效的方法吗？若容器被删除，容器内的数据也被删除怎么办？

这就又涉及一个问题，容器是如果对容器内数据进行管理的？

目前Docker提供了三种不同的方式将数据从宿主机挂载到容器中：

- **volumes：**Docker管理宿主机文件系统的一部分，默认位于 /var/lib/docker/volumes 目录中；（**最常用的方式**）
- **bind mounts：**可以存储在宿主机系统的任意位置；（**比较常用的方式**）但是，bind mount在不同的宿主机系统时不可移植的，这也是为什么bind mount不能出现在Dockerfile中的原因。
- **tmpfs：**挂载存储在宿主机系统的内存中，而不会写入宿主机的文件系统；（**一般都不会用的方式**）

![图片](https://mmbiz.qpic.cn/mmbiz_png/fCpd1cf8iacYo0R2ok1kmV4OFXzx0iajS0c2OjpyFKW3s3ib1zMiceU8RT3xdWf3rwaY0x6iaf9rJcUPF4qNa699Pvw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



### 数据卷

数据卷是一个可供容器使用的特殊目录，它绕过文件系统（?），而通过挂载宿主机文件系统目录的方式，可以提供很多有用的特性：

1. 数据卷可以在容器之间共享和重用。
2. 对数据卷的更改会立即生效。
3. 对数据卷的更新不会影响镜像。
4. 数据卷会一直存在，直到没有容器使用。

数据卷的使用，类似于 linux 下对目录或文件进行 mount 操作。



- 一般情况下，每个容器都会有自己的数据卷，而从同一镜像创建出来的容器的数据卷的标识都不一样。
  - 通过`docker inspect`可查看容器的数据卷—volume：mounts列表中`type="volume"`的元素即docker数据卷，默认Name为该数据卷的名称，`Destination` 为挂载到容器的文件夹路径，可以通过`docker volume ls` 查看到对应的数据卷
  - 使用 `docker volume prune` 删除未被容器使用的数据卷

- 容器的数据卷在容器移除后还存在于宿主机文件系统中，可以在下一次创建容器时挂载并复用。
- 测试**数据卷数据共享**：
  - 运行 activemq 容器（**activemq1**），`docker inspect activemq` 查看容器的数据卷，将挂载到 `/opt/activemq/conf` 容器文件夹的数据卷的 Name复制下来，假设为 `${activemq-conf-volume-name}`
  - `docker volume ls`可以列出所有的docker 数据卷，其中可以看到名为 `${activemq-conf-volume-name}`的数据卷
  - 进入容器（**activemq1**），修改 `/opt/activemq/conf` 文件夹下的`activemq.xml` 文件，随便加下任何注释内容，如`<!-- test docker-volume --> `
  - 退出容器，运行新的activemq容器（**activemq2**），并将docker数据卷挂载到新的容器中 
    - -v `${activemq-conf-volume-name}`:/opt/activemq/conf
  - 进入容器（**activemq2**），查看 `/opt/activemq/conf` 文件夹下的`activemq.xml` 文件，可以看到在容器 **activemq1**写入的注释 `<!-- test docker-volume --> `
- 与volumes不同，bind mounts的方式会隐藏掉被挂载目录里面的内容（如果非空的话）
- 同volumes一样，当我们清理掉容器之后，挂载目录里面的文件仍然还在，不会随着容器的结束而消失，从而实现数据持久化



### 在容器内创建一个数据卷

在用 docker run 命令的时候，使用 -v 标记可以在容器内创建一个数据卷。多次使用 -v 标记可以创建多个数据卷。

```shell
# docker inspect 查看容器信息，以下是磁盘挂载，包含两种类型："bind" or "volume"
"Mounts": [
    {
        "Type": "volume", # 容器内数据卷 可以使用 docker run -v ${容器内文件夹绝对路径} 生成容器数据卷，以下数据卷是activemq容器默认的数据卷，用于存放容器内应用产生的日志数据
        "Name": "191d97917ffb20b8e64d969762f5d47a3198ccd74d8d45aa4b9ac4364c6bc8e4",
        "Source": "/var/lib/docker/volumes/191d97917ffb20b8e64d969762f5d47a3198ccd74d8d45aa4b9ac4364c6bc8e4/_data",
        "Destination": "/var/log/activemq",
        "Driver": "local",
        "Mode": "",
        "RW": true,
        "Propagation": ""
    },
    ...
]
```



### 挂载一个主机目录作为数据卷

使用 -v 标记也可以指定挂载一个本地的已有目录到容器中去作为数据卷：

```shell
# docker inspect 查看容器信息，以下是磁盘挂载，包含两种类型："bind" or "volume"
"Mounts": [
    {
        "Type": "bind", # 映射宿主机磁盘目录 docker run -v ${宿主机文件夹绝对路径}:${容器内文件夹绝对路径}:ro 挂载一个宿主机目录作为数据卷（如果目录不存在，Docker 会自动创建（需要考确认docker用户权限））
        # 加了 :ro 之后，容器内挂载的数据卷内的数据就变成只读的了。
        "Source": "/run/desktop/mnt/host/d/WorkSpace/docker/webdata",
        "Destination": "/webdata",
        "Mode": "",
        "RW": true,
        "Propagation": "rprivate"
    }
]
```



## 拓展

### docker run -P/-p

当使用 -P 标记时，Docker 会随机映射一个 `49000~49900` 的端口到内部容器开放的网络端口。
使用 `docker ps` 可以看到，本地主机的 49155 被映射到了容器的 5000 端口。此时访问本机的 49155 端口即可访问容器内 web 应用提供的界面。

-p（小写）则可以指定要映射的IP和端口，但是在一个指定端口上只可以绑定一个容器。

