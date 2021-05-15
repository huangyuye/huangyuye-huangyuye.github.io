## Arthas 线上环境排查工具



### 使用实例

1. 工具安装

```shell
# 替代方案：wget https://alibaba.github.io/arthas/arthas-boot.jar
apt-get update && apt-get install curl && curl -O https://arthas.aliyun.com/arthas-boot.jar
```

2. 启动工具

```shell
java -jar  arthas-boot.jar
```

3. 相关命令

   - 方法参数|响应监听

   ```shell
   # '{params, returnObj}':ognl观察表达式() -f: 方法结束监听 || -x: 指定遍历深度 
   watch [path] [method] '{params, returnObj}'  -f -x 7 
   ```

   - 调用springBean方法：

   ```shell
   ## 执行tt命令来记录RequestMappingHandlerAdapter#invokeHandlerMethod的请求，找到对应index
   tt -t org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter invokeHandlerMethod
   
   ## 指定对应的index并调用springBean方法（tt -i 用于查看调用信息）
   tt -i 1020 -w 'target.getApplicationContext().getBean("cacheManager").getCache("cache").put("WEWORK_ECRP_GROUP_ID_ww97d386b1d60bea91", 80000016L)'
   ```

4. 一键执行

```shell
## 工具下载&运行
apt-get update && apt-get install curl && curl -O https://arthas.aliyun.com/arthas-boot.jar && java -jar  arthas-boot.jar
## 方法监听
watch [path] [method] '{params, returnObj}'  -f -x 7 
```



### 参考文档

[ Arthas用户文档 ]: https://arthas.aliyun.com/doc/
[Alibaba Arthas实践]: https://github.com/alibaba/arthas/issues/482
