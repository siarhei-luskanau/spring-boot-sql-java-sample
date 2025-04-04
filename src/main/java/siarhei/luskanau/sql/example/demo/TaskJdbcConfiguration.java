package siarhei.luskanau.sql.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories
public class TaskJdbcConfiguration extends AbstractJdbcConfiguration {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcOperations operations) {
        return new NamedParameterJdbcTemplate(operations);
    }

    @Bean
    public DataSourceInitializer initializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);

        ClassPathResource script = new ClassPathResource("db_schema.sql");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(script);
        initializer.setDatabasePopulator(populator);

        return initializer;
    }
}
