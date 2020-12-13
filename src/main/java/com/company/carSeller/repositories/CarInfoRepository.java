package com.company.carSeller.repositories;

import com.company.carSeller.entities.CarInfoEntity;
import com.company.carSeller.entities.ColorEntity;
import com.company.carSeller.entities.EngineEntity;
import com.company.carSeller.entities.MarkaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarInfoRepository extends CrudRepository<CarInfoEntity, Integer> {
    List<CarInfoEntity> findByOrderByPriceDesc();
    List<CarInfoEntity> findByOrderByIdDesc();
    List<CarInfoEntity> findByOrderByIdAsc();
}
