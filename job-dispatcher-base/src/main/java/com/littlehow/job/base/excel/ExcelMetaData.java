package com.littlehow.job.base.excel;

import com.littlehow.job.base.excel.support.IExcelFieldExecute;
import com.littlehow.job.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;

/**
 * @author JimChery
 */
@Setter
@Getter
public class ExcelMetaData {
    /**
     * 字段
     */
    private Field field;

    /**
     * 对应的get方法
     */
    private Method getMethod;

    /**
     * 对应的set方法
     */
    private Method setMethod;

    /**
     * 处理方式
     */
    private String[] processIds;

    /**
     * 列标
     */
    private int column;

    /**
     * 日期顶级接口
     */
    private boolean isTemporalAccessor = false;

    /**
     * 是否为decimal类型
     */
    private boolean isDecimal = false;

    /**
     * 是否为long的日期类型
     */
    private boolean isLongDate = false;

    /**
     * 字段属性
     */
    private ExcelColumnMeta columnMeta;

    /**
     * 获取对象的值，做简单类型的格式化
     * @param obj - 对象
     * @return - obj对应该字段的格式化值
     */
    public String getString(Object obj) {
        if (obj == null) {
            return "";
        }
        Object value = getValue(obj);
        if (value == null) {
            return "";
        }
        if (processIds != null && processIds.length > 0) {
            for (String processId : processIds) {
                IExcelFieldExecute execute = IExcelFieldExecute.resolve.get(processId);
                if (execute != null) {
                    value = execute.process(value);
                    if (execute.canBreak()) {
                        return value.toString();
                    }
                }
            }
        }
        if (isTemporalAccessor) {
            return columnMeta.getDateString((TemporalAccessor) value);
        } else if (isLongDate) {
            return columnMeta.getDateString((Long) value);
        } else if (isDecimal) {
            return ((BigDecimal) value).stripTrailingZeros().toPlainString();
        } else {
            return columnMeta.getValue(value.toString());
        }
    }

    /**
     * 设置列值
     * @param obj   - 实例
     * @param value - 值
     */
    public void setColumnValue(Object obj, String value) {
        Object objValue = getColumnValue(value);
        if (objValue != null) {
            setValue(obj, objValue);
        }
    }

    /**
     * 支持字符串、日期、int、long、boolean
     * 后续需要增加支持类型，在此继续追加解析即可
     * @param value - excel内容
     * @return - 值
     */
    private Object getColumnValue(String value) {
        if (value == null) {
            return null;
        }
        if (processIds != null && processIds.length > 0) {
            for (String processId : processIds) {
                IExcelFieldExecute execute = IExcelFieldExecute.resolve.get(processId);
                if (execute != null) {
                    value = execute.convert(value);
                }
            }
        }
        // 如果有transfer的需要进行转换
        value = columnMeta.getTransferValue(value);
        Class<?> type = field.getType();
        if (type == String.class) {
            return value;
        }
        // 如果是非字符串类型的，又没有内容的就直接返回null
        if (!StringUtils.hasText(value)) {
            return null;
        }
        if (isTemporalAccessor) {
            // 转换为日期或者时间
            if (type == LocalDate.class) {
                return LocalDate.parse(value, columnMeta.getFormatter());
            } else if (type == LocalDateTime.class) {
                return LocalDateTime.parse(value, columnMeta.getFormatter());
            } else {
                return LocalTime.parse(value, columnMeta.getFormatter());
            }
        } else if (isLongDate) {
            // 智能转换为时间
            LocalDateTime time = LocalDateTime.parse(value, columnMeta.getFormatter());
            return DateUtil.getTime(time);
        } else if (isDecimal) {
            return new BigDecimal(value);
        } else if (type == Integer.class || type == int.class){
            return Integer.valueOf(value);
        } else if (type == Long.class || type == long.class) {
            return Long.valueOf(value);
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.valueOf(value);
        } else {
            return value;
        }
    }

    /**
     * 获取对象的值, 优先get方法获取，没有get方法的将使用field获取，所以如果没有提供get方法，需要将字段访问属性设置为public
     * 这里就不对字段进行field.setAccessible设置
     * @param obj  - 对象
     * @return - 对应对应的值
     */
    private Object getValue(Object obj) {
        try {
            if (getMethod != null) {
                return getMethod.invoke(obj);
            } else {
                return field.get(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(Object obj, Object value) {
        try {
            if (setMethod != null) {
                setMethod.invoke(obj, value);
            } else {
                field.set(obj, value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
