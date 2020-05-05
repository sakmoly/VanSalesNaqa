package com.van.sale.vansale.model;

public class SalesInvoiceModeOfPayment {

    Integer KEY_ID,SALES_INVOICE_ID;
    String SALES_INVOICE_PAYMENT_ACCOUNT,SALES_INVOICE_PAYMENT_MODE_OF_PAYMENT,SALES_INVOICE_PAYMENT_BASE_AMOUNT,SALES_INVOICE_PAYMENT_AMOUNT,SALES_INVOICE_PAYMENT_TYPE;

    public SalesInvoiceModeOfPayment(Integer SALES_INVOICE_ID, String SALES_INVOICE_PAYMENT_ACCOUNT, String SALES_INVOICE_PAYMENT_MODE_OF_PAYMENT, String SALES_INVOICE_PAYMENT_BASE_AMOUNT, String SALES_INVOICE_PAYMENT_AMOUNT, String SALES_INVOICE_PAYMENT_TYPE) {
        this.SALES_INVOICE_ID = SALES_INVOICE_ID;
        this.SALES_INVOICE_PAYMENT_ACCOUNT = SALES_INVOICE_PAYMENT_ACCOUNT;
        this.SALES_INVOICE_PAYMENT_MODE_OF_PAYMENT = SALES_INVOICE_PAYMENT_MODE_OF_PAYMENT;
        this.SALES_INVOICE_PAYMENT_BASE_AMOUNT = SALES_INVOICE_PAYMENT_BASE_AMOUNT;
        this.SALES_INVOICE_PAYMENT_AMOUNT = SALES_INVOICE_PAYMENT_AMOUNT;
        this.SALES_INVOICE_PAYMENT_TYPE = SALES_INVOICE_PAYMENT_TYPE;
    }

    public Integer getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(Integer KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public Integer getSALES_INVOICE_ID() {
        return SALES_INVOICE_ID;
    }

    public void setSALES_INVOICE_ID(Integer SALES_INVOICE_ID) {
        this.SALES_INVOICE_ID = SALES_INVOICE_ID;
    }

    public String getSALES_INVOICE_PAYMENT_ACCOUNT() {
        return SALES_INVOICE_PAYMENT_ACCOUNT;
    }

    public void setSALES_INVOICE_PAYMENT_ACCOUNT(String SALES_INVOICE_PAYMENT_ACCOUNT) {
        this.SALES_INVOICE_PAYMENT_ACCOUNT = SALES_INVOICE_PAYMENT_ACCOUNT;
    }

    public String getSALES_INVOICE_PAYMENT_MODE_OF_PAYMENT() {
        return SALES_INVOICE_PAYMENT_MODE_OF_PAYMENT;
    }

    public void setSALES_INVOICE_PAYMENT_MODE_OF_PAYMENT(String SALES_INVOICE_PAYMENT_MODE_OF_PAYMENT) {
        this.SALES_INVOICE_PAYMENT_MODE_OF_PAYMENT = SALES_INVOICE_PAYMENT_MODE_OF_PAYMENT;
    }

    public String getSALES_INVOICE_PAYMENT_BASE_AMOUNT() {
        return SALES_INVOICE_PAYMENT_BASE_AMOUNT;
    }

    public void setSALES_INVOICE_PAYMENT_BASE_AMOUNT(String SALES_INVOICE_PAYMENT_BASE_AMOUNT) {
        this.SALES_INVOICE_PAYMENT_BASE_AMOUNT = SALES_INVOICE_PAYMENT_BASE_AMOUNT;
    }

    public String getSALES_INVOICE_PAYMENT_AMOUNT() {
        return SALES_INVOICE_PAYMENT_AMOUNT;
    }

    public void setSALES_INVOICE_PAYMENT_AMOUNT(String SALES_INVOICE_PAYMENT_AMOUNT) {
        this.SALES_INVOICE_PAYMENT_AMOUNT = SALES_INVOICE_PAYMENT_AMOUNT;
    }

    public String getSALES_INVOICE_PAYMENT_TYPE() {
        return SALES_INVOICE_PAYMENT_TYPE;
    }

    public void setSALES_INVOICE_PAYMENT_TYPE(String SALES_INVOICE_PAYMENT_TYPE) {
        this.SALES_INVOICE_PAYMENT_TYPE = SALES_INVOICE_PAYMENT_TYPE;
    }
}
