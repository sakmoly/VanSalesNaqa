package com.van.sale.vansale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.itextpdf.text.pdf.PRIndirectReference;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Retrofit_Interface.UserInterface;
import com.van.sale.vansale.Retrofit_Model.Authentication_Response;
import com.van.sale.vansale.Retrofit_Model.InvoiceNameIdData;
import com.van.sale.vansale.Retrofit_Model.InvoiceNameIdItemData;
import com.van.sale.vansale.Retrofit_Model.InvoiceNameIdItemDataList;
import com.van.sale.vansale.Retrofit_Model.InvoiceNameIdItem_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.InvoiceNameId_TokenResponse;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.SalesOrderReturnListAdapter;
import com.van.sale.vansale.model.SalesOrderItemClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IsReturnActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView RETURN_RECYCLER_VIEW;
    private String SELECTED_CUSTOMER_NAME, SELECTED_CUSTOMER_ID;
    private DatabaseHandler db;
    private List<SalesOrderItemClass> getSalesOrderItem;
    private List<SalesOrderItemClass> returnOrderItems;
    private SalesOrderReturnListAdapter returnListAdapter;
    private ImageView imageView;
    private Button done_request, cancel_request;
    private String SELECT_RETURN_ITEM;
    private TextView empty_tv;

    private String cookie;
    private Retrofit retrofit;
    private UserInterface userInterface;
    private String storedUrl;
    private String returnAgainstInvoiceNo="";
    OkHttpClient httpClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_return);

        db = new DatabaseHandler(this);
        returnOrderItems = new ArrayList<>();
        getSalesOrderItem = new ArrayList<>();

        RETURN_RECYCLER_VIEW = (RecyclerView) findViewById(R.id.return_recycler_view);
        done_request = (Button) findViewById(R.id.done_request);
        cancel_request = (Button) findViewById(R.id.cancel_request);
        empty_tv = (TextView) findViewById(R.id.empty_tv);

        cookie = Utility.getPrefs("vansale_cookie", IsReturnActivity.this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("RETURN ITEM LIST");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        SELECTED_CUSTOMER_NAME = getIntent().getStringExtra("Return_Selected_Customer");
        SELECTED_CUSTOMER_ID = getIntent().getStringExtra("Return_Selected_Customer_id");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RETURN_RECYCLER_VIEW.setLayoutManager(mLayoutManager);
        RETURN_RECYCLER_VIEW.setHasFixedSize(true);
        RETURN_RECYCLER_VIEW.setItemAnimator(new DefaultItemAnimator());

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(IsReturnActivity.this));

        httpClient = new OkHttpClient.Builder()
                /* .connectTimeout(20, TimeUnit.SECONDS)
                 .writeTimeout(20, TimeUnit.SECONDS)
                 .readTimeout(30, TimeUnit.SECONDS)*/
                .cookieJar(cookieJar)
                .addInterceptor(logging)
                .build();

        storedUrl = db.getStoredUrl();

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

                    final String cookie = "user_id=" + cookieHashmap.get("user_id") + ";full_name=" + cookieHashmap.get("full_name") + ";sid=" + cookieHashmap.get("sid");
                    Utility.setPrefs("vansale_cookie", cookie, IsReturnActivity.this);


                    Call<InvoiceNameId_TokenResponse> invoiceNameId_tokenResponseCall = userInterface.getInvoiceName(cookie, storedUrl+"/api/resource/Sales%20Invoice/?filters=%5B%5B%22Sales%20Invoice%22%2C%22doc_no%22%2C%22%3D%22%2C%22IN-ABB5249-0000020%22%5D%5D");
                    invoiceNameId_tokenResponseCall.enqueue(new Callback<InvoiceNameId_TokenResponse>() {
                        @Override
                        public void onResponse(Call<InvoiceNameId_TokenResponse> call, Response<InvoiceNameId_TokenResponse> response) {
                            Log.i("INVOICE_NAME", "<==" + response.code());
                            Log.i("INVOICE_NAME", "<==" + response.message());

                            if (response.code() == 200) {

                                InvoiceNameId_TokenResponse id_tokenResponse = response.body();
                                List<InvoiceNameIdData> data = id_tokenResponse.getData();

                                for (InvoiceNameIdData ni : data) {

                                    returnAgainstInvoiceNo =ni.getName();
                                    Call<InvoiceNameIdItem_TokenResponse> tokenResponseCall = userInterface.getInvoiceNameDataList(cookie, storedUrl+"/api/resource/Sales%20Invoice/"+ni.getName());
                                    tokenResponseCall.enqueue(new Callback<InvoiceNameIdItem_TokenResponse>() {
                                        @Override
                                        public void onResponse(Call<InvoiceNameIdItem_TokenResponse> call, Response<InvoiceNameIdItem_TokenResponse> response) {
                                            Log.i("INVOICE_DATA","<=="+response.code());
                                            Log.i("INVOICE_DATA","<=="+response.message());

                                            if (response.code()==200){

                                                InvoiceNameIdItem_TokenResponse item_tokenResponse = response.body();
                                                InvoiceNameIdItemData getData = item_tokenResponse.getData();
                                                List<InvoiceNameIdItemDataList> items = getData.getItems();

                                                for (InvoiceNameIdItemDataList id : items){

                                                    SalesOrderItemClass orderItemClass = new SalesOrderItemClass();

                                                    orderItemClass.setSALES_RATE(id.getRate());
                                                    orderItemClass.setSALES_PRICE_LIST_RATE(id.getPriceListRate());
                                                    orderItemClass.setSALES_QTY(id.getQty());
                                                    orderItemClass.setSALES_DISCOUNT_PERCENTAGE(id.getDiscountPercentage());
                                                    orderItemClass.setSALES_WAREHOUSE(id.getWarehouse());
                                                    orderItemClass.setSALES_STOCK_UOM(id.getStockUom());
                                                    orderItemClass.setSALES_ORDER_DOC_NO(id.getSalesOrder());
                                                    orderItemClass.setSALES_ITEM_NAME(id.getItemName());
                                                    orderItemClass.setSALES_ITEM_CODE(id.getItemCode());
                                                    orderItemClass.setSALES_TOTAL(id.getTotalAmount());


                                                   /* orderItemClass.setSALES_DELIVERY_STATUS(id.getItemCode());
                                                    orderItemClass.setSALES_ORDER_CUSTOMER(id.getItemCode());*/
                                                    orderItemClass.setSALES_TAX_RATE(Float.parseFloat(id.getTaxRate().toString()));
                                                    orderItemClass.setSALES_TAX_AMOUNT(id.getTotalAmount());
                                                    orderItemClass.setSALES_GROSS(id.getTotalAmount());
                                                    orderItemClass.setSALES_NET(Float.parseFloat(id.getNetAmount().toString()));
                                                    orderItemClass.setSALES_VAT(Float.parseFloat(id.getTaxAmount().toString()));
                                                    orderItemClass.setSALES_TOTAL(id.getTotalAmount());



                                                    getSalesOrderItem.add(orderItemClass);

                                                }



                                                if (!getSalesOrderItem.isEmpty()) {

                                                            empty_tv.setVisibility(View.GONE);

                                                    returnListAdapter = new SalesOrderReturnListAdapter(IsReturnActivity.this, getSalesOrderItem, new SalesOrderReturnListAdapter.CustomItemClickListener() {
                                                        @Override
                                                        public void onItemClick(View v, int position) {
                                                            imageView = v.findViewById(R.id.selected_img);
                                                            if (getSalesOrderItem.get(position).isReturn_status()) {
                                                                getSalesOrderItem.get(position).setReturn_status(false);
                                                                imageView.setImageResource(R.drawable.ic_circle);
                                                                SELECT_RETURN_ITEM = "";
                                                                // Toast t = Utility.setToast(IsReturnActivity.this,SELECT_RETURN_ITEM);
                                                                //  t.show();
                                                            } else {
                                                                getSalesOrderItem.get(position).setReturn_status(true);
                                                                imageView.setImageResource(R.drawable.ic_tick);
                                                                SELECT_RETURN_ITEM = getSalesOrderItem.get(position).getSALES_ORDER_DOC_NO();
                                                            }

                                                        }
                                                    });

                                                    RETURN_RECYCLER_VIEW.setAdapter(returnListAdapter);

                                                } else {
                                                    returnAgainstInvoiceNo = "";
                                                    empty_tv.setVisibility(View.VISIBLE);
                                                }



                                            }




                                        }

                                        @Override
                                        public void onFailure(Call<InvoiceNameIdItem_TokenResponse> call, Throwable t) {

                                        }
                                    });
                                }

                            }


                        }

                        @Override
                        public void onFailure(Call<InvoiceNameId_TokenResponse> call, Throwable t) {

                        }
                    });


                }


            }

            @Override
            public void onFailure(Call<Authentication_Response> call, Throwable t) {
                Utility.dismissProgressDialog();
                Utility.showDialog(IsReturnActivity.this, "NETWORK ERROR !", "Check Your Network !\n Try Again....", R.color.dialog_network_error_background);

            }
        });




      //  getSalesOrderItem = db.getSalesOrderReturnItem(SELECTED_CUSTOMER_NAME);

      /*  for (SalesOrderItemClass so : getSalesOrderItem) {

            Log.i("RETURN_ITEM", "<==" + so.getSALES_ITEM_NAME());
            Log.i("RETURN_ITEM", "<==" + so.getSALES_ITEM_CODE());
            Log.i("RETURN_ITEM", "<==" + so.getSALES_QTY());

        }*/

       /* if (!getSalesOrderItem.isEmpty()) {
            empty_tv.setVisibility(View.GONE);
            returnListAdapter = new SalesOrderReturnListAdapter(IsReturnActivity.this, getSalesOrderItem, new SalesOrderReturnListAdapter.CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    imageView = v.findViewById(R.id.selected_img);
                    if (getSalesOrderItem.get(position).isReturn_status()) {
                        getSalesOrderItem.get(position).setReturn_status(false);
                        imageView.setImageResource(R.drawable.ic_circle);
                        SELECT_RETURN_ITEM = "";
                        // Toast t = Utility.setToast(IsReturnActivity.this,SELECT_RETURN_ITEM);
                        //  t.show();
                    } else {
                        getSalesOrderItem.get(position).setReturn_status(true);
                        imageView.setImageResource(R.drawable.ic_tick);
                        SELECT_RETURN_ITEM = getSalesOrderItem.get(position).getSALES_ORDER_DOC_NO();
                    }

                }
            });

            RETURN_RECYCLER_VIEW.setAdapter(returnListAdapter);

        } else {
            empty_tv.setVisibility(View.VISIBLE);
        }
*/

        done_request.setOnClickListener(this);
        cancel_request.setOnClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                SalesInvoiceAddActivity.setIS_RETURN_SELECTION_STATUS();
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

            case R.id.done_request:
                doneClick();
                break;
            case R.id.cancel_request:
                SalesInvoiceAddActivity.setIS_RETURN_SELECTION_STATUS();
                finish();
                break;

        }
    }


    private void doneClick() {

        for (SalesOrderItemClass sc : getSalesOrderItem) {

            if (sc.isReturn_status()) {
                returnOrderItems.add(sc);
            }

        }

        for (SalesOrderItemClass ic : returnOrderItems){

            Log.i("AAA","<=="+ic.getSALES_ITEM_NAME());
            Log.i("AAA","<=="+ic.getSALES_ITEM_CODE());

        }


        // InvoiceItemListActivity.setReturnItems(returnOrderItems);

        if (!returnOrderItems.isEmpty()) {

            Intent intent = new Intent();
            intent.putExtra("ORDER_NUMBER", SELECT_RETURN_ITEM);
            SalesInvoiceAddActivity.setReturnAgainstInvoiceNo(returnAgainstInvoiceNo);
            SalesInvoiceAddActivity.setSelectedClass(returnOrderItems);
            setResult(SalesInvoiceAddActivity.RETURN_REQUEST_CODE, intent);
            finish();

        } else {
            Utility.showDialog(IsReturnActivity.this, "ERROR !", "Ooops, No Data Selected ! \n Cancel Recommented !", R.color.dialog_error_background);
        }


    }


}
