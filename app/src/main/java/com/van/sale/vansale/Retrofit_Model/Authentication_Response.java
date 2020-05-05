package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maaz on 24/09/18.
 */

public class Authentication_Response {

    @SerializedName("home_page")
    @Expose
    private String homePage;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("full_name")
    @Expose
    private String fullName;

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


}
