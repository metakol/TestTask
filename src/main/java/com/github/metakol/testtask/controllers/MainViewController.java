package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MainViewController  {

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

    @FXML
    void goDepartmentsInfoClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/info-about-departments.fxml"));
    }

    @FXML
    void goEmployeesInfoClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/full-info-about-employees.fxml"));
    }

    @FXML
    void goEmployeesSalaryInfoClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/info-about-employees-salary.fxml"));
    }

    @FXML
    void goStaffingTableInfoClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/info-about-staffing-table.fxml"));
    }
}