package cn.johnnyzen.util.reuslt;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2018/9/30  18:52:48
 * @Description: ...
 */

public enum ResultCode {
    SUCCESS(200),//成功
    FAIL(400),//失败
    UNAUTHORIZED(401),//未认证（签名错误）
    NOT_FOUND(404),//接口不存在

    //特设用户枚举
    NOT_LOGIN_NO_ACCESS(444), //未登录，不能访问
    USERNAME_ERROR_OR_PASSWORD_ERROR(445), //用户名或者密码错误

    INTERNAL_SERVER_ERROR(500);//服务器内部错误

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
