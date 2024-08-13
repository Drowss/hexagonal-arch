package com.drow.mensajeria.infrastructure.configuration;

import com.drow.mensajeria.domain.api.IPinUserServicePort;
import com.drow.mensajeria.domain.spi.IPinUserPersistencePort;
import com.drow.mensajeria.domain.usecase.PinUserUseCase;
import com.drow.mensajeria.infrastructure.out.adapter.PinUserJpaAdapter;
import com.drow.mensajeria.infrastructure.out.mapper.IPinUserEntityMapper;
import com.drow.mensajeria.infrastructure.out.repository.IPinUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IPinUserRepository pinUserRepository;
    private final IPinUserEntityMapper pinUserEntityMapper;

    @Bean
    public IPinUserServicePort pinUserServicePort() {
        return new PinUserUseCase(pinUserPersistencePort(), random());
    }

    @Bean
    public IPinUserPersistencePort pinUserPersistencePort() {
        return new PinUserJpaAdapter(pinUserRepository, pinUserEntityMapper);
    }

    @Bean
    public Random random() {
        return new Random();
    }

}
