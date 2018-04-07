package com.littlehow.job.manager.support;

import com.littlehow.job.manager.api.TaskManagerService;
import com.littlehow.job.manager.event.TaskEvent;
import com.littlehow.job.manager.event.TaskEventType;
import com.littlehow.job.manager.mapper.job.OperationLogMapper;
import com.littlehow.job.manager.mapper.job.TaskManagerMapper;
import com.littlehow.job.manager.model.job.OperationLog;
import com.littlehow.job.manager.model.job.TaskManager;
import com.littlehow.job.manager.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MysqlTaskManagerSupport implements TaskManagerService {
    @Autowired
    private TaskManagerMapper taskManagerMapper;
    @Autowired
    private OperationLogMapper operationLogMapper;
    @Autowired
    private ApplicationContext applicationContext;

    @Transactional
    @Override
    public boolean addTask(TaskDto taskDto) {
        //查询任务是否存在
        TaskManager taskManager = taskManagerMapper.selectByPrimaryKey(taskDto.getBusinessId(), taskDto.getTaskId());
        if (taskManager != null) {
            throw new TaskManagerException(TaskExceptionInfo.TASK_EXIST);
        }
        taskManager = wrapManager(taskDto);
        //插入任务信息
        int count = taskManagerMapper.insertTask(taskManager);
        //插入日志信息
        count += writeLog(taskDto.getBusinessId(), taskDto.getTaskId(),
                taskDto.getOperId(), OperType.ADD);
        applicationContext.publishEvent(new TaskEvent(TaskEventType.ADD, taskDto));
        return count == 2;
    }

    @Transactional
    @Override
    public boolean update(TaskDto taskDto) {
        //查询任务是否存在
        TaskManager taskManager = taskManagerMapper.selectByPrimaryKey(taskDto.getBusinessId(), taskDto.getTaskId());
        if (taskManager == null) {
            throw new TaskManagerException(TaskExceptionInfo.TASK_NOT_EXIST);
        }
        if (!StringUtils.isEmpty(taskDto.getExpression())) {
            //只有TaskType.TRIGGER类型的才能修改expression
            if (TaskType.valueOf(taskManager.getTaskType()) != TaskType.TRIGGER) {
                throw new TaskManagerException(TaskExceptionInfo.ONLY_TRIGGER);
            }
            taskManager.setExpression(taskDto.getExpression());
        }
        if (!StringUtils.isEmpty(taskDto.getCallbackUrl())) {
            taskManager.setCallbackUrl(taskDto.getCallbackUrl());
        }
        //修改任务
        int count = taskManagerMapper.changeTaskByPrimaryKey(taskManager);
        //插入日志
        count += writeLog(taskDto.getBusinessId(), taskDto.getTaskId(),
                taskDto.getOperId(), OperType.UPDATE);
        applicationContext.publishEvent(new TaskEvent(TaskEventType.UPDATE, taskDto));
        return count == 2;
    }

    @Transactional
    @Override
    public boolean removeTask(TaskDto taskDto) {
        //查询任务是否存在
        TaskManager taskManager = taskManagerMapper.selectByPrimaryKey(taskDto.getBusinessId(), taskDto.getTaskId());
        if (taskManager == null) {
            throw new TaskManagerException(TaskExceptionInfo.TASK_NOT_EXIST);
        }
        //删除任务
        int count = taskManagerMapper.deleteTaskByPrimaryKey(taskDto.getBusinessId(), taskDto.getTaskId());
        count += writeLog(taskDto.getBusinessId(), taskDto.getTaskId(),
                taskDto.getOperId(), OperType.DELETE);
        applicationContext.publishEvent(new TaskEvent(TaskEventType.REMOVE, taskDto));
        return count == 2;
    }

    @Override
    public List<TaskDto> selectByBusinessId(String businessId) {
        return wrapDtoList(taskManagerMapper.selectByBusinessId(businessId));
    }

    @Override
    public List<TaskDto> selectAll() {
        return wrapDtoList(taskManagerMapper.selectAll());
    }

    @Override
    public TaskDto getTaskByIdentified(String businessId, String taskId) {
        return wrapDto(taskManagerMapper.selectByPrimaryKey(businessId, taskId));
    }

    /**
     * 写入操作日志
     * @param businessId
     * @param taskId
     * @param operId
     * @param operType
     */
    private int writeLog(String businessId, String taskId, String operId, OperType operType) {
        OperationLog operationLog = new OperationLog();
        operationLog.setOperId(operId);
        operationLog.setOperType(operType.v);
        operationLog.setTaskId(taskId);
        operationLog.setBusinessId(businessId);
        return operationLogMapper.insertLog(operationLog);
    }

    /**
     * 将数据库模型转换为传输模型
     * @param taskManager
     * @return
     */
    private static TaskDto wrapDto(TaskManager taskManager) {
        if (taskManager == null) {
            return null;
        }
        TaskDto taskDto = new TaskDto();
        taskDto.setBusinessId(taskManager.getBusinessId());
        taskDto.setExpression(taskManager.getExpression());
        taskDto.setTaskId(taskManager.getTaskId());
        taskDto.setCallbackUrl(taskManager.getCallbackUrl());
        taskDto.setTaskType(TaskType.valueOf(taskManager.getTaskType()));
        taskDto.setCreateTime(taskManager.getCreateTime().getTime());
        taskDto.setUpdateTime(taskManager.getUpdateTime().getTime());
        return taskDto;
    }

    /**
     * 批量将数据库模型转为传输模型
     * @param taskManagers
     * @return
     */
    private static List<TaskDto> wrapDtoList(List<TaskManager> taskManagers) {
        if (CollectionUtils.isEmpty(taskManagers)) {
            return new ArrayList<>();
        }
        return taskManagers.stream().map(MysqlTaskManagerSupport::wrapDto).collect(Collectors.toList());
    }

    /**
     * 将传输模型转为数据库模型
     * @param taskDto
     * @return
     */
    private static TaskManager wrapManager(TaskDto taskDto) {
        TaskManager taskManager = new TaskManager();
        taskManager.setTaskId(taskDto.getTaskId());
        taskManager.setBusinessId(taskDto.getBusinessId());
        taskManager.setExpression(taskDto.getExpression());
        taskManager.setCallbackUrl(taskDto.getCallbackUrl());
        if (taskDto.getTaskType() != null) {
            taskManager.setTaskType(taskDto.getTaskType().v);
        }
        return taskManager;
    }

}
