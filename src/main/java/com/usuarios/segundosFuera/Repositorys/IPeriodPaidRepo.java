package com.usuarios.segundosFuera.Repositorys;

import com.usuarios.segundosFuera.Models.PeriodPayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPeriodPaidRepo extends JpaRepository <PeriodPayEntity,Long> {
}
