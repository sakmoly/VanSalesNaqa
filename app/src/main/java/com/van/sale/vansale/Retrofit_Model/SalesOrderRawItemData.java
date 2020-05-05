package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesOrderRawItemData {

    private String qty;
    private String warehouse;
    private String item_name;
    private String rate;
    private String stock_uom;
    private String item_code;
    private String price_list_rate;
    private String discount_percentage;
    private String tax_rate;
    private String tax_amount;

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStock_uom() {
        return stock_uom;
    }

    public void setStock_uom(String stock_uom) {
        this.stock_uom = stock_uom;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getPrice_list_rate() {
        return price_list_rate;
    }

    public void setPrice_list_rate(String price_list_rate) {
        this.price_list_rate = price_list_rate;
    }

    public String getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(String discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public String getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(String tax_rate) {
        this.tax_rate = tax_rate;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(String tax_amount) {
        this.tax_amount = tax_amount;
    }
}
