package com.littlehow.job.base;


import com.littlehow.job.base.config.TaskType;

public abstract class BaseFixedTask extends BaseTask {
    private final long interval;
    private final long delay;

    public BaseFixedTask(String id, long interval, long delay) {
        super(delay <= 0L ? TaskType.FIXED_RATE : TaskType.FIXED_DELAY, id);
        this.interval = interval;
        this.delay = delay;
    }

    @Override
    public long interval() {
        return interval;
    }

    @Override
    public long delay() {
        return delay;
    }

    @Override
    public final String getExpression() {
        return null;
    }

    @Override
    public final void setExpression(String expression) {

    }
}
