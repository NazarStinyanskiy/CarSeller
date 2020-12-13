package com.company.carSeller.repositories;

import com.company.carSeller.entities.EngineEntity;
import org.springframework.data.repository.CrudRepository;

public interface EngineRepository extends CrudRepository<EngineEntity, Integer> {
}
