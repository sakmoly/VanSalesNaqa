package com.van.sale.vansale.model;

/**
 * Created by maaz on 17/09/18.
 */

public class AddressClass {

    private String id,Title,name,address_line1,address_line2,company,city,sync_status;

    public AddressClass() {
    }

    public AddressClass(String id,String title, String name, String address_line1, String address_line2, String company, String city,String sync_status) {
        this.Title = title;
        this.name = name;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.company = company;
        this.city = city;
        this.id=id;

    }

    public AddressClass(String title, String name, String address_line1, String address_line2, String company, String city, String sync_status) {
        Title = title;
        this.name = name;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.company = company;
        this.city = city;
        this.sync_status=sync_status;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSync_status() {
        return sync_status;
    }

    public void setSync_status(String sync_status) {
        this.sync_status = sync_status;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
