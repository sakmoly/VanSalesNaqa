package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesOrderSyncData {

    @SerializedName("billing_status")
    @Expose
    private String billingStatus;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("title")
    @Expose
    private String title;
   /* @SerializedName("packed_items")
    @Expose
    private List<Object> packedItems = null;*/
    @SerializedName("selling_price_list")
    @Expose
    private String sellingPriceList;
    @SerializedName("tc_name")
    @Expose
    private Object tcName;
    @SerializedName("source")
    @Expose
    private Object source;
    @SerializedName("base_grand_total")
    @Expose
    private Integer baseGrandTotal;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("tax_id")
    @Expose
    private Object taxId;
    @SerializedName("base_in_words")
    @Expose
    private String baseInWords;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("ignore_pricing_rule")
    @Expose
    private Integer ignorePricingRule;
    @SerializedName("base_discount_amount")
    @Expose
    private Integer baseDiscountAmount;
    @SerializedName("base_total_taxes_and_charges")
    @Expose
    private Integer baseTotalTaxesAndCharges;
   /* @SerializedName("items")
    @Expose
    private List<Item> items = null;*/
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("name")
    @Expose
    private String name;
   /* @SerializedName("taxes")
    @Expose
    private List<Tax> taxes = null;*/
    @SerializedName("select_print_heading")
    @Expose
    private Object selectPrintHeading;
    @SerializedName("base_rounding_adjustment")
    @Expose
    private Integer baseRoundingAdjustment;
    @SerializedName("permit_no")
    @Expose
    private Object permitNo;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("parentfield")
    @Expose
    private Object parentfield;
    @SerializedName("creation")
    @Expose
    private String creation;
    @SerializedName("party_account_currency")
    @Expose
    private Object partyAccountCurrency;
    @SerializedName("net_total")
    @Expose
    private Integer netTotal;
    @SerializedName("company_trn")
    @Expose
    private Object companyTrn;
    @SerializedName("po_date")
    @Expose
    private Object poDate;
    @SerializedName("price_list_currency")
    @Expose
    private String priceListCurrency;
    @SerializedName("contact_display")
    @Expose
    private String contactDisplay;
    @SerializedName("terms")
    @Expose
    private Object terms;
    @SerializedName("parent")
    @Expose
    private Object parent;
    @SerializedName("advance_paid")
    @Expose
    private Integer advancePaid;
    @SerializedName("customer_address")
    @Expose
    private Object customerAddress;
    @SerializedName("total_commission")
    @Expose
    private Integer totalCommission;
    @SerializedName("contact_mobile")
    @Expose
    private Object contactMobile;
    @SerializedName("delivery_status")
    @Expose
    private String deliveryStatus;
    @SerializedName("base_net_total")
    @Expose
    private Integer baseNetTotal;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("rounded_total")
    @Expose
    private Integer roundedTotal;
    @SerializedName("shipping_address_name")
    @Expose
    private String shippingAddressName;
    @SerializedName("apply_discount_on")
    @Expose
    private String applyDiscountOn;
    @SerializedName("po_no")
    @Expose
    private String poNo;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("in_words")
    @Expose
    private String inWords;
    @SerializedName("additional_discount_percentage")
    @Expose
    private Integer additionalDiscountPercentage;
    @SerializedName("campaign")
    @Expose
    private Object campaign;
    @SerializedName("conversion_rate")
    @Expose
    private Integer conversionRate;
    @SerializedName("to_date")
    @Expose
    private Object toDate;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("payment_terms_template")
    @Expose
    private Object paymentTermsTemplate;
    @SerializedName("commission_rate")
    @Expose
    private Integer commissionRate;
    @SerializedName("base_total")
    @Expose
    private Integer baseTotal;
    @SerializedName("from_date")
    @Expose
    private Object fromDate;
    @SerializedName("territory")
    @Expose
    private String territory;
    @SerializedName("sales_partner")
    @Expose
    private Object salesPartner;
    @SerializedName("other_charges_calculation")
    @Expose
    private String otherChargesCalculation;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("base_rounded_total")
    @Expose
    private Integer baseRoundedTotal;
    @SerializedName("subscription")
    @Expose
    private Object subscription;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("reverse_charge_applicable")
    @Expose
    private String reverseChargeApplicable;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("project")
    @Expose
    private Object project;
    @SerializedName("rounding_adjustment")
    @Expose
    private Integer roundingAdjustment;
    @SerializedName("shipping_address")
    @Expose
    private Object shippingAddress;
    @SerializedName("customer_group")
    @Expose
    private String customerGroup;
    @SerializedName("address_display")
    @Expose
    private Object addressDisplay;
    @SerializedName("naming_series")
    @Expose
    private String namingSeries;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("letter_head")
    @Expose
    private String letterHead;
    @SerializedName("shipping_rule")
    @Expose
    private Object shippingRule;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("amended_from")
    @Expose
    private Object amendedFrom;
    @SerializedName("transaction_date")
    @Expose
    private String transactionDate;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("per_delivered")
    @Expose
    private Integer perDelivered;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("group_same_items")
    @Expose
    private Integer groupSameItems;
    @SerializedName("customer_name_in_arabic")
    @Expose
    private Object customerNameInArabic;
    @SerializedName("taxes_and_charges")
    @Expose
    private Object taxesAndCharges;
    @SerializedName("per_billed")
    @Expose
    private Integer perBilled;
    @SerializedName("total_net_weight")
    @Expose
    private Integer totalNetWeight;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
   /* @SerializedName("payment_schedule")
    @Expose
    private List<PaymentSchedule> paymentSchedule = null;*/
    @SerializedName("plc_conversion_rate")
    @Expose
    private Integer plcConversionRate;
    @SerializedName("parenttype")
    @Expose
    private Object parenttype;
    @SerializedName("total_taxes_and_charges")
    @Expose
    private Integer totalTaxesAndCharges;
    @SerializedName("contact_email")
    @Expose
    private Object contactEmail;
   /* @SerializedName("sales_team")
    @Expose
    private List<Object> salesTeam = null;*/

    public String getBillingStatus() {
        return billingStatus;
    }

    public void setBillingStatus(String billingStatus) {
        this.billingStatus = billingStatus;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   /* public List<Object> getPackedItems() {
        return packedItems;
    }

    public void setPackedItems(List<Object> packedItems) {
        this.packedItems = packedItems;
    }*/

    public String getSellingPriceList() {
        return sellingPriceList;
    }

    public void setSellingPriceList(String sellingPriceList) {
        this.sellingPriceList = sellingPriceList;
    }

    public Object getTcName() {
        return tcName;
    }

    public void setTcName(Object tcName) {
        this.tcName = tcName;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Integer getBaseGrandTotal() {
        return baseGrandTotal;
    }

    public void setBaseGrandTotal(Integer baseGrandTotal) {
        this.baseGrandTotal = baseGrandTotal;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getTaxId() {
        return taxId;
    }

    public void setTaxId(Object taxId) {
        this.taxId = taxId;
    }

    public String getBaseInWords() {
        return baseInWords;
    }

    public void setBaseInWords(String baseInWords) {
        this.baseInWords = baseInWords;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public Integer getIgnorePricingRule() {
        return ignorePricingRule;
    }

    public void setIgnorePricingRule(Integer ignorePricingRule) {
        this.ignorePricingRule = ignorePricingRule;
    }

    public Integer getBaseDiscountAmount() {
        return baseDiscountAmount;
    }

    public void setBaseDiscountAmount(Integer baseDiscountAmount) {
        this.baseDiscountAmount = baseDiscountAmount;
    }

    public Integer getBaseTotalTaxesAndCharges() {
        return baseTotalTaxesAndCharges;
    }

    public void setBaseTotalTaxesAndCharges(Integer baseTotalTaxesAndCharges) {
        this.baseTotalTaxesAndCharges = baseTotalTaxesAndCharges;
    }

   /* public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }*/

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
    }*/

    public Object getSelectPrintHeading() {
        return selectPrintHeading;
    }

    public void setSelectPrintHeading(Object selectPrintHeading) {
        this.selectPrintHeading = selectPrintHeading;
    }

    public Integer getBaseRoundingAdjustment() {
        return baseRoundingAdjustment;
    }

    public void setBaseRoundingAdjustment(Integer baseRoundingAdjustment) {
        this.baseRoundingAdjustment = baseRoundingAdjustment;
    }

    public Object getPermitNo() {
        return permitNo;
    }

    public void setPermitNo(Object permitNo) {
        this.permitNo = permitNo;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Object getParentfield() {
        return parentfield;
    }

    public void setParentfield(Object parentfield) {
        this.parentfield = parentfield;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public Object getPartyAccountCurrency() {
        return partyAccountCurrency;
    }

    public void setPartyAccountCurrency(Object partyAccountCurrency) {
        this.partyAccountCurrency = partyAccountCurrency;
    }

    public Integer getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(Integer netTotal) {
        this.netTotal = netTotal;
    }

    public Object getCompanyTrn() {
        return companyTrn;
    }

    public void setCompanyTrn(Object companyTrn) {
        this.companyTrn = companyTrn;
    }

    public Object getPoDate() {
        return poDate;
    }

    public void setPoDate(Object poDate) {
        this.poDate = poDate;
    }

    public String getPriceListCurrency() {
        return priceListCurrency;
    }

    public void setPriceListCurrency(String priceListCurrency) {
        this.priceListCurrency = priceListCurrency;
    }

    public String getContactDisplay() {
        return contactDisplay;
    }

    public void setContactDisplay(String contactDisplay) {
        this.contactDisplay = contactDisplay;
    }

    public Object getTerms() {
        return terms;
    }

    public void setTerms(Object terms) {
        this.terms = terms;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public Integer getAdvancePaid() {
        return advancePaid;
    }

    public void setAdvancePaid(Integer advancePaid) {
        this.advancePaid = advancePaid;
    }

    public Object getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Object customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Integer getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Integer totalCommission) {
        this.totalCommission = totalCommission;
    }

    public Object getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(Object contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Integer getBaseNetTotal() {
        return baseNetTotal;
    }

    public void setBaseNetTotal(Integer baseNetTotal) {
        this.baseNetTotal = baseNetTotal;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getRoundedTotal() {
        return roundedTotal;
    }

    public void setRoundedTotal(Integer roundedTotal) {
        this.roundedTotal = roundedTotal;
    }

    public String getShippingAddressName() {
        return shippingAddressName;
    }

    public void setShippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    public String getApplyDiscountOn() {
        return applyDiscountOn;
    }

    public void setApplyDiscountOn(String applyDiscountOn) {
        this.applyDiscountOn = applyDiscountOn;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getInWords() {
        return inWords;
    }

    public void setInWords(String inWords) {
        this.inWords = inWords;
    }

    public Integer getAdditionalDiscountPercentage() {
        return additionalDiscountPercentage;
    }

    public void setAdditionalDiscountPercentage(Integer additionalDiscountPercentage) {
        this.additionalDiscountPercentage = additionalDiscountPercentage;
    }

    public Object getCampaign() {
        return campaign;
    }

    public void setCampaign(Object campaign) {
        this.campaign = campaign;
    }

    public Integer getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Integer conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Object getToDate() {
        return toDate;
    }

    public void setToDate(Object toDate) {
        this.toDate = toDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Object getPaymentTermsTemplate() {
        return paymentTermsTemplate;
    }

    public void setPaymentTermsTemplate(Object paymentTermsTemplate) {
        this.paymentTermsTemplate = paymentTermsTemplate;
    }

    public Integer getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Integer commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Integer getBaseTotal() {
        return baseTotal;
    }

    public void setBaseTotal(Integer baseTotal) {
        this.baseTotal = baseTotal;
    }

    public Object getFromDate() {
        return fromDate;
    }

    public void setFromDate(Object fromDate) {
        this.fromDate = fromDate;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public Object getSalesPartner() {
        return salesPartner;
    }

    public void setSalesPartner(Object salesPartner) {
        this.salesPartner = salesPartner;
    }

    public String getOtherChargesCalculation() {
        return otherChargesCalculation;
    }

    public void setOtherChargesCalculation(String otherChargesCalculation) {
        this.otherChargesCalculation = otherChargesCalculation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getBaseRoundedTotal() {
        return baseRoundedTotal;
    }

    public void setBaseRoundedTotal(Integer baseRoundedTotal) {
        this.baseRoundedTotal = baseRoundedTotal;
    }

    public Object getSubscription() {
        return subscription;
    }

    public void setSubscription(Object subscription) {
        this.subscription = subscription;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getReverseChargeApplicable() {
        return reverseChargeApplicable;
    }

    public void setReverseChargeApplicable(String reverseChargeApplicable) {
        this.reverseChargeApplicable = reverseChargeApplicable;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getProject() {
        return project;
    }

    public void setProject(Object project) {
        this.project = project;
    }

    public Integer getRoundingAdjustment() {
        return roundingAdjustment;
    }

    public void setRoundingAdjustment(Integer roundingAdjustment) {
        this.roundingAdjustment = roundingAdjustment;
    }

    public Object getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Object shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public Object getAddressDisplay() {
        return addressDisplay;
    }

    public void setAddressDisplay(Object addressDisplay) {
        this.addressDisplay = addressDisplay;
    }

    public String getNamingSeries() {
        return namingSeries;
    }

    public void setNamingSeries(String namingSeries) {
        this.namingSeries = namingSeries;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLetterHead() {
        return letterHead;
    }

    public void setLetterHead(String letterHead) {
        this.letterHead = letterHead;
    }

    public Object getShippingRule() {
        return shippingRule;
    }

    public void setShippingRule(Object shippingRule) {
        this.shippingRule = shippingRule;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Object getAmendedFrom() {
        return amendedFrom;
    }

    public void setAmendedFrom(Object amendedFrom) {
        this.amendedFrom = amendedFrom;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public Integer getPerDelivered() {
        return perDelivered;
    }

    public void setPerDelivered(Integer perDelivered) {
        this.perDelivered = perDelivered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getGroupSameItems() {
        return groupSameItems;
    }

    public void setGroupSameItems(Integer groupSameItems) {
        this.groupSameItems = groupSameItems;
    }

    public Object getCustomerNameInArabic() {
        return customerNameInArabic;
    }

    public void setCustomerNameInArabic(Object customerNameInArabic) {
        this.customerNameInArabic = customerNameInArabic;
    }

    public Object getTaxesAndCharges() {
        return taxesAndCharges;
    }

    public void setTaxesAndCharges(Object taxesAndCharges) {
        this.taxesAndCharges = taxesAndCharges;
    }

    public Integer getPerBilled() {
        return perBilled;
    }

    public void setPerBilled(Integer perBilled) {
        this.perBilled = perBilled;
    }

    public Integer getTotalNetWeight() {
        return totalNetWeight;
    }

    public void setTotalNetWeight(Integer totalNetWeight) {
        this.totalNetWeight = totalNetWeight;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

   /* public List<PaymentSchedule> getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(List<PaymentSchedule> paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }*/

    public Integer getPlcConversionRate() {
        return plcConversionRate;
    }

    public void setPlcConversionRate(Integer plcConversionRate) {
        this.plcConversionRate = plcConversionRate;
    }

    public Object getParenttype() {
        return parenttype;
    }

    public void setParenttype(Object parenttype) {
        this.parenttype = parenttype;
    }

    public Integer getTotalTaxesAndCharges() {
        return totalTaxesAndCharges;
    }

    public void setTotalTaxesAndCharges(Integer totalTaxesAndCharges) {
        this.totalTaxesAndCharges = totalTaxesAndCharges;
    }

    public Object getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(Object contactEmail) {
        this.contactEmail = contactEmail;
    }

   /* public List<Object> getSalesTeam() {
        return salesTeam;
    }

    public void setSalesTeam(List<Object> salesTeam) {
        this.salesTeam = salesTeam;
    }*/

}
