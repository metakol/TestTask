package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.DBHandler.DBHandler;
import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainViewController {

    @FXML
    void goChangeDepartmentsClick(MouseEvent event) {

    }

    @FXML
    void goChangeEmployeesClick(MouseEvent event) {

    }

    @FXML
    void goChangePositionsClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/change-positions.fxml"));
    }

    @FXML
    private TextField field;

    @FXML
    void onType(KeyEvent event) {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.getStatement();
        ) {
            String sql = "SELECT * FROM positions WHERE title LIKE '%"+field.getText().trim()+"%'";
            System.out.println(sql);
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(2) + " " + resultSet.getInt(3));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showInfoFromDB() {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.getStatement();
        ) {
            String sql = "select * from employees";
            System.out.println(sql);
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4) + " " + resultSet.getString(5));
                }
            }

            sql = "select * from departments";
            System.out.println(sql);
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4));
                }
            }

            sql = "select * from positions";
            System.out.println(sql);
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}