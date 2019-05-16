/**
 * resolveBaiduIndexByJs.js / 解析百度指数
 * @description 通过模拟鼠标滑动来解析百度指数网页的js脚本
 * @version 1.0.1 - 正式版
 * @copyright base MIT [基于MIT: a LGPL / 一种开源协议] 
 * 		+ 1.MIT是和BSD一样宽范的许可协议，作者只想保留版权，而无任何其它的限制。
 * 			+ 即 你必须在你的发行版里包含原许可协议的声明，无论你是以二进制发布的还是以源代码发布的。
 * 		+ 2.MIT协议又称麻省理工学院许可证，最初由麻省理工学院开发。
 * 		+ 3.被授权人权利：1、被授权人有权利使用、复制、修改、合并、出版发行、散布、再授权及贩售软件及软件的副本。
 *		+ 4.被授权人可根据程式的需要修改授权条款为适当的内容。
        + 5.被授权人义务：【在软件和软件的所有副本中都必须包含版权声明和许可声明】。
 * @author johnny zen
 * @github https://github.com/Johnny-ZTSD/BaiduIndexSpider
 * @email johnnyztsd@gmail.com
 * @publishDate 2019-05-16 12:52:02
 * @notice
 *      + [注意事项] 不同情境下使用本js的一些注意事项
 *          + 在WebDriver(Java or Python)进行本js脚本注入执行时的推荐环境
 *              + 基于PC电脑(台式 or 笔记本)
 *              + WebDriver选择ChromeDriver
 *              + 浏览器:Chrome (IE8+)
 *          + 直接copy 本js 进浏览器中试运行
 *              + 基于PC电脑(台式 or 笔记本)
 *              + 浏览器:Chrome (IE8+)
 *              + 将浏览器窗口最大化
 *              + 执行本js前，请将当前所处的【右侧滚动条】放置于【屏幕的最顶部】
 *                  + 即 不要让鼠标滑动到页面底部去，否则很可能会执行失败
 *              + 不同尺寸的电脑，可能需要更改BaiduIndexTask中的鼠标坐标参数
 *      + 针对上一版本进行改进
 *          + 按照完全面向对象的程序设计方法将脚本模块化
 *          + 含 Point,Mouse,BaiduIndex,BaiduIndexTask 四个类
 *      + 采用了ES6的语法，如class、let等。
 *      + 本脚本仅支持 IE8+的浏览器
 *      + 优先支持 Chrome 浏览器
 *      + 针对不同电脑终端的屏幕尺寸，其参数需要自行计算
 * 		+ 使用方法见底部 ["本次任务执行区"]
 */

/**
 * [附加说明]
 * 	 + 注入浏览器JS的时效性问题
 * 		+ 通过 浏览器 VM 执行的JS，本文件的JS执行之后，再次取变量时将获取不到(以如下注释的小片段JS代码为例)
 */
// var clientX = 90; //仅当次JS执行时有效
// var housePricesJsonStr = "7858758784748748";  //仅当次JS执行时有效
// localStorage.setItem["clientX"] = clientX; //仅当次JS执行时有效
// localStorage.setItem["housePricesJsonStr"] = housePricesJsonStr; //仅当次JS执行时有效
// console.log("localStorage.getItem['clientX']:"+localStorage.getItem["clientX"]);
// console.log("localStorage.getItem['housePricesJsonStr']:"+localStorage.getItem["housePricesJsonStr"]);
// console.log("clientX:"+clientX); //仅当次JS执行时有效
// console.log("housePricesJsonStr:"+housePricesJsonStr); //仅当次JS执行时有效


/////////////////////////
//      一 工具层       //
/////////////////////////

/**
 * 坐标类
 */
class Point {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    };
    toString() {
        return "< x:" + this.x + ", y:" + this.y + " >";
    }
}

/**
 * 鼠标
 * + mousePositionListen 实时监听鼠标在浏览器中的坐标
 * + getMousePosition 获取鼠标在【canvas】中的相对坐标
 * + simulateOnceMousemove 模拟鼠标【横向】滑动一次
 * [dependency]
 * + Point
 * [notice]
 *  + 便于调试
 *  + 实现鼠标模拟所需的全部基本方法 
 */

