package com.noofinc.inventory.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.noofinc.inventory.model.Inventory;
import com.noofinc.inventory.model.RetailProductLocation;
import com.noofinc.inventory.model.RetailProductLocationPrimaryKey;

@Repository
public interface RetailProductLocationRepository extends CrudRepository<RetailProductLocation, RetailProductLocationPrimaryKey> {

}
