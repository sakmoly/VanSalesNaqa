package com.van.sale.vansale.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.ItemDetailClass;
import com.van.sale.vansale.model.SalesInvoiceItemClass;
import com.van.sale.vansale.model.SelectedItemClass;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDialog extends AppCompatDialogFragment {

    private EditText rate, quantity;
    private TextView addtocart, gross_value, net_value, vat_value, total_value, discount_percentage, product_item_code, product_discription,
            pricedisplay, closeid;
    private MaterialSpinner spinner;
    private String selected_item_id,selected_item_code, selected_price_display, selected_quantity, selected_discount_percentage, spinner_value, selected_rate;
    private DatabaseHandler db;

    private DetailDialogListener cartDialogListener;
    private String discount_percentage_value = "0";
    private int uom_position = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        selected_item_id = InvoiceDetailItemListActivity.getSelectedId();
        selected_item_code = InvoiceDetailItemListActivity.getSelectedItemCode();
        selected_price_display = InvoiceDetailItemListActivity.getSelectedPriceList();
        selected_quantity = InvoiceDetailItemListActivity.getSelectedQuantity();
        selected_discount_percentage = InvoiceDetailItemListActivity.getSelectedDiscountPercentage();
        selected_rate = InvoiceDetailItemListActivity.getSelectedRate();
        spinner_value = InvoiceDetailItemListActivity.getSelectedUom();

        db = new DatabaseHandler(getContext());

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.orderpopup, null);

        rate = (EditText) dialogView.findViewById(R.id.rate);
        quantity = (EditText) dialogView.findViewById(R.id.quantity);
        addtocart = (TextView) dialogView.findViewById(R.id.addtocartid);
        gross_value = (TextView) dialogView.findViewById(R.id.gross_value);
        net_value = (TextView) dialogView.findViewById(R.id.net_value);
        vat_value = (TextView) dialogView.findViewById(R.id.vat_value);
        total_value = (TextView) dialogView.findViewById(R.id.total_value);
        discount_percentage = (TextView) dialogView.findViewById(R.id.discount_percentage);
        product_item_code = (TextView) dialogView.findViewById(R.id.product_item_code);
        product_discription = (TextView) dialogView.findViewById(R.id.product_discription);
        pricedisplay = (TextView) dialogView.findViewById(R.id.pricedisplay);
        spinner = (MaterialSpinner) dialogView.findViewById(R.id.spinner);
        closeid = (TextView) dialogView.findViewById(R.id.closeid);


        addtocart.setText("DONE");
        product_discription.setVisibility(View.GONE);
        product_item_code.setText(selected_item_code);
        discount_percentage.setText(selected_discount_percentage + "%");
        discount_percentage_value = selected_discount_percentage;
        rate.setText(selected_rate);

        final List<ItemDetailClass> getItemDetail = db.getItemDetail(selected_item_code);
        List<String> uom = new ArrayList<String>();

        for (ItemDetailClass it : getItemDetail) {
            uom.add(it.getUom());
        }


        for (ItemDetailClass it : getItemDetail) {

            if (it.getUom().equals(spinner_value)) {
                break;
            }

            uom_position = uom_position + 1;

        }


       /* try {
            spinner_value = getItemDetail.get(0).getUom();
        } catch (Exception e) {

        }*/


        try {

            if (getItemDetail.get(0).getPrice() == null) {
                pricedisplay.setText("0.0");
                rate.setText("0.0");
            } else {
                pricedisplay.setText(getItemDetail.get(0).getPrice());
            }
        } catch (Exception e) {

        }
        spinner.setItems(uom);
        spinner.setSelectedIndex(uom_position);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                //  Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                spinner_value = item;

                if (getItemDetail.get(position).getPrice() == null) {
                    pricedisplay.setText("0.0");
                    rate.setText("0.0");
                } else {
                   // pricedisplay.setText(getItemDetail.get(position).getPrice());


                    pricedisplay.setText(getItemDetail.get(position).getPrice());
                    rate.setText(getItemDetail.get(position).getPrice());

                    Float gr_tot = Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString());
                    gross_value.setText(String.valueOf(Utility.round(gr_tot, 2)));
                   // rate.setText(String.valueOf(Utility.round(gr_tot, 2)));

                    Float net_tot = Float.parseFloat(rate.getText().toString()) * Float.parseFloat(quantity.getText().toString());
                    net_value.setText(String.valueOf(Utility.round(net_tot, 2)));

                           /* Float vat_tot = Float.parseFloat(db.getTaxRate()) * Float.parseFloat(quantity.getText().toString());
                            vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));*/

                    Float vat_tot = ((Float.parseFloat(db.getTaxRate()) * Float.parseFloat(rate.getText().toString())) / 100) * Float.parseFloat(quantity.getText().toString());
                    vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));

                    Float tot = net_tot + vat_tot;
                    total_value.setText(String.valueOf(Utility.round(tot, 2)));

                   /* Float prcnt = ((Float.parseFloat(pricedisplay.getText().toString()) - Float.parseFloat(rate.getText().toString())) / Float.parseFloat(pricedisplay.getText().toString())) * 100;
                    discount_percentage_value = String.valueOf(Math.round(prcnt));
                    discount_percentage.setText(discount_percentage_value + "%");*/

                  //  Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - Float.parseFloat(rate.getText().toString())) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;
                    Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - (Float.parseFloat(rate.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;
                  //  discount_percentage_value = String.valueOf(Math.round(prcnt));
                    if (Math.round(prcnt) < 0){
                        discount_percentage_value = "0";
                    }else{
                        discount_percentage_value = String.valueOf(Math.round(prcnt));
                    }
                    discount_percentage.setText(discount_percentage_value + "%");



                }

            }
        });


        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (!(s.toString().isEmpty())) {

                    Float gr_tot = Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(s.toString());
                    gross_value.setText(String.valueOf(Utility.round(gr_tot, 2)));


                    Float net_tot = Float.parseFloat(rate.getText().toString()) * Float.parseFloat(s.toString());
                    net_value.setText(String.valueOf(Utility.round(net_tot, 2)));

                   /* Float vat_tot = Float.parseFloat(db.getTaxRate()) * Float.parseFloat(s.toString());
                    vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));*/

                    Float vat_tot = ((Float.parseFloat(db.getTaxRate()) * Float.parseFloat(rate.getText().toString())) / 100) * Float.parseFloat(s.toString());
                    vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));

                    Float tot = net_tot + vat_tot;
                    total_value.setText(String.valueOf(Utility.round(tot, 2)));

                    Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(s.toString())) - (Float.parseFloat(rate.getText().toString()) * Float.parseFloat(s.toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(s.toString()))) * 100;
                  //  discount_percentage_value = String.valueOf(Math.round(prcnt));
                    if (Math.round(prcnt) < 0){
                        discount_percentage_value = "0";
                    }else{
                        discount_percentage_value = String.valueOf(Math.round(prcnt));
                    }
                    discount_percentage.setText(discount_percentage_value + "%");


                } else {
                    gross_value.setText("0.0");
                    net_value.setText("0.0");
                    vat_value.setText("0.0");
                    total_value.setText("0.0");

                }
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (!(s.toString().isEmpty())) {

                    Float gr_tot = Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString());
                    gross_value.setText(String.valueOf(Utility.round(gr_tot, 2)));

                    Float net_tot = Float.parseFloat(s.toString()) * Float.parseFloat(quantity.getText().toString());
                    net_value.setText(String.valueOf(Utility.round(net_tot, 2)));

                           /* Float vat_tot = Float.parseFloat(db.getTaxRate()) * Float.parseFloat(quantity.getText().toString());
                            vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));*/

                    Float vat_tot = ((Float.parseFloat(db.getTaxRate()) * Float.parseFloat(s.toString())) / 100) * Float.parseFloat(quantity.getText().toString());
                    vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));

                    Float tot = net_tot + vat_tot;
                    total_value.setText(String.valueOf(Utility.round(tot, 2)));

                   /* Float prcnt = ((Float.parseFloat(pricedisplay.getText().toString()) - Float.parseFloat(s.toString())) / Float.parseFloat(pricedisplay.getText().toString())) * 100;
                    discount_percentage_value = String.valueOf(Math.round(prcnt));
                    discount_percentage.setText(discount_percentage_value + "%");*/

                   // Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - Float.parseFloat(s.toString())) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;

                    Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - (Float.parseFloat(s.toString()) * Float.parseFloat(quantity.getText().toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;
                  //  discount_percentage_value = String.valueOf(Math.round(prcnt));
                    if (Math.round(prcnt) < 0){
                        discount_percentage_value = "0";
                    }else{
                        discount_percentage_value = String.valueOf(Math.round(prcnt));
                    }
                    discount_percentage.setText(discount_percentage_value + "%");

                }
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        pricedisplay.setText(selected_price_display);
        quantity.setText(selected_quantity);

        quantity.setSelection(quantity.getText().length());

        // product_item_code.setText(channelsList.get(position).getItem_code());


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* SelectedItemClass itemClass = new SelectedItemClass();
                itemClass.setQty(quantity.getText().toString());
                itemClass.setStock_uom(spinner_value);
                itemClass.setPrice_list(pricedisplay.getText().toString());
                itemClass.setRate(rate.getText().toString());
                itemClass.setDiscount_percentage(discount_percentage_value);
                itemClass.setGross(gross_value.getText().toString());
                itemClass.setNet(net_value.getText().toString());
                itemClass.setVat(vat_value.getText().toString());
                itemClass.setTotal(total_value.getText().toString());*/

                SalesInvoiceItemClass aClass = new SalesInvoiceItemClass();
                aClass.setINVOICE_ITEM_QTY(Float.valueOf(quantity.getText().toString()));
                aClass.setINVOICE_ITEM_STOCK_UOM(spinner_value);
                aClass.setINVOICE_ITEM_PRICELIST_RATE(Float.valueOf(pricedisplay.getText().toString()));
                aClass.setINVOICE_ITEM_RATE(Float.valueOf(rate.getText().toString()));
                aClass.setINVOICE_ITEM_DISCOUNT_PERCENTAGE(Float.valueOf(discount_percentage_value));
                aClass.setSALES_GROSS(Float.valueOf(gross_value.getText().toString()));
                aClass.setSALES_NET(Float.valueOf(net_value.getText().toString()));
                aClass.setSALES_VAT(Float.valueOf(vat_value.getText().toString()));
                aClass.setSALES_TOTAL(Float.valueOf(total_value.getText().toString()));


                db.updateInvoiceItemDetails(selected_item_id,aClass);

                Toast t = Utility.setToast(getContext(),"Updated");
                t.show();

                cartDialogListener.applyTexts(aClass);

            }
        });


        closeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cartDialogListener.dismisDialog();

            }
        });


        builder.setView(dialogView);

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            cartDialogListener = (DetailDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement CartDialogListener");
        }

    }

    public interface DetailDialogListener {
        void applyTexts(SalesInvoiceItemClass selectedItemClass);
        void dismisDialog();
    }

}
