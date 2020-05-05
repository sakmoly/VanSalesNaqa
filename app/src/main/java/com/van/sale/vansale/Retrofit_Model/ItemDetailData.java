package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maaz on 08/10/18.
 */

public class ItemDetailData {

    @SerializedName("website_specifications")
    @Expose
    private List<Object> websiteSpecifications = null;
    @SerializedName("max_discount")
    @Expose
    private Integer maxDiscount;
    @SerializedName("reorder_levels")
    @Expose
    private List<ItemDetailReorderLevel> reorderLevels = null;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("income_account")
    @Expose
    private String incomeAccount;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("has_expiry_date")
    @Expose
    private Integer hasExpiryDate;
    @SerializedName("default_material_request_type")
    @Expose
    private String defaultMaterialRequestType;
    @SerializedName("website_item_groups")
    @Expose
    private List<Object> websiteItemGroups = null;
    @SerializedName("disabled")
    @Expose
    private Integer disabled;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("item_group")
    @Expose
    private String itemGroup;
    @SerializedName("weight_per_unit")
    @Expose
    private Integer weightPerUnit;
    @SerializedName("creation")
    @Expose
    private String creation;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("taxes")
    @Expose
    private List<Object> taxes = null;
    @SerializedName("create_new_batch")
    @Expose
    private Integer createNewBatch;
    @SerializedName("has_variants")
    @Expose
    private Integer hasVariants;
    @SerializedName("supplier_items")
    @Expose
    private List<Object> supplierItems = null;
    @SerializedName("inspection_required_before_delivery")
    @Expose
    private Integer inspectionRequiredBeforeDelivery;
    @SerializedName("is_sales_item")
    @Expose
    private Integer isSalesItem;
    @SerializedName("is_sub_contracted_item")
    @Expose
    private Integer isSubContractedItem;
    @SerializedName("tolerance")
    @Expose
    private Integer tolerance;
    @SerializedName("customer_code")
    @Expose
    private String customerCode;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("quality_parameters")
    @Expose
    private List<Object> qualityParameters = null;
    @SerializedName("is_stock_item")
    @Expose
    private Integer isStockItem;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("variant_based_on")
    @Expose
    private String variantBasedOn;
    @SerializedName("min_order_qty")
    @Expose
    private Integer minOrderQty;
    @SerializedName("valuation_rate")
    @Expose
    private Integer valuationRate;
    @SerializedName("attributes")
    @Expose
    private List<Object> attributes = null;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("customer_items")
    @Expose
    private List<Object> customerItems = null;
    @SerializedName("is_item_from_hub")
    @Expose
    private Integer isItemFromHub;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("retain_sample")
    @Expose
    private Integer retainSample;
    @SerializedName("show_in_website")
    @Expose
    private Integer showInWebsite;
    @SerializedName("is_purchase_item")
    @Expose
    private Integer isPurchaseItem;
    @SerializedName("safety_stock")
    @Expose
    private Integer safetyStock;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("delivered_by_supplier")
    @Expose
    private Integer deliveredBySupplier;

    @SerializedName("uoms")
    @Expose
    private List<ItemDetailUom> uoms = null;

    @SerializedName("last_purchase_rate")
    @Expose
    private Double lastPurchaseRate;
    @SerializedName("publish_in_hub")
    @Expose
    private Integer publishInHub;
    @SerializedName("end_of_life")
    @Expose
    private String endOfLife;
    @SerializedName("synced_with_hub")
    @Expose
    private Integer syncedWithHub;
    @SerializedName("stock_uom")
    @Expose
    private String stockUom;
    @SerializedName("show_variant_in_website")
    @Expose
    private Integer showVariantInWebsite;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("sample_quantity")
    @Expose
    private Integer sampleQuantity;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("weightage")
    @Expose
    private Integer weightage;
    @SerializedName("lead_time_days")
    @Expose
    private Integer leadTimeDays;
    @SerializedName("opening_stock")
    @Expose
    private Integer openingStock;
    @SerializedName("has_batch_no")
    @Expose
    private Integer hasBatchNo;
    @SerializedName("has_serial_no")
    @Expose
    private Integer hasSerialNo;
    @SerializedName("standard_rate")
    @Expose
    private Integer standardRate;
    @SerializedName("is_fixed_asset")
    @Expose
    private Integer isFixedAsset;
    @SerializedName("inspection_required_before_purchase")
    @Expose
    private Integer inspectionRequiredBeforePurchase;

