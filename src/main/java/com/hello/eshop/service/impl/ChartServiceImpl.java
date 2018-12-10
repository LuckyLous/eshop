package com.hello.eshop.service.impl;

import com.hello.eshop.bean.Chart;
import com.hello.eshop.dao.ChartMapper;
import com.hello.eshop.dao.OrderMapper;
import com.hello.eshop.service.ChartService;
import org.jfree.data.category.DefaultCategoryDataset;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Hello on 2018/3/29.
 */

@Service
public class ChartServiceImpl implements ChartService{

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ChartMapper chartMapper;

    /**
     * 返回指定日期的完成订单
     * @return
     */
    @Override
    public List<Chart> getByDate(Date date) {
        List<Chart> list = orderMapper.selectByDate(date);
        return list;
    }

    /**
     * 查询数据集
     * @param date
     * @return
     */
    @Override
    public DefaultCategoryDataset getSellTop(Date date) {
        // 1.查询数据
        List<Chart> list = chartMapper.getSellTop(date);

        // 2.将数据放到数据集
        DateTime dateTime = new DateTime(date.getTime());//转换格式
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Chart chart:list) {
            dataset.addValue(chart.getNum(), dateTime.getMonthOfYear()+"月", chart.getName());
        }

        return dataset;
    }
}