class Mouse {
    constructor() {};
    /**
     * 实时监听鼠标在浏览器中的坐标
     * + 为计算鼠标在浏览器中的坐标，为模拟鼠标滑动的具体规矩作前期运算
     * @param cssSelector 
     */
    static mousePositionListen(cssSelector) {
        let mouseListenDom = document.querySelector(cssSelector);
        // mouseListenDom.onmousedown = mousedown; //鼠标按下去的时候
        mouseListenDom.onmousemove = mousemove; //鼠标移动的事件
        mouseListenDom.mouseover = mousemover;
        // mouseListenDom.onmouseup = mouseup; //鼠标松开的事件
        function mouseEventBaseHandle(event) {
            var e = event || window.event; //为了兼容ie和火狐
            let clientX = e.clientX; //鼠标所在的x坐标
            let clientY = e.clientY; //鼠标所在的y坐标
            let offsetX = e.offsetX; //相对位置X
            let offsetY = e.offsetY; //相对位置Y
            console.log("[Mouse#mousePositionListen] '[" + cssSelector + "'] " + "clientX:" + clientX + " \tclientY:" + clientY + " \toffsetX:" + offsetX + " \toffsetY:" + offsetY);
            // console.log("'[" + cssSelector + "'] " + "element<leftMax,topMax> = " + (clientX - offsetX) + "," + (clientY - offsetY));
        }
        function mousemover(){
            mouseEventBaseHandle(event);
        }
        function mousedown(event) {
            mouseEventBaseHandle(event);
        }
        function mousemove(event) {
            mouseEventBaseHandle(event);
        }
        function mouseup(event) {
            mouseEventBaseHandle(event);
        }
    };
    static _getMousePos(canvas, event) { //[私有方法] 获取鼠标在【canvas】中的相对位置
        var rect = canvas.getBoundingClientRect();
        let x = event.clientX - rect.left * (canvas.width / rect.width);
        let y = event.clientY - rect.top * (canvas.height / rect.height);
        return new Point(x, y);
    };
    /**
     * 获取鼠标在【canvas】中的相对位置
     * @param {*} canvas 
     * @param {*} event 
     */
    static getMousePosition(canvas, event) { //获取鼠标在【canvas】中的相对位置
        // console.log("event:"+event);//test
        let repeatMaxSize = 5; //如果获取坐标失败，则 最多重复获取的次数
        let point = null;
        while (point == null && (point < repeatMaxSize)) {
            try {
                point = Mouse._getMousePos(canvas, event);
            } catch (e) {
                console.error(e);
                console.error("[Mouse.getMousePosition] event:" + event);
                console.error("[Mouse.getMousePosition] canvas:" + canvas);
            }
            repeatMaxSize--;
        }
        if (point == null) {
            console.error("[Mouse.getMousePosition] point is null!");
        } else {
            console.log("[Mouse.getMousePosition] x:" + point.x + ",y:" + point.y); //test
        }
        return point;
    };
    /**
     * 模拟鼠标滑动一次
     * + 连续递增1像素的方式实现"一次"滑动
     * @author 	johnny
     * @param clientX  相对于[浏览器]窗口横坐标
     * @param clientY  相对于[浏览器]窗口横坐标
     * @param distance [横向]滑动距离
     * 
     * @url    	https://blog.csdn.net/lctmei/article/details/88885678
     * 			https://developer.mozilla.org/zh-CN/docs/Web/API/MouseEvent/initMouseEvent 
     *			https://blog.csdn.net/qiumingsheng/article/details/48051585
     * @notice	key api 
     *	 		+ 	https://developer.mozilla.org/zh-CN/docs/Web/API/MouseEvent/initMouseEvent 
     *		  	+	https://blog.csdn.net/qiumingsheng/article/details/48051585
     *		 	event.initMouseEvent(	type, 		canBubble, 	cancelable,	view, 		detail, 	
     *								screenX, 	screenY, 	clientX, 	clientY, 	ctrlKey, 
     *								altKey, 	shiftKey, 	metaKey, 	button, 	relatedTarget);
     *			+ type 设置事件类型type 的字符串，包含以下几种鼠标事件：
     *			 	click，mousedown，mouseup，mouseover，[mousemove]，mouseout。
     *			+ canBubble 是否可以冒泡。取值集合见Event.bubbles。
     *			+ cancelable 是否可以阻止事件默认行为。取值集合见Event.cancelable
     *			+ view 事件的AbstractView对象引用，这里其实指向window 对象。取值集合见 UIEvent.view。
     *			+ detail 事件的鼠标点击数量。取值集合见Event.detail
     *			+ screenX 事件的屏幕的x坐标。取值集合见MouseEvent.screenX
     *			+ screenY 事件的屏幕的y坐标。取值集合见MouseEvent.screenY
     *			+ clientX 事件的客户端x坐标。取值集合见MouseEvent.clientX
     *			+ clientY 事件的客户端y坐标。取值集合见MouseEvent.clientY
     *			+ ctrlKey 事件发生时 control 键是否被按下。取值集合见MouseEvent.ctrlKey
     *			+ altKey 事件发生时 alt 键是否被按下。取值集合见MouseEvent.altKey
     *			+ shiftKey 事件发生时 shift 键是否被按下。取值集合见MouseEvent.shiftKey
     *			+ metaKey 事件发生时 meta 键是否被按下。取值集合见MouseEvent.metaKey
     *			+ button 鼠标按键值 button
     *			+ relatedTarget 事件的相关对象。只在某些事件类型有用 (例如 mouseover ?和 mouseout)。其它的传null。		
     */
    static simulateOnceMousemove(cssSelector, clientX, clientY, distance) {
        var elem = document.querySelector(cssSelector),
            k = 0,
            interval;
        console.log("[Mouse.simulateOnceMousemove] elem:" + elem); //test
        // console.log(elem); //test
        //elem.focus();
        // iME(elem, "mousedown", 0, 0, clientX, clientY);
        interval = setInterval(function () {
            k++;
            iter(k);
            if (k === distance) {
                clearInterval(interval);
                // iME(elem, "mouseup", clientX + k, clientY, 220 + k, 400);
            }
        }, 10);
        function iter(y) {
            // console.log("[#simulateOnceMousemove] clientX + y:" + (clientX + y) + "\t|\tclientY:" + clientY + "\t|clientX:" + clientX + "\t|y:" + y);
            iME(elem, "mousemove", clientX + y, clientY, clientX + y, clientY); //[johnny：猜测]水平滑动
        }
        function iME(obj, event, screenXArg, screenYArg, clientXArg, clientYArg) {
            var mousemove = document.createEvent("MouseEvent");
            mousemove.initMouseEvent(event, true, true, window, 0, screenXArg, screenYArg, clientXArg, clientYArg,
                0, 0, 0, 0, 0, null);
            obj.dispatchEvent(mousemove);
        }
    }
}

