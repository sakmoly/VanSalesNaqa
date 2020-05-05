package com.van.sale.vansale.model;

public class Mode_Of_Payment {

    private String PAYMENT_NAME,PAYMENT_MODE,PAYMENT_TYPE,PAYMENT_PAID_TO;
    private Integer KEY_ID;

    public Mode_Of_Payment() {
    }

    public Mode_Of_Payment(String PAYMENT_NAME, String PAYMENT_MODE, String PAYMENT_TYPE,String PAYMENT_PAID_TO) {
        this.PAYMENT_NAME = PAYMENT_NAME;
        this.PAYMENT_MODE = PAYMENT_MODE;
        this.PAYMENT_TYPE = PAYMENT_TYPE;
        this.PAYMENT_PAID_TO = PAYMENT_PAID_TO;
    }

    public String getPAYMENT_PAID_TO() {
        return PAYMENT_PAID_TO;
    }

    public void setPAYMENT_PAID_TO(String PAYMENT_PAID_TO) {
        this.PAYMENT_PAID_TO = PAYMENT_PAID_TO;
    }

    public String getPAYMENT_NAME() {
        return PAYMENT_NAME;
    }

    public void setPAYMENT_NAME(String PAYMENT_NAME) {
        this.PAYMENT_NAME = PAYMENT_NAME;
    }

    public String getPAYMENT_MODE() {
        return PAYMENT_MODE;
    }

    public void setPAYMENT_MODE(String PAYMENT_MODE) {
        this.PAYMENT_MODE = PAYMENT_MODE;
    }

    public String getPAYMENT_TYPE() {
        return PAYMENT_TYPE;
    }

    public void setPAYMENT_TYPE(String PAYMENT_TYPE) {
        this.PAYMENT_TYPE = PAYMENT_TYPE;
    }

    public Integer getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(Integer KEY_ID) {
        this.KEY_ID = KEY_ID;
    }
}
