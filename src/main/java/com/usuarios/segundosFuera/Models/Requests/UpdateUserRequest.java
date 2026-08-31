package com.usuarios.segundosFuera.Models.Requests;

import com.usuarios.segundosFuera.Validation.ValidDni;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    private String name;
    private String surname;
    private int age;
    @NotBlank(message = "El DNI es obligatorio")
    @ValidDni
    private String dni;
    private Long period;
}
