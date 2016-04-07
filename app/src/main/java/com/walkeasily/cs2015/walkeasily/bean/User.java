package com.walkeasily.cs2015.walkeasily.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class User extends BmobObject {

    private String name;
    private String password;
    private String age;
    private String majorAndClass;
    private String studentId;

    private String phoneNum;
    private String mail;

    private String linderName;
    private String linderPhoneNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMajorAndClass() {
        return majorAndClass;
    }

    public void setMajorAndClass(String majorAndClass) {
        this.majorAndClass = majorAndClass;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLinderName() {
        return linderName;
    }

    public void setLinderName(String linderName) {
        this.linderName = linderName;
    }

    public String getLinderPhoneNum() {
        return linderPhoneNum;
    }

    public void setLinderPhoneNum(String linderPhoneNum) {
        this.linderPhoneNum = linderPhoneNum;
    }
}
