package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.exception.PendingOrderException;
import com.drow.plazoleta.domain.model.OrderItemModel;
import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.model.enums.OrderStatus;
import com.drow.plazoleta.domain.spi.IJwtHandler;
import com.drow.plazoleta.domain.spi.IOrderItemPersistencePort;
import com.drow.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.drow.plazoleta.domain.api.IOrderServicePort;
import com.drow.plazoleta.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;


@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IJwtHandler jwtHandler;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderItemPersistencePort orderItemPersistencePort;

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
}
