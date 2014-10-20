package com.noofinc.inventory;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.aggregation.Aggregation;
import com.hazelcast.mapreduce.aggregation.Aggregations;
import com.hazelcast.mapreduce.aggregation.Supplier;
 
public class DistributedAggregation {
    public static void main(String[] args) {
        Config config = new Config();
        HazelcastInstance h = Hazelcast.newHazelcastInstance(config);
 
        IMap<String, Integer> salaries = h.getMap("salaries");
        fillInSalaries(salaries);
 
        Supplier<String, Integer, Integer> supplier = Supplier.all();
        
//        int sum = salaries.aggregate(supplier, Aggregations.integerSum());
//        
//        System.out.println("Aggregated sum: " + sum);
    }

	private static void fillInSalaries(IMap<String, Integer> salaries) {

		
		for (int i = 0; i < 10000; i++) {
			salaries.put(randomName(), randomNumber());
		}
		
	}

	private static Integer randomNumber() {
		Integer integer = 25000 + (int)(Math.random()*60000000); 
		return integer;
	}

	private static String randomName() {
		// TODO Auto-generated method stub
		return new NameGenerator().getName();
	}
}
