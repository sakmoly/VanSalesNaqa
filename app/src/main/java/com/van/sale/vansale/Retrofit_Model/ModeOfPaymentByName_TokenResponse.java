package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModeOfPaymentByName_TokenResponse {

    @SerializedName("data")
    @Expose
    private ModeOfPaymentByNameData data;

    public ModeOfPaymentByNameData getData() {
        return data;
    }

    public void setData(ModeOfPaymentByNameData data) {
        this.data = data;
    }

}
