-- 默认已经进入root用户
create user 'job' identified by 'job123';
grant all privileges on *.* to 'job'@'%';
flush privileges;
quit;
-- 用新用户进入
mysql -u job -pjob123
-- 创建数据库(因为数简单库，所以字符集不采用utf8mb4)
create database job default character set utf8;

drop table if exists job.task_manager;

/*==============================================================*/
/* Table: task_manager                                          */
/*==============================================================*/
create table job.task_manager
(
   business_id          varchar(16) not null comment '接入业务方',
   task_id              varchar(16) not null comment '任务编号',
   task_type            tinyint unsigned not null comment '任务类型 1.cron 2.trigger 3.fixed 4.fixed_delay',
   expression           varchar(128) not null comment '任务表达式
            类型1,2时格式如:0/5 0 0 * * *?
            类型3,4时格式如:fixed=1000,delay=0
                       ',
   callback_url         varchar(512) not null comment '回调地址',
   create_time          timestamp default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   deleted              bit not null comment '是否删除 0:未删除 1:已删除',
   primary key (task_id, business_id)
);

alter table job.task_manager comment '任务管理表';

drop table if exists job.operation_log;

/*==============================================================*/
/* Table: operation_log                                         */
/*==============================================================*/
create table job.operation_log
(
   id                   int unsigned not null auto_increment comment '日志编号',
   oper_id              varchar(32) not null,
   business_id          varchar(16) not null comment '接入业务编号',
   task_id              varchar(16) not null comment '任务编号',
   oper_type            tinyint unsigned not null comment '操作类型 1:新增 2:删除 3:修改
                       ',
   oper_time            timestamp default CURRENT_TIMESTAMP comment '创建时间',
   primary key (id)
);

alter table job.operation_log comment '操作日志表';



