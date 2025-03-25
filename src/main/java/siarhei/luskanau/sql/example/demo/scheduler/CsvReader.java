package siarhei.luskanau.sql.example.demo.scheduler;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReader {

    private static final Logger log = LoggerFactory.getLogger(CsvReader.class);

    public List<ScvModel> read() {
        List<ScvModel> scvModels = new ArrayList<>();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler.csv");

        if (inputStream != null) {
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                Iterable<CSVRecord> records = CSVFormat.RFC4180.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .get()
                        .parse(reader);

                for (CSVRecord record : records) {
                    String time = record.get("time").trim();
                    String bitmask = record.get("bitmask").trim();
                    scvModels.add(new ScvModel(time, bitmask));
                }
            } catch (IOException e) {
                log.error("CSV parsing error", e);
            }
        }

        return scvModels;
    }
}
