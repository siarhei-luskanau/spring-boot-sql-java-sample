package siarhei.luskanau.sql.example.demo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SchedulerAction {

    private static final Logger log = LoggerFactory.getLogger(SchedulerAction.class);

    public void execute() {
        log.info("######## SchedulerAction.executed ########");
    }
}

