package com.adityakumar.dao;

import com.adityakumar.model.Contact;
import com.adityakumar.model.Request;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ContactUsAppDao {
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Malak9410";
    private static final String CONN_STRING = "jdbc:postgresql://localhost:5432/contactsdb";

    private static final int CONTACT_NAME = 1;
    private static final int CONTACT_EMAIL = 2;
    private static final int CONTACT_MSG = 3;
    private static final int CONTACT_DATE = 4;
    private static final int CONTACT_STATUS = 5;

    private static final int ADMIN_USERNAME = 1;
    private static final int ADMIN_PASSWORD = 2;

    private static String CONTACT_INSERT_QUERY = "INSERT  INTO  contacts VALUES(?,?,?,?,?)";
    private static String ADMIN_SEARCH_QUERY = "SELECT * FROM admin WHERE  username=? AND password=?";
    private static String CONTACTS_SEARCH_QUERY = "SELECT * FROM contacts";
    private static String UPDATE_CONTACT_QUERY = "UPDATE contacts  SET is_active = ?  WHERE contact_date = ?";

    public int addContactToDb(Contact contact) {
        Connection conn;
        PreparedStatement statement;
        int rowsAdded = 0;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            statement = conn.prepareStatement(CONTACT_INSERT_QUERY);
            statement.setString(CONTACT_NAME, contact.getFullName());
            statement.setString(CONTACT_EMAIL, contact.getEmail());
            statement.setString(CONTACT_MSG, contact.getMessage());
            String contactDate = getContactDate();
            statement.setString(CONTACT_DATE, contactDate);
            statement.setBoolean(CONTACT_STATUS, true);
            rowsAdded = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rowsAdded;
    }

    private String getContactDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm");
        Date date = Calendar.getInstance().getTime();
        return dateFormat.format(date);
    }

    public boolean validateAdmin(String username, String password) {
        boolean isValidAdmin = false;
        Connection connection = null;

        try {
            //Connect to database
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_SEARCH_QUERY);
            //Set parameter with current admin
            preparedStatement.setString(ADMIN_USERNAME, username);
            preparedStatement.setString(ADMIN_PASSWORD, password);
            //Execute the statement and check whether admin exists or not
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isValidAdmin = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isValidAdmin;
    }

    public List<Request> getAllRequests() {
        Request request;
        List<Request> requests = new ArrayList<>();
        Connection conn;
        ResultSet rs;
        //Connect to database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

            Statement st = conn.createStatement();
            rs = st.executeQuery(CONTACTS_SEARCH_QUERY);
            while (rs.next()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public boolean updateContact(String contactDate, boolean contactState) {
        boolean isUpdated = false;
        Connection connection = null;

        try {
            //Connect to database
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT_QUERY);
            statement.setBoolean(1, contactState);
            statement.setString(2, contactDate);
            isUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }
}
