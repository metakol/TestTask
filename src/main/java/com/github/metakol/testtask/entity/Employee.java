package com.github.metakol.testtask.entity;

public class Employee {
    private int ID;
    private String FIO;
    private String departmentName;
    private String positionName;
    private String phoneNumber;
    private String passport;

    public Employee(int ID, String FIO, String departmentName, String positionName, String phoneNumber, String passport) {
        this.ID = ID;
        this.FIO = FIO;
        this.departmentName = departmentName;
        this.positionName = positionName;
        this.phoneNumber = phoneNumber;
        this.passport = passport;
    }

    public Employee(String FIO, String departmentName, String positionName, String phoneNumber, String passport) {
        this.FIO = FIO;
        this.departmentName = departmentName;
        this.positionName = positionName;
        this.phoneNumber = phoneNumber;
        this.passport = passport;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}
