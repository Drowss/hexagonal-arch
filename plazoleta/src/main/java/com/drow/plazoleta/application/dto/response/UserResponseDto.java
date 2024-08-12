package com.drow.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Integer documentoDeIdentidad;
    private String nombre;
    private String apellido;
    private Rol rol;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
}
