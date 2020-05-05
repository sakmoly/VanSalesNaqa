package com.van.sale.vansale.adapter;//package com.example.user.alumini.Models;
//

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.van.sale.vansale.R;
import com.van.sale.vansale.model.Cart;
import com.van.sale.vansale.model.SalesOrder;
import com.van.sale.vansale.model.SalesOrderItemClass;
import com.van.sale.vansale.model.SelectedItemClass;

import java.util.List;


/**
 * Created by LEN on 2/16/2018.
 */

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<SalesOrderItemClass> cartList;
    private RecyclerView mRecyclerView;

    private CartClickListener cartClickListener;

    public interface CartClickListener {
        public void onItemClick(View v, int position);
        public void onItemDeleteClick(View v, int position);
    }


    public CartRecyclerViewAdapter(Context mContext, List<SalesOrderItemClass> cartList, CartClickListener cartClickListener) {
        this.cartList = cartList;
        this.mContext = mContext;
        this.cartClickListener = cartClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.cart_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.item_name_tv.setText(cartList.get(position).getSALES_ITEM_NAME());
        holder.item_code_tv.setText("Code : " + cartList.get(position).getSALES_ITEM_CODE());
        holder.item_qty_tv.setText("Qty : " + cartList.get(position).getSALES_QTY());
        holder.total_amount.setText("₹" + cartList.get(position).getSALES_TOTAL());
        holder.complete_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickListener.onItemClick(view,position);
            }
        });
        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cartClickListener.onItemDeleteClick(view,position);

            }
        });


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

        private TextView item_name_tv, item_code_tv, item_qty_tv, total_amount;
        private LinearLayout complete_click,delete_item;

        public MyViewHolder(View view) {
            super(view);

            item_name_tv = (TextView) view.findViewById(R.id.item_name_tv);
            item_code_tv = (TextView) view.findViewById(R.id.item_code_tv);
            item_qty_tv = (TextView) view.findViewById(R.id.item_qty_tv);
            total_amount = (TextView) view.findViewById(R.id.total_amount);
            complete_click = (LinearLayout)view.findViewById(R.id.complete_click);
            delete_item = (LinearLayout)view.findViewById(R.id.delete_item);

        }


    }
}