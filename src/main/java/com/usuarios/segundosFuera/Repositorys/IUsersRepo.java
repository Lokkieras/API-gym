package com.usuarios.segundosFuera.Repositorys;

import com.usuarios.segundosFuera.Models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsersRepo extends JpaRepository<UsersEntity,Long> {
    List<UsersEntity> findByPaidTrue();
    Optional<UsersEntity> findByDni(String dni);
}