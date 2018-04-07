package com.littlehow.job.vo;

import com.littlehow.job.manager.pojo.TaskType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "任务类型", description = "code=1,2为表达式任务, code=3,4为固定频率任务")
public class TaskTypeVo {
    @ApiModelProperty("任务类型名称")
    private String name;
    @ApiModelProperty("任务类型对应代码")
    private Byte code;

    public TaskTypeVo(TaskType taskType) {
        name = taskType.name();
        code = taskType.v;
    }

    public String getName() {
        return name;
    }

    public Byte getCode() {
        return code;
    }
}
