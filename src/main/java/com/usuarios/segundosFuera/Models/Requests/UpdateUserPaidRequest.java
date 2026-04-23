package com.usuarios.segundosFuera.Models.Requests;

public class UpdateUserPaidRequest {
    private String dni;

    public UpdateUserPaidRequest() {
    }

    public UpdateUserPaidRequest(String dni) {
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}

