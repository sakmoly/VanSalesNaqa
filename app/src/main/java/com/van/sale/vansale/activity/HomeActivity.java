package com.van.sale.vansale.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Retrofit_Interface.UserInterface;
import com.van.sale.vansale.Retrofit_Model.AddressData;
import com.van.sale.vansale.Retrofit_Model.AddressSync_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.Address_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.Authentication_Response;
import com.van.sale.vansale.Retrofit_Model.CustomerAssetData;
import com.van.sale.vansale.Retrofit_Model.CustomerAsset_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.CustomerData;
import com.van.sale.vansale.Retrofit_Model.CustomerSync_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.CustomerVisitRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.Customer_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.InvoiceNameIdData;
import com.van.sale.vansale.Retrofit_Model.InvoiceNameId_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.ItemData;
import com.van.sale.vansale.Retrofit_Model.ItemDetailData;
import com.van.sale.vansale.Retrofit_Model.ItemDetailUom;
import com.van.sale.vansale.Retrofit_Model.ItemDetail_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.Item_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.ModeOfPaymentByNameAccount;
import com.van.sale.vansale.Retrofit_Model.ModeOfPaymentByNameData;
import com.van.sale.vansale.Retrofit_Model.ModeOfPaymentByName_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.ModePaymentData;
import com.van.sale.vansale.Retrofit_Model.ModePayment_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.PaymentRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.PaymentSync_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw1_ItemData;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw1_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw_ItemData;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw_TaxData;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw_TokenResponse_1;
import com.van.sale.vansale.Retrofit_Model.SalesOrderRawItemData;
import com.van.sale.vansale.Retrofit_Model.SalesOrderRawItemData1;
import com.van.sale.vansale.Retrofit_Model.SalesOrderRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesOrderRaw_TokenResponse1;
import com.van.sale.vansale.Retrofit_Model.SalesOrderSync_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesOrderTaxRawData;
import com.van.sale.vansale.SyncData.AutoSyncTimerService;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.AddressClass;
import com.van.sale.vansale.model.AddressPost;
import com.van.sale.vansale.model.CusomerAssetList;
import com.van.sale.vansale.model.CustomerClass;
import com.van.sale.vansale.model.CustomerPost;
import com.van.sale.vansale.model.CustomerVisitLog;
import com.van.sale.vansale.model.ItemClass;
import com.van.sale.vansale.model.ItemDetailClass;
import com.van.sale.vansale.model.Mode_Of_Payment;
import com.van.sale.vansale.model.Payment;
import com.van.sale.vansale.model.SalesInvoiceRaw1_ItemPayments;
import com.van.sale.vansale.model.SalesInvoiceRaw_ItemPayments;
import com.van.sale.vansale.model.SettingsClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener  {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 125;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 128;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 111;
    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 333;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 222;
    private LinearLayout item_layout, customer_layout, sales_order_layout, sales_invoice_layout, collection_layout, transfer_layout, sync_layout,daily_report_layout, logout_layout;
    private DatabaseHandler db;
    private int mCustomerAccessStatus = 0, mOrderAccessStatus = 0, mInvoiceAccessStatus = 0, mPaymentAccessStatus = 0, mTransferAccessStatus = 0;
    public static AlertDialog alertDialog, customer_alertDialog, item_alertDialog;
    private int master_warehouse_status = 1, master_currency_status = 1, master_item_status = 1, master_price_list_status = 1, master_payment_status = 1, master_customer_status = 1,
            master_user_status = 1, transaction_customer_status = 0, transaction_sales_order_status = 0, transaction_sales_invoice_status = 0, transaction_payment_status = 0,
            transaction_transfer_in_status = 0, transaction_transfer_out_status = 0;
    OkHttpClient httpClient;
    private Retrofit retrofit;
    private UserInterface userInterface;
    private String cookie;
    private String storedUrl;
    private int master_click_status = 0, transaction_click_status = 0;
    private List<SalesOrderTaxRawData> taxess;
    private SalesOrderTaxRawData salesOrderTaxRawData;
    private SalesOrderRaw_TokenResponse rawTokenResponse;
    private SalesInvoiceRaw_TokenResponse invoiceRawTokenResponse;
    private SalesInvoiceRaw_TokenResponse_1 invoiceRawTokenResponse_1;
    private PaymentRaw_TokenResponse paymentRaw_tokenResponse;
    private CustomerVisitRaw_TokenResponse visitClass;
    String  InvoiceNo="";

    private Float Sync_Tax_Rate = 0.0f, Sync_Tax_Amount = 0.0f, Sync_Total = 0.0f;
    private Float Sync_Tax_Rate1 = 0.0f, Sync_Tax_Amount1 = 0.0f, Sync_Total1 = 0.0f;

    private String Tax_Account_Head, COMPANY_NAME;
    static int si_data = 0, py_data = 0, sn_data = 0, customer_sync_status = 0, item_sync_status = 0,cv_data=0,cv_data1=0;
    static int si_sync_count=0;
    static int si_pending=0;
    Boolean isSalesSyncProgress=false;

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
    private TextView tv_login_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        db = new DatabaseHandler(HomeActivity.this);

        Tax_Account_Head = db.getAccountHead();
        COMPANY_NAME = db.getCompanyName();

        List<ItemDetailClass> getAllItemDetai = db.getAllItemDetail();

        for (ItemDetailClass idc : getAllItemDetai) {

            Log.i("UOMM", "<==" + idc.getItem_parent());
            Log.i("UOMM", "<==" + idc.getUom());

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            cameraPermissionAccess();

        }


        customer_layout = (LinearLayout) findViewById(R.id.customer_layout);
        sales_order_layout = (LinearLayout) findViewById(R.id.sales_order_layout);
        sales_invoice_layout = (LinearLayout) findViewById(R.id.sales_invoice_layout);
        collection_layout = (LinearLayout) findViewById(R.id.collection_layout);
        transfer_layout = (LinearLayout) findViewById(R.id.transfer_layout);
        sync_layout = (LinearLayout) findViewById(R.id.sync_layout);
        logout_layout = (LinearLayout) findViewById(R.id.logout_layout);
        item_layout = (LinearLayout) findViewById(R.id.item_layout);
        daily_report_layout=(LinearLayout)findViewById(R.id.report_layout);
        tv_login_user=(TextView)findViewById(R.id.login_user_tv);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(HomeActivity.this));

        httpClient = new OkHttpClient.Builder()
                // .connectTimeout(20, TimeUnit.SECONDS)
                 .writeTimeout(60, TimeUnit.SECONDS)
                 .readTimeout(60, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .addInterceptor(logging)
                .build();

        storedUrl = db.getStoredUrl();

        callRetrofitBase(storedUrl);


        cookie = Utility.getPrefs("vansale_cookie", HomeActivity.this);
        tv_login_user.setText(Utility.getPrefs("login_user", HomeActivity.this));

        List<SettingsClass> getSettings = db.getSettings();

        mCustomerAccessStatus = getSettings.get(0).getCustomer_access();
        mOrderAccessStatus = getSettings.get(0).getSales_order_access();
        mInvoiceAccessStatus = getSettings.get(0).getSales_invoice_access();
        mPaymentAccessStatus = getSettings.get(0).getPayment_access();
        mTransferAccessStatus = getSettings.get(0).getTransfer_access();


        customer_layout.setOnClickListener(this);
        sales_order_layout.setOnClickListener(this);
        sales_invoice_layout.setOnClickListener(this);
        collection_layout.setOnClickListener(this);
        transfer_layout.setOnClickListener(this);
        sync_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
        item_layout.setOnClickListener(this);
        daily_report_layout.setOnClickListener(this);
        if(db.getSyncInterval()>4){
            Log.i("SyncStart",db.getSyncInterval()+" Started");
            startService(new Intent(HomeActivity.this, AutoSyncTimerService.class));
        }



    }

    private void cameraPermissionAccess() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

            }

        }


    }

    private void locationPermissionAccess() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            }

           /* if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this, permissionsToRequest.
                        toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);

            } else {

                ActivityCompat.requestPermissions(this, permissionsToRequest.
                        toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);

            }*/


        }


    }

    private void storagePermissionAccess() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_STORAGE);

            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_STORAGE);

            }

        }


    }

    private void storageWRITEPermissionAccess() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);

            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);

            }

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    storagePermissionAccess();
                    // Toast.makeText(getApplicationContext(), "Location Permission granted", Toast.LENGTH_SHORT).show();

                } else {
                    locationPermissionAccess();

                }

                return;
            }

            case MY_PERMISSIONS_REQUEST_CAMERA: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    locationPermissionAccess();

                    //  Toast.makeText(getApplicationContext(), "Camera Permission granted", Toast.LENGTH_SHORT).show();

                } else {
                    cameraPermissionAccess();

                }

                return;

            }

            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    storageWRITEPermissionAccess();

                    //  Toast.makeText(getApplicationContext(), "Camera Permission granted", Toast.LENGTH_SHORT).show();

                } else {
                    storagePermissionAccess();

                }

                return;

            }


            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // storageWRITEPermissionAccess();

                    //  Toast.makeText(getApplicationContext(), "Camera Permission granted", Toast.LENGTH_SHORT).show();

                } else {
                    storageWRITEPermissionAccess();

                }

                return;

            }

        }

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.customer_layout:
                customerClick();
                break;
            case R.id.sales_order_layout:
                salesOrderClick();
                break;
            case R.id.sales_invoice_layout:
                salesInvoiceClick();
                break;
            case R.id.collection_layout:
                collectionClick();
                break;
            case R.id.transfer_layout:
                transferClick();
                break;
            case R.id.sync_layout:
                syncClick();
                break;
            case R.id.logout_layout:
                logoutClick();
                break;
            case R.id.item_layout:
                itemClick();
                break;
            case R.id.report_layout:
                 reportClick();
                break;

        }
    }


    private void itemClick() {

        Intent itemClickIntent = new Intent(HomeActivity.this, HomeItemListActivity.class);
        startActivity(itemClickIntent);

    }


    private void logoutClick() {

        new android.app.AlertDialog.Builder(this)
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                       /* HomeActivity.super.onBackPressed();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //    handler.removeCallbacksAndMessages(null);
                        startActivity(intent);*/

                        Utility.setPrefs("vansale_login_status", "0", HomeActivity.this);
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));

                    }
                }).create().show();

       /* Toast t = Utility.setToast(HomeActivity.this, Utility.TOAST_COMING_SOON);
        t.show();*/
    }


    private void syncClick() {

        LayoutInflater li = LayoutInflater.from(HomeActivity.this);
        View promptsView = li.inflate(R.layout.sync_popup, null);

        LinearLayout master_click = promptsView.findViewById(R.id.master_data_click);
        LinearLayout transaction_click = promptsView.findViewById(R.id.transaction_click);


        /* ======== master ========= */


        LinearLayout master_warehouse = promptsView.findViewById(R.id.master_warehouse);
        LinearLayout master_currency = promptsView.findViewById(R.id.master_currency);
        LinearLayout master_item = promptsView.findViewById(R.id.master_item);
        LinearLayout master_price_list = promptsView.findViewById(R.id.master_price_list);
        LinearLayout master_payment = promptsView.findViewById(R.id.master_payment);
        LinearLayout master_customer = promptsView.findViewById(R.id.master_customer);
        LinearLayout master_user = promptsView.findViewById(R.id.master_user);

        /* ========= transaction ========= */

        LinearLayout transaction_customer = promptsView.findViewById(R.id.transaction_customer);
        LinearLayout transaction_sales_order = promptsView.findViewById(R.id.transaction_sales_order);
        LinearLayout transaction_sales_invoice = promptsView.findViewById(R.id.transaction_sales_invoice);
        LinearLayout transaction_payment = promptsView.findViewById(R.id.transaction_payment);
        LinearLayout transaction_transfer_in = promptsView.findViewById(R.id.transaction_transfer_in);
        LinearLayout transaction_transfer_out = promptsView.findViewById(R.id.transaction_transfer_out);

        final LinearLayout master_data = promptsView.findViewById(R.id.master_data);
        final LinearLayout transaction_data = promptsView.findViewById(R.id.transaction_data);

        Button sync_request = promptsView.findViewById(R.id.sync_request);
        Button cancel_request = promptsView.findViewById(R.id.cancel_request);

        final ImageView master_click_image = promptsView.findViewById(R.id.master_click_image);
        final ImageView transaction_click_image = promptsView.findViewById(R.id.transaction_image_click);

        final ImageView warehouse_click = promptsView.findViewById(R.id.warehouse_click);
        final ImageView currency_click = promptsView.findViewById(R.id.currency_click);
        final ImageView item_click = promptsView.findViewById(R.id.item_click);
        final ImageView price_list_click = promptsView.findViewById(R.id.price_list_click);
        final ImageView master_payment_click = promptsView.findViewById(R.id.master_payment_click);
        final ImageView master_customer_click = promptsView.findViewById(R.id.master_customer_click);
        final ImageView master_user_click = promptsView.findViewById(R.id.master_user_click);


        final ImageView transaction_customer_click = promptsView.findViewById(R.id.transaction_customer_click);
        final ImageView transaction_sales_order_click = promptsView.findViewById(R.id.transaction_sales_order_click);
        final ImageView transaction_sales_invoice_click = promptsView.findViewById(R.id.transaction_sales_invoice_click);
        final ImageView transaction_payment_click = promptsView.findViewById(R.id.transaction_payment_click);
        final ImageView transaction_trans_in_click = promptsView.findViewById(R.id.transaction_trans_in_click);
        final ImageView transaction_trans_out_click = promptsView.findViewById(R.id.transaction_trans_out_click);


        master_warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (master_warehouse_status == 0) {
                    master_warehouse_status = 1;
                    warehouse_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    master_warehouse_status = 0;
                    warehouse_click.setImageResource(R.drawable.ic_tick_circle);
                }

            }
        });


        master_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (master_currency_status == 0) {
                    master_currency_status = 1;
                    currency_click.setImageResource(R.drawable.ic_tick_done);


                } else {
                    master_currency_status = 0;
                    currency_click.setImageResource(R.drawable.ic_tick_circle);


                }
            }
        });


        master_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (master_item_status == 0) {
                    master_item_status = 1;
                    item_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    master_item_status = 0;
                    item_click.setImageResource(R.drawable.ic_tick_circle);
                }
            }
        });


        master_price_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (master_price_list_status == 0) {
                    master_price_list_status = 1;
                    price_list_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    master_price_list_status = 0;
                    price_list_click.setImageResource(R.drawable.ic_tick_circle);
                }
            }
        });

        master_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (master_payment_status == 0) {
                    master_payment_status = 1;
                    master_payment_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    master_payment_status = 0;
                    master_payment_click.setImageResource(R.drawable.ic_tick_circle);
                }
            }
        });

        master_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (master_customer_status == 0) {
                    master_customer_status = 1;
                    master_customer_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    master_customer_status = 0;
                    master_customer_click.setImageResource(R.drawable.ic_tick_circle);
                }
            }
        });

        master_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (master_user_status == 0) {
                    master_user_status = 1;
                    master_user_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    master_user_status = 0;
                    master_user_click.setImageResource(R.drawable.ic_tick_circle);
                }
            }
        });

        transaction_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (transaction_customer_status == 0) {
                    transaction_customer_status = 1;
                    transaction_customer_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    transaction_customer_status = 0;
                    transaction_customer_click.setImageResource(R.drawable.ic_tick_circle);
                }
            }
        });

        transaction_sales_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (transaction_sales_order_status == 0) {
                    transaction_sales_order_status = 1;
                    transaction_sales_order_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    transaction_sales_order_status = 0;
                    transaction_sales_order_click.setImageResource(R.drawable.ic_tick_circle);
                }
            }
        });

        transaction_sales_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (transaction_sales_invoice_status == 0) {
                    transaction_sales_invoice_status = 1;
                    transaction_sales_invoice_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    transaction_sales_invoice_status = 0;
                    transaction_sales_invoice_click.setImageResource(R.drawable.ic_tick_circle);
                }
            }
        });


        transaction_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (transaction_payment_status == 0) {
                    transaction_payment_status = 1;
                    transaction_payment_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    transaction_payment_status = 0;
                    transaction_payment_click.setImageResource(R.drawable.ic_tick_circle);
                }
            }
        });

        transaction_transfer_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (transaction_transfer_in_status == 0) {
                    transaction_transfer_in_status = 1;
                    transaction_trans_in_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    transaction_transfer_in_status = 0;
                    transaction_trans_in_click.setImageResource(R.drawable.ic_tick_circle);
                }
            }
        });


        transaction_transfer_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (transaction_transfer_out_status == 0) {
                    transaction_transfer_out_status = 1;
                    transaction_trans_out_click.setImageResource(R.drawable.ic_tick_done);

                } else {
                    transaction_transfer_out_status = 0;
                    transaction_trans_out_click.setImageResource(R.drawable.ic_tick_circle);
                }
            }
        });


        sync_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Call<Authentication_Response> authentication_responseCall = userInterface.authentication(db.getApiUsername(), db.getApiPassword());
                authentication_responseCall.enqueue(new Callback<Authentication_Response>() {
                    @Override
                    public void onResponse(Call<Authentication_Response> call, Response<Authentication_Response> response) {
                        Log.i("AUTH_RESPONSE", "<==" + response.code());
                        Log.i("AUTH_RESPONSE", "<==" + response.message());
                        Log.i("AUTH_RESPONSE", "<==" + response.errorBody());
                        Log.i("AUTH_RESPONSE", "<==" + response.headers());
                        Utility.dismissProgressDialog();
                        if (response.code() == 200) {

                            Authentication_Response authentication_response = response.body();
                            Log.i("AUTH_RESPONSE", "<==" + authentication_response.getMessage());
                            Log.i("AUTH_RESPONSE", "<==" + authentication_response.getFullName());

                            HashMap cookieHashmap = new HashMap<String, String>();
                            Headers headers = response.headers();
                            List<String> cookies = headers.values("Set-Cookie");


                            for (String cookie : cookies) {

                                int index1 = cookie.indexOf("=");
                                int index2 = cookie.indexOf(";");

                                String key = cookie.substring(0, index1);
                                String value = cookie.substring((index1 + 1), index2);

                                cookieHashmap.put(key, value);
                            }

                            String cookie = "user_id=" + cookieHashmap.get("user_id") + ";full_name=" + cookieHashmap.get("full_name") + ";sid=" + cookieHashmap.get("sid");
                            Utility.setPrefs("vansale_cookie", cookie, HomeActivity.this);


                            if (master_click_status == 1) {

                                if (master_warehouse_status == 1) {

                                }

                                if (master_currency_status == 1) {

                                }

                                if (master_item_status == 1) {
                                    itemListDataFromServer();
                                }

                                if (master_price_list_status == 1) {

                                }

                                if (master_payment_status == 1) {
                                    modePaymentDataFromServer();
                                }

                                if (master_customer_status == 1) {

                                    customerListDataFromServer();

                                }

                                if (master_user_status == 1) {

                                }


                                if (master_warehouse_status == 0 &&
                                        master_currency_status == 0 &&
                                        master_item_status == 0 &&
                                        master_price_list_status == 0 &&
                                        master_payment_status == 0 &&
                                        master_customer_status == 0 &&
                                        master_user_status == 0) {

                                    Utility.showDialog(HomeActivity.this, "WARNING !", "Selection Required !", R.color.dialog_network_error_background);
                                }


                            } else if (transaction_click_status == 1) {

                                if (transaction_customer_status == 1) {
                                    syncCustomerDataFromLocalToServer();
                                }

                                if (transaction_sales_order_status == 1) {

                                    syncSalesOrderFromLocalToServer();

                                }

                                if (transaction_sales_invoice_status == 1) {

                                    try {


                                        String last_sync_time = db.getLastSyncTime();

                                        if (last_sync_time != null && !last_sync_time.isEmpty()) {

                                            List<String> invoice_pending = db.getSalesInvoicePendingAfterSync(last_sync_time);
                                            if (invoice_pending.size() > 0) {

                                                si_pending = invoice_pending.size();

                                                for (final String inv_doc_no : invoice_pending) {

                                                    Call<InvoiceNameId_TokenResponse> invoiceNameId_tokenResponseCall = userInterface.getInvoiceName(cookie, storedUrl + "/api/resource/Sales Invoice?filters=[[\"Sales Invoice\",\"doc_no\",\"=\",\"" + inv_doc_no + "\"],[\"Sales Invoice\",\"docstatus\",\"=\",\"1\"]]&limit_page_length=100000");
                                                    invoiceNameId_tokenResponseCall.enqueue(new Callback<InvoiceNameId_TokenResponse>() {
                                                        @Override
                                                        public void onResponse(Call<InvoiceNameId_TokenResponse> call, Response<InvoiceNameId_TokenResponse> response) {
                                                            Log.i("INVOICE_NAME", "<==" + response.code());
                                                            Log.i("INVOICE_NAME", "<==" + response.message());

                                                            if (response.code() == 200) {
                                                                si_pending--;

                                                                InvoiceNameId_TokenResponse id_tokenResponse = response.body();
                                                                List<InvoiceNameIdData> data = id_tokenResponse.getData();
                                                                if (data.size() > 0) {
                                                                    db.updateSalesInvoiceSyncStatusWithDocNo(inv_doc_no);

                                                                }
                                                                if (si_pending == 0)
                                                                    CallInvoiceSync();
                                                            }


                                                        }

                                                        public void CallInvoiceSync() {
                                                            syncSalesInvoiceFromLocalToServerNew();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<InvoiceNameId_TokenResponse> call, Throwable t) {

                                                        }
                                                    });
                                                }
                                            } else {
                                                syncSalesInvoiceFromLocalToServerNew();
                                            }

                                        } else
                                            syncSalesInvoiceFromLocalToServerNew();
                                        // if(db.IsCustomerVisitNoSaleSync()&&!isSalesSyncProgress)
                                        // syncCustomerVisitNoSaleFromLocalToServer();
                                    }catch (Exception ex){
                                        Utility.showDialog(HomeActivity.this, "ERROR !", ex.getMessage(), R.color.dialog_network_error_background);
                                    }
                                }


                                if (transaction_payment_status == 1) {
                                    syncPaymentFromLocalToServer();
                                }


                                if (transaction_transfer_in_status == 1) {

                                }

                                if (transaction_transfer_out_status == 1) {

                                }

                                if (transaction_customer_status == 0 &&
                                        transaction_sales_order_status == 0 &&
                                        transaction_sales_invoice_status == 0 &&
                                        transaction_payment_status == 0 &&
                                        transaction_transfer_in_status == 0 &&
                                        transaction_transfer_out_status == 0
                                        ) {

                                    Utility.showDialog(HomeActivity.this, "WARNING !", "Selection Required !", R.color.dialog_network_error_background);
                                }


                            } else {

                                Utility.showDialog(HomeActivity.this, "WARNING !", "Select One !", R.color.dialog_network_error_background);
                            }


                            alertDialog.dismiss();


                        }


                    }

                    @Override
                    public void onFailure(Call<Authentication_Response> call, Throwable t) {
                        Utility.dismissProgressDialog();
                        Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);
                        alertDialog.dismiss();
                    }
                });


                alertDialog.dismiss();


            }
        });


        cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

            }
        });


        master_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                master_click_image.setImageResource(R.drawable.ic_tick_done_blue);
                transaction_click_image.setImageResource(R.drawable.ic_circle_fill);
                master_data.setVisibility(View.VISIBLE);
                transaction_data.setVisibility(View.GONE);
                master_click_status = 1;
                transaction_click_status = 0;

                master_warehouse_status = 1;
                master_currency_status = 1;
                master_item_status = 1;
                master_price_list_status = 1;
                master_payment_status = 1;
                master_customer_status = 1;
                master_user_status = 1;


                warehouse_click.setImageResource(R.drawable.ic_tick_done);
                currency_click.setImageResource(R.drawable.ic_tick_done);
                item_click.setImageResource(R.drawable.ic_tick_done);
                price_list_click.setImageResource(R.drawable.ic_tick_done);
                master_payment_click.setImageResource(R.drawable.ic_tick_done);
                master_customer_click.setImageResource(R.drawable.ic_tick_done);
                master_user_click.setImageResource(R.drawable.ic_tick_done);


            }
        });


        transaction_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                master_click_image.setImageResource(R.drawable.ic_circle_fill);
                transaction_click_image.setImageResource(R.drawable.ic_tick_done_blue);
                master_data.setVisibility(View.GONE);
                transaction_data.setVisibility(View.VISIBLE);
                master_click_status = 0;
                transaction_click_status = 1;

                transaction_customer_status = 1;
                transaction_sales_order_status = 1;
                transaction_sales_invoice_status = 1;
                transaction_payment_status = 1;
                transaction_transfer_in_status = 1;
                transaction_transfer_out_status = 1;


                transaction_customer_click.setImageResource(R.drawable.ic_tick_done);
                transaction_sales_order_click.setImageResource(R.drawable.ic_tick_done);
                transaction_sales_invoice_click.setImageResource(R.drawable.ic_tick_done);
                transaction_payment_click.setImageResource(R.drawable.ic_tick_done);
                transaction_trans_in_click.setImageResource(R.drawable.ic_tick_done);
                transaction_trans_out_click.setImageResource(R.drawable.ic_tick_done);


            }
        });


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

       /* Toast t = Utility.setToast(HomeActivity.this, Utility.TOAST_COMING_SOON);
        t.show();*/
    }

    private void syncPaymentFromLocalToServer() {

        try{
        List<Payment> getPaymentList = db.getPaymentList();

        py_data = getPaymentList.size();

        if (!getPaymentList.isEmpty()) {

            for (final Payment p : getPaymentList) {

                py_data = py_data - 1;

                paymentRaw_tokenResponse = new PaymentRaw_TokenResponse();

                //paymentRaw_tokenResponse.setOwner(db.getApiUsername());
                paymentRaw_tokenResponse.setDocstatus(1);
                paymentRaw_tokenResponse.setReceipt_no(p.getPAYMENT_DOC_NO());
                paymentRaw_tokenResponse.setCreation(p.getPAYMENT_CREATION());
                paymentRaw_tokenResponse.setPosting_date(p.getPAYMENT_CREATION().split(" ")[0]);
                //paymentRaw_tokenResponse.setNaming_series(db.getPaymentName());
                paymentRaw_tokenResponse.setCompany(db.getCompanyName());
                paymentRaw_tokenResponse.setPayment_type("Receive");
                paymentRaw_tokenResponse.setMode_of_payment(p.getMODE_OF_PAYMENT());
                paymentRaw_tokenResponse.setParty_type("Customer");
                paymentRaw_tokenResponse.setParty(p.getPAYMENT_CUSTOMER());
                paymentRaw_tokenResponse.setPaid_amount(Double.valueOf(p.getRECEIVED_AMOUNT()));
                paymentRaw_tokenResponse.setReceived_amount(Double.valueOf(p.getRECEIVED_AMOUNT()));
                paymentRaw_tokenResponse.setAllocate_payment_amount(Double.valueOf(p.getRECEIVED_AMOUNT()));
                paymentRaw_tokenResponse.setReference_no(p.getPAYMENT_REFERENCE_NO());
                paymentRaw_tokenResponse.setReference_date(p.getPAYMENT_REFERENCE_DATE());
                paymentRaw_tokenResponse.setPaid_from(db.getPAID_FROMFromSetting());
                paymentRaw_tokenResponse.setPaid_to(p.getPAYMENT_PAID_TO());
                paymentRaw_tokenResponse.setWarehouse(db.getWarehouse());
                paymentRaw_tokenResponse.setOwner(p.getPAYMENT_OWNER());
                paymentRaw_tokenResponse.setModified_by(p.getPAYMENT_OWNER());
                paymentRaw_tokenResponse.setPosting_time(p.getPAYMENT_POSTING_TIME());
               // paymentRaw_tokenResponse.setPosting_time(p.getPAYMENT_CREATION().split(" ")[1]);

                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getOwner());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getDocstatus());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getReceipt_no());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getCreation());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getPosting_date());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getPosting_time());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getNaming_series());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getCompany());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getPayment_type());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getMode_of_payment());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getParty_type());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getParty());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getPaid_amount());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getReceived_amount());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getAllocate_payment_amount());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getReference_no());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getReference_date());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getPaid_from());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getPaid_to());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getWarehouse());

                Call<PaymentSync_TokenResponse> tokenResponseCall = userInterface.paymentPost(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Payment Entry", paymentRaw_tokenResponse);
                tokenResponseCall.enqueue(new Callback<PaymentSync_TokenResponse>() {
                    @Override
                    public void onResponse(Call<PaymentSync_TokenResponse> call, Response<PaymentSync_TokenResponse> response) {
                        Log.i("PAYMENT_RESPONSE", "<==" + response.code());
                        Log.i("PAYMENT_RESPONSE", "<==" + response.message());
                        Log.i("PAYMENT_RESPONSE", "<==" + response.errorBody());
                        Log.i("PAYMENT_RESPONSE", "<==" + response.headers());

                        if (response.code() == 200) {

                            db.updatePaymentSyncStatus(String.valueOf(p.getKEY_ID()));
                            syncMsg();

                        } else if (response.code() == 403 || response.code() == 500|| response.code() == 417) {
                            Utility.showDialog(HomeActivity.this, "ERROR !", response.message()+"!\n Try Again...", R.color.dialog_error_background);

                        } else {

                        }

                    }

                    private void syncMsg() {

                        if (py_data == 0) {

                            Toast t = Utility.setToast(HomeActivity.this, "Payment Sync Success");
                            t.show();

                        }


                    }

                    @Override
                    public void onFailure(Call<PaymentSync_TokenResponse> call, Throwable t) {
                        Log.i("FAILURE", "<==" + t.getMessage());
                        Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);
                    }
                });


            }
        } else {
            Toast t = Utility.setToast(HomeActivity.this, "No New Payment Data");
            t.show();
        }
        }catch (Exception ex){
            Toast t = Utility.setToast(HomeActivity.this, ex.getMessage());
            t.show();
        }

    }
    private String getInvoiceNoFromServer(String DocNo) {

        try{
            Call<InvoiceNameId_TokenResponse> invoiceNameId_tokenResponseCall = userInterface.getInvoiceName(Utility.getPrefs("vansale_cookie", HomeActivity.this), "/api/resource/Sales%20Invoice/?filters=%5B%5B%22Sales%20Invoice%22%2C%22doc_no%22%2C%22%3D%22%2C%22"+DocNo+"%22%5D%5D");
            invoiceNameId_tokenResponseCall.enqueue(new Callback<InvoiceNameId_TokenResponse>() {
                public void onResponse(Call<InvoiceNameId_TokenResponse > call, Response<InvoiceNameId_TokenResponse> response)
                {
                    Log.i("INVOICE_NAME", "<==" + response.code());
                    Log.i("INVOICE_NAME", "<==" + response.message());

                    if (response.code() == 200) {

                        InvoiceNameId_TokenResponse id_tokenResponse = response.body();
                        List<InvoiceNameIdData> data = id_tokenResponse.getData();

                        if(!data.isEmpty()) {

                            InvoiceNameIdData ni = data.get(0);
                            InvoiceNo =ni.getName();
                        }}}
                            @Override
                            public void onFailure(Call<InvoiceNameId_TokenResponse> call, Throwable t) {

                            }
                        });

        }catch (Exception e){
            Utility.showDialog(HomeActivity.this, "ERROR !", e.getMessage()+"\n Try Again...", R.color.dialog_error_background);
        }
        return InvoiceNo;
    }//not in use
    private void syncCustomerVisitFromLocalToServer1() {

        try{
            CustomerVisitRaw_TokenResponse visitClass;
            List<CustomerVisitLog> customerVisitLogForSync = db.getCustomerVisitLogForSync();

            si_data=customerVisitLogForSync.size();
            if(!customerVisitLogForSync.isEmpty()){
                for (final CustomerVisitLog cv : customerVisitLogForSync) {

                    String InvoiceNo="";
                    String docNo=cv.getCUSTOMER_VISIT_REFERENCE();
                    if(!docNo.equals("")){
                        InvoiceNo=getInvoiceNoFromServer(docNo);}
                    visitClass = new CustomerVisitRaw_TokenResponse();
                    visitClass.setReference(InvoiceNo);
                    visitClass.setDoc_no(cv.getCUSTOMER_VISIT_REFERENCE());
                    visitClass.setNaming_series(cv.getCUSTOMER_VISIT_NAMING_SERIES());
                    visitClass.setCreation(cv.getCUSTOMER_VISIT_CREATION());
                    visitClass.setOwner(cv.getCUSTOMER_VISIT_OWNER());
                    visitClass.setModified_by(cv.getCUSTOMER_VISIT_MODIFIED_BY());
                    visitClass.setLatitude(cv.getCUSTOMER_VISIT_LATITUDE());
                    visitClass.setDocstatus(cv.getCUSTOMER_VISIT_DOC_STATUS());
                    visitClass.setComments(cv.getCUSTOMER_VISIT_COMMENTS());
                    visitClass.setCustomer(cv.getCUSTOMER_VISIT_CUSTOMER());
                    visitClass.setAmount(cv.getCUSTOMER_VISIT_AMOUNT());
                    visitClass.setVisit_result(cv.getCUSTOMER_VISIT_VISIT_RESULT());
                    visitClass.setModified(cv.getCUSTOMER_VISIT_MODIFIED());
                    visitClass.setLongitude(cv.getCUSTOMER_VISIT_LONGITUDE());
                    visitClass.setVisit_date(cv.getCUSTOMER_VISIT_VISIT_DATE());
                    visitClass.setSales_person(cv.getCUSTOMER_VISIT_SALES_PERSON());
                    visitClass.setIdx(cv.getCUTOMER_VISIT_IDX());
                    si_data = si_data - 1;


                    Call<CustomerVisitRaw_TokenResponse> customerVisitRaw_tokenResponseCall = userInterface.CustomerVisitPost(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Customer%20Visit%20Log", visitClass);
                    customerVisitRaw_tokenResponseCall.enqueue(new Callback<CustomerVisitRaw_TokenResponse>() {
                        @Override
                        public void onResponse(Call<CustomerVisitRaw_TokenResponse> call, Response<CustomerVisitRaw_TokenResponse> response) {

                            Log.i("INVOICE_RESPONSE", "<==" + response.code());
                            Log.i("INVOICE_RESPONSE", "<==" + response.message());


                            if (response.code() == 200) {

                                db.updateCustomerVisitSyncStatus(String.valueOf(cv.getCUSTOMER_VISIT_KEY_ID()));
                                syncMsg();
                            } else if (response.code() == 403 || response.code() == 500) {
                                //Utility.dismissProgressDialog();

                                Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                            } else {
                                // Utility.dismissProgressDialog();

                            }


                        }

                        private void syncMsg() {

                            if (si_data == 0) {

                                Toast t = Utility.setToast(HomeActivity.this, "Customer Visit Sync Success");
                                t.show();

                            }

                        }

                        @Override
                        public void onFailure(Call<CustomerVisitRaw_TokenResponse> call, Throwable t) {
                            Utility.dismissProgressDialog();
                            Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

                        }
                    });
                }


            }


        }catch (Exception e){
            Utility.showDialog(HomeActivity.this, "ERROR !", e.getMessage()+"\n Try Again...", R.color.dialog_error_background);
        }

    }//not in use
    private void syncCustomerVisitFromLocalToServer(){

        try{
            CustomerVisitRaw_TokenResponse visitClass;
            List<CustomerVisitLog> customerVisitLogForSync = db.getCustomerVisitLogForSync();

            si_data=customerVisitLogForSync.size();
            if(!customerVisitLogForSync.isEmpty()) {
                for (final CustomerVisitLog cv : customerVisitLogForSync) {

                    String docNo = cv.getCUSTOMER_VISIT_REFERENCE();
                    if (!docNo.equals("")) {

                        Call<InvoiceNameId_TokenResponse> invoiceNameId_tokenResponseCall = userInterface.getInvoiceName(Utility.getPrefs("vansale_cookie", HomeActivity.this), "/api/resource/Sales%20Invoice/?filters=%5B%5B%22Sales%20Invoice%22%2C%22doc_no%22%2C%22%3D%22%2C%22"+docNo+"%22%5D%5D");
                        invoiceNameId_tokenResponseCall.enqueue(new Callback<InvoiceNameId_TokenResponse>() {
                            public void onResponse(Call<InvoiceNameId_TokenResponse > call, Response<InvoiceNameId_TokenResponse> response)
                            {
                                Log.i("INVOICE_NAME", "<==" + response.code());
                                Log.i("INVOICE_NAME", "<==" + response.message());

                                if (response.code() == 200) {

                                    InvoiceNameId_TokenResponse id_tokenResponse = response.body();
                                    List<InvoiceNameIdData> data = id_tokenResponse.getData();

                                    if(!data.isEmpty()) {

                                        InvoiceNameIdData ni = data.get(0);
                                        String InvoiceNo =ni.getName();
                                        CustomerVisitRaw_TokenResponse visitClass = new CustomerVisitRaw_TokenResponse();
                                        visitClass.setReference(InvoiceNo);
                                        visitClass.setDoc_no(cv.getCUSTOMER_VISIT_REFERENCE());
                                        visitClass.setNaming_series(cv.getCUSTOMER_VISIT_NAMING_SERIES());
                                        visitClass.setCreation(cv.getCUSTOMER_VISIT_CREATION());
                                        visitClass.setOwner(cv.getCUSTOMER_VISIT_OWNER());
                                        visitClass.setModified_by(cv.getCUSTOMER_VISIT_MODIFIED_BY());
                                        visitClass.setLatitude(cv.getCUSTOMER_VISIT_LATITUDE());
                                        visitClass.setDocstatus(cv.getCUSTOMER_VISIT_DOC_STATUS());
                                        visitClass.setComments(cv.getCUSTOMER_VISIT_COMMENTS());
                                        visitClass.setCustomer(cv.getCUSTOMER_VISIT_CUSTOMER());
                                        visitClass.setAmount(cv.getCUSTOMER_VISIT_AMOUNT());
                                        visitClass.setVisit_result(cv.getCUSTOMER_VISIT_VISIT_RESULT());
                                        visitClass.setModified(cv.getCUSTOMER_VISIT_MODIFIED());
                                        visitClass.setLongitude(cv.getCUSTOMER_VISIT_LONGITUDE());
                                        visitClass.setVisit_date(cv.getCUSTOMER_VISIT_VISIT_DATE());
                                        visitClass.setSales_person(cv.getCUSTOMER_VISIT_SALES_PERSON());
                                        visitClass.setIdx(cv.getCUTOMER_VISIT_IDX());
                                        si_data = si_data - 1;


                                        Call<CustomerVisitRaw_TokenResponse> customerVisitRaw_tokenResponseCall = userInterface.CustomerVisitPost(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Customer%20Visit%20Log", visitClass);
                                        customerVisitRaw_tokenResponseCall.enqueue(new Callback<CustomerVisitRaw_TokenResponse>() {
                                            @Override
                                            public void onResponse(Call<CustomerVisitRaw_TokenResponse> call, Response<CustomerVisitRaw_TokenResponse> response) {

                                                Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                                Log.i("INVOICE_RESPONSE", "<==" + response.message());


                                                if (response.code() == 200) {

                                                    db.updateCustomerVisitSyncStatus(String.valueOf(cv.getCUSTOMER_VISIT_KEY_ID()));
                                                    syncMsg();
                                                } else if (response.code() == 403 || response.code() == 500) {
                                                    //Utility.dismissProgressDialog();

                                                    Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                                } else {
                                                    // Utility.dismissProgressDialog();

                                                }


                                            }

                                            private void syncMsg() {

                                                if (si_data == 0) {

                                                    Toast t = Utility.setToast(HomeActivity.this, "Customer Visit Sync Success");
                                                    t.show();

                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<CustomerVisitRaw_TokenResponse> call, Throwable t) {
                                                Utility.dismissProgressDialog();
                                              //  Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

                                            }
                                        });

                                    }}}
                            @Override
                            public void onFailure(Call<InvoiceNameId_TokenResponse> call, Throwable t) {
                                Utility.dismissProgressDialog();
                            }
                        });

                    }
                    else
                    {
                        visitClass = new CustomerVisitRaw_TokenResponse();
                        visitClass.setReference("");
                        visitClass.setDoc_no(cv.getCUSTOMER_VISIT_REFERENCE());
                        visitClass.setNaming_series(cv.getCUSTOMER_VISIT_NAMING_SERIES());
                        visitClass.setCreation(cv.getCUSTOMER_VISIT_CREATION());
                        visitClass.setOwner(cv.getCUSTOMER_VISIT_OWNER());
                        visitClass.setModified_by(cv.getCUSTOMER_VISIT_MODIFIED_BY());
                        visitClass.setLatitude(cv.getCUSTOMER_VISIT_LATITUDE());
                        visitClass.setDocstatus(cv.getCUSTOMER_VISIT_DOC_STATUS());
                        visitClass.setComments(cv.getCUSTOMER_VISIT_COMMENTS());
                        visitClass.setCustomer(cv.getCUSTOMER_VISIT_CUSTOMER());
                        visitClass.setAmount(cv.getCUSTOMER_VISIT_AMOUNT());
                        visitClass.setVisit_result(cv.getCUSTOMER_VISIT_VISIT_RESULT());
                        visitClass.setModified(cv.getCUSTOMER_VISIT_MODIFIED());
                        visitClass.setLongitude(cv.getCUSTOMER_VISIT_LONGITUDE());
                        visitClass.setVisit_date(cv.getCUSTOMER_VISIT_VISIT_DATE());
                        visitClass.setSales_person(cv.getCUSTOMER_VISIT_SALES_PERSON());
                        visitClass.setIdx(cv.getCUTOMER_VISIT_IDX());
                        si_data = si_data - 1;


                        Call<CustomerVisitRaw_TokenResponse> customerVisitRaw_tokenResponseCall = userInterface.CustomerVisitPost(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Customer%20Visit%20Log", visitClass);
                        customerVisitRaw_tokenResponseCall.enqueue(new Callback<CustomerVisitRaw_TokenResponse>() {
                            @Override
                            public void onResponse(Call<CustomerVisitRaw_TokenResponse> call, Response<CustomerVisitRaw_TokenResponse> response) {

                                Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                Log.i("INVOICE_RESPONSE", "<==" + response.message());


                                if (response.code() == 200) {

                                    db.updateCustomerVisitSyncStatus(String.valueOf(cv.getCUSTOMER_VISIT_KEY_ID()));
                                    syncMsg();
                                } else if (response.code() == 403 || response.code() == 500) {
                                    //Utility.dismissProgressDialog();

                                    Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                } else {
                                    // Utility.dismissProgressDialog();

                                }


                            }

                            private void syncMsg() {

                                if (si_data == 0) {

                                    Toast t = Utility.setToast(HomeActivity.this, "Customer Visit Sync Success");
                                    t.show();

                                }

                            }

                            @Override
                            public void onFailure(Call<CustomerVisitRaw_TokenResponse> call, Throwable t) {
                                Utility.dismissProgressDialog();
                                Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

                            }
                        });

                    }
                }
            }

        }catch (Exception e){
            Utility.showDialog(HomeActivity.this, "ERROR !", e.getMessage()+"\n Try Again...", R.color.dialog_error_background);
        }
    }
    private void syncAllCustomerVisitFromLocalToServer(){

        try{
            CustomerVisitRaw_TokenResponse visitClass;
            List<CustomerVisitLog> customerVisitLogForSync = db.getCustomerVisitLogForSync();

            cv_data=customerVisitLogForSync.size();
            if(!customerVisitLogForSync.isEmpty()) {
                for (final CustomerVisitLog cv : customerVisitLogForSync) {

                        String docNo = cv.getCUSTOMER_VISIT_REFERENCE();
                        visitClass = new CustomerVisitRaw_TokenResponse();
                        visitClass.setReference("");
                        visitClass.setDoc_no(cv.getCUSTOMER_VISIT_REFERENCE());
                        visitClass.setNaming_series(cv.getCUSTOMER_VISIT_NAMING_SERIES());
                        visitClass.setCreation(cv.getCUSTOMER_VISIT_CREATION());
                        visitClass.setOwner(cv.getCUSTOMER_VISIT_OWNER());
                        visitClass.setModified_by(cv.getCUSTOMER_VISIT_MODIFIED_BY());
                        visitClass.setLatitude(cv.getCUSTOMER_VISIT_LATITUDE());
                        visitClass.setDocstatus(cv.getCUSTOMER_VISIT_DOC_STATUS());
                        visitClass.setComments(cv.getCUSTOMER_VISIT_COMMENTS());
                        visitClass.setCustomer(cv.getCUSTOMER_VISIT_CUSTOMER());
                        visitClass.setAmount(cv.getCUSTOMER_VISIT_AMOUNT());
                        visitClass.setVisit_result(cv.getCUSTOMER_VISIT_VISIT_RESULT());
                        visitClass.setModified(cv.getCUSTOMER_VISIT_MODIFIED());
                        visitClass.setLongitude(cv.getCUSTOMER_VISIT_LONGITUDE());
                        visitClass.setVisit_date(cv.getCUSTOMER_VISIT_VISIT_DATE());
                        visitClass.setSales_person(cv.getCUSTOMER_VISIT_SALES_PERSON());
                        visitClass.setIdx(cv.getCUTOMER_VISIT_IDX());
                    cv_data = cv_data - 1;


                        Call<CustomerVisitRaw_TokenResponse> customerVisitRaw_tokenResponseCall = userInterface.CustomerVisitPost(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Customer%20Visit%20Log", visitClass);
                        customerVisitRaw_tokenResponseCall.enqueue(new Callback<CustomerVisitRaw_TokenResponse>() {
                            @Override
                            public void onResponse(Call<CustomerVisitRaw_TokenResponse> call, Response<CustomerVisitRaw_TokenResponse> response) {

                                Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                Log.i("INVOICE_RESPONSE", "<==" + response.message());


                                if (response.code() == 200) {

                                    db.updateCustomerVisitSyncStatus(String.valueOf(cv.getCUSTOMER_VISIT_KEY_ID()));
                                    syncMsg();
                                } else if (response.code() == 403 || response.code() == 500) {
                                    //Utility.dismissProgressDialog();

                                    Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                } else {
                                    // Utility.dismissProgressDialog();

                                }


                            }

                            private void syncMsg() {

                                if (cv_data == 0) {

                                    Toast t = Utility.setToast(HomeActivity.this, "Customer Visit Sync Success");
                                    t.show();

                                }

                            }

                            @Override
                            public void onFailure(Call<CustomerVisitRaw_TokenResponse> call, Throwable t) {
                                Utility.dismissProgressDialog();
                                Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

                            }
                        });


                }
            }

        }catch (Exception e){
            Utility.showDialog(HomeActivity.this, "ERROR !", e.getMessage()+"\n Try Again...", R.color.dialog_error_background);
        }
    }
    private void syncCustomerVisitNoSaleFromLocalToServer(){

        try{
            CustomerVisitRaw_TokenResponse visitClass;
            List<CustomerVisitLog> customerVisitLogForSync = db.getCustomerVisitLogNoSaleForSync();

            cv_data1=customerVisitLogForSync.size();
            if(!customerVisitLogForSync.isEmpty()) {
                for (final CustomerVisitLog cv : customerVisitLogForSync) {

                    String docNo = cv.getCUSTOMER_VISIT_REFERENCE();
                    if (!docNo.equals("")) {

                        Call<InvoiceNameId_TokenResponse> invoiceNameId_tokenResponseCall = userInterface.getInvoiceName(Utility.getPrefs("vansale_cookie", HomeActivity.this), "/api/resource/Sales%20Invoice/?filters=%5B%5B%22Sales%20Invoice%22%2C%22doc_no%22%2C%22%3D%22%2C%22"+docNo+"%22%5D%5D");
                        invoiceNameId_tokenResponseCall.enqueue(new Callback<InvoiceNameId_TokenResponse>() {
                            public void onResponse(Call<InvoiceNameId_TokenResponse > call, Response<InvoiceNameId_TokenResponse> response)
                            {
                                Log.i("INVOICE_NAME", "<==" + response.code());
                                Log.i("INVOICE_NAME", "<==" + response.message());

                                if (response.code() == 200) {

                                    InvoiceNameId_TokenResponse id_tokenResponse = response.body();
                                    List<InvoiceNameIdData> data = id_tokenResponse.getData();

                                    if(!data.isEmpty()) {

                                        InvoiceNameIdData ni = data.get(0);
                                        String InvoiceNo =ni.getName();
                                        CustomerVisitRaw_TokenResponse visitClass = new CustomerVisitRaw_TokenResponse();
                                        visitClass.setReference(InvoiceNo);
                                        visitClass.setDoc_no(cv.getCUSTOMER_VISIT_REFERENCE());
                                        visitClass.setNaming_series(cv.getCUSTOMER_VISIT_NAMING_SERIES());
                                        visitClass.setCreation(cv.getCUSTOMER_VISIT_CREATION());
                                        visitClass.setOwner(cv.getCUSTOMER_VISIT_OWNER());
                                        visitClass.setModified_by(cv.getCUSTOMER_VISIT_MODIFIED_BY());
                                        visitClass.setLatitude(cv.getCUSTOMER_VISIT_LATITUDE());
                                        visitClass.setDocstatus(cv.getCUSTOMER_VISIT_DOC_STATUS());
                                        visitClass.setComments(cv.getCUSTOMER_VISIT_COMMENTS());
                                        visitClass.setCustomer(cv.getCUSTOMER_VISIT_CUSTOMER());
                                        visitClass.setAmount(cv.getCUSTOMER_VISIT_AMOUNT());
                                        visitClass.setVisit_result(cv.getCUSTOMER_VISIT_VISIT_RESULT());
                                        visitClass.setModified(cv.getCUSTOMER_VISIT_MODIFIED());
                                        visitClass.setLongitude(cv.getCUSTOMER_VISIT_LONGITUDE());
                                        visitClass.setVisit_date(cv.getCUSTOMER_VISIT_VISIT_DATE());
                                        visitClass.setSales_person(cv.getCUSTOMER_VISIT_SALES_PERSON());
                                        visitClass.setIdx(cv.getCUTOMER_VISIT_IDX());
                                        cv_data1 = cv_data1 - 1;


                                        Call<CustomerVisitRaw_TokenResponse> customerVisitRaw_tokenResponseCall = userInterface.CustomerVisitPost(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Customer%20Visit%20Log", visitClass);
                                        customerVisitRaw_tokenResponseCall.enqueue(new Callback<CustomerVisitRaw_TokenResponse>() {
                                            @Override
                                            public void onResponse(Call<CustomerVisitRaw_TokenResponse> call, Response<CustomerVisitRaw_TokenResponse> response) {

                                                Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                                Log.i("INVOICE_RESPONSE", "<==" + response.message());


                                                if (response.code() == 200) {

                                                    db.updateCustomerVisitSyncStatus(String.valueOf(cv.getCUSTOMER_VISIT_KEY_ID()));
                                                    syncMsg();
                                                } else if (response.code() == 403 || response.code() == 500) {
                                                    //Utility.dismissProgressDialog();

                                                    Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                                } else {
                                                    // Utility.dismissProgressDialog();

                                                }


                                            }

                                            private void syncMsg() {

                                                if (cv_data1 == 0) {

                                                    Toast t = Utility.setToast(HomeActivity.this, "Customer Visit Sync Success");
                                                    t.show();

                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<CustomerVisitRaw_TokenResponse> call, Throwable t) {
                                                Utility.dismissProgressDialog();
                                                //  Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

                                            }
                                        });

                                    }}}
                            @Override
                            public void onFailure(Call<InvoiceNameId_TokenResponse> call, Throwable t) {
                                Utility.dismissProgressDialog();
                            }
                        });

                    }
                    else
                    {
                        visitClass = new CustomerVisitRaw_TokenResponse();
                        visitClass.setReference("");
                        visitClass.setDoc_no(cv.getCUSTOMER_VISIT_REFERENCE());
                        visitClass.setNaming_series(cv.getCUSTOMER_VISIT_NAMING_SERIES());
                        visitClass.setCreation(cv.getCUSTOMER_VISIT_CREATION());
                        visitClass.setOwner(cv.getCUSTOMER_VISIT_OWNER());
                        visitClass.setModified_by(cv.getCUSTOMER_VISIT_MODIFIED_BY());
                        visitClass.setLatitude(cv.getCUSTOMER_VISIT_LATITUDE());
                        visitClass.setDocstatus(cv.getCUSTOMER_VISIT_DOC_STATUS());
                        visitClass.setComments(cv.getCUSTOMER_VISIT_COMMENTS());
                        visitClass.setCustomer(cv.getCUSTOMER_VISIT_CUSTOMER());
                        visitClass.setAmount(cv.getCUSTOMER_VISIT_AMOUNT());
                        visitClass.setVisit_result(cv.getCUSTOMER_VISIT_VISIT_RESULT());
                        visitClass.setModified(cv.getCUSTOMER_VISIT_MODIFIED());
                        visitClass.setLongitude(cv.getCUSTOMER_VISIT_LONGITUDE());
                        visitClass.setVisit_date(cv.getCUSTOMER_VISIT_VISIT_DATE());
                        visitClass.setSales_person(cv.getCUSTOMER_VISIT_SALES_PERSON());
                        visitClass.setIdx(cv.getCUTOMER_VISIT_IDX());
                        si_data = si_data - 1;


                        Call<CustomerVisitRaw_TokenResponse> customerVisitRaw_tokenResponseCall = userInterface.CustomerVisitPost(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Customer%20Visit%20Log", visitClass);
                        customerVisitRaw_tokenResponseCall.enqueue(new Callback<CustomerVisitRaw_TokenResponse>() {
                            @Override
                            public void onResponse(Call<CustomerVisitRaw_TokenResponse> call, Response<CustomerVisitRaw_TokenResponse> response) {

                                Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                Log.i("INVOICE_RESPONSE", "<==" + response.message());


                                if (response.code() == 200) {

                                    db.updateCustomerVisitSyncStatus(String.valueOf(cv.getCUSTOMER_VISIT_KEY_ID()));
                                    syncMsg();
                                } else if (response.code() == 403 || response.code() == 500) {
                                    //Utility.dismissProgressDialog();

                                    Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                } else {
                                    // Utility.dismissProgressDialog();

                                }


                            }

                            private void syncMsg() {

                                if (si_data == 0) {

                                    Toast t = Utility.setToast(HomeActivity.this, "Customer Visit Sync Success");
                                    t.show();

                                }

                            }

                            @Override
                            public void onFailure(Call<CustomerVisitRaw_TokenResponse> call, Throwable t) {
                                Utility.dismissProgressDialog();
                                Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

                            }
                        });

                    }
                }
            }

        }catch (Exception e){
            Utility.showDialog(HomeActivity.this, "ERROR !", e.getMessage()+"\n Try Again...", R.color.dialog_error_background);
        }
    }//not in use
    private void syncOneCustomerVisitFromLocalToServer(final String docNo){

        try{
            CustomerVisitRaw_TokenResponse visitClass;

                        final Call<InvoiceNameId_TokenResponse> invoiceNameId_tokenResponseCall = userInterface.getInvoiceName(Utility.getPrefs("vansale_cookie", HomeActivity.this), "/api/resource/Sales%20Invoice/?filters=%5B%5B%22Sales%20Invoice%22%2C%22doc_no%22%2C%22%3D%22%2C%22"+docNo+"%22%5D%5D");
                        invoiceNameId_tokenResponseCall.enqueue(new Callback<InvoiceNameId_TokenResponse>() {
                            public void onResponse(Call<InvoiceNameId_TokenResponse > call, Response<InvoiceNameId_TokenResponse> response)
                            {
                                Log.i("INVOICE_NAME", "<==" + response.code());
                                Log.i("INVOICE_NAME", "<==" + response.message());

                                if (response.code() == 200) {
                                    final CustomerVisitLog cv = db.getOneCustomerVisitLogForSync(docNo);
                                    InvoiceNameId_TokenResponse id_tokenResponse = response.body();
                                    List<InvoiceNameIdData> data = id_tokenResponse.getData();

                                    if(!data.isEmpty()) {

                                        InvoiceNameIdData ni = data.get(0);
                                        String InvoiceNo =ni.getName();
                                        CustomerVisitRaw_TokenResponse visitClass = new CustomerVisitRaw_TokenResponse();
                                        visitClass.setReference(InvoiceNo);
                                        visitClass.setDoc_no(cv.getCUSTOMER_VISIT_REFERENCE());
                                        visitClass.setNaming_series(cv.getCUSTOMER_VISIT_NAMING_SERIES());
                                        visitClass.setCreation(cv.getCUSTOMER_VISIT_CREATION());
                                        visitClass.setOwner(cv.getCUSTOMER_VISIT_OWNER());
                                        visitClass.setModified_by(cv.getCUSTOMER_VISIT_MODIFIED_BY());
                                        visitClass.setLatitude(cv.getCUSTOMER_VISIT_LATITUDE());
                                        visitClass.setDocstatus(cv.getCUSTOMER_VISIT_DOC_STATUS());
                                        visitClass.setComments(cv.getCUSTOMER_VISIT_COMMENTS());
                                        visitClass.setCustomer(cv.getCUSTOMER_VISIT_CUSTOMER());
                                        visitClass.setAmount(cv.getCUSTOMER_VISIT_AMOUNT());
                                        visitClass.setVisit_result(cv.getCUSTOMER_VISIT_VISIT_RESULT());
                                        visitClass.setModified(cv.getCUSTOMER_VISIT_MODIFIED());
                                        visitClass.setLongitude(cv.getCUSTOMER_VISIT_LONGITUDE());
                                        visitClass.setVisit_date(cv.getCUSTOMER_VISIT_VISIT_DATE());
                                        visitClass.setSales_person(cv.getCUSTOMER_VISIT_SALES_PERSON());
                                        visitClass.setIdx(cv.getCUTOMER_VISIT_IDX());



                                        Call<CustomerVisitRaw_TokenResponse> customerVisitRaw_tokenResponseCall = userInterface.CustomerVisitPost(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Customer%20Visit%20Log", visitClass);
                                        customerVisitRaw_tokenResponseCall.enqueue(new Callback<CustomerVisitRaw_TokenResponse>() {
                                            @Override
                                            public void onResponse(Call<CustomerVisitRaw_TokenResponse> call, Response<CustomerVisitRaw_TokenResponse> response) {

                                                Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                                Log.i("INVOICE_RESPONSE", "<==" + response.message());


                                                if (response.code() == 200) {

                                                    db.updateCustomerVisitSyncStatus(String.valueOf(cv.getCUSTOMER_VISIT_KEY_ID()));
                                                    //syncMsg();
                                                } else if (response.code() == 403 || response.code() == 500) {
                                                    //Utility.dismissProgressDialog();

                                                    Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                                } else {
                                                    // Utility.dismissProgressDialog();

                                                }


                                            }

                                            private void syncMsg() {
                                                    Toast t = Utility.setToast(HomeActivity.this, "Customer Visit Sync Success");
                                                    t.show();

                                            }

                                            @Override
                                            public void onFailure(Call<CustomerVisitRaw_TokenResponse> call, Throwable t) {
                                                Utility.dismissProgressDialog();
                                                //  Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

                                            }
                                        });

                                    }}}
                            @Override
                            public void onFailure(Call<InvoiceNameId_TokenResponse> call, Throwable t) {
                                Utility.dismissProgressDialog();
                            }
                        });

        }catch (Exception e){
            Utility.showDialog(HomeActivity.this, "ERROR !", e.getMessage()+"\n Try Again...", R.color.dialog_error_background);
        }
    }

    private void syncSalesInvoiceFromLocalToServer() {

        try {
            isSalesSyncProgress=false;

            List<SalesInvoiceRaw1_TokenResponse> getSalesInvoiceForSync = db.getSalesInvoiceForSync();

            si_data = getSalesInvoiceForSync.size();

            if (!getSalesInvoiceForSync.isEmpty()) {
                isSalesSyncProgress=true;
                for (final SalesInvoiceRaw1_TokenResponse rt : getSalesInvoiceForSync) {

                    si_data = si_data - 1;

                    Sync_Tax_Rate = 0.0f;
                    Sync_Tax_Amount = 0.0f;
                    Sync_Total = 0.0f;

                    if (rt.getSALES_INVOICE_IS_POS() == 1) {

                        invoiceRawTokenResponse = new SalesInvoiceRaw_TokenResponse();

                        invoiceRawTokenResponse.setCreation(rt.getCreation());
                        invoiceRawTokenResponse.setOwner(rt.getOwner());
                        invoiceRawTokenResponse.setPrice_list_currency(rt.getPrice_list_currency());
                        invoiceRawTokenResponse.setCustomer(rt.getCustomer());
                        invoiceRawTokenResponse.setCompany(rt.getCompany());
                        invoiceRawTokenResponse.setNaming_series(rt.getNaming_series());
                        invoiceRawTokenResponse.setCurrency(rt.getCurrency());
                        invoiceRawTokenResponse.setDoc_no(rt.getDoc_no());
                        invoiceRawTokenResponse.setConversion_rate(rt.getConversion_rate());
                        invoiceRawTokenResponse.setPlc_conversion_rate(rt.getPlc_conversion_rate());
                        invoiceRawTokenResponse.setPosting_time(rt.getPosting_time());
                        invoiceRawTokenResponse.setPosting_date(rt.getPosting_date());
                        invoiceRawTokenResponse.setDocstatus(rt.getDocstatus());
                        invoiceRawTokenResponse.setIs_return(rt.getIs_return());
                        invoiceRawTokenResponse.setDevice_id(rt.getDevice_id());
                        invoiceRawTokenResponse.setIs_pos(1);
                        invoiceRawTokenResponse.setUpdate_stock(1);

                        Log.i("RAW1_creation", "<==" + invoiceRawTokenResponse.getCreation());
                        Log.i("RAW1_owner", "<==" + invoiceRawTokenResponse.getOwner());
                        Log.i("RAW1_pri_list", "<==" + invoiceRawTokenResponse.getPrice_list_currency());
                        Log.i("RAW1_customer", "<==" + invoiceRawTokenResponse.getCustomer());
                        Log.i("RAW1_company", "<==" + invoiceRawTokenResponse.getCompany());
                        Log.i("RAW1_naming_series", "<==" + invoiceRawTokenResponse.getNaming_series());
                        Log.i("RAW1_currency", "<==" + invoiceRawTokenResponse.getCurrency());
                        Log.i("RAW1_doc_no", "<==" + invoiceRawTokenResponse.getDoc_no());
                        Log.i("RAW1_conver_rate", "<==" + invoiceRawTokenResponse.getConversion_rate());
                        Log.i("RAW1_plc_con_rate", "<==" + invoiceRawTokenResponse.getPlc_conversion_rate());
                        Log.i("RAW1_time", "<==" + invoiceRawTokenResponse.getPosting_time());
                        Log.i("RAW1_posting_date", "<==" + invoiceRawTokenResponse.getPosting_date());
                        Log.i("RAW1_doc_status", "<==" + invoiceRawTokenResponse.getDocstatus());
                        Log.i("RAW1_is_return", "<==" + invoiceRawTokenResponse.getIs_return());
                        Log.i("RAW1_device_id", "<==" + invoiceRawTokenResponse.getDevice_id());


                        List<SalesInvoiceRaw_ItemData> itemss = new ArrayList<>();
                        List<SalesInvoiceRaw1_ItemData> items = rt.getItems();

                        for (SalesInvoiceRaw1_ItemData id : items) {

                            SalesInvoiceRaw_ItemData sid = new SalesInvoiceRaw_ItemData();

                            //Sync_Tax_Rate1 = Sync_Tax_Rate1 + Float.parseFloat(id.getTax_rate());
                            //  Sync_Tax_Amount1 = Sync_Tax_Amount1 + Float.parseFloat(id.getTax_amount());
                            Sync_Tax_Amount1 = Sync_Tax_Amount1 + id.getVat();
                            Sync_Total1 = Sync_Total1 + id.getTotal();

                            sid.setQty(id.getQty());
                            sid.setWarehouse(id.getWarehouse());
                            sid.setItem_name(id.getItem_name());
                            sid.setRate(id.getRate());
                            sid.setStock_uom(id.getStock_uom());
                            sid.setItem_code(id.getItem_code());
                            sid.setPrice_list_rate(id.getPrice_list_rate());
                            sid.setDiscount_percentage(id.getDiscount_percentage());
                            sid.setTax_rate(id.getTax_rate());
                            sid.setTax_amount(id.getTax_amount());
                            //  sid.setSales_order(id.getSales_order());
                            sid.setSales_order("");
                            sid.setSo_detail(id.getSo_detail());
                            sid.setfreezer_(id.getfreezer_());

                            Log.i("RAW-ITEM1_percentage", "<==" + id.getDiscount_percentage());
                            Log.i("RAW-ITEM1_item_code", "<==" + id.getItem_code());
                            Log.i("RAW-ITEM1_itemname", "<==" + id.getItem_name());
                            Log.i("RAW-ITEM1_price_list", "<==" + id.getPrice_list_rate());
                            Log.i("RAW-ITEM1_qty", "<==" + id.getQty());
                            Log.i("RAW-ITEM1_rate", "<==" + id.getRate());
                            Log.i("RAW-ITEM1_sales_order", "<==" + id.getSales_order());
                            Log.i("RAW-ITEM1_detail", "<==" + id.getSo_detail());
                            Log.i("RAW-ITEM1_stock_uom", "<==" + id.getStock_uom());
                            Log.i("RAW-ITEM1_tax_amount", "<==" + id.getTax_amount());
                            Log.i("RAW-ITEM1_tax_rate", "<==" + id.getTax_rate());
                            Log.i("RAW-ITEM1_warehouse", "<==" + id.getWarehouse());
                            Log.i("RAW-ITEM1_key_id", "<==" + id.getKEY_ID());
                            Log.i("RAW-ITEM1_sales_inv_id", "<==" + id.getSALES_INVOICE_ID());
                            Log.i("RAW-ITEM1_freezer#", "<==" + id.getfreezer_());
                            itemss.add(sid);

                        }

                        invoiceRawTokenResponse.setItems(itemss);


                        List<SalesInvoiceRaw_ItemPayments> getInvoiceRawItemPayments = new ArrayList<>();
                        List<SalesInvoiceRaw1_ItemPayments> getInvoiceRawItemPayment = rt.getPayments();

                        for (SalesInvoiceRaw1_ItemPayments ip : getInvoiceRawItemPayment) {

                            SalesInvoiceRaw_ItemPayments po = new SalesInvoiceRaw_ItemPayments();
                            po.setAccount(ip.getAccount());
                            //po.setAccount("1.01.02.0001 - Cash on Hand - SAQYA");
                            po.setMode_of_payment(ip.getMode_of_payment());
                            po.setAmount(ip.getAmount());
                            po.setBase_amount(ip.getBase_amount());
                            po.setType(ip.getType());
                            //po.setType("Cash");

                            Log.i("RAW-ITEM1_Account", "<==" + ip.getAccount());
                            Log.i("RAW-ITEM1_mode_payment", "<==" + ip.getMode_of_payment());
                            Log.i("RAW-ITEM1_amount", "<==" + ip.getAmount());
                            Log.i("RAW-ITEM1_base_amount", "<==" + ip.getBase_amount());
                            Log.i("RAW-ITEM1_type", "<==" + ip.getType());


                            getInvoiceRawItemPayments.add(po);

                        }

                        invoiceRawTokenResponse.setPayments(getInvoiceRawItemPayments);


                        List<SalesInvoiceRaw_TaxData> taxes = new ArrayList<>();
                        taxes.add(new SalesInvoiceRaw_TaxData(Tax_Account_Head, "VAT", "On Net Total", String.valueOf(Sync_Total1), String.valueOf(Sync_Tax_Amount1), db.getTaxRate(),rt.getOwner(),rt.getOwner()));//String.valueOf(Sync_Tax_Rate1)
                        invoiceRawTokenResponse.setTaxes(taxes);


                        if (!rt.getItems().isEmpty()) {

                            Call<SalesInvoiceRaw_TokenResponse> salesInvoiceRaw_tokenResponseCall = userInterface.salesInvoicePost(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Sales Invoice", invoiceRawTokenResponse);


                            salesInvoiceRaw_tokenResponseCall.enqueue(new Callback<SalesInvoiceRaw_TokenResponse>() {
                                @Override
                                public void onResponse(Call<SalesInvoiceRaw_TokenResponse> call, Response<SalesInvoiceRaw_TokenResponse> response) {

                                    Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                    Log.i("INVOICE_RESPONSE", "<==" + response.message());

                                    if (response.code() == 200) {

                                        db.updateSalesInvoiceSyncStatus(String.valueOf(rt.getKey_id()));

                                        syncMsg();
                                    } else if (response.code() == 403 || response.code() == 500) {
                                        //Utility.dismissProgressDialog();

                                        Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                    } else {
                                        // Utility.dismissProgressDialog();

                                    }


                                }

                                private void syncMsg() {

                                    if (si_data == 0) {

                                        Toast t = Utility.setToast(HomeActivity.this, "Sales Invoice Sync Success");
                                        t.show();
                                        syncCustomerVisitFromLocalToServer();
                                        isSalesSyncProgress=false;
                                    }

                                }

                                @Override
                                public void onFailure(Call<SalesInvoiceRaw_TokenResponse> call, Throwable t) {
                                    Utility.dismissProgressDialog();
                                    Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);
                                    isSalesSyncProgress=false;
                                }
                            });
                        } else {

                            db.deleteSalesInvoice(Integer.parseInt(String.valueOf(rt.getKey_id())));

                        }


                    } else {


                        invoiceRawTokenResponse_1 = new SalesInvoiceRaw_TokenResponse_1();

                        invoiceRawTokenResponse_1.setCreation(rt.getCreation());
                        invoiceRawTokenResponse_1.setOwner(rt.getOwner());
                        invoiceRawTokenResponse_1.setPrice_list_currency(rt.getPrice_list_currency());
                        invoiceRawTokenResponse_1.setCustomer(rt.getCustomer());
                        invoiceRawTokenResponse_1.setCompany(rt.getCompany());
                        invoiceRawTokenResponse_1.setNaming_series(rt.getNaming_series());
                        invoiceRawTokenResponse_1.setCurrency(rt.getCurrency());
                        invoiceRawTokenResponse_1.setDoc_no(rt.getDoc_no());
                        invoiceRawTokenResponse_1.setConversion_rate(rt.getConversion_rate());
                        invoiceRawTokenResponse_1.setPlc_conversion_rate(rt.getPlc_conversion_rate());
                        invoiceRawTokenResponse_1.setPosting_time(rt.getPosting_time());
                        invoiceRawTokenResponse_1.setPosting_date(rt.getPosting_date());
                        invoiceRawTokenResponse_1.setDocstatus(rt.getDocstatus());
                        invoiceRawTokenResponse_1.setIs_return(rt.getIs_return());
                        invoiceRawTokenResponse_1.setDevice_id(rt.getDevice_id());
                        invoiceRawTokenResponse_1.setIs_pos(0);
                        invoiceRawTokenResponse_1.setUpdate_stock(1);

                        Log.i("RAW1_creation", "<==" + invoiceRawTokenResponse_1.getCreation());
                        Log.i("RAW1_owner", "<==" + invoiceRawTokenResponse_1.getOwner());
                        Log.i("RAW1_pri_list", "<==" + invoiceRawTokenResponse_1.getPrice_list_currency());
                        Log.i("RAW1_customer", "<==" + invoiceRawTokenResponse_1.getCustomer());
                        Log.i("RAW1_company", "<==" + invoiceRawTokenResponse_1.getCompany());
                        Log.i("RAW1_naming_series", "<==" + invoiceRawTokenResponse_1.getNaming_series());
                        Log.i("RAW1_currency", "<==" + invoiceRawTokenResponse_1.getCurrency());
                        Log.i("RAW1_doc_no", "<==" + invoiceRawTokenResponse_1.getDoc_no());
                        Log.i("RAW1_conver_rate", "<==" + invoiceRawTokenResponse_1.getConversion_rate());
                        Log.i("RAW1_plc_con_rate", "<==" + invoiceRawTokenResponse_1.getPlc_conversion_rate());
                        Log.i("RAW1_time", "<==" + invoiceRawTokenResponse_1.getPosting_time());
                        Log.i("RAW1_posting_date", "<==" + invoiceRawTokenResponse_1.getPosting_date());
                        Log.i("RAW1_doc_status", "<==" + invoiceRawTokenResponse_1.getDocstatus());
                        Log.i("RAW1_is_return", "<==" + invoiceRawTokenResponse_1.getIs_return());
                        Log.i("RAW1_device_id", "<==" + invoiceRawTokenResponse_1.getDevice_id());


                        List<SalesInvoiceRaw_ItemData> itemss = new ArrayList<>();
                        List<SalesInvoiceRaw1_ItemData> items = rt.getItems();

                        for (SalesInvoiceRaw1_ItemData id : items) {

                            SalesInvoiceRaw_ItemData sid = new SalesInvoiceRaw_ItemData();

                            Sync_Tax_Rate1 = Sync_Tax_Rate1 + Float.parseFloat(id.getTax_rate());
                            Sync_Tax_Amount1 = Sync_Tax_Amount1 + Float.parseFloat(id.getTax_amount());
                            Sync_Total1 = Sync_Total1 + id.getTotal();

                            sid.setQty(id.getQty());
                            sid.setWarehouse(id.getWarehouse());
                            sid.setItem_name(id.getItem_name());
                            sid.setRate(id.getRate());
                            sid.setStock_uom(id.getStock_uom());
                            sid.setItem_code(id.getItem_code());
                            sid.setPrice_list_rate(id.getPrice_list_rate());
                            sid.setDiscount_percentage(id.getDiscount_percentage());
                            sid.setTax_rate(id.getTax_rate());
                            sid.setTax_amount(id.getTax_amount());
                            //  sid.setSales_order(id.getSales_order());
                            sid.setSales_order("");
                            sid.setSo_detail(id.getSo_detail());
                            sid.setfreezer_(id.getfreezer_());

                            Log.i("RAW-ITEM1_percentage", "<==" + id.getDiscount_percentage());
                            Log.i("RAW-ITEM1_item_code", "<==" + id.getItem_code());
                            Log.i("RAW-ITEM1_itemname", "<==" + id.getItem_name());
                            Log.i("RAW-ITEM1_price_list", "<==" + id.getPrice_list_rate());
                            Log.i("RAW-ITEM1_qty", "<==" + id.getQty());
                            Log.i("RAW-ITEM1_rate", "<==" + id.getRate());
                            Log.i("RAW-ITEM1_sales_order", "<==" + id.getSales_order());
                            Log.i("RAW-ITEM1_detail", "<==" + id.getSo_detail());
                            Log.i("RAW-ITEM1_stock_uom", "<==" + id.getStock_uom());
                            Log.i("RAW-ITEM1_tax_amount", "<==" + id.getTax_amount());
                            Log.i("RAW-ITEM1_tax_rate", "<==" + id.getTax_rate());
                            Log.i("RAW-ITEM1_warehouse", "<==" + id.getWarehouse());
                            Log.i("RAW-ITEM1_key_id", "<==" + id.getKEY_ID());
                            Log.i("RAW-ITEM1_sales_inv_id", "<==" + id.getSALES_INVOICE_ID());
                            Log.i("RAW-ITEM1_freezer#", "<==" + id.getfreezer_());
                            itemss.add(sid);

                        }

                        invoiceRawTokenResponse_1.setItems(itemss);


                        List<SalesInvoiceRaw_TaxData> taxes = new ArrayList<>();
                        taxes.add(new SalesInvoiceRaw_TaxData(Tax_Account_Head, "VAT", "On Net Total", String.valueOf(Sync_Total1), String.valueOf(Sync_Tax_Amount1), String.valueOf(Sync_Tax_Rate1),rt.getOwner(),rt.getOwner()));
                        invoiceRawTokenResponse_1.setTaxes(taxes);


                        if (!rt.getItems().isEmpty()) {

                            Call<SalesInvoiceRaw_TokenResponse_1> salesInvoiceRaw_tokenResponseCall = userInterface.salesInvoicePost1(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Sales Invoice", invoiceRawTokenResponse_1);
                            salesInvoiceRaw_tokenResponseCall.enqueue(new Callback<SalesInvoiceRaw_TokenResponse_1>() {
                                @Override
                                public void onResponse(Call<SalesInvoiceRaw_TokenResponse_1> call, Response<SalesInvoiceRaw_TokenResponse_1> response) {

                                    Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                    Log.i("INVOICE_RESPONSE", "<==" + response.message());

                                    if (response.code() == 200) {

                                        db.updateSalesInvoiceSyncStatus(String.valueOf(rt.getKey_id()));
                                        syncMsg();
                                    } else if (response.code() == 403 || response.code() == 500) {
                                        //Utility.dismissProgressDialog();

                                        Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                    } else {
                                        // Utility.dismissProgressDialog();

                                    }


                                }

                                private void syncMsg() {

                                    if (si_data == 0) {

                                        Toast t = Utility.setToast(HomeActivity.this, "Sales Invoice Sync Success");
                                        t.show();

                                        syncCustomerVisitFromLocalToServer();
                                        isSalesSyncProgress=false;
                                    }

                                }

                                @Override
                                public void onFailure(Call<SalesInvoiceRaw_TokenResponse_1> call, Throwable t) {
                                    Utility.dismissProgressDialog();
                                    Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);
                                    isSalesSyncProgress=false;
                                }
                            });
                        } else {

                            db.deleteSalesInvoice(Integer.parseInt(String.valueOf(rt.getKey_id())));

                        }


                    }


                }
            } else {
                Toast t = Utility.setToast(HomeActivity.this, "No New Sales Invoice Data");
                t.show();
            }

        }catch (Exception e){
            Utility.showDialog(HomeActivity.this, "ERROR !", e.getMessage()+"\n Try Again...", R.color.dialog_error_background);
        }
        //  syncCustomerVisitFromLocalToServer();

    }
    private void syncSalesInvoiceFromLocalToServerNew() {

        try {
            isSalesSyncProgress=false;

            List<SalesInvoiceRaw1_TokenResponse> getSalesInvoiceForSync = db.getSalesInvoiceForSync();

            si_data = getSalesInvoiceForSync.size();
            si_sync_count = si_data;

            if (!getSalesInvoiceForSync.isEmpty()) {
                isSalesSyncProgress=true;
                for (final SalesInvoiceRaw1_TokenResponse rt : getSalesInvoiceForSync) {

                    si_data = si_data - 1;

                    Sync_Tax_Rate = 0.0f;
                    Sync_Tax_Amount = 0.0f;
                    Sync_Total = 0.0f;

                    Sync_Tax_Amount1 = 0.0f;
                    Sync_Total1=0.0f;

                    if (rt.getSALES_INVOICE_IS_POS() == 1) {

                        invoiceRawTokenResponse = new SalesInvoiceRaw_TokenResponse();

                        invoiceRawTokenResponse.setCreation(rt.getCreation());
                        invoiceRawTokenResponse.setOwner(rt.getOwner());
                        invoiceRawTokenResponse.setModified_by(rt.getOwner());
                        invoiceRawTokenResponse.setPrice_list_currency(rt.getPrice_list_currency());
                        invoiceRawTokenResponse.setCustomer(rt.getCustomer());
                        invoiceRawTokenResponse.setCompany(rt.getCompany());
                        //invoiceRawTokenResponse.setNaming_series(rt.getNaming_series());
                        invoiceRawTokenResponse.setCurrency(rt.getCurrency());
                        invoiceRawTokenResponse.setDoc_no(rt.getDoc_no());
                        invoiceRawTokenResponse.setConversion_rate(rt.getConversion_rate());
                        invoiceRawTokenResponse.setPlc_conversion_rate(rt.getPlc_conversion_rate());
                        invoiceRawTokenResponse.setPosting_time(rt.getPosting_time());
                        invoiceRawTokenResponse.setPosting_date(rt.getPosting_date());
                        invoiceRawTokenResponse.setDocstatus(rt.getDocstatus());
                        invoiceRawTokenResponse.setIs_return(rt.getIs_return());
                        invoiceRawTokenResponse.setDevice_id(rt.getDevice_id());
                        invoiceRawTokenResponse.setIs_pos(1);
                        invoiceRawTokenResponse.setUpdate_stock(1);
                        invoiceRawTokenResponse.setSet_posting_time(1);

                        Log.i("RAW1_creation", "<==" + invoiceRawTokenResponse.getCreation());
                        Log.i("RAW1_owner", "<==" + invoiceRawTokenResponse.getOwner());
                        Log.i("RAW1_pri_list", "<==" + invoiceRawTokenResponse.getPrice_list_currency());
                        Log.i("RAW1_customer", "<==" + invoiceRawTokenResponse.getCustomer());
                        Log.i("RAW1_company", "<==" + invoiceRawTokenResponse.getCompany());
                        Log.i("RAW1_naming_series", "<==" + invoiceRawTokenResponse.getNaming_series());
                        Log.i("RAW1_currency", "<==" + invoiceRawTokenResponse.getCurrency());
                        Log.i("RAW1_doc_no", "<==" + invoiceRawTokenResponse.getDoc_no());
                        Log.i("RAW1_conver_rate", "<==" + invoiceRawTokenResponse.getConversion_rate());
                        Log.i("RAW1_plc_con_rate", "<==" + invoiceRawTokenResponse.getPlc_conversion_rate());
                        Log.i("RAW1_time", "<==" + invoiceRawTokenResponse.getPosting_time());
                        Log.i("RAW1_posting_date", "<==" + invoiceRawTokenResponse.getPosting_date());
                        Log.i("RAW1_doc_status", "<==" + invoiceRawTokenResponse.getDocstatus());
                        Log.i("RAW1_is_return", "<==" + invoiceRawTokenResponse.getIs_return());
                        Log.i("RAW1_device_id", "<==" + invoiceRawTokenResponse.getDevice_id());


                        List<SalesInvoiceRaw_ItemData> itemss = new ArrayList<>();
                        List<SalesInvoiceRaw1_ItemData> items = rt.getItems();

                        for (SalesInvoiceRaw1_ItemData id : items) {

                            SalesInvoiceRaw_ItemData sid = new SalesInvoiceRaw_ItemData();

                            //Sync_Tax_Rate1 = Sync_Tax_Rate1 + Float.parseFloat(id.getTax_rate());
                            //  Sync_Tax_Amount1 = Sync_Tax_Amount1 + Float.parseFloat(id.getTax_amount());
                            Sync_Tax_Amount1 = Sync_Tax_Amount1 + id.getVat();
                            Sync_Total1 = Sync_Total1 + id.getTotal();
                            sid.setOwner(rt.getOwner());
                            sid.setModified_by(rt.getOwner());
                            sid.setQty(id.getQty());
                            sid.setWarehouse(id.getWarehouse());
                            sid.setItem_name(id.getItem_name());
                            sid.setRate(id.getRate());
                            sid.setStock_uom(id.getStock_uom());
                            sid.setItem_code(id.getItem_code());
                            sid.setPrice_list_rate(id.getPrice_list_rate());
                            sid.setDiscount_percentage(id.getDiscount_percentage());
                            sid.setTax_rate(id.getTax_rate());
                            sid.setTax_amount(id.getTax_amount());
                            //  sid.setSales_order(id.getSales_order());
                            sid.setSales_order("");
                            sid.setSo_detail(id.getSo_detail());
                            sid.setfreezer_(id.getfreezer_());

                            Log.i("RAW-ITEM1_percentage", "<==" + id.getDiscount_percentage());
                            Log.i("RAW-ITEM1_item_code", "<==" + id.getItem_code());
                            Log.i("RAW-ITEM1_itemname", "<==" + id.getItem_name());
                            Log.i("RAW-ITEM1_price_list", "<==" + id.getPrice_list_rate());
                            Log.i("RAW-ITEM1_qty", "<==" + id.getQty());
                            Log.i("RAW-ITEM1_rate", "<==" + id.getRate());
                            Log.i("RAW-ITEM1_sales_order", "<==" + id.getSales_order());
                            Log.i("RAW-ITEM1_detail", "<==" + id.getSo_detail());
                            Log.i("RAW-ITEM1_stock_uom", "<==" + id.getStock_uom());
                            Log.i("RAW-ITEM1_tax_amount", "<==" + id.getTax_amount());
                            Log.i("RAW-ITEM1_tax_rate", "<==" + id.getTax_rate());
                            Log.i("RAW-ITEM1_warehouse", "<==" + id.getWarehouse());
                            Log.i("RAW-ITEM1_key_id", "<==" + id.getKEY_ID());
                            Log.i("RAW-ITEM1_sales_inv_id", "<==" + id.getSALES_INVOICE_ID());
                            Log.i("RAW-ITEM1_freezer#", "<==" + id.getfreezer_());
                            itemss.add(sid);

                        }

                        invoiceRawTokenResponse.setItems(itemss);


                        List<SalesInvoiceRaw_ItemPayments> getInvoiceRawItemPayments = new ArrayList<>();
                        List<SalesInvoiceRaw1_ItemPayments> getInvoiceRawItemPayment = rt.getPayments();

                        for (SalesInvoiceRaw1_ItemPayments ip : getInvoiceRawItemPayment) {

                            SalesInvoiceRaw_ItemPayments po = new SalesInvoiceRaw_ItemPayments();
                            po.setAccount(ip.getAccount());
                            po.setOwner(rt.getOwner());
                            po.setModified_by(rt.getOwner());
                            //po.setAccount("1.01.02.0001 - Cash on Hand - SAQYA");
                            po.setMode_of_payment(ip.getMode_of_payment());
                            po.setAmount(ip.getAmount());
                            po.setBase_amount(ip.getBase_amount());
                            po.setType(ip.getType());
                            //po.setType("Cash");

                            Log.i("RAW-ITEM1_Account", "<==" + ip.getAccount());
                            Log.i("RAW-ITEM1_mode_payment", "<==" + ip.getMode_of_payment());
                            Log.i("RAW-ITEM1_amount", "<==" + ip.getAmount());
                            Log.i("RAW-ITEM1_base_amount", "<==" + ip.getBase_amount());
                            Log.i("RAW-ITEM1_type", "<==" + ip.getType());


                            getInvoiceRawItemPayments.add(po);

                        }

                        invoiceRawTokenResponse.setPayments(getInvoiceRawItemPayments);


                     List<SalesInvoiceRaw_TaxData> taxes = new ArrayList<>();
                        taxes.add(new SalesInvoiceRaw_TaxData(Tax_Account_Head, "VAT", "On Net Total", String.valueOf(Sync_Total1), String.valueOf(Sync_Tax_Amount1), db.getTaxRate(),rt.getOwner(),rt.getOwner()));//String.valueOf(Sync_Tax_Rate1)
                        invoiceRawTokenResponse.setTaxes(taxes);


                        if (!rt.getItems().isEmpty()) {

                            Call<SalesInvoiceRaw_TokenResponse> salesInvoiceRaw_tokenResponseCall = userInterface.salesInvoicePost(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Sales Invoice", invoiceRawTokenResponse);


                            salesInvoiceRaw_tokenResponseCall.enqueue(new Callback<SalesInvoiceRaw_TokenResponse>() {
                                @Override
                                public void onResponse(Call<SalesInvoiceRaw_TokenResponse> call, Response<SalesInvoiceRaw_TokenResponse> response) {

                                    Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                    Log.i("INVOICE_RESPONSE", "<==" + response.message());

                                    if (response.code() == 200) {

                                        db.updateSalesInvoiceSyncStatus(String.valueOf(rt.getKey_id()));
                                        si_sync_count--;
                                        //syncOneCustomerVisitFromLocalToServer(rt.getDoc_no());
                                        if (si_data == si_sync_count)
                                        syncMsg();
                                    } else if (response.code() == 403 || response.code() == 500||response.code()==417) {
                                        //Utility.dismissProgressDialog();

                                        Utility.showDialog(HomeActivity.this, "ERROR !", rt.getDoc_no()+"\n"+response.message()+"\n Try Again...", R.color.dialog_error_background);

                                    } else {
                                        Utility.showDialog(HomeActivity.this, "ERROR !", rt.getDoc_no()+"\n"+response.message(), R.color.dialog_error_background);

                                    }


                                }


                                private void syncMsg() {

                                    if (si_data == 0) {

                                        Toast t = Utility.setToast(HomeActivity.this, "Sales Invoice Sync Success");
                                        t.show();
                                        syncAllCustomerVisitFromLocalToServer();
                                        isSalesSyncProgress=false;
                                    }

                                }

                                @Override
                                public void onFailure(Call<SalesInvoiceRaw_TokenResponse> call, Throwable t) {
                                    Utility.dismissProgressDialog();
                                    Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);
                                    isSalesSyncProgress=false;
                                }
                            });
                        } else {

                            db.deleteSalesInvoice(Integer.parseInt(String.valueOf(rt.getKey_id())));

                        }


                    } else {


                        invoiceRawTokenResponse_1 = new SalesInvoiceRaw_TokenResponse_1();
                        invoiceRawTokenResponse_1.setCreation(rt.getCreation());
                        invoiceRawTokenResponse_1.setOwner(rt.getOwner());
                        invoiceRawTokenResponse_1.setModified_by(rt.getOwner());
                        invoiceRawTokenResponse_1.setPrice_list_currency(rt.getPrice_list_currency());
                        invoiceRawTokenResponse_1.setCustomer(rt.getCustomer());
                        invoiceRawTokenResponse_1.setCompany(rt.getCompany());
                       // invoiceRawTokenResponse_1.setNaming_series(rt.getNaming_series());
                        invoiceRawTokenResponse_1.setCurrency(rt.getCurrency());
                        invoiceRawTokenResponse_1.setDoc_no(rt.getDoc_no());
                        invoiceRawTokenResponse_1.setConversion_rate(rt.getConversion_rate());
                        invoiceRawTokenResponse_1.setPlc_conversion_rate(rt.getPlc_conversion_rate());
                        invoiceRawTokenResponse_1.setPosting_time(rt.getPosting_time());
                        invoiceRawTokenResponse_1.setPosting_date(rt.getPosting_date());
                        invoiceRawTokenResponse_1.setDocstatus(rt.getDocstatus());
                        invoiceRawTokenResponse_1.setIs_return(rt.getIs_return());
                        invoiceRawTokenResponse_1.setDevice_id(rt.getDevice_id());
                        invoiceRawTokenResponse_1.setIs_pos(0);
                        invoiceRawTokenResponse_1.setUpdate_stock(1);
                        invoiceRawTokenResponse_1.setSet_posting_time(1);

                        Log.i("RAW1_creation", "<==" + invoiceRawTokenResponse_1.getCreation());
                        Log.i("RAW1_owner", "<==" + invoiceRawTokenResponse_1.getOwner());
                        Log.i("RAW1_pri_list", "<==" + invoiceRawTokenResponse_1.getPrice_list_currency());
                        Log.i("RAW1_customer", "<==" + invoiceRawTokenResponse_1.getCustomer());
                        Log.i("RAW1_company", "<==" + invoiceRawTokenResponse_1.getCompany());
                        Log.i("RAW1_naming_series", "<==" + invoiceRawTokenResponse_1.getNaming_series());
                        Log.i("RAW1_currency", "<==" + invoiceRawTokenResponse_1.getCurrency());
                        Log.i("RAW1_doc_no", "<==" + invoiceRawTokenResponse_1.getDoc_no());
                        Log.i("RAW1_conver_rate", "<==" + invoiceRawTokenResponse_1.getConversion_rate());
                        Log.i("RAW1_plc_con_rate", "<==" + invoiceRawTokenResponse_1.getPlc_conversion_rate());
                        Log.i("RAW1_time", "<==" + invoiceRawTokenResponse_1.getPosting_time());
                        Log.i("RAW1_posting_date", "<==" + invoiceRawTokenResponse_1.getPosting_date());
                        Log.i("RAW1_doc_status", "<==" + invoiceRawTokenResponse_1.getDocstatus());
                        Log.i("RAW1_is_return", "<==" + invoiceRawTokenResponse_1.getIs_return());
                        Log.i("RAW1_device_id", "<==" + invoiceRawTokenResponse_1.getDevice_id());


                        List<SalesInvoiceRaw_ItemData> itemss = new ArrayList<>();
                        List<SalesInvoiceRaw1_ItemData> items = rt.getItems();

                        for (SalesInvoiceRaw1_ItemData id : items) {

                            SalesInvoiceRaw_ItemData sid = new SalesInvoiceRaw_ItemData();

                            Sync_Tax_Rate1 = Sync_Tax_Rate1 + Float.parseFloat(id.getTax_rate());
                            Sync_Tax_Amount1 = Sync_Tax_Amount1 + Float.parseFloat(id.getTax_amount());
                            Sync_Total1 = Sync_Total1 + id.getTotal();
                            sid.setOwner(rt.getOwner());
                            sid.setModified_by(rt.getOwner());
                            sid.setQty(id.getQty());
                            sid.setWarehouse(id.getWarehouse());
                            sid.setItem_name(id.getItem_name());
                            sid.setRate(id.getRate());
                            sid.setStock_uom(id.getStock_uom());
                            sid.setItem_code(id.getItem_code());
                            sid.setPrice_list_rate(id.getPrice_list_rate());
                            sid.setDiscount_percentage(id.getDiscount_percentage());
                            sid.setTax_rate(id.getTax_rate());
                            sid.setTax_amount(id.getTax_amount());
                            //  sid.setSales_order(id.getSales_order());
                            sid.setSales_order("");
                            sid.setSo_detail(id.getSo_detail());
                            sid.setfreezer_(id.getfreezer_());

                            Log.i("RAW-ITEM1_percentage", "<==" + id.getDiscount_percentage());
                            Log.i("RAW-ITEM1_item_code", "<==" + id.getItem_code());
                            Log.i("RAW-ITEM1_itemname", "<==" + id.getItem_name());
                            Log.i("RAW-ITEM1_price_list", "<==" + id.getPrice_list_rate());
                            Log.i("RAW-ITEM1_qty", "<==" + id.getQty());
                            Log.i("RAW-ITEM1_rate", "<==" + id.getRate());
                            Log.i("RAW-ITEM1_sales_order", "<==" + id.getSales_order());
                            Log.i("RAW-ITEM1_detail", "<==" + id.getSo_detail());
                            Log.i("RAW-ITEM1_stock_uom", "<==" + id.getStock_uom());
                            Log.i("RAW-ITEM1_tax_amount", "<==" + id.getTax_amount());
                            Log.i("RAW-ITEM1_tax_rate", "<==" + id.getTax_rate());
                            Log.i("RAW-ITEM1_warehouse", "<==" + id.getWarehouse());
                            Log.i("RAW-ITEM1_key_id", "<==" + id.getKEY_ID());
                            Log.i("RAW-ITEM1_sales_inv_id", "<==" + id.getSALES_INVOICE_ID());
                            Log.i("RAW-ITEM1_freezer#", "<==" + id.getfreezer_());
                            itemss.add(sid);

                        }

                        invoiceRawTokenResponse_1.setItems(itemss);


                        List<SalesInvoiceRaw_TaxData> taxes = new ArrayList<>();
                        taxes.add(new SalesInvoiceRaw_TaxData(Tax_Account_Head, "VAT", "On Net Total", String.valueOf(Sync_Total1), String.valueOf(Sync_Tax_Amount1), db.getTaxRate(),rt.getOwner(),rt.getOwner()));
                        invoiceRawTokenResponse_1.setTaxes(taxes);


                        if (!rt.getItems().isEmpty()) {

                            Call<SalesInvoiceRaw_TokenResponse_1> salesInvoiceRaw_tokenResponseCall = userInterface.salesInvoicePost1(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Sales Invoice", invoiceRawTokenResponse_1);
                            salesInvoiceRaw_tokenResponseCall.enqueue(new Callback<SalesInvoiceRaw_TokenResponse_1>() {
                                @Override
                                public void onResponse(Call<SalesInvoiceRaw_TokenResponse_1> call, Response<SalesInvoiceRaw_TokenResponse_1> response) {

                                    Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                    Log.i("INVOICE_RESPONSE", "<==" + response.message());

                                    if (response.code() == 200) {

                                        db.updateSalesInvoiceSyncStatus(String.valueOf(rt.getKey_id()));
                                        si_sync_count--;
                                        //syncOneCustomerVisitFromLocalToServer(rt.getDoc_no());
                                        if (si_data == si_sync_count)
                                        syncMsg();
                                    } else if (response.code() == 403 || response.code() == 500||response.code()==417) {
                                        //Utility.dismissProgressDialog();
                                        Utility.showDialog(HomeActivity.this, "ERROR !", rt.getDoc_no()+"\n"+response.message()+"\n Try Again...", R.color.dialog_error_background);
                                        //Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                    } else {
                                        Utility.showDialog(HomeActivity.this, "ERROR !", rt.getDoc_no()+"\n"+response.message(), R.color.dialog_error_background);

                                    }


                                }

                                private void syncMsg() {

                                    if (si_data == 0) {

                                        Toast t = Utility.setToast(HomeActivity.this, "Sales Invoice Sync Success");
                                        t.show();
                                        syncAllCustomerVisitFromLocalToServer();
                                        isSalesSyncProgress=false;
                                    }

                                }

                                @Override
                                public void onFailure(Call<SalesInvoiceRaw_TokenResponse_1> call, Throwable t) {
                                    Utility.dismissProgressDialog();
                                    Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);
                                    isSalesSyncProgress=false;
                                }
                            });
                        } else {

                            db.deleteSalesInvoice(Integer.parseInt(String.valueOf(rt.getKey_id())));

                        }


                    }
                    if(si_data==0){
                        db.updateLastSyncTime();
                    }

                }
            } else {
                Toast t = Utility.setToast(HomeActivity.this, "No New Sales Invoice Data");
                t.show();
                db.updateLastSyncTime();
            }

        }catch (Exception e){
            Utility.showDialog(HomeActivity.this, "ERROR !", e.getMessage()+"\n Try Again...", R.color.dialog_error_background);
        }
        //  syncCustomerVisitFromLocalToServer();

    }



    private void syncSalesOrderFromLocalToServer() {

        final List<SalesOrderRaw_TokenResponse1> getSalesOrderForSync = db.getSalesOrderForSync();

        sn_data = getSalesOrderForSync.size();

        if (!getSalesOrderForSync.isEmpty()) {


            Log.i("SSCookie", "<==" + cookie);

            for (final SalesOrderRaw_TokenResponse1 st : getSalesOrderForSync) {

                sn_data = sn_data - 1;

                Sync_Tax_Rate = 0.0f;
                Sync_Tax_Amount = 0.0f;
                Sync_Total = 0.0f;


                rawTokenResponse = new SalesOrderRaw_TokenResponse();

                rawTokenResponse.setCreation(st.getCreation());
                rawTokenResponse.setOwner(st.getOwner());
                rawTokenResponse.setBilling_status("Not Billed");
                rawTokenResponse.setCreation(st.getCreation());
                rawTokenResponse.setPo_no(st.getPo_no());
                rawTokenResponse.setCustomer(st.getCustomer());
                rawTokenResponse.setOrder_type("Sales");
                rawTokenResponse.setStatus(st.getStatus());
                // rawTokenResponse.setCompany("Printechs Advanced Printing Trading Co.");
                rawTokenResponse.setCompany(st.getCompany());
                rawTokenResponse.setNaming_series(st.getNaming_series());
                rawTokenResponse.setDoc_no(st.getDoc_no());
                rawTokenResponse.setDelivery_date(st.getDelivery_date());
                // rawTokenResponse.setCurrency("SAR");
                rawTokenResponse.setCurrency(st.getCurrency());
                rawTokenResponse.setConversion_rate(st.getConversion_rate());
                //rawTokenResponse.setPrice_list_currency("SAR");
                rawTokenResponse.setPrice_list_currency(st.getPrice_list_currency());
                rawTokenResponse.setPlc_conversion_rate(st.getPlc_conversion_rate());
                rawTokenResponse.setDevice_id(st.getDevice_id());


                Log.i("SSO", "<==" + rawTokenResponse.getCreation());
                Log.i("SSO", "<==" + rawTokenResponse.getOwner());
                Log.i("SSO", "<==" + rawTokenResponse.getBilling_status());
                Log.i("SSO", "<==" + rawTokenResponse.getPo_no());
                Log.i("SSO", "<==" + rawTokenResponse.getCustomer());
                Log.i("SSO", "<==" + rawTokenResponse.getOrder_type());
                Log.i("SSO", "<==" + rawTokenResponse.getStatus());
                Log.i("SSO", "<==" + rawTokenResponse.getCompany());
                Log.i("SSO", "<==" + rawTokenResponse.getNaming_series());
                Log.i("SSO", "<==" + rawTokenResponse.getDoc_no());
                Log.i("SSO", "<==" + rawTokenResponse.getDelivery_date());
                Log.i("SSO", "<==" + rawTokenResponse.getCurrency());
                Log.i("SSO", "<==" + rawTokenResponse.getConversion_rate());
                Log.i("SSO", "<==" + rawTokenResponse.getPrice_list_currency());
                Log.i("SSO", "<==" + rawTokenResponse.getPlc_conversion_rate());
                Log.i("SSO", "<==" + rawTokenResponse.getDevice_id());


                List<SalesOrderRawItemData> itemss = new ArrayList<>();
                List<SalesOrderRawItemData1> items = st.getItems();

                for (SalesOrderRawItemData1 si : items) {

                    SalesOrderRawItemData sd = new SalesOrderRawItemData();

                    Log.i("SSI", "<==" + si.getQty());
                    Log.i("SSI", "<==" + si.getWarehouse());
                    Log.i("SSI", "<==" + si.getItem_name());
                    Log.i("SSI", "<==" + si.getRate());
                    Log.i("SSI", "<==" + si.getStock_uom());
                    Log.i("SSI", "<==" + si.getItem_code());
                    Log.i("SSI", "<==" + si.getPrice_list_rate());
                    Log.i("SSI", "<==" + si.getDiscount_percentage());
                    Log.i("SSI", "<==" + si.getTax_rate());
                    Log.i("SSI", "<==" + si.getTax_amount());

                    Sync_Tax_Rate = Sync_Tax_Rate + Float.parseFloat(si.getTax_rate());
                    Sync_Tax_Amount = Sync_Tax_Amount + Float.parseFloat(si.getTax_amount());
                    Sync_Total = Sync_Total + Float.parseFloat(si.getTotal());

                    sd.setQty(si.getQty());
                    sd.setWarehouse(si.getWarehouse());
                    sd.setItem_name(si.getItem_name());
                    sd.setRate(si.getRate());
                    sd.setStock_uom(si.getStock_uom());
                    sd.setItem_code(si.getItem_code());
                    sd.setPrice_list_rate(si.getPrice_list_rate());
                    sd.setDiscount_percentage(si.getDiscount_percentage());
                    sd.setTax_rate(si.getTax_rate());
                    sd.setTax_amount(si.getTax_amount());

                    itemss.add(sd);
                }


                rawTokenResponse.setItems(itemss);
                taxess = new ArrayList<SalesOrderTaxRawData>();
                taxess.add(new SalesOrderTaxRawData(Tax_Account_Head, "VAT", "On Net Total", String.valueOf(Sync_Total), String.valueOf(Sync_Tax_Amount), String.valueOf(Sync_Tax_Rate)));
                rawTokenResponse.setTaxes(taxess);





           /* I/SSO: <==2018-12-03
I/SSO: <==apiuser@saqiya.com
I/SSO: <==Not Billed
I/SSO: <==
I/SSO: <==ASWAK ZARA
I/SSO: <==Sales
I/SSO: <==Draft
I/SSO: <==Al Naqa'a Al Mubtakir Ice Factory
I/SSO: <==So
I/SSO: <==So000002
I/SSO: <==2018-12-3
I/SSO: <==SAR
I/SSO: <==1
I/SSO: <==SAR
I/SSO: <==1
I/SSO: <==805e6c047e03dbce
I/SSI: <==1
I/SSI: <==Stores - SAQYA
I/SSI: <==Naqa Ice Cubes Bag Small 3 Kg
I/SSI: <==1.5
I/SSI: <==Kg
I/SSI: <==0001
I/SSI: <==1.5
I/SSI: <==0
I/SSI: <==5
I/SSI: <==0*/


                if (!st.getItems().isEmpty()) {


                    Call<SalesOrderSync_TokenResponse> salesOrderSync_tokenResponseCall = userInterface.salesOrderPost(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Sales Order", rawTokenResponse);
                    salesOrderSync_tokenResponseCall.enqueue(new Callback<SalesOrderSync_TokenResponse>() {
                        @Override
                        public void onResponse(Call<SalesOrderSync_TokenResponse> call, Response<SalesOrderSync_TokenResponse> response) {
                            Log.i("SALES_RESPONSE", "<==" + response.code());
                            Log.i("SALES_RESPONSE", "<==" + response.message());
                            Log.i("SALES_RESPONSE", "<==" + response.errorBody());
                            Log.i("SALES_RESPONSE", "<==" + response.headers());

                            if (response.code() == 200) {

                                db.updateSalesOrderSyncStatus(st.getId());

                                syncMsg();
                                // sn_data = 1;


                            } else if (response.code() == 403 || response.code() == 500) {
                                //Utility.dismissProgressDialog();

                                Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                            } else {
                                // Utility.dismissProgressDialog();

                            }

                        }

                        private void syncMsg() {

                            if (sn_data == 0) {

                                Toast t = Utility.setToast(HomeActivity.this, "Sales Order Sync Success");
                                t.show();

                            }


                        }


                        @Override
                        public void onFailure(Call<SalesOrderSync_TokenResponse> call, Throwable t) {

                            Utility.dismissProgressDialog();
                            Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

                        }
                    });


                } else {

                    db.deleteSalesOrder(Integer.parseInt(st.getId()));


                }

               /* if (sn_data == 1) {
                    Toast t = Utility.setToast(HomeActivity.this, "Sync Success");
                    t.show();
                }*/

            }

            // Toast t = Utility.setToast(HomeActivity.this, "Sync Success");
            // t.show();

           /* if (sn_data == 1 ){
                Toast t = Utility.setToast(HomeActivity.this, "Sync Success");
                t.show();
            }*/

        } else {
            Toast t = Utility.setToast(HomeActivity.this, "No New Sales Order Data");
            t.show();
        }

        // Toast.makeText(this, "OKKKK", Toast.LENGTH_SHORT).show();


    }


    private void itemListDataFromServer() {

        db.clearItemTable();
        db.clearItemDetailTable();

        LayoutInflater li = LayoutInflater.from(HomeActivity.this);
        View promptsView = li.inflate(R.layout.progress_dialog, null);
        TextView txt = promptsView.findViewById(R.id.text_text);
        txt.setText("Updating Table....");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false);
        item_alertDialog = alertDialogBuilder.create();
        item_alertDialog.show();

        // Utility.showProgressDialog(HomeActivity.this, "Updating Item Table....");

        // Call<Item_TokenResponse> item_tokenResponseCall = userInterface.getItem(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Item?fields=%5B%22item_code%22%2C%22item_name%22%2C%22description%22%2C%22item_group%22%2C%22brand%22%2C%22barcode%22%2C%22stock_uom%22%5D");
       // Call<Item_TokenResponse> item_tokenResponseCall = userInterface.getItem(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Item?fields=%5B%22item_code%22%2C%22item_name%22%2C%22description%22%2C%22item_group%22%2C%22brand%22%2C%22barcode%22%2C%22stock_uom%22%5D&limit_page_length=100000");
        Call<Item_TokenResponse> item_tokenResponseCall = userInterface.getItem(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Item?filters=[[\"Item\",\"disabled\",\"=\",\"0\"],[\"Item\",\"is_sales_item\",\"=\",\"1\"],[\"Item\",\"is_fixed_asset\",\"=\",\"0\"]]&fields=[\"item_code\",\"item_name\",\"description\",\"item_group\",\"brand\",\"barcode\",\"stock_uom\"]&limit_page_length=100000");
        item_tokenResponseCall.enqueue(new Callback<Item_TokenResponse>() {
            @Override
            public void onResponse(Call<Item_TokenResponse> call, Response<Item_TokenResponse> response) {
                Log.i("ITEM_RESPONSE", "<==" + response.code());
                Log.i("ITEM_RESPONSE", "<==" + response.message());

                if (response.code() == 200) {

                    Item_TokenResponse item_tokenResponse = response.body();
                    List<ItemData> itemData = item_tokenResponse.getData();

                    item_sync_status = itemData.size();

                    for (ItemData id : itemData) {

                        item_sync_status = item_sync_status - 1;

                        Log.i("UOMM_ITM", "<==" + id.getItemCode());

                        db.addItem(new ItemClass(id.getItemCode(), id.getItemName(), id.getDescription(), id.getItemGroup(), id.getBrand(), id.getBarcode(), id.getStockUom()));

                        Call<ItemDetail_TokenResponse> itemDetail_tokenResponseCall = userInterface.getItemdetail(cookie, "api/resource/Item/" + id.getItemCode());
                        itemDetail_tokenResponseCall.enqueue(new Callback<ItemDetail_TokenResponse>() {
                            @Override
                            public void onResponse(Call<ItemDetail_TokenResponse> call, Response<ItemDetail_TokenResponse> response) {
                                Log.i("ITEM_DETAIL_RESPONSE", "<==" + response.code());
                                Log.i("ITEM_DETAIL_RESPONSE", "<==" + response.message());

                                if (response.code() == 200) {

                                    ItemDetail_TokenResponse itemDetail_tokenResponse = response.body();
                                    ItemDetailData getData = itemDetail_tokenResponse.getData();
                                    List<ItemDetailUom> uoms = getData.getUoms();

                                    Log.i("UOMM_SIZE", "<==" + uoms.size());

                                    for (ItemDetailUom itm : uoms) {

                                        Log.i("UOMM_ITM", "<==" + itm.getParent());
                                        Log.i("UOMM_ITM", "<==" + itm.getUom());

                                        db.addItemDetail(new ItemDetailClass(itm.getParent(), String.valueOf(itm.getConversionFactor()), itm.getUom(), itm.getAlu2(), itm.getPrice()));

                                    }


                                   /* for (ItemDetailUom itm : uoms) {

                                        Log.i("UOMM_ITM", "<==" + itm.getParent());
                                        Log.i("UOMM_ITM", "<==" + itm.getUom());

                                        db.addItemDetail(new ItemDetailClass(itm.getParent(), String.valueOf(itm.getConversionFactor()), itm.getUom(), itm.getAlu2(), itm.getPrice()));

                                    }*/

                                    // itemSyncMsg();


                                    //Utility.dismissProgressDialog();
                                } else if (response.code() == 403 || response.code() == 500) {
                                    // Utility.dismissProgressDialog();
                                    item_alertDialog.dismiss();
                                    Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                } else {
                                    // Utility.dismissProgressDialog();
                                    item_alertDialog.dismiss();
                                }


                            }


                            @Override
                            public void onFailure(Call<ItemDetail_TokenResponse> call, Throwable t) {

                            }
                        });

                    }

                    itemSyncMsg();

                    // Utility.dismissProgressDialog();


                } else if (response.code() == 403 || response.code() == 500) {
                    // Utility.dismissProgressDialog();
                    item_alertDialog.dismiss();
                    Utility.showDialog(HomeActivity.this, "ERROR !", "Session Timeout !\n Settings Reset Reccomented...", R.color.dialog_error_background);

                } else {
                    // Utility.dismissProgressDialog();
                    item_alertDialog.dismiss();
                }

                item_alertDialog.dismiss();
            }

            private void itemSyncMsg() {

                if (item_sync_status == 0) {
                    item_alertDialog.dismiss();
                    Toast t = Utility.setToast(HomeActivity.this, "Item Table Updated");
                    t.show();

                }


            }

            @Override
            public void onFailure(Call<Item_TokenResponse> call, Throwable t) {
                item_alertDialog.dismiss();
                // Utility.dismissProgressDialog();
                Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);
            }
        });
        item_alertDialog.dismiss();
        //Utility.dismissProgressDialog();
    }

    private void syncCustomerDataFromLocalToServer() {

        List<CustomerPost> getUnsyncCustomer = db.getUnsyncCustomer();

        if (!getUnsyncCustomer.isEmpty()) {

            Utility.showProgressDialog(HomeActivity.this, "Customer Sync on Progress...");

            for (final CustomerPost cp : getUnsyncCustomer) {


                Log.i("COOOOOO", "<==" + cookie);


                Call<CustomerSync_TokenResponse> customerSync_tokenResponseCall = userInterface.customerSync(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Customer", cp);
                customerSync_tokenResponseCall.enqueue(new Callback<CustomerSync_TokenResponse>() {
                    @Override
                    public void onResponse(Call<CustomerSync_TokenResponse> call, Response<CustomerSync_TokenResponse> response) {
                        Log.i("CUSTOM_RES", "<==" + response.code());
                        Log.i("CUSTOM_RES", "<==" + response.message());
                        Log.i("CUSTOM_RES", "<==" + response.errorBody());
                        Log.i("CUSTOM_RES", "<==" + response.headers());

                        if (response.code() == 200) {

                            db.updateCustomerSyncStatus(cp.getCustomer_name());

                        } else if (response.code() == 403 || response.code() == 500) {

                            Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                        } else {

                        }


                    }

                    @Override
                    public void onFailure(Call<CustomerSync_TokenResponse> call, Throwable t) {

                        Utility.dismissProgressDialog();
                        Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);
                    }
                });

            }


            Utility.dismissProgressDialog();
            syncAddressDataFromLocalToServer();
            customerAssetListDataFromServer();

        } else {

            Toast t = Utility.setToast(HomeActivity.this, "No New Customer Data");
            t.show();

        }


    }

    private void syncAddressDataFromLocalToServer() {

        List<AddressPost> getUnsyncCustomerAddress = db.getUnsyncCustomerAddress();

        customer_sync_status = getUnsyncCustomerAddress.size();

        if (!getUnsyncCustomerAddress.isEmpty()) {

            Utility.showProgressDialog(HomeActivity.this, "Address Sync on Progress...");
            for (final AddressPost ap : getUnsyncCustomerAddress) {

                customer_sync_status = customer_sync_status - 1;

                Call<AddressSync_TokenResponse> addressSync_tokenResponseCall = userInterface.addressSync(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Address", ap);
                addressSync_tokenResponseCall.enqueue(new Callback<AddressSync_TokenResponse>() {
                    @Override
                    public void onResponse(Call<AddressSync_TokenResponse> call, Response<AddressSync_TokenResponse> response) {
                        Log.i("ADDRESS_RES", "<==" + response.code());
                        Log.i("ADDRESS_RES", "<==" + response.message());
                        Log.i("ADDRESS_RES", "<==" + response.errorBody());
                        Log.i("ADDRESS_RES", "<==" + response.headers());
                        if (response.code() == 200) {

                            db.updateAddresSyncStatus(ap.getCustomer_name());

                            custSyncMsg();

                        } else if (response.code() == 403 || response.code() == 500) {

                            Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                        } else {

                        }

                    }

                    private void custSyncMsg() {

                        if (customer_sync_status == 0) {

                            Toast t = Utility.setToast(HomeActivity.this, "Customer Sync Success");
                            t.show();

                        }
                    }

                    @Override
                    public void onFailure(Call<AddressSync_TokenResponse> call, Throwable t) {
                        Log.i("ERRORRR", "<==" + t.getMessage());
                        Utility.dismissProgressDialog();
                        Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);
                    }
                });
            }

            Utility.dismissProgressDialog();
        } else {
            Toast t = Utility.setToast(HomeActivity.this, "No New Data");
            t.show();
        }

    }


    private void modePaymentDataFromServer() {


        //  Call<ModePayment_TokenResponse> tokenResponseCall = userInterface.getModeOfPayment(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Mode%20of%20Payment?fields=%5B%22name%22%2C%20%22mode_of_payment%22%2C%22type%22%5D");
        Call<ModePayment_TokenResponse> tokenResponseCall = userInterface.getModeOfPayment(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Mode%20of%20Payment?filters=[[\"Mode of Payment\",\"accessible_in_mobile\",\"=\",\"1\"]]&fields=%5B%22name%22%2C%20%22mode_of_payment%22%2C%22type%22%5D&limit_page_length=100000");
        tokenResponseCall.enqueue(new Callback<ModePayment_TokenResponse>() {
            @Override
            public void onResponse(Call<ModePayment_TokenResponse> call, Response<ModePayment_TokenResponse> response) {
                Log.i("MODE_RESPONSE", "<==" + response.code());
                Log.i("MODE_RESPONSE", "<==" + response.message());

                if (response.code() == 200) {

                    db.clearModePaymentTable();

                    ModePayment_TokenResponse payment_tokenResponse = response.body();
                    List<ModePaymentData> data = payment_tokenResponse.getData();

                    for (final ModePaymentData pd : data) {

                        Call<ModeOfPaymentByName_TokenResponse> byNameTokenResponseCall = userInterface.getModeOfPaymentByNAme(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Mode%20of%20Payment/" + pd.getModeOfPayment());
                        byNameTokenResponseCall.enqueue(new Callback<ModeOfPaymentByName_TokenResponse>() {
                            @Override
                            public void onResponse(Call<ModeOfPaymentByName_TokenResponse> call, Response<ModeOfPaymentByName_TokenResponse> response) {
                                Log.i("MODE_RESPONSE_NAME", "<==" + response.code());
                                Log.i("MODE_RESPONSE_NAME", "<==" + response.message());

                                if (response.code() == 200) {

                                    ModeOfPaymentByName_TokenResponse tokenResponse = response.body();
                                    ModeOfPaymentByNameData data = tokenResponse.getData();
                                    List<ModeOfPaymentByNameAccount> accounts = data.getAccounts();
                                    if (!accounts.isEmpty()) {

                                        db.addModeOfPayment(new Mode_Of_Payment(pd.getName(), pd.getModeOfPayment(), pd.getType(), accounts.get(0).getDefaultAccount()));

                                    }
                                } else if (response.code() == 403 || response.code() == 500) {
                                    // customer_alertDialog.dismiss();
                                    // Utility.dismissProgressDialog();
                                    Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                } else {
                                    // customer_alertDialog.dismiss();
                                    // Utility.dismissProgressDialog();
                                }


                            }

                            @Override
                            public void onFailure(Call<ModeOfPaymentByName_TokenResponse> call, Throwable t) {

                            }
                        });


                        //  db.addModeOfPayment(new Mode_Of_Payment(pd.getName(), pd.getModeOfPayment(), pd.getType()));

                    }

                    Toast t = Utility.setToast(HomeActivity.this, "Payment Table Updated..");
                    t.show();

                } else if (response.code() == 403 || response.code() == 500) {
                    // customer_alertDialog.dismiss();
                    // Utility.dismissProgressDialog();
                    Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                } else {
                    // customer_alertDialog.dismiss();
                    // Utility.dismissProgressDialog();
                }


            }

            @Override
            public void onFailure(Call<ModePayment_TokenResponse> call, Throwable t) {

                Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

            }
        });


    }


    private void customerAddressDataFromServer() {

        //  Utility.showProgressDialog(HomeActivity.this, "Updating Address Table....");
        //   Call<Address_TokenResponse> address_tokenResponseCall = userInterface.getAddress(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Address?fields=%5B%22name%22%2C%22customer_name%22%2C%22address_title%22%2C%22address_line1%22%2C%22address_line2%22%2C%22company%22%2C%22city%22%5D");
        Call<Address_TokenResponse> address_tokenResponseCall = userInterface.getAddress(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Address?fields=%5B%22name%22%2C%22address_title%22%2C%22address_line1%22%2C%22address_line2%22%2C%22city%22%2C%22company%22%2C%22customer_name%22%5D&limit_page_length=100000");
        address_tokenResponseCall.enqueue(new Callback<Address_TokenResponse>() {
            @Override
            public void onResponse(Call<Address_TokenResponse> call, Response<Address_TokenResponse> response) {
                Log.i("ADDRESS_RESPONSE", "<==" + response.code());
                Log.i("ADDRESS_RESPONSE", "<==" + response.message());
                if (response.code() == 200) {

                    db.clearAddressTable();

                    Address_TokenResponse address_tokenResponse = response.body();
                    List<AddressData> addressData = address_tokenResponse.getData();
                    for (AddressData ad : addressData) {

                        Log.i("Address", "<==" + ad.getName());

                        db.addAddress(new AddressClass(ad.getAddressTitle(), ad.getName(), ad.getAddressLine1(), ad.getAddressLine2(), ad.getCompany(), ad.getCity(), "1"));

                    }

                    Toast t = Utility.setToast(HomeActivity.this, "Customer Address Updated..");
                    t.show();
                    //customer_alertDialog.dismiss();

                    //Utility.dismissProgressDialog();
                } else if (response.code() == 403 || response.code() == 500) {
                    customer_alertDialog.dismiss();
                    // Utility.dismissProgressDialog();
                    Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                } else {
                    customer_alertDialog.dismiss();
                    // Utility.dismissProgressDialog();
                }
            }


            @Override
            public void onFailure(Call<Address_TokenResponse> call, Throwable t) {
                customer_alertDialog.dismiss();
                //Utility.dismissProgressDialog();
                Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

            }
        });

        // Utility.dismissProgressDialog();
    }

    private void customerListDataFromServer() {


        // Utility.showProgressDialog(HomeActivity.this, "Updating Customer Table....");

        LayoutInflater li = LayoutInflater.from(HomeActivity.this);
        View promptsView = li.inflate(R.layout.progress_dialog, null);
        TextView txt = promptsView.findViewById(R.id.text_text);
        txt.setText("Updating Customer Info....");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false);
        customer_alertDialog = alertDialogBuilder.create();
        customer_alertDialog.show();

        // Call<Customer_TokenResponse> customer_tokenResponseCall = userInterface.getCustomer(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Customer?fields=%5B%22customer_id%22%2C%22name%22%2C%22customer_name%22%2C%22email_id%22%2C%22mobile_no%22%2C%22tax_id%22%2C%22customer_primary_contact%22%2C%22credit_limit%22%2C%22device_id%22%5D");
        Call<Customer_TokenResponse> customer_tokenResponseCall = userInterface.getCustomer(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Customer?filters=[[\"Customer\",\"Disabled\",\"=\",\"0\"]]&fields=%5B%22customer_id%22%2C%22name%22%2C%22customer_name%22%2C%22email_id%22%2C%22mobile_no%22%2C%22tax_id%22%2C%22customer_primary_contact%22%2C%22credit_limit%22%2C%22device_id%22%5D&limit_page_length=100000");
        customer_tokenResponseCall.enqueue(new Callback<Customer_TokenResponse>() {
            @Override
            public void onResponse(Call<Customer_TokenResponse> call, Response<Customer_TokenResponse> response) {
                Log.i("CUSTOMER_RESPONSE", "<==" + response.code());
                Log.i("CUSTOMER_RESPONSE", "<==" + response.message());
                if (response.code() == 200) {

                    db.clearCustomerTable();

                    Customer_TokenResponse customer_tokenResponse = response.body();
                    List<CustomerData> customerData = customer_tokenResponse.getData();

                    for (CustomerData cd : customerData) {

                        Log.i("CUSTOMER_NAME", "<==" + cd.getCustomerName());
                        Log.i("CUSTOMER_NAME1", "<==" + cd.getName());
                        Log.i("CUSTOMER_NAME1", "<==" + cd.getEmailId());
                        Log.i("CUSTOMER_NAME1", "<==" + cd.getCustomer_id());

                        db.addCustomer(new CustomerClass(cd.getName(), cd.getCustomerName(), cd.getEmailId(), cd.getMobileNo(), cd.getTaxId(), cd.getCustomerPrimaryContact(), cd.getDeviceId(), "0", "1", cd.getCreditLimit(), cd.getCustomer_id()));

                    }

                    Toast t = Utility.setToast(HomeActivity.this, "Customer list Updated..");
                    t.show();
                    // Utility.dismissProgressDialog();
                    customerAddressDataFromServer();
                    customerAssetListDataFromServer();


                } else if (response.code() == 403 || response.code() == 500) {
                    customer_alertDialog.dismiss();
                    Utility.dismissProgressDialog();
                    Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                } else {
                    customer_alertDialog.dismiss();
                    Utility.dismissProgressDialog();
                }

            }


            @Override
            public void onFailure(Call<Customer_TokenResponse> call, Throwable t) {
                customer_alertDialog.dismiss();
                //  Utility.dismissProgressDialog();
                Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

            }
        });

       // customerAssetListDataFromServer();
    }
    private void customerAssetListDataFromServer() {


        // Utility.showProgressDialog(HomeActivity.this, "Updating Customer Table....");

       /* LayoutInflater li = LayoutInflater.from(HomeActivity.this);
        View promptsView = li.inflate(R.layout.progress_dialog, null);
        TextView txt = promptsView.findViewById(R.id.text_text);
        txt.setText("Updating Customer Asset List Table....");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false);
        customer_alertDialog = alertDialogBuilder.create();
        customer_alertDialog.show();*/
        try {
            // Call<Customer_TokenResponse> customer_tokenResponseCall = userInterface.getCustomer(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Customer?fields=%5B%22customer_id%22%2C%22name%22%2C%22customer_name%22%2C%22email_id%22%2C%22mobile_no%22%2C%22tax_id%22%2C%22customer_primary_contact%22%2C%22credit_limit%22%2C%22device_id%22%5D");
            Call<CustomerAsset_TokenResponse> customerAsset_tokenResponseCall = userInterface.getCustomerAsset(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Asset%20Assignment?filters=%5B%5B%22Asset Assignment%22%2C%22disabled%22%2C%22%3D%22%2C%220%22%5D%5D&fields=%5B%22customer%22%2C%22asset%22%5D&limit_page_length=100000");
            customerAsset_tokenResponseCall.enqueue(new Callback<CustomerAsset_TokenResponse>() {
                @Override
                public void onResponse(Call<CustomerAsset_TokenResponse> call, Response<CustomerAsset_TokenResponse> response) {
                    Log.i("CUSTOMER_ASSET_RESPONSE", "<==" + response.code());
                    Log.i("CUSTOMER_ASSET_RESPONSE", "<==" + response.message());
                    if (response.code() == 200) {

                        db.clearCustomerAssetTable();

                        CustomerAsset_TokenResponse customerAsset_tokenResponse = response.body();
                        List<CustomerAssetData> customerAssetData = customerAsset_tokenResponse.getData();

                        for (CustomerAssetData cd : customerAssetData) {

                            Log.i("CUSTOMER_NAME", "<==" + cd.getCustomer());
                            Log.i("CUSTOMER_ASSET", "<==" + cd.getAsset());


                            db.addCustomerAsset(new CusomerAssetList(cd.getCustomer(), cd.getAsset()));

                        }
                        Toast t = Utility.setToast(HomeActivity.this, "Customer Asset List Updated..");
                        t.show();
                        customer_alertDialog.dismiss();


                    } else if (response.code() == 403 || response.code() == 500) {
                        customer_alertDialog.dismiss();
                        Utility.dismissProgressDialog();
                        Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                    } else {
                        customer_alertDialog.dismiss();
                        Utility.dismissProgressDialog();
                    }

                }


                @Override
                public void onFailure(Call<CustomerAsset_TokenResponse> call, Throwable t) {
                    customer_alertDialog.dismiss();
                    //  Utility.dismissProgressDialog();
                    Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

                }
            });
        }catch (Exception e){
            Utility.showDialog(HomeActivity.this, "ERROR !", e.getMessage()+" !\n Try Again....", R.color.dialog_network_error_background);
        }
        customer_alertDialog.dismiss();
    }


    private void callRetrofitBase(String storedUrl) {

        if (storedUrl.contains("http://")) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(storedUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();

        } else {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + storedUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }

        userInterface = retrofit.create(UserInterface.class);

    }


    private void transferClick() {

        if (mTransferAccessStatus != 0) {
            Toast t = Utility.setToast(HomeActivity.this, Utility.TOAST_COMING_SOON);
            t.show();

        } else {
            Utility.showDialog(HomeActivity.this, "Transfer", "Access Denied....", R.color.dialog_background);
        }

    }


    private void collectionClick() {

        if (mPaymentAccessStatus != 0) {

            startActivity(new Intent(HomeActivity.this, CollectionMainActivity.class));

        } else {
            Utility.showDialog(HomeActivity.this, "Collection", "Access Denied....", R.color.dialog_background);

        }

    }

    private void salesInvoiceClick() {

        if (mInvoiceAccessStatus != 0) {
            startActivity(new Intent(HomeActivity.this, SalesOrderInvoiceActivity.class));
        } else {
            Utility.showDialog(HomeActivity.this, "Sales Invoice", "Access Denied....", R.color.dialog_background);

        }

    }
    private void reportClick() {

        Intent rpt=new Intent(HomeActivity.this, DailyReportActivity.class);
        startActivity(rpt);
    }

    private void salesOrderClick() {

        if (mOrderAccessStatus != 0) {
                    startActivity(new Intent(HomeActivity.this, SalesOrderActivity.class));
        } else {
            Utility.showDialog(HomeActivity.this, "Sales Order", "Access Denied....", R.color.dialog_background);

        }

    }


    private void customerClick() {

        if (mCustomerAccessStatus != 0) {
            startActivity(new Intent(HomeActivity.this, CustomerActivity.class));
        } else {
            Utility.showDialog(HomeActivity.this, "Customer", "Access Denied....", R.color.dialog_background);

        }

    }

    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        HomeActivity.super.onBackPressed();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //    handler.removeCallbacksAndMessages(null);
                        startActivity(intent);
                    }
                }).create().show();


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
           // android.widget.Toast.makeText(this, location.getLatitude()+" \n"+location.getLongitude(), Toast.LENGTH_LONG).show();
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
       /*  if(googleApiClient!=null){
            googleApiClient.connect();
        }*/
    }
    @Override
    protected void onResume() {
        super.onResume();
        /* if(!checkPlayService()){
            Utility.setToast(this,"You need to install playstore").show();
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
       /* if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,  this);
            googleApiClient.disconnect();
        }*/
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
