package com.van.sale.vansale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.SalesOrderClass;

import java.util.Calendar;
import java.util.List;

public class SalesOrderAddActivity extends AppCompatActivity implements View.OnClickListener {


    Button next_request, cancel_request;
    private EditText creation_date, date_picker_result, customer_po_no;
    private LinearLayout date_picker_image;
    private TextView customer_tv, doc_no;
    public static int CUSTOMER_SELECTION_REQUEST_CODE = 119;
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
    private String SALES_ORDER_NAMING_SERIES;
    private String DEVICE_ID;
    private String DEFAULT_COMPANY,SALES_LATTITUDE,SALES_Longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order_add);

        /* https://github.com/delight-im/Android-SimpleLocation */

        next_request = (Button) findViewById(R.id.next_request);
        cancel_request = (Button) findViewById(R.id.cancel_request);

        creation_date = (EditText) findViewById(R.id.creation_date);
        doc_no = (TextView) findViewById(R.id.doc_no);
        customer_po_no = (EditText) findViewById(R.id.customer_po_no);
        date_picker_result = (EditText) findViewById(R.id.date_picker_result);
        date_picker_image = (LinearLayout) findViewById(R.id.date_picker_image);
        customer_tv = (TextView) findViewById(R.id.customer_tv);

        db = new DatabaseHandler(this);

        SALES_ORDER_CURRENCY = db.getCurrency();
        SALES_ORDER_PRICE_LIST_CURRENCY = db.getCurrency();
        DEFAULT_COMPANY = db.getCompanyName();
        SALES_ORDER_NAMING_SERIES = db.getSalesOrderName();
        DEVICE_ID = db.getDeviceIdFromSetting();
        SALES_ORDER_OWNER = db.getApiUsername();
        DEFAULT_BILLING_STATUS = "Not Billed";
        DEFAULT_ORDER_TYPE = "Sales";
        DEFAULT_STATUS = "Draft";

        SALES_LATTITUDE = getIntent().getStringExtra("SALES_LATTITUDE");
        SALES_Longitude = getIntent().getStringExtra("SALES_LONGITUDE");

        // Toast.makeText(this, ""+DEFAULT_COMPANY, Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("TAKE ORDER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        creation_date.setText(Utility.getCurrentDate());
        doc_no.setText(db.getSalesOrderName() + "00000" + (db.getSalesOrderDocNo() + 1));






       // easyWayLocation = new EasyWayLocation(this);
       // easyWayLocation.setListener(this);

        next_request.setOnClickListener(this);
        cancel_request.setOnClickListener(this);
        date_picker_image.setOnClickListener(this);
        customer_tv.setOnClickListener(this);


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
            case R.id.cancel_request:
                finish();
                break;
            case R.id.next_request:
                nextClick();
                break;
            case R.id.date_picker_image:
                datePickerDialog();
                break;
            case R.id.customer_tv:
                customerSelectionClick();
                break;
        }
    }

    private void customerSelectionClick() {

        Intent selectionIntent = new Intent(SalesOrderAddActivity.this, CustomerSearchActivity.class);
        startActivityForResult(selectionIntent, CUSTOMER_SELECTION_REQUEST_CODE);


    }

    private void datePickerDialog() {

        Utility.datePickerResult(SalesOrderAddActivity.this, date_picker_result);

    }

    private void nextClick() {


        if (!doc_no.getText().toString().isEmpty()) {
            if (!creation_date.getText().toString().isEmpty()) {
                if (!date_picker_result.getText().toString().isEmpty()) {
                   /* if (!customer_po_no.getText().toString().isEmpty()) {*/
                        if (!customer_tv.getText().toString().isEmpty()) {

                            Utility.setPrefs("VANSALE_DOC_NO", doc_no.getText().toString(), SalesOrderAddActivity.this);
                            Utility.setPrefs("VANSALE_CREATE_DATE", creation_date.getText().toString(), SalesOrderAddActivity.this);
                            Utility.setPrefs("VANSALE_DELIVERY_DATE", date_picker_result.getText().toString(), SalesOrderAddActivity.this);
                            Utility.setPrefs("VANSALE_PO_NO", customer_po_no.getText().toString(), SalesOrderAddActivity.this);
                            Utility.setPrefs("VANSALE_cus", customer_tv.getText().toString(), SalesOrderAddActivity.this);

                            db.addSalesOrder(new SalesOrderClass(DEFAULT_CONVERSION_RATE, DEFAULT_PLC_CONVERSION_RATE, DEFAULT_DOC_STATUS, UNSYNC_STATUS, creation_date.getText().toString(), SALES_ORDER_OWNER, DEFAULT_BILLING_STATUS, customer_po_no.getText().toString(), customer_tv.getText().toString(), DEFAULT_ORDER_TYPE, DEFAULT_STATUS, DEFAULT_COMPANY, SALES_ORDER_NAMING_SERIES, doc_no.getText().toString(), date_picker_result.getText().toString(), SALES_ORDER_CURRENCY, SALES_ORDER_PRICE_LIST_CURRENCY, DEVICE_ID,SALES_LATTITUDE,SALES_Longitude));
                            db.updateSalesOrderDocNo(db.getSalesOrderDocNo() + 1);
                            SalesOrderItemListActivity.setBadgeCount(0);

                            startActivity(new Intent(SalesOrderAddActivity.this, SalesOrderItemListActivity.class));
                            finish();

                        } else {
                            Utility.showDialog(SalesOrderAddActivity.this, "ERROR !", "Select Customer....", R.color.dialog_error_background);
                        }
                    /*} else {
                        Utility.showDialog(SalesOrderAddActivity.this, "ERROR !", "Customer po no. Required....", R.color.dialog_error_background);
                    }*/
                } else {
                    Utility.showDialog(SalesOrderAddActivity.this, "ERROR !", "Delivery Date Required....", R.color.dialog_error_background);
                }
            } else {
                Utility.showDialog(SalesOrderAddActivity.this, "ERROR !", "Date Required....", R.color.dialog_error_background);
            }
        } else {
            Utility.showDialog(SalesOrderAddActivity.this, "ERROR !", "Doc no. Required....", R.color.dialog_error_background);
        }




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CUSTOMER_SELECTION_REQUEST_CODE) {

            String message = data.getStringExtra("cstmr_name");
            customer_tv.setText(message);

        }


    }

   /* @Override
    public void locationOn() {

        //  Toast.makeText(this, "Location ON", Toast.LENGTH_SHORT).show();
        easyWayLocation.beginUpdates();
        lati = easyWayLocation.getLatitude();
        longi = easyWayLocation.getLongitude();

        // Toast.makeText(this, "Lattitude"+String.valueOf(lati)+", Longitude"+String.valueOf(longi), Toast.LENGTH_SHORT).show();

    }*/

   /* @Override
    public void onPositionChanged() {

    }

    @Override
    public void locationCancelled() {

    }*/

    @Override
    protected void onResume() {
        super.onResume();

        // make the device update its location
      //  easyWayLocation.beginUpdates();


    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
      //  easyWayLocation.endUpdates();


        super.onPause();
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
