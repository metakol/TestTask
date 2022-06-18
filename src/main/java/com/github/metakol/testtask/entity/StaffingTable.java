package com.github.metakol.testtask.entity;

public class StaffingTable {
    private int id;
    private String department;
    private String position;
    private int freePlaces;
    private int numberPlaces;

    public StaffingTable(int id, String department, String position, int freePlaces, int numberPlaces) {
        this.id = id;
        this.department = department;
        this.position = position;
        this.freePlaces = freePlaces;
        this.numberPlaces = numberPlaces;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }

    public int getNumberPlaces() {
        return numberPlaces;
    }

    public void setNumberPlaces(int numberPlaces) {
        this.numberPlaces = numberPlaces;
    }
}
