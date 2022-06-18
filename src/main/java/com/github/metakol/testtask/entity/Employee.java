package com.github.metakol.testtask.entity;

public class Employee {
    private int ID;
    private String FIO;
    private int IDDepartment;
    private int IDPosition;
    private String phoneNumber;
    private String passport;

    public Employee(int ID, String FIO, int IDDepartment, int IDPosition, String phoneNumber, String passport) {
        this.ID = ID;
        this.FIO = FIO;
        this.IDDepartment = IDDepartment;
        this.IDPosition = IDPosition;
        this.phoneNumber = phoneNumber;
        this.passport = passport;
    }

    public Employee(String FIO, int IDDepartment, int IDPosition, String phoneNumber, String passport) {
        this.FIO = FIO;
        this.IDDepartment = IDDepartment;
        this.IDPosition = IDPosition;
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

    public int getIDDepartment() {
        return IDDepartment;
    }

    public void setIDDepartment(int IDDepartment) {
        this.IDDepartment = IDDepartment;
    }

    public int getIDPosition() {
        return IDPosition;
    }

    public void setIDPosition(int IDPosition) {
        this.IDPosition = IDPosition;
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
