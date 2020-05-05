package com.van.sale.vansale.Util;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.van.sale.vansale.R;
import com.van.sale.vansale.activity.LoginActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.DecimalFormat;
/**
 * Created by maaz on 14/09/18.
 */

public class Utility {

    /* ============= TOAST MESSAGES ============== */

    public static String TOAST_COMING_SOON = "Coming Soon...";
    public static ProgressDialog progressDialog;
    public static AlertDialog alertDialog;public static String result = "";
    public static ArrayList<String> googleLoc=new ArrayList<>();

    public static String setPrefs(String key, String value, Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
        return key;
    }
    public static String getLoginUser(Context context)
    {
        String user=getPrefs("login_user",context);
        return user;
    }

    public static String getPrefs(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    private static String getCurrentDate1(){

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        String currentDate=day+"/"+(month+1)+"/"+year;
        return currentDate;
    }

    public static Toast setToast(Context context, String message) {

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }

    public static void showProgressDialog(Context context, String text) {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.progress_dialog, null);
        TextView txt = promptsView.findViewById(R.id.text_text);
        txt.setText(text);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    public static void showDialog(Context context, String head_text, String body_text, int dialog_background) {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.other_progress_dialog, null);
        TextView header_txt = promptsView.findViewById(R.id.head_text);
        TextView body_txt = promptsView.findViewById(R.id.body_text);
        LinearLayout dialog_header = promptsView.findViewById(R.id.header);
        TextView dialog_footer = promptsView.findViewById(R.id.footer);
        dialog_header.setBackgroundResource(dialog_background);
        dialog_footer.setBackgroundResource(dialog_background);
        header_txt.setText(head_text);
        body_txt.setText(body_text);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }
    public static String getRightString(String inputString,int length)
    {
        return String.format("%1$-" + length + "s", inputString);
    }

    public static void dismissProgressDialog() {

        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }


    public static String getDeviceId(Context context) {

        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

    }

    public static String getCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());

        return strDate;
    }


    public static String getCurrentTime(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = mdformat.format(calendar.getTime());

        return strDate;

    }
    public static String getCurrentTimeWOsecond(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDate = mdformat.format(calendar.getTime());

        return strDate;

    }

    public static void datePickerResult(Context context, final EditText date_picker_result) {

        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date_picker_result.setText(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();


    }


    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    public static String roundToTwoDecimal(String d) {

        DecimalFormat df = new DecimalFormat("0.00");
        String value=String.valueOf(df.format(Float.valueOf(d)));
        return value;
    }
    public static String padLeftFormat(String input, int padUpTo){
        return String.format("%0" + padUpTo + "d", Integer.parseInt(input));
    }



}