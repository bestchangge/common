package com.sun.baselibrary.util;

/**
 * @author Sun
 * @created: 2019/6/10 17:29
 * @description:
 */
public class StringUtil {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
