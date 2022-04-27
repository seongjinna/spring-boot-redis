package com.example.demo.config.scheduler;

import com.example.demo.scheduler.MarketInfoScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

@Slf4j
@Configuration
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class SchedulerConfig {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void start() {
        JobDetail marketInfoJob = buildJobDetail(MarketInfoScheduler.class, new HashMap());
        JobDetail marketInfoJobStartNow = buildJobDetail(MarketInfoScheduler.class, new HashMap());

        try {
            // 5분 간격으로 실행
            scheduler.scheduleJob(marketInfoJob, buildJobTrigger("0 0/5 * 1/1 * ? *"));

            // 최초 1회 즉시 실행
            scheduler.scheduleJob(marketInfoJobStartNow, buildJobTriggerStartNow());
        }
        catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    private Trigger buildJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
                .build();
    }

    private Trigger buildJobTriggerStartNow() {
        return TriggerBuilder.newTrigger()
                .startNow()
                .build();
    }

    private JobDetail buildJobDetail(Class job, Map params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return newJob(job).usingJobData(jobDataMap).build();
    }
}
