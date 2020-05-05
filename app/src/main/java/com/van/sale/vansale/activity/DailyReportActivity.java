package com.van.sale.vansale.activity;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
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
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Report_model.Daily_Sales_Collection_Report;
import com.van.sale.vansale.Report_model.Item_Return_Details;
import com.van.sale.vansale.Report_model.OutStanding_Invoice_Details;
import com.van.sale.vansale.Report_model.Outstanding_Collection_Details;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.ReportPageAdaptor;
import com.van.sale.vansale.zebraPrinter.zebraPrint;

import org.w3c.dom.Text;

public class DailyReportActivity extends AppCompatActivity implements View.OnClickListener {

    Button BTN_PRINT,BTN_CLOSE,BTN_VIEW;
    EditText rptTime_et,rptDate_et,rptToDate_et,rptToTime_et;
    DatePickerDialog dtpicker;
    TimePickerDialog dttime;
    private DatabaseHandler db;

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tabItem_summary,tabItem_invoice_info,tabItem_collection_info;
    ReportPageAdaptor pageAdaptor;
    TextView tv_sales_amt,tv_vat,tv_total,tv_pending,tv_received,tv_total_collection,tv_sales_net,tv_cash_sales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);
        db = new DatabaseHandler(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("Daily Sales and Collection Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        BTN_CLOSE=(Button)findViewById(R.id.close_request);
        BTN_PRINT=(Button)findViewById(R.id.print_request);
        BTN_VIEW=(Button)findViewById(R.id.view_request);

        tv_sales_amt=(TextView)findViewById(R.id.TV_sales_amount);
        tv_vat=(TextView)findViewById(R.id.TV_vat);
        tv_total=(TextView)findViewById(R.id.TV_total);
        tv_pending=(TextView)findViewById(R.id.TV_pending);
        tv_received=(TextView)findViewById(R.id.TV_received);
        tv_total_collection=(TextView)findViewById(R.id.TV_total_collection);
        tv_cash_sales=(TextView)findViewById(R.id.TV_cash_sales);
        tv_sales_net=(TextView)findViewById(R.id.TV_sales_net);


        rptDate_et=(EditText)findViewById(R.id.rpt_date);
        rptTime_et=(EditText)findViewById(R.id.rpt_time);
        rptDate_et.setInputType(InputType.TYPE_NULL);
        rptTime_et.setInputType(InputType.TYPE_NULL);

        rptToDate_et=(EditText)findViewById(R.id.rpt_Todate);
        rptToTime_et=(EditText)findViewById(R.id.rpt_totime);
        rptToDate_et.setInputType(InputType.TYPE_NULL);
        rptToTime_et.setInputType(InputType.TYPE_NULL);

      //  tabLayout=(TabLayout)findViewById(R.id.tabLayout_details);
      //  tabItem_summary=(TabItem)findViewById(R.id.tab_summary);
      //  tabItem_invoice_info=(TabItem)findViewById(R.id.tab_invoice_info);
      //  tabItem_collection_info=(TabItem)findViewById(R.id.tab_collection_info);

     //   viewPager=(ViewPager)findViewById(R.id.view_pager);


        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
       // rptDate_et.setText(year + "-" + (month+1 ) + "-" + day);
        //rptToDate_et.setText(year + "-" + (month+1 ) + "-" + day);
        rptDate_et.setText(Utility.getCurrentDate());
        rptToDate_et.setText(Utility.getCurrentDate());

        int hr = cldr.get(Calendar.HOUR);
        int min = cldr.get(Calendar.MINUTE);

        rptTime_et.setText("00:00");
        rptToTime_et.setText(Utility.getCurrentTimeWOsecond() );



        rptDate_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                dtpicker = new DatePickerDialog(DailyReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                rptDate_et.setText(year + "-" +  Utility.padLeftFormat(String.valueOf((monthOfYear+1)), 2)  + "-" +Utility.padLeftFormat(String.valueOf(dayOfMonth),2));
                            }
                        }, year, month, day);
                dtpicker.show();

            }
        });

        rptTime_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();

                int hr = cldr.get(Calendar.HOUR);
                int min = cldr.get(Calendar.MINUTE);
                int sec = cldr.get(Calendar.SECOND);

                // date picker dialog
                dttime = new TimePickerDialog(DailyReportActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hr, int min) {
                                rptTime_et.setText( Utility.padLeftFormat(String.valueOf(hr), 2) + ":" + Utility.padLeftFormat(String.valueOf(min),2)  );
                            }
                        },hr,min,true);
                dttime.show();

            }
        });

        rptToDate_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                dtpicker = new DatePickerDialog(DailyReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                rptToDate_et.setText(year + "-" +  Utility.padLeftFormat(String.valueOf((monthOfYear+1)), 2)  + "-" +Utility.padLeftFormat(String.valueOf(dayOfMonth),2));
                        }
                        }, year, month, day);
                dtpicker.show();

            }
        });

        rptToTime_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();

                int hr = cldr.get(Calendar.HOUR);
                int min = cldr.get(Calendar.MINUTE);
                int sec = cldr.get(Calendar.SECOND);

                // date picker dialog
                dttime = new TimePickerDialog(DailyReportActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hr, int min) {
                                rptToTime_et.setText( Utility.padLeftFormat(String.valueOf(hr), 2) + ":" + Utility.padLeftFormat(String.valueOf(min),2)  );
                            }
                        },hr,min,true);
                dttime.show();

            }
        });
