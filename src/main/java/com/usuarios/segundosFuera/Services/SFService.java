package com.usuarios.segundosFuera.Services;

import com.usuarios.segundosFuera.Models.PeriodPayEntity;
import com.usuarios.segundosFuera.Models.Requests.CreateUserRequest;
import com.usuarios.segundosFuera.Models.Requests.UpdateUserRequest;
import com.usuarios.segundosFuera.Models.Response.CreateUserResponse;
import com.usuarios.segundosFuera.Models.Response.UserListEntityResponse;
import com.usuarios.segundosFuera.Models.UsersEntity;
import com.usuarios.segundosFuera.Repositorys.IPeriodPaidRepo;
import com.usuarios.segundosFuera.Repositorys.IUsersRepo;
import com.usuarios.segundosFuera.Mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;


@Service
public class SFService {
    @Autowired
    private IUsersRepo userRepo;

    @Autowired
    private IPeriodPaidRepo periodPaidRepo;


    public List<UserListEntityResponse> GetAllUsers()
    {

        return userRepo.findAll()
                .stream()
                .map(User -> {
                    UserListEntityResponse userResponse = new UserListEntityResponse();
                    userResponse.setId(User.getId());
                    userResponse.setName(User.getName());
                    userResponse.setSurname(User.getSurname());
                    userResponse.setDni(User.getDni());
                    userResponse.setPeriod(User.getPeriodPaidID().getName());
                    userResponse.setPayDay(User.getPayDay());
                    userResponse.setExpirationDate(User.getExpirationDay());
                    userResponse.setPaid(User.isPaid());
                    return userResponse;
                })
                .toList();
    }

    public CreateUserResponse SaveUser(CreateUserRequest user)
    {
        LocalDate actualDay = LocalDate.now();
        LocalDate expirationDate;

        if(user.getPeriod() == 1)
        {
            if(user.getAge()<18)
            {
                user.setPeriod(3L);
            } else {
                user.setPeriod(1L);
            }
        }

        PeriodPayEntity periodDay = periodPaidRepo.findById(user.getPeriod())
                .orElseThrow(() -> new RuntimeException("PeriodPay not found"));

        Long days = periodDay.getDays();
        if(days==30)
        {
            expirationDate = actualDay.with(TemporalAdjusters.lastDayOfMonth());
        } else {
            expirationDate = actualDay;
        }

        UsersEntity userSupp = userRepo.save(UserMapper.CreateUserRequestToUserEntity(user,actualDay,expirationDate,periodDay));

        return UserMapper.CreateEntityToResponse(userSupp);
    }

    public ResponseEntity DeleteUser(Long id)
    {
        Optional<UsersEntity> exist = userRepo.findById(id);

        if(exist.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }

        UsersEntity user = exist.get();
        userRepo.deleteById(id);

        return ResponseEntity.ok().body("Registro con id " + id + " eliminado correctamente");
    }

    public List<UsersEntity> getPaidUsers() {
        return userRepo.findByPaidTrue();
    }

    public void checkAndUpdateExpiredUsers() {
        LocalDate today = LocalDate.now();
        List<UsersEntity> paidUsers = userRepo.findByPaidTrue();
        
        for(UsersEntity user : paidUsers) {
            if(user.getExpirationDay() != null && user.getExpirationDay().isBefore(today)) {
                user.setPaid(false);
                userRepo.save(user);
                System.out.println("Usuario " + user.getId() + " (" + user.getName() + ") marcado como no pagado - expiración: " + user.getExpirationDay());
            }
        }
    }

    public String activateUserByDni(Long id) {
        Optional<UsersEntity> userOpt = userRepo.findById(id);
        
        if(userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        
        UsersEntity user = userOpt.get();
        
        if(user.isPaid()) {
            return "ya tiene mensualidad";
        }
        
        user.setPaid(true);

        /*LocalDate actualDay = LocalDate.now();
        LocalDate lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        user.setExpirationDay(lastDayOfMonth);
        user.setPayDay(actualDay);*/

        PeriodPayEntity periodDay = periodPaidRepo.findById(user.getPeriodPaidID().getId())
                .orElseThrow(() -> new RuntimeException("PeriodPay not found"));
        LocalDate actualDay = LocalDate.now();
        user.setPayDay(actualDay);
        LocalDate expirationDate;
        Long days = periodDay.getDays();
        if(days==30)
        {
            expirationDate = actualDay.with(TemporalAdjusters.lastDayOfMonth());
        } else {
            expirationDate = actualDay;
        }
        user.setExpirationDay(expirationDate);
        userRepo.save(user);
        
        return "Usuario " + user.getName() + " activado correctamente con expiración hasta " + expirationDate;
    }

    public String updateUserByDni(String dni, UpdateUserRequest request) {
        Optional<UsersEntity> userOpt = userRepo.findByDni(dni);
        
        if(userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con DNI: " + dni);
        }
        
        UsersEntity user = userOpt.get();
        
        // Update fields
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getSurname() != null) {
            user.setSurname(request.getSurname());
        }
        if (request.getAge() != 0) { // assuming age > 0
            user.setAge(request.getAge());
        }
        if (request.getDni() != null && !request.getDni().equals(user.getDni())) {
            Optional<UsersEntity> existing = userRepo.findByDni(request.getDni());
            if (existing.isPresent()) {
                throw new RuntimeException("DNI ya está en uso: " + request.getDni());
            }
            user.setDni(request.getDni());
        }
        if (request.getPeriod() != null) {
            PeriodPayEntity periodDay = periodPaidRepo.findById(request.getPeriod())
                    .orElseThrow(() -> new RuntimeException("PeriodPay not found"));
            user.setPeriodPaidID(periodDay);

            LocalDate actualDay = LocalDate.now();
            LocalDate expirationDate;
            Long days = periodDay.getDays();
            if(days==30)
            {
                expirationDate = actualDay.with(TemporalAdjusters.lastDayOfMonth());
            } else {
                expirationDate = actualDay;
            }
            user.setExpirationDay(expirationDate);
        }
        
        userRepo.save(user);
        
        return "Usuario con DNI " + dni + " actualizado correctamente";
    }
}