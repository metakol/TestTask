package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.DBHandler.DBHandler;
import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    void goChangeDepartmentsClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/change-departments.fxml"));
    }

    @FXML
    void goChangeEmployeesClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/change-employees.fxml"));
    }

    @FXML
    void goChangePositionsClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/change-positions.fxml"));
    }

    @FXML
    void goChangeStaffingTableClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/change-staffing-table.fxml"));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query = "select * from job_positions";
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            ObservableList<String> list = FXCollections.observableArrayList();
            while (resultSet.next()) {
                list.add(resultSet.getString(2));

            }
           System.out.println(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}