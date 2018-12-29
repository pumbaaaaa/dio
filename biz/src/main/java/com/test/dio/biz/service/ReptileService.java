package com.test.dio.biz.service;

import com.google.common.collect.Lists;
import com.test.dio.biz.Constant;
import com.test.dio.biz.entity.ErrLog;
import com.test.dio.biz.entity.Floor;
import com.test.dio.biz.entity.Post;
import com.test.dio.biz.mapper.ErrLogMapper;
import com.test.dio.biz.mapper.FloorMapper;
import com.test.dio.biz.mapper.PostMapper;
import com.test.dio.biz.util.CommonUtil;
import com.test.dio.biz.util.DateUtil;
import org.apache.commons.codec.digest.DigestUtils;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.test.dio.biz.Constant.MAELSTROM;

@Service
public class ReptileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReptileService.class);

    private static final Map<String, String> COOKIE = new HashMap<String, String>() {{
        put("taihe", "b7f5af5859e5a53c87ce00f4db969733");
        put("ngaPassportOid", "fae26b9a206759a99255e05549baf771");
        put("ngacn0comUserInfo", "Excursion%09Excursion%0939%0939%09%0910%090%094%090%090%09");
        put("ngaPassportUid", "10315395");
        put("ngaPassportUrlencodedUname", "Excursion");
        put("ngaPassportCid", "b64bb2796ad892d1dbc0c2c1ac4035a4");
        put("lastpath", "/thread.php?fid=-7");

        put("Hm_lvt_5adc78329e14807f050ce131992ae69b", "1544492195,1544776707,1545118511,1545285546");
        put("ngacn0comUserInfoCheck", "16b900e110b43f08d1232f2563a9e920");
        put("ngacn0comInfoCheckTime", "1545373846");
        put("lastvisit", "1545383217");
        put("Hm_lpvt_5adc78329e14807f050ce131992ae69b", "1545383221");
        put("taihe_session", "dfffebfffee98f9fe5d32619cf1cde13");
        put("bbsmisccookies", "%7B%22uisetting%22%3A%7B0%3A64%2C1%3A1552209627%7D%2C%22insad_refreshid%22%3A%7B0%3A%22/154531817574267%22%2C1%3A1545968461%7D%2C%22pv_count_for_insad%22%3A%7B0%3A-37%2C1%3A1545411602%7D%2C%22insad_views%22%3A%7B0%3A1%2C1%3A1545411602%7D%7D");
        put("UM_distinctid", "167abe43f8a83b-0a24c6d5271b17-113c6654-100200-167abe43f8b40d");
        put("CNZZDATA30039253", "cnzz_eid%3D948383559-1544422023-%26ntime%3D1545379062");
        put("CNZZDATA30043604", "cnzz_eid%3D651755901-1544422500-%26ntime%3D1545380554");
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

    private final Executor executor = Executors.newFixedThreadPool(9,
            r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });

    @Autowired
    @Qualifier("primarySqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ErrLogMapper errLogMapper;

    public void maelstrom() {
        List<String> listUrl = CommonUtil.getListUrl(Constant.MAELSTROM_URL, 10);
        List<Post> posts = listUrl.stream()
                .flatMap(e -> getPost(e).stream())
                .collect(Collectors.toList());
        batchInsertPosts(posts);
    }

    private List<Post> getPost(String url) {
        List<Post> posts = new ArrayList<>();
        try {
            Document maelstromDoc = getDocument(url);
            Elements bodyElements = maelstromDoc.select("tbody");

            for (Element body : bodyElements) {
                Long replies = Long.valueOf(body.select("[class=c1]").select("a[href]").text());
                String href = body.select("[class=c2]").select("a[href]").attr("abs:href");
                String title = body.select("[class=c2]").select("a[href]").text();
                String user = body.select("[class=c3]").select("a[href]").attr("href");
                String userId = CommonUtil.subEqualSign(user);
                Long topicId = Long.valueOf(CommonUtil.subEqualSign(href));

                HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
                if (!opsForHash.hasKey(MAELSTROM, href)) {
                    Post post = Post.builder()
                            .topicId(topicId)
                            .title(title)
                            .url(href)
                            .replies(replies)
                            .userId(userId)
                            .build();
                    posts.add(post);

                    LOGGER.info("Query new post... replies: {}, title: {}", replies, title);
                    getFloor(href, topicId, replies, null);
                    opsForHash.put(MAELSTROM, href, String.valueOf(replies));
                } else {
                    String lastReplies = opsForHash.get(MAELSTROM, href);
                    if (lastReplies != null && Long.valueOf(lastReplies) < replies) {

                        LOGGER.info("Query old post starting from {} replies, title: {}", replies, title);
                        getFloor(href, topicId, replies, Long.valueOf(lastReplies));
                        opsForHash.put(MAELSTROM, href, String.valueOf(replies));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("=====>Get post form {} failed {}<=====", url, e.getMessage());
            errLogMapper.insertErrLog(ErrLog.builder()
                    .stackTrace(ExceptionUtils.getStackTrace(e))
                    .url(url)
                    .build());
        }
        return posts;
    }

    private void getFloor(String url, Long topicId, Long replies, Long lastReplies) {
        long queryStart = System.currentTimeMillis();
        List<String> repliesUrl = CommonUtil.getRepliesUrl(url, replies, lastReplies);
        List<CompletableFuture<List<Floor>>> futures = repliesUrl.stream()
                .map(u -> CompletableFuture.supplyAsync(() -> getFloorList(u, topicId, lastReplies), executor))
                .collect(Collectors.toList());
        List<Floor> allFloor = futures.stream()
                .flatMap(future -> future.join().stream())
                .collect(Collectors.toList());
        long queryEnd = System.currentTimeMillis();
        LOGGER.info("======>Query floors url: {} done in {} msecs<======", url, queryEnd - queryStart);
        batchInsertFloors(allFloor);
        long insertEnd = System.currentTimeMillis();
        LOGGER.info("======>Insert floors url: {} rows: {}  done in {} msecs<======", url, allFloor.size(), insertEnd - queryEnd);
    }

    private List<Floor> getFloorList(String url, Long topicId, Long lastReplies) {
        List<Floor> result = new ArrayList<>();
        try {
            Document postDoc = getDocument(url);
            Elements floorElements = postDoc.select("[class=forumbox postbox]");
            for (Element e : floorElements) {
                Elements dateElements = e.select("[id~=postdate\\d+]");
                Long floor = Long.valueOf(dateElements.attr("id").substring(8));
                if (null == lastReplies || floor > lastReplies) {
                    Date replyTime = DateUtil.parseStrWithPattern(dateElements.text(), Constant.YYYY_MM_DD_HH_MM);
                    String userHref = e.select("[id~=posterinfo\\d+]").select("a[href]").attr("abs:href");
                    String userId = CommonUtil.subEqualSign(userHref);
                    String content = e.select("[id~=postcontent\\d+]").text();
                    String hash = DigestUtils.md5Hex(topicId + floor + userId);
                    Floor f = Floor.builder()
                            .floor(floor)
                            .replyTime(replyTime)
                            .userId(userId)
                            .content(content)
                            .hash(hash)
                            .topicId(topicId)
                            .build();
                    result.add(f);
                }
            }
        } catch (Exception e) {
            LOGGER.error("=====>Get floor form {} failed {}<=====", url, e.getMessage());
            errLogMapper.insertErrLog(ErrLog.builder()
                    .stackTrace(ExceptionUtils.getStackTrace(e))
                    .lastReplies(lastReplies)
                    .url(url)
                    .build());
        }
        return result;
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
            Lists.partition(floors, 1000).forEach(list -> {
                list.forEach(floorMapper::insertFloor);
                session.commit();
            });
        }
    }
}
