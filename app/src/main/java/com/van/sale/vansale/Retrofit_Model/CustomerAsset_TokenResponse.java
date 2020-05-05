package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerAsset_TokenResponse {
    @SerializedName("data")
    @Expose
    private List<CustomerAssetData> data = null;

    public List<CustomerAssetData> getData() {
        return data;
    }

    public void setData(List<CustomerAssetData> data) {
        this.data = data;
    }

}
