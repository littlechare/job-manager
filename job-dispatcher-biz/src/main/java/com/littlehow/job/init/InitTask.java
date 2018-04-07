package com.littlehow.job.init;

import com.littlehow.job.manager.api.TaskManagerService;
import com.littlehow.job.manager.pojo.TaskDto;
import com.littlehow.job.service.TaskExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * spring容器初始化完成后，开始初始化任务
 * 容器重启后，原来内存中的任务被销毁，需要再次从
 * 持久化工具中拉取加载如内存
 * littlehow 2018/4/7
 */
@Component
public class InitTask implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private TaskManagerService taskManagerService;
    @Autowired
    private TaskExecuteService taskExecuteService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<TaskDto> taskDtos = taskManagerService.selectAll();
        if (!CollectionUtils.isEmpty(taskDtos)) {
            taskDtos.forEach(task -> taskExecuteService.addTask(task));
        }
    }
}
