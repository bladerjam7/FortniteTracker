package com.example.android.fortnitetracker;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.fortnitetracker.Activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StoreWidgetProvider extends AppWidgetProvider {

    //public static final String ACTION_STORE_LIST = "com.example.android.fortnitetracker.ACTION_STORE_LIST";

    public static ArrayList<String> storeList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_widget);


        /* Create an Intent to launch MainActivity when clicked */
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.image_news_widget, pendingIntent);

        getCurrentJSONnews(context, views, appWidgetId);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    private static void getCurrentJSONnews(final Context context, final RemoteViews views, final int appWidgetId) {
        String url = context.getString(R.string.news_url);
        RequestQueue mQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject entries = response.getJSONObject(context.getString(R.string.battleroyalenews));
                            JSONObject news = entries.getJSONObject(context.getString(R.string.news));
                            JSONArray motds = news.getJSONArray(context.getString(R.string.motds));

                            JSONObject firstEntry = motds.getJSONObject(0);

                            String newsTitle = firstEntry.getString(context.getString(R.string.title));
                            String newsBody = firstEntry.getString(context.getString(R.string.body));
                            String newsImageUrl = firstEntry.getString(context.getString(R.string.image));

                            views.setTextViewText(R.id.tv_news_title_widget, newsTitle);
                            views.setTextViewText(R.id.tv_news_body_widget, newsBody);


                            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

                            URL storeWidget;
                            storeWidget = new URL(newsImageUrl);
                            new MyNetworkTask(views, context, appWidgetManager, appWidgetId).execute(storeWidget);

                            appWidgetManager.updateAppWidget(appWidgetId, views);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
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

    private static class MyNetworkTask extends AsyncTask<URL, Void, Bitmap>{

        RemoteViews views;
        Context context;
        AppWidgetManager appWidgetManager;
        int appWidgetId;

        public MyNetworkTask(RemoteViews views, Context context, AppWidgetManager appWidgetManager, int appWidgetId){
            this.views = views;
            this.context = context;
            this.appWidgetManager = appWidgetManager;
            this.appWidgetId = appWidgetId;
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {

            Bitmap networkBitmap = null;

            URL networkUrl = urls[0]; //Load the first element
            try {
                networkBitmap = BitmapFactory.decodeStream(
                        networkUrl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return networkBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            views.setImageViewBitmap(R.id.image_news_widget, result);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }


}



