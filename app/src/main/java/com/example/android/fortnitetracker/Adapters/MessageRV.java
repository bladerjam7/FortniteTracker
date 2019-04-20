package com.example.android.fortnitetracker.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.fortnitetracker.R;

public class MessageRV extends RecyclerView.ViewHolder {
    public TextView name, body;

    public MessageRV(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.message_username);
        body = itemView.findViewById(R.id.message_body);
    }
}
