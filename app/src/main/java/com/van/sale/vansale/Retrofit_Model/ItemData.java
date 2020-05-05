package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maaz on 05/10/18.
 */

public class ItemData {

    @SerializedName("item_group")
    @Expose
    private String itemGroup;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("stock_uom")
    @Expose
    private String stockUom;

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getStockUom() {
        return stockUom;
    }

    public void setStockUom(String stockUom) {
        this.stockUom = stockUom;
    }

}
