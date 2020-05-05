package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModeOfPaymentByNameData {

    @SerializedName("mode_of_payment")
    @Expose
    private String modeOfPayment;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("name")
    @Expose
    private String name;
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
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("accounts")
    @Expose
    private List<ModeOfPaymentByNameAccount> accounts = null;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("type")
    @Expose
    private String type;

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public List<ModeOfPaymentByNameAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<ModeOfPaymentByNameAccount> accounts) {
        this.accounts = accounts;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}
