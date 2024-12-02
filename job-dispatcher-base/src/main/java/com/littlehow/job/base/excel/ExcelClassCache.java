package com.littlehow.job.base.excel;

import com.littlehow.job.base.excel.support.ExcelFilterProcess;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JimChery
 */
public class ExcelClassCache {
    private static Map<Class, ExcelMeta> EXCEL_MATE_CACHE = new HashMap<>();

    /**
     * 获取meta配置数据
     * @param clazz - 类
     * @return  -
     */
    static ExcelMeta getMeta(Class clazz) {
        ExcelMeta meta = EXCEL_MATE_CACHE.get(clazz);
        if (meta != null) {
            return meta;
        }
        meta = new ExcelMeta();
        meta.setClazz(clazz);
        // 解析class
        List<Field> fields = new ArrayList<>();
        getAllField(fields, clazz);
        List<ExcelMetaData> metaData = new ArrayList<>();
        fields.forEach(o -> {
            if (o.isAnnotationPresent(ExcelColumn.class)) {
                ExcelMetaData excelMetaData = new ExcelMetaData();
                ExcelColumn excelColumn = o.getAnnotation(ExcelColumn.class);
                ExcelColumnMeta columnMeta = new ExcelColumnMeta()
                        .setHeight(excelColumn.height())
                        .setTitle(excelColumn.title())
                        .setWidth(excelColumn.width());
                if (StringUtils.hasText(excelColumn.transfer())) {
                    columnMeta.setTransfer(getTransfer(excelColumn.transfer()));
                    columnMeta.setTransferRevert(getTransferRevert(columnMeta.getTransfer()));
                }
                if (TemporalAccessor.class.isAssignableFrom(o.getType())) {
                    excelMetaData.setTemporalAccessor(true);
                    String format = excelColumn.format();
                    if (StringUtils.hasText(format)) {
                        columnMeta.setFormatter(DateTimeFormatter.ofPattern(format));
                    }
                } else if (o.getType() == Long.class && StringUtils.hasText(excelColumn.format())) {
                    columnMeta.setFormatter(DateTimeFormatter.ofPattern(excelColumn.format()));
                    excelMetaData.setLongDate(true);
                } else if (o.getType() == BigDecimal.class) {
                    excelMetaData.setDecimal(true);
                }
                excelMetaData.setField(o);
                excelMetaData.setGetMethod(getGetMethod(o, clazz));
                excelMetaData.setSetMethod(getSetMethod(o, clazz));
                excelMetaData.setColumnMeta(columnMeta);
                excelMetaData.setColumn(excelColumn.column());
                // 判断是否存在特殊处理器
                if (o.isAnnotationPresent(ExcelFilterProcess.class)) {
                    ExcelFilterProcess process = o.getAnnotation(ExcelFilterProcess.class);
                    excelMetaData.setProcessIds(process.value());
                }
                metaData.add(excelMetaData);
            }
        });
        Assert.notEmpty(metaData, "excel config not found");
        metaData.sort(Comparator.comparing(ExcelMetaData::getColumn));
        meta.setExcelMetaData(metaData);
        meta.setWidth(metaData.stream().map(o -> o.getColumnMeta().getWidth()).collect(Collectors.toList()));
        meta.setHeight(metaData.get(0).getColumnMeta().getHeight());
        meta.setHeader(metaData.stream().map(o -> o.getColumnMeta().getTitle()).collect(Collectors.toList()));
        EXCEL_MATE_CACHE.put(clazz, meta);
        return meta;
    }

    /**
     * 获取所有字段，包括父类的字段
     * @param fieldList -
     * @param clazz -
     */
    private static void getAllField(List<Field> fieldList, Class clazz) {
        if (clazz == Object.class) {
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        fieldList.addAll(Arrays.asList(fields));
        getAllField(fieldList, clazz.getSuperclass());

    }

    /**
     * 获取字段对应的get方法
     * @param field -
     * @param clazz -
     * @return -
     */
    private static <T> Method getGetMethod(Field field, Class<T> clazz) {
        try {
            String name = field.getName();
            if (name.length() > 1) {
                return  clazz.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
            } else {
                return clazz.getMethod("get" + name.toUpperCase());
            }
        } catch (Exception e) {
            // skip;
            return null;
        }
    }

    private static <T> Method getSetMethod(Field field, Class<T> clazz) {
        try {
            String name = field.getName();
            if (name.length() > 1) {
                return  clazz.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), field.getType());
            } else {
                return clazz.getMethod("set" + name.toUpperCase(), field.getType());
            }
        } catch (Exception e) {
            // skip;
            return null;
        }
    }

    private static Map<String, String> getTransfer(String value) {
        String[] values = value.split(",");
        Map<String, String> transfer = new HashMap<>();
        for (String v : values) {
            String[] info = v.split("=");
            transfer.put(info[0], info[1]);
        }
        return transfer;
    }

    private static Map<String, String> getTransferRevert(Map<String, String> map) {
        Map<String, String> revert = new HashMap<>();
        map.forEach((key, value) -> revert.put(value, key));
        return revert;
    }
}
