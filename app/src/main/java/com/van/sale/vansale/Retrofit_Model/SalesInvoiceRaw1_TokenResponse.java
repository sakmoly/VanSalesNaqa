package com.van.sale.vansale.Retrofit_Model;


import com.van.sale.vansale.model.SalesInvoiceRaw1_ItemPayments;

import java.util.List;

public class SalesInvoiceRaw1_TokenResponse {

    private Integer key_id;
    private String creation;
    private String owner;
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
    private Integer docstatus,sync_status;
    private Integer is_return;
    private Integer SALES_INVOICE_IS_POS;
    private String device_id,return_against;
    private List<SalesInvoiceRaw1_ItemData> items = null;
    private List<SalesInvoiceRaw1_ItemPayments> payments = null;

    public Integer getSALES_INVOICE_IS_POS() {
        return SALES_INVOICE_IS_POS;
    }

    public void setSALES_INVOICE_IS_POS(Integer SALES_INVOICE_IS_POS) {
        this.SALES_INVOICE_IS_POS = SALES_INVOICE_IS_POS;
    }

    public Integer getSync_status() {
        return sync_status;
    }

    public void setSync_status(Integer sync_status) {
        this.sync_status = sync_status;
    }

    public String getReturn_against() {
        return return_against;
    }

    public void setReturn_against(String return_against) {
        this.return_against = return_against;
    }

    public Integer getKey_id() {
        return key_id;
    }

    public void setKey_id(Integer key_id) {
        this.key_id = key_id;
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

    public List<SalesInvoiceRaw1_ItemData> getItems() {
        return items;
    }

    public void setItems(List<SalesInvoiceRaw1_ItemData> items) {
        this.items = items;
    }

    public List<SalesInvoiceRaw1_ItemPayments> getPayments() {
        return payments;
    }

    public void setPayments(List<SalesInvoiceRaw1_ItemPayments> payments) {
        this.payments = payments;
    }
}
