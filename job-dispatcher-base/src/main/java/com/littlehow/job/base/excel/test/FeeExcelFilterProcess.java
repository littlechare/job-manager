package com.littlehow.job.base.excel.test;

import com.littlehow.job.base.excel.support.IExcelFieldExecute;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author JimChery
 * @since 12/2/24 13:26
 */
public class FeeExcelFilterProcess implements IExcelFieldExecute {
    private static final BigDecimal HUNDRED = new BigDecimal("100");

    @Override
    public Object process(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            return decimal.multiply(HUNDRED).stripTrailingZeros().toPlainString() + "%";
        }
        return value;
    }

    @Override
    public boolean canBreak() {
        return true;
    }

    @Override
    public String convert(String value) {
        if (value == null || !value.contains("%")) {
            return value;
        }
        return new BigDecimal(value.replace("%", "")).divide(HUNDRED, 6, RoundingMode.DOWN)
                .stripTrailingZeros().toPlainString();
    }
}
