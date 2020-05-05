package com.van.sale.vansale.adapter;//package com.example.user.alumini.Models;
//

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.van.sale.vansale.R;
import com.van.sale.vansale.model.AddressClass;

import java.util.List;


/**
 * Created by LEN on 2/16/2018.
 */

public class CustomerAddressViewAdapter extends RecyclerView.Adapter<CustomerAddressViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<AddressClass> customerLists;
    private RecyclerView mRecyclerView;


    public CustomerAddressViewAdapter(Context mContext, List<AddressClass> customerLists) {
        this.customerLists = customerLists;
        this.mContext = mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.address_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(customerLists.get(position).getTitle());
        holder.address_name.setText(customerLists.get(position).getName());
        holder.address_company.setText(customerLists.get(position).getCompany());
        holder.address_city.setText(customerLists.get(position).getCity());
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

        private TextView title,address_name,address_company,address_city;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            address_name = (TextView) view.findViewById(R.id.address_name);
            address_company = (TextView) view.findViewById(R.id.address_company);
            address_city = (TextView) view.findViewById(R.id.address_city);
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
