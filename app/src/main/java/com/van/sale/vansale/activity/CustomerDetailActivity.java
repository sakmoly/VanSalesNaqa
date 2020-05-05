package com.van.sale.vansale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.adapter.CustomerAddressViewAdapter;
import com.van.sale.vansale.model.AddressClass;

import java.util.ArrayList;
import java.util.List;

public class CustomerDetailActivity extends AppCompatActivity {

    RecyclerView mAddressRecycler;
    private List<AddressClass> customerListsl;
    private CustomerAddressViewAdapter addressViewAdapter;
    private LinearLayout close_layout;
    private String cu_server_id,customer_id,customer_mob,customer_name,customer_email,customer_tax_id,customer_primary_contact;
    private Float customer_credit_limit;
    private TextView TV_customer_server_id,TV_customer_name,TV_email,TV_mob,TV_tax,TV_primary_contact,TV_credit_limit,TV_address_title,TV_address,TV_company,TV_city;
    private Button cancel_request;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       /* mAddressRecycler = (RecyclerView) findViewById(R.id.address_recycler);*/
        cancel_request = (Button)findViewById(R.id.cancel_request);

        db = new DatabaseHandler(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("DETAIL");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        customer_id = getIntent().getStringExtra("cu_id");
        cu_server_id = getIntent().getStringExtra("cu_server_id");
        customer_name = getIntent().getStringExtra("cu_name");
        customer_email = getIntent().getStringExtra("cu_email");
        customer_tax_id = getIntent().getStringExtra("cu_tax_id");
        customer_mob = getIntent().getStringExtra("cu_mob");
        customer_primary_contact = getIntent().getStringExtra("cu_primary_contact");
        customer_credit_limit = getIntent().getFloatExtra("cu_credit_limit",0.0f);


        TV_customer_name = (TextView)findViewById(R.id.TV_customer_name);
        TV_email = (TextView)findViewById(R.id.TV_email);
        TV_mob = (TextView)findViewById(R.id.TV_mob);
        TV_tax = (TextView)findViewById(R.id.TV_tax);
        TV_primary_contact = (TextView)findViewById(R.id.TV_primary_contact);
        TV_credit_limit = (TextView)findViewById(R.id.TV_credit_limit);
        TV_customer_server_id = (TextView)findViewById(R.id.TV_customer_server_id);

        TV_address_title = (TextView)findViewById(R.id.TV_address_title);
        TV_address = (TextView)findViewById(R.id.TV_address);
        TV_company = (TextView)findViewById(R.id.TV_company);
        TV_city = (TextView)findViewById(R.id.TV_city);

        AddressClass getCustomerAddress = db.getCustomerAddress(customer_name+"-Billing");

        TV_customer_name.setText(customer_name);
        TV_customer_server_id.setText(cu_server_id);
        TV_email.setText(customer_email);
        TV_mob.setText(customer_mob);
        TV_tax.setText(customer_tax_id);
        TV_primary_contact.setText(customer_primary_contact);
        TV_credit_limit.setText(String.valueOf(customer_credit_limit));


        TV_address_title.setText(getCustomerAddress.getTitle());
        TV_address.setText(getCustomerAddress.getAddress_line1()+","+getCustomerAddress.getAddress_line2());
        TV_company.setText(getCustomerAddress.getCompany());
        TV_city.setText(getCustomerAddress.getCity());





       /* LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAddressRecycler.setLayoutManager(mLayoutManager);
        mAddressRecycler.setHasFixedSize(true);
        mAddressRecycler.setItemAnimator(new DefaultItemAnimator());

        customerListsl = new ArrayList<>();
       // customerListsl.add(new AddressClass("AddressClass 1", "Valiyaveettil (h)", "Company 1", "Thrissur"));
       // customerListsl.add(new AddressClass("AddressClass 2", "puthiyaveettil (h)", "Company 2", "Ernakulam"));


        addressViewAdapter = new CustomerAddressViewAdapter(getApplicationContext(), customerListsl);
        mAddressRecycler.setAdapter(addressViewAdapter);*/

        cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


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
    public void onBackPressed() {
        finish();
    }
}
