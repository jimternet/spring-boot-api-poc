package com.noofinc.inventory.model;

import java.io.Serializable;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.stereotype.Component;

@Table
@Component
public class Inventory  implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5180023453111985496L;

	@PrimaryKey
	private String inventory_id;
	
	private int supply;
	private int demand;
	
//	public String getInventory_id() {
//		return inventory_id;
//	}
//	public void setInventory_id(String inventory_id) {
//		this.inventory_id = inventory_id;
//	}
	
	public String getInventory_id() {
		return inventory_id;
	}
	public void setInventory_id(String inventory_id) {
		this.inventory_id = inventory_id;
	}
	public int getSupply() {
		return supply;
	}
	public void setSupply(int supply) {
		this.supply = supply;
	}
	public int getDemand() {
		return demand;
	}
	public void setDemand(int demand) {
		this.demand = demand;
	}
	@Override
	public String toString() {
		return "Inventory [id=" + inventory_id + ", supply=" + supply + ", demand="
				+ demand + "]";
	}
	
	
	
	
	
	
	

}
