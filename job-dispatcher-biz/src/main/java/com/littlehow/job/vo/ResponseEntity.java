package com.littlehow.job.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统一的响应报文体
 * 因为使用controllerAdvice统一处理，所以Api配置信息swagger无法展示
 * littlehow 2018/4/6
 */
@ApiModel("响应报文体")
public class ResponseEntity {
    @ApiModelProperty("操作结果信息")
    private String message;
    @ApiModelProperty("操作结果码 0表示成功，其他为失败")
    private String code;
    @ApiModelProperty("操作结果数据")
    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
