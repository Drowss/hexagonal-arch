package com.drow.plazoleta.infrastructure.configuration;

import com.drow.plazoleta.domain.api.ICategoryServicePort;
import com.drow.plazoleta.domain.api.IDishServicePort;
import com.drow.plazoleta.domain.api.IRestaurantServicePort;
import com.drow.plazoleta.domain.spi.ICategoryPersistencePort;
import com.drow.plazoleta.domain.spi.IDishPersistencePort;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.drow.plazoleta.domain.usecase.CategoryUseCase;
import com.drow.plazoleta.domain.usecase.DishUseCase;
import com.drow.plazoleta.domain.usecase.RestaurantUseCase;
import com.drow.plazoleta.infrastructure.out.adapter.CategoryJpaAdapter;
import com.drow.plazoleta.infrastructure.out.adapter.DishJpaAdapter;
import com.drow.plazoleta.infrastructure.out.adapter.RestaurantJpaAdapter;
import com.drow.plazoleta.infrastructure.out.mapper.ICategoryEntityMapper;
import com.drow.plazoleta.infrastructure.out.mapper.IDishEntityMapper;
import com.drow.plazoleta.infrastructure.out.mapper.IRestaurantEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.ICategoryRepository;
import com.drow.plazoleta.infrastructure.out.repository.IDishRepository;
import com.drow.plazoleta.infrastructure.out.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort());
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), categoryPersistencePort(), restaurantPersistencePort(), categoryEntityMapper, restaurantEntityMapper);
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }
}
