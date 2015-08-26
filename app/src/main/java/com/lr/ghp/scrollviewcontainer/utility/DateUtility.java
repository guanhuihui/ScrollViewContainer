package com.lr.ghp.scrollviewcontainer.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtility {
    public  static final String YYYY_MM_DD_T_HH_MM = "yyyy-MM-dd'T'HH:mm";
    public  static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final SimpleDateFormat df2Date = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM);
    public static final SimpleDateFormat df2Datess = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS);

    public static final SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat df4 = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat df5 = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat df6 = new SimpleDateFormat("yyyy.MM.dd");
    private static final DateFormat df2string = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
    public static final SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat df2OnlyHour =new SimpleDateFormat("yyMMdd-HH");
    public static final SimpleDateFormat df2hourminutes = new SimpleDateFormat("HH:mm");
    /** 时分秒. */
    public static final String dateFormatHMS = "HH:mm:ss";

    public static String DateNomal(String dateStr,SimpleDateFormat startSF,SimpleDateFormat endSF){
        try{
            Date date=startSF.parse(dateStr);
            return endSF.format(date.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算红包是否过期
     * @param strExpiredate 过期时间
     * @return  true 可用未过期，false 已过期
     */
    public static boolean isAvaliable(String strExpiredate){
        Date expirdate = null;
        Date today=new Date();
        try {
            expirdate = df2Date.parse(strExpiredate);
            long spacing=expirdate.getTime()-today.getTime();
            if(spacing>0){
                return true;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String transformTime(Date date){
        return sdf.format(date);
    }


    /**
     * 描述：获取表示当前日期时间的字符串.
     *
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String String类型的当前日期时间
     */
    public static String getCurrentDate(String format) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;

    }

    public static Date getSdfDate(String format){
        Date date=null;
        try {
            return sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static final String FORMATER_YMD = "yyyy-MM-dd";
    public static final String FORMATER_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMATER_CHAT = "yyyy-MM-dd HH:mm";
    public static final String FORMATER_TIME_LINE = "MM-dd";
    public static final String FORMATER_MM_dd_HH_mm = "MM-dd HH:mm";
    public static final String FORMATER_HOME_NEW = "HH:mm";
    public static final int MINUTE60 = 3600;

    /**
     * 格式化'秒
     */
    public static String formatSeconds(Long useSecond) {
        int minute = (int) (useSecond / 60);
        String result = "";
        if (minute != 0) {
            result = minute + "'";
        }
        int second = (int) (useSecond - minute * 60);
        result = result + second + "''";
        return result;
    }

    /**
     * 格式化日期(使用默认格式)
     *
     * @param date
     * @return
     */
    public static String format_md(Date date) {
        return format(date, FORMATER_TIME_LINE);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param formatString
     * @return
     */
    public static String format(Date date, String formatString) {
        SimpleDateFormat df = new SimpleDateFormat(formatString);
        return df.format(date);
    }

    /**
     * 格式化日期(使用默认格式)
     *
     * @param date
     * @return
     */
    public static String format_ymd(Date date) {
        return format(date, FORMATER_YMD);
    }

    /**
     * 格式化日期(使用默认格式)
     *
     * @param date
     * @return
     */
    public static String format_chat(Date date) {
        return format(date, FORMATER_CHAT);
    }

    /**
     * 格式化日期(使用默认格式)
     *
     * @param date
     * @return
     */
    public static String format_ymdhms(Date date) {
        return format(date, FORMATER_YMDHMS);
    }

    /**
     * 转换成日期
     *
     * @param dateString
     * @param formatString
     * @return
     */
    public static Date parse(String dateString, String formatString) {
        SimpleDateFormat df = new SimpleDateFormat(formatString);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 转换成日期(使用默认格式)
     *
     * @param dateString
     * @return
     */
    public static Date parse_ymd(String dateString) {
        return parse(dateString, FORMATER_YMD);
    }

    /**
     * 转换成日期(使用默认格式)
     *
     * @param dateString
     * @return
     */
    public static Date parse_ymdhms(String dateString) {
        return parse(dateString, FORMATER_YMDHMS);
    }

    /**
     * 昨天
     *
     * @return
     */
    public static Date yesterday() {
        return addDay(-1);
    }

    /**
     * 明天
     *
     * @return
     */
    public static Date tomorrow() {
        return addDay(1);
    }

    /**
     * 现在
     *
     * @return
     */
    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 按日加
     *
     * @param value
     * @return
     */
    public static Date addDay(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, value);
        return now.getTime();
    }

    /**
     * 获取年月日时分秒
     */
    public static String getYMDHMS() {
        StringBuffer current = new StringBuffer();
        current.append("本机时间:");
        current.append(DateUtility.year() + "年");

        String month = "";
        if (DateUtility.month() < 10) {
            month = "0" + DateUtility.month() + "月";
        } else {
            month = DateUtility.month() + "月";
        }
        current.append(month);

        String day = "";
        if (DateUtility.day() < 10) {
            day = "0" + DateUtility.day() + "日";
        } else {
            day = DateUtility.day() + "日";
        }
        current.append(day);

        String hour = "";
        if (DateUtility.hour() < 10) {
            hour = "0" + DateUtility.hour() + "时";
        } else {
            hour = DateUtility.hour() + "时";
        }
        current.append(hour);

        String minute = "";
        if (DateUtility.minute() < 10) {
            minute = "0" + DateUtility.minute() + "分";
        } else {
            minute = DateUtility.minute() + "分";
        }
        current.append(minute);

        String second = "";
        if (DateUtility.second() < 10) {
            second = "0" + DateUtility.second() + "秒";
        } else {
            second = DateUtility.second() + "秒";
        }
        current.append(second);

        return current.toString();
    }

    /**
     * 年份
     *
     * @return
     */
    public static int year() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    /**
     * 月份
     *
     * @return
     */
    public static int month() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 日(号)
     *
     * @return
     */
    public static int day() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 小时(点)
     *
     * @return
     */
    public static int hour() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 分钟
     *
     * @return
     */
    public static int minute() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MINUTE);
    }

    /**
     * 秒
     *
     * @return
     */
    public static int second() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.SECOND);
    }

}
