package com.littlehow.job.manager.pojo;

/**
 * 任务类型
 * littlehow 2018/4/4
 */
public enum TaskType {
    CRON((byte)1),
    TRIGGER((byte)2),
    FIXED((byte)3),
    FIXED_DELAY((byte)4)
    ;
    public final byte v;

    TaskType(byte v) {
        this.v = v;
    }

    public static TaskType valueOf(byte v) {
        switch (v) {
            case 1 : return CRON;
            case 2 : return TRIGGER;
            case 3 : return FIXED;
            case 4 : return FIXED_DELAY;
            default: throw new IllegalArgumentException("没有值为[" + v + "]的TaskType枚举类型");
        }
    }
}
