package siarhei.luskanau.sql.example.demo.scheduler;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvParser {

    private static final DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public CsvModel parse(String time, String bitmask) {
        LocalTime localTime = LocalTime.parse(time, LOCAL_TIME_FORMATTER);

        byte scheduleBitmask = Byte.parseByte(bitmask, 16);
        List<DayOfWeek> dayOfWeekList = Arrays.stream(DayOfWeek.values())
                .filter(dayOfWeek -> (1 << dayOfWeek.ordinal() & scheduleBitmask) != 0)
                .toList();

        return new CsvModel(localTime, dayOfWeekList);
    }
}
