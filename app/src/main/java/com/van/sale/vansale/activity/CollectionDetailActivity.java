package com.van.sale.vansale.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.van.sale.vansale.UIHelper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.van.sale.vansale.Bluetooth_Print.BluetoothPrinter;
import com.van.sale.vansale.Bluetooth_Print.UnicodeFormatter;
import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.DeviceListActivity;
import com.van.sale.vansale.HeaderFooterPageEvent;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.SalesInvoiceItemClass;
import com.van.sale.vansale.model.SettingsClass;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CollectionDetailActivity extends AppCompatActivity implements Runnable {

    Connection printerConnection = null;
    private UIHelper helper = new UIHelper(this);
    private String Collection_creation, Collection_doc_no, Collection_mode_pay, Collection_receive_amt, Collection_customer, Collection_reference_no,
            Collection_refer_date, Collection_paid_to;
    private TextView payment_no, creation_date, customer_tv, mode_payment_tv, paid_to, reference_no, reference_date, received_amount;
    private Button cancel_request;
    private String outpath;
    Uri path, pdfUri;

    /* https://infinum.co/the-capsized-eight/share-files-using-fileprovider   */

    /* ================ */

    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    String Email_id;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);


        Collection_creation = getIntent().getStringExtra(("Collection_creation"));
        Collection_doc_no = getIntent().getStringExtra(("Collection_doc_no"));
        Collection_mode_pay = getIntent().getStringExtra(("Collection_mode_pay"));
        Collection_receive_amt = getIntent().getStringExtra(("Collection_receive_amt"));
        Collection_customer = getIntent().getStringExtra(("Collection_customer"));
        Collection_reference_no = getIntent().getStringExtra(("Collection_reference_no"));
        Collection_refer_date = getIntent().getStringExtra(("Collection_refer_date"));
        Collection_paid_to = getIntent().getStringExtra(("Collection_paid_to"));

        db = new DatabaseHandler(this);
       // Email_id = db.getEmailId();
        Email_id = "nivinvincent14@gmail.com";

        payment_no = (TextView) findViewById(R.id.payment_no);
        creation_date = (TextView) findViewById(R.id.creation_date);
        customer_tv = (TextView) findViewById(R.id.customer_tv);
        mode_payment_tv = (TextView) findViewById(R.id.mode_payment_tv);
        paid_to = (TextView) findViewById(R.id.paid_to);
        reference_no = (TextView) findViewById(R.id.reference_no);
        reference_date = (TextView) findViewById(R.id.reference_date);
        received_amount = (TextView) findViewById(R.id.received_amount);

        cancel_request = (Button) findViewById(R.id.cancel_request);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbar_title = toolbar.findViewById(R.id.titleName);
        toolbar_title.setText("COLLECTION DETAILS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
       /* if (mBluetoothAdapter == null) {
            Toast.makeText(CollectionDetailActivity.this, "Message1", Toast.LENGTH_SHORT).show();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,
                        REQUEST_ENABLE_BT);
            } else {
                ListPairedDevices();
                Intent connectIntent = new Intent(CollectionDetailActivity.this,
                        DeviceListActivity.class);
                startActivityForResult(connectIntent,
                        REQUEST_CONNECT_DEVICE);
            }
        }
        */

        payment_no.setText(Collection_doc_no);
        creation_date.setText(Collection_creation);
        customer_tv.setText(Collection_customer);
        mode_payment_tv.setText(Collection_mode_pay);
        paid_to.setText(Collection_paid_to);
        reference_no.setText(Collection_reference_no);
        reference_date.setText(Collection_refer_date);
        received_amount.setText(Collection_receive_amt);


        cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.collection_detail_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //  int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_print:
                printCall();
                return true;
            case R.id.menu_share:
                shareCall();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareCall() {


        try {
            print1();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

    private void print1() throws DocumentException {


        //Document document = new Document(PageSize.A4, 20, 20, 50, 25);

        //  FileOutputStream fOut = openFileOutput("file name",Context.MODE_PRIVATE);


        Document document = new Document(PageSize.A4, 36, 36, 90, 36);
        outpath = Environment.getExternalStorageDirectory() + "/mypdf21.pdf";


        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        PdfWriter writer = PdfWriter.getInstance(document, bos);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);


        File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "mypdf21.pdf");
        path = Uri.fromFile(filelocation);


        // PdfPTable table = new PdfPTable(new float[]{2, 1, 2});
        PdfPTable table = new PdfPTable(3);
        table.getFooter().setRole(PdfName.A);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);


        table.addCell("Name");
        table.addCell("Class");
        table.addCell("Amount");
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();

        Log.i("nbnb", String.valueOf(cells.length));

        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }


       /* for (CashReceipt_model st : paidList) {
            table.addCell(st.getName_rcpt());
            table.addCell(st.getClassStud_rcpt());
            table.addCell(st.getTotalfee_rcpt());
        }
*/

        try {
            PdfWriter.getInstance(document, new FileOutputStream(outpath));
            //  PdfWriter.getInstance(document, openFileOutput("vanpdf.pdf",Context.MODE_PRIVATE));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // String klm="Hai this is test";
        document.open();
        document.add(new Paragraph("Report"));
        document.add(new Paragraph("             "));
        document.add(new Paragraph("             "));
        document.add(table);
        document.add(new Paragraph("Total Amount"));
        document.close();
        //System.out.println("Done");

        shareDocument();

    }


    private void shareDocument() {

        //  Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        if (!Email_id.isEmpty()) {

            Intent shareIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", Email_id, null));

            try

            {
                pdfUri = Uri.parse(outpath);
            } catch (Exception e) {

            }
            //  shareIntent.setType("message/rfc822");
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "VANSALE ");
            // String shareMessage = getResources().getString(R.string.label_share_message);
            String shareMessage = "TEST";
            // shareIntent.putExtra(Intent.EXTRA_EMAIL, "nivinvincent14@gmail.com");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
            // shareIntent.putExtra(Intent.EXTRA_STREAM, path);
            shareIntent.putExtra(Intent.EXTRA_STREAM, path);
            // startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.label_chooser_title)));
            startActivity(Intent.createChooser(shareIntent, "Send mail..."));
        } else {
            Utility.showDialog(CollectionDetailActivity.this, "ERROR !", "No Email Id Found !", R.color.dialog_error_background);

        }

    }


    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
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
                    String BILL = "                   Receipt"+ "\r\n";
                    BILL = BILL + "-----------------------------------------------\r\n";
                    BILL = BILL +"Recept#     :"+Collection_doc_no + "          Date:"+Collection_creation +"\r\n";
                    BILL = BILL +"Company     :"+ db.getCompanyName()+"\r\n";
                    BILL = BILL +"Customer    :"+Collection_customer+"\r\n";
                    BILL = BILL +"Payment Type:"+Collection_mode_pay  +"\r\n";
                    BILL = BILL + "-----------------------------------------------\r\n";
                    BILL = BILL + String.format("%1$-15s %2$15s %3$10s ", "Received Amount", "Ref#", "RefDate");
                    BILL = BILL + "\r\n";
                    BILL = BILL + "-----------------------------------------------\r\n";
                    BILL = BILL + String.format("%1$-15s %2$15s %3$10s ","    "+ Collection_receive_amt, Collection_reference_no +"", Collection_refer_date +"");
                    BILL = BILL + "\r\n";
                    BILL = BILL + "-----------------------------------------------\r\n";
                    BILL = BILL + "\r\n";
                    BILL = BILL + "\r\n";
                    BILL = BILL + "                          Authorized Signatory";
                    BILL = BILL + "\r\n";
                    BILL = BILL + "\r\n";
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
    }
    private String createZplReceipt() {
        String tmpHeader =
                "^XA" +
                        "^POI^PW600^MNN^LL%d^LH0,0"+
                        "^FO20,50" + "\r\n" +"^A0,N,35,40"+ "\r\n" +"^FD "+db.getCompanyName()+"^FS"+ "\r\n" +
                        "^FO220,110" + "\r\n" +"^A0,N,30,40"+ "\r\n" +"^FDReceipt^FS"+ "\r\n" +
                        "^FO180,150" + "\r\n" +"^GB200,1,1,B,0^FS"+ "\r\n" +
                        "^FO10,190" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDCustomer^FS"+ "\r\n" +
                        "^FO150,190" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                        "^FO170,190" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+Collection_customer+"^FS"+ "\r\n" +
                        "^FO10,230" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDRecept# ^FS"+ "\r\n" +
                        "^FO150,230" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                        "^FO170,230" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+Collection_doc_no+"^FS"+ "\r\n" +
                        "^FO350,230" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDDate^FS"+ "\r\n" +
                        "^FO400,230" + "\r\n" +"^A0,N,25,40"+ "\r\n" +"^FD:^FS"+ "\r\n" +
                        "^FO420,230" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD"+Collection_creation+"^FS"+ "\r\n" +
                        "^FO10,270" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" +
                        "^FO10,280" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDReceived Amount^FS"+ "\r\n" +
                        "^FO300,280" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDRef.No^FS"+ "\r\n" +
                        "^FO400,280" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FDRef.Date^FS"+ "\r\n" +
                        "^FO10,310" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" +
                        "^FO30,340" + "\r\n" +"^A0,N,30,40"+ "\r\n" +"^FD"+Utility.roundToTwoDecimal(Collection_receive_amt)+"^FS"+ "\r\n" +
                        "^FO300,340" + "\r\n" +"^A0,N,30,40"+ "\r\n" +"^FD"+Collection_reference_no+"^FS"+ "\r\n" +
                        "^FO400,340" + "\r\n" +"^A0,N,30,40"+ "\r\n" +"^FD"+Collection_refer_date+"^FS"+ "\r\n" +
                        "^FO10,410" + "\r\n" +"^GB550,1,1,B,0^FS"+ "\r\n" +
                        "^FO350,520" + "\r\n" +"^A0,N,25,50"+ "\r\n" +"^FDAuthorized Signatory^FS"+ "\r\n" +
                        "^FO10,620" + "\r\n" +"^A0,N,22,40"+ "\r\n" +"^FD ^FS"+ "\r\n" +
                        "^XZ";
        String label = String.format(tmpHeader, 680);
        return label;
    }
    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }


    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
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
                    ListPairedDevices();
                    Intent connectIntent = new Intent(CollectionDetailActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(CollectionDetailActivity.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }



    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }

    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(CollectionDetailActivity.this, "DeviceConnected", Toast.LENGTH_SHORT).show();
        }
    };

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

}
