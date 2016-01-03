package com.pzy.util;

/**
 * 
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pzy.entity.Order;

/**
 * @author Hongten
 * @created 2014-5-20
 */
public class ExcelUtil {
    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public static List<Order> readXls(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        List<Order> list = new ArrayList<Order>();
        HSSFSheet sheet = workbook.getSheetAt(0);
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow row = sheet.getRow(rowNum);
                if (row != null) {
                    Order order = new Order();
                    order.setId(getValue(row.getCell(1)));
                    order.setC1(getValue(row.getCell(0)));
                    order.setC2(getValue(row.getCell(2)));
                    order.setC3(getValue(row.getCell(3)));
                    order.setC4(getValue(row.getCell(4)));
                    order.setC5(getValue(row.getCell(5)));
                    order.setC6(getValue(row.getCell(6)));
                    order.setC7(getValue(row.getCell(7)));
                    order.setC8(getValue(row.getCell(8)));
                    order.setC9(getValue(row.getCell(9)));
                    order.setC10(getValue(row.getCell(10)));
                    order.setC11(getValue(row.getCell(11)));
                    order.setC12(getValue(row.getCell(12)));
                    order.setC13(getValue(row.getCell(13)));
                    order.setC14(getValue(row.getCell(14)));
                    order.setC15(getValue(row.getCell(15)));
                    order.setC16(getValue(row.getCell(16)));
                    order.setC17(getValue(row.getCell(17)));
                    order.setC18(getValue(row.getCell(18)));
                    order.setC19(getValue(row.getCell(19)));
                    list.add(order);
                }
        }
        return list;
    }
    @SuppressWarnings("static-access")
    private static String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    
    public static void main(String arg[]) throws IOException{
    	List<Order> list=ExcelUtil.readXls("d:\\1.xls");
    	System.out.println(list.size());
    }
}