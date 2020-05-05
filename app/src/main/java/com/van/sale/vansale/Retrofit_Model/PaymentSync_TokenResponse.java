package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentSync_TokenResponse {

    @SerializedName("data")
    @Expose
    private PaymentSyncData data;

    public PaymentSyncData getData() {
        return data;
    }

    public void setData(PaymentSyncData data) {
        this.data = data;
    }

}
