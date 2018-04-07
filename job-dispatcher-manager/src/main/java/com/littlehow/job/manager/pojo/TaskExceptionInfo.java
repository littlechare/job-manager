package com.littlehow.job.manager.pojo;

/**
 * 错误集合
 */
public enum TaskExceptionInfo {
    TASK_EXIST("任务已存在", "99"),
    TASK_NOT_EXIST("任务不存在", "98"),
    ONLY_TRIGGER("只有trigger任务类型的支持修改expression", "97")
    ;
    public final String name;
    public final String code;
    TaskExceptionInfo(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
