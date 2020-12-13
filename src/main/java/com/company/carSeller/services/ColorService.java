package com.company.carSeller.services;

import com.company.carSeller.entities.ColorEntity;
import com.company.carSeller.repositories.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ColorService {
    private ColorRepository repository;

    @Autowired
    public ColorService(ColorRepository repository) {
        this.repository = repository;
    }

    public List<ColorEntity> getAll(){
        Iterator<ColorEntity> iterator = repository.findAll().iterator();
        List<ColorEntity> resultList = new ArrayList<>();

        while(iterator.hasNext()){
            resultList.add(iterator.next());
        }

        return resultList;
    }

    public ColorEntity getById(int id){
        return repository.findById(id).get();
    }
}
