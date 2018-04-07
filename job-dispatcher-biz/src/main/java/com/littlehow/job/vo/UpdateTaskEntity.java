package com.littlehow.job.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "修改任务实体", description = "当expression不为空时，任务类型必须为trigger，否则将抛出异常")
public class UpdateTaskEntity extends BaseTaskEntity {
    @ApiModelProperty(value = "回调地址:http和https都支持, 如果为空，则表示不更新回调")
    private String callbackUrl;

    @ApiModelProperty(value = "任务执行表达式:1,2[0/5 0 0 * * *?] 3,4[fixed=1000,delay=0], 如果为空则表示不更新表达式")
    private String expression;

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
