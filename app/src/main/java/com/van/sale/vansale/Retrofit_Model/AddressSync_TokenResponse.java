package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maaz on 04/10/18.
 */

public class AddressSync_TokenResponse {

    @SerializedName("data")
    @Expose
    private AddressSyncData data;

    public AddressSyncData getData() {
        return data;
    }

    public void setData(AddressSyncData data) {
        this.data = data;
    }

}
