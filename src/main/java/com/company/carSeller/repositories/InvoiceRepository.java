package com.company.carSeller.repositories;

import com.company.carSeller.entities.InvoiceEntity;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<InvoiceEntity, Integer> {
}
