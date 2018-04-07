package com.littlehow.job.controller;

import com.littlehow.job.controller.base.BaseController;
import com.littlehow.job.manager.api.TaskManagerService;
import com.littlehow.job.manager.pojo.TaskDto;
import com.littlehow.job.manager.pojo.TaskType;
import com.littlehow.job.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务管理controller
 * littlehow 2018/4/4
 */
@Api("任务管理接口")
@RestController
@RequestMapping("/task/api")
public class TaskManagerController extends BaseController {
    @Autowired
    private TaskManagerService taskManagerService;
    /**
     * 新增调度任务
     * littlehow 2018/4/6
     * @return
     */
    @ApiOperation(value = "添加定时任务接口", notes = "任务类型说明:1:cron基本表达式 " +
            "2:trigger表达式并且支持动态修改 3:fixed固定频率 4:fixed_delay固定频率，延迟多久开始执行")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addTask(@Valid @RequestBody @ApiParam("定时任务实体") AddTaskEntity addTaskEntity) {
        TaskDto taskDto = initTaskDto(addTaskEntity);
        taskDto.setCallbackUrl(addTaskEntity.getCallbackUrl());
        taskDto.setExpression(addTaskEntity.getExpression());
        taskDto.setTaskType(TaskType.valueOf(addTaskEntity.getTaskType()));
        return taskManagerService.addTask(taskDto);
    }

    @ApiOperation(value = "修改定时任务", notes = "可以修改expression和callbackUrl，为空表示不修改, 当expression不为空时，任务类型必须为trigger")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public boolean updateTask(@Valid @RequestBody @ApiParam("定时任务实体")UpdateTaskEntity updateTaskEntity) {
        TaskDto taskDto = initTaskDto(updateTaskEntity);
        String expression = updateTaskEntity.getExpression();
        String callbackUrl = updateTaskEntity.getCallbackUrl();
        validateUpdateParams(expression, callbackUrl);
        taskDto.setExpression(expression);
        taskDto.setCallbackUrl(callbackUrl);
        return taskManagerService.update(taskDto);
    }

    @ApiOperation("删除定时任务")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public boolean removeTask(@RequestBody @ApiParam("定时任务实体")BaseTaskEntity baseTaskEntity) {
        TaskDto taskDto = initTaskDto(baseTaskEntity);
        return taskManagerService.removeTask(taskDto);
    }

    @ApiOperation("查询定时任务")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<TaskVo> getTask() {
        List<TaskDto> taskDtos = taskManagerService.selectByBusinessId(businessId());
        if (CollectionUtils.isEmpty(taskDtos)) {
            return new ArrayList<>();
        }
        return taskDtos.stream().map(TaskVo :: new).collect(Collectors.toList());
    }

    @ApiOperation("查询指定定时任务")
    @ApiImplicitParam(name = "taskId", value = "定时任务编号")
    @RequestMapping(value = "/get/{taskId}", method = RequestMethod.GET)
    public TaskVo getTask(@PathVariable("taskId") String taskId) {
        TaskDto taskDto = taskManagerService.getTaskByIdentified(businessId(), taskId);
        if (taskDto == null) {
            return null;
        }
        return new TaskVo(taskDto);
    }

    /**
     * 校验修改的参数
     * @param expression
     * @param callbackUrl
     */
    private void validateUpdateParams(String expression, String callbackUrl) {
        if (expression == null && callbackUrl == null) {
            throw new ArgumentsException("expression和callbackUrl不可同时为null");
        }
        //简单校验
        if (expression != null && expression.split(" ").length != 6) {
            throw new ArgumentsException("expression必须有6个参数");
        }

        if (callbackUrl != null && !callbackUrl.startsWith("http")) {
            throw new ArgumentsException("回调地址只支持http(s)请求");
        }
    }
}
