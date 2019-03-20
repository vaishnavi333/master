package com.jsf.crud.dboperation;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
 
import javax.faces.context.FacesContext;
 
import com.jsf.crud.OrderBean;
 
public class OrderDatabaseOperation 
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
 
    public static ArrayList getordersListFromDB() {
        ArrayList ordersList = new ArrayList();  
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from orderr");    
            while(resultSetObj.next()) {  
                OrderBean stuObj = new OrderBean(); 
                stuObj.setCode(resultSetObj.getInt("order_code"));  
                stuObj.setProductcode(resultSetObj.getString("product_code"));  
                stuObj.setQuantity(resultSetObj.getInt("order_quantity"));  
                stuObj.setCustomerid(resultSetObj.getInt("customer_id"));  
                stuObj.setVendorid(resultSetObj.getInt("vendor_id"));  
                ordersList.add(stuObj);  
            }   
            System.out.println("Total Records Fetched: " + ordersList.size());
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        } 
        return ordersList;
    }
 
    public static String saveorderDetailsInDB(OrderBean neworderObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {      
            pstmt = getConnection().prepareStatement("insert into orderr (order_code, product_code, order_quantity, customer_id, vendor_id) values (?, ?, ?, ?,?)");         
            pstmt.setInt(1, neworderObj.getCode());
            pstmt.setString(2, neworderObj.getProductcode());
            pstmt.setInt(3, neworderObj.getQuantity());
            pstmt.setInt(4, neworderObj.getCustomerid());
            pstmt.setInt(5, neworderObj.getVendorid());
            saveResult = pstmt.executeUpdate();
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        if(saveResult !=0) {
            navigationResult = "orderList.xhtml?faces-redirect=true";
        } else {
            navigationResult = "createOrder.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }
 
    public static String editorderRecordInDB(int orderCode) {
        OrderBean editRecord = null;
        System.out.println("editorderRecordInDB() : order Id: " + orderCode);
 
        /* Setting The Particular order Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
 
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from orderr where order_code = "+orderCode);    
            if(resultSetObj != null) {
                resultSetObj.next();
                editRecord = new OrderBean(); 
                editRecord.setCode(resultSetObj.getInt("order_code"));
                editRecord.setProductcode(resultSetObj.getString("product_code"));
                editRecord.setQuantity(resultSetObj.getInt("order_quantity"));
                editRecord.setCustomerid(resultSetObj.getInt("customer_id"));
                editRecord.setVendorid(resultSetObj.getInt("vendor_id"));
                
               
            }
            sessionMapObj.put("editRecordObj", editRecord);
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/editOrder.xhtml?faces-redirect=true";
    }
 
    public static String updateorderDetailsInDB(OrderBean updateorderObj) {
        try {
            pstmt = getConnection().prepareStatement("update orderr set product_code=?, order_quantity=?, customer_id=?, vendor_id=? where order_code=?");    
            
              
            pstmt.setString(1,updateorderObj.getProductcode());  
            pstmt.setInt(2,updateorderObj.getQuantity());  
            pstmt.setInt(3,updateorderObj.getCustomerid());  
            pstmt.setInt(4,updateorderObj.getVendorid()); 
            pstmt.setInt(5,updateorderObj.getCode());
            
           
                       pstmt.executeUpdate();
            connObj.close();            
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/orderList.xhtml?faces-redirect=true";
    }
 
    public static String deleteorderRecordInDB(int orderCode){
        System.out.println("deleteorderRecordInDB() : order Id: " + orderCode);
        try {
            pstmt = getConnection().prepareStatement("delete from orderr where order_code6 = "+orderCode);  
            pstmt.executeUpdate();  
            connObj.close();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/orderList.xhtml?faces-redirect=true";
    }
}

