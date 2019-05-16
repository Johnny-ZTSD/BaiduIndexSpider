package cn.johnnyzen.app.spider;

import java.util.Calendar;
import java.util.Date;

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

    public BaiduIndex(){

    }

    public BaiduIndex(String keyword, Calendar date, int indexValue) {
        this.keyword = keyword;
        this.date = date;
        this.indexValue = indexValue;
    }

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
        return "\nBaiduIndex{" +
                "\n\tkeyword='" + keyword + '\'' +
                ",\n\tdate=" + date +
                ",\n\tindexValue=" + indexValue +
                "\n}";
    }
}
