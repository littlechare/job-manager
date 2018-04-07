package com.littlehow.job.manager.pojo;

/**
 * 任务异常
 *littlehow 2018/4/4
 */
public class TaskManagerException extends RuntimeException {
    private String code;
    public TaskManagerException(TaskExceptionInfo exceptionInfo) {
        super(exceptionInfo.name);
        this.code = exceptionInfo.code;
    }

    /**
     * 去掉异常堆栈信息
     * @return
     */
    @Override
    public TaskManagerException fillInStackTrace() {
        return this;
    }

    public String getCode() {
        return code;
    }
}
