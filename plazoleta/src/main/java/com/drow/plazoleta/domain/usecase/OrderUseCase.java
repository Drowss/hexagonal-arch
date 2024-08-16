package com.drow.plazoleta.domain.usecase;

import com.drow.plazoleta.domain.dto.PinUserResponseDto;
import com.drow.plazoleta.domain.dto.RestaurantEfficiencyDto;
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
import com.drow.plazoleta.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
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
    private final TraceabilityFeignPort traceabilityFeignPort;

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
        OrderModel newOrder = orderPersistencePort.saveOrder(orderModel);

        TraceabilityResponseDto traceabilityResponseDto = new TraceabilityResponseDto();
        traceabilityResponseDto.setCustomerEmail(jwtHandler.getUsername(token));
        traceabilityResponseDto.setOrderStartDate(LocalDateTime.now());
        traceabilityResponseDto.setCurrentStatus(OrderStatus.PENDIENTE.toString());
        traceabilityResponseDto.setCustomerId(cedula);
        traceabilityResponseDto.setOrderId(newOrder.getId());
        traceabilityFeignPort.saveTraceability(traceabilityResponseDto);
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

        TraceabilityResponseDto traceabilityResponseDto = traceabilityFeignPort.findTraceabilityByOrderId(orderId);
        traceabilityResponseDto.setCurrentStatus(status);
        traceabilityResponseDto.setEmployeeId(cedula);
        traceabilityResponseDto.setEmployeeEmail(jwtHandler.getUsername(token));

        for (OrderItemModel orderItemModel : orderModelEmployee.getItems()) {
            orderItemModel.setOrder(orderPersistencePort.findByIdIgnoreCycle(orderModelEmployee.getId()));
        }

        orderPersistencePort.saveOrder(orderModelEmployee);
        traceabilityFeignPort.saveTraceability(traceabilityResponseDto);

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
        if (!Objects.equals(orderModel.getEmployee(), cedula)) {
            throw new NotYourOrder("No puedes marcar una orden como lista si no eres el empleado asignado");
        }
        orderModel.setItems(orderItemPersistencePort.findAllByOrderId(orderModel.getId()));
        for (OrderItemModel orderItemModel : orderModel.getItems()) {
            orderItemModel.setOrder(orderPersistencePort.findByIdIgnoreCycle(orderModel.getId()));
        }
        if (orderModel.getStatus() != OrderStatus.EN_PREPARACION) {
            throw new PendingOrderException("La orden no se encuentra en estado de preparación");
        }
        TraceabilityResponseDto traceabilityResponseDto = traceabilityFeignPort.findTraceabilityByOrderId(orderId);
        System.out.println(traceabilityResponseDto.getId());
        traceabilityResponseDto.setCurrentStatus(OrderStatus.LISTO.toString());
        orderModel.setStatus(OrderStatus.LISTO);
        pinUserFeignPort.savePinUser(orderModel.getUserId(), orderId);
        orderPersistencePort.saveOrder(orderModel);
        traceabilityFeignPort.saveTraceability(traceabilityResponseDto);
    }

    @Override
    public void deliverOrder(String token, Integer orderId, Integer pin) {
        Integer cedula = jwtHandler.getCedulaFromToken(token);
        OrderModel orderModel = orderPersistencePort.findByIdIgnoreCycle(orderId);
        if (!Objects.equals(orderModel.getEmployee(), cedula)) {
            throw new NotYourOrder("No puedes marcar una orden como entregada si no eres el empleado asignado");
        }
        orderModel.setItems(orderItemPersistencePort.findAllByOrderId(orderModel.getId()));
        for (OrderItemModel orderItemModel : orderModel.getItems()) {
            orderItemModel.setOrder(orderPersistencePort.findByIdIgnoreCycle(orderModel.getId()));
        }
        RestaurantEmployeeModel restaurantEmployeeModel = restaurantEmployeePersistencePort.findEmployeeByCedula(cedula);
        if (!restaurantEmployeeModel.getRestaurantNit().equals(orderModel.getRestaurant().getNit())) {
            throw new NotYourRestaurant("No puedes marcar una orden como entregada si no pertenece a tu restaurante");
        }
        PinUserResponseDto pinUserResponseDto = pinUserFeignPort.findPinUser(pin);
        if (!Objects.equals(pinUserResponseDto.getOrderId(), orderId)) {
            throw new NotYourOrder("El pin ingresado no corresponde a la orden");
        }
        TraceabilityResponseDto traceabilityResponseDto = traceabilityFeignPort.findTraceabilityByOrderId(orderId);
        traceabilityResponseDto.setCurrentStatus(OrderStatus.ENTREGADO.toString());
        traceabilityResponseDto.setOrderEndDate(LocalDateTime.now());
        orderModel.setStatus(OrderStatus.ENTREGADO);
        orderPersistencePort.saveOrder(orderModel);
        traceabilityFeignPort.saveTraceability(traceabilityResponseDto);
    }

    @Override
    public void deleteOrder(String token, Integer orderId) {
        Integer cedula = jwtHandler.getCedulaFromToken(token);
        OrderModel orderModel = orderPersistencePort.findByIdIgnoreCycle(orderId);
        if (!Objects.equals(orderModel.getUserId(), cedula)) {
            throw new NotYourOrder("No puedes eliminar una orden que no te pertenece");
        }
        if (orderModel.getStatus() != OrderStatus.PENDIENTE) {
            throw new PendingOrderException("Lo sentimos, tu pedido ya está en proceso de preparación y no puede cancelarse");
        }
        TraceabilityResponseDto traceabilityResponseDto = traceabilityFeignPort.findTraceabilityByOrderId(orderId);
        traceabilityFeignPort.deleteTraceabilityByOrderId(traceabilityResponseDto.getId());
        orderPersistencePort.deleteOrder(orderModel);
    }

    @Override
    public RestaurantEfficiencyDto getEfficiency(String token) {
        Integer cedula = jwtHandler.getCedulaFromToken(token);
        String restaurantNit = restaurantPersistencePort.findByCedula(String.valueOf(cedula)).getNit();
        List<OrderModel> orderModelList = orderPersistencePort.findAllByRestaurantNit(restaurantNit);
        return traceabilityFeignPort.restaurantEfficiency(orderModelList);
    }
}
