package com.littlehow.job.base;

import com.littlehow.job.base.config.ScheduleTaskConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleBaseService {
    @Autowired
    private ScheduleTaskConfig config;

    /**
     * 新增任务
     * @param task
     * @return
     */
    public String addTask(BaseTask task) {
        return config.addTask(task);
    }

    /**
     * 修改任务执行频率等
     * @param taskId
     * @param expression
     * @return
     */
    public String changeTask(String taskId, String expression) {
        return config.changeTask(taskId, expression);
    }

    /**
     * 删除任务
     * @param taskId
     * @return
     */
    public String removeTask(String taskId) {
        return config.cancelTask(taskId);
    }
}
