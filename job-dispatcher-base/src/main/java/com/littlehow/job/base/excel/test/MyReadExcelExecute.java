package com.littlehow.job.base.excel.test;

import com.littlehow.job.base.excel.support.IReadExcelExecute;
import com.littlehow.job.base.excel.test.bo.Product;

import java.util.List;

/**
 * @author JimChery
 * @since 12/2/24 13:32
 */
public class MyReadExcelExecute implements IReadExcelExecute<Product> {
    @Override
    public void process(Product data) {
        // do nothing
    }

    public void processList(List<Product> data) {
        System.out.println(data);
    }

    public boolean isList() {
        return true;
    }
}
