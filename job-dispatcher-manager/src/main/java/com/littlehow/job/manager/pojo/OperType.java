package com.littlehow.job.manager.pojo;

/**
 * 操作类型
 * littlehow 2018/4/4
 */
public enum OperType {
    ADD((byte)1),
    DELETE((byte)2),
    UPDATE((byte)3)
    ;
    public final Byte v;
    OperType(Byte v) {
        this.v = v;
    }
}
