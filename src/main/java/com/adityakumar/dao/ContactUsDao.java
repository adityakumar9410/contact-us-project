package com.adityakumar.dao;

import com.adityakumar.model.User;
import com.adityakumar.model.Request;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ContactUsDao {
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Malak9410";
    private static final String CONN_STRING = "jdbc:postgresql://localhost:5432/contactsdb";

    private static final int CONTACT_NAME = 1;
    private static final int CONTACT_EMAIL = 2;
    private static final int CONTACT_MSG = 3;
    private static final int CONTACT_DATE = 4;
    private static final int CONTACT_STATUS = 5;

    private static String CONTACT_INSERT_QUERY = "INSERT  INTO  contacts(full_name, email, message, contact_date, is_active) VALUES(?,?,?,?,?)";

    public int saveContact(Request request) {
        PreparedStatement statement;
        int rowsAdded = 0;

        try {
            Connection conn = getConnectionToDb();
            statement = conn.prepareStatement(CONTACT_INSERT_QUERY);
            statement.setString(CONTACT_NAME, request.getFullName());
            statement.setString(CONTACT_EMAIL, request.getEmail());
            statement.setString(CONTACT_MSG, request.getMessage());
            String contactDate = getContactDate();
            statement.setString(CONTACT_DATE, contactDate);
            statement.setBoolean(CONTACT_STATUS, true);
            rowsAdded = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAdded;
    }

    private String getContactDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm");
        Date date = Calendar.getInstance().getTime();
        return dateFormat.format(date);
    }

    private Connection getConnectionToDb() {
        Connection conn = null;
        //Connect to database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
