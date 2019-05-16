package cn.johnnyzen.util.code;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2018/10/6  19:45:18
 * @Description: ...
 */

public class CodeUtil {

    /* 普通MD5 加密 */
    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     * @return uuid
     */
    public static String getUUID() {
    /*UUID uuid = UUID.randomUUID();
    String str = uuid.toString();
    // 去掉"-"符号
    String temp = str.substring(0, 8) + str.substring(9, 13)
            + str.substring(14, 18) + str.substring(19, 23)
            + str.substring(24);
    return temp;*/

        return UUID.randomUUID().toString().replace("-", "");
    }

}
