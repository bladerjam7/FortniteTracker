package com.example.android.fortnitetracker.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.fortnitetracker.Adapters.StoreAdapter;
import com.example.android.fortnitetracker.R;
import com.example.android.fortnitetracker.StoreWidgetProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreTabFragment extends Fragment {

    GridView gridView;
    private RequestQueue mQueue;
    TextView dailyStore;
    Typeface fortniteFont;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_tab, container, false);

        gridView = view.findViewById(R.id.store_grid);
        dailyStore = view.findViewById(R.id.daily_store_header);

        fortniteFont = Typeface.createFromAsset(getContext().getAssets(), getString(R.string.fortnite_font_resource));
        dailyStore.setTypeface(fortniteFont);

        mQueue = Volley.newRequestQueue(view.getContext());

        jsonParseStore();

        gridView.setNumColumns(calculateNoOfColumns(getContext()));

        return view;

    }

    private void jsonParseStore() {
        String url = getString(R.string.store_url);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<String> storeItemList = new ArrayList<String>();
                        try {


                            JSONArray jsonArray = response.getJSONArray(getString(R.string.data));

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject items = jsonArray.getJSONObject(i);

                                JSONObject item = items.getJSONObject(getString(R.string.item));
                                JSONObject images = item.getJSONObject(getString(R.string.images));
                                String information = images.getString(getString(R.string.information));


                                storeItemList.add(information);
                            }

                            StoreAdapter adapter = new StoreAdapter(storeItemList, getContext());
                            gridView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
        });
        mQueue.add(request);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }
}
