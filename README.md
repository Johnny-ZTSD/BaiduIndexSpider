# 解析百度指数的爬虫 / BaiduIndexSpider

## 当前版本(Version):
+ 1.0.1
    + publish datetime:2019-05-16 15:23

## 开源协议(LGPL, GNU Lesser General Public License )
+ 基于MIT
    + 1.MIT是和BSD一样宽范的许可协议，作者只想保留版权，而无任何其它的限制。
        + 即 你必须在你的发行版里包含原许可协议的声明，无论你是以二进制发布的还是以源代码发布的。
    + 2.MIT协议又称麻省理工学院许可证，最初由麻省理工学院开发。
    + 3.被授权人权利：1、被授权人有权利使用、复制、修改、合并、出版发行、散布、再授权及贩售软件及软件的副本。
    + 4.被授权人可根据程式的需要修改授权条款为适当的内容。
    + 5.被授权人义务：【在软件和软件的所有副本中都必须包含版权声明和许可声明】。

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
+ 项目特色：基本原理的实现过程即已提供了一种新的爬虫思路。

## 依赖项(Dependency)
+ 安装浏览器(推荐：Chrome)
+ 安装 WebDriver(本项目推荐安装：ChromeDriver)
    + 安装完成后修改[BrowserDriverSpiderUtil.java](https://github.com/Johnny-ZTSD/BaiduIndexSpider/blob/master/src/main/java/cn/johnnyzen/util/spider/BrowserDriverSpiderUtil.java)中属性systemPropertyValueOfBrowser的WebDriver.exe的存放路径
    + [注意] 最好是安装与浏览器版本相对应/兼容的 WebDriver

## 项目使用方式(Method of use)
+ 从本项目中下载核心[BaiduIndexService.java](https://github.com/Johnny-ZTSD/BaiduIndexSpider/blob/master/src/main/java/cn/johnnyzen/app/spider/BaiduIndexService.java)类
+ 从本项目中下载该类中import所需要其他依赖类

## 测试(Test)
+ [视频演示链接:百度网盘]
    + link: [https://pan.baidu.com/s/1iQbWHfT5_SKA3omK9nFgYg](https://pan.baidu.com/s/1iQbWHfT5_SKA3omK9nFgYg)
    + code: 5gpi
``` java
    @Test
    public  void resolveBaiduIndexValuesTest() {
        String query = "北京房价";
    //        WebDriver webDriver =BaiduIndex.loadBaiduIndexPageByWebDriver(query);
    //        System.out.println(webDriver.findElement(By.cssSelector("html")).getText());
        Print.print(BaiduIndexService.resolveBaiduIndexValues(query));//获取解析的数据
    }
```

```
// output
HousePrice{
keyword='北京房价',
 date=java.util.GregorianCalendar[time=1555372800000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="UTC",offset=0,dstSavings=0,useDaylight=false,transitions=0,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2019,MONTH=3,WEEK_OF_YEAR=16,WEEK_OF_MONTH=3,DAY_OF_MONTH=16,DAY_OF_YEAR=106,DAY_OF_WEEK=3,DAY_OF_WEEK_IN_MONTH=3,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=0,ZONE_OFFSET=0,DST_OFFSET=0],
 indexValue=2569} HousePrice{
keyword='北京房价',
 date=java.util.GregorianCalendar[time=1555459200000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="UTC",offset=0,dstSavings=0,useDaylight=false,transitions=0,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2019,MONTH=3,WEEK_OF_YEAR=16,WEEK_OF_MONTH=3,DAY_OF_MONTH=17,DAY_OF_YEAR=107,DAY_OF_WEEK=4,DAY_OF_WEEK_IN_MONTH=3,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=0,ZONE_OFFSET=0,DST_OFFSET=0],
 indexValue=2311} HousePrice{
keyword='北京房价',
 date=java.util.GregorianCalendar[time=1555545600000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="UTC",offset=0,dstSavings=0,useDaylight=false,transitions=0,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2019,MONTH=3,WEEK_OF_YEAR=16,WEEK_OF_MONTH=3,DAY_OF_MONTH=18,DAY_OF_YEAR=108,DAY_OF_WEEK=5,DAY_OF_WEEK_IN_MONTH=3,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=0,ZONE_OFFSET=0,DST_OFFSET=0],
 indexValue=2318} HousePrice{
keyword='北京房价',
 date=java.util.GregorianCalendar[time=1555632000000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="UTC",offset=0,dstSavings=0,useDaylight=false,transitions=0,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2019,MONTH=3,WEEK_OF_YEAR=16,WEEK_OF_MONTH=3,DAY_OF_MONTH=19,DAY_OF_YEAR=109,DAY_OF_WEEK=6,DAY_OF_WEEK_IN_MONTH=3,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=0,ZONE_OFFSET=0,DST_OFFSET=0],
 indexValue=2207} HousePrice{
keyword='北京房价',
 //... more
 date=java.util.GregorianCalendar[time=1557878400000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="UTC",offset=0,dstSavings=0,useDaylight=false,transitions=0,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2019,MONTH=4,WEEK_OF_YEAR=20,WEEK_OF_MONTH=3,DAY_OF_MONTH=15,DAY_OF_YEAR=135,DAY_OF_WEEK=4,DAY_OF_WEEK_IN_MONTH=3,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=0,ZONE_OFFSET=0,DST_OFFSET=0],
 indexValue=2142} 
```

## 作者(Author)
+ Johnny Zen
    + [github] https://github.com/Johnny-ZTSD

## 项目联系邮箱(Email)
+ johnnyztsd@gmail.com
