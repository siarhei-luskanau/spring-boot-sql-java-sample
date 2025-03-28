package siarhei.luskanau.sql.example.demo.scheduler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvReaderTest {

    @Mock
    private CsvParser csvParser;

    @InjectMocks
    private CsvReader csvReader;

    @Test
    void testRead() {
        // Given
        String filename = "scheduler_test.csv";
        CsvModel csvModel = new CsvModel(
                LocalTime.of(10, 8),
                List.of(DayOfWeek.FRIDAY)
        );
        when(csvParser.parse("10:08", "10")).thenReturn(csvModel);

        // When
        List<CsvModel> actual = csvReader.read(filename);

        // Then
        assertEquals(List.of(csvModel), actual);
        verify(csvParser).parse("10:08", "10");
    }

    @Test
    void testReadFailure() {
        // Given
        String filename = "missing.csv";

        // When
        CsvFileException result = assertThrows(CsvFileException.class, () -> csvReader.read(filename));

        // Then
        assertEquals(filename, result.getMessage());
    }
}
