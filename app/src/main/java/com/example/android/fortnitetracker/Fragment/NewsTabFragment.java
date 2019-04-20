package com.example.android.fortnitetracker.Fragment;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.fortnitetracker.Adapters.NewsRecyclerViewAdapter;
import com.example.android.fortnitetracker.Objects.NewsObjects;
import com.example.android.fortnitetracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NewsTabFragment extends Fragment {

    private RequestQueue mQueue;
    TextView newsTitle;

    TextView newTitle;
    Typeface fortniteFont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_tab, container, false);

        newsTitle = (TextView) view.findViewById(R.id.news_header);
        mQueue = Volley.newRequestQueue(view.getContext());

        newsTitle = view.findViewById(R.id.news_header);

        fortniteFont = Typeface.createFromAsset(getContext().getAssets(), getString(R.string.fortnite_font_resource));
        newsTitle.setTypeface(fortniteFont);


        requestNewsJSON(view.getContext());


        return view;
    }

    private void requestNewsJSON(final Context mContext) {
        String newsURL = getString(R.string.news_url);
        final ArrayList<NewsObjects> newsList = new ArrayList<>();

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newsURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray entries = response.getJSONArray(getString(R.string.entries));

                            for (int i = 0; i < entries.length(); i++){
                                JSONObject items = entries.getJSONObject(i);

                                String title = items.getString(getString(R.string.title));
                                String body = items.getString(getString(R.string.body));
                                String image = items.getString(getString(R.string.image));

                                newsList.add(new NewsObjects(title, body, image));
                            }

                            storeInView(newsList, mContext);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    private void storeInView(ArrayList<NewsObjects> newsList, Context mContext) {
                        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.news_rv);
                        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(newsList, mContext);
                        rv.setAdapter(adapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        rv.setLayoutManager(layoutManager);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mQueue.add(request);

    }



}
