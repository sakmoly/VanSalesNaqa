package com.van.sale.vansale.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.SalesOrderListAdapter;
import com.van.sale.vansale.model.ItemClass;
import com.van.sale.vansale.model.ItemDetailClass;
import com.van.sale.vansale.model.SalesInvoiceItemClass;
import com.van.sale.vansale.model.SalesOrderItemClass;

import java.util.ArrayList;
import java.util.List;

import static com.koushikdutta.async.AsyncServer.LOGTAG;

public class InvoiceDetailMoreItemListAddActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView RECYCLER_VIEW;
    private TextView EMPTY_TV;
    private Button DONE_BTN, CANCEL_BTN;
    private DatabaseHandler db;
    private List<ItemClass> getItem, getItembyBarcode;
    private LayoutInflater inflater, inflater1;
    private SalesOrderListAdapter orderListAdapter;
    private AlertDialog.Builder dialogBuilder;
    private View dialogView;
    private EditText rate, quantity;
    private TextView addtocart, closeid, product_item_code, product_discription, pricedisplay, discount_percentage, gross_value, net_value, vat_value, total_value;
    private MaterialSpinner spinner;
    private AlertDialog alertDialog, alertDialog2;
    private String spinner_value;
    private String discount_percentage_value = "0";
    private List<SalesOrderItemClass> itemClasses;
    List<SalesOrderItemClass> return_data;
    private int scanStatus = 0;
    private String INVOICE_CUSTOMER_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_item_list_add);

        INVOICE_CUSTOMER_ID = getIntent().getStringExtra("INVOICE_CUSTOMER_ID");

        scanStatus = 0;

        db = new DatabaseHandler(this);
        getItem = db.getItem();
        itemClasses = new ArrayList<>();
        return_data = new ArrayList<>();

        for (ItemClass v : getItem) {

            Log.i("BBB", "<==" + v.getItem_name());
            Log.i("BBB", "<==" + v.getBar_code());

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("INVOICE ITEM ADD");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        RECYCLER_VIEW = (RecyclerView) findViewById(R.id.recycler_view);
        EMPTY_TV = (TextView) findViewById(R.id.empty_tv);
        DONE_BTN = (Button) findViewById(R.id.done_request);
        CANCEL_BTN = (Button) findViewById(R.id.cancel_request);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RECYCLER_VIEW.setLayoutManager(mLayoutManager);
        RECYCLER_VIEW.setHasFixedSize(true);
        //  mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RECYCLER_VIEW.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));


        displayItem();


        DONE_BTN.setOnClickListener(this);
        CANCEL_BTN.setOnClickListener(this);
    }

    private void displayItem() {

        scanStatus = 0;

        orderListAdapter = new SalesOrderListAdapter(getApplicationContext(), getItem, new SalesOrderListAdapter.CustomItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {

                inflater = InvoiceDetailMoreItemListAddActivity.this.getLayoutInflater();
                dialogBuilder = new AlertDialog.Builder(InvoiceDetailMoreItemListAddActivity.this);
                dialogView = inflater.inflate(R.layout.orderpopup, null);
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
                dialogBuilder.setView(dialogView);
                alertDialog = dialogBuilder.create();
                alertDialog.show();

                final List<ItemDetailClass> getItemDetail = db.getItemDetail(getItem.get(position).getItem_code());
                // List<ItemDetailClass> getItemDetail = db.getItemDetail("IND.CON.HIJ.0524");
                List<String> uom = new ArrayList<String>();

                for (ItemDetailClass it : getItemDetail) {

                    Log.i("UUU", "<==" + it.getUom());
                    uom.add(it.getUom());
                }

                try {
                    spinner_value = getItemDetail.get(0).getUom();
                } catch (Exception e) {

                }

                rate.setText("0.0");
                pricedisplay.setText("0.0");
                discount_percentage.setText(discount_percentage_value + "%");

                try {

                    if (getItemDetail.get(0).getPrice() == null) {
                        pricedisplay.setText("0.0");
                        rate.setText("0.0");
                    } else {
                        pricedisplay.setText(getItemDetail.get(0).getPrice());
                        rate.setText(getItemDetail.get(0).getPrice());
                    }
                } catch (Exception e) {

                }
                spinner.setItems(uom);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                        //  Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                        spinner_value = item;

                        if (getItemDetail.get(position).getPrice() == null) {
                            pricedisplay.setText("0.0");
                            rate.setText("0.0");

                        } else {

                           /* pricedisplay.setText(getItemDetail.get(position).getPrice());
                            rate.setText(getItemDetail.get(position).getPrice());

                            Float gr_tot = Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString());
                            gross_value.setText(String.valueOf(Utility.round(gr_tot, 2)));

                            Float net_tot = Float.parseFloat(rate.getText().toString()) * Float.parseFloat(quantity.getText().toString());
                            net_value.setText(String.valueOf(Utility.round(net_tot, 2)));

                           *//* Float vat_tot = Float.parseFloat(db.getTaxRate()) * Float.parseFloat(quantity.getText().toString());
                            vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));*//*

                            Float vat_tot = ((Float.parseFloat(db.getTaxRate()) * Float.parseFloat(rate.getText().toString())) / 100) * Float.parseFloat(quantity.getText().toString());
                            vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));

                            Float tot = net_tot + vat_tot;
                            total_value.setText(String.valueOf(Utility.round(tot, 2)));

                            Float prcnt = ((Float.parseFloat(pricedisplay.getText().toString()) - Float.parseFloat(rate.getText().toString())) / Float.parseFloat(pricedisplay.getText().toString())) * 100;
                            discount_percentage_value = String.valueOf(Math.round(prcnt));
                            discount_percentage.setText(discount_percentage_value + "%");*/


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

                            // Float prcnt = ((Float.parseFloat(pricedisplay.getText().toString()) - Float.parseFloat(rate.getText().toString())) / Float.parseFloat(pricedisplay.getText().toString())) * 100;
                            Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - (Float.parseFloat(rate.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;
                            //  discount_percentage_value = String.valueOf(Math.round(prcnt));
                            if (Math.round(prcnt) < 0) {
                                discount_percentage_value = "0";
                            } else {
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
                            // rate.setText(String.valueOf(Utility.round(gr_tot, 2)));


                            Float net_tot = Float.parseFloat(rate.getText().toString()) * Float.parseFloat(s.toString());
                            net_value.setText(String.valueOf(Utility.round(net_tot, 2)));

                           /* Float vat_tot = Float.parseFloat(db.getTaxRate()) * Float.parseFloat(s.toString());
                            vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));*/

                            Float vat_tot = ((Float.parseFloat(db.getTaxRate()) * Float.parseFloat(rate.getText().toString())) / 100) * Float.parseFloat(s.toString());
                            vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));


                            Float tot = net_tot + vat_tot;
                            total_value.setText(String.valueOf(Utility.round(tot, 2)));

                            Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(s.toString())) - (Float.parseFloat(rate.getText().toString()) * Float.parseFloat(s.toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(s.toString()))) * 100;
                            // discount_percentage_value = String.valueOf(Math.round(prcnt));
                            if (Math.round(prcnt) < 0) {
                                discount_percentage_value = "0";
                            } else {
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

                            Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - (Float.parseFloat(s.toString()) * Float.parseFloat(quantity.getText().toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;

                            if (Math.round(prcnt) < 0) {
                                discount_percentage_value = "0";
                            } else {
                                discount_percentage_value = String.valueOf(Math.round(prcnt));
                            }

                            discount_percentage.setText(discount_percentage_value + "%");

                           /* Float prcnt = (Float.parseFloat(s.toString()) / Float.parseFloat(pricedisplay.getText().toString())) * 100;
                            discount_percentage_value = String.valueOf(Math.round(prcnt));
                            discount_percentage.setText(discount_percentage_value+"%");*/


                        }

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });


                quantity.setText("1");

                quantity.setSelection(quantity.getText().length());
                product_item_code.setText(getItem.get(position).getItem_code());
                product_discription.setText(getItem.get(position).getDescription());


                addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (!quantity.getText().toString().isEmpty()) {
                            if (!rate.getText().toString().isEmpty()) {

                              //  return_data.add(new SalesOrderItemClass(String.valueOf(db.getSalesInvoiceHighestID()), db.getWarehouse(), getItem.get(position).getItem_name(), spinner_value, getItem.get(position).getItem_code(), Float.parseFloat(quantity.getText().toString()), Float.parseFloat(rate.getText().toString()), Float.parseFloat(pricedisplay.getText().toString()), Float.parseFloat(discount_percentage_value), Float.parseFloat(db.getTaxRate()), Float.parseFloat("0"), Float.parseFloat(gross_value.getText().toString()), Float.parseFloat(net_value.getText().toString()), Float.parseFloat(vat_value.getText().toString()), Float.parseFloat(total_value.getText().toString()), 0, "0"));

                                db.addSalesInvoiceItems(new SalesInvoiceItemClass(Integer.parseInt(INVOICE_CUSTOMER_ID), db.getWarehouse(), getItem.get(position).getItem_code(), spinner_value, getItem.get(position).getItem_code(), "", "", Float.parseFloat(quantity.getText().toString()), Float.parseFloat(rate.getText().toString()), Float.parseFloat(pricedisplay.getText().toString()), Float.parseFloat(discount_percentage_value), Float.parseFloat(db.getTaxRate()), Float.parseFloat("0"), Float.parseFloat(gross_value.getText().toString()), Float.parseFloat(net_value.getText().toString()), Float.parseFloat(vat_value.getText().toString()), Float.parseFloat(total_value.getText().toString()),""));
                               // db.updateSalesOrderDeliveryStatus(String.valueOf(sc.getKEY_ID()), "1");


                                Toast t = Utility.setToast(InvoiceDetailMoreItemListAddActivity.this, "Added Successfull");
                                t.show();
                                alertDialog.dismiss();

                            } else {
                                Utility.showDialog(InvoiceDetailMoreItemListAddActivity.this, "ERROR !", "Rate Required,\n Default Value 0 ", R.color.dialog_error_background);
                            }
                        } else {
                            Utility.showDialog(InvoiceDetailMoreItemListAddActivity.this, "ERROR !", "Quantity Required....", R.color.dialog_error_background);
                        }



                      /*  if (!quantity.getText().toString().isEmpty()) {
                            if (!rate.getText().toString().isEmpty()) {

                                mCartItemCount = mCartItemCount + 1;
                                setupBadge();

                                db.addSalesOrderItem(new SalesOrderItemClass(VANSALE_DOC_NO,String.valueOf(salesOrderLastId), VANSALE_cus, warehouse, getItem.get(position).getItem_name(), spinner_value, getItem.get(position).getItem_code(), Float.parseFloat(quantity.getText().toString()), Float.parseFloat(rate.getText().toString()), Float.parseFloat(pricedisplay.getText().toString()), Float.parseFloat(discount_percentage_value), Float.parseFloat(taxRate), Float.parseFloat("0"), Float.parseFloat(gross_value.getText().toString()), Float.parseFloat(net_value.getText().toString()), Float.parseFloat(vat_value.getText().toString()), Float.parseFloat(total_value.getText().toString()), UNSYNC_STATUS));

                                // selectedItemClasses.add(new SelectedItemClass(quantity.getText().toString(), getItem.get(position).getItem_name(), rate.getText().toString(), spinner_value, getItem.get(position).getItem_code(), pricedisplay.getText().toString(), discount_percentage.getText().toString(), taxRate, "0", warehouse, gross_value.getText().toString(), net_value.getText().toString(), vat_value.getText().toString(), total_value.getText().toString()));

                                Toast t = Utility.setToast(InvoiceItemListAddActivity.this, "Added to cart");
                                t.show();
                                alertDialog.dismiss();

                            } else {
                                Utility.showDialog(InvoiceItemListAddActivity.this, "ERROR !", "Rate Required,\n Default Value 0 ", R.color.dialog_error_background);
                            }
                        } else {
                            Utility.showDialog(InvoiceItemListAddActivity.this, "ERROR !", "Quantity Required....", R.color.dialog_error_background);
                        }

*/


                    }
                });

                closeid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                    }
                });


            }
        });
        RECYCLER_VIEW.setAdapter(orderListAdapter);


    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.done_request:
                doneClick();
                break;
            case R.id.cancel_request:
                if (scanStatus == 0) {
                    finish();
                }else if (scanStatus == 1){
                    displayItem();
                }
                //finish();
                break;

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.invoice_item_list_activity_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setQueryHint("Search Here");
        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(InvoiceDetailMoreItemListAddActivity.this, R.drawable.ic_search_white));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                //if (!getSalesOrder.isEmpty()) {

                if (s != null && !s.isEmpty()) {

                    int textlength = s.length();
                    final ArrayList<ItemClass> tempArrayList = new ArrayList<ItemClass>();
                    for (ItemClass c : getItem) {
                        if (textlength <= c.getItem_code().length()) {
                            if (c.getItem_name().toLowerCase().contains(s.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }
                   /* getSalesOrder.clear();
                    getSalesOrder.addAll(tempArrayList);*/
                    orderListAdapter = new SalesOrderListAdapter(getApplicationContext(), tempArrayList, new SalesOrderListAdapter.CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, final int position) {

                            inflater1 = InvoiceDetailMoreItemListAddActivity.this.getLayoutInflater();
                            dialogBuilder = new AlertDialog.Builder(InvoiceDetailMoreItemListAddActivity.this);
                            dialogView = inflater1.inflate(R.layout.orderpopup, null);
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
                            dialogBuilder.setView(dialogView);
                            alertDialog = dialogBuilder.create();
                            alertDialog.show();

                            final List<ItemDetailClass> getItemDetail = db.getItemDetail(tempArrayList.get(position).getItem_code());
                            // List<ItemDetailClass> getItemDetail = db.getItemDetail("IND.CON.HIJ.0524");
                            List<String> uom = new ArrayList<String>();

                            for (ItemDetailClass it : getItemDetail) {

                                Log.i("UUU", "<==" + it.getUom());
                                uom.add(it.getUom());
                            }

                            try {
                                spinner_value = getItemDetail.get(0).getUom();
                            } catch (Exception e) {

                            }

                            rate.setText("0.0");
                            pricedisplay.setText("0.0");
                            discount_percentage.setText(discount_percentage_value + "%");

                            try {

                                if (getItemDetail.get(0).getPrice() == null) {
                                    pricedisplay.setText("0.0");
                                    rate.setText("0.0");
                                } else {
                                    // pricedisplay.setText(getItemDetail.get(0).getPrice());
                                    pricedisplay.setText(getItemDetail.get(0).getPrice());
                                    rate.setText(getItemDetail.get(0).getPrice());
                                }
                            } catch (Exception e) {

                            }
                            spinner.setItems(uom);
                            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                                @Override
                                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                                    //  Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                                    spinner_value = item;

                                    if (getItemDetail.get(position).getPrice() == null) {
                                        pricedisplay.setText("0.0");
                                    } else {
                                        // pricedisplay.setText(getItemDetail.get(position).getPrice());
                                        pricedisplay.setText(getItemDetail.get(position).getPrice());
                                        rate.setText(getItemDetail.get(position).getPrice());

                                        Float gr_tot = Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString());
                                        gross_value.setText(String.valueOf(Utility.round(gr_tot, 2)));

                                        Float net_tot = Float.parseFloat(rate.getText().toString()) * Float.parseFloat(quantity.getText().toString());
                                        net_value.setText(String.valueOf(Utility.round(net_tot, 2)));

                           /* Float vat_tot = Float.parseFloat(db.getTaxRate()) * Float.parseFloat(quantity.getText().toString());
                            vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));*/

                                        Float vat_tot = ((Float.parseFloat(db.getTaxRate()) * Float.parseFloat(rate.getText().toString())) / 100) * Float.parseFloat(quantity.getText().toString());
                                        vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));

                                        Float tot = net_tot + vat_tot;
                                        total_value.setText(String.valueOf(Utility.round(tot, 2)));

                                        // Float prcnt = ((Float.parseFloat(pricedisplay.getText().toString()) - Float.parseFloat(rate.getText().toString())) / Float.parseFloat(pricedisplay.getText().toString())) * 100;
                                        Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - (Float.parseFloat(rate.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;
                                        //  discount_percentage_value = String.valueOf(Math.round(prcnt));
                                        if (Math.round(prcnt) < 0) {
                                            discount_percentage_value = "0";
                                        } else {
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
                                        // rate.setText(String.valueOf(Utility.round(gr_tot, 2)));

                                        Float net_tot = Float.parseFloat(rate.getText().toString()) * Float.parseFloat(s.toString());
                                        net_value.setText(String.valueOf(Utility.round(net_tot, 2)));

                                       /* Float vat_tot = Float.parseFloat(db.getTaxRate()) * Float.parseFloat(s.toString());
                                        vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));*/

                                        Float vat_tot = ((Float.parseFloat(db.getTaxRate()) * Float.parseFloat(rate.getText().toString())) / 100) * Float.parseFloat(s.toString());
                                        vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));

                                        Float tot = net_tot + vat_tot;
                                        total_value.setText(String.valueOf(Utility.round(tot, 2)));

                                        Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(s.toString())) - (Float.parseFloat(rate.getText().toString()) * Float.parseFloat(s.toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(s.toString()))) * 100;
                                        // discount_percentage_value = String.valueOf(Math.round(prcnt));
                                        if (Math.round(prcnt) < 0) {
                                            discount_percentage_value = "0";
                                        } else {
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

                                        // Float prcnt = ((Float.parseFloat(pricedisplay.getText().toString()) - Float.parseFloat(s.toString())) / Float.parseFloat(pricedisplay.getText().toString())) * 100;
                                        Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - (Float.parseFloat(s.toString()) * Float.parseFloat(quantity.getText().toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;
                                        //  discount_percentage_value = String.valueOf(Math.round(prcnt));
                                        if (Math.round(prcnt) < 0) {
                                            discount_percentage_value = "0";
                                        } else {
                                            discount_percentage_value = String.valueOf(Math.round(prcnt));
                                        }
                                        discount_percentage.setText(discount_percentage_value + "%");

                                    }

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });


                            quantity.setText("1");

                            quantity.setSelection(quantity.getText().length());
                            product_item_code.setText(tempArrayList.get(position).getItem_code());
                            product_discription.setText(tempArrayList.get(position).getDescription());
                            // discount_percentage.setText("0.0");

                            addtocart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (!quantity.getText().toString().isEmpty()) {
                                        if (!rate.getText().toString().isEmpty()) {

                                            return_data.add(new SalesOrderItemClass(String.valueOf(db.getSalesInvoiceHighestID()), db.getWarehouse(), getItem.get(position).getItem_name(), spinner_value, getItem.get(position).getItem_code(), Float.parseFloat(quantity.getText().toString()), Float.parseFloat(rate.getText().toString()), Float.parseFloat(pricedisplay.getText().toString()), Float.parseFloat(discount_percentage_value), Float.parseFloat(db.getTaxRate()), Float.parseFloat("0"), Float.parseFloat(gross_value.getText().toString()), Float.parseFloat(net_value.getText().toString()), Float.parseFloat(vat_value.getText().toString()), Float.parseFloat(total_value.getText().toString()), 0, "0",""));
                                            // itemClasses.add(new SalesOrderItemClass());

                                            // db.addSalesInvoiceItems(new SalesInvoiceItemClass(db.getSalesInvoiceHighestID(),db.getWarehouse(),getItem.get(position).getItem_name(),spinner_value,getItem.get(position).getItem_code(),"","",Float.parseFloat(quantity.getText().toString()),Float.parseFloat(rate.getText().toString()),Float.parseFloat(pricedisplay.getText().toString()),Float.parseFloat(discount_percentage_value),Float.parseFloat(db.getTaxRate()),Float.parseFloat("0")));

                                            Toast t = Utility.setToast(InvoiceDetailMoreItemListAddActivity.this, "Added Successfull");
                                            t.show();
                                            alertDialog.dismiss();

                                        } else {
                                            Utility.showDialog(InvoiceDetailMoreItemListAddActivity.this, "ERROR !", "Rate Required,\n Default Value 0 ", R.color.dialog_error_background);
                                        }
                                    } else {
                                        Utility.showDialog(InvoiceDetailMoreItemListAddActivity.this, "ERROR !", "Quantity Required....", R.color.dialog_error_background);
                                    }



                                  /*  if (!quantity.getText().toString().isEmpty()) {
                                        if (!rate.getText().toString().isEmpty()) {

                                            mCartItemCount = mCartItemCount + 1;
                                            setupBadge();

                                            db.addSalesOrderItem(new SalesOrderItemClass(VANSALE_DOC_NO,String.valueOf(salesOrderLastId), VANSALE_cus, warehouse, tempArrayList.get(position).getItem_name(), spinner_value, tempArrayList.get(position).getItem_code(), Float.parseFloat(quantity.getText().toString()), Float.parseFloat(rate.getText().toString()), Float.parseFloat(pricedisplay.getText().toString()), Float.parseFloat(discount_percentage.getText().toString()), Float.parseFloat(taxRate), Float.parseFloat("0"), Float.parseFloat(gross_value.getText().toString()), Float.parseFloat(net_value.getText().toString()), Float.parseFloat(vat_value.getText().toString()), Float.parseFloat(total_value.getText().toString()), UNSYNC_STATUS,"0"));

                                            // selectedItemClasses.add(new SelectedItemClass(quantity.getText().toString(), getItem.get(position).getItem_name(), rate.getText().toString(), spinner_value, getItem.get(position).getItem_code(), pricedisplay.getText().toString(), discount_percentage.getText().toString(), taxRate, "0", warehouse, gross_value.getText().toString(), net_value.getText().toString(), vat_value.getText().toString(), total_value.getText().toString()));

                                            Toast t = Utility.setToast(SalesOrderItemListActivity.this, "Added to cart");
                                            t.show();
                                            alertDialog.dismiss();

                                        } else {
                                            Utility.showDialog(SalesOrderItemListActivity.this, "ERROR !", "Rate Required,\n Default Value 0 ", R.color.dialog_error_background);
                                        }
                                    } else {
                                        Utility.showDialog(SalesOrderItemListActivity.this, "ERROR !", "Quantity Required....", R.color.dialog_error_background);
                                    }
*/

                                }
                            });

                            closeid.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();

                                }
                            });


                        }
                    });
                    RECYCLER_VIEW.setAdapter(orderListAdapter);

                } else {

                    orderListAdapter = new SalesOrderListAdapter(getApplicationContext(), getItem, new SalesOrderListAdapter.CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, final int position) {

                            inflater1 = InvoiceDetailMoreItemListAddActivity.this.getLayoutInflater();
                            dialogBuilder = new AlertDialog.Builder(InvoiceDetailMoreItemListAddActivity.this);
                            dialogView = inflater1.inflate(R.layout.orderpopup, null);
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
                            dialogBuilder.setView(dialogView);
                            alertDialog = dialogBuilder.create();
                            alertDialog.show();

                            final List<ItemDetailClass> getItemDetail = db.getItemDetail(getItem.get(position).getItem_code());
                            // List<ItemDetailClass> getItemDetail = db.getItemDetail("IND.CON.HIJ.0524");
                            List<String> uom = new ArrayList<String>();

                            for (ItemDetailClass it : getItemDetail) {

                                Log.i("UUU", "<==" + it.getUom());
                                uom.add(it.getUom());
                            }

                            try {
                                spinner_value = getItemDetail.get(0).getUom();
                            } catch (Exception e) {

                            }

                            rate.setText("0.0");
                            pricedisplay.setText("0.0");
                            discount_percentage.setText(discount_percentage_value + "%");

                            try {

                                if (getItemDetail.get(0).getPrice() == null) {
                                    pricedisplay.setText("0.0");
                                    rate.setText("0.0");
                                } else {
                                    // pricedisplay.setText(getItemDetail.get(0).getPrice());
                                    pricedisplay.setText(getItemDetail.get(0).getPrice());
                                    rate.setText(getItemDetail.get(0).getPrice());
                                }
                            } catch (Exception e) {

                            }
                            spinner.setItems(uom);
                            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                                @Override
                                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                                    //  Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                                    spinner_value = item;

                                    if (getItemDetail.get(position).getPrice() == null) {
                                        pricedisplay.setText("0.0");
                                    } else {
                                        // pricedisplay.setText(getItemDetail.get(position).getPrice());

                                        pricedisplay.setText(getItemDetail.get(position).getPrice());
                                        rate.setText(getItemDetail.get(position).getPrice());

                                        Float gr_tot = Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString());
                                        gross_value.setText(String.valueOf(Utility.round(gr_tot, 2)));

                                        Float net_tot = Float.parseFloat(rate.getText().toString()) * Float.parseFloat(quantity.getText().toString());
                                        net_value.setText(String.valueOf(Utility.round(net_tot, 2)));

                           /* Float vat_tot = Float.parseFloat(db.getTaxRate()) * Float.parseFloat(quantity.getText().toString());
                            vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));*/

                                        Float vat_tot = ((Float.parseFloat(db.getTaxRate()) * Float.parseFloat(rate.getText().toString())) / 100) * Float.parseFloat(quantity.getText().toString());
                                        vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));

                                        Float tot = net_tot + vat_tot;
                                        total_value.setText(String.valueOf(Utility.round(tot, 2)));

                                        // Float prcnt = ((Float.parseFloat(pricedisplay.getText().toString()) - Float.parseFloat(rate.getText().toString())) / Float.parseFloat(pricedisplay.getText().toString())) * 100;
                                        Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - (Float.parseFloat(rate.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;
                                        //  discount_percentage_value = String.valueOf(Math.round(prcnt));
                                        if (Math.round(prcnt) < 0) {
                                            discount_percentage_value = "0";
                                        } else {
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
                                        // discount_percentage_value = String.valueOf(Math.round(prcnt));
                                        if (Math.round(prcnt) < 0) {
                                            discount_percentage_value = "0";
                                        } else {
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

                                        // Float prcnt = ((Float.parseFloat(pricedisplay.getText().toString()) - Float.parseFloat(s.toString())) / Float.parseFloat(pricedisplay.getText().toString())) * 100;
                                        Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - (Float.parseFloat(s.toString()) * Float.parseFloat(quantity.getText().toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;
                                        //  discount_percentage_value = String.valueOf(Math.round(prcnt));
                                        if (Math.round(prcnt) < 0) {
                                            discount_percentage_value = "0";
                                        } else {
                                            discount_percentage_value = String.valueOf(Math.round(prcnt));
                                        }
                                        discount_percentage.setText(discount_percentage_value + "%");

                                    }

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });


                            quantity.setText("1");

                            quantity.setSelection(quantity.getText().length());
                            product_item_code.setText(getItem.get(position).getItem_code());
                            product_discription.setText(getItem.get(position).getDescription());
                            // discount_percentage.setText("0.0");

                            addtocart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (!quantity.getText().toString().isEmpty()) {
                                        if (!rate.getText().toString().isEmpty()) {

                                            return_data.add(new SalesOrderItemClass(String.valueOf(db.getSalesInvoiceHighestID()), db.getWarehouse(), getItem.get(position).getItem_name(), spinner_value, getItem.get(position).getItem_code(), Float.parseFloat(quantity.getText().toString()), Float.parseFloat(rate.getText().toString()), Float.parseFloat(pricedisplay.getText().toString()), Float.parseFloat(discount_percentage_value), Float.parseFloat(db.getTaxRate()), Float.parseFloat("0"), Float.parseFloat(gross_value.getText().toString()), Float.parseFloat(net_value.getText().toString()), Float.parseFloat(vat_value.getText().toString()), Float.parseFloat(total_value.getText().toString()), 0, "0",""));
                                            // itemClasses.add(new SalesOrderItemClass());

                                            // db.addSalesInvoiceItems(new SalesInvoiceItemClass(db.getSalesInvoiceHighestID(),db.getWarehouse(),getItem.get(position).getItem_name(),spinner_value,getItem.get(position).getItem_code(),"","",Float.parseFloat(quantity.getText().toString()),Float.parseFloat(rate.getText().toString()),Float.parseFloat(pricedisplay.getText().toString()),Float.parseFloat(discount_percentage_value),Float.parseFloat(db.getTaxRate()),Float.parseFloat("0")));

                                            Toast t = Utility.setToast(InvoiceDetailMoreItemListAddActivity.this, "Added Successfull");
                                            t.show();
                                            alertDialog.dismiss();

                                        } else {
                                            Utility.showDialog(InvoiceDetailMoreItemListAddActivity.this, "ERROR !", "Rate Required,\n Default Value 0 ", R.color.dialog_error_background);
                                        }
                                    } else {
                                        Utility.showDialog(InvoiceDetailMoreItemListAddActivity.this, "ERROR !", "Quantity Required....", R.color.dialog_error_background);
                                    }



                                   /* if (!quantity.getText().toString().isEmpty()) {
                                        if (!rate.getText().toString().isEmpty()) {

                                            mCartItemCount = mCartItemCount + 1;
                                            setupBadge();

                                            db.addSalesOrderItem(new SalesOrderItemClass(VANSALE_DOC_NO,String.valueOf(salesOrderLastId), VANSALE_cus, warehouse, getItem.get(position).getItem_name(), spinner_value, getItem.get(position).getItem_code(), Float.parseFloat(quantity.getText().toString()), Float.parseFloat(rate.getText().toString()), Float.parseFloat(pricedisplay.getText().toString()), Float.parseFloat(discount_percentage.getText().toString()), Float.parseFloat(taxRate), Float.parseFloat("0"), Float.parseFloat(gross_value.getText().toString()), Float.parseFloat(net_value.getText().toString()), Float.parseFloat(vat_value.getText().toString()), Float.parseFloat(total_value.getText().toString()), UNSYNC_STATUS,"0"));

                                            // selectedItemClasses.add(new SelectedItemClass(quantity.getText().toString(), getItem.get(position).getItem_name(), rate.getText().toString(), spinner_value, getItem.get(position).getItem_code(), pricedisplay.getText().toString(), discount_percentage.getText().toString(), taxRate, "0", warehouse, gross_value.getText().toString(), net_value.getText().toString(), vat_value.getText().toString(), total_value.getText().toString()));

                                            Toast t = Utility.setToast(SalesOrderItemListActivity.this, "Added to cart");
                                            t.show();
                                            alertDialog.dismiss();

                                        } else {
                                            Utility.showDialog(SalesOrderItemListActivity.this, "ERROR !", "Rate Required,\n Default Value 0 ", R.color.dialog_error_background);
                                        }
                                    } else {
                                        Utility.showDialog(SalesOrderItemListActivity.this, "ERROR !", "Quantity Required....", R.color.dialog_error_background);
                                    }
*/

                                }
                            });

                            closeid.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();

                                }
                            });


                        }
                    });
                    RECYCLER_VIEW.setAdapter(orderListAdapter);

                    /*getSalesOrder.clear();
                    getSalesOrder.addAll(getSalesOrder_temp);*/
                   /* orderRecyclerViewAdapter = new SalesOrderRecyclerViewAdapter(getApplicationContext(), getSalesOrder);
                    mRecyclerView.setAdapter(orderRecyclerViewAdapter);*/


                }

               /* }else {
                    Utility.showDialog(SalesOrderActivity.this,"WARNING","No Data Found!",R.color.dialog_background);
                }*/

                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                if (scanStatus == 0) {
                    finish();
                }else if (scanStatus == 1){
                    displayItem();
                }
                //finish();
                return true;

            case R.id.menu_scan:
                IntentIntegrator integrator = new IntentIntegrator(InvoiceDetailMoreItemListAddActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan a barcode");
               /* integrator.setOrientationLocked(true);
                integrator.setCaptureActivity(CaptureActivityPortrait.class);*/
                integrator.initiateScan();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {

            scanStatus = 1;

            // handle scan result
            final String result = scanResult.getContents();
            Log.d(LOGTAG, "Have scan result in your app activity :" + result);
           /* android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(InvoiceItemListAddActivity.this).create();
            alertDialog.setTitle("Scan result");
            alertDialog.setMessage(result);
            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {*/

            String sResult = db.getItemCodeByBarcode(result);

            getItembyBarcode = db.getItemByBarcode(sResult);

            if (!getItembyBarcode.isEmpty()) {


                orderListAdapter = new SalesOrderListAdapter(getApplicationContext(), getItembyBarcode, new SalesOrderListAdapter.CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, final int position) {

                        inflater = InvoiceDetailMoreItemListAddActivity.this.getLayoutInflater();
                        dialogBuilder = new AlertDialog.Builder(InvoiceDetailMoreItemListAddActivity.this);
                        dialogView = inflater.inflate(R.layout.orderpopup, null);
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
                        dialogBuilder.setView(dialogView);
                        alertDialog2 = dialogBuilder.create();
                        alertDialog2.show();


                        final List<ItemDetailClass> getItemDetail = db.getItemDetail(getItembyBarcode.get(position).getItem_code());
                        // List<ItemDetailClass> getItemDetail = db.getItemDetail("IND.CON.HIJ.0524");
                        List<String> uom = new ArrayList<String>();

                        for (ItemDetailClass it : getItemDetail) {

                            Log.i("UUU", "<==" + it.getUom());
                            uom.add(it.getUom());
                        }

                        try {
                            spinner_value = getItemDetail.get(0).getUom();
                        } catch (Exception e) {

                        }

                        rate.setText("0.0");
                        pricedisplay.setText("0.0");
                        discount_percentage.setText(discount_percentage_value + "%");

                        try {

                            if (getItemDetail.get(0).getPrice() == null) {
                                pricedisplay.setText("0.0");
                                rate.setText("0.0");
                            } else {
                                pricedisplay.setText(getItemDetail.get(0).getPrice());
                                rate.setText(getItemDetail.get(0).getPrice());
                            }
                        } catch (Exception e) {

                        }
                        spinner.setItems(uom);
                        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                            @Override
                            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {


                                spinner_value = item;

                                // Snackbar.make(view, "Clicked " + spinner_value, Snackbar.LENGTH_LONG).show();

                                if (getItemDetail.get(position).getPrice() == null) {
                                    pricedisplay.setText("0.0");
                                    rate.setText("0.0");

                                } else {

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

                                    // Float prcnt = ((Float.parseFloat(pricedisplay.getText().toString()) - Float.parseFloat(rate.getText().toString())) / Float.parseFloat(pricedisplay.getText().toString())) * 100;
                                    Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - (Float.parseFloat(rate.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;
                                    // discount_percentage_value = String.valueOf(Math.round(prcnt));
                                    if (Math.round(prcnt) < 0) {
                                        discount_percentage_value = "0";
                                    } else {
                                        discount_percentage_value = String.valueOf(Math.round(prcnt));
                                    }
                                    discount_percentage.setText(discount_percentage_value + "%");



                           /* discount_percentage_value="0";
                            discount_percentage.setText(discount_percentage_value+"%");*/
                           /* Float vat_tot = ((Float.parseFloat(db.getTaxRate()) * Float.parseFloat(getItemDetail.get(position).getPrice()))/100) * Float.parseFloat(quantity.getText().toString());
                            vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));*/

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
                                    // rate.setText(String.valueOf(Utility.round(gr_tot, 2)));


                                    Float net_tot = Float.parseFloat(rate.getText().toString()) * Float.parseFloat(s.toString());
                                    net_value.setText(String.valueOf(Utility.round(net_tot, 2)));

                           /* Float vat_tot = Float.parseFloat(db.getTaxRate()) * Float.parseFloat(s.toString());
                            vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));*/

                                    Float vat_tot = ((Float.parseFloat(db.getTaxRate()) * Float.parseFloat(rate.getText().toString())) / 100) * Float.parseFloat(s.toString());
                                    vat_value.setText(String.valueOf(Utility.round(vat_tot, 2)));


                                    Float tot = net_tot + vat_tot;
                                    total_value.setText(String.valueOf(Utility.round(tot, 2)));

                                    Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(s.toString())) - (Float.parseFloat(rate.getText().toString()) * Float.parseFloat(s.toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(s.toString()))) * 100;
                                    // discount_percentage_value = String.valueOf(Math.round(prcnt));
                                    if (Math.round(prcnt) < 0) {
                                        discount_percentage_value = "0";
                                    } else {
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

                                    Float prcnt = (((Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString())) - (Float.parseFloat(s.toString()) * Float.parseFloat(quantity.getText().toString()))) / (Float.parseFloat(pricedisplay.getText().toString()) * Float.parseFloat(quantity.getText().toString()))) * 100;
                                    //  discount_percentage_value = String.valueOf(Math.round(prcnt));
                                    if (Math.round(prcnt) < 0) {
                                        discount_percentage_value = "0";
                                    } else {
                                        discount_percentage_value = String.valueOf(Math.round(prcnt));
                                    }
                                    discount_percentage.setText(discount_percentage_value + "%");

                           /* Float prcnt = (Float.parseFloat(s.toString()) / Float.parseFloat(pricedisplay.getText().toString())) * 100;
                            discount_percentage_value = String.valueOf(Math.round(prcnt));
                            discount_percentage.setText(discount_percentage_value+"%");*/


                                }

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });


                        quantity.setText("1");

                        quantity.setSelection(quantity.getText().length());
                        product_item_code.setText(getItembyBarcode.get(position).getItem_code());
                        product_discription.setText(getItembyBarcode.get(position).getDescription());


                        addtocart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                if (!quantity.getText().toString().isEmpty()) {
                                    if (!rate.getText().toString().isEmpty()) {

                                        return_data.add(new SalesOrderItemClass(String.valueOf(db.getSalesInvoiceHighestID()), db.getWarehouse(), getItembyBarcode.get(position).getItem_name(), spinner_value, getItembyBarcode.get(position).getItem_code(), Float.parseFloat(quantity.getText().toString()), Float.parseFloat(rate.getText().toString()), Float.parseFloat(pricedisplay.getText().toString()), Float.parseFloat(discount_percentage_value), Float.parseFloat(db.getTaxRate()), Float.parseFloat("0"), Float.parseFloat(gross_value.getText().toString()), Float.parseFloat(net_value.getText().toString()), Float.parseFloat(vat_value.getText().toString()), Float.parseFloat(total_value.getText().toString()), 0, "0",""));
                                        // itemClasses.add(new SalesOrderItemClass());

                                        // db.addSalesInvoiceItems(new SalesInvoiceItemClass(db.getSalesInvoiceHighestID(),db.getWarehouse(),getItem.get(position).getItem_name(),spinner_value,getItem.get(position).getItem_code(),"","",Float.parseFloat(quantity.getText().toString()),Float.parseFloat(rate.getText().toString()),Float.parseFloat(pricedisplay.getText().toString()),Float.parseFloat(discount_percentage_value),Float.parseFloat(db.getTaxRate()),Float.parseFloat("0")));




                                        Toast t = Utility.setToast(InvoiceDetailMoreItemListAddActivity.this, "Added Successfull");
                                        t.show();
                                        alertDialog2.dismiss();

                                    } else {
                                        Utility.showDialog(InvoiceDetailMoreItemListAddActivity.this, "ERROR !", "Rate Required,\n Default Value 0 ", R.color.dialog_error_background);
                                    }
                                } else {
                                    Utility.showDialog(InvoiceDetailMoreItemListAddActivity.this, "ERROR !", "Quantity Required....", R.color.dialog_error_background);
                                }



                                              /*  if (!quantity.getText().toString().isEmpty()) {
                                                    if (!rate.getText().toString().isEmpty()) {

                                                        mCartItemCount = mCartItemCount + 1;
                                                        setupBadge();

                                                        db.addSalesOrderItem(new SalesOrderItemClass(VANSALE_DOC_NO,String.valueOf(salesOrderLastId), VANSALE_cus, warehouse, getItem.get(position).getItem_name(), spinner_value, getItem.get(position).getItem_code(), Float.parseFloat(quantity.getText().toString()), Float.parseFloat(rate.getText().toString()), Float.parseFloat(pricedisplay.getText().toString()), Float.parseFloat(discount_percentage_value), Float.parseFloat(taxRate), Float.parseFloat("0"), Float.parseFloat(gross_value.getText().toString()), Float.parseFloat(net_value.getText().toString()), Float.parseFloat(vat_value.getText().toString()), Float.parseFloat(total_value.getText().toString()), UNSYNC_STATUS,"0"));

                                                        // selectedItemClasses.add(new SelectedItemClass(quantity.getText().toString(), getItem.get(position).getItem_name(), rate.getText().toString(), spinner_value, getItem.get(position).getItem_code(), pricedisplay.getText().toString(), discount_percentage.getText().toString(), taxRate, "0", warehouse, gross_value.getText().toString(), net_value.getText().toString(), vat_value.getText().toString(), total_value.getText().toString()));

                                                        Toast t = Utility.setToast(SalesOrderItemListActivity.this, "Added to cart");
                                                        t.show();
                                                        alertDialog2.dismiss();

                                                    } else {
                                                        Utility.showDialog(SalesOrderItemListActivity.this, "ERROR !", "Rate Required,\n Default Value 0 ", R.color.dialog_error_background);
                                                    }
                                                } else {
                                                    Utility.showDialog(SalesOrderItemListActivity.this, "ERROR !", "Quantity Required....", R.color.dialog_error_background);
                                                }

*/
                            }
                        });


                        closeid.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog2.dismiss();

                            }
                        });


                    }
                });
                RECYCLER_VIEW.setAdapter(orderListAdapter);


            }/* else {
                Utility.showDialog(InvoiceDetailMoreItemListAddActivity.this, "ERROR !", "No Result Found...", R.color.dialog_error_background);
            }*/


            // dialog.dismiss();
                       /* }
                    });
            alertDialog.show();*/
        }
    }

    @Override
    public void onBackPressed() {
        if (scanStatus == 0) {
            finish();
        }else if (scanStatus == 1){
            displayItem();
        }
    }

    private void doneClick() {

        Intent intent = new Intent();

        for (SalesOrderItemClass sd : return_data) {

            Log.i("RRRR", "<==" + sd.getSALES_ORDER_LAST_ID());
            Log.i("RRRR", "<==" + sd.getSALES_ITEM_NAME());
            Log.i("RRRR", "<==" + sd.getSALES_ITEM_CODE());
            Log.i("RRRR", "<==" + sd.getSALES_STOCK_UOM());
            Log.i("RRRR", "<==" + sd.getSALES_QTY());
            Log.i("RRRR", "<==" + sd.getSALES_NET());

        }

      //  InvoiceItemListActivity.setSelectedItem(return_data);

        setResult(InvoiceItemListActivity.ITEM_ADD_REQUEST_CODE, intent);
        finish();

    }
}
