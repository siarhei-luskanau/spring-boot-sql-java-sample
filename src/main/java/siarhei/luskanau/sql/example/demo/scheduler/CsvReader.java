package siarhei.luskanau.sql.example.demo.scheduler;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvReader {

    private static final Logger log = LoggerFactory.getLogger(CsvReader.class);
    private final CsvParser csvParser;

    public CsvReader(@Autowired CsvParser csvParser) {
        this.csvParser = csvParser;
    }

    public List<CsvModel> read(String path) {
        List<CsvModel> csvModels = new ArrayList<>();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

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
                    CsvModel csvModel = csvParser.parse(time, bitmask);
                    csvModels.add(csvModel);
                }
            } catch (IOException e) {
                log.error("CSV parsing error", e);
            }
        } else {
            throw new CsvFileException(path);
        }

        return csvModels;
    }
}
