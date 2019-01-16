package com.test.dio.biz.consts;

import java.util.HashMap;
import java.util.Map;

public interface Constant {

    // Pattern
    String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    // URL
    String MAELSTROM = "maelstrom";
    String MAELSTROM_URL = "https://bbs.nga.cn/thread.php?fid=-7";

    // Regex
    String LABEL_REGEX = "\\[[^]]*]";
    String URL_REGEX = "((https?|ftp|file|)://|\\.\\/)[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%?=~_|]";

    // Pool Size
    int POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;

    // Header
    Map<String, String> HEADER = new HashMap<String, String>() {{
        put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        put("Accept-Encoding", "gzip, deflate, br");
        put("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7");
        put("Connection", "keep-alive");
        put("Host", "bbs.nga.cn");
        put("Referer", "https://bbs.nga.cn/thread.php?fid=-7");
        put("Upgrade-Insecure-Requests", "1");
        put("Cache-Control", "max-age=0");
        put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");
    }};
}
