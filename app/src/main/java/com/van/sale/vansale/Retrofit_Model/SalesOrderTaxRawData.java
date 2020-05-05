package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesOrderTaxRawData {

    private String account_head;
    private String description;
    private String charge_type;
    private String total;
    private String tax_amount;
    private String rate;

    public SalesOrderTaxRawData(String account_head, String description, String charge_type, String total, String tax_amount, String rate) {
        this.account_head = account_head;
        this.description = description;
        this.charge_type = charge_type;
        this.total = total;
        this.tax_amount = tax_amount;
        this.rate = rate;
    }

    public String getAccount_head() {
        return account_head;
    }

    public void setAccount_head(String account_head) {
        this.account_head = account_head;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(String tax_amount) {
        this.tax_amount = tax_amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
