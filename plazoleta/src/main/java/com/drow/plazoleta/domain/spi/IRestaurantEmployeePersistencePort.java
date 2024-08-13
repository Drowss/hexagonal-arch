package com.drow.plazoleta.domain.spi;

import com.drow.plazoleta.domain.model.RestaurantEmployeeModel;

public interface IRestaurantEmployeePersistencePort {
    void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel);
}
