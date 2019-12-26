package com.tools.todo;


import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: lvp
 * @Date: 2019-02-18
 * @Description: 本类适合解析Excel, 生成Excel
 */
public class ExcelUtils {

    private final static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 解析Excel文件到List
     *
     * @param inputStream 要解析的流如果是servlet的输入流不需要手动关闭，其他自己手动创建的流需要自己关掉，在此方法中没有做关闭处理
     * @param startRow    真实数据的开始行 从0开始数
     * @param startCol    真实数据的开始列 从0开始数
     * @return List<Map>  map的key就是列数  从0开始计数
     */
    public static List<HashMap<Integer, HashMap<Integer, String>>> parseExcel(InputStream inputStream, Integer startRow, Integer endRow, Integer startCol, Integer endCol) {
        Workbook workbook = null;
        List<HashMap<Integer, HashMap<Integer, String>>> resultList = new ArrayList<>();
        try {
            workbook = WorkbookFactory.create(inputStream);
            for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
                Sheet sheet = workbook.getSheetAt(numSheet);
                if (sheet == null) {
                    return null;
                }
                if (endRow == null) {
                    endRow = sheet.getLastRowNum();
                }
                HashMap<Integer, HashMap<Integer, String>> dataMap = new HashMap();
                for (int i = startRow; i <= endRow; i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    if (endCol == null) {
                        endCol = Integer.valueOf(row.getLastCellNum());
                    }
                    HashMap<Integer, String> cellValueMap = new HashMap<>();
                    for (int j = startCol; j <= endCol; j++) {
                        String cellValue = getCellValue(row.getCell(j));
                        cellValueMap.put(j, cellValue);
                    }
                    dataMap.put(i, cellValueMap);
                }
                resultList.add(dataMap);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
        }
        return resultList;
    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        String cellValue = "";
        switch (cell.getCellTypeEnum()) {
            case STRING:
                cellValue = cell.getRichStringCellValue().getString().trim();
                break;
            case NUMERIC:
                DecimalFormat df = new DecimalFormat("##.##########");
                cellValue = df.format(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case FORMULA:
                try {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    cellValue = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            case BLANK:
                cellValue = "";
                break;
            case ERROR:
                cellValue = "";
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

}
