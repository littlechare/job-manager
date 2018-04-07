package com.littlehow.job.test.task;

import com.littlehow.job.base.BaseFixedTask;
import com.littlehow.job.base.config.TaskType;
import org.springframework.stereotype.Component;

@Component
public class LocalJobOne extends BaseFixedTask {
    public static final String JOB_ID = "local_job_one";
    public LocalJobOne() {
        //每30秒执行一次
        super(JOB_ID, 10000, 0);
    }

    @Override
    public void run() {
        log.info("local job one, time[" + System.currentTimeMillis() + "]");
    }
}
