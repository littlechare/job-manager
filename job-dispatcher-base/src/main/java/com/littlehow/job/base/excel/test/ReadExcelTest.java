package com.littlehow.job.base.excel.test;

import com.littlehow.job.base.excel.ExcelRead;
import com.littlehow.job.base.excel.test.bo.Product;

/**
 * @author JimChery
 * @since 11/30/24 12:35
 */
public class ReadExcelTest {

    public static void main(String[] args) {
        testRead();
    }

    private static void testRead() {
        FeeExcelFilterProcess process = new FeeExcelFilterProcess();
        process.afterPropertiesSet();
        ExcelRead<Product> excel = ExcelRead.build(Product.class, System.out::println);
        excel.setStartRowIndex(1).read("/usr/local/excel/test.xlsx");
        // 通过list读取
        excel = ExcelRead.build(Product.class, new MyReadExcelExecute());
        excel.setStartRowIndex(1).read("/usr/local/excel/test.xlsx");
    }
}
