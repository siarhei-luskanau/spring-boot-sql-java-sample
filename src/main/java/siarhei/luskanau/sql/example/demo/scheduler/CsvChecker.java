package siarhei.luskanau.sql.example.demo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvChecker {

    private final CsvReader csvReader;
    private final SchedulerAction schedulerAction;
    private final String timeZoneValue;

    private static final Logger log = LoggerFactory.getLogger(CsvChecker.class);

    public CsvChecker(
            @Autowired CsvReader csvReader,
            @Autowired SchedulerAction schedulerAction,
            @Value("${scheduler.timezone}") String timeZoneValue
    ) {
        this.csvReader = csvReader;
        this.schedulerAction = schedulerAction;
        this.timeZoneValue = timeZoneValue;
    }

    public void check() {
        List<ScvModel> csvModelList = csvReader.read();
        log.info("List<ScvModel>: {}", csvModelList);
        List<ScvModel> triggered = getTriggeredScvModel(csvModelList);
        triggered.forEach(scvModel -> log.info("Action is triggered: {}", scvModel));
        if (!triggered.isEmpty()) {
            schedulerAction.execute();
        }
    }

    private List<ScvModel> getTriggeredScvModel(List<ScvModel> csvModelList) {
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of(timeZoneValue));
        log.info("CurrentTime: {}", currentTime);
        return csvModelList.stream()
                .filter(scvModel -> {
                    String[] timeString = scvModel.time().split(":");
                    return (Integer.parseInt(scvModel.bitmask()) >> currentTime.getDayOfWeek().ordinal()) % 2 == 1
                            && Integer.parseInt(timeString[0]) == currentTime.getHour()
                            && Integer.parseInt(timeString[1]) == currentTime.getMinute();
                })
                .collect(Collectors.toList());
    }
}
