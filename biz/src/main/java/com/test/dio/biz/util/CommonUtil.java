package com.test.dio.biz.util;

public class CommonUtil {

    public static String getRepliesPage(String url, Long replies) {
        long page = replies / 20 + 1;
        if (page != 1) {
            return url + "&page=" + page;
        }
        return url;
    }

    public static String subEqualSign(String str) {
        return str.substring(str.lastIndexOf("=") + 1);
    }
}
