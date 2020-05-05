package com.van.sale.vansale.SyncData;
import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Retrofit_Interface.UserInterface;
import com.van.sale.vansale.Retrofit_Model.AddressData;
import com.van.sale.vansale.Retrofit_Model.Address_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.Authentication_Response;
import com.van.sale.vansale.Retrofit_Model.CustomerAssetData;
import com.van.sale.vansale.Retrofit_Model.CustomerAsset_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.CustomerData;
import com.van.sale.vansale.Retrofit_Model.CustomerVisitRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.Customer_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.ItemData;
import com.van.sale.vansale.Retrofit_Model.ItemDetailData;
import com.van.sale.vansale.Retrofit_Model.ItemDetailUom;
import com.van.sale.vansale.Retrofit_Model.ItemDetail_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.Item_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.PaymentRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.PaymentSync_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw1_ItemData;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw1_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw_ItemData;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw_TaxData;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw_TokenResponse_1;
import com.van.sale.vansale.Util.Utility;

import com.van.sale.vansale.activity.HomeActivity;
import com.van.sale.vansale.model.AddressClass;
import com.van.sale.vansale.model.CusomerAssetList;
import com.van.sale.vansale.model.CustomerClass;
import com.van.sale.vansale.model.CustomerVisitLog;
import com.van.sale.vansale.model.ItemClass;
import com.van.sale.vansale.model.ItemDetailClass;
import com.van.sale.vansale.model.Payment;
import com.van.sale.vansale.model.SalesInvoiceRaw1_ItemPayments;
import com.van.sale.vansale.model.SalesInvoiceRaw_ItemPayments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.content.Intent;

import java.util.TimerTask;

public class AutoSyncTimerService extends Service{

    private Retrofit retrofit;
    private UserInterface userInterface;
    private String cookie;
    private String storedUrl;
    private SalesInvoiceRaw_TokenResponse invoiceRawTokenResponse;
    private SalesInvoiceRaw_TokenResponse_1 invoiceRawTokenResponse_1;
    private PaymentRaw_TokenResponse paymentRaw_tokenResponse;
    private CustomerVisitRaw_TokenResponse visitClass;
    private DatabaseHandler db;
    String  InvoiceNo="";
    OkHttpClient httpClient;
    private Float Sync_Tax_Rate = 0.0f, Sync_Tax_Amount = 0.0f, Sync_Total = 0.0f;
    private Float Sync_Tax_Rate1 = 0.0f, Sync_Tax_Amount1 = 0.0f, Sync_Total1 = 0.0f;
    Context  vanContext;
    private String Tax_Account_Head, COMPANY_NAME;
    static int si_data = 0, py_data = 0, sn_data = 0, customer_sync_status = 0, item_sync_status = 0,cv_data=0,cv_data1=0;
    Boolean isSalesSyncProgress=false;
    public static long NOTIFY_INTERVAL = 5 * 1000; // 5 seconds
    public  static final String TAG="AutoSync";
    int i=0;
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        Log.i("InCreate","Create");

        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();

                db = new DatabaseHandler(this);

                NOTIFY_INTERVAL=(db.getSyncInterval()*1000)*60;
                Tax_Account_Head = db.getAccountHead();
                COMPANY_NAME = db.getCompanyName();

