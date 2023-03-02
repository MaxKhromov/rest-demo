package ru.rest.demo.config;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;


public class FlywayConfig {

    private ApplicationContext context;
    private DataSource dataSource;

    public FlywayConfig(ApplicationContext context, DataSource dataSource) {
        this.context = context;
        this.dataSource = dataSource;
        config();
    }

    public void config(){
        PasswordEncoder pwdEncoder = (PasswordEncoder) this.context.getBean("pwdEncoder");
        Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(this.dataSource)
                .locations("classpath:/db/migration","classpath:/db/testdata")
                .placeholders(Map.of("adminpwd", pwdEncoder.encode("admin")))
                .load().migrate();
    }
}