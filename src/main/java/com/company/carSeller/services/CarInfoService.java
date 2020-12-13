package com.company.carSeller.services;

import com.company.carSeller.entities.CarInfoEntity;
import com.company.carSeller.entities.InvoiceEntity;
import com.company.carSeller.entities.ModificationEntity;
import com.company.carSeller.repositories.CarInfoRepository;
import com.company.carSeller.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarInfoService {
    private final CarInfoRepository repository;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public CarInfoService(CarInfoRepository repository, InvoiceRepository invoiceRepository) {
        this.repository = repository;
        this.invoiceRepository = invoiceRepository;
    }

    public List<CarInfoEntity> getAll() {
        Iterator<CarInfoEntity> iterator = repository.findAll().iterator();
        List<CarInfoEntity> resultList = new ArrayList<>();

        while (iterator.hasNext()) {
            CarInfoEntity entity = iterator.next();
            resultList.add(entity);
        }

        return resultList;
    }

    public List<CarInfoEntity> getAllDesc() {
        return repository.findByOrderByIdDesc();
    }

    public List<CarInfoEntity> getMostExpensiveLimit5() {
        return repository.findByOrderByPriceDesc().stream().limit(5).collect(Collectors.toList());
    }

    public List<CarInfoEntity> getAllByColor_id(List<CarInfoEntity> list, int color_id) {
        List<CarInfoEntity> resultList = new ArrayList<>();
        Iterator<CarInfoEntity> iterator = list.iterator();

        while (iterator.hasNext()) {
            CarInfoEntity entity = iterator.next();
            if (entity.getColorEntity().getId() == color_id) {
                resultList.add(entity);
            }
        }

        return resultList;
    }

    public List<CarInfoEntity> getAllByEngine_id(List<CarInfoEntity> list, int engine_id) {
        List<CarInfoEntity> resultList = new ArrayList<>();
        Iterator<CarInfoEntity> iterator = list.iterator();

        while (iterator.hasNext()) {
            CarInfoEntity entity = iterator.next();
            if (entity.getEngineEntity().getId() == engine_id) {
                resultList.add(entity);
            }
        }

        return resultList;
    }

    public List<CarInfoEntity> getAllByMarka_id(List<CarInfoEntity> list, int marka_id) {
        List<CarInfoEntity> resultList = new ArrayList<>();
        Iterator<CarInfoEntity> iterator = list.iterator();

        while (iterator.hasNext()) {
            CarInfoEntity entity = iterator.next();
            if (entity.getMarkaEntity().getId() == marka_id) {
                resultList.add(entity);
            }
        }

        return resultList;
    }

    public List<CarInfoEntity> getAllBetweenPrice(List<CarInfoEntity> list, int lowerPrice, int higherPrice){
        List<CarInfoEntity> resultList = new ArrayList<>();
        Iterator<CarInfoEntity> iterator = list.iterator();

        if(higherPrice < lowerPrice) higherPrice = 2147483647;

        while(iterator.hasNext()){
            CarInfoEntity entity = iterator.next();
            if(entity.getPrice() > lowerPrice && entity.getPrice() < higherPrice){
                resultList.add(entity);
            }
        }

        return resultList;
    }

    public CarInfoEntity getById(int id) {
        return repository.findById(id).get();
    }

    public CarInfoEntity save(CarInfoEntity carInfoEntity) {
        Set<ModificationEntity> set = carInfoEntity.getModificationEntity();
        set.removeIf(modificationEntity -> modificationEntity.getId() == 0);

        carInfoEntity.setModificationEntity(set);

        return repository.save(carInfoEntity);
    }

    public List<CarInfoEntity> getAllOrderedDescForUserId(int id) {
        Iterator<CarInfoEntity> iterator = repository.findByOrderByIdDesc().iterator();
        List<CarInfoEntity> resultList = new ArrayList<>();

        while (iterator.hasNext()) {
            CarInfoEntity car = iterator.next();
            if (car.getUserInfoEntity().getId() == id) {
                resultList.add(car);
            }
        }

        return resultList;
    }

    public void deleteById(int id) {
        InvoiceEntity invoiceEntity = repository.findById(id).get().getInvoiceEntity();
        if (invoiceEntity != null)
            invoiceRepository.deleteById(repository.findById(id).get().getInvoiceEntity().getId());

        if (repository.findById(id).isPresent()) repository.deleteById(id);
    }
}