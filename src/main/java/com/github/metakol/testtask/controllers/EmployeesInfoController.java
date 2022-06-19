package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.DBHandler.DBHandler;
import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.entity.Employee;
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

public class EmployeesInfoController implements Initializable {
    @FXML
    private TableView<Employee> table;
    @FXML
    private TableColumn<Employee, String> columnFIO;
    @FXML
    private TableColumn<Employee, String> columnDepartment;
    @FXML
    private TableColumn<Employee, String> columnPosition;
    @FXML
    private TableColumn<Employee, String> columnPhoneNumber;
    @FXML
    private TableColumn<Employee, String> columnPassport;

    ObservableList<Employee> list = FXCollections.observableArrayList();

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
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        columnPassport.setCellValueFactory(new PropertyValueFactory<>("passport"));
        table.setItems(list);
    }

    private void updateTable() {
        list.clear();
        String sql = "SELECT employees.id,FIO,department_name,position_name,personal_phone_number,passport FROM employees " +
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
            int id = resultSet.getInt(1);
            String FIO = resultSet.getString(2);
            String departmentName = resultSet.getString(3);
            String positionName = resultSet.getString(4);
            String phoneNumber = resultSet.getString(5);
            String passport = resultSet.getString(6);
            list.add(new Employee(id, FIO, departmentName, positionName, phoneNumber, passport));
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
