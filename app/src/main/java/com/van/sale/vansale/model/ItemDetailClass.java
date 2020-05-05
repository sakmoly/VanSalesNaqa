package com.van.sale.vansale.model;

/**
 * Created by maaz on 08/10/18.
 */

public class ItemDetailClass {


    public String id,item_parent,conversion_factor,uom,alu2,price = "0.0";

    public ItemDetailClass() {
    }

    public ItemDetailClass(String item_parent, String conversion_factor, String uom, String alu2, String price) {
        this.item_parent = item_parent;
        this.conversion_factor = conversion_factor;
        this.uom = uom;
        this.alu2 = alu2;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_parent() {
        return item_parent;
    }

    public void setItem_parent(String item_parent) {
        this.item_parent = item_parent;
    }

    public String getConversion_factor() {
        return conversion_factor;
    }

    public void setConversion_factor(String conversion_factor) {
        this.conversion_factor = conversion_factor;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getAlu2() {
        return alu2;
    }

    public void setAlu2(String alu2) {
        this.alu2 = alu2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
