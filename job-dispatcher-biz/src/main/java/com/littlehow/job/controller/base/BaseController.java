package com.littlehow.job.controller.base;

import com.littlehow.job.manager.pojo.TaskDto;
import com.littlehow.job.vo.BaseTaskEntity;

import static com.littlehow.job.interceptor.BusinessContext.getBusinessId;

/**
 * 基础controller
 * littlehow 2018/4/6
 */
public class BaseController {
    /**
     * 获取业务的businessId
     * @return
     */
    protected String businessId() {
        return getBusinessId();
    }

    /**
     * 初始化taskDto
     * @param taskId 任务编号和业务编号组合成唯一标识
     * @return
     */
    protected TaskDto initTaskDto(String taskId) {
        TaskDto taskDto = new TaskDto();
        taskDto.setBusinessId(businessId());
        taskDto.setTaskId(taskId);
        return taskDto;
    }

    /**
     * 初始化taskDto
     * @param taskEntity
     * @return
     */
    protected TaskDto initTaskDto(BaseTaskEntity taskEntity) {
        TaskDto taskDto = initTaskDto(taskEntity.getTaskId());
        taskDto.setOperId(taskEntity.getOperId());
        return taskDto;
    }
}
