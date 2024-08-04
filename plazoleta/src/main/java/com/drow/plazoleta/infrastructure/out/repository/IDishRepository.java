package com.drow.plazoleta.infrastructure.out.repository;

import com.drow.plazoleta.infrastructure.out.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Integer> {
    Page<DishEntity> findAllByRestaurantNit(Pageable pageable, String nit);
}
