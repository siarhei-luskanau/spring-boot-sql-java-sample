package siarhei.luskanau.sql.example.demo.scheduler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ScheduledComponentTest {

    @Mock
    private CsvChecker csvChecker;

    private ScheduledComponent scheduledComponent;

    @BeforeEach
    void setUp() {
        scheduledComponent = new ScheduledComponent(csvChecker, "UTC");
    }

    @Test
    void testExecute() {
        // Given

        // When
        scheduledComponent.execute();

        // Then
        verify(csvChecker).check(any());
    }
}
