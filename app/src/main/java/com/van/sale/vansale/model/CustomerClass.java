package com.van.sale.vansale.model;

/**
 * Created by maaz on 02/10/18.
 */

public class CustomerClass {

    private String id,name,customer_name,email_id,mobile_no,tax_id,customer_primary_contact,device_id,is_new,sync_status,server_customer_id,asset;
    private Float credit_limit;

    public CustomerClass() {
    }

    public CustomerClass(String id,String name,String customer_name, String email_id, String mobile_no, String tax_id, String customer_primary_contact, String device_id, Float credit_limit) {
        this.name = name;
        this.id=id;
        this.email_id = email_id;
        this.mobile_no = mobile_no;
        this.tax_id = tax_id;
        this.customer_primary_contact = customer_primary_contact;
        this.device_id = device_id;
        this.credit_limit = credit_limit;
        this.customer_name=customer_name;
    }


    public CustomerClass(String name,String customer_name, String email_id, String mobile_no, String tax_id, String customer_primary_contact, String device_id, String is_new, String sync_status, Float credit_limit,String server_customer_id) {
        this.name = name;
        this.email_id = email_id;
        this.mobile_no = mobile_no;
        this.tax_id = tax_id;
        this.customer_primary_contact = customer_primary_contact;
        this.device_id = device_id;
        this.is_new = is_new;
        this.sync_status = sync_status;
        this.credit_limit = credit_limit;
        this.customer_name=customer_name;
        this.server_customer_id=server_customer_id;
    }

    public CustomerClass(String name,String customer_name, String email_id, String mobile_no, String tax_id, String customer_primary_contact, String device_id, String is_new, String sync_status, Float credit_limit) {

        this.name = name;
        this.email_id = email_id;
        this.mobile_no = mobile_no;
        this.tax_id = tax_id;
        this.customer_primary_contact = customer_primary_contact;
        this.device_id = device_id;
        this.is_new = is_new;
        this.sync_status = sync_status;
        this.credit_limit = credit_limit;
        this.customer_name=customer_name;

    }

    public String getServer_customer_id() {
        return server_customer_id;
    }

    public void setServer_customer_id(String server_customer_id) {
        this.server_customer_id = server_customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getCustomer_primary_contact() {
        return customer_primary_contact;
    }

    public void setCustomer_primary_contact(String customer_primary_contact) {
        this.customer_primary_contact = customer_primary_contact;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getSync_status() {
        return sync_status;
    }

    public void setSync_status(String sync_status) {
        this.sync_status = sync_status;
    }

    public Float getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(Float credit_limit) {
        this.credit_limit = credit_limit;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }
}
