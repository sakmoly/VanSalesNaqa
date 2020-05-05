package com.van.sale.vansale.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
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
import com.van.sale.vansale.adapter.SalesInvoiceRecyclerViewAdapter;
import com.van.sale.vansale.model.SalesInvoiceClass;
import com.van.sale.vansale.model.SalesInvoiceItemClass;
import com.van.sale.vansale.model.SalesOrderClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.koushikdutta.async.AsyncServer.LOGTAG;

public class SalesOrderInvoiceActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private TextView empty_tv;
    private SalesInvoiceRecyclerViewAdapter viewAdapter;
    private List<SalesOrderClass> getSalesOrder;
    List<SalesInvoiceClass> getSalesInvoice;
    private DatabaseHandler db;
    private int scanStatus = 0;
    private List<SalesOrderClass> getSalesOrder_temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order_invoice);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("SALES INVOICE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        db = new DatabaseHandler(this);
        getSalesInvoice = db.getSalesInvoice();

       /* for (SalesInvoiceClass ccv : getSalesInvoice) {

            if (ccv.getITEM_COUNT()==0){

                db.deleteSalesInvoice(ccv.getKEY_ID());
                getSalesInvoice.remove(ccv);

            }

        }*/



       /* for (SalesInvoiceClass ic : getSalesInvoice){

            Log.i("INVOICE","<=="+ic.getSALES_INVOICE_CUSTOMER());
            Log.i("INVOICE","<=="+ic.getSALES_INVOICE_IS_RETURN());

        }*/

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        empty_tv = (TextView) findViewById(R.id.empty_tv);

        getSalesOrder = new ArrayList<>();
        getSalesOrder.add(new SalesOrderClass("Ali","2018-11-05"));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    mFloatingActionButton.hide();
                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                    mFloatingActionButton.show();
                }
            }
        });

        Collections.reverse(getSalesInvoice);

        displaySalesOrder();



        mFloatingActionButton.setOnClickListener(this);

    }



    private void displaySalesOrder() {
        scanStatus = 0;

        if (getSalesInvoice.isEmpty()) {
            empty_tv.setVisibility(View.VISIBLE);
        } else {
            empty_tv.setVisibility(View.GONE);
        }

        viewAdapter = new SalesInvoiceRecyclerViewAdapter(getApplicationContext(),getSalesInvoice);
        mRecyclerView.setAdapter(viewAdapter);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (scanStatus == 0) {
                    startActivity(new Intent(SalesOrderInvoiceActivity.this, HomeActivity.class));
                    //finish();
                }else if (scanStatus == 1){
                    displaySalesOrder();
                }

                return true;

            case R.id.menu_scan:
                IntentIntegrator integrator = new IntentIntegrator(SalesOrderInvoiceActivity.this);
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

            List<SalesInvoiceClass> getSalesInvoiceByCustomer = db.getSalesInvoiceByCustomer(cust_name);

            if (!getSalesInvoiceByCustomer.isEmpty()) {

                scanStatus = 1;

                if (getSalesInvoiceByCustomer.isEmpty()) {
                    empty_tv.setVisibility(View.VISIBLE);
                } else {
                    empty_tv.setVisibility(View.GONE);
                }

                viewAdapter = new SalesInvoiceRecyclerViewAdapter(getApplicationContext(), getSalesInvoiceByCustomer);
                mRecyclerView.setAdapter(viewAdapter);

            }/*else{

                Utility.showDialog(SalesOrderInvoiceActivity.this, "ERROR !", "No Result Found...", R.color.dialog_error_background);

            }*/


        }
    }



    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.floating_action_button:
                floatingButtonClick();
                break;
        }


    }

    private void floatingButtonClick() {

        startActivity(new Intent(SalesOrderInvoiceActivity.this, SalesInvoiceAddActivity.class));

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
        searchIcon.setImageDrawable(ContextCompat.getDrawable(SalesOrderInvoiceActivity.this, R.drawable.ic_search_white));


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
                    ArrayList<SalesInvoiceClass> tempArrayList = new ArrayList<SalesInvoiceClass>();
                    for (SalesInvoiceClass c : getSalesInvoice) {
                        if (textlength <= c.getSALES_INVOICE_CUSTOMER().length()) {
                            if (c.getSALES_INVOICE_CUSTOMER().toLowerCase().contains(s.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }


                    if (tempArrayList.isEmpty()) {
                        empty_tv.setVisibility(View.VISIBLE);
                    } else {
                        empty_tv.setVisibility(View.GONE);
                    }

                    viewAdapter = new SalesInvoiceRecyclerViewAdapter(getApplicationContext(),tempArrayList);
                    mRecyclerView.setAdapter(viewAdapter);


                }else{


                    displaySalesOrder();


                }


                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        if (scanStatus == 0) {
            startActivity(new Intent(SalesOrderInvoiceActivity.this, HomeActivity.class));
            //finish();
        }else if (scanStatus == 1){
            displaySalesOrder();
        }
    }
}
