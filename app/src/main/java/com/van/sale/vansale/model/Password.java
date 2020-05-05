package com.van.sale.vansale.model;

/**
 * Created by maaz on 05/10/18.
 */

public class Password {

    private String accessPassword;

    public Password() {
    }

    public Password(String accessPassword) {
        this.accessPassword = accessPassword;
    }



    public String getAccessPassword() {
        return accessPassword;
    }

    public void setAccessPassword(String accessPassword) {
        this.accessPassword = accessPassword;
    }
}
