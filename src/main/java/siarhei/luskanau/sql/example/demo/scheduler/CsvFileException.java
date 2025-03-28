package siarhei.luskanau.sql.example.demo.scheduler;

public class CsvFileException extends RuntimeException {

    public CsvFileException(String message) {
        super(message);
    }

    CsvFileException(String message, Throwable cause) {
        super(message, cause);
    }
}