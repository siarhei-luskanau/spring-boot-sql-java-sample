package siarhei.luskanau.sql.example.demo.scheduler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ScheduledComponentTest {

    @Mock
    private CsvChecker csvChecker;

    @InjectMocks
    private ScheduledComponent scheduledComponent;

    @Test
    void testExecute() {
        // Given

        // When
        scheduledComponent.execute();

        // Then
        verify(csvChecker).check();
    }
}
