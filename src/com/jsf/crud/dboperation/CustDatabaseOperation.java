package com.jsf.crud.dboperation;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
 
import javax.faces.context.FacesContext;
 
import com.jsf.crud.CustomerBean;
 
public class CustDatabaseOperation {
 
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
 
    public static ArrayList getCustomersListFromDB() {
        ArrayList customersList = new ArrayList();  
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from customer");    
            while(resultSetObj.next()) {  
                CustomerBean stuObj = new CustomerBean(); 
                stuObj.setId(resultSetObj.getInt("customer_id"));  
                stuObj.setName(resultSetObj.getString("customer_name"));  
                stuObj.setEmail(resultSetObj.getString("customer_email"));  
                stuObj.setPassword(resultSetObj.getString("customer_contact"));  
                stuObj.setGender(resultSetObj.getString("customer_gender"));  
                stuObj.setAddress(resultSetObj.getString("customer_address"));  
                customersList.add(stuObj);  
            }   
            System.out.println("Total Records Fetched: " + customersList.size());
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        } 
        return customersList;
    }
 
    public static String saveCustomerDetailsInDB(CustomerBean newCustomerObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {      
            pstmt = getConnection().prepareStatement("insert into customer (customer_name, customer_email, customer_contact, customer_gender, customer_address) values (?, ?, ?, ?, ?)");         
            pstmt.setString(1, newCustomerObj.getName());
            pstmt.setString(2, newCustomerObj.getEmail());
            pstmt.setString(3, newCustomerObj.getPassword());
            pstmt.setString(4, newCustomerObj.getGender());
            pstmt.setString(5, newCustomerObj.getAddress());
            saveResult = pstmt.executeUpdate();
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        if(saveResult !=0) {
            navigationResult = "customerList.xhtml?faces-redirect=true";
        } else {
            navigationResult = "createCustomer.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }
 
    public static String editCustomerRecordInDB(int customerId) {
        CustomerBean editRecord = null;
        System.out.println("editCustomerRecordInDB() : Customer Id: " + customerId);
 
        /* Setting The Particular Customer Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
 
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from customer where customer_id = "+customerId);    
            if(resultSetObj != null) {
                resultSetObj.next();
                editRecord = new CustomerBean(); 
                editRecord.setId(resultSetObj.getInt("customer_id"));
                editRecord.setName(resultSetObj.getString("customer_name"));
                editRecord.setEmail(resultSetObj.getString("customer_email"));
                editRecord.setGender(resultSetObj.getString("customer_gender"));
                editRecord.setAddress(resultSetObj.getString("customer_address"));
                editRecord.setPassword(resultSetObj.getString("customer_contact")); 
            }
            sessionMapObj.put("editRecordObj", editRecord);
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/editCustomer.xhtml?faces-redirect=true";
    }
 
    public static String updateCustomerDetailsInDB(CustomerBean updateCustomerObj) {
        try {
            pstmt = getConnection().prepareStatement("update customer set customer_name=?, customer_email=?, customer_contact=?, customer_gender=?, customer_address=? where customer_id=?");    
            pstmt.setString(1,updateCustomerObj.getName());  
            pstmt.setString(2,updateCustomerObj.getEmail());  
            pstmt.setString(3,updateCustomerObj.getPassword());  
            pstmt.setString(4,updateCustomerObj.getGender());  
            pstmt.setString(5,updateCustomerObj.getAddress());  
            pstmt.setInt(6,updateCustomerObj.getId());  
            pstmt.executeUpdate();
            connObj.close();            
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/customersList.xhtml?faces-redirect=true";
    }
 
    public static String deleteCustomerRecordInDB(int customerId){
        System.out.println("deleteCustomerRecordInDB() : Customer Id: " + customerId);
        try {
            pstmt = getConnection().prepareStatement("delete from customer where customer_id = "+customerId);  
            pstmt.executeUpdate();  
            connObj.close();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/customersList.xhtml?faces-redirect=true";
    }
}

