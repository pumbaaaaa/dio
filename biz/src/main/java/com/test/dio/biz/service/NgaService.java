package com.test.dio.biz.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NgaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NgaService.class);

    public void test() throws IOException {
        Document doc = Jsoup.connect("bbs.nga.cn").get();
        LOGGER.info(doc.title());
        doc.select("");
    }
}
