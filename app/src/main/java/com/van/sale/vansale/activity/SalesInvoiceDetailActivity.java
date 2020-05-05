package com.van.sale.vansale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.SalesInvoiceClass;
import com.van.sale.vansale.model.SalesOrderItemClass;

import java.util.ArrayList;
import java.util.List;

public class SalesInvoiceDetailActivity extends AppCompatActivity implements View.OnClickListener {

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
    private String DEFAULT_COMPANY, INVOICE_CUSTOMER_ID,INVOICE_SYNC_STATUS,INVOICE_IS_POS;


    private TextView cash_tv,INVOICE_NO_TV, CREATION_DATE_TV, CUSTOMER_TV, RETURN_AGAINST_TV, POSTING_TIME_TV, POSTING_DATE_TV;
    private LinearLayout IS_RETURN_LAYOUT;
    private static LinearLayout RETURN_AGAINST_LAYOUT;
    private static ImageView IS_RETURN_IMAGE,cash_image;
    private static int IS_RETURN_SELECTION_STATUS = 0;
    private Button NEXT_BTN, CANCEL_BTN;
    private static List<SalesOrderItemClass> getSalesOrderItem;
    private static String selected_customer_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_invoice_add);

        db = new DatabaseHandler(this);


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
        NEXT_BTN = (Button) findViewById(R.id.next_request);
        CANCEL_BTN = (Button) findViewById(R.id.cancel_request);

        IS_RETURN_LAYOUT = (LinearLayout) findViewById(R.id.isreturn_layout);
        RETURN_AGAINST_LAYOUT = (LinearLayout) findViewById(R.id.return_against_layout);
        IS_RETURN_IMAGE = (ImageView) findViewById(R.id.isreturn_image);
        cash_image = (ImageView) findViewById(R.id.cash_image);
        cash_tv = (TextView) findViewById(R.id.cash_tv);

        INVOICE_CUSTOMER_ID = getIntent().getStringExtra("INVOICE_CUSTOMER_ID");

        CUSTOMER_TV.setText(getIntent().getStringExtra("INVOICE_CUSTOMER"));
        CREATION_DATE_TV.setText(getIntent().getStringExtra("INVOICE_CREATION"));
        POSTING_DATE_TV.setText(getIntent().getStringExtra("INVOICE_CREATION"));
        INVOICE_NO_TV.setText(getIntent().getStringExtra("INVOICE_NO"));
        POSTING_TIME_TV.setText(getIntent().getStringExtra("INVOICE_POSTING_TIME"));
        INVOICE_SYNC_STATUS = getIntent().getStringExtra("INVOICE_SYNC_STATUS");
        INVOICE_IS_POS = getIntent().getStringExtra("INVOICE_IS_POS");


            if (getIntent().getStringExtra("INVOICE_IS_RETURN").equals("1")) {

                RETURN_AGAINST_LAYOUT.setVisibility(View.VISIBLE);
                IS_RETURN_IMAGE.setImageResource(R.drawable.ic_switch_on);
                RETURN_AGAINST_TV.setText(getIntent().getStringExtra("INVOICE_RETURN_AGAINST"));

            }




        if (INVOICE_IS_POS.equals("1")){

            cash_image.setImageResource(R.drawable.ic_switch_on);
            cash_tv.setText("Cash");

            }else{

            cash_image.setImageResource(R.drawable.ic_switch_off);
            cash_tv.setText("Credit");

            }





        NEXT_BTN.setOnClickListener(this);
        CANCEL_BTN.setOnClickListener(this);

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

        }
    }


    private void nextClick() {


        Intent itemIntent = new Intent(SalesInvoiceDetailActivity.this, InvoiceDetailItemListActivity.class);

        itemIntent.putExtra("INVOICE_CUSTOMER_ID", INVOICE_CUSTOMER_ID);
        itemIntent.putExtra("INVOICE_CUSTOMER_NAME", CUSTOMER_TV.getText().toString());
        itemIntent.putExtra("INVOICE_NO", INVOICE_NO_TV.getText().toString());
        itemIntent.putExtra("INVOICE_DATE", CREATION_DATE_TV.getText().toString());
        itemIntent.putExtra("INVOICE_SYNC_STATUS", INVOICE_SYNC_STATUS);

        startActivity(itemIntent);



       /* if (!CUSTOMER_TV.getText().toString().isEmpty()) {

            db.addSalesInvoice(new SalesInvoiceClass(1, IS_RETURN_SELECTION_STATUS, CREATION_DATE_TV.getText().toString(), SALES_ORDER_OWNER, SALES_ORDER_PRICE_LIST_CURRENCY, CUSTOMER_TV.getText().toString(), DEFAULT_COMPANY, SALES_INVOICE_NAMING_SERIES, SALES_ORDER_CURRENCY, INVOICE_NO_TV.getText().toString(), RETURN_AGAINST_TV.getText().toString(), POSTING_TIME_TV.getText().toString(), POSTING_DATE_TV.getText().toString(), DEFAULT_STATUS, DEVICE_ID, "0", DEFAULT_CONVERSION_RATE, DEFAULT_PLC_CONVERSION_RATE));
            db.updateSalesInvoiceDocNo(db.getSalesInvoiceDocNo() + 1);


            InvoiceItemListActivity.setCustomerName(CUSTOMER_TV.getText().toString());
            InvoiceItemListActivity.setCustomerId(selected_customer_id);
            InvoiceItemListActivity.setReturnItems(getSalesOrderItem);

            Intent next_intent = new Intent(SalesInvoiceDetailActivity.this, InvoiceItemListActivity.class);
            startActivity(next_intent);


        } else {
            Utility.showDialog(SalesInvoiceDetailActivity.this, "ERROR !", "Customer Name Required....", R.color.dialog_error_background);
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

}
