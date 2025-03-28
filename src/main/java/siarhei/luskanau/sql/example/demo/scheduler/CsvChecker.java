package siarhei.luskanau.sql.example.demo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CsvChecker {

    private static final Logger log = LoggerFactory.getLogger(CsvChecker.class);
    private final CsvReader csvReader;
    private final SchedulerAction schedulerAction;

    public CsvChecker(
            @Autowired CsvReader csvReader,
            @Autowired SchedulerAction schedulerAction
    ) {
        this.csvReader = csvReader;
        this.schedulerAction = schedulerAction;
    }

    public void check(ZonedDateTime currentTime) {
        List<CsvModel> csvModelList = csvReader.read("scheduler.csv");
        log.info("List<ScvModel>: {}", csvModelList);
        boolean isTriggeredEmpty = csvModelList.stream()
                .filter(csvModel -> csvModel.dayOfWeekList().contains(currentTime.getDayOfWeek())
                        && csvModel.localTime().getHour() == currentTime.getHour()
                        && csvModel.localTime().getMinute() == currentTime.getMinute())
                .toList().isEmpty();
        if (!isTriggeredEmpty) {
            schedulerAction.execute();
        }
    }
}
