package com.jsf.crud.dboperation;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
 
import javax.faces.context.FacesContext;
 
import com.jsf.crud.InventoryBean;
 
public class InventoryDatabaseOperation 
{
 
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
 
    public static ArrayList getInventoryListFromDB() {
        ArrayList InventoryList = new ArrayList();  
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from inventory");    
            while(resultSetObj.next()) {  
                InventoryBean stuObj = new InventoryBean(); 
                stuObj.setUnitstock(resultSetObj.getInt("units_stock"));  
                stuObj.setUnitprice(resultSetObj.getInt("unit_price"));  
                stuObj.setSerialno(resultSetObj.getString("serial_no"));  
                stuObj.setVendorid(resultSetObj.getInt("vendor_id"));  
                InventoryList.add(stuObj);  
            }   
            System.out.println("Total Records Fetched: " + InventoryList.size());
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        } 
        return InventoryList;
    }
 
    public static String saveInventoryDetailsInDB(InventoryBean newInventoryObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {      
            pstmt = getConnection().prepareStatement("insert into Inventory (units_stock, unit_price ,serial_no ,vendor_id) values (?, ?, ?, ?)");         
            pstmt.setInt(1, newInventoryObj.getUnitstock());
            pstmt.setInt(2, newInventoryObj.getUnitprice());
            pstmt.setString(3, newInventoryObj.getSerialno());
            pstmt.setInt(4, newInventoryObj.getVendorid());
            saveResult = pstmt.executeUpdate();
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        if(saveResult !=0) {
            navigationResult = "inventoryList.xhtml?faces-redirect=true";
        } else {
            navigationResult = "createInventory.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }
 
    public static String editInventoryRecordInDB(int InventoryCode) {
        InventoryBean editRecord = null;
        System.out.println("editInventoryRecordInDB() : Inventory Id: " + InventoryCode);
 
        /* Setting The Particular Inventory Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
 
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from Inventory where vendor_id = "+InventoryCode);    
            if(resultSetObj != null) {
                resultSetObj.next();
                editRecord = new InventoryBean(); 
                editRecord.setUnitstock(resultSetObj.getInt("units_stock"));
                editRecord.setUnitprice(resultSetObj.getInt("unit_price"));
                editRecord.setSerialno(resultSetObj.getString("serial_no"));
                editRecord.setVendorid(resultSetObj.getInt("vendor_id"));
                
               
            }
            sessionMapObj.put("editRecordObj", editRecord);
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/editInventory.xhtml?faces-redirect=true";
    }
 
    public static String updateInventoryDetailsInDB(InventoryBean updateInventoryObj) {
        try {
            pstmt = getConnection().prepareStatement("update Inventory set units_stock=?, unit_price=?, serial_no=? where vendor_id=?");    
            
            pstmt.setInt(1,updateInventoryObj.getUnitstock());  
            pstmt.setInt(2,updateInventoryObj.getUnitprice());  
            pstmt.setString(3,updateInventoryObj.getSerialno());  
            pstmt.setInt(4,updateInventoryObj.getVendorid());  
            
           
                       pstmt.executeUpdate();
            connObj.close();            
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/inventoryList.xhtml?faces-redirect=true";
    }
 
    public static String deleteInventoryRecordInDB(int InventoryCode){
        System.out.println("deleteInventoryRecordInDB() : Inventory Id: " + InventoryCode);
        try {
            pstmt = getConnection().prepareStatement("delete from Inventory where vendor_id = "+InventoryCode);  
            pstmt.executeUpdate();  
            connObj.close();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/inventoryList.xhtml?faces-redirect=true";
    }
}

