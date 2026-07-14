package com.usuarios.segundosFuera.Models.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserListEntityResponse {
    private Long id;
    private String name;
    private String surname;
    private String dni;
    private String period;
    private LocalDate payDay;
    private LocalDate expirationDate;
    private boolean paid;
}
