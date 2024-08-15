package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.dto.PinUserResponseDto;
import com.drow.plazoleta.domain.exception.NotYourOrder;
import com.drow.plazoleta.domain.exception.NotYourRestaurant;
import com.drow.plazoleta.domain.exception.PendingOrderException;
import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.model.RestaurantEmployeeModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.model.enums.OrderStatus;
import com.drow.plazoleta.domain.spi.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Mock
    private IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;

    @Mock
    private PinUserFeignPort pinUserFeignPort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private OrderModel orderModel;
    private RestaurantEmployeeModel restaurantEmployeeModel;
    private PinUserResponseDto pinUserResponseDto;

    @BeforeEach
    public void setUp() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit("123456789");
        orderModel = new OrderModel();
        orderModel.setId(1);
        orderModel.setEmployee(12345);
        orderModel.setStatus(OrderStatus.PENDIENTE);
        orderModel.setRestaurant(restaurantModel);
        orderModel.setItems(new ArrayList<>());

        restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setRestaurantNit("123456789");

        pinUserResponseDto = new PinUserResponseDto();
        pinUserResponseDto.setOrderId(1);
    }

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

    @Test
    public void testDeliverOrder_NotYourOrder() {
        when(jwtHandler.getCedulaFromToken(anyString())).thenReturn(54321);
        when(orderPersistencePort.findByIdIgnoreCycle(anyInt())).thenReturn(orderModel);

        Exception exception = assertThrows(NotYourOrder.class, () -> {
            orderUseCase.deliverOrder("token", 1, 1234);
        });

        assertEquals("No puedes marcar una orden como entregada si no eres el empleado asignado", exception.getMessage());
    }

    @Test
    public void testDeliverOrder_NotYourRestaurant() {
        when(jwtHandler.getCedulaFromToken(anyString())).thenReturn(12345);
        when(orderPersistencePort.findByIdIgnoreCycle(anyInt())).thenReturn(orderModel);
        restaurantEmployeeModel.setRestaurantNit("987654321");
        when(restaurantEmployeePersistencePort.findEmployeeByCedula(anyInt())).thenReturn(restaurantEmployeeModel);

        Exception exception = assertThrows(NotYourRestaurant.class, () -> {
            orderUseCase.deliverOrder("token", 1, 1234);
        });

        assertEquals("No puedes marcar una orden como entregada si no pertenece a tu restaurante", exception.getMessage());
    }

    @Test
    public void testDeliverOrder_InvalidPin() {
        when(jwtHandler.getCedulaFromToken(anyString())).thenReturn(12345);
        when(orderPersistencePort.findByIdIgnoreCycle(anyInt())).thenReturn(orderModel);
        when(restaurantEmployeePersistencePort.findEmployeeByCedula(anyInt())).thenReturn(restaurantEmployeeModel);
        pinUserResponseDto.setOrderId(2);
        when(pinUserFeignPort.findPinUser(anyInt())).thenReturn(pinUserResponseDto);

        Exception exception = assertThrows(NotYourOrder.class, () -> {
            orderUseCase.deliverOrder("token", 1, 1234);
        });

        assertEquals("El pin ingresado no corresponde a la orden", exception.getMessage());
    }
}