package siarhei.luskanau.sql.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import siarhei.luskanau.sql.example.demo.model.TestModel1;
import siarhei.luskanau.sql.example.demo.model.TestModel2;
import siarhei.luskanau.sql.example.demo.model.TestModel3;

import java.util.List;

@RestController
public class TaskRestController {

    private final TaskRepository repository;

    public TaskRestController(@Autowired TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/task1a")  // GET http://localhost:8080/task1a
    public List<TestModel1> task1a() {
        return repository.task1a(
                "2019-01-01",
                "2023-01-01",
                10
        );
    }

    @GetMapping(path = "/task1b")  // GET http://localhost:8080/task1b
    public List<TestModel1> task1b() {
        return repository.task1b(
                "2019-01-01",
                "2023-01-01"
        );
    }

    @GetMapping(path = "/task2")  // GET http://localhost:8080/task2
    public List<TestModel2> task2() {
        return repository.task2();
    }

    @GetMapping(path = "/task3")  // GET http://localhost:8080/task3
    public List<TestModel3> task3() {
        return repository.task3();
    }
}
