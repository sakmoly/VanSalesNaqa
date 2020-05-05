package com.van.sale.vansale.Report_model;

import java.util.List;

public class Daily_Sales_Collection_Report {
    private String from_date,to_date,warehouse,sales_man;
    private Float sales_amount,vat_amount,sales_total_with_vat,pending_collection,receved_amount,total_collection;
    private List<OutStanding_Invoice_Details> outStanding_invoice_detailsList;
    private List<Outstanding_Collection_Details> outstanding_collection_details;
    private List<Item_Return_Details> item_return_details;
    public  Daily_Sales_Collection_Report(){

    }


    public String getFrom_date() {
        return from_date;
    }
    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getSales_man() {
        return sales_man;
    }

    public void setSales_man(String sales_man) {
        this.sales_man = sales_man;
    }

    public Float getSales_amount() {
        return sales_amount;
    }

    public void setSales_amount(Float sales_amount) {
        this.sales_amount = sales_amount;
    }

    public Float getVat_amount() {
        return vat_amount;
    }
    public void setVat_amount(Float vat_amount) {
        this.vat_amount = vat_amount;
    }

    public Float getSales_total_with_vat() {
        return sales_total_with_vat;
    }
    public void setSales_total_with_vat(Float sales_total_with_vat) {
        this.sales_total_with_vat = sales_total_with_vat;
    }

    public Float getPending_collection() {
        return pending_collection;
    }
    public void setPending_collection(Float pending_collection) {
        this.pending_collection = pending_collection;
    }

    public Float getReceved_amount() {
        return receved_amount;
    }

    public void setReceved_amount(Float receved_amount) {
        this.receved_amount = receved_amount;
    }

    public Float getTotal_collection() {
        return total_collection;
    }

    public void setTotal_collection(Float total_collection) {
        this.total_collection = total_collection;
    }

    public List<OutStanding_Invoice_Details> getOutStanding_invoice_detailsList() {
        return outStanding_invoice_detailsList;
    }

    public void setOutStanding_invoice_detailsList(List<OutStanding_Invoice_Details> outStanding_invoice_detailsList) {
        this.outStanding_invoice_detailsList = outStanding_invoice_detailsList;
    }

    public List<Item_Return_Details> getItem_return_details() {
        return item_return_details;
    }

    public void setItem_return_details(List<Item_Return_Details> item_return_details) {
        this.item_return_details = item_return_details;
    }

    public List<Outstanding_Collection_Details> getOutstanding_collection_details() {
        return outstanding_collection_details;
    }

    public void setOutstanding_collection_details(List<Outstanding_Collection_Details> outstanding_collection_details) {
        this.outstanding_collection_details = outstanding_collection_details;
    }
}

