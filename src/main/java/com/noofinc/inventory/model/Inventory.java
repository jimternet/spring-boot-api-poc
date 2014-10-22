package com.noofinc.inventory.model;

import java.io.Serializable;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Inventory  implements Serializable {
	
	
	@PrimaryKey
	private String id;
	private int supply;
	private int demand;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
		return "Inventory [id=" + id + ", supply=" + supply + ", demand="
				+ demand + "]";
	}
	
	
	
	
	
	
	

}
