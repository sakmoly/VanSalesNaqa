package com.van.sale.vansale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.AddressClass;
import com.van.sale.vansale.model.CustomerClass;

import java.util.List;

public class NewCustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ED_customer_name, ED_email, ED_mobile, ED_tax, ED_customer_primary_contact,
            ED_credit_limit, ED_address_title, ED_address_line1, ED_address_line2, ED_company, ED_city;
    private Button cancel_request, save_request;
    private String customer_name, email, mobile, tax, customer_primary_contact, credit_limit, address_title, address_line1, address_line2, company, city, device_id;
    private DatabaseHandler db;
    private TextView cu_name_tv,Email_tv,cust_prmy_cnt;
    private String next;
    private int cus_name_status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        db = new DatabaseHandler(this);
        device_id = Utility.getDeviceId(NewCustomerActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("ADD CUSTOMER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));


        ED_customer_name = (EditText) findViewById(R.id.ED_customer_name);
        ED_email = (EditText) findViewById(R.id.ED_email);
        ED_mobile = (EditText) findViewById(R.id.ED_mobile);
        ED_tax = (EditText) findViewById(R.id.ED_tax);
        ED_customer_primary_contact = (EditText) findViewById(R.id.ED_customer_primary_contact);
        ED_credit_limit = (EditText) findViewById(R.id.ED_credit_limit);
        ED_address_title = (EditText) findViewById(R.id.ED_address_title);
        ED_address_line1 = (EditText) findViewById(R.id.ED_address_line1);
        ED_address_line2 = (EditText) findViewById(R.id.ED_address_line2);
        ED_company = (EditText) findViewById(R.id.ED_company);
        ED_city = (EditText) findViewById(R.id.ED_city);
        cu_name_tv = (TextView) findViewById(R.id.cu_name_tv);
        Email_tv = (TextView) findViewById(R.id.Email_tv);
        cust_prmy_cnt = (TextView) findViewById(R.id.cust_prmy_cnt);

        String first = "Customer Name ";
        String email = "Email id ";
        String custmr_cnt = "Customer Primary Contact ";
        next = "<font color='#EE0000'>*</font>";
        cu_name_tv.setText(Html.fromHtml(first + next));
        Email_tv.setText(Html.fromHtml(email + next));
        cust_prmy_cnt.setText(Html.fromHtml(custmr_cnt + next));

        save_request = (Button) findViewById(R.id.save_request);
        cancel_request = (Button) findViewById(R.id.cancel_request);


        save_request.setOnClickListener(this);
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
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.cancel_request:
                finish();
                break;

            case R.id.save_request:
                saveClick();
                break;

        }


    }

    private void saveClick() {

        customer_name = ED_customer_name.getText().toString();
        email = ED_email.getText().toString();
        mobile = ED_mobile.getText().toString();
        tax = ED_tax.getText().toString();
        customer_primary_contact = ED_customer_primary_contact.getText().toString();
        credit_limit = ED_credit_limit.getText().toString();
        address_title = ED_address_title.getText().toString();
        address_line1 = ED_address_line1.getText().toString();
        address_line2 = ED_address_line2.getText().toString();
        company = ED_company.getText().toString();
        city = ED_city.getText().toString();

        if (!customer_name.isEmpty()) {
            if (!email.isEmpty()) {
                if (!mobile.isEmpty()) {
                    if (!tax.isEmpty()) {
                        if (!customer_primary_contact.isEmpty()) {
                            if (!credit_limit.isEmpty()) {
                                if (!address_title.isEmpty()) {
                                    if (!address_line1.isEmpty()) {
                                        if (!company.isEmpty()) {
                                            if (!city.isEmpty()) {

                                                List<CustomerClass> getCustomerNamesAll = db.getCustomerNamesAll();
                                                for (CustomerClass l : getCustomerNamesAll){

                                                    if (customer_name.toLowerCase().equals(l.getCustomer_name().toLowerCase())){
                                                        cus_name_status = 1;
                                                        break;
                                                    }

                                                }

                                                if (cus_name_status == 0) {

                                                    db.addCustomer(new CustomerClass(customer_name, customer_name, email, mobile, tax, customer_primary_contact, device_id, "1", "0", Float.parseFloat(credit_limit)));
                                                    db.addAddress(new AddressClass(address_title, customer_name, address_line1, address_line2, company, city, "0"));
                                                    Toast t = Utility.setToast(NewCustomerActivity.this, "Add Successful");
                                                    t.show();
                                                    startActivity(new Intent(NewCustomerActivity.this, CustomerActivity.class));
                                                    finish();

                                                }else {

                                                    Utility.showDialog(NewCustomerActivity.this, "ERROR !", "Customer Already Exist....", R.color.dialog_error_background);

                                                }
                                            } else {
                                                Utility.showDialog(NewCustomerActivity.this, "ERROR !", "City Required....", R.color.dialog_error_background);
                                            }
                                        } else {
                                            Utility.showDialog(NewCustomerActivity.this, "ERROR !", "Company Required....", R.color.dialog_error_background);
                                        }
                                    } else {
                                        Utility.showDialog(NewCustomerActivity.this, "ERROR !", "Address Line 1 Required....", R.color.dialog_error_background);
                                    }
                                } else {
                                    Utility.showDialog(NewCustomerActivity.this, "ERROR !", "Address Title Required....", R.color.dialog_error_background);
                                }
                            } else {
                                Utility.showDialog(NewCustomerActivity.this, "ERROR !", "Credit Limit Required....", R.color.dialog_error_background);
                            }
                        } else {
                            Utility.showDialog(NewCustomerActivity.this, "ERROR !", "Customer Primary Contact Required....", R.color.dialog_error_background);
                        }
                    } else {
                        Utility.showDialog(NewCustomerActivity.this, "ERROR !", "Tax id Required....", R.color.dialog_error_background);
                    }
                } else {
                    Utility.showDialog(NewCustomerActivity.this, "ERROR !", "Mobile No. Required....", R.color.dialog_error_background);
                }
            } else {
                Utility.showDialog(NewCustomerActivity.this, "ERROR !", "Email Required....", R.color.dialog_error_background);
            }
        } else {
            Utility.showDialog(NewCustomerActivity.this, "ERROR !", "Customer Name Required....", R.color.dialog_error_background);
        }
    }


}
