package com.van.sale.vansale.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.van.sale.vansale.Authentication.ServiceGenerator;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Retrofit_Interface.UserInterface;
import com.van.sale.vansale.Retrofit_Model.User_TokenResponse;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.Password;
import com.van.sale.vansale.model.SettingsClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView login_image;
    LinearLayout login_setting_layout;
    private Retrofit retrofit;
    private UserInterface userInterface;
    private EditText Edt_username, Edt_password;
    private DatabaseHandler db;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHandler(this);
        setVersion();
        Edt_username = (EditText) findViewById(R.id.Edt_username);
        Edt_password = (EditText) findViewById(R.id.Edt_password);
        login_image = (ImageView) findViewById(R.id.login_image);
        login_setting_layout = (LinearLayout) findViewById(R.id.login_setting_layout);

        String login_status = Utility.getPrefs("vansale_login_status", LoginActivity.this);
        if (login_status == null)
            login_status = "0";

        if (login_status.equals("1")) {

            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();

        }



        login_image.setOnClickListener(this);
        login_setting_layout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.login_image:
                loginClick();
                break;
            case R.id.login_setting_layout:
                settingClick();
                break;
        }
    }

    private void settingClick() {

        LayoutInflater li = LayoutInflater.from(LoginActivity.this);
        View promptsView = li.inflate(R.layout.forgot_password_popup, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle("Enter Password");
        final AlertDialog alertDialog = alertDialogBuilder.create();

        final EditText default_password = (EditText) promptsView.findViewById(R.id.add_mobile);
        final Button send_request = (Button) promptsView.findViewById(R.id.send_request);
        final Button cancel_request = (Button) promptsView.findViewById(R.id.cancel_request);

        send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!(default_password.getText().toString().isEmpty() || default_password.getText().toString().equals(""))) {

                        Password pass = db.getAccessPassword();

                        if (default_password.getText().toString().equals(pass.getAccessPassword())) {
                            alertDialog.dismiss();
                            Toast t = Utility.setToast(LoginActivity.this, "You are in....");
                            t.show();
                            startActivity(new Intent(LoginActivity.this, SettingActivity.class));
                        } else {
                            Utility.showDialog(LoginActivity.this, "ERROR !", "Password mismatch...", R.color.dialog_error_background);
                        }
                    } else {
                        Utility.showDialog(LoginActivity.this, "ERROR !", "Enter Password ...", R.color.dialog_error_background);
                    }


                }catch (Exception e){
                    Utility.showDialog(LoginActivity.this, "ERROR !", e.getMessage(), R.color.dialog_error_background);
            }
            }
        });

        cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    private void loginClick() {
    try {
        if (!Edt_username.getText().toString().isEmpty()) {
            if (!Edt_password.getText().toString().isEmpty()) {
                password = db.searchPass(Edt_username.getText().toString());
                if (password.equals(Edt_password.getText().toString())) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    Utility.setPrefs("vansale_login_status", "1", LoginActivity.this);
                    Utility.setPrefs("login_user",Edt_username.getText().toString(),LoginActivity.this);
                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Utility.showDialog(LoginActivity.this, "ERROR !", "Username or Password is incorrect....", R.color.dialog_error_background);
                }
            } else {
                Utility.showDialog(LoginActivity.this, "ERROR !", "Password Required....", R.color.dialog_error_background);
            }
        } else {
            Utility.showDialog(LoginActivity.this, "ERROR !", "Username Required....", R.color.dialog_error_background);
        }
    }catch (Exception e){
        Utility.showDialog(LoginActivity.this, "ERROR !", e.getMessage(), R.color.dialog_error_background);
    }
    }
    private void setVersion()
    {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        TextView versionText = (TextView) findViewById(R.id.versionName);
        versionText.setText(String.format("%s     Ver: %s",getString(R.string.copyright),version));


    }
    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginActivity.super.onBackPressed();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //    handler.removeCallbacksAndMessages(null);
                        startActivity(intent);
                    }
                }).create().show();


    }
}
