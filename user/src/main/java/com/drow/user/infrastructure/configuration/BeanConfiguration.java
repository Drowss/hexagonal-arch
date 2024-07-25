package com.drow.user.infrastructure.configuration;

import com.drow.user.domain.api.IUserServicePort;
import com.drow.user.domain.spi.IUserPersistencePort;
import com.drow.user.domain.usecase.UserUseCase;
import com.drow.user.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.drow.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.drow.user.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
