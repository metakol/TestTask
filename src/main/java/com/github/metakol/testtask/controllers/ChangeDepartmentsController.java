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
import java.sql.PreparedStatement;
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

    private Department selectedItem;
    private ObservableList<Department> list = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private TextField departmentNameFieldForAdd;
    @FXML
    private TextField phoneNumberFieldForAdd;
    @FXML
    private TextField emailFieldForAdd;

    @FXML
    private TextField departmentNameFieldForChange;
    @FXML
    private TextField phoneNumberFieldForChange;
    @FXML
    private TextField emailFieldForChange;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        updateTable();
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
                departmentNameFieldForChange.setText(item.getDepartmentName());
                phoneNumberFieldForChange.setText(item.getPhoneNumber());
                emailFieldForChange.setText(item.getEmail());
            } else {
                selectedItem = null;
            }
        });
    }

    private void updateTable() {
        list.clear();
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement()
        ) {
            String query = "SELECT id,department_name,phone_number,email FROM departments " +
                    "WHERE department_name LIKE '%" + searchField.getText().trim() + "%'";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                fillListForTable(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillListForTable(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String departmentName = resultSet.getString(2);
            String phoneNumber = resultSet.getString(3);
            String email = resultSet.getString(4);
            list.add(new Department(id, departmentName, phoneNumber, email));
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
    void onAddDepartmentClick(MouseEvent event) {
        if (Fields.fieldsAreNotEmpty(departmentNameFieldForAdd, phoneNumberFieldForAdd, emailFieldForAdd)) {
            Department department = new Department(
                    departmentNameFieldForAdd.getText().trim(),
                    phoneNumberFieldForAdd.getText().trim(),
                    emailFieldForAdd.getText().trim()
            );
            addDepartment(department);
            departmentNameFieldForAdd.clear();
            phoneNumberFieldForAdd.clear();
            emailFieldForAdd.clear();
        }
    }

    private void addDepartment(Department department) {
        String query = "INSERT INTO departments (department_name,phone_number,email) VALUES (?,?,?)";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(query);
        ) {
            statement.setString(1, department.getDepartmentName());
            statement.setString(2, department.getPhoneNumber());
            statement.setString(3, department.getEmail());
            statement.executeUpdate();
            updateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onChangeDepartmentClick(MouseEvent event) {
        if (selectedItem != null && Fields.fieldsAreNotEmpty(departmentNameFieldForChange, phoneNumberFieldForChange, emailFieldForChange)) {
            Department newItem = new Department(
                    departmentNameFieldForChange.getText().trim(),
                    phoneNumberFieldForChange.getText().trim(),
                    emailFieldForChange.getText().trim()
            );
            updateDepartment(selectedItem, newItem);
            departmentNameFieldForChange.clear();
            phoneNumberFieldForChange.clear();
            emailFieldForChange.clear();
        }
    }

    private void updateDepartment(Department oldItem, Department newItem) {
        String query = "UPDATE departments SET department_name=? , phone_number=? , email=? WHERE id=?";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(query);
        ) {
            statement.setString(1, newItem.getDepartmentName());
            statement.setString(2, newItem.getPhoneNumber());
            statement.setString(3, newItem.getEmail());
            statement.setInt(4, oldItem.getID());
            statement.executeUpdate();
            updateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDeleteDepartmentClick(MouseEvent event) {
        if (selectedItem != null) {
            if(!isAlreadyUsed()){
                deleteDepartment(selectedItem);
            }
        }
    }

    private boolean isAlreadyUsed() {
        String sql = "SELECT COUNT(id_department) FROM staffing_table WHERE id_department=" + selectedItem.getID();
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)
        ) {
            if (resultSet.next()) {
                if(resultSet.getInt(1)==0){
                    return false;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void deleteDepartment(Department item) {
        String query = "DELETE FROM departments WHERE id=?";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(query);
        ) {
            statement.setInt(1, item.getID());
            statement.executeUpdate();
            updateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
