package siarhei.luskanau.sql.example.demo.scheduler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CsvCheckerTest {

    @Mock
    private CsvReader csvReader;

    @Mock
    private SchedulerAction schedulerAction;

    @InjectMocks
    private CsvChecker csvChecker;

    @Test
    void testCheck_TriggersSchedulerActionWhenMatchFound() {
        // Given
        ZonedDateTime currentTime = ZonedDateTime.of(2025, 3, 28, 15, 30, 0, 0, ZonedDateTime.now().getZone());

        CsvModel csvModel = new CsvModel(
                LocalTime.of(15, 30),
                List.of(DayOfWeek.FRIDAY)
        );

        List<CsvModel> csvModelList = List.of(csvModel);
        when(csvReader.read("scheduler.csv")).thenReturn(csvModelList);

        // When
        csvChecker.check(currentTime);

        // Then
        verify(schedulerAction, times(1)).execute();
    }

    @Test
    void testCheck_DoesNotTriggerSchedulerActionWhenNoMatch() {
        // Given
        ZonedDateTime currentTime = ZonedDateTime.of(2025, 3, 28, 10, 0, 0, 0, ZonedDateTime.now().getZone());

        CsvModel csvModel = new CsvModel(
                LocalTime.of(15, 30),
                List.of(DayOfWeek.MONDAY)
        );

        List<CsvModel> csvModelList = List.of(csvModel);
        when(csvReader.read("scheduler.csv")).thenReturn(csvModelList);

        // When
        csvChecker.check(currentTime);

        // Then
        verify(schedulerAction, times(0)).execute();
    }
}
