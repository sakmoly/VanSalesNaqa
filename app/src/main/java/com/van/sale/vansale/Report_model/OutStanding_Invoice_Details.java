package com.van.sale.vansale.Report_model;

public class OutStanding_Invoice_Details {

    private String invoice_no,customer_id,freezer_no;
    private Float invoice_amt;
    public OutStanding_Invoice_Details(){

    }
    public OutStanding_Invoice_Details(String customer_id,Float invoice_amt,String freezer_no,String invoice_no){
       this.customer_id=customer_id;
       this.invoice_amt=invoice_amt;
       this.freezer_no=freezer_no;
       this.invoice_no=invoice_no;
    }
    public String getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public Float getInvoice_amt() {
        return invoice_amt;
    }

    public void setInvoice_amt(Float invoice_amt) {
        this.invoice_amt = invoice_amt;
    }

    public String getFreezer_no() {
        return freezer_no;
    }

    public void setFreezer_no(String freezer_no) {
        this.freezer_no = freezer_no;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }
}
