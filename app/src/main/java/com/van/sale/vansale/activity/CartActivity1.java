package com.van.sale.vansale.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.CartRecyclerViewAdapter;
import com.van.sale.vansale.model.Cart;
import com.van.sale.vansale.model.SalesOrderItemClass;
import com.van.sale.vansale.model.SelectedItemClass;

import java.util.ArrayList;
import java.util.List;

public class CartActivity1 extends AppCompatActivity implements View.OnClickListener, CartDialog.CartDialogListener {

    private static String selected_discount_percentage, selected_rate, selected_quantity, selected_price_display, selected_item_code;
    private static int selected_position;

    /*  https://stackoverflow.com/questions/23854119/using-interface-along-with-dialogs-in-android  */

    private RecyclerView mRecyclerView;
    private Button cancel_request, checkout_request;
    private List<Cart> carts;
    private CartRecyclerViewAdapter adapter;
    private List<SelectedItemClass> channelsList;
    TextView gross_value, net_value, vat_value, total_value, addtocart, discount_percentage, product_item_code,
            product_discription, pricedisplay, closeid;
    private Float gross = 0.0f, net = 0.0f, vat = 0.0f, tot = 0.0f;
    private LayoutInflater inflater;
    private AlertDialog.Builder dialogBuilder;
    private View dialogView;
    private EditText rate, quantity;
    private MaterialSpinner spinner;
    private AlertDialog alertDialog;
    private DatabaseHandler db;
    private String spinner_value;
    private int uom_position = 0;
    private CartDialog cartDialog;

    private Float DEFAULT_CONVERSION_RATE = Float.valueOf(1);
    private Float DEFAULT_PLC_CONVERSION_RATE = Float.valueOf(1);
    private int DEFAULT_DOC_STATUS = 0;
    private int UNSYNC_STATUS = 0;
  //  private String SALES_ORDER_OWNER = "Administrator";
    private String SALES_ORDER_OWNER;
    private String DEFAULT_BILLING_STATUS = "Not Billed";
    private String DEFAULT_ORDER_TYPE = "Sales";
    private String DEFAULT_STATUS = "Draft";
    private String DEFAULT_COMPANY;
    private String SALES_ORDER_CURRENCY;
    private String SALES_ORDER_PRICE_LIST_CURRENCY;
    private String SALES_ORDER_NAMING_SERIES;
    private String DEVICE_ID;

    private int salesOrderLastId=0;
    private List<SalesOrderItemClass> getSalesOrderItem;


    private String VANSALE_DOC_NO, VANSALE_CREATE_DATE, VANSALE_DELIVERY_DATE, VANSALE_PO_NO, VANSALE_cus;


    // public String selected_item_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        carts = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cancel_request = (Button) findViewById(R.id.cancel_request);
        checkout_request = (Button) findViewById(R.id.save_request);

        gross_value = (TextView) findViewById(R.id.gross_value);
        net_value = (TextView) findViewById(R.id.net_value);
        vat_value = (TextView) findViewById(R.id.vat_value);
        total_value = (TextView) findViewById(R.id.total_value);

        VANSALE_DOC_NO = Utility.getPrefs("VANSALE_DOC_NO",getApplicationContext());
        VANSALE_CREATE_DATE = Utility.getPrefs("VANSALE_CREATE_DATE",getApplicationContext());
        VANSALE_DELIVERY_DATE = Utility.getPrefs("VANSALE_DELIVERY_DATE",getApplicationContext());
        VANSALE_PO_NO = Utility.getPrefs("VANSALE_PO_NO",getApplicationContext());
        VANSALE_cus = Utility.getPrefs("VANSALE_cus",getApplicationContext());

        db = new DatabaseHandler(this);

        getSalesOrderItem = db.getSalesOrderItem(VANSALE_cus);

        for (SalesOrderItemClass si : getSalesOrderItem) {

            Log.i("CART", "<==" + si.getSALES_ITEM_NAME());
            Log.i("CART", "<==" + si.getSALES_STOCK_UOM());

            gross = gross + si.getSALES_GROSS();
            net = net + si.getSALES_NET();
            vat = vat + si.getSALES_VAT();
            tot = tot + si.getSALES_TOTAL();

        }

        gross_value.setText(String.valueOf(Utility.round(gross, 2)));
        net_value.setText(String.valueOf(Utility.round(net, 2)));
        vat_value.setText(String.valueOf(Utility.round(vat, 2)));
        total_value.setText(String.valueOf(Utility.round(tot, 2)));




