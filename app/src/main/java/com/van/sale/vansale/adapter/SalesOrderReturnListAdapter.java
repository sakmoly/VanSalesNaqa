package com.van.sale.vansale.adapter;//package com.example.user.alumini.Models;
//

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.van.sale.vansale.R;
import com.van.sale.vansale.model.ItemClass;
import com.van.sale.vansale.model.SalesOrderItemClass;

import java.util.List;


/**
 * Created by LEN on 2/16/2018.
 */

public class SalesOrderReturnListAdapter extends RecyclerView.Adapter<SalesOrderReturnListAdapter.MyViewHolder> {

    private Context mContext;
    private List<SalesOrderItemClass> itemList;
    private RecyclerView mRecyclerView;
    AlertDialog alertDialog;
    AlertDialog.Builder dialogBuilder;
    LayoutInflater inflater;

    CustomItemClickListener listener;

    public interface CustomItemClickListener {
        public void onItemClick(View v, int position);

    }

    public SalesOrderReturnListAdapter(Context mContext, List<SalesOrderItemClass> itemList,CustomItemClickListener listener) {
        this.itemList = itemList;
        this.mContext = mContext;
        this.listener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.return_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.item_name.setText(itemList.get(position).getSALES_ITEM_NAME());
        holder.item_code_tv.setText("Code : " +itemList.get(position).getSALES_ITEM_CODE());
        holder.item_qty_tv.setText("Qty : " +String.valueOf(itemList.get(position).getSALES_QTY()));
        holder.total_amount.setText("₹" + itemList.get(position).getSALES_TOTAL());

        holder.completelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onItemClick(view,position);

            }
        });



       // holder.item_.setText(itemList.get(position).getSALES_ITEM_NAME());

      /*  holder.item_name.setText(itemList.get(position).getItem_name());
        holder.completelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, position);
            }
        });*/


    }


    @Override
    public int getItemCount() {

        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView item_name,item_code_tv,item_qty_tv,total_amount;
        private LinearLayout completelayout;

        public MyViewHolder(View view) {
            super(view);

            item_name = (TextView) view.findViewById(R.id.item_name_tv);
            item_code_tv = (TextView) view.findViewById(R.id.item_code_tv);
            item_qty_tv = (TextView) view.findViewById(R.id.item_qty_tv);
            total_amount = (TextView) view.findViewById(R.id.total_amount);
            completelayout = (LinearLayout) view.findViewById(R.id.complete_click);


        }


    }
}