package com.drow.plazoleta.domain.api;

public interface IOrderServicePort {
    void saveOrder(String token, String restaurantNit);
}
