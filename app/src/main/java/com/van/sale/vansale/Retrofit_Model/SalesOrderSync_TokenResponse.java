package com.van.sale.vansale.Retrofit_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesOrderSync_TokenResponse {

    @SerializedName("data")
    @Expose
    private SalesOrderSyncData data;
    @SerializedName("_server_messages")
    @Expose
    private String serverMessages;

    public SalesOrderSyncData getData() {
        return data;
    }

    public void setData(SalesOrderSyncData data) {
        this.data = data;
    }

    public String getServerMessages() {
        return serverMessages;
    }

    public void setServerMessages(String serverMessages) {
        this.serverMessages = serverMessages;
    }

}
