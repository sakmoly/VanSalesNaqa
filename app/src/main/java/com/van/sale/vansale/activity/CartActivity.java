package com.van.sale.vansale.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.van.sale.vansale.DialogDismiss;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.CartRecyclerViewAdapter;
import com.van.sale.vansale.model.Cart;
import com.van.sale.vansale.model.ItemDetailClass;
import com.van.sale.vansale.model.SalesOrderClass;
import com.van.sale.vansale.model.SalesOrderItemClass;
import com.van.sale.vansale.model.SelectedItemClass;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, CartDialog.CartDialogListener {

    private static String selected_uom,selected_discount_percentage, selected_rate, selected_quantity, selected_price_display, selected_item_code, selected_item_id;
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
    private String DEVICE_ID,SALES_ORDER_LAST_ID;

    private int salesOrderLastId = 0, BadgeCount = 0;
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

        SALES_ORDER_LAST_ID = getIntent().getStringExtra("SALES_ORDER_LAST_ID");

        VANSALE_DOC_NO = Utility.getPrefs("VANSALE_DOC_NO", getApplicationContext());
        VANSALE_CREATE_DATE = Utility.getPrefs("VANSALE_CREATE_DATE", getApplicationContext());
        VANSALE_DELIVERY_DATE = Utility.getPrefs("VANSALE_DELIVERY_DATE", getApplicationContext());
        VANSALE_PO_NO = Utility.getPrefs("VANSALE_PO_NO", getApplicationContext());
        VANSALE_cus = Utility.getPrefs("VANSALE_cus", getApplicationContext());

        BadgeCount = SalesOrderItemListActivity.getBadgeCount();

        db = new DatabaseHandler(this);

        getSalesOrderItem = db.getSalesOrderItem(SALES_ORDER_LAST_ID);

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


        adapter = new CartRecyclerViewAdapter(getApplicationContext(), getSalesOrderItem, new CartRecyclerViewAdapter.CartClickListener() {
            @Override
            public void onItemClick(View v, final int position) {

                selected_item_id = String.valueOf(getSalesOrderItem.get(position).getKEY_ID());
                selected_item_code = getSalesOrderItem.get(position).getSALES_ITEM_CODE();
                selected_price_display = String.valueOf(getSalesOrderItem.get(position).getSALES_PRICE_LIST_RATE());
                selected_quantity = String.valueOf((int) Math.round(getSalesOrderItem.get(position).getSALES_QTY()));
                selected_discount_percentage = String.valueOf(getSalesOrderItem.get(position).getSALES_DISCOUNT_PERCENTAGE());
                selected_rate = String.valueOf(getSalesOrderItem.get(position).getSALES_RATE());
                selected_uom = String.valueOf(getSalesOrderItem.get(position).getSALES_STOCK_UOM());
                selected_position = position;

                cartDialog = new CartDialog();
                cartDialog.show(getSupportFragmentManager(), "example dialog");


            }

            @Override
            public void onItemDeleteClick(View v, final int position) {

                new android.app.AlertDialog.Builder(CartActivity.this)
                        .setMessage("Are you sure you want to delete?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                               /* gross = gross - Float.parseFloat(channelsList.get(position).getGross());
                                net = net - Float.parseFloat(channelsList.get(position).getNet());
                                vat = vat - Float.parseFloat(channelsList.get(position).getVat());
                                tot = tot - Float.parseFloat(channelsList.get(position).getTotal());

                                channelsList.remove(position);
                                adapter.notifyDataSetChanged();*/

                                db.deleteOrder(getSalesOrderItem.get(position).getKEY_ID());

                                gross = gross - getSalesOrderItem.get(position).getSALES_GROSS();
                                net = net - getSalesOrderItem.get(position).getSALES_NET();
                                vat = vat - getSalesOrderItem.get(position).getSALES_VAT();
                                tot = tot - getSalesOrderItem.get(position).getSALES_TOTAL();

                             /*   TV_gross.setText(String.valueOf(Utility.round(gross, 2)));
                                TV_net.setText(String.valueOf(Utility.round(net, 2)));
                                TV_vat.setText(String.valueOf(Utility.round(vat, 2)));
                                TV_total.setText(String.valueOf(Utility.round(tot, 2)));*/

                                getSalesOrderItem.remove(position);
                                adapter.notifyDataSetChanged();

                                BadgeCount = BadgeCount - 1;
                                SalesOrderItemListActivity.setBadgeCount(BadgeCount);


                                displayTotal();

                                Toast t = Utility.setToast(CartActivity.this, "DELETED ");
                                t.show();

                            }
                        }).create().show();

            }
        });


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

        // Toast.makeText(this, "Reached.....", Toast.LENGTH_SHORT).show();
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

        Toast t = Utility.setToast(CartActivity.this, "Added Successfully!");
        t.show();

        startActivity(new Intent(CartActivity.this, SalesOrderActivity.class));
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                SalesOrderItemListActivity.setupBadge();
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

    public static String getSelectedId() {
        return selected_item_id;
    }

    public static String getSelectedUom() {
        return selected_uom;
    }

    @Override
    public void applyTexts(SelectedItemClass selectedItemClass) {

        getSalesOrderItem.get(selected_position).setSALES_QTY(Float.valueOf(selectedItemClass.getQty()));
        getSalesOrderItem.get(selected_position).setSALES_STOCK_UOM(selectedItemClass.getStock_uom());
        getSalesOrderItem.get(selected_position).setSALES_PRICE_LIST_RATE(Float.valueOf(selectedItemClass.getPrice_list()));
        getSalesOrderItem.get(selected_position).setSALES_RATE(Float.valueOf(selectedItemClass.getRate()));
        getSalesOrderItem.get(selected_position).setSALES_DISCOUNT_PERCENTAGE(Float.valueOf(selectedItemClass.getDiscount_percentage()));
        getSalesOrderItem.get(selected_position).setSALES_GROSS(Float.valueOf(selectedItemClass.getGross()));
        getSalesOrderItem.get(selected_position).setSALES_NET(Float.valueOf(selectedItemClass.getNet()));
        getSalesOrderItem.get(selected_position).setSALES_VAT(Float.valueOf(selectedItemClass.getVat()));
        getSalesOrderItem.get(selected_position).setSALES_TOTAL(Float.valueOf(selectedItemClass.getTotal()));

        adapter.notifyDataSetChanged();

        gross = 0.0f;
        net = 0.0f;
        vat = 0.0f;
        tot = 0.0f;

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

        cartDialog.dismiss();


    }

    @Override
    public void dismisDialog() {
        cartDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        SalesOrderItemListActivity.setupBadge();
        finish();
    }
}
