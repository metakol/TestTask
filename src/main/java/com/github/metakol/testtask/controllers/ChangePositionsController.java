package com.github.metakol.testtask.controllers;

import com.github.metakol.testtask.DBHandler.DBHandler;
import com.github.metakol.testtask.Launch;
import com.github.metakol.testtask.entity.Position;
import com.github.metakol.testtask.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
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

    ObservableList<Position> list = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private TextField positionForAddField;
    @FXML
    private TextField salaryForAddField;

    @FXML
    private TextField positionForChangeField;
    @FXML
    private TextField salaryForChangeField;

    //для избежания ошибок, когда выделение поля в таблице пропадает
    private boolean wasUpdateTable = false;

    private Position selectItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        fillList();
    }

    private void initTable() {
        columnPositionName.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        table.setItems(list);

        TableView.TableViewSelectionModel<Position> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observableValue, oldItem, item) -> {
            if (!wasUpdateTable && list.size() > 0) {
                System.out.println(1);
                selectItem = item;
                positionForChangeField.setText(item.getTitle());
                salaryForChangeField.setText(String.valueOf(item.getSalary()));
            } else {
                System.out.println(2);
                selectItem = null;
            }
        });
    }

    private void fillList() {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.getStatement();
        ) {
            String query = "select * from positions";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    list.add(new Position(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean fieldsAreNotEmpty(TextInputControl... fields) {
        for (TextInputControl field : fields) {
            if (field.getText().trim().equals("")) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void onTypeKeyForSearch(KeyEvent event) {
        updateList();
    }

    private void updateList() {
        list.clear();
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.getStatement();
        ) {
            String query = "select * from positions where title like '%" + searchField.getText().trim() + "%'";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    list.add(new Position(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onGoBackClick(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/main-view.fxml"));
    }

    @FXML
    void onAddPositionClick(MouseEvent event) {
        if (fieldsAreNotEmpty(positionForAddField, salaryForAddField)) {
            Position position = new Position(positionForAddField.getText(), Integer.parseInt(salaryForAddField.getText()));
            addPosition(position);
            positionForAddField.clear();
            salaryForAddField.clear();
        }
    }

    private void addPosition(Position position) {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.getStatement();
        ) {
            String query = "INSERT INTO positions (title,salary) VALUES ('" + position.getTitle() + "','" + position.getSalary() + "')";
            statement.executeUpdate(query);
            updateList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onChangePositionClick(MouseEvent event) {
        if (selectItem != null && fieldsAreNotEmpty(positionForChangeField,salaryForChangeField)) {
            Position newItem = new Position(positionForChangeField.getText(), Integer.parseInt(salaryForChangeField.getText()));
            updatePosition(selectItem, newItem);
            positionForChangeField.clear();
            salaryForChangeField.clear();
        }
    }

    private void updatePosition(Position oldItem, Position newItem) {
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.getStatement();
        ) {
            String query = "UPDATE positions SET title='" + newItem.getTitle() + "', salary=" + newItem.getSalary() + " WHERE id=" + oldItem.getID();
            statement.executeUpdate(query);
            updateList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDeletePositionClick(MouseEvent event) {
        deletePosition();
    }

    private void deletePosition() {
        if (selectItem != null) {
            try (DBHandler handler = new DBHandler();
                 Statement statement = handler.getStatement();
            ) {
                String query = "DELETE FROM positions WHERE id=" + selectItem.getID();
                statement.executeUpdate(query);
                updateList();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
