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

    private static final int REQUEST_ID = 1;
    private static final int REQUEST_NAME = 2;
    private static final int REQUEST_EMAIL = 3;
    private static final int REQUEST_MSG = 4;
    private static final int REQUEST_DATE = 5;
    private static final int REQUEST_STATUS = 6;

    private static final int ADMIN_USERNAME = 1;
    private static final int ADMIN_PASSWORD = 2;

    private static final int UPDATE_STATUS_ID = 1;
    private static final int UPDATE_ID = 2;

    private static String CONTACT_INSERT_QUERY = "INSERT  INTO  contacts(full_name, email, message, contact_date, is_active) VALUES(?,?,?,?,?)";
    private static String ADMIN_SEARCH_QUERY = "SELECT * FROM admin WHERE  username=? AND password=?";
    private static String REQUEST_SEARCH_QUERY = "SELECT * FROM contacts";
    private static String UPDATE_CONTACT_QUERY = "UPDATE contacts  SET is_active = ?  WHERE contact_id = ?";

    public int addContactToDb(Contact contact) {
        PreparedStatement statement;
        int rowsAdded = 0;

        try {
            Connection conn = getConnectionToDb();
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

        try {
            Connection connection = getConnectionToDb();
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
        }
        return isValidAdmin;
    }

    public List<Request> getAllRequests() {
        Request request;
        List<Request> requests = new ArrayList<>();
        ResultSet rs;
        try {
            Connection conn = getConnectionToDb();
            Statement st = conn.createStatement();
            rs = st.executeQuery(REQUEST_SEARCH_QUERY);
            while (rs.next()) {
                request = new Request();
                request.setRequestId(rs.getInt(REQUEST_ID));
                request.setFullName(rs.getString(REQUEST_NAME));
                request.setEmail(rs.getString(REQUEST_EMAIL));
                request.setMessage(rs.getString(REQUEST_MSG));
                request.setRequestDate(rs.getString(REQUEST_DATE));
                request.setActive(rs.getBoolean(REQUEST_STATUS));
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public boolean updateContact(int contactId, boolean contactState) {
        boolean isUpdated = false;

        try {
            Connection connection = getConnectionToDb();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT_QUERY);
            statement.setBoolean(UPDATE_STATUS_ID, contactState);
            statement.setInt(UPDATE_ID, contactId);
            isUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
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
