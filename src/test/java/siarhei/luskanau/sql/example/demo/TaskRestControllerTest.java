package siarhei.luskanau.sql.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TaskRestControllerTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskRestController taskRestController;

    @Test
    void testTask1a() {
        // Given

        // When
        taskRestController.task1a();

        // Then
        verify(taskRepository).task1a(anyString(), anyString(), anyInt());
    }

    @Test
    void testTask1b() {
        // Given

        // When
        taskRestController.task1b();

        // Then
        verify(taskRepository).task1b(anyString(), anyString());
    }

    @Test
    void testTask2() {
        // Given

        // When
        taskRestController.task2();

        // Then
        verify(taskRepository).task2();
    }

    @Test
    void testTask3() {
        // Given

        // When
        taskRestController.task3();

        // Then
        verify(taskRepository).task3();
    }
}
