package com.van.sale.vansale.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
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
import com.van.sale.vansale.adapter.ItemListRecyclerViewAdapter;
import com.van.sale.vansale.adapter.SalesOrderListAdapter;
import com.van.sale.vansale.model.ItemClass;
import com.van.sale.vansale.model.ItemDetailClass;
import com.van.sale.vansale.model.SalesOrderItemClass;
import com.van.sale.vansale.model.SalesOrderList;
import com.van.sale.vansale.model.SelectedItemClass;

import java.util.ArrayList;
import java.util.List;

import static com.koushikdutta.async.AsyncServer.LOGTAG;

public class HomeItemListActivity extends AppCompatActivity {

    private static int mCartItemCount = 0;
    private static TextView textCartItemCount;

    private RecyclerView mRecyclerView, itemRecyclerView;
    private SalesOrderListAdapter orderListAdapter;
    private List<SalesOrderList> salesOrderLists;
    private LayoutInflater inflater;
    private LayoutInflater inflater1;
    private AlertDialog.Builder dialogBuilder;
    private View dialogView;
    private AlertDialog alertDialog;
    private TextView addtocart, closeid, product_item_code, product_discription, pricedisplay, discount_percentage, gross_value, net_value, vat_value, total_value;
    private DatabaseHandler db;
    private List<ItemClass> getItem;
    private List<ItemClass> getItembyBarcode;
    private MaterialSpinner spinner;
    private EditText rate, quantity;
    private List<SelectedItemClass> selectedItemClasses;
    private String spinner_value, warehouse, taxRate;

    private int salesOrderLastId = 0;

