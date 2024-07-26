package com.drow.plazoleta.infrastructure.configuration;

import com.drow.plazoleta.domain.api.IRestaurantServicePort;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.drow.plazoleta.domain.usecase.RestaurantUseCase;
import com.drow.plazoleta.infrastructure.out.adapter.RestaurantJpaAdapter;
import com.drow.plazoleta.infrastructure.out.mapper.IRestaurantEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort());
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

}
