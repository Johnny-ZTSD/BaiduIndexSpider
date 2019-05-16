/**
 * resolveBaiduIndexByJs-1-0-0.js / 解析百度指数
 * @description 通过模拟鼠标滑动来解析百度指数网页的js脚本
 * @version 1.0.0 - 非正式版
 * @copyright base MIT [基于MIT: a LGPL / 一种开源协议] 
 * 		+ 1.MIT是和BSD一样宽范的许可协议，作者只想保留版权，而无任何其它的限制。
 * 			+ 即 你必须在你的发行版里包含原许可协议的声明，无论你是以二进制发布的还是以源代码发布的。
 * 		+ 2.MIT协议又称麻省理工学院许可证，最初由麻省理工学院开发。
 * 		+ 3.被授权人权利：1、被授权人有权利使用、复制、修改、合并、出版发行、散布、再授权及贩售软件及软件的副本。
 *		+ 4.被授权人可根据程式的需要修改授权条款为适当的内容。
 *      + 5.被授权人义务：【在软件和软件的所有副本中都必须包含版权声明和许可声明】。
 * @author johnny zen
 * @github https://github.com/Johnny-ZTSD
 * @email johnnyztsd@gmail.com
 * @publishDate 2019-05-15 10:00:01
 * @notice
 *      + 本版本的一些程序语言特点
 *          + 按照面向对象的程序设计方法将脚本模块化
 * 			+ 采用了部分JavaScript ES6的语法，如class、let等。
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
//      0 小工具       //
/////////////////////////

/**
 * 鼠标位置监听
 * + 便于调试
 */

