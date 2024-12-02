package com.littlehow.job.base.excel.test.bo;

import com.littlehow.job.base.excel.ExcelColumn;
import com.littlehow.job.base.excel.support.ExcelFilterProcess;
import com.littlehow.job.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author littlehow
 * @since 11/30/24 12:31
 */
@Getter
@Setter
@Accessors(chain = true)
public class Product {
    @ExcelColumn(title = "产品名称", column = 1)
    private String name;

    @ExcelColumn(title = "价格", column = 2)
    private BigDecimal price;

    @ExcelColumn(title = "状态", column = 3, transfer = "1=上架,0=下架")
    private Integer status;

    @ExcelColumn(title = "手续费", column = 4)
    @ExcelFilterProcess("FeeExcelFilterProcess")
    private BigDecimal fee;

    @ExcelColumn(title = "生产日期", column = 5, format = "yyyy-MM-dd")
    private LocalDate birthday;

    @ExcelColumn(title = "更新时间", column = 6, format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return "{\"name\":\"" + name + "\",\"price\":"
                + (price == null ? "null" : price.stripTrailingZeros().toPlainString())
                + ",\"status\":" + status + ",\"fee\":"
                + (fee == null ? "null" : fee.stripTrailingZeros().toPlainString())
                + ",\"birthday\":\""
                + (birthday == null ? "" : DateUtil.formatDate(birthday))
                + "\",\"updateTime\":\""
                + (updateTime == null ? "" : DateUtil.formatDate(updateTime))
                + "\"}";
    }
}
