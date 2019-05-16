package cn.johnnyzen.app.spider;

import cn.johnnyzen.util.file.FileUtil;
import cn.johnnyzen.util.reuslt.Result;
import cn.johnnyzen.util.reuslt.ResultCode;
import cn.johnnyzen.util.reuslt.ResultUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2019/5/16  13:36:42
 * @Description: ...
 */
@Controller
public class BaiduSpiderController {
    private static final Logger logger = Logger.getLogger(BaiduSpiderController.class.getName());

    //日志前缀字符串,方便通过日志定位程序
    protected String logPrefix = null;

    /**
     * resolveBaiduIndexByJs.js 获取百度指数解析js脚本
     **/
    @RequestMapping("/resolveBaiduIndexByJs.js")
    @ResponseBody
    public String resolveBaiduIndexByJs(HttpServletRequest request,HttpServletResponse response){
        logPrefix = "[BaiduSpiderController.login] ";
        String resolveBaiduIndexByJsPath = "C:\\Users\\千千寰宇\\Desktop\\resolveBaiduIndexByJs.js";
        String jsCodeText = null;
        jsCodeText = FileUtil.readFile(resolveBaiduIndexByJsPath);
        if(jsCodeText==null){
            return "not found the js file!";
        } else {
            return jsCodeText;
        }
    }
}
