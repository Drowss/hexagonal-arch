package com.drow.user.infrastructure.configuration;

import com.drow.user.infrastructure.configuration.filter.CookieAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CookieAuthenticationFilter cookieAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/v1/user/login").permitAll()
                    .requestMatchers("/api/v1/user/save/owner").hasRole("PROPIETARIO")
                    .requestMatchers("/api/v1/user/save/employee").hasRole("EMPLEADO")
                    .requestMatchers("/api/v1/user/save/client").permitAll()
                    .anyRequest()
                    .permitAll()
            )
            .addFilterBefore(cookieAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
