package com.dj.model;

import java.util.Date;

/**
 * Created by Darshan on 10/15/2016.
 */

public class User {
    private int id;
    private String name;
    private Date dob;
    private String imgName;

    public User() {
    }

    public User(int id, String name, Date dob, String imgName) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.imgName = imgName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", imgName='" + imgName + '\'' +
                '}';
    }

    public User merge(User updatedUser) {
        this.setName(updatedUser.getName());
        this.setDob(updatedUser.getDob());
        return this;
    }
}
