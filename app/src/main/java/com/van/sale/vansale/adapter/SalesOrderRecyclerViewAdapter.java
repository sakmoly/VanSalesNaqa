package com.van.sale.vansale.adapter;//package com.example.user.alumini.Models;
//

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.van.sale.vansale.R;
import com.van.sale.vansale.model.CustomerList;
import com.van.sale.vansale.model.SalesOrder;
import com.van.sale.vansale.model.SalesOrderClass;

import java.util.List;


/**
 * Created by LEN on 2/16/2018.
 */

public class SalesOrderRecyclerViewAdapter extends RecyclerView.Adapter<SalesOrderRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<SalesOrderClass> customerLists;
    private RecyclerView mRecyclerView;


    public SalesOrderRecyclerViewAdapter(Context mContext, List<SalesOrderClass> customerLists) {
        this.customerLists = customerLists;
        this.mContext = mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.sales_order_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.customer_name.setText(customerLists.get(position).getSALES_ORDER_CUSTOMER());
        holder.customer_email.setText(customerLists.get(position).getSALES_ORDER_DELIVERY_DATE());
        holder.customer_phone.setText("Qty : "+customerLists.get(position).getTOTAL_QUANDITY());
    }


    @Override
    public int getItemCount() {

        return customerLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView customer_name, customer_email, customer_phone;

        public MyViewHolder(View view) {
            super(view);

            customer_name = (TextView) view.findViewById(R.id.customer_name);
            customer_email = (TextView) view.findViewById(R.id.customer_email);
            customer_phone = (TextView) view.findViewById(R.id.customer_phone);
        }


    }
}