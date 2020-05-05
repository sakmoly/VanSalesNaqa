package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maaz on 03/10/18.
 */

public class Address_TokenResponse {

    @SerializedName("data")
    @Expose
    private List<AddressData> data = null;

    public List<AddressData> getData() {
        return data;
    }

    public void setData(List<AddressData> data) {
        this.data = data;
    }

}
