package com.dj.model;

/**
 * Created by Darshan on 11/1/2016.
 */
public class Receipt {
    private int id;
    private String fileName;

    public Receipt() {
    }

    public Receipt(int id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
