package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.DBHandler.DBHandler;
import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.entity.Employee;
import com.github.metakol.testtask.entity.EmployeeWithSalary;
import com.github.metakol.testtask.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EmployeesSalaryInfoController implements Initializable {
    @FXML
    private TableView<EmployeeWithSalary> table;
    @FXML
    private TableColumn<EmployeeWithSalary, String> columnFIO;
    @FXML
    private TableColumn<EmployeeWithSalary, String> columnDepartment;
    @FXML
    private TableColumn<EmployeeWithSalary, String> columnPosition;
    @FXML
    private TableColumn<EmployeeWithSalary, Integer> columnSalary;

    ObservableList<EmployeeWithSalary> list = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        updateTable();
    }

    private void initTable() {
        columnFIO.setCellValueFactory(new PropertyValueFactory<>("FIO"));
        columnDepartment.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        table.setItems(list);
    }

    private void updateTable() {
        list.clear();
        String sql = "SELECT FIO,department_name,position_name,salary " +
                "FROM employees " +
                "INNER JOIN departments ON id_department=departments.id " +
                "INNER JOIN job_positions ON id_position=job_positions.id " +
                "WHERE FIO LIKE '%" + searchField.getText().trim() + "%'";
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)
        ) {
            fillListForTable(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillListForTable(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String FIO = resultSet.getString(1);
            String departmentName = resultSet.getString(2);
            String positionName = resultSet.getString(3);
            int salary = resultSet.getInt(4);
            list.add(new EmployeeWithSalary(FIO, departmentName, positionName, salary));
        }
    }

    @FXML
    void onGoBackClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/main-view.fxml"));
    }

    @FXML
    void onTypeKeyForSearch(KeyEvent event) {
        updateTable();
    }
}
