package com.littlehow.job.base.excel.test;

import com.littlehow.job.base.excel.ExcelRead;
import com.littlehow.job.base.excel.test.bo.Product;

/**
 * @author littlehow
 * @since 11/30/24 12:35
 */
public class ReadExcelTest {

    public static void main(String[] args) {
        testRead();
    }

    private static void testRead() {
        FeeExcelFilterProcess process = new FeeExcelFilterProcess();
        process.afterPropertiesSet();
        // 这里进行单行读取，将每行读取结果打印
        ExcelRead<Product> excel = ExcelRead.build(Product.class, System.out::println);
        excel.setStartRowIndex(1).read("/usr/local/excel/test.xlsx");
        System.out.println("========================== 下面是list处理结果哦 =====================");
        // 通过list读取
        excel = ExcelRead.build(Product.class, new MyReadExcelExecute());
        excel.setStartRowIndex(1).read("/usr/local/excel/test.xlsx");
    }
}
