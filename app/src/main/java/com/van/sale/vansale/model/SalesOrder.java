package com.van.sale.vansale.model;

/**
 * Created by maaz on 18/09/18.
 */

public class SalesOrder {

    private String customer_name,date,amount;

    public SalesOrder(String customer_name, String date, String amount) {
        this.customer_name = customer_name;
        this.date = date;
        this.amount = amount;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
