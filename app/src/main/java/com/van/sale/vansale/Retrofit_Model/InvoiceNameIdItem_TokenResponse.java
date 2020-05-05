package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceNameIdItem_TokenResponse {

    @SerializedName("data")
    @Expose
    private InvoiceNameIdItemData data;

    public InvoiceNameIdItemData getData() {
        return data;
    }

    public void setData(InvoiceNameIdItemData data) {
        this.data = data;
    }

}
