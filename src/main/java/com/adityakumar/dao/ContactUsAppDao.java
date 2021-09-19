package com.adityakumar.dao;

import com.adityakumar.model.Contact;
import com.adityakumar.model.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


    public List<Request> getAllRequests(){
        Request  request;
        List<Request> requests = new ArrayList<>();
        Connection conn;
        ResultSet   rs;

        //Connect to database
        try {
            Class.forName("org.postgresql.Driver");
            conn=DriverManager.getConnection(CONN_STRING, USERNAME,PASSWORD);

            String searchQuery = "SELECT * FROM contacts";
            Statement st = conn.createStatement();
            rs= st.executeQuery(searchQuery);

            while (rs.next()){
                request = new Request();
                request.setFullName(rs.getString(1));
                request.setEmail(rs.getString(2));
                request.setMessage(rs.getString(3));
                request.setRequestDate(rs.getString(4));
                request.setActive(rs.getBoolean(5));

                requests.add(request);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return  requests;
    }

    public boolean updateContact(String contactDate, boolean  contactState) {
        boolean isUpdated = false;
        Connection connection=null;

        try{
            //Connect to database
            Class.forName("org.postgresql.Driver");
            connection=DriverManager.getConnection(CONN_STRING, USERNAME,PASSWORD);
            //Write sql query to update contacts table
            String updateQuery = "update contacts  set  is_active = ?  where contact_date = ?;";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setBoolean(1, contactState);
            statement.setString(2,contactDate);


            isUpdated= statement.executeUpdate()>0;

        }catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return  isUpdated;
    }

}