                Log.i("Company",COMPANY_NAME);

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));

                httpClient = new OkHttpClient.Builder()
                        // .connectTimeout(20, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .cookieJar(cookieJar)
                        .addInterceptor(logging)
                        .build();

                storedUrl = db.getStoredUrl();

                callRetrofitBase(storedUrl);
                cookie = Utility.getPrefs("vansale_cookie", this);

                Log.i("StoredURL",storedUrl);
            }
            i = 0;
           Log.i("NOTIFY_INTERVAL",NOTIFY_INTERVAL+"");
            // schedule task
            mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);

    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    if(db.IsSalesAvailableToSync()||db.IsPaymentAvailableToSync()) {
                        if (IsNetworkAvailable()) {
                            Log.i(TAG, "In Schedulter " + i);
                            SyncStart();
                            i++;
                        } else {
                            Log.i(TAG, "Network not available");
                        }
                    }

                }

            });
        }


        private void SyncStart(){
            Call<Authentication_Response> authentication_responseCall = userInterface.authentication(db.getApiUsername(), db.getApiPassword());
            authentication_responseCall.enqueue(new Callback<Authentication_Response>(){
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
                        Utility.setPrefs("vansale_cookie", cookie, AutoSyncTimerService.this);
                        syncSalesInvoiceFromLocalToServerNew();
                        syncPaymentFromLocalToServer();
                        //syncCustomerListDataFromServer();


                    }
                }
                @Override
                public void onFailure(Call<Authentication_Response> call, Throwable t) {
                    Log.i(TAG,"NETWORK ERROR !");
                }
            });

        }

    }

    private Boolean IsNetworkAvailable() {
        boolean haveWifi = false;
        boolean haveMobileData = false;
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] activeNetworkInfo = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo info : activeNetworkInfo) {
            if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                if (info.isConnected())
                    haveWifi = true;
            } else {
                if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
                    if (info.isConnected())
                        haveMobileData = true;
                }
            }

        }
        return haveWifi || haveMobileData;
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
    private void customerAddressDataFromServer() {


        Call<Address_TokenResponse> address_tokenResponseCall = userInterface.getAddress(Utility.getPrefs("vansale_cookie", vanContext), "api/resource/Address?fields=%5B%22name%22%2C%22address_title%22%2C%22address_line1%22%2C%22address_line2%22%2C%22city%22%2C%22company%22%2C%22customer_name%22%5D&limit_page_length=100000");
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
                } else if (response.code() == 403 || response.code() == 500) {
                    Log.i(TAG,"Something went wrong!\n Try Again...");

                }
            }


            @Override
            public void onFailure(Call<Address_TokenResponse> call, Throwable t) {
                Log.i(TAG,"NETWORK ERROR !");
            }
        });

        // Utility.dismissProgressDialog();
    }
    private void syncCustomerListDataFromServer() {
        Call<Customer_TokenResponse> customer_tokenResponseCall = userInterface.getCustomer(Utility.getPrefs("vansale_cookie", vanContext), "api/resource/Customer?filters=[[\"Customer\",\"Disabled\",\"=\",\"0\"]]&fields=%5B%22customer_id%22%2C%22name%22%2C%22customer_name%22%2C%22email_id%22%2C%22mobile_no%22%2C%22tax_id%22%2C%22customer_primary_contact%22%2C%22credit_limit%22%2C%22device_id%22%5D&limit_page_length=100000");
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


                    // Utility.dismissProgressDialog();
                    customerAddressDataFromServer();
                    customerAssetListDataFromServer();


                } else if (response.code() == 403 || response.code() == 500) {

                    Log.i(TAG,"Something went wrong!\n Try Again...");

                }

            }


            @Override
            public void onFailure(Call<Customer_TokenResponse> call, Throwable t) {

                Log.i(TAG,"NETWORK ERROR");

            }
        });

        // customerAssetListDataFromServer();
    }
    private void customerAssetListDataFromServer() {



        try {

            Call<CustomerAsset_TokenResponse> customerAsset_tokenResponseCall = userInterface.getCustomerAsset(Utility.getPrefs("vansale_cookie", vanContext), "api/resource/Asset%20Assignment?filters=%5B%5B%22Asset Assignment%22%2C%22disabled%22%2C%22%3D%22%2C%220%22%5D%5D&fields=%5B%22customer%22%2C%22asset%22%5D&limit_page_length=100000");
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

                    } else if (response.code() == 403 || response.code() == 500) {
                        Log.i(TAG,"Something went wrong!\n Try Again...");

                    }

                }


                @Override
                public void onFailure(Call<CustomerAsset_TokenResponse> call, Throwable t) {
                    Log.i(TAG,"NETWORK ERROR !");
                }
            });
        }catch (Exception e){
            Log.i(TAG,e.getMessage());
        }

    }
    private void syncItemListDataFromServer() {

        db.clearItemTable();
        db.clearItemDetailTable();



        // Utility.showProgressDialog(HomeActivity.this, "Updating Item Table....");

        // Call<Item_TokenResponse> item_tokenResponseCall = userInterface.getItem(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Item?fields=%5B%22item_code%22%2C%22item_name%22%2C%22description%22%2C%22item_group%22%2C%22brand%22%2C%22barcode%22%2C%22stock_uom%22%5D");
        // Call<Item_TokenResponse> item_tokenResponseCall = userInterface.getItem(Utility.getPrefs("vansale_cookie", HomeActivity.this), "api/resource/Item?fields=%5B%22item_code%22%2C%22item_name%22%2C%22description%22%2C%22item_group%22%2C%22brand%22%2C%22barcode%22%2C%22stock_uom%22%5D&limit_page_length=100000");
        Call<Item_TokenResponse> item_tokenResponseCall = userInterface.getItem(Utility.getPrefs("vansale_cookie", vanContext), "api/resource/Item?filters=[[\"Item\",\"disabled\",\"=\",\"0\"],[\"Item\",\"is_sales_item\",\"=\",\"1\"],[\"Item\",\"is_fixed_asset\",\"=\",\"0\"]]&fields=[\"item_code\",\"item_name\",\"description\",\"item_group\",\"brand\",\"barcode\",\"stock_uom\"]&limit_page_length=100000");
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
                                  //  item_alertDialog.dismiss();
                                   // Utility.showDialog(HomeActivity.this, "ERROR !", "Something went wrong!\n Try Again...", R.color.dialog_error_background);

                                } else {
                                    // Utility.dismissProgressDialog();
                                   // item_alertDialog.dismiss();
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
                   // item_alertDialog.dismiss();
                    //Utility.showDialog(HomeActivity.this, "ERROR !", "Session Timeout !\n Settings Reset Reccomented...", R.color.dialog_error_background);

                } else {
                    // Utility.dismissProgressDialog();
                    //item_alertDialog.dismiss();
                }

                //item_alertDialog.dismiss();
            }

            private void itemSyncMsg() {

                if (item_sync_status == 0) {
                    //item_alertDialog.dismiss();
                  //  Toast t = Utility.setToast(HomeActivity.this, "Item Table Updated");
                  //  t.show();

                }


            }

            @Override
            public void onFailure(Call<Item_TokenResponse> call, Throwable t) {
                //item_alertDialog.dismiss();
                // Utility.dismissProgressDialog();
               // Utility.showDialog(HomeActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);
            }
        });
        //item_alertDialog.dismiss();
        //Utility.dismissProgressDialog();
    }
    private void syncSalesInvoiceFromLocalToServerNew() {

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

                    Sync_Tax_Amount1 = 0.0f;
                    Sync_Total1 = 0.0f;

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

                            Call<SalesInvoiceRaw_TokenResponse> salesInvoiceRaw_tokenResponseCall = userInterface.salesInvoicePost(Utility.getPrefs("vansale_cookie", this), "api/resource/Sales Invoice", invoiceRawTokenResponse);


                            salesInvoiceRaw_tokenResponseCall.enqueue(new Callback<SalesInvoiceRaw_TokenResponse>() {
                                @Override
                                public void onResponse(Call<SalesInvoiceRaw_TokenResponse> call, Response<SalesInvoiceRaw_TokenResponse> response) {

                                    Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                    Log.i("INVOICE_RESPONSE", "<==" + response.message());

                                    if (response.code() == 200) {

                                        db.updateSalesInvoiceSyncStatus(String.valueOf(rt.getKey_id()));
                                        if (si_data == 0)
                                            syncMsg();
                                    } else if (response.code() == 403 || response.code() == 500) {
                                        Log.i(TAG,"Something went wrong!\n Try Again...");

                                    }

                                }

                                private void syncMsg() {

                                    if (si_data == 0) {

                                        syncAllCustomerVisitFromLocalToServer();
                                        isSalesSyncProgress=false;
                                    }

                                }

                                @Override
                                public void onFailure(Call<SalesInvoiceRaw_TokenResponse> call, Throwable t) {
                                    Log.i(TAG,"NETWORK ERROR !");
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
                        taxes.add(new SalesInvoiceRaw_TaxData(Tax_Account_Head, "VAT", "On Net Total", String.valueOf(Sync_Total1), String.valueOf(Sync_Tax_Amount1), String.valueOf(Sync_Tax_Rate1),rt.getOwner(),rt.getOwner()));
                        invoiceRawTokenResponse_1.setTaxes(taxes);


                        if (!rt.getItems().isEmpty()) {

                            Call<SalesInvoiceRaw_TokenResponse_1> salesInvoiceRaw_tokenResponseCall = userInterface.salesInvoicePost1(Utility.getPrefs("vansale_cookie", this), "api/resource/Sales Invoice", invoiceRawTokenResponse_1);
                            salesInvoiceRaw_tokenResponseCall.enqueue(new Callback<SalesInvoiceRaw_TokenResponse_1>() {
                                @Override
                                public void onResponse(Call<SalesInvoiceRaw_TokenResponse_1> call, Response<SalesInvoiceRaw_TokenResponse_1> response) {

                                    Log.i("INVOICE_RESPONSE", "<==" + response.code());
                                    Log.i("INVOICE_RESPONSE", "<==" + response.message());

                                    if (response.code() == 200) {

                                        db.updateSalesInvoiceSyncStatus(String.valueOf(rt.getKey_id()));
                                        if (si_data == 0)
                                            syncMsg();
                                    } else if (response.code() == 403 || response.code() == 500) {
                                        Log.i(TAG,"Something went wrong!\n Try Again...");

                                    }

                                }

                                private void syncMsg() {

                                    if (si_data == 0) {
                                        syncAllCustomerVisitFromLocalToServer();
                                        isSalesSyncProgress=false;
                                    }

                                }

                                @Override
                                public void onFailure(Call<SalesInvoiceRaw_TokenResponse_1> call, Throwable t) {
                                    Log.i(TAG,"NETWORK ERROR !");
                                    isSalesSyncProgress=false;
                                }
                            });
                        } else {

                            db.deleteSalesInvoice(Integer.parseInt(String.valueOf(rt.getKey_id())));

                        }


                    }


                }
            }

        }catch (Exception e){
            Log.i(TAG,e.getMessage());
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


                    Call<CustomerVisitRaw_TokenResponse> customerVisitRaw_tokenResponseCall = userInterface.CustomerVisitPost(Utility.getPrefs("vansale_cookie", this), "api/resource/Customer%20Visit%20Log", visitClass);
                    customerVisitRaw_tokenResponseCall.enqueue(new Callback<CustomerVisitRaw_TokenResponse>() {
                        @Override
                        public void onResponse(Call<CustomerVisitRaw_TokenResponse> call, Response<CustomerVisitRaw_TokenResponse> response) {

                            Log.i("INVOICE_RESPONSE", "<==" + response.code());
                            Log.i("INVOICE_RESPONSE", "<==" + response.message());


                            if (response.code() == 200) {

                                db.updateCustomerVisitSyncStatus(String.valueOf(cv.getCUSTOMER_VISIT_KEY_ID()));

                            } else if (response.code() == 403 || response.code() == 500) {
                                Log.i(TAG,"Something went wrong!\n Try Again...");
                            }


                        }


                        @Override
                        public void onFailure(Call<CustomerVisitRaw_TokenResponse> call, Throwable t) {
                            Log.i(TAG,"NETWORK ERROR !");

                        }
                    });


                }
            }

        }catch (Exception e){
            Log.i(TAG,e.getMessage());
        }
    }
    private void syncPaymentFromLocalToServer() {

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
                paymentRaw_tokenResponse.setPosting_date(p.getPAYMENT_CREATION());
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


                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getOwner());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getDocstatus());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getReceipt_no());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getCreation());
                Log.i("PAY", "<==" + paymentRaw_tokenResponse.getPosting_date());
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

                Call<PaymentSync_TokenResponse> tokenResponseCall = userInterface.paymentPost(Utility.getPrefs("vansale_cookie", this), "api/resource/Payment Entry", paymentRaw_tokenResponse);
                tokenResponseCall.enqueue(new Callback<PaymentSync_TokenResponse>() {
                    @Override
                    public void onResponse(Call<PaymentSync_TokenResponse> call, Response<PaymentSync_TokenResponse> response) {
                        Log.i("PAYMENT_RESPONSE", "<==" + response.code());
                        Log.i("PAYMENT_RESPONSE", "<==" + response.message());
                        Log.i("PAYMENT_RESPONSE", "<==" + response.errorBody());
                        Log.i("PAYMENT_RESPONSE", "<==" + response.headers());

                        if (response.code() == 200) {

                            db.updatePaymentSyncStatus(String.valueOf(p.getKEY_ID()));


                        } else if (response.code() == 403 || response.code() == 500) {
                            Log.i(TAG,"Something went wrong!\n Try Again...");


                        } else {

                        }

                    }

                    @Override
                    public void onFailure(Call<PaymentSync_TokenResponse> call, Throwable t) {
                        Log.i(TAG,"NETWORK ERROR !");
                    }
                });


            }
        }


    }
}
