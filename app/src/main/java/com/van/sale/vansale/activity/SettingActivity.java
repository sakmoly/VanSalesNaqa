package com.van.sale.vansale.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.van.sale.vansale.Authentication.ServiceGenerator;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.Database.UserClass;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Retrofit_Interface.UserInterface;
import com.van.sale.vansale.Retrofit_Model.Authentication_Response;
import com.van.sale.vansale.Retrofit_Model.SuccessAuth_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.UserResponseData;
import com.van.sale.vansale.Retrofit_Model.User_TokenResponse;
import com.van.sale.vansale.UIHelper;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.Fields;
import com.van.sale.vansale.model.Password;
import com.van.sale.vansale.model.SettingsClass;
import com.van.sale.vansale.zebraPrinter.BluetoothDiscovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mCloseButton;
    private EditText default_payment_mode,mCompanyName,mTaxAccountHead,mNameSalesOrder, mNameSalesInvoice, mNameSalesPayment, mNameSalesTransfer,
            mDocSalesOrder, mDocSalesInvoice, mDocSalesPayment, mDocSalesTransfer, mDefaultCurrency,
            mDefaultCreditLimit, mTaxRate, mSettingAccessPassword, mUrl, mApiUserName, mApiPassword, Edt_warehouse,paid_from,
            mBluetoothMac,mIPAddress,mPortNo,mCustomerVisit,mSyncInterval,mDefaultTransQty;
    private CheckBox mCustomerAccessCheck, mOrderAccessCheck, mInvoiceAccessCheck, mPaymentAccessCheck, mTransferAccessCheck;
    private Button mCancelButton, mSaveButton;
    private TextView tv_device_id;
    private ImageView iv_bluetoothSearch;
    private RadioButton rbBluetooth,rbIpAddress;
    private Retrofit retrofit;
    private UserInterface userInterface;
    private DatabaseHandler db;
    private int mCustomerAccessStatus = 0, mOrderAccessStatus = 0, mInvoiceAccessStatus = 0, mPaymentAccessStatus = 0, mTransferAccessStatus = 0;
    private String device_id, warehouse_name;
    private List<UserResponseData> responseData;
    OkHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        db = new DatabaseHandler(this);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(SettingActivity.this));

        httpClient = new OkHttpClient.Builder()
                /* .connectTimeout(20, TimeUnit.SECONDS)
                 .writeTimeout(20, TimeUnit.SECONDS)
                 .readTimeout(30, TimeUnit.SECONDS)*/
                .cookieJar(cookieJar)
                .addInterceptor(logging)
                .build();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("SETTINGS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        tv_device_id = (TextView) findViewById(R.id.tv_device_id);
        mNameSalesOrder = (EditText) findViewById(R.id.name_sales_order);
        Edt_warehouse = (EditText) findViewById(R.id.Edt_warehouse);
        mNameSalesInvoice = (EditText) findViewById(R.id.name_sales_invoice);
        mNameSalesPayment = (EditText) findViewById(R.id.name_sales_payment);
        mNameSalesTransfer = (EditText) findViewById(R.id.name_sales_transfer);
        mDocSalesOrder = (EditText) findViewById(R.id.doc_sales_order);
        mDocSalesInvoice = (EditText) findViewById(R.id.doc_sales_invoice);
        mDocSalesPayment = (EditText) findViewById(R.id.doc_payment);
        mDocSalesTransfer = (EditText) findViewById(R.id.doc_transfer);
        mDefaultCurrency = (EditText) findViewById(R.id.default_currency);
        mDefaultCreditLimit = (EditText) findViewById(R.id.default_credit_limit);
        mTaxRate = (EditText) findViewById(R.id.tax_rate);
        mSettingAccessPassword = (EditText) findViewById(R.id.setting_access_password);
        mUrl = (EditText) findViewById(R.id.url);
        mCompanyName = (EditText) findViewById(R.id.company_name_edt);
        mTaxAccountHead = (EditText) findViewById(R.id.tax_account_head_edt);
        mApiUserName = (EditText) findViewById(R.id.api_username);
        mApiPassword = (EditText) findViewById(R.id.api_password);
        paid_from = (EditText) findViewById(R.id.paid_from);
        default_payment_mode = (EditText) findViewById(R.id.default_payment_mode);
        mCustomerVisit=(EditText)findViewById(R.id.name_customer_visit);
        mSyncInterval=(EditText)findViewById(R.id.sync_interval);
        mDefaultTransQty=(EditText)findViewById(R.id.default_trans_qty);

        mCancelButton = (Button) findViewById(R.id.cancel_request);
        mSaveButton = (Button) findViewById(R.id.save_request);

        mCustomerAccessCheck = (CheckBox) findViewById(R.id.customer_access_check);
        mOrderAccessCheck = (CheckBox) findViewById(R.id.order_access_check);
        mInvoiceAccessCheck = (CheckBox) findViewById(R.id.invoice_access_check);
        mPaymentAccessCheck = (CheckBox) findViewById(R.id.payment_access_check);
        mTransferAccessCheck = (CheckBox) findViewById(R.id.transfer_access_check);

        mBluetoothMac=(EditText)findViewById(R.id.bluetooth_mac_address);
        mIPAddress=(EditText)findViewById(R.id.ip_address);
        mPortNo=(EditText)findViewById(R.id.port_no);

        rbBluetooth=(RadioButton)findViewById(R.id.bluetoothRadio);
        rbIpAddress=(RadioButton)findViewById(R.id.ipDnsRadio);

        iv_bluetoothSearch=(ImageView)findViewById(R.id.bluetooth_search_image);

        tv_device_id.setText(Utility.getDeviceId(SettingActivity.this));
        try{
        List<SettingsClass> getSettings = db.getSettings();

        if (!getSettings.isEmpty()) {


            Log.i("TT", "<==" + getSettings.get(0).getNaming_series_sales_order());
            Log.i("TT", "<==" + getSettings.get(0).getAPI_Password());
            Log.i("TT", "<==" + getSettings.get(0).getAPI_User_Name());
            Log.i("TT", "<==" + getSettings.get(0).getDevice_id());
            Log.i("TT", "<==" + getSettings.get(0).getURL_string());
            Log.i("TT", "<==" + getSettings.get(0).getWarehouse());
            Log.i("TT", "<==" + getSettings.get(0).getLast_doc_no_payment());
            Log.i("TTr", "<==" + getSettings.get(0).getTransfer_access());
            Log.i("TTp", "<==" + getSettings.get(0).getPayment_access());
            Log.i("TTc", "<==" + getSettings.get(0).getCustomer_access());
            Log.i("TTsi", "<==" + getSettings.get(0).getSales_invoice_access());
            Log.i("TTso", "<==" + getSettings.get(0).getSales_order_access());
            Log.i("TT", "<==" + getSettings.get(0).getDefault_currency());
            Log.i("TT", "<==" + getSettings.get(0).getAPI_User_Name());
            //     Log.i("TT", "<==" + getSettings.get(0).getPrinter_mac_address());
            /*for (SettingsClass s : getSettings) {

                Log.i("TT", "<==" + s.getNaming_series_sales_order());
                Log.i("TT", "<==" + s.getAPI_Password());
                Log.i("TT", "<==" + s.getAPI_User_Name());
                Log.i("TT", "<==" + s.getDevice_id());
                Log.i("TT", "<==" + s.getURL_string());
                Log.i("TT", "<==" + s.getWarehouse());
                Log.i("TT", "<==" + s.getLast_doc_no_payment());
                Log.i("TTr", "<==" + s.getTransfer_access());
                Log.i("TTp", "<==" + s.getPayment_access());
                Log.i("TTc", "<==" + s.getCustomer_access());
                Log.i("TTsi", "<==" + s.getSales_invoice_access());
                Log.i("TTso", "<==" + s.getSales_order_access());
                Log.i("TT", "<==" + s.getDefault_currency());
                Log.i("TT", "<==" + s.getAPI_User_Name());

            }*/

            mNameSalesOrder.setText(getSettings.get(0).getNaming_series_sales_order());
            mNameSalesInvoice.setText(getSettings.get(0).getNaming_series_sales_invoice());
            mNameSalesPayment.setText(getSettings.get(0).getNaming_series_payment());
            mNameSalesTransfer.setText(getSettings.get(0).getNaming_series_transfer());
            mDocSalesOrder.setText(String.valueOf(getSettings.get(0).getLast_doc_no_sales_order()));
            mDocSalesInvoice.setText(String.valueOf(getSettings.get(0).getLast_doc_no_sales_invoice()));
            mDocSalesPayment.setText(String.valueOf(getSettings.get(0).getLast_doc_no_payment()));
            mDocSalesTransfer.setText(String.valueOf(getSettings.get(0).getLast_doc_no_transfer()));
            mDefaultCurrency.setText(getSettings.get(0).getDefault_currency());
            mDefaultCreditLimit.setText(String.valueOf(getSettings.get(0).getDefault_credit_limit_for_new_customer()));
            mTaxRate.setText(String.valueOf(getSettings.get(0).getTax_rate()));
            mSettingAccessPassword.setText(getSettings.get(0).getSetting_access_password());
            mUrl.setText(getSettings.get(0).getURL_string());
            mApiUserName.setText(getSettings.get(0).getAPI_User_Name());
            mApiPassword.setText(getSettings.get(0).getAPI_Password());
            Edt_warehouse.setText(getSettings.get(0).getWarehouse());
            tv_device_id.setText(getSettings.get(0).getDevice_id());
            mCompanyName.setText(getSettings.get(0).getCompany_name());
            mTaxAccountHead.setText(getSettings.get(0).getTax_account_head());
            paid_from.setText(getSettings.get(0).getSETTINGS_PAID_FROM());
            default_payment_mode.setText(getSettings.get(0).getSETTINGS_DEFAULT_PAYMENT_MODE());
            mBluetoothMac.setText(getSettings.get(0).getPrinter_mac_address());
            mDefaultTransQty.setText(String.valueOf(getSettings.get(0).getDefault_trans_qty()));

            if (Integer.valueOf(getSettings.get(0).getPrinter_type()) == 1) {
                rbBluetooth.setChecked(true);
                mIPAddress.setEnabled(false);
                mPortNo.setEnabled(false);
                mBluetoothMac.setText(getSettings.get(0).getPrinter_mac_address());
            } else if (Integer.valueOf(getSettings.get(0).getPrinter_type()) == 2) {
                rbIpAddress.setChecked(true);
                mIPAddress.setEnabled(true);
                mPortNo.setEnabled(true);
                mBluetoothMac.setEnabled(false);
                mIPAddress.setText(getSettings.get(0).getPrinter_ip_address());
                mPortNo.setText(getSettings.get(0).getPrinter_port_no());
            }

            if (getSettings.get(0).getCustomer_access() == 1) {
                mCustomerAccessCheck.setChecked(true);
                mCustomerAccessStatus = 1;
            }

            if (getSettings.get(0).getSales_order_access() == 1) {
                mOrderAccessCheck.setChecked(true);
                mOrderAccessStatus = 1;
            }

            if (getSettings.get(0).getSales_invoice_access() == 1) {
                mInvoiceAccessCheck.setChecked(true);
                mInvoiceAccessStatus = 1;
            }

            if (getSettings.get(0).getPayment_access() == 1) {
                mPaymentAccessCheck.setChecked(true);
                mPaymentAccessStatus = 1;
            }

            if (getSettings.get(0).getTransfer_access() == 1) {
                mTransferAccessCheck.setChecked(true);
                mTransferAccessStatus = 1;
            }
            mCustomerVisit.setText(getSettings.get(0).getNaming_series_customer_visit());
            mSyncInterval.setText(String.valueOf(getSettings.get(0).getSync_interval()));
        }
        }catch(Exception e){
            (new UIHelper(this)).showErrorDialogOnGuiThread(e.getMessage());
            return;

        }



        mCustomerAccessCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    mCustomerAccessStatus = 1;
                } else {
                    mCustomerAccessStatus = 0;
                }

            }
        });

        mOrderAccessCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mOrderAccessStatus = 1;
                } else {
                    mOrderAccessStatus = 0;
                }

            }
        });

        mInvoiceAccessCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    mInvoiceAccessStatus = 1;
                } else {
                    mInvoiceAccessStatus = 0;
                }
            }
        });

        mPaymentAccessCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    mPaymentAccessStatus = 1;
                } else {
                    mPaymentAccessStatus = 0;
                }
            }
        });

        mTransferAccessCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    mTransferAccessStatus = 1;
                } else {
                    mTransferAccessStatus = 0;
                }
            }
        });


        mCancelButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        iv_bluetoothSearch.setOnClickListener(this);
    }
