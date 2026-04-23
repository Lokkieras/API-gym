package com.usuarios.segundosFuera.Services;

import com.usuarios.segundosFuera.Models.PeriodPayEntity;
import com.usuarios.segundosFuera.Models.Requests.CreateUserRequest;
import com.usuarios.segundosFuera.Models.Response.CreateUserResponse;
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


    public CreateUserResponse SaveUser(CreateUserRequest user)
    {
        PeriodPayEntity periodDay = periodPaidRepo.findById(user.getPeriod())
                .orElseThrow(() -> new RuntimeException("PeriodPay not found"));

        Long days = periodDay.getDays();

        //Fecha actual y fecha de caducidad obtenidas
        //LocalDate expirationDate = actualDay.plusDays(days);
        LocalDate actualDay = LocalDate.now();
        LocalDate expirationDate = actualDay.with(TemporalAdjusters.lastDayOfMonth());

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

    public String activateUserByDni(String dni) {
        Optional<UsersEntity> userOpt = userRepo.findByDni(dni);
        
        if(userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con DNI: " + dni);
        }
        
        UsersEntity user = userOpt.get();
        
        if(user.isPaid()) {
            return "ya tiene mensualidad";
        }
        
        user.setPaid(true);
        LocalDate lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        user.setExpirationDay(lastDayOfMonth);
        userRepo.save(user);
        
        return "Usuario " + user.getName() + " activado correctamente con expiración hasta " + lastDayOfMonth;
    }
}