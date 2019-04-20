package com.example.android.fortnitetracker.Fragment;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.fortnitetracker.Adapters.ChallengeAdapter;
import com.example.android.fortnitetracker.Objects.ChallengeObjects;
import com.example.android.fortnitetracker.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;


public class ChallengeTabFragment extends Fragment
{

    private RequestQueue mQueue;
    LinearLayout bigBanner;
    LinearLayout battleStarBanner;
    View backgroundTint;
    TextView weekBanner;

    FloatingActionButton fabWeeks;
    LinearLayout weekMenuFrame;

    /* Weeks */
    TextView tvWeek1;
    TextView tvWeek2;
    TextView tvWeek3;
    TextView tvWeek4;
    TextView tvWeek5;
    TextView tvWeek6;
    TextView tvWeek7;
    TextView tvWeek8;
    TextView tvWeek9;
    TextView tvWeek10;


    private ArrayList<String> weekList= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int orientation = getResources().getConfiguration().orientation;
        View view = inflater.inflate(R.layout.challenge_tab, container, false);
        mQueue = Volley.newRequestQueue(view.getContext());

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {


            backgroundTint = (View) view.findViewById(R.id.background_tint);
            bigBanner = (LinearLayout) view.findViewById(R.id.intro_challenge_banner);
            weekBanner = (TextView) view.findViewById(R.id.week_banner);
            battleStarBanner = (LinearLayout) view.findViewById(R.id.battle_star_image);
            jsonParseChallenges(view, bigBanner, backgroundTint, weekBanner, battleStarBanner);

            return view;


        } else {


            weekMenuFrame = (LinearLayout) view.findViewById(R.id.week_menu_frame);
            fabWeeks = (FloatingActionButton) view.findViewById(R.id.week_fab);
            backgroundTint = (View) view.findViewById(R.id.background_tint_challenge);
            bigBanner = (LinearLayout) view.findViewById(R.id.intro_challenge_banner);

            /* Declaring all the weeks */
            tvWeek1 = (TextView) view.findViewById(R.id.tv_week_1);
            tvWeek2 = (TextView) view.findViewById(R.id.tv_week_2);
            tvWeek3 = (TextView) view.findViewById(R.id.tv_week_3);
            tvWeek4 = (TextView) view.findViewById(R.id.tv_week_4);
            tvWeek5 = (TextView) view.findViewById(R.id.tv_week_5);
            tvWeek6 = (TextView) view.findViewById(R.id.tv_week_6);
            tvWeek7 = (TextView) view.findViewById(R.id.tv_week_7);
            tvWeek8 = (TextView) view.findViewById(R.id.tv_week_8);
            tvWeek9 = (TextView) view.findViewById(R.id.tv_week_9);
            tvWeek10 = (TextView) view.findViewById(R.id.tv_week_10);


            fabWeeks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(weekMenuFrame.getVisibility() == View.GONE){
                        final String week = getString(R.string.week);

                        /* OPEN MENU */
                        weekMenuFrame.setVisibility(View.VISIBLE);
                        backgroundTint.setVisibility(View.VISIBLE);

                        Animation animationOpen = new TranslateAnimation(750, Animation.ABSOLUTE, 0,0 );
                        animationOpen.setDuration(400);
                        animationOpen.setFillAfter(true);

                        weekMenuFrame.setAnimation(animationOpen);

                        /* Set listeners for week menu*/
                        tvWeek1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String weekSelected = week + getString(R.string.number_1);
                                bigBanner.setVisibility(View.GONE);
                                jsonRequestWeek(weekSelected);

                                closeMenu();
                            }
                        });
                        tvWeek2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String weekSelected = week + getString(R.string.number_2);
                                bigBanner.setVisibility(View.GONE);
                                jsonRequestWeek(weekSelected);

                                closeMenu();
                            }
                        });
                        tvWeek3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String weekSelected = week + getString(R.string.number_3);
                                bigBanner.setVisibility(View.GONE);
                                jsonRequestWeek(weekSelected);

                                closeMenu();
                            }
                        });
                        tvWeek4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String weekSelected = week + getString(R.string.number_4);
                                bigBanner.setVisibility(View.GONE);
                                jsonRequestWeek(weekSelected);

                                closeMenu();
                            }
                        });
                        tvWeek5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String weekSelected = week + getString(R.string.number_5);
                                bigBanner.setVisibility(View.GONE);
                                jsonRequestWeek(weekSelected);

                                closeMenu();
                            }
                        });
                        tvWeek6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String weekSelected = week + getString(R.string.number_6);
                                bigBanner.setVisibility(View.GONE);
                                jsonRequestWeek(weekSelected);

                                closeMenu();
                            }
                        });
                        tvWeek7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String weekSelected = week + getString(R.string.number_7);
                                bigBanner.setVisibility(View.GONE);
                                jsonRequestWeek(weekSelected);

                                closeMenu();
                            }
                        });
                        tvWeek8.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String weekSelected = week + getString(R.string.number_8);
                                bigBanner.setVisibility(View.GONE);
                                jsonRequestWeek(weekSelected);

                                closeMenu();
                            }
                        });
                        tvWeek9.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String weekSelected = week + getString(R.string.number_9);
                                bigBanner.setVisibility(View.GONE);
                                jsonRequestWeek(weekSelected);

                                closeMenu();
                            }
                        });
                        tvWeek10.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String weekSelected = week + getString(R.string.number_10);
                                bigBanner.setVisibility(View.GONE);
                                jsonRequestWeek(weekSelected);

                                closeMenu();
                            }
                        });


                    } else {

                        /* CLOSE MENU */
                        closeMenu();

                    }
                }
            });
            return view;

        }
    }

    private void closeMenu() {
        backgroundTint.setVisibility(View.GONE);
        Animation animationClose = new TranslateAnimation(Animation.ABSOLUTE, 750, 0, 0);
        animationClose.setDuration(400);
        animationClose.setFillAfter(true);

        weekMenuFrame.setAnimation(animationClose);

        weekMenuFrame.setVisibility(View.GONE);
    }

    private void jsonRequestWeek(final String weekSelected) {
        String url = getString(R.string.challenge_url);


        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<ChallengeObjects> challengeObjectsList = new ArrayList<>();
                            JSONObject challenges = response.getJSONObject("challenges");
                            JSONArray week = challenges.getJSONArray(weekSelected);


                            if (week.length() == 0){
                                Toast.makeText(getContext(),
                                        weekSelected + getString(R.string.toast_not_available),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                for (int i = 0; i < week.length(); i++){
                                    JSONObject weekArray = week.getJSONObject(i);

                                    String challenge = weekArray.getString("challenge");
                                    int total = weekArray.getInt("total");
                                    int stars = weekArray.getInt("stars");

                                    challengeObjectsList.add(new ChallengeObjects(challenge, String.valueOf(total), String.valueOf(stars)));


                                }

                                ListView listView = (ListView) getView().findViewById(R.id.challenge_listView);
                                ChallengeAdapter adapter = new ChallengeAdapter(challengeObjectsList, R.layout.challenge_adapter_layout, getContext());
                                listView.setAdapter(adapter);
                            }
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


    private void jsonParseChallenges(final View view, final LinearLayout bigBanner, final View backgroundTint, final TextView weekBanner, final LinearLayout battleStarBanner) {
        String url = "https://fortnite-public-api.theapinetwork.com/prod09/challenges/get?season=current";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        FabSpeedDial fabSpeedDial = (FabSpeedDial) getView().findViewById(R.id.fab_speed_dial);
                        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
                            @Override
                            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                                backgroundTint.setVisibility(View.VISIBLE);
                                return true;
                            }

                            @SuppressLint("SetTextI18n")
                            @Override
                            public boolean onMenuItemSelected(MenuItem menuItem) {
                                try {
                                    ArrayList<ChallengeObjects> challengeObjectsList = new ArrayList<>();
                                    JSONObject challenges = response.getJSONObject("challenges");

                                    if (bigBanner.getVisibility() ==  View.VISIBLE){
                                        bigBanner.setVisibility(View.INVISIBLE);
                                    }

                                    backgroundTint.setVisibility(View.GONE);
                                    battleStarBanner.setVisibility(View.VISIBLE);

                                    String weekNumber = (String.valueOf(menuItem)).replaceAll("[^0-9]", "");
                                    String jsonWeekName = "week" + weekNumber;

                                    weekBanner.setText(getString(R.string.week_challeges_banner, weekNumber));

                                    JSONArray week = challenges.getJSONArray(jsonWeekName);

                                    if (week.length() == 0){
                                        Toast.makeText(getContext(),
                                                menuItem + getString(R.string.toast_not_available),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        for (int i = 0; i < week.length(); i++){
                                            JSONObject weekArray = week.getJSONObject(i);

                                            String challenge = weekArray.getString("challenge");
                                            int total = weekArray.getInt("total");
                                            int stars = weekArray.getInt("stars");

                                            challengeObjectsList.add(new ChallengeObjects(challenge, String.valueOf(total), String.valueOf(stars)));


                                        }

                                        ListView listView = (ListView) getView().findViewById(R.id.challenge_listView);
                                        ChallengeAdapter adapter = new ChallengeAdapter(challengeObjectsList, R.layout.challenge_adapter_layout, getContext());
                                        listView.setAdapter(adapter);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                return true;
                            }

                            @Override
                            public void onMenuClosed() {
                                backgroundTint.setVisibility(View.GONE);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }

}
