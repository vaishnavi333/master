package com.jsf.crud.dboperation;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
 
import javax.faces.context.FacesContext;
 
import com.jsf.crud.VendorBean;
 
public class VendorDatabaseOperation {
 
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
 
    public static Connection getConnection(){  
        try{  
            Class.forName("com.mysql.jdbc.Driver");     
            String db_url ="jdbc:mysql://127.0.0.1:3306/project",
                    db_userName = "root",
                    db_password = "password";
            connObj = DriverManager.getConnection(db_url,db_userName,db_password);  
        } catch(Exception sqlException) {  
            sqlException.printStackTrace();
        }  
        return connObj;
    }
 
    public static ArrayList getvendorsListFromDB() {
        ArrayList vendorsList = new ArrayList();  
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from vendor");    
            while(resultSetObj.next()) {  
                VendorBean stuObj = new VendorBean(); 
                stuObj.setId(resultSetObj.getInt("vendor_id"));  
                stuObj.setName(resultSetObj.getString("vendor_name"));  
                stuObj.setEmail(resultSetObj.getString("vendor_email"));  
                 
                stuObj.setContact(resultSetObj.getString("vendor_contact"));  
                stuObj.setAddress(resultSetObj.getString("vendor_address"));  
                vendorsList.add(stuObj);  
            }   
            System.out.println("Total Records Fetched: " + vendorsList.size());
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        } 
        return vendorsList;
    }
 
    public static String savevendorDetailsInDB(VendorBean newvendorObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {      
            pstmt = getConnection().prepareStatement("insert into vendor (vendor_id, vendor_name, vendor_email, vendor_contact, vendor_address) values (?, ?, ?, ?, ?)");         
            pstmt.setInt(1, newvendorObj.getId());
            pstmt.setString(2, newvendorObj.getName());
            pstmt.setString(3, newvendorObj.getEmail());
            pstmt.setString(4, newvendorObj.getContact());
            pstmt.setString(5, newvendorObj.getAddress());
            saveResult = pstmt.executeUpdate();
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        if(saveResult !=0) {
            navigationResult = "vendorList.xhtml?faces-redirect=true";
        } else {
            navigationResult = "createVendor.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }
 
    public static String editvendorRecordInDB(int vendorId) {
        VendorBean editRecord = null;
        System.out.println("editvendorRecordInDB() : vendor Id: " + vendorId);
 
        /* Setting The Particular vendor Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
 
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from vendor where vendor_id = "+vendorId);    
            if(resultSetObj != null) {
                resultSetObj.next();
                editRecord = new VendorBean(); 
                editRecord.setId(resultSetObj.getInt("vendor_id"));
                editRecord.setName(resultSetObj.getString("vendor_name"));
                editRecord.setEmail(resultSetObj.getString("vendor_email"));
                editRecord.setContact(resultSetObj.getString("vendor_contact"));
                editRecord.setAddress(resultSetObj.getString("vendor_address"));
               
            }
            sessionMapObj.put("editRecordObj", editRecord);
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/editVendor.xhtml?faces-redirect=true";
    }
 
    public static String updatevendorDetailsInDB(VendorBean updatevendorObj) {
        try {
            pstmt = getConnection().prepareStatement("update vendor set vendor_name=?, vendor_email=?, vendor_contact=?, vendor_address=? where vendor_id=?");    
            pstmt.setString(1,updatevendorObj.getName());  
            pstmt.setString(2,updatevendorObj.getEmail());  
            pstmt.setString(3,updatevendorObj.getContact());  
            pstmt.setString(4,updatevendorObj.getAddress());
            pstmt.setInt(5,updatevendorObj.getId());
             
            pstmt.executeUpdate();
            connObj.close();            
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/vendorList.xhtml?faces-redirect=true";
    }
 
    public static String deletevendorRecordInDB(int vendorId){
        System.out.println("deletevendorRecordInDB() : vendor Id: " + vendorId);
        try {
            pstmt = getConnection().prepareStatement("delete from vendor where vendor_id = "+vendorId);  
            pstmt.executeUpdate();  
            connObj.close();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/vendorList.xhtml?faces-redirect=true";
    }
}

