package com.littlehow.job.base.excel.test;

import com.littlehow.job.base.excel.ExcelWrite;
import com.littlehow.job.base.excel.test.bo.Product;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JimChery
 * @since 11/30/24 12:09
 */
public class WriteExcelTest {
    public static void main(String[] args) {
        testWrite();
    }

    private static void testWrite() {
        FeeExcelFilterProcess process = new FeeExcelFilterProcess();
        process.afterPropertiesSet();
        List<Product> data = new ArrayList<>();
        data.add(new Product().setBirthday(LocalDate.now().minusDays(65))
                .setFee(new BigDecimal("0.015"))
                .setName("汇源果汁")
                .setPrice(new BigDecimal("2.5"))
                .setStatus(1)
                .setUpdateTime(LocalDateTime.now()));
        data.add(new Product().setBirthday(LocalDate.now().minusDays(365))
                .setFee(new BigDecimal("0.03"))
                .setName("农夫山泉")
                .setPrice(new BigDecimal("2"))
                .setStatus(0)
                .setUpdateTime(LocalDateTime.now().minusMinutes(35)));
        data.add(new Product().setBirthday(LocalDate.now().minusDays(365))
                .setFee(new BigDecimal("0.03"))
                .setName("娃哈哈")
                .setPrice(new BigDecimal("2"))
                .setStatus(0)
                .setUpdateTime(LocalDateTime.now().minusMinutes(35)));
        data.add(new Product().setBirthday(LocalDate.now().minusDays(365))
                .setFee(new BigDecimal("0.03"))
                .setName("清凉油")
                .setPrice(new BigDecimal("2"))
                .setStatus(0)
                .setUpdateTime(LocalDateTime.now().minusMinutes(35)));
        data.add(new Product().setBirthday(LocalDate.now().minusDays(365))
                .setFee(new BigDecimal("0.03"))
                .setName("脉动")
                .setPrice(new BigDecimal("2"))
                .setStatus(1)
                .setUpdateTime(LocalDateTime.now().minusMinutes(35)));

        data.add(new Product().setFee(new BigDecimal("0.03"))
                .setName("秘制水哦")
                .setPrice(new BigDecimal("2"))
                .setStatus(1)
                .setUpdateTime(LocalDateTime.now().minusMinutes(35)));
        ExcelWrite<Product> excel = ExcelWrite.build(Product.class);
        try {
            excel.write(data).create(new FileOutputStream("/usr/local/excel/test.xlsx"));
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
