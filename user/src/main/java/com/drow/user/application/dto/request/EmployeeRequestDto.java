package com.drow.user.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequestDto {

    @NotNull
    private Integer documentoDeIdentidad;

    @NotNull
    @NotBlank
    private String nombre;

    @NotNull
    @NotBlank
    private String apellido;

    @NotNull
    @NotBlank
    @Size(max = 13)
    @Pattern(regexp = "\\+?\\d+", message = "El celular debe contener un máximo de 13 caracteres y puede contener el símbolo +")
    private String celular;

    @Email
    @NotNull
    @NotBlank
    private String correo;

    @NotNull
    @NotBlank
    private String clave;
}
