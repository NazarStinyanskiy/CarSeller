package com.company.carSeller.repositories;

import com.company.carSeller.entities.CarInfoEntity;
import com.company.carSeller.entities.ModificationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface ModificationRepository extends CrudRepository<ModificationEntity, Integer> {
    List<ModificationEntity> findAllByTitle(String title);
    List<ModificationEntity> findAllByCarInfoEntities(CarInfoEntity carInfoEntities);
    List<ModificationEntity> findByOrderById();
}