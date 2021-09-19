package com.adityakumar.dao;

import com.adityakumar.model.Contact;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactUsAppDao {
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Malak9410";
    private static final String CONN_STRING = "jdbc:postgresql://localhost:5432/contactsdb";

    private static String insertQuery = "INSERT  INTO  contacts VALUES(?,?,?)";

    public  int addContactToDb(Contact contact){
        Connection conn=null;
        int rowsAdded =0;
        try {
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection(CONN_STRING, USERNAME,PASSWORD);
            PreparedStatement statement  = conn.prepareStatement(insertQuery);
            statement.setString(1, contact.getFullName());
            statement.setString(2, contact.getEmail());
            statement.setString(3, contact.getMessage());

            rowsAdded = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return  rowsAdded;
    }



}
