package com.littlehow.job.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

/**
 * 新增任务校验实体
 * littlehow 2018/4/6
 */
@ApiModel(value = "新增任务实体", description = "新增任务时需要该实体内参数全部不为空")
public class AddTaskEntity extends BaseTaskEntity {
    @ApiModelProperty(value = "任务类型:1:cron 2:trigger 3:fixed 4:fixed_delay")
    @Range(min = 1, max = 4)
    private Byte taskType;
    @ApiModelProperty(value = "回调地址:http和https都支持")
    @Pattern(regexp = "^http(s)?://.+$")
    private String callbackUrl;
    @ApiModelProperty(value = "任务执行表达式:1,2[0/5 0 0 * * *?] 3,4[fixed=1000,delay=0]")
    @NotBlank
    private String expression;

    public Byte getTaskType() {
        return taskType;
    }

    public void setTaskType(Byte taskType) {
        this.taskType = taskType;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
