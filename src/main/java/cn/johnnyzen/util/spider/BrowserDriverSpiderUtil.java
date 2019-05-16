package cn.johnnyzen.util.spider;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2019/4/26  09:56:05
 * @Description:
 *  + 移动设备user-agent表格
 *      + 备注 移动版网站的反爬虫的能力一般比较弱
 *      + url http://www.fynas.com/ua
 *  + 推荐文献
 *      + [ChromeDriver官方文档- Google](https://sites.google.com/a/chromium.org/chromedriver/capabilities)
 *      + [chrome浏览器的options参数(强烈推荐)](https://blog.csdn.net/xc_zhou/article/details/82415870)
 */
@Component
public class BrowserDriverSpiderUtil {
    private static String systemPropertyNameOfBrowser="webdriver.chrome.driver";

    private static String systemPropertyValueOfBrowser="D:\\Program Files (x86)\\Chrome-Driver\\chromedriver.exe";

    static {
        System.setProperty(systemPropertyNameOfBrowser,systemPropertyValueOfBrowser);
    }

    public synchronized static ChromeDriver getChromeDriver(){
        return getChromeDriver(true);//默认：开启无头模式
    }

    public final static String userAgent(){
        return "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36";
    }

    /**
     * @param isHeadless 是否开启无头模式
     *    + true  开启无头模式
     *    + false 关闭无头模式
     */
    public synchronized static ChromeDriver getChromeDriver(boolean isHeadless){
        ChromeOptions chromeOptions;
        chromeOptions = new ChromeOptions();

        if(isHeadless){
            chromeOptions.setHeadless(true);//开启无头模式 方式一
//            chromeOptions.addArguments("--headless");//无头模式 方式二
        }
//        chromeOptions.addArguments("--disable-images");//禁用图像[测试无效] 其他："--disable-plugins","--start-maximized","--disable-javascript"
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.managed_default_content_settings.images", 2);//禁用图像[测试有效]
//        chromeOptions.setExperimentalOption("prefs", prefs);

//        chromeOptions.addArguments("--referer=http://www.baidu.com");//设置refer[未经测试]

        chromeOptions.addArguments("--http_proxy=118.18.188.188");

//        chromeOptions.addArguments("--user-agent=Apple Iphone 5");//设置user agent为iPhone5
//        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 6.0.1; Nexus 6P Build/MMB29P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.83 Mobile Safari/537.36");
//        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac\n" + "OS X; en-us) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53");
        chromeOptions.addArguments("--user-agent="+userAgent());
        //        chromeOptions.addArguments("--lang=zh_CN.UTF-8");//设置语言

        ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);
        chromeDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);//隐式等待15秒
        return chromeDriver;
    }

    public static String getSystemPropertyNameOfBrowser() {
        return systemPropertyNameOfBrowser;
    }

    public static void setSystemPropertyNameOfBrowser(String systemPropertyNameOfBrowser) {
        BrowserDriverSpiderUtil.systemPropertyNameOfBrowser = systemPropertyNameOfBrowser;
    }

    public static String getSystemPropertyValueOfBrowser() {
        return systemPropertyValueOfBrowser;
    }

    public static void setSystemPropertyValueOfBrowser(String systemPropertyValueOfBrowser) {
        BrowserDriverSpiderUtil.systemPropertyValueOfBrowser = systemPropertyValueOfBrowser;
    }
}