/**
 * 基于HTML的数据交互工具
 * + saveDataToHTML 插入数据到HTML中
 * + loadDataFromHTML 通过元素的ID获取元素的文本数据
 * [notice]
 * + 操纵DOM，通过JS与HTML DOM实现 浏览器数据(JS变量等数据) 与 Java 的数据交互
 * [demo]
 *  var divId = "webDriver_id";
 * 	var text = "4643774373473";
 * 	HTMLDataUtil.saveDataToHTML(divId,text);//通过HTML来传值
 * 	console.log(HTMLDataUtil.loadDataFromHTML(divId));
 */
class HTMLDataUtil {
    constructor() {};
    /**
     * 插入数据到HTML中
     * + 数据将显示在网页(body元素的)底部
     * @param containerId 
     * @param text 
     */
    static saveDataToHTML(containerId, data) {
        var body = document.querySelector("body");
        var div = document.createElement("div");

        div.id = containerId;
        div.innerText = data;
        body.appendChild(div);
    };
    /**
     * 通过元素的ID获取元素的文本数据
     * @param containerId 
     */
    static loadDataFromHTML(containerId) {
        var dom = document.querySelector("#" + containerId);
        return dom.innerText.trim();
    }
}

/////////////////////////
//      二 业务层       //
/////////////////////////

/**
 * BaiduIndex
 * + loadOncBaiduIndexInfoDom 获取关键词信息的HTML DIV盒子
 * + resolveAsBaiduIndexObject 通过dom解析并封装为指数对象
 * + loadOnceBaiduIndex 返回已解析完成的指数对象
 * + outputBaiduIndesxToHTMLAsJSON 输出指数信息(map)到HTML中
 * + storeBaiduIndexToMap 存储指数对象信息到 baiduIndexsMap 的Map对象中
 * + toString()
 * [demo]
 *  let bi = new BaiduIndex("2019-04-14","成都房价",2345);
 *  bi.toString();
 */
