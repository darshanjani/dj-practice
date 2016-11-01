package com.dj.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Darshan on 11/1/2016.
 */
public class Report {
    private int id;
    private String title;
    private Date rptDate;
    private List<Expense> expenses = new ArrayList<>();

    public Report() {
    }

    public Report(int id, String title, Date rptDate, List<Expense> expenses) {
        this.id = id;
        this.title = title;
        this.rptDate = rptDate;
        this.expenses = expenses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRptDate() {
        return rptDate;
    }

    public void setRptDate(Date rptDate) {
        this.rptDate = rptDate;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rptDate=" + rptDate +
                ", expenses=" + expenses +
                '}';
    }
}
