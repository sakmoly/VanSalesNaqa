package com.van.sale.vansale.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.CustomerRecyclerViewAdapter;
import com.van.sale.vansale.model.CustomerClass;
import com.van.sale.vansale.model.CustomerList;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

import static android.widget.LinearLayout.VERTICAL;
import static com.koushikdutta.async.AsyncServer.LOGTAG;

public class CustomerActivity extends AppCompatActivity implements RecyclerTouchListener.RecyclerTouchListenerHelper, View.OnClickListener {

    private static final String TAG = "SCANNER";
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private CustomerRecyclerViewAdapter adapter;
    private List<CustomerList> customerListsl;
    private RecyclerTouchListener onTouchListener;
    private OnActivityTouchListener touchListener;
    private List<CustomerClass> getCustomer;
    private List<CustomerClass> getCustomerTemp;
    private DatabaseHandler db;
    private int CUSTOMER_DETAIL_REQUEST_CODE = 3,scanStatus = 0;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private ZBarScannerView mScannerView;
    private TextView empty_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        empty_tv = (TextView) findViewById(R.id.empty_tv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("CUSTOMER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        db = new DatabaseHandler(this);

        mScannerView = new ZBarScannerView(this);

        getCustomer = db.getCustomer();
        //Collections.reverse(getCustomer);
        getCustomerTemp = new ArrayList<>();
        getCustomerTemp.addAll(getCustomer);

        for (CustomerClass cc : getCustomerTemp) {

            Log.i("CCCC", "<==" + cc.getName());
            Log.i("CCCC", "<==" + cc.getCustomer_name());
            Log.i("CCCC", "<==" + cc.getId());
            Log.i("CCCC", "<==" + cc.getEmail_id());
            Log.i("CCCC", "<==" + cc.getCredit_limit());
            Log.i("CCCC", "<==" + cc.getServer_customer_id());

        }


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

        displayCust();

        mFloatingActionButton.setOnClickListener(this);

    }

    private void displayCust() {

        scanStatus = 0;

        if (getCustomer.isEmpty()) {
            empty_tv.setVisibility(View.VISIBLE);
        } else {
            empty_tv.setVisibility(View.GONE);
        }

        adapter = new CustomerRecyclerViewAdapter(getApplicationContext(), getCustomer);
        mRecyclerView.setAdapter(adapter);

        onTouchListener = new RecyclerTouchListener(this, mRecyclerView);
        onTouchListener
                .setIndependentViews(R.id.customer_phone)
                .setViewsToFade(R.id.customer_phone)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {

                        Intent intent = new Intent(CustomerActivity.this, CustomerDetailActivity.class);
                        intent.putExtra("cu_id", getCustomer.get(position).getId());
                        intent.putExtra("cu_server_id", getCustomer.get(position).getServer_customer_id());
                        intent.putExtra("cu_name", getCustomer.get(position).getName());
                        intent.putExtra("cu_email", getCustomer.get(position).getEmail_id());
                        intent.putExtra("cu_mob", getCustomer.get(position).getMobile_no());
                        intent.putExtra("cu_tax_id", getCustomer.get(position).getTax_id());
                        intent.putExtra("cu_primary_contact", getCustomer.get(position).getCustomer_primary_contact());
                        intent.putExtra("cu_credit_limit", getCustomer.get(position).getCredit_limit());
                        startActivity(intent);

                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                        Intent intent = new Intent(CustomerActivity.this, CustomerDetailActivity.class);
                        intent.putExtra("cu_id", getCustomer.get(position).getId());
                        intent.putExtra("cu_server_id", getCustomer.get(position).getServer_customer_id());
                        intent.putExtra("cu_name", getCustomer.get(position).getName());
                        intent.putExtra("cu_email", getCustomer.get(position).getEmail_id());
                        intent.putExtra("cu_mob", getCustomer.get(position).getMobile_no());
                        intent.putExtra("cu_tax_id", getCustomer.get(position).getTax_id());
                        intent.putExtra("cu_primary_contact", getCustomer.get(position).getCustomer_primary_contact());
                        intent.putExtra("cu_credit_limit", getCustomer.get(position).getCredit_limit());
                        startActivity(intent);

                    }
                })

