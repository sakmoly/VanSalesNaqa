package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesOrderRaw_TokenResponse {

    private String creation;

    private String owner;

    private String billing_status;

    private String po_no;

    private String customer;

    private String order_type;

    private String status;

    private String company;

    private String naming_series;

    private String doc_no;

    private String delivery_date;

    private String currency;

    private String conversion_rate;

    private String price_list_currency;

    private String plc_conversion_rate;

    private String device_id;

    private List<SalesOrderRawItemData> items = null;

    private List<SalesOrderTaxRawData> taxes = null;

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBilling_status() {
        return billing_status;
    }

    public void setBilling_status(String billing_status) {
        this.billing_status = billing_status;
    }

    public String getPo_no() {
        return po_no;
    }

    public void setPo_no(String po_no) {
        this.po_no = po_no;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNaming_series() {
        return naming_series;
    }

    public void setNaming_series(String naming_series) {
        this.naming_series = naming_series;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getConversion_rate() {
        return conversion_rate;
    }

    public void setConversion_rate(String conversion_rate) {
        this.conversion_rate = conversion_rate;
    }

    public String getPrice_list_currency() {
        return price_list_currency;
    }

    public void setPrice_list_currency(String price_list_currency) {
        this.price_list_currency = price_list_currency;
    }

    public String getPlc_conversion_rate() {
        return plc_conversion_rate;
    }

    public void setPlc_conversion_rate(String plc_conversion_rate) {
        this.plc_conversion_rate = plc_conversion_rate;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public List<SalesOrderRawItemData> getItems() {
        return items;
    }

    public void setItems(List<SalesOrderRawItemData> items) {
        this.items = items;
    }

    public List<SalesOrderTaxRawData> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<SalesOrderTaxRawData> taxes) {
        this.taxes = taxes;
    }
}
