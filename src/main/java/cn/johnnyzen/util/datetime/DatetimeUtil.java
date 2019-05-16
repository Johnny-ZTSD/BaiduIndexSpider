package cn.johnnyzen.util.datetime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2018/11/4  00:39:44
 * @Description: 时间日期转换工具类
 * @Reference: https://blog.csdn.net/haqer0825/article/details/7034920
 */

public class DatetimeUtil {
    public static String calendarToString(Calendar calendar, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format); //such as: "yyyy-MM-dd"
        return sdf.format(calendar.getTime());
    }

    public static Calendar stringToCalendar(String dateStr,String formatOfStr) throws ParseException {
        SimpleDateFormat sdf= new SimpleDateFormat(formatOfStr); //such as:"yyyy-MM-dd"
        Date date =sdf.parse(dateStr);
        return dateToCalendar(date);
    }

    public static String dateToString(Date date, String format){
        SimpleDateFormat sdf= new SimpleDateFormat(format); //such as:"yyyy-MM-dd","HH:mm:ss"
        return sdf.format(date);
    }

    /**
     * 日期[字符串]转[Date 日期对象]
     * @param dateStr
     * @param formatOfStr
     * @return
     * @throws ParseException
     *
     * [notice]
     *  SimpleDateFormat构造函数的样式(formatOfStr)与dateStr的样式必须完全相符
     * [demo]
     *  dateStr                     formatOfStr
     *  "2018-11-4"                 "yyyy-MM-dd"
     *  "2019-06-04T06:10:48.775Z"  "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     */
    public static Date stringToDate(String dateStr,String formatOfStr) throws ParseException {
        //dateStr such as :"2018-11-4"
        SimpleDateFormat sdf= new SimpleDateFormat(formatOfStr);//such as:"yyyy-MM-dd","HH:mm:ss"
        return sdf.parse(dateStr);
    }

    public static Calendar dateToCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date calendarToDate(Calendar calendar){
        Date date = calendar.getTime();
        return date;
    }

    public static Timestamp stringToTimestamp(String dateStr){
        //dateStr such as:"2019-1-14 08:11:00"
        System.out.println("[DatetimeUtil.stringToTimestamp] dateStr:"+dateStr);//test
        return Timestamp.valueOf(dateStr);
    }

    public static String timestampToString(Timestamp timestamp, String format){
        SimpleDateFormat sdf= new SimpleDateFormat(format);//such as:"yyyy-MM-dd"
        return sdf.format(timestamp);
    }

    public static Timestamp dateToTimestamp(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Timestamp.valueOf(df.format(date));
    }

    public static Calendar timestampToCalendar(Timestamp timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        return calendar;
    }
    public static Timestamp calendarToTimestamp(Calendar calendar){
        return dateToTimestamp(calendarToDate(calendar));
    }

    public static String millisecondToDateString(long ms){

        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();
        if(day >= 0) {
            sb.append(day+"day");
        }
        if(hour >= 0) {
            sb.append(hour+"hour");
        }
        if(minute >= 0) {
            sb.append(minute+"minute");
        }
        if(second >= 0) {
            sb.append(second+"second");
        }
        return sb.toString();

    }
}
