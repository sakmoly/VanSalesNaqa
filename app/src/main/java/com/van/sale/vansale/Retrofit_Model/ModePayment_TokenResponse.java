package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModePayment_TokenResponse {

    @SerializedName("data")
    @Expose
    private List<ModePaymentData> data = null;

    public List<ModePaymentData> getData() {
        return data;
    }

    public void setData(List<ModePaymentData> data) {
        this.data = data;
    }


}