class BaiduIndex {
    //构造函数
    constructor(date, keyword, indexValue) {
        this.date = new Date(date); //date可以是Date对象，也可以是形如"2019-04-14"的日期字符串
        this.keyword = keyword;
        this.indexValue = indexValue; //指数值
    };
    static loadOnceBaiduIndexInfoDom() { //获取指数对象信息的HTML DIV盒子
        var infoBoxDom = null;
        infoBoxDom = document.querySelectorAll("body > div.index-main > div:nth-child(2) > div.index-main-content > div.index-trend-view > div.index-trend-content.mb30 > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div.loading-wrapper.index-loaded > div > div:nth-child(2)")[0];
        if (infoBoxDom == null) {
            console.error("[BaiduIndex.loadOnceBaiduIndexInfoDom] Fail to load html dom of baiduIndex info!");
            return null;
        }
        infoBoxDom.style.display = "block";
        return infoBoxDom;
    };
    static resolveAsBaiduIndexObject(infoBoxDom) { //通过dom解析并封装为百度指数信息对象
        var errorMsg = "[BaiduIndex.resolveAsBaiduIndexObject] Fail to resolve BaiduIndex info from 'infoBoxDom'!";
        if (infoBoxDom == null) {
            console.error(errorMsg + "< Because:infoBoxDom is null. >");
            return null;
        }
        console.log("[BaiduIndex#resolveAsBaiduIndexObject] infoBoxDom:" + infoBoxDom); //test
        console.log(infoBoxDom); //test
        var baiduIndex = null;
        var date = null;
        var keyword = null;
        var indexValue = null;
        try {
            date = infoBoxDom.querySelectorAll("div")[0].innerText.substring(0, 10).trim();
            keyword = infoBoxDom.querySelectorAll("div")[2].innerText.trim();
            indexValue = parseFloat(infoBoxDom.querySelectorAll("div")[3].innerText.trim().replace(",", ""));
        } catch (e) {
            console.error(errorMsg + "< Because: Fail when resolve data from infoBoxDom. >");
            return null;
        }
        baiduIndex = new BaiduIndex(date, keyword, indexValue);
        return baiduIndex;
    };
    static loadOnceBaiduIndex() { //返回：已解析完成的房价信息对象
        return BaiduIndex.resolveAsBaiduIndexObject(BaiduIndex.loadOnceBaiduIndexInfoDom());
    };
    /**
     * 输出房价(map)到HTML中
     * @param containerId 输出baiduIndexsMap的json字符串数据到新插div的Id名
     * @param baiduIndexsMap baiduIndexsMap存储指数信息的Map对象
     * [baiduIndexsMap] var baiduIndexsMap = new Map();//key : 日期  value : 指数(Eg：房价指数 6868)
     */    
    static outputBaiduIndexsToHTMLAsJSON(containerId, baiduIndexsMap) {
        let iter = baiduIndexsMap.values();
        let values = new Array();
        let item = null;
        while ((item = iter.next()).done != true) { //还未遍历完
            values.push(item.value);
            item = null;
        }
        HTMLDataUtil.saveDataToHTML(containerId, JSON.stringify(values));
    };
    static storeBaiduIndexToMap(baiduIndexObject, baiduIndexsMap) { //存储指数对象信息到 baiduIndexsMap 的Map对象中
        if(baiduIndexsMap==undefined){
            console.error("[BaiduIndex#storeBaiduIndexToMap] baiduIndexsMap is undefined! (baiduIndexsMap:"+baiduIndexsMap+")");
            return null;
        }
        if((baiduIndexsMap instanceof Map)==false){
            console.error("[BaiduIndex#storeBaiduIndexToMap] baiduIndexsMap is not Map Object! (baiduIndexsMap's Type is Map? "+(baiduIndexsMap instanceof Map)+")");
            return null;
        }
        baiduIndexsMap.set(baiduIndexObject.date.toLocaleDateString(), baiduIndexObject);
        //housePricesMap.get(housePriceObject.date.toLocaleDateString());
        return baiduIndexsMap;
    };
    toString() {
        return "BaiduIndex {\n\tdate:'" + this.date.toLocaleDateString() + "', \n\keyword:'" + this.keyword + "', \n\tindexValue: '" + this.indexValue + "'\n}";
    }
}

/////////////////////////////////////
//      三 百度指数任务类           //
/////////////////////////////////////
/**
 * BaiduIndexTask
 * [notice]
 * + 将本次任务抽象为方法
 */
