package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maaz on 05/10/18.
 */

public class SalesOrderData {

    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("price_list_currency")
    @Expose
    private String priceListCurrency;
    @SerializedName("naming_series")
    @Expose
    private String namingSeries;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("creation")
    @Expose
    private String creation;
    @SerializedName("conversion_rate")
    @Expose
    private Integer conversionRate;
    @SerializedName("plc_conversion_rate")
    @Expose
    private Integer plcConversionRate;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("po_no")
    @Expose
    private Object poNo;
    @SerializedName("billing_status")
    @Expose
    private String billingStatus;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPriceListCurrency() {
        return priceListCurrency;
    }

    public void setPriceListCurrency(String priceListCurrency) {
        this.priceListCurrency = priceListCurrency;
    }

    public String getNamingSeries() {
        return namingSeries;
    }

    public void setNamingSeries(String namingSeries) {
        this.namingSeries = namingSeries;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public Integer getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Integer conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Integer getPlcConversionRate() {
        return plcConversionRate;
    }

    public void setPlcConversionRate(Integer plcConversionRate) {
        this.plcConversionRate = plcConversionRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Object getPoNo() {
        return poNo;
    }

    public void setPoNo(Object poNo) {
        this.poNo = poNo;
    }

    public String getBillingStatus() {
        return billingStatus;
    }

    public void setBillingStatus(String billingStatus) {
        this.billingStatus = billingStatus;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

}
