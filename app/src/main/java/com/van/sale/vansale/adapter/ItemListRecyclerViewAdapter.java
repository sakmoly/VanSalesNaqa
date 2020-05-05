package com.van.sale.vansale.adapter;//package com.example.user.alumini.Models;
//

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.van.sale.vansale.R;
import com.van.sale.vansale.model.ItemDetailClass;
import com.van.sale.vansale.model.SalesOrderItemClass;

import java.util.List;


/**
 * Created by LEN on 2/16/2018.
 */


public class ItemListRecyclerViewAdapter extends RecyclerView.Adapter<ItemListRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<ItemDetailClass> cartList;
    private RecyclerView mRecyclerView;
    private String barcode;


    public ItemListRecyclerViewAdapter(Context mContext, List<ItemDetailClass> cartList, String barcode) {
        this.cartList = cartList;
        this.mContext = mContext;
        this.barcode = barcode;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (cartList.get(position).getPrice()==null){
            holder.price_tv.setText("0.0");
        }else{
            holder.price_tv.setText(cartList.get(position).getPrice());
        }

        holder.bar_code_tv.setText(cartList.get(position).getAlu2());
        holder.uom_tv.setText(cartList.get(position).getUom());


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

        private TextView price_tv, bar_code_tv, uom_tv;


        public MyViewHolder(View view) {
            super(view);

            price_tv = (TextView) view.findViewById(R.id.price_tv);
            bar_code_tv = (TextView) view.findViewById(R.id.bar_code_tv);
            uom_tv = (TextView) view.findViewById(R.id.uom_tv);


        }


    }
}