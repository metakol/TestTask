package com.github.metakol.testtask.entity;

public class Position {
    private int ID;
    private String title;
    private int salary;

    public Position(int ID, String title, int salary) {
        this.ID = ID;
        this.title = title;
        this.salary = salary;
    }

    public Position(String title, int salary) {
        this.title = title;
        this.salary = salary;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
