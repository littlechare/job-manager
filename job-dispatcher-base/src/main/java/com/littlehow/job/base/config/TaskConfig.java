package com.littlehow.job.base.config;


import com.littlehow.job.base.BaseTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TaskConfig {
    /**
     * 存放任务信息
     */
    private static final Map<String, BaseTask> tasks = new HashMap<>();

    /**
     * 获取全部任务列表
     * @return
     */
    public static List<BaseTask> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * 设置任务信息
     * @param baseTask
     */
    public static void addTask(BaseTask baseTask) {
        if (baseTask != null) {
            tasks.put(baseTask.getId(), baseTask);
        }
    }

    /**
     * 判断是否已经存在了定时任务
     * @param taskId
     * @return
     */
    static boolean containsTask(String taskId) {
        return tasks.containsKey(taskId);
    }

    /**
     * 获取单个任务信息
     * @param taskId
     * @return
     */
    static BaseTask getTask(String taskId) {
        return tasks.get(taskId);
    }

    static BaseTask removeTask(String taskId) {
        return tasks.remove(taskId);
    }
}
