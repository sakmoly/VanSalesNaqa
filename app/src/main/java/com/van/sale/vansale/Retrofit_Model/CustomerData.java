package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maaz on 02/10/18.
 */

public class CustomerData {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("credit_limit")
    @Expose
    private Float creditLimit;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("customer_primary_contact")
    @Expose
    private String customerPrimaryContact;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("tax_id")
    @Expose
    private String taxId;
    @SerializedName("customer_id")
    @Expose
    private String customer_id;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Float creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCustomerPrimaryContact() {
        return customerPrimaryContact;
    }

    public void setCustomerPrimaryContact(String customerPrimaryContact) {
        this.customerPrimaryContact = customerPrimaryContact;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
}
