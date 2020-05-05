package com.van.sale.vansale.adapter;//package com.example.user.alumini.Models;
//

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.van.sale.vansale.R;
import com.van.sale.vansale.model.Cart;
import com.van.sale.vansale.model.SalesOrderItemClass;

import java.util.List;


/**
 * Created by LEN on 2/16/2018.
 */

public class SelectedOrderRecyclerViewAdapter extends RecyclerView.Adapter<SelectedOrderRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<SalesOrderItemClass> cartList;
    private RecyclerView mRecyclerView;


    public SelectedOrderRecyclerViewAdapter(Context mContext, List<SalesOrderItemClass> cartList) {
        this.cartList = cartList;
        this.mContext = mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.selected_order_list, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.item_name_tv.setText(cartList.get(position).getSALES_ITEM_NAME());
        holder.item_code_tv.setText("Code : "+cartList.get(position).getSALES_ITEM_CODE());
        holder.item_qty_tv.setText("Qty : "+cartList.get(position).getSALES_QTY());
        holder.total_amount.setText("₹"+cartList.get(position).getSALES_TOTAL());

    }


    @Override
    public int getItemCount() {

        return cartList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView item_name_tv, item_code_tv, item_qty_tv,total_amount;

        public MyViewHolder(View view) {
            super(view);

            item_name_tv = (TextView) view.findViewById(R.id.item_name_tv);
            item_code_tv = (TextView) view.findViewById(R.id.item_code_tv);
            item_qty_tv = (TextView) view.findViewById(R.id.item_qty_tv);
            total_amount = (TextView) view.findViewById(R.id.total_amount);
        }


    }
}