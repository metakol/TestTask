package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.DBHandler.DBHandler;
import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.entity.Employee;
import com.github.metakol.testtask.helpers.Fields;
import com.github.metakol.testtask.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
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
    private final ObservableList<Employee> list = FXCollections.observableArrayList();


    @FXML
    private TextField searchField;

    @FXML
    private TextField fioFieldForAdd;
    @FXML
    private TextField personalPhoneFieldForAdd;
    @FXML
    private TextField passportFieldForAdd;
    @FXML
    private ComboBox<String> departmentBoxForAdd;
    @FXML
    private ComboBox<String> positionBoxForAdd;

    @FXML
    private TextField fioFieldForChange;
    @FXML
    private TextField personalPhoneFieldForChange;
    @FXML
    private TextField passportFieldForChange;
    @FXML
    private ComboBox<String> departmentBoxForChange;
    @FXML
    private ComboBox<String> positionBoxForChange;

    private final ObservableList<String> departmentNamesForAdd = FXCollections.observableArrayList();
    private final ObservableList<String> positionNamesForAdd = FXCollections.observableArrayList();

    private final ObservableList<String> departmentNamesForChange = FXCollections.observableArrayList();
    private final ObservableList<String> positionNamesForChange = FXCollections.observableArrayList();

    private final Map<String, Integer> departments = new HashMap<>();
    private final Map<String, Integer> positions = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDepartments();
        fillPositions();
        fillDepartmentBoxes();
        departmentBoxForAdd.setItems(departmentNamesForAdd);
        departmentBoxForChange.setItems(departmentNamesForChange);
        positionBoxForAdd.setItems(positionNamesForAdd);
        positionBoxForChange.setItems(positionNamesForChange);
        initTable();
        updateTable();
    }

    private void fillDepartments() {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement()
        ) {
            String query = "SELECT department_name,id FROM departments";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    departments.put(resultSet.getString(1), resultSet.getInt(2));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillPositions() {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement()
        ) {
            String query = "SELECT position_name,id FROM job_positions";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    positions.put(resultSet.getString(1), resultSet.getInt(2));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillDepartmentBoxes() {
        departmentNamesForAdd.addAll(departments.keySet());
        departmentNamesForChange.addAll(departments.keySet());
    }

    private void initTable() {
        columnFIO.setCellValueFactory(new PropertyValueFactory<>("FIO"));
        columnDepartment.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnPersonalPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        columnPassport.setCellValueFactory(new PropertyValueFactory<>("passport"));
        table.setItems(list);

        TableView.TableViewSelectionModel<Employee> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observableValue, oldItem, item) -> {
            if (list.size() > 0) {
                selectedItem = item;
                fioFieldForChange.setText(item.getFIO());
                personalPhoneFieldForChange.setText(item.getPhoneNumber());
                passportFieldForChange.setText(item.getPassport());
                departmentBoxForChange.setValue(item.getDepartmentName());
                departmentBoxForChange.setValue(item.getDepartmentName());
                positionBoxForChange.setValue(item.getPositionName());
            } else {
                selectedItem = null;
                departmentBoxForAdd.setValue(null);
                positionBoxForAdd.setValue(null);

                fioFieldForChange.clear();
                personalPhoneFieldForChange.clear();
                passportFieldForChange.clear();
                departmentBoxForChange.setValue(null);
                positionBoxForChange.setValue(null);
            }
        });
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
    void onTypeKeyForSearch(KeyEvent event) {
        updateTable();
    }

    @FXML
    void onGoBackClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/main-view.fxml"));
    }

    @FXML
    void onSelectDepartmentForAdd(ActionEvent event) {
        updatePositionsInBoxByDepartment(departmentBoxForAdd, positionNamesForAdd);
    }

    @FXML
    void onSelectDepartmentForChange(ActionEvent event) {
        updatePositionsInBoxByDepartment(departmentBoxForChange, positionNamesForChange);
    }

    private void updatePositionsInBoxByDepartment(ComboBoxBase<String> departmentBox, ObservableList<String> positionNames) {
        String sql = "SELECT position_name FROM staffing_table " +
                "INNER JOIN job_positions ON job_positions.id=id_position " +
                "WHERE id_department=" + departments.get(departmentBox.getValue()) + " AND free_places>0";
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)
        ) {
            positionNames.clear();
            while (resultSet.next()) {
                positionNames.add(resultSet.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onAddEmployeeClick(MouseEvent event) {
        if (Fields.fieldsAreNotEmpty(fioFieldForAdd, personalPhoneFieldForAdd, passportFieldForAdd) &&
                departmentBoxForAdd.getValue() != null && positionBoxForAdd.getValue() != null
        ) {
            String fio = fioFieldForAdd.getText();
            String departmentName = departmentBoxForAdd.getValue();
            String positionName = positionBoxForAdd.getValue();
            String phoneNumber = personalPhoneFieldForAdd.getText();
            String passport = passportFieldForAdd.getText();
            addEmployee(new Employee(fio, departmentName, positionName, phoneNumber, passport));
            fioFieldForAdd.clear();
            personalPhoneFieldForAdd.clear();
            passportFieldForAdd.clear();
            departmentBoxForAdd.setValue(null);
            positionBoxForAdd.setValue(null);
        }
    }

    private void addEmployee(Employee employee) {
        takePositionInStaffingTable(employee.getDepartmentName(), employee.getPositionName());

        String sql = "INSERT INTO employees (FIO,id_department,id_position,personal_phone_number,passport) VALUES (?,?,?,?,?)";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(sql)
        ) {
            statement.setString(1, employee.getFIO());
            statement.setInt(2, departments.get(employee.getDepartmentName()));
            statement.setInt(3, positions.get(employee.getPositionName()));
            statement.setString(4, employee.getPhoneNumber());
            statement.setString(5, employee.getPassport());
            statement.executeUpdate();
            updateTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onChangeEmployeeClick(MouseEvent event) {
        if (Fields.fieldsAreNotEmpty(fioFieldForChange, personalPhoneFieldForChange, passportFieldForChange) &&
                departmentBoxForChange.getValue() != null && positionBoxForChange.getValue() != null
        ) {
            String fio = fioFieldForChange.getText();
            String departmentName = departmentBoxForChange.getValue();
            String positionName = positionBoxForChange.getValue();
            String phoneNumber = personalPhoneFieldForChange.getText();
            String passport = passportFieldForChange.getText();
            Employee newItem = new Employee(fio, departmentName, positionName, phoneNumber, passport);
            updateEmployee(selectedItem, newItem);
        }
    }

    private void updateEmployee(Employee oldItem, Employee newItem) {
        releasePositionInStaffingTable(oldItem.getDepartmentName(), oldItem.getPositionName());
        takePositionInStaffingTable(newItem.getDepartmentName(), newItem.getPositionName());

        String query = "UPDATE employees SET fio=?, id_department=?, id_position=?, personal_phone_number=?, passport=? WHERE id=?";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(query)
        ) {
            statement.setString(1, newItem.getFIO());
            statement.setInt(2, departments.get(newItem.getDepartmentName()));
            statement.setInt(3, positions.get(newItem.getPositionName()));
            statement.setString(4, newItem.getPhoneNumber());
            statement.setString(5, newItem.getPassport());
            statement.setInt(6, oldItem.getID());
            statement.executeUpdate();
            updateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDeleteEmployeeClick(MouseEvent event) {
        if (selectedItem != null) {
            deleteEmployee(selectedItem);
        }
    }

    private void deleteEmployee(Employee employee) {
        releasePositionInStaffingTable(employee.getDepartmentName(), employee.getPositionName());

        String query = "DELETE FROM employees WHERE id=?";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(query)
        ) {
            statement.setInt(1, employee.getID());
            statement.executeUpdate();
            updateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void releasePositionInStaffingTable(String departmentName, String positionName) {
        changePositionState(departmentName,positionName,true);
    }

    private void takePositionInStaffingTable(String departmentName, String positionName) {
        changePositionState(departmentName,positionName,false);
    }

    private void changePositionState(String departmentName, String positionName, boolean isPositive) {
        String sign = isPositive ? "+" : "-";
        String query =String.format( "UPDATE staffing_table SET free_places=free_places %s 1 " +
                "WHERE id_department=? AND id_position=?",sign);
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(query)
        ) {
            statement.setInt(1, departments.get(departmentName));
            statement.setInt(2, positions.get(positionName));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
