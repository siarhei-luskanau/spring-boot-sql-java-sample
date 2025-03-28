package siarhei.luskanau.sql.example.demo.scheduler;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvParserTest {

    private final CsvParser csvParser = new CsvParser();

    @Test
    void testParse_validInputAllDays() {
        // Given
        String time = "14:30";  // 2:30 PM
        String bitmask = "7F";   // All days

        // When
        CsvModel result = csvParser.parse(time, bitmask);

        // Then

        // Validate the parsed time
        assertEquals(LocalTime.of(14, 30), result.localTime());

        // Validate the parsed days of the week
        assertEquals(List.of(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY,
                DayOfWeek.SUNDAY
        ), result.dayOfWeekList());
    }


    @Test
    void testParse_validInputMondayWednesday() {
        // Given
        String time = "14:30";  // 2:30 PM
        String bitmask = "5";   // This corresponds to Monday and Wednesday (in binary)

        // When
        CsvModel result = csvParser.parse(time, bitmask);

        // Then

        // Validate the parsed time
        assertEquals(LocalTime.of(14, 30), result.localTime());

        // Validate the parsed days of the week
        assertEquals(List.of(
                DayOfWeek.MONDAY,
                DayOfWeek.WEDNESDAY
        ), result.dayOfWeekList());
    }

    @Test
    void testParse_emptyBitmask() {
        // Given
        String time = "08:00";  // 8:00 AM
        String bitmask = "0";   // This means no days are selected.

        // When
        CsvModel result = csvParser.parse(time, bitmask);

        // Then

        // Validate the parsed time
        assertEquals(LocalTime.of(8, 0), result.localTime());

        // Validate that no days are selected
        assertTrue(result.dayOfWeekList().isEmpty());
    }

    @Test
    void testParse_singleDayBitmask() {
        // Given
        String time = "10:00";  // 10:00 AM
        String bitmask = "10";   // This corresponds to only Friday being selected.

        // When
        CsvModel result = csvParser.parse(time, bitmask);

        // Then

        // Validate the parsed time
        assertEquals(LocalTime.of(10, 0), result.localTime());

        // Validate that only Monday is selected
        assertEquals(List.of(DayOfWeek.FRIDAY), result.dayOfWeekList());
    }

    @Test
    void testParse_invalidBitmask() {
        // Given
        String time = "12:00";  // 12:00 PM
        String invalidBitmask = "ZZ";  // Invalid bitmask (non-hexadecimal characters)

        // When
        assertThrows(NumberFormatException.class, () -> csvParser.parse(time, invalidBitmask));

        // Then
    }

    @Test
    void testParse_invalidTimeFormat() {
        // Given
        String invalidTime = "14:60";  // Invalid time format (60 minutes is not valid)

        // When
        assertThrows(java.time.format.DateTimeParseException.class, () -> csvParser.parse(invalidTime, "1F"));

        // Then
    }
}
