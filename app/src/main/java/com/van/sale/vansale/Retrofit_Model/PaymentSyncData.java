package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentSyncData {

    @SerializedName("total_allocated_amount")
    @Expose
    private String totalAllocatedAmount;
    @SerializedName("naming_series")
    @Expose
    private String namingSeries;



   /* private String modeOfPayment;
    @SerializedName("target_exchange_rate")
    @Expose
    private String targetExchangeRate;
    @SerializedName("paid_to")
    @Expose
    private String paidTo;
    @SerializedName("base_paid_amount")
    @Expose
    private String basePaidAmount;
    @SerializedName("paid_to_account_currency")
    @Expose
    private String paidToAccountCurrency;
    @SerializedName("reference_date")
    @Expose
    private String referenceDate;
    @SerializedName("letter_head")
    @Expose
    private String letterHead;
    @SerializedName("reference_no")
    @Expose
    private String referenceNo;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("print_heading")
    @Expose
    private String printHeading;
    @SerializedName("unallocated_amount")
    @Expose
    private String unallocatedAmount;
    @SerializedName("allocate_payment_amount")
    @Expose
    private String allocatePaymentAmount;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("party_type")
    @Expose
    private String partyType;
    @SerializedName("amended_from")
    @Expose
    private String amendedFrom;
    @SerializedName("base_total_allocated_amount")
    @Expose
    private String baseTotalAllocatedAmount;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("receipt_no")
    @Expose
    private String receiptNo;
    @SerializedName("party")
    @Expose
    private String party;
    @SerializedName("base_received_amount")
    @Expose
    private String baseReceivedAmount;
    @SerializedName("subscription")
    @Expose
    private String subscription;
    @SerializedName("source_exchange_rate")
    @Expose
    private String sourceExchangeRate;
    @SerializedName("creation")
    @Expose
    private String creation;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("paid_from_account_balance")
    @Expose
    private String paidFromAccountBalance;
    @SerializedName("parent")
    @Expose
    private Object parent;
    @SerializedName("whs_abbrev")
    @Expose
    private Object whsAbbrev;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("paid_from")
    @Expose
    private String paidFrom;
    @SerializedName("party_balance")
    @Expose
    private Double partyBalance;
    @SerializedName("party_name")
    @Expose
    private String partyName;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("clearance_date")
    @Expose
    private Object clearanceDate;
    @SerializedName("user_default_warehouse")
    @Expose
    private Object userDefaultWarehouse;
    @SerializedName("device_id")
    @Expose
    private Object deviceId;
    @SerializedName("paid_from_account_currency")
    @Expose
    private String paidFromAccountCurrency;
    @SerializedName("paid_to_account_balance")
    @Expose
    private Integer paidToAccountBalance;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("difference_amount")
    @Expose
    private Integer differenceAmount;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("received_amount")
    @Expose
    private Integer receivedAmount;
    @SerializedName("project")
    @Expose
    private Object project;
    @SerializedName("parenttype")
    @Expose
    private Object parenttype;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("parentfield")
    @Expose
    private Object parentfield;*/

    public String getTotalAllocatedAmount() {
        return totalAllocatedAmount;
    }

    public void setTotalAllocatedAmount(String totalAllocatedAmount) {
        this.totalAllocatedAmount = totalAllocatedAmount;
    }

    public String getNamingSeries() {
        return namingSeries;
    }

    public void setNamingSeries(String namingSeries) {
        this.namingSeries = namingSeries;
    }
}
