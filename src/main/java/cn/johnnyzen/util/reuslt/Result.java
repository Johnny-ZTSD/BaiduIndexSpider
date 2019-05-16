package cn.johnnyzen.util.reuslt;

import java.io.Serializable;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2018/9/25  15:19:30
 * @Description: 统一API响应结果封装
 */

public class Result implements Serializable {
    private int code = -1;
    /*
     * 对code解释
     * 阅读对象：程序员
     * */
    private String message = null;
    /*
     * 返回object或者提示数据
     * 阅读对象：用户
     **/
    private Object data = null;

    public Result() {
        super();
    }

    public Result(int code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result [code=" + code + ", message=" + message + ", data="
                + data + "]";
    }
}

