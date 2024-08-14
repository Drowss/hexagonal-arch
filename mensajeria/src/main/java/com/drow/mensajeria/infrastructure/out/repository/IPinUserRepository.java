package com.drow.mensajeria.infrastructure.out.repository;

import com.drow.mensajeria.infrastructure.out.entity.PinUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPinUserRepository extends JpaRepository<PinUserEntity, Integer> {
    PinUserEntity findByUserIdAndOrderId(Integer userId, Integer orderId);
    PinUserEntity findByPin(Integer pin);
}
