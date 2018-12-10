package com.hello.eshop.controller;


import com.hello.eshop.bean.Chart;
import com.hello.eshop.service.ChartService;
import com.hello.eshop.utils.ExcelUtil;
import com.hello.eshop.utils.JFreeChartUtils;
import com.hello.eshop.utils.ResponseUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 统计图表Controller
 */
@Controller
@RequestMapping("/chart")
public class ChartController {

    @Autowired
    private ChartService chartService;

    /**
     * 按照年、月份导出销售数据
     */
    @RequestMapping("/export")
    public void export(Date date, HttpServletResponse response) throws Exception {
        List<Chart> list = chartService.getByDate(date);
        Workbook wb = ExcelUtil.fillOrderDataWithTemplate(list, "SuccessOrder.xls");
        // 转换为Joba Time，方便取出年、月
        DateTime dateTime = new DateTime(date.getTime());
        ResponseUtil.export(response, wb, "导出"+dateTime.getYear()+"年"+dateTime.getMonthOfYear()+"月销售数据.xls");
    }

    /**
     * 按照年、月份显示销量图
     */
    @RequestMapping("/bar")
    public String genBarChart(Date date,HttpSession session, Model model) throws IOException {

        // 1。获取数据集
        DefaultCategoryDataset dataset = chartService.getSellTop(date);

        // 2.解决字体乱码
        JFreeChartUtils.changeFont();

        /*double [][]data=new double[][]{{1320,1110,1123,321},{720,210,1423,1321},{830,1310,123,521},{400,1110,623,321}};
        String []rowKeys={"苹果","香蕉","橘子","梨子"};
        String []columnKeys={"深圳","北京","上海","南京"};
        //CategoryDataset dataset= DatasetUtilities.createCategoryDataset(rowKeys,columnKeys ,data);
        */

        // 3.将数据集放到工厂生产产品
        JFreeChart chart = JFreeChartUtils.getBarChart(dataset);

        // 4.将产品放到特定的servlet,留下url给jsp页面访问
        String filename= ServletUtilities.saveChartAsPNG(chart, 1000, 600, null,session);
        model.addAttribute("filename",filename);
        return "forward:/admin/chart/bar.jsp";
    }
}
