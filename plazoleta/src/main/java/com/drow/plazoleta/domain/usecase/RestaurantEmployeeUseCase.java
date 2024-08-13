package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.api.IRestaurantEmployeeServicePort;
import com.drow.plazoleta.domain.model.RestaurantEmployeeModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.spi.IJwtHandler;
import com.drow.plazoleta.domain.spi.IRestaurantEmployeePersistencePort;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantEmployeeUseCase implements IRestaurantEmployeeServicePort {

    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IJwtHandler jwtHandler;

    @Override
    public void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel, String token) {
        Integer ownerDni = jwtHandler.getCedulaFromToken(token);
        RestaurantModel restaurantModel = restaurantPersistencePort.findByCedula(ownerDni.toString());
        restaurantEmployeeModel.setRestaurantNit(restaurantModel.getNit());
        System.out.println("ESTOY EN RESTAURANTEMPLOYEE " + restaurantEmployeeModel.getEmployeeId());
        restaurantEmployeePersistencePort.saveRestaurantEmployee(restaurantEmployeeModel);
    }
}
