package com.example.android.fortnitetracker.Activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.Toast;


import com.example.android.fortnitetracker.Adapters.MessageRV;
import com.example.android.fortnitetracker.Fragment.ChallengeTabFragment;
import com.example.android.fortnitetracker.Fragment.NewsTabFragment;
import com.example.android.fortnitetracker.Objects.Post;
import com.example.android.fortnitetracker.R;
import com.example.android.fortnitetracker.Fragment.StoreTabFragment;
import com.example.android.fortnitetracker.Fragment.TrackerTabFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class HomeActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    static final Uri CONTENT_URL = Uri.parse(
            "content://com.example.android.fortnitetracker.database.NameProvider/ftusers"
    );

    ContentResolver resolver;
    String username = "";


    EditText messageText;
    ImageView sendBtn;

    RecyclerView messageRV;
    FrameLayout messageFrame;
    View backgroundTint;
    FloatingActionButton fabMessageBtn;
    Button submitButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post, MessageRV> adapter;

    Animation animation_move_in;
    Animation animation_move_out;

    // Username entry
    FrameLayout usernameFrame;
    EditText usernameEntryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        initApp();

        getName();

        fabMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(backgroundTint.getVisibility() == View.GONE){
                    backgroundTint.setVisibility(View.VISIBLE);
                    if(checkDatabaseisEmpty()){
                        usernameFrame.setVisibility(View.VISIBLE);
                        usernameFrame.setAnimation(animation_move_in);
                        submitButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addName(v);
                                usernameFrame.setAnimation(animation_move_out);
                                usernameFrame.setVisibility(View.GONE);
                                usernameFrame.getAnimation().start();
                                //messageRV.setLayoutManager(new LinearLayoutManager(v.getContext()));
                                messageFrame.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {

                        messageFrame.setVisibility(View.VISIBLE);
                        messageFrame.setAnimation(animation_move_in);
                        messageFrame.getAnimation().start();
                        messageRV.setLayoutManager(new LinearLayoutManager(v.getContext()));
                    }

                } else {
                    if(checkDatabaseisEmpty()){
                        usernameFrame.setVisibility(View.GONE);
                        usernameFrame.setAnimation(animation_move_out);
                        usernameFrame.getAnimation().start();
                    } else {
                        messageFrame.setVisibility(View.GONE);
                        messageFrame.setAnimation(animation_move_out);
                        messageFrame.getAnimation().start();
                        messageRV.setLayoutManager(null);
                    }
                    backgroundTint.setVisibility(View.GONE);
                }
            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
                messageText.setText("");


            }
        });


        displayComment();
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    private boolean checkDatabaseisEmpty() {
        String [] projection = new String[]{"name"};
        Cursor cursor = resolver.query(CONTENT_URL, projection, null, null, null);
        if (cursor.moveToFirst()) {
            return false;
        }
        else{
            return true;
        }
    }

    private void postComment() {
         String message = messageText.getText().toString();
         String empty = new String();

         if(message.equals(empty)){
             Toast.makeText(this, R.string.toast_no_message_found, Toast.LENGTH_SHORT).show();
         } else {
             Post post = new Post(username, message);
             databaseReference.push().setValue(post);
             messageRV.scrollToPosition(adapter.getItemCount() - 1);
             adapter.notifyDataSetChanged();
         }

    }

    private void displayComment() {
        options =
                new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(databaseReference, Post.class)
                .build();

        adapter =
                new FirebaseRecyclerAdapter<Post, MessageRV>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MessageRV holder, int position, @NonNull Post model) {
                        holder.body.setText(model.getMessage());
                        holder.name.setText(model.getName());
                    }

                    @NonNull
                    @Override
                    public MessageRV onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.message_layout, viewGroup,false);
                        return new MessageRV(itemView);
                    }


                };


        adapter.startListening();
        messageRV.setAdapter(adapter);
        messageRV.scrollToPosition(adapter.getItemCount() - 1);

    }

    public void addName(View view) {
        String nameToAdd = usernameEntryText.getText().toString();
        String empty = new String();

        if (nameToAdd.equals(empty)){
            Toast.makeText(this, R.string.toast_enter_username, Toast.LENGTH_SHORT).show();
        } else {
            ContentValues values = new ContentValues();
            values.put("name", nameToAdd);

            resolver.insert(CONTENT_URL, values);

            getName();
        }
    }

    private void getName() {
        String [] projection = new String[]{"name"};
        Cursor cursor = resolver.query(CONTENT_URL, projection, null, null, null);


        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                username = name;
            } else {

                Toast.makeText(this, R.string.toast_no_username_in_database, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, R.string.toast_no_cursor_found, Toast.LENGTH_SHORT).show();
        }
    }


   /* private void DeleteUsers() {
        String [] projection = new String[]{"username"};

        long delete = resolver.delete(CONTENT_URL, "name = ? ", projection);


    }*/

    private void initApp() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Set up message
        messageText = findViewById(R.id.message_edit_text);
        sendBtn = findViewById(R.id.message_send);
        submitButton = findViewById(R.id.btn_username_entry);
        messageRV = findViewById(R.id.message_rv);
        messageRV.setLayoutManager(new LinearLayoutManager(this));
        messageFrame = findViewById(R.id.message_frame);
        backgroundTint = findViewById(R.id.message_background_tint);
        fabMessageBtn = findViewById(R.id.floating_message_btn);

        // Set up dataBase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(getString(R.string.edmt));

        // Animation
        animation_move_in = AnimationUtils.loadAnimation(this, R.anim.move_in);
        animation_move_out = AnimationUtils.loadAnimation(this, R.anim.move_out);

        resolver = getContentResolver();

        // Username Entry
        usernameEntryText = findViewById(R.id.et_username_entry);
        usernameFrame = findViewById(R.id.message_username_entry_frame);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Activity activity;

        public SectionsPagerAdapter(FragmentManager fm, Activity act) {
            super(fm);
            activity = act;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position){
                case 0:
                    NewsTabFragment newsTabFragment = new NewsTabFragment();
                    return newsTabFragment;

                case 1:
                    TrackerTabFragment trackerTabFragment = new TrackerTabFragment();
                    return trackerTabFragment;

                case 2:
                    StoreTabFragment storeTabFragment = new StoreTabFragment();
                    return storeTabFragment;

                case 3:
                    ChallengeTabFragment challengeTabFragment = new ChallengeTabFragment();
                    return challengeTabFragment;

                    default:
                        return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}



