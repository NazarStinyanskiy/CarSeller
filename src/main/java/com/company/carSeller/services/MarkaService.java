package com.company.carSeller.services;

import com.company.carSeller.entities.ColorEntity;
import com.company.carSeller.entities.MarkaEntity;
import com.company.carSeller.repositories.ColorRepository;
import com.company.carSeller.repositories.MarkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MarkaService {
    private MarkaRepository repository;

    @Autowired
    public MarkaService(MarkaRepository repository) {
        this.repository = repository;
    }

    public List<MarkaEntity> getAll(){
        Iterator<MarkaEntity> iterator = repository.findAll().iterator();
        List<MarkaEntity> resultList = new ArrayList<>();

        while(iterator.hasNext()){
            resultList.add(iterator.next());
        }

        return resultList;
    }

    public MarkaEntity getById(int id){
        return repository.findById(id).get();
    }
}
