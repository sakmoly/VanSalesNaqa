package com.van.sale.vansale.adapter;//package com.example.user.alumini.Models;
//

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.van.sale.vansale.R;
import com.van.sale.vansale.activity.SalesInvoiceDetailActivity;
import com.van.sale.vansale.model.SalesInvoiceClass;
import com.van.sale.vansale.model.SalesOrderClass;

import java.util.List;


/**
 * Created by LEN on 2/16/2018.
 */

public class SalesInvoiceRecyclerViewAdapter extends RecyclerView.Adapter<SalesInvoiceRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<SalesInvoiceClass> customerLists;
    private RecyclerView mRecyclerView;


    public SalesInvoiceRecyclerViewAdapter(Context mContext, List<SalesInvoiceClass> customerLists) {
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

        holder.customer_name.setText(customerLists.get(position).getSALES_INVOICE_CUSTOMER());
        holder.customer_email.setText("Invoice#:"+customerLists.get(position).getSALES_INVOICE_DOC_NO()+"  Date:"+customerLists.get(position).getSALES_INVOICE_CREATION());
        holder.customer_phone.setText("Qty : "+customerLists.get(position).getQTY());
        if(String.valueOf(customerLists.get(position).getSALES_INVOICE_SYNC_STATUS()).equals("1"))
            holder.sync_done.setImageResource(R.drawable.ic_tick_done);

        holder.rowFG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(mContext,SalesInvoiceDetailActivity.class);
                in.putExtra("INVOICE_NO",String.valueOf(customerLists.get(position).getSALES_INVOICE_DOC_NO()));
                in.putExtra("INVOICE_CREATION",customerLists.get(position).getSALES_INVOICE_CREATION());
                in.putExtra("INVOICE_CUSTOMER",customerLists.get(position).getSALES_INVOICE_CUSTOMER());
                in.putExtra("INVOICE_IS_RETURN",String.valueOf(customerLists.get(position).getSALES_INVOICE_IS_RETURN()));
                in.putExtra("INVOICE_RETURN_AGAINST",String.valueOf(customerLists.get(position).getSALES_INVOICE_RETURN_AGAINST()));
                in.putExtra("INVOICE_POSTING_TIME",customerLists.get(position).getSALES_INVOICE_POSTING_TIME());
                in.putExtra("INVOICE_POSTING_DATE",customerLists.get(position).getSALES_INVOICE_POSTING_DATE());
                in.putExtra("INVOICE_CUSTOMER_ID",String.valueOf(customerLists.get(position).getKEY_ID()));
                in.putExtra("INVOICE_SYNC_STATUS",String.valueOf(customerLists.get(position).getSALES_INVOICE_SYNC_STATUS()));
                in.putExtra("INVOICE_IS_POS",String.valueOf(customerLists.get(position).getSALES_INVOICE_IS_POS()));
                mContext.startActivity(in);

            }
        });




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
        private ImageView sync_done;
        private LinearLayout rowFG;

        public MyViewHolder(View view) {
            super(view);

            customer_name = (TextView) view.findViewById(R.id.customer_name);
            customer_email = (TextView) view.findViewById(R.id.customer_email);
            customer_phone = (TextView) view.findViewById(R.id.customer_phone);
            sync_done = (ImageView) view.findViewById(R.id.image_sync);
            rowFG = (LinearLayout)view.findViewById(R.id.rowFG);

        }


    }
}