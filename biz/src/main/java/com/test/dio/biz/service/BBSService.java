package com.test.dio.biz.service;

import com.test.dio.biz.entity.Floor;
import com.test.dio.biz.entity.Post;
import com.test.dio.biz.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.test.dio.biz.Constant.MAELSTROM;

@Service
public class BBSService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BBSService.class);

    private static final String URL = "https://bbs.nga.cn/thread.php?fid=-7";

    private static final Map<String, String> COOKIE = new HashMap<String, String>() {{
        put("taihe", "b7f5af5859e5a53c87ce00f4db969733");
        put("UM_distinctid", "1679701fe82110-0edf03b04b7dd9-113c6654-100200-1679701fe83212");
        put("bbsmisccookies", "%7B%22insad_refreshid%22%3A%7B0%3A%22/154399253466302%22%2C1%3A1545031546%7D%2C%22uisetting%22%3A%7B0%3A64%2C1%3A1552209627%7D%7D");
        put("Hm_lvt_5adc78329e14807f050ce131992ae69b", "1544426750,1544492195,1544776707,1545118511");
        put("taihe_session", "b5388c455022e815e1cb047e828d4bff");
        put("CNZZDATA1256638828", "1679748355-1544510337-https%253A%252F%252Fbbs.nga.cn%252F%7C1544510337");
        put("CNZZDATA1256638919", "1038003479-1544512733-%7C1544512733");
        put("CNZZDATA1256638851", "1618748851-1544771688-https%253A%252F%252Fbbs.nga.cn%252F%7C1544771688");
        put("CNZZDATA30039253", "cnzz_eid%3D948383559-1544422023-%26ntime%3D1545116700");
        put("CNZZDATA30043604", "cnzz_eid%3D651755901-1544422500-%26ntime%3D1545116056");
        put("CNZZDATA1256638820", "1330156108-1544433512-https%253A%252F%252Fbbs.nga.cn%252F%7C1545116148");
        put("ngaPassportOid", "fae26b9a206759a99255e05549baf771");
        put("ngacn0comUserInfo", "Excursion%09Excursion%0939%0939%09%0910%090%094%090%090%09");
        put("ngacn0comUserInfoCheck", "9028f564e63a4d19a4229131b265ae08");
        put("ngacn0comInfoCheckTime", "1545118499");
        put("ngaPassportUid", "10315395");
        put("ngaPassportUrlencodedUname", "Excursion");
        put("ngaPassportCid", "b64bb2796ad892d1dbc0c2c1ac4035a4");
        put("lastvisit", "1545118574");
        put("lastpath", "/thread.php?fid=-7");
        put("Hm_lpvt_5adc78329e14807f050ce131992ae69b", "1545118578");
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
        put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");
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
            if (!opsForHash.hasKey(MAELSTROM, href)) {
                Post post = Post.builder()
                        .topicId(Long.valueOf(topicId))
                        .title(title)
                        .relies(replies)
                        .build();

                List<Floor> postList = new ArrayList<>();
                getAllFloor(href, postList, topicId);
                opsForHash.put(MAELSTROM, href, replies);

            } else {


            }
        }

        String nextPage = maelstromDoc.select("[class=pager_spacer]").attr("abs:href");
        int page = Integer.parseInt(nextPage.substring(nextPage.lastIndexOf("=") + 1));
    }

    private void getAllFloor(String postUrl, List<Floor> list, String topicId) throws IOException {
        Document postDoc = getDocument(postUrl);
        Elements floorElements = postDoc.select("[class=forumbox postbox]");
        for (Element f : floorElements) {
            Elements dateElements = f.select("[id~=postdate\\d*]");
            String floor = dateElements.attr("id").substring(7);
            String replyTime = dateElements.text();
            String userHref = f.select("[id~=posterinfo\\d*]").select("a[href]").attr("abs:href");
            String userId = CommonUtil.subEqualSign(userHref);
            String content = f.select("[id~=postcontent\\d*]").text();
            Floor post = Floor.builder()
                    .floor(floor)
                    .replyTime(replyTime)
                    .userId(userId)
                    .content(content)
                    .topicId(Long.valueOf(topicId))
                    .build();
            list.add(post);
        }
        String nextPage = postDoc.select("[title=下一页]").select("a[href]").attr("abs:href");
        if (StringUtils.isNotEmpty(nextPage)) {
            getAllFloor(nextPage, list, topicId);
        }
    }

    private Document getDocument(String url) throws IOException {
        return Jsoup
                .connect(url)
                .headers(HEADER)
                .cookies(COOKIE)
                .get();
    }
}
