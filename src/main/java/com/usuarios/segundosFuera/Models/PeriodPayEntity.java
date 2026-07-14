package com.usuarios.segundosFuera.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "PeriodPaid")
@Getter
@Setter
public class PeriodPayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long days;

    @OneToMany(mappedBy = "periodPaidID")
    private List<UsersEntity> users;
}
