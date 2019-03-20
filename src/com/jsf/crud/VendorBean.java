package com.jsf.crud;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.jsf.crud.dboperation.VendorDatabaseOperation;
 
@ManagedBean @RequestScoped
public class VendorBean {
 
    private int id;  
    private String name;  
    private String email;  
    private String contact;  
   private String address;
 
    public ArrayList vendorsListFromDB;
 
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
 
    public String getContact() {
        return contact;
    }
 
    public void setContact(String contact) {
        this.contact = contact;
    }
 
   
    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }  
     
    @PostConstruct
    public void init() {
        vendorsListFromDB = VendorDatabaseOperation.getvendorsListFromDB();
    }
 
    public ArrayList vendorList() {
        return vendorsListFromDB;
    }
     
    public String savevendorDetails(VendorBean newvendorObj) {
        return VendorDatabaseOperation.savevendorDetailsInDB(newvendorObj);
    }
     
    public String editvendorRecord(int vendorId) {
        return VendorDatabaseOperation.editvendorRecordInDB(vendorId);
    }
     
    public String updatevendorDetails(VendorBean updatevendorObj) {
        return VendorDatabaseOperation.updatevendorDetailsInDB(updatevendorObj);
    }
     
    public String deletevendorRecord(int vendorId) {
        return VendorDatabaseOperation.deletevendorRecordInDB(vendorId);
    }
}
