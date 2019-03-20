package com.jsf.crud;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 
import com.jsf.crud.dboperation.CustDatabaseOperation;
 
@ManagedBean @RequestScoped
public class CustomerBean {
 
    private int id;  
    private String name;  
    private String email;  
    private String password;  
    private String gender;  
    private String address;
 
    public ArrayList customersListFromDB;
 
    public int getId() {
        return id;  
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getGender() {
        return gender;
    }
 
    public void setGender(String gender) {
        this.gender = gender;
    }
 
    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }  
     
    @PostConstruct
    public void init() {
        customersListFromDB = CustDatabaseOperation.getCustomersListFromDB();
    }
 
    public ArrayList customerList() {
        return customersListFromDB;
    }
     
    public String savecustomerDetails(CustomerBean newcustomerObj) {
        return CustDatabaseOperation.saveCustomerDetailsInDB(newcustomerObj);
    }
     
    public String editcustomerRecord(int customerId) {
        return CustDatabaseOperation.editCustomerRecordInDB(customerId);
    }
     
    public String updatecustomerDetails(CustomerBean updatecustomerObj) {
        return CustDatabaseOperation.updateCustomerDetailsInDB(updatecustomerObj);
    }
     
    public String deletecustomerRecord(int customerId) {
        return CustDatabaseOperation.deleteCustomerRecordInDB(customerId);
    }
}
