package ru.rest.demo.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @noinspection unused
 */
public class FlywayConfig {

    private final ApplicationContext context;
    private final DataSource dataSource;

    public FlywayConfig(ApplicationContext context, DataSource dataSource) {
        this.context = context;
        this.dataSource = dataSource;
        config();
    }

    public void config() {
        PasswordEncoder pwdEncoder = (PasswordEncoder) this.context.getBean("pwdEncoder");
        Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(this.dataSource)
                .locations("classpath:/db/migration", "classpath:/db/testdata")
                .placeholders(Map.of("adminpwd", pwdEncoder.encode("admin")))
                .load().migrate();
    }
}