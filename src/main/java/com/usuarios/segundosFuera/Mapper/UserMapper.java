package com.usuarios.segundosFuera.Mapper;

import com.usuarios.segundosFuera.Models.PeriodPayEntity;
import com.usuarios.segundosFuera.Models.Requests.CreateUserRequest;
import com.usuarios.segundosFuera.Models.Response.CreateUserResponse;
import com.usuarios.segundosFuera.Models.Response.DeleteUserResponse;
import com.usuarios.segundosFuera.Models.UsersEntity;

import java.time.LocalDate;

public class UserMapper {

    public static UsersEntity CreateUserRequestToUserEntity(CreateUserRequest user, LocalDate actualDay, LocalDate expirationDate, PeriodPayEntity periodDay)
    {
        UsersEntity users = new UsersEntity(user.getName(),user.getSurname(),user.getDni(),periodDay,actualDay,expirationDate,true);
        return users;
    }


    public static CreateUserResponse CreateEntityToResponse(UsersEntity user)
    {
        CreateUserResponse userResponse = new CreateUserResponse();
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setDni(user.getDni());
        userResponse.setPeriod(user.getPeriodPaidID().getId());
        userResponse.setPayDay(user.getPayDay());
        userResponse.setExpirationDate(user.getExpirationDay());

        return userResponse;
    }

    public static DeleteUserResponse DeleteEntityToResponse(UsersEntity user)
    {
        DeleteUserResponse userResponse = new DeleteUserResponse();
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setDni(user.getDni());
        userResponse.setPeriod(user.getPeriodPaidID().getId());
        userResponse.setPayDay(user.getPayDay());
        userResponse.setExpirationDate(user.getExpirationDay());

        return userResponse;
    }


}
