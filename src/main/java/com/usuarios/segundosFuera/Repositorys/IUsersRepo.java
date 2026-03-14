package com.usuarios.segundosFuera.Repositorys;

import com.usuarios.segundosFuera.Models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepo extends JpaRepository<UsersEntity,Long> {
}
