package com.van.sale.vansale.model;

public class SalesOrderItemClass {

    private String SALES_DELIVERY_STATUS,SALES_ORDER_DOC_NO,SALES_ORDER_LAST_ID,SALES_ORDER_CUSTOMER, SALES_WAREHOUSE, SALES_ITEM_NAME, SALES_STOCK_UOM, SALES_ITEM_CODE,SALES_ITEM_ASSET;
    private Float SALES_QTY, SALES_RATE, SALES_PRICE_LIST_RATE, SALES_DISCOUNT_PERCENTAGE, SALES_TAX_RATE, SALES_TAX_AMOUNT,SALES_GROSS,SALES_NET,SALES_VAT,SALES_TOTAL;
    private int SYNC_STATUS,KEY_ID;
    private boolean return_status = false;

    public SalesOrderItemClass() {
    }

    public SalesOrderItemClass(String SALES_ORDER_DOC_NO,String SALES_ORDER_LAST_ID,String SALES_ORDER_CUSTOMER, String SALES_WAREHOUSE, String SALES_ITEM_NAME, String SALES_STOCK_UOM, String SALES_ITEM_CODE, Float SALES_QTY, Float SALES_RATE, Float SALES_PRICE_LIST_RATE, Float SALES_DISCOUNT_PERCENTAGE, Float SALES_TAX_RATE, Float SALES_TAX_AMOUNT, Float SALES_GROSS, Float SALES_NET, Float SALES_VAT, Float SALES_TOTAL, int SYNC_STATUS,String SALES_DELIVERY_STATUS) {
        this.SALES_ORDER_CUSTOMER = SALES_ORDER_CUSTOMER;
        this.SALES_WAREHOUSE = SALES_WAREHOUSE;
        this.SALES_ITEM_NAME = SALES_ITEM_NAME;
        this.SALES_STOCK_UOM = SALES_STOCK_UOM;
        this.SALES_ITEM_CODE = SALES_ITEM_CODE;
        this.SALES_QTY = SALES_QTY;
        this.SALES_RATE = SALES_RATE;
        this.SALES_PRICE_LIST_RATE = SALES_PRICE_LIST_RATE;
        this.SALES_DISCOUNT_PERCENTAGE = SALES_DISCOUNT_PERCENTAGE;
        this.SALES_TAX_RATE = SALES_TAX_RATE;
        this.SALES_TAX_AMOUNT = SALES_TAX_AMOUNT;
        this.SALES_GROSS = SALES_GROSS;
        this.SALES_NET = SALES_NET;
        this.SALES_VAT = SALES_VAT;
        this.SALES_TOTAL = SALES_TOTAL;
        this.SYNC_STATUS = SYNC_STATUS;
        this.SALES_ORDER_LAST_ID = SALES_ORDER_LAST_ID;
        this.SALES_ORDER_DOC_NO = SALES_ORDER_DOC_NO;
        this.SALES_DELIVERY_STATUS = SALES_DELIVERY_STATUS;

    }

    public SalesOrderItemClass(String SALES_ORDER_LAST_ID, String SALES_WAREHOUSE, String SALES_ITEM_NAME, String SALES_STOCK_UOM, String SALES_ITEM_CODE, Float SALES_QTY, Float SALES_RATE, Float SALES_PRICE_LIST_RATE, Float SALES_DISCOUNT_PERCENTAGE, Float SALES_TAX_RATE, Float SALES_TAX_AMOUNT, Float SALES_GROSS, Float SALES_NET, Float SALES_VAT, Float SALES_TOTAL, int SYNC_STATUS,String SALES_DELIVERY_STATUS,String SALES_ITEM_ASSET) {
        this.SALES_WAREHOUSE = SALES_WAREHOUSE;
        this.SALES_ITEM_NAME = SALES_ITEM_NAME;
        this.SALES_STOCK_UOM = SALES_STOCK_UOM;
        this.SALES_ITEM_CODE = SALES_ITEM_CODE;
        this.SALES_QTY = SALES_QTY;
        this.SALES_RATE = SALES_RATE;
        this.SALES_PRICE_LIST_RATE = SALES_PRICE_LIST_RATE;
        this.SALES_DISCOUNT_PERCENTAGE = SALES_DISCOUNT_PERCENTAGE;
        this.SALES_TAX_RATE = SALES_TAX_RATE;
        this.SALES_TAX_AMOUNT = SALES_TAX_AMOUNT;
        this.SALES_GROSS = SALES_GROSS;
        this.SALES_NET = SALES_NET;
        this.SALES_VAT = SALES_VAT;
        this.SALES_TOTAL = SALES_TOTAL;
        this.SYNC_STATUS = SYNC_STATUS;
        this.SALES_ORDER_LAST_ID = SALES_ORDER_LAST_ID;
        this.SALES_DELIVERY_STATUS = SALES_DELIVERY_STATUS;
        this.SALES_ITEM_ASSET=SALES_ITEM_ASSET;
    }

    public String getSALES_ITEM_ASSET() {
        return SALES_ITEM_ASSET;
    }

    public void setSALES_ITEM_ASSET(String SALES_ITEM_ASSET) {
        this.SALES_ITEM_ASSET = SALES_ITEM_ASSET;
    }