    private String VANSALE_cus, VANSALE_DOC_NO, discount_percentage_value = "0";
    private int UNSYNC_STATUS = 0, scanStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order_item_list);
        scanStatus = 0;
        db = new DatabaseHandler(this);

        getItem = db.getItem();
        warehouse = db.getWarehouse();
        taxRate = db.getTaxRate();
        salesOrderLastId = db.getSalesOrderHighestID();

        for (ItemClass v : getItem) {

            Log.i("BBB", "<==" + v.getItem_name());
            Log.i("BBB", "<==" + v.getBar_code());

        }

        VANSALE_cus = Utility.getPrefs("VANSALE_cus", getApplicationContext());
        VANSALE_DOC_NO = Utility.getPrefs("VANSALE_DOC_NO", getApplicationContext());

        salesOrderLists = new ArrayList<>();
        selectedItemClasses = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("ITEM LIST");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        //  mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

        displayList();


    }

    private void displayList() {

        scanStatus = 0;

        orderListAdapter = new SalesOrderListAdapter(getApplicationContext(), getItem, new SalesOrderListAdapter.CustomItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {

                inflater = HomeItemListActivity.this.getLayoutInflater();
                dialogBuilder = new AlertDialog.Builder(HomeItemListActivity.this);
                dialogView = inflater.inflate(R.layout.homeitemlistpopup, null);
                product_item_code = (TextView) dialogView.findViewById(R.id.product_item_code);
                product_discription = (TextView) dialogView.findViewById(R.id.product_discription);
                itemRecyclerView = (RecyclerView) dialogView.findViewById(R.id.uom_list);

                closeid = (TextView) dialogView.findViewById(R.id.addtocartid);
                dialogBuilder.setView(dialogView);
                alertDialog = dialogBuilder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();

                LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
                itemRecyclerView.setLayoutManager(mLayoutManager2);
                itemRecyclerView.setHasFixedSize(true);
                itemRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

                final List<ItemDetailClass> getItemDetail = db.getItemDetail(getItem.get(position).getItem_code());

                List<String> uom = new ArrayList<String>();

                for (ItemDetailClass it : getItemDetail) {

                    Log.i("UUU", "<==" + it.getUom());
                    uom.add(it.getUom());
                }


                ItemListRecyclerViewAdapter recyclerViewAdapter = new ItemListRecyclerViewAdapter(HomeItemListActivity.this, getItemDetail, getItem.get(position).getBar_code());
                itemRecyclerView.setAdapter(recyclerViewAdapter);


                product_item_code.setText(getItem.get(position).getItem_code());
                product_discription.setText(getItem.get(position).getDescription());


                closeid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();

                    }
                });


            }
        });
        mRecyclerView.setAdapter(orderListAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.home_item_list_activity_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_search);


        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setQueryHint("Search Here");
        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(HomeItemListActivity.this, R.drawable.ic_search_white));

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


                            inflater1 = HomeItemListActivity.this.getLayoutInflater();
                            dialogBuilder = new AlertDialog.Builder(HomeItemListActivity.this);
                            dialogView = inflater1.inflate(R.layout.homeitemlistpopup, null);
                            product_item_code = (TextView) dialogView.findViewById(R.id.product_item_code);
                            product_discription = (TextView) dialogView.findViewById(R.id.product_discription);
                            itemRecyclerView = (RecyclerView) dialogView.findViewById(R.id.uom_list);

                            closeid = (TextView) dialogView.findViewById(R.id.addtocartid);
                            dialogBuilder.setView(dialogView);
                            alertDialog = dialogBuilder.create();
                            alertDialog.setCancelable(false);
                            alertDialog.show();

                            LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
                            itemRecyclerView.setLayoutManager(mLayoutManager2);
                            itemRecyclerView.setHasFixedSize(true);
                            itemRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

                            final List<ItemDetailClass> getItemDetail = db.getItemDetail(tempArrayList.get(position).getItem_code());

                            List<String> uom = new ArrayList<String>();

                            for (ItemDetailClass it : getItemDetail) {

                                Log.i("UUU", "<==" + it.getUom());
                                uom.add(it.getUom());
                            }


                            ItemListRecyclerViewAdapter recyclerViewAdapter = new ItemListRecyclerViewAdapter(HomeItemListActivity.this, getItemDetail, tempArrayList.get(position).getBar_code());
                            itemRecyclerView.setAdapter(recyclerViewAdapter);


                            product_item_code.setText(tempArrayList.get(position).getItem_code());
                            product_discription.setText(tempArrayList.get(position).getDescription());


                            closeid.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();

                                }
                            });


                        }
                    });
                    mRecyclerView.setAdapter(orderListAdapter);

                } else {

                    orderListAdapter = new SalesOrderListAdapter(getApplicationContext(), getItem, new SalesOrderListAdapter.CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, final int position) {

                            inflater1 = HomeItemListActivity.this.getLayoutInflater();
                            dialogBuilder = new AlertDialog.Builder(HomeItemListActivity.this);
                            dialogView = inflater1.inflate(R.layout.homeitemlistpopup, null);
                            product_item_code = (TextView) dialogView.findViewById(R.id.product_item_code);
                            product_discription = (TextView) dialogView.findViewById(R.id.product_discription);
                            itemRecyclerView = (RecyclerView) dialogView.findViewById(R.id.uom_list);

                            closeid = (TextView) dialogView.findViewById(R.id.addtocartid);
                            dialogBuilder.setView(dialogView);
                            alertDialog = dialogBuilder.create();
                            alertDialog.setCancelable(false);
                            alertDialog.show();

                            LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
                            itemRecyclerView.setLayoutManager(mLayoutManager2);
                            itemRecyclerView.setHasFixedSize(true);
                            itemRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

                            final List<ItemDetailClass> getItemDetail = db.getItemDetail(getItem.get(position).getItem_code());

                            List<String> uom = new ArrayList<String>();

                            for (ItemDetailClass it : getItemDetail) {

                                Log.i("UUU", "<==" + it.getUom());
                                uom.add(it.getUom());
                            }


                            ItemListRecyclerViewAdapter recyclerViewAdapter = new ItemListRecyclerViewAdapter(HomeItemListActivity.this, getItemDetail, getItem.get(position).getBar_code());
                            itemRecyclerView.setAdapter(recyclerViewAdapter);


                            product_item_code.setText(getItem.get(position).getItem_code());
                            product_discription.setText(getItem.get(position).getDescription());


                            closeid.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();

                                }
                            });


                        }
                    });
                    mRecyclerView.setAdapter(orderListAdapter);

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
                    displayList();
                }
                return true;
            case R.id.menu_scan:
                IntentIntegrator integrator = new IntentIntegrator(HomeItemListActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan a barcode");
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
           /* android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(SalesOrderItemListActivity.this).create();
            alertDialog.setTitle("Scan result");
            alertDialog.setMessage(result);
            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {*/

            String sResult = db.getItemCodeByBarcode(result);

            getItembyBarcode = db.getItemByBarcode(sResult);

            //  getItembyBarcode = db.getItemByBarcode(result);

            if (!getItembyBarcode.isEmpty()) {

               /* new SalesOrderListAdapter.CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        inflater1 = HomeItemListActivity.this.getLayoutInflater();
                        dialogBuilder = new AlertDialog.Builder(HomeItemListActivity.this);
                        dialogView = inflater1.inflate(R.layout.homeitemlistpopup, null);
                        product_item_code = (TextView) dialogView.findViewById(R.id.product_item_code);
                        product_discription = (TextView) dialogView.findViewById(R.id.product_discription);
                        itemRecyclerView = (RecyclerView)dialogView.findViewById(R.id.uom_list);

                        closeid = (TextView) dialogView.findViewById(R.id.addtocartid);
                        dialogBuilder.setView(dialogView);
                        alertDialog = dialogBuilder.create();
                        alertDialog.setCancelable(false);
                        alertDialog.show();

                        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
                        itemRecyclerView.setLayoutManager(mLayoutManager2);
                        itemRecyclerView.setHasFixedSize(true);
                        itemRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

                        final List<ItemDetailClass> getItemDetail = db.getItemDetail(getItembyBarcode.get(0).getItem_code());

                        List<String> uom = new ArrayList<String>();

                        for (ItemDetailClass it : getItemDetail) {

                            Log.i("UUU", "<==" + it.getUom());
                            uom.add(it.getUom());
                        }


                        ItemListRecyclerViewAdapter recyclerViewAdapter = new ItemListRecyclerViewAdapter(HomeItemListActivity.this,getItemDetail,getItembyBarcode.get(0).getBar_code());
                        itemRecyclerView.setAdapter(recyclerViewAdapter);


                        product_item_code.setText(getItembyBarcode.get(0).getItem_code());
                        product_discription.setText(getItembyBarcode.get(0).getDescription());



                        closeid.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();

                            }
                        });



                    }
                };*/

                orderListAdapter = new SalesOrderListAdapter(getApplicationContext(), getItembyBarcode, new SalesOrderListAdapter.CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, final int position) {

                        inflater1 = HomeItemListActivity.this.getLayoutInflater();
                        dialogBuilder = new AlertDialog.Builder(HomeItemListActivity.this);
                        dialogView = inflater1.inflate(R.layout.homeitemlistpopup, null);
                        product_item_code = (TextView) dialogView.findViewById(R.id.product_item_code);
                        product_discription = (TextView) dialogView.findViewById(R.id.product_discription);
                        itemRecyclerView = (RecyclerView) dialogView.findViewById(R.id.uom_list);

                        closeid = (TextView) dialogView.findViewById(R.id.addtocartid);
                        dialogBuilder.setView(dialogView);
                        alertDialog = dialogBuilder.create();
                        alertDialog.setCancelable(false);
                        alertDialog.show();

                        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
                        itemRecyclerView.setLayoutManager(mLayoutManager2);
                        itemRecyclerView.setHasFixedSize(true);
                        itemRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

                        final List<ItemDetailClass> getItemDetail = db.getItemDetail(getItembyBarcode.get(position).getItem_code());

                        List<String> uom = new ArrayList<String>();

                        for (ItemDetailClass it : getItemDetail) {

                            Log.i("UUU", "<==" + it.getUom());
                            uom.add(it.getUom());
                        }


                        ItemListRecyclerViewAdapter recyclerViewAdapter = new ItemListRecyclerViewAdapter(HomeItemListActivity.this, getItemDetail, getItembyBarcode.get(position).getBar_code());
                        itemRecyclerView.setAdapter(recyclerViewAdapter);


                        product_item_code.setText(getItembyBarcode.get(position).getItem_code());
                        product_discription.setText(getItembyBarcode.get(position).getDescription());


                        closeid.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();

                            }
                        });


                    }
                });
                mRecyclerView.setAdapter(orderListAdapter);


            } else {
                Utility.showDialog(HomeItemListActivity.this, "ERROR !", "No Result Found...", R.color.dialog_error_background);
            }


                         /*   dialog.dismiss();
                        }
                    });
            alertDialog.show();*/
        }
    }


    public static void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }


   /* private void cartClick() {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("channel", (ArrayList<SelectedItemClass>) selectedItemClasses);
        Intent intent = new Intent(HomeItemListActivity.this, CartActivity.class);
        intent.putExtra("SALES_ORDER_LAST_ID",String.valueOf(salesOrderLastId));
        intent.putExtras(bundle);
        startActivity(intent);


    }*/

    public static int getBadgeCount() {
        return mCartItemCount;
    }

    public static void setBadgeCount(int count) {
        mCartItemCount = count;

    }

    @Override
    public void onBackPressed() {
        if (scanStatus == 0) {
            finish();
        }else if (scanStatus == 1){
            displayList();
        }


    }
}
