package com.hello.eshop.dao;

import com.hello.eshop.bean.Chart;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Hello on 2018/3/29.
 */
public interface ChartMapper {

    /**
     * 按日期查询销量最高的5个商品
     */
    List<Chart> getSellTop(@Param("date") Date date);

}
