package com.hello.eshop.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;

import java.awt.*;
import java.text.NumberFormat;

/**
 * JFreeChart工具类
 * Created by Hello on 2018/3/29.
 */
public class JFreeChartUtils {

    /**
     * 解决字体乱码（在创建图表之前设定）
     */
    public static void changeFont(){

        //创建主题样式
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
        //设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD,20));
        //设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));
        //设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));
        //应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);
    }

    /**
     * 根据数据集生产bar chart
     */
    public static JFreeChart getBarChart(CategoryDataset dataset){
        JFreeChart chart =
                ChartFactory.createBarChart3D("图书销售统计图", "图书", "销量", dataset,
                PlotOrientation.VERTICAL, true, true, true);

        CategoryPlot plot = chart.getCategoryPlot();
        // 设置网格背景颜色
        plot.setBackgroundPaint(Color.white);
        // 设置网格竖线颜色
        plot.setDomainGridlinePaint(Color.pink);
        // 设置网格横线颜色
        plot.setRangeGridlinePaint(Color.pink);

        // 显示每个柱的数值，并修改该数值的字体属性
        BarRenderer3D renderer = new BarRenderer3D();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);

        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        renderer.setItemLabelAnchorOffset(10D);

        // 设置平行柱的之间距离
        renderer.setItemMargin(1);

        plot.setRenderer(renderer);

        return chart;
    }

    /**
     * 根据Pie数据集生产pie chart
     */
    public static JFreeChart getPieChart(DefaultPieDataset dataset){
        JFreeChart chart=ChartFactory.createPieChart3D("图书销售分布图", dataset, true, true, true);

        // 副标题
        chart.addSubtitle(new TextTitle("2018年度"));

        PiePlot pieplot=(PiePlot)chart.getPlot();
        pieplot.setLabelFont(new Font("宋体",0,11));
        // 设置饼图是圆的（true），还是椭圆的（false）；默认为true
        pieplot.setCircular(true);
        // 没有数据的时候显示的内容
        pieplot.setNoDataMessage("无数据显示");
        StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0}:({1}.{2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
        pieplot.setLabelGenerator(standarPieIG);

        // 3D绘图
        PiePlot3D pieplot3d = (PiePlot3D)chart.getPlot();

        //设置开始角度
        pieplot3d.setStartAngle(120D);
        //设置方向为”顺时针方向“
        pieplot3d.setDirection(Rotation.CLOCKWISE);
        //设置透明度，0.5F为半透明，1为不透明，0为全透明
        pieplot3d.setForegroundAlpha(0.7F);

        return chart;
    }

    /**
     * 根据时间序列生产line chart
     */
    public static JFreeChart getLineChart(TimeSeries timeSeries){
        // 定义时间序列的集合
        TimeSeriesCollection lineDataset=new TimeSeriesCollection();
        lineDataset.addSeries(timeSeries);

        JFreeChart chart=ChartFactory.createTimeSeriesChart("销量统计时间折线图", "月份", "销量", lineDataset, true, true, true);

        //设置主标题
        chart.setTitle(new TextTitle("销售统计", new Font("隶书", Font.ITALIC, 15)));
        //设置子标题
        TextTitle subtitle = new TextTitle("2018年度", new Font("黑体", Font.BOLD, 12));
        chart.addSubtitle(subtitle);
        chart.setAntiAlias(true);

        //设置时间轴的范围。
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis dateaxis = (DateAxis)plot.getDomainAxis();
        dateaxis.setDateFormatOverride(new java.text.SimpleDateFormat("M月"));
        dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH,1));

        //设置曲线是否显示数据点
        XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer)plot.getRenderer();
        xylinerenderer.setBaseShapesVisible(true);

        //设置曲线显示各数据点的值
        XYItemRenderer xyitem = plot.getRenderer();
        xyitem.setBaseItemLabelsVisible(true);
        xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 12));
        plot.setRenderer(xyitem);

        return chart;
    }
}
