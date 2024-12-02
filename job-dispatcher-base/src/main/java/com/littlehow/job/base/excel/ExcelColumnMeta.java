package com.littlehow.job.base.excel;

import com.littlehow.job.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Map;

/**
 * @author littlehow
 */
@Setter
@Getter
@Accessors(chain = true)
public class ExcelColumnMeta {
    /**
     * 表头
     */
    private String title;

    /**
     * 行高
     */
    private short height;

    /**
     * 列宽
     */
    private int width;

    /**
     * 日期格式化
     */
    private DateTimeFormatter formatter;

    private Map<String, String> transfer;
    private Map<String, String> transferRevert;

    public String getDateString(TemporalAccessor accessor) {
        if (formatter != null) {
            return formatter.format(accessor);
        }
        return accessor.toString();
    }

    public String getDateString(Long time) {
        if (time == null) {
            return "";
        } else if (formatter == null) {
            return time.toString();
        } else {
            return formatter.format(DateUtil.toLocalDateTime(time));
        }
    }

    public String getValue(Object obj) {
        String value = obj.toString();
        if (transfer != null) {
            String temp = transfer.get(value);
            if (temp != null) {
                value = temp;
            }
        }
        return value;
    }

    public String getTransferValue(String value) {
        if (transferRevert != null) {
            String revert = transferRevert.get(value);
            if (revert != null) {
                return revert;
            }
        }
        return value;
    }

    public DateTimeFormatter getFormatter() {
        if (formatter == null) {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        return formatter;
    }
}
