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
import com.example.pmsu_projekat.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleListAdapter extends ArrayAdapter<Article> {

    private Context mContext;
    int mResource;

    public ArticleListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Article> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getNaziv();
        Double price = getItem(position).getCena();
        String description = getItem(position).getOpis();

        //Article article = new Article(name, price, description);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.article_name);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.article_price);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.article_description);

        tvName.setText(name);
        tvPrice.setText(price.toString());
        tvDescription.setText(description);

        return convertView;
    }
}
