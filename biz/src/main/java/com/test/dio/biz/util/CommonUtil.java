package com.test.dio.biz.util;

public class CommonUtil {

    public static String subEqualSign(String str) {
        return str.substring(str.lastIndexOf("=") + 1);
    }
}
