package com.company.carSeller.services;

import com.company.carSeller.entities.InvoiceEntity;
import com.company.carSeller.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private InvoiceRepository repository;

    @Autowired
    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    public InvoiceEntity findById(int id){
        return repository.findById(id).get();
    }

    public boolean save(InvoiceEntity entity){
        if(entity.getCustomerName() != null && !entity.getCustomerName().isEmpty()
                || entity.getCustomerPhone() != null && !entity.getCustomerPhone().isEmpty()
                || entity.getSellerName() != null && !entity.getSellerName().isEmpty()
                || entity.getSellerPhone() != null && !entity.getSellerPhone().isEmpty()) {

            repository.save(entity);
            return true;
        }

        return false;
    }
}
