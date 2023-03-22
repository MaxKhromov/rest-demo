package ru.rest.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {


    private BasicAuthenticationPoint basicAuthenticationPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/users/hello").permitAll()
                        .anyRequest().authenticated()
                );
        http.httpBasic().authenticationEntryPoint(basicAuthenticationPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }


    @Component
    public static class BasicAuthenticationPoint extends BasicAuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
                throws IOException {
            // ↓ ↓ Добавляет возможность авторизации через диалоговое окно браузера
            response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            writer.println("HTTP Status 401 - Unauthorized");
        }

        @Override
        public void afterPropertiesSet() {
            setRealmName("DefaultRealmName");
            super.afterPropertiesSet();
        }


    }
}
