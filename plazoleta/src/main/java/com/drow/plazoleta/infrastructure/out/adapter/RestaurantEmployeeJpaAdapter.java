package com.drow.plazoleta.infrastructure.out.adapter;

import com.drow.plazoleta.domain.model.RestaurantEmployeeModel;
import com.drow.plazoleta.domain.spi.IRestaurantEmployeePersistencePort;
import com.drow.plazoleta.infrastructure.out.mapper.IRestaurantEmployeeEntityMapper;
import com.drow.plazoleta.infrastructure.out.repository.IRestaurantEmployeeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantEmployeeJpaAdapter implements IRestaurantEmployeePersistencePort {

    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;

    @Override
    public void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel) {
        restaurantEmployeeRepository.save(restaurantEmployeeEntityMapper.toEntity(restaurantEmployeeModel));
    }

    @Override
    public RestaurantEmployeeModel findEmployeeByCedula(Integer cedula) {
        return restaurantEmployeeEntityMapper.toModel(restaurantEmployeeRepository.findByEmployeeId(cedula));
    }
}