                .setSwipeOptionViews(R.id.edit, R.id.delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, final int position) {

                        if (viewID == R.id.edit) {

                            if (getCustomer.get(position).getSync_status().equals("0")) {

                                Intent intent = new Intent(CustomerActivity.this, CustomerEditActivity.class);
                                intent.putExtra("cu_name", getCustomer.get(position).getName());

                                startActivityForResult(intent, CUSTOMER_DETAIL_REQUEST_CODE);
                            } else {

                                Utility.showDialog(CustomerActivity.this, "WARNING !", "Server Data!\nUnable to edit...", R.color.dialog_network_error_background);
                            }

                        } else if (viewID == R.id.delete) {

                            if (getCustomer.get(position).getSync_status().equals("0")) {
                                new android.app.AlertDialog.Builder(CustomerActivity.this)
                                        .setMessage("Are you sure you want to delete?")
                                        .setNegativeButton(android.R.string.no, null)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface arg0, int arg1) {

                                                db.deleteCustomer(getCustomer.get(position).getName());
                                                db.deleteAddress(getCustomer.get(position).getName());

                                                Toast t = Utility.setToast(CustomerActivity.this, "Delete Successful");
                                                t.show();
                                                startActivity(new Intent(CustomerActivity.this, CustomerActivity.class));

                                            }
                                        }).create().show();

                            } else {

                                Utility.showDialog(CustomerActivity.this, "WARNING !", "Server Data!\nUnable to delete...", R.color.dialog_network_error_background);

                            }

                        }
                    }
                });





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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CUSTOMER_DETAIL_REQUEST_CODE) {

            adapter.notifyDataSetChanged();

        }

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {

            final String result = scanResult.getContents();
            Log.d(LOGTAG, "Have scan result in your app activity :" + result);
           /* AlertDialog alertDialog = new AlertDialog.Builder(CustomerActivity.this).create();
            alertDialog.setTitle("Scan result");
            alertDialog.setMessage(result);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {*/

                            final List<CustomerClass> getCustomerDetail = db.getCustomerDetailScan(result);

                            if (!getCustomerDetail.isEmpty()) {
                                scanStatus = 1;
                                adapter = new CustomerRecyclerViewAdapter(getApplicationContext(), getCustomerDetail);
                                mRecyclerView.setAdapter(adapter);
                                onTouchListener = new RecyclerTouchListener(this, mRecyclerView);
                                onTouchListener
                                        .setIndependentViews(R.id.customer_phone)
                                        .setViewsToFade(R.id.customer_phone)
                                        .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                                            @Override
                                            public void onRowClicked(int position) {

                                                Intent intent = new Intent(CustomerActivity.this, CustomerDetailActivity.class);
                                                intent.putExtra("cu_id", getCustomerDetail.get(position).getId());
                                                intent.putExtra("cu_server_id", getCustomerDetail.get(position).getServer_customer_id());
                                                intent.putExtra("cu_name", getCustomerDetail.get(position).getName());
                                                intent.putExtra("cu_email", getCustomerDetail.get(position).getEmail_id());
                                                intent.putExtra("cu_mob", getCustomerDetail.get(position).getMobile_no());
                                                intent.putExtra("cu_tax_id", getCustomerDetail.get(position).getTax_id());
                                                intent.putExtra("cu_primary_contact", getCustomerDetail.get(position).getCustomer_primary_contact());
                                                intent.putExtra("cu_credit_limit", getCustomerDetail.get(position).getCredit_limit());
                                                startActivity(intent);

                                            }

                                            @Override
                                            public void onIndependentViewClicked(int independentViewID, int position) {

                                                Intent intent = new Intent(CustomerActivity.this, CustomerDetailActivity.class);
                                                intent.putExtra("cu_id", getCustomerDetail.get(position).getId());
                                                intent.putExtra("cu_server_id", getCustomerDetail.get(position).getServer_customer_id());
                                                intent.putExtra("cu_name", getCustomerDetail.get(position).getName());
                                                intent.putExtra("cu_email", getCustomerDetail.get(position).getEmail_id());
                                                intent.putExtra("cu_mob", getCustomerDetail.get(position).getMobile_no());
                                                intent.putExtra("cu_tax_id", getCustomerDetail.get(position).getTax_id());
                                                intent.putExtra("cu_primary_contact", getCustomerDetail.get(position).getCustomer_primary_contact());
                                                intent.putExtra("cu_credit_limit", getCustomerDetail.get(position).getCredit_limit());
                                                startActivity(intent);

                                            }
                                        })

                                        .setSwipeOptionViews(R.id.edit, R.id.delete)
                                        .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                                            @Override
                                            public void onSwipeOptionClicked(int viewID, final int position) {

                                                if (viewID == R.id.edit) {

                                                    if (getCustomerDetail.get(position).getSync_status().equals("0")) {

                                                        Intent intent = new Intent(CustomerActivity.this, CustomerEditActivity.class);
                                                        intent.putExtra("cu_name", getCustomerDetail.get(position).getName());

                                                        startActivityForResult(intent, CUSTOMER_DETAIL_REQUEST_CODE);
                                                    } else {

                                                        Utility.showDialog(CustomerActivity.this, "WARNING !", "Server Data!\nUnable to edit...", R.color.dialog_network_error_background);
                                                    }

                                                } else if (viewID == R.id.delete) {

                                                    if (getCustomerDetail.get(position).getSync_status().equals("0")) {
                                                        new android.app.AlertDialog.Builder(CustomerActivity.this)
                                                                .setMessage("Are you sure you want to delete?")
                                                                .setNegativeButton(android.R.string.no, null)
                                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface arg0, int arg1) {

                                                                        db.deleteCustomer(getCustomerDetail.get(position).getName());
                                                                        db.deleteAddress(getCustomerDetail.get(position).getName());

                                                                        Toast t = Utility.setToast(CustomerActivity.this, "Delete Successful");
                                                                        t.show();
                                                                        startActivity(new Intent(CustomerActivity.this, CustomerActivity.class));

                                                                    }
                                                                }).create().show();

                                                    } else {

                                                        Utility.showDialog(CustomerActivity.this, "WARNING !", "Server Data!\nUnable to delete...", R.color.dialog_network_error_background);

                                                    }

                                                }
                                            }
                                        });


                            } /*else {
                                Utility.showDialog(CustomerActivity.this, "ERROR !", "No Result Found...", R.color.dialog_error_background);
                            }*/


                          //  dialog.dismiss();
                      /*  }
                    });
            alertDialog.show();*/
        }

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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                if (scanStatus == 0) {
                    finish();
                }else if (scanStatus == 1){
                    displayCust();
                }
               // finish();
                return true;

            case R.id.menu_scan:
                IntentIntegrator integrator = new IntentIntegrator(CustomerActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan a barcode");
                integrator.initiateScan();
                return true;

            default:
                return super.onOptionsItemSelected(item);
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

       // startActivity(new Intent(CustomerActivity.this, NewCustomerActivity.class));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.customer_activity_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setQueryHint("Search Here");
        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(CustomerActivity.this, R.drawable.ic_search_white));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

               // if (!getCustomer.isEmpty()) {

                if (s != null && !s.isEmpty()) {

                    int textlength = s.length();
                    ArrayList<CustomerClass> tempArrayList = new ArrayList<CustomerClass>();
                    for (CustomerClass c : getCustomer) {
                        if (textlength <= c.getName().length()) {
                            if (c.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }/*else if (c.getMobile_no().contains(s.toString())){
                                    tempArrayList.add(c);
                                }*/
                        }
                    }

                    getCustomer.clear();
                    getCustomer.addAll(tempArrayList);

                    adapter = new CustomerRecyclerViewAdapter(getApplicationContext(), getCustomer);
                    mRecyclerView.setAdapter(adapter);
                    onTouchListener = new RecyclerTouchListener(CustomerActivity.this, mRecyclerView);
                    onTouchListener
                            .setIndependentViews(R.id.customer_phone)
                            .setViewsToFade(R.id.customer_phone)
                            .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                                @Override
                                public void onRowClicked(int position) {

                                    Intent intent = new Intent(CustomerActivity.this, CustomerDetailActivity.class);
                                    intent.putExtra("cu_id", getCustomer.get(position).getId());
                                    intent.putExtra("cu_server_id", getCustomer.get(position).getServer_customer_id());
                                    intent.putExtra("cu_name", getCustomer.get(position).getName());
                                    intent.putExtra("cu_email", getCustomer.get(position).getEmail_id());
                                    intent.putExtra("cu_mob", getCustomer.get(position).getMobile_no());
                                    intent.putExtra("cu_tax_id", getCustomer.get(position).getTax_id());
                                    intent.putExtra("cu_primary_contact", getCustomer.get(position).getCustomer_primary_contact());
                                    intent.putExtra("cu_credit_limit", getCustomer.get(position).getCredit_limit());
                                    startActivity(intent);

                                }

                                @Override
                                public void onIndependentViewClicked(int independentViewID, int position) {

                                    Intent intent = new Intent(CustomerActivity.this, CustomerDetailActivity.class);
                                    intent.putExtra("cu_id", getCustomer.get(position).getId());
                                    intent.putExtra("cu_server_id", getCustomer.get(position).getServer_customer_id());
                                    intent.putExtra("cu_name", getCustomer.get(position).getName());
                                    intent.putExtra("cu_email", getCustomer.get(position).getEmail_id());
                                    intent.putExtra("cu_mob", getCustomer.get(position).getMobile_no());
                                    intent.putExtra("cu_tax_id", getCustomer.get(position).getTax_id());
                                    intent.putExtra("cu_primary_contact", getCustomer.get(position).getCustomer_primary_contact());
                                    intent.putExtra("cu_credit_limit", getCustomer.get(position).getCredit_limit());
                                    startActivity(intent);

                                }
                            })

                            .setSwipeOptionViews(R.id.edit, R.id.delete)
                            .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                                @Override
                                public void onSwipeOptionClicked(int viewID, final int position) {

                                    if (viewID == R.id.edit) {

                                        if (getCustomer.get(position).getSync_status().equals("0")) {

                                            Intent intent = new Intent(CustomerActivity.this, CustomerEditActivity.class);
                                            intent.putExtra("cu_name", getCustomer.get(position).getName());

                                            startActivityForResult(intent, CUSTOMER_DETAIL_REQUEST_CODE);
                                        } else {

                                            Utility.showDialog(CustomerActivity.this, "WARNING !", "Server Data!\nUnable to edit...", R.color.dialog_network_error_background);
                                        }

                                    } else if (viewID == R.id.delete) {

                                        if (getCustomer.get(position).getSync_status().equals("0")) {
                                            new android.app.AlertDialog.Builder(CustomerActivity.this)
                                                    .setMessage("Are you sure you want to delete?")
                                                    .setNegativeButton(android.R.string.no, null)
                                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface arg0, int arg1) {

                                                            db.deleteCustomer(getCustomer.get(position).getName());
                                                            db.deleteAddress(getCustomer.get(position).getName());

                                                            Toast t = Utility.setToast(CustomerActivity.this, "Delete Successful");
                                                            t.show();
                                                            startActivity(new Intent(CustomerActivity.this, CustomerActivity.class));

                                                        }
                                                    }).create().show();

                                        } else {

                                            Utility.showDialog(CustomerActivity.this, "WARNING !", "Server Data!\nUnable to delete...", R.color.dialog_network_error_background);

                                        }

                                    }
                                }
                            });



                } else {
                    getCustomer.clear();
                    getCustomer.addAll(getCustomerTemp);
                    adapter = new CustomerRecyclerViewAdapter(getApplicationContext(), getCustomer);
                    mRecyclerView.setAdapter(adapter);
                    onTouchListener = new RecyclerTouchListener(CustomerActivity.this, mRecyclerView);
                    onTouchListener
                            .setIndependentViews(R.id.customer_phone)
                            .setViewsToFade(R.id.customer_phone)
                            .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                                @Override
                                public void onRowClicked(int position) {

                                    Intent intent = new Intent(CustomerActivity.this, CustomerDetailActivity.class);
                                    intent.putExtra("cu_id", getCustomer.get(position).getId());
                                    intent.putExtra("cu_server_id", getCustomer.get(position).getServer_customer_id());
                                    intent.putExtra("cu_name", getCustomer.get(position).getName());
                                    intent.putExtra("cu_email", getCustomer.get(position).getEmail_id());
                                    intent.putExtra("cu_mob", getCustomer.get(position).getMobile_no());
                                    intent.putExtra("cu_tax_id", getCustomer.get(position).getTax_id());
                                    intent.putExtra("cu_primary_contact", getCustomer.get(position).getCustomer_primary_contact());
                                    intent.putExtra("cu_credit_limit", getCustomer.get(position).getCredit_limit());
                                    startActivity(intent);

                                }

                                @Override
                                public void onIndependentViewClicked(int independentViewID, int position) {

                                    Intent intent = new Intent(CustomerActivity.this, CustomerDetailActivity.class);
                                    intent.putExtra("cu_id", getCustomer.get(position).getId());
                                    intent.putExtra("cu_server_id", getCustomer.get(position).getServer_customer_id());
                                    intent.putExtra("cu_name", getCustomer.get(position).getName());
                                    intent.putExtra("cu_email", getCustomer.get(position).getEmail_id());
                                    intent.putExtra("cu_mob", getCustomer.get(position).getMobile_no());
                                    intent.putExtra("cu_tax_id", getCustomer.get(position).getTax_id());
                                    intent.putExtra("cu_primary_contact", getCustomer.get(position).getCustomer_primary_contact());
                                    intent.putExtra("cu_credit_limit", getCustomer.get(position).getCredit_limit());
                                    startActivity(intent);

                                }
                            })

                            .setSwipeOptionViews(R.id.edit, R.id.delete)
                            .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                                @Override
                                public void onSwipeOptionClicked(int viewID, final int position) {

                                    if (viewID == R.id.edit) {

                                        if (getCustomer.get(position).getSync_status().equals("0")) {

                                            Intent intent = new Intent(CustomerActivity.this, CustomerEditActivity.class);
                                            intent.putExtra("cu_name", getCustomer.get(position).getName());

                                            startActivityForResult(intent, CUSTOMER_DETAIL_REQUEST_CODE);
                                        } else {

                                            Utility.showDialog(CustomerActivity.this, "WARNING !", "Server Data!\nUnable to edit...", R.color.dialog_network_error_background);
                                        }

                                    } else if (viewID == R.id.delete) {

                                        if (getCustomer.get(position).getSync_status().equals("0")) {
                                            new android.app.AlertDialog.Builder(CustomerActivity.this)
                                                    .setMessage("Are you sure you want to delete?")
                                                    .setNegativeButton(android.R.string.no, null)
                                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface arg0, int arg1) {

                                                            db.deleteCustomer(getCustomer.get(position).getName());
                                                            db.deleteAddress(getCustomer.get(position).getName());

                                                            Toast t = Utility.setToast(CustomerActivity.this, "Delete Successful");
                                                            t.show();
                                                            startActivity(new Intent(CustomerActivity.this, CustomerActivity.class));

                                                        }
                                                    }).create().show();

                                        } else {

                                            Utility.showDialog(CustomerActivity.this, "WARNING !", "Server Data!\nUnable to delete...", R.color.dialog_network_error_background);

                                        }

                                    }
                                }
                            });


                }

               /* }else {
                    Utility.showDialog(CustomerActivity.this,"WARNING","No Data Found!",R.color.dialog_background);
                }*/
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (scanStatus == 0) {
            //startActivity(new Intent(CustomerActivity.this, HomeActivity.class));
            finish();
        }else if (scanStatus == 1){
            displayCust();
        }

    }


}