/*
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        BTN_PRINT.setOnClickListener(this);
        BTN_CLOSE.setOnClickListener(this);
        BTN_VIEW.setOnClickListener(this);
        view_report();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.print_request:
                print_report();
                break;
            case R.id.close_request:
                finish();
                break;
            case R.id.view_request:
                view_report();
                break;

        }
    }
    private void view_report(){
        String fromDate=rptDate_et.getText()+" "+rptTime_et.getText()+":00";
        String toDate=rptToDate_et.getText()+" "+rptToTime_et.getText()+":59";

       // pageAdaptor=new ReportPageAdaptor(getSupportFragmentManager(),tabLayout.getTabCount(),fromDate,toDate);
      //  viewPager.setAdapter(pageAdaptor);
        Daily_Sales_Collection_Report report = db.getDailySalesCollectonReport(fromDate, toDate);
        if(report!=null) {

            tv_sales_amt.setText(Utility.roundToTwoDecimal(report.getSales_amount().toString()));
            tv_vat.setText(Utility.roundToTwoDecimal(report.getVat_amount().toString()));
            tv_total.setText(Utility.roundToTwoDecimal(report.getSales_total_with_vat().toString()));
            tv_pending.setText(Utility.roundToTwoDecimal(report.getPending_collection().toString()));
            tv_sales_net.setText(Utility.roundToTwoDecimal(String.valueOf(report.getSales_total_with_vat()-report.getPending_collection())));
            tv_cash_sales.setText(tv_sales_net.getText());
            tv_received.setText(Utility.roundToTwoDecimal(report.getReceved_amount().toString()));
            tv_total_collection.setText(Utility.roundToTwoDecimal(report.getTotal_collection().toString()));
        }
        else
        {
            tv_sales_amt.setText("0:00");
            tv_vat.setText("0:00");
            tv_total.setText("0:00");
            tv_sales_net.setText("0:00");
            tv_pending.setText("0:00");
            tv_cash_sales.setText("0:00");
            tv_received.setText("0:00");
            tv_total_collection.setText("0:00");
        }


    }
    private void print_report(){

        String fromDate=rptDate_et.getText()+" "+rptTime_et.getText()+":00";
        String toDate=rptToDate_et.getText()+" "+rptToTime_et.getText()+":59";
        try {

            Daily_Sales_Collection_Report report = db.getDailySalesCollectonReport(fromDate, toDate);

            if(report!=null){

                String header=
                        "^XA" +
                                "^POI^PW600^MNN^LL%d^LH0,0"+
                                "^FO20,50" + "\r\n" +"^A0,N,35,40"+ "\r\n" +"^FD "+db.getCompanyName()+"^FS"+ "\r\n" +
                                "^FO100,105" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDDaily Sales and Collection Report^FS"+ "\r\n" +
                                "^FO80,150" + "\r\n" +"^GB400,1,1,B,0^FS"+ "\r\n" +

                                "^FO10,190" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDFrom^FS"+ "\r\n" +
                                "^FO70,190" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO90,190" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+fromDate+"^FS"+ "\r\n" +
                                "^FO310,190" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDTo^FS"+ "\r\n" +
                                "^FO340,190" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO360,190" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+toDate+"^FS"+ "\r\n" +

                                "^FO10,230" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDVehicle^FS"+ "\r\n" +
                                "^FO150,230" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO170,230" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+report.getWarehouse()+"^FS"+ "\r\n" +

                                "^FO10,270" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDSales Man^FS"+ "\r\n" +
                                "^FO150,270" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO170,270" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+Utility.getLoginUser(this) +"^FS"+ "\r\n" +

                                "^FO10,310" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" +
                                "^FO200,330" + "\r\n" +"^A0,N,30,45"+ "\r\n" +"^FDSales Info^FS"+ "\r\n" +
                                "^FO180,380" + "\r\n" +"^GB200,1,1,B,0^FS"+ "\r\n" +
                                "^FO50,400" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDSales Amount^FS"+ "\r\n" +
                                "^FO250,400" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO270,400" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD"+Utility.roundToTwoDecimal(report.getSales_amount().toString())+"^FS"+ "\r\n" +

                                "^FO50,460" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDVAT^FS"+ "\r\n" +
                                "^FO250,460" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO270,460" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD"+Utility.roundToTwoDecimal(report.getVat_amount().toString())+"^FS"+ "\r\n" +

                                "^FO50,520" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDTotal^FS"+ "\r\n" +
                                "^FO250,520" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO270,520" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD"+Utility.roundToTwoDecimal(report.getSales_total_with_vat().toString())+"^FS"+ "\r\n" +

                                "^FO50,580" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDCredit Sale^FS"+ "\r\n" +
                                "^FO250,580" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO270,580" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD"+(Utility.roundToTwoDecimal(report.getPending_collection().toString()))+"^FS"+ "\r\n" +

                                "^FO50,640" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDSales Net^FS"+ "\r\n" +
                                "^FO250,640" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO270,640" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD"+Utility.roundToTwoDecimal(String.valueOf(report.getSales_total_with_vat()-report.getPending_collection()))+"^FS"+ "\r\n" +

                                "^FO200,700" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDCollection Info^FS"+ "\r\n" +
                                "^FO180,750" + "\r\n" +"^GB200,1,1,B,0^FS"+ "\r\n" +

                                "^FO50,770" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDCash Sales^FS"+ "\r\n" +
                                "^FO250,770" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO270,770" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+Utility.roundToTwoDecimal(String.valueOf(report.getSales_total_with_vat()-report.getPending_collection()))+"^FS"+ "\r\n" +

                                "^FO50,830" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDOutstanding Collected^FS"+ "\r\n" +
                                "^FO250,830" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO270,830" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+Utility.roundToTwoDecimal(report.getReceved_amount().toString())+"^FS"+ "\r\n" +

                                "^FO50,890" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDTotal Collection^FS"+ "\r\n" +
                                "^FO250,890" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                                "^FO270,890" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD"+Utility.roundToTwoDecimal(report.getTotal_collection().toString())+"^FS"+ "\r\n" +

                                "^FO10,950" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" +
                                "^FO150,970" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDCredit Sales/Collection Details^FS"+ "\r\n" +
                                "^FO10,1030" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDCredit Sales^FS"+ "\r\n" +

                                "^FO10,1080" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" +
                                "^FO10,1100" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDNo^FS"+ "\r\n" +
                                "^FO50,1100" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDInvoice No^FS"+ "\r\n" +
                                "^FO200,1100" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDCustomer ID^FS"+ "\r\n" +
                                "^FO350,1100" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDFreezer No^FS"+ "\r\n" +
                                "^FO470,1100" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDAmount^FS"+ "\r\n" +
                                "^FO10,1140" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" ;



                int headerHeight = 1160;
                String outstandSales = String.format("^LH0,%d", headerHeight);
                int heightOfOneLine = 40;
                int i = 0;
                int print_sn=1;
                for(OutStanding_Invoice_Details invoice_details:report.getOutStanding_invoice_detailsList()){

                    String lineItem = "^FO10,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS" + "\r\n" +//seqno
                            "^FO50,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +
                            "^FO200,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +
                            "^FO350,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +
                            "^FO470,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +
                            "";

                    int totalHeight = i++ * heightOfOneLine;
                    outstandSales += String.format(lineItem, totalHeight, print_sn, totalHeight, invoice_details.getInvoice_no(),totalHeight,invoice_details.getCustomer_id(),totalHeight,invoice_details.getFreezer_no(),totalHeight,getRightString(Utility.roundToTwoDecimal(invoice_details.getInvoice_amt().toString()),10));
                    print_sn = print_sn + 1;
                }

                outstandSales +=String.format("^FO10,%d" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n",(print_sn++*heightOfOneLine));
                int outstandSalesTotal=print_sn*heightOfOneLine;
                int outstandColStart=headerHeight+outstandSalesTotal;

                String outStandcol = String.format("^LH0,%d" ,outstandColStart)+ "\r\n" +
                                      "^FO10,30" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDCollection^FS"+ "\r\n" +
                                      "^FO10,50" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" +
                                      "^FO10,70" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDNo^FS"+ "\r\n" +
                                      "^FO50,70" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDReceipt No^FS"+ "\r\n" +
                                      "^FO200,70" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDCustomer ID^FS"+ "\r\n" +
                                      "^FO350,70" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDAmount^FS"+ "\r\n" +
                                      "^FO10,110" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" ;

                i = 0;
                print_sn=1;
                int outcolheadheight=120;
                for(Outstanding_Collection_Details collection_details:report.getOutstanding_collection_details()){

                    String lineItem = "^FO10,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS" + "\r\n" +//seqno
                            "^FO50,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +
                            "^FO200,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +
                            "^FO350,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +

                            "";

                    int totalHeight = (i++ * heightOfOneLine)+outcolheadheight;
                    outStandcol += String.format(lineItem, totalHeight, print_sn, totalHeight, collection_details.getReceipt_no(),totalHeight,collection_details.getCustomer_id(),totalHeight,getRightString(Utility.roundToTwoDecimal(collection_details.getReceipt_amt().toString()),8));
                    print_sn = print_sn + 1;
                }
                outStandcol +=String.format("^FO10,%d" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n",(print_sn++*heightOfOneLine)+outcolheadheight);
                int outstandColTotal=(print_sn*heightOfOneLine)+outcolheadheight;

                int returnStart=headerHeight+outstandSalesTotal+outstandColTotal;
                String resturnStr=String.format("^LH0,%d", returnStart)+
                                  "^FO180,20" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDReturn/Damaged Info^FS"+ "\r\n" +
                                  "^FO10,80" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" +
                                  "^FO10,100" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDNo^FS"+ "\r\n" +
                                  "^FO50,100" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDItem Name^FS"+ "\r\n" +
                                  "^FO450,100" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDQuantity^FS"+ "\r\n" +
                                  "^FO10,140" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" ;

                i = 0;
                print_sn=1;
                int returnheadheight=150;
                for(Item_Return_Details item:report.getItem_return_details()){

                    String lineItem = "^FO10,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS" + "\r\n" +//seqno
                            "^FO50,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +
                            "";

                    int totalHeight = (i++ * heightOfOneLine)+returnheadheight;
                    resturnStr += String.format(lineItem, totalHeight, print_sn, totalHeight, item.getItem_name(),4);
                    print_sn = print_sn + 1;
                }
                resturnStr +=String.format("^FO10,%d" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n",(print_sn++*heightOfOneLine)+returnheadheight);
                resturnStr +=String.format("^FO200,%d" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDTotal Quantity^FS"+ "\r\n",(print_sn*heightOfOneLine)+returnheadheight);
                int returnTotal=(print_sn*heightOfOneLine)+returnheadheight;

                int footerStart=headerHeight+outstandSalesTotal+outstandColTotal+returnTotal;

                String foot=String.format("^LH0,%d", footerStart)+
                        "^FO10,100" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDPrint Date :^FS"+ "\r\n" +
                        "^FO120,100" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+Utility.getCurrentDate()+" "+Utility.getCurrentTime()+"^FS"+ "\r\n" +
                        "^FO420,200" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDSignature^FS"+ "\r\n" +
                        "^FO10,300" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD ^FS"+ "\r\n" +
                        "^XZ";
                long footerHeight = 350;
                long labelLength = headerHeight + outstandSalesTotal+outstandColTotal +returnTotal+ footerHeight;
                String header1 = String.format(header, labelLength);
                String wholeZplLabel = String.format("%s%s%s%s%s", header1, outstandSales,outStandcol,resturnStr ,foot);
                new zebraPrint().PrintToZebra(wholeZplLabel,this);

            }
            else
            {
                Utility.setToast(this,"There is no sales to print").show();
            }
        }catch (Exception e){
            Utility.showDialog(DailyReportActivity.this, "ERROR!", e.getMessage(), R.color.dialog_error_background);
        }

    }
    private String getRightString(String inputString,int length)
    {
        return String.format("%1$-" + length + "s", inputString);
    }


    @Override
    public void onBackPressed() {
       // if (scanStatus == 0) {
            //startActivity(new Intent(DailyReportActivity.this, HomeActivity.class));
            finish();

    }


}
