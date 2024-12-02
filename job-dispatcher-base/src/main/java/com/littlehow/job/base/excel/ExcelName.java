package com.littlehow.job.base.excel;

/**
 * @author littlehow
 */
public class ExcelName {
    public static String encodeName(String name) {
        try {
            return new String(name.getBytes(), "ISO8859-1");
        } catch (Exception e) {
            return name;
        }
    }
}
