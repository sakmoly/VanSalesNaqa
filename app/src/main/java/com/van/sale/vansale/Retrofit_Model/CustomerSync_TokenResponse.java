package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maaz on 04/10/18.
 */

public class CustomerSync_TokenResponse {

    @SerializedName("data")
    @Expose
    private CustomerSyncData data;

    public CustomerSyncData getData() {
        return data;
    }

    public void setData(CustomerSyncData data) {
        this.data = data;
    }

}
