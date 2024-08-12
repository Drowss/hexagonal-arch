package com.drow.plazoleta.infrastructure.configuration.filter;

import com.drow.plazoleta.application.dto.response.UserResponseDto;
import com.drow.plazoleta.domain.spi.IJwtHandler;
import com.drow.plazoleta.infrastructure.out.feign.UserFeignClientAdapter;
import feign.RequestTemplate;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CookieAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtHandler jwtHandler;
    private final UserFeignClientAdapter userFeignClientAdapter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        List<Cookie> cookieList = Arrays.asList(cookies);

        String token = cookieList.stream()
                .filter(cookie -> "token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (token != null && jwtHandler.isTokenValid(token)) {
            String correo = jwtHandler.getUsername(token);
            ResponseEntity<UserResponseDto> userResponseDto = userFeignClientAdapter.getUserByCorreo(correo);
            PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
                    userResponseDto, null, List.of(() -> "ROLE_" + Objects.requireNonNull(userResponseDto.getBody()).getRol().name()));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
