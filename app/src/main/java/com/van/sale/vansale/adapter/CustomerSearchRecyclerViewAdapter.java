package com.van.sale.vansale.adapter;//package com.example.user.alumini.Models;
//

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.van.sale.vansale.R;
import com.van.sale.vansale.model.CustomerClass;

import java.util.List;


/**
 * Created by LEN on 2/16/2018.
 */

public class CustomerSearchRecyclerViewAdapter extends RecyclerView.Adapter<CustomerSearchRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<CustomerClass> customerLists;
    private RecyclerView mRecyclerView;

    CustomerClickListener listener;

    public interface CustomerClickListener {
        public void onItemClick(View v, int position);
    }


    public CustomerSearchRecyclerViewAdapter(Context mContext, List<CustomerClass> customerLists, CustomerClickListener listener) {
        this.customerLists = customerLists;
        this.mContext = mContext;
        this.listener = listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.customer_name_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.rowFG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, position);
            }
        });
        holder.customer_name.setText(customerLists.get(position).getCustomer_name());
        holder.customer_email.setText("Cust ID: "+customerLists.get(position).getServer_customer_id());
        holder.customer_phone.setText(customerLists.get(position).getMobile_no());
        holder.asset.setText("Freezer#: "+customerLists.get(position).getAsset());
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

        private TextView customer_name, customer_email, customer_phone,asset;
        private LinearLayout rowFG;

        public MyViewHolder(View view) {
            super(view);

            customer_name = (TextView) view.findViewById(R.id.customer_name);
            customer_email = (TextView) view.findViewById(R.id.customer_email);
            customer_phone = (TextView) view.findViewById(R.id.customer_phone);
            asset=(TextView) view.findViewById(R.id.customer_asset);
            rowFG = (LinearLayout) view.findViewById(R.id.rowFG);

        }


    }

}
