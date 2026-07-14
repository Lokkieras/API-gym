package com.usuarios.segundosFuera.Models.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUserResponse {
    private String name;
    private String surname;
    private String dni;
    private int age;
    private Long period;
    private LocalDate payDay;
    private LocalDate expirationDate;
}
