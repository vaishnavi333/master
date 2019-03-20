package com.jsf.crud;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 
import com.jsf.crud.dboperation.SalesDatabaseOperation;
 
@ManagedBean @RequestScoped
public class SalesBean {
 
    private int customerid;  
    private int vendorid; 
    private String date_order; 
    private String date_reaching;
   
    public ArrayList salesListFromDB;
     public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public int getVendorid() {
		return vendorid;
	}

	public void setVendorid(int vendorid) {
		this.vendorid = vendorid;
	}

	public String getDate_order() {
		return date_order;
	}

	public void setDate_order(String date_order) {
		this.date_order = date_order;
	}

	public String getDate_reaching() {
		return date_reaching;
	}

	public void setDate_reaching(String date_reaching) {
		this.date_reaching = date_reaching;
	}

	@PostConstruct
    public void init()
	{
        salesListFromDB = SalesDatabaseOperation.getsalesListFromDB();
    }
 
    public ArrayList salesList() {
        return salesListFromDB;
    }
     
    public String savesalesDetails(SalesBean newsalesObj) {
        return SalesDatabaseOperation.savesalesDetailsInDB(newsalesObj);
    }
     
    public String editsalesRecord(int salesCode) {
        return SalesDatabaseOperation.editsalesRecordInDB(salesCode);
    }
     
    public String updatesalesDetails(SalesBean updatesalesObj) {
        return SalesDatabaseOperation.updatesalesDetailsInDB(updatesalesObj);
    }
     
    public String deletesalesRecord(int salesCode) {
        return SalesDatabaseOperation.deletesalesRecordInDB(salesCode);
    }
}
