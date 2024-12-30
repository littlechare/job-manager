package com.littlehow.job.base.excel;

import com.littlehow.job.base.excel.support.IReadExcelExecute;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author littlehow
 * @since 11/30/24 12:35
 */
public class ExcelRead<T> {
    private ExcelMeta excelMeta;
    private IReadExcelExecute<T> execute;
    private int startRowIndex;

    private ExcelRead() {
        // 不开放
    }

    public static <T> ExcelRead<T> build(Class<T> clazz, IReadExcelExecute<T> execute) {
        ExcelRead<T> excel = new ExcelRead<>();
        excel.excelMeta = ExcelClassCache.getMeta(clazz);
        excel.execute = execute;
        excel.startRowIndex = 0;
        return excel;
    }

    public ExcelRead<T> setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
        return this;
    }

    /**
     * 读取本地文件
     * @param file - 文件
     */
    public void read(File file) {
        try {
            read(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取本地文件
     * @param file - 文件
     */
    public void read(String file) {
        try {
            read(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取本地文件
     * @param is - 输入流
     */
    public void read(InputStream is) {
        try {
            OPCPackage pkg = OPCPackage.open(is);
            XSSFReader reader = new XSSFReader(pkg);
            SharedStringsTable sst = reader.getSharedStringsTable();
            XMLReader parser = XMLReaderFactory.createXMLReader();
            ExcelHandler handler = new ExcelHandler(sst);
            parser.setContentHandler(handler);
            Iterator<InputStream> sheetData = reader.getSheetsData();
            while (sheetData.hasNext()) {
                InputStream sheetStream = sheetData.next();
                parser.parse(new InputSource(sheetStream));
                sheetStream.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // skip
                }
            }
        }
    }

    // 自定义 SAX 事件处理器
    private class ExcelHandler extends DefaultHandler {
        private final SharedStringsTable sst;
        // 单元格内容
        private String lastContents;
        // 是否为共享字符串
        private boolean isString;
        // 是否在单元格中
        private boolean isCellOpen;
        // 当前行标
        private int rowIndex;
        // 当前行的内容
        private List<String> currentRow;
        // 当要以list输出时，缓存还没输出的数据
        private List<T> dataCache;

        ExcelHandler(SharedStringsTable sst) {
            this.sst = sst;
            if (execute.isList()) {
                dataCache = new ArrayList<>();
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if ("row".equals(qName)) {
                // 行的开始
                currentRow = new ArrayList<>();
                rowIndex += 1;
            } else if ("c".equals(qName)) {
                // 单元格为字母c
                String cellType = attributes.getValue("t");
                // 判断是否为共享字符串
                isString = "s".equals(cellType);
                isCellOpen = true;
            }
            // 清空内容
            lastContents = "";
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (isCellOpen) {
                lastContents += new String(ch, start, length);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if ("c".equals(qName)) {
                // 单元格结束
                String value = lastContents.trim();
                if (isString) {
                    // 如果是共享字符串，该内容是数字索引
                    int idx = Integer.parseInt(value);
                    value = sst.getItemAt(idx).getString();
                }
                // 添加到当前行, 列数据最后可以按照下标读取
                currentRow.add(value);
                isCellOpen = false;
            } else if ("row".equals(qName) && rowIndex >= startRowIndex) {
                // 行结束
                T t = convert();
                if (execute.isList()) {
                    dataCache.add(t);
                    if (dataCache.size() >= execute.getPageSize()) {
                        execute.processList(dataCache);
                        dataCache = new ArrayList<>();
                    }
                } else {
                    execute.process(t);
                }
            }
        }

        @Override
        public void startDocument() {
            // 设置初始值
            rowIndex = -1;
        }

        @Override
        public void endDocument() {
            if (dataCache != null && dataCache.size() > 0) {
                execute.processList(dataCache);
                dataCache = new ArrayList<>();
            }
        }

        private T convert() {
            Object data = excelMeta.instance();
            excelMeta.foreach(o -> {
                int column = o.getColumn();
                if (column <= currentRow.size()) {
                    o.setColumnValue(data, currentRow.get(column - 1));
                }
            });
            return (T)data;
        }
    }
}
