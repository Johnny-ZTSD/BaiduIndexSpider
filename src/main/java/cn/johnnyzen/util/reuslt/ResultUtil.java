package cn.johnnyzen.util.reuslt;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2018/9/30  21:03:59
 * @Description: ...
 */

public class ResultUtil {

    public static Result success(Object object){
        return template(ResultCode.SUCCESS, "request success!", object);
    }

    public static Result success(String message){
        return template(ResultCode.SUCCESS, message, null);
    }

    public static Result success(String message, Object object){
        return template(ResultCode.SUCCESS, message, object);
    }

    public static Result success() {
        return success("request success!");
    }

    public static Result error(ResultCode code, String msg) {
        return template(code, msg, null);
    }

    public static Result template(ResultCode code, String message, Object object){
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(object);
        return  result;
    }
}
