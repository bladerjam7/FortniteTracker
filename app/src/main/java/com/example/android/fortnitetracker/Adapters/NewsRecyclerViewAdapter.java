package com.example.android.fortnitetracker.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.fortnitetracker.Objects.NewsObjects;
import com.example.android.fortnitetracker.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<NewsObjects> mData;
    private Context context;

    public NewsRecyclerViewAdapter(ArrayList<NewsObjects> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_adapter_layout,viewGroup, false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.mTitle.setText(mData.get(i).getTitle());
        myViewHolder.mBody.setText(mData.get(i).getBody());

        Picasso.get().load(mData.get(i).getImageUrl()).into(myViewHolder.mImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        TextView mBody;
        ImageView mImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.tv_news_title_layout);
            mBody = (TextView) itemView.findViewById(R.id.tv_news_body_layout);
            mImage = (ImageView) itemView.findViewById(R.id.image_news_layout);
        }


    }
}
