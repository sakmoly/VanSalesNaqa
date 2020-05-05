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
import com.van.sale.vansale.model.SalesOrder;
import com.van.sale.vansale.model.SalesOrderList;

import java.util.List;


/**
 * Created by LEN on 2/16/2018.
 */

public class SalesOrderListAdapter extends RecyclerView.Adapter<SalesOrderListAdapter.MyViewHolder> {

    private Context mContext;
    private List<ItemClass> itemList;
    private RecyclerView mRecyclerView;
    AlertDialog alertDialog;
    AlertDialog.Builder dialogBuilder;
    LayoutInflater inflater;

    CustomItemClickListener listener;

    public interface CustomItemClickListener {
        public void onItemClick(View v, int position);

    }

    public SalesOrderListAdapter(Context mContext, List<ItemClass> itemList, CustomItemClickListener listener) {
        this.itemList = itemList;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.sales_item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

       holder.item_name.setText(itemList.get(position).getItem_name());
      holder.completelayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              listener.onItemClick(view,position);
          }
      });

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

        private TextView item_name;
        private LinearLayout completelayout;

        public MyViewHolder(View view) {
            super(view);

            item_name = (TextView) view.findViewById(R.id.item_name);
            completelayout = (LinearLayout) view.findViewById(R.id.completelayout);

        }


    }
}