class BaiduIndexTask{
    constructor(canvasOfCssSelector,dataContainerId){//参数初始化
        this.canvasOfCssSelector = canvasOfCssSelector;//"canvas[data-zr-dom-id='zr_0']";
        this.canvas = document.querySelector(this.canvasOfCssSelector);
        this.outputDom = document.querySelector("body > div.index-main > div:nth-child(2) > div.index-main-content > div.index-trend-view > div.index-trend-content.mb30 > div:nth-child(1) > div.tabs.tabs-blue.tabs-line > div.nav.tab-nav-container.border-bottom > li > span > span.text"); //输出到html元素中，方便直接显示
        this.baiduIndexs = new Map(); //key : 日期  value : 百度指数对象
        this.targetPositionPoint = new Point(1200, 164); //目标滑动位置 [不同尺寸的电脑，可通过Mouse.mousePositionListen具体测算，保障运行成功率]
        this.dataContainerId = dataContainerId; //"webDriver_housePrices"; //输出房价数据到HTML中的div容器id
    };
    _check(){//[私有方法] 检查参数是否初始化完毕
        console.log(this.canvas);
        console.log(this.baiduIndexs);
        console.log(this.targetPositionPoint);
    };
    executeBaiduIndexTask(){
        this._check();
        // Mouse.mousePositionListen("html");//实时监听html坐标
        // Mouse.mousePositionListen(this.canvasOfCssSelector); //实时监听canvas坐标
        // document.querySelector(this.canvasOfCssSelector).style.border = "1px solid red";
        console.log("html:"+document.getElementsByTagName("html"));
        Mouse.simulateOnceMousemove(this.canvasOfCssSelector, 62, 429, 1200); //模拟鼠标滑动一次 [不同尺寸的电脑，可通过Mouse.mousePositionListen具体测算，保障运行成功率]
        
        let thiz = this; //作用域问题，一不小心就会非常严重
        this.canvas.addEventListener("mousemove", function (event) {
            console.log("[BaiduIndexTask.executeBaiduIndexTask] > canvas.addEventListener:canvas "+thiz.canvas);//test
            //1 > 每当鼠标滑动时，便获取一次房价信息
            let baiduIndex = null;
            baiduIndex = BaiduIndex.loadOnceBaiduIndex();
            if (baiduIndex != null) { //解析成功
                console.log("[BaiduIndexTask.executeBaiduIndexTask] baiduIndexs:\nbaiduIndexs"+thiz.baiduIndexs);//test
                thiz.baiduIndexs = BaiduIndex.storeBaiduIndexToMap(baiduIndex, thiz.baiduIndexs);
            }
            //2 > 监听鼠标位置变换后的当前坐标信息
            let point = null;
            point = Mouse.getMousePosition(thiz.canvas, event);
            console.log("[BaiduIndexTask.executeBaiduIndexTask] current point:" + point.toString());//test
            thiz.outputDom.innerText = point.toString(); //输出当前canvas坐标到HTML上

            // 3 > 当滑动完指定位置后，输出到html，方便Java操纵数据
            if (point != null && point.x >= thiz.targetPositionPoint.x) {
                console.log("[BaiduIndexTask.executeBaiduIndexTask] > canvas.addEventListener: start output to html!");//test
                BaiduIndex.outputBaiduIndexsToHTMLAsJSON(thiz.dataContainerId, thiz.baiduIndexs);
            }
        });
    };
    getBaidunIndexs(){//返回数组
        return JSON.parse(this.getBaiduIndexDataHTMLDom().innerText.trim());
    };
    getBaiduIndexDataHTMLDom(){
        while(document.getElementById(this.dataContainerId)!=null){
            return document.getElementById(this.dataContainerId);
        };
    }
}

////////////////////////////////
//      三 本次任务执行区      //
////////////////////////////////
let canvasOfCssSelector = "canvas[data-zr-dom-id='zr_0']";
let dataContainerId = "webDriver_housePrices";
let baiduIndexTask = new BaiduIndexTask(canvasOfCssSelector,dataContainerId);
baiduIndexTask.executeBaiduIndexTask();
// console.log(baiduIndexTask.getBaidunIndexs());//需要等待任务实行完毕
// console.log(baiduIndexTask.getBaiduIndexDataHTMLDom());//需要等待任务实行完毕