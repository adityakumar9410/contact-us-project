package com.adityakumar.dao;

import com.adityakumar.model.User;

import java.sql.*;

public class LoginDao {
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Malak9410";
    private static final String CONN_STRING = "jdbc:postgresql://localhost:5432/contactsdb";

    private static final int ADMIN_USERNAME = 1;
    private static final int ADMIN_PASSWORD = 2;

    private static String ADMIN_SEARCH_QUERY = "SELECT * FROM admin WHERE  username=? AND password=?";

    public boolean validateUser(User user) {
        boolean isValidAdmin = false;

        try {
            Connection connection = getConnectionToDb();
            PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_SEARCH_QUERY);
            //Set parameter with current admin
            preparedStatement.setString(ADMIN_USERNAME, user.getUsername());
            preparedStatement.setString(ADMIN_PASSWORD, user.getPassword());
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
