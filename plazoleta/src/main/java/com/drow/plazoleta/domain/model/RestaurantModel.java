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
    private Long id;
    private Integer idPropietario;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String urlLogo;
}
