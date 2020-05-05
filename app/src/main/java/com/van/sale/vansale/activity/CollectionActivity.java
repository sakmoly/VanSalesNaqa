package com.van.sale.vansale.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.Mode_Of_Payment;
import com.van.sale.vansale.model.Payment;

import java.util.ArrayList;
import java.util.List;

import static com.van.sale.vansale.activity.SalesOrderAddActivity.CUSTOMER_SELECTION_REQUEST_CODE;

public class CollectionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView payment_no, creation_date, customer_tv, mod_of_payment_tv,paid_to;
    private EditText received_amount, reference_no, reference_date;
    private Button cancel_request, next_request;
    private DatabaseHandler db;
    private MaterialSpinner spinner;
    private List<Mode_Of_Payment> getModeOfPayment;
    private List<String> modeArray;
    private String spinner_value, spinner_type,spinner_paid_to;
    private LinearLayout reference_no_layout, reference_date_layout;
    private int mode_position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        payment_no = (TextView) findViewById(R.id.payment_no);
        creation_date = (TextView) findViewById(R.id.creation_date);
        customer_tv = (TextView) findViewById(R.id.customer_tv);
        paid_to = (TextView) findViewById(R.id.paid_to);
        received_amount = (EditText) findViewById(R.id.received_amount);
        reference_no = (EditText) findViewById(R.id.reference_no);
        reference_date = (EditText) findViewById(R.id.reference_date);
        cancel_request = (Button) findViewById(R.id.cancel_request);
        next_request = (Button) findViewById(R.id.next_request);
        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        reference_no_layout = (LinearLayout) findViewById(R.id.reference_no_layout);
        reference_date_layout = (LinearLayout) findViewById(R.id.reference_date_layout);

        try {


            db = new DatabaseHandler(this);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView toolbar_title = toolbar.findViewById(R.id.titleName);
            toolbar_title.setText("ADD COLLECTION");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

            creation_date.setText(Utility.getCurrentDate());
            payment_no.setText(db.getPaymentName() + "00000" + (db.getPaymentDocNo() + 1));
            // mod_of_payment_tv.setText("cash");
            getModeOfPayment = new ArrayList<>();
            getModeOfPayment = db.getModeOfPayment();
            modeArray = new ArrayList<>();


            for (Mode_Of_Payment p : getModeOfPayment) {

                if (p.getPAYMENT_MODE().equals(db.getDefaultModeOfPayment())) {

                    spinner_value = p.getPAYMENT_MODE();
                    spinner_type = p.getPAYMENT_TYPE();
                    spinner_paid_to = p.getPAYMENT_PAID_TO();

                    break;
                }

                mode_position = mode_position + 1;

            }


            for (Mode_Of_Payment mp : getModeOfPayment) {

                modeArray.add(mp.getPAYMENT_MODE());

            }


       /* spinner_value = modeArray.get(0);
        spinner_type = getModeOfPayment.get(0).getPAYMENT_TYPE();
        spinner_paid_to = getModeOfPayment.get(0).getPAYMENT_PAID_TO();*/
            if (!spinner_paid_to.equals(null))
                paid_to.setText(spinner_paid_to);

            if (getModeOfPayment.get(0).getPAYMENT_TYPE().equals("Bank")) {
                reference_no_layout.setVisibility(View.VISIBLE);
                reference_date_layout.setVisibility(View.VISIBLE);
            } else {
                reference_no_layout.setVisibility(View.GONE);
                reference_date_layout.setVisibility(View.GONE);
            }


            spinner.setItems(modeArray);
            spinner.setSelectedIndex(mode_position);
            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                    spinner_value = item;
                    spinner_type = getModeOfPayment.get(position).getPAYMENT_TYPE();
                    spinner_paid_to = getModeOfPayment.get(position).getPAYMENT_PAID_TO();
                    paid_to.setText(spinner_paid_to);


                    if (getModeOfPayment.get(position).getPAYMENT_TYPE().equals("Bank")) {
                        reference_no_layout.setVisibility(View.VISIBLE);
                        reference_date_layout.setVisibility(View.VISIBLE);
                    } else {
                        reference_no_layout.setVisibility(View.GONE);
                        reference_date_layout.setVisibility(View.GONE);
                    }

                }
            });
        }catch (Exception ex){
            Utility.showDialog(CollectionActivity.this, "ERROR !", ex.getMessage(), R.color.dialog_error_background);
        }

        cancel_request.setOnClickListener(this);
        next_request.setOnClickListener(this);
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
            case R.id.next_request:
                AlertDialog.Builder myalert = new AlertDialog.Builder(this);
                myalert.setTitle("Confirm");
                myalert.setMessage("Do you want to save?");
                myalert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveClick();

                    }
                });
                myalert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                myalert.show();
                break;
            case R.id.customer_tv:
                customerSelectionClick();
                break;
        }
    }


    private void customerSelectionClick() {

        Intent selectionIntent = new Intent(CollectionActivity.this, CustomerSearchActivity.class);
        startActivityForResult(selectionIntent, CUSTOMER_SELECTION_REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CUSTOMER_SELECTION_REQUEST_CODE) {

            String message = data.getStringExtra("cstmr_name");
            customer_tv.setText(message);

        }

    }



    private void saveClick() {

        if (!customer_tv.getText().toString().isEmpty()) {
            if (!spinner_value.isEmpty()) {
                if (spinner_type.equals("Bank")) {
                    if (!reference_no.getText().toString().isEmpty()) {
                        if (!reference_date.getText().toString().isEmpty()) {
                            if (!received_amount.getText().toString().isEmpty()) {

                               // Toast.makeText(this, "" + spinner_value, Toast.LENGTH_SHORT).show();

                                db.addPayment(new Payment(creation_date.getText().toString()+" "+Utility.getCurrentTime(), payment_no.getText().toString(), spinner_value, received_amount.getText().toString(), customer_tv.getText().toString(), 0,reference_no.getText().toString(),reference_date.getText().toString(),paid_to.getText().toString(),Utility.getLoginUser(this),Utility.getCurrentTime()));
                                db.updatePaymentDocNo(db.getPaymentDocNo() + 1);


                                startActivity(new Intent(CollectionActivity.this, CollectionMainActivity.class));
                                finish();


                            } else {
                                Utility.showDialog(CollectionActivity.this, "ERROR !", "Amount Required....", R.color.dialog_error_background);
                            }


                        } else {
                            Utility.showDialog(CollectionActivity.this, "ERROR !", "Reference Date Required....", R.color.dialog_error_background);
                        }
                    } else {
                        Utility.showDialog(CollectionActivity.this, "ERROR !", "Reference No. Required....", R.color.dialog_error_background);
                    }


                } else {
                    if (!received_amount.getText().toString().isEmpty()) {

                      //  Toast.makeText(this, "" + spinner_value, Toast.LENGTH_SHORT).show();

                        db.addPayment(new Payment(creation_date.getText().toString()+" "+Utility.getCurrentTime(), payment_no.getText().toString(), spinner_value, received_amount.getText().toString(), customer_tv.getText().toString(), 0,"","",paid_to.getText().toString(),Utility.getLoginUser(this),Utility.getCurrentTime()));
                        db.updatePaymentDocNo(db.getPaymentDocNo() + 1);


                        startActivity(new Intent(CollectionActivity.this, CollectionMainActivity.class));
                        finish();


                    } else {
                        Utility.showDialog(CollectionActivity.this, "ERROR !", "Amount Required....", R.color.dialog_error_background);
                    }
                }

            } else {
                Utility.showDialog(CollectionActivity.this, "ERROR !", "Select Mode Of Payment....", R.color.dialog_error_background);
            }
        } else {
            Utility.showDialog(CollectionActivity.this, "ERROR !", "Select Customer....", R.color.dialog_error_background);
        }
    }


}