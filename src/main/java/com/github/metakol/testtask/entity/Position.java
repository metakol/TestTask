package com.github.metakol.testtask.entity;

public class Position {
    private int ID;
    private String positionName;
    private int salary;

    public Position(int ID, String positionName, int salary) {
        this.ID = ID;
        this.positionName = positionName;
        this.salary = salary;
    }

    public Position(String positionName, int salary) {
        this.positionName = positionName;
        this.salary = salary;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
