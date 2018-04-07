###一、数据源选择
- 结构化数据库(mysql/oracle等)
- 缓存(redis/memcache等)
- 非结构化数据库(elasticSearch/mongoDb等)
- 配置中心zookeeper等
###二、说明
**此为简单的任务调度中心系统，只支持http的任务回调**
**没有做高可用处理，但是可以加上监控**
**这里使用mysql进行任务存储，因为本人不习惯
<br>使用mybatis自动生成的信息。所以自己文件都是自己写的**

