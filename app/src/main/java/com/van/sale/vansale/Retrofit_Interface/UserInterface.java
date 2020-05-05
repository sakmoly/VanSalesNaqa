package com.van.sale.vansale.Retrofit_Interface;

import com.google.gson.JsonObject;
import com.van.sale.vansale.Retrofit_Model.AddressSync_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.Address_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.Authentication_Response;
import com.van.sale.vansale.Retrofit_Model.CustomerAsset_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.CustomerSync_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.CustomerVisitRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.Customer_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.InvoiceNameIdItem_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.InvoiceNameId_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.ItemDetail_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.Item_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.ModeOfPaymentByName_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.ModePayment_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.PaymentRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.PaymentSync_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesInvoiceRaw_TokenResponse_1;
import com.van.sale.vansale.Retrofit_Model.SalesOrderRaw_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesOrderSync_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SalesOrder_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.SuccessAuth_TokenResponse;
import com.van.sale.vansale.Retrofit_Model.User_TokenResponse;
import com.van.sale.vansale.model.AddressPost;
import com.van.sale.vansale.model.CustomerPost;
import com.van.sale.vansale.model.Fields;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by maaz on 24/09/18.
 */

public interface UserInterface {

    public static final String API_BASE_URL = "http://23.239.220.206:99/";
    public static final String AUTHENTICATION_USERNAME = "Administrator";
    public static final String AUTHENTICATION_PASSWORD = "admin";


    @GET
    Call<User_TokenResponse> getUser(@Header("Cookie") String usernameAndPassword, @Url String url);

    @GET
    Call<Customer_TokenResponse> getCustomer(@Header("Cookie") String usernameAndPassword, @Url String url);

    @GET
    Call<CustomerAsset_TokenResponse> getCustomerAsset(@Header("Cookie") String usernameAndPassword, @Url String url);

    @GET
    Call<Address_TokenResponse> getAddress(@Header("Cookie") String usernameAndPassword, @Url String url);

    @GET
    Call<Item_TokenResponse> getItem(@Header("Cookie") String usernameAndPassword, @Url String url);

    @GET
    Call<ItemDetail_TokenResponse> getItemdetail(@Header("Cookie") String usernameAndPassword, @Url String url);

    @GET("api/resource/User")
    Call<SuccessAuth_TokenResponse> getSuccessAuth();

    @GET
    Call<InvoiceNameId_TokenResponse> getInvoiceName(@Header("Cookie") String usernameAndPassword, @Url String url);

    @GET
    Call<InvoiceNameIdItem_TokenResponse> getInvoiceNameDataList(@Header("Cookie") String usernameAndPassword, @Url String url);

    @GET
    Call<ModePayment_TokenResponse> getModeOfPayment(@Header("Cookie") String usernameAndPassword, @Url String url);

    @GET
    Call<ModeOfPaymentByName_TokenResponse> getModeOfPaymentByNAme(@Header("Cookie") String usernameAndPassword, @Url String url);






    @FormUrlEncoded
    @POST("api/method/login")
    Call<Authentication_Response> authentication(@Field("usr") String username, @Field("pwd") String password);

    @POST
    Call<CustomerSync_TokenResponse> customerSync(@Header("Cookie") String usernameAndPassword, @Url String url, @Body CustomerPost params);

    @POST
    Call<AddressSync_TokenResponse> addressSync(@Header("Cookie") String usernameAndPassword, @Url String url, @Body AddressPost params);

    @POST
    Call<SalesOrderSync_TokenResponse> salesOrderPost(@Header("Cookie") String usernameAndPassword, @Url String url, @Body SalesOrderRaw_TokenResponse salesOrderRaw_tokenResponse);

    @POST
    Call<SalesInvoiceRaw_TokenResponse> salesInvoicePost(@Header("Cookie") String usernameAndPassword, @Url String url, @Body SalesInvoiceRaw_TokenResponse salesInvoiceRaw_tokenResponse);

    @POST
    Call<CustomerVisitRaw_TokenResponse> CustomerVisitPost(@Header("Cookie") String usernameAndPassword, @Url String url, @Body CustomerVisitRaw_TokenResponse customerVisitRaw_TokenResponse);


    @POST
    Call<SalesInvoiceRaw_TokenResponse_1> salesInvoicePost1(@Header("Cookie") String usernameAndPassword, @Url String url, @Body SalesInvoiceRaw_TokenResponse_1 salesInvoiceRaw_tokenResponse);


    @POST
    Call<PaymentSync_TokenResponse> paymentPost(@Header("Cookie") String usernameAndPassword, @Url String url, @Body PaymentRaw_TokenResponse paymentRawTokenResponse);



}
