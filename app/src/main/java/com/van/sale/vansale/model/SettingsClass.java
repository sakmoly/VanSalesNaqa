package com.van.sale.vansale.model;

import android.app.IntentService;

/**
 * Created by maaz on 25/09/18.
 */

public class SettingsClass {

private String SETTINGS_DEFAULT_PAYMENT_MODE,SETTINGS_PAID_FROM,tax_account_head,company_name,naming_series_sales_order,naming_series_sales_invoice,
        naming_series_payment,naming_series_transfer,default_currency,device_id,warehouse,setting_access_password,URL_string,API_User_Name,API_Password,
        printer_type,printer_ip_address,printer_port_no,printer_mac_address,naming_series_customer_visit;
private int last_doc_no_sales_order,last_doc_no_sales_invoice,last_doc_no_payment,last_doc_no_transfer,last_doc_no_customer_visit
        ,customer_access,sales_order_access,sales_invoice_access,payment_access,transfer_access,sync_interval;
private Float default_credit_limit_for_new_customer,tax_rate,default_trans_qty;

    public SettingsClass() {
    }

    public SettingsClass(String company_name, String tax_account_head, String naming_series_sales_order, String naming_series_sales_invoice, String naming_series_payment, String naming_series_transfer, String default_currency, String device_id, String warehouse, String setting_access_password, String URL_string, String API_User_Name, String API_Password, int last_doc_no_sales_order, int last_doc_no_sales_invoice, int last_doc_no_payment, int last_doc_no_transfer, int customer_access, int sales_order_access, int sales_invoice_access, int payment_access, int transfer_access, Float default_credit_limit_for_new_customer, Float tax_rate, String SETTINGS_PAID_FROM, String SETTINGS_DEFAULT_PAYMENT_MODE, String printer_type, String printer_ip_address, String printer_port_no, String printer_mac_address, String naming_series_customer_visit, Integer last_doc_no_customer_visit, Integer sync_interval,Float default_trans_qty) {

        this.tax_account_head=tax_account_head;
        this.company_name=company_name;
        this.naming_series_sales_order = naming_series_sales_order;
        this.naming_series_sales_invoice = naming_series_sales_invoice;
        this.naming_series_payment = naming_series_payment;
        this.naming_series_transfer = naming_series_transfer;
        this.default_currency = default_currency;
        this.device_id = device_id;
        this.warehouse = warehouse;
        this.setting_access_password = setting_access_password;
        this.URL_string = URL_string;
        this.API_User_Name = API_User_Name;
        this.API_Password = API_Password;
        this.last_doc_no_sales_order = last_doc_no_sales_order;
        this.last_doc_no_sales_invoice = last_doc_no_sales_invoice;
        this.last_doc_no_payment = last_doc_no_payment;
        this.last_doc_no_transfer = last_doc_no_transfer;
        this.customer_access = customer_access;
        this.sales_order_access = sales_order_access;
        this.sales_invoice_access = sales_invoice_access;
        this.payment_access = payment_access;
        this.transfer_access = transfer_access;
        this.default_credit_limit_for_new_customer = default_credit_limit_for_new_customer;
        this.tax_rate = tax_rate;
        this.SETTINGS_PAID_FROM = SETTINGS_PAID_FROM;
        this.SETTINGS_DEFAULT_PAYMENT_MODE = SETTINGS_DEFAULT_PAYMENT_MODE;
        this.printer_type=printer_type;
        this.printer_ip_address=printer_ip_address;
        this.printer_port_no=printer_port_no;
        this.printer_mac_address=printer_mac_address;
        this.naming_series_customer_visit=naming_series_customer_visit;
        this.last_doc_no_customer_visit=last_doc_no_customer_visit;
        this.sync_interval=sync_interval;
        this.default_trans_qty=default_trans_qty;

    }

    public String getSETTINGS_DEFAULT_PAYMENT_MODE() {
        return SETTINGS_DEFAULT_PAYMENT_MODE;
    }

    public void setSETTINGS_DEFAULT_PAYMENT_MODE(String SETTINGS_DEFAULT_PAYMENT_MODE) {
        this.SETTINGS_DEFAULT_PAYMENT_MODE = SETTINGS_DEFAULT_PAYMENT_MODE;
    }

    public String getSETTINGS_PAID_FROM() {
        return SETTINGS_PAID_FROM;
    }

    public void setSETTINGS_PAID_FROM(String SETTINGS_PAID_FROM) {
        this.SETTINGS_PAID_FROM = SETTINGS_PAID_FROM;
    }

    public String getTax_account_head() {
        return tax_account_head;
    }

