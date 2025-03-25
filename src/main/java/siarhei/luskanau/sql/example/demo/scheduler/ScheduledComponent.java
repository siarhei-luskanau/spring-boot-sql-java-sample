package siarhei.luskanau.sql.example.demo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledComponent {

    private final CsvChecker csvChecker;

    private static final Logger log = LoggerFactory.getLogger(ScheduledComponent.class);

    @Autowired
    public ScheduledComponent(CsvChecker csvChecker) {
        this.csvChecker = csvChecker;
    }

    // @Scheduled(fixedRate = 60000)
    @Scheduled(cron = "${scheduler.cron}")
    public void execute() {
        log.info("CsvChecker.check {}", new Date());
        csvChecker.check();
    }
}
