package com.littlehow.job.base.excel.support;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExcelFilterProcess {
    String[] value();
}
