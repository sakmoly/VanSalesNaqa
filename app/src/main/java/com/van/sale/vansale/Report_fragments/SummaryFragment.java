package com.van.sale.vansale.Report_fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.van.sale.vansale.Database.DatabaseHandler;
import com.van.sale.vansale.R;
import com.van.sale.vansale.Report_model.Daily_Sales_Collection_Report;
import com.van.sale.vansale.Util.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {

    private DatabaseHandler db;
    String date_from,date_to;
    TextView tv_sales_amt,tv_vat,tv_total,tv_pending,tv_received,tv_total_collection;
    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       String from_date,to_date;
        if (getArguments() != null) {
            from_date = getArguments().getString("from_date");
            to_date = getArguments().getString("to_date");
        }

        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_sales_amt=(TextView)getView().findViewById(R.id.TV_sales_amount);
        tv_vat=(TextView)getView().findViewById(R.id.TV_vat);
        tv_total=(TextView)getView().findViewById(R.id.TV_total);
        tv_pending=(TextView)getView().findViewById(R.id.TV_pending);
        tv_received=(TextView)getView().findViewById(R.id.TV_received);
        tv_total_collection=(TextView)getView().findViewById(R.id.TV_total_collection);
        db=new DatabaseHandler(getContext());
        Daily_Sales_Collection_Report report = db.getDailySalesCollectonReport(date_from, date_to);

        if(report!=null) {

            tv_sales_amt.setText(Utility.roundToTwoDecimal(report.getSales_amount().toString()));
            tv_vat.setText(Utility.roundToTwoDecimal(report.getVat_amount().toString()));
            tv_total.setText(Utility.roundToTwoDecimal(report.getSales_total_with_vat().toString()));
            tv_pending.setText(Utility.roundToTwoDecimal(report.getPending_collection().toString()));
            tv_received.setText(Utility.roundToTwoDecimal(report.getReceved_amount().toString()));
            tv_total_collection.setText(Utility.roundToTwoDecimal(report.getTotal_collection().toString()));


        }


    }


}
