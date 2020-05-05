package com.van.sale.vansale.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Retrofit_Model.SalesOrderRawItemData;
import com.van.sale.vansale.Retrofit_Model.SalesOrderRaw_TokenResponse;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.SalesOrderRecyclerViewAdapter;
import com.van.sale.vansale.model.SalesOrder;
import com.van.sale.vansale.model.SalesOrderClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;
import static com.koushikdutta.async.AsyncServer.LOGTAG;

public class SalesOrderActivity extends AppCompatActivity implements View.OnClickListener, RecyclerTouchListener.RecyclerTouchListenerHelper {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private List<SalesOrder> orderList;
    private SalesOrderRecyclerViewAdapter orderRecyclerViewAdapter;
    private RecyclerTouchListener onTouchListener;
    private OnActivityTouchListener touchListener;
    private DatabaseHandler db;
    private List<SalesOrderClass> getSalesOrder;
    private List<SalesOrderClass> getSalesOrder_temp;
    private TextView empty_tv;


    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;
    private long FASTEST_INTERVAL = 2000;
    private String Latitude, Longitude;
    private int scanStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order);

        /*  https://medium.com/@droidbyme/get-current-location-using-fusedlocationproviderclient-in-android-cb7ebf5ab88e   */

        startLocationUpdates();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        empty_tv = (TextView) findViewById(R.id.empty_tv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("SALES ORDER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        db = new DatabaseHandler(this);
        getSalesOrder = db.getSalesOrder();

       /* for (SalesOrderClass ccv : getSalesOrder) {

           if (ccv.getITEM_COUNT()==0){

               db.deleteSalesOrder(Integer.parseInt(ccv.getKEY_ID()));
               getSalesOrder.remove(ccv);

           }

        }*/

        Collections.reverse(getSalesOrder);
        getSalesOrder_temp = new ArrayList<>();
        getSalesOrder_temp.addAll(getSalesOrder);

        for (SalesOrderClass cc : getSalesOrder_temp) {

            Log.i("CCCC", "<==" + cc.getTOTAL_QUANDITY());
            Log.i("CCCC", "<==" + cc.getSALES_ORDER_CUSTOMER());
            Log.i("CCCC", "<==" + cc.getSALES_ORDER_CREATION());
            Log.i("CCCC", "<==" + cc.getSALES_ORDER_DELIVERY_DATE());

        }


      /*  List<SalesOrderRaw_TokenResponse> getSalesOrderForSync = db.getSalesOrderForSync();

        for (SalesOrderRaw_TokenResponse rt : getSalesOrderForSync){

            Log.i("SSO","<=="+rt.getCreation());
            Log.i("SSO","<=="+rt.getOwner());

            List<SalesOrderRawItemData> items = rt.getItems();

            for (SalesOrderRawItemData si : items){

                Log.i("SSI","<=="+si.getItemName());
            }

        }

*/


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

        displaySalesOrder();


        mFloatingActionButton.setOnClickListener(this);

    }

    private void displaySalesOrder() {
        scanStatus = 0;

        if (getSalesOrder.isEmpty()) {
            empty_tv.setVisibility(View.VISIBLE);
        } else {
            empty_tv.setVisibility(View.GONE);
        }

        orderRecyclerViewAdapter = new SalesOrderRecyclerViewAdapter(getApplicationContext(), getSalesOrder);
        mRecyclerView.setAdapter(orderRecyclerViewAdapter);
        onTouchListener = new RecyclerTouchListener(this, mRecyclerView);
        onTouchListener
                .setIndependentViews(R.id.customer_phone)
                .setViewsToFade(R.id.customer_phone)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {


                        Intent c_intent = new Intent(SalesOrderActivity.this, SalesOrderDetailActivity.class);
                        c_intent.putExtra("DOC_NO", getSalesOrder.get(position).getSALES_ORDER_DOC_NO());
                        c_intent.putExtra("ORDER_CREATION", getSalesOrder.get(position).getSALES_ORDER_CREATION());
                        c_intent.putExtra("ORDER_DELIVERY_DATE", getSalesOrder.get(position).getSALES_ORDER_DELIVERY_DATE());
                        c_intent.putExtra("ORDER_CUSTOMER_PO_NO", getSalesOrder.get(position).getSALES_ORDER_PO_NO());
                        c_intent.putExtra("ORDER_CUSTOMER_NAME", getSalesOrder.get(position).getSALES_ORDER_CUSTOMER());
                        c_intent.putExtra("ORDER_CUSTOMER_ID", getSalesOrder.get(position).getKEY_ID());
                        // c_intent.putExtra("ORDER_SYNC_STATUS", String.valueOf(getSalesOrder.get(position).getSYNC_STATUS()));
                        startActivity(c_intent);


                    }


                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                        Intent c_intent = new Intent(SalesOrderActivity.this, SalesOrderDetailActivity.class);
                        c_intent.putExtra("DOC_NO", getSalesOrder.get(position).getSALES_ORDER_DOC_NO());
                        c_intent.putExtra("ORDER_CREATION", getSalesOrder.get(position).getSALES_ORDER_CREATION());
                        c_intent.putExtra("ORDER_DELIVERY_DATE", getSalesOrder.get(position).getSALES_ORDER_DELIVERY_DATE());
                        c_intent.putExtra("ORDER_CUSTOMER_PO_NO", getSalesOrder.get(position).getSALES_ORDER_PO_NO());
                        c_intent.putExtra("ORDER_CUSTOMER_NAME", getSalesOrder.get(position).getSALES_ORDER_CUSTOMER());
                        c_intent.putExtra("ORDER_CUSTOMER_ID", getSalesOrder.get(position).getKEY_ID());
                        // c_intent.putExtra("ORDER_SYNC_STATUS", getSalesOrder.get(position).getSYNC_STATUS());
                        startActivity(c_intent);

                    }
                });


    }


    private void startLocationUpdates() {


        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        // onLocationChanged(locationResult.getLastLocation());
                        Location location = locationResult.getLastLocation();
                        Latitude = String.valueOf(location.getLatitude());
                        Longitude = String.valueOf(location.getLongitude());

                        Log.i("SALES_Latitude", "<==" + location.getLatitude());
                        Log.i("SALES_Longitude", "<==" + location.getLongitude());

                        // Toast.makeText(SalesOrderActivity.this, "Lattitude "+location.getLatitude()+", Longitude"+location.getLongitude(), Toast.LENGTH_SHORT).show();

                    }
                },
                Looper.myLooper());


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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                if (scanStatus == 0) {
                   // startActivity(new Intent(SalesOrderActivity.this, HomeActivity.class));
                    finish();
                }else if (scanStatus == 1){
                    displaySalesOrder();
                }

                return true;
            case R.id.menu_scan:
                IntentIntegrator integrator = new IntentIntegrator(SalesOrderActivity.this);
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

          //  Toast.makeText(this, ""+result+cust_name, Toast.LENGTH_SHORT).show();

            final List<SalesOrderClass> getSalesOrderByCustomer = db.getSalesOrderByCustomer(cust_name);

            if (!getSalesOrderByCustomer.isEmpty()) {

                scanStatus = 1;

                if (getSalesOrderByCustomer.isEmpty()) {
                    empty_tv.setVisibility(View.VISIBLE);
                } else {
                    empty_tv.setVisibility(View.GONE);
                }

                orderRecyclerViewAdapter = new SalesOrderRecyclerViewAdapter(getApplicationContext(), getSalesOrderByCustomer);
                mRecyclerView.setAdapter(orderRecyclerViewAdapter);
                onTouchListener = new RecyclerTouchListener(this, mRecyclerView);
                onTouchListener
                        .setIndependentViews(R.id.customer_phone)
                        .setViewsToFade(R.id.customer_phone)
                        .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                            @Override
                            public void onRowClicked(int position) {


                                Intent c_intent = new Intent(SalesOrderActivity.this, SalesOrderDetailActivity.class);
                                c_intent.putExtra("DOC_NO", getSalesOrderByCustomer.get(position).getSALES_ORDER_DOC_NO());
                                c_intent.putExtra("ORDER_CREATION", getSalesOrderByCustomer.get(position).getSALES_ORDER_CREATION());
                                c_intent.putExtra("ORDER_DELIVERY_DATE", getSalesOrderByCustomer.get(position).getSALES_ORDER_DELIVERY_DATE());
                                c_intent.putExtra("ORDER_CUSTOMER_PO_NO", getSalesOrderByCustomer.get(position).getSALES_ORDER_PO_NO());
                                c_intent.putExtra("ORDER_CUSTOMER_NAME", getSalesOrderByCustomer.get(position).getSALES_ORDER_CUSTOMER());
                                c_intent.putExtra("ORDER_CUSTOMER_ID", getSalesOrderByCustomer.get(position).getKEY_ID());
                                // c_intent.putExtra("ORDER_SYNC_STATUS", String.valueOf(getSalesOrder.get(position).getSYNC_STATUS()));
                                startActivity(c_intent);


                            }


                            @Override
                            public void onIndependentViewClicked(int independentViewID, int position) {

                                Intent c_intent = new Intent(SalesOrderActivity.this, SalesOrderDetailActivity.class);
                                c_intent.putExtra("DOC_NO", getSalesOrderByCustomer.get(position).getSALES_ORDER_DOC_NO());
                                c_intent.putExtra("ORDER_CREATION", getSalesOrderByCustomer.get(position).getSALES_ORDER_CREATION());
                                c_intent.putExtra("ORDER_DELIVERY_DATE", getSalesOrderByCustomer.get(position).getSALES_ORDER_DELIVERY_DATE());
                                c_intent.putExtra("ORDER_CUSTOMER_PO_NO", getSalesOrderByCustomer.get(position).getSALES_ORDER_PO_NO());
                                c_intent.putExtra("ORDER_CUSTOMER_NAME", getSalesOrderByCustomer.get(position).getSALES_ORDER_CUSTOMER());
                                c_intent.putExtra("ORDER_CUSTOMER_ID", getSalesOrderByCustomer.get(position).getKEY_ID());
                                // c_intent.putExtra("ORDER_SYNC_STATUS", getSalesOrder.get(position).getSYNC_STATUS());
                                startActivity(c_intent);

                            }
                        });

            }/*else{

                Utility.showDialog(SalesOrderActivity.this, "ERROR !", "No Result Found...", R.color.dialog_error_background);

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

        Intent intt = new Intent(SalesOrderActivity.this, SalesOrderAddActivity.class);
        intt.putExtra("SALES_LATTITUDE", Latitude);
        intt.putExtra("SALES_LONGITUDE", Longitude);
        startActivity(intt);

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
        searchIcon.setImageDrawable(ContextCompat.getDrawable(SalesOrderActivity.this, R.drawable.ic_search_white));

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
                    ArrayList<SalesOrderClass> tempArrayList = new ArrayList<SalesOrderClass>();
                    for (SalesOrderClass c : getSalesOrder) {
                        if (textlength <= c.getSALES_ORDER_CUSTOMER().length()) {
                            if (c.getSALES_ORDER_CUSTOMER().toLowerCase().contains(s.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }

                    getSalesOrder.clear();
                    getSalesOrder.addAll(tempArrayList);
                    orderRecyclerViewAdapter = new SalesOrderRecyclerViewAdapter(getApplicationContext(), getSalesOrder);
                    mRecyclerView.setAdapter(orderRecyclerViewAdapter);
                    onTouchListener = new RecyclerTouchListener(SalesOrderActivity.this, mRecyclerView);
                    onTouchListener
                            .setIndependentViews(R.id.customer_phone)
                            .setViewsToFade(R.id.customer_phone)
                            .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                                @Override
                                public void onRowClicked(int position) {


                                    Intent c_intent = new Intent(SalesOrderActivity.this, SalesOrderDetailActivity.class);
                                    c_intent.putExtra("DOC_NO", getSalesOrder.get(position).getSALES_ORDER_DOC_NO());
                                    c_intent.putExtra("ORDER_CREATION", getSalesOrder.get(position).getSALES_ORDER_CREATION());
                                    c_intent.putExtra("ORDER_DELIVERY_DATE", getSalesOrder.get(position).getSALES_ORDER_DELIVERY_DATE());
                                    c_intent.putExtra("ORDER_CUSTOMER_PO_NO", getSalesOrder.get(position).getSALES_ORDER_PO_NO());
                                    c_intent.putExtra("ORDER_CUSTOMER_NAME", getSalesOrder.get(position).getSALES_ORDER_CUSTOMER());
                                    c_intent.putExtra("ORDER_CUSTOMER_ID", getSalesOrder.get(position).getKEY_ID());
                                    // c_intent.putExtra("ORDER_SYNC_STATUS", String.valueOf(getSalesOrder.get(position).getSYNC_STATUS()));
                                    startActivity(c_intent);


                                }


                                @Override
                                public void onIndependentViewClicked(int independentViewID, int position) {

                                    Intent c_intent = new Intent(SalesOrderActivity.this, SalesOrderDetailActivity.class);
                                    c_intent.putExtra("DOC_NO", getSalesOrder.get(position).getSALES_ORDER_DOC_NO());
                                    c_intent.putExtra("ORDER_CREATION", getSalesOrder.get(position).getSALES_ORDER_CREATION());
                                    c_intent.putExtra("ORDER_DELIVERY_DATE", getSalesOrder.get(position).getSALES_ORDER_DELIVERY_DATE());
                                    c_intent.putExtra("ORDER_CUSTOMER_PO_NO", getSalesOrder.get(position).getSALES_ORDER_PO_NO());
                                    c_intent.putExtra("ORDER_CUSTOMER_NAME", getSalesOrder.get(position).getSALES_ORDER_CUSTOMER());
                                    c_intent.putExtra("ORDER_CUSTOMER_ID", getSalesOrder.get(position).getKEY_ID());
                                    // c_intent.putExtra("ORDER_SYNC_STATUS", getSalesOrder.get(position).getSYNC_STATUS());
                                    startActivity(c_intent);

                                }
                            });



                } else {
                    getSalesOrder.clear();
                    getSalesOrder.addAll(getSalesOrder_temp);
                    orderRecyclerViewAdapter = new SalesOrderRecyclerViewAdapter(getApplicationContext(), getSalesOrder);
                    mRecyclerView.setAdapter(orderRecyclerViewAdapter);
                    onTouchListener = new RecyclerTouchListener(SalesOrderActivity.this, mRecyclerView);
                    onTouchListener
                            .setIndependentViews(R.id.customer_phone)
                            .setViewsToFade(R.id.customer_phone)
                            .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                                @Override
                                public void onRowClicked(int position) {


                                    Intent c_intent = new Intent(SalesOrderActivity.this, SalesOrderDetailActivity.class);
                                    c_intent.putExtra("DOC_NO", getSalesOrder.get(position).getSALES_ORDER_DOC_NO());
                                    c_intent.putExtra("ORDER_CREATION", getSalesOrder.get(position).getSALES_ORDER_CREATION());
                                    c_intent.putExtra("ORDER_DELIVERY_DATE", getSalesOrder.get(position).getSALES_ORDER_DELIVERY_DATE());
                                    c_intent.putExtra("ORDER_CUSTOMER_PO_NO", getSalesOrder.get(position).getSALES_ORDER_PO_NO());
                                    c_intent.putExtra("ORDER_CUSTOMER_NAME", getSalesOrder.get(position).getSALES_ORDER_CUSTOMER());
                                    c_intent.putExtra("ORDER_CUSTOMER_ID", getSalesOrder.get(position).getKEY_ID());
                                    // c_intent.putExtra("ORDER_SYNC_STATUS", String.valueOf(getSalesOrder.get(position).getSYNC_STATUS()));
                                    startActivity(c_intent);


                                }


                                @Override
                                public void onIndependentViewClicked(int independentViewID, int position) {

                                    Intent c_intent = new Intent(SalesOrderActivity.this, SalesOrderDetailActivity.class);
                                    c_intent.putExtra("DOC_NO", getSalesOrder.get(position).getSALES_ORDER_DOC_NO());
                                    c_intent.putExtra("ORDER_CREATION", getSalesOrder.get(position).getSALES_ORDER_CREATION());
                                    c_intent.putExtra("ORDER_DELIVERY_DATE", getSalesOrder.get(position).getSALES_ORDER_DELIVERY_DATE());
                                    c_intent.putExtra("ORDER_CUSTOMER_PO_NO", getSalesOrder.get(position).getSALES_ORDER_PO_NO());
                                    c_intent.putExtra("ORDER_CUSTOMER_NAME", getSalesOrder.get(position).getSALES_ORDER_CUSTOMER());
                                    c_intent.putExtra("ORDER_CUSTOMER_ID", getSalesOrder.get(position).getKEY_ID());
                                    // c_intent.putExtra("ORDER_SYNC_STATUS", getSalesOrder.get(position).getSYNC_STATUS());
                                    startActivity(c_intent);

                                }
                            });


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
           // startActivity(new Intent(SalesOrderActivity.this, HomeActivity.class));
            finish();
        }else if (scanStatus == 1){
            displaySalesOrder();
        }
    }
}
