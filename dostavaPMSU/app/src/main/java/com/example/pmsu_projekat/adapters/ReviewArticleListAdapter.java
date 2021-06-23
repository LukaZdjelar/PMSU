package com.example.pmsu_projekat.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pmsu_projekat.R;
import com.example.pmsu_projekat.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class ReviewArticleListAdapter extends ArrayAdapter<CartItem> {

    private Context mContext;
    int mResource;
    List<CartItem> cartItems = new ArrayList<>();

    public ReviewArticleListAdapter(Context context, int resource, List<CartItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String articleName = getItem(position).getArtikal().getNaziv();
        Double price = getItem(position).getArtikal().getCena();
        Integer ammount = getItem(position).getKolicina();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.order_review_article);
        TextView tvPriceAmmount = (TextView) convertView.findViewById(R.id.order_review_ammount_price);
        TextView tvPriceSum = (TextView) convertView.findViewById(R.id.order_review_ammount_sum);

        tvName.setText(articleName);
        tvPriceAmmount.setText(price.toString() + " * " + ammount.toString());
        tvPriceSum.setText(String.valueOf(price*ammount));

        return convertView;
    }
}