        /*VANSALE_DOC_NO = getIntent().getStringExtra("VANSALE_DOC_NO");
        VANSALE_CREATE_DATE = getIntent().getStringExtra("VANSALE_DOC_NO");
        VANSALE_DELIVERY_DATE = getIntent().getStringExtra("VANSALE_DOC_NO");
        VANSALE_PO_NO = getIntent().getStringExtra("VANSALE_DOC_NO");
        VANSALE_cus = getIntent().getStringExtra("VANSALE_DOC_NO");*/




       /* SALES_ORDER_OWNER = db.getApiUsername();
        SALES_ORDER_CURRENCY = db.getCurrency();
        SALES_ORDER_PRICE_LIST_CURRENCY = db.getCurrency();
        DEFAULT_COMPANY = db.getCompanyName();
        SALES_ORDER_NAMING_SERIES = db.getSalesOrderName();
        DEVICE_ID = db.getDeviceIdFromSetting();

        Bundle getBundle = this.getIntent().getExtras();
        channelsList = getBundle.getParcelableArrayList("channel");

        refreshData(channelsList);*/
        // displayTotal();






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("CART");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        //  mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));


      /*  adapter = new CartRecyclerViewAdapter(getApplicationContext(), channelsList, new CartRecyclerViewAdapter.CartClickListener() {
            @Override
            public void onItemClick(View v, final int position) {

                selected_item_code = channelsList.get(position).getItem_code();
                selected_price_display = channelsList.get(position).getPrice_list();
                selected_quantity = channelsList.get(position).getQty();
                selected_discount_percentage = channelsList.get(position).getDiscount_percentage();
                selected_rate = channelsList.get(position).getRate();
                selected_position = position;

                cartDialog = new CartDialog();
                cartDialog.show(getSupportFragmentManager(), "example dialog");


            }

            @Override
            public void onItemDeleteClick(View v, final int position) {

                new AlertDialog.Builder(CartActivity1.this)
                        .setMessage("Are you sure you want to delete?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                                gross = gross - Float.parseFloat(channelsList.get(position).getGross());
                                net = net - Float.parseFloat(channelsList.get(position).getNet());
                                vat = vat - Float.parseFloat(channelsList.get(position).getVat());
                                tot = tot - Float.parseFloat(channelsList.get(position).getTotal());

                                channelsList.remove(position);
                                adapter.notifyDataSetChanged();

                                displayTotal();

                                Toast t = Utility.setToast(CartActivity1.this, "DELETED ");
                                t.show();

                            }
                        }).create().show();

            }
        });*/


        mRecyclerView.setAdapter(adapter);

