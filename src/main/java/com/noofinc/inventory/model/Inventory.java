package com.noofinc.inventory.model;

import java.io.Serializable;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.stereotype.Component;

@Table
@Component
public class Inventory implements Serializable {
	
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + demand;
		result = prime * result
				+ ((inventory_id == null) ? 0 : inventory_id.hashCode());
		result = prime * result + supply;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		if (demand != other.demand)
			return false;
		if (inventory_id == null) {
			if (other.inventory_id != null)
				return false;
		} else if (!inventory_id.equals(other.inventory_id))
			return false;
		if (supply != other.supply)
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
	

}
