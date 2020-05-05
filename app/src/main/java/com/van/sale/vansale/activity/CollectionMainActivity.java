package com.van.sale.vansale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.CollectionListAdapter;
import com.van.sale.vansale.model.Payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionMainActivity extends AppCompatActivity {

    private List<Payment> getPaymentList;
    private DatabaseHandler db;
    private CollectionListAdapter listAdapter;
    private RecyclerView recycler_view;
    private TextView empty_tv;
    private FloatingActionButton fab;
    private int scanStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        empty_tv = (TextView) findViewById(R.id.empty_tv);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("COLLECTION");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setHasFixedSize(true);
        recycler_view.setItemAnimator(new DefaultItemAnimator());


        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.floating_action_button);

        db = new DatabaseHandler(this);
        getPaymentList = db.getPaymentMainList();

       // Collections.reverse(getPaymentList);

        displayCollectionList();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), CollectionActivity.class));

            }
        });


    }


    private void displayCollectionList() {
        scanStatus = 0;

        if (getPaymentList.isEmpty()) {
            empty_tv.setVisibility(View.VISIBLE);
        } else {
            Collections.reverse(getPaymentList);
            empty_tv.setVisibility(View.GONE);
        }

        listAdapter = new CollectionListAdapter(getApplicationContext(), getPaymentList, new CollectionListAdapter.CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent collection_intent = new Intent(getApplicationContext(), CollectionDetailActivity.class);

                collection_intent.putExtra("Collection_creation", getPaymentList.get(position).getPAYMENT_CREATION());
                collection_intent.putExtra("Collection_doc_no", getPaymentList.get(position).getPAYMENT_DOC_NO());
                collection_intent.putExtra("Collection_mode_pay", getPaymentList.get(position).getMODE_OF_PAYMENT());
                collection_intent.putExtra("Collection_receive_amt", getPaymentList.get(position).getRECEIVED_AMOUNT());
                collection_intent.putExtra("Collection_customer", getPaymentList.get(position).getPAYMENT_CUSTOMER());
                collection_intent.putExtra("Collection_reference_no", getPaymentList.get(position).getPAYMENT_REFERENCE_NO());
                collection_intent.putExtra("Collection_refer_date", getPaymentList.get(position).getPAYMENT_REFERENCE_DATE());
                collection_intent.putExtra("Collection_paid_to", getPaymentList.get(position).getPAYMENT_PAID_TO());
                startActivity(collection_intent);

            }
        });
        recycler_view.setAdapter(listAdapter);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                if (scanStatus == 0) {
                    startActivity(new Intent(CollectionMainActivity.this, HomeActivity.class));
                    finish();
                }else if (scanStatus == 1){
                    displayCollectionList();
                }

                return true;

            case R.id.menu_scan:
                IntentIntegrator integrator = new IntentIntegrator(CollectionMainActivity.this);
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

            final String result = scanResult.getContents();
            String cust_name = db.getCustomerNameByBarcode(result);

            final List<Payment> getPaymentMainListByCustomer = db.getPaymentMainListByCustomer(cust_name);

            if (!getPaymentMainListByCustomer.isEmpty()) {
                scanStatus = 1;

                if (getPaymentMainListByCustomer.isEmpty()) {
                    empty_tv.setVisibility(View.VISIBLE);
                } else {
                    empty_tv.setVisibility(View.GONE);
                }

                listAdapter = new CollectionListAdapter(getApplicationContext(), getPaymentMainListByCustomer, new CollectionListAdapter.CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        Intent collection_intent = new Intent(getApplicationContext(), CollectionDetailActivity.class);

                        collection_intent.putExtra("Collection_creation", getPaymentMainListByCustomer.get(position).getPAYMENT_CREATION());
                        collection_intent.putExtra("Collection_doc_no", getPaymentMainListByCustomer.get(position).getPAYMENT_DOC_NO());
                        collection_intent.putExtra("Collection_mode_pay", getPaymentMainListByCustomer.get(position).getMODE_OF_PAYMENT());
                        collection_intent.putExtra("Collection_receive_amt", getPaymentMainListByCustomer.get(position).getRECEIVED_AMOUNT());
                        collection_intent.putExtra("Collection_customer", getPaymentMainListByCustomer.get(position).getPAYMENT_CUSTOMER());
                        collection_intent.putExtra("Collection_reference_no", getPaymentMainListByCustomer.get(position).getPAYMENT_REFERENCE_NO());
                        collection_intent.putExtra("Collection_refer_date", getPaymentMainListByCustomer.get(position).getPAYMENT_REFERENCE_DATE());
                        collection_intent.putExtra("Collection_paid_to", getPaymentMainListByCustomer.get(position).getPAYMENT_PAID_TO());
                        startActivity(collection_intent);

                    }
                });
                recycler_view.setAdapter(listAdapter);

            }/*else{

                    Utility.showDialog(CollectionMainActivity.this, "ERROR !", "No Result Found...", R.color.dialog_error_background);

            }*/


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.collection_activity_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setQueryHint("Search Here");
        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(CollectionMainActivity.this, R.drawable.ic_search_white));


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
                    final ArrayList<Payment> tempArrayList = new ArrayList<Payment>();
                    for (Payment c : getPaymentList) {
                        if (textlength <= c.getPAYMENT_CUSTOMER().length()) {
                            if (c.getPAYMENT_CUSTOMER().toLowerCase().contains(s.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }
                    if (getPaymentList.isEmpty()) {
                        empty_tv.setVisibility(View.VISIBLE);
                    } else {
                        empty_tv.setVisibility(View.GONE);
                    }

                    listAdapter = new CollectionListAdapter(getApplicationContext(), tempArrayList, new CollectionListAdapter.CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {

                            Intent collection_intent = new Intent(getApplicationContext(), CollectionDetailActivity.class);
                            collection_intent.putExtra("Collection_creation", tempArrayList.get(position).getPAYMENT_CREATION());
                            collection_intent.putExtra("Collection_doc_no", tempArrayList.get(position).getPAYMENT_DOC_NO());
                            collection_intent.putExtra("Collection_mode_pay", tempArrayList.get(position).getMODE_OF_PAYMENT());
                            collection_intent.putExtra("Collection_receive_amt", tempArrayList.get(position).getRECEIVED_AMOUNT());
                            collection_intent.putExtra("Collection_customer", tempArrayList.get(position).getPAYMENT_CUSTOMER());
                            collection_intent.putExtra("Collection_reference_no", tempArrayList.get(position).getPAYMENT_REFERENCE_NO());
                            collection_intent.putExtra("Collection_refer_date", tempArrayList.get(position).getPAYMENT_REFERENCE_DATE());
                            collection_intent.putExtra("Collection_paid_to", tempArrayList.get(position).getPAYMENT_PAID_TO());
                            startActivity(collection_intent);


                        }
                    });
                    recycler_view.setAdapter(listAdapter);

                   /* getSalesOrder.clear();
                    getSalesOrder.addAll(tempArrayList);
                    orderRecyclerViewAdapter = new SalesOrderRecyclerViewAdapter(getApplicationContext(), getSalesOrder);
                    mRecyclerView.setAdapter(orderRecyclerViewAdapter);*/

                } else {

                    if (getPaymentList.isEmpty()) {
                        empty_tv.setVisibility(View.VISIBLE);
                    } else {
                        empty_tv.setVisibility(View.GONE);
                    }

                    listAdapter = new CollectionListAdapter(getApplicationContext(), getPaymentList, new CollectionListAdapter.CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {

                            Intent collection_intent = new Intent(getApplicationContext(), CollectionDetailActivity.class);
                            collection_intent.putExtra("Collection_creation", getPaymentList.get(position).getPAYMENT_CREATION());
                            collection_intent.putExtra("Collection_doc_no", getPaymentList.get(position).getPAYMENT_DOC_NO());
                            collection_intent.putExtra("Collection_mode_pay", getPaymentList.get(position).getMODE_OF_PAYMENT());
                            collection_intent.putExtra("Collection_receive_amt", getPaymentList.get(position).getRECEIVED_AMOUNT());
                            collection_intent.putExtra("Collection_customer", getPaymentList.get(position).getPAYMENT_CUSTOMER());
                            collection_intent.putExtra("Collection_reference_no", getPaymentList.get(position).getPAYMENT_REFERENCE_NO());
                            collection_intent.putExtra("Collection_refer_date", getPaymentList.get(position).getPAYMENT_REFERENCE_DATE());
                            collection_intent.putExtra("Collection_paid_to", getPaymentList.get(position).getPAYMENT_PAID_TO());
                            startActivity(collection_intent);


                        }
                    });
                    recycler_view.setAdapter(listAdapter);


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
    public void onBackPressed() {
        if (scanStatus == 0) {
            startActivity(new Intent(CollectionMainActivity.this, HomeActivity.class));
            finish();
        }else if (scanStatus == 1){
            displayCollectionList();
        }
    }
}
