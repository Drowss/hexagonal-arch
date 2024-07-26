package com.drow.user.infrastructure.out.jpa.entity;

import com.drow.user.domain.model.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "userTable")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {
    @Id
    private Integer documentoDeIdentidad;
    private String nombre;
    private String apellido;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String clave;
}
