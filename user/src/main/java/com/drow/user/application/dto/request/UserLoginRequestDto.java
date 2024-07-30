package com.drow.user.application.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequestDto {
    @NotEmpty
    @NotNull
    private String correo;
    @NotEmpty
    @NotNull
    private String clave;
}