    public String getSALES_DELIVERY_STATUS() {
        return SALES_DELIVERY_STATUS;
    }

    public void setSALES_DELIVERY_STATUS(String SALES_DELIVERY_STATUS) {
        this.SALES_DELIVERY_STATUS = SALES_DELIVERY_STATUS;
    }

    public String getSALES_ORDER_DOC_NO() {
        return SALES_ORDER_DOC_NO;
    }

    public void setSALES_ORDER_DOC_NO(String SALES_ORDER_DOC_NO) {
        this.SALES_ORDER_DOC_NO = SALES_ORDER_DOC_NO;
    }

    public boolean isReturn_status() {
        return return_status;
    }

    public void setReturn_status(boolean return_status) {
        this.return_status = return_status;
    }


    public String getSALES_ORDER_LAST_ID() {
        return SALES_ORDER_LAST_ID;
    }

    public void setSALES_ORDER_LAST_ID(String SALES_ORDER_LAST_ID) {
        this.SALES_ORDER_LAST_ID = SALES_ORDER_LAST_ID;
    }

    public Float getSALES_GROSS() {
        return SALES_GROSS;
    }

    public void setSALES_GROSS(Float SALES_GROSS) {
        this.SALES_GROSS = SALES_GROSS;
    }

    public Float getSALES_NET() {
        return SALES_NET;
    }

    public void setSALES_NET(Float SALES_NET) {
        this.SALES_NET = SALES_NET;
    }

    public Float getSALES_VAT() {
        return SALES_VAT;
    }

    public void setSALES_VAT(Float SALES_VAT) {
        this.SALES_VAT = SALES_VAT;
    }

    public Float getSALES_TOTAL() {
        return SALES_TOTAL;
    }

    public void setSALES_TOTAL(Float SALES_TOTAL) {
        this.SALES_TOTAL = SALES_TOTAL;
    }

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public String getSALES_ORDER_CUSTOMER() {
        return SALES_ORDER_CUSTOMER;
    }

    public void setSALES_ORDER_CUSTOMER(String SALES_ORDER_CUSTOMER) {
        this.SALES_ORDER_CUSTOMER = SALES_ORDER_CUSTOMER;
    }

    public String getSALES_WAREHOUSE() {
        return SALES_WAREHOUSE;
    }

    public void setSALES_WAREHOUSE(String SALES_WAREHOUSE) {
        this.SALES_WAREHOUSE = SALES_WAREHOUSE;
    }

    public String getSALES_ITEM_NAME() {
        return SALES_ITEM_NAME;
    }

    public void setSALES_ITEM_NAME(String SALES_ITEM_NAME) {
        this.SALES_ITEM_NAME = SALES_ITEM_NAME;
    }

    public String getSALES_STOCK_UOM() {
        return SALES_STOCK_UOM;
    }

    public void setSALES_STOCK_UOM(String SALES_STOCK_UOM) {
        this.SALES_STOCK_UOM = SALES_STOCK_UOM;
    }

    public String getSALES_ITEM_CODE() {
        return SALES_ITEM_CODE;
    }

    public void setSALES_ITEM_CODE(String SALES_ITEM_CODE) {
        this.SALES_ITEM_CODE = SALES_ITEM_CODE;
    }

    public Float getSALES_QTY() {
        return SALES_QTY;
    }

    public void setSALES_QTY(Float SALES_QTY) {
        this.SALES_QTY = SALES_QTY;
    }

    public Float getSALES_RATE() {
        return SALES_RATE;
    }

    public void setSALES_RATE(Float SALES_RATE) {
        this.SALES_RATE = SALES_RATE;
    }

    public Float getSALES_PRICE_LIST_RATE() {
        return SALES_PRICE_LIST_RATE;
    }

    public void setSALES_PRICE_LIST_RATE(Float SALES_PRICE_LIST_RATE) {
        this.SALES_PRICE_LIST_RATE = SALES_PRICE_LIST_RATE;
    }

    public Float getSALES_DISCOUNT_PERCENTAGE() {
        return SALES_DISCOUNT_PERCENTAGE;
    }

    public void setSALES_DISCOUNT_PERCENTAGE(Float SALES_DISCOUNT_PERCENTAGE) {
        this.SALES_DISCOUNT_PERCENTAGE = SALES_DISCOUNT_PERCENTAGE;
    }

    public Float getSALES_TAX_RATE() {
        return SALES_TAX_RATE;
    }

    public void setSALES_TAX_RATE(Float SALES_TAX_RATE) {
        this.SALES_TAX_RATE = SALES_TAX_RATE;
    }

    public Float getSALES_TAX_AMOUNT() {
        return SALES_TAX_AMOUNT;
    }

    public void setSALES_TAX_AMOUNT(Float SALES_TAX_AMOUNT) {
        this.SALES_TAX_AMOUNT = SALES_TAX_AMOUNT;
    }

    public int getSYNC_STATUS() {
        return SYNC_STATUS;
    }

    public void setSYNC_STATUS(int SYNC_STATUS) {
        this.SYNC_STATUS = SYNC_STATUS;
    }
}
