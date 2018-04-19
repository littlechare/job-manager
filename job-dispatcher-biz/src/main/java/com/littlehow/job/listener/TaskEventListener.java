package com.littlehow.job.listener;

import com.littlehow.job.manager.event.TaskEvent;
import com.littlehow.job.manager.event.TaskEventType;
import com.littlehow.job.service.TaskExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TaskEventListener implements ApplicationListener<TaskEvent> {
    @Autowired
    private TaskExecuteService taskExecuteService;
    @Override
    public void onApplicationEvent(TaskEvent event) {
        TaskEventType type = event.getSource();
        switch (type) {
            case ADD:taskExecuteService.addTask(event.getTask());
                break;
            case UPDATE:taskExecuteService.updateTask(event.getTask());
                break;
            case REMOVE:taskExecuteService.removeTask(event.getTask());
                break;
            default:throw new IllegalStateException("暂时未实现此类型的事件处理");
        }
    }
}
