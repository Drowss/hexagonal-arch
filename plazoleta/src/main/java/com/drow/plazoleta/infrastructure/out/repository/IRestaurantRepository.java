package com.drow.plazoleta.infrastructure.out.repository;

import com.drow.plazoleta.infrastructure.out.entity.RestaurantEntity;
import org.apache.el.stream.Optional;
import org.apache.el.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, String> {
    RestaurantEntity findByCedulaPropietario(String ownerDni);
}
