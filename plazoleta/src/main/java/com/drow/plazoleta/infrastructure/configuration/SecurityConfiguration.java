package com.drow.plazoleta.infrastructure.configuration;

import com.drow.plazoleta.infrastructure.configuration.filter.CookieAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Component
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CookieAuthenticationFilter cookieAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/category/save").hasRole("PROPIETARIO")
                        .requestMatchers("/api/v1/dish/save").hasRole("PROPIETARIO")
                        .requestMatchers("/api/v1/dish/modify").hasRole("PROPIETARIO")
                        .requestMatchers("/api/v1/dish/toggle").hasRole("PROPIETARIO")
                        .requestMatchers("/api/v1/dish/dishes").permitAll()
                        .requestMatchers("/api/v1/order/save").hasRole("CLIENTE")
                        .requestMatchers("/api/v1/order/detail").hasRole("CLIENTE")
                        .requestMatchers("/api/v1/order/all").hasRole("EMPLEADO")
                        .requestMatchers("/api/v1/order/assign").hasRole("EMPLEADO")
                        .requestMatchers("/api/v1/order/ready").hasRole("EMPLEADO")
                        .requestMatchers("/api/v1/order/deliver").hasRole("EMPLEADO")
                        .requestMatchers("/api/v1/order/delete").hasRole("CLIENTE")
                        .requestMatchers("/api/v1/order/efficiency").hasRole("PROPIETARIO")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(cookieAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
