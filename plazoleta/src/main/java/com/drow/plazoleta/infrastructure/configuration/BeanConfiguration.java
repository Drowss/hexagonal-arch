package com.drow.plazoleta.infrastructure.configuration;

import com.drow.plazoleta.domain.api.*;
import com.drow.plazoleta.domain.spi.*;
import com.drow.plazoleta.domain.usecase.*;
import com.drow.plazoleta.infrastructure.out.adapter.*;
import com.drow.plazoleta.infrastructure.out.feign.FeignClientInterceptor;
import com.drow.plazoleta.infrastructure.out.feign.PinUserFeignClientAdapter;
import com.drow.plazoleta.infrastructure.out.feign.UserFeignClientAdapter;
import com.drow.plazoleta.infrastructure.out.mapper.*;
import com.drow.plazoleta.infrastructure.out.repository.*;
import jakarta.servlet.http.HttpServletRequest;
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
    private final IJwtHandler jwtHandler;
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderItemRepository orderItemRepository;
    private final IOrderItemEntityMapper orderItemMapper;
    private final PinUserFeignClientAdapter pinUserClientAdapter;
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort());
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IOrderItemServicePort orderItemServicePort() {
        return new OrderItemUseCase(orderItemPersistencePort(), dishPersistencePort(), orderPersistencePort());
    }

    @Bean
    public IOrderItemPersistencePort orderItemPersistencePort() {
        return new OrderItemJpaAdapter(orderItemRepository, orderItemMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(),
                categoryPersistencePort(),
                restaurantPersistencePort(),
                categoryEntityMapper,
                restaurantEntityMapper,
                dishEntityMapper,
                jwtHandler);
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

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort(), jwtHandler, restaurantPersistencePort(), orderItemPersistencePort(), pinUserFeignAdapter());
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(orderRepository, orderEntityMapper, restaurantEntityMapper);
    }

    @Bean
    public PinUserFeignAdapter pinUserFeignAdapter() {
        return new PinUserFeignAdapter(pinUserClientAdapter);
    }

    @Bean
    public IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort() {
        return new RestaurantEmployeeJpaAdapter(restaurantEmployeeRepository, restaurantEmployeeEntityMapper);
    }

    @Bean
    public IRestaurantEmployeeServicePort restaurantEmployeeServicePort() {
        return new RestaurantEmployeeUseCase(restaurantEmployeePersistencePort(), restaurantPersistencePort(), jwtHandler);
    }
}
