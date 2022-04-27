package com.example.demo.scheduler;

import com.example.demo.service.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MarketInfoScheduler extends QuartzJobBean {

    private MarketService marketService;

    @Autowired
    public void setTickerService(MarketService marketService) {
        this.marketService = marketService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("마켓 정보 조회 스케쥴러 호출");
        this.marketService.createMarketInfoCacheDto();
    }
}
