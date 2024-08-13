package com.drow.plazoleta.infrastructure.out.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "restaurantEmployeeTable")
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String restaurantNit;
    private Integer employeeId;
}