function mousePositionListen(cssSelector) {
	var mouseListenDom = document.querySelector(cssSelector);
	mouseListenDom.onmousedown = mousedown; //鼠标按下去的时候
	mouseListenDom.onmousemove = mousemove; //鼠标移动的事件
	mouseListenDom.onmouseup = mouseup; //鼠标松开的事件
	function mouseEventBaseHandle(event) {
		var e = event || window.event; //为了兼容ie和火狐
		clientX = e.clientX; //鼠标所在的x坐标
		clientY = e.clientY; //鼠标所在的y坐标
		offsetX = e.offsetX; //相对位置X
		offsetY = e.offsetY; //相对位置Y
		console.log("'[" + cssSelector + "'] " + "clientX:" + clientX + " \tclientY:" + clientY + " \toffsetX:" + offsetX + " \toffsetY:" + offsetY);
		// console.log("'[" + cssSelector + "'] " + "element<leftMax,topMax> = " + (clientX - offsetX) + "," + (clientY - offsetY));
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
}

/**
 * 操纵DOM，通过JS与HTML DOM实现 浏览器数据(JS变量等数据) 与 Java 的数据交互
 * [demo]
 *  var divId = "webDriver_id";
 * 	var text = "4643774373473";
 * 	HTMLDataUtil.saveDataToHTML(divId,text);//通过HTML来传值
 * 	console.log(HTMLDataUtil.loadDataFromHTML(divId));
 */
class HTMLDataUtil {
	constructor() {

	};
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

////////////////////////////////////////////
//      1 鼠标滑动时的位置监听(用于调试)    //
////////////////////////////////////////////

class Point {
	constructor(x, y) {
		this.x = x;
		this.y = y;
	};
	static _getMousePos(canvas, event) { //私有方法
		var rect = canvas.getBoundingClientRect();
		let x = event.clientX - rect.left * (canvas.width / rect.width);
		let y = event.clientY - rect.top * (canvas.height / rect.height);
		return new Point(x, y);
	};
	static getMousePosition(canvas, event) { //获取鼠标位置
		// console.log("event:"+event);//test
		let repeatMaxSize = 5; //如果获取坐标失败，则 最多重复获取的次数
		let point = null;
		while (point == null && (point < repeatMaxSize)) {
			try {
				point = Point._getMousePos(canvas, event);
			} catch (e) {
				console.error(e);
				console.error("[Point.getMousePosition] event:" + event);
				console.error("[Point.getMousePosition] canvas:" + canvas);
			}
			repeatMaxSize--;
		}
		if (point == null) {
			console.error("[Point.getMousePosition] point is null!");
		} else {
			console.log("[Point.getMousePosition] x:" + point.x + ",y:" + point.y); //test
		}
		return point;
	};
	toString() {
		return "< x:" + this.x + ", y:" + this.y + " >";
	}
}

/**
 * 房价类
 * [demo]
 *  let hp = new HousePrice("2019-04-14","成都房价",2345);
 *  hp.toString();
 */
class HousePrice {
	//构造函数
	constructor(date, keyword, indexValue) {
		this.date = new Date(date); //date可以是Date对象，也可以是形如"2019-04-14"的日期字符串
		this.keyword = keyword;
		this.indexValue = indexValue; //指数值
	};
	static loadOnceHouseInfoDom() { //获取房价信息的HTML DIV盒子
		var infoBoxDom = null;
		infoBoxDom = document.querySelectorAll("body > div.index-main > div:nth-child(2) > div.index-main-content > div.index-trend-view > div.index-trend-content.mb30 > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div.loading-wrapper.index-loaded > div > div:nth-child(2)")[0];
		if (infoBoxDom == null) {
			console.error("[HousePrice.loadOnceHouseInfoDom] Fail to load html dom of house info!");
			return null;
		}
		infoBoxDom.style.display = "block";
		return infoBoxDom;
	};
	static resolveAsHousePriceObject(infoBoxDom) { //通过dom解析并封装为房价信息对象
		var errorMsg = "[HousePrice.resolveAsHousePriceObject] Fail to resolve house info from 'infoBoxDom'!";
		if (infoBoxDom == null) {
			console.error(errorMsg + "< Because:infoBoxDom is null. >");
			return null;
		}
		console.log("[HousePrice#resolveAsHousePriceObject] infoBoxDom:" + infoBoxDom); //test
		console.log(infoBoxDom);//test
		var hp = null;
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
		hp = new HousePrice(date, keyword, indexValue);
		return hp;
	};
	static loadOnceHousePrice() { //返回：已解析完成的房价信息对象
		return HousePrice.resolveAsHousePriceObject(HousePrice.loadOnceHouseInfoDom());
	};
	/**
	 * 输出房价(map)到HTML中
	 * @param containerId 输出housePricesMap的json字符串数据到新插div的Id名
	 * @param housePricesMap housePricesMap存储房价信息的Map对象
	 * [housePricesMap] var housePrices = new Map();//key : 日期  value : 房价
	 */
	static outputHousePricesToHTMLAsJSON(containerId,housePricesMap){
		let iter = housePricesMap.values();
		let values = new Array();
		let item = null;
		while((item=iter.next()).done!=true){//还未遍历完
			values.push(item.value);
			item=null;
		}
		HTMLDataUtil.saveDataToHTML(containerId,JSON.stringify(values));
	};
	static storeHousePriceToMap(housePriceObject,housePricesMap){//存储房价信息到 housePricesMap 的Map对象中
		console.log("[HousePrice#storeHousePriceToMap] "+(housePricesMap instanceof Map));//test
		housePricesMap.set(housePriceObject.date.toLocaleDateString(),housePriceObject);
		//housePricesMap.get(housePriceObject.date.toLocaleDateString());
		return housePricesMap;
	};
	toString() {
		return "HousePrice {\n\tdate:'" + this.date.toLocaleDateString() + "', \n\keyword:'" + this.keyword + "', \n\tindexValue: '" + this.indexValue + "'\n}";
	}
}

/**
 * 模拟鼠标滑动一次
 * + 连续递增1像素的方式实现"一次"滑动
 * @param clientX  相对于[浏览器]窗口横坐标
 * @param clientY  相对于[浏览器]窗口横坐标
 * @param distance [横向]滑动距离
 * 
 * @author 	johnny
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
function simulateOnceMousemove(cssSelector, clientX, clientY, distance) {
	var elem = document.querySelector(cssSelector),
		k = 0,
		interval;
	console.log("elem:" + elem); //test
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
		// console.log("[#dragandDrop] clientX + y:" + (clientX + y) + "\t|\tclientY:" + clientY + "\t|clientX:" + clientX + "\t|y:" + y);
		iME(elem, "mousemove", clientX + y, clientY, clientX + y, clientY); //[johnny：猜测]水平滑动
	}
	function iME(obj, event, screenXArg, screenYArg, clientXArg, clientYArg) {
		var mousemove = document.createEvent("MouseEvent");
		mousemove.initMouseEvent(event, true, true, window, 0, screenXArg, screenYArg, clientXArg, clientYArg,
			0, 0, 0, 0, 0, null);
		obj.dispatchEvent(mousemove);
	}
}

////////////////////////////////////
//      3 本次任务执行区/Demo      //
////////////////////////////////////

(function(){
	// mousePositionListen("html");//实时监听html坐标
	mousePositionListen("canvas");//实时监听canvas坐标
	document.querySelector("canvas").style.border="1px solid red";
	simulateOnceMousemove("canvas",62,429,1200);//模拟鼠标滑动一次

	var canvasOfCssSelector = "canvas[data-zr-dom-id='zr_0']";
	var canvas = document.querySelector(canvasOfCssSelector);
	var outputDom = document.querySelector("body > div.index-main > div:nth-child(2) > div.index-main-content > div.index-trend-view > div.index-trend-content.mb30 > div:nth-child(1) > div.tabs.tabs-blue.tabs-line > div.nav.tab-nav-container.border-bottom > li > span > span.text");//输出到html元素中，方便直接显示
	var housePrices = new Map();//key : 日期  value : 房价
	let targetPositionPoint = new Point(1200,164);//目标滑动位置
	let dataContainerId = "#webDriver_housePrices";//输出房价数据到HTML中的div容器id
	canvas.addEventListener("mousemove", function(event) {
		//1 > 每当鼠标滑动时，便获取一次房价信息
		let housePrice = null;
		housePrice = HousePrice.loadOnceHousePrice();
		if(housePrice!=null){//解析成功
			housePrices = HousePrice.storeHousePriceToMap(housePrice,housePrices);	
		}
		//2 > 监听鼠标位置变换后的当前坐标信息
		var point = null;
		point = Point.getMousePosition(canvas, event);
		console.log("[canvas.addEventListener] current point:"+point.toString());
		outputDom.innerText = point.toString();//输出当前canvas坐标到HTML上

		// 3 > 当滑动完指定位置后，输出到html，方便Java操纵数据
		if(point!=null && point.x>=targetPositionPoint.x){
			console.log("[canvas.addEventListener] start output to html!");
			HousePrice.outputHousePricesToHTMLAsJSON(dataContainerId,housePrices);
		}
	});
})();
