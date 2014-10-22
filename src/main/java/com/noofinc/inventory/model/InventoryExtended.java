package com.noofinc.inventory.model;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

public class InventoryExtended {
	
	
	private int departmentId;
	private int classId;
	private int itemId;
	private int locationId;
	private boolean isHold;
	private boolean isShipToGuest;
	private boolean isRush;
	private int ounHandQuantity;
	private int demand;
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public boolean isHold() {
		return isHold;
	}
	public void setHold(boolean isHold) {
		this.isHold = isHold;
	}
	public boolean isShipToGuest() {
		return isShipToGuest;
	}
	public void setShipToGuest(boolean isShipToGuest) {
		this.isShipToGuest = isShipToGuest;
	}
	public boolean isRush() {
		return isRush;
	}
	public void setRush(boolean isRush) {
		this.isRush = isRush;
	}
	public int getOunHandQuantity() {
		return ounHandQuantity;
	}
	public void setOunHandQuantity(int ounHandQuantity) {
		this.ounHandQuantity = ounHandQuantity;
	}
	public int getDemand() {
		return demand;
	}
	public void setDemand(int demand) {
		this.demand = demand;
	}
	
	
    public static void main(String[] args) {
    	
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        MultiMap <String , InventoryExtended > map = hazelcastInstance.getMultiMap( "map" );
        
        

        
    }
	
	

}
