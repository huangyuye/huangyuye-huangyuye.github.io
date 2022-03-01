## 概念

1. HSSF (Horrible SpreadSheet Format（电子表格格式）的缩写)
   1. 对应.xls 文件，兼容 Office97-2003 版本 ）（一个sheet最大行数65536，最大列数256）
2. XSSF
   1. 容 Office2007及以后版本 （一个sheet最大行数1048576，最大列数16384）
3. **SXSSF** （低内存占用的操作方式）
   1. SXSSFWorkbook w3= new SXSSFWorkbook(100);//内存中保留100条数据，其余写入硬盘临时文件



## 文档结构类

HSSFWorkbook excel文档对象
HSSFSheet excel的sheet 
HSSFRow excel的行
HSSFCell excel的单元格 
HSSFFont excel字体
HSSFName 名称 
HSSFDataFormat 日期格式
HSSFHeader sheet头
HSSFFooter sheet尾
HSSFCellStyle cell样式
HSSFDateUtil 日期
HSSFPrintSetup 打印
HSSFErrorConstants 错误信息表

### POI

### easyExcel

https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java

### easyPoi

https://gitee.com/lemur/easypoi





