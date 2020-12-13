package com.company.carSeller.services;

import com.company.carSeller.entities.ColorEntity;
import com.company.carSeller.entities.EngineEntity;
import com.company.carSeller.repositories.EngineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EngineService {
    private EngineRepository repository;

    @Autowired
    public EngineService(EngineRepository repository) {
        this.repository = repository;
    }

    public List<EngineEntity> getAll(){
        Iterator<EngineEntity> iterator = repository.findAll().iterator();
        List<EngineEntity> resultList = new ArrayList<>();

        while(iterator.hasNext()){
            resultList.add(iterator.next());
        }

        return resultList;
    }

    public EngineEntity getById(int id){
        return repository.findById(id).get();
    }
}
