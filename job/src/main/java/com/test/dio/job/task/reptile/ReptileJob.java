package com.test.dio.job.task.reptile;

import com.test.dio.biz.consts.Constant;
import com.test.dio.biz.reptile.service.ReptileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReptileJob {

    private static final int PAGE_LIMIT = 30;

    @Autowired
    private ReptileService reptileService;

    @Scheduled(fixedDelay = 300000L)
    public void maelstromReptileJob() {
        reptileService.reptile(Constant.MAELSTROM_URL, PAGE_LIMIT);
    }

    @Scheduled(fixedDelay = 600000L)
    public void reptileJob() {
        reptileService.reptile(Constant.GAME_COMPLEX_URL, PAGE_LIMIT);
        reptileService.reptile(Constant.BASKETBALL_URL, PAGE_LIMIT);
        reptileService.reptile(Constant.FOOTBALL_URL, PAGE_LIMIT);
    }
}
