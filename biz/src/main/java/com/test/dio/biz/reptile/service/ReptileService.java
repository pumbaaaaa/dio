package com.test.dio.biz.reptile.service;

import com.google.common.collect.Lists;
import com.test.dio.biz.consts.Constant;
import com.test.dio.biz.reptile.entity.ErrLog;
import com.test.dio.biz.reptile.entity.Floor;
import com.test.dio.biz.reptile.entity.Post;
import com.test.dio.biz.reptile.mapper.ErrLogMapper;
import com.test.dio.biz.reptile.mapper.FloorMapper;
import com.test.dio.biz.reptile.mapper.PostMapper;
import com.test.dio.biz.reptile.util.PageUtil;
import com.test.dio.base.util.DateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.test.dio.biz.consts.Constant.MAELSTROM;

@Service
public class ReptileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReptileService.class);

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

    private static final Map<String, String> COOKIE = new HashMap<String, String>() {{
        put("taihe", "b7f5af5859e5a53c87ce00f4db969733");
        put("ngacn0comUserInfo", "Excursion%09Excursion%0939%0939%09%0910%090%094%090%090%09");
        put("ngaPassportUid", "10315395");
        put("ngaPassportUrlencodedUname", "Excursion");
        put("ngaPassportCid", "b64bb2796ad892d1dbc0c2c1ac4035a4");
        put("lastpath", "/thread.php?fid=-7");
        put("UM_distinctid", "167abe43f8a83b-0a24c6d5271b17-113c6654-100200-167abe43f8b40d");

        put("ngaPassportOid", "e41337d1af7ded63b3fc4d457974903c");
        put("Hm_lvt_5adc78329e14807f050ce131992ae69b", "1545285546,1545738363,1546422366,1547627130");
        put("ngacn0comUserInfoCheck", "683c2e5b67b3b2e15886b63e24316165");
        put("ngacn0comInfoCheckTime", "1547627156");
        put("lastvisit", "1547627168");
        put("Hm_lpvt_5adc78329e14807f050ce131992ae69b", "1547627171");
        put("taihe_session", "6628820b64ff0e1b245604cb4b5d7614");
        put("bbsmisccookies", "%7B%22uisetting%22%3A%7B0%3A0%2C1%3A1553514372%7D%2C%22insad_refreshid%22%3A%7B0%3A%22/154684951899572%22%2C1%3A1548231926%7D%2C%22pv_count_for_insad%22%3A%7B0%3A-46%2C1%3A1547658052%7D%2C%22insad_views%22%3A%7B0%3A1%2C1%3A1547658052%7D%7D");
    }};

    private final Executor executor = Executors.newFixedThreadPool(Constant.POOL_SIZE,
            r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });

    @Qualifier("primarySqlSessionFactory")
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ErrLogMapper errLogMapper;

    // Sync get post
    public void reptile(String url, int limit) {
        List<String> listUrl = PageUtil.getListUrl(url, limit);
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

                // Post hide
                if (StringUtils.isBlank(href)) {
                    continue;
                }

                String userId = PageUtil.subEqualSign(user);
                Long topicId = Long.valueOf(PageUtil.subEqualSign(href));

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
        } catch (IOException e) {
            LOGGER.error("=====>Get post form {} failed {}<=====", url, e.getMessage());
            errLogMapper.insertErrLog(ErrLog.builder()
                    .stackTrace(ExceptionUtils.getStackTrace(e))
                    .url(url)
                    .build());
        }
        return posts;
    }

    // Async get all floor
    private void getFloor(String url, Long topicId, Long replies, Long lastReplies) {
        long queryStart = System.currentTimeMillis();

        List<String> repliesUrl = PageUtil.getRepliesUrl(url, replies, lastReplies);
        List<CompletableFuture<List<Floor>>> futures = repliesUrl.stream()
                .map(u -> CompletableFuture.supplyAsync(() -> getFloorList(u, topicId, lastReplies), executor))
                .collect(Collectors.toList());

        List<Floor> allFloor = futures.stream()
                .flatMap(future -> future.join().stream())
                .collect(Collectors.toList());

        // deduplication
        List<Floor> deduplicationFloors = deduplicationFloors(allFloor);
        long queryEnd = System.currentTimeMillis();
        LOGGER.info("======>Query floors url: {} done in {} msecs<======", url, queryEnd - queryStart);
        batchInsertFloors(deduplicationFloors);
        long insertEnd = System.currentTimeMillis();
        LOGGER.info("======>Insert floors url: {} rows: {}  done in {} msecs<======", url, allFloor.size(), insertEnd - queryEnd);
    }

    // Get floor & insert
    private List<Floor> getFloorList(String url, Long topicId, Long lastReplies) {
        List<Floor> result = new ArrayList<>();
        try {
            Document postDoc = getDocument(url);
            Elements floorElements = postDoc.select("[class=forumbox postbox]");

            for (Element e : floorElements) {
                Elements dateElements = e.select("[id~=postdate\\d+]");
                String date = dateElements.text();
                Long floor = Long.valueOf(dateElements.attr("id").substring(8));

                if (null == lastReplies || floor > lastReplies) {
                    Date replyTime = DateUtil.parseStrWithPattern(date, Constant.YYYY_MM_DD_HH_MM);
                    String userHref = e.select("[id~=posterinfo\\d+]").select("a[href]").attr("abs:href");
                    String userId = PageUtil.subEqualSign(userHref);
                    String hash = DigestUtils.md5Hex(topicId + floor + userId + date);
                    String content = e.select("[id~=postcontent\\d+]").text();

                    Floor f = Floor.builder()
                            .floor(floor)
                            .replyTime(replyTime)
                            .userId(userId)
                            .topicId(topicId)
                            .hash(hash)
                            .build();

                    setContentAndUrl(f, content);
                    result.add(f);
                }
            }
        } catch (IOException e) {
            LOGGER.error("=====>Get floor form {} failed {}<=====", url, e.getMessage());
            errLogMapper.insertErrLog(ErrLog.builder()
                    .stackTrace(ExceptionUtils.getStackTrace(e))
                    .lastReplies(lastReplies)
                    .url(url)
                    .build());
        }
        return result;
    }

    // Clean content & get url
    private void setContentAndUrl(Floor floor, String content) {
        String cleanContent = content
                .replaceAll(Constant.LABEL_REGEX, "")
                .replaceAll(Constant.URL_REGEX, "");
        Pattern pattern = Pattern.compile(Constant.URL_REGEX);
        Matcher matcher = pattern.matcher(content);
        List<String> urlList = new ArrayList<>();
        while (matcher.find()) {
            String str = matcher.group();
            if (str.startsWith("./")) {
                urlList.add(Constant.ATTACHMENTS + str.substring(1));
            } else {
                urlList.add(str);
            }
        }
        floor.setContent(StringUtils.trim(cleanContent));
        floor.setUrl(StringUtils.join(urlList, ","));
    }

    private List<Floor> deduplicationFloors(List<Floor> allFloor) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        List<Floor> dFloors = allFloor.stream()
                .filter(e -> !set.isMember(Constant.DEDUPLICATION, e.getHash()))
                .collect(Collectors.toList());
        allFloor.forEach(e -> set.add(Constant.DEDUPLICATION, e.getHash()));
        return dFloors;
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
