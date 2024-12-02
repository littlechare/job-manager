package com.littlehow.job.base.excel.support;

import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * littlehow
 */
public interface IExcelFieldExecute extends InitializingBean {

    Map<String, IExcelFieldExecute> resolve = new ConcurrentHashMap<>();

    Object process(Object value);

    default String convert(String value) {
        return value;
    }

    default String getId() {
        return this.getClass().getSimpleName();
    }

    default boolean canBreak() {
        return false;
    }

    default void afterPropertiesSet() {
        IExcelFieldExecute old = resolve.get(getId());
        if (old != null) {
            throw new IllegalArgumentException("duplicate serialize key " + old.getId() + ", class " + old.getClass());
        }
        resolve.put(getId(), this);
    }
}
