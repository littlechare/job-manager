package com.littlehow.job.base.excel.support;

import java.util.List;

/**
 * @author JimChery
 * @since 12/2/24 11:30
 */
public interface IReadExcelExecute<T> {
    void process(T data);

    default void processList(List<T> data) {
        // do nothing
    }

    default boolean isList() {
        return false;
    }

    default int getPageSize() {
        return 1000;
    }
}
