package com.noofinc.inventory.controllers;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ISet;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.Predicates;
import com.noofinc.inventory.model.Inventory;
import com.noofinc.inventory.model.RetailProductLocation;
import com.noofinc.inventory.model.RetailProductLocationPrimaryKey;
import com.noofinc.inventory.repositories.InventoryRepository;
import com.noofinc.inventory.repositories.RetailProductLocationRepository;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
public class RetailProductLocationController {

	private static final Logger LOG = LoggerFactory
			.getLogger(RetailProductLocationController.class);

	@Autowired
	RetailProductLocationRepository retailProductLocationRepository;

	@Autowired
	HazelcastInstance hazelcastInstance;

	@RequestMapping(value = "/rpl", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "get all rpl records")
	public @ResponseBody Iterable<RetailProductLocation> getAllRpl() {
		return retailProductLocationRepository.findAll();
	}

	@RequestMapping(value = "/rplFiltered", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "get all rpl records")
	public @ResponseBody Iterable<RetailProductLocation> getFilteredRpl() {
		
		Iterable<RetailProductLocation> list = retailProductLocationRepository
				.findAll();
		
		

		
		
//		RetailProductLocationPrimaryKey key = new RetailProductLocationPrimaryKey();
//		key.setDepartmentId(25);
//		key.setClassId(1);
//		key.setItemId(1);

		IMap<String, RetailProductLocation> rplMap = hazelcastInstance.getMap("rpl");


		
		    for (RetailProductLocation entry : list) {
		    	rplMap.put(entry.getDpci().toString(), entry);
		    }
	
		    LOG.info("count of rplMap results : " + rplMap.size());

	
		EntryObject e = new PredicateBuilder().getEntryObject();
		
//		Predicate predicate = e.get("isHold").equal(false);
		
		
		Predicates.equal("isHold", true);
		
		Predicate isHoldPredicate = Predicates.equal("isHold", true);
		Predicate isShipPredicate = Predicates.equal("isShip", true);
		Predicate isRushPredicate = Predicates.equal("isRush", true);


		Predicate aggregatePredictate =  Predicates.and(isHoldPredicate, isShipPredicate,isRushPredicate );
		
		Set<RetailProductLocation> filteredResults = (Set<RetailProductLocation>) rplMap.values(aggregatePredictate);		
		
		LOG.info("count of filtered results : " + filteredResults.size());

		return filteredResults;
	}

}
