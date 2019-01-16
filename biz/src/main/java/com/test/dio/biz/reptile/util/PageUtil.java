package com.test.dio.biz.reptile.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    public static List<String> getRepliesUrl(String url, Long replies, Long lastReplies) {
        List<String> urlList = new ArrayList<>();
        long start = 1;
        if (null != lastReplies) {
            start = lastReplies / 20 + 1;
        }
        for (long i = start; i <= replies / 20 + 1; i++) {
            urlList.add(url + "&page=" + i);
        }
        return urlList;
    }

    public static List<String> getListUrl(String url, int limit) {
        List<String> urlList = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            urlList.add(url + "&page=" + i);
        }
        return urlList;
    }

    public static String subEqualSign(String str) {
        return str.substring(str.lastIndexOf("=") + 1);
    }
}
