package au.com.rainmore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private static final Logger log = LoggerFactory.getLogger(SchedulerConfig.class);

    @Value("${app.scheduler}")
    private String appScheduler;

    @Scheduled(cron = "${app.scheduler:0 */5 * * * *}")
    public void scheduler() {
        log.info(String.format("Log at %s for scheduler %s", LocalDateTime.now(),  appScheduler));
    }

}
