package com.hello.eshop.convert;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SpringMVC 日期转换器
 * 接受日期格式的数据并转为为Date类型
 * Created by Hello on 2018/4/18.
 */
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        String pattern = source.length() == 7 ? "yyyy-MM" : "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
