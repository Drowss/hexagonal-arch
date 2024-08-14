package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.dto.PinUserResponseDto;
import com.drow.plazoleta.domain.exception.NotYourOrder;
import com.drow.plazoleta.domain.exception.NotYourRestaurant;
import com.drow.plazoleta.domain.exception.PendingOrderException;
import com.drow.plazoleta.domain.model.OrderItemModel;
import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.model.RestaurantEmployeeModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.model.enums.OrderStatus;
import com.drow.plazoleta.domain.spi.*;
import com.drow.plazoleta.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IJwtHandler jwtHandler;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderItemPersistencePort orderItemPersistencePort;
    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final PinUserFeignPort pinUserFeignPort;

    @Override
    public void saveOrder(String token, String restaurantNit) {
        Integer cedula = jwtHandler.getCedulaFromToken(token);
        RestaurantModel restaurantModel = restaurantPersistencePort.getRestaurantByNit(restaurantNit);
        List<OrderModel> orderModelList = orderPersistencePort.findOrderEntityByUserIdAndRestaurant(cedula, restaurantModel);
        if (!orderModelList.isEmpty()) {
            for (OrderModel orderModel : orderModelList) {
                if (orderModel.getStatus() == OrderStatus.PENDIENTE) {
                    throw new PendingOrderException("Ya tienes una orden pendiente con este restaurante");
                }
            }
        }

        RestaurantModel restaurant = restaurantPersistencePort.getRestaurantByNit(restaurantNit);
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(cedula);
        orderModel.setRestaurant(restaurant);
        orderModel.setStatus(OrderStatus.PENDIENTE);
        orderPersistencePort.saveOrder(orderModel);
    }

    @Override
    public Page<OrderModel> findAllByStatus(String token, int page, int size, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Integer cedula = jwtHandler.getCedulaFromToken(token);
        List<OrderModel> orderModelList = orderPersistencePort.findAllByUserIdAndStatus(OrderStatus.valueOf(status), cedula, pageable);
        for (OrderModel orderModel : orderModelList) {
            orderModel.setItems(orderItemPersistencePort.findAllByOrderId(orderModel.getId()));
        }
        return new PageImpl<>(orderModelList, pageable, orderModelList.size());
    }

    @Override
    public Page<OrderModel> assignEmployeeToOrder(String token, Integer orderId, int page, int size, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Integer cedula = jwtHandler.getCedulaFromToken(token);
        OrderModel orderModelEmployee = orderPersistencePort.findByIdIgnoreCycle(orderId);
        orderModelEmployee.setEmployee(cedula);
        orderModelEmployee.setItems(orderItemPersistencePort.findAllByOrderId(orderModelEmployee.getId()));
        orderModelEmployee.setStatus(OrderStatus.valueOf(status));
        for (OrderItemModel orderItemModel : orderModelEmployee.getItems()) {
            orderItemModel.setOrder(orderPersistencePort.findByIdIgnoreCycle(orderModelEmployee.getId()));
        }
        orderPersistencePort.saveOrder(orderModelEmployee);
        List<OrderModel> orderModelList = orderPersistencePort.findAllByUserIdAndStatus(OrderStatus.valueOf(status), cedula, pageable);
        for (OrderModel orderModel : orderModelList) {
            orderModel.setItems(orderItemPersistencePort.findAllByOrderId(orderModel.getId()));
        }
        return new PageImpl<>(orderModelList, pageable, orderModelList.size());
    }

    @Override
    public void readyOrder(String token, Integer orderId) {
        Integer cedula = jwtHandler.getCedulaFromToken(token);
        OrderModel orderModel = orderPersistencePort.findByIdIgnoreCycle(orderId);
        System.out.println(orderModel.getEmployee() + " " + cedula);
        if (!Objects.equals(orderModel.getEmployee(), cedula)) {
            throw new NotYourOrder("No puedes marcar una orden como lista si no eres el empleado asignado");
        }
        orderModel.setItems(orderItemPersistencePort.findAllByOrderId(orderModel.getId()));
        for (OrderItemModel orderItemModel : orderModel.getItems()) {
            orderItemModel.setOrder(orderPersistencePort.findByIdIgnoreCycle(orderModel.getId()));
        }
        orderModel.setStatus(OrderStatus.LISTO);
        pinUserFeignPort.savePinUser(orderModel.getUserId(), orderId);
        orderPersistencePort.saveOrder(orderModel);
    }

    @Override
    public void deliverOrder(String token, Integer orderId, Integer pin) {
        System.out.println("entra a deliver");
        Integer cedula = jwtHandler.getCedulaFromToken(token);
        OrderModel orderModel = orderPersistencePort.findByIdIgnoreCycle(orderId);
        if (!Objects.equals(orderModel.getEmployee(), cedula)) {
            throw new NotYourOrder("No puedes marcar una orden como entregada si no eres el empleado asignado");
        }
        System.out.println("pasa primer if");
        orderModel.setItems(orderItemPersistencePort.findAllByOrderId(orderModel.getId()));
        for (OrderItemModel orderItemModel : orderModel.getItems()) {
            orderItemModel.setOrder(orderPersistencePort.findByIdIgnoreCycle(orderModel.getId()));
        }
        System.out.println("instancio restaurantEmployeeModel");
        RestaurantEmployeeModel restaurantEmployeeModel = restaurantEmployeePersistencePort.findEmployeeByCedula(cedula);
        System.out.println(restaurantEmployeeModel.getRestaurantNit() + " aaaaaaa " + orderModel.getRestaurant().getNit());
        if (!restaurantEmployeeModel.getRestaurantNit().equals(orderModel.getRestaurant().getNit())) {
            throw new NotYourRestaurant("No puedes marcar una orden como entregada si no pertenece a tu restaurante");
        }
        PinUserResponseDto pinUserResponseDto = pinUserFeignPort.findPinUser(pin);
        if (!Objects.equals(pinUserResponseDto.getOrderId(), orderId)) {
            throw new NotYourOrder("El pin ingresado no corresponde a la orden");
        }
        orderModel.setStatus(OrderStatus.ENTREGADO);
        orderPersistencePort.saveOrder(orderModel);
    }
}
