package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.exception.PendingOrderException;
import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.model.enums.OrderStatus;
import com.drow.plazoleta.domain.spi.IJwtHandler;
import com.drow.plazoleta.domain.spi.IOrderItemPersistencePort;
import com.drow.plazoleta.domain.spi.IOrderPersistencePort;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IJwtHandler jwtHandler;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IOrderItemPersistencePort orderItemPersistencePort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    @Test
    public void testSaveOrder() {
        // Arrange
        String token = "valid-token";
        String restaurantNit = "123456789";
        Integer cedula = 123456;
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit(restaurantNit);

        when(jwtHandler.getCedulaFromToken(token)).thenReturn(cedula);
        when(restaurantPersistencePort.getRestaurantByNit(restaurantNit)).thenReturn(restaurantModel);
        when(orderPersistencePort.findOrderEntityByUserIdAndRestaurant(cedula, restaurantModel)).thenReturn(Collections.emptyList());

        // Act
        orderUseCase.saveOrder(token, restaurantNit);

        // Assert
        verify(orderPersistencePort, times(1)).saveOrder(any(OrderModel.class));
    }

    @Test
    public void testSaveOrderWithPendingOrder() {
        // Arrange
        String token = "valid-token";
        String restaurantNit = "123456789";
        Integer cedula = 123456;
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit(restaurantNit);
        OrderModel pendingOrder = new OrderModel();
        pendingOrder.setStatus(OrderStatus.PENDIENTE);

        when(jwtHandler.getCedulaFromToken(token)).thenReturn(cedula);
        when(restaurantPersistencePort.getRestaurantByNit(restaurantNit)).thenReturn(restaurantModel);
        when(orderPersistencePort.findOrderEntityByUserIdAndRestaurant(cedula, restaurantModel)).thenReturn(List.of(pendingOrder));

        // Act & Assert
        assertThrows(PendingOrderException.class, () -> {
            orderUseCase.saveOrder(token, restaurantNit);
        });

        verify(orderPersistencePort, never()).saveOrder(any(OrderModel.class));
    }
}