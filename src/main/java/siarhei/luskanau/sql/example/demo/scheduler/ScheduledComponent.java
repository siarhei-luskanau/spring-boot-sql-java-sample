package siarhei.luskanau.sql.example.demo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ScheduledComponent {

    private static final Logger log = LoggerFactory.getLogger(ScheduledComponent.class);
    private final CsvChecker csvChecker;
    private final String timeZoneValue;

    @Autowired
    public ScheduledComponent(
            CsvChecker csvChecker,
            @Value("${scheduler.timezone}") String timeZoneValue
    ) {
        this.csvChecker = csvChecker;
        this.timeZoneValue = timeZoneValue;
    }

    // @Scheduled(fixedRate = 60000)
    @Scheduled(cron = "${scheduler.cron}")
    public void execute() {
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of(timeZoneValue));
        log.info("CsvChecker.check: {}", currentTime);
        csvChecker.check(currentTime);
    }
}
