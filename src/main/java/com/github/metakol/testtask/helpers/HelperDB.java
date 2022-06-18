package com.github.metakol.testtask.helpers;

import com.github.metakol.testtask.DBHandler.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelperDB {
    public static int getIDByDepartmentName(String name) {
        return getIDByField(String.format
                ("SELECT id FROM departments WHERE department_name='%s'", name));
    }

    public static int getIDByPositionName(String name) {
        return getIDByField(String.format
                ("SELECT id FROM job_positions WHERE position_name='%s'", name));
    }

    public static int getIDByField(String sql) {
        int id = 0;
        try (DBHandler handler = new DBHandler();
             Statement statement = handler.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);
        ) {
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
