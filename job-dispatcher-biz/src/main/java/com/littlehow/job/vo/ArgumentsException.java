package com.littlehow.job.vo;


/**
 * 用于参数异常处理
 * littlehow 2018/4/6
 */
public class ArgumentsException extends IllegalArgumentException {
    public ArgumentsException(String message) {
        super(message);
    }

    /**
     * 去掉异常堆栈信息
     * @return
     */
    @Override
    public ArgumentsException fillInStackTrace() {
        return this;
    }
}
