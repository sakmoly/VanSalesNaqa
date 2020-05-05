package com.van.sale.vansale.adapter;//package com.example.user.alumini.Models;
//

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.van.sale.vansale.R;
import com.van.sale.vansale.Util.Utility;
import com.van.sale.vansale.model.ItemClass;
import com.van.sale.vansale.model.Payment;

import java.util.List;


/**
 * Created by LEN on 2/16/2018.
 */

public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Payment> itemList;
    private RecyclerView mRecyclerView;
    AlertDialog alertDialog;
    AlertDialog.Builder dialogBuilder;
    LayoutInflater inflater;

    CustomItemClickListener listener;

    public interface CustomItemClickListener {
        public void onItemClick(View v, int position);

    }

    public CollectionListAdapter(Context mContext, List<Payment> itemList, CustomItemClickListener listener) {
        this.itemList = itemList;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.collection_item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.payment_name.setText(itemList.get(position).getPAYMENT_CUSTOMER());
        holder.payment_date.setText(itemList.get(position).getPAYMENT_DOC_NO()+"  "+itemList.get(position).getPAYMENT_CREATION());
        holder.paid_amount.setText("SAR."+Utility.roundToTwoDecimal(itemList.get(position).getRECEIVED_AMOUNT()));
        if(String.valueOf(itemList.get(position).getPAYMENT_SYNC_STATUS()).equals("1"))
            holder.sync_done.setImageResource(R.drawable.ic_tick_done);


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

        private TextView payment_name, payment_date, paid_amount;
        private LinearLayout completelayout;
        private ImageView sync_done;

        public MyViewHolder(View view) {
            super(view);

            payment_name = (TextView) view.findViewById(R.id.payment_name);
            payment_date = (TextView) view.findViewById(R.id.payment_date);
            paid_amount = (TextView) view.findViewById(R.id.paid_amount);
            sync_done = (ImageView) view.findViewById(R.id.image_sync);

            completelayout = (LinearLayout) view.findViewById(R.id.completelayout);

        }


    }
}