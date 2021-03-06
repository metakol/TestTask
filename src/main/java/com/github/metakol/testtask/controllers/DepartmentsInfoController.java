package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.entity.Employee;
import javafx.fxml.Initializable;
import com.github.metakol.testtask.DBHandler.DBHandler;
import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.entity.Department;
import com.github.metakol.testtask.entity.StaffingTable;
import com.github.metakol.testtask.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentsInfoController implements Initializable {
    @FXML
    private TableView<Employee> table;
    @FXML
    private TableColumn<Employee, String> columnFIO;
    @FXML
    private TableColumn<Employee, String> columnPosition;

    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label emailLabel;

    ObservableList<Employee> list = FXCollections.observableArrayList();
    ObservableList<String> departmentNames = FXCollections.observableArrayList();
    List<Department> departments = new ArrayList<>();

    @FXML
    private ComboBox<String> departmentsBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDepartments();
        departmentsBox.setItems(departmentNames);
        if (departmentNames.size() > 0) {
            departmentsBox.setValue(departmentNames.get(0));
            setEmailTitle(departments.get(0).getEmail());
            setPhoneNumberTitle(departments.get(0).getPhoneNumber());
        }
        initTable();
        updateTable();
    }

    private void fillDepartments() {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement()
        ) {
            String query = "SELECT department_name,phone_number,email FROM departments";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String departmentName = resultSet.getString(1);
                    String phoneNumber = resultSet.getString(2);
                    String email = resultSet.getString(3);

                    departmentNames.add(resultSet.getString(1));
                    departments.add(new Department(departmentName, phoneNumber, email));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setEmailTitle(String email) {
        emailLabel.setText("?????????????????????? ??????????: " + email);
    }

    private void setPhoneNumberTitle(String phoneNumber) {
        phoneNumberLabel.setText("?????????? ???????????????? ????????????: " + phoneNumber);
    }

    private void initTable() {
        columnFIO.setCellValueFactory(new PropertyValueFactory<>("FIO"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        table.setItems(list);
    }

    private void updateTable() {
        list.clear();
        String sql = "SELECT FIO,department_name,position_name " +
                "FROM employees " +
                "INNER JOIN departments ON id_department=departments.id " +
                "INNER JOIN job_positions ON id_position=job_positions.id " +
                "WHERE department_name = '" + departmentsBox.getValue() + "'";
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
            String positionName = resultSet.getString(3);
            list.add(new Employee(FIO,positionName));
        }
    }

    @FXML
    void onSelectDepartment(ActionEvent event) {
        Department department = getDepartmentByName(departmentsBox.getValue());
        setEmailTitle(department.getEmail());
        setPhoneNumberTitle(department.getPhoneNumber());
        updateTable();
    }

    private Department getDepartmentByName(String name) {
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getDepartmentName().equals(name)) {
                return departments.get(i);
            }
        }
        return null;
    }

    @FXML
    void onGoBackClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/main-view.fxml"));
    }
}
