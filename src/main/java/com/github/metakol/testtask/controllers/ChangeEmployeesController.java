package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.DBHandler.DBHandler;
import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.entity.Employee;
import com.github.metakol.testtask.entity.Position;
import com.github.metakol.testtask.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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

public class ChangeEmployeesController implements Initializable {
    @FXML
    private TableView<Employee> table;
    @FXML
    private TableColumn<Employee, String> columnFIO;
    @FXML
    private TableColumn<Employee, String> columnDepartment;
    @FXML
    private TableColumn<Employee, String> columnPosition;
    @FXML
    private TableColumn<Employee, String> columnPersonalPhone;
    @FXML
    private TableColumn<Employee, String> columnPassport;

    private Employee selectedItem;
    private ObservableList<Employee> list = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private TextField fioFieldForAdd;
    @FXML
    private TextField personalFieldPhoneForAdd;
    @FXML
    private TextField passportFieldForAdd;
    @FXML
    private ComboBox<?> departmentBoxForAdd;
    @FXML
    private ComboBox<?> positionBoxForAdd;

    @FXML
    private TextField fioFieldForChange;
    @FXML
    private TextField personalFieldPhoneForChange;
    @FXML
    private TextField passportFieldForChange;
    @FXML
    private ComboBox<?> departmentBoxForChange;
    @FXML
    private ComboBox<?> positionBoxForChange;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        fillTable();
    }

    private void initTable() {

    }

    private void fillTable() {

    }

    private void fillListForTable(ResultSet resultSet) throws SQLException {

    }

    @FXML
    void onTypeKeyForSearch(KeyEvent event) {
        updateTable();
    }

    private void updateTable() {

    }

    @FXML
    void onGoBackClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/main-view.fxml"));
    }

    @FXML
    void onAddEmployeeClick(MouseEvent event) {
        addEmployee(null);
    }

    private void addEmployee(Employee employee) {

    }

    @FXML
    void onChangeEmployeeClick(MouseEvent event) {
        updateEmployee(null, null);
    }

    private void updateEmployee(Employee oldItem, Employee newItem) {

    }

    @FXML
    void onDeleteEmployeeClick(MouseEvent event) {
        if (selectedItem != null) {
            deleteEmployee(selectedItem);
        }
    }

    private void deleteEmployee(Employee item) {

    }
}
