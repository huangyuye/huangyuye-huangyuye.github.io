**日常使用可直接跳转至**：[常用技巧](##常用技巧)



## 概述

tar命令：可以为linux的文件和目录创建档案



关于打包和压缩：打包是指将一大堆文件或目录变成一个总的文件；压缩则是将一个大的文件通过一些压缩算法变成一个小文件。



关于压缩：

1、因为Linux中很多压缩程序只能针对一个文件进行压缩，这样当你想要压缩一大堆文件时，你得先将这一大堆文件先打成一个包（tar命令），然后再用压缩程序进行压缩（[gzip](http://man.linuxde.net/gzip) [bzip2](http://man.linuxde.net/bzip2)命令）

2、.bz2和.gz的区别在于，前者比后者压缩率更高，后者比前者花费更少的时间



## tar、jar、war 三者之间的区别

**tar**：unix下的打包工具

jar：即Java Archive，Java的包

war：Web application Archive，与jar基本相同，但它通常表示这是一个Java的Web应用程序的包



关于zip命令：zip和unzip命令主要用于处理zip包，但是我们也可以用unzip去解压jar包。



## 语法

```shell
tar(选项)(参数)
```



## 选项

```
-A或--catenate：新增文件到以存在的备份文件；
-B：设置区块大小；
-c或--create：建立新的备份文件；
-C <目录>：这个选项用在解压缩，若要在特定目录解压缩，可以使用这个选项。
-d：记录文件的差别；
-x或--extract或--get：从备份文件中还原文件；
-t或--list：列出备份文件的内容；
-z或--gzip或--ungzip：通过gzip指令处理备份文件；
-Z或--compress或--uncompress：通过compress指令处理备份文件；
-f<备份文件>或--file=<备份文件>：指定备份文件；
-v或--verbose：显示指令执行过程；
-r：添加文件到已经压缩的文件；
-u：添加改变了和现有的文件到已经存在的压缩文件；
-j：支持bzip2解压文件；
-v：显示操作过程；
-l：文件系统边界设置；
-k：保留原有文件不覆盖；
-m：保留文件不被覆盖；
-w：确认压缩文件的正确性；
-p或--same-permissions：用原来的文件权限还原文件；
-P或--absolute-names：文件名使用绝对名称，不移除文件名称前的“/”号；
-N <日期格式> 或 --newer=<日期时间>：只将较指定日期更新的文件保存到备份文件里；
--exclude=<范本样式>：排除符合范本样式的文件。
```



## 常用技巧



```shell
# tar <选项> <打包名称> <目标文件>
# 选项—— v：verbose查看详细信息； f：file指定文件
# 理论上 vf 选项必加
# gzip压缩后的包添加.gzip后缀；bzip2压缩后的包添加.bz2后缀
```



### 打包/压缩

```shell
# 选项—— z：通过gzip指令处理备份文件； c：create；v：verbose查看详细信息； f：file指定文件

# 最简单的命令：仅打包，不压缩！ 
tar -cvf filename.tar <file>
# 打包后，以 gzip 压缩; 也可以使用bzip2压缩(选项z改为j)
tar -zcvf filename.tar.gz <file>
```



### 查看

```shell
# 选项—— z：通过gzip指令处理备份文件； t：list；v：verbose查看详细信息； f：file指定文件
tar -ztvf filename.tar.gz
```



### 解压缩

```shell
# 选项—— z：通过gzip指令处理备份文件； x：--extract；v：verbose查看详细信息； f：file指定文件 -C：指定目录
tar -zxvf filename.tar.gz -C <path>
```



### war包

```shell
# 使用zip/unzip命令或jar(jdk)命令压缩和解压缩文件
# 选项—— -o override 解压时覆盖已经存在的文件，并且不要求用户确认;-n 解压时不覆盖已经存在的文件;-v 查看压缩文件的详细信息,但不解压;
unzip project.war -d <path>
jar -xvf project.war # 不能指定解压目录
```



### 最常用记忆

```shell
压　缩：tar -jcv -f filename.tar.bz2 <要被压缩的文件或目录名称>
查　询：tar -jtv -f filename.tar.bz2
解压缩：tar -jxv -f filename.tar.bz2 -C <欲解压缩的目录>
```



## 参考链接

- https://man.linuxde.net/tar

