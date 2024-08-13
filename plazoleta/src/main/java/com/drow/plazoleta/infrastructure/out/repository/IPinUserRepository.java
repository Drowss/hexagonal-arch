package com.drow.plazoleta.infrastructure.out.repository;

import com.drow.plazoleta.infrastructure.out.entity.PinUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPinUserRepository extends JpaRepository<PinUserEntity, Integer>  {
    PinUserEntity findByUserIdAndOrderId(Integer userId, Integer orderId);
}
