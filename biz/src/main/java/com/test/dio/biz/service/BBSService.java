package com.test.dio.biz.service;

import com.test.dio.biz.entity.Post;
import com.test.dio.biz.util.CommonUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.test.dio.biz.Constant.MAELSTROM;

@Service
public class BBSService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BBSService.class);

    private static final String URL = "https://bbs.nga.cn/thread.php?fid=-7";

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36";

    private static final int TIME_OUT = 3000;

    private static final Map<String, String> COOKIE = new HashMap<String, String>() {{
        put("taihe", "b7f5af5859e5a53c87ce00f4db969733");
        put("UM_distinctid", "1679701fe82110-0edf03b04b7dd9-113c6654-100200-1679701fe83212");
        put("bbsmisccookies", "%7B%22insad_refreshid%22%3A%7B0%3A%22/154399253466302%22%2C1%3A1545031546%7D%2C%22uisetting%22%3A%7B0%3A64%2C1%3A1552209627%7D%7D");
        put("Hm_lvt_5adc78329e14807f050ce131992ae69b", "1544426750,1544492195");
        put("taihe_session", "bd60cb233fdb77255f6b2d69056fea1c");
        put("CNZZDATA30043604", "cnzz_eid%3D651755901-1544422500-%26ntime%3D1544491034");
        put("CNZZDATA30039253", "cnzz_eid%3D948383559-1544422023-%26ntime%3D1544486929");
        put("ngaPassportOid", "fae26b9a206759a99255e05549baf771");
        put("ngacn0comUserInfo", "Excursion%09Excursion%0939%0939%09%0910%090%094%090%090%09");
        put("ngacn0comUserInfoCheck", "e7523affa798d1368cb748291b4b83ad");
        put("ngacn0comInfoCheckTime", "1544492217");
        put("ngaPassportUid", "10315395");
        put("ngaPassportUrlencodedUname", "Excursion");
        put("ngaPassportCid", "b64bb2796ad892d1dbc0c2c1ac4035a4");
        put("lastvisit", "1544492226");
        put("lastpath", "/thread.php?fid=-7");
        put("Hm_lpvt_5adc78329e14807f050ce131992ae69b", "1544492229");
        put("CNZZDATA1256638820", "1330156108-1544433512-https%253A%252F%252Fbbs.nga.cn%252F%7C1544487539");
    }};

    private static final Map<String, String> HEADER = new HashMap<String, String>() {{
        put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        put("Accept-Encoding", "gzip, deflate, br");
        put("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7");
        put("Connection", "keep-alive");
        put("Host", "bbs.nga.cn");
        put("Referer", "https://bbs.nga.cn/thread.php?fid=-7");
        put("Upgrade-Insecure-Requests", "1");
        put("Cache-Control", "max-age=0");
    }};

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void maelstrom() throws IOException {

        Document maelstromDoc = getDocument(URL);
        Elements tbodyElements = maelstromDoc.select("tbody");
        for (Element tbody : tbodyElements) {
            String replies = tbody.select("[class=c1]").select("a[href]").text();
            String href = tbody.select("[class=c2]").select("a[href]").attr("abs:href");
            String title = tbody.select("[class=c2]").select("a[href]").text();
            String topicId = CommonUtil.subEqualSign(href);

            HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
            if (opsForHash.hasKey(MAELSTROM, href)) {

            } else {
                opsForHash.put(MAELSTROM, href, replies);
            }

        }

        Elements c2Elements = maelstromDoc.select("[class=c2]");
        for (Element td : c2Elements) {
            String href = td.select("a[href]").attr("abs:href");
            String topicId = CommonUtil.subEqualSign(href);


            Document postDoc = getDocument(href);
            Elements floorElements = postDoc.select("[class=forumbox postbox]");
            AtomicLong floor = new AtomicLong();

            floorElements.forEach(f -> {
                String uHref = f.getElementById("postInfo" + floor).select("a[href]").attr("href");
                String userId = CommonUtil.subEqualSign(uHref);
                String replyTime = f.getElementById("postdate" + floor).text();
                String content = f.getElementById("postcontent" + floor).text();
                Post post = Post.builder()
                        .topicId(topicId)
//                        .title(title)
                        .floor(floor.longValue())
                        .userId(userId)
                        .content(content)
                        .replyTime(replyTime)
                        .build();
            });

        }

        String nextPage = maelstromDoc.select("[class=pager_spacer]").attr("abs:href");
        int page = Integer.parseInt(nextPage.substring(nextPage.lastIndexOf("=") + 1));
    }

    private Document getDocument(String url) throws IOException {
        return Jsoup
                .connect(url)
                .headers(HEADER)
                .cookies(COOKIE)
                .userAgent(USER_AGENT)
                .timeout(TIME_OUT)
                .get();
    }

    public static void main(String[] args) {
        String url = "nuke.php?func=ucp&amp;uid=623521";
        String substring = url.substring(url.lastIndexOf("=") + 1);
        System.out.println(substring);
    }
}
