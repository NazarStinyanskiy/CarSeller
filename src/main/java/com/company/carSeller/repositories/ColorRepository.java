package com.company.carSeller.repositories;

import com.company.carSeller.entities.ColorEntity;
import org.springframework.data.repository.CrudRepository;

public interface ColorRepository extends CrudRepository<ColorEntity, Integer> {
}