        cancel_request.setOnClickListener(this);
        checkout_request.setOnClickListener(this);


    }


    private void refreshData(List<SelectedItemClass> channelsList) {

        gross = 0.0f;
        net = 0.0f;
        vat = 0.0f;
        tot = 0.0f;

        for (SelectedItemClass si : channelsList) {

            Log.i("CART", "<==" + si.getItem_name());
            Log.i("CART", "<==" + si.getStock_uom());

            gross = gross + Float.parseFloat(si.getGross());
            net = net + Float.parseFloat(si.getNet());
            vat = vat + Float.parseFloat(si.getVat());
            tot = tot + Float.parseFloat(si.getTotal());

        }

        gross_value.setText(String.valueOf(Utility.round(gross, 2)));
        net_value.setText(String.valueOf(Utility.round(net, 2)));
        vat_value.setText(String.valueOf(Utility.round(vat, 2)));
        total_value.setText(String.valueOf(Utility.round(tot, 2)));

    }

    private void displayTotal() {

        Toast.makeText(this, "Reached.....", Toast.LENGTH_SHORT).show();
        gross_value.setText(String.valueOf(Utility.round(gross, 2)));
        net_value.setText(String.valueOf(Utility.round(net, 2)));
        vat_value.setText(String.valueOf(Utility.round(vat, 2)));
        total_value.setText(String.valueOf(Utility.round(tot, 2)));


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.cancel_request:
                finish();
                break;
            case R.id.save_request:
                checkoutClick();
                break;

        }

    }

    private void checkoutClick() {


     //   db.addSalesOrder(new SalesOrderClass(DEFAULT_CONVERSION_RATE, DEFAULT_PLC_CONVERSION_RATE, DEFAULT_DOC_STATUS, UNSYNC_STATUS, VANSALE_CREATE_DATE, SALES_ORDER_OWNER, DEFAULT_BILLING_STATUS, VANSALE_PO_NO, VANSALE_cus, DEFAULT_ORDER_TYPE, DEFAULT_STATUS, DEFAULT_COMPANY, SALES_ORDER_NAMING_SERIES, VANSALE_DOC_NO, VANSALE_DELIVERY_DATE, SALES_ORDER_CURRENCY, SALES_ORDER_PRICE_LIST_CURRENCY, DEVICE_ID));

        salesOrderLastId = db.getSalesOrderHighestID();


        Log.i("EEE","<=="+DEFAULT_CONVERSION_RATE+ "," +DEFAULT_PLC_CONVERSION_RATE+ "," + DEFAULT_DOC_STATUS+ "," + UNSYNC_STATUS+ "," + VANSALE_CREATE_DATE+ "," + SALES_ORDER_OWNER+ "," + DEFAULT_BILLING_STATUS+ "," + VANSALE_PO_NO+ "," + VANSALE_cus+ "," + DEFAULT_ORDER_TYPE+ "," + DEFAULT_STATUS+ "," + DEFAULT_COMPANY+ "," + SALES_ORDER_NAMING_SERIES+ "," + VANSALE_DOC_NO+ "," + VANSALE_DELIVERY_DATE+ "," + SALES_ORDER_CURRENCY+ "," + SALES_ORDER_PRICE_LIST_CURRENCY+ "," + DEVICE_ID);

        for (SelectedItemClass sic : channelsList) {

            Log.i("EEE", "" + sic.getWarehouse() + "," + sic.getItem_name() + "," + sic.getStock_uom() + "," + sic.getItem_code() + "," + sic.getQty() + "," + sic.getRate() + "," + sic.getPrice_list() + "," + sic.getDiscount_percentage() + "," + sic.getTax_rate() + "," + sic.getTax_amount() + "," + sic.getGross() + "," + sic.getNet() + "," + sic.getVat() + "," + sic.getTotal());

           // db.addSalesOrderItem(new SalesOrderItemClass(String.valueOf(salesOrderLastId),VANSALE_cus, sic.getWarehouse(), sic.getItem_name(), sic.getStock_uom(), sic.getItem_code(), Float.parseFloat(sic.getQty()), Float.parseFloat(sic.getRate()), Float.parseFloat(sic.getPrice_list()), Float.parseFloat(sic.getDiscount_percentage()), Float.parseFloat(sic.getTax_rate()), Float.parseFloat(sic.getTax_amount()), Float.parseFloat(sic.getGross()), Float.parseFloat(sic.getNet()), Float.parseFloat(sic.getVat()), Float.parseFloat(sic.getTotal()), UNSYNC_STATUS));

        }

      //  db.updateSalesOrderDocNo(db.getSalesOrderDocNo()+1);

        Toast t = Utility.setToast(CartActivity1.this, "Added Successfully!");
        t.show();

        startActivity(new Intent(CartActivity1.this, SalesOrderActivity.class));
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static String getSelectedItemCode() {
        return selected_item_code;
    }

    public static String getSelectedPriceList() {
        return selected_price_display;
    }

    public static String getSelectedQuantity() {
        return selected_quantity;
    }

    public static String getSelectedDiscountPercentage() {
        return selected_discount_percentage;
    }

    public static String getSelectedRate() {
        return selected_rate;
    }

    @Override
    public void applyTexts(SelectedItemClass selectedItemClass) {

        channelsList.get(selected_position).setQty(selectedItemClass.getQty());
        channelsList.get(selected_position).setStock_uom(selectedItemClass.getStock_uom());
        channelsList.get(selected_position).setPrice_list(selectedItemClass.getPrice_list());
        channelsList.get(selected_position).setRate(selectedItemClass.getRate());
        channelsList.get(selected_position).setDiscount_percentage(selectedItemClass.getDiscount_percentage());
        channelsList.get(selected_position).setGross(selectedItemClass.getGross());
        channelsList.get(selected_position).setNet(selectedItemClass.getNet());
        channelsList.get(selected_position).setVat(selectedItemClass.getVat());
        channelsList.get(selected_position).setTotal(selectedItemClass.getTotal());

        adapter.notifyDataSetChanged();

        gross = 0.0f;
        net = 0.0f;
        vat = 0.0f;
        tot = 0.0f;

        for (SelectedItemClass si : channelsList) {

            Log.i("CART", "<==" + si.getItem_name());
            Log.i("CART", "<==" + si.getStock_uom());

            gross = gross + Float.parseFloat(si.getGross());
            net = net + Float.parseFloat(si.getNet());
            vat = vat + Float.parseFloat(si.getVat());
            tot = tot + Float.parseFloat(si.getTotal());

        }

        gross_value.setText(String.valueOf(Utility.round(gross, 2)));
        net_value.setText(String.valueOf(Utility.round(net, 2)));
        vat_value.setText(String.valueOf(Utility.round(vat, 2)));
        total_value.setText(String.valueOf(Utility.round(tot, 2)));

        cartDialog.dismiss();


    }

    @Override
    public void dismisDialog() {
        cartDialog.dismiss();
    }


}
