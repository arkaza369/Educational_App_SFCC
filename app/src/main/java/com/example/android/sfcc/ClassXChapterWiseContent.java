package com.example.android.sfcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class ClassXChapterWiseContent extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private String TAG = "ClassXChapterWiseContent";
    int lastClickedItemPosition;

    private TextView chapter_title,chapter_descp;
    SimpleExoPlayer exoPlayer;
    private PlayerView mExoplayerView;

    FirebaseAuth mAuth;
    DatabaseReference reference_header, reference_videos, reference;
    FirebaseUser user;
    FirebaseDatabase database;
    DataSnapshot dataSnap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_class_xchapter_wise_content);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SFCC");



        chapter_title = findViewById(R.id.video_title_chapterwise);
        chapter_descp = findViewById(R.id.video_descp_chapterwise);
        mExoplayerView = findViewById(R.id.exoplayer_view_chapterwise);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mAuth = FirebaseAuth.getInstance();
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.home:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: home");
                        break;
                    case R.id.courses:
                        intent = new Intent(getApplicationContext(), CoursesActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Courses");
                        break;
                    case R.id.tutors:
                        intent = new Intent(getApplicationContext(), TutorsActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Tutors");
                        break;
                    case R.id.test_yourself:
                        intent = new Intent(getApplicationContext(), TestYourselfActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Test Yourself");
                        break;
                    case R.id.settings:
                        intent = new Intent(getApplicationContext(), Settings.class);
                        startActivity(intent);
                        break;
                    case R.id.contact_us:
                        Toast.makeText(ClassXChapterWiseContent.this, "Contact us", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Contact us");
                        break;
                    case R.id.model_set:
                        intent = new Intent(getApplicationContext(), ModelSetActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Model Sets");
                        break;
                    case R.id.logout:
                        mAuth.signOut();
                        intent = new Intent(getApplicationContext(), Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //To access nav_header views i.e. username
        // startVideo(startChapter);
        String id = mAuth.getCurrentUser().getUid();
        reference_header = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("users");
        reference = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference();

        reference_header.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    String keys = datas.getKey();
                    user = mAuth.getCurrentUser();
                    String uid = user.getUid();
                    String user_name = datas.child("/username").getValue().toString();
                    View view = navigationView.getHeaderView(0);
                    TextView username = view.findViewById(R.id.name);
                    username.setText("Welcome " + user_name);
                    CircularImageView userProfilePic = view.findViewById(R.id.imageView);
                    if (datas.hasChild("/image")) {
                        String image = datas.child("/image").getValue().toString();
                        Picasso.get().load(image).into(userProfilePic);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Intent mIntent = getIntent();
        lastClickedItemPosition = mIntent.getIntExtra("CurrentAdapterPosition", 0);
        Log.i(TAG, "onClick: "+lastClickedItemPosition);
        reference_videos = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("course/class_10");


        reference_videos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
               // dataSnap = dataSnapshots;
                int index = 0;
                for (DataSnapshot childSnapshot : dataSnapshots.getChildren()) {
                    if (index == lastClickedItemPosition) {
                        String chapter_name = childSnapshot.child("/name").getValue().toString();
                        String description = childSnapshot.child("/description").getValue().toString();
                        String video = childSnapshot.child("/video").getValue().toString();
                        chapter_title.setText(chapter_name);
                        chapter_descp.setText(description);
                        try{
                            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(getApplicationContext()).build();
                            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                            LoadControl loadControl = new DefaultLoadControl();
                            exoPlayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(getApplicationContext());
                            Uri videos= Uri.parse(video);
                            DefaultHttpDataSourceFactory dataSourceFactory=new DefaultHttpDataSourceFactory("class_10");
                            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                            MediaSource mediaSource = new ExtractorMediaSource(videos,dataSourceFactory,extractorsFactory,
                                    null,null);
                            mExoplayerView.setPlayer(exoPlayer);
                            exoPlayer.prepare(mediaSource);
                            exoPlayer.setPlayWhenReady(true);

                        }catch (Exception e){
                            Log.e(TAG, "exoPlayerError: "+ e.toString());
                        }

                        Log.i(TAG, "onClick: "+chapter_name+ " "+description);
                    }

                    index++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }
    @Override
    public void onPause() {
        super.onPause();

        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
