package com.xiandong.fst.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.xiandong.fst.utils.StringUtil.isEmpty;
import static com.xiandong.fst.utils.StringUtil.strFormat2;

/**
 * TimeUtil
 *
 * @author Horo
 */
public class TimeUtil {
    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_TIME = "hh:mm";
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd hh:mm";
    public final static String FORMAT_MONTH_DAY_TIME = "MM-dd hh:mm";
    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat();
    private static final int YEAR = 365 * 24 * 60 * 60;
    private static final int MONTH = 30 * 24 * 60 * 60;
    private static final int DAY = 24 * 60 * 60;
    private static final int HOUR = 60 * 60;
    private static final int MINUTE = 60;


    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

    /**
     * 根据时间戳获取指定格式的时间，如2011-11-30 08:40
     *
     * @param timestamp 时间戳 单位为毫秒
     * @param format    指定格式 如果为null或空串则使用默认格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getFormatTimeFromTimestamp(long timestamp,
                                                    String format) {
        if (StringUtil.isBlank(format)) {
            mSimpleDateFormat.applyPattern(FORMAT_DATE);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int year = Integer.valueOf(mSimpleDateFormat.format(
                    new Date(timestamp)).substring(0, 4));
            if (currentYear == year) {
                mSimpleDateFormat.applyPattern(FORMAT_MONTH_DAY_TIME);
            } else {
                mSimpleDateFormat.applyPattern(FORMAT_DATE_TIME);
            }
        } else {
            mSimpleDateFormat.applyPattern(format);
        }
        Date date = new Date(timestamp);
        return mSimpleDateFormat.format(date);
    }

    /**
     * 根据时间戳获取时间字符串，并根据指定的时间分割数partionSeconds来自动判断返回描述性时间还是指定格式的时间
     *
     * @param timestamp      时间戳 单位是毫秒
     * @param partionSeconds 时间分割线，当现在时间与指定的时间戳的秒数差大于这个分割线时则返回指定格式时间，否则返回描述性时间
     * @param format
     * @return
     */
    public static String getMixTimeFromTimestamp(long timestamp,
                                                 long partionSeconds, String format) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;
        if (timeGap <= partionSeconds) {
            return convertTimeToFormat(timestamp);
        } else {
            return getFormatTimeFromTimestamp(timestamp, format);
        }
    }

    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getCurrentTime(String format) {
        if (StringUtil.isBlank(format)) {
            mSimpleDateFormat.applyPattern(FORMAT_DATE_TIME);
        } else {
            mSimpleDateFormat.applyPattern(format);
        }
        return mSimpleDateFormat.format(new Date());
    }

    /**
     * 将日期字符串以指定格式转换为Date
     *
     * @param timeStr 日期字符串
     * @param format  指定的日期格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static Date getTimeFromString(String timeStr, String format) {
        if (StringUtil.isBlank(format)) {
            mSimpleDateFormat.applyPattern(FORMAT_DATE_TIME);
        } else {
            mSimpleDateFormat.applyPattern(format);
        }
        try {
            return mSimpleDateFormat.parse(timeStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 将Date以指定格式转换为日期时间字符串
     *
     * @param time   日期
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getStringFromTime(Date time, String format) {
        if (StringUtil.isBlank(format)) {
            mSimpleDateFormat.applyPattern(FORMAT_DATE_TIME);
        } else {
            mSimpleDateFormat.applyPattern(format);
        }
        return mSimpleDateFormat.format(time);
    }


    /****
     * 将指定格式的时间字符串转换为时间戳
     *
     * @param timeStr
     * @param format
     * @return
     */
    public static long getLongFromTime(String timeStr, String format) {
        if (StringUtil.isBlank(format)) {
            mSimpleDateFormat.applyPattern(FORMAT_DATE_TIME);
        } else {
            mSimpleDateFormat.applyPattern(format);
        }
        try {
            return mSimpleDateFormat.parse(timeStr).getTime();
        } catch (ParseException e) {
            return new Date().getTime();
        }
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param format  格式
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }


    /**
     * 标准化日期时间类型的数据，不足两位的补0.
     *
     * @param dateTime 预格式的时间字符串，如:2012-3-2 12:2:20
     * @return String 格式化好的时间字符串，如:2012-03-20 12:02:20
     */
    public static String dateTimeFormat(String dateTime) {
        StringBuilder sb = new StringBuilder();
        try {
            if (isEmpty(dateTime)) {
                return null;
            }
            String[] dateAndTime = dateTime.split(" ");
            if (dateAndTime.length > 0) {
                for (String str : dateAndTime) {
                    if (str.indexOf("-") != -1) {
                        String[] date = str.split("-");
                        for (int i = 0; i < date.length; i++) {
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append("-");
                            }
                        }
                    } else if (str.indexOf(":") != -1) {
                        sb.append(" ");
                        String[] date = str.split(":");
                        for (int i = 0; i < date.length; i++) {
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append(":");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }


    /***
     * 把总秒数显示成00：00：00
     *
     * @param time 总秒数
     * @return 00：00：00
     */
    public static String showTime(int time) {
        StringBuffer sb = new StringBuffer();
        int o = time / 60 / 60;
        int m = time / 60;
        int f;
        int s = time % 60;
        if (o > 0) {
            if (o < 10) {
                sb.append("0" + o + ":");
            } else {
                sb.append(o + ":");
            }
        } else {
            sb.append("00:");
        }

        if (m > 0) {
            f = (time - o * 60 * 60) / 60;
            if (f < 10) {
                sb.append("0" + f + ":");
            } else {
                sb.append(f + ":");
            }
        } else {
            sb.append("00:");
        }

        if (s < 10) {
            sb.append("0" + s);
        } else {
            sb.append(s);
        }

        return sb.toString();
    }

    /***
     * 日期需要
     * @param c
     * @return
     */
    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public static String pad2(int c) {
        if (c == 12)
            return String.valueOf(c);
        if (c == 00)
            return String.valueOf(c + 12);
        if (c > 12)
            return String.valueOf(c - 12);
        else
            return String.valueOf(c);
    }

    public static String pad3(int c) {
        if (c == 12)
            return " PM";
        if (c == 00)
            return " AM";
        if (c > 12)
            return " PM";
        else
            return " AM";
    }
}
