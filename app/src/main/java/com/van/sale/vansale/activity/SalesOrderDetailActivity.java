package com.van.sale.vansale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.van.sale.vansale.R;

public class SalesOrderDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Button next_request, cancel_request;
    private String doc_no,order_creation,order_delivery_date,customer_po,customer_name,customer_id;
    private TextView TV_DOC_NO,TV_CREATION_DATE,TV_DELIVERY_DATE,TV_CUSTOMER_PO,TV_CUSTOMER_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order_detail);

        next_request = (Button) findViewById(R.id.next_request);
        cancel_request = (Button) findViewById(R.id.cancel_request);
        TV_DOC_NO = (TextView)findViewById(R.id.TV_DOC_NO);
        TV_CREATION_DATE = (TextView)findViewById(R.id.TV_CREATION_DATE);
        TV_DELIVERY_DATE = (TextView)findViewById(R.id.TV_DELIVERY_DATE);
        TV_CUSTOMER_PO = (TextView)findViewById(R.id.TV_CUSTOMER_PO);
        TV_CUSTOMER_NAME = (TextView)findViewById(R.id.TV_CUSTOMER_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("ORDER DETAIL");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        doc_no = getIntent().getStringExtra("DOC_NO");
        order_creation = getIntent().getStringExtra("ORDER_CREATION");
        order_delivery_date = getIntent().getStringExtra("ORDER_DELIVERY_DATE");
        customer_po = getIntent().getStringExtra("ORDER_CUSTOMER_PO_NO");
        customer_name = getIntent().getStringExtra("ORDER_CUSTOMER_NAME");
        customer_id = getIntent().getStringExtra("ORDER_CUSTOMER_ID");

        TV_DOC_NO.setText(doc_no);
        TV_CREATION_DATE.setText(order_creation);
        TV_DELIVERY_DATE.setText(order_delivery_date);
        TV_CUSTOMER_PO.setText(customer_po);
        TV_CUSTOMER_NAME.setText(customer_name);


        next_request.setOnClickListener(this);
        cancel_request.setOnClickListener(this);

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

        Intent orderList_intent = new Intent(SalesOrderDetailActivity.this, OrderDetailSelectedListActivity.class);
        orderList_intent.putExtra("ORDER_C_NAME",customer_name);
        orderList_intent.putExtra("ORDER_C_ID",customer_id);
        startActivity(orderList_intent);


    }
}
