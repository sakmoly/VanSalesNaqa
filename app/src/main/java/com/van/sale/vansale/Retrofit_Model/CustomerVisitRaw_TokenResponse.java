package com.van.sale.vansale.Retrofit_Model;

public class CustomerVisitRaw_TokenResponse {
    private String reference;
    private String naming_series;
    private String creation;
    private String owner;
    private String modified_by;
    private String doc_no;
    //private String _user_tags;
    //private String parenttype;
    private String latitude;
    private Integer docstatus;
    //private String _liked_by;
   // private String parent;
    //private String _assign;
    private String _comments;
    private String customer;
    //private String name;
    private Integer idx;
    private Float amount;
    private String visit_result;
    private String modified;
    private String longitude;
    private String visit_date;
    private String sales_person;
   // private String parentfield;

    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
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

    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getNaming_series() {
        return naming_series;
    }
    public void setNaming_series(String naming_series) {
        this.naming_series = naming_series;
    }

    public String getModified_by() {
        return modified_by;
    }
    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public Integer getDocstatus() {
        return docstatus;
    }
    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getComments() {
        return _comments;
    }
    public void setComments(String _comments) {
        this._comments = _comments;
    }

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Float getAmount() {
        return amount;
    }
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getVisit_result() {
        return visit_result;
    }
    public void setVisit_result(String visit_result) {
        this.visit_result = visit_result;
    }

    public String getModified() {
        return modified;
    }
    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getVisit_date() {
        return visit_date;
    }
    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public Integer getIdx() {
        return idx;
    }
    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getSales_person() {
        return sales_person;
    }
    public void setSales_person(String sales_person) {
        this.sales_person = sales_person;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }
}
