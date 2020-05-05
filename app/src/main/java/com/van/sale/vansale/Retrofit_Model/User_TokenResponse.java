package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maaz on 24/09/18.
 */

public class User_TokenResponse {

    @SerializedName("session_expired")
    @Expose
    private String session_expired = "not expired";
    @SerializedName("data")
    @Expose
    private List<UserResponseData> data = null;

    public List<UserResponseData> getData() {
        return data;
    }

    public void setData(List<UserResponseData> data) {
        this.data = data;
    }

    public String getSession_expired() {
        return session_expired;
    }

    public void setSession_expired(String session_expired) {
        this.session_expired = session_expired;
    }
}
