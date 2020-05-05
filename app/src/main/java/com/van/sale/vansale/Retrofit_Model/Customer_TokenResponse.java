package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maaz on 02/10/18.
 */

public class Customer_TokenResponse {

    @SerializedName("data")
    @Expose
    private List<CustomerData> data = null;

    public List<CustomerData> getData() {
        return data;
    }

    public void setData(List<CustomerData> data) {
        this.data = data;
    }


}
