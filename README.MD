**项目是基于springboot构建,任务回调只支持http(s)方式**

### 一、数据源选择

- 结构化数据库(mysql/oracle等)
- 缓存(redis/memcache等)
- 非结构化数据库(elasticSearch/mongoDb等)
- 配置中心zookeeper等

### 二、说明

**此为简单的任务调度中心系统，任务数据存储于MySql上，只支持http的任务回调**
**没有做高可用处理，但是可以加上监控**

### 三、API说明

**api是以swagger的方式提供http://localhost/swagger-ui.html**

### 四、测试说明

**测试需要启动job-dispatcher-test下的启动springboot**
**测试回调地址为http://localhost:8081/job/test/call**
**可以观察控制台输出信息**

