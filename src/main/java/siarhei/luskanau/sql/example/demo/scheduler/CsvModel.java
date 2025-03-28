package siarhei.luskanau.sql.example.demo.scheduler;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public record CsvModel(
        LocalTime localTime,
        List<DayOfWeek> dayOfWeekList
) {
}
