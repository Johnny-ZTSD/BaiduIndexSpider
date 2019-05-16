# 解析百度指数的爬虫 / BaiduIndexSpider

## 当前版本(Version):
+ 1.0.1
    + publish datetime:2019-05-16 15:23
## 基本原理(Basic Principle)
+ 基本原理
    + 通过模拟鼠标滑动来解析百度指数网页的数据，存放于HTML页面中
    + 通过ChromeDriver控制浏览器获取上一步骤中js解析生成的数据
+ 针对上一版本进行改进
    + 按照完全面向对象的程序设计方法将脚本模块化
    + 含 Point,Mouse,BaiduIndex,BaiduIndexTask 四个类
    + 采用了JavaScript ES6的语法，如class、let等。
+ 本脚本仅支持 IE8+的浏览器
+ 优先支持 Chrome 浏览器
+ 针对不同电脑终端的屏幕尺寸，其(resolveBaiduIndexByJs.js)的鼠标坐标参数需要自行计算

## 依赖项(Dependency)
+ 安装浏览器(推荐：Chrome)
+ 安装 WebDriver(本项目推荐安装：ChromeDriver)
    + 安装完成后修改[BrowserDriverSpiderUtil.java](https://github.com/Johnny-ZTSD/BaiduIndexSpider/blob/master/src/main/java/cn/johnnyzen/util/spider/BrowserDriverSpiderUtil.java)中属性systemPropertyValueOfBrowser的WebDriver.exe的存放路径
    + [注意] 最好是安装与浏览器版本相对应/兼容的 WebDriver

## 项目使用方式(Method of use)
+ 从本项目中下载核心[BaiduIndexService.java](https://github.com/Johnny-ZTSD/BaiduIndexSpider/blob/master/src/main/java/cn/johnnyzen/app/spider/BaiduIndexService.java)类
+ 从本项目中下载该类中import所需要其他依赖类

## 测试(Test)
``` java
    @Test
    public  void resolveBaiduIndexValuesTest() {
        String query = "北京房价";
    //        WebDriver webDriver =BaiduIndex.loadBaiduIndexPageByWebDriver(query);
    //        System.out.println(webDriver.findElement(By.cssSelector("html")).getText());
        Print.print(BaiduIndexService.resolveBaiduIndexValues(query));//获取解析的数据
    }
```

## 开源协议(LGPL, GNU Lesser General Public License )
+ 基于MIT
    + 1.MIT是和BSD一样宽范的许可协议，作者只想保留版权，而无任何其它的限制。
        + 即 你必须在你的发行版里包含原许可协议的声明，无论你是以二进制发布的还是以源代码发布的。
    + 2.MIT协议又称麻省理工学院许可证，最初由麻省理工学院开发。
    + 3.被授权人权利：1、被授权人有权利使用、复制、修改、合并、出版发行、散布、再授权及贩售软件及软件的副本。
    + 4.被授权人可根据程式的需要修改授权条款为适当的内容。
    + 5.被授权人义务：【在软件和软件的所有副本中都必须包含版权声明和许可声明】。

## 作者(Author)
+ Johnny Zen
    + [github] https://github.com/Johnny-ZTSD

## 联系邮箱(Email)
+ johnnyztsd@gmail.com