    public List<Object> getWebsiteSpecifications() {
        return websiteSpecifications;
    }

    public void setWebsiteSpecifications(List<Object> websiteSpecifications) {
        this.websiteSpecifications = websiteSpecifications;
    }

    public Integer getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Integer maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public List<ItemDetailReorderLevel> getReorderLevels() {
        return reorderLevels;
    }

    public void setReorderLevels(List<ItemDetailReorderLevel> reorderLevels) {
        this.reorderLevels = reorderLevels;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getIncomeAccount() {
        return incomeAccount;
    }

    public void setIncomeAccount(String incomeAccount) {
        this.incomeAccount = incomeAccount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getHasExpiryDate() {
        return hasExpiryDate;
    }

    public void setHasExpiryDate(Integer hasExpiryDate) {
        this.hasExpiryDate = hasExpiryDate;
    }

    public String getDefaultMaterialRequestType() {
        return defaultMaterialRequestType;
    }

    public void setDefaultMaterialRequestType(String defaultMaterialRequestType) {
        this.defaultMaterialRequestType = defaultMaterialRequestType;
    }

    public List<Object> getWebsiteItemGroups() {
        return websiteItemGroups;
    }

    public void setWebsiteItemGroups(List<Object> websiteItemGroups) {
        this.websiteItemGroups = websiteItemGroups;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public Integer getWeightPerUnit() {
        return weightPerUnit;
    }

    public void setWeightPerUnit(Integer weightPerUnit) {
        this.weightPerUnit = weightPerUnit;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public List<Object> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Object> taxes) {
        this.taxes = taxes;
    }

    public Integer getCreateNewBatch() {
        return createNewBatch;
    }

    public void setCreateNewBatch(Integer createNewBatch) {
        this.createNewBatch = createNewBatch;
    }

    public Integer getHasVariants() {
        return hasVariants;
    }

    public void setHasVariants(Integer hasVariants) {
        this.hasVariants = hasVariants;
    }

    public List<Object> getSupplierItems() {
        return supplierItems;
    }

    public void setSupplierItems(List<Object> supplierItems) {
        this.supplierItems = supplierItems;
    }

    public Integer getInspectionRequiredBeforeDelivery() {
        return inspectionRequiredBeforeDelivery;
    }

    public void setInspectionRequiredBeforeDelivery(Integer inspectionRequiredBeforeDelivery) {
        this.inspectionRequiredBeforeDelivery = inspectionRequiredBeforeDelivery;
    }

    public Integer getIsSalesItem() {
        return isSalesItem;
    }

    public void setIsSalesItem(Integer isSalesItem) {
        this.isSalesItem = isSalesItem;
    }

    public Integer getIsSubContractedItem() {
        return isSubContractedItem;
    }

    public void setIsSubContractedItem(Integer isSubContractedItem) {
        this.isSubContractedItem = isSubContractedItem;
    }

    public Integer getTolerance() {
        return tolerance;
    }

    public void setTolerance(Integer tolerance) {
        this.tolerance = tolerance;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public List<Object> getQualityParameters() {
        return qualityParameters;
    }

    public void setQualityParameters(List<Object> qualityParameters) {
        this.qualityParameters = qualityParameters;
    }

    public Integer getIsStockItem() {
        return isStockItem;
    }

    public void setIsStockItem(Integer isStockItem) {
        this.isStockItem = isStockItem;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getVariantBasedOn() {
        return variantBasedOn;
    }

    public void setVariantBasedOn(String variantBasedOn) {
        this.variantBasedOn = variantBasedOn;
    }

    public Integer getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(Integer minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public Integer getValuationRate() {
        return valuationRate;
    }

    public void setValuationRate(Integer valuationRate) {
        this.valuationRate = valuationRate;
    }

    public List<Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Object> attributes) {
        this.attributes = attributes;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Object> getCustomerItems() {
        return customerItems;
    }

    public void setCustomerItems(List<Object> customerItems) {
        this.customerItems = customerItems;
    }

    public Integer getIsItemFromHub() {
        return isItemFromHub;
    }

    public void setIsItemFromHub(Integer isItemFromHub) {
        this.isItemFromHub = isItemFromHub;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getRetainSample() {
        return retainSample;
    }

    public void setRetainSample(Integer retainSample) {
        this.retainSample = retainSample;
    }

    public Integer getShowInWebsite() {
        return showInWebsite;
    }

    public void setShowInWebsite(Integer showInWebsite) {
        this.showInWebsite = showInWebsite;
    }

    public Integer getIsPurchaseItem() {
        return isPurchaseItem;
    }

    public void setIsPurchaseItem(Integer isPurchaseItem) {
        this.isPurchaseItem = isPurchaseItem;
    }

    public Integer getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(Integer safetyStock) {
        this.safetyStock = safetyStock;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Integer getDeliveredBySupplier() {
        return deliveredBySupplier;
    }

    public void setDeliveredBySupplier(Integer deliveredBySupplier) {
        this.deliveredBySupplier = deliveredBySupplier;
    }

    public List<ItemDetailUom> getUoms() {
        return uoms;
    }

    public void setUoms(List<ItemDetailUom> uoms) {
        this.uoms = uoms;
    }

    public Double getLastPurchaseRate() {
        return lastPurchaseRate;
    }

    public void setLastPurchaseRate(Double lastPurchaseRate) {
        this.lastPurchaseRate = lastPurchaseRate;
    }

    public Integer getPublishInHub() {
        return publishInHub;
    }

    public void setPublishInHub(Integer publishInHub) {
        this.publishInHub = publishInHub;
    }

    public String getEndOfLife() {
        return endOfLife;
    }

    public void setEndOfLife(String endOfLife) {
        this.endOfLife = endOfLife;
    }

    public Integer getSyncedWithHub() {
        return syncedWithHub;
    }

    public void setSyncedWithHub(Integer syncedWithHub) {
        this.syncedWithHub = syncedWithHub;
    }

    public String getStockUom() {
        return stockUom;
    }

    public void setStockUom(String stockUom) {
        this.stockUom = stockUom;
    }

    public Integer getShowVariantInWebsite() {
        return showVariantInWebsite;
    }

    public void setShowVariantInWebsite(Integer showVariantInWebsite) {
        this.showVariantInWebsite = showVariantInWebsite;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public Integer getSampleQuantity() {
        return sampleQuantity;
    }

    public void setSampleQuantity(Integer sampleQuantity) {
        this.sampleQuantity = sampleQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeightage() {
        return weightage;
    }

    public void setWeightage(Integer weightage) {
        this.weightage = weightage;
    }

    public Integer getLeadTimeDays() {
        return leadTimeDays;
    }

    public void setLeadTimeDays(Integer leadTimeDays) {
        this.leadTimeDays = leadTimeDays;
    }

    public Integer getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(Integer openingStock) {
        this.openingStock = openingStock;
    }

    public Integer getHasBatchNo() {
        return hasBatchNo;
    }

    public void setHasBatchNo(Integer hasBatchNo) {
        this.hasBatchNo = hasBatchNo;
    }

    public Integer getHasSerialNo() {
        return hasSerialNo;
    }

    public void setHasSerialNo(Integer hasSerialNo) {
        this.hasSerialNo = hasSerialNo;
    }

    public Integer getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(Integer standardRate) {
        this.standardRate = standardRate;
    }

    public Integer getIsFixedAsset() {
        return isFixedAsset;
    }

    public void setIsFixedAsset(Integer isFixedAsset) {
        this.isFixedAsset = isFixedAsset;
    }

    public Integer getInspectionRequiredBeforePurchase() {
        return inspectionRequiredBeforePurchase;
    }

    public void setInspectionRequiredBeforePurchase(Integer inspectionRequiredBeforePurchase) {
        this.inspectionRequiredBeforePurchase = inspectionRequiredBeforePurchase;
    }
}
