package com.drow.trazabilidad.domain.dto.response;

import java.util.List;

public class RestaurantEfficiencyDto {
    private Float efficiency;
    private List<OrderEfficiency> orderEfficiencies;

    public RestaurantEfficiencyDto(Float efficiency, List<OrderEfficiency> orderEfficiencies) {
        this.efficiency = efficiency;
        this.orderEfficiencies = orderEfficiencies;
    }

    public RestaurantEfficiencyDto() {
    }

    public Float getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Float efficiency) {
        this.efficiency = efficiency;
    }

    public List<OrderEfficiency> getOrderEfficiencies() {
        return orderEfficiencies;
    }

    public void setOrderEfficiencies(List<OrderEfficiency> orderEfficiencies) {
        this.orderEfficiencies = orderEfficiencies;
    }
}
