package com.github.metakol.testtask.DBHandler;

import java.sql.*;

public class DBHandler implements AutoCloseable {
    private static String nameConnection="org.sqlite.JDBC";
    private static String DBPath="jdbc:sqlite:src/main/resources/com/github/metakol/testtask/db/systemdb.db";
    private Connection connection;

    public DBHandler(){
        try {
            Class.forName(nameConnection);
            connection=DriverManager.getConnection(DBPath);
            System.out.println("DB open");
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
            System.out.println("DB close");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }
}