package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.exception.PendingOrderException;
import com.drow.plazoleta.domain.model.OrderItemModel;
import com.drow.plazoleta.domain.model.OrderModel;
import com.drow.plazoleta.domain.model.PinUserModel;
import com.drow.plazoleta.domain.model.RestaurantModel;
import com.drow.plazoleta.domain.model.enums.OrderStatus;
import com.drow.plazoleta.domain.spi.*;
import com.drow.plazoleta.domain.api.IOrderServicePort;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;


@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IJwtHandler jwtHandler;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderItemPersistencePort orderItemPersistencePort;
    private final IUserPinPersistencePort userPinPersistencePort;
    @Value("${twilioAccountSid}")
    private String twilioAccountSid;
    @Value("${twilioPassword}")
    private String password;
    @Value("${twilioPhoneNumber}")
    private String twilioPhoneNumber;
    @Value("${twilioMyPhoneNumber}")
    private String twilioMyPhoneNumber;

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
            throw new PendingOrderException("No puedes marcar una orden como lista si no eres el empleado asignado");
        }
        if (userPinPersistencePort.findByUserIdAndOrderId(orderModel.getUserId(), orderModel.getId()) != null) {
            Integer pin = userPinPersistencePort.findByUserIdAndOrderId(orderModel.getUserId(), orderModel.getId()).getPin();
            throw new PendingOrderException("Ya se ha generado un pin para esta orden " + pin);
        }
        orderModel.setItems(orderItemPersistencePort.findAllByOrderId(orderModel.getId()));
        for (OrderItemModel orderItemModel : orderModel.getItems()) {
            orderItemModel.setOrder(orderPersistencePort.findByIdIgnoreCycle(orderModel.getId()));
        }
        Random random = new Random();
        int randomNumber = random.nextInt((99999 - 10000) + 1) + 10000;
        PinUserModel pinUserModel = new PinUserModel();
        pinUserModel.setPin(randomNumber);
        pinUserModel.setUserId(orderModel.getUserId());
        pinUserModel.setOrderId(orderModel.getId());
        Twilio.init(twilioAccountSid, password);
        Message.creator(
            new com.twilio.type.PhoneNumber(twilioMyPhoneNumber),
            new com.twilio.type.PhoneNumber(twilioPhoneNumber),
        "Su pin de seguridad es " + randomNumber).create();
        orderModel.setStatus(OrderStatus.LISTO);
        userPinPersistencePort.savePin(pinUserModel);
        orderPersistencePort.saveOrder(orderModel);
    }
}
