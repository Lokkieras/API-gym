package com.usuarios.segundosFuera.Controller;

import com.usuarios.segundosFuera.Models.Requests.CreateUserRequest;
import com.usuarios.segundosFuera.Models.Requests.UpdateUserPaidRequest;
import com.usuarios.segundosFuera.Models.Requests.UpdateUserRequest;
import com.usuarios.segundosFuera.Models.Response.CreateUserResponse;
import com.usuarios.segundosFuera.Models.Response.UserListEntityResponse;
import com.usuarios.segundosFuera.Models.UsersEntity;
import com.usuarios.segundosFuera.Services.SFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gym")
public class SFController {

    @Autowired
    private SFService userService;

    //Listar Usuarios
    @GetMapping("/bbdd")
    public List<UserListEntityResponse> findAll() {
        return userService.GetAllUsers();
    }

    // Guardar un nuevo usuario
    @PostMapping
    public ResponseEntity<CreateUserResponse> save(@RequestBody CreateUserRequest user) {
        return ResponseEntity.created(URI.create("")).body(userService.SaveUser(user));
    }

    //Borrado de un usuario por id
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        return userService.DeleteUser(id);
    }

    //Obtención de usuarios con suscripción activa
    @GetMapping("/paid")
    public ResponseEntity<List<UsersEntity>> getPaidUsers() {
        return ResponseEntity.ok(userService.getPaidUsers());
    }

    //Comprobacion de usuarios con suscripción expirada
    @PostMapping("/check-expiration")
    public ResponseEntity<String> checkExpiredUsers() {
        userService.checkAndUpdateExpiredUsers();
        return ResponseEntity.ok("Verificación de expiración completada");
    }

    //Activación de usuario por dni
    @PostMapping("/activate-by-dni")
    public ResponseEntity<String> activateUserByDni(@RequestBody UpdateUserPaidRequest request) {
        try {
            String result = userService.activateUserByDni(request.getDni());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Modificación de usuario por dni
    @PutMapping("/{dni}")
    public ResponseEntity<String> updateUserByDni(@PathVariable String dni, @RequestBody UpdateUserRequest request) {
        try {
            String result = userService.updateUserByDni(dni, request);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}