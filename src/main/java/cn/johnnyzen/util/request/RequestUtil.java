package cn.johnnyzen.util.request;

import cn.johnnyzen.util.collection.CollectionUtil;
import cn.johnnyzen.util.datetime.DatetimeUtil;
import cn.johnnyzen.util.print.Print;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2018/10/7  15:51:28
 * @Description: ...
 *      @Component:
 *       将本Bean置入Spring容器中，形成绑定
 *       把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
 *       定义Spring管理Bean.
 */
public class RequestUtil {
    private static final Logger logger = Logger.getLogger(RequestUtil.class.getName());

    private static String logPrefix = null;

    /**
     * 为webDriver设置Cookie
     * + 测试有效
     * @param webDriver
     * @param cookies
     */
    public synchronized static void addCookiesForWebDriver(WebDriver webDriver,Collection cookies){
        List<Cookie> cookiesList = null;
        cookiesList = CollectionUtil.collectionToList(cookies);
        if(cookiesList.size()>0){
            System.out.print("cookies is not null!");
            for(int i=0;i<cookies.size();i++){
                webDriver.manage().addCookie(cookiesList.get(i));//添加Cookie
            }
        }
    }

    /**
     * 通过本地文件获取 Cookie集
     * @param cookiesFilePath 形如："C:\\Users\\千千寰宇\\Desktop\\cookies.txt"
     * @param isOuputLogToConsole 是否将信息打印到控制台(调试时可使用)
     * @return cookies[Set<Cookie>]
     *  + cookies[size=0]
     *  + cookies[size>0]
     *  + (绝不存在null)
     *
     * [notice]
     * <1> 针对的主要调用方：chromeDriver.manage().addCookie(cookies[i]);//添加Cookie
     * <2> 文件内容格式
     *  Name	Value					              Domain	    Path	Expires/Max-Age             Size
     *  BAIDUID	44D362CBDBCCBCFDAA328F1F23A8DB5B:FG=1 N/A	        /	    2019-06-04T06:10:48.775Z    47
     *  BDORZ	B490B5EBF6F3CD402E515D22BCDA1598	  .baidu.com	N/A	    N/A	                        40
     *  ...(more)
     *  <3> 文件中的各列数据【必须且仅支持】以上列出的6项，不能多也不能少！(否则将报错)
     *  <4> 测试时使用的默认字符集编码：ANSI
     */
    public static HashMap<String,Cookie> getCookiesFromLocalFile(boolean isOuputLogToConsole,String cookiesFilePath){
        logPrefix="[RequestUtil.getCookiesFromLocalFile] ";
        Print.print(isOuputLogToConsole,logPrefix+"start execute cookies file '"+cookiesFilePath +"'.");
        HashMap<String,Cookie> cookies = new HashMap<String,Cookie>();//key:cookieName,value:Cookie Object
        BufferedReader bufferedReader=null;
//        String cookiesFilePath = "C:\\Users\\千千寰宇\\Desktop\\cookies.txt";
        FileReader fileReader = null;
        File cookiesFile = new File(cookiesFilePath);
        if(!cookiesFile.exists()){//IF 文件不存在
            logger.warning(logPrefix+"Fail to invoke!Not found the file '"+cookiesFilePath+"'!");
            return cookies;//size:0
        }
        try {
            fileReader = new FileReader(cookiesFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bufferedReader = new BufferedReader(fileReader);
        String line=null;
        try {
            line = bufferedReader.readLine();//默认忽略 第一行 [Cookie的各列备注信息]
        } catch (IOException e) {
            e.printStackTrace();
            logger.warning(logPrefix+"Fail to invoke!Because: Fail to read first line data!");
            return cookies;//size:0
        }

        try {
            while ((line = bufferedReader.readLine()) != null){
                    StringTokenizer stringTokenizer = new StringTokenizer(line);//java默认的分隔符是“空格”、“制表符（‘\t’）”、“换行符(‘\n’）”、“回车符（‘\r’）”。
                    while (stringTokenizer.hasMoreTokens()){
                        Print.print(isOuputLogToConsole,"↓+++++++++++++++++++++++++++++++↓");//test

                        String name = stringTokenizer.nextToken();
                        Print.print(isOuputLogToConsole,"name:"+name);//test

                        String value = stringTokenizer.nextToken();
                        Print.print(isOuputLogToConsole,"value:"+value);//test

                        String domain = null;
                        domain = stringTokenizer.nextToken();
                        Print.print(isOuputLogToConsole,"domain:"+domain);//test
                        if(domain!=null && domain.equals("N/A")){
                            domain = null;
                        }

                        String path = stringTokenizer.nextToken();
                        Print.print(isOuputLogToConsole,"path:"+path);//test
                        if(path!=null && path.equals("N/A")){
                            path = null;
                        }

                        String expiresOrMaxAgeStr = stringTokenizer.nextToken();
                        Print.print(isOuputLogToConsole,"Expires/Max-Age:"+expiresOrMaxAgeStr);
                        if(expiresOrMaxAgeStr!=null && expiresOrMaxAgeStr.equals("N/A")){
                            expiresOrMaxAgeStr = null;
                        }
                        Date expiry = null;
                        if(expiresOrMaxAgeStr!=null){
                            expiry = DatetimeUtil.stringToDate(expiresOrMaxAgeStr,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//"2019-06-04T06:10:48.775Z"  "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
                        }

                        String size = stringTokenizer.nextToken();
                        Print.print(isOuputLogToConsole,"size:"+size);

                        Print.print(isOuputLogToConsole,"↑+++++++++++++++++++++++++++++++↑");//test

                        boolean isSecure = false;//设置浏览器是否仅仅使用安全协议来发送cookie，例如使用Https或ssl
                        Cookie cookie = new Cookie(name, value,domain,path,expiry,isSecure);
                        Print.print(isOuputLogToConsole,"cookie:\n"+cookie);
                        cookies.put(name,cookie);
                    }
                }
        } catch (IOException e) {//加载第二行开始的cookie信息失败
            e.printStackTrace();
            logger.warning(logPrefix+"Fail to invoke!Because: Fail to read cookie data after first line!");
            return cookies;//size: 0 or other
        } catch (ParseException e) {//解析日期失败
            e.printStackTrace();
            logger.warning(logPrefix+"Fail to parse dateStr as Object 'expire' for 'DatetimeUtil.stringToDate'!");
            return cookies; //size: 0 or other
        }
        return cookies;//size: 0 or other
    }

    /**
     * 从JS引擎中，获得变量值
     */
    public static <T> T getVariableValue(ScriptEngine engine,String variableName){
        T value = null;
        value = (T) engine.get(variableName);
        return  value;
    }

    /**
     * 判断uri是否是api请求(返回json)
     *  即：路径中是否包含：/api/
     * @Author: xxxxx
     * @Description: 是否需要过滤
     * @Date: 2018-03-12 13:20:54
     * @param uri
     */
    public static boolean isApiURI(String uri){
        String [] dirs = uri.split("/");
        for(String dir:dirs){
            if(dir.equalsIgnoreCase("API")){
                return true;//API URI
            }
        }
        return false;
    }
    //URL编码
    public static String urlEncode(String query) {
        String result = "";
        if (query == null) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    //URL解码
    public static String urlDecode(String encodeStr) {
        String result = "";
        if (null == encodeStr) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(encodeStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     *  获取指定HTML标签的指定属性的值
     * @param source 要匹配的源文本
     * @param element 标签名称
     * @param attr 标签的属性名称
     * @return 属性值列表
     */
    public static List<String> matchHTMLAttribute(String source, String element, String attr) {
        List<String> result = new ArrayList<String>();
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?(\\s.*?)?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result.add(r);
        }
        return result;
    }

    public static ScriptEngine getJavaScriptEngine(){
        ScriptEngineManager manager = new ScriptEngineManager();//JS执行引起
        ScriptEngine engine = manager.getEngineByName("javascript");
        return engine;
    }

    /**
     * 通过 JS代码片段，获取HTML中某一<script>标签的内置代码
     */
    public static String findJavaScriptCodeOfHTMLByScriptFragment(String sourceHtml,String scriptFragment){
        logPrefix="[RequestUtil.findJavaScriptCodeOfHTMLByScriptFragment] ";
        if(sourceHtml==null ||scriptFragment==null){
            logger.warning(logPrefix+"sourceHtml or scriptFragment is null!");
            return null;
        } else {
            scriptFragment = scriptFragment.trim();
        }
        Elements scripts = null;
        scripts =  Jsoup.parse(sourceHtml).select("script");
        if(scripts==null||scripts.size()<1){
            return null;
        }
        //查找含有代码片段的js脚本
        int scirptindex = -1;//标记脚本位置
        for(int i=0;i<scripts.size();i++){
            if(scripts.get(i).html().contains(scriptFragment)){
                scirptindex=i;
//                System.out.println("script["+i+"]:\n"+scripts.get(i).html());//test
            }
        }
        if(scirptindex==-1){//未找到
            return null;
        }
        return scripts.get(scirptindex).html();
    }

    /**
     * [建议]
     *  也可用 Jsoup.parse(html)方法来获取/操纵 HTML的DOM元素
     * @param source 要匹配的源文本
     * @param element 标签名称
     * @return  内容集合
     */
    public static List<String> matchHTMLTagContent(String source, String element) {
        List<String> result = new ArrayList<String>();
        String reg = "<" + element + ">" + "(.+?)</" + element + ">";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result.add(r);
        }
        return result;
    }

    /**
     * 获取指定HTML标签的指定属性的值
     * @param source 要匹配的源文本
     * @param element 标签名称
     * @param attr 标签的属性名称
     * @return 属性值列表
     */
    public static List<String> match(String source, String element, String attr) {
        List<String> result = new ArrayList<String>();
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?(\\s.*?)?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result.add(r);
        }
        return result;
    }

    /**
     * 通过url获取第三方的HTML/JSON等网络响应文档(数据)
     * @param url
     */
    public Document getDocument(String url) throws IOException {
        Document doc = null;
        doc = Jsoup.connect(url)
//                .header("Cache-Control", requestProperties.getCacheControl())
//                .header("Connection", requestProperties.getConnection())
//                .header("Accept", requestProperties.getAccept())
//                .header("Accept-Encoding", requestProperties.getAcceptEncoding())
//                .header("Accept-Language", requestProperties.getAcceptLanguage())
//                .header("Set-Cookie", requestProperties.getCookie())
//                .userAgent(requestProperties.getUserAgent())
                .ignoreContentType(true) //resolve:org.jsoup.UnsupportedMimeTypeException: Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml. Mimetype=application/x-javascript, URL=www.a.com
                .get();
        return doc;
    }
}
