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
import com.example.pmsu_projekat.model.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order> {

    Context mContext;
    int mResource;

    public OrderListAdapter(@NonNull Context context, int resource, ArrayList<Order> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getProdavac().getNaziv();
        Double price = getItem(position).getCena();
        String date = getItem(position).getDatum();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.order_review_name);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.order_review_price);
        TextView tvDate = (TextView) convertView.findViewById(R.id.order_review_date);

        tvName.setText(name);
        tvPrice.setText(price.toString());
        tvDate.setText(date);

        return convertView;
    }
}
