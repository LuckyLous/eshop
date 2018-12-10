package com.hello.eshop.service;

import com.hello.eshop.bean.Chart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Date;
import java.util.List;

/**
 * Created by Hello on 2018/3/29.
 */
public interface ChartService {
    /**
     * 根据日期查询已完成的订单
     * @param date
     * @return
     */
    List<Chart> getByDate(Date date);

    /**
     * 查询销量前5的商品，封装成bar数据集
     * @return
     * @param date
     */
    DefaultCategoryDataset getSellTop(Date date);


}
