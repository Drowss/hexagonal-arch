package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.dto.PinUserResponseDto;
import com.drow.plazoleta.domain.dto.TraceabilityResponseDto;
import com.drow.plazoleta.domain.exception.NotYourOrder;
import com.drow.plazoleta.domain.exception.NotYourRestaurant;
import com.drow.plazoleta.domain.exception.PendingOrderException;
import com.drow.plazoleta.domain.model.OrderItemModel;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Mock
    private TraceabilityFeignPort traceabilityFeignPort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private OrderModel orderModel;
    private RestaurantEmployeeModel restaurantEmployeeModel;
    private PinUserResponseDto pinUserResponseDto;
    private List<OrderModel> orderModelList;

    @BeforeEach
    public void setUp() {
        orderModelList = new ArrayList<>();
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
        OrderModel newOrder = new OrderModel();
        newOrder.setUserId(cedula);
        newOrder.setRestaurant(restaurantModel);
        newOrder.setStatus(OrderStatus.PENDIENTE);

        when(jwtHandler.getCedulaFromToken(token)).thenReturn(cedula);
        when(restaurantPersistencePort.getRestaurantByNit(restaurantNit)).thenReturn(restaurantModel);
        when(orderPersistencePort.findOrderEntityByUserIdAndRestaurant(cedula, restaurantModel)).thenReturn(Collections.emptyList());
        when(orderPersistencePort.saveOrder(any(OrderModel.class))).thenReturn(newOrder);
        when(jwtHandler.getUsername(token)).thenReturn("sample@example.com");

        // Act
        orderUseCase.saveOrder(token, restaurantNit);

        // Assert
        verify(orderPersistencePort, times(1)).saveOrder(any(OrderModel.class));
        verify(traceabilityFeignPort, times(1)).saveTraceability(any(TraceabilityResponseDto.class));
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
    void testSaveOrder_PendingOrderException() {
        // Arrange
        String token = "sampleToken";
        String restaurantNit = "sampleNit";
        Integer cedula = 12345;

        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit(restaurantNit);

        OrderModel existingOrderModel = new OrderModel();
        existingOrderModel.setUserId(cedula);
        existingOrderModel.setRestaurant(restaurantModel);
        existingOrderModel.setStatus(OrderStatus.PENDIENTE);

        List<OrderModel> existingOrderList = Collections.singletonList(existingOrderModel);

        when(jwtHandler.getCedulaFromToken(token)).thenReturn(cedula);
        when(restaurantPersistencePort.getRestaurantByNit(restaurantNit)).thenReturn(restaurantModel);
        when(orderPersistencePort.findOrderEntityByUserIdAndRestaurant(cedula, restaurantModel)).thenReturn(existingOrderList);

        // Act & Assert
        assertThrows(PendingOrderException.class, () -> orderUseCase.saveOrder(token, restaurantNit));
    }

    @Test
    public void testFindAllByStatus_Success() {
        // Arrange
        String token = "sampleToken";
        int page = 0;
        int size = 10;
        String status = "PENDIENTE";
        Integer cedula = 12345;

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        List<OrderModel> orderModelList = new ArrayList<>();
        OrderModel order1 = new OrderModel();
        order1.setId(1);
        orderModelList.add(order1);

        List<OrderItemModel> orderItemList = new ArrayList<>();
        OrderItemModel orderItem1 = new OrderItemModel();
        orderItem1.setId(1);
        orderItemList.add(orderItem1);

        when(jwtHandler.getCedulaFromToken(token)).thenReturn(cedula);
        when(orderPersistencePort.findAllByUserIdAndStatus(OrderStatus.valueOf(status), cedula, pageable)).thenReturn(orderModelList);
        when(orderItemPersistencePort.findAllByOrderId(order1.getId())).thenReturn(orderItemList);

        // Act
        Page<OrderModel> result = orderUseCase.findAllByStatus(token, page, size, status);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(orderItemList, result.getContent().get(0).getItems());

        verify(jwtHandler, times(1)).getCedulaFromToken(token);
        verify(orderPersistencePort, times(1)).findAllByUserIdAndStatus(OrderStatus.valueOf(status), cedula, pageable);
        verify(orderItemPersistencePort, times(1)).findAllByOrderId(order1.getId());
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

    @Test
    void testReadyOrder_Success() {
        // Arrange
        String token = "sampleToken";
        Integer orderId = 1;
        Integer cedula = 12345;

        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setEmployee(cedula);
        orderModel.setStatus(OrderStatus.EN_PREPARACION);

        OrderItemModel orderItemModel = new OrderItemModel();
        orderItemModel.setId(1);
        List<OrderItemModel> orderItems = Collections.singletonList(orderItemModel);

        TraceabilityResponseDto traceabilityResponseDto = new TraceabilityResponseDto();
        traceabilityResponseDto.setId("id");

        when(jwtHandler.getCedulaFromToken(token)).thenReturn(cedula);
        when(orderPersistencePort.findByIdIgnoreCycle(orderId)).thenReturn(orderModel);
        when(orderItemPersistencePort.findAllByOrderId(orderId)).thenReturn(orderItems);
        when(traceabilityFeignPort.findTraceabilityByOrderId(orderId)).thenReturn(traceabilityResponseDto);

        // Act
        orderUseCase.readyOrder(token, orderId);

        // Assert
        assertEquals(OrderStatus.LISTO, orderModel.getStatus());
        verify(orderPersistencePort, times(1)).saveOrder(orderModel);
        verify(traceabilityFeignPort, times(1)).saveTraceability(traceabilityResponseDto);
        verify(pinUserFeignPort, times(1)).savePinUser(orderModel.getUserId(), orderId);
    }

    @Test
    void testReadyOrder_NotYourOrder() {
        // Arrange
        String token = "sampleToken";
        Integer orderId = 1;
        Integer cedula = 12345;
        Integer anotherCedula = 67890;

        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setEmployee(anotherCedula);

        when(jwtHandler.getCedulaFromToken(token)).thenReturn(cedula);
        when(orderPersistencePort.findByIdIgnoreCycle(orderId)).thenReturn(orderModel);

        // Act & Assert
        assertThrows(NotYourOrder.class, () -> orderUseCase.readyOrder(token, orderId));
    }

    @Test
    void testReadyOrder_PendingOrderException() {
        // Arrange
        String token = "sampleToken";
        Integer orderId = 1;
        Integer cedula = 12345;

        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setEmployee(cedula);
        orderModel.setStatus(OrderStatus.ENTREGADO);

        when(jwtHandler.getCedulaFromToken(token)).thenReturn(cedula);
        when(orderPersistencePort.findByIdIgnoreCycle(orderId)).thenReturn(orderModel);

        // Act & Assert
        assertThrows(PendingOrderException.class, () -> orderUseCase.readyOrder(token, orderId));
    }

    @Test
    void testDeleteOrder_Success() {
        // Arrange
        String token = "sampleToken";
        Integer orderId = 1;
        Integer cedula = 12345;

        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setUserId(cedula);
        orderModel.setStatus(OrderStatus.PENDIENTE);

        when(jwtHandler.getCedulaFromToken(token)).thenReturn(cedula);
        when(orderPersistencePort.findByIdIgnoreCycle(orderId)).thenReturn(orderModel);

        // Act
        orderUseCase.deleteOrder(token, orderId);

        // Assert
        verify(orderPersistencePort, times(1)).deleteOrder(orderModel);
    }

    @Test
    void testDeleteOrder_NotYourOrder() {
        // Arrange
        String token = "sampleToken";
        Integer orderId = 1;
        Integer cedula = 12345;
        Integer anotherUserId = 67890;

        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setUserId(anotherUserId);

        when(jwtHandler.getCedulaFromToken(token)).thenReturn(cedula);
        when(orderPersistencePort.findByIdIgnoreCycle(orderId)).thenReturn(orderModel);

        // Act & Assert
        assertThrows(NotYourOrder.class, () -> orderUseCase.deleteOrder(token, orderId));
    }

    @Test
    void testDeleteOrder_PendingOrderException() {
        // Arrange
        String token = "sampleToken";
        Integer orderId = 1;
        Integer cedula = 12345;

        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setUserId(cedula);
        orderModel.setStatus(OrderStatus.EN_PREPARACION);

        when(jwtHandler.getCedulaFromToken(token)).thenReturn(cedula);
        when(orderPersistencePort.findByIdIgnoreCycle(orderId)).thenReturn(orderModel);

        // Act & Assert
        assertThrows(PendingOrderException.class, () -> orderUseCase.deleteOrder(token, orderId));
    }
}