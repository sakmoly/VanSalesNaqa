package com.van.sale.vansale.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.GpsTrack.LocationTrack;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.CustomerVisitLog;
import com.van.sale.vansale.model.SalesInvoiceClass;
import com.van.sale.vansale.model.SalesInvoiceModeOfPayment;
import com.van.sale.vansale.model.SalesOrderClass;
import com.van.sale.vansale.model.SalesOrderItemClass;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class SalesInvoiceAddActivity extends AppCompatActivity implements View.OnClickListener , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener   {

    Button next_request, cancel_request;
    private EditText date_picker_result, customer_po_no;
    private LinearLayout date_picker_image;

    private TextView customer_tv, doc_no;
    public static int CUSTOMER_SELECTION_REQUEST_CODE = 119;
    public static int RETURN_REQUEST_CODE = 129;
    private DatabaseHandler db;
    // EasyWayLocation easyWayLocation;
    private Double lati, longi;
    private Float DEFAULT_CONVERSION_RATE = Float.valueOf(1);
    private Float DEFAULT_PLC_CONVERSION_RATE = Float.valueOf(1);
    private int DEFAULT_DOC_STATUS = 0;
    private int UNSYNC_STATUS = 0;
    private String SALES_ORDER_OWNER;
    private String DEFAULT_BILLING_STATUS;
    private String DEFAULT_ORDER_TYPE;
    private String DEFAULT_STATUS;
    private String SALES_ORDER_CURRENCY;
    private String SALES_ORDER_PRICE_LIST_CURRENCY;
    private String SALES_INVOICE_NAMING_SERIES;
    private String DEVICE_ID;
    private String DEFAULT_COMPANY;
    private static String RETURN_AGAINST_INVOICE_NO = "";


    private TextView cash_tv, INVOICE_NO_TV, CREATION_DATE_TV, CUSTOMER_TV, RETURN_AGAINST_TV, POSTING_TIME_TV, POSTING_DATE_TV;
    private LinearLayout IS_RETURN_LAYOUT, cash_layout;
    private static LinearLayout RETURN_AGAINST_LAYOUT;
    private static ImageView IS_RETURN_IMAGE, cash_image;
    private static int IS_RETURN_SELECTION_STATUS, cash_selection_status;
    private Button NEXT_BTN, CANCEL_BTN, CUSTOMER_VISIT_BTN;
    private static List<SalesOrderItemClass> getSalesOrderItem;
    private static String selected_customer_id;
    List<SalesInvoiceClass> getSalesInvoice;
    List<String> gpsloc;

    //Location related
    private Location location;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST=9000;
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL=5000,FASTEST_INTERVAL=5000;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected=new ArrayList<>();
    private ArrayList<String> permissions=new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT=1011;
    //Location related end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_invoice_add);

        db = new DatabaseHandler(this);
        //SALES_ORDER_OWNER = db.getApiUsername();
        SALES_ORDER_OWNER = Utility.getLoginUser(this);
        DEFAULT_COMPANY = db.getCompanyName();
        SALES_ORDER_CURRENCY = db.getCurrency();
        SALES_ORDER_PRICE_LIST_CURRENCY = db.getCurrency();
        SALES_INVOICE_NAMING_SERIES = db.getSalesInvoiceName();
        DEFAULT_STATUS = "Unpaid";
        DEVICE_ID = db.getDeviceIdFromSetting();
        IS_RETURN_SELECTION_STATUS = 0;

        getSalesOrderItem = new ArrayList<>();
       //gpsloc=getLocation();

        //location related
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.
                        toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        googleApiClient=new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
        //location related end

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("INVOICE CREATION");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        INVOICE_NO_TV = (TextView) findViewById(R.id.invoice_no);
        CREATION_DATE_TV = (TextView) findViewById(R.id.creation_date);
        CUSTOMER_TV = (TextView) findViewById(R.id.customer_tv);
        RETURN_AGAINST_TV = (TextView) findViewById(R.id.return_against_tv);
        POSTING_TIME_TV = (TextView) findViewById(R.id.posting_time_tv);
        POSTING_DATE_TV = (TextView) findViewById(R.id.posting_date_tv);
        cash_tv = (TextView) findViewById(R.id.cash_tv);
        NEXT_BTN = (Button) findViewById(R.id.next_request);
        CANCEL_BTN = (Button) findViewById(R.id.cancel_request);
        CUSTOMER_VISIT_BTN = (Button) findViewById(R.id.customer_visit);

        IS_RETURN_LAYOUT = (LinearLayout) findViewById(R.id.isreturn_layout);
        RETURN_AGAINST_LAYOUT = (LinearLayout) findViewById(R.id.return_against_layout);
        cash_layout = (LinearLayout) findViewById(R.id.cash_layout);
        IS_RETURN_IMAGE = (ImageView) findViewById(R.id.isreturn_image);
        cash_image = (ImageView) findViewById(R.id.cash_image);

        cash_selection_status = 1;
        cash_image.setImageResource(R.drawable.ic_switch_on);
        cash_tv.setText("Cash");

        CREATION_DATE_TV.setText(Utility.getCurrentDate()+" "+Utility.getCurrentTime());
        POSTING_DATE_TV.setText(Utility.getCurrentDate());
        INVOICE_NO_TV.setText(db.getSalesInvoiceName() + Utility.padLeftFormat(String.valueOf(db.getSalesInvoiceDocNo() + 1), 5));
        POSTING_TIME_TV.setText(Utility.getCurrentTime());



        /* https://github.com/delight-im/Android-SimpleLocation */


        IS_RETURN_LAYOUT.setOnClickListener(this);
        CUSTOMER_TV.setOnClickListener(this);
        NEXT_BTN.setOnClickListener(this);
        CANCEL_BTN.setOnClickListener(this);
        CUSTOMER_VISIT_BTN.setOnClickListener(this);
        cash_layout.setOnClickListener(this);

       // homeActivity=new HomeActivity();

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


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.isreturn_layout:
                isReturnItem();
                break;
            case R.id.cancel_request:
                finish();
                break;
            case R.id.next_request:
                nextClick();
                break;
            case R.id.customer_tv:
                customerSelectionClick();
                break;
            case R.id.cash_layout:
                cashClick();
                break;
            case R.id.customer_visit:
                if (!CUSTOMER_TV.getText().toString().isEmpty()) {
                    AlertDialog.Builder myalert = new AlertDialog.Builder(this);
                    myalert.setTitle("Confirm");
                    myalert.setMessage("Is there no sales?");
                    myalert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            addCustomerVisit();
                        }
                    });
                    myalert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    myalert.show();
                }
                else {
                    Utility.showDialog(SalesInvoiceAddActivity.this, "ERROR !", "Customer Name Required....", R.color.dialog_error_background);
                }
                break;
        }
    }


    private void cashClick() {

        if (cash_selection_status == 0) {
            cash_image.setImageResource(R.drawable.ic_switch_on);
            cash_tv.setText("Cash");
            cash_selection_status = 1;
        } else {

            cash_image.setImageResource(R.drawable.ic_switch_off);
            cash_tv.setText("Credit");
            cash_selection_status = 0;
        }


    }


    @Override
    public void onBackPressed() {

        finish();

    }

    private void isReturnItem() {

        if (IS_RETURN_SELECTION_STATUS == 0) {
            RETURN_AGAINST_LAYOUT.setVisibility(View.VISIBLE);

            if (!CUSTOMER_TV.getText().toString().isEmpty()) {

                IS_RETURN_IMAGE.setImageResource(R.drawable.ic_switch_on);
                IS_RETURN_SELECTION_STATUS = 1;

                Intent return_intent = new Intent(SalesInvoiceAddActivity.this, IsReturnActivity.class);
                return_intent.putExtra("Return_Selected_Customer", CUSTOMER_TV.getText().toString());
                return_intent.putExtra("Return_Selected_Customer_id", selected_customer_id);
                startActivityForResult(return_intent, RETURN_REQUEST_CODE);


            } else {

                Utility.showDialog(SalesInvoiceAddActivity.this, "ERROR !", "Select Customer....", R.color.dialog_error_background);

            }


        } else {
            RETURN_AGAINST_LAYOUT.setVisibility(View.GONE);
            IS_RETURN_IMAGE.setImageResource(R.drawable.ic_switch_off);
            IS_RETURN_SELECTION_STATUS = 0;
        }

    }


    private void customerSelectionClick() {

        Intent selectionIntent = new Intent(SalesInvoiceAddActivity.this, CustomerSearchActivity.class);
        startActivityForResult(selectionIntent, CUSTOMER_SELECTION_REQUEST_CODE);

    }


    private void datePickerDialog() {

        Utility.datePickerResult(SalesInvoiceAddActivity.this, date_picker_result);

    }

    private void addCustomerVisit() {
        try {

            if (!CUSTOMER_TV.getText().toString().isEmpty()) {

                /*List<String> loc=getLocation();
                //Thread.sleep(1500);
                if(loc.size()==0)
                    loc=gpsloc;*/
                String creationDateTime=CREATION_DATE_TV.getText().toString();

                CustomerVisitLog cv = new CustomerVisitLog();
                cv.setCUSTOMER_VISIT_KEY_ID(db.getCustomerVisitHighestID() + 1);
                cv.setCUSTOMER_VISIT_SALES_PERSON(Utility.getPrefs("login_user", this));
                cv.setCUSTOMER_VISIT_VISIT_DATE(creationDateTime);

                cv.setCUSTOMER_VISIT_MODIFIED(creationDateTime);
                cv.setCUSTOMER_VISIT_VISIT_RESULT("No Sale");
                cv.setCUSTOMER_VISIT_AMOUNT(0.0f);
                cv.setCUSTOMER_VISIT_CUSTOMER(CUSTOMER_TV.getText().toString());
                cv.setCUSTOMER_VISIT_COMMENTS("");
                cv.setCUSTOMER_VISIT_DOC_STATUS(0);
                /*if(loc.size()>0) {
                    cv.setCUSTOMER_VISIT_LATITUDE(loc.get(0));
                    cv.setCUSTOMER_VISIT_LONGITUDE(loc.get(1));
                }
                else
                {
                    cv.setCUSTOMER_VISIT_LATITUDE("0");
                    cv.setCUSTOMER_VISIT_LONGITUDE("0");
                }*/
                if(Utility.googleLoc.isEmpty()){
                    cv.setCUSTOMER_VISIT_LONGITUDE("0");
                    cv.setCUSTOMER_VISIT_LATITUDE("0");
                }
                else{
                    cv.setCUSTOMER_VISIT_LONGITUDE(Utility.googleLoc.get(1));
                    cv.setCUSTOMER_VISIT_LATITUDE(Utility.googleLoc.get(0));
                }
                cv.setCUSTOMER_VISIT_OWNER(Utility.getPrefs("login_user", this));
                cv.setCUSTOMER_VISIT_CREATION(creationDateTime);
                cv.setCUSTOMER_VISIT_NAMING_SERIES(db.getCusomerVisitName());
                cv.setCUSTOMER_VISIT_REFERENCE("");
                cv.setCUSTOMER_VISIT_MODIFIED_BY(Utility.getPrefs("login_user", this));
                cv.setCUSTOMER_VISIT_SYNC_STATUS("0");
                cv.setCUTOMER_VISIT_IDX(0);
                db.addCustomerVisitLog(cv);

                AlertDialog.Builder okAlert = new AlertDialog.Builder(this);
                okAlert.setTitle("Save");
                okAlert.setMessage("Customer visit saved successfully");
                okAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                okAlert.show();

            } else {
                Utility.showDialog(SalesInvoiceAddActivity.this, "ERROR !", "Customer Name Required....", R.color.dialog_error_background);
            }

        } catch (Exception e) {

            Utility.showDialog(SalesInvoiceAddActivity.this, "ERROR!", e.getMessage(), R.color.dialog_error_background);

        }

    }

    private void nextClick() {


        if (!CUSTOMER_TV.getText().toString().isEmpty()) {

            SalesInvoiceClass salesHeader = new SalesInvoiceClass(1, IS_RETURN_SELECTION_STATUS, CREATION_DATE_TV.getText().toString(), SALES_ORDER_OWNER, SALES_ORDER_PRICE_LIST_CURRENCY, CUSTOMER_TV.getText().toString(), DEFAULT_COMPANY, SALES_INVOICE_NAMING_SERIES, SALES_ORDER_CURRENCY, INVOICE_NO_TV.getText().toString(), RETURN_AGAINST_TV.getText().toString(), POSTING_TIME_TV.getText().toString(), POSTING_DATE_TV.getText().toString(), DEFAULT_STATUS, DEVICE_ID, "0", DEFAULT_CONVERSION_RATE, DEFAULT_PLC_CONVERSION_RATE, cash_selection_status, 1, RETURN_AGAINST_INVOICE_NO);
            // db.addSalesInvoice(new SalesInvoiceClass(1, IS_RETURN_SELECTION_STATUS, CREATION_DATE_TV.getText().toString(), SALES_ORDER_OWNER, SALES_ORDER_PRICE_LIST_CURRENCY, CUSTOMER_TV.getText().toString(), DEFAULT_COMPANY, SALES_INVOICE_NAMING_SERIES, SALES_ORDER_CURRENCY, INVOICE_NO_TV.getText().toString(), RETURN_AGAINST_TV.getText().toString(), POSTING_TIME_TV.getText().toString(), POSTING_DATE_TV.getText().toString(), DEFAULT_STATUS, DEVICE_ID, "0", DEFAULT_CONVERSION_RATE, DEFAULT_PLC_CONVERSION_RATE,cash_selection_status,1,RETURN_AGAINST_INVOICE_NO));
            //db.updateSalesInvoiceDocNo(db.getSalesInvoiceDocNo() + 1);
            InvoiceItemListActivity.setSalesInvoiceClass(salesHeader);

            InvoiceItemListActivity.setCustomerName(CUSTOMER_TV.getText().toString());
            InvoiceItemListActivity.setCustomerId(selected_customer_id);
            InvoiceItemListActivity.setReturnItems(getSalesOrderItem);

            Intent next_intent = new Intent(SalesInvoiceAddActivity.this, InvoiceItemListActivity.class);
            next_intent.putExtra("IS_RETURN_SELECTION_STATUS", String.valueOf(IS_RETURN_SELECTION_STATUS));
            next_intent.putExtra("CASH_SELECTION_STATUS", String.valueOf(cash_selection_status));
            next_intent.putExtra("CURRENT_DOC_NO", db.getSalesInvoiceDocNo());
            startActivity(next_intent);

        } else {
            Utility.showDialog(SalesInvoiceAddActivity.this, "ERROR !", "Customer Name Required....", R.color.dialog_error_background);
        }


       /* if (!doc_no.getText().toString().isEmpty()) {
            if (!creation_date.getText().toString().isEmpty()) {
                if (!date_picker_result.getText().toString().isEmpty()) {
                    if (!customer_po_no.getText().toString().isEmpty()) {
                        if (!customer_tv.getText().toString().isEmpty()) {

                            Utility.setPrefs("VANSALE_DOC_NO", doc_no.getText().toString(), SalesInvoiceAddActivity.this);
                            Utility.setPrefs("VANSALE_CREATE_DATE", creation_date.getText().toString(), SalesInvoiceAddActivity.this);
                            Utility.setPrefs("VANSALE_DELIVERY_DATE", date_picker_result.getText().toString(), SalesInvoiceAddActivity.this);
                            Utility.setPrefs("VANSALE_PO_NO", customer_po_no.getText().toString(), SalesInvoiceAddActivity.this);
                            Utility.setPrefs("VANSALE_cus", customer_tv.getText().toString(), SalesInvoiceAddActivity.this);

                            // db.addSalesOrder(new SalesOrderClass(DEFAULT_CONVERSION_RATE, DEFAULT_PLC_CONVERSION_RATE, DEFAULT_DOC_STATUS, UNSYNC_STATUS, creation_date.getText().toString(), SALES_ORDER_OWNER, DEFAULT_BILLING_STATUS, customer_po_no.getText().toString(), customer_tv.getText().toString(), DEFAULT_ORDER_TYPE, DEFAULT_STATUS, DEFAULT_COMPANY, SALES_ORDER_NAMING_SERIES, doc_no.getText().toString(), date_picker_result.getText().toString(), SALES_ORDER_CURRENCY, SALES_ORDER_PRICE_LIST_CURRENCY, DEVICE_ID));
                            db.updateSalesOrderDocNo(db.getSalesOrderDocNo() + 1);
                            SalesOrderItemListActivity.setBadgeCount(0);

                            startActivity(new Intent(SalesInvoiceAddActivity.this, SalesOrderItemListActivity.class));
                            finish();

                        } else {
                            Utility.showDialog(SalesInvoiceAddActivity.this, "ERROR !", "Select Customer....", R.color.dialog_error_background);
                        }
                    } else {
                        Utility.showDialog(SalesInvoiceAddActivity.this, "ERROR !", "Customer po no. Required....", R.color.dialog_error_background);
                    }
                } else {
                    Utility.showDialog(SalesInvoiceAddActivity.this, "ERROR !", "Delivery Date Required....", R.color.dialog_error_background);
                }
            } else {
                Utility.showDialog(SalesInvoiceAddActivity.this, "ERROR !", "Date Required....", R.color.dialog_error_background);
            }
        } else {
            Utility.showDialog(SalesInvoiceAddActivity.this, "ERROR !", "Doc no. Required....", R.color.dialog_error_background);
        }
*/


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CUSTOMER_SELECTION_REQUEST_CODE) {

            String message = data.getStringExtra("cstmr_name");
            selected_customer_id = data.getStringExtra("cstmr_id");
            CUSTOMER_TV.setText(message);

        } else if (resultCode == RETURN_REQUEST_CODE) {


            String selected_invoice_id = data.getStringExtra("ORDER_NUMBER");
            RETURN_AGAINST_TV.setText(selected_invoice_id);

        }
    }


    public static void setSelectedClass(List<SalesOrderItemClass> getSalesOrderItems) {

        getSalesOrderItem = getSalesOrderItems;

    }

    public static void setReturnAgainstInvoiceNo(String returnAgainstInvoiceNo) {

        RETURN_AGAINST_INVOICE_NO = returnAgainstInvoiceNo;

    }

    public static void setIS_RETURN_SELECTION_STATUS() {

        RETURN_AGAINST_LAYOUT.setVisibility(View.GONE);
        IS_RETURN_IMAGE.setImageResource(R.drawable.ic_switch_off);
        IS_RETURN_SELECTION_STATUS = 0;

    }


    private List<String> getLocation() {
        final List<String> gpsLoc = new ArrayList<String>();
        LocationTrack.LocationResult locationResult = new LocationTrack.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                gpsLoc.add(String.valueOf(location.getLatitude()));
                gpsLoc.add(String.valueOf(location.getLongitude()));
            }
        };
        LocationTrack myLocation = new LocationTrack();
        myLocation.getLocation(this, locationResult);

        return gpsLoc;
    }
    //Location related
    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions){
        ArrayList<String> result=new ArrayList<>();
        for(String perm:wantedPermissions){
            if(!hasPermission(perm)){
                result.add(perm);
            }
        }
        return  result;
    }
    private Boolean hasPermission(String permission){
        if(Build.VERSION.SDK_INT==Build.VERSION_CODES.M){
            return checkSelfPermission(permission)== PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED ){
            return;
        }
        location=LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(location!=null){
            Utility.googleLoc.clear();
            Utility.googleLoc.add(String.valueOf(location.getLatitude()));
            Utility.googleLoc.add(String.valueOf(location.getLongitude()));
            //android.widget.Toast.makeText(this, location.getLatitude()+" \n"+location.getLongitude(), Toast.LENGTH_LONG).show();
        }
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            Utility.googleLoc.clear();
            Utility.googleLoc.add(String.valueOf(location.getLatitude()));
            Utility.googleLoc.add(String.valueOf(location.getLongitude()));
            //Toast.makeText(this,location.getLatitude()+"\n"+location.getLongitude(),Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(googleApiClient!=null){
            googleApiClient.connect();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!checkPlayService()){
            Utility.setToast(this,"You need to install playstore").show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,  this);
            googleApiClient.disconnect();
        }
    }


    private void startLocationUpdates(){
        try {


            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(UPDATE_INTERVAL);
            locationRequest.setFastestInterval(FASTEST_INTERVAL);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You need to enable location service", Toast.LENGTH_LONG).show();
            }
            // LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (com.google.android.gms.location.LocationListener) this);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,this);
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    private boolean checkPlayService(){
        GoogleApiAvailability googleApiAvailability= GoogleApiAvailability.getInstance();
        int resultCode=googleApiAvailability.isGooglePlayServicesAvailable(this);
        if(resultCode!=ConnectionResult.SUCCESS){
            if(googleApiAvailability.isUserResolvableError(resultCode)){
                googleApiAvailability.getErrorDialog(this,resultCode,PLAY_SERVICES_RESOLUTION_REQUEST);

            }
            else{
                finish();

            }
            return false;
        }

        return true;
    }
    //Location related end
}