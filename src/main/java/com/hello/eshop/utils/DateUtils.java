package com.hello.eshop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date转换类
 * Created by Hello on 2018/3/30.
 */
public class DateUtils {

    public static String MONTH_PATTERN = "yyyy-MM";

    public static String DATE_PATTERN = "yyyy-MM-dd";

    public static String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String TIMESTAMP_PATTERN = "yyyyMMddhhmmssSSS";

    /**
     * 日期对象转字符串
     */
    public static String formatDate(Date date,String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 字符串转日期对象
     */
    public static Date paraseDate(String source,String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(source);
    }

    /**
     * 生成当前日期时间串
     * @return
     * @throws Exception
     */
    public static String getCurrentDateStr(){
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat(TIMESTAMP_PATTERN);
        return sdf.format(date);
    }
}
