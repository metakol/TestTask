package com.github.metakol.testtask.DBHandler;

import java.sql.*;
import java.util.Properties;

public class DBHandler implements AutoCloseable {
    private static String nameConnection = "org.firebirdsql.jdbc.FBDriver";
    private static String DBPath = "jdbc:firebirdsql://localhost:3050/D:/GIT/TestTask/src/main/resources/com/github/metakol/testtask/db/systemdb.FDB";
    private Connection connection;

    public DBHandler() {
        try {
            Class.forName(nameConnection);
            Properties prop=new Properties();
            prop.put("user","SYSDBA");
            prop.put("password","masterkey");
            prop.put("characterEncoding","UTF-8");
            connection = DriverManager.getConnection(DBPath,prop);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public PreparedStatement preparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
