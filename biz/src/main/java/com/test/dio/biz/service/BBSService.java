package com.test.dio.biz.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BBSService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BBSService.class);

    private static final String URL = "https://bbs.nga.cn/thread.php?fid=-7&rand=880";

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36";

    public void test() throws IOException {
        Map<String, String> cookies = new HashMap<String, String>() {{
            put("taihe", "b7f5af5859e5a53c87ce00f4db969733");
            put("UM_distinctid", "1679701fe82110-0edf03b04b7dd9-113c6654-100200-1679701fe83212");
            put("Hm_lvt_5adc78329e14807f050ce131992ae69b", "1544426750");
            put("CNZZDATA30043604", "cnzz_eid%3D651755901-1544422500-%26ntime%3D1544431634");
            put("CNZZDATA30039253", "cnzz_eid%3D948383559-1544422023-%26ntime%3D1544432928");
            put("CNZZDATA1256638820", "1330156108-1544433512-https%253A%252F%252Fbbs.nga.cn%252F%7C1544433512");
            put("ngaPassportUid", "guest05c0e36ac0a59c");
            put("taihe_session", "35953ccfb6a76ed8d4475ebcd0efe673");
            put("bbsmisccookies", "%7B%22insad_refreshid%22%3A%7B0%3A%22/154399253466302%22%2C1%3A1545031546%7D%2C%22pv_count_for_insad%22%3A%7B0%3A16%2C1%3A1544461293%7D%2C%22insad_views%22%3A%7B0%3A0%2C1%3A1544461293%7D%2C%22uisetting%22%3A%7B0%3A64%2C1%3A1552209627%7D%7D");
            put("Hm_lpvt_5adc78329e14807f050ce131992ae69b", "1544437033");
            put("lastvisit", "1544437096");
            put("guestJs", "154443709");
        }};
        Map<String, String> headers = new HashMap<String, String>() {{
            put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            put("Accept-Encoding", "gzip, deflate, br");
            put("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7");
            put("Connection", "keep-alive");
            put("Host", "bbs.nga.cn");
            put("Referer", "https://bbs.nga.cn/thread.php?fid=-7");
            put("Upgrade-Insecure-Requests", "1");
        }};

        Document doc = Jsoup
                .connect(URL)
                .headers(headers)
                .cookies(cookies)
                .userAgent(USER_AGENT)
                .get();
        LOGGER.info(doc.title());
    }
}
