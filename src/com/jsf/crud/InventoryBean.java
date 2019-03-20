package com.jsf.crud;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 
import com.jsf.crud.dboperation.InventoryDatabaseOperation;
 
@ManagedBean @RequestScoped
public class InventoryBean 
{
 
    private int unitstock;
    private int unitprice;
    private String serialno; 
    private int vendorid;
    
    public int getVendorid() {
		return vendorid;
	}

	public void setVendorid(int vendorid) {
		this.vendorid = vendorid;
	}

	public ArrayList inventoryListFromDB;
    
    public int getUnitstock() {
		return unitstock;
	}

	public void setUnitstock(int unitstock) {
		this.unitstock = unitstock;
	}

	
   public int getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(int unitprice) {
		this.unitprice = unitprice;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	
    
   @PostConstruct
    public void init() {
        inventoryListFromDB = InventoryDatabaseOperation.getInventoryListFromDB();
    }
 
    public ArrayList inventoryList() {
        return inventoryListFromDB;
    }
     
    public String saveinventoryDetails(InventoryBean newinventoryObj) {
        return InventoryDatabaseOperation.saveInventoryDetailsInDB(newinventoryObj);
    }
     
    public String editinventoryRecord(int inventoryCode) {
        return InventoryDatabaseOperation.editInventoryRecordInDB(inventoryCode);
    }
     
    public String updateinventoryDetails(InventoryBean updateinventoryObj) {
        return InventoryDatabaseOperation.updateInventoryDetailsInDB(updateinventoryObj);
    }
     
    public String deleteinventoryRecord(int inventoryCode) {
        return InventoryDatabaseOperation.deleteInventoryRecordInDB(inventoryCode);
    }
}
