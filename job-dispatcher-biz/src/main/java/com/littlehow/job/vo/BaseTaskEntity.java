package com.littlehow.job.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

@ApiModel("任务基本实体")
public class BaseTaskEntity {
    @ApiModelProperty("任务唯一编号")
    @Length(min = 6, max = 16)
    private String taskId;
    @ApiModelProperty("操作者编号[业务方的操作者]")
    @Length(min = 4, max = 32)
    private String operId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }
}
