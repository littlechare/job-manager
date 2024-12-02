package com.littlehow.job.base.excel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Consumer;

/**
 * 记录表宽度、表头、行高以及excel字段信息
 * @author JimChery
 */
@Setter
@Getter
public class ExcelMeta {

    private Class clazz;

    /**
     * 列宽
     */
    private List<Integer> width;

    /**
     * 行高
     */
    private short height;

    /**
     * 表头
     */
    private List<String> header;

    /**
     * 字段属性集
     */
    private List<ExcelMetaData> excelMetaData;

    public Object instance() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            throw new RuntimeException("class [" + clazz + "] must have a public non-param constructor function");
        }
    }

    public void foreach(Consumer<ExcelMetaData> action) {
        excelMetaData.forEach(action);
    }
}
