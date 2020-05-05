package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModeOfPaymentByNameAccount {


    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parent")
    @Expose
    private String parent;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("creation")
    @Expose
    private String creation;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("parenttype")
    @Expose
    private String parenttype;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("default_account")
    @Expose
    private String defaultAccount;
    @SerializedName("parentfield")
    @Expose
    private String parentfield;

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getParenttype() {
        return parenttype;
    }

    public void setParenttype(String parenttype) {
        this.parenttype = parenttype;
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

    public String getDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(String defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    public String getParentfield() {
        return parentfield;
    }

    public void setParentfield(String parentfield) {
        this.parentfield = parentfield;
    }

}
