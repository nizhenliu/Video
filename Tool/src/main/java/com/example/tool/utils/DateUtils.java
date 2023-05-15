package com.example.tool.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/25 13:32
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/25 13:32
 * @updateRemark
 */
public class DateUtils {
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String diffDateFromUTC(String source){
        try {
            return diffDate(getLocalTimeFromUTC(source));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String diffDate(String source){
        Date date = new Date();//当前时间

        Date time = null;
        try {
            time = format.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//      当前时间与其他时间相差的毫秒数
        long diff = date.getTime() - time.getTime();
        long sec = diff / 1000;
        long min = diff / 60 / 1000;
        long hours = diff / 60 / 60 / 1000;
        long days = diff / 24 / 60 / 60 / 1000;
        StringBuilder sb=new StringBuilder();
        if (days>0){
            sb.append(days+"天");
            return sb.toString();
        }
        if (hours>0){
            sb.append(hours+"小时");
            return sb.toString();
        }
        if (min>0){
            sb.append(min+"分钟");
            return sb.toString();
        }
        if (sec>0){
            sb.append(sec);
            return sb.toString();
        }
        return sb.toString();
    }

    public static String getLocalTimeFromUTC(String time) throws ParseException {
        DateFormat df = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        }else {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        Date  date = df.parse(time);
        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        Date date1 =  df1.parse(date.toString());
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df2.format(date1);
    }
}
