package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maaz on 04/10/18.
 */

public class AddressSyncData {


    @SerializedName("links")
    @Expose
    private List<Object> links = null;
    @SerializedName("email_id")
    @Expose
    private Object emailId;
    @SerializedName("creation")
    @Expose
    private String creation;
    @SerializedName("pincode")
    @Expose
    private Object pincode;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("county")
    @Expose
    private Object county;
    @SerializedName("is_your_company_address")
    @Expose
    private Integer isYourCompanyAddress;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("address_line2")
    @Expose
    private String addressLine2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("address_line1")
    @Expose
    private String addressLine1;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("address_title")
    @Expose
    private String addressTitle;
    @SerializedName("is_primary_address")
    @Expose
    private Integer isPrimaryAddress;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("address_type")
    @Expose
    private String addressType;
    @SerializedName("fax")
    @Expose
    private Object fax;
    @SerializedName("parent")
    @Expose
    private Object parent;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("is_shipping_address")
    @Expose
    private Integer isShippingAddress;
    @SerializedName("parenttype")
    @Expose
    private Object parenttype;
    @SerializedName("parentfield")
    @Expose
    private Object parentfield;

    public List<Object> getLinks() {
        return links;
    }

    public void setLinks(List<Object> links) {
        this.links = links;
    }

    public Object getEmailId() {
        return emailId;
    }

    public void setEmailId(Object emailId) {
        this.emailId = emailId;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public Object getPincode() {
        return pincode;
    }

    public void setPincode(Object pincode) {
        this.pincode = pincode;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public Object getCounty() {
        return county;
    }

    public void setCounty(Object county) {
        this.county = county;
    }

    public Integer getIsYourCompanyAddress() {
        return isYourCompanyAddress;
    }

    public void setIsYourCompanyAddress(Integer isYourCompanyAddress) {
        this.isYourCompanyAddress = isYourCompanyAddress;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public Integer getIsPrimaryAddress() {
        return isPrimaryAddress;
    }

    public void setIsPrimaryAddress(Integer isPrimaryAddress) {
        this.isPrimaryAddress = isPrimaryAddress;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Object getFax() {
        return fax;
    }

    public void setFax(Object fax) {
        this.fax = fax;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Integer getIsShippingAddress() {
        return isShippingAddress;
    }

    public void setIsShippingAddress(Integer isShippingAddress) {
        this.isShippingAddress = isShippingAddress;
    }

    public Object getParenttype() {
        return parenttype;
    }

    public void setParenttype(Object parenttype) {
        this.parenttype = parenttype;
    }

    public Object getParentfield() {
        return parentfield;
    }

    public void setParentfield(Object parentfield) {
        this.parentfield = parentfield;
    }

}
