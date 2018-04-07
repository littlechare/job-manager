package com.littlehow.job.manager.event;

import com.littlehow.job.manager.pojo.TaskDto;
import org.springframework.context.ApplicationEvent;


/**
 * 任务改变通知事件
 * littlehow 2018/4/7
 */
public class TaskEvent extends ApplicationEvent {
    private TaskDto task;
    /**
     * 任务改变事件
     * @param source
     */
    public TaskEvent(TaskEventType source, TaskDto task) {
        super(source);
        this.task = task;
    }

    @Override
    public TaskEventType getSource() {
        return (TaskEventType) source;
    }

    /**
     * 获取事件需要处理的任务
     * @return
     */
    public TaskDto getTask() {
        return task;
    }
}
