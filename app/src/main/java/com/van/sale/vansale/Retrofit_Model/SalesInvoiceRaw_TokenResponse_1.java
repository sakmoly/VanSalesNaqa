package com.van.sale.vansale.Retrofit_Model;


import com.van.sale.vansale.model.SalesInvoiceRaw_ItemPayments;

import java.util.List;

public class SalesInvoiceRaw_TokenResponse_1 {

    private String creation;
    private String owner;
    private String modified_by;
    private String price_list_currency;
    private String customer;
    private String company;
    private String naming_series;
    private String currency;
    private String doc_no;
    private String conversion_rate;
    private String plc_conversion_rate;
    private String posting_time;
    private String posting_date;
    private Integer docstatus;
    private Integer is_return;
    private Integer is_pos;
    private Integer update_stock;
    private Integer set_posting_time;
    private String device_id;
    private List<SalesInvoiceRaw_ItemData> items = null;
    private List<SalesInvoiceRaw_TaxData> taxes = null;


    public Integer getIs_pos() {
        return is_pos;
    }

    public void setIs_pos(Integer is_pos) {
        this.is_pos = is_pos;
    }

    public Integer getUpdate_stock() {
        return update_stock;
    }

    public void setUpdate_stock(Integer update_stock) {
        this.update_stock = update_stock;
    }

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

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getModified_by() {
        return modified_by;
    }

    public String getPrice_list_currency() {
        return price_list_currency;
    }

    public void setPrice_list_currency(String price_list_currency) {
        this.price_list_currency = price_list_currency;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getConversion_rate() {
        return conversion_rate;
    }

    public void setConversion_rate(String conversion_rate) {
        this.conversion_rate = conversion_rate;
    }

    public String getPlc_conversion_rate() {
        return plc_conversion_rate;
    }

    public void setPlc_conversion_rate(String plc_conversion_rate) {
        this.plc_conversion_rate = plc_conversion_rate;
    }

    public String getPosting_time() {
        return posting_time;
    }

    public void setPosting_time(String posting_time) {
        this.posting_time = posting_time;
    }

    public String getPosting_date() {
        return posting_date;
    }

    public void setPosting_date(String posting_date) {
        this.posting_date = posting_date;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public Integer getIs_return() {
        return is_return;
    }

    public void setIs_return(Integer is_return) {
        this.is_return = is_return;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public List<SalesInvoiceRaw_ItemData> getItems() {
        return items;
    }

    public void setItems(List<SalesInvoiceRaw_ItemData> items) {
        this.items = items;
    }

    public List<SalesInvoiceRaw_TaxData> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<SalesInvoiceRaw_TaxData> taxes) {
        this.taxes = taxes;
    }
    public void setSet_posting_time(Integer set_posting_time) {
        this.set_posting_time = set_posting_time;
    }

    public Integer getSet_posting_time() {
        return set_posting_time;
    }
}
