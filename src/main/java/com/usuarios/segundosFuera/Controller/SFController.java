package com.usuarios.segundosFuera.Controller;

import com.usuarios.segundosFuera.Models.Requests.CreateUserRequest;
import com.usuarios.segundosFuera.Models.Response.CreateUserResponse;
import com.usuarios.segundosFuera.Services.SFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/gym")
public class SFController {

    @Autowired
    private SFService userService;


    // Guardar un nuevo usuario
    @PostMapping
    public ResponseEntity<CreateUserResponse> save(@RequestBody CreateUserRequest user) {
        return ResponseEntity.created(URI.create("")).body(userService.saveUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }
}
