package com.littlehow.job.manager.mapper.job;

import com.littlehow.job.manager.model.job.OperationLog;

/**
 * 日记记录mapper
 * littlehow 2018/4/4
 */
public interface OperationLogMapper {
    int insertLog(OperationLog operationLog);
}