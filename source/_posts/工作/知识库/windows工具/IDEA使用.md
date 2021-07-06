# IDEA使用



## 常用插件

1、GenerateAllSetter ：通过alt+enter对变量类生成对类的所有setter方法的调用

2、Maven Helper：帮助你更方便的分析依赖关系

3、Translation：中英文翻译工具

4、aiXcode & codota & Kite ：强大的代码完成器和代码搜索引擎，且帮助您在GitHub上搜索API用例

5、checkStyle & spotBugs(FindBugs的升级版) & sonor：作为静态代码检查插件，可以检查你代码中的隐患，并给出原因。

6、SequenceDiagram：可以根据代码调用链路自动生成时序图

7、GsonFormat：把json格式的内容转成Object的需求

8、AceJump：能够代替鼠标的软件

- https://cloud.tencent.com/developer/article/1343318

9、Java Stream Debugger：流式编程调试插件 

11、lombok

12、**Alibaba Java Coding Guidelines** 

14、junitGenerator V2.0（**定义生成测试case的风格**）

15、background image plus

16、CamelCase：命名风格切换，如驼峰命名切换成下划线分割

17、CodeGlance：在编辑区的右侧显示的代码地图

18、Material Theme UI：主题插件

19、Nyan progress bar：进度条美化

20、Power mode II

21、Rainbow Brackets：彩虹颜色的括号



### 插件网

https://plugins.jetbrains.com/



**配置idea模板**

todo：powerDesigner的常用配置及使用技巧 + idea注释模板配置.pdf



**参考链接**

https://blog.csdn.net/weixin_41846320/article/details/82697818

https://zhuanlan.zhihu.com/p/99354824

https://www.jianshu.com/p/c1f87b0dd597

https://blog.csdn.net/weixin_41846320/article/details/82697818

https://zhuanlan.zhihu.com/p/99354824

https://www.jianshu.com/p/c1f87b0dd597

https://cloud.tencent.com/developer/article/1343318

https://www.zhihu.com/question/20783392



## Settings配置

修改主题
- editor > color scheme 

字体大小
- editor > font

代码补全匹配规则
- editor > General > Code Completion 

包自动导入功能
- editor > auto import > 
  - Add unambiguous imports on the fly：快速添加明确的导入;
  - Optimize imports on the fly：快速优化导入，优化的意思即自动帮助删除无用的导入。

tab页面多行显示的设置
- editor > General > Editor Tabs 

代码注释风格
- editor > Code Style > Java > Code Generation > Comment Code



## VM配置

- Help / Edit Custom VM Options..

默认配置：

```
-Xms128m
-Xmx2016m
-XX:ReservedCodeCacheSize=240m
-XX:+UseConcMarkSweepGC
-XX:SoftRefLRUPolicyMSPerMB=50
-ea
-XX:CICompilerCount=2
-Dsun.io.useCanonPrefixCache=false
-Djdk.http.auth.tunneling.disabledSchemes=""
-XX:+HeapDumpOnOutOfMemoryError
-XX:-OmitStackTraceInFastThrow
-Djdk.attach.allowAttachSelf=true
-Dkotlinx.coroutines.debug=off
-Djdk.module.illegalAccess.silent=true
```

调整配置

```
-server
-Xms4096m
-Xmx4096m
-XX:NewRatio=3
-Xss16m
-XX:+UseConcMarkSweepGC
-XX:+CMSParallelRemarkEnabled
-XX:ConcGCThreads=4
-XX:ReservedCodeCacheSize=2048m
-XX:+AlwaysPreTouch
-XX:+TieredCompilation
-XX:+UseCompressedOops
-XX:SoftRefLRUPolicyMSPerMB=50
-Dsun.io.useCanonCaches=false
-Djava.net.preferIPv4Stack=true
-Djsse.enableSNIExtension=false
-ea
-XX:CICompilerCount=2
-Dsun.io.useCanonPrefixCache=false
-Djdk.http.auth.tunneling.disabledSchemes=""
-XX:+HeapDumpOnOutOfMemoryError
-XX:-OmitStackTraceInFastThrow
-Djdk.attach.allowAttachSelf=true
-Dkotlinx.coroutines.debug=off
-Djdk.module.illegalAccess.silent=true
```



## GIT命令行配置

settings > terminal -> 配置shell path为 ${GIT_HOME}/bin/bash.exe



## HTTP工具

所有HTTP请求需要在后缀为.http的文件中进行，新建一个test.http文件。基本格式为：

```
请求类型(如：GET， POST，PUT) +  请求地址（http://www.baidu.com）
请求头
...

请求体
```



### 参考链接

[IntelliJ IDEA的这个接口调试工具真是太好用了！](https://segmentfault.com/a/1190000021714448)

[IDEA-HTTP工具](https://www.jianshu.com/p/2404654d655a)



## 常用命令

查看方法实现：ctrl + alt + B

查看方法声明：ctrl + U （Super Method）

查看类声明：ctrl + shrift + B



## 注释模板



### 方法注释

`settings -> Editor -> Live templates`

建立模板组 -> 新建模板 -> 设置模板名称为 *

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154737.png)

- 模板内容：

```java
*
 * TODO
 *
 $params$
 * @return {@link $return$}
 * @author huangyuye
 */
```

- Edit variable -> param 配置：

```
groovyScript("if(\"${_1}\".length() == 2) {return '';} else {def result=''; def params=\"${_1}\".replaceAll('[\\\\[|\\\\]|\\\\s]', '').split(',').toList();for(i = 0; i < params.size(); i++) {if(i==0){result+='* @param ' + params[i] + ' TODO '}else{result+='\\n' + ' * @param ' + params[i] + ' TODO '}}; return result;}", methodParameters())
```

- 使用： 方法签名上输入 `/**`+ 补全键

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515154944.png)



### 类注释配置

![img](https://cdn.jsdelivr.net/gh/huangyuye/huangyuye.github.io@data/img/20210515155045.png)



### 参考链接

[idea配置方法注释模板（带参数获取）](https://www.jianshu.com/p/7dd84a9f6597)

