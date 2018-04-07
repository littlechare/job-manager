package com.littlehow.job.vo;

import com.littlehow.job.manager.pojo.TaskDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 表现层实体
 * littlehow 2018/4/6
 */
@ApiModel(value = "任务信息返回", description = "回执任务的详细信息")
public class TaskVo {
    @ApiModelProperty("业务编号")
    private String businessId;
    @ApiModelProperty("任务编号")
    private String taskId;
    @ApiModelProperty("任务类型")
    private TaskTypeVo taskType;
    @ApiModelProperty("任务表达式")
    private String expression;
    @ApiModelProperty("任务回调地址")
    private String callbackUrl;
    @ApiModelProperty("任务创建时间,毫秒时间戳")
    private long createTime;
    @ApiModelProperty("任务最后修改时间,毫秒时间戳")
    private long updateTime;

    public TaskVo(TaskDto taskDto) {
        businessId = taskDto.getBusinessId();
        taskId = taskDto.getTaskId();
        taskType = new TaskTypeVo(taskDto.getTaskType());
        expression = taskDto.getExpression();
        callbackUrl = taskDto.getCallbackUrl();
        createTime = taskDto.getCreateTime();
        updateTime = taskDto.getUpdateTime();
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskTypeVo getTaskType() {
        return taskType;
    }

    public String getExpression() {
        return expression;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }
}
