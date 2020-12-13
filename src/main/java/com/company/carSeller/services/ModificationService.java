package com.company.carSeller.services;

import com.company.carSeller.entities.CarInfoEntity;
import com.company.carSeller.entities.ModificationEntity;
import com.company.carSeller.repositories.ModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ModificationService {
    private final ModificationRepository repository;

    @Autowired
    public ModificationService(ModificationRepository repository) {
        this.repository = repository;
    }

    public List<ModificationEntity> getAll(){
        Iterator<ModificationEntity> iterator = repository.findByOrderById().iterator();
        List<ModificationEntity> resultList = new ArrayList<>();

        while(iterator.hasNext()){
            resultList.add(iterator.next());
        }

        return resultList;
    }

    public ModificationEntity getByTitle(String title){
        return repository.findAllByTitle(title).stream().findFirst().get();
    }

    public List<ModificationEntity> getAllByCarInfo(CarInfoEntity entity){
        List<CarInfoEntity> carInfoEntities = new ArrayList<>();
        carInfoEntities.add(entity);

        return repository.findAllByCarInfoEntities(entity);
    }
}
