package com.adityakumar.dao;

import com.adityakumar.model.Contact;

import java.sql.*;

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

    public  boolean validateAdmin(String userName , String password){
        boolean isValidAdmin=false;
        Connection connection=null;

        try{
            //Connect to database
            Class.forName("org.postgresql.Driver");
            connection=DriverManager.getConnection(CONN_STRING, USERNAME,PASSWORD);
            //Write sql query to get admin data
            String sql = "SELECT * FROM admin WHERE  username=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //Set parameter with current admin
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2, password);

            //Execute the statement and check whether admin exists or not

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                isValidAdmin=true;
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return  isValidAdmin;
    }


}
