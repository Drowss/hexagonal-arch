package com.drow.plazoleta.infrastructure.out.repository;

import com.drow.plazoleta.infrastructure.out.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
