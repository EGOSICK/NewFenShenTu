package com.xiandong.fst.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 判断是否空白
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 判断是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 判断是否相等
     *
     * @param actual
     * @param expected
     * @return
     */
    public static boolean isEquals(String actual, String expected) {
        return ObjectUtil.isEquals(actual, expected);
    }

    /**
     * 将null字符串转换为空字符串
     *
     * @param str
     * @return
     */
    public static String nullStrToEmpty(String str) {
        return (str == null ? "" : str);
    }

    /**
     * 将首字母大写
     * <p/>
     * <pre>
     * capitalizeFirstLetter(null) = null;
     * capitalizeFirstLetter("") = "";
     * capitalizeFirstLetter("2ab") = "2ab"
     * capitalizeFirstLetter("a") = "A"<
     * capitalizeFirstLetter("ab") = "Ab"
     * capitalizeFirstLetter("Abc") = "Abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
                : new StringBuilder(str.length())
                .append(Character.toUpperCase(c))
                .append(str.substring(1)).toString();
    }

    /**
     * long型转成小数点后两位的double型
     *
     * @param l
     * @return
     */
    public static String longToWan(long l) {
        if (l < 10000)
            return String.valueOf(l);

        return String.format("%1$.2f", (double) l / 10000) + "万";
    }

    /**
     * UTF-8编码
     * <p/>
     * <pre>
     * utf8Encode(null) = null
     * utf8Encode("") = "";
     * utf8Encode("aa") = "aa";
     * utf8Encode("啊啊啊啊") = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     *
     * @param str
     * @return
     */
    public static String utf8Encode(String str) {

        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * UTF-8编码 如果异常则返回默认值
     * <p/>
     * <pre>
     * utf8Encode(null) = null
     * utf8Encode("") = "";
     * utf8Encode("aa") = "aa";
     * utf8Encode("啊啊啊啊") = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     *
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * 全宽字符变换半宽字符
     * <p/>
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth("") = "";
     * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 半宽字符变换全宽字符
     * <p/>
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth("") = "";
     * halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 手机号格式验证.
     *
     * @param str 指定的手机号码字符串
     * @return 是否为手机号码格式:是为true，否则false
     */
    public static Boolean isMobileNo(String str) {
        Boolean isMobileNo = false;
        try {
            Pattern p = Pattern
                    .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(str);
            isMobileNo = m.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isMobileNo;
    }


    /**
     * 手机号:目前全国有27种手机号段。
     * 移动有16个号段：134、135、136、137、138、139、147、150、151、152、157、158、159、182、187、188。其中147、157、188是3G号段，其他都是2G号段。
     * 联通有7种号段：130、131、132、155、156、185、186。其中186是3G（WCDMA）号段，其余为2G号段。
     * 电信有4个号段：133、153、180、189。其中189是3G号段（CDMA2000），133号段主要用作无线网卡号。
     * 150、151、152、153、155、156、157、158、159 九个;
     * 130、131、132、133、134、135、136、137、138、139 十个;
     * 180、182、185、186、187、188、189 七个;
     * 13、15、18三个号段共30个号段，154、181、183、184暂时没有，加上147共27个。
     */
    public static boolean isTelPhone(String tel) {
        Pattern p = Pattern.compile("^((13\\d{9}$)|(15\\d{9}$)|(145\\d{9}$)|(177\\d{8}$)|(18[0,2,3,5,6,7,8,9]\\d{8}$)|(147\\d{8})$)");
        Matcher m = p.matcher(tel);
        return m.matches();
    }


    /***
     * 检验身份证是否合法
     *
     * @param num
     * @return
     */
    public static boolean checkCardNum(String num) {
        Pattern idNumPattern;
        Matcher idNumMatcher;
        boolean isTure;
        switch (num.length()) {
            case 15:
                idNumPattern = Pattern.compile("/^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$/");
                idNumMatcher = idNumPattern.matcher(num);
                isTure = idNumMatcher.matches();
                break;
            case 18:
                idNumPattern = Pattern.compile("/^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$/");
                idNumMatcher = idNumPattern.matcher(num);
                isTure = idNumMatcher.matches();
                break;
            default:
                isTure = false;
                break;
        }

        return isTure;

    }

    /**
     * 是否只是字母和数字.
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberLetter(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }

    /**
     * 是否只是数字.
     *
     * @param str 指定的字符串
     * @return 是否只是数字:是为true，否则false
     */
    public static Boolean isNumber(String str) {
        Boolean isNumber = false;
        String expr = "^[0-9]+$";
        if (str.matches(expr)) {
            isNumber = true;
        }
        return isNumber;
    }

    /**
     * 是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    /**
     * 是否是中文.
     *
     * @param str 指定的字符串
     * @return 是否是中文:是为true，否则false
     */
    public static Boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                // 获取一个字符
                String temp = str.substring(i, i + 1);
                // 判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    /**
     * 是否包含中文.
     *
     * @param str 指定的字符串
     * @return 是否包含中文:是为true，否则false
     */
    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                // 获取一个字符
                String temp = str.substring(i, i + 1);
                // 判断是否为中文字符
                if (temp.matches(chinese)) {
                    isChinese = true;
                } else {

                }
            }
        }
        return isChinese;
    }


    /**
     * 不足2个字符的在前面补“0”.
     *
     * @param str 指定的字符串
     * @return 至少2个字符的字符串
     */
    public static String strFormat2(String str) {
        try {
            if (str.length() <= 1) {
                str = "0" + str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取某个字符串包含另一个字符串的个数
     *
     * @param src
     * @param find
     * @return
     */
    public static int getContainCount(String src, String find) {
        int count = 0;
        while (src.indexOf(find) >= 0) {
            count++;
            src = src.replaceFirst(find, "");
        }
        return count;
    }

    /***
     * 去掉所有空格
     *
     * @param str
     * @return
     */
    public static String replaceEmp(String str) {
        String s = "";
        if (str != null && str.length() > 0) {
            s = str.replaceAll("\\s*", "");
        }
        return s;
    }

    /**
     * 保留两位
     * @param d
     * @return
     */
    public static String doubleFormat(double d) {
        return new DecimalFormat("#0.00").format(d);
    }
}
