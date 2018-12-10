package com.hello.eshop.test;

import com.hello.eshop.utils.ExcelUtil;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

public class PoiTest {
    public static void main(String[] args) throws Exception {
        PoiTest poiTest = new PoiTest();
//        poiTest.writeExcel();
//        poiTest.readExcel2();
//        String str = DateUtils.formatDate(new Date(), DateUtils.TIME_PATTERN);
//        System.out.println(str);

    }

    // 批量导出


    // 一次性读取excel
    public void readExcel2() throws IOException {
        InputStream is=new FileInputStream("D:\\workspace_idea\\用Poi搞出来的Cell.xls");
        POIFSFileSystem fs=new POIFSFileSystem(is);
        HSSFWorkbook wb=new HSSFWorkbook(fs);

        ExcelExtractor excelExtractor=new ExcelExtractor(wb);
        excelExtractor.setIncludeSheetNames(false);// 我们不需要Sheet页的名字
        System.out.println(excelExtractor.getText());
    }

    // 读取excel
    public void readExcel() throws IOException {
        InputStream is=new FileInputStream("D:\\workspace_idea\\用Poi搞出来的Cell.xls");
        POIFSFileSystem fs=new POIFSFileSystem(is);
        HSSFWorkbook wb=new HSSFWorkbook(fs);
        HSSFSheet hssfSheet=wb.getSheetAt(0); // 获取第一个Sheet页
        if(hssfSheet==null){
            return;
        }
        // 遍历行Row
        for(int rowNum=0;rowNum<=hssfSheet.getLastRowNum();rowNum++){
            HSSFRow hssfRow=hssfSheet.getRow(rowNum);
            if(hssfRow==null){
                continue;
            }
            // 遍历列Cell
            for(int cellNum=0;cellNum<=hssfRow.getLastCellNum();cellNum++){
                HSSFCell hssfCell=hssfRow.getCell(cellNum);
                if(hssfCell==null){
                    continue;
                }
                System.out.print(" "+ ExcelUtil.formatCell(hssfCell));
            }
            System.out.println();
        }
    }


    // 将数据写到excel
    public void writeExcel() throws IOException {
        Workbook wb = new HSSFWorkbook();// 定义一个新的工作簿
        Sheet sheet = wb.createSheet();// 创建第一个Sheet页
        wb.createSheet("第二个Sheet页");  // 创建第二个Sheet页
        Row row=sheet.createRow(0); // 创建一个行
        Cell cell=row.createCell(0); // 创建一个单元格  第1列
        cell.setCellValue(1);  // 给单元格设置值

        row.createCell(1).setCellValue(1.2);   // 创建一个单元格 第2列 值是1.2

        row.createCell(2).setCellValue("这是一个字符串类型"); // 创建一个单元格 第3列 值为一个字符串

        row.createCell(3).setCellValue(false);  // 创建一个单元格 第4列 值为布尔类型

        CreationHelper createHelper=wb.getCreationHelper();
        CellStyle cellStyle=wb.createCellStyle(); //单元格样式类
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyy-mm-dd hh:mm:ss"));
        cell=row.createCell(4); // 第4列
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);

        cell=row.createCell(5);  // 第5列
        cell.setCellValue(Calendar.getInstance());
        cell.setCellStyle(cellStyle);

        FileOutputStream fileOut = new FileOutputStream("D:\\workspace_idea\\用Poi搞出来的Cell.xls");
        wb.write(fileOut);
        fileOut.close();
    }
}
