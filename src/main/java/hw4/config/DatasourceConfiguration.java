package hw4.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5433/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setMaximumPoolSize(10);
        return new HikariDataSource(config);
    }
}
