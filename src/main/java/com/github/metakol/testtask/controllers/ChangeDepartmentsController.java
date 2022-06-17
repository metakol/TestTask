package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.DBHandler.DBHandler;
import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.entity.Department;
import com.github.metakol.testtask.helpers.Fields;
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

public class ChangeDepartmentsController implements Initializable {
    @FXML
    private TableView<Department> table;
    @FXML
    private TableColumn<Department, String> columnDepartmentName;
    @FXML
    private TableColumn<Department, String> columnPhoneNumber;
    @FXML
    private TableColumn<Department, String> columnEmail;

    ObservableList<Department> list = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private TextField departmentNameForAddField;
    @FXML
    private TextField phoneNumberForAddField;
    @FXML
    private TextField emailForAddField;

    @FXML
    private TextField departmentNameForChangeField;
    @FXML
    private TextField phoneNumberForChangeField;
    @FXML
    private TextField emailForChangeField;

    private Department selectedItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        fillList();
    }

    private void initTable() {
        columnDepartmentName.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        table.setItems(list);

        TableView.TableViewSelectionModel<Department> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observableValue, oldItem, item) -> {
            if (list.size() > 0) {
                selectedItem = item;
                departmentNameForChangeField.setText(item.getDepartmentName());
                phoneNumberForChangeField.setText(item.getPhoneNumber());
                emailForChangeField.setText(item.getEmail());
            } else {
                selectedItem = null;
            }
        });
    }

    private void fillList() {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.getStatement()
        ) {
            String query = "SELECT * FROM departments";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    list.add(new Department(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onTypeKeyForSearch(KeyEvent event) {
        updateList();
    }

    private void updateList() {
        list.clear();
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.getStatement()
        ) {
            String query = "SELECT * FROM departments " +
                    "WHERE department_name LIKE '%" + searchField.getText().trim() + "%'";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    list.add(new Department(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onGoBackClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/main-view.fxml"));
    }

    @FXML
    void onAddDepartmentClick(MouseEvent event) {
        if (Fields.fieldsAreNotEmpty(departmentNameForAddField, phoneNumberForAddField, emailForAddField)) {
            Department department = new Department(departmentNameForAddField.getText().trim(),
                    phoneNumberForAddField.getText().trim(),
                    emailForAddField.getText().trim());
            addDepartment(department);
            departmentNameForAddField.clear();
            phoneNumberForAddField.clear();
            emailForAddField.clear();
        }
    }

    private void addDepartment(Department department) {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.getStatement();
        ) {
            String query = "INSERT INTO departments (department_name,phone_number,email) " +
                    "VALUES ('" + department.getDepartmentName() + "','" + department.getPhoneNumber() + "','" + department.getEmail() + "')";
            statement.executeUpdate(query);
            updateList();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onChangeDepartmentClick(MouseEvent event) {
        if (selectedItem != null && Fields.fieldsAreNotEmpty(departmentNameForChangeField, phoneNumberForChangeField, emailForChangeField)) {
            Department newItem = new Department(departmentNameForChangeField.getText().trim(),
                    phoneNumberForChangeField.getText().trim(),
                    emailForChangeField.getText().trim());
            updateDepartment(selectedItem, newItem);
            departmentNameForChangeField.clear();
            phoneNumberForChangeField.clear();
            emailForChangeField.clear();
        }
    }

    private void updateDepartment(Department oldItem, Department newItem) {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.getStatement();
        ) {
            String query = "UPDATE departments SET department_name='" + newItem.getDepartmentName() +
                    "', phone_number='" + newItem.getPhoneNumber() + "', email='" + newItem.getEmail() +
                    "' WHERE id=" + oldItem.getID();
            statement.executeUpdate(query);
            updateList();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDeleteDepartmentClick(MouseEvent event) {
        if (selectedItem != null) {
            deleteDepartment(selectedItem);
        }
    }

    private void deleteDepartment(Department item) {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.getStatement();
        ) {
            String query = "DELETE FROM departments WHERE id=" + item.getID();
            statement.executeUpdate(query);
            updateList();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
