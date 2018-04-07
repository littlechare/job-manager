###一、数据源选择
- 结构化数据库(mysql/oracle等)
- 缓存(redis/memcache等)
- 非结构化数据库(elasticSearch/mongoDb等)
- 配置中心zookeeper等

<br>

###二、说明
**此为简单的任务调度中心系统，任务数据存储于MySql上，只支持http的任务回调**
**没有做高可用处理，但是可以加上监控**

###三、测试说明
**测试回调地址为http://localhost:8081/job/test/call**
**可以观察控制台输出信息**

