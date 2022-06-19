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
}