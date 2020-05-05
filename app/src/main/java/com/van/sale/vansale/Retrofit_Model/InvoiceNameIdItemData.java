package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvoiceNameIdItemData {


   /* @SerializedName("doc_no")
    @Expose
    private String docNo;
    @SerializedName("base_write_off_amount")
    @Expose
    private Integer baseWriteOffAmount;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("packed_items")
    @Expose
    private List<Object> packedItems = null;
    @SerializedName("selling_price_list")
    @Expose
    private String sellingPriceList;
    @SerializedName("set_posting_time")
    @Expose
    private Integer setPostingTime;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("base_in_words")
    @Expose
    private String baseInWords;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("ignore_pricing_rule")
    @Expose
    private Integer ignorePricingRule;
    @SerializedName("advances")
    @Expose
    private List<Object> advances = null;
    @SerializedName("base_discount_amount")
    @Expose
    private Integer baseDiscountAmount;
    @SerializedName("base_total_taxes_and_charges")
    @Expose
    private Integer baseTotalTaxesAndCharges;
    @SerializedName("commission_rate")
    @Expose
    private Integer commissionRate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_return")
    @Expose
    private Integer isReturn;
    @SerializedName("write_off_outstanding_amount_automatically")
    @Expose
    private Integer writeOffOutstandingAmountAutomatically;
    @SerializedName("taxes")
    @Expose
    private List<Tax> taxes = null;
    @SerializedName("base_rounding_adjustment")
    @Expose
    private Integer baseRoundingAdjustment;
    @SerializedName("is_pos")
    @Expose
    private Integer isPos;
    @SerializedName("against_income_account")
    @Expose
    private String againstIncomeAccount;
    @SerializedName("write_off_amount")
    @Expose
    private Integer writeOffAmount;
    @SerializedName("creation")
    @Expose
    private String creation;
    @SerializedName("party_account_currency")
    @Expose
    private String partyAccountCurrency;
    @SerializedName("net_total")
    @Expose
    private Integer netTotal;*/
    @SerializedName("items")
    @Expose
    private List<InvoiceNameIdItemDataList> items = null;
    /*@SerializedName("total_advance")
    @Expose
    private Integer totalAdvance;
    @SerializedName("price_list_currency")
    @Expose
    private String priceListCurrency;
    @SerializedName("timesheets")
    @Expose
    private List<Object> timesheets = null;
    @SerializedName("payments")
    @Expose
    private List<Object> payments = null;
    @SerializedName("is_opening")
    @Expose
    private String isOpening;
    @SerializedName("total_commission")
    @Expose
    private Integer totalCommission;
    @SerializedName("base_net_total")
    @Expose
    private Integer baseNetTotal;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("rounded_total")
    @Expose
    private Integer roundedTotal;
    @SerializedName("shipping_address_name")
    @Expose
    private String shippingAddressName;
    @SerializedName("apply_discount_on")
    @Expose
    private String applyDiscountOn;
    @SerializedName("in_words")
    @Expose
    private String inWords;
    @SerializedName("additional_discount_percentage")
    @Expose
    private Integer additionalDiscountPercentage;
    @SerializedName("base_paid_amount")
    @Expose
    private Integer basePaidAmount;
    @SerializedName("conversion_rate")
    @Expose
    private Integer conversionRate;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("posting_time")
    @Expose
    private String postingTime;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("update_stock")
    @Expose
    private Integer updateStock;
    @SerializedName("base_total")
    @Expose
    private Integer baseTotal;
    @SerializedName("c_form_applicable")
    @Expose
    private String cFormApplicable;
    @SerializedName("territory")
    @Expose
    private String territory;
    @SerializedName("update_billed_amount_in_sales_order")
    @Expose
    private Integer updateBilledAmountInSalesOrder;
    @SerializedName("other_charges_calculation")
    @Expose
    private String otherChargesCalculation;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("base_rounded_total")
    @Expose
    private Integer baseRoundedTotal;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("reverse_charge_applicable")
    @Expose
    private String reverseChargeApplicable;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("rounding_adjustment")
    @Expose
    private Integer roundingAdjustment;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("customer_group")
    @Expose
    private String customerGroup;
    @SerializedName("naming_series")
    @Expose
    private String namingSeries;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("letter_head")
    @Expose
    private String letterHead;
    @SerializedName("paid_amount")
    @Expose
    private Integer paidAmount;
    @SerializedName("debit_to")
    @Expose
    private String debitTo;
    @SerializedName("base_change_amount")
    @Expose
    private Integer baseChangeAmount;
    @SerializedName("base_grand_total")
    @Expose
    private Integer baseGrandTotal;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_billing_amount")
    @Expose
    private Integer totalBillingAmount;
    @SerializedName("group_same_items")
    @Expose
    private Integer groupSameItems;
    @SerializedName("outstanding_amount")
    @Expose
    private Integer outstandingAmount;
    @SerializedName("change_amount")
    @Expose
    private Integer changeAmount;
    @SerializedName("total_net_weight")
    @Expose
    private Integer totalNetWeight;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("payment_schedule")
    @Expose
    private List<PaymentSchedule> paymentSchedule = null;
    @SerializedName("plc_conversion_rate")
    @Expose
    private Integer plcConversionRate;
    @SerializedName("total_taxes_and_charges")
    @Expose
    private Integer totalTaxesAndCharges;
    @SerializedName("sales_team")
    @Expose
    private List<Object> salesTeam = null;*/

    public List<InvoiceNameIdItemDataList> getItems() {
        return items;
    }

    public void setItems(List<InvoiceNameIdItemDataList> items) {
        this.items = items;
    }
}
