package com.tencent.common.utils;


/**
 * 字符串工具类（统一出口，便于切换）
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 判断字符串是否为null或空白
     *
     * @param cs 字符串
     * @return 为null或空白时返回true ，其他返回false
     */
    public static boolean isBlank(final CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isBlank(cs);
    }

    /**
     * 判断字符串是否非null且非空白
     *
     * @param cs 字符串
     * @return 非null且非空白时返回true ，其他返回false
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 判断字符串不能为空白，且返回自身
     *
     * @param cs 字符串
     * @return 字符串 string
     * @throws NullPointerException 当字符串为空白时
     */
    public static String requireNonBlank(final CharSequence cs) {
        if (isBlank(cs)) {
            throw new NullPointerException();
        }
        return (String) cs;
    }

    /**
     * 判断两个字符串是否相等
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 若相等 ，返回true
     */
    public static boolean equals(CharSequence str1, CharSequence str2) {
        return org.apache.commons.lang3.StringUtils.equals(str1, str2);
    }

    /**
     * 替换所有的"."为"_"
     *
     * @param str 字符串
     * @return 替换后字符串 string
     */
    public static String replaceAllPointToUnderline(String str) {
        return str.replaceAll("\\.", "_");
    }

    /**
     * 替换所有的"."为"/"
     *
     * @param str 字符串
     * @return 替换后字符串 string
     */
    public static String replaceAllPointToSlash(String str) {
        return str.replaceAll("\\.", "/");
    }

    /**
     * 将传入的字符串首字母小写并返回
     *
     * @param name 字符串
     * @return 首字母小写字符串 string
     */
    public static String lowerFirst(String name) {
        String first = name.substring(0, 1).toLowerCase();
        return first + name.substring(1);
    }

    /**
     * 将传入的字符串首字母大写并返回
     *
     * @param name 字符串
     * @return 首字母小写字符串 string
     */
    public static String upperFirst(String name) {
        String first = name.substring(0, 1).toUpperCase();
        return first + name.substring(1);
    }

    /**
     * 将字符串除前几位和后几位以外的字符串转化为*号
     *
     * @param content 字符串                frontNum  显示前几位的位数                endNum    显示后几位的位数
     * @param frontNum the front num
     * @param endNum the end num
     * @return string
     */
    public static String makeStringToStarShow(String content, int frontNum, int endNum) {
        if (content == null || content.trim().isEmpty()) {
            return content;
        }
        int len = content.length();

        if (frontNum >= len || frontNum < 0 || endNum >= len || endNum < 0) {
            return content;
        }

        if (frontNum + endNum >= len) {
            return content;
        }

        int beginIndex = frontNum;
        int endIndex = len - endNum;
        char[] cardChar = content.toCharArray();

        for (int j = beginIndex; j < endIndex; j++) {
            cardChar[j] = '*';
        }
        return new String(cardChar);
    }


    /**
     * 将字符串前几位和后几位的字符串转化为*号
     *
     * @param content 字符串                frontNum  隐藏前几位的位数                endNum    隐藏后几位的位数
     * @param frontNum the front num
     * @param endNum the end num
     * @return string
     */
    public static String makeStringToStarHidden(String content, int frontNum, int endNum) {
        if (content == null || content.trim().isEmpty()) {
            return content;
        }
        int len = content.length();

        if (frontNum >= len || frontNum < 0 || endNum >= len || endNum < 0) {
            return content;
        }

        if (frontNum + endNum >= len) {
            return content;
        }

        int beginIndex = frontNum;
        int endIndex = len - endNum;
        char[] cardChar = content.toCharArray();

        for (int j = 0; j < beginIndex; j++) {
            cardChar[j] = '*';
        }
        for (int j = endIndex; j < len; j++) {
            cardChar[j] = '*';
        }
        return new String(cardChar);
    }

    /**
     * 正则特殊字符转义
     *
     * @param str the str
     * @return string buffer
     */
    public static StringBuffer regexEscape(String str) {
        String str1 = "*.?+$^[](){}|\\/";
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            String ss = String.valueOf(str.charAt(i));
            if (str1.contains(ss)) {
                ss = "\\" + ss;
            }
            sf.append(ss);
        }
        return sf;
    }
}
