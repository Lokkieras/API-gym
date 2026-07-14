package com.usuarios.segundosFuera.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Users",uniqueConstraints = {@UniqueConstraint(name = "UK_dni", columnNames = "dni")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private int age;

    @Column(nullable = false)
    private String dni;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "periodPaidID", foreignKey = @ForeignKey(name ="FK_periodPaidID"), nullable = false)
    private PeriodPayEntity periodPaidID;

    private LocalDate payDay;
    private LocalDate expirationDay;

    private boolean paid;
}
