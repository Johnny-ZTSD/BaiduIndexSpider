package cn.johnnyzen.app.spider;

import cn.johnnyzen.util.collection.CollectionUtil;
import cn.johnnyzen.util.file.FileUtil;
import cn.johnnyzen.util.print.Print;
import cn.johnnyzen.util.request.RequestUtil;
import cn.johnnyzen.util.spider.BrowserDriverSpiderUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2019/5/15  09:13:29
 * @Description: 百度指数
 */

public class BaiduIndexService {
    /**
     * 百度指数的首页(的详细)url
     * + 方便后续爬虫在业务流程中的逻辑判断
     */
    private  final static String indexUrl = "http://index.baidu.com/v2/index.html";

    /**
     * 存放百度指数(index.baidu.com)网站的Cookie文件
     */
    private final static String baiduIndexCookiesFilePath = "C:\\Users\\千千寰宇\\Desktop\\cookies-baidu-index.txt";

    /**
     * 注入百度指数网站JS的本地js文件存放路径
     */
    private final static String jsFilePath = "C:\\Users\\千千寰宇\\Desktop\\insert-script.js";

    /**
     * 百度指数网站的用户名
     */
    private final static String username = "######@qq.com";

    /**
     * 百度指数网站的用户密码
     */
    private final static String password = "########";

    /**
     * 模拟登陆
     * @param webDriver 此时传入的webDriver正处于显示了登陆提示框的页面
     * @return WebDriver 已经登陆后的WebDriver(方便进行后续操作)
     * [demo]
     * + 传入的webDriver可能处于这样的url中
     *      + http://index.baidu.com/v2/index.html#/?login=1&fromu=http%3A%2F%2Findex.baidu.com%2Fv2%2Fmain%2Findex.html%23%2Ftrend%2F%25E5%258C%2597%25E4%25BA%25AC%25E6%2588%25BF%25E4%25BB%25B7%3Fwords%3D%25E5%258C%2597%25E4%25BA%25AC%25E6%2588%25BF%25E4%25BB%25B7
     * [notice]
     * + 等待是为了避开出现验证码
     */
    public static ChromeDriver simulateLoginByWebDriver(ChromeDriver webDriver){
        if(webDriver==null){//must be not null
            return null;
        }
        webDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);//隐式等待
        webDriver.findElement(By.cssSelector("#TANGRAM__PSP_4__userName")).sendKeys(username);

        webDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        webDriver.findElement(By.cssSelector("#TANGRAM__PSP_4__password")).sendKeys(password);

        webDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        webDriver.findElement(By.cssSelector("#TANGRAM__PSP_4__submit")).click();

        webDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
//        System.out.println("[#simulateLoginByWebDriver] now url:\n"+webDriver.getCurrentUrl());//just for test 显示当前url
        webDriver.navigate().refresh();//刷新当前页面(可能还是在首页，也可能是登陆后的页面)
//        System.out.print("[#simulateLoginByWebDriver] current page's html text:\n"+webDriver.findElement(By.cssSelector("html")).getText());//just for test
        return webDriver;
    }

    /**
     * 通过Web浏览器驱动加载待检索关键词的百度指数页面
     * @param query 查询的关键词
     * @return webDriver  此时可直接操纵它来获取/操纵想要的网页数据或者行为
     *
     * [dependency] 本函数处理过程中，依赖于开发者的其他一些类
     *  + BrowserDriverSpiderUtil
     *      + getChromeDriver()
     *  + RequestUtil
     *      + urlEncode()
     *      + getCookiesFromLocalFile()
     *      + addCookiesForWebDriver()
     *  + CollectionUtil
     *      + collectionToList
     */
    public static ChromeDriver loadBaiduIndexPageByWebDriver(String query){
        //step1 生成目标查询链接
        String queryMainUrl = "http://index.baidu.com/v2/main/index.html#/trend/"+ RequestUtil.urlEncode(query)+"?words="+RequestUtil.urlEncode(query);//queryMainUrl：百度指数查询后的(结果)主页
        //step2 获取已经过初始化的 WebDriver (默认：ChromeDriver)
        ChromeDriver webDriver = BrowserDriverSpiderUtil.getChromeDriver(false);//isHeadless：false 关闭无头模式：查看运行过程，方便调试
        //step3 直接请求queryMainUrl链接
        webDriver.get(queryMainUrl);
        webDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);//启动请求后，隐式等待 1s 模拟真实情况，以防止被网站察觉是爬虫
        //step4 添加Cookie到webDriver中
        List<Cookie> cookies = null;
        cookies = CollectionUtil.collectionToList(RequestUtil.getCookiesFromLocalFile(true,baiduIndexCookiesFilePath).values());
        RequestUtil.addCookiesForWebDriver(webDriver,cookies);

        String currentUrl = webDriver.getCurrentUrl();//获取当前webDriver所处的url
        System.out.println("now url:\n"+currentUrl);//just for test
        //step5 判断当前是否处于百度指数的结果页
        if(currentUrl.startsWith(indexUrl)){//当前页面是首页Url（即 要求登录）
            //step5.1 模拟登陆
            webDriver = simulateLoginByWebDriver(webDriver);
            currentUrl = webDriver.getCurrentUrl();
            if(currentUrl.startsWith(indexUrl)) {//如果当前页面还是首页(即 虽然已经登陆成功，但并没有处于爬虫期待的 queryMainUrl 的数据页面)
                //方式一 再次【重新】请求 queryMainUrl
                webDriver.get(queryMainUrl);
                webDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);//隐式等待
                System.out.println("[#loadBaiduIndexPageByWebDriver] now url:\n"+currentUrl);//just for test
                //方式二 在当前所处的百度指数首页中模拟输入我们要查询的关键词，将会跳转到 queryMainUrl 页面
            }
        } else {//第一次请求便已成功获取到百度指数页(queryMainUrl)
        }
        System.out.println("now url:\n"+currentUrl);//just for test
