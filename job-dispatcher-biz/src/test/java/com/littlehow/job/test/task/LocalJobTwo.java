package com.littlehow.job.test.task;

import com.littlehow.job.base.BaseCronTask;
import com.littlehow.job.base.config.TaskType;
import org.springframework.stereotype.Component;

@Component
public class LocalJobTwo extends BaseCronTask {
    public static final String JOB_ID = "local_job_two";
    public LocalJobTwo() {
        super(TaskType.TRIGGER, JOB_ID);
        setExpression("0/5 * * * * *?");
    }
    @Override
    public void run() {
        log.info("local job two, time[" + System.currentTimeMillis() + "]");
    }
}
