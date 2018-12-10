package com.hello.eshop.utils;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * response工具类
 */
public class ResponseUtil {


	/**
	 * 直接在网页上输出信息（解决乱码）
	 * @param response
	 * @param o
	 * @throws Exception
	 */
	public static void write(HttpServletResponse response,Object o)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.print(o.toString());
		out.flush();
		out.close();
	}

	/**
	 * 文件下载
	 * @param response
	 * @param wb
	 * @param fileName
	 * @throws Exception
	 */
	public static void export(HttpServletResponse response,Workbook wb,String fileName)throws Exception{
		response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"iso8859-1"));
		response.setContentType("application/ynd.ms-excel;charset=UTF-8");
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
	}

}
