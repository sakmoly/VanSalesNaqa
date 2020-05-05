package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maaz on 08/10/18.
 */

public class ItemDetail_TokenResponse {

    @SerializedName("data")
    @Expose
    private ItemDetailData data;

    public ItemDetailData getData() {
        return data;
    }

    public void setData(ItemDetailData data) {
        this.data = data;
    }



}
