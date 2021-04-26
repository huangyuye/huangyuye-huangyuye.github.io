---
categories:
  - Java
  - 知识库
  - Java
---
# 默认的equals与hashcode

1. equals：判断两个对象的引用指向的是不是同一个对象
2. hashcode：根据对象地址生成一个整数数值；



## 重写场景

需求：若对象间特定的几个属性值相等，则判定对象相等

问题：默认的equals和hashcode判定的是对象的引用，两个new出来的对象引用一定不同

解决方案：重写equals方法和hashcode方法



只重写equals方法的话会出现对象equals相等但是hashcode不等的情况（hashcode默认判断引用地址），放到hashSet中也有存在两个对象。

HashMap 则是先根据Key值的hashcode分配和获取对象保存数组下标的，然后再根据equals区分唯一值。也存在一样的问题。



## 问题

为什么重写equals时必须重写hashCode方法？



前提：

**如果两个对象相等，则hashcode一定也是相同的；**
**两个对象相等,对两个对象分别调用equals方法都返回true；**



两个对象有相同的hashcode值，它们也不一定是相等的；（如HashMap）

因此，equals 方法被覆盖过，则 hashCode 方法也必须被覆盖；
hashCode() 的**默认行为是对堆上的对象产生独特值**。如果没有重写 hashCode()，则该 class 的两个对象无论如何都不会相等（即使这两个对象指向相同的数据）；



https://juejin.im/post/6844903854639693837#heading-3