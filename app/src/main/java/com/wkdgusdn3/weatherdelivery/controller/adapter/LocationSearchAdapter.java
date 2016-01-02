//package com.wkdgusdn3.weatherdelivery.controller.adapter;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.wkdgusdn3.weatherdelivery.model.Address;
//
//import java.util.ArrayList;
//
///**
// * Created by HyunWoo on 2016-01-02.
// */
//public class LocationSearchAdapter extends WhatsupBaseArrayAdapter<Address> {
//
//    ArrayList<Address> addresses = new ArrayList<Address>();
//
//
//    @Override
//    public int getCount() {
//        return addresses.size();
//    }
//
//    @Override
//    public Address getItem(int position) {
//        return addresses.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return addresses.get(position).hashCode();
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return curView;
//    }
//
//}
