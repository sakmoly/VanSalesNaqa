package com.van.sale.vansale.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.van.sale.vansale.Report_fragments.CollectionInfoFragment;
import com.van.sale.vansale.Report_fragments.InvoiceInfoFragment;
import com.van.sale.vansale.Report_fragments.SummaryFragment;

public class ReportPageAdaptor extends FragmentPagerAdapter {

    private int noOfTabs;
    private String from_date,to_date;
    Bundle arg;
    public ReportPageAdaptor(FragmentManager fm,int noOfTabs,String from_date,String to_date) {
        super(fm);
        this.noOfTabs=noOfTabs;
        arg=new Bundle();
        arg.putString("from_date",from_date);
        arg.putString("to_date",to_date);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                SummaryFragment summaryFragment=new SummaryFragment();
                summaryFragment.setArguments(arg);
                return summaryFragment;

            case 1:
                InvoiceInfoFragment invoiceInfoFragment=new InvoiceInfoFragment();
                invoiceInfoFragment.setArguments(arg);
                return invoiceInfoFragment;
            case 2:
                CollectionInfoFragment collectionInfoFragment=new CollectionInfoFragment();
                collectionInfoFragment.setArguments(arg);
                return collectionInfoFragment;
            default:
                    return null;
        }


    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
