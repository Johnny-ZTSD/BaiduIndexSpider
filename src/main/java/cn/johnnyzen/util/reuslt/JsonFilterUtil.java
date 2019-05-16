package cn.johnnyzen.util.reuslt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2018/11/3  19:35:33
 * @Description: json 属性过滤器,利用Jackson的JsonFilter来实现动态过滤数据列（数据列权限控制）
 * @Reference: https://blog.csdn.net/adsadadaddadasda/article/details/81613193
 */

public class JsonFilterUtil {
    public static ObjectMapper ObjectMapperOfCloseSpecifiedFields(String[] beanCloseFileds,String jsonFilterId){
        ObjectMapper mapper = new ObjectMapper();
//        String[] beanProperties = new String[]{"password"};
        String[] beanProperties = beanCloseFileds;
//        String nonPasswordFilterName = "non-password";//需要跟User类上的注解@JsonFilter("non-password")里面的一致
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter(jsonFilterId, SimpleBeanPropertyFilter.serializeAllExcept(beanProperties));
        //serializeAllExcept 表示序列化全部，除了指定字段
        //filterOutAllExcept 表示过滤掉全部，除了指定的字段
        mapper.setFilterProvider(filterProvider);
        return mapper;
    }
}
