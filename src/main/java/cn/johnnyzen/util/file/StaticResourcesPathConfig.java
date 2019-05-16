package cn.johnnyzen.util.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2018/11/6  11:41:16
 * @Description: 实现对静态资源的路径开放
 * @Reference: 参考文献 https://blog.csdn.net/superlover_/article/details/80893007
 */

@Configuration
public class StaticResourcesPathConfig extends WebMvcConfigurerAdapter {

    //静态资源对外暴露的访问路径
    @Value("${file.staticAccessRootPath}")
    private String staticAccessRootPath;

    //文件上传/下载的实际物理根目录（注意Linux和Windows上的目录结构不同）
    //Eg for Linux: file.uploadFolder=/root/uploadFiles/
    //Eg for Windows: file.uploadFolder=d://uploadFiles/
    @Value("${file.staticRealRootPath}")
    private String staticRealRootPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessRootPath).addResourceLocations("file:" + staticRealRootPath);
        super.addResourceHandlers(registry);
    }

}