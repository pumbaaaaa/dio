package com.test.dio.biz.consts;

public interface Constant {

    // Pattern
    String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    // URL
    String MAELSTROM = "maelstrom";
    String MAELSTROM_URL = "https://bbs.nga.cn/thread.php?fid=-7";      //maelstrom
    String BASKETBALL_URL = "https://bbs.nga.cn/thread.php?fid=485";    //basketball
    String FOOTBALL_URL = "https://bbs.nga.cn/thread.php?fid=-81981";   //football
    String GAME_COMPLEX_URL = "https://bbs.nga.cn/thread.php?fid=414";  //gameComplex
    String ATTACHMENTS = "https://img.nga.178.com/attachments";

    // Regex
    String LABEL_REGEX = "\\[[^]]*]";
    String URL_REGEX = "((https?|ftp|file|)://|\\.\\/)[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%?=~_|]";

    // Thread Pool
    int POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;

    // Deduplication
    String DEDUPLICATION = "floor_deduplication";
}
