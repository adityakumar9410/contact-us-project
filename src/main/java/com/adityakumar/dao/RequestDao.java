package com.adityakumar.dao;

import com.adityakumar.model.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDao {
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Malak9410";
    private static final String CONN_STRING = "jdbc:postgresql://localhost:5432/contactsdb";

    private static final int REQUEST_ID = 1;
    private static final int REQUEST_NAME = 2;
    private static final int REQUEST_EMAIL = 3;
    private static final int REQUEST_MSG = 4;
    private static final int REQUEST_DATE = 5;
    private static final int REQUEST_STATUS = 6;

    private static final int UPDATE_STATUS_ID = 1;
    private static final int UPDATE_REQUEST_ID = 2;

    private static String REQUEST_SEARCH_QUERY = "SELECT * FROM contacts";
    private static String UPDATE_CONTACT_QUERY = "UPDATE contacts  SET is_active = ?  WHERE contact_id = ?";

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

    public boolean updateRequest(int requestId, boolean isActive) {
        boolean isUpdated = false;
        try {
            Connection connection = getConnectionToDb();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT_QUERY);
            statement.setBoolean(UPDATE_STATUS_ID, isActive);
            statement.setInt(UPDATE_REQUEST_ID, requestId);
            isUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    private Connection getConnectionToDb() {
        Connection connection = null;
        //Connect to database
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
