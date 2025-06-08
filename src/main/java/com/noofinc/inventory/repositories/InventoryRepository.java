package com.noofinc.inventory.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.hazelcast.core.HazelcastInstance;
import com.noofinc.inventory.model.Inventory;

@Repository
public interface InventoryRepository extends CassandraRepository<Inventory, String> {
    
    @Cacheable(value = "inventory", key = "#inventory_id")
    default Inventory findOneInventory(String inventory_id) {
        return findById(inventory_id).orElse(null);
    }
    
    @Cacheable(value = "inventories")
    default List<Inventory> findAllInventory() {
        return (List<Inventory>) findAll();
    }
    
    @Caching(evict = { 
        @CacheEvict(value = "inventories", allEntries=true), 
        @CacheEvict(value = "inventory", key = "#inventory.inventory_id") 
    })
    default Inventory saveInventory(Inventory inventory) {
        return save(inventory);
    }
    
    default Integer getCount(HazelcastInstance hazelcastInstance) {
        Map<String, List<Inventory>> map = hazelcastInstance.getMap("inventories");
        if (map.isEmpty()) {
            return 0;
        }
        List<Inventory> list = map.values().iterator().next();
        return list != null ? list.size() : 0;
    }
}