    public void setTax_account_head(String tax_account_head) {
        this.tax_account_head = tax_account_head;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getNaming_series_sales_order() {
        return naming_series_sales_order;
    }

    public void setNaming_series_sales_order(String naming_series_sales_order) {
        this.naming_series_sales_order = naming_series_sales_order;
    }

    public String getNaming_series_sales_invoice() {
        return naming_series_sales_invoice;
    }

    public void setNaming_series_sales_invoice(String naming_series_sales_invoice) {
        this.naming_series_sales_invoice = naming_series_sales_invoice;
    }

    public String getNaming_series_payment() {
        return naming_series_payment;
    }

    public void setNaming_series_payment(String naming_series_payment) {
        this.naming_series_payment = naming_series_payment;
    }

    public String getNaming_series_transfer() {
        return naming_series_transfer;
    }

    public void setNaming_series_transfer(String naming_series_transfer) {
        this.naming_series_transfer = naming_series_transfer;
    }

    public String getNaming_series_customer_visit() {
        return naming_series_customer_visit;
    }

    public void setNaming_series_customer_visit(String naming_series_customer_visit) {
        this.naming_series_customer_visit = naming_series_customer_visit;
    }


    public String getDefault_currency() {
        return default_currency;
    }

    public void setDefault_currency(String default_currency) {
        this.default_currency = default_currency;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getSetting_access_password() {
        return setting_access_password;
    }

    public void setSetting_access_password(String setting_access_password) {
        this.setting_access_password = setting_access_password;
    }

    public String getURL_string() {
        return URL_string;
    }

    public void setURL_string(String URL_string) {
        this.URL_string = URL_string;
    }

    public String getAPI_User_Name() {
        return API_User_Name;
    }

    public void setAPI_User_Name(String API_User_Name) {
        this.API_User_Name = API_User_Name;
    }

    public String getAPI_Password() {
        return API_Password;
    }

    public void setAPI_Password(String API_Password) {
        this.API_Password = API_Password;
    }

    public int getLast_doc_no_sales_order() {
        return last_doc_no_sales_order;
    }

    public void setLast_doc_no_sales_order(int last_doc_no_sales_order) {
        this.last_doc_no_sales_order = last_doc_no_sales_order;
    }

    public int getLast_doc_no_sales_invoice() {
        return last_doc_no_sales_invoice;
    }

    public void setLast_doc_no_sales_invoice(int last_doc_no_sales_invoice) {
        this.last_doc_no_sales_invoice = last_doc_no_sales_invoice;
    }

    public int getLast_doc_no_payment() {
        return last_doc_no_payment;
    }

    public void setLast_doc_no_payment(int last_doc_no_payment) {
        this.last_doc_no_payment = last_doc_no_payment;
    }

    public int getLast_doc_no_transfer() {
        return last_doc_no_transfer;
    }

    public void setLast_doc_no_transfer(int last_doc_no_transfer) {
        this.last_doc_no_transfer = last_doc_no_transfer;
    }

    public int getLast_doc_no_customer_visit() {
        return last_doc_no_customer_visit;
    }

    public void setLast_doc_no_customer_visit(int last_doc_no_customer_visit) {
        this.last_doc_no_customer_visit = last_doc_no_customer_visit;
    }

    public int getCustomer_access() {
        return customer_access;
    }

    public void setCustomer_access(int customer_access) {
        this.customer_access = customer_access;
    }

    public int getSales_order_access() {
        return sales_order_access;
    }

    public void setSales_order_access(int sales_order_access) {
        this.sales_order_access = sales_order_access;
    }

    public int getSales_invoice_access() {
        return sales_invoice_access;
    }

    public void setSales_invoice_access(int sales_invoice_access) {
        this.sales_invoice_access = sales_invoice_access;
    }

    public int getPayment_access() {
        return payment_access;
    }

    public void setPayment_access(int payment_access) {
        this.payment_access = payment_access;
    }

    public int getTransfer_access() {
        return transfer_access;
    }

    public void setTransfer_access(int transfer_access) {
        this.transfer_access = transfer_access;
    }

    public Float getDefault_credit_limit_for_new_customer() {
        return default_credit_limit_for_new_customer;
    }

    public void setDefault_credit_limit_for_new_customer(Float default_credit_limit_for_new_customer) {
        this.default_credit_limit_for_new_customer = default_credit_limit_for_new_customer;
    }

    public Float getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(Float tax_rate) {
        this.tax_rate = tax_rate;
    }

    public String getPrinter_type() {
        return printer_type;
    }

    public void setPrinter_type(String printer_type) {this.printer_type = printer_type;}

    public String getPrinter_ip_address() {return printer_ip_address;}

    public void setPrinter_ip_address(String printer_ip_address) {this.printer_ip_address = printer_ip_address;}

    public String getPrinter_port_no() {return printer_port_no;}

    public void setPrinter_port_no(String printer_port_no) {this.printer_port_no = printer_port_no;}

    public String getPrinter_mac_address() {return printer_mac_address;}

    public void setPrinter_mac_address(String printer_mac_address) {this.printer_mac_address = printer_mac_address;}

    public int getSync_interval() {
        return sync_interval;
    }

    public void setSync_interval(int sync_interval) {
        this.sync_interval = sync_interval;
    }

    public void setDefault_trans_qty(Float default_trans_qty) {
        this.default_trans_qty = default_trans_qty;
    }

    public Float getDefault_trans_qty() {
        return default_trans_qty;
    }
}
