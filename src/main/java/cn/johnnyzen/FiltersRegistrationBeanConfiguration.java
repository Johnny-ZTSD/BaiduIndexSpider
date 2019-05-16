package cn.johnnyzen;

import cn.johnnyzen.util.filter.CosFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2019/4/22  10:22:09
 * @Description: ...
 *   在springboot添加过滤器有两种方式：
 *　1、通过创建FilterRegistrationBean的方式
 *  （建议使用此种方式，统一管理，且通过注解的方式若不是本地调试，如果在filter中需要增加cookie可能会存在写不进前端情况）
 *　2、通过注解@WebFilter的方式
 *  3、order值越小，越先执行
 */
@Configuration
public class FiltersRegistrationBeanConfiguration {
    @Bean //filter3 注册并申请挂载到filter链上
    public FilterRegistrationBean cosFilterRegistrationBean() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加我们写好的过滤器
        registration.setFilter( new CosFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/*");
        //设置过滤器执行优先级order(越小，执行优先级越高)
        registration.setOrder(3);

        //设置过滤器名
        registration.setName("cosFilter");
        return registration;
    }


    @Bean //filter1 注册并申请挂载到filter链上,无Bean，将不会注入IOC
    public FilterRegistrationBean characterEncodingFilterRegistrationBean() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加我们写好的过滤器
        CharacterEncodingFilter c = new CharacterEncodingFilter();
        c.setEncoding("UTF-8");
        registration.setFilter(c);
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/*");
        //设置过滤器执行优先级order(越小，执行优先级越高)
        registration.setOrder(1);

        //设置过滤器名
        registration.setName("characterEncodingFilter");
        return registration;
    }
}