//        System.out.print(webDriver.findElement(By.cssSelector("html")).getText());//just for test
//        webDriver.quit();//退出webDriver浏览器
        webDriver.navigate().refresh();//刷新一次
        return webDriver;
    }

    public static List<BaiduIndex> resolveBaiduIndexValues(String query){
        List<BaiduIndex> baiduIndexs = new ArrayList<BaiduIndex>();
        //step 1 加载百度指数的查询数据页
        ChromeDriver webDriver = (ChromeDriver) BaiduIndexService.loadBaiduIndexPageByWebDriver(query);//向下转型回 ChromeDriver,否则WebDriver没有执行JS脚本的方法
        //step 2 解析数据(By JS注入)
        String jsCode = FileUtil.readFile(jsFilePath);

        webDriver.manage().window().maximize();       //将浏览器窗口最大化
        try{
            //step 3 插入解析百度指数的[js文件]脚本入百度指数网页中 RequestUtil.getJavaScriptEngine();
            //为什么是插入js文件，而不是直接执行js脚本？因为：通过 webDriver 可执行的部分 js 函数无法正常执行，故而采取此下策。
            webDriver.executeScript(jsCode);
        } catch (Exception e){
            e.printStackTrace();
            System.err.println("[BaiduIndex.resolveBaiduIndexValues] Fail to execute javascript code!");
            return baiduIndexs;//size:0
        }

        ObjectMapper mapper = new ObjectMapper();//jackson
        String baiduIndexsJsonStr = null;
        //step 4 等待鼠标模拟滑动js脚本执行完毕
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);//隐式等待
        //step 5 获取脚本采集好的json数据(格式：百度指数json数组)
        baiduIndexsJsonStr = webDriver.findElement(By.cssSelector("#webDriver_housePrices")).getText().trim();//获取js脚本产生的json字符串
        try {
            if(baiduIndexsJsonStr!=null){
                //step 6 通过jackson将json字符串转为 Java (数组)对象
                baiduIndexs = mapper.readValue(baiduIndexsJsonStr, new TypeReference<List<BaiduIndex>>() {});
            } else {
                System.err.println("[BaiduIndex.resolveBaiduIndexValues] baiduIndexsJsonStr is null!");
                return baiduIndexs;//size:0
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("[BaiduIndex#resolveBaiduIndexValues] jsCode:\n"+jsCode+"\n");//test
////        webDriver.executeScript("var housePricesJsonStr = JSON.stringify(housePrices);");
//        System.out.println("[BaiduIndex#resolveBaiduIndexValues] now url:\n"+webDriver.getCurrentUrl()+"\n");
////        String housePricesStr = (String) webDriver.executeScript("housePricesJsonStr");//test
////        System.out.println("housePricesStr:\n"+housePricesStr);//test
//        System.out.println("[BaiduIndex#resolveBaiduIndexValues] enable JS?"+webDriver.getCapabilities().isJavascriptEnabled());//test
        return baiduIndexs;
    }

    public static void main(String[] args) {
        String query = "北京房价";
//        WebDriver webDriver =BaiduIndex.loadBaiduIndexPageByWebDriver(query);
//        System.out.println(webDriver.findElement(By.cssSelector("html")).getText());
        Print.print(BaiduIndexService.resolveBaiduIndexValues(query));//获取解析的数据
    }
}
