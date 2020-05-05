package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maaz on 04/10/18.
 */

public class CustomerSyncData {

    @SerializedName("customer_details")
    @Expose
    private Object customerDetails;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("naming_series")
    @Expose
    private String namingSeries;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("creation")
    @Expose
    private String creation;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("disabled")
    @Expose
    private Integer disabled;
    @SerializedName("salutation")
    @Expose
    private Object salutation;
    @SerializedName("accounts")
    @Expose
    private List<Object> accounts = null;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("lead_name")
    @Expose
    private Object leadName;
    @SerializedName("bypass_credit_limit_check_at_sales_order")
    @Expose
    private Integer bypassCreditLimitCheckAtSalesOrder;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("default_currency")
    @Expose
    private Object defaultCurrency;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("tax_id")
    @Expose
    private String taxId;
    @SerializedName("default_sales_partner")
    @Expose
    private Object defaultSalesPartner;
    @SerializedName("parenttype")
    @Expose
    private Object parenttype;
    @SerializedName("payment_terms")
    @Expose
    private Object paymentTerms;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("territory")
    @Expose
    private String territory;
    @SerializedName("customer_title")
    @Expose
    private Object customerTitle;
    @SerializedName("website")
    @Expose
    private Object website;
    @SerializedName("customer_name_in_arabic")
    @Expose
    private Object customerNameInArabic;
    @SerializedName("parent")
    @Expose
    private Object parent;
    @SerializedName("default_commission_rate")
    @Expose
    private Integer defaultCommissionRate;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("customer_primary_contact")
    @Expose
    private String customerPrimaryContact;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("credit_limit")
    @Expose
    private Integer creditLimit;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("customer_group")
    @Expose
    private String customerGroup;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("customer_type")
    @Expose
    private String customerType;
    @SerializedName("default_price_list")
    @Expose
    private Object defaultPriceList;
    @SerializedName("is_frozen")
    @Expose
    private Integer isFrozen;
    @SerializedName("customer_pos_id")
    @Expose
    private Object customerPosId;
    @SerializedName("sales_team")
    @Expose
    private List<Object> salesTeam = null;
    @SerializedName("parentfield")
    @Expose
    private Object parentfield;

    public Object getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(Object customerDetails) {
        this.customerDetails = customerDetails;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getNamingSeries() {
        return namingSeries;
    }

    public void setNamingSeries(String namingSeries) {
        this.namingSeries = namingSeries;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Object getSalutation() {
        return salutation;
    }

    public void setSalutation(Object salutation) {
        this.salutation = salutation;
    }

    public List<Object> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Object> accounts) {
        this.accounts = accounts;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Object getLeadName() {
        return leadName;
    }

    public void setLeadName(Object leadName) {
        this.leadName = leadName;
    }

    public Integer getBypassCreditLimitCheckAtSalesOrder() {
        return bypassCreditLimitCheckAtSalesOrder;
    }

    public void setBypassCreditLimitCheckAtSalesOrder(Integer bypassCreditLimitCheckAtSalesOrder) {
        this.bypassCreditLimitCheckAtSalesOrder = bypassCreditLimitCheckAtSalesOrder;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Object getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Object defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public Object getDefaultSalesPartner() {
        return defaultSalesPartner;
    }

    public void setDefaultSalesPartner(Object defaultSalesPartner) {
        this.defaultSalesPartner = defaultSalesPartner;
    }

    public Object getParenttype() {
        return parenttype;
    }

    public void setParenttype(Object parenttype) {
        this.parenttype = parenttype;
    }

    public Object getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(Object paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public Object getCustomerTitle() {
        return customerTitle;
    }

    public void setCustomerTitle(Object customerTitle) {
        this.customerTitle = customerTitle;
    }

    public Object getWebsite() {
        return website;
    }

    public void setWebsite(Object website) {
        this.website = website;
    }

    public Object getCustomerNameInArabic() {
        return customerNameInArabic;
    }

    public void setCustomerNameInArabic(Object customerNameInArabic) {
        this.customerNameInArabic = customerNameInArabic;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public Integer getDefaultCommissionRate() {
        return defaultCommissionRate;
    }

    public void setDefaultCommissionRate(Integer defaultCommissionRate) {
        this.defaultCommissionRate = defaultCommissionRate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCustomerPrimaryContact() {
        return customerPrimaryContact;
    }

    public void setCustomerPrimaryContact(String customerPrimaryContact) {
        this.customerPrimaryContact = customerPrimaryContact;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Object getDefaultPriceList() {
        return defaultPriceList;
    }

    public void setDefaultPriceList(Object defaultPriceList) {
        this.defaultPriceList = defaultPriceList;
    }

    public Integer getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(Integer isFrozen) {
        this.isFrozen = isFrozen;
    }

    public Object getCustomerPosId() {
        return customerPosId;
    }

    public void setCustomerPosId(Object customerPosId) {
        this.customerPosId = customerPosId;
    }

    public List<Object> getSalesTeam() {
        return salesTeam;
    }

    public void setSalesTeam(List<Object> salesTeam) {
        this.salesTeam = salesTeam;
    }

    public Object getParentfield() {
        return parentfield;
    }

    public void setParentfield(Object parentfield) {
        this.parentfield = parentfield;
    }

}
