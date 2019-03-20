package com.jsf.crud.dboperation;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
 
import javax.faces.context.FacesContext;
 
import com.jsf.crud.ProductBean;
 
public class ProductDatabaseOperation 
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
 
    public static ArrayList getproductsListFromDB() {
        ArrayList productsList = new ArrayList();  
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from product");    
            while(resultSetObj.next()) {  
                ProductBean stuObj = new ProductBean(); 
                stuObj.setCode(resultSetObj.getInt("product_id"));  
                stuObj.setName(resultSetObj.getString("product_code"));  
                stuObj.setType(resultSetObj.getString("product_type"));  
                stuObj.setPrice(resultSetObj.getInt("product_price"));  
                stuObj.setShipping(resultSetObj.getString("shipping"));  
                productsList.add(stuObj);  
            }   
            System.out.println("Total Records Fetched: " + productsList.size());
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        } 
        return productsList;
    }
 
    public static String saveproductDetailsInDB(ProductBean newproductObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {      
            pstmt = getConnection().prepareStatement("insert into product (product_id, product_code, product_type, product_price, shipping) values (?, ?, ?, ?,?)");         
            pstmt.setInt(1, newproductObj.getCode());
            pstmt.setString(2, newproductObj.getName());
            pstmt.setString(3, newproductObj.getType());
            pstmt.setInt(4, newproductObj.getPrice());
            pstmt.setString(5, newproductObj.getShipping());
            saveResult = pstmt.executeUpdate();
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        if(saveResult !=0) {
            navigationResult = "productList.xhtml?faces-redirect=true";
        } else {
            navigationResult = "createProduct.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }
 
    public static String editproductRecordInDB(int productId) {
        ProductBean editRecord = null;
        System.out.println("editproductRecordInDB() : product Id: " + productId);
 
        /* Setting The Particular product Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
 
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from product where product_id = "+productId);    
            if(resultSetObj != null) {
                resultSetObj.next();
                editRecord = new ProductBean(); 
                editRecord.setCode(resultSetObj.getInt("product_id"));
                editRecord.setName(resultSetObj.getString("product_code"));
                editRecord.setType(resultSetObj.getString("product_type"));
                editRecord.setPrice(resultSetObj.getInt("product_price"));
                editRecord.setShipping(resultSetObj.getString("shipping"));
                
               
            }
            sessionMapObj.put("editRecordObj", editRecord);
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/editProduct.xhtml?faces-redirect=true";
    }
 
    public static String updateproductDetailsInDB(ProductBean updateproductObj) {
        try {
            pstmt = getConnection().prepareStatement("update product set product_code=?, product_type=?, product_price=?, shipping=? where product_id=?");    
            
            pstmt.setInt(5,updateproductObj.getCode());  
            pstmt.setString(1,updateproductObj.getName()); 
            pstmt.setString(2,updateproductObj.getType()); 
            pstmt.setInt(3,updateproductObj.getCode()); 
            pstmt.setString(4,updateproductObj.getShipping()); 
                       pstmt.executeUpdate();
            connObj.close();            
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/productList.xhtml?faces-redirect=true";
    }
 
    public static String deleteproductRecordInDB(int productId){
        System.out.println("deleteproductRecordInDB() : product Id: " + productId);
        try {
            pstmt = getConnection().prepareStatement("delete from product where product_id = "+productId);  
            pstmt.executeUpdate();  
            connObj.close();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/productList.xhtml?faces-redirect=true";
    }
}

