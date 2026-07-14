package com.usuarios.segundosFuera.Models.Requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    private String name;
    private String surname;
    private int age;
    private String dni;
    private Long period;
}
