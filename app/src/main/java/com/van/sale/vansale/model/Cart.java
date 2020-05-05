package com.van.sale.vansale.model;

/**
 * Created by maaz on 19/09/18.
 */

public class Cart {

    private String item_name,itemCode,itemQuantity,itemAmount;

    public Cart(String item_name, String itemCode, String itemQuantity, String itemAmount) {
        this.item_name = item_name;
        this.itemCode = itemCode;
        this.itemQuantity = itemQuantity;
        this.itemAmount = itemAmount;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }
}
