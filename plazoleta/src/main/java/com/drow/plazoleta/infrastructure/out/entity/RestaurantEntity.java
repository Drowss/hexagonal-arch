package com.drow.plazoleta.infrastructure.out.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "restaurantTable")
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEntity {
    @Id
    private String nit;
    private String cedulaPropietario;
    private String nombre;
    private String direccion;
    private String telefono;
    private String urlLogo;
    @OneToMany(mappedBy = "restaurant")
    private List<DishEntity> dishes;
}