//Menu for default settings update
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.UpdateDefaultSettings:
            setDefault();
                break;
            case R.id.ClearTransactions:
                AlertDialog.Builder dialog=new AlertDialog.Builder(SettingActivity.this);
                dialog.setMessage("Are you sure?");
                dialog.setTitle("Confirm");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.clearSalesInvoice();
                        db.clearPayments();
                        Utility.setToast(SettingActivity.this,"Transactions cleared Successfully").show();
                    }
                })
                 .setNegativeButton("No",null);
                dialog.show();
                break;
           // default:
              //  return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {

            case R.id.cancel_request:
                closeButtonClick();
                break;

            case R.id.save_request:
                saveButtonClick();
                break;
            case R.id.bluetooth_search_image:
                searchBluetooth();
                break;
        }

    }
    private void searchBluetooth(){
        try{
            Intent intent = new Intent(this, BluetoothDiscovery.class);
            startActivity(intent);
        }
        catch (Exception e){
            Utility.setToast(this,e.getMessage()).show();
        }


    }
    private void setDefault()    {
        mNameSalesOrder.setText("SO-");
        mNameSalesInvoice.setText("IN-");
        mNameSalesPayment.setText("PE-");
        mNameSalesTransfer.setText("WT-");
        mCustomerVisit.setText("CV-");
        mDocSalesOrder.setText("0");
        mDocSalesInvoice.setText("0");
        mDocSalesPayment.setText("0");
        mDocSalesTransfer.setText("0");
        mDefaultCurrency.setText("SAR");
        mDefaultCreditLimit.setText("25000");
        mTaxRate.setText("5");
        mSettingAccessPassword.setText("print@van");
        mUrl.setText("http://23.239.220.206:99");
        mApiUserName.setText("apiuser@saqiya.com");
        mApiPassword.setText("api");
        Edt_warehouse.setText("ABB-5249 - SAQYA");
        //tv_device_id.setText();
        mCompanyName.setText("Al Naqaa Al Mubtakir Ice Factory");
        mTaxAccountHead.setText("1.01.07.0001 - VAT Receivable - SAQYA");
        paid_from.setText("1.01.01.0001 - Client Receivable - SAQYA");
        default_payment_mode.setText("Cash Vehicle");
        mSyncInterval.setText("0");
        mCustomerAccessCheck.setChecked(true);
        mOrderAccessCheck.setChecked(true);
        mPaymentAccessCheck.setChecked(true);
        mTransferAccessCheck.setChecked(true);
        mInvoiceAccessCheck.setChecked(true);
        mDefaultTransQty.setText("1");

    }
    private void saveButtonClick() {

        db.clearSettingTable();
        db.clearPasswordTable();
        String printer_type="1";
        String printer_ip="";
        String bluetoothMac=mBluetoothMac.getText().toString();
        String printer_port=mPortNo.getText().toString();
if(rbIpAddress.isChecked())
    printer_type="2";
        if (!mNameSalesOrder.getText().toString().isEmpty()) {
            if (!mNameSalesInvoice.getText().toString().isEmpty()) {
                if (!mNameSalesPayment.getText().toString().isEmpty()) {
                    if (!mNameSalesTransfer.getText().toString().isEmpty()) {
                        if (!mDocSalesOrder.getText().toString().isEmpty()) {
                            if (!mDocSalesInvoice.getText().toString().isEmpty()) {
                                if (!mDocSalesPayment.getText().toString().isEmpty()) {
                                    if (!mDocSalesTransfer.getText().toString().isEmpty()) {
                                        if (!mDefaultCurrency.getText().toString().isEmpty()) {
                                            if (!mDefaultCreditLimit.getText().toString().isEmpty()) {
                                                if (!mTaxRate.getText().toString().isEmpty()) {
                                                    if (!Edt_warehouse.getText().toString().isEmpty()) {
                                                        if (!mSettingAccessPassword.getText().toString().isEmpty()) {
                                                            if (!mUrl.getText().toString().isEmpty()) {
                                                                if (!mCompanyName.getText().toString().isEmpty()) {
                                                                    if (!mTaxAccountHead.getText().toString().isEmpty()) {
                                                                        if (!mApiUserName.getText().toString().isEmpty()) {
                                                                            if (!mApiPassword.getText().toString().isEmpty()) {
                                                                                if (!default_payment_mode.getText().toString().isEmpty()) {
                                                                                    if (!mSyncInterval.getText().toString().isEmpty()) {
                                                                                        if(Integer.parseInt(mSyncInterval.getText().toString())==0 ||Integer.parseInt(mSyncInterval.getText().toString())>4){

                                                                                        callRetrofitBase(mUrl.getText().toString());

                                                                                        db.addSettings(new SettingsClass(
                                                                                                mCompanyName.getText().toString(),
                                                                                                mTaxAccountHead.getText().toString(),
                                                                                                mNameSalesOrder.getText().toString(),
                                                                                                mNameSalesInvoice.getText().toString(),
                                                                                                mNameSalesPayment.getText().toString(),
                                                                                                mNameSalesTransfer.getText().toString(),
                                                                                                mDefaultCurrency.getText().toString(),
                                                                                                tv_device_id.getText().toString(),
                                                                                                Edt_warehouse.getText().toString(),
                                                                                                mSettingAccessPassword.getText().toString(),
                                                                                                mUrl.getText().toString(),
                                                                                                mApiUserName.getText().toString(),
                                                                                                mApiPassword.getText().toString(),
                                                                                                Integer.parseInt(mDocSalesOrder.getText().toString()),
                                                                                                Integer.parseInt(mDocSalesInvoice.getText().toString()),
                                                                                                Integer.parseInt(mDocSalesPayment.getText().toString()),
                                                                                                Integer.parseInt(mDocSalesTransfer.getText().toString()),
                                                                                                mCustomerAccessStatus,
                                                                                                mOrderAccessStatus,
                                                                                                mInvoiceAccessStatus,
                                                                                                mPaymentAccessStatus,
                                                                                                mTransferAccessStatus,
                                                                                                Float.parseFloat(mDefaultCreditLimit.getText().toString()),
                                                                                                Float.parseFloat(mTaxRate.getText().toString()),
                                                                                                paid_from.getText().toString(),
                                                                                                default_payment_mode.getText().toString(),
                                                                                                printer_type, printer_ip, printer_port, bluetoothMac,
                                                                                                mCustomerVisit.getText().toString(), 0,
                                                                                                Integer.parseInt(mSyncInterval.getText().toString()),
                                                                                                Float.parseFloat(mDefaultTransQty.getText().toString())


                                                                                        ));

                                                                                        db.addPassword(new Password(mSettingAccessPassword.getText().toString()));


                                                                                        Utility.showProgressDialog(SettingActivity.this, "Activating server....");

                                                                                        try {

                                                                                            final Call<Authentication_Response> authentication_responseCall = userInterface.authentication(mApiUserName.getText().toString(), mApiPassword.getText().toString());
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

// fetch cookie from login response as hashmap
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
 /*set append cookies for future use : we can pass multiple cookies as single string with separated by";"
                  pls check UserInterface there use @Header("Cookie") type*/

                                                                                                        String cookie = "user_id=" + cookieHashmap.get("user_id") + ";full_name=" + cookieHashmap.get("full_name") + ";sid=" + cookieHashmap.get("sid");

                                                                                                        Utility.setPrefs("vansale_cookie", cookie, SettingActivity.this);

                                                                                                        Utility.showProgressDialog(SettingActivity.this, "Updating database....");


                                                                                                        Call<User_TokenResponse> user_tokenResponseCall = userInterface.getUser(cookie, "api/resource/User?filters=[[\"User\",\"Enabled\",\"=\",\"1\"]]&fields=%5B%22username%22%2C%22first_name%22%2C%22last_name%22%2C%22password_for_mob_apps%22%2C%22full_name%22%5D");
                                                                                                        //  Call<User_TokenResponse> user_tokenResponseCall = userInterface.getUser("%5B%22first_name%22%2C%22last_name%22%2C%22full_name%22%2C%22username%22%5D");
                                                                                                        //  Call<User_TokenResponse> user_tokenResponseCall = userInterface.getUser();
                                                                                                        user_tokenResponseCall.enqueue(new Callback<User_TokenResponse>() {
                                                                                                            @Override
                                                                                                            public void onResponse(Call<User_TokenResponse> call, Response<User_TokenResponse> response) {
                                                                                                                Log.i("USER_RESPONSE", "<==" + response.code());
                                                                                                                Log.i("USER_RESPONSE", "<==" + response.message());
                                                                                                                Log.i("USER_RESPONSE", "<==" + response.errorBody());
                                                                                                                Log.i("USER_RESPONSE", "<==" + response.headers());

                                                                                                                //  getAuthStatus();

                                                                                                                if (response.code() == 200) {

                                                                                                                    db.clearUserTable();

                                                                                                                    User_TokenResponse user_tokenResponse = response.body();

                                                                                                                    if (user_tokenResponse.getSession_expired().equals("1")) {

                                                                                                                        Utility.showDialog(SettingActivity.this, "ERROR !", "Session Expired !\n Activate Server Recomented....", R.color.dialog_error_background);


                                                                                                                    } else {

                                                                                                                        responseData = user_tokenResponse.getData();
                                                                                                                        Log.i("USER_DATA_SIZE", "<==" + responseData.size());

                                                                                                                        for (UserResponseData ud : responseData) {

                                                                                                                            Log.i("RES_FIRST", "<==" + ud.getFirstName());
                                                                                                                            Log.i("RES_LAST", "<==" + ud.getLastName());

                                                                                                                            db.addUser(new UserClass(ud.getUsername(), ud.getFirstName(), ud.getLastName(), ud.getPasswordForMobApps(), ud.getFullName()));

                                                                                                                        }

                                                                                                                        Utility.dismissProgressDialog();
                                                                                                                        finish();
                                                                                                                        Utility.setToast(SettingActivity.this, "Database updated");

                                                                                                                    }
                                                                                                                }

                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onFailure(Call<User_TokenResponse> call, Throwable t) {
                                                                                                                Utility.dismissProgressDialog();
                                                                                                                Utility.showDialog(SettingActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

                                                                                                            }
                                                                                                        });


                                                                                                    }


                                                                                                }

                                                                                                @Override
                                                                                                public void onFailure(Call<Authentication_Response> call, Throwable t) {
                                                                                                    Utility.dismissProgressDialog();
                                                                                                    Utility.showDialog(SettingActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

                                                                                                }
                                                                                            });
                                                                                        }catch (Exception ex){
                                                                                            Utility.showDialog(SettingActivity.this, "ERROR !", ex.getMessage(), R.color.dialog_network_error_background);
                                                                                        }

                                                                                    }
                                                                                        else {
                                                                                            Utility.showDialog(SettingActivity.this, "ERROR !", "Default sync interval required", R.color.dialog_error_background);
                                                                                        }
                                                                                }
                                                                                    else {
                                                                                        Utility.showDialog(SettingActivity.this, "ERROR !", "sync interval either 0 or greater than or equal to 5", R.color.dialog_error_background);
                                                                                    }

                                                                            } else {
                                                                                Utility.showDialog(SettingActivity.this, "ERROR !", "Default Payment Mode Required....", R.color.dialog_error_background);

                                                                            }
                                                                            } else {
                                                                                Utility.showDialog(SettingActivity.this, "ERROR !", "API Password Required....", R.color.dialog_error_background);

                                                                            }
                                                                        } else {
                                                                            Utility.showDialog(SettingActivity.this, "ERROR !", "API Username Required....", R.color.dialog_error_background);

                                                                        }
                                                                    }else {
                                                                        Utility.showDialog(SettingActivity.this, "ERROR !", "Tax Account Head Required....", R.color.dialog_error_background);

                                                                    }

                                                                }else {
                                                                    Utility.showDialog(SettingActivity.this, "ERROR !", "Company Name Required....", R.color.dialog_error_background);

                                                                }

                                                            } else {
                                                                Utility.showDialog(SettingActivity.this, "ERROR !", "URL Required....", R.color.dialog_error_background);

                                                            }
                                                        } else {
                                                            Utility.showDialog(SettingActivity.this, "ERROR !", "Setting Access Password Required....", R.color.dialog_error_background);

                                                        }
                                                    } else {
                                                        Utility.showDialog(SettingActivity.this, "ERROR !", "Warehouse Required....", R.color.dialog_error_background);

                                                    }
                                                } else {
                                                    Utility.showDialog(SettingActivity.this, "ERROR !", "Tax Rate Required....", R.color.dialog_error_background);

                                                }
                                            } else {
                                                Utility.showDialog(SettingActivity.this, "ERROR !", "Default Credit Limit Required....", R.color.dialog_error_background);

                                            }
                                        } else {
                                            Utility.showDialog(SettingActivity.this, "ERROR !", "Default Currency Required....", R.color.dialog_error_background);

                                        }
                                    } else {
                                        Utility.showDialog(SettingActivity.this, "ERROR !", "Doc Sales Transfer Required....", R.color.dialog_error_background);

                                    }
                                } else {
                                    Utility.showDialog(SettingActivity.this, "ERROR !", "Doc Sales Payment Required....", R.color.dialog_error_background);

                                }
                            } else {
                                Utility.showDialog(SettingActivity.this, "ERROR !", "Doc Sales Invoice Required....", R.color.dialog_error_background);

                            }
                        } else {
                            Utility.showDialog(SettingActivity.this, "ERROR !", "Doc Sales Order Required....", R.color.dialog_error_background);

                        }
                    } else {
                        Utility.showDialog(SettingActivity.this, "ERROR !", "naming Sales Transfer Required....", R.color.dialog_error_background);

                    }
                } else {
                    Utility.showDialog(SettingActivity.this, "ERROR !", "naming Sales Payment Required....", R.color.dialog_error_background);

                }
            } else {
                Utility.showDialog(SettingActivity.this, "ERROR !", "naming Sales Invoice Required....", R.color.dialog_error_background);

            }
        } else {
            Utility.showDialog(SettingActivity.this, "ERROR !", "naming Sales Order Required....", R.color.dialog_error_background);

        }


    }

    private void callRetrofitBase(String murl) {

        if (murl.contains("http://")) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(murl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();

        } else {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + murl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }


        userInterface = retrofit.create(UserInterface.class);

    }

    private void getAuthStatus() {
//not that
        Log.i("===============", "<======");

        Call<SuccessAuth_TokenResponse> successAuth_tokenResponseCall = userInterface.getSuccessAuth();
        successAuth_tokenResponseCall.enqueue(new Callback<SuccessAuth_TokenResponse>() {
            @Override
            public void onResponse(Call<SuccessAuth_TokenResponse> call, Response<SuccessAuth_TokenResponse> response) {
                Log.i("SuccessAuth", "<==" + response.code());
                Log.i("SuccessAuth", "<==" + response.message());
                Log.i("SuccessAuth", "<==" + response.errorBody());
                Log.i("SuccessAuth", "<==" + response.headers());

                if (response.code() == 200) {

                    SuccessAuth_TokenResponse successAuth_tokenResponse = response.body();

                    Log.i("SuccessAuth", "<==" + successAuth_tokenResponse.getMessage());


                } else if (response.code() == 500) {

                    // getAuthStatus();
                }

            }

            @Override
            public void onFailure(Call<SuccessAuth_TokenResponse> call, Throwable t) {

            }
        });


    }

    private void closeButtonClick() {
        finish();

    }
}
