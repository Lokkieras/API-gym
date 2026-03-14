package com.usuarios.segundosFuera.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Users",uniqueConstraints = {@UniqueConstraint(name = "UK_dni", columnNames = "dni")})
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


    public UsersEntity() {
    }

    public UsersEntity(String name, String surname, int age, String dni, PeriodPayEntity periodPaidID, LocalDate payDay, LocalDate expirationDay, boolean paid) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.dni = dni;
        this.periodPaidID = periodPaidID;
        this.payDay = payDay;
        this.expirationDay = expirationDay;
        this.paid = paid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public PeriodPayEntity getPeriodPaidID() {
        return periodPaidID;
    }

    public void setPeriodPaidID(PeriodPayEntity periodPaidID) {
        this.periodPaidID = periodPaidID;
    }

    public LocalDate getPayDay() {
        return payDay;
    }

    public void setPayDay(LocalDate payDay) {
        this.payDay = payDay;
    }

    public LocalDate getExpirationDay() {
        return expirationDay;
    }

    public void setExpirationDay(LocalDate expirationDay) {
        this.expirationDay = expirationDay;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
