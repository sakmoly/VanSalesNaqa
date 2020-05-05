package com.van.sale.vansale.Report_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.van.sale.vansale.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionInfoFragment extends Fragment {


    public CollectionInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String from_date,to_date;
        if (getArguments() != null) {
            from_date = getArguments().getString("from_date");
            to_date = getArguments().getString("to_date");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection_info, container, false);
    }

}
