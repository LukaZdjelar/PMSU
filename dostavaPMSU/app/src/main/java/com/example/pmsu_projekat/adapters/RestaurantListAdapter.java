package com.example.pmsu_projekat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.Seller;

import java.util.ArrayList;

public class RestaurantListAdapter extends ArrayAdapter<Seller> {

    private Context mContext;
    int mResource;

    public RestaurantListAdapter(Context context, int resource, ArrayList<Seller> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String restaurant_name = getItem(position).getNaziv();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.restaurant_name);
        tv.setText(restaurant_name);

        return convertView;
    }
}
