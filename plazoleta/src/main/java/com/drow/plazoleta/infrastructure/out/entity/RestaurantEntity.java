package com.drow.plazoleta.infrastructure.out.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "restaurantTable")
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEntity {
    @Id
    private String nit;
    private Integer idPropietario;
    private String nombre;
    private String direccion;
    private String telefono;
    private String urlLogo;
}
