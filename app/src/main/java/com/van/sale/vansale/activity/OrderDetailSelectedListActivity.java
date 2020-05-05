package com.van.sale.vansale.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.CartRecyclerViewAdapter;
import com.van.sale.vansale.adapter.SelectedOrderRecyclerViewAdapter;
import com.van.sale.vansale.model.Cart;
import com.van.sale.vansale.model.SalesOrderItemClass;
import com.van.sale.vansale.model.SelectedItemClass;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailSelectedListActivity extends AppCompatActivity implements View.OnClickListener, RecyclerTouchListener.RecyclerTouchListenerHelper, DetailDialog.DetailDialogListener {

    private RecyclerView mRecyclerView;
    private Button cancel_request, checkout_request;
    private List<Cart> carts;
    private SelectedOrderRecyclerViewAdapter adapter;
    private RecyclerTouchListener onTouchListener;
    private OnActivityTouchListener touchListener;
    private DatabaseHandler db;
    private String customer_name,customer_id;
    private List<SalesOrderItemClass> getSalesOrderItem;
    private TextView TV_gross, TV_net, TV_vat, TV_total;
    private Float gross = 0.0f, net = 0.0f, vat = 0.0f, tot = 0.0f;

    private static String selected_uom, selected_discount_percentage, selected_rate, selected_quantity, selected_price_display, selected_item_code, selected_item_id;
    private static int selected_position;
    private DetailDialog detailDialog;
    private int ORDER_SYNC_STATUS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_list);
        carts = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cancel_request = (Button) findViewById(R.id.cancel_request);
        checkout_request = (Button) findViewById(R.id.checkout_request);

        TV_gross = (TextView) findViewById(R.id.TV_gross);
        TV_net = (TextView) findViewById(R.id.TV_net);
        TV_vat = (TextView) findViewById(R.id.TV_vat);
        TV_total = (TextView) findViewById(R.id.TV_total);

        customer_name = getIntent().getStringExtra("ORDER_C_NAME");
        customer_id = getIntent().getStringExtra("ORDER_C_ID");

        // ORDER_SYNC_STATUS = Integer.parseInt(Utility.getPrefs("ORDER_SYNC_STATUS",OrderDetailSelectedListActivity.this));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("SELECTED LIST");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        db = new DatabaseHandler(this);

        getSalesOrderItem = db.getSalesOrderItem(customer_id);

        for (SalesOrderItemClass si : getSalesOrderItem) {

            Log.i("CART", "<==" + si.getSALES_ITEM_NAME());
            Log.i("CART", "<==" + si.getSALES_STOCK_UOM());

            gross = gross + si.getSALES_GROSS();
            net = net + si.getSALES_NET();
            vat = vat + si.getSALES_VAT();
            tot = tot + si.getSALES_TOTAL();

        }

        TV_gross.setText(String.valueOf(Utility.round(gross, 2)));
        TV_net.setText(String.valueOf(Utility.round(net, 2)));
        TV_vat.setText(String.valueOf(Utility.round(vat, 2)));
        TV_total.setText(String.valueOf(Utility.round(tot, 2)));


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        //  mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

        /*carts.add(new Cart("Item 1", "eer2323", "2", "1000"));
        carts.add(new Cart("Item 4", "ee3e323", "1", "900"));*/


        adapter = new SelectedOrderRecyclerViewAdapter(getApplicationContext(), getSalesOrderItem);
        mRecyclerView.setAdapter(adapter);
        onTouchListener = new RecyclerTouchListener(this, mRecyclerView);
        onTouchListener
                .setIndependentViews(R.id.total_amount)
                .setViewsToFade(R.id.total_amount)
                .setSwipeOptionViews(R.id.edit, R.id.delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, final int position) {

                        if (viewID == R.id.edit) {

                            //  Toast.makeText(OrderDetailSelectedListActivity.this, "Clicked", Toast.LENGTH_SHORT).show();

                            selected_item_id = String.valueOf(getSalesOrderItem.get(position).getKEY_ID());
                            selected_item_code = getSalesOrderItem.get(position).getSALES_ITEM_CODE();
                            selected_price_display = String.valueOf(getSalesOrderItem.get(position).getSALES_PRICE_LIST_RATE());
                            selected_quantity = String.valueOf((int) Math.round(getSalesOrderItem.get(position).getSALES_QTY()));
                            selected_discount_percentage = String.valueOf(getSalesOrderItem.get(position).getSALES_DISCOUNT_PERCENTAGE());
                            selected_rate = String.valueOf(getSalesOrderItem.get(position).getSALES_RATE());
                            selected_uom = String.valueOf(getSalesOrderItem.get(position).getSALES_STOCK_UOM());
                            selected_position = position;

                          //  Toast.makeText(OrderDetailSelectedListActivity.this, "" + getSalesOrderItem.get(position).getSALES_STOCK_UOM(), Toast.LENGTH_SHORT).show();

                            detailDialog = new DetailDialog();
                            detailDialog.show(getSupportFragmentManager(), "example dialog");


                        } else if (viewID == R.id.delete) {

                            new android.app.AlertDialog.Builder(OrderDetailSelectedListActivity.this)
                                    .setMessage("Are you sure you want to delete?")
                                    .setNegativeButton(android.R.string.no, null)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            db.deleteOrder(getSalesOrderItem.get(position).getKEY_ID());

                                            gross = gross - getSalesOrderItem.get(position).getSALES_GROSS();
                                            net = net - getSalesOrderItem.get(position).getSALES_NET();
                                            vat = vat - getSalesOrderItem.get(position).getSALES_VAT();
                                            tot = tot - getSalesOrderItem.get(position).getSALES_TOTAL();

                                            TV_gross.setText(String.valueOf(Utility.round(gross, 2)));
                                            TV_net.setText(String.valueOf(Utility.round(net, 2)));
                                            TV_vat.setText(String.valueOf(Utility.round(vat, 2)));
                                            TV_total.setText(String.valueOf(Utility.round(tot, 2)));

                                            getSalesOrderItem.remove(position);
                                            adapter.notifyDataSetChanged();

                                            Toast t = Utility.setToast(OrderDetailSelectedListActivity.this, "Deleted");
                                            t.show();


                                        }
                                    }).create().show();

                        }


                    }
                });


        cancel_request.setOnClickListener(this);
        checkout_request.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.addOnItemTouchListener(onTouchListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRecyclerView.removeOnItemTouchListener(onTouchListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (touchListener != null) touchListener.getTouchCoordinates(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {
        this.touchListener = listener;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.cancel_request:
                finish();
                break;
            case R.id.checkout_request:
                checkoutClick();
                break;

        }

    }

    private void checkoutClick() {

        Toast t = Utility.setToast(OrderDetailSelectedListActivity.this, "Payment Gatewway Not Integrated!");
        t.show();
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


        TV_gross.setText(String.valueOf(Utility.round(gross, 2)));
        TV_net.setText(String.valueOf(Utility.round(net, 2)));
        TV_vat.setText(String.valueOf(Utility.round(vat, 2)));
        TV_total.setText(String.valueOf(Utility.round(tot, 2)));

        detailDialog.dismiss();


    }

    @Override
    public void dismisDialog() {
        detailDialog.dismiss();
    }
}
