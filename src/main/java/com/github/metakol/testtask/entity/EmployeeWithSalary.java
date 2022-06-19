package com.github.metakol.testtask.entity;

public class EmployeeWithSalary {
    private int ID;
    private String FIO;
    private String departmentName;
    private String positionName;
    private int salary;

    public EmployeeWithSalary(int ID, String FIO, String departmentName, String positionName, int salary) {
        this.ID = ID;
        this.FIO = FIO;
        this.departmentName = departmentName;
        this.positionName = positionName;
        this.salary = salary;
    }

    public EmployeeWithSalary(String FIO, String departmentName, String positionName, int salary) {
        this.FIO = FIO;
        this.departmentName = departmentName;
        this.positionName = positionName;
        this.salary = salary;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
