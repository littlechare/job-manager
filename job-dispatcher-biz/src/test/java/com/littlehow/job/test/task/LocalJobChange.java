package com.littlehow.job.test.task;

import com.littlehow.job.base.ScheduleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Lazy(false)
public class LocalJobChange {
    @Autowired
    private ScheduleBaseService service;
    public LocalJobChange() {
        new Thread(() -> {
            //30秒后改变任务执行频率
            try {
                TimeUnit.SECONDS.sleep(30);
                service.changeTask(LocalJobTwo.JOB_ID, "0/12 * * * * *?");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
