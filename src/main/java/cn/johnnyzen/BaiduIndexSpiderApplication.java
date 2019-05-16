package cn.johnnyzen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2019/5/16  13:28:44
 * @Description:
 *  [1] SpringBoot 取消数据库连接配置
 *      https://blog.csdn.net/u012240455/article/details/82356075
 *  [2] spring boot 搭建web项目常见五种返回形式
 *      https://www.cnblogs.com/ben-mario/p/9075042.html
 */

@Controller
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})//取消数据库配置
@ServletComponentScan
public class BaiduIndexSpiderApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaiduIndexSpiderApplication.class, args);
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
