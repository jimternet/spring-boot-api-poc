package com.noofinc.inventory.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import com.target.domain.esv.podam.CustomPodamFactoryImpl;

import uk.co.jemos.podam.annotations.PodamBooleanValue;
import uk.co.jemos.podam.annotations.PodamDoubleValue;
import uk.co.jemos.podam.annotations.PodamIntValue;
import uk.co.jemos.podam.annotations.PodamStringValue;
import uk.co.jemos.podam.api.PodamFactory;

@Table(forceQuote=true, value="retailProductLocation")
public class RetailProductLocation implements Serializable{

	
	@PrimaryKey
	private RetailProductLocationPrimaryKey dpci;
	


	//setup below
	private boolean isHold;
	private boolean isShip;
	private boolean isRush;
	private boolean isShipToStore;


	@PodamStringValue(length = 10)
	private String statusCode;
	
	private Date statusUpdateDate ;
	private Date expectedInventoryOutageDate;
	@PodamStringValue(length = 5)
	private String taxClassificationCode;
	@PodamStringValue(length = 5)
	private String vendors;
	@PodamBooleanValue
	private boolean isProductRecalledForStore;
	@PodamBooleanValue
	private boolean isPosWeightRequired;
	@PodamStringValue(length = 2)
	private String tareWeightCode;
	@PodamStringValue(length = 5)
	private String sellingConstraint1ComplianceProgramCode;
	@PodamStringValue(length = 5)
	private String sellingConstraint1Value;
	@PodamStringValue(length = 5)
	private String sellingConstraint2ComplianceProgramCode;
	@PodamStringValue(length = 5)
	private String sellingConstraint2Value;
	@PodamDoubleValue(maxValue = 1000)
	private double regularRetailPrice;
	@PodamDoubleValue(maxValue = 1000)
	private double currentRetailPrice;
	@PodamStringValue(length = 2)
	private String currentRetailPriceTypeCode;

	private boolean isKillSwitchEnabled;

	@PodamIntValue(minValue = 0, maxValue = 2)
	private int inventoryDeterminationIndicator;

	public RetailProductLocation() {
		super();
		this.isHold = Math.random() < 0.5;
		this.isShip = Math.random() < 0.5;
		this.isRush = Math.random() < 0.5;
		this.isShipToStore = Math.random() < 0.5;
		this.statusUpdateDate = new Date();
		this.expectedInventoryOutageDate= new Date();	
	}

	public static void main(String[] args) {

		RetailProductLocation example = RetailProductLocation
				.getRandomInstance();
		System.out.println(example.toString());
	}

	public static RetailProductLocation getRandomInstance() {
		PodamFactory factory = new CustomPodamFactoryImpl(); // This will use
																// the default
																// Random Data
																// Provider
																// Strategy
		RetailProductLocation obj = factory
				.manufacturePojo(RetailProductLocation.class);
		return obj;
	}

	
	
	
	

	public RetailProductLocationPrimaryKey getDpci() {
		return dpci;
	}

	public void setDpci(RetailProductLocationPrimaryKey dpci) {
		this.dpci = dpci;
	}

	public boolean isHold() {
		return isHold;
	}

	public void setHold(boolean isHold) {
		this.isHold = isHold;
	}

	public boolean isShip() {
		return isShip;
	}

	public void setShip(boolean isShip) {
		this.isShip = isShip;
	}

	public boolean isRush() {
		return isRush;
	}

	public void setRush(boolean isRush) {
		this.isRush = isRush;
	}



	public int getInventoryDeterminationIndicator() {
		return inventoryDeterminationIndicator;
	}

	public void setInventoryDeterminationIndicator(
			int inventoryDeterminationIndicator) {
		this.inventoryDeterminationIndicator = inventoryDeterminationIndicator;
	}

	public boolean isShipToStore() {
		return isShipToStore;
	}

