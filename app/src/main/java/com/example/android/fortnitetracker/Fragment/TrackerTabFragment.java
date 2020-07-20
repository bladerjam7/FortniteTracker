package com.example.android.fortnitetracker.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.fortnitetracker.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;


public class TrackerTabFragment extends Fragment {

    TextView editTextView;
    Button searchButton;
    GridLayout statsLL;

    private RequestQueue mQueue;

    TextView soloTitle;
    TextView duoTitle;
    TextView squadTitle;

    Typeface fortniteFont;
    ImageView fortnitePicture;
    private String usernameExtract = "";

    //                              SOLO                                //


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tracker_tab, container, false);

        editTextView = (TextView) view.findViewById(R.id.search_EditText);


        searchButton =  (Button) view.findViewById(R.id.search_button);
        statsLL = (GridLayout) view.findViewById(R.id.display_stats);
        fortnitePicture = (ImageView) view.findViewById(R.id.iv_fortnite);


         // ************* Font Family Change ************* //

        soloTitle = (TextView) view.findViewById(R.id.tracker_solo);
        duoTitle = (TextView) view.findViewById(R.id.tracker_duo);
        squadTitle = (TextView) view.findViewById(R.id.tracker_squad);

        fortniteFont = Typeface.createFromAsset(getContext().getAssets(), getString(R.string.fortnite_font_resource));

        soloTitle.setTypeface(fortniteFont);
        duoTitle.setTypeface(fortniteFont);
        squadTitle.setTypeface(fortniteFont);



        // *********************************************** //

        mQueue = Volley.newRequestQueue(view.getContext());

        statsLL.setVisibility(View.INVISIBLE);
        if (savedInstanceState != null){
            usernameExtract = savedInstanceState.getString(getString(R.string.username));

            if (usernameExtract != "") {
                jsonParseId(usernameExtract);
                fortnitePicture.setVisibility(View.GONE);
                statsLL.setVisibility(View.VISIBLE);
            }
        }



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fortnitePicture.setVisibility(View.GONE);
                usernameExtract = editTextView.getText().toString();

                usernameExtract.replaceAll(" ", "&");
                
                closeKeyboard();

                jsonParseId(usernameExtract);
                statsLL.setVisibility(View.VISIBLE);



            }
        });



        return view;

    }

    private void closeKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    /*private void jsonParseUsername(String usernameExtract) {
        String url = getString(R.string.tracker_id_url) + usernameExtract;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String uid = response.getString(getString(R.string.uid));

                            jsonParseId(uid);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), getString(R.string.toast_no_username) , Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
        });
        mQueue.add(request);
    }*/

    private void jsonParseId(String uid) {
        String url = getString(R.string.tracker_id_url) + uid + "?" + getString(R.string.api_key);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject lifetime = response.getJSONObject(getString(R.string.lifetime));
                            JSONObject all = lifetime.getJSONObject(getString(R.string.all));

                            // *****************   Duo Stats  ***************************************

                            JSONObject defaultDuo = all.getJSONObject(getString(R.string.defaultduo));
                            int duoTop12 = defaultDuo.getInt(getString(R.string.placetop12));
                            int duoTop5 = defaultDuo.getInt(getString(R.string.placetop5));
                            int duoTop1 = defaultDuo.getInt(getString(R.string.placetop1));
                            int duoScore = defaultDuo.getInt(getString(R.string.lowercase_score));
                            int duoMatchesPlayed = defaultDuo.getInt(getString(R.string.lowercase_matchesplayed));
                            int duoKills = defaultDuo.getInt(getString(R.string.lowercase_kills));

                            // **********************************************************************

                            // *****************   Squad Stats  *************************************


                            JSONObject defaultSquad = all.getJSONObject(getString(R.string.defaultsquad));
                            int sqTop6 = defaultSquad.getInt(getString(R.string.placetop6));
                            int sqTop3 = defaultSquad.getInt(getString(R.string.placetop3));
                            int sqTop1 = defaultSquad.getInt(getString(R.string.placetop1));
                            int sqScore = defaultSquad.getInt(getString(R.string.lowercase_score));
                            int sqMatchesPlayed = defaultSquad.getInt(getString(R.string.lowercase_matchesplayed));
                            int sqKills = defaultSquad.getInt(getString(R.string.lowercase_kills));

                            // **********************************************************************

                            // *****************   Solo Stats  **************************************

                            JSONObject defaultSolo = all.getJSONObject(getString(R.string.defaultsolo));
                            int sTop25 = defaultSolo.getInt(getString(R.string.placetop25));
                            int sTop10 = defaultSolo.getInt(getString(R.string.placetop10));
                            int sTop1 = defaultSolo.getInt(getString(R.string.placetop1));
                            int sScore = defaultSolo.getInt(getString(R.string.lowercase_score));
                            int sMatchesPlayed = defaultSolo.getInt(getString(R.string.lowercase_matchesplayed));
                            int sKills = defaultSolo.getInt(getString(R.string.lowercase_kills));

                            // **********************************************************************

                            // Update Stats Display //

                            double killsVMatches;

                            TextView tvSoloMatchesPlayed = (TextView) getView().findViewById(R.id.matches_solo);
                            TextView tvSoloScore = (TextView) getView().findViewById(R.id.tv_score_solo);
                            TextView tvSoloKills = (TextView) getView().findViewById(R.id.tv_kills_solo);
                            TextView tvSoloKillsPerMatch = (TextView) getView().findViewById(R.id.tv_kills_v_match_solo);
                            TextView tvSoloTop1 = (TextView) getView().findViewById(R.id.tv_top1_solo);
                            TextView tvSoloTop10 = (TextView) getView().findViewById(R.id.tv_top10_solo);
                            TextView tvSoloTop25 = (TextView) getView().findViewById(R.id.tv_top25_solo);

                            tvSoloMatchesPlayed.setText(String.valueOf(getString(R.string.matches_tv, sMatchesPlayed)));
                            tvSoloScore.setText(String.valueOf(sScore));
                            tvSoloKills.setText(String.valueOf(sKills));
                            killsVMatches = round((double)sKills / (double) sMatchesPlayed, 2);
                            tvSoloKillsPerMatch.setText(String.valueOf(killsVMatches));
                            tvSoloTop1.setText(String.valueOf(sTop1));
                            tvSoloTop10.setText(String.valueOf(sTop10));
                            tvSoloTop25.setText(String.valueOf(sTop25));



                            //                              DUO                                //
                            TextView tvDuoMatchesPlayed = (TextView) getView().findViewById(R.id.matches_duo);
                            TextView tvDuoScore = (TextView) getView().findViewById(R.id.tv_score_duo);
                            TextView tvDuoKills = (TextView) getView().findViewById(R.id.tv_kills_duo);
                            TextView tvDuoKillsPerMatch = (TextView) getView().findViewById(R.id.tv_kills_v_match_duo);
                            TextView tvDuoTop1 = (TextView) getView().findViewById(R.id.tv_top1_duo);
                            TextView tvDuoTop5 = (TextView) getView().findViewById(R.id.tv_top5_duo);
                            TextView tvDuoTop12 = (TextView) getView().findViewById(R.id.tv_top12_duo);

                            tvDuoMatchesPlayed.setText(String.valueOf(getString(R.string.matches_tv, duoMatchesPlayed)));
                            tvDuoScore.setText(String.valueOf(duoScore));
                            tvDuoKills.setText(String.valueOf(duoKills));
                            killsVMatches = round((double)duoKills/(double)duoMatchesPlayed, 2);
                            tvDuoKillsPerMatch.setText(String.valueOf(killsVMatches));
                            tvDuoTop1.setText(String.valueOf(duoTop1));
                            tvDuoTop5.setText(String.valueOf(duoTop5));
                            tvDuoTop12.setText(String.valueOf(duoTop12));



                            //                              SQUAD                                //
                            TextView tvSquadMatchesPlayed = (TextView) getView().findViewById(R.id.matches_squad);
                            TextView tvSquadScore = (TextView) getView().findViewById(R.id.tv_score_squad);
                            TextView tvSquadKills = (TextView) getView().findViewById(R.id.tv_kills_squad);
                            TextView tvSquadKillsPerMatch = (TextView) getView().findViewById(R.id.tv_kills_v_match_squad);
                            TextView tvSquadTop1 = (TextView) getView().findViewById(R.id.tv_top1_squad);
                            TextView tvSquadTop3 = (TextView) getView().findViewById(R.id.tv_top3_squad);
                            TextView tvSquadTop6 = (TextView) getView().findViewById(R.id.tv_top6_squad);

                            tvSquadMatchesPlayed.setText(String.valueOf(getString(R.string.matches_tv,sqMatchesPlayed)));
                            tvSquadScore.setText(String.valueOf(sqScore));
                            tvSquadKills.setText(String.valueOf(sqKills));
                            killsVMatches = round((double)sqKills / (double) sqMatchesPlayed, 2);
                            tvSquadKillsPerMatch.setText(String.valueOf(killsVMatches));
                            tvSquadTop1.setText(String.valueOf(sqTop1));
                            tvSquadTop3.setText(String.valueOf(sqTop3));
                            tvSquadTop6.setText(String.valueOf(sqTop6));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
        }) {
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("x-rapidapi-host", "fortnite-api.p.rapidapi.com");
                headers.put("x-rapidapi-key", "1c10cbfcd7msh5e4421ba5337b7cp169105jsned1029083419");
                return headers;
            }*/
        };
        mQueue = Volley.newRequestQueue(getContext());
        mQueue.add(request);
    }

    private double round(double v, int i) {
        if (i < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(i, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.username), usernameExtract);
    }
}
