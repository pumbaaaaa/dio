package com.test.dio.biz.util;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

    public static List<String> getRepliesUrl(String url, Long replies, Long lastReplies) {
        List<String> urlList = new ArrayList<>();
        long start = 2;
        if (null != lastReplies) {
            start = lastReplies / 20 + 1;
        } else {
            urlList.add(url);
        }
        for (long i = start; i <= replies / 20 + 1; i++) {
            urlList.add(url + "&page=" + i);
        }
        return urlList;
    }

    public static String getLastRepliesUrl(String url, Long replies) {
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
