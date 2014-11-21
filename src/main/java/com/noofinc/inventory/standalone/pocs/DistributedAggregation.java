package com.noofinc.inventory.standalone.pocs;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.aggregation.Aggregation;
import com.hazelcast.mapreduce.aggregation.Aggregations;
import com.hazelcast.mapreduce.aggregation.Supplier;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.SqlPredicate;
import com.noofinc.inventory.model.Inventory;
import com.noofinc.inventory.util.NameGenerator;

public class DistributedAggregation {

	static final MetricRegistry metrics = new MetricRegistry();

	public static void main(String[] args) {
		DistributedAggregation ag = new DistributedAggregation();
		startReport();
		Config config = new Config();
		ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
		managementCenterConfig.setEnabled(true);
		managementCenterConfig.setUrl("http://localhost:8080/mancenter-3.3.2");
		config.setManagementCenterConfig(managementCenterConfig );
		
		HazelcastInstance h = Hazelcast.newHazelcastInstance(config);

		IMap<String, Integer> salaries = h.getMap("salaries");
		IMap<String, Inventory> inventories = h.getMap("inventories");

		ag.findByCriteriaByCount(inventories, 100);
		ag.findByCriteriaByCount(inventories, 2500);


		// for (int i = 0; i < 5; i++) {
		// ag.runThrough(salaries, 10);
		// ag.runThrough(salaries, 1000);
		// }

	}

	private void findByCriteriaByCount(IMap<String, Inventory> inventories,
			int count) {
		fillInInventories(inventories, count);
		findByCriteria(inventories, count);
	}

	private void findByCriteria(IMap<String, Inventory> inventories, int count) {
		Timer timer = metrics.timer("findByCriteria" + count);
		final Timer.Context mintimerContext = timer.time();

		EntryObject e = new PredicateBuilder().getEntryObject();
		Predicate predicate = e.get("supply").lessThan(30)
				.and(e.get("demand").lessThan(30));

		Set<Inventory> invSet = (Set<Inventory>) inventories.values(predicate);

		// Set<Inventory> invSet = (Set<Inventory>) inventories.values(new
		// SqlPredicate("supply < 10 AND demand < 30"));

		Iterator<Inventory> it = invSet.iterator();
		while (it.hasNext()) {
			System.out.println(it.next().toString());
		}

		mintimerContext.stop();
	}

	private void fillInInventories(IMap<String, Inventory> inventories,
			int count) {

		inventories.clear();

		for (int i = 0; i < count; i++) {
			Inventory inv = randomInventory();
			inventories.put(inv.getInventory_id(), inv);
		}

	}

	private void runThrough(IMap<String, Integer> salaries, int count) {
		fillInSalaries(salaries, count);

		int sum = sumAll(salaries, count);
		int max = findMax(salaries, count);
		int min = findMin(salaries, count);
		System.out.println("Aggregated sum: " + sum);
		System.out.println("Aggregated max: " + max);
		System.out.println("Aggregated min: " + min);
	}

	private int sumAll(IMap<String, Integer> salaries, int count) {
		Timer timer = metrics.timer("sumAll" + count);
		final Timer.Context sumAllContext = timer.time();

		Supplier<String, Integer, Integer> supplier = Supplier.all();
		Aggregation<String, Integer, Integer> aggregation = Aggregations
				.integerSum();

		int sum = salaries.aggregate(supplier, aggregation);
		sumAllContext.stop();

		return sum;
	}

	private int findMin(IMap<String, Integer> salaries, int count) {
		Timer timer = metrics.timer("findMin" + count);

		final Timer.Context mintimerContext = timer.time();

		Supplier<String, Integer, Integer> supplier = Supplier.all();
		Aggregation<String, Integer, Integer> aggregation = Aggregations
				.integerMin();

		int min = salaries.aggregate(supplier, aggregation);
		mintimerContext.stop();
		return min;
	}

	private int findMax(IMap<String, Integer> salaries, int count) {
		Timer timer = metrics.timer("findMax" + count);

		final Timer.Context maxTimerContext = timer.time();

		Supplier<String, Integer, Integer> supplier = Supplier.all();
		Aggregation<String, Integer, Integer> aggregation = Aggregations
				.integerMax();
		int min = salaries.aggregate(supplier, aggregation);
		maxTimerContext.stop();
		return min;
	}

	private void fillInSalaries(IMap<String, Integer> salaries, int count) {

		salaries.clear();

		for (int i = 0; i < count; i++) {
			salaries.put(randomName(), randomNumber());
		}

	}

	private Integer randomNumber() {
		Integer integer = 10 + (int) (Math.random() * 5000);
		return integer;
	}

	private String randomName() {
		// TODO Auto-generated method stub
		return new NameGenerator().getName();
	}

	private Inventory randomInventory() {
		Inventory inventory = new Inventory();
		inventory.setInventory_id(randomName());
		inventory.setDemand((int) (Math.random() * 100));
		inventory.setSupply((int) (Math.random() * 100));
		return inventory;

	}

	static void startReport() {
		ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();
		reporter.start(10, TimeUnit.SECONDS);
	}
}
