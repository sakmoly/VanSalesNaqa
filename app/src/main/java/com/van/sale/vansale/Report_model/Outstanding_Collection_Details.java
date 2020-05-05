package com.van.sale.vansale.Report_model;

public class Outstanding_Collection_Details {
    private String receipt_no,customer_id,freezer_no;
    private Float receipt_amt;
    public Outstanding_Collection_Details(){

    }
    public Outstanding_Collection_Details(String receipt_no,String customer_id,Float receipt_amt){
        this.receipt_no=receipt_no;
        this.customer_id=customer_id;
        this.receipt_amt=receipt_amt;

    }

    public String getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public Float getReceipt_amt() {
        return receipt_amt;
    }

    public void setReceipt_amt(Float receipt_amt) {
        this.receipt_amt = receipt_amt;
    }

    public String getFreezer_no() {
        return freezer_no;
    }

    public void setFreezer_no(String freezer_no) {
        this.freezer_no = freezer_no;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }
}
