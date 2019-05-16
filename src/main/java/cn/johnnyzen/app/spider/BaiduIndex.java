package cn.johnnyzen.app.spider;

import java.util.Calendar;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2019/5/15  15:59:11
 * @Description: 房价
 */

public class BaiduIndex {
    /**
     * 关键词
     */
    private String keyword;

    /**
     * 日期
     */
    private Calendar date;

    /**
     * 百度指数值
     */
    private int indexValue;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(int indexValue) {
        this.indexValue = indexValue;
    }

    @Override
    public String toString() {
        return "HousePrice{" +
                "\nkeyword='" + keyword + '\'' +
                ",\n date=" + date +
                ",\n indexValue=" + indexValue +
                "}";
    }
}
