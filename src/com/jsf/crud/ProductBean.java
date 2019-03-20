package com.jsf.crud;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 
import com.jsf.crud.dboperation.ProductDatabaseOperation;
 
@ManagedBean @RequestScoped
public class ProductBean {
 
    private int code;  
    private String name;  
    private String type;  
    private int price; 
    private String shipping; 
  
	public ArrayList productsListFromDB;
 
    public int getCode() {
        return code;  
    }
 
    public void setCode(int code) {
        this.code = code;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
 
    public void setType(String type) {
        this.type = type;
    }
    public int getPrice() {
        return price;  
    }
 
    public void setPrice(int price) {
        this.price = price;
    }
    public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

 
   
     
    @PostConstruct
    public void init() {
        productsListFromDB = ProductDatabaseOperation.getproductsListFromDB();
    }
 
    public ArrayList productList() {
        return productsListFromDB;
    }
     
    public String saveproductDetails(ProductBean newproductObj) {
        return ProductDatabaseOperation.saveproductDetailsInDB(newproductObj);
    }
     
    public String editproductRecord(int productId) {
        return ProductDatabaseOperation.editproductRecordInDB(productId);
    }
     
    public String updateproductDetails(ProductBean updateproductObj) {
        return ProductDatabaseOperation.updateproductDetailsInDB(updateproductObj);
    }
     
    public String deleteproductRecord(int productId) {
        return ProductDatabaseOperation.deleteproductRecordInDB(productId);
    }
}
