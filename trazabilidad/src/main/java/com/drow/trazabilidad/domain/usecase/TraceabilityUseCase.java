package com.drow.trazabilidad.domain.usecase;

import com.drow.trazabilidad.domain.api.ITraceabilityServicePort;
import com.drow.trazabilidad.domain.dto.response.OrderEfficiency;
import com.drow.trazabilidad.domain.dto.OrderModelDto;
import com.drow.trazabilidad.domain.dto.response.RestaurantEfficiencyDto;
import com.drow.trazabilidad.domain.model.TraceabilityModel;
import com.drow.trazabilidad.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TraceabilityUseCase implements ITraceabilityServicePort {

    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    @Override
    public void saveTraceability(TraceabilityModel traceabilityModel) {
        traceabilityPersistencePort.saveTraceability(traceabilityModel);
    }

    @Override
    public TraceabilityModel findTraceabilityByOrderId(Integer orderId) {
        TraceabilityModel traceabilityModel = traceabilityPersistencePort.findTraceabilityByOrderId(orderId);
        System.out.println(traceabilityModel.getId() + "En usecase");
        return traceabilityModel;
    }

    @Override
    public void deleteTraceabilityByOrderId(String orderId) {
        traceabilityPersistencePort.deleteTraceabilityByOrderId(orderId);
    }

    @Override
    public RestaurantEfficiencyDto getEfficiency(List<OrderModelDto> orderModelDtos) {
        long totalDurationInMinutes = 0;
        RestaurantEfficiencyDto response = new RestaurantEfficiencyDto();
        response.setOrderEfficiencies(new ArrayList<>());
        for (OrderModelDto orderModelDto : orderModelDtos) {
            OrderEfficiency orderEfficiency = new OrderEfficiency();
            orderEfficiency.setOrderId(orderModelDto.getId());
            orderEfficiency.setEmployeeId(orderModelDto.getEmployee());
            if (orderModelDto.getId() != 1) {
                TraceabilityModel traceabilityModel = this.findTraceabilityByOrderId(orderModelDto.getId());
                LocalDateTime orderStartDate = traceabilityModel.getOrderStartDate();
                LocalDateTime orderEndDate = traceabilityModel.getOrderEndDate();

                orderEfficiency.setOrderDate(orderStartDate);
                orderEfficiency.setDeliveryDate(orderEndDate);

                long durationInMinutes = ChronoUnit.MINUTES.between(orderStartDate, orderEndDate);
                totalDurationInMinutes += durationInMinutes;

                response.getOrderEfficiencies().add(orderEfficiency);
            }

        }

        if (!orderModelDtos.isEmpty()) {
            float averageDurationInMinutes = (float) totalDurationInMinutes / orderModelDtos.size();
            response.setEfficiency(averageDurationInMinutes);
        } else {
            response.setEfficiency(0.0f);
        }

        return response;
    }
}
