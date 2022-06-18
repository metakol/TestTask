package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.DBHandler.DBHandler;
import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.entity.StaffingTable;
import com.github.metakol.testtask.helpers.HelperDB;
import com.github.metakol.testtask.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ChangeStaffingTableController implements Initializable {

    @FXML
    private TableView<StaffingTable> table;
    @FXML
    private TableColumn<StaffingTable, String> columnPositionName;
    @FXML
    private TableColumn<StaffingTable, Integer> columnFreePlaces;
    @FXML
    private TableColumn<StaffingTable, Integer> columnNumberPlaces;

    ObservableList<StaffingTable> list = FXCollections.observableArrayList();
    ObservableList<String> departmentsList = FXCollections.observableArrayList();
    ObservableList<String> positionsList = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> departmentsBox;
    @FXML
    private ChoiceBox<String> positionsBox;

    StaffingTable selectedPositionInStaffingTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDepartmentsList();
        fillPositionsList();
        departmentsBox.setItems(departmentsList);
        positionsBox.setItems(positionsList);
        departmentsBox.setValue(departmentsList.get(0));
        positionsBox.setValue(positionsList.get(0));
        initTable();
        updateTable();
    }

    private void fillDepartmentsList() {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement();
        ) {
            String query = "SELECT department_name FROM departments";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    departmentsList.add(resultSet.getString(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillPositionsList() {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement();
        ) {
            String query = "SELECT position_name FROM job_positions";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    positionsList.add(resultSet.getString(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTable() {
        columnPositionName.setCellValueFactory(new PropertyValueFactory<>("position"));
        columnFreePlaces.setCellValueFactory(new PropertyValueFactory<>("freePlaces"));
        columnNumberPlaces.setCellValueFactory(new PropertyValueFactory<>("numberPlaces"));
        table.setItems(list);
    }

    private void updateTable() {
        list.clear();
        String sql = "SELECT staffing_table.id,department_name,position_name,free_places,number_places FROM staffing_table" +
                " INNER JOIN job_positions ON id_position=job_positions.id " +
                " INNER JOIN departments ON id_department=departments.id " +
                "WHERE department_name='" + departmentsBox.getValue() + "'";
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);
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
            String departmentName = resultSet.getString(2);
            String positionName = resultSet.getString(3);
            int freePlaces = resultSet.getInt(4);
            int numberPlaces = resultSet.getInt(5);
            list.add(new StaffingTable(id, departmentName, positionName, freePlaces, numberPlaces));
        }
    }

    @FXML
    void onGoBackClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/main-view.fxml"));
    }

    @FXML
    void onChangeDepartment(ActionEvent event) {
        updateTable();
    }

    @FXML
    void onAddClick(MouseEvent event) {
        if (isPositionAlreadyExists()) {
            addPosition();
        } else {
            insertPosition();
        }
        updateTable();
    }

    private boolean isPositionAlreadyExists() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPosition().equals(positionsBox.getValue())) {
                selectedPositionInStaffingTable = list.get(i);
                return true;
            }
        }
        return false;
    }

    private void addPosition() {
        String sql = "UPDATE staffing_table SET free_places=free_places+1 , number_places=number_places+1 WHERE id=?";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(sql);
        ) {
            statement.setInt(1, selectedPositionInStaffingTable.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertPosition() {
        int departmentID = HelperDB.getIDByDepartmentName(departmentsBox.getValue());
        int positionID = HelperDB.getIDByPositionName(positionsBox.getValue());

        String query = "INSERT INTO staffing_table(id_department,id_position,free_places,number_places) VALUES (?,?,?,?)";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(query);
        ) {
            statement.setInt(1, departmentID);
            statement.setInt(2, positionID);
            statement.setInt(3, 1);
            statement.setInt(4, 1);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDeleteClick(MouseEvent event) {
        if (isPositionAlreadyExists() && selectedPositionInStaffingTable.getFreePlaces() > 0) {
            if (selectedPositionInStaffingTable.getNumberPlaces() == 1) {
                deletePosition();
            } else {
                reducingPosition();
            }
            updateTable();
        }
    }

    private void deletePosition() {
        String query = "DELETE FROM staffing_table WHERE id=?";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(query);
        ) {
            statement.setInt(1, selectedPositionInStaffingTable.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reducingPosition() {
        String query = "UPDATE staffing_table SET free_places=free_places-1 , number_places=number_places-1 WHERE id=?";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(query);
        ) {
            statement.setInt(1, selectedPositionInStaffingTable.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
