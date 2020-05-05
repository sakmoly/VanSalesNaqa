package com.van.sale.vansale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class CustomerEditActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ED_customer_name, ED_email, ED_mobile, ED_tax, ED_customer_primary_contact,
            ED_credit_limit, ED_address_title, ED_address_line1, ED_address_line2, ED_company, ED_city;
    private Button cancel_request, save_request;
    private String customer_name, email, mobile, tax, customer_primary_contact, credit_limit, address_title, address_line1, address_line2, company, city, device_id;
    private DatabaseHandler db;
private String intent_cu_name,cu_id,ad_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit);

        db = new DatabaseHandler(this);
        intent_cu_name = getIntent().getStringExtra("cu_name");

        List<CustomerClass> gc = db.getCustomerDetail(intent_cu_name);
        AddressClass getCustomerAddress = db.getCustomerAddress(intent_cu_name);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("EDIT CUSTOMER");
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

        save_request = (Button) findViewById(R.id.save_request);
        cancel_request = (Button) findViewById(R.id.cancel_request);

        cu_id = gc.get(0).getId();
        ED_customer_name.setText(gc.get(0).getCustomer_name());
        ED_email.setText(gc.get(0).getEmail_id());
        ED_mobile.setText(gc.get(0).getMobile_no());
        ED_tax.setText(gc.get(0).getTax_id());
        ED_customer_primary_contact.setText(gc.get(0).getCustomer_primary_contact());
        ED_credit_limit.setText(String.valueOf(gc.get(0).getCredit_limit()));

        ED_address_title.setText(getCustomerAddress.getTitle());
        ED_address_line1.setText(getCustomerAddress.getAddress_line1());
        ED_address_line2.setText(getCustomerAddress.getAddress_line2());
        ED_company.setText(getCustomerAddress.getCompany());
        ED_city.setText(getCustomerAddress.getCity());
        ad_id = getCustomerAddress.getId();

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
                updateClick();
                break;
        }

    }

    private void updateClick() {

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

                                                db.updateCustomerDetails(new CustomerClass(cu_id,customer_name, customer_name, email, mobile, tax, customer_primary_contact, device_id, Float.parseFloat(credit_limit)));
                                                db.updateAddressDetails(new AddressClass(ad_id,address_title, customer_name, address_line1, address_line2, company, city,""));

                                                Toast t = Utility.setToast(CustomerEditActivity.this, "Update Successful");
                                                t.show();
                                                Intent intent = new Intent();
                                                setResult(3,intent);
                                                startActivity(new Intent(CustomerEditActivity.this, CustomerActivity.class));

                                            } else {
                                                Utility.showDialog(CustomerEditActivity.this, "ERROR !", "City Required....", R.color.dialog_error_background);
                                            }
                                        } else {
                                            Utility.showDialog(CustomerEditActivity.this, "ERROR !", "Company Required....", R.color.dialog_error_background);
                                        }
                                    } else {
                                        Utility.showDialog(CustomerEditActivity.this, "ERROR !", "Address Line 1 Required....", R.color.dialog_error_background);
                                    }
                                } else {
                                    Utility.showDialog(CustomerEditActivity.this, "ERROR !", "Address Title Required....", R.color.dialog_error_background);
                                }
                            } else {
                                Utility.showDialog(CustomerEditActivity.this, "ERROR !", "Credit Limit Required....", R.color.dialog_error_background);
                            }
                        } else {
                            Utility.showDialog(CustomerEditActivity.this, "ERROR !", "Customer Primary Contact Required....", R.color.dialog_error_background);
                        }
                    } else {
                        Utility.showDialog(CustomerEditActivity.this, "ERROR !", "Tax id Required....", R.color.dialog_error_background);
                    }
                } else {
                    Utility.showDialog(CustomerEditActivity.this, "ERROR !", "Mobile No. Required....", R.color.dialog_error_background);
                }
            } else {
                Utility.showDialog(CustomerEditActivity.this, "ERROR !", "Email Required....", R.color.dialog_error_background);
            }
        } else {
            Utility.showDialog(CustomerEditActivity.this, "ERROR !", "Customer Name Required....", R.color.dialog_error_background);
        }


    }
}
