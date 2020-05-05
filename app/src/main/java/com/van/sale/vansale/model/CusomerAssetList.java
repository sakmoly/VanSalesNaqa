package com.van.sale.vansale.model;

public class CusomerAssetList {

    String ASSET_CUSTOMER_NAME,ASSET_ASSET_ID;

    public CusomerAssetList() {
    }
    public CusomerAssetList(String ASSET_CUSTOMER_NAME,String ASSET_ASSET_ID) {

        this.ASSET_CUSTOMER_NAME = ASSET_CUSTOMER_NAME;
        this.ASSET_ASSET_ID = ASSET_ASSET_ID;
    }
    public String getASSET_CUSTOMER_NAME() {
        return ASSET_CUSTOMER_NAME;
    }

    public void setASSET_CUSTOMER_NAME(String ASSET_CUSTOMER_NAME) {
        this.ASSET_CUSTOMER_NAME = ASSET_CUSTOMER_NAME;
    }

    public String getASSET_ASSET_ID() {
        return ASSET_ASSET_ID;
    }

    public void setASSET_ASSET_ID(String ASSET_ASSET_ID) {
        this.ASSET_ASSET_ID = ASSET_ASSET_ID;
    }
}
