package com.example.android.fortnitetracker.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoreAdapter extends BaseAdapter {
    ArrayList<String> mItemStore;
    Context mContext;

    public StoreAdapter(ArrayList<String> mItemStore, Context mContext){
        this.mItemStore = mItemStore;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mItemStore.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemStore.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image;
        image = new ImageView(mContext);
        Picasso.get().load(mItemStore.get(position)).into(image, new com.squareup.picasso.Callback(){
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {

            }
        });

        return image;
    }
}
