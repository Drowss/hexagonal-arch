package com.drow.user.domain.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Integer documentoDeIdentidad;
    private String nombre;
    private String apellido;
    private Rol rol;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String clave;
}
