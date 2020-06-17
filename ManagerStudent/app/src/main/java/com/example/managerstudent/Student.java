package com.example.managerstudent;

public class Student {
    private String mssv;
    private  String name;
    private  String birthday;
    private String email;
    private  String address;

    public Student(String mssv, String name, String birthday, String email, String address) {
        this.mssv = mssv;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.address = address;
    }
    public Student(){}

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
