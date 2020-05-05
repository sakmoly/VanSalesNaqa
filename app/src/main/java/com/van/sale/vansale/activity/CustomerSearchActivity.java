package com.van.sale.vansale.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.CustomerRecyclerViewAdapter;
import com.van.sale.vansale.adapter.CustomerSearchRecyclerViewAdapter;
import com.van.sale.vansale.model.CustomerClass;

import java.util.ArrayList;
import java.util.List;

import static com.koushikdutta.async.AsyncServer.LOGTAG;

public class CustomerSearchActivity extends AppCompatActivity {

    private EditText item_searchid;
    private RecyclerView recyclerviewid;
    private DatabaseHandler db;
    private List<CustomerClass> getCustomer;
    private List<CustomerClass> getCustomerDetail;
    private CustomerSearchRecyclerViewAdapter viewAdapter;
    private CustomerSearchRecyclerViewAdapter viewAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search);

        item_searchid = (EditText) findViewById(R.id.item_searchid);
        recyclerviewid = (RecyclerView) findViewById(R.id.recyclerviewid);

        db = new DatabaseHandler(this);
        getCustomer = db.getCustomer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("CUSTOMER SEARCH");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerviewid.setLayoutManager(mLayoutManager);
        recyclerviewid.setHasFixedSize(true);
        recyclerviewid.setItemAnimator(new DefaultItemAnimator());

        viewAdapter = new CustomerSearchRecyclerViewAdapter(CustomerSearchActivity.this, getCustomer, new CustomerSearchRecyclerViewAdapter.CustomerClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent i = new Intent();
                i.putExtra("cstmr_name", getCustomer.get(position).getCustomer_name());
                i.putExtra("cstmr_id", getCustomer.get(position).getId());
                setResult(SalesOrderAddActivity.CUSTOMER_SELECTION_REQUEST_CODE, i);
                finish();

            }
        });

        recyclerviewid.setAdapter(viewAdapter);


        item_searchid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                try {

                    if (s != null) {

                        int textlength = s.length();

                        if (s.toString().contains("\n")) {

                            try{
                                String cust_name = db.getCustomerNameByBarcode(s.toString().trim());
                                if (!cust_name.equals("")) {

                                    Intent ic = new Intent();
                                    ic.putExtra("cstmr_name", cust_name);
                                    setResult(SalesOrderAddActivity.CUSTOMER_SELECTION_REQUEST_CODE, ic);
                                    finish();

                                } else {
                                    item_searchid.setText(s.toString().trim());
                                    Toast toast = Toast.makeText(CustomerSearchActivity.this, "Not found", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    // playSound(R.raw.sound);
                                }
                            }catch (Exception e){
                                Utility.showDialog(CustomerSearchActivity.this, "Error", e.getMessage(), R.color.dialog_error_background);
                            }


                        } else {
                            final ArrayList<CustomerClass> tempArrayList = new ArrayList<CustomerClass>();

                            try
                            {
                            for (CustomerClass c : getCustomer) {

                                if (textlength <= c.getName().length()) {
                                    if (c.getName().toLowerCase().contains(s.toString().toLowerCase()) || c.getServer_customer_id().contains(s.toString().trim())) {
                                        tempArrayList.add(c);
                                    }
                                }
                            }


                            viewAdapter = new CustomerSearchRecyclerViewAdapter(getApplicationContext(), tempArrayList, new CustomerSearchRecyclerViewAdapter.CustomerClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    Intent i = new Intent();
                                    i.putExtra("cstmr_name", tempArrayList.get(position).getCustomer_name());
                                    setResult(SalesOrderAddActivity.CUSTOMER_SELECTION_REQUEST_CODE, i);
                                    finish();
                                }
                            });
                            recyclerviewid.setAdapter(viewAdapter);
                            }catch (Exception e){
                                Utility.showDialog(CustomerSearchActivity.this, "Error", e.getMessage(), R.color.dialog_error_background);
                            }
                        }


                    } else {

                        viewAdapter = new CustomerSearchRecyclerViewAdapter(getApplicationContext(), getCustomer, new CustomerSearchRecyclerViewAdapter.CustomerClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Intent i = new Intent();
                                i.putExtra("cstmr_name", getCustomer.get(position).getCustomer_name());
                                setResult(SalesOrderAddActivity.CUSTOMER_SELECTION_REQUEST_CODE, i);
                                finish();
                            }
                        });
                        recyclerviewid.setAdapter(viewAdapter);
                    }

                }catch(Exception exception){

                    Utility.showDialog(CustomerSearchActivity.this, "Error", exception.getMessage(), R.color.dialog_error_background);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_search_activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                return true;

            case R.id.menu_scan:
                IntentIntegrator integrator = new IntentIntegrator(CustomerSearchActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setOrientationLocked(false);

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

            String result = scanResult.getContents();
            Log.d(LOGTAG, "Have scan result in your app activity :" + result);

            Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();

             getCustomerDetail = db.getCustomerDetailScan(result);

            if (!getCustomerDetail.isEmpty()) {

                for (CustomerClass ccc : getCustomerDetail){
                    Log.i("SEARCH","<=="+ccc.getCustomer_name());
                    Log.i("SEARCH","<=="+ccc.getCustomer_primary_contact());
                    Log.i("SEARCH","<=="+ccc.getServer_customer_id());
                }


                viewAdapter = new CustomerSearchRecyclerViewAdapter(CustomerSearchActivity.this, getCustomerDetail, new CustomerSearchRecyclerViewAdapter.CustomerClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        Intent i = new Intent();
                        i.putExtra("cstmr_name", getCustomerDetail.get(position).getCustomer_name());
                        i.putExtra("cstmr_id", getCustomerDetail.get(position).getId());
                        setResult(SalesOrderAddActivity.CUSTOMER_SELECTION_REQUEST_CODE, i);
                        finish();

                    }
                });
                recyclerviewid.setAdapter(viewAdapter);

            }/* else {
                Utility.showDialog(CustomerSearchActivity.this, "ERROR !", "No Result Found...", R.color.dialog_error_background);
            }*/

        }
    }


    private void playSound(int resId){
        MediaPlayer mp = MediaPlayer.create(CustomerSearchActivity.this, resId);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        });
        mp.start();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
