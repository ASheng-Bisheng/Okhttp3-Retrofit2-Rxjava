package cn.whzwl.xbs.network.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/10.
 */

public class DateUtils {

    private static SimpleDateFormat sf;
    private static SimpleDateFormat sdf;

    /**
     * 获取系统时间 格式为："yyyy/MM/dd "
     **/
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /**
     * 获取系统时间 HH-MM-SS
     *
     * @return
     */
    public static String getTime() {
        sf = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        return sf.format(new Date());
    }


    /**
     * 获取系统时间 格式为："yyyy "
     **/
    public static String getCurrentYear() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy");
        return sf.format(d);
    }

    /**
     * 获取系统时间 格式为："MM"
     **/
    public static String getCurrentMonth() {
        Date d = new Date();
        sf = new SimpleDateFormat("MM");
        return sf.format(d);
    }

    /**
     * 获取系统时间 格式为："dd"
     **/
    public static String getCurrentDay() {
        Date d = new Date();
        sf = new SimpleDateFormat("dd");
        return sf.format(d);
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long getCurrentTime() {
        long d = new Date().getTime() / 1000;
        return d;
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return sf.format(d);
    }


    /**
     * 时间戳中获取年
     */
    public static String getYearFromTime(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("yyyy");
        return sf.format(d);
    }

    /**
     * 时间戳中获取月
     */
    public static String getMonthFromTime(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("MM");
        return sf.format(d);
    }

    /**
     * 时间戳中获取日
     */
    public static String getDayFromTime(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("dd");
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToDate(String time) {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static boolean isValidDate(String str) {

        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static String formatTimeBySecond(Integer s) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        String minute_str = "";
        String second_str = "";
        second = s;
        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }

        if (minute == 0) {
            minute_str = "00";
        } else {
            if (minute < 10) {
                minute_str = "0" + minute;
            } else {
                minute_str = "" + minute;
            }

        }

        if (second == 0) {
            second_str = "00";
        } else {
            if (second < 10) {
                second_str = "0" + second;
            } else {
                second_str = "" + second;
            }
        }
        String strtime = minute_str + ":" + second_str;
        return strtime;
    }


    public static Map<String, Integer> formatTimeBySecond_Map(Integer s) {
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = s;
        Map<String, Integer> map = new HashMap<>();
        if (second > 60) {
            minute = second / 60;
            second = second % 60;
            if (minute > 60) {
                hour = minute / 60;
                minute = minute % 60;
            }
        }

        map.put("hh", hour);
        map.put("mm", minute);
        map.put("ss", second);

        return map;
    }

    /**
     * 分钟转换秒
     *
     * @param m
     * @return 秒
     */
    public static int formatTImeByMinute(Integer m) {
        Integer s = m * 60;

        return s;
    }

}
