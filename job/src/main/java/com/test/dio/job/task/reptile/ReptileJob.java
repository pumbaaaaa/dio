package com.test.dio.job.task.reptile;

import com.test.dio.biz.reptile.service.ReptileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReptileJob {

    @Autowired
    private ReptileService reptileService;

    @Scheduled(fixedDelay = 50000L)
    public void reptileJob() {
        reptileService.maelstrom();
    }
}
