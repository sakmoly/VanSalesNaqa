package com.van.sale.vansale.model;

/**
 * Created by maaz on 05/10/18.
 */

public class ItemClass {

    private String id,item_code,item_name,description,item_group,brand,bar_code,stock_uom;

    public ItemClass() {
    }

    public ItemClass(String item_code, String item_name, String description, String item_group, String brand, String bar_code, String stock_uom) {
        this.item_code = item_code;
        this.item_name = item_name;
        this.description = description;
        this.item_group = item_group;
        this.brand = brand;
        this.bar_code = bar_code;
        this.stock_uom = stock_uom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItem_group() {
        return item_group;
    }

    public void setItem_group(String item_group) {
        this.item_group = item_group;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public String getStock_uom() {
        return stock_uom;
    }

    public void setStock_uom(String stock_uom) {
        this.stock_uom = stock_uom;
    }
}
