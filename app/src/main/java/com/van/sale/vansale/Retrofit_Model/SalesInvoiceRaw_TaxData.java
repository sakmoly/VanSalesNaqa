package com.van.sale.vansale.Retrofit_Model;


public class SalesInvoiceRaw_TaxData {

    private String account_head;
    private String description;
    private String charge_type;
    private String total;
    private String tax_amount;
    private String rate;
    private String owner,modified_by;

    public SalesInvoiceRaw_TaxData(String account_head, String description, String charge_type, String total, String tax_amount, String rate,String owner,String modified_by) {
        this.account_head = account_head;
        this.description = description;
        this.charge_type = charge_type;
        this.total = total;
        this.tax_amount = tax_amount;
        this.rate = rate;
        this.owner=owner;
        this.modified_by=modified_by;
    }

    public String getAccount_head() {
        return account_head;
    }

    public void setAccount_head(String account_head) {
        this.account_head = account_head;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(String tax_amount) {
        this.tax_amount = tax_amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }


}
