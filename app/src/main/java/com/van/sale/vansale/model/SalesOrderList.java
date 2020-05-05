package com.van.sale.vansale.model;

/**
 * Created by maaz on 19/09/18.
 */

public class SalesOrderList {

    private String order_item;

    public SalesOrderList(String order_item) {
        this.order_item = order_item;
    }

    public String getOrder_item() {
        return order_item;
    }

    public void setOrder_item(String order_item) {
        this.order_item = order_item;
    }
}
