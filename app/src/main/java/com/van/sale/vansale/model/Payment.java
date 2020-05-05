package com.van.sale.vansale.model;

import android.service.autofill.FillEventHistory;

public class Payment {

    private String PAYMENT_CREATION,PAYMENT_DOC_NO,MODE_OF_PAYMENT,RECEIVED_AMOUNT,PAYMENT_CUSTOMER,PAYMENT_REFERENCE_NO,PAYMENT_REFERENCE_DATE,PAYMENT_PAID_TO,PAYMENT_OWNER,PAYMENT_POSTING_TIME;
    private Integer KEY_ID,PAYMENT_SYNC_STATUS;

    public Payment() {
    }

    public Payment(String PAYMENT_CREATION, String PAYMENT_DOC_NO, String MODE_OF_PAYMENT, String RECEIVED_AMOUNT, String PAYMENT_CUSTOMER, Integer PAYMENT_SYNC_STATUS,String PAYMENT_REFERENCE_NO,String PAYMENT_REFERENCE_DATE,String PAYMENT_PAID_TO,String PAYMENT_OWNER,String PAYMENT_POSTING_TIME) {
        this.PAYMENT_CREATION = PAYMENT_CREATION;
        this.PAYMENT_DOC_NO = PAYMENT_DOC_NO;
        this.MODE_OF_PAYMENT = MODE_OF_PAYMENT;
        this.RECEIVED_AMOUNT = RECEIVED_AMOUNT;
        this.PAYMENT_CUSTOMER = PAYMENT_CUSTOMER;
        this.PAYMENT_SYNC_STATUS = PAYMENT_SYNC_STATUS;
        this.PAYMENT_REFERENCE_NO = PAYMENT_REFERENCE_NO;
        this.PAYMENT_REFERENCE_DATE = PAYMENT_REFERENCE_DATE;
        this.PAYMENT_PAID_TO = PAYMENT_PAID_TO;
        this.PAYMENT_OWNER=PAYMENT_OWNER;
        this.PAYMENT_POSTING_TIME=PAYMENT_POSTING_TIME;

    }

    public String getPAYMENT_PAID_TO() {
        return PAYMENT_PAID_TO;
    }

    public void setPAYMENT_PAID_TO(String PAYMENT_PAID_TO) {
        this.PAYMENT_PAID_TO = PAYMENT_PAID_TO;
    }

    public String getPAYMENT_REFERENCE_NO() {
        return PAYMENT_REFERENCE_NO;
    }

    public void setPAYMENT_REFERENCE_NO(String PAYMENT_REFERENCE_NO) {
        this.PAYMENT_REFERENCE_NO = PAYMENT_REFERENCE_NO;
    }

    public String getPAYMENT_REFERENCE_DATE() {
        return PAYMENT_REFERENCE_DATE;
    }

    public void setPAYMENT_REFERENCE_DATE(String PAYMENT_REFERENCE_DATE) {
        this.PAYMENT_REFERENCE_DATE = PAYMENT_REFERENCE_DATE;
    }

    public Integer getPAYMENT_SYNC_STATUS() {
        return PAYMENT_SYNC_STATUS;
    }

    public void setPAYMENT_SYNC_STATUS(Integer PAYMENT_SYNC_STATUS) {
        this.PAYMENT_SYNC_STATUS = PAYMENT_SYNC_STATUS;
    }

    public String getPAYMENT_CUSTOMER() {
        return PAYMENT_CUSTOMER;
    }

    public void setPAYMENT_CUSTOMER(String PAYMENT_CUSTOMER) {
        this.PAYMENT_CUSTOMER = PAYMENT_CUSTOMER;
    }

    public Integer getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(Integer KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public String getPAYMENT_CREATION() {
        return PAYMENT_CREATION;
    }

    public void setPAYMENT_CREATION(String PAYMENT_CREATION) {
        this.PAYMENT_CREATION = PAYMENT_CREATION;
    }

    public String getPAYMENT_DOC_NO() {
        return PAYMENT_DOC_NO;
    }

    public void setPAYMENT_DOC_NO(String PAYMENT_DOC_NO) {
        this.PAYMENT_DOC_NO = PAYMENT_DOC_NO;
    }

    public String getMODE_OF_PAYMENT() {
        return MODE_OF_PAYMENT;
    }

    public void setMODE_OF_PAYMENT(String MODE_OF_PAYMENT) {
        this.MODE_OF_PAYMENT = MODE_OF_PAYMENT;
    }

    public String getRECEIVED_AMOUNT() {
        return RECEIVED_AMOUNT;
    }

    public void setRECEIVED_AMOUNT(String RECEIVED_AMOUNT) {
        this.RECEIVED_AMOUNT = RECEIVED_AMOUNT;
    }

    public String getPAYMENT_OWNER() {
        return PAYMENT_OWNER;
    }

    public void setPAYMENT_OWNER(String PAYMENT_OWNER) {
        this.PAYMENT_OWNER = PAYMENT_OWNER;
    }

    public String getPAYMENT_POSTING_TIME() {
        return PAYMENT_POSTING_TIME;
    }

    public void setPAYMENT_POSTING_TIME(String PAYMENT_POSTING_TIME) {
        this.PAYMENT_POSTING_TIME = PAYMENT_POSTING_TIME;
    }
}
