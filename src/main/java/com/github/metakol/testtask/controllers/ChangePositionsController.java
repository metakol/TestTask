package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.DBHandler.DBHandler;
import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.entity.Position;
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

public class ChangePositionsController implements Initializable {
    @FXML
    private TableView<Position> table;
    @FXML
    private TableColumn<Position, String> columnPositionName;
    @FXML
    private TableColumn<Position, Integer> columnSalary;

    private Position selectedItem;
    private ObservableList<Position> list = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private TextField positionFieldForAdd;
    @FXML
    private TextField salaryFieldForAdd;

    @FXML
    private TextField positionFieldForChange;
    @FXML
    private TextField salaryFieldForChange;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        updateTable();
    }

    private void initTable() {
        columnPositionName.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        table.setItems(list);

        TableView.TableViewSelectionModel<Position> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observableValue, oldItem, item) -> {
            if (list.size() > 0) {
                selectedItem = item;
                positionFieldForChange.setText(item.getPositionName());
                salaryFieldForChange.setText(String.valueOf(item.getSalary()));
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
            String query = "SELECT id,position_name,salary FROM job_positions " +
                    "WHERE position_name LIKE '%" + searchField.getText().trim() + "%'";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    fillListForTable(resultSet);
                }
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
            String positionName = resultSet.getString(2);
            int salary = resultSet.getInt(3);
            list.add(new Position(id, positionName, salary));
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
    void onAddPositionClick(MouseEvent event) {
        if (Fields.fieldsAreNotEmpty(positionFieldForAdd, salaryFieldForAdd)) {
            Position position = new Position(positionFieldForAdd.getText(), Integer.parseInt(salaryFieldForAdd.getText()));
            addPosition(position);
            positionFieldForAdd.clear();
            salaryFieldForAdd.clear();
        }
    }

    private void addPosition(Position position) {
        String query = "INSERT INTO job_positions (position_name,salary) VALUES (?,?)";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(query);
        ) {
            statement.setString(1, position.getPositionName());
            statement.setInt(2, position.getSalary());
            statement.executeUpdate();
            updateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onChangePositionClick(MouseEvent event) {
        if (selectedItem != null && Fields.fieldsAreNotEmpty(positionFieldForChange, salaryFieldForChange)) {
            Position newItem = new Position(positionFieldForChange.getText(), Integer.parseInt(salaryFieldForChange.getText()));
            updatePosition(selectedItem, newItem);
            positionFieldForChange.clear();
            salaryFieldForChange.clear();
        }
    }

    private void updatePosition(Position oldItem, Position newItem) {
        String query = "UPDATE job_positions SET position_name=?, salary=? WHERE id=?";
        try (DBHandler handler = new DBHandler();
             PreparedStatement statement = handler.preparedStatement(query);
        ) {
            statement.setString(1, newItem.getPositionName());
            statement.setInt(2, newItem.getSalary());
            statement.setInt(3, oldItem.getID());
            statement.executeUpdate();
            updateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDeletePositionClick(MouseEvent event) {
        if (selectedItem != null) {
            deletePosition(selectedItem);
        }
    }

    private void deletePosition(Position item) {
        String query = "DELETE FROM job_positions WHERE id=?";
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
