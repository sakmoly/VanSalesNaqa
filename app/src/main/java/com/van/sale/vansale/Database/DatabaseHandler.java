package com.van.sale.vansale.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.van.sale.vansale.Report_model.Daily_Sales_Collection_Report;
import com.van.sale.vansale.Report_model.Item_Return_Details;
import com.van.sale.vansale.Report_model.OutStanding_Invoice_Details;
import com.van.sale.vansale.Report_model.Outstanding_Collection_Details;
import com.van.sale.vansale.Retrofit_Model.CustomerVisitRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.ItemDetailUom;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw1_ItemData;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw1_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesOrderRawItemData1;
import com.van.sale.vansale.Retrofit_Model.SalesOrderRaw_TokenResponse1;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.AddressClass;
import com.van.sale.vansale.model.AddressPost;
import com.van.sale.vansale.model.CusomerAssetList;
import com.van.sale.vansale.model.CustomerClass;
import com.van.sale.vansale.model.CustomerPost;
import com.van.sale.vansale.model.CustomerVisitLog;
import com.van.sale.vansale.model.ItemClass;
import com.van.sale.vansale.model.ItemDetailClass;
import com.van.sale.vansale.model.Mode_Of_Payment;
import com.van.sale.vansale.model.Password;
import com.van.sale.vansale.model.Payment;
import com.van.sale.vansale.model.SalesInvoiceClass;
import com.van.sale.vansale.model.SalesInvoiceItemClass;
import com.van.sale.vansale.model.SalesInvoiceModeOfPayment;
import com.van.sale.vansale.model.SalesInvoiceRaw1_ItemPayments;
import com.van.sale.vansale.model.SalesOrderClass;
import com.van.sale.vansale.model.SalesOrderItemClass;
import com.van.sale.vansale.model.SelectedItemClass;
import com.van.sale.vansale.model.SettingsClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maaz on 24/09/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "vansaleDatabase";
    String CREATE_CUSTOMER_ASSET_LIST_TABLE ="";
    String CREATE_CUSTOMER_VISIT_LOG_TABLE ="";

    private static final String TABLE_SETTINGS = "vansale_settings";
    private static final String TABLE_USER = "vansale_user";
    private static final String TABLE_CUSTOMER = "vansale_customer";
    private static final String TABLE_ADDRESS = "vansale_address";
    private static final String TABLE_PASSWORD = "vansale_password";
    private static final String TABLE_ITEM = "vansale_item";
    private static final String TABLE_ITEM_DETAIL = "vansale_item_detail";
    private static final String TABLE_SALES_ORDER = "vansale_sales_order";
    private static final String TABLE_SALES_ORDER_ITEM = "vansale_sales_order_item";
    private static final String TABLE_SALES_INVOICE = "vansale_sales_invoice";
    private static final String TABLE_SALES_INVOICE_ITEM = "vansale_sales_invoice_item";
    private static final String TABLE_MODE_OF_PAYMENT = "vansale_mode_of_payment";
    private static final String TABLE_PAYMENT = "vansale_payment";
    private static final String TABLE_SALES_INVOICE_PAYMENT = "vansale_sales_invoice_payment";
   // private static final String TABLE_PRINTER_SETTINGS = "vansale_printer_settings";
    private static final String TABLE_CUSTOMER_ASSET_LIST = "vansale_customer_asset_list";
    private static final String TABLE_CUSTOMER_VISIT_LOG = "vansale_customer_visit_log";

    /* ================== SETTINGS ======================== */

    private static final String NAME = "name";

    private static final String KEY_ID = "id";
    private static final String KEY_NAMING_SERIES_SALES_ORDER = "naming_series_sales_order";
    private static final String KEY_NAMING_SERIES_SALES_INVOICE = "naming_series_sales_invoice";
    private static final String KEY_NAMING_SERIES_PAYMENT = "naming_series_payment";
    private static final String KEY_NAMING_SERIES_TRANSFER = "naming_series_transfer";
    private static final String LAST_DOC_NO_SALES_ORDER = "last_doc_no_sales_order";
    private static final String LAST_DOC_NO_SALES_INVOICE = "last_doc_no_sales_invoice";
    private static final String LAST_DOC_NO_PAYMENT = "last_doc_no_payment";
    private static final String LAST_DOC_NO_TRANSFER = "last_doc_no_transfer";
   // private static final String LAST_DOC_NO_CUSTOMER_VISIT = "last_doc_no_customer_visit";
    private static final String DEFAULT_CURRENCY = "default_currency";
    private static final String DEFAULT_CREDIT_LIMIT_FOR_NEW_CUSTOMER = "default_credit_limit_for_new_customer";
    private static final String TAX_RATE = "tax_rate";
    private static final String DEVICE_ID = "device_id";
    private static final String WARE_HOUSE = "ware_house";
    private static final String CUSTOMER_ACCESS = "customer_access";
    private static final String SALES_ORDER_ACCESS = "sales_order_access";
    private static final String SALES_INVOICE_ACCESS = "sales_invoice_access";
    private static final String PAYMENT_ACCESS = "payment_access";
    private static final String TRANSFER_ACCESS = "transfer_access";
    private static final String SETTING_ACCESS_PASSWORD = "setting_access_password";
    private static final String SETTING_URL = "setting_url";
    private static final String API_USERNAME = "api_username";
    private static final String API_PASSWORD = "api_password";
    private static final String COMPANY_NAME = "company_name";
    private static final String TAX_ACCOUNT_HEAD = "tax_account_head";
    private static final String SETTINGS_PAID_FROM = "settings_paid_from";
    private static final String SETTINGS_PAID_TO = "settings_paid_to";
    private static final String SETTINGS_DEFAULT_PAYMENT_MODE = "settings_default_payment_mode";
    //PRINTER SETTINGS
    private static final String PRINTER_TYPE = "printer_type";//1 bluetooth and 2 Network
    private static final String PRINTER_IP_ADDRESS = "printer_ip";
    private static final String PRINTER_PORT_NO = "printer_port_no";
    private static final String PRINTER_MAC_ADDRESS = "printer_mac_address";

    private static final String KEY_NAMING_SERIES_CUSTOMER_VISIT = "naming_series_customer_visit";
    private static final String AUTO_SYNC_INTERVAL = "auto_sync_interval";
    private static final String TRANS_DEFAULT_QTY="trans_default_qty";
    private static final String LAST_SYNC_TIME="last_sync_time";
    /* ================== CUSTOMER ============================ */

    private static final String CUSTOMER_UNIQUE_NAME = "customer_unique_name";
    private static final String SERVER_CUSTOMER_ID = "server_customer_id";
    private static final String CUSTOMER_NAME = "customer_name";
    private static final String EMAIL_ID = "email_id";
    private static final String MOBILE_NO = "mobile_no";
    private static final String TAX_ID = "tax_id";
    private static final String CUSTOMER_PRIMARY_CONTACT = "customer_primary_contact";
    private static final String CREDIT_LIMIT = "credit_limit";
    private static final String IS_NEW = "is_new";
    private static final String SYNC_STATUS = "sync_status";


    /* ================== ADDRESS ============================= */

    private static final String ADDRESS_TITLE = "address_title";
    private static final String ADDRESS_LINE1 = "address_line1";
    private static final String ADDRESS_LINE2 = "address_line2";
    private static final String COMPANY = "company";
    private static final String CITY = "city";

    /* ====================== USER ================================ */

    private static final String USER_FIRST_NAME = "user_first_name";
    private static final String USER_LAST_NAME = "user_last_name";
    private static final String USER_FULL_NAME = "user_full_name";
    private static final String USER_USERNAME = "user_username";
    private static final String USER_PASSWORD_FOR_MOB_APPS = "user_password_for_mob_";

    /* ==================== CURRENCY ========================= */

    private static final String FRACTION_UNITS = "fraction_units";
    private static final String SYMBOL = "symbol";
    private static final String CURRENCY_NAME = "currency_name";
    private static final String FRACTION = "fraction";
    private static final String NUMBER_FORMAT = "number_format";
    private static final String SMALLEST_CURRENCY_FRACTION_VALUE = "smallest_currency_fraction_value";

    /* ======================== ITEM ================= */

    private static final String ITEM_CODE = "item_code";
    private static final String ITEM_NAME = "item_name";
    private static final String DESCRIPTION = "description";
    private static final String ITEM_GROUP = "item_group";
    private static final String BRAND = "brand";
    private static final String BAR_CODE = "bar_code";
    private static final String STOCK_UOM = "stock_uom";


    /* ================== ITEM DETAIL ====================== */

    private static final String ITEM_PARENT = "item_parent";
    private static final String CONVERSION_FACTOR = "conversion_factor";
    private static final String UOM = "uom";
    private static final String ALU2 = "alu2";
    private static final String PRICE = "price";

    /* ================== SALES ORDER ======================== */

    private static final String SALES_ORDER_CREATION = "sales_order_creation";
    private static final String SALES_ORDER_OWNER = "sales_order_owner";
    private static final String SALES_ORDER_DOC_STATUS = "sales_order_doc_status";
    private static final String SALES_ORDER_BILLING_STATUS = "sales_order_billing_status";
    private static final String SALES_ORDER_PO_NO = "sales_order_po_no";
    private static final String SALES_ORDER_CUSTOMER = "sales_order_customer";
    private static final String SALES_ORDER_ORDER_TYPE = "sales_order_order_type";
    private static final String SALES_ORDER_STATUS = "sales_order_status";
    private static final String SALES_ORDER_COMPANY = "sales_order_company";
    private static final String SALES_ORDER_NAMING_SERIES = "sales_order_naming_series";
    private static final String SALES_ORDER_DOC_NO = "sales_order_doc_no";
    private static final String SALES_ORDER_DELIVERY_DATE = "sales_order_delivery_date";
    private static final String SALES_ORDER_CURRENCY = "sales_order_currency";
    private static final String SALES_ORDER_CONVERSION_RATE = "sales_order_conversion_rate";
    private static final String SALES_ORDER_PRICE_LIST = "sales_order_price_list";
    private static final String SALES_ORDER_PLC_CONVERSION_RATE = "sales_order_plc_conversion_rate";
    private static final String SALES_ORDER_DEVICE_ID = "sales_order_device_id";
    private static final String SALES_ORDER_LAST_ID = "sales_order_device_id";
    private static final String SALES_ORDER_LATITUDE = "sales_order_latitude";
    private static final String SALES_ORDER_LONGITUDE = "sales_order_longitude";


    /* ======================= SALES ORDER ITEMS ================ */

    private static final String SALES_QTY = "sales_qty";
    private static final String SALES_WAREHOUSE = "sales_warehouse";
    private static final String SALES_ITEM_NAME = "sales_item_name";
    private static final String SALES_RATE = "sales_rate";
    private static final String SALES_STOCK_UOM = "sales_stock_uom";
    private static final String SALES_ITEM_CODE = "sales_item_code";
    private static final String SALES_PRICE_LIST_RATE = "sales_price_list_rate";
    private static final String SALES_DISCOUNT_PERCENTAGE = "sales_discount_percentage";
    private static final String SALES_TAX_RATE = "sales_tax_rate";
    private static final String SALES_TAX_AMOUNT = "sales_tax_amount";
    private static final String SALES_GROSS = "sales_gross";
    private static final String SALES_NET = "sales_net";
    private static final String SALES_VAT = "sales_vat";
    private static final String SALES_TOTAL = "sales_total";
    private static final String SALES_DELIVERY_STATUS = "sales_delivery_status";


    /* ======================= SALES INVOICE ========================== */

    private static final String SALES_INVOICE_CREATION = "sales_invoice_creation";
    private static final String SALES_INVOICE_OWNER = "sales_invoice_owner";
    private static final String SALES_INVOICE_DOC_STATUS = "sales_invoice_doc_status";
    private static final String SALES_INVOICE_SELLING_PRICE_LIST = "sales_invoice_selling_price_list";
    private static final String SALES_INVOICE_CUSTOMER = "sales_invoice_customer";
    private static final String SALES_INVOICE_COMPANY = "sales_invoice_company";
    private static final String SALES_INVOICE_NAMING_SERIES = "sales_invoice_naming_series";
    private static final String SALES_INVOICE_CURRENCY = "sales_invoice_currency";
    private static final String SALES_INVOICE_DOC_NO = "sales_invoice_doc_no";
    private static final String SALES_INVOICE_CONVERSION_RATE = "sales_invoice_conversion_rate";
    private static final String SALES_INVOICE_PLC_CONVERSION_RATE = "sales_invoice_plc_conversion_rate";
    private static final String SALES_INVOICE_RETURN_AGAINST = "sales_invoice_return_against";
    private static final String SALES_INVOICE_IS_RETURN = "sales_invoice_is_return";
    private static final String SALES_INVOICE_POSTING_TIME = "sales_invoice_posting_time";
    private static final String SALES_INVOICE_POSTING_DATE = "sales_invoice_posting_date";
    private static final String SALES_INVOICE_STATUS = "sales_invoice_status";
    private static final String SALES_INVOICE_DEVICE_ID = "sales_invoice_device_id";
    private static final String SALES_INVOICE_SYNC_STATUS = "sales_invoice_sync_status";
    private static final String SALES_INVOICE_IS_POS = "sales_invoice_is_pos";
    private static final String SALES_INVOICE_UPDATE_STOCK = "sales_invoice_update_stock";
    private static final String SALES_INVOICE_RETURN_AGAINST_INVOICE_NO = "sales_invoice_return_against_invoice_no";
    private static final String SALES_INVOICE_LATITUDE = "sales_invoice_latitude";
    private static final String SALES_INVOICE_LONGITUDE = "sales_invoice_longitude";
    private static final String TERRITORY = "territory";

    /* ======================= SALES INVOICE ITEM ====================== */

    private static final String SALES_INVOICE_ID = "sales_invoice_id";
    private static final String INVOICE_ITEM_QTY = "invoice_item_qty";
    private static final String INVOICE_ITEM_WAREHOUSE = "invoice_item_warehouse";
    private static final String INVOICE_ITEM_ITEMNAME = "invoice_item_itemname";
    private static final String INVOICE_ITEM_RATE = "invoice_item_rate";
    private static final String INVOICE_ITEM_STOCK_UOM = "invoice_item_stock_uom";
    private static final String INVOICE_ITEM_ITEMCODE = "invoice_item_itemcode";
    private static final String INVOICE_ITEM_PRICELIST_RATE = "invoice_item_pricelist_rate";
    private static final String INVOICE_ITEM_DISCOUNT_PERCENTAGE = "invoice_item_discount_percentage";
    private static final String INVOICE_ITEM_TAX_RATE = "invoice_item_tax_rate";
    private static final String INVOICE_ITEM_TAX_AMOUNT = "invoice_item_tax_amount";
    private static final String INVOICE_ITEM_SALES_ORDER = "invoice_item_sales_order";
    private static final String INVOICE_ITEM_SO_DETAIL = "invoice_item_so_detail";
    private static final String INVOICE_ITEM_ASSET = "invoice_item_asset";
    /* ======================= Mode Of Payment ========================== */

    private static final String PAYMENT_NAME = "payment_name";
    private static final String PAYMENT_MODE = "payment_mode";
    private static final String PAYMENT_TYPE = "payment_type";
    private static final String PAYMENT_CUSTOMER = "payment_customer";
    private static final String PAYMENT_PAID_TO = "payment_paid_to";

    /* ======================== Payment ================================== */


    // private static final String PAYMENT_NAME = "payment_name";

    private static final String PAYMENT_CREATION = "payment_creation";
    private static final String PAYMENT_DOC_NO = "payment_doc_no";
    private static final String MODE_OF_PAYMENT = "mode_of_payment";
    private static final String RECEIVED_AMOUNT = "received_amount";
    private static final String PAYMENT_SYNC_STATUS = "payment_sync_status";
    private static final String PAYMENT_REFERENCE_NO = "payment_reference_no";
    private static final String PAYMENT_REFERENCE_DATE = "payment_reference_date";
    private static final String PAYMENT_OWNER = "payment_owner";
    private static final String PAYMENT_POSTING_TIME = "payment_posting_time";
