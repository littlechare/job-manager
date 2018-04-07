package com.littlehow.job.service;

import com.littlehow.job.base.BaseCronTask;
import com.littlehow.job.base.BaseFixedTask;
import com.littlehow.job.base.ScheduleBaseService;
import com.littlehow.job.manager.pojo.TaskDto;
import com.littlehow.job.manager.pojo.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaskExecuteService {
    @Autowired
    private ScheduleBaseService scheduleBaseService;
    @Autowired
    private CallbackExecuteService executeService;
    private static final Pattern fixed = Pattern.compile("fixed=(\\d+)\\s*(,\\s*delay=(\\d+))?");

    /**
     * 新增任务
     * 因表达式解析错误可能会抛出异常
     * @param taskDto
     */
    public void addTask(TaskDto taskDto) {
        if (taskDto == null) {
            return;
        }
        String taskId = getTaskId(taskDto);
        CallbackExecuteService.addOrUpdateTask(taskId, taskDto.getCallbackUrl());
        TaskType taskType = taskDto.getTaskType();
        switch (taskType) {
            case FIXED:
            case FIXED_DELAY:
                long[] params = getFixedParameters(taskDto.getExpression());
                BaseFixedTask baseFixedTask = new BaseFixedTask(taskId,
                        params[0], params[1]) {
                    @Override
                    public void run() {
                        executeService.execute(this.getId());
                    }
                };
                scheduleBaseService.addTask(baseFixedTask);
                break;
            case CRON:
            case TRIGGER:
                BaseCronTask baseCronTask = new BaseCronTask(taskType == TaskType.CRON ?
                com.littlehow.job.base.config.TaskType.CRON :
                com.littlehow.job.base.config.TaskType.TRIGGER, taskId) {
                    @Override
                    public void run() {
                        executeService.execute(this.getId());
                    }
                };
                baseCronTask.setExpression(taskDto.getExpression());
                scheduleBaseService.addTask(baseCronTask);
        }
    }

    /**
     * 修改任务
     * @param taskDto
     */
    public void updateTask(TaskDto taskDto) {
        if (!StringUtils.isEmpty(taskDto.getExpression())) {
            //修改表达式
            scheduleBaseService.changeTask(getTaskId(taskDto), taskDto.getExpression());
        }
        if (!StringUtils.isEmpty(taskDto.getCallbackUrl())) {
            //修改回调地址
            CallbackExecuteService.addOrUpdateTask(getTaskId(taskDto), taskDto.getCallbackUrl());
        }
    }

    /**
     * 删除任务
     * @param taskDto
     */
    public void removeTask(TaskDto taskDto) {
        String taskId = getTaskId(taskDto);
        scheduleBaseService.removeTask(taskId);
        CallbackExecuteService.removeTask(taskId);
    }

    /**
     * 获取fixed类型的实际参数信息
     * @param expression
     * @return
     */
    private static long[] getFixedParameters(String expression) {
        Matcher matcher = fixed.matcher(expression);
        if (matcher.find()) {
            long[] fixedParam = {0L, 0L};
            fixedParam[0] = Long.parseLong(matcher.group(1));
            String delay = matcher.group(3);
            if (delay != null) {
                fixedParam[1] = Long.parseLong(delay);
            }
            return fixedParam;
        }
        throw new IllegalArgumentException("invalid expression:" +expression);
    }

    /**
     * 获取任务编号
     * @param task
     * @return
     */
    private String getTaskId(TaskDto task) {
        return task.getBusinessId() + "-" + task.getTaskId();
    }
}
