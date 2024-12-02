package com.littlehow.job.base.excel;

import java.lang.annotation.*;

/**
 * @author JimChery
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExcelColumn {

    /**
     * @return - 表头
     */
    String title();

    /**
     * 第几列
     * @return - 从1开始, 如果是读取excel，这个列很重要
     */
    int column();

    /**
     * @return 日期格式化
     */
    String format() default "";

    /**
     * 转义值  格式如 1=发送中,2=完成
     * @return -
     */
    String transfer() default "";

    /**
     * @return 行高
     */
    short height() default 450;

    /**
     * @return - 列宽
     */
    int width() default 4800;
}