/* ========================== Sales Invoice Payment ========================= */

    private static final String SALES_INVOICE_PAYMENT_ACCOUNT = "sales_invoice_payment_account";
    private static final String SALES_INVOICE_PAYMENT_MODE_OF_PAYMENT = "sales_invoice_mode_of_payment";
    private static final String SALES_INVOICE_PAYMENT_BASE_AMOUNT = "sales_invoice_payment_base_amount";
    private static final String SALES_INVOICE_PAYMENT_AMOUNT = "sales_invoice_payment_amount";
    private static final String SALES_INVOICE_PAYMENT_TYPE = "sales_invoice_payment_type";


    /* ========================== Customer asset list ========================= */

    private static final String ASSET_CUSTOMER_NAME = "customer_name";
    private static final String ASSET_ASSET_ID = "asset_id";

    /* ========================== Customer visit log ========================= */
    private static final String CUSTOMER_VISIT_CUSTOMER = "customer_visit_customer";
    private static final String CUSTOMER_VISIT_REFERENCE = "customer_visit_reference";
    private static final String CUSTOMER_VISIT_NAMING_SERIES = "customer_visit_naming_series";
    private static final String CUSTOMER_VISIT_CREATION = "customer_visit_creation";
    private static final String CUSTOMER_VISIT_OWNER = "customer_visit_owner";
    private static final String CUSTOMER_VISIT_MODIFIED_BY = "customer_visit_modified_by";
    private static final String CUSTOMER_VISIT_DOC_STATUS = "customer_visit_doc_status";
    private static final String CUSTOMER_VISIT_LATITUDE = "customer_visit_latitude";
    private static final String CUSTOMER_VISIT_LONGITUDE = "customer_visit_longitude";
    private static final String CUSTOMER_VISIT_COMMENTS = "customer_visit_comments";
    private static final String CUSTOMER_VISIT_IDX = "customer_visit_idx";
    private static final String CUSTOMER_VISIT_AMOUNT = "customer_visit_amount";
    private static final String CUSTOMER_VISIT_VISIT_RESULT = "customer_visit_visit_result";
    private static final String CUSTOMER_VISIT_MODIFIED = "customer_visit_modified";
    private static final String CUSTOMER_VISIT_VISIT_DATE = "customer_visit_visit_date";
    private static final String CUSTOMER_VISIT_SALES_PERSON = "customer_visit_sales_person";
    private static final String CUSTOMER_VISIT_SYNC_STATUS = "customer_visit_sync_status";


    /* ======================= */


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        /*=================== SALES INVOICE MODE OF PAYME N============ */

    try {

        String CREATE_SALES_INVOICE_PAYMENT_TABLE = "CREATE TABLE " + TABLE_SALES_INVOICE_PAYMENT + "(" +

                KEY_ID + " INTEGER PRIMARY KEY," +
                SALES_INVOICE_ID + " INTEGER," +
                SALES_INVOICE_PAYMENT_ACCOUNT + " TEXT," +
                SALES_INVOICE_PAYMENT_MODE_OF_PAYMENT + " TEXT," +
                SALES_INVOICE_PAYMENT_BASE_AMOUNT + " TEXT," +
                SALES_INVOICE_PAYMENT_AMOUNT + " TEXT," +
                SALES_INVOICE_PAYMENT_TYPE + " TEXT" +

                ")";

        db.execSQL(CREATE_SALES_INVOICE_PAYMENT_TABLE);


        /* ================== PAYMENT ================================= */

        String CREATE_PAYMENT_TABLE = "CREATE TABLE " + TABLE_PAYMENT + "(" +

                KEY_ID + " INTEGER PRIMARY KEY," +
                PAYMENT_CREATION + " DATETIME," +
                PAYMENT_DOC_NO + " TEXT," +
                MODE_OF_PAYMENT + " TEXT," +
                RECEIVED_AMOUNT + " TEXT," +
                PAYMENT_CUSTOMER + " TEXT," +
                PAYMENT_SYNC_STATUS + " INTEGER," +
                PAYMENT_REFERENCE_NO + " TEXT," +
                PAYMENT_REFERENCE_DATE + " TEXT," +
                PAYMENT_PAID_TO + " TEXT," +
                PAYMENT_OWNER + " TEXT," +
                TERRITORY + " TEXT," +
                PAYMENT_POSTING_TIME + " TEXT" +
                ")";

        db.execSQL(CREATE_PAYMENT_TABLE);


        /* ================== MODE OF PAYMENT ========================= */

        String CREATE_MODE_OF_PAYMENT_TABLE = "CREATE TABLE " + TABLE_MODE_OF_PAYMENT + "(" +


                KEY_ID + " INTEGER PRIMARY KEY," +
                PAYMENT_NAME + " TEXT," +
                PAYMENT_MODE + " TEXT," +
                PAYMENT_TYPE + " TEXT," +
                PAYMENT_PAID_TO + " TEXT" +
                ")";


        db.execSQL(CREATE_MODE_OF_PAYMENT_TABLE);



        /* ================== SALES INVOICE ITEM ====================== */

        String CREATE_SALES_INVOICE_ITEM_TABLE = "CREATE TABLE " + TABLE_SALES_INVOICE_ITEM + "(" +


                KEY_ID + " INTEGER PRIMARY KEY," +
                SALES_INVOICE_ID + " INTEGER," +
                INVOICE_ITEM_QTY + " REAL," +
                INVOICE_ITEM_WAREHOUSE + " TEXT," +
                INVOICE_ITEM_ITEMNAME + " TEXT," +
                INVOICE_ITEM_RATE + " REAL," +
                INVOICE_ITEM_STOCK_UOM + " TEXT," +
                INVOICE_ITEM_ITEMCODE + " TEXT," +
                INVOICE_ITEM_PRICELIST_RATE + " REAL," +
                INVOICE_ITEM_DISCOUNT_PERCENTAGE + " REAL," +
                INVOICE_ITEM_TAX_RATE + " REAL," +
                INVOICE_ITEM_TAX_AMOUNT + " REAL," +
                INVOICE_ITEM_SALES_ORDER + " TEXT," +
                INVOICE_ITEM_SO_DETAIL + " TEXT," +
                SALES_GROSS + " REAL," +
                SALES_NET + " REAL," +
                SALES_VAT + " REAL," +
                SALES_TOTAL + " REAL," +
                INVOICE_ITEM_ASSET + " TEXT" +
                ")";


        db.execSQL(CREATE_SALES_INVOICE_ITEM_TABLE);

        /* ================== SALES INVOICE =========================== */

        String CREATE_SALES_INVOICE_TABLE = "CREATE TABLE " + TABLE_SALES_INVOICE + "(" +


                KEY_ID + " INTEGER PRIMARY KEY," +
                SALES_INVOICE_CREATION + " DATETIME," +
                SALES_INVOICE_OWNER + " TEXT," +
                SALES_INVOICE_DOC_STATUS + " INTEGER," +
                SALES_INVOICE_SELLING_PRICE_LIST + " TEXT," +
                SALES_INVOICE_CUSTOMER + " TEXT," +
                SALES_INVOICE_COMPANY + " TEXT," +
                SALES_INVOICE_NAMING_SERIES + " TEXT," +
                SALES_INVOICE_CURRENCY + " TEXT," +
                SALES_INVOICE_DOC_NO + " TEXT," +
                SALES_INVOICE_CONVERSION_RATE + " REAL," +
                SALES_INVOICE_PLC_CONVERSION_RATE + " REAL," +
                SALES_INVOICE_RETURN_AGAINST + " TEXT," +
                SALES_INVOICE_IS_RETURN + " INTEGER," +
                SALES_INVOICE_POSTING_TIME + " TEXT," +
                SALES_INVOICE_POSTING_DATE + " TEXT," +
                SALES_INVOICE_STATUS + " TEXT," +
                SALES_INVOICE_DEVICE_ID + " TEXT," +
                SALES_INVOICE_SYNC_STATUS + " TEXT," +
                SALES_INVOICE_IS_POS + " INTEGER," +
                SALES_INVOICE_UPDATE_STOCK + " INTEGER," +
                SALES_INVOICE_RETURN_AGAINST_INVOICE_NO + " TEXT," +
                TERRITORY + " TEXT" +
                //SALES_INVOICE_LATITUDE + " TEXT," +
                //SALES_INVOICE_LONGITUDE + " TEXT" +
                ")";


        db.execSQL(CREATE_SALES_INVOICE_TABLE);


        /* ================== SALES ORDER ITEMS ======================= */


        String CREATE_SALES_ORDER_ITEM_TABLE = "CREATE TABLE " + TABLE_SALES_ORDER_ITEM + "(" +

                KEY_ID + " INTEGER PRIMARY KEY," +
                SALES_ORDER_CUSTOMER + " TEXT," +
                SALES_QTY + " REAL," +
                SALES_WAREHOUSE + " TEXT," +
                SALES_ITEM_NAME + " TEXT," +
                SALES_RATE + " REAL," +
                SALES_STOCK_UOM + " TEXT," +
                SALES_ITEM_CODE + " TEXT," +
                SALES_PRICE_LIST_RATE + " REAL," +
                SALES_DISCOUNT_PERCENTAGE + " REAL," +
                SALES_TAX_RATE + " REAL," +
                SALES_TAX_AMOUNT + " REAL," +
                SALES_GROSS + " REAL," +
                SALES_NET + " REAL," +
                SALES_VAT + " REAL," +
                SALES_TOTAL + " REAL," +
                SYNC_STATUS + " INTEGER," +
                SALES_ORDER_LAST_ID + " INTEGER," +
                SALES_ORDER_DOC_NO + " TEXT," +
                SALES_DELIVERY_STATUS + " TEXT" +
                ")";


        db.execSQL(CREATE_SALES_ORDER_ITEM_TABLE);


        /* ================== SALES ORDER TABLE ======================= */


        String CREATE_SALES_ORDER_TABLE = "CREATE TABLE " + TABLE_SALES_ORDER + "(" +

                KEY_ID + " INTEGER PRIMARY KEY," +
                SALES_ORDER_CREATION + " TEXT," +
                SALES_ORDER_OWNER + " TEXT," +
                SALES_ORDER_DOC_STATUS + " INTEGER," +
                SALES_ORDER_BILLING_STATUS + " TEXT," +
                SALES_ORDER_PO_NO + " TEXT," +
                SALES_ORDER_CUSTOMER + " TEXT," +
                SALES_ORDER_ORDER_TYPE + " TEXT," +
                SALES_ORDER_STATUS + " TEXT," +
                SALES_ORDER_COMPANY + " TEXT," +
                SALES_ORDER_NAMING_SERIES + " TEXT," +
                SALES_ORDER_DOC_NO + " TEXT," +
                SALES_ORDER_DELIVERY_DATE + " TEXT," +
                SALES_ORDER_CURRENCY + " TEXT," +
                SALES_ORDER_CONVERSION_RATE + " REAL," +
                SALES_ORDER_PRICE_LIST + " TEXT," +
                SALES_ORDER_PLC_CONVERSION_RATE + " REAL," +
                SALES_ORDER_DEVICE_ID + " TEXT," +
                SYNC_STATUS + " INTEGER," +
                SALES_ORDER_LATITUDE + " TEXT," +
                SALES_ORDER_LONGITUDE + " TEXT" +
                ")";


        db.execSQL(CREATE_SALES_ORDER_TABLE);



        /* ================== ITEM DETAIL TABLE ======================= */

        String CREATE_ITEM_DETAIL_TABLE = "CREATE TABLE " + TABLE_ITEM_DETAIL + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                ITEM_PARENT + " TEXT," +
                CONVERSION_FACTOR + " TEXT," +
                UOM + " TEXT," +
                ALU2 + " TEXT," +
                PRICE + " TEXT" +
                ")";

        db.execSQL(CREATE_ITEM_DETAIL_TABLE);


        /* ================== ITEM TABLE ============================== */

        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                ITEM_CODE + " TEXT," +
                ITEM_NAME + " TEXT," +
                DESCRIPTION + " TEXT," +
                ITEM_GROUP + " TEXT," +
                BRAND + " TEXT," +
                BAR_CODE + " TEXT," +
                STOCK_UOM + " TEXT" +
                ")";

        db.execSQL(CREATE_ITEM_TABLE);


        /* ================== PASSWORD TABLE ========================== */


        String CREATE_PASSWORD_TABLE = "CREATE TABLE " + TABLE_PASSWORD + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                SETTING_ACCESS_PASSWORD + " TEXT" +
                ")";

        db.execSQL(CREATE_PASSWORD_TABLE);

        db.execSQL("INSERT INTO " + TABLE_PASSWORD + " (" + SETTING_ACCESS_PASSWORD + ") VALUES ('print@van');");

        /* ========================= USER TABLE ============================== */

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                USER_FIRST_NAME + " TEXT," +
                USER_LAST_NAME + " TEXT," +
                USER_FULL_NAME + " TEXT," +
                USER_USERNAME + " TEXT," +
                USER_PASSWORD_FOR_MOB_APPS + " TEXT" +
                ")";

        db.execSQL(CREATE_USER_TABLE);

        /* ====================== ADDRESS TABLE ======================= */

        String CREATE_ADDRESS_TABLE = "CREATE TABLE " + TABLE_ADDRESS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                CUSTOMER_UNIQUE_NAME + " TEXT," +
                CUSTOMER_NAME + " TEXT," +
                ADDRESS_TITLE + " TEXT," +
                ADDRESS_LINE1 + " TEXT," +
                ADDRESS_LINE2 + " TEXT," +
                COMPANY + " TEXT," +
                CITY + " TEXT," +
                SYNC_STATUS + " TEXT" +
                ")";

        db.execSQL(CREATE_ADDRESS_TABLE);


        /* ========================= CUSTOMER TABLE =========================== */

        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMER + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                CUSTOMER_UNIQUE_NAME + " TEXT," +
                CUSTOMER_NAME + " TEXT," +
                EMAIL_ID + " TEXT," +
                MOBILE_NO + " TEXT," +
                TAX_ID + " TEXT," +
                CUSTOMER_PRIMARY_CONTACT + " TEXT," +
                CREDIT_LIMIT + " REAL," +
                DEVICE_ID + " TEXT," +
                IS_NEW + " INTEGER," +
                SYNC_STATUS + " INTEGER," +
                SERVER_CUSTOMER_ID + " TEXT," +
                TERRITORY + " TEXT" +
                ")";

        db.execSQL(CREATE_CUSTOMER_TABLE);

        /* ========================= SETTINGS TABLE =========================== */

        String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAMING_SERIES_SALES_ORDER + " TEXT," +
                KEY_NAMING_SERIES_SALES_INVOICE + " TEXT," +
                KEY_NAMING_SERIES_PAYMENT + " TEXT," +
                KEY_NAMING_SERIES_TRANSFER + " TEXT," +
                LAST_DOC_NO_SALES_ORDER + " INTEGER," +
                LAST_DOC_NO_SALES_INVOICE + " INTEGER," +
                LAST_DOC_NO_PAYMENT + " INTEGER," +
                LAST_DOC_NO_TRANSFER + " INTEGER," +
                //LAST_DOC_NO_CUSTOMER_VISIT + " INTEGER," +
                DEFAULT_CURRENCY + " TEXT," +
                DEFAULT_CREDIT_LIMIT_FOR_NEW_CUSTOMER + " REAL," +
                TAX_RATE + " REAL," +
                DEVICE_ID + " TEXT," +
                WARE_HOUSE + " TEXT," +
                CUSTOMER_ACCESS + " INTEGER," +
                SALES_ORDER_ACCESS + " INTEGER," +
                SALES_INVOICE_ACCESS + " INTEGER," +
                PAYMENT_ACCESS + " INTEGER," +
                TRANSFER_ACCESS + " INTEGER," +
                SETTING_ACCESS_PASSWORD + " TEXT," +
                SETTING_URL + " TEXT," +
                API_USERNAME + " TEXT," +
                API_PASSWORD + " TEXT," +
                COMPANY_NAME + " TEXT," +
                TAX_ACCOUNT_HEAD + " TEXT," +
                SETTINGS_PAID_FROM + " TEXT," +
                SETTINGS_PAID_TO + " TEXT," +
                SETTINGS_DEFAULT_PAYMENT_MODE + " TEXT," +
                PRINTER_TYPE + " TEXT," +
                PRINTER_IP_ADDRESS + " TEXT," +
                PRINTER_PORT_NO + " TEXT," +
                PRINTER_MAC_ADDRESS + " TEXT," +
                KEY_NAMING_SERIES_CUSTOMER_VISIT + " TEXT," +
                AUTO_SYNC_INTERVAL + " INTEGER," +
                TRANS_DEFAULT_QTY + " REAL," +
                LAST_SYNC_TIME + " TEXT" +
                ")";

        db.execSQL(CREATE_SETTINGS_TABLE);


        /* ========================= PRINTER SETTINGS TABLE ===========================

        String CREATE_PRINTER_SETTINGS_TABLE = "CREATE TABLE " + TABLE_PRINTER_SETTINGS + "(" +
                PRINTER_TYPE + " TEXT," +
                PRINTER_IP_ADDRESS + " TEXT," +
                PRINTER_PORT_NO + " TEXT," +
                PRINTER_MAC_ADDRESS + " TEXT" +
                ")";

        db.execSQL(CREATE_PRINTER_SETTINGS_TABLE);*/

        /* ================== CUSTOMER_ASSET_LIST =========================== */

        CREATE_CUSTOMER_ASSET_LIST_TABLE = "CREATE TABLE " + TABLE_CUSTOMER_ASSET_LIST + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                ASSET_CUSTOMER_NAME + " TEXT," +
                ASSET_ASSET_ID + " TEXT" +
                ")";

        db.execSQL(CREATE_CUSTOMER_ASSET_LIST_TABLE);

        /* ================== SALES INVOICE ===========================

        CREATE_CUSTOMER_VISIT_LOG_TABLE = "CREATE TABLE " + TABLE_CUSTOMER_VISIT_LOG + "(" +

                KEY_ID + " INTEGER PRIMARY KEY," +
                CUSTOMER_VISIT_CUSTOMER + " TEXT," +
                CUSTOMER_VISIT_REFERENCE + " TEXT," +
                CUSTOMER_VISIT_CREATION + " TEXT," +
                CUSTOMER_VISIT_DOC_STATUS + " INTEGER," +
                CUSTOMER_VISIT_IDX + " INTEGER," +
                CUSTOMER_VISIT_AMOUNT + " REAL," +
                CUSTOMER_VISIT_COMMENTS + " TEXT," +
                CUSTOMER_VISIT_LATITUDE + " TEXT," +
                CUSTOMER_VISIT_LONGITUDE + " TEXT," +
                CUSTOMER_VISIT_MODIFIED + " TEXT," +
                CUSTOMER_VISIT_MODIFIED_BY + " TEXT," +
                CUSTOMER_VISIT_NAMING_SERIES + " TEXT," +
                CUSTOMER_VISIT_OWNER + " TEXT," +
                CUSTOMER_VISIT_SALES_PERSON + " TEXT," +
                CUSTOMER_VISIT_VISIT_DATE + " TEXT," +
                CUSTOMER_VISIT_VISIT_RESULT + " TEXT," +
                CUSTOMER_VISIT_SYNC_STATUS + " TEXT" +

                ")";


        db.execSQL(CREATE_CUSTOMER_VISIT_LOG_TABLE);*/
        CreateCustomerVisitTable(db);



    }catch (Exception e){
        throw e;
    }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int i1) {

        if(oldversion<2){
            db.execSQL("ALTER TABLE "+TABLE_SETTINGS+ " ADD COLUMN "+PRINTER_TYPE+" TEXT");
            db.execSQL("ALTER TABLE "+TABLE_SETTINGS+ " ADD COLUMN "+PRINTER_IP_ADDRESS+" TEXT");
            db.execSQL("ALTER TABLE "+TABLE_SETTINGS+ " ADD COLUMN "+PRINTER_PORT_NO+" TEXT");
            db.execSQL("ALTER TABLE "+TABLE_SETTINGS+ " ADD COLUMN "+PRINTER_MAC_ADDRESS+" TEXT");

        }
        if(oldversion<3){
            db.execSQL("ALTER TABLE "+TABLE_SALES_INVOICE+ " ADD COLUMN "+SALES_INVOICE_RETURN_AGAINST_INVOICE_NO+" TEXT");
           // db.execSQL("ALTER TABLE "+TABLE_SALES_INVOICE+ " ADD COLUMN "+SALES_INVOICE_LATITUDE+" TEXT");
            //db.execSQL("ALTER TABLE "+TABLE_SALES_INVOICE+ " ADD COLUMN "+SALES_INVOICE_LONGITUDE+" TEXT");
            db.execSQL("ALTER TABLE "+TABLE_SALES_INVOICE_ITEM+ " ADD COLUMN "+INVOICE_ITEM_ASSET+" TEXT");
            db.execSQL("ALTER TABLE "+TABLE_SETTINGS+ " ADD COLUMN "+KEY_NAMING_SERIES_CUSTOMER_VISIT+" TEXT");
            //db.execSQL("ALTER TABLE "+TABLE_SETTINGS+ " ADD COLUMN "+LAST_DOC_NO_CUSTOMER_VISIT+" INTEGER");
            CreateCustomerAssetListTable(db);
            CreateCustomerVisitTable(db);
            //db.execSQL(CREATE_CUSTOMER_ASSET_LIST_TABLE);
        }
        if(oldversion<4){
            db.execSQL("ALTER TABLE "+TABLE_SALES_INVOICE+ " ADD COLUMN "+TERRITORY+" TEXT");
            db.execSQL("ALTER TABLE "+TABLE_PAYMENT+ " ADD COLUMN "+PAYMENT_OWNER+" TEXT");
            db.execSQL("ALTER TABLE "+TABLE_PAYMENT+ " ADD COLUMN "+TERRITORY+" TEXT");
            db.execSQL("ALTER TABLE "+TABLE_CUSTOMER+ " ADD COLUMN "+TERRITORY+" TEXT");
        }
        if(oldversion<5){
            db.execSQL("ALTER TABLE "+TABLE_SETTINGS+ " ADD COLUMN "+AUTO_SYNC_INTERVAL+" INTEGER");
        }
        if(oldversion<6){
            db.execSQL("ALTER TABLE "+TABLE_SETTINGS+ " ADD COLUMN "+TRANS_DEFAULT_QTY+" REAL"+" DEFAULT 1");
            db.execSQL("ALTER TABLE "+TABLE_SETTINGS+ " ADD COLUMN "+LAST_SYNC_TIME+" TEXT");

        }
        if(oldversion<7){
            db.execSQL("ALTER TABLE "+TABLE_PAYMENT+ " ADD COLUMN "+PAYMENT_POSTING_TIME+" TEXT");
        }

    }
    private  void CreateCustomerAssetListTable(SQLiteDatabase db){

        try{
            CREATE_CUSTOMER_ASSET_LIST_TABLE = "CREATE TABLE " + TABLE_CUSTOMER_ASSET_LIST + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY," +
                    ASSET_CUSTOMER_NAME + " TEXT," +
                    ASSET_ASSET_ID + " TEXT" +
                    ")";

            db.execSQL(CREATE_CUSTOMER_ASSET_LIST_TABLE);
        }catch (Exception e){
            throw e;

        }
    }
    private  void CreateCustomerVisitTable(SQLiteDatabase db){

        try{
            CREATE_CUSTOMER_VISIT_LOG_TABLE = "CREATE TABLE " + TABLE_CUSTOMER_VISIT_LOG + "(" +

                    KEY_ID + " INTEGER PRIMARY KEY," +
                    CUSTOMER_VISIT_CUSTOMER + " TEXT," +
                    CUSTOMER_VISIT_REFERENCE + " TEXT," +
                    CUSTOMER_VISIT_CREATION + " TEXT," +
                    CUSTOMER_VISIT_DOC_STATUS + " INTEGER," +
                    CUSTOMER_VISIT_IDX + " INTEGER," +
                    CUSTOMER_VISIT_AMOUNT + " REAL," +
                    CUSTOMER_VISIT_COMMENTS + " TEXT," +
                    CUSTOMER_VISIT_LATITUDE + " TEXT," +
                    CUSTOMER_VISIT_LONGITUDE + " TEXT," +
                    CUSTOMER_VISIT_MODIFIED + " TEXT," +
                    CUSTOMER_VISIT_MODIFIED_BY + " TEXT," +
                    CUSTOMER_VISIT_NAMING_SERIES + " TEXT," +
                    CUSTOMER_VISIT_OWNER + " TEXT," +
                    CUSTOMER_VISIT_SALES_PERSON + " TEXT," +
                    CUSTOMER_VISIT_VISIT_DATE + " TEXT," +
                    CUSTOMER_VISIT_VISIT_RESULT + " TEXT," +
                    CUSTOMER_VISIT_SYNC_STATUS + " TEXT" +

                    ")";


            db.execSQL(CREATE_CUSTOMER_VISIT_LOG_TABLE);

        }catch (Exception e){
            throw e;
        }
    }
    public void addSalesInvoiceModePayment(SalesInvoiceModeOfPayment itemClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SALES_INVOICE_ID, itemClass.getSALES_INVOICE_ID());
        values.put(SALES_INVOICE_PAYMENT_ACCOUNT, itemClass.getSALES_INVOICE_PAYMENT_ACCOUNT());
        values.put(SALES_INVOICE_PAYMENT_MODE_OF_PAYMENT, itemClass.getSALES_INVOICE_PAYMENT_MODE_OF_PAYMENT());
        values.put(SALES_INVOICE_PAYMENT_BASE_AMOUNT, itemClass.getSALES_INVOICE_PAYMENT_BASE_AMOUNT());
        values.put(SALES_INVOICE_PAYMENT_AMOUNT, itemClass.getSALES_INVOICE_PAYMENT_AMOUNT());
        values.put(SALES_INVOICE_PAYMENT_TYPE, itemClass.getSALES_INVOICE_PAYMENT_TYPE());

        db.insert(TABLE_SALES_INVOICE_PAYMENT, null, values);
        db.close();

    }




    public void addPayment(Payment itemClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PAYMENT_CREATION, itemClass.getPAYMENT_CREATION());
        values.put(PAYMENT_DOC_NO, itemClass.getPAYMENT_DOC_NO());
        values.put(MODE_OF_PAYMENT, itemClass.getMODE_OF_PAYMENT());
        values.put(RECEIVED_AMOUNT, itemClass.getRECEIVED_AMOUNT());
        values.put(PAYMENT_CUSTOMER, itemClass.getPAYMENT_CUSTOMER());
        values.put(PAYMENT_SYNC_STATUS, itemClass.getPAYMENT_SYNC_STATUS());
        values.put(PAYMENT_REFERENCE_NO, itemClass.getPAYMENT_REFERENCE_NO());
        values.put(PAYMENT_REFERENCE_DATE, itemClass.getPAYMENT_REFERENCE_DATE());
        values.put(PAYMENT_PAID_TO, itemClass.getPAYMENT_PAID_TO());
        values.put(PAYMENT_OWNER, itemClass.getPAYMENT_OWNER());
        values.put(PAYMENT_POSTING_TIME, itemClass.getPAYMENT_POSTING_TIME());
        db.insert(TABLE_PAYMENT, null, values);
        db.close();

    }


    public void addModeOfPayment(Mode_Of_Payment itemClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PAYMENT_NAME, itemClass.getPAYMENT_NAME());
        values.put(PAYMENT_MODE, itemClass.getPAYMENT_MODE());
        values.put(PAYMENT_TYPE, itemClass.getPAYMENT_TYPE());
        values.put(PAYMENT_PAID_TO, itemClass.getPAYMENT_PAID_TO());

        db.insert(TABLE_MODE_OF_PAYMENT, null, values);
        db.close();


    }

    public void addSalesInvoiceItems(SalesInvoiceItemClass itemClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SALES_INVOICE_ID, itemClass.getSALES_INVOICE_ID());
        values.put(INVOICE_ITEM_QTY, itemClass.getINVOICE_ITEM_QTY());
        values.put(INVOICE_ITEM_WAREHOUSE, itemClass.getINVOICE_ITEM_WAREHOUSE());
        values.put(INVOICE_ITEM_ITEMNAME, itemClass.getINVOICE_ITEM_ITEMNAME());
        values.put(INVOICE_ITEM_RATE, itemClass.getINVOICE_ITEM_RATE());
        values.put(INVOICE_ITEM_STOCK_UOM, itemClass.getINVOICE_ITEM_STOCK_UOM());
        values.put(INVOICE_ITEM_ITEMCODE, itemClass.getINVOICE_ITEM_ITEMCODE());
        values.put(INVOICE_ITEM_PRICELIST_RATE, itemClass.getINVOICE_ITEM_PRICELIST_RATE());
        values.put(INVOICE_ITEM_DISCOUNT_PERCENTAGE, itemClass.getINVOICE_ITEM_DISCOUNT_PERCENTAGE());
        values.put(INVOICE_ITEM_TAX_RATE, itemClass.getINVOICE_ITEM_TAX_RATE());
        values.put(INVOICE_ITEM_TAX_AMOUNT, itemClass.getINVOICE_ITEM_TAX_AMOUNT());
        values.put(INVOICE_ITEM_SALES_ORDER, itemClass.getINVOICE_ITEM_SALES_ORDER());
        values.put(INVOICE_ITEM_SO_DETAIL, itemClass.getINVOICE_ITEM_SO_DETAIL());
        values.put(SALES_GROSS, itemClass.getSALES_GROSS());
        values.put(SALES_NET, itemClass.getSALES_NET());
        values.put(SALES_VAT, itemClass.getSALES_VAT());
        values.put(SALES_TOTAL, itemClass.getSALES_TOTAL());
        values.put(INVOICE_ITEM_ASSET, itemClass.getINVOICE_ITEM_ASSET());
        db.insert(TABLE_SALES_INVOICE_ITEM, null, values);
        db.close();


    }


    public void addSalesInvoice(SalesInvoiceClass invoiceClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(SALES_INVOICE_CREATION, invoiceClass.getSALES_INVOICE_CREATION());
        values.put(SALES_INVOICE_OWNER, invoiceClass.getSALES_INVOICE_OWNER());
        values.put(SALES_INVOICE_DOC_STATUS, invoiceClass.getSALES_INVOICE_DOC_STATUS());
        values.put(SALES_INVOICE_SELLING_PRICE_LIST, invoiceClass.getSALES_INVOICE_SELLING_PRICE_LIST());
        values.put(SALES_INVOICE_CUSTOMER, invoiceClass.getSALES_INVOICE_CUSTOMER());
        values.put(SALES_INVOICE_COMPANY, invoiceClass.getSALES_INVOICE_COMPANY());
        values.put(SALES_INVOICE_NAMING_SERIES, invoiceClass.getSALES_INVOICE_NAMING_SERIES());
        values.put(SALES_INVOICE_CURRENCY, invoiceClass.getSALES_INVOICE_CURRENCY());
        values.put(SALES_INVOICE_DOC_NO, invoiceClass.getSALES_INVOICE_DOC_NO());
        values.put(SALES_INVOICE_CONVERSION_RATE, invoiceClass.getSALES_INVOICE_CONVERSION_RATE());
        values.put(SALES_INVOICE_PLC_CONVERSION_RATE, invoiceClass.getSALES_INVOICE_PLC_CONVERSION_RATE());
        values.put(SALES_INVOICE_RETURN_AGAINST, invoiceClass.getSALES_INVOICE_RETURN_AGAINST());
        values.put(SALES_INVOICE_IS_RETURN, invoiceClass.getSALES_INVOICE_IS_RETURN());
        values.put(SALES_INVOICE_POSTING_TIME, invoiceClass.getSALES_INVOICE_POSTING_TIME());
        values.put(SALES_INVOICE_POSTING_DATE, invoiceClass.getSALES_INVOICE_POSTING_DATE());
        values.put(SALES_INVOICE_STATUS, invoiceClass.getSALES_INVOICE_STATUS());
        values.put(SALES_INVOICE_DEVICE_ID, invoiceClass.getSALES_INVOICE_DEVICE_ID());
        values.put(SALES_INVOICE_SYNC_STATUS, invoiceClass.getSALES_INVOICE_SYNC_STATUS());
        values.put(SALES_INVOICE_IS_POS, invoiceClass.getSALES_INVOICE_IS_POS());
        values.put(SALES_INVOICE_UPDATE_STOCK, invoiceClass.getSALES_INVOICE_UPDATE_STOCK());


        db.insert(TABLE_SALES_INVOICE, null, values);
        db.close();


    }

    public void addCustomerVisitLog(CustomerVisitLog itemClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CUSTOMER_VISIT_CUSTOMER , itemClass.getCUSTOMER_VISIT_CUSTOMER());
        values.put(CUSTOMER_VISIT_REFERENCE , itemClass.getCUSTOMER_VISIT_REFERENCE());
        values.put(CUSTOMER_VISIT_NAMING_SERIES , itemClass.getCUSTOMER_VISIT_NAMING_SERIES());
        values.put(CUSTOMER_VISIT_CREATION , itemClass.getCUSTOMER_VISIT_CREATION());
        values.put(CUSTOMER_VISIT_OWNER , itemClass.getCUSTOMER_VISIT_OWNER());
        values.put(CUSTOMER_VISIT_MODIFIED_BY , itemClass.getCUSTOMER_VISIT_MODIFIED_BY());
        values.put(CUSTOMER_VISIT_DOC_STATUS , itemClass.getCUSTOMER_VISIT_DOC_STATUS());
        values.put(CUSTOMER_VISIT_LATITUDE , itemClass.getCUSTOMER_VISIT_LATITUDE());
        values.put(CUSTOMER_VISIT_LONGITUDE , itemClass.getCUSTOMER_VISIT_LONGITUDE());
        values.put(CUSTOMER_VISIT_COMMENTS , itemClass.getCUSTOMER_VISIT_COMMENTS());
        values.put(CUSTOMER_VISIT_AMOUNT , itemClass.getCUSTOMER_VISIT_AMOUNT());
        values.put(CUSTOMER_VISIT_VISIT_RESULT , itemClass.getCUSTOMER_VISIT_VISIT_RESULT());
        values.put(CUSTOMER_VISIT_MODIFIED , itemClass.getCUSTOMER_VISIT_MODIFIED());
        values.put(CUSTOMER_VISIT_VISIT_DATE , itemClass.getCUSTOMER_VISIT_VISIT_DATE());
        values.put(CUSTOMER_VISIT_SALES_PERSON , itemClass.getCUSTOMER_VISIT_SALES_PERSON());
        values.put(CUSTOMER_VISIT_SYNC_STATUS , itemClass.getCUSTOMER_VISIT_SYNC_STATUS());

        db.insert(TABLE_CUSTOMER_VISIT_LOG, null, values);
        db.close();


    }

    public void addSalesOrderItem(SalesOrderItemClass orderItemClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SALES_ORDER_CUSTOMER, orderItemClass.getSALES_ORDER_CUSTOMER());
        values.put(SALES_QTY, orderItemClass.getSALES_QTY());
        values.put(SALES_WAREHOUSE, orderItemClass.getSALES_WAREHOUSE());
        values.put(SALES_ITEM_NAME, orderItemClass.getSALES_ITEM_NAME());
        values.put(SALES_RATE, orderItemClass.getSALES_RATE());
        values.put(SALES_STOCK_UOM, orderItemClass.getSALES_STOCK_UOM());
        values.put(SALES_ITEM_CODE, orderItemClass.getSALES_ITEM_CODE());
        values.put(SALES_PRICE_LIST_RATE, orderItemClass.getSALES_PRICE_LIST_RATE());
        values.put(SALES_DISCOUNT_PERCENTAGE, orderItemClass.getSALES_DISCOUNT_PERCENTAGE());
        values.put(SALES_TAX_RATE, orderItemClass.getSALES_TAX_RATE());
        values.put(SALES_TAX_AMOUNT, orderItemClass.getSALES_TAX_AMOUNT());
        values.put(SYNC_STATUS, orderItemClass.getSYNC_STATUS());
        values.put(SALES_GROSS, orderItemClass.getSALES_GROSS());
        values.put(SALES_NET, orderItemClass.getSALES_NET());
        values.put(SALES_VAT, orderItemClass.getSALES_VAT());
        values.put(SALES_TOTAL, orderItemClass.getSALES_TOTAL());
        values.put(SALES_ORDER_LAST_ID, orderItemClass.getSALES_ORDER_LAST_ID());
        values.put(SALES_ORDER_DOC_NO, orderItemClass.getSALES_ORDER_DOC_NO());
        values.put(SALES_DELIVERY_STATUS, orderItemClass.getSALES_DELIVERY_STATUS());

        db.insert(TABLE_SALES_ORDER_ITEM, null, values);
        db.close();


    }


    public void addSalesOrder(SalesOrderClass orderClass) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put(SALES_ORDER_CREATION, orderClass.getSALES_ORDER_CREATION());
        values.put(SALES_ORDER_OWNER, orderClass.getSALES_ORDER_OWNER());
        values.put(SALES_ORDER_DOC_STATUS, orderClass.getSALES_ORDER_DOC_STATUS());
        values.put(SALES_ORDER_BILLING_STATUS, orderClass.getSALES_ORDER_BILLING_STATUS());
        values.put(SALES_ORDER_PO_NO, orderClass.getSALES_ORDER_PO_NO());
        values.put(SALES_ORDER_CUSTOMER, orderClass.getSALES_ORDER_CUSTOMER());
        values.put(SALES_ORDER_ORDER_TYPE, orderClass.getSALES_ORDER_ORDER_TYPE());
        values.put(SALES_ORDER_STATUS, orderClass.getSALES_ORDER_STATUS());
        values.put(SALES_ORDER_COMPANY, orderClass.getSALES_ORDER_COMPANY());
        values.put(SALES_ORDER_NAMING_SERIES, orderClass.getSALES_ORDER_NAMING_SERIES());
        values.put(SALES_ORDER_DOC_NO, orderClass.getSALES_ORDER_DOC_NO());
        values.put(SALES_ORDER_DELIVERY_DATE, orderClass.getSALES_ORDER_DELIVERY_DATE());
        values.put(SALES_ORDER_CURRENCY, orderClass.getSALES_ORDER_CURRENCY());
        values.put(SALES_ORDER_CONVERSION_RATE, orderClass.getSALES_ORDER_CONVERSION_RATE());
        values.put(SALES_ORDER_PRICE_LIST, orderClass.getSALES_ORDER_PRICE_LIST());
        values.put(SALES_ORDER_PLC_CONVERSION_RATE, orderClass.getSALES_ORDER_PLC_CONVERSION_RATE());
        values.put(SALES_ORDER_DEVICE_ID, orderClass.getSALES_ORDER_DEVICE_ID());
        values.put(SYNC_STATUS, orderClass.getSYNC_STATUS());
        values.put(SALES_ORDER_LATITUDE, orderClass.getSALES_ORDER_LATITUDE());
        values.put(SALES_ORDER_LONGITUDE, orderClass.getSALES_ORDER_LONGITUDE());

        db.insert(TABLE_SALES_ORDER, null, values);

        db.close();


    }


    public void addSettings(SettingsClass settingsClass) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put(KEY_NAMING_SERIES_SALES_ORDER, settingsClass.getNaming_series_sales_order());
        values.put(KEY_NAMING_SERIES_SALES_INVOICE, settingsClass.getNaming_series_sales_invoice());
        values.put(KEY_NAMING_SERIES_PAYMENT, settingsClass.getNaming_series_payment());
        values.put(KEY_NAMING_SERIES_TRANSFER, settingsClass.getNaming_series_transfer());
        values.put(KEY_NAMING_SERIES_CUSTOMER_VISIT, settingsClass.getNaming_series_customer_visit());
        values.put(LAST_DOC_NO_SALES_ORDER, settingsClass.getLast_doc_no_sales_order());
        values.put(LAST_DOC_NO_SALES_INVOICE, settingsClass.getLast_doc_no_sales_invoice());
        values.put(LAST_DOC_NO_PAYMENT, settingsClass.getLast_doc_no_payment());
        values.put(LAST_DOC_NO_TRANSFER, settingsClass.getLast_doc_no_transfer());
        //values.put(LAST_DOC_NO_CUSTOMER_VISIT, settingsClass.getLast_doc_no_customer_visit());
        values.put(DEFAULT_CURRENCY, settingsClass.getDefault_currency());
        values.put(DEFAULT_CREDIT_LIMIT_FOR_NEW_CUSTOMER, settingsClass.getDefault_credit_limit_for_new_customer());
        values.put(TAX_RATE, settingsClass.getTax_rate());
        values.put(DEVICE_ID, settingsClass.getDevice_id());
        values.put(WARE_HOUSE, settingsClass.getWarehouse());
        values.put(CUSTOMER_ACCESS, settingsClass.getCustomer_access());
        values.put(SALES_ORDER_ACCESS, settingsClass.getSales_order_access());
        values.put(SALES_INVOICE_ACCESS, settingsClass.getSales_invoice_access());
        values.put(PAYMENT_ACCESS, settingsClass.getPayment_access());
        values.put(TRANSFER_ACCESS, settingsClass.getTransfer_access());
        values.put(SETTING_ACCESS_PASSWORD, settingsClass.getSetting_access_password());
        values.put(SETTING_URL, settingsClass.getURL_string());
        values.put(API_USERNAME, settingsClass.getAPI_User_Name());
        values.put(API_PASSWORD, settingsClass.getAPI_Password());
        values.put(COMPANY_NAME, settingsClass.getCompany_name());
        values.put(TAX_ACCOUNT_HEAD, settingsClass.getTax_account_head());
        values.put(SETTINGS_PAID_FROM, settingsClass.getSETTINGS_PAID_FROM());
        values.put(SETTINGS_DEFAULT_PAYMENT_MODE, settingsClass.getSETTINGS_DEFAULT_PAYMENT_MODE());
        values.put(PRINTER_TYPE,settingsClass.getPrinter_type());
        values.put(PRINTER_IP_ADDRESS,settingsClass.getPrinter_ip_address());
        values.put(PRINTER_PORT_NO,settingsClass.getPrinter_port_no());
        values.put(PRINTER_MAC_ADDRESS,settingsClass.getPrinter_mac_address());
        values.put(AUTO_SYNC_INTERVAL,settingsClass.getSync_interval());
        values.put(TRANS_DEFAULT_QTY,settingsClass.getDefault_trans_qty());
        db.insert(TABLE_SETTINGS, null, values);

        db.close();


    }

    public void adItmDtl(List<ItemDetailUom> uoms) {


    }


    public void addItemDetail(ItemDetailClass itemDetailClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ITEM_PARENT, itemDetailClass.getItem_parent());
        values.put(CONVERSION_FACTOR, itemDetailClass.getConversion_factor());
        values.put(UOM, itemDetailClass.getUom());
        values.put(ALU2, itemDetailClass.getAlu2());
        values.put(PRICE, itemDetailClass.getPrice());

        db.insert(TABLE_ITEM_DETAIL, null, values);
        db.close();

    }


    public void addItem(ItemClass itemClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ITEM_CODE, itemClass.getItem_code());
        values.put(ITEM_NAME, itemClass.getItem_name());
        values.put(DESCRIPTION, itemClass.getDescription());
        values.put(ITEM_GROUP, itemClass.getItem_group());
        values.put(BRAND, itemClass.getBrand());
        values.put(BAR_CODE, itemClass.getBar_code());
        values.put(STOCK_UOM, itemClass.getStock_uom());

        db.insert(TABLE_ITEM, null, values);
        db.close();

    }


    public List<ItemClass> getItemByBarcode(String bar) {


        List<ItemClass> arrayList = new ArrayList<ItemClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEM + " where " + ITEM_CODE + " = '" + bar + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemClass itemClass = new ItemClass();

                itemClass.setId(cursor.getString(0));
                itemClass.setItem_code(cursor.getString(1));
                itemClass.setItem_name(cursor.getString(2));
                itemClass.setDescription(cursor.getString(3));
                itemClass.setItem_group(cursor.getString(4));
                itemClass.setBrand(cursor.getString(5));
                itemClass.setBar_code(cursor.getString(6));
                itemClass.setStock_uom(cursor.getString(7));

                arrayList.add(itemClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public List<ItemClass> getItem() {

        List<ItemClass> arrayList = new ArrayList<ItemClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEM + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemClass itemClass = new ItemClass();

                itemClass.setId(cursor.getString(0));
                itemClass.setItem_code(cursor.getString(1));
                itemClass.setItem_name(cursor.getString(2));
                itemClass.setDescription(cursor.getString(3));
                itemClass.setItem_group(cursor.getString(4));
                itemClass.setBrand(cursor.getString(5));
                itemClass.setBar_code(cursor.getString(6));
                itemClass.setStock_uom(cursor.getString(7));

                arrayList.add(itemClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }
    public List<Item_Return_Details> getItemReturnDetails() {

        List<Item_Return_Details> arrayList = new ArrayList<Item_Return_Details>();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEM + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Item_Return_Details itemClass = new Item_Return_Details();
                itemClass.setItem_name(cursor.getString(2));


                arrayList.add(itemClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public void addUser(UserClass userClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_FIRST_NAME, userClass.getmFirstName());
        values.put(USER_LAST_NAME, userClass.getmLastName());
        values.put(USER_FULL_NAME, userClass.getmFullName());
        values.put(USER_USERNAME, userClass.getmUserName());
        values.put(USER_PASSWORD_FOR_MOB_APPS, userClass.getmPasswordMob());

        db.insert(TABLE_USER, null, values);

        db.close();


    }


    public void addPassword(Password password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SETTING_ACCESS_PASSWORD, password.getAccessPassword());

        db.insert(TABLE_PASSWORD, null, values);
        db.close();


    }


    public void addCustomer(CustomerClass customerClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CUSTOMER_UNIQUE_NAME, customerClass.getName());
        values.put(CUSTOMER_NAME, customerClass.getCustomer_name());
        values.put(EMAIL_ID, customerClass.getEmail_id());
        values.put(MOBILE_NO, customerClass.getMobile_no());
        values.put(TAX_ID, customerClass.getTax_id());
        values.put(CUSTOMER_PRIMARY_CONTACT, customerClass.getCustomer_primary_contact());
        values.put(CREDIT_LIMIT, customerClass.getCredit_limit());
        values.put(DEVICE_ID, customerClass.getDevice_id());
        values.put(IS_NEW, customerClass.getIs_new());
        values.put(SYNC_STATUS, customerClass.getSync_status());
        values.put(SERVER_CUSTOMER_ID, customerClass.getServer_customer_id());

        db.insert(TABLE_CUSTOMER, null, values);
        db.close();

    }

    public void addCustomerAsset(CusomerAssetList customerClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ASSET_CUSTOMER_NAME, customerClass.getASSET_CUSTOMER_NAME());
        values.put(ASSET_ASSET_ID, customerClass.getASSET_ASSET_ID());

        db.insert(TABLE_CUSTOMER_ASSET_LIST, null, values);
        db.close();

    }

    public void addAddress(AddressClass addressClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CUSTOMER_UNIQUE_NAME, addressClass.getName());
        values.put(CUSTOMER_NAME, addressClass.getName());
        values.put(ADDRESS_TITLE, addressClass.getTitle());
        values.put(ADDRESS_LINE1, addressClass.getAddress_line1());
        values.put(ADDRESS_LINE2, addressClass.getAddress_line2());
        values.put(COMPANY, addressClass.getCompany());
        values.put(CITY, addressClass.getCity());
        values.put(SYNC_STATUS, addressClass.getSync_status());

        db.insert(TABLE_ADDRESS, null, values);
        db.close();

    }

    public List<Payment> getPaymentMainList() {

        List<Payment> arrayList = new ArrayList<Payment>();

        String selectQuery = "SELECT  * FROM " + TABLE_PAYMENT + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Payment orderClass = new Payment();


                orderClass.setKEY_ID(cursor.getInt(0));
                orderClass.setPAYMENT_CREATION(cursor.getString(1));
                orderClass.setPAYMENT_DOC_NO(cursor.getString(2));
                orderClass.setMODE_OF_PAYMENT(cursor.getString(3));
                orderClass.setRECEIVED_AMOUNT(cursor.getString(4));
                orderClass.setPAYMENT_CUSTOMER(cursor.getString(5));
                orderClass.setPAYMENT_SYNC_STATUS(cursor.getInt(6));
                orderClass.setPAYMENT_REFERENCE_NO(cursor.getString(7));
                orderClass.setPAYMENT_REFERENCE_DATE(cursor.getString(8));
                orderClass.setPAYMENT_PAID_TO(cursor.getString(9));


                arrayList.add(orderClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public List<Payment> getPaymentMainListByCustomer(String cust) {

        List<Payment> arrayList = new ArrayList<Payment>();

        String selectQuery = "SELECT  * FROM " + TABLE_PAYMENT + " where " + PAYMENT_CUSTOMER + " = '" + cust + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Payment orderClass = new Payment();


                orderClass.setKEY_ID(cursor.getInt(0));
                orderClass.setPAYMENT_CREATION(cursor.getString(1));
                orderClass.setPAYMENT_DOC_NO(cursor.getString(2));
                orderClass.setMODE_OF_PAYMENT(cursor.getString(3));
                orderClass.setRECEIVED_AMOUNT(cursor.getString(4));
                orderClass.setPAYMENT_CUSTOMER(cursor.getString(5));
                orderClass.setPAYMENT_REFERENCE_NO(cursor.getString(7));
                orderClass.setPAYMENT_REFERENCE_DATE(cursor.getString(8));
                orderClass.setPAYMENT_PAID_TO(cursor.getString(9));


                arrayList.add(orderClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public List<Payment> getPaymentList() {

        List<Payment> arrayList = new ArrayList<Payment>();

        String selectQuery = "SELECT  * FROM " + TABLE_PAYMENT + " where " + PAYMENT_SYNC_STATUS + " = 0 ;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Payment orderClass = new Payment();


                orderClass.setKEY_ID(cursor.getInt(0));
                orderClass.setPAYMENT_CREATION(cursor.getString(1));
                orderClass.setPAYMENT_DOC_NO(cursor.getString(2));
                orderClass.setMODE_OF_PAYMENT(cursor.getString(3));
                orderClass.setRECEIVED_AMOUNT(cursor.getString(4));
                orderClass.setPAYMENT_CUSTOMER(cursor.getString(5));
                orderClass.setPAYMENT_REFERENCE_NO(cursor.getString(7));
                orderClass.setPAYMENT_REFERENCE_DATE(cursor.getString(8));
                orderClass.setPAYMENT_PAID_TO(cursor.getString(9));
                orderClass.setPAYMENT_OWNER(cursor.getString(10));//11 territory
                orderClass.setPAYMENT_POSTING_TIME(cursor.getString(12));
                arrayList.add(orderClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public List<Mode_Of_Payment> getModeOfPayment() {

        List<Mode_Of_Payment> arrayList = new ArrayList<Mode_Of_Payment>();

        String selectQuery = "SELECT  * FROM " + TABLE_MODE_OF_PAYMENT + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Mode_Of_Payment orderClass = new Mode_Of_Payment();

                orderClass.setKEY_ID(cursor.getInt(0));
                orderClass.setPAYMENT_NAME(cursor.getString(1));
                orderClass.setPAYMENT_MODE(cursor.getString(2));
                orderClass.setPAYMENT_TYPE(cursor.getString(3));
                orderClass.setPAYMENT_PAID_TO(cursor.getString(4));

                arrayList.add(orderClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public List<SalesInvoiceClass> getSalesInvoice() {

        List<SalesInvoiceClass> arrayList = new ArrayList<SalesInvoiceClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_INVOICE + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                SalesInvoiceClass orderClass = new SalesInvoiceClass();

                orderClass.setKEY_ID(cursor.getInt(0));
                orderClass.setSALES_INVOICE_CREATION(cursor.getString(1));
                orderClass.setSALES_INVOICE_OWNER(cursor.getString(2));
                orderClass.setSALES_INVOICE_DOC_STATUS(cursor.getInt(3));
                orderClass.setSALES_INVOICE_SELLING_PRICE_LIST(cursor.getString(4));
                orderClass.setSALES_INVOICE_CUSTOMER(cursor.getString(5));
                orderClass.setSALES_INVOICE_COMPANY(cursor.getString(6));
                orderClass.setSALES_INVOICE_NAMING_SERIES(cursor.getString(7));
                orderClass.setSALES_INVOICE_CURRENCY(cursor.getString(8));
                orderClass.setSALES_INVOICE_DOC_NO(cursor.getString(9));
                orderClass.setSALES_INVOICE_CONVERSION_RATE(cursor.getFloat(10));
                orderClass.setSALES_INVOICE_PLC_CONVERSION_RATE(cursor.getFloat(11));
                orderClass.setSALES_INVOICE_RETURN_AGAINST(cursor.getString(12));
                orderClass.setSALES_INVOICE_IS_RETURN(cursor.getInt(13));
                orderClass.setSALES_INVOICE_POSTING_TIME(cursor.getString(14));
                orderClass.setSALES_INVOICE_POSTING_DATE(cursor.getString(15));
                orderClass.setSALES_INVOICE_STATUS(cursor.getString(16));
                orderClass.setSALES_INVOICE_DEVICE_ID(cursor.getString(17));
                orderClass.setSALES_INVOICE_SYNC_STATUS(cursor.getString(18));
                orderClass.setSALES_INVOICE_IS_POS(cursor.getInt(19));
                orderClass.setQTY(getInvoiceQty(cursor.getInt(0)));
                orderClass.setITEM_COUNT(getInvoiceListCount(cursor.getInt(0)));

                if (getInvoiceListCount(cursor.getInt(0)) != 0) {
                    arrayList.add(orderClass);
                }

            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    private int getInvoiceListCount(int anInt) {


        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select count(" + SALES_INVOICE_ID + ") from " + TABLE_SALES_INVOICE_ITEM + " where " + SALES_INVOICE_ID + " = '" + anInt + "';";
        Cursor cursor = db.rawQuery(query, null);
        int result = 0;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return result;


    }


    public List<SalesInvoiceClass> getSalesInvoiceByCustomer(String bar) {

        List<SalesInvoiceClass> arrayList = new ArrayList<SalesInvoiceClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_INVOICE + " where " + SALES_INVOICE_CUSTOMER + " = '" + bar + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                SalesInvoiceClass orderClass = new SalesInvoiceClass();

                orderClass.setKEY_ID(cursor.getInt(0));
                orderClass.setSALES_INVOICE_CREATION(cursor.getString(1));
                orderClass.setSALES_INVOICE_OWNER(cursor.getString(2));
                orderClass.setSALES_INVOICE_DOC_STATUS(cursor.getInt(3));
                orderClass.setSALES_INVOICE_SELLING_PRICE_LIST(cursor.getString(4));
                orderClass.setSALES_INVOICE_CUSTOMER(cursor.getString(5));
                orderClass.setSALES_INVOICE_COMPANY(cursor.getString(6));
                orderClass.setSALES_INVOICE_NAMING_SERIES(cursor.getString(7));
                orderClass.setSALES_INVOICE_CURRENCY(cursor.getString(8));
                orderClass.setSALES_INVOICE_DOC_NO(cursor.getString(9));
                orderClass.setSALES_INVOICE_CONVERSION_RATE(cursor.getFloat(10));
                orderClass.setSALES_INVOICE_PLC_CONVERSION_RATE(cursor.getFloat(11));
                orderClass.setSALES_INVOICE_RETURN_AGAINST(cursor.getString(12));
                orderClass.setSALES_INVOICE_IS_RETURN(cursor.getInt(13));
                orderClass.setSALES_INVOICE_POSTING_TIME(cursor.getString(14));
                orderClass.setSALES_INVOICE_POSTING_DATE(cursor.getString(15));
                orderClass.setSALES_INVOICE_STATUS(cursor.getString(16));
                orderClass.setSALES_INVOICE_DEVICE_ID(cursor.getString(17));
                orderClass.setSALES_INVOICE_SYNC_STATUS(cursor.getString(18));
                orderClass.setQTY(getInvoiceQty(cursor.getInt(0)));

                arrayList.add(orderClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    private int getInvoiceQty(int id) {


        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select sum(" + INVOICE_ITEM_QTY + ") from " + TABLE_SALES_INVOICE_ITEM + " where " + SALES_INVOICE_ID + " = '" + id + "';";
        Cursor cursor = db.rawQuery(query, null);
        int result = 0;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return result;


    }


    public List<SalesOrderClass> getSalesOrder() {

        List<SalesOrderClass> arrayList = new ArrayList<SalesOrderClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_ORDER + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SalesOrderClass orderClass = new SalesOrderClass();

                orderClass.setKEY_ID(cursor.getString(0));
                orderClass.setSALES_ORDER_CREATION(cursor.getString(1));
                orderClass.setSALES_ORDER_OWNER(cursor.getString(2));
                orderClass.setSALES_ORDER_DOC_STATUS(cursor.getInt(3));
                orderClass.setSALES_ORDER_BILLING_STATUS(cursor.getString(4));
                orderClass.setSALES_ORDER_PO_NO(cursor.getString(5));
                orderClass.setSALES_ORDER_CUSTOMER(cursor.getString(6));
                orderClass.setSALES_ORDER_ORDER_TYPE(cursor.getString(7));
                orderClass.setSALES_ORDER_STATUS(cursor.getString(8));
                orderClass.setSALES_ORDER_COMPANY(cursor.getString(9));
                orderClass.setSALES_ORDER_NAMING_SERIES(cursor.getString(10));
                orderClass.setSALES_ORDER_DOC_NO(cursor.getString(11));
                orderClass.setSALES_ORDER_DELIVERY_DATE(cursor.getString(12));
                orderClass.setSALES_ORDER_CURRENCY(cursor.getString(13));
                orderClass.setSALES_ORDER_CONVERSION_RATE(cursor.getFloat(14));
                orderClass.setSALES_ORDER_PRICE_LIST(cursor.getString(15));
                orderClass.setSALES_ORDER_PLC_CONVERSION_RATE(cursor.getFloat(16));
                orderClass.setSALES_ORDER_DEVICE_ID(cursor.getString(17));
                orderClass.setSYNC_STATUS(cursor.getInt(18));
                orderClass.setTOTAL_QUANDITY(getTotalQty(cursor.getString(0)));
                orderClass.setITEM_COUNT(getOrderItemCount(cursor.getString(0)));


                // orderClass.setSALES_ORDER_CUSTOMER_CODE(getSalesOrderCustomerCode(cursor.getString(6)));
                if (getOrderItemCount(cursor.getString(0)) != 0) {
                    arrayList.add(orderClass);
                }


            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    private int getOrderItemCount(String cnt) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select count(" + SALES_ORDER_LAST_ID + ") from " + TABLE_SALES_ORDER_ITEM + " where " + SALES_ORDER_LAST_ID + " = '" + cnt + "';";
        Cursor cursor = db.rawQuery(query, null);
        int result = 0;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return result;


    }

    public List<SalesOrderClass> getSalesOrderByCustomer(String bar) {

        List<SalesOrderClass> arrayList = new ArrayList<SalesOrderClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_ORDER + " where " + SALES_ORDER_CUSTOMER + " = '" + bar + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SalesOrderClass orderClass = new SalesOrderClass();

                orderClass.setKEY_ID(cursor.getString(0));
                orderClass.setSALES_ORDER_CREATION(cursor.getString(1));
                orderClass.setSALES_ORDER_OWNER(cursor.getString(2));
                orderClass.setSALES_ORDER_DOC_STATUS(cursor.getInt(3));
                orderClass.setSALES_ORDER_BILLING_STATUS(cursor.getString(4));
                orderClass.setSALES_ORDER_PO_NO(cursor.getString(5));
                orderClass.setSALES_ORDER_CUSTOMER(cursor.getString(6));
                orderClass.setSALES_ORDER_ORDER_TYPE(cursor.getString(7));
                orderClass.setSALES_ORDER_STATUS(cursor.getString(8));
                orderClass.setSALES_ORDER_COMPANY(cursor.getString(9));
                orderClass.setSALES_ORDER_NAMING_SERIES(cursor.getString(10));
                orderClass.setSALES_ORDER_DOC_NO(cursor.getString(11));
                orderClass.setSALES_ORDER_DELIVERY_DATE(cursor.getString(12));
                orderClass.setSALES_ORDER_CURRENCY(cursor.getString(13));
                orderClass.setSALES_ORDER_CONVERSION_RATE(cursor.getFloat(14));
                orderClass.setSALES_ORDER_PRICE_LIST(cursor.getString(15));
                orderClass.setSALES_ORDER_PLC_CONVERSION_RATE(cursor.getFloat(16));
                orderClass.setSALES_ORDER_DEVICE_ID(cursor.getString(17));
                orderClass.setSYNC_STATUS(cursor.getInt(18));
                orderClass.setTOTAL_QUANDITY(getTotalQty(cursor.getString(0)));
                // orderClass.setSALES_ORDER_CUSTOMER_CODE(getSalesOrderCustomerCode(cursor.getString(6)));
                arrayList.add(orderClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }

   /* private String getSalesOrderCustomerCode(String string) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + CUSTOMER_UNIQUE_NAME + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;

    }*/


    private int getTotalQty(String id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select sum(" + SALES_QTY + ") from " + TABLE_SALES_ORDER_ITEM + " where " + SALES_ORDER_LAST_ID + " = '" + id + "';";
        Cursor cursor = db.rawQuery(query, null);
        int result = 0;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return result;


    }


    public List<SalesOrderItemClass> getSalesOrderItem(String last_id) {

        List<SalesOrderItemClass> arrayList = new ArrayList<SalesOrderItemClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_ORDER_ITEM + " where " + SALES_ORDER_LAST_ID + " = '" + last_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                SalesOrderItemClass orderItemClass = new SalesOrderItemClass();

                orderItemClass.setKEY_ID(cursor.getInt(0));
                orderItemClass.setSALES_ORDER_CUSTOMER(cursor.getString(1));
                orderItemClass.setSALES_QTY(cursor.getFloat(2));
                orderItemClass.setSALES_WAREHOUSE(cursor.getString(3));
                orderItemClass.setSALES_ITEM_NAME(cursor.getString(4));
                orderItemClass.setSALES_RATE(cursor.getFloat(5));
                orderItemClass.setSALES_STOCK_UOM(cursor.getString(6));
                orderItemClass.setSALES_ITEM_CODE(cursor.getString(7));
                orderItemClass.setSALES_PRICE_LIST_RATE(cursor.getFloat(8));
                orderItemClass.setSALES_DISCOUNT_PERCENTAGE(cursor.getFloat(9));
                orderItemClass.setSALES_TAX_RATE(cursor.getFloat(10));
                orderItemClass.setSALES_TAX_AMOUNT(cursor.getFloat(11));
                orderItemClass.setSALES_GROSS(cursor.getFloat(12));
                orderItemClass.setSALES_NET(cursor.getFloat(13));
                orderItemClass.setSALES_VAT(cursor.getFloat(14));
                orderItemClass.setSALES_TOTAL(cursor.getFloat(15));
                orderItemClass.setSYNC_STATUS(cursor.getInt(16));
                orderItemClass.setSALES_ORDER_LAST_ID(cursor.getString(17));
                orderItemClass.setSALES_ORDER_DOC_NO(cursor.getString(18));


                arrayList.add(orderItemClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public List<SalesOrderItemClass> SalesInvoicegetSalesOrderItem(String cust) {

        List<SalesOrderItemClass> arrayList = new ArrayList<SalesOrderItemClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_ORDER_ITEM + " where " + SALES_ORDER_CUSTOMER + " = '" + cust + "' and " + SALES_DELIVERY_STATUS + " = 0 ;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                SalesOrderItemClass orderItemClass = new SalesOrderItemClass();

                orderItemClass.setKEY_ID(cursor.getInt(0));
                orderItemClass.setSALES_ORDER_CUSTOMER(cursor.getString(1));
                orderItemClass.setSALES_QTY(cursor.getFloat(2));
                orderItemClass.setSALES_WAREHOUSE(cursor.getString(3));
                orderItemClass.setSALES_ITEM_NAME(cursor.getString(4));
                orderItemClass.setSALES_RATE(cursor.getFloat(5));
                orderItemClass.setSALES_STOCK_UOM(cursor.getString(6));
                orderItemClass.setSALES_ITEM_CODE(cursor.getString(7));
                orderItemClass.setSALES_PRICE_LIST_RATE(cursor.getFloat(8));
                orderItemClass.setSALES_DISCOUNT_PERCENTAGE(cursor.getFloat(9));
                orderItemClass.setSALES_TAX_RATE(cursor.getFloat(10));
                orderItemClass.setSALES_TAX_AMOUNT(cursor.getFloat(11));
                orderItemClass.setSALES_GROSS(cursor.getFloat(12));
                orderItemClass.setSALES_NET(cursor.getFloat(13));
                orderItemClass.setSALES_VAT(cursor.getFloat(14));
                orderItemClass.setSALES_TOTAL(cursor.getFloat(15));
                orderItemClass.setSYNC_STATUS(cursor.getInt(16));
                orderItemClass.setSALES_ORDER_LAST_ID(cursor.getString(17));
                orderItemClass.setSALES_ORDER_DOC_NO(cursor.getString(18));


                arrayList.add(orderItemClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public List<SalesInvoiceItemClass> getSalesInvoiceSelectedItem(int last_id) {

        List<SalesInvoiceItemClass> arrayList = new ArrayList<SalesInvoiceItemClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_INVOICE_ITEM + " where " + SALES_INVOICE_ID + " = '" + last_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                SalesInvoiceItemClass orderItemClass = new SalesInvoiceItemClass();

                orderItemClass.setKEY_ID(cursor.getInt(0));
                orderItemClass.setSALES_INVOICE_ID(cursor.getInt(1));
                orderItemClass.setINVOICE_ITEM_QTY(cursor.getFloat(2));
                orderItemClass.setINVOICE_ITEM_WAREHOUSE(cursor.getString(3));
                orderItemClass.setINVOICE_ITEM_ITEMNAME(cursor.getString(4));
                orderItemClass.setINVOICE_ITEM_RATE(cursor.getFloat(5));
                orderItemClass.setINVOICE_ITEM_STOCK_UOM(cursor.getString(6));
                orderItemClass.setINVOICE_ITEM_ITEMCODE(cursor.getString(7));
                orderItemClass.setINVOICE_ITEM_PRICELIST_RATE(cursor.getFloat(8));
                orderItemClass.setINVOICE_ITEM_DISCOUNT_PERCENTAGE(cursor.getFloat(9));
                orderItemClass.setINVOICE_ITEM_TAX_RATE(cursor.getFloat(10));
                orderItemClass.setINVOICE_ITEM_TAX_AMOUNT(cursor.getFloat(11));
                orderItemClass.setINVOICE_ITEM_SALES_ORDER(cursor.getString(12));
                orderItemClass.setINVOICE_ITEM_SO_DETAIL(cursor.getString(13));
                orderItemClass.setSALES_GROSS(cursor.getFloat(14));
                orderItemClass.setSALES_NET(cursor.getFloat(15));
                orderItemClass.setSALES_VAT(cursor.getFloat(16));
                orderItemClass.setSALES_TOTAL(cursor.getFloat(17));
                orderItemClass.setINVOICE_ITEM_ASSET(cursor.getString(cursor.getColumnIndex(INVOICE_ITEM_ASSET)));

                arrayList.add(orderItemClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public Daily_Sales_Collection_Report getDailySalesCollectonReport(String from_date,String to_date) {

        List<SalesInvoiceItemClass> arrayList = new ArrayList<SalesInvoiceItemClass>();

        Daily_Sales_Collection_Report rpt=null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String innerQry = "SELECT " + KEY_ID + " FROM " + TABLE_SALES_INVOICE + " WHERE " + SALES_INVOICE_CREATION + " BETWEEN '" + from_date + "' AND '" + to_date + "'";
            //String innerQry = "SELECT " + KEY_ID + " FROM " + TABLE_SALES_INVOICE + " WHERE " + SALES_INVOICE_CREATION + " >= '" + from_date + "'";
            //String selectQuery = "SELECT  SUM(" + INVOICE_ITEM_QTY + "*" + INVOICE_ITEM_RATE + ") AS sales_amount,SUM(" + INVOICE_ITEM_TAX_AMOUNT + ") AS total_tax FROM " + TABLE_SALES_INVOICE_ITEM + " where " + SALES_INVOICE_ID + " IN (" + innerQry + ");";
            String selectQuery = "SELECT  SUM(" + INVOICE_ITEM_QTY + "*" + INVOICE_ITEM_RATE + ") AS sales_amount,SUM(" + SALES_VAT + ") AS total_tax FROM " + TABLE_SALES_INVOICE_ITEM + " where " + SALES_INVOICE_ID + " IN ("+innerQry+");";
            Log.i("QUERY","<=="+ selectQuery);

            Cursor cursor5 = db.rawQuery(innerQry, null);
            if(cursor5!=null)
            if (cursor5.moveToFirst()) {
                Log.i("QUERY","<=="+ innerQry);
            }
           // SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {


                rpt = new Daily_Sales_Collection_Report();

                rpt.setFrom_date(from_date);
                rpt.setTo_date(to_date);
                rpt.setSales_amount(cursor.getFloat(0));
                rpt.setVat_amount(cursor.getFloat(1));
                rpt.setSales_total_with_vat(rpt.getSales_amount() + rpt.getVat_amount());
                rpt.setWarehouse(getWarehouse());

                selectQuery = "SELECT  SUM(" + SALES_INVOICE_PAYMENT_AMOUNT + ") sales_received_amount  FROM " + TABLE_SALES_INVOICE_PAYMENT + " where " + SALES_INVOICE_ID + " IN (" + innerQry + ")";
                Cursor cursor1 = db.rawQuery(selectQuery, null);

                if (cursor1.moveToFirst()) {
                    rpt.setPending_collection(rpt.getSales_total_with_vat()-cursor1.getFloat(0));
                }
                selectQuery = "SELECT  SUM(" + RECEIVED_AMOUNT + ") received_amount  FROM " + TABLE_PAYMENT + " where " + PAYMENT_CREATION +  " BETWEEN '" + from_date + "' AND '" + to_date + "'";
                Cursor cursor2 = db.rawQuery(selectQuery, null);

                if (cursor2.moveToFirst()) {
                    rpt.setReceved_amount(cursor2.getFloat(0));
                    rpt.setTotal_collection((rpt.getSales_total_with_vat()-rpt.getPending_collection())+rpt.getReceved_amount());
                }
                else {
                    rpt.setReceved_amount(0.0f);
                    rpt.setTotal_collection((rpt.getSales_total_with_vat()-rpt.getPending_collection())+rpt.getReceved_amount());
                }

                rpt.setOutStanding_invoice_detailsList(getOutStandingInvoiceDetails(from_date,to_date));
                rpt.setOutstanding_collection_details(getOutStandingCollectionDetails(from_date,to_date));
                rpt.setItem_return_details(getItemReturnDetails());
                cursor.close();
                cursor1.close();
                cursor2.close();

            }
        }catch (Exception e){
            throw e;

        }
        return rpt;
    }
    private List<Outstanding_Collection_Details> getOutStandingCollectionDetails(String from_date, String to_date)    {

        List<Outstanding_Collection_Details> lst=new ArrayList<Outstanding_Collection_Details>();
        try{


            String selectQuery = "SELECT  P."+PAYMENT_DOC_NO+",C."+SERVER_CUSTOMER_ID+",P."+RECEIVED_AMOUNT+
                    " FROM " + TABLE_PAYMENT +" AS P "+
                    " LEFT JOIN "+TABLE_CUSTOMER+ " AS C ON C."+CUSTOMER_NAME+"=P."+PAYMENT_CUSTOMER+
                    " WHERE P."+PAYMENT_CREATION +" BETWEEN '" + from_date + "' AND '" + to_date + "'"+
                    " ORDER BY P."+PAYMENT_REFERENCE_NO+",C."+SERVER_CUSTOMER_ID+";";


            Log.i("QUERY OUT COL","<=="+ selectQuery);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    lst.add(new Outstanding_Collection_Details(cursor.getString(0),cursor.getString(1),cursor.getFloat(2)));

                } while (cursor.moveToNext());
            }

        }catch (Exception e){
            throw  e;
        }
        return lst;
    }
    private List<OutStanding_Invoice_Details> getOutStandingInvoiceDetails(String from_date,String to_date)    {

        List<OutStanding_Invoice_Details> lst=new ArrayList<OutStanding_Invoice_Details>();
        try{


            String selectQuery = "SELECT  H."+SALES_INVOICE_DOC_NO+",C."+SERVER_CUSTOMER_ID+",L."+INVOICE_ITEM_ASSET+"," +
                                "SUM((L."+INVOICE_ITEM_RATE+"*L."+INVOICE_ITEM_QTY+")+L."+SALES_VAT+") " +
                                "FROM " + TABLE_SALES_INVOICE_ITEM +" AS L LEFT JOIN "+TABLE_SALES_INVOICE +" AS H ON H."+KEY_ID+"="+"L."+SALES_INVOICE_ID+
                                " LEFT JOIN "+TABLE_CUSTOMER+ " AS C ON C."+CUSTOMER_NAME+"=H."+SALES_INVOICE_CUSTOMER+
                                " WHERE H."+SALES_INVOICE_CREATION +" BETWEEN '" + from_date + "' AND '" + to_date + "'"+
                                " AND L."+SALES_INVOICE_ID + " NOT IN (SELECT " + SALES_INVOICE_ID + " FROM " + TABLE_SALES_INVOICE_PAYMENT +")"+
                                " GROUP BY H."+SALES_INVOICE_DOC_NO+",C."+SERVER_CUSTOMER_ID+",L."+INVOICE_ITEM_ASSET +
                                " ORDER BY H."+SALES_INVOICE_DOC_NO+",C."+SERVER_CUSTOMER_ID+",L."+INVOICE_ITEM_ASSET+";";

            Log.i("QUERY OUT INV","<=="+ selectQuery);

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    lst.add(new OutStanding_Invoice_Details(cursor.getString(1),cursor.getFloat(3),cursor.getString(2),cursor.getString(0)));

                } while (cursor.moveToNext());
            }

        }catch (Exception e){
            throw  e;
        }
        return lst;
    }

    public List<SalesInvoiceItemClass> getSalesInvoicetem() {

        List<SalesInvoiceItemClass> arrayList = new ArrayList<SalesInvoiceItemClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_INVOICE_ITEM + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                SalesInvoiceItemClass orderItemClass = new SalesInvoiceItemClass();

                orderItemClass.setKEY_ID(cursor.getInt(0));
                orderItemClass.setSALES_INVOICE_ID(cursor.getInt(1));
                orderItemClass.setINVOICE_ITEM_QTY(cursor.getFloat(2));
                orderItemClass.setINVOICE_ITEM_WAREHOUSE(cursor.getString(3));
                orderItemClass.setINVOICE_ITEM_ITEMNAME(cursor.getString(4));
                orderItemClass.setINVOICE_ITEM_RATE(cursor.getFloat(5));
                orderItemClass.setINVOICE_ITEM_STOCK_UOM(cursor.getString(6));
                orderItemClass.setINVOICE_ITEM_ITEMCODE(cursor.getString(7));
                orderItemClass.setINVOICE_ITEM_PRICELIST_RATE(cursor.getFloat(8));
                orderItemClass.setINVOICE_ITEM_DISCOUNT_PERCENTAGE(cursor.getFloat(9));
                orderItemClass.setINVOICE_ITEM_TAX_RATE(cursor.getFloat(10));
                orderItemClass.setINVOICE_ITEM_TAX_AMOUNT(cursor.getFloat(11));
                orderItemClass.setINVOICE_ITEM_SALES_ORDER(cursor.getString(12));
                orderItemClass.setINVOICE_ITEM_SO_DETAIL(cursor.getString(13));

                arrayList.add(orderItemClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public List<SalesOrderItemClass> getSalesOrderReturnItem(String CUSTOMER_NAME) {

        List<SalesOrderItemClass> arrayList = new ArrayList<SalesOrderItemClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_ORDER_ITEM + " where " + SALES_ORDER_CUSTOMER + " = '" + CUSTOMER_NAME + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                SalesOrderItemClass orderItemClass = new SalesOrderItemClass();

                orderItemClass.setKEY_ID(cursor.getInt(0));
                orderItemClass.setSALES_ORDER_CUSTOMER(cursor.getString(1));
                orderItemClass.setSALES_QTY(cursor.getFloat(2));
                orderItemClass.setSALES_WAREHOUSE(cursor.getString(3));
                orderItemClass.setSALES_ITEM_NAME(cursor.getString(4));
                orderItemClass.setSALES_RATE(cursor.getFloat(5));
                orderItemClass.setSALES_STOCK_UOM(cursor.getString(6));
                orderItemClass.setSALES_ITEM_CODE(cursor.getString(7));
                orderItemClass.setSALES_PRICE_LIST_RATE(cursor.getFloat(8));
                orderItemClass.setSALES_DISCOUNT_PERCENTAGE(cursor.getFloat(9));
                orderItemClass.setSALES_TAX_RATE(cursor.getFloat(10));
                orderItemClass.setSALES_TAX_AMOUNT(cursor.getFloat(11));
                orderItemClass.setSALES_GROSS(cursor.getFloat(12));
                orderItemClass.setSALES_NET(cursor.getFloat(13));
                orderItemClass.setSALES_VAT(cursor.getFloat(14));
                orderItemClass.setSALES_TOTAL(cursor.getFloat(15));
                orderItemClass.setSYNC_STATUS(cursor.getInt(16));
                orderItemClass.setSALES_ORDER_LAST_ID(cursor.getString(17));
                orderItemClass.setSALES_ORDER_DOC_NO(cursor.getString(18));


                arrayList.add(orderItemClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public List<ItemDetailClass> getItemDetail(String item_code) {

        List<ItemDetailClass> arrayList = new ArrayList<ItemDetailClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEM_DETAIL + " where " + ITEM_PARENT + " = '" + item_code + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemDetailClass customerClass = new ItemDetailClass();

                customerClass.setId(cursor.getString(0));
                customerClass.setItem_parent(cursor.getString(1));
                customerClass.setConversion_factor(cursor.getString(2));
                customerClass.setUom(cursor.getString(3));
                customerClass.setAlu2(cursor.getString(4));
                customerClass.setPrice(cursor.getString(5));

                arrayList.add(customerClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public List<ItemDetailClass> getAllItemDetail() {

        List<ItemDetailClass> arrayList = new ArrayList<ItemDetailClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEM_DETAIL + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemDetailClass customerClass = new ItemDetailClass();

                customerClass.setId(cursor.getString(0));
                customerClass.setItem_parent(cursor.getString(1));
                customerClass.setConversion_factor(cursor.getString(2));
                customerClass.setUom(cursor.getString(3));
                customerClass.setAlu2(cursor.getString(4));
                customerClass.setPrice(cursor.getString(5));

                arrayList.add(customerClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public List<CustomerClass> getCustomerDetail(String name) {

        List<CustomerClass> arrayList = new ArrayList<CustomerClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER + " where " + CUSTOMER_UNIQUE_NAME + " = '" + name + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CustomerClass customerClass = new CustomerClass();

                customerClass.setId(cursor.getString(0));
                customerClass.setName(cursor.getString(1));
                customerClass.setCustomer_name(cursor.getString(2));
                customerClass.setEmail_id(cursor.getString(3));
                customerClass.setMobile_no(cursor.getString(4));
                customerClass.setTax_id(cursor.getString(5));
                customerClass.setCustomer_primary_contact(cursor.getString(6));
                customerClass.setCredit_limit(cursor.getFloat(7));
                customerClass.setDevice_id(cursor.getString(8));
                customerClass.setIs_new(cursor.getString(9));
                customerClass.setSync_status(cursor.getString(10));

                arrayList.add(customerClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public List<CustomerClass> getCustomerNamesAll() {

        List<CustomerClass> arrayList = new ArrayList<CustomerClass>();

        String selectQuery = "SELECT * FROM " + TABLE_CUSTOMER + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CustomerClass customerClass = new CustomerClass();

                customerClass.setId(cursor.getString(0));
                customerClass.setName(cursor.getString(1));
                customerClass.setCustomer_name(cursor.getString(2));
                customerClass.setEmail_id(cursor.getString(3));
                customerClass.setMobile_no(cursor.getString(4));
                customerClass.setTax_id(cursor.getString(5));
                customerClass.setCustomer_primary_contact(cursor.getString(6));
                customerClass.setCredit_limit(cursor.getFloat(7));
                customerClass.setDevice_id(cursor.getString(8));
                customerClass.setIs_new(cursor.getString(9));
                customerClass.setSync_status(cursor.getString(10));

                arrayList.add(customerClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public List<CustomerClass> getCustomerDetailScan(String name) {

        List<CustomerClass> arrayList = new ArrayList<CustomerClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER + " where " + MOBILE_NO + " = '" + name + "' OR " + SERVER_CUSTOMER_ID + " = '" + name + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                CustomerClass customerClass = new CustomerClass();

                customerClass.setId(cursor.getString(0));
                customerClass.setName(cursor.getString(1));
                customerClass.setCustomer_name(cursor.getString(2));
                customerClass.setEmail_id(cursor.getString(3));
                customerClass.setMobile_no(cursor.getString(4));
                customerClass.setTax_id(cursor.getString(5));
                customerClass.setCustomer_primary_contact(cursor.getString(6));
                customerClass.setCredit_limit(cursor.getFloat(7));
                customerClass.setDevice_id(cursor.getString(8));
                customerClass.setIs_new(cursor.getString(9));
                customerClass.setSync_status(cursor.getString(10));
                customerClass.setServer_customer_id(cursor.getString(11));

                arrayList.add(customerClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public AddressClass getCustomerAddress(String name) {
        //  List<CustomerClass> arrayList = new ArrayList<CustomerClass>();
        AddressClass addressClass = new AddressClass();


        String selectQuery = "SELECT  * FROM " + TABLE_ADDRESS + " where " + CUSTOMER_UNIQUE_NAME + " = '" + name + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                addressClass.setId(cursor.getString(0));
                addressClass.setTitle(cursor.getString(3));
                addressClass.setAddress_line1(cursor.getString(4));
                addressClass.setAddress_line2(cursor.getString(5));
                addressClass.setCompany(cursor.getString(6));
                addressClass.setCity(cursor.getString(7));

            } while (cursor.moveToNext());
        }
        return addressClass;
    }


    public Password getAccessPassword() {

        Password password = new Password();
        String selectQuery = "SELECT  * FROM " + TABLE_PASSWORD + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                password.setAccessPassword(cursor.getString(1));

            } while (cursor.moveToNext());
        }
        return password;
    }


    public List<AddressPost> getUnsyncCustomerAddress() {
        List<AddressPost> arrayList = new ArrayList<AddressPost>();

        String selectQuery = "SELECT  * FROM " + TABLE_ADDRESS + " where " + SYNC_STATUS + " = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AddressPost addressClass = new AddressPost();

                addressClass.setCustomer_name(cursor.getString(1));
                addressClass.setAddress_title(cursor.getString(3));
                addressClass.setAddress_line1(cursor.getString(4));
                addressClass.setAddress_line2(cursor.getString(5));
                addressClass.setCompany(cursor.getString(6));
                addressClass.setCity(cursor.getString(7));

                arrayList.add(addressClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public List<CustomerClass> getCustomer() {
        List<CustomerClass> arrayList = new ArrayList<CustomerClass>();

        //String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER+ " ORDER BY "+SERVER_CUSTOMER_ID+" ASC ";
        String selectQuery = "SELECT  C.*,IFNULL(A.asset,'') asset FROM " + TABLE_CUSTOMER+
                " AS C LEFT JOIN  (SELECT "+ASSET_CUSTOMER_NAME+",GROUP_CONCAT("+ASSET_ASSET_ID+") asset FROM " + TABLE_CUSTOMER_ASSET_LIST+" GROUP BY "+ASSET_CUSTOMER_NAME+")" +
                " AS A ON C."+CUSTOMER_NAME+"=A."+ASSET_CUSTOMER_NAME+" ORDER BY C."+SERVER_CUSTOMER_ID+" ASC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CustomerClass customerClass = new CustomerClass();

                customerClass.setId(cursor.getString(0));
                customerClass.setName(cursor.getString(1));
                customerClass.setCustomer_name(cursor.getString(2));
                customerClass.setEmail_id(cursor.getString(3));
                customerClass.setMobile_no(cursor.getString(4));
                customerClass.setTax_id(cursor.getString(5));
                customerClass.setCustomer_primary_contact(cursor.getString(6));
                customerClass.setCredit_limit(cursor.getFloat(7));
                customerClass.setDevice_id(cursor.getString(8));
                customerClass.setIs_new(cursor.getString(9));
                customerClass.setSync_status(cursor.getString(10));
                customerClass.setServer_customer_id(cursor.getString(11));
                customerClass.setAsset(cursor.getString(cursor.getColumnIndex("asset")));


                arrayList.add(customerClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public List<CustomerPost> getUnsyncCustomer() {
        List<CustomerPost> arrayList = new ArrayList<CustomerPost>();

        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER + " where " + SYNC_STATUS + " = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CustomerPost customerClass = new CustomerPost();

                customerClass.setCustomer_name(cursor.getString(2));
                customerClass.setEmail_id(cursor.getString(3));
                customerClass.setMobile_no(cursor.getString(4));
                customerClass.setTax_id(cursor.getString(5));
                // customerClass.setCustomer_primary_contact(cursor.getString(6));
                customerClass.setCustomer_primary_contact("");
                customerClass.setCredit_limit(String.valueOf(cursor.getFloat(7)));
                customerClass.setDevice_id(cursor.getString(8));


                arrayList.add(customerClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public List<SettingsClass> getSettings() {
        List<SettingsClass> contactList = new ArrayList<SettingsClass>();

        String selectQuery = "SELECT  * FROM " + TABLE_SETTINGS;
    try {


            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    SettingsClass settingsClass = new SettingsClass();

                    settingsClass.setNaming_series_sales_order(cursor.getString(1));
                    settingsClass.setNaming_series_sales_invoice(cursor.getString(2));
                    settingsClass.setNaming_series_payment(cursor.getString(3));
                    settingsClass.setNaming_series_transfer(cursor.getString(4));
                    settingsClass.setLast_doc_no_sales_order(cursor.getInt(5));
                    settingsClass.setLast_doc_no_sales_invoice(cursor.getInt(6));
                    settingsClass.setLast_doc_no_payment(cursor.getInt(7));
                    settingsClass.setLast_doc_no_transfer(cursor.getInt(8));
                    settingsClass.setDefault_currency(cursor.getString(9));
                    settingsClass.setDefault_credit_limit_for_new_customer(cursor.getFloat(10));
                    settingsClass.setTax_rate(cursor.getFloat(11));
                    settingsClass.setDevice_id(cursor.getString(12));
                    settingsClass.setWarehouse(cursor.getString(13));
                    settingsClass.setCustomer_access(cursor.getInt(14));
                    settingsClass.setSales_order_access(cursor.getInt(15));
                    settingsClass.setSales_invoice_access(cursor.getInt(16));
                    settingsClass.setPayment_access(cursor.getInt(17));
                    settingsClass.setTransfer_access(cursor.getInt(18));
                    settingsClass.setSetting_access_password(cursor.getString(19));
                    settingsClass.setURL_string(cursor.getString(20));
                    settingsClass.setAPI_User_Name(cursor.getString(21));
                    settingsClass.setAPI_Password(cursor.getString(22));
                    settingsClass.setCompany_name(cursor.getString(23));
                    settingsClass.setTax_account_head(cursor.getString(24));
                    settingsClass.setSETTINGS_PAID_FROM(cursor.getString(25));
                    settingsClass.setSETTINGS_DEFAULT_PAYMENT_MODE(cursor.getString(27));

                    if(cursor.getString(28)!=null)
                        settingsClass.setPrinter_type(cursor.getString(28));
                    else
                         settingsClass.setPrinter_type("1");

                    if(cursor.getString(29)!=null)
                        settingsClass.setPrinter_ip_address(cursor.getString(29));
                    else
                        settingsClass.setPrinter_ip_address("");

                    if(cursor.getString(30)!=null)
                        settingsClass.setPrinter_port_no(cursor.getString(30));
                    else
                        settingsClass.setPrinter_port_no("");

                    if(cursor.getString(31)!=null)
                        settingsClass.setPrinter_mac_address(cursor.getString(31));
                    else
                        settingsClass.setPrinter_mac_address("9100");

                    if(cursor.getString(32)!=null)
                        settingsClass.setNaming_series_customer_visit(cursor.getString(32));
                    else
                        settingsClass.setNaming_series_customer_visit("");

                    if(cursor.getString(33)!=null)
                        settingsClass.setSync_interval(cursor.getInt(33));
                    else
                        settingsClass.setSync_interval(0);

                    if(cursor.getString(34)!=null)
                        settingsClass.setDefault_trans_qty(cursor.getFloat(34));
                    else
                        settingsClass.setDefault_trans_qty(0f);
                    contactList.add(settingsClass);
                } while (cursor.moveToNext());
            }
    }catch (Exception e){
    throw e;
    }
        return contactList;
    }

    public List<String> getCustomerAssetList(String customer) {

        List<String> arrayList = new ArrayList<String>();

        String selectQuery = "SELECT "+ASSET_ASSET_ID+" FROM " + TABLE_CUSTOMER_ASSET_LIST + " WHERE "+ASSET_CUSTOMER_NAME+"='"+customer+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                arrayList.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public SettingsClass getPrinterSettings() {
        SettingsClass settingsClass = null;

        try {
        String selectQuery = "SELECT  "+
                                PRINTER_TYPE + ","+
                                PRINTER_IP_ADDRESS + ","+
                                PRINTER_PORT_NO + ","+
                                PRINTER_MAC_ADDRESS +
                              " FROM " + TABLE_SETTINGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                settingsClass = new SettingsClass();
                settingsClass.setPrinter_type(cursor.getString(1));
                settingsClass.setPrinter_ip_address(cursor.getString(2));
                settingsClass.setPrinter_port_no(cursor.getString(3));
                settingsClass.setPrinter_mac_address(cursor.getString(4));
            } while (cursor.moveToNext());
        }
        } catch (Exception e) {
        throw e;
    }

        return settingsClass;
    }
    public void updateAddressDetails(AddressClass addressClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CUSTOMER_UNIQUE_NAME, addressClass.getName());
        values.put(CUSTOMER_NAME, addressClass.getName());
        values.put(ADDRESS_TITLE, addressClass.getTitle());
        values.put(ADDRESS_LINE1, addressClass.getAddress_line1());
        values.put(ADDRESS_LINE2, addressClass.getAddress_line2());
        values.put(COMPANY, addressClass.getCompany());
        values.put(CITY, addressClass.getCity());

        db.update(TABLE_ADDRESS, values, KEY_ID + " = " + addressClass.getId(), null);
        db.close();

    }


    public void updateCustomerDetails(CustomerClass customerClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CUSTOMER_UNIQUE_NAME, customerClass.getName());
        values.put(CUSTOMER_NAME, customerClass.getCustomer_name());
        values.put(EMAIL_ID, customerClass.getEmail_id());
        values.put(MOBILE_NO, customerClass.getMobile_no());
        values.put(TAX_ID, customerClass.getTax_id());
        values.put(CUSTOMER_PRIMARY_CONTACT, customerClass.getCustomer_primary_contact());
        values.put(CREDIT_LIMIT, customerClass.getCredit_limit());
        values.put(DEVICE_ID, customerClass.getDevice_id());

        db.update(TABLE_CUSTOMER, values, KEY_ID + " = " + customerClass.getId(), null);
        db.close();

    }

    public void clearSalesInvoice(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SALES_INVOICE);
        db.execSQL("DELETE FROM " + TABLE_SALES_INVOICE_ITEM );
        db.execSQL("DELETE FROM " + TABLE_SALES_INVOICE_PAYMENT );
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_VISIT_LOG );
    }
    public void clearPayments(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PAYMENT);

    }


    public void clearSettingTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SETTINGS + ";");
    }

    public void clearItemTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ITEM + ";");
    }

    public void clearModePaymentTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MODE_OF_PAYMENT + ";");
    }

    public void clearItemDetailTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ITEM_DETAIL + ";");
    }

    public void clearUserTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER + ";");
    }

    public void clearPasswordTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PASSWORD + ";");
    }

    public void clearCustomerTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER + ";");
    }

    public void clearAddressTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ADDRESS + ";");
    }

    public void clearCustomerAssetTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_ASSET_LIST + ";");
    }

    public void updateCustomerSyncStatus(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SYNC_STATUS, "1");

        db.update(TABLE_CUSTOMER, values, CUSTOMER_UNIQUE_NAME + " = '" + name + "'", null);
        db.close();

    }


    public void updateAddresSyncStatus(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SYNC_STATUS, "1");

        db.update(TABLE_ADDRESS, values, CUSTOMER_UNIQUE_NAME + " = '" + name + "'", null);
        db.close();

    }


    public void updateSalesOrderSyncStatus(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SYNC_STATUS, "1");

        db.update(TABLE_SALES_ORDER, values, KEY_ID + " = '" + id + "'", null);
        db.close();

    }

    public void updateSalesInvoiceSyncStatus(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SALES_INVOICE_SYNC_STATUS, "1");

        db.update(TABLE_SALES_INVOICE, values, KEY_ID + " = '" + id + "'", null);
        db.close();

    }
    public void updateSalesInvoiceSyncStatusWithDocNo(String docNo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SALES_INVOICE_SYNC_STATUS, "1");

        db.update(TABLE_SALES_INVOICE, values, SALES_INVOICE_DOC_NO + " = '" + docNo + "'", null);
        db.close();

    }

    public void updateCustomerVisitSyncStatus(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CUSTOMER_VISIT_SYNC_STATUS, "1");

        db.update(TABLE_CUSTOMER_VISIT_LOG, values, KEY_ID + " = '" + id + "'", null);
        db.close();

    }

    public void updatePaymentSyncStatus(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PAYMENT_SYNC_STATUS, "1");

        db.update(TABLE_PAYMENT, values, KEY_ID + " = '" + id + "'", null);
        db.close();

    }

    public void updateSalesOrderDocNo(int number) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LAST_DOC_NO_SALES_ORDER, number);

        db.update(TABLE_SETTINGS, values, KEY_ID + " = 1", null);
        db.close();

    }

    public void updatePaymentDocNo(int number) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LAST_DOC_NO_PAYMENT, number);

        db.update(TABLE_SETTINGS, values, KEY_ID + " = 1", null);
        db.close();

    }

    public void updateSalesOrderDeliveryStatus(String id, String status) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SALES_DELIVERY_STATUS, status);

        db.update(TABLE_SALES_ORDER_ITEM, values, KEY_ID + "  = " + id, null);
        db.close();

    }

    public void updateSalesInvoiceDocNo(int number) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LAST_DOC_NO_SALES_INVOICE, number);

        db.update(TABLE_SETTINGS, values, KEY_ID + " = 1", null);
        db.close();

    }
    public void updateLastSyncTime() {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LAST_SYNC_TIME, Utility.getCurrentDate()+" "+Utility.getCurrentTime());

        db.update(TABLE_SETTINGS, values, null, null);
        db.close();

    }

    public void deleteCustomer(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER + " WHERE " + CUSTOMER_UNIQUE_NAME + " = '" + name + "';");

    }

    public void deleteAddress(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ADDRESS + " WHERE " + CUSTOMER_UNIQUE_NAME + " = '" + name + "';");

    }

    public String getCustomerNameByBarcode(String barcode) {
        String result = "";
        try {


            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT  " + CUSTOMER_NAME + " FROM " + TABLE_CUSTOMER + " where " + MOBILE_NO + " = '" + barcode + "' OR " + SERVER_CUSTOMER_ID + " = '" + barcode + "';";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getString(0);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            throw e;
        }
        return result;
    }

    public String getCustomerVATNo(String Customer) {
        String result = "";
        try {


            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT  " + TAX_ID + " FROM " + TABLE_CUSTOMER + " where " + CUSTOMER_NAME + " = '" + Customer + "';";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getString(0);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            throw e;
        }
        return result;
    }

    public String getStoredUrl() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + SETTING_URL + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = null;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String getSalesOrderName() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + KEY_NAMING_SERIES_SALES_ORDER + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = null;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String getPaymentName() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + KEY_NAMING_SERIES_PAYMENT + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = null;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }


    public String getSalesInvoiceName() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + KEY_NAMING_SERIES_SALES_INVOICE + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = null;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String getCusomerVisitName() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + KEY_NAMING_SERIES_CUSTOMER_VISIT + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = null;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }
    public int getSalesOrderDocNo() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + LAST_DOC_NO_SALES_ORDER + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        int result = 0;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public int getPaymentDocNo() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + LAST_DOC_NO_PAYMENT + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        int result = 0;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public int getSalesInvoiceDocNo() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + LAST_DOC_NO_SALES_INVOICE + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        int result = 0;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    /*
    public int getCustomerVisitDocNo() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + LAST_DOC_NO_CUSTOMER_VISIT + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        int result = 0;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return result;
    }
*/

    public String getWarehouse() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + WARE_HOUSE + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }
    public String getTransDefaultQty() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + TRANS_DEFAULT_QTY + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String getDeviceIdFromSetting() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + DEVICE_ID + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String getPAID_FROMFromSetting() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + SETTINGS_PAID_FROM + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String getPAID_TOFromSetting() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + SETTINGS_PAID_TO + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }


    public String getTaxRate() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + TAX_RATE + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }
    public int getSyncInterval() {
        int interval = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        final String MY_QUERY = "SELECT  "+ AUTO_SYNC_INTERVAL +" FROM " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                interval = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return interval;
    }

    public String getCompanyName() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + COMPANY_NAME + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String getCurrency() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + DEFAULT_CURRENCY + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public Float getCreditLimit() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + DEFAULT_CREDIT_LIMIT_FOR_NEW_CUSTOMER + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        Float result = 0.0f;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getFloat(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String getEmailId() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + EMAIL_ID + " from " + TABLE_CUSTOMER;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }


    public String getAccountHead() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + TAX_ACCOUNT_HEAD + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }


    public String getApiUsername() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + API_USERNAME + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }


    public String getApiPassword() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + API_PASSWORD + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }


    public String getDefaultModeOfPayment() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + SETTINGS_DEFAULT_PAYMENT_MODE + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String getPaidTo(String pto) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + PAYMENT_PAID_TO + " from " + TABLE_MODE_OF_PAYMENT+" where "+PAYMENT_MODE+" = '"+pto+"'";
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String getType(String pto) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + PAYMENT_TYPE + " from " + TABLE_MODE_OF_PAYMENT+" where "+PAYMENT_MODE+" = '"+pto+"'";
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String searchPass(String user_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + USER_USERNAME + "," + USER_PASSWORD_FOR_MOB_APPS + " from " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);
        String user, pass = "not found";
        if (cursor.moveToFirst()) {
            do {
                user = cursor.getString(0);
                Log.e("user", user + "kkkkk");
                if (user.equals(user_name)) {
                    pass = cursor.getString(1);
                    Log.e("passs", pass + "jjjj");
                    break;
                }
            } while (cursor.moveToNext());
        }
        return pass;
    }
    public String getLastSyncTime() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + LAST_SYNC_TIME + " from " + TABLE_SETTINGS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public void deleteOrder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SALES_ORDER_ITEM + " WHERE " + KEY_ID + " = " + id + ";");

    }

    public void deleteInvoice(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SALES_INVOICE_ITEM + " WHERE " + KEY_ID + " = " + id + ";");

    }

    public void deleteSalesOrder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SALES_ORDER + " WHERE " + KEY_ID + " = " + id + ";");

    }

    public void deleteSalesInvoice(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SALES_INVOICE + " WHERE " + KEY_ID + " = " + id + ";");

    }

    public void updateOrderItemDetails(String iddd, SelectedItemClass itemClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SALES_QTY, itemClass.getQty());
        values.put(SALES_STOCK_UOM, itemClass.getStock_uom());
        values.put(SALES_PRICE_LIST_RATE, itemClass.getPrice_list());
        values.put(SALES_RATE, itemClass.getRate());
        values.put(SALES_DISCOUNT_PERCENTAGE, itemClass.getDiscount_percentage());
        values.put(SALES_GROSS, itemClass.getGross());
        values.put(SALES_NET, itemClass.getNet());
        values.put(SALES_VAT, itemClass.getVat());
        values.put(SALES_TOTAL, itemClass.getTotal());

        db.update(TABLE_SALES_ORDER_ITEM, values, KEY_ID + " = " + iddd, null);
        db.close();


    }

    public void updateInvoiceItemDetails(String iddd, SalesInvoiceItemClass itemClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(INVOICE_ITEM_QTY, itemClass.getINVOICE_ITEM_QTY());
        values.put(INVOICE_ITEM_STOCK_UOM, itemClass.getINVOICE_ITEM_STOCK_UOM());
        values.put(INVOICE_ITEM_PRICELIST_RATE, itemClass.getINVOICE_ITEM_PRICELIST_RATE());
        values.put(INVOICE_ITEM_RATE, itemClass.getINVOICE_ITEM_RATE());
        values.put(INVOICE_ITEM_DISCOUNT_PERCENTAGE, itemClass.getINVOICE_ITEM_DISCOUNT_PERCENTAGE());
        values.put(SALES_GROSS, itemClass.getSALES_GROSS());
        values.put(SALES_NET, itemClass.getSALES_NET());
        values.put(SALES_VAT, itemClass.getSALES_VAT());
        values.put(SALES_TOTAL, itemClass.getSALES_TOTAL());


        db.update(TABLE_SALES_INVOICE_ITEM, values, KEY_ID + " = " + iddd, null);
        db.close();


    }


    public List<SalesOrderRaw_TokenResponse1> getSalesOrderForSync() {

        List<SalesOrderRaw_TokenResponse1> arrayList = new ArrayList<SalesOrderRaw_TokenResponse1>();


        String selectQuery = "SELECT  * FROM " + TABLE_SALES_ORDER + " where " + SYNC_STATUS + " = 0 ;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                SalesOrderRaw_TokenResponse1 orderClass = new SalesOrderRaw_TokenResponse1();

                orderClass.setId(cursor.getString(0));
                orderClass.setCreation(cursor.getString(1));
                orderClass.setOwner(cursor.getString(2));
                orderClass.setPo_no(cursor.getString(5));
                orderClass.setCustomer(cursor.getString(6));
                orderClass.setStatus(cursor.getString(8));
                orderClass.setCompany(cursor.getString(9));
                orderClass.setNaming_series(cursor.getString(10));
                orderClass.setDoc_no(cursor.getString(11));
                orderClass.setDelivery_date(cursor.getString(12));
                orderClass.setCurrency(cursor.getString(13));
                orderClass.setConversion_rate(cursor.getString(14));
                orderClass.setPrice_list_currency(cursor.getString(15));
                orderClass.setPlc_conversion_rate(cursor.getString(16));
                orderClass.setDevice_id(cursor.getString(17));

                orderClass.setItems(getOrderRawItem(cursor.getString(0)));


                arrayList.add(orderClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    private List<SalesOrderRawItemData1> getOrderRawItem(String last_id) {


        List<SalesOrderRawItemData1> arrayList = new ArrayList<SalesOrderRawItemData1>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_ORDER_ITEM + " where " + SALES_ORDER_LAST_ID + " = " + last_id + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SalesOrderRawItemData1 orderClass = new SalesOrderRawItemData1();

                orderClass.setQty(cursor.getString(2));
                orderClass.setWarehouse(cursor.getString(3));
                orderClass.setItem_name(cursor.getString(4));
                orderClass.setRate(cursor.getString(5));
                orderClass.setStock_uom(cursor.getString(6));
                orderClass.setItem_code(cursor.getString(7));
                orderClass.setPrice_list_rate(cursor.getString(8));
                orderClass.setDiscount_percentage(cursor.getString(9));
                orderClass.setTax_rate(cursor.getString(10));
                orderClass.setTax_amount(cursor.getString(11));
                orderClass.setTotal(cursor.getString(15));

                arrayList.add(orderClass);

            } while (cursor.moveToNext());
        }

        return arrayList;

    }
    public List<String> getSalesInvoicePendingAfterSync(String last_sync_time) {
        List<String> arrayList = new ArrayList<>();

        String selectQuery = "SELECT  "+SALES_INVOICE_DOC_NO+" FROM " + TABLE_SALES_INVOICE + " where " + SALES_INVOICE_SYNC_STATUS + " = 0 AND "+SALES_INVOICE_CREATION+" < '"+last_sync_time+"';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                arrayList.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public List<SalesInvoiceRaw1_TokenResponse> getSalesInvoiceForSync() {
        List<SalesInvoiceRaw1_TokenResponse> arrayList = new ArrayList<SalesInvoiceRaw1_TokenResponse>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_INVOICE + " where " + SALES_INVOICE_SYNC_STATUS + " = 0 ;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                SalesInvoiceRaw1_TokenResponse orderClass = new SalesInvoiceRaw1_TokenResponse();

                orderClass.setKey_id(cursor.getInt(0));
                orderClass.setCreation(cursor.getString(1));
                orderClass.setOwner(cursor.getString(2));
                orderClass.setDocstatus(cursor.getInt(3));
                orderClass.setPrice_list_currency(cursor.getString(4));
                orderClass.setCustomer(cursor.getString(5));
                orderClass.setCompany(cursor.getString(6));
                orderClass.setNaming_series(cursor.getString(7));
                orderClass.setCurrency(cursor.getString(8));
                orderClass.setDoc_no(cursor.getString(9));
                orderClass.setConversion_rate(cursor.getString(10));
                orderClass.setPlc_conversion_rate(cursor.getString(11));
                orderClass.setReturn_against(cursor.getString(12));
                orderClass.setIs_return(cursor.getInt(13));
                orderClass.setPosting_time(cursor.getString(14));
                orderClass.setPosting_date(cursor.getString(15));
                orderClass.setDevice_id(cursor.getString(17));
                orderClass.setSync_status(cursor.getInt(18));
                orderClass.setSALES_INVOICE_IS_POS(cursor.getInt(19));

                orderClass.setItems(getInvoiceRawItem(cursor.getInt(0)));

                if (cursor.getInt(19)==1){
                    orderClass.setPayments(getInvoiceRawItemPayments(cursor.getInt(0)));
                }

                arrayList.add(orderClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public List<CustomerVisitRaw_TokenResponse> getCustomerVisitLogForSync1() {
        List<CustomerVisitRaw_TokenResponse> arrayList = new ArrayList<CustomerVisitRaw_TokenResponse>();

        String selectQuery = "SELECT   "+
                        CUSTOMER_VISIT_REFERENCE +
                        ","+CUSTOMER_VISIT_NAMING_SERIES +
                        ","+CUSTOMER_VISIT_CREATION +
                        ","+CUSTOMER_VISIT_OWNER +
                        ","+CUSTOMER_VISIT_MODIFIED_BY +
                        ","+CUSTOMER_VISIT_LATITUDE +
                        ","+CUSTOMER_VISIT_DOC_STATUS +
                        ","+CUSTOMER_VISIT_COMMENTS +
                        ","+CUSTOMER_VISIT_CUSTOMER +
                        ","+CUSTOMER_VISIT_AMOUNT +
                        ","+CUSTOMER_VISIT_VISIT_RESULT +
                        ","+CUSTOMER_VISIT_MODIFIED +
                        ","+CUSTOMER_VISIT_LONGITUDE +
                        ","+CUSTOMER_VISIT_VISIT_DATE +
                        ","+CUSTOMER_VISIT_SALES_PERSON +

                        " FROM " + TABLE_CUSTOMER_VISIT_LOG + " where " + CUSTOMER_VISIT_SYNC_STATUS + " = 0 ;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                CustomerVisitRaw_TokenResponse visitClass = new CustomerVisitRaw_TokenResponse();

                visitClass.setReference(cursor.getString(cursor.getColumnIndex(CUSTOMER_VISIT_REFERENCE)));
                visitClass.setNaming_series(cursor.getString(1));
                visitClass.setCreation(cursor.getString(2));
                visitClass.setOwner(cursor.getString(3));
                visitClass.setModified_by(cursor.getString(4));
                visitClass.setLatitude(cursor.getString(5));
                visitClass.setDocstatus( Integer.parseInt(cursor.getString(6)));
                visitClass.setComments(cursor.getString(7));
                visitClass.setCustomer(cursor.getString(8));
                visitClass.setAmount(cursor.getFloat(9));
                visitClass.setVisit_result(cursor.getString(10));
                visitClass.setModified(cursor.getString(11));
                visitClass.setLongitude(cursor.getString(12));
                visitClass.setVisit_date(cursor.getString(13));
                visitClass.setSales_person(cursor.getString(14));
                arrayList.add(visitClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }
    public List<CustomerVisitLog> getCustomerVisitLogNoSaleForSync() {
        List<CustomerVisitLog> arrayList = new ArrayList<CustomerVisitLog>();

        String selectQuery = "SELECT   "+

                CUSTOMER_VISIT_REFERENCE +
                ","+CUSTOMER_VISIT_NAMING_SERIES +
                ","+CUSTOMER_VISIT_CREATION +
                ","+CUSTOMER_VISIT_OWNER +
                ","+CUSTOMER_VISIT_MODIFIED_BY +
                ","+CUSTOMER_VISIT_LATITUDE +
                ","+CUSTOMER_VISIT_DOC_STATUS +
                ","+CUSTOMER_VISIT_COMMENTS +
                ","+CUSTOMER_VISIT_CUSTOMER +
                ","+CUSTOMER_VISIT_AMOUNT +
                ","+CUSTOMER_VISIT_VISIT_RESULT +
                ","+CUSTOMER_VISIT_MODIFIED +
                ","+CUSTOMER_VISIT_LONGITUDE +
                ","+CUSTOMER_VISIT_VISIT_DATE +
                ","+CUSTOMER_VISIT_SALES_PERSON +
                ","+KEY_ID +
                " FROM " + TABLE_CUSTOMER_VISIT_LOG + " where " + CUSTOMER_VISIT_SYNC_STATUS + " = 0 AND "+CUSTOMER_VISIT_VISIT_RESULT+"='No Sale';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                CustomerVisitLog visitClass = new CustomerVisitLog();

                visitClass.setCUSTOMER_VISIT_REFERENCE(cursor.getString(cursor.getColumnIndex(CUSTOMER_VISIT_REFERENCE)));
                visitClass.setCUSTOMER_VISIT_NAMING_SERIES(cursor.getString(1));
                visitClass.setCUSTOMER_VISIT_CREATION(cursor.getString(2));
                visitClass.setCUSTOMER_VISIT_OWNER(cursor.getString(3));
                visitClass.setCUSTOMER_VISIT_MODIFIED_BY(cursor.getString(4));
                visitClass.setCUSTOMER_VISIT_LATITUDE(cursor.getString(5));
                visitClass.setCUSTOMER_VISIT_DOC_STATUS( Integer.parseInt(cursor.getString(6)));
                visitClass.setCUSTOMER_VISIT_COMMENTS(cursor.getString(7));
                visitClass.setCUSTOMER_VISIT_CUSTOMER(cursor.getString(8));
                visitClass.setCUSTOMER_VISIT_AMOUNT(cursor.getFloat(9));
                visitClass.setCUSTOMER_VISIT_VISIT_RESULT(cursor.getString(10));
                visitClass.setCUSTOMER_VISIT_MODIFIED(cursor.getString(11));
                visitClass.setCUSTOMER_VISIT_LONGITUDE(cursor.getString(12));
                visitClass.setCUSTOMER_VISIT_VISIT_DATE(cursor.getString(13));
                visitClass.setCUSTOMER_VISIT_SALES_PERSON(cursor.getString(14));
                visitClass.setCUSTOMER_VISIT_KEY_ID(cursor.getInt(15));
                arrayList.add(visitClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }
    public List<CustomerVisitLog> getCustomerVisitLogForSync() {
        List<CustomerVisitLog> arrayList = new ArrayList<CustomerVisitLog>();

        String selectQuery = "SELECT   "+

                CUSTOMER_VISIT_REFERENCE +
                ","+CUSTOMER_VISIT_NAMING_SERIES +
                ","+CUSTOMER_VISIT_CREATION +
                ","+CUSTOMER_VISIT_OWNER +
                ","+CUSTOMER_VISIT_MODIFIED_BY +
                ","+CUSTOMER_VISIT_LATITUDE +
                ","+CUSTOMER_VISIT_DOC_STATUS +
                ","+CUSTOMER_VISIT_COMMENTS +
                ","+CUSTOMER_VISIT_CUSTOMER +
                ","+CUSTOMER_VISIT_AMOUNT +
                ","+CUSTOMER_VISIT_VISIT_RESULT +
                ","+CUSTOMER_VISIT_MODIFIED +
                ","+CUSTOMER_VISIT_LONGITUDE +
                ","+CUSTOMER_VISIT_VISIT_DATE +
                ","+CUSTOMER_VISIT_SALES_PERSON +
                ","+KEY_ID +
                " FROM " + TABLE_CUSTOMER_VISIT_LOG + " where " + CUSTOMER_VISIT_SYNC_STATUS + " = 0 ;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                CustomerVisitLog visitClass = new CustomerVisitLog();

                visitClass.setCUSTOMER_VISIT_REFERENCE(cursor.getString(cursor.getColumnIndex(CUSTOMER_VISIT_REFERENCE)));
                visitClass.setCUSTOMER_VISIT_NAMING_SERIES(cursor.getString(1));
                visitClass.setCUSTOMER_VISIT_CREATION(cursor.getString(2));
                visitClass.setCUSTOMER_VISIT_OWNER(cursor.getString(3));
                visitClass.setCUSTOMER_VISIT_MODIFIED_BY(cursor.getString(4));
                visitClass.setCUSTOMER_VISIT_LATITUDE(cursor.getString(5));
                visitClass.setCUSTOMER_VISIT_DOC_STATUS( Integer.parseInt(cursor.getString(6)));
                visitClass.setCUSTOMER_VISIT_COMMENTS(cursor.getString(7));
                visitClass.setCUSTOMER_VISIT_CUSTOMER(cursor.getString(8));
                visitClass.setCUSTOMER_VISIT_AMOUNT(cursor.getFloat(9));
                visitClass.setCUSTOMER_VISIT_VISIT_RESULT(cursor.getString(10));
                visitClass.setCUSTOMER_VISIT_MODIFIED(cursor.getString(11));
                visitClass.setCUSTOMER_VISIT_LONGITUDE(cursor.getString(12));
                visitClass.setCUSTOMER_VISIT_VISIT_DATE(cursor.getString(13));
                visitClass.setCUSTOMER_VISIT_SALES_PERSON(cursor.getString(14));
                visitClass.setCUSTOMER_VISIT_KEY_ID(cursor.getInt(15));
                arrayList.add(visitClass);

            } while (cursor.moveToNext());
        }
        return arrayList;
    }
    public CustomerVisitLog getOneCustomerVisitLogForSync(String DocNo) {
        //List<CustomerVisitLog> arrayList = new ArrayList<CustomerVisitLog>();
        CustomerVisitLog visitClass=null;
        String selectQuery = "SELECT   "+

                CUSTOMER_VISIT_REFERENCE +
                ","+CUSTOMER_VISIT_NAMING_SERIES +
                ","+CUSTOMER_VISIT_CREATION +
                ","+CUSTOMER_VISIT_OWNER +
                ","+CUSTOMER_VISIT_MODIFIED_BY +
                ","+CUSTOMER_VISIT_LATITUDE +
                ","+CUSTOMER_VISIT_DOC_STATUS +
                ","+CUSTOMER_VISIT_COMMENTS +
                ","+CUSTOMER_VISIT_CUSTOMER +
                ","+CUSTOMER_VISIT_AMOUNT +
                ","+CUSTOMER_VISIT_VISIT_RESULT +
                ","+CUSTOMER_VISIT_MODIFIED +
                ","+CUSTOMER_VISIT_LONGITUDE +
                ","+CUSTOMER_VISIT_VISIT_DATE +
                ","+CUSTOMER_VISIT_SALES_PERSON +
                ","+KEY_ID +
                " FROM " + TABLE_CUSTOMER_VISIT_LOG + " where " + CUSTOMER_VISIT_SYNC_STATUS + " = 0 AND "+CUSTOMER_VISIT_REFERENCE+"='"+DocNo+"';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
           // do {

                visitClass = new CustomerVisitLog();

                visitClass.setCUSTOMER_VISIT_REFERENCE(cursor.getString(cursor.getColumnIndex(CUSTOMER_VISIT_REFERENCE)));
                visitClass.setCUSTOMER_VISIT_NAMING_SERIES(cursor.getString(1));
                visitClass.setCUSTOMER_VISIT_CREATION(cursor.getString(2));
                visitClass.setCUSTOMER_VISIT_OWNER(cursor.getString(3));
                visitClass.setCUSTOMER_VISIT_MODIFIED_BY(cursor.getString(4));
                visitClass.setCUSTOMER_VISIT_LATITUDE(cursor.getString(5));
                visitClass.setCUSTOMER_VISIT_DOC_STATUS( Integer.parseInt(cursor.getString(6)));
                visitClass.setCUSTOMER_VISIT_COMMENTS(cursor.getString(7));
                visitClass.setCUSTOMER_VISIT_CUSTOMER(cursor.getString(8));
                visitClass.setCUSTOMER_VISIT_AMOUNT(cursor.getFloat(9));
                visitClass.setCUSTOMER_VISIT_VISIT_RESULT(cursor.getString(10));
                visitClass.setCUSTOMER_VISIT_MODIFIED(cursor.getString(11));
                visitClass.setCUSTOMER_VISIT_LONGITUDE(cursor.getString(12));
                visitClass.setCUSTOMER_VISIT_VISIT_DATE(cursor.getString(13));
                visitClass.setCUSTOMER_VISIT_SALES_PERSON(cursor.getString(14));
                visitClass.setCUSTOMER_VISIT_KEY_ID(cursor.getInt(15));
                //arrayList.add(visitClass);

           // } while (cursor.moveToNext());
        }
        return visitClass;
    }


    private List<SalesInvoiceRaw1_ItemPayments> getInvoiceRawItemPayments(int id) {


        List<SalesInvoiceRaw1_ItemPayments> arrayList = new ArrayList<SalesInvoiceRaw1_ItemPayments>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_INVOICE_PAYMENT + " where " + SALES_INVOICE_ID + " = " + id + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SalesInvoiceRaw1_ItemPayments orderClass = new SalesInvoiceRaw1_ItemPayments();

                orderClass.setKEY_ID(cursor.getInt(0));
                orderClass.setAccount(cursor.getString(2));
                orderClass.setMode_of_payment(cursor.getString(3));
                orderClass.setBase_amount(cursor.getFloat(4));
                orderClass.setAmount(cursor.getFloat(5));
                orderClass.setType(cursor.getString(6));


                arrayList.add(orderClass);

            } while (cursor.moveToNext());
        }


        return arrayList;




    }

    private List<SalesInvoiceRaw1_ItemData> getInvoiceRawItem(int id) {

        List<SalesInvoiceRaw1_ItemData> arrayList = new ArrayList<SalesInvoiceRaw1_ItemData>();

        String selectQuery = "SELECT  * FROM " + TABLE_SALES_INVOICE_ITEM + " where " + SALES_INVOICE_ID + " = " + id + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SalesInvoiceRaw1_ItemData orderClass = new SalesInvoiceRaw1_ItemData();

                orderClass.setKEY_ID(cursor.getInt(0));
                orderClass.setSALES_INVOICE_ID(cursor.getInt(1));
                orderClass.setQty(cursor.getString(2));
                orderClass.setWarehouse(cursor.getString(3));
                orderClass.setItem_name(cursor.getString(4));
                orderClass.setRate(cursor.getString(5));
                orderClass.setStock_uom(cursor.getString(6));
                orderClass.setItem_code(cursor.getString(7));
                orderClass.setPrice_list_rate(cursor.getString(8));
                orderClass.setDiscount_percentage(cursor.getString(9));
                orderClass.setTax_rate(cursor.getString(10));
                orderClass.setTax_amount(cursor.getString(11));
                orderClass.setSales_order(cursor.getString(12));
                orderClass.setSo_detail(cursor.getString(13));
                orderClass.setGross(cursor.getFloat(14));
                orderClass.setNet(cursor.getFloat(15));
                orderClass.setVat(cursor.getFloat(16));
                orderClass.setTotal(cursor.getFloat(17));
                orderClass.setfreezer_(cursor.getString(cursor.getColumnIndex(INVOICE_ITEM_ASSET)));


                arrayList.add(orderClass);

            } while (cursor.moveToNext());
        }


        return arrayList;


    }


    public int getSalesOrderHighestID() {
        int last_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        final String MY_QUERY = "SELECT MAX(" + KEY_ID + ") FROM " + TABLE_SALES_ORDER;
        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                last_id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return last_id;
    }

    public int getSalesInvoiceHighestID() {
        int last_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        final String MY_QUERY = "SELECT MAX(" + KEY_ID + ") FROM " + TABLE_SALES_INVOICE;
        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                last_id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return last_id;
    }

    public int getCustomerVisitHighestID() {
        int last_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        final String MY_QUERY = "SELECT MAX(" + KEY_ID + ") FROM " + TABLE_CUSTOMER_ASSET_LIST;
        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                last_id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return last_id;
    }

    public String getItemCodeByBarcode(String result) {


        String last_id = "";
        SQLiteDatabase db = this.getWritableDatabase();
        final String MY_QUERY = "SELECT " + ITEM_PARENT + " FROM " + TABLE_ITEM_DETAIL + " where " + ALU2 + " = '" + result + "';";
        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                last_id = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        return last_id;
    }
    public Boolean IsValidCustomerAsset(String customer,String asset) {

        Boolean result=false;
        String last_id = "";
        SQLiteDatabase db = this.getWritableDatabase();
        final String MY_QUERY = "SELECT COUNT(*) FROM " + TABLE_CUSTOMER_ASSET_LIST + " where " + ASSET_CUSTOMER_NAME + " = '" + customer + "' AND "+ASSET_ASSET_ID+"='"+asset+"';";
        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                last_id = cursor.getString(0);
                if(Integer.parseInt(last_id)>0)
                    result=true;
            } while (cursor.moveToNext());
        }
        return result;
    }
    public Boolean IsInvoiceExists(String invNo) {

        Boolean result=false;
        String last_id = "";
        SQLiteDatabase db = this.getWritableDatabase();
        final String MY_QUERY = "SELECT COUNT(*) FROM " + TABLE_SALES_INVOICE + " where " + SALES_INVOICE_DOC_NO + " = '" + invNo + "' ;";
        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                last_id = cursor.getString(0);
                if(Integer.parseInt(last_id)>0)
                    result=true;
            } while (cursor.moveToNext());
        }
        return result;
    }
    public Boolean IsCustomerVisitSync() {

        Boolean result=false;
        String last_id = "";
        SQLiteDatabase db = this.getWritableDatabase();
        final String MY_QUERY = "SELECT COUNT(*) FROM " + TABLE_CUSTOMER_VISIT_LOG + " where " + CUSTOMER_VISIT_SYNC_STATUS +" = 0 ;";
        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                last_id = cursor.getString(0);
                if(Integer.parseInt(last_id)>0)
                    result=true;
            } while (cursor.moveToNext());
        }
        return result;
    }
    public Boolean IsCustomerVisitNoSaleSync() {

        Boolean result=false;
        String last_id = "";
        SQLiteDatabase db = this.getWritableDatabase();
        final String MY_QUERY = "SELECT COUNT(*) FROM " + TABLE_CUSTOMER_VISIT_LOG + " where " + CUSTOMER_VISIT_SYNC_STATUS + " = 0 AND "+CUSTOMER_VISIT_VISIT_RESULT+"='No Sale';";
        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                last_id = cursor.getString(0);
                if(Integer.parseInt(last_id)>0)
                    result=true;
            } while (cursor.moveToNext());
        }
        return result;
    }
    public Boolean IsSalesAvailableToSync() {

        Boolean result=false;
        String last_id = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String MY_QUERY = "SELECT  COUNT(*) FROM " + TABLE_SALES_INVOICE + " where " + SALES_INVOICE_SYNC_STATUS + " = 0 ;";
        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                last_id = cursor.getString(0);
                if(Integer.parseInt(last_id)>0)
                    result=true;
            } while (cursor.moveToNext());
        }
        return result;
    }
    public Boolean IsPaymentAvailableToSync() {

        Boolean result=false;
        String last_id = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String MY_QUERY = "SELECT  COUNT(*) FROM " + TABLE_PAYMENT + " where " + PAYMENT_SYNC_STATUS + " = 0 ;";
        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                last_id = cursor.getString(0);
                if(Integer.parseInt(last_id)>0)
                    result=true;
            } while (cursor.moveToNext());
        }
        return result;
    }


}
