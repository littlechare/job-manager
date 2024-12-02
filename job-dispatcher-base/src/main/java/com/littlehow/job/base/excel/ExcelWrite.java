package com.littlehow.job.base.excel;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author littlehow
 */
public class ExcelWrite<T> {
    private SXSSFWorkbook workbook;

    private SXSSFSheet sheet;

    private int rowIndex = 0;

    private boolean create = false;

    private ExcelMeta excelMeta;

    private ExcelWrite() {
        // 不开放
    }

    /**
     * 构建excel导出类
     * @param clazz - 导出类型
     * @param <T> -
     * @return - 导出类
     */
    public static <T> ExcelWrite<T> build(Class<T> clazz) {
        ExcelWrite<T> excel = new ExcelWrite<>();
        excel.excelMeta = ExcelClassCache.getMeta(clazz);
        excel.workbook = new SXSSFWorkbook();
        excel.sheet = excel.workbook.createSheet();
        // 设置列头以及列宽
        excel.fullHeader();
        return excel;
    }

    /**
     * 填充类容
     * @param content - 类容
     */
    public ExcelWrite<T> write(List<T> content) {
        Assert.isTrue(!create, "excel already created");
        if (CollectionUtils.isEmpty(content)) {
            return this;
        }
        XSSFCellStyle style = (XSSFCellStyle)workbook.createCellStyle();
        content.forEach(t -> {
            // 创建表头
            SXSSFRow row = sheet.createRow(this.rowIndex++);
            row.setHeight(excelMeta.getHeight());
            setContent(row, getContent(t), style);
        });
        return this;
    }

    /**
     * 输出 本方法没有关闭外部输出流
     * @param os - 输出流
     * @throws IOException  - io异常
     */
    public void create(OutputStream os) throws IOException {
        workbook.write(os);
        create = true;
    }



    private void fullHeader() {

        setColumnWidth(this.sheet, excelMeta.getWidth());
        // 设置表头
        SXSSFRow header = this.sheet.createRow(this.rowIndex++);
        header.setHeight(excelMeta.getHeight());
        XSSFCellStyle style = (XSSFCellStyle)workbook.createCellStyle();
        setContent(header, excelMeta.getHeader(), style);
    }

    private List<String> getContent(T t) {
        List<ExcelMetaData> excelMetaData = excelMeta.getExcelMetaData();
        List<String> values = new ArrayList<>();
        excelMetaData.forEach(data -> values.add(data.getString(t)));
        return values;
    }

    private void setColumnWidth(SXSSFSheet sheet, List<Integer> columnWidths) {
        for (int i = 0, len = columnWidths.size(); i < len; i++) {
            sheet.setColumnWidth(i, columnWidths.get(i));
        }
    }

    private void setContent(SXSSFRow row, List<String> content, XSSFCellStyle style) {
        // 设置第一行
        for (int i = 0, len = content.size(); i < len; i++) {
            SXSSFCell cell = row.createCell(i);
            cell.setCellValue(content.get(i));
            // 创建一个单元格样式
            cell.setCellStyle(style);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
        }
    }
}
