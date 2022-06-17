package com.github.metakol.testtask.entity;

public class Department {
    private int ID;
    private String departmentName;
    private String phoneNumber;
    private String email;

    public Department(int ID, String departmentName, String phoneNumber, String email) {
        this.ID = ID;
        this.departmentName = departmentName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Department(String departmentName, String phoneNumber, String email) {
        this.departmentName = departmentName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
