package com.van.sale.vansale.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.UIHelper;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.CustomerSearchRecyclerViewAdapter;
import com.van.sale.vansale.adapter.SelectedInvoiceRecyclerViewAdapter;
import com.van.sale.vansale.model.CustomerClass;
import com.van.sale.vansale.model.CustomerVisitLog;
import com.van.sale.vansale.model.SalesInvoiceClass;
import com.van.sale.vansale.model.SalesInvoiceItemClass;
import com.van.sale.vansale.model.SalesInvoiceModeOfPayment;
import com.van.sale.vansale.model.SalesOrderItemClass;
import com.van.sale.vansale.GpsTrack.LocationTrack;
import com.van.sale.vansale.GpsTrack.LocationTrack.LocationResult;
import com.van.sale.vansale.zebraPrinter.zebraPrint;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static com.koushikdutta.async.AsyncServer.LOGTAG;

public class InvoiceItemListActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ITEM_ADD_REQUEST_CODE = 229;
    private Button SAVE_BUTTON, CANCEL_BUTTON;
    private EditText editText_search_asset;
    private static String CUSTOMER_NAME, selected_customer_id;
    private static String SELECTED_ASSET="";
    private RecyclerView RECYCLER_VIEW;
    private TextView EMPTY_TV,CURRENT_ASSET_TV;
    private ImageView SCAN_IV;
    private CheckBox check_print_after_save;

    private SelectedInvoiceRecyclerViewAdapter viewAdapter;
    private static SalesInvoiceClass salesInvoiceheader;
    private static List<SalesOrderItemClass> itemClasses;
    private List<SalesOrderItemClass> getSalesOrderItem;
    private static List<SalesInvoiceItemClass> getSalesInvoiceSelectedItem;
    private static DatabaseHandler db;
    private String IS_RETURN_SELECTION_STATUS, CASH_SELECTION_STATUS;
    private Integer CURRENT_DOC_N0=0;
    private Float credit_total = 0.0f;
    private TextView TV_gross, TV_net, TV_vat, TV_total,TV_total_qty;
    private Float gross = 0.0f, net = 0.0f, vat = 0.0f, tot = 0.0f, sales_invoice_payment_total = 0.0f,total_qty=0.0f;
    List<String> gpsloc;

    private int print_sn = 0,print_qty = 0;
    private Float print_subtot = 0.0f;
    private Float print_vat = 0.0f;
    private Float print_total = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_item_list);

        CURRENT_ASSET_TV=(TextView)findViewById(R.id.current_asset_tv);
        TV_gross = (TextView) findViewById(R.id.TV_gross);
        TV_net = (TextView) findViewById(R.id.TV_net);
        TV_vat = (TextView) findViewById(R.id.TV_vat);
        TV_total = (TextView) findViewById(R.id.TV_total);
        TV_total_qty = (TextView) findViewById(R.id.TV_total_qty);

        editText_search_asset=(EditText)findViewById(R.id.ed_asset_search);
        SCAN_IV=(ImageView)findViewById(R.id.scan_btn);

        check_print_after_save=(CheckBox)findViewById(R.id.chk_print_after_save);

        IS_RETURN_SELECTION_STATUS = getIntent().getStringExtra("IS_RETURN_SELECTION_STATUS");
        CASH_SELECTION_STATUS = getIntent().getStringExtra("CASH_SELECTION_STATUS");
        CURRENT_DOC_N0=getIntent().getIntExtra("CURRENT_DOC_NO",0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("ITEMS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));
        gpsloc=getLocation();
        editText_search_asset.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText=editText_search_asset.getText().toString();
                    if(searchText.length()==13 ||searchText.length()==12 ){
                        if(searchText.endsWith(GetChecksumDigit(searchText))){
                            searchText=(searchText.substring(0,searchText.length()-1)).replaceFirst("^0+(?!$)", "");
                        }
                    }
                    if(db.IsValidCustomerAsset(salesInvoiceheader.getSALES_INVOICE_CUSTOMER(),searchText)){
                        SELECTED_ASSET=searchText;
                        CURRENT_ASSET_TV.setText(getString(R.string.assetLabel)+SELECTED_ASSET);
                        Utility.setToast(InvoiceItemListActivity.this,CURRENT_ASSET_TV.getText().toString()).show();
                        editText_search_asset.setText("");
                        return true;
                    }else{

                        Utility.setToast(InvoiceItemListActivity.this,"Invalid Freezer#").show();
                        //Toast toast = Toast.makeText(getApplicationContext(), "Invalid Freezer#", Toast.LENGTH_SHORT);
                       //toast.show();
                    }



                }
                return false;
            }
        });

        getSalesInvoiceSelectedItem = new ArrayList<>();
    try{
        for (SalesOrderItemClass sd : itemClasses) {

            SalesInvoiceItemClass aClass = new SalesInvoiceItemClass();
            aClass.setINVOICE_ITEM_ITEMCODE(sd.getSALES_ITEM_CODE());
            aClass.setINVOICE_ITEM_ITEMNAME(sd.getSALES_ITEM_NAME());
            aClass.setINVOICE_ITEM_SALES_ORDER(sd.getSALES_ORDER_DOC_NO());
            aClass.setINVOICE_ITEM_STOCK_UOM(sd.getSALES_STOCK_UOM());
            aClass.setINVOICE_ITEM_WAREHOUSE(sd.getSALES_WAREHOUSE());
            aClass.setINVOICE_ITEM_DISCOUNT_PERCENTAGE(sd.getSALES_DISCOUNT_PERCENTAGE());
            aClass.setINVOICE_ITEM_TAX_RATE(sd.getSALES_TAX_RATE());
            aClass.setINVOICE_ITEM_RATE(sd.getSALES_RATE());
            aClass.setINVOICE_ITEM_PRICELIST_RATE(sd.getSALES_PRICE_LIST_RATE());
            if(IS_RETURN_SELECTION_STATUS.equals("1"))
            {
                aClass.setINVOICE_ITEM_QTY(-1*sd.getSALES_QTY());
                aClass.setINVOICE_ITEM_TAX_AMOUNT(-1*sd.getSALES_TAX_AMOUNT());
                aClass.setSALES_NET(-1*sd.getSALES_NET());
                aClass.setSALES_GROSS(-1*sd.getSALES_GROSS());
                aClass.setSALES_VAT(-1*sd.getSALES_VAT());
                aClass.setSALES_TOTAL(-1*sd.getSALES_TOTAL());
            }
            else
            {
                aClass.setINVOICE_ITEM_TAX_AMOUNT(sd.getSALES_TAX_AMOUNT());
                aClass.setINVOICE_ITEM_QTY(sd.getSALES_QTY());
                aClass.setSALES_NET(sd.getSALES_NET());
                aClass.setSALES_GROSS(sd.getSALES_GROSS());
                aClass.setSALES_VAT(sd.getSALES_VAT());
                aClass.setSALES_TOTAL(sd.getSALES_TOTAL());
            }
            if(sd.getSALES_ITEM_ASSET()==null)
                aClass.setINVOICE_ITEM_ASSET("");
            else
                aClass.setINVOICE_ITEM_ASSET(sd.getSALES_ITEM_ASSET());

            getSalesInvoiceSelectedItem.add(aClass);


        }
    }catch (Exception e) {
        (new UIHelper(this)).showErrorDialogOnGuiThread(e.getMessage());
        return;
    }


        /*

        I/RAW1_creation: <==2018-11-15
I/RAW1_owner: <==Administrator
I/RAW1_pri_list: <==SAR
I/RAW1_customer: <==SASCO
I/RAW1_company: <==Printechs Advanced Printing Trading Co.
I/RAW1_naming_series: <==Si_
I/RAW1_currency: <==SAR
I/RAW1_doc_no: <==Si_000006
I/RAW1_conver_rate: <==1
I/RAW1_plc_con_rate: <==1
I/RAW1_time: <==14:08:12
I/RAW1_posting_date: <==2018-11-15
I/RAW1_doc_status: <==1
I/RAW1_is_return: <==0
I/RAW1_device_id: <==805e6c047e03dbce
I/RAW-ITEM1_percentage: <==0
I/RAW-ITEM1_item_code: <==IND.CON.HIJ.1818
I/RAW-ITEM1_itemname: <==HITACHI MakeUp 1 Ltr TH-Type A
I/RAW-ITEM1_price_list: <==0
I/RAW-ITEM1_qty: <==1
I/RAW-ITEM1_rate: <==0
I/RAW-ITEM1_sales_order: <==So_000002
I/RAW-ITEM1_detail: <==
I/RAW-ITEM1_stock_uom: <==BTL
I/RAW-ITEM1_tax_amount: <==0
I/RAW-ITEM1_tax_rate: <==5
I/RAW-ITEM1_warehouse: <==Jeddah Office - WHS - PRNTX
I/RAW-ITEM1_key_id: <==6
I/RAW-ITEM1_sales_inv_id: <==5


*/


        db = new DatabaseHandler(this);


        SAVE_BUTTON = (Button) findViewById(R.id.save_request);
        CANCEL_BUTTON = (Button) findViewById(R.id.cancel_request);
        RECYCLER_VIEW = (RecyclerView) findViewById(R.id.recycler_view);
        EMPTY_TV = (TextView) findViewById(R.id.empty_tv);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RECYCLER_VIEW.setLayoutManager(mLayoutManager);
        RECYCLER_VIEW.setHasFixedSize(true);
        RECYCLER_VIEW.setItemAnimator(new DefaultItemAnimator());

        setData();

        SAVE_BUTTON.setOnClickListener(this);
        CANCEL_BUTTON.setOnClickListener(this);
        SCAN_IV.setOnClickListener(this);

    }

    private void setDataFromLocalDataBase() {

        getSalesInvoiceSelectedItem = db.getSalesInvoiceSelectedItem(db.getSalesInvoiceHighestID());
        setData();

    }

    private void setData() {

    try {
        if (!getSalesInvoiceSelectedItem.isEmpty()) {

            gross = 0.0f;
            net = 0.0f;
            vat = 0.0f;
            tot = 0.0f;
            total_qty=0.0f;

            for (SalesInvoiceItemClass si : getSalesInvoiceSelectedItem) {

                gross = gross + si.getSALES_GROSS();
                net = net + si.getSALES_NET();
                vat = vat + si.getSALES_VAT();
                tot = tot + si.getSALES_TOTAL();
                total_qty=total_qty+si.getINVOICE_ITEM_QTY();

            }


            TV_gross.setText(String.valueOf(Utility.round(gross, 2)));
            TV_net.setText(String.valueOf(Utility.round(net, 2)));
            TV_vat.setText(String.valueOf(Utility.round(vat, 2)));
            TV_total.setText(String.valueOf(Utility.round(tot, 2)));
            TV_total_qty.setText(String.valueOf(Utility.round(total_qty, 2)));

            EMPTY_TV.setVisibility(View.GONE);
            viewAdapter = new SelectedInvoiceRecyclerViewAdapter(InvoiceItemListActivity.this, getSalesInvoiceSelectedItem, new SelectedInvoiceRecyclerViewAdapter.ItemClickListener() {
                @Override
                public void onItemDeleteClick(View v, final int position) {

                    new android.app.AlertDialog.Builder(InvoiceItemListActivity.this)
                            .setMessage("Are you sure you want to delete?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {

                                    getSalesInvoiceSelectedItem.remove(position);
                                    viewAdapter.notifyDataSetChanged();

                                    Toast t = Utility.setToast(InvoiceItemListActivity.this, "DELETED ");
                                    t.show();

                                }
                            }).create().show();

                }
            });
            RECYCLER_VIEW.setAdapter(viewAdapter);
        } else {
            EMPTY_TV.setVisibility(View.VISIBLE);
        }
    }catch (Exception e) {
        (new UIHelper(this)).showErrorDialogOnGuiThread(e.getMessage());
        return;
    }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.invoice_itemlist_activity_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:

               /* for (SalesInvoiceItemClass sd : getSalesInvoiceSelectedItem) {

                    SalesOrderItemClass aClass = new SalesOrderItemClass();

                    aClass.setSALES_ITEM_CODE(sd.getINVOICE_ITEM_ITEMCODE());
                    aClass.setSALES_ITEM_NAME(sd.getINVOICE_ITEM_ITEMNAME());
                    aClass.setSALES_ORDER_DOC_NO(sd.getINVOICE_ITEM_SALES_ORDER());
                    aClass.setSALES_STOCK_UOM(sd.getINVOICE_ITEM_STOCK_UOM());
                    aClass.setSALES_WAREHOUSE(sd.getINVOICE_ITEM_WAREHOUSE());
                    aClass.setSALES_DISCOUNT_PERCENTAGE(sd.getINVOICE_ITEM_DISCOUNT_PERCENTAGE());
                    aClass.setSALES_QTY(sd.getINVOICE_ITEM_QTY());
                    aClass.setSALES_RATE(sd.getINVOICE_ITEM_RATE());
                    aClass.setSALES_PRICE_LIST_RATE(sd.getINVOICE_ITEM_PRICELIST_RATE());

                    getSalesOrderItem.add(aClass);

                }

                SalesInvoiceAddActivity.setSelectedClass(getSalesOrderItem);
*/

                finish();
                return true;

            case R.id.menu_add_item:
                if (!IS_RETURN_SELECTION_STATUS.equals("1")) {
                    addItemClick();
                }
                return true;

            case R.id.menu_get_item:
                if (!IS_RETURN_SELECTION_STATUS.equals("1")) {
                    getItemClick();
                }
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void getItemClick() {

        getSalesOrderItem = db.SalesInvoicegetSalesOrderItem(CUSTOMER_NAME);
        //  getSalesInvoiceSelectedItem.addAll(getSalesOrderItem);
        for (SalesOrderItemClass sd : getSalesOrderItem) {

            SalesInvoiceItemClass aClass = new SalesInvoiceItemClass();
            aClass.setKEY_ID(sd.getKEY_ID());
            aClass.setINVOICE_ITEM_ITEMCODE(sd.getSALES_ITEM_CODE());
            aClass.setINVOICE_ITEM_ITEMNAME(sd.getSALES_ITEM_NAME());
            aClass.setINVOICE_ITEM_SALES_ORDER(sd.getSALES_ORDER_DOC_NO());
            aClass.setINVOICE_ITEM_STOCK_UOM(sd.getSALES_STOCK_UOM());
            aClass.setINVOICE_ITEM_WAREHOUSE(sd.getSALES_WAREHOUSE());
            aClass.setINVOICE_ITEM_DISCOUNT_PERCENTAGE(sd.getSALES_DISCOUNT_PERCENTAGE());
            aClass.setINVOICE_ITEM_QTY(sd.getSALES_QTY());
            aClass.setINVOICE_ITEM_RATE(sd.getSALES_RATE());
            aClass.setINVOICE_ITEM_PRICELIST_RATE(sd.getSALES_PRICE_LIST_RATE());
            aClass.setSALES_GROSS(sd.getSALES_GROSS());
            aClass.setSALES_NET(sd.getSALES_NET());
            aClass.setSALES_VAT(sd.getSALES_VAT());
            aClass.setSALES_TOTAL(sd.getSALES_TOTAL());
            aClass.setINVOICE_ITEM_TAX_RATE(sd.getSALES_TAX_RATE());
            aClass.setINVOICE_ITEM_TAX_AMOUNT(sd.getSALES_TAX_AMOUNT());

            getSalesInvoiceSelectedItem.add(aClass);


        }

        setData();

    }


    private void addItemClick() {

        if(SELECTED_ASSET!="") {
            Intent item_add = new Intent(InvoiceItemListActivity.this, InvoiceItemListAddActivity.class);
            startActivityForResult(item_add, ITEM_ADD_REQUEST_CODE);
        }
        else
        {
            Utility.setToast(this,"Select Freezer# first").show();
            editText_search_asset.setFocusable(true);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ITEM_ADD_REQUEST_CODE) {

           /* String message = data.getStringExtra("cstmr_name");
            selected_customer_id = data.getStringExtra("cstmr_id");
            CUSTOMER_TV.setText(message);*/
            setData();
        }
        else
        {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            Utility.setToast(this,resultCode+"");
            if (scanResult != null) {


                String searchText = scanResult.getContents();
                Log.d(LOGTAG, "Have scan result in your app activity :" + searchText);

                Toast.makeText(this, "" + searchText, Toast.LENGTH_SHORT).show();

                if(searchText.length()==13 ||searchText.length()==12 ){
                    if(searchText.endsWith(GetChecksumDigit(searchText))){
                        searchText=(searchText.substring(0,searchText.length()-1)).replaceFirst("^0+(?!$)", "");
                    }
                }
                if(db.IsValidCustomerAsset(salesInvoiceheader.getSALES_INVOICE_CUSTOMER(),searchText)) {
                    SELECTED_ASSET = searchText;
                    CURRENT_ASSET_TV.setText(getString(R.string.assetLabel) + SELECTED_ASSET);
                }else{

                    Toast.makeText(getApplicationContext(), "Invalid Freezer#", Toast.LENGTH_SHORT).show();

                }



            }

            }
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.save_request:

                AlertDialog.Builder myalert = new AlertDialog.Builder(this);
                myalert.setTitle("Confirm");
                myalert.setMessage("Do you want to save?");
                myalert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*ProgressDialog dialog = new ProgressDialog(InvoiceItemListActivity.this);
                        dialog.setMessage("Saving...");
                        dialog.setIndeterminate(true);
                        dialog.setCancelable(false);
                        dialog.show();*/
                        saveClick();
                       // if(dialog.isShowing())
                          //  dialog.dismiss();
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
            case R.id.cancel_request:
                SELECTED_ASSET="";
                finish();
                break;
            case R.id.scan_btn:
                IntentIntegrator integrator = new IntentIntegrator(InvoiceItemListActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan a barcode");
                integrator.initiateScan();
                break;

        }


    }
    private String createZplReceipt() {
        String tmpHeader =
                "^XA" +
                        "^POI^PW600^MNN^LL%d^LH0,0"+
                        "^FO20,50" + "\r\n" +"^A0,N,35,40"+ "\r\n" +"^FD "+db.getCompanyName()+"^FS"+ "\r\n" +
                        "^FO140,95" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FDVAT#302008396200003^FS"+ "\r\n" +
                        "^FO180,130" + "\r\n" +"^A0,N,30,40"+ "\r\n" +"^FDTax Invoice^FS"+ "\r\n" +
                        "^FO10,190" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDBill To^FS"+ "\r\n" +
                        "^FO150,190" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                        "^FO170,190" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+salesInvoiceheader.getSALES_INVOICE_CUSTOMER()+"^FS"+ "\r\n" +

                        "^FO10,230" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDVAT#^FS"+ "\r\n" +
                        "^FO150,230" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                        "^FO170,230" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+db.getCustomerVATNo(salesInvoiceheader.getSALES_INVOICE_CUSTOMER())+"^FS"+ "\r\n" +

                        "^FO10,270" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDInvoice#^FS"+ "\r\n" +
                        "^FO150,270" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                        "^FO170,270" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+salesInvoiceheader.getSALES_INVOICE_DOC_NO()+"^FS"+ "\r\n" +
                        "^FO300,270" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDDate^FS"+ "\r\n" +
                        "^FO350,270" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                        "^FO360,270" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+salesInvoiceheader.getSALES_INVOICE_CREATION()+"^FS"+ "\r\n" +
                        "^FO10,310" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" +
                        "^FO10,320" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDS#^FS"+ "\r\n" +
                        "^FO50,320" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDDescription^FS"+ "\r\n" +
                        "^FO360,320" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDQty^FS"+ "\r\n" +
                        "^FO430,320" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDRate^FS"+ "\r\n" +
                        "^FO500,320" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDAmount^FS"+ "\r\n" +
                        "^FO10,350" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" ;
        int headerHeight = 365;
        String body = String.format("^LH0,%d", headerHeight);

        int heightOfOneLine = 40;
        print_sn=1;
        String itemName="";
        int i = 0;
        for (SalesInvoiceItemClass si : getSalesInvoiceSelectedItem) {

            //  if(si.getINVOICE_ITEM_ITEMNAME().length()>14)
            //    itemName=si.getINVOICE_ITEM_ITEMNAME().substring(0,14);
            //else
            itemName=si.getINVOICE_ITEM_ITEMNAME();
            // BILL = BILL + "\n\r " + String.format("%1$-3s %2$-14s %3$8s %4$7s %5$8s", ""+print_sn, itemName, ""+si.getINVOICE_ITEM_QTY(), ""+si.getINVOICE_ITEM_RATE(), ""+si.getSALES_NET());

            String lineItem = "^FO10,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS" + "\r\n" +//seqno
                    "^FO50,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +//description
                    "^FO360,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +//qty
                    "^FO430,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +//rate
                    "^FO500,%d" + "\r\n" + "^A0,N,22,40" + "\r\n" + "^FD%s^FS"+ "\r\n" +//total
                    "";

            int totalHeight = i++ * heightOfOneLine;
            body += String.format(lineItem, totalHeight, print_sn, totalHeight, itemName,totalHeight,si.getINVOICE_ITEM_QTY().toString(),totalHeight,Utility.roundToTwoDecimal(si.getINVOICE_ITEM_RATE().toString()),totalHeight,Utility.getRightString(Utility.roundToTwoDecimal(si.getSALES_NET().toString()),10));
            print_sn = print_sn + 1;
            print_subtot = print_subtot + si.getSALES_NET();
            print_qty = print_qty + (int) Math.round(si.getINVOICE_ITEM_QTY());
            print_vat = print_vat + si.getSALES_VAT();
            print_total=print_subtot+print_vat;
        }
        long totalBodyHeight = (print_sn) * heightOfOneLine;

        long footerStartPosition = headerHeight + totalBodyHeight;

        String foot = "^LH0,%d" + "\r\n" +
                "^FO10,30" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n"+
                "^FO360,40" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDSubTotal^FS"+ "\r\n" +
                "^FO450,40" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                "^FO500,40" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD%s^FS"+ "\r\n" +//subtotal
                "^FO360,70" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDVat^FS"+ "\r\n" +
                "^FO450,70" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                "^FO500,70" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD%s^FS"+ "\r\n" +//totalvat
                "^FO10,100" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n"+
                "^FO200,110" + "\r\n" +"^A0,N,30,50"+ "\r\n" +"^FDTotal^FS"+ "\r\n" +
                "^FO360,110" + "\r\n" +"^A0,N,30,50"+ "\r\n" +"^FD%s^FS"+ "\r\n" +//totalqty
                "^FO480,110" + "\r\n" +"^A0,N,30,50"+ "\r\n" +"^FD%s^FS"+ "\r\n" +//totalamount
                "^FO10,140" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n"+
                "^FO350,240" + "\r\n" +"^A0,N,25,50"+ "\r\n" +"^FDAuthorized Signatory^FS"+ "\r\n" +
                "^FO10,290" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDWe declare that this invoice shows the actual price of the^FS"+ "\r\n" +
                "^FO10,320" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDgoods described and that all particulars are true and correct.^FS"+ "\r\n" +
                "^FO120,400" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD----- Thanking you ------ ^FS"+ "\r\n" +
                "^XZ";
        String footer = String.format(foot,footerStartPosition,Utility.roundToTwoDecimal(print_subtot.toString()),Utility.roundToTwoDecimal(print_vat.toString()),print_qty+"",Utility.roundToTwoDecimal(print_total.toString()));
        long footerHeight = 500;
        long labelLength = headerHeight + totalBodyHeight + footerHeight;
        String header = String.format(tmpHeader, labelLength);
        String wholeZplLabel = String.format("%s%s%s", header, body, footer);
        return wholeZplLabel;
    }

    private void saveClick() {

        try {




            // Toast.makeText(this, ""+CASH_SELECTION_STATUS, Toast.LENGTH_SHORT).show();

            // if (IS_RETURN_SELECTION_STATUS.equals("1")) {
            Float qty = 0f;
            Float total_net=0.0f;
            for (SalesInvoiceItemClass sc : getSalesInvoiceSelectedItem) {

                qty = sc.getINVOICE_ITEM_QTY();
                //if(IS_RETURN_SELECTION_STATUS.equals("1"))
                //   qty=-qty;
                total_net=total_net+sc.getSALES_TOTAL();
                credit_total = credit_total + (sc.getINVOICE_ITEM_RATE() * qty);

            }

            if (credit_total <= db.getCreditLimit() || CASH_SELECTION_STATUS.equals("1")) {

                if (!db.IsInvoiceExists(salesInvoiceheader.getSALES_INVOICE_DOC_NO())) {
                    db.addSalesInvoice(salesInvoiceheader);
                    db.updateSalesInvoiceDocNo(db.getSalesInvoiceDocNo() + 1);
                    saveCustomerVisit(total_net);
                }

                for (SalesInvoiceItemClass sc : getSalesInvoiceSelectedItem) {

                    Log.i("SC", "<==" + sc.getINVOICE_ITEM_ITEMNAME());

                    qty = sc.getINVOICE_ITEM_QTY();
                    Float gross = sc.getSALES_GROSS();
                    Float net = sc.getSALES_NET();
                    Float vat = sc.getSALES_VAT();
                    Float total = sc.getSALES_TOTAL();

                /*if(IS_RETURN_SELECTION_STATUS.equals("1"))
                {
                    qty=-qty;
                    gross=-gross;
                    net=-net;
                    vat=-vat;
                    total=-total;
                }*/

                    db.addSalesInvoiceItems(new SalesInvoiceItemClass(db.getSalesInvoiceHighestID(), db.getWarehouse(), sc.getINVOICE_ITEM_ITEMNAME(), sc.getINVOICE_ITEM_STOCK_UOM(), sc.getINVOICE_ITEM_ITEMCODE(), sc.getINVOICE_ITEM_SALES_ORDER(), "", qty, sc.getINVOICE_ITEM_RATE(), sc.getINVOICE_ITEM_PRICELIST_RATE(), sc.getINVOICE_ITEM_DISCOUNT_PERCENTAGE(), Float.parseFloat(db.getTaxRate()), Float.parseFloat("0"), gross, net, vat, total, sc.getINVOICE_ITEM_ASSET()));
                    db.updateSalesOrderDeliveryStatus(String.valueOf(sc.getKEY_ID()), "1");
                    sales_invoice_payment_total = sales_invoice_payment_total + total;
                }

                if (CASH_SELECTION_STATUS.equals("1")) {

                    db.addSalesInvoiceModePayment(new SalesInvoiceModeOfPayment(db.getSalesInvoiceHighestID(), db.getPaidTo(db.getDefaultModeOfPayment()), db.getDefaultModeOfPayment(), String.valueOf(sales_invoice_payment_total), String.valueOf(sales_invoice_payment_total), db.getDefaultModeOfPayment()));//)
                }

                Toast t = Utility.setToast(InvoiceItemListActivity.this, "Save selected");
                t.show();
                if(check_print_after_save.isChecked())
                new zebraPrint().PrintToZebra(createZplReceipt(),this);
                SELECTED_ASSET="";
                Intent save_intent = new Intent(InvoiceItemListActivity.this, SalesOrderInvoiceActivity.class);
                startActivity(save_intent);

            } else {

                Utility.showDialog(InvoiceItemListActivity.this, "ERROR !", "Exceed Credit Limit ! \n Credit Limit is " + db.getCreditLimit(), R.color.dialog_error_background);

            }
        }catch (Exception e){
            Utility.showDialog(InvoiceItemListActivity.this, "ERROR !", e.getMessage()+" \n Credit Limit is " + db.getCreditLimit(), R.color.dialog_error_background);
        }




    }
    private void saveCustomerVisit(Float net){
        try{
            //List<String> loc=getLocation();
            //if(loc.size()==0)
               // loc=gpsloc;
            //Thread.sleep(1500);
            String creationDateTime=salesInvoiceheader.getSALES_INVOICE_CREATION();

            CustomerVisitLog cv=new CustomerVisitLog();
            cv.setCUSTOMER_VISIT_KEY_ID(db.getCustomerVisitHighestID()+1);
            cv.setCUSTOMER_VISIT_SALES_PERSON(Utility.getLoginUser(this));
            cv.setCUSTOMER_VISIT_VISIT_DATE(creationDateTime);
            cv.setCUSTOMER_VISIT_MODIFIED(creationDateTime);
            cv.setCUSTOMER_VISIT_VISIT_RESULT("Sale");
            cv.setCUSTOMER_VISIT_AMOUNT(net);
            cv.setCUSTOMER_VISIT_CUSTOMER(salesInvoiceheader.getSALES_INVOICE_CUSTOMER());
            cv.setCUSTOMER_VISIT_COMMENTS("");
            cv.setCUSTOMER_VISIT_DOC_STATUS(0);
            cv.setCUSTOMER_VISIT_OWNER(Utility.getLoginUser(this));
            cv.setCUSTOMER_VISIT_CREATION(creationDateTime);
            cv.setCUSTOMER_VISIT_NAMING_SERIES(db.getCusomerVisitName());
            cv.setCUSTOMER_VISIT_REFERENCE(salesInvoiceheader.getSALES_INVOICE_DOC_NO());
            cv.setCUSTOMER_VISIT_MODIFIED_BY(Utility.getLoginUser(this));
            cv.setCUSTOMER_VISIT_SYNC_STATUS("0");
            cv.setCUTOMER_VISIT_IDX(0);
            if(Utility.googleLoc.isEmpty()){
                cv.setCUSTOMER_VISIT_LONGITUDE("0");
                cv.setCUSTOMER_VISIT_LATITUDE("0");
            }
            else{
                cv.setCUSTOMER_VISIT_LONGITUDE(Utility.googleLoc.get(1));
                cv.setCUSTOMER_VISIT_LATITUDE(Utility.googleLoc.get(0));
            }


            db.addCustomerVisitLog(cv);


        }catch (Exception e){
            Utility.showDialog(InvoiceItemListActivity.this, "ERROR !", e.getMessage(), R.color.dialog_error_background);
        }

    }
    public static void setSalesInvoiceClass(SalesInvoiceClass invoiceClass) {
        salesInvoiceheader = invoiceClass;
    }
    public static void setCustomerName(String name) {
        CUSTOMER_NAME = name;
    }

    public static void setCustomerId(String id) {
        selected_customer_id = id;
    }

    public static void setReturnItems(List<SalesOrderItemClass> return_data) {

        itemClasses = new ArrayList<SalesOrderItemClass>();
        //  getSalesInvoiceSelectedItem = new ArrayList<>();
        itemClasses.addAll(return_data);

       /* for (SalesOrderItemClass sd : return_data) {

            SalesInvoiceItemClass aClass = new SalesInvoiceItemClass();
            aClass.setINVOICE_ITEM_ITEMCODE(sd.getSALES_ITEM_CODE());
            aClass.setINVOICE_ITEM_ITEMNAME(sd.getSALES_ITEM_NAME());
            aClass.setINVOICE_ITEM_SALES_ORDER(sd.getSALES_ORDER_DOC_NO());
            aClass.setINVOICE_ITEM_STOCK_UOM(sd.getSALES_STOCK_UOM());
            aClass.setINVOICE_ITEM_WAREHOUSE(sd.getSALES_WAREHOUSE());
            aClass.setINVOICE_ITEM_DISCOUNT_PERCENTAGE(sd.getSALES_DISCOUNT_PERCENTAGE());
            aClass.setINVOICE_ITEM_QTY(sd.getSALES_QTY());
            aClass.setINVOICE_ITEM_RATE(sd.getSALES_RATE());
            aClass.setINVOICE_ITEM_PRICELIST_RATE(sd.getSALES_PRICE_LIST_RATE());

            getSalesInvoiceSelectedItem.add(aClass);


        }*/
    }

    public static void setSelectedItem(List<SalesOrderItemClass> return_data) {

        for (SalesOrderItemClass sd : return_data) {

            SalesInvoiceItemClass aClass = new SalesInvoiceItemClass();
            aClass.setINVOICE_ITEM_ITEMCODE(sd.getSALES_ITEM_CODE());
            aClass.setINVOICE_ITEM_ITEMNAME(sd.getSALES_ITEM_NAME());
            aClass.setINVOICE_ITEM_SALES_ORDER(sd.getSALES_ORDER_DOC_NO());
            aClass.setINVOICE_ITEM_STOCK_UOM(sd.getSALES_STOCK_UOM());
            aClass.setINVOICE_ITEM_WAREHOUSE(sd.getSALES_WAREHOUSE());
            aClass.setINVOICE_ITEM_DISCOUNT_PERCENTAGE(sd.getSALES_DISCOUNT_PERCENTAGE());
            aClass.setINVOICE_ITEM_QTY(sd.getSALES_QTY());
            aClass.setINVOICE_ITEM_RATE(sd.getSALES_RATE());
            if(sd.getSALES_RATE()==0){
                aClass.setINVOICE_ITEM_PRICELIST_RATE(0.0f);
                aClass.setSALES_VAT(0.0f);
            }
            else{
                aClass.setINVOICE_ITEM_PRICELIST_RATE(sd.getSALES_PRICE_LIST_RATE());
                aClass.setSALES_VAT(sd.getSALES_VAT());
            }

            aClass.setSALES_GROSS(sd.getSALES_GROSS());
            aClass.setSALES_NET(sd.getSALES_NET());
            aClass.setSALES_VAT(sd.getSALES_VAT());
            aClass.setSALES_TOTAL(sd.getSALES_TOTAL());
            if(sd.getSALES_ITEM_ASSET()!=null && sd.getSALES_ITEM_ASSET()!="")
            aClass.setINVOICE_ITEM_ASSET(sd.getSALES_ITEM_ASSET());
            else
                aClass.setINVOICE_ITEM_ASSET(SELECTED_ASSET);
            getSalesInvoiceSelectedItem.add(aClass);

        }


    }
    private List<String> getLocation()
    {
        final List<String> gpsLoc=new ArrayList<String>();
        LocationResult locationResult = new LocationResult() {
            @Override
            public void gotLocation(Location location) {
                gpsLoc.add(String.valueOf(location.getLatitude()));
                gpsLoc.add(String.valueOf(location.getLongitude()));
            }
        };
        LocationTrack myLocation = new LocationTrack();
        myLocation.getLocation(this, locationResult);

        return gpsLoc;
    }

    @Override
    public void onBackPressed() {
        SELECTED_ASSET="";
        finish();

       /* for (SalesInvoiceItemClass sd : getSalesInvoiceSelectedItem) {

            SalesOrderItemClass aClass = new SalesOrderItemClass();

            aClass.setSALES_ITEM_CODE(sd.getINVOICE_ITEM_ITEMCODE());
            aClass.setSALES_ITEM_NAME(sd.getINVOICE_ITEM_ITEMNAME());
            aClass.setSALES_ORDER_DOC_NO(sd.getINVOICE_ITEM_SALES_ORDER());
            aClass.setSALES_STOCK_UOM(sd.getINVOICE_ITEM_STOCK_UOM());
            aClass.setSALES_WAREHOUSE(sd.getINVOICE_ITEM_WAREHOUSE());
            aClass.setSALES_DISCOUNT_PERCENTAGE(sd.getINVOICE_ITEM_DISCOUNT_PERCENTAGE());
            aClass.setSALES_QTY(sd.getINVOICE_ITEM_QTY());
            aClass.setSALES_RATE(sd.getINVOICE_ITEM_RATE());
            aClass.setSALES_PRICE_LIST_RATE(sd.getINVOICE_ITEM_PRICELIST_RATE());

            getSalesOrderItem.add(aClass);

        }

        SalesInvoiceAddActivity.setSelectedClass(getSalesOrderItem);

*/
    }
    public String GetChecksumDigit(String sTemp)
    {

        int iSum = 0;
        int iDigit = 0;
        String ChecksumDigit ="";
        try{
            // Calculate the checksum digit here.
            for (int i = sTemp.length()-1; i >= 1; i--)
            {
                iDigit = Integer.parseInt(sTemp.substring(i - 1, i));
                if (i % 2 == 0)
                {	// odd
                    iSum += iDigit * 3;
                }
                else
                {	// even
                    iSum += iDigit * 1;
                }
            }

            int iCheckSum = (10 - (iSum % 10)) % 10;
            ChecksumDigit = String.valueOf(iCheckSum);

        }catch (Exception e){
            Utility.showDialog(InvoiceItemListActivity.this, "ERROR !", e.getMessage(), R.color.dialog_error_background);

        }

        return ChecksumDigit;

    }
}
