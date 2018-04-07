package com.littlehow.job.test.task;

import com.littlehow.job.base.ScheduleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Lazy(false)
public class LocalJobCancel {
    @Autowired
    private ScheduleBaseService service;
    public LocalJobCancel() {
        new Thread(() -> {
            //一分钟后取消任务一
            try {
                TimeUnit.MINUTES.sleep(1);
                service.removeTask(LocalJobOne.JOB_ID);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            //二分钟后取消任务二
            try {
                TimeUnit.MINUTES.sleep(2);
                service.removeTask(LocalJobTwo.JOB_ID);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
