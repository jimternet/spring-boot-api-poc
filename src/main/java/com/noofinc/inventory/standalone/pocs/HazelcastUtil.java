package com.noofinc.inventory.standalone.pocs;

import java.util.Iterator;
import java.util.Set;

import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.noofinc.inventory.model.Inventory;

public class HazelcastUtil {
	
	public static void main(String[] args) {
		Config config = new Config();
		ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
		// managementCenterConfig.setEnabled(true);
		// managementCenterConfig.setUrl("http://localhost:8080/mancenter-3.3.2");
		config.setManagementCenterConfig(managementCenterConfig );
		
		HazelcastInstance h = Hazelcast.newHazelcastInstance(config);

		IMap<?, ?> salaries = h.getMap("inventory");
		Set<?> keyset = salaries.keySet();
		Iterator<?> it = keyset.iterator();
		
		while (it.hasNext()){
			Object o = it.next();
			System.out.println("Key type : " + o.getClass().toString() + " : " + o.toString());
			Object value = salaries.get(o);
			System.out.println("Value type : " + value.getClass().toString() + " : " + value.toString());





		}
		
//		IMap<String, Inventory> inventories = h.getMap("inventories");
		
	}


}