	public void setShipToStore(boolean isShipToStore) {
		this.isShipToStore = isShipToStore;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Date getStatusUpdateDate() {
		return statusUpdateDate;
	}

	public void setStatusUpdateDate(Date statusUpdateDate) {
		this.statusUpdateDate = statusUpdateDate;
	}

	public Date getExpectedInventoryOutageDate() {
		return expectedInventoryOutageDate;
	}

	public void setExpectedInventoryOutageDate(Date expectedInventoryOutageDate) {
		this.expectedInventoryOutageDate = expectedInventoryOutageDate;
	}

	public String getTaxClassificationCode() {
		return taxClassificationCode;
	}

	public void setTaxClassificationCode(String taxClassificationCode) {
		this.taxClassificationCode = taxClassificationCode;
	}

	public String getVendors() {
		return vendors;
	}

	public void setVendors(String vendors) {
		this.vendors = vendors;
	}

	public boolean isProductRecalledForStore() {
		return isProductRecalledForStore;
	}

	public void setProductRecalledForStore(boolean isProductRecalledForStore) {
		this.isProductRecalledForStore = isProductRecalledForStore;
	}

	public boolean isPosWeightRequired() {
		return isPosWeightRequired;
	}

	public void setPosWeightRequired(boolean isPosWeightRequired) {
		this.isPosWeightRequired = isPosWeightRequired;
	}

	public String getTareWeightCode() {
		return tareWeightCode;
	}

	public void setTareWeightCode(String tareWeightCode) {
		this.tareWeightCode = tareWeightCode;
	}

	public String getSellingConstraint1ComplianceProgramCode() {
		return sellingConstraint1ComplianceProgramCode;
	}

	public void setSellingConstraint1ComplianceProgramCode(
			String sellingConstraint1ComplianceProgramCode) {
		this.sellingConstraint1ComplianceProgramCode = sellingConstraint1ComplianceProgramCode;
	}

	public String getSellingConstraint1Value() {
		return sellingConstraint1Value;
	}

	public void setSellingConstraint1Value(String sellingConstraint1Value) {
		this.sellingConstraint1Value = sellingConstraint1Value;
	}

	public String getSellingConstraint2ComplianceProgramCode() {
		return sellingConstraint2ComplianceProgramCode;
	}

	public void setSellingConstraint2ComplianceProgramCode(
			String sellingConstraint2ComplianceProgramCode) {
		this.sellingConstraint2ComplianceProgramCode = sellingConstraint2ComplianceProgramCode;
	}

	public String getSellingConstraint2Value() {
		return sellingConstraint2Value;
	}

	public void setSellingConstraint2Value(String sellingConstraint2Value) {
		this.sellingConstraint2Value = sellingConstraint2Value;
	}

	public double getRegularRetailPrice() {
		return regularRetailPrice;
	}

	public void setRegularRetailPrice(double regularRetailPrice) {
		this.regularRetailPrice = regularRetailPrice;
	}

	public double getCurrentRetailPrice() {
		return currentRetailPrice;
	}

	public void setCurrentRetailPrice(double currentRetailPrice) {
		this.currentRetailPrice = currentRetailPrice;
	}

	public String getCurrentRetailPriceTypeCode() {
		return currentRetailPriceTypeCode;
	}

	public void setCurrentRetailPriceTypeCode(String currentRetailPriceTypeCode) {
		this.currentRetailPriceTypeCode = currentRetailPriceTypeCode;
	}

	public boolean isKillSwitchEnabled() {
		return isKillSwitchEnabled;
	}

	public void setKillSwitchEnabled(boolean isKillSwitchEnabled) {
		this.isKillSwitchEnabled = isKillSwitchEnabled;
	}

	@Override
	public String toString() {
		return "RetailProductLocation [dpci=" + dpci + ", isHold=" + isHold
				+ ", isShip=" + isShip + ", isRush=" + isRush
				+ ", isShipToStore=" + isShipToStore + ", statusCode="
				+ statusCode + ", statusUpdateDate=" + statusUpdateDate
				+ ", expectedInventoryOutageDate="
				+ expectedInventoryOutageDate + ", taxClassificationCode="
				+ taxClassificationCode + ", vendors=" + vendors
				+ ", isProductRecalledForStore=" + isProductRecalledForStore
				+ ", isPosWeightRequired=" + isPosWeightRequired
				+ ", tareWeightCode=" + tareWeightCode
				+ ", sellingConstraint1ComplianceProgramCode="
				+ sellingConstraint1ComplianceProgramCode
				+ ", sellingConstraint1Value=" + sellingConstraint1Value
				+ ", sellingConstraint2ComplianceProgramCode="
				+ sellingConstraint2ComplianceProgramCode
				+ ", sellingConstraint2Value=" + sellingConstraint2Value
				+ ", regularRetailPrice=" + regularRetailPrice
				+ ", currentRetailPrice=" + currentRetailPrice
				+ ", currentRetailPriceTypeCode=" + currentRetailPriceTypeCode
				+ ", isKillSwitchEnabled=" + isKillSwitchEnabled
				+ ", inventoryDeterminationIndicator="
				+ inventoryDeterminationIndicator + "]";
	}

	

}
