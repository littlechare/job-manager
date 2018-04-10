package com.littlehow.job.manager.pojo;

/**
 * 任务异常
 *littlehow 2018/4/4
 */
public class TaskManagerException extends RuntimeException {
    private String code;

    /**
     * 构造不爬栈的异常
     * 去掉之前的重写fillInStackTrace
     * littlehow update 2018/4/10
     * @see Throwable#Throwable(String, Throwable, boolean, boolean)
     * @param exceptionInfo
     */
    public TaskManagerException(TaskExceptionInfo exceptionInfo) {
        super(exceptionInfo.name, null, true, false);
        this.code = exceptionInfo.code;
    }

    public String getCode() {
        return code;
    }
}
