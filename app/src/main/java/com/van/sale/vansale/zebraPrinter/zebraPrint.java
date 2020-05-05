package com.van.sale.vansale.zebraPrinter;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.van.sale.vansale.Database.DatabaseHandler;
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

import java.util.List;

public class zebraPrint {
    private DatabaseHandler db;
    Connection printerConnection = null;

    public  void PrintToZebra(String label, Context context){

        UIHelper helper = new UIHelper((Activity)context);
        try {
            //SettingsClass getSettings = db.getPrinterSettings();
            try
            {

                db = new DatabaseHandler(context);
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
                            byte[] configLabel = label.getBytes();
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
            Log.e("ZebraPrint", "Exe ", e);
        }


    }
}
