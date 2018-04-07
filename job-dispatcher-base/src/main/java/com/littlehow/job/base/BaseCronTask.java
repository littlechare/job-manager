package com.littlehow.job.base;


import com.littlehow.job.base.config.TaskType;

public abstract class BaseCronTask extends BaseTask {
    private String expression;
    public BaseCronTask(TaskType taskType, String id) {
        super(taskType, id);
    }

    @Override
    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    public final long interval() {
        return 0L;
    }

    public final long delay() {
        return 0L;
    }
}
