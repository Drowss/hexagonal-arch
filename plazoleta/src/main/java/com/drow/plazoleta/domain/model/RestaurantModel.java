package com.drow.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantModel {
    private String nit;
    private String cedulaPropietario;
    private String nombre;
    private String direccion;
    private String telefono;
    private String urlLogo;
}
