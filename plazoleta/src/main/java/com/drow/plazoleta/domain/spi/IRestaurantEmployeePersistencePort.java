package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.RestaurantEmployeeModel;
import com.drow.plazoleta.infrastructure.out.entity.RestaurantEntity;

public interface IRestaurantEmployeePersistencePort {
    void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel);

    RestaurantEmployeeModel findEmployeeByCedula(Integer cedula);
}
