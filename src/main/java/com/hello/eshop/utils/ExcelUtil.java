package com.hello.eshop.utils;

import com.hello.eshop.bean.Book;
import com.hello.eshop.bean.Chart;
import com.hello.eshop.bean.Product;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Excel工具类
 */
public class ExcelUtil {

	// 自己封装标题
	public static void fillExcelData(List<Product> list, Workbook wb, String[] headers)throws Exception{
		int rowIndex=0;
		Sheet sheet=wb.createSheet();
		Row row=sheet.createRow(rowIndex++);
		for(int i=0;i<headers.length;i++){
			row.createCell(i).setCellValue(headers[i]);
		}
		for (Product prod: list) {
			row=sheet.createRow(rowIndex++);
			int cellIndex = 0;
			row.createCell(cellIndex++).setCellValue(prod.getId());
			row.createCell(cellIndex++).setCellValue(prod.getProdName());
			row.createCell(cellIndex++).setCellValue(prod.getShopPrice().toString());
			row.createCell(cellIndex++).setCellValue(prod.getNum().toString());
			row.createCell(cellIndex++).setCellValue(prod.getDiscount().toString());

			row.createCell(cellIndex++).setCellValue(prod.getImage());
			row.createCell(cellIndex++).setCellValue(DateUtils.formatDate(prod.getAddTime(), DateUtils.TIME_PATTERN));
			row.createCell(cellIndex++).setCellValue(prod.getIsHot().toString());
			row.createCell(cellIndex++).setCellValue(prod.getDescription());
			row.createCell(cellIndex++).setCellValue(prod.getStatus().toString());

			row.createCell(cellIndex++).setCellValue(prod.getBigCateId().toString());
			row.createCell(cellIndex++).setCellValue(prod.getCateId().toString());
		}
	}

	// 用模板导出商品数据
	public static Workbook fillExcelDataWithTemplate(List<Product> list,String fileName)throws IOException{
		// 把文件读取成流
		//使用ClassLoader.getResourceAsStream时， 路径直接使用相对于classpath的绝对路径,并且不能已 / 开头
		InputStream inp=ExcelUtil.class.getClassLoader().getResourceAsStream("template/"+fileName);
		POIFSFileSystem fs=new POIFSFileSystem(inp);
		Workbook wb=new HSSFWorkbook(fs);
		Sheet sheet=wb.getSheetAt(0);
//		int cellNums=sheet.getRow(0).getLastCellNum();
		int rowIndex=1;
		for (Product prod: list) {
			Row row=sheet.createRow(rowIndex++);
			int cellIndex = 0;
			Book book = prod.getBook();
			row.createCell(cellIndex++).setCellValue(prod.getId());
			row.createCell(cellIndex++).setCellValue(book.getBookName());
			row.createCell(cellIndex++).setCellValue(prod.getShopPrice().toString());
			row.createCell(cellIndex++).setCellValue(prod.getNum().toString());
			row.createCell(cellIndex++).setCellValue(prod.getDiscount().toString());

			row.createCell(cellIndex++).setCellValue(book.getAuthor());
			row.createCell(cellIndex++).setCellValue(book.getPress());
			row.createCell(cellIndex++).setCellValue(book.getMarketPrice().toString());
			row.createCell(cellIndex++).setCellValue(DateUtils.formatDate(book.getPublishDate(), DateUtils.MONTH_PATTERN));
			row.createCell(cellIndex++).setCellValue(prod.getDescription());

			row.createCell(cellIndex++).setCellValue(prod.getBigCate().getName());
			row.createCell(cellIndex++).setCellValue(prod.getCate().getName());
		}
		return wb;
	}

	// excel格式转换
	public static String formatCell(HSSFCell hssfCell){
		if(hssfCell==null){
			return "";
		}else{
			if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
				return String.valueOf(hssfCell.getBooleanCellValue());
			}else if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
				return String.valueOf(hssfCell.getNumericCellValue());
			}else{
				return String.valueOf(hssfCell.getStringCellValue());
			}
		}
	}

	// 用模板导出订单数据
    public static Workbook fillOrderDataWithTemplate(List<Chart> list, String fileName) throws IOException {
		InputStream inp=ExcelUtil.class.getClassLoader().getResourceAsStream("template/"+fileName);
		POIFSFileSystem fs=new POIFSFileSystem(inp);
		Workbook wb=new HSSFWorkbook(fs);
		Sheet sheet=wb.getSheetAt(0);
		int rowIndex=1;
		for (Chart chart: list) {
			Row row=sheet.createRow(rowIndex++);
			int cellIndex = 0;
			row.createCell(cellIndex++).setCellValue(chart.getName());
			row.createCell(cellIndex++).setCellValue(chart.getPrice().toString());
			row.createCell(cellIndex++).setCellValue(chart.getNum().toString());
			row.createCell(cellIndex++).setCellValue(chart.getAmount().toString());
		}
		return wb;
    }
}
