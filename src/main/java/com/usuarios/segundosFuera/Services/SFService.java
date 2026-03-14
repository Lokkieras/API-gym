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
import java.util.Optional;


@Service
public class SFService {
    @Autowired
    private IUsersRepo userRepo;

    @Autowired
    private IPeriodPaidRepo periodPaidRepo;


    public CreateUserResponse saveUser(CreateUserRequest user)
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

    public ResponseEntity delete(Long id)
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
}
