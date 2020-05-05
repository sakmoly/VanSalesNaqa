package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvoiceNameId_TokenResponse {

    @SerializedName("data")
    @Expose
    private List<InvoiceNameIdData> data = null;

    public List<InvoiceNameIdData> getData() {
        return data;
    }

    public void setData(List<InvoiceNameIdData> data) {
        this.data = data;
    }

}
