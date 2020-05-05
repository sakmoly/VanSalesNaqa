package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maaz on 05/10/18.
 */

public class SalesOrder_TokenResponse {

    @SerializedName("data")
    @Expose
    private List<SalesOrderData> data = null;

    public List<SalesOrderData> getData() {
        return data;
    }

    public void setData(List<SalesOrderData> data) {
        this.data = data;
    }


}
