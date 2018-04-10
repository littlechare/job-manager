package com.littlehow.job.vo;


/**
 * 用于参数异常处理
 * littlehow 2018/4/6
 */
public class ArgumentsException extends RuntimeException {
    /**
     * 构造不爬栈的异常
     * 去掉之前的重写fillInStackTrace
     * littlehow update 2018/4/10
     * @see Throwable#Throwable(String, Throwable, boolean, boolean)
     * @param message
     */
    public ArgumentsException(String message) {
        super(message, null, true, false);
    }

}
