package com.test.dio.biz.service;

import com.test.dio.biz.Constant;
import com.test.dio.biz.entity.Floor;
import com.test.dio.biz.entity.Post;
import com.test.dio.biz.mapper.FloorMapper;
import com.test.dio.biz.mapper.PostMapper;
import com.test.dio.biz.util.CommonUtil;
import com.test.dio.biz.util.DateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.test.dio.biz.Constant.MAELSTROM;

@Service
public class ReptileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReptileService.class);

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

    private static final int PAGE_LIMIT = 5;

    @Autowired
    @Qualifier("primarySqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void maelstrom() {
        List<Post> posts = new ArrayList<>();
        try {
            getPost(URL, PAGE_LIMIT, posts);
            batchInsertPosts(posts);
        } catch (IOException e) {
            LOGGER.error("==========> Reptile maelstrom failed <==========\n {}", ExceptionUtils.getStackTrace(e));
        }
    }

    private void getPost(String url, int limit, List<Post> posts) throws IOException {
        Document maelstromDoc = getDocument(url);
        Elements bodyElements = maelstromDoc.select("tbody");

        for (Element body : bodyElements) {
            Long replies = Long.valueOf(body.select("[class=c1]").select("a[href]").text());
            String href = body.select("[class=c2]").select("a[href]").attr("abs:href");
            String title = body.select("[class=c2]").select("a[href]").text();
            String user = body.select("[class=c3]").select("a[href]").attr("href");
            Long userId = Long.valueOf(CommonUtil.subEqualSign(user));
            Long topicId = Long.valueOf(CommonUtil.subEqualSign(href));

            HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
            List<Floor> floors = new ArrayList<>();
            if (!opsForHash.hasKey(MAELSTROM, href)) {
                Post post = Post.builder()
                        .topicId(topicId)
                        .title(title)
                        .relies(replies)
                        .userId(userId)
                        .build();
                posts.add(post);

                LOGGER.info("Query new post... replies: {}, title: {}, user: {}", replies, title, userId);
                getFloor(href, floors, topicId, null);
                opsForHash.put(MAELSTROM, href, String.valueOf(replies));
            } else {
                String lastReplies = opsForHash.get(MAELSTROM, href);
                if (lastReplies != null && Long.valueOf(lastReplies) < replies) {

                    LOGGER.info("Query old post starting from {} replies, title: {}, user: {}", replies, title, userId);
                    getFloor(href, floors, topicId, replies);
                    opsForHash.put(MAELSTROM, href, String.valueOf(replies));
                }
            }
            batchInsertFloors(floors);
        }
        String nextPage = maelstromDoc.select("[title=下一页]").select("a[href]").attr("abs:href");
        int page = Integer.parseInt(CommonUtil.subEqualSign(nextPage));
        if (page <= limit) {
            getPost(nextPage, limit, posts);
        }
    }

    private void getFloor(String url, List<Floor> list, Long topicId, Long replies) throws IOException {
        //todo #anony_b474f8ae094bfa3c76c5d7486d9c5e7d
        if (null != replies) {
            url = CommonUtil.getRepliesPage(url, replies);
        }
        Document postDoc = getDocument(url);
        String nextPage = postDoc.select("[title=下一页]").select("a[href]").attr("abs:href");
        Elements floorElements = postDoc.select("[class=forumbox postbox]");
        for (Element f : floorElements) {
            Elements dateElements = f.select("[id~=postdate\\d*]");
            Long floor = Long.valueOf(dateElements.attr("id").substring(8));
            if (null == replies || floor > replies) {
                Date replyTime = DateUtil.parseStrWithPattern(dateElements.text(), Constant.YYYY_MM_DD_HH_MM);
                String userHref = f.select("[id~=posterinfo\\d*]").select("a[href]").attr("abs:href");
                Long userId = Long.valueOf(CommonUtil.subEqualSign(userHref));
                String content = f.select("[id~=postcontent\\d*]").text();
                String hash = DigestUtils.md5Hex(String.valueOf(topicId + floor + userId));
                Floor post = Floor.builder()
                        .floor(floor)
                        .replyTime(replyTime)
                        .userId(userId)
                        .content(content)
                        .hash(hash)
                        .topicId(topicId)
                        .build();
                list.add(post);
            }
        }
        if (StringUtils.isNotEmpty(nextPage)) {
            getFloor(nextPage, list, topicId, null);
        }
    }

    private Document getDocument(String url) throws IOException {
        return Jsoup
                .connect(url)
                .headers(HEADER)
                .cookies(COOKIE)
                .get();
    }

    private void batchInsertPosts(List<Post> posts) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            PostMapper postMapper = session.getMapper(PostMapper.class);
            posts.forEach(postMapper::insertPost);
            session.commit();
        }
    }

    private void batchInsertFloors(List<Floor> floors) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            FloorMapper floorMapper = session.getMapper(FloorMapper.class);
            floors.forEach(floorMapper::insertFloor);
            session.commit();
        }
    }
}
