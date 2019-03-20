package com.jsf.crud.dboperation;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
 
import javax.faces.context.FacesContext;
 
import com.jsf.crud.SalesBean;
 
public class SalesDatabaseOperation 
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
 
    public static ArrayList getsalesListFromDB() {
        ArrayList salesList = new ArrayList();  
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from sales");    
            while(resultSetObj.next()) {  
                SalesBean stuObj = new SalesBean(); 
                stuObj.setCustomerid(resultSetObj.getInt("customer_id"));  
                stuObj.setVendorid(resultSetObj.getInt("vendor_id"));  
                stuObj.setDate_order(resultSetObj.getString("date_order"));  
                stuObj.setDate_reaching(resultSetObj.getString("date_reaching"));  
                 
                salesList.add(stuObj);  
            }   
            System.out.println("Total Records Fetched: " + salesList.size());
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        } 
        return salesList;
    }
 
    public static String savesalesDetailsInDB(SalesBean newsalesObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {      
            pstmt = getConnection().prepareStatement("insert into sales (customer_id, vendor_id, date_order, date_reaching) values (?, ?, ?, ?)");         
            pstmt.setInt(1, newsalesObj.getCustomerid());
            pstmt.setInt(2, newsalesObj.getVendorid());
            pstmt.setString(3, newsalesObj.getDate_order());
            pstmt.setString(4, newsalesObj.getDate_reaching());
            saveResult = pstmt.executeUpdate();
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        if(saveResult !=0) {
            navigationResult = "salesList.xhtml?faces-redirect=true";
        } else {
            navigationResult = "createSales.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }
 
    public static String editsalesRecordInDB(int salesCode) {
        SalesBean editRecord = null;
        System.out.println("editsalesRecordInDB() : customer_id " + salesCode);
 
        /* Setting The Particular sales Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
 
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from sales where customer_id = "+salesCode);    
            if(resultSetObj != null) {
                resultSetObj.next();
                editRecord = new SalesBean(); 
                editRecord.setCustomerid(resultSetObj.getInt("customer_id"));
                editRecord.setVendorid(resultSetObj.getInt("vendor_id"));
                editRecord.setDate_order(resultSetObj.getString("date_order"));
                editRecord.setDate_reaching(resultSetObj.getString("date_reaching"));
               
                
               
            }
            sessionMapObj.put("editRecordObj", editRecord);
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/editSales.xhtml?faces-redirect=true";
    }
 
    public static String updatesalesDetailsInDB(SalesBean updatesalesObj) {
        try {
            pstmt = getConnection().prepareStatement("update sales set vendor_id=?, date_order=?, date_reaching=? where customer_id=?");    
            
            pstmt.setInt(4,updatesalesObj.getCustomerid());  
            pstmt.setInt(1,updatesalesObj.getVendorid());  
            pstmt.setString(2,updatesalesObj.getDate_order());  
            pstmt.setString(3,updatesalesObj.getDate_reaching());  
            pstmt.executeUpdate();
            connObj.close();            
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/salesList.xhtml?faces-redirect=true";
    }
 
    public static String deletesalesRecordInDB(int salesCode){
        System.out.println("deletesalesRecordInDB() : sales Id: " + salesCode);
        try {
            pstmt = getConnection().prepareStatement("delete from sales where customer_id = "+salesCode);  
            pstmt.executeUpdate();  
            connObj.close();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/salesList.xhtml?faces-redirect=true";
    }
}


