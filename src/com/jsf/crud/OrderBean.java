package com.jsf.crud;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 
import com.jsf.crud.dboperation.OrderDatabaseOperation;
 
@ManagedBean @RequestScoped
public class OrderBean {
 
    private int code;  
    private String productcode; 
    private int quantity; 
    private int customerid;
    
	private int vendorid;
    
    public ArrayList ordersListFromDB;
 
    public int getCode() {
        return code;  
    }
 
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
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

   
    public int getQuantity() {
        return quantity;  
    }
 
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
   
    
 
    @PostConstruct
    public void init() {
        ordersListFromDB = OrderDatabaseOperation.getordersListFromDB();
    }
 
    public ArrayList orderList() {
        return ordersListFromDB;
    }
     
    public String saveorderDetails(OrderBean neworderObj) {
        return OrderDatabaseOperation.saveorderDetailsInDB(neworderObj);
    }
     
    public String editorderRecord(int orderCode) {
        return OrderDatabaseOperation.editorderRecordInDB(orderCode);
    }
     
    public String updateorderDetails(OrderBean updateorderObj) {
        return OrderDatabaseOperation.updateorderDetailsInDB(updateorderObj);
    }
     
    public String deleteorderRecord(int orderCode) {
        return OrderDatabaseOperation.deleteorderRecordInDB(orderCode);
    }
}
