package com.example.android.fortnitetracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.fortnitetracker.Objects.ChallengeObjects;
import com.example.android.fortnitetracker.R;

import java.util.ArrayList;

public class ChallengeAdapter extends ArrayAdapter<ChallengeObjects> {
    ArrayList<ChallengeObjects> mData;
    Context mContext;
    int mResource;

    public ChallengeAdapter(ArrayList<ChallengeObjects> challengeObjectsList, int challenge_layout, Context context) {
        super(context, challenge_layout, challengeObjectsList);
        mData = challengeObjectsList;
        mContext = context;
        mResource = challenge_layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String total = mData.get(position).getTotal();
        String challenge = mData.get(position).getChallenge();
        String stars = mData.get(position).getStars();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTotal = (TextView) convertView.findViewById(R.id.tv_total);
        TextView tvChallenge = (TextView) convertView.findViewById(R.id.tv_challenge);
        TextView tvStars = (TextView) convertView.findViewById(R.id.tv_stars);
        final FrameLayout flFinished = (FrameLayout) convertView.findViewById(R.id.challenge_finished);
        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.challenge_checkbox);

        tvTotal.setText(mContext.getString(R.string.zero_slash, total));
        tvChallenge.setText(challenge);
        tvStars.setText(stars);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flFinished.getVisibility() == View.INVISIBLE)
                    flFinished.setVisibility(View.VISIBLE);
                else
                    flFinished.setVisibility(View.INVISIBLE);
            }
        });


        return convertView;
    }
}
