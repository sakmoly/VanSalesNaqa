package com.van.sale.vansale.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itextpdf.text.pdf.parser.Line;
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.van.sale.vansale.Bluetooth_Print.UnicodeFormatter;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.DeviceListActivity;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.adapter.SelectedInvoiceRecyclerViewAdapter;
import com.van.sale.vansale.adapter.SelectedInvoiceRecyclerViewDetailAdapter;
import com.van.sale.vansale.model.SalesInvoiceItemClass;
import com.van.sale.vansale.model.SalesOrderItemClass;
import com.van.sale.vansale.model.SelectedItemClass;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*import com.zebra.android.devdemo.ConnectionScreen;
import com.zebra.android.devdemo.R;
import com.zebra.android.devdemo.util.DemoSleeper;
import com.zebra.android.devdemo.util.SettingsHelper;*/
import com.van.sale.vansale.UIHelper;
import com.van.sale.vansale.model.SettingsClass;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import static com.koushikdutta.async.AsyncServer.LOGTAG;

public class InvoiceDetailItemListActivity extends AppCompatActivity implements View.OnClickListener, RecyclerTouchListener.RecyclerTouchListenerHelper, InvoiceDetailDialog.DetailDialogListener, Runnable {

    Connection printerConnection = null;
    private UIHelper helper = new UIHelper(this);
    private ImageView SCAN_IV;
    public static final int ITEM_ADD_REQUEST_CODE = 229;
    private Button SAVE_BUTTON, CANCEL_BUTTON;
    private static String CUSTOMER_NAME, selected_customer_id;
    private RecyclerView RECYCLER_VIEW;
    private TextView EMPTY_TV,CURRENT_ASSET_TV;
    private EditText editText_search_asset;
    private static String SELECTED_ASSET="";
    private SelectedInvoiceRecyclerViewDetailAdapter viewAdapter;
    private static List<SalesOrderItemClass> itemClasses;
    // private List<SalesOrderItemClass> getSalesOrderItem;
    private List<SalesInvoiceItemClass> getSalesInvoiceSelectedItem;
    private DatabaseHandler db;
    private String INVOICE_CUSTOMER_ID, INVOICE_CUSTOMER_NAME, INVOICE_NO, INVOICE_DATE,INVOICE_SYNC_STATUS;
    private RecyclerTouchListener onTouchListener;
    private OnActivityTouchListener touchListener;
    private static String selected_uom, selected_discount_percentage, selected_rate, selected_quantity, selected_price_display, selected_item_code, selected_item_id;
    private static int selected_position;
    private InvoiceDetailDialog detailDialog;
    private TextView TV_gross, TV_net, TV_vat, TV_total;
    private Float gross = 0.0f, net = 0.0f, vat = 0.0f, tot = 0.0f;
    private BluetoothSocket mBluetoothSocket;
    BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    BluetoothDevice mBluetoothDevice;
    private ProgressDialog mBluetoothConnectProgressDialog;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private int print_sn = 0,print_qty = 0;
    private Float print_subtot = 0.0f;
    private Float print_vat = 0.0f;
    private Float print_total = 0.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_invoice_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("ITEMS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
       /* if (mBluetoothAdapter == null) {
            Toast.makeText(InvoiceDetailItemListActivity.this, "Message1", Toast.LENGTH_SHORT).show();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,
                        REQUEST_ENABLE_BT);
            } else {
                // ListPairedDevices();
                Intent connectIntent = new Intent(InvoiceDetailItemListActivity.this,
                        DeviceListActivity.class);
                startActivityForResult(connectIntent,
                        REQUEST_CONNECT_DEVICE);
            }
        }
    */

        db = new DatabaseHandler(this);

        INVOICE_CUSTOMER_ID = getIntent().getStringExtra("INVOICE_CUSTOMER_ID");
        INVOICE_CUSTOMER_NAME = getIntent().getStringExtra("INVOICE_CUSTOMER_NAME");
        INVOICE_NO = getIntent().getStringExtra("INVOICE_NO");
        INVOICE_DATE = getIntent().getStringExtra("INVOICE_DATE");
        INVOICE_SYNC_STATUS = getIntent().getStringExtra("INVOICE_SYNC_STATUS");

        CURRENT_ASSET_TV=(TextView)findViewById(R.id.current_asset_tv);
        TV_gross = (TextView) findViewById(R.id.TV_gross);
        TV_net = (TextView) findViewById(R.id.TV_net);
        TV_vat = (TextView) findViewById(R.id.TV_vat);
        TV_total = (TextView) findViewById(R.id.TV_total);

        SAVE_BUTTON = (Button) findViewById(R.id.save_request);
        CANCEL_BUTTON = (Button) findViewById(R.id.cancel_request);
        RECYCLER_VIEW = (RecyclerView) findViewById(R.id.recycler_view);
        EMPTY_TV = (TextView) findViewById(R.id.empty_tv);
        SAVE_BUTTON.setVisibility(View.GONE);
        // itemClasses = new ArrayList<SalesOrderItemClass>();
        SCAN_IV=(ImageView)findViewById(R.id.scan_btn);
        editText_search_asset=(EditText)findViewById(R.id.ed_asset_search);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RECYCLER_VIEW.setLayoutManager(mLayoutManager);
        RECYCLER_VIEW.setHasFixedSize(true);
        // RECYCLER_VIEW.setItemAnimator(new DefaultItemAnimator());
        RECYCLER_VIEW.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

        getSalesInvoiceSelectedItem = db.getSalesInvoiceSelectedItem(Integer.parseInt(INVOICE_CUSTOMER_ID));


        for (SalesInvoiceItemClass si : getSalesInvoiceSelectedItem) {

          /*  Log.i("CART", "<==" + si.getSALES_ITEM_NAME());
            Log.i("CART", "<==" + si.getSALES_STOCK_UOM());*/

            gross = gross + si.getSALES_GROSS();
            net = net + si.getSALES_NET();
            vat = vat + si.getSALES_VAT();
            tot = tot + si.getSALES_TOTAL();

        }


        TV_gross.setText(String.valueOf(Utility.round(gross, 2)));
        TV_net.setText(String.valueOf(Utility.round(net, 2)));
        TV_vat.setText(String.valueOf(Utility.round(vat, 2)));
        TV_total.setText(String.valueOf(Utility.round(tot, 2)));



       /* if (!getSalesInvoiceSelectedItem.isEmpty()) {
            EMPTY_TV.setVisibility(View.GONE);*/
        viewAdapter = new SelectedInvoiceRecyclerViewDetailAdapter(getApplicationContext(), getSalesInvoiceSelectedItem);
        RECYCLER_VIEW.setAdapter(viewAdapter);
        onTouchListener = new RecyclerTouchListener(this, RECYCLER_VIEW);
        onTouchListener
                .setIndependentViews(R.id.total_amount)
                .setViewsToFade(R.id.total_amount)
                .setSwipeOptionViews(R.id.edit, R.id.delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, final int position) {
                        // Toast.makeText(InvoiceDetailItemListActivity.this, "clicked "+position, Toast.LENGTH_SHORT).show();
                        if (viewID == R.id.edit) {

                            // Toast.makeText(InvoiceDetailItemListActivity.this, "Edit clicked", Toast.LENGTH_SHORT).show();

                            if (INVOICE_SYNC_STATUS.equals("0")) {

                                selected_item_id = String.valueOf(getSalesInvoiceSelectedItem.get(position).getKEY_ID());
                                selected_item_code = getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_ITEMCODE();
                                selected_price_display = String.valueOf(getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_PRICELIST_RATE());
                                selected_quantity = String.valueOf((int) Math.round(getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_QTY()));
                                selected_discount_percentage = String.valueOf(getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_DISCOUNT_PERCENTAGE());
                                selected_rate = String.valueOf(getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_RATE());
                                selected_uom = String.valueOf(getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_STOCK_UOM());
                                selected_position = position;


                                detailDialog = new InvoiceDetailDialog();
                                detailDialog.show(getSupportFragmentManager(), "example dialog");

                            }else {
                                Utility.showDialog(InvoiceDetailItemListActivity.this, "WARNING !", "Server Data!\nUnable to edit...", R.color.dialog_network_error_background);
                            }



                        } else if (viewID == R.id.delete) {

                            if (INVOICE_SYNC_STATUS.equals("0")) {
                                new android.app.AlertDialog.Builder(InvoiceDetailItemListActivity.this)
                                        .setMessage("Are you sure you want to delete?")
                                        .setNegativeButton(android.R.string.no, null)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface arg0, int arg1) {

                                                db.deleteInvoice(getSalesInvoiceSelectedItem.get(position).getKEY_ID());

                                                getSalesInvoiceSelectedItem.remove(position);
                                                viewAdapter.notifyDataSetChanged();

                                                Toast t = Utility.setToast(InvoiceDetailItemListActivity.this, "Deleted");
                                                t.show();


                                            }
                                        }).create().show();
                            }else {
                                Utility.showDialog(InvoiceDetailItemListActivity.this, "WARNING !", "Server Data!\nUnable to delete...", R.color.dialog_network_error_background);

                            }
                        }
                    }
                });



       /* } else {
            EMPTY_TV.setVisibility(View.VISIBLE);
        }
*/


        //setData();
        //  setDataFromLocalDataBase();

        editText_search_asset.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(db.IsValidCustomerAsset(INVOICE_CUSTOMER_NAME,editText_search_asset.getText().toString())){
                        SELECTED_ASSET=editText_search_asset.getText().toString();
                        CURRENT_ASSET_TV.setText(getString(R.string.assetLabel)+SELECTED_ASSET);
                        editText_search_asset.setText("");
                        Utility.setToast(InvoiceDetailItemListActivity.this,CURRENT_ASSET_TV.getText().toString()).show();
                        return true;
                    }else{

                        Utility.setToast(InvoiceDetailItemListActivity.this,"Invalid Freezer#").show();
                        //Toast toast = Toast.makeText(getApplicationContext(), "Invalid Freezer#", Toast.LENGTH_SHORT);
                        //toast.show();
                    }



                }
                return false;
            }
        });

        if(INVOICE_SYNC_STATUS.equals("1"))
        {

            editText_search_asset.setEnabled(false);
            SCAN_IV.setEnabled(false);
            //LinearLayout assetSearch=(LinearLayout)findViewById(R.id.assetSearch);
            //assetSearch.setEnabled(false);
        }

     //   SCAN_IV.setOnClickListener(this);
        SAVE_BUTTON.setOnClickListener(this);
        CANCEL_BUTTON.setOnClickListener(this);


    }


    private void setDataFromLocalDataBase() {

        getSalesInvoiceSelectedItem = db.getSalesInvoiceSelectedItem(Integer.parseInt(INVOICE_CUSTOMER_ID));

        gross = 0.0f;
        net = 0.0f;
        vat = 0.0f;
        tot = 0.0f;

        for (SalesInvoiceItemClass si : getSalesInvoiceSelectedItem) {

          /*  Log.i("CART", "<==" + si.getSALES_ITEM_NAME());
            Log.i("CART", "<==" + si.getSALES_STOCK_UOM());*/

            gross = gross + si.getSALES_GROSS();
            net = net + si.getSALES_NET();
            vat = vat + si.getSALES_VAT();
            tot = tot + si.getSALES_TOTAL();

        }


        TV_gross.setText(String.valueOf(Utility.round(gross, 2)));
        TV_net.setText(String.valueOf(Utility.round(net, 2)));
        TV_vat.setText(String.valueOf(Utility.round(vat, 2)));
        TV_total.setText(String.valueOf(Utility.round(tot, 2)));

        setData();


    }


    private void setData() {

        viewAdapter = new SelectedInvoiceRecyclerViewDetailAdapter(getApplicationContext(), getSalesInvoiceSelectedItem);
        RECYCLER_VIEW.setAdapter(viewAdapter);
        onTouchListener = new RecyclerTouchListener(this, RECYCLER_VIEW);
        onTouchListener
                .setIndependentViews(R.id.total_amount)
                .setViewsToFade(R.id.total_amount)
                .setSwipeOptionViews(R.id.edit, R.id.delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, final int position) {
                        // Toast.makeText(InvoiceDetailItemListActivity.this, "clicked "+position, Toast.LENGTH_SHORT).show();
                        if (viewID == R.id.edit) {

                            // Toast.makeText(InvoiceDetailItemListActivity.this, "Edit clicked", Toast.LENGTH_SHORT).show();


                            selected_item_id = String.valueOf(getSalesInvoiceSelectedItem.get(position).getKEY_ID());
                            selected_item_code = getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_ITEMCODE();
                            selected_price_display = String.valueOf(getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_PRICELIST_RATE());
                            selected_quantity = String.valueOf((int) Math.round(getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_QTY()));
                            selected_discount_percentage = String.valueOf(getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_DISCOUNT_PERCENTAGE());
                            selected_rate = String.valueOf(getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_RATE());
                            selected_uom = String.valueOf(getSalesInvoiceSelectedItem.get(position).getINVOICE_ITEM_STOCK_UOM());
                            selected_position = position;


                            detailDialog = new InvoiceDetailDialog();
                            detailDialog.show(getSupportFragmentManager(), "example dialog");


                        } else if (viewID == R.id.delete) {


                            new android.app.AlertDialog.Builder(InvoiceDetailItemListActivity.this)
                                    .setMessage("Are you sure you want to delete?")
                                    .setNegativeButton(android.R.string.no, null)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            db.deleteInvoice(getSalesInvoiceSelectedItem.get(position).getKEY_ID());

                                            getSalesInvoiceSelectedItem.remove(position);
                                            viewAdapter.notifyDataSetChanged();

                                            Toast t = Utility.setToast(InvoiceDetailItemListActivity.this, "Deleted");
                                            t.show();


                                        }
                                    }).create().show();

                        }
                    }
                });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.invoice_detaillist_itemlist_activity_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.menu_print:
                printCall();
                        return true;

            case R.id.menu_add_item:

                addmoreItemClick();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void printCall1() {


        Thread t = new Thread() {
            public void run() {
                try {
                    OutputStream os = mBluetoothSocket
                            .getOutputStream();
                    //String s="\r\n";
                    //byte b1[]=s.getBytes();
                    String BILL = "";

                    BILL = Html.fromHtml("    "+db.getCompanyName()+" </b> ")+"\r\n";

                    BILL = BILL + "-----------------------------------------------\r\n";
                    BILL = BILL + "Bill To:                              Invoice \r\n";
                    BILL = BILL + INVOICE_CUSTOMER_NAME + "  \r\n";
                    BILL = BILL + "                        Invoice No.:" + INVOICE_NO + " \r\n";
                    BILL = BILL + "                        Dated      :" + INVOICE_DATE + "\r\n";
                    BILL = BILL + "                        Due Dated  :" + INVOICE_DATE + "\r\n";



                  /*  BILL = "                   XXXX MART   \r\n" +
                            "                   XX.AA.BB.CC.     \r\n" +
                            "                 NO 25 ABC ABCDE    \r\n" +
                            "                  XXXXX YYYYYY      \r\n" +
                            "                   MMM 590019091      \r\n";
                    BILL = BILL + "-----------------------------------------------\r\n";
*/
                    BILL = BILL
                            + "-----------------------------------------------";
                    BILL = BILL + "\r\n";

                    BILL = BILL + String.format("%1$-3s %2$-14s %3$8s %4$7s %5$8s", "SN", "Description", "Quantity", "Rate", "Amount");
                    BILL = BILL + "\r\n";
                    BILL = BILL
                            + "-----------------------------------------------";
                   // BILL = BILL + "\r\n";
                    print_sn=1;
                    String itemName="";
                    for (SalesInvoiceItemClass si : getSalesInvoiceSelectedItem) {

                        if(si.getINVOICE_ITEM_ITEMNAME().length()>14)
                         itemName=si.getINVOICE_ITEM_ITEMNAME().substring(0,14);
                        else
                            itemName=si.getINVOICE_ITEM_ITEMNAME();
                        BILL = BILL + "\n\r " + String.format("%1$-3s %2$-14s %3$8s %4$7s %5$8s", ""+print_sn, itemName, ""+si.getINVOICE_ITEM_QTY(), ""+si.getINVOICE_ITEM_RATE(), ""+si.getSALES_NET());
//si.getINVOICE_ITEM_STOCK_UOM();
                        print_sn = print_sn + 1;
                        print_subtot = print_subtot + si.getSALES_NET();
                        print_qty = print_qty + (int) Math.round(si.getINVOICE_ITEM_QTY());
                        print_vat = print_vat + si.getSALES_VAT();
                        print_total=print_subtot+print_vat;
                    }
                    BILL = BILL + "\r\n";
                    BILL = BILL + "-----------------------------------------------";
                    BILL = BILL + "\r\n";
                    BILL = BILL + String.format("%1$3s %2$14s %3$8s %4$7s %5$8s", "", "", "", "SubTotal", ""+print_subtot);
                    BILL = BILL + "\r\n";
                    BILL = BILL + String.format("%1$3s %2$14s %3$8s %4$7s %5$8s", "", "", "", "    Vat", ""+print_vat);
                    BILL = BILL + "\r\n";
                  /*  BILL = BILL + String.format("%1$-10s %2$10s %3$13s %4$10s %5$10s", "", "", "", "Cartage Charged", "100");
                    BILL = BILL + "\r\n";*/

                    BILL = BILL
                            + "-----------------------------------------------";

                    BILL = BILL + "\r\n";
                    BILL = BILL + String.format("%1$-3s %2$14s %3$8s %4$15s", "", "Total", print_qty+"", "SAR."+print_total+"");

                  /*  BILL = BILL + "\n\r " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-002", "10", "5", "50.00");
                    BILL = BILL + "\n\r " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-003", "20", "10", "200.00");
                    BILL = BILL + "\n\r " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-004", "50", "10", "500.00");*/

                    BILL = BILL
                            + "\r\n-----------------------------------------------";
                    BILL = BILL + "\r\n ";
                    BILL = BILL + "\r\n";

                   /* BILL = BILL + "                   Total Qty:" + "      " + "85" + "\r\n";
                    BILL = BILL + "                   Total Value:" + "     " + "700.00" + "\r\n";*/

                    BILL = BILL + "                          Authorized Signatory\r\n";
                    BILL = BILL + "We declare that this invoice shows the actual price of the goods described and that all particulars are true and correct.\r\n";


                    BILL = BILL
                            + "-----------------------------------------------\r\n";
                    BILL = BILL + "\r\n\n ";
                    //byte b[]=BILL.getBytes();
                    os.write(BILL.getBytes());

                    //This is printer specific code you can comment ==== > Start

                    // Setting height
                    int gs = 29;
                    os.write(intToByteArray(gs));
                    int h = 104;
                    os.write(intToByteArray(h));
                    int n = 162;
                    os.write(intToByteArray(n));

                    // Setting Width
                    int gs_width = 29;
                    os.write(intToByteArray(gs_width));
                    int w = 119;
                    os.write(intToByteArray(w));
                    int n_width = 2;
                    os.write(intToByteArray(n_width));


                } catch (Exception e) {
                    Log.e("MainActivity", "Exe ", e);
                }
            }
        };
        t.start();


    }
    private void printCall() {
      //  Thread t = new Thread() {
        //    public void run() {
                try {
                    //SettingsClass getSettings = db.getPrinterSettings();
                    try
                    {
                    List<SettingsClass> getSettings = db.getSettings();
                    if (!getSettings.isEmpty()) {
                        if(Integer.valueOf(getSettings.get(0).getPrinter_type())>0) {
                            if (Integer.valueOf(getSettings.get(0).getPrinter_type())== 1) {
                                if(getSettings.get(0).getPrinter_mac_address().toString()!="")
                                printerConnection = new BluetoothConnection(getSettings.get(0).getPrinter_mac_address().toString());
                                else
                                {
                                    helper.showErrorDialogOnGuiThread("Invalid Printer");
                                    return;
                                }
                            }
                             else if (Integer.valueOf(getSettings.get(0).getPrinter_type()) == 2) {
                                try {
                                    printerConnection = new TcpConnection(getSettings.get(0).getPrinter_ip_address(), Integer.valueOf(getSettings.get(0).getPrinter_port_no()));
                                } catch (NumberFormatException e) {
                                    helper.showErrorDialogOnGuiThread("Port number/ip address is invalid");
                                    return;
                                }
                            }
                        }
                        else
                        {
                            helper.showErrorDialogOnGuiThread("No Printer settings");
                            return;
                        }
                    }
                    else
                    {
                        helper.showErrorDialogOnGuiThread("Invalid printer settings");
                        return;
                    }

                    }catch (Exception e) {
                        helper.showErrorDialogOnGuiThread(e.getMessage());
                        return;
                    }finally {
                        helper.dismissLoadingDialog();
                    }

                    try {
                        helper.showLoadingDialog("Connecting...");
                        printerConnection.open();

                        ZebraPrinter printer = null;

                        if (printerConnection.isConnected()) {
                            printer = ZebraPrinterFactory.getInstance(printerConnection);

                            if (printer != null) {
                                PrinterLanguage pl = printer.getPrinterControlLanguage();
                                if (pl == PrinterLanguage.CPCL) {
                                    helper.showErrorDialogOnGuiThread("Not work for CPCL printers!");
                                }
                                else
                                    {
                                        byte[] configLabel = createZplReceipt().getBytes();
                                        printerConnection.write(configLabel);
                                        Thread.sleep(1500);
                                        if (printerConnection instanceof BluetoothConnection) {
                                            Thread.sleep(500);
                                        }

                                }
                                printerConnection.close();

                            }
                        }
                    } catch (ConnectionException e) {
                        helper.showErrorDialogOnGuiThread(e.getMessage());
                    } catch (ZebraPrinterLanguageUnknownException e) {
                        helper.showErrorDialogOnGuiThread("Could not detect printer language");
                    } finally {
                        helper.dismissLoadingDialog();
                    }


                } catch (Exception e) {
                    helper.showErrorDialogOnGuiThread(e.getMessage());
                    Log.e("MainActivity", "Exe ", e);
                }
          //  }
        //};
        //t.start();


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
                        "^FO170,190" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+INVOICE_CUSTOMER_NAME+"^FS"+ "\r\n" +
                        "^FO10,230" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDVAT#^FS"+ "\r\n" +
                        "^FO150,230" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                        "^FO170,230" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+db.getCustomerVATNo(INVOICE_CUSTOMER_NAME)+"^FS"+ "\r\n" +
                        "^FO10,270" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDInvoice#^FS"+ "\r\n" +
                        "^FO150,270" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                        "^FO170,270" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+INVOICE_NO+"^FS"+ "\r\n" +
                        "^FO300,270" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDDate^FS"+ "\r\n" +
                        "^FO350,270" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                        "^FO360,270" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+INVOICE_DATE+"^FS"+ "\r\n" +
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
            body += String.format(lineItem, totalHeight, print_sn, totalHeight, itemName,totalHeight,si.getINVOICE_ITEM_QTY().toString(),totalHeight,Utility.roundToTwoDecimal(si.getINVOICE_ITEM_RATE().toString()),totalHeight,getRightString(Utility.roundToTwoDecimal(si.getSALES_NET().toString()),10));
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
private String getRightString(String inputString,int length)
{
    return String.format("%1$-" + length + "s", inputString);
}
    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }


    private void addmoreItemClick() {

        if(INVOICE_SYNC_STATUS.equals("0")) {

            if(!SELECTED_ASSET.equals("")){
                Intent item_add = new Intent(InvoiceDetailItemListActivity.this, InvoiceDetailMoreItemListAddActivity.class);
                item_add.putExtra("INVOICE_CUSTOMER_ID", INVOICE_CUSTOMER_ID);
                startActivityForResult(item_add, ITEM_ADD_REQUEST_CODE);
            }
            else{
                Utility.setToast(this,"Select Freezer# first").show();
                editText_search_asset.setFocusable(true);
            }
        }
        else
            Utility.setToast(this,"Already synchorised this invoice, you can't add more item").show();


    }

    @Override
    protected void onActivityResult(int mRequestCode, int mResultCode, Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        if (mResultCode == ITEM_ADD_REQUEST_CODE) {

           /* String message = data.getStringExtra("cstmr_name");
            selected_customer_id = data.getStringExtra("cstmr_id");
            CUSTOMER_TV.setText(message);*/
            setDataFromLocalDataBase();
        }
        else
        {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(mRequestCode, mResultCode, mDataIntent);
            Utility.setToast(this,mResultCode+"");
            if (scanResult != null) {

                String result = scanResult.getContents();
                Log.d(LOGTAG, "Have scan result in your app activity :" + result);

                Toast.makeText(this, "" + result, Toast.LENGTH_SHORT).show();
                if(db.IsValidCustomerAsset(INVOICE_CUSTOMER_NAME,result)) {
                    SELECTED_ASSET = result;
                    CURRENT_ASSET_TV.setText(getString(R.string.assetLabel) + SELECTED_ASSET);
                }else{

                    Toast.makeText(getApplicationContext(), "Invalid Freezer#", Toast.LENGTH_SHORT).show();

                }



            }

        }
/*
        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    // Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);
                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();
                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress dialog with thread
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    //  ListPairedDevices();
                    Intent connectIntent = new Intent(InvoiceDetailItemListActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(InvoiceDetailItemListActivity.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }*/


    }


    private void getItemClick() {

      /*  getSalesOrderItem = db.getSalesOrderItem(selected_customer_id);
        itemClasses.addAll(getSalesOrderItem);*/

        setData();


       /* Toast t = Utility.setToast(InvoiceItemListActivity.this, "GET Item Clicked");
        t.show();*/


    }


    private void addItemClick() {

        Intent item_add = new Intent(InvoiceDetailItemListActivity.this, InvoiceItemListAddActivity.class);
        startActivityForResult(item_add, ITEM_ADD_REQUEST_CODE);


    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.save_request:
                saveClick();
                break;
            case R.id.cancel_request:
                SELECTED_ASSET="";
                finish();
                break;
            case R.id.scan_btn:
                IntentIntegrator integrator = new IntentIntegrator(InvoiceDetailItemListActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan a barcode");
                integrator.initiateScan();
                break;

        }


    }


    private void saveClick() {

        Toast t = Utility.setToast(InvoiceDetailItemListActivity.this, "Save selected");
        t.show();


    }

    public static String getSelectedItemCode() {
        return selected_item_code;
    }

    public static String getSelectedPriceList() {
        return selected_price_display;
    }

    public static String getSelectedQuantity() {
        return selected_quantity;
    }

    public static String getSelectedDiscountPercentage() {
        return selected_discount_percentage;
    }

    public static String getSelectedRate() {
        return selected_rate;
    }

    public static String getSelectedId() {
        return selected_item_id;
    }

    public static String getSelectedUom() {
        return selected_uom;
    }

    public static void setCustomerName(String name) {
        CUSTOMER_NAME = name;
    }

    public static void setCustomerId(String id) {
        selected_customer_id = id;
    }

    public static void setReturnItems(List<SalesOrderItemClass> return_data) {
        itemClasses = new ArrayList<SalesOrderItemClass>();
        itemClasses.addAll(return_data);

    }

    @Override
    public void onBackPressed() {
        SELECTED_ASSET="";
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RECYCLER_VIEW.addOnItemTouchListener(onTouchListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RECYCLER_VIEW.removeOnItemTouchListener(onTouchListener);
    }

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (touchListener != null) touchListener.getTouchCoordinates(ev);
        return super.dispatchTouchEvent(ev);
    }*/

    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {
        this.touchListener = listener;
    }

    @Override
    public void applyTexts(SalesInvoiceItemClass selectedItemClass) {

        getSalesInvoiceSelectedItem.get(selected_position).setINVOICE_ITEM_QTY(selectedItemClass.getINVOICE_ITEM_QTY());
        getSalesInvoiceSelectedItem.get(selected_position).setINVOICE_ITEM_STOCK_UOM(selectedItemClass.getINVOICE_ITEM_STOCK_UOM());
        getSalesInvoiceSelectedItem.get(selected_position).setINVOICE_ITEM_PRICELIST_RATE(selectedItemClass.getINVOICE_ITEM_PRICELIST_RATE());
        getSalesInvoiceSelectedItem.get(selected_position).setINVOICE_ITEM_RATE(selectedItemClass.getINVOICE_ITEM_RATE());
        getSalesInvoiceSelectedItem.get(selected_position).setINVOICE_ITEM_DISCOUNT_PERCENTAGE(selectedItemClass.getINVOICE_ITEM_DISCOUNT_PERCENTAGE());
        getSalesInvoiceSelectedItem.get(selected_position).setSALES_GROSS(selectedItemClass.getSALES_GROSS());
        getSalesInvoiceSelectedItem.get(selected_position).setSALES_NET(selectedItemClass.getSALES_NET());
        getSalesInvoiceSelectedItem.get(selected_position).setSALES_VAT(selectedItemClass.getSALES_VAT());
        getSalesInvoiceSelectedItem.get(selected_position).setSALES_TOTAL(selectedItemClass.getSALES_TOTAL());

        viewAdapter.notifyDataSetChanged();

        gross = 0.0f;
        net = 0.0f;
        vat = 0.0f;
        tot = 0.0f;

        for (SalesInvoiceItemClass si : getSalesInvoiceSelectedItem) {

          /*  Log.i("CART", "<==" + si.getSALES_ITEM_NAME());
            Log.i("CART", "<==" + si.getSALES_STOCK_UOM());*/

            gross = gross + si.getSALES_GROSS();
            net = net + si.getSALES_NET();
            vat = vat + si.getSALES_VAT();
            tot = tot + si.getSALES_TOTAL();

        }


        TV_gross.setText(String.valueOf(Utility.round(gross, 2)));
        TV_net.setText(String.valueOf(Utility.round(net, 2)));
        TV_vat.setText(String.valueOf(Utility.round(vat, 2)));
        TV_total.setText(String.valueOf(Utility.round(tot, 2)));


        detailDialog.dismiss();

    }

    @Override
    public void dismisDialog() {
        detailDialog.dismiss();
    }

    @Override
    public void run() {

        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            // Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }


    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(InvoiceDetailItemListActivity.this, "DeviceConnected", Toast.LENGTH_SHORT).show();
        }
    };

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            // Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            // Log.d(TAG, "CouldNotCloseSocket");
        }
    }



/*
    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {




    }*/
}
