package com.van.sale.vansale.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SelectedItemClass implements Parcelable {


    private String Qty,item_name,rate,stock_uom,item_code,price_list,discount_percentage,tax_rate,tax_amount,warehouse,gross,net,vat,total;

    public SelectedItemClass() {
    }

    public SelectedItemClass(String qty, String item_name, String rate, String stock_uom, String item_code, String price_list, String discount_percentage, String tax_rate, String tax_amount, String warehouse, String gross, String net, String vat, String total) {
        Qty = qty;
        this.item_name = item_name;
        this.rate = rate;
        this.stock_uom = stock_uom;
        this.item_code = item_code;
        this.price_list = price_list;
        this.discount_percentage = discount_percentage;
        this.tax_rate = tax_rate;
        this.tax_amount = tax_amount;
        this.warehouse = warehouse;
        this.gross = gross;
        this.net = net;
        this.vat = vat;
        this.total = total;
    }

    public String getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = gross;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStock_uom() {
        return stock_uom;
    }

    public void setStock_uom(String stock_uom) {
        this.stock_uom = stock_uom;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getPrice_list() {
        return price_list;
    }

    public void setPrice_list(String price_list) {
        this.price_list = price_list;
    }

    public String getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(String discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public String getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(String tax_rate) {
        this.tax_rate = tax_rate;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(String tax_amount) {
        this.tax_amount = tax_amount;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }


    public SelectedItemClass(Parcel in){
        super();
        readFromParcel(in);
    }


    public static final Parcelable.Creator<SelectedItemClass> CREATOR = new Parcelable.Creator<SelectedItemClass>() {
        public SelectedItemClass createFromParcel(Parcel in) {
            return new SelectedItemClass(in);
        }

        public SelectedItemClass[] newArray(int size) {

            return new SelectedItemClass[size];
        }

    };


    public void readFromParcel(Parcel in) {
        String[] result = new String[14];
        in.readStringArray(result);


        this.Qty = result[0];
        this.item_name = result[1];
        this.rate = result[2];
        this.stock_uom = result[3];
        this.item_code = result[4];
        this.price_list = result[5];
        this.discount_percentage = result[6];
        this.tax_rate = result[7];
        this.tax_amount = result[8];
        this.warehouse = result[9];
        this.gross = result[10];
        this.net = result[11];
        this.vat = result[12];
        this.total = result[13];

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {

        dest.writeStringArray(new String[] {
                this.Qty,this.item_name,this.rate,this.stock_uom,this.item_code,this.price_list,this.discount_percentage,this.tax_rate,this.tax_amount,this.warehouse,this.gross,this.net,this.vat,this.total
               });

    }
}
