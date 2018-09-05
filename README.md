# table-create

### 创建本项目的初衷是为了解决 Mybatis 无法像 hibernate 一样自动建表
https://github.com/newShiJ/creat-table-Demo 是如何使用该工具的一个使用demo

master 分支是为了测试代码功能的    
jar 分支是真正打包的代码，删除了一些无关代码

### 不足之处
*	目前只支持 Mysql 数据库
* 	Mysql 数据库中仅支持 varchar，bigint ，int ，double，float 等常用类型对应的java类型
*  需要与 Spring 进行集成，因为使用的初衷就是为了项目启动时自动建表，所以使用了 Spring 的一些 API
*  目前支持 update 级别的自动建表，不支持删除列，仅支持添加列
