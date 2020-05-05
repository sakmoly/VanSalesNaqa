package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerAssetData {
    @SerializedName("customer")
    @Expose
    private String customer;

    @SerializedName("asset")
    @Expose
    private String asset;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.customer = asset;
    }
}
