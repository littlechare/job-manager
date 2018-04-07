package com.littlehow.job.manager.mapper.job;

import com.littlehow.job.manager.model.job.TaskManager;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务管理mapper
 * littlehow 2018/4/4
 */
public interface TaskManagerMapper {
    /**
     * 新增任务
     * @param taskManager
     * @return
     */
    int insertTask(TaskManager taskManager);

    /**
     * 根据业务方编号，列出业务方当前有效的任务信息
     * @param businessId
     * @return
     */
    List<TaskManager> selectByBusinessId(@Param("businessId") String businessId);

    /**
     * 根据主键获取任务信息
     * @param businessId
     * @param taskId
     * @return
     */
    TaskManager selectByPrimaryKey(@Param("businessId") String businessId,
                                   @Param("taskId") String taskId);

    /**
     * 获取任务系统的所有任务
     * @return
     */
    List<TaskManager> selectAll();

    /**
     * 修改任务信息
     * @param taskManager
     * @return
     */
    int changeTaskByPrimaryKey(TaskManager taskManager);

    /**
     * 删除任务信息
     * @param businessId
     * @param taskId
     * @return
     */
    int deleteTaskByPrimaryKey(@Param("businessId") String businessId,
                               @Param("taskId") String taskId);
}