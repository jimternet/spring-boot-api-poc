package com.noofinc.inventory.model;

import java.io.Serializable;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import uk.co.jemos.podam.annotations.PodamIntValue;

@PrimaryKeyClass
public class RetailProductLocationPrimaryKey implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4190188823957073879L;
	@PodamIntValue(minValue = 1, maxValue = 1000)
	@PrimaryKeyColumn(forceQuote=true, ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private int departmentId;
	@PodamIntValue(minValue = 1, maxValue = 100)
	@PrimaryKeyColumn(forceQuote=true, ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private int classId;
	@PodamIntValue(minValue = 1, maxValue = 1000)
	@PrimaryKeyColumn(forceQuote=true, ordinal = 2, type = PrimaryKeyType.PARTITIONED)
	private int itemId;
	@PodamIntValue(minValue = 1, maxValue = 1000)
	@PrimaryKeyColumn(forceQuote=true, ordinal = 3, type = PrimaryKeyType.CLUSTERED)
	private int locationId;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "RetailProductLocationPrimaryKey [departmentId=" + departmentId
				+ ", classId=" + classId + ", itemId=" + itemId
				+ ", locationId=" + locationId + "]";
	}
	
	
}
