package com.littlehow.job.manager.api;

import com.littlehow.job.manager.pojo.TaskDto;

import java.util.List;

/**
 * 任务调度管理接口
 * littlehow 2018/4/4
 */
public interface TaskManagerService {
    /**
     * 新增任务
     * 可能抛出任务已存在异常
     * @param taskDto
     * @return
     */
    boolean addTask(TaskDto taskDto);

    /**
     * 修改任务
     * 可能抛出任务不存在异常
     * @param taskDto
     * @return
     */
    boolean update(TaskDto taskDto);

    /**
     * 删除任务
     * 可能抛出任务不存在异常
     * @param taskDto
     * @return
     */
    boolean removeTask(TaskDto taskDto);

    /**
     * 按业务方查询任务
     * 无任务返回空list
     * @param businessId
     * @return
     */
    List<TaskDto> selectByBusinessId(String businessId);

    /**
     * 获取所有任务
     * 无任务返回空list
     * @return
     */
    List<TaskDto> selectAll();

    /**
     * 根据任务唯一标识获取任务信息
     * 无任务返回null
     * @param businessId
     * @param taskId
     * @return
     */
    TaskDto getTaskByIdentified(String businessId, String taskId);
}
