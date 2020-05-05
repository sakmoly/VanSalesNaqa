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
import com.van.sale.vansale.model.CustomerList;

import java.util.List;


/**
 * Created by LEN on 2/16/2018.
 */

public class CustomerRecyclerViewAdapter extends RecyclerView.Adapter<CustomerRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<CustomerClass> customerLists;
    private RecyclerView mRecyclerView;


    public CustomerRecyclerViewAdapter(Context mContext, List<CustomerClass> customerLists) {
        this.customerLists = customerLists;
        this.mContext = mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.customer_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (customerLists.get(position).getSync_status().equals("0")){

            holder.rowFG.setBackgroundResource(R.color.Light_green);

        }

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
            rowFG = (LinearLayout)view.findViewById(R.id.rowFG);

        }


    }

   /* public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        userListData.clear();
        if (charText.length() == 0) {
            userListData.addAll(arraylist);
        } else {
            for (UserListData wp : arraylist) {
                try {

                   *//* if ((wp.getChapter_name().toLowerCase(Locale.getDefault()).trim().contains(charText.toLowerCase(Locale.getDefault()).trim()))||(wp.getFirstName().toLowerCase(Locale.getDefault()).trim().contains(charText.toLowerCase(Locale.getDefault()).trim()))||(wp.getCompany().toLowerCase(Locale.getDefault()).contains(charText))||(wp.getCategory().toLowerCase(Locale.getDefault()).contains(charText))||(wp.getPhoneNumber().toLowerCase().contains(charText))||(wp.getState().toLowerCase(Locale.getDefault()).contains(charText))) {
                        userListData.add(wp);
                    }*//*

                   if (wp.getFirstName().toLowerCase(Locale.getDefault()).contains(charText)){
                        userListData.add(wp);
                    }else if (wp.getLastName().toLowerCase(Locale.getDefault()).contains(charText.toLowerCase())){
                        userListData.add(wp);
                    }else if (wp.getCompany().toLowerCase(Locale.getDefault()).contains(charText)){
                        userListData.add(wp);
                    }else if (wp.getCategory().toLowerCase(Locale.getDefault()).contains(charText)){
                        userListData.add(wp);
                    }else if (wp.getChapter_name().toLowerCase(Locale.getDefault()).contains(charText)){
                        userListData.add(wp);
                    }else if (wp.getPhoneNumber().toLowerCase(Locale.getDefault()).contains(charText)){
                        userListData.add(wp);
                    }



                } catch (Exception e) {

                }
            }
        }
        notifyDataSetChanged();
    }*/


}
