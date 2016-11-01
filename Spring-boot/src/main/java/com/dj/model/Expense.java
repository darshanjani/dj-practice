package com.dj.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Darshan on 11/1/2016.
 */
public class Expense {
    private int id;
    private String category;
    private Date txnDate;
    private String bizReason;
    private float amount;
    private String comment;
    private List<Receipt> receipts = new ArrayList<>();

    public Expense() {
    }

    public Expense(int id, String category, Date txnDate, String bizReason, float amount, String comment, List<Receipt> receipts) {
        this.id = id;
        this.category = category;
        this.txnDate = txnDate;
        this.bizReason = bizReason;
        this.amount = amount;
        this.comment = comment;
        this.receipts = receipts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public String getBizReason() {
        return bizReason;
    }

    public void setBizReason(String bizReason) {
        this.bizReason = bizReason;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public void addReceipt(Receipt receipt) {
        this.receipts.add(receipt);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", txnDate=" + txnDate +
                ", bizReason='" + bizReason + '\'' +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                ", receipts=" + receipts +
                '}';
    }
}
