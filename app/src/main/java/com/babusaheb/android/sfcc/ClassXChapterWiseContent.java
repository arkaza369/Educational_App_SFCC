package com.babusaheb.android.sfcc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClassXChapterWiseContent extends YouTubeBaseActivity {

    private Toolbar toolbar;
//    private DrawerLayout drawerLayout;
//    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private String TAG = "ClassXChapterWiseContent";
    int lastClickedItemPosition;

    private TextView chapter_title,chapter_descp;
    /*SimpleExoPlayer exoPlayer;
    private PlayerView mExoplayerView;*/
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

//    ShimmerFrameLayout container;

    FirebaseAuth mAuth;
    DatabaseReference reference_videos, reference;
    //DatabaseReference reference_header;
    FirebaseUser user;
    FirebaseDatabase database;
    DataSnapshot dataSnap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_class_xchapter_wise_content);

        toolbar = findViewById(R.id.toolbar);
//        navigationView = findViewById(R.id.navigationView);
//        drawerLayout = findViewById(R.id.drawer);
       /* setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SFCC");*/



        chapter_title = findViewById(R.id.video_title_chapterwise);
        chapter_descp = findViewById(R.id.video_descp_chapterwise);
       // mExoplayerView = findViewById(R.id.exoplayer_view_chapterwise);
       // container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container_class_10);
        youTubePlayerView = findViewById(R.id.youtube_player_view_chapterwise);


//        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
        mAuth = FirebaseAuth.getInstance();
 //       navigationView.setItemIconTintList(null);

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                drawerLayout.closeDrawer(GravityCompat.START);
//                Intent intent;
//                switch (item.getItemId()) {
//                    case R.id.home:
//                        intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
//                        Log.d(TAG, "onNavigationItemSelected: home");
//                        break;
//                    case R.id.courses:
//                        intent = new Intent(getApplicationContext(), CoursesActivity.class);
//                        startActivity(intent);
//                        Log.d(TAG, "onNavigationItemSelected: Courses");
//                        break;
//                    case R.id.tutors:
//                        intent = new Intent(getApplicationContext(), TutorsActivity.class);
//                        startActivity(intent);
//                        Log.d(TAG, "onNavigationItemSelected: Tutors");
//                        break;
//                    case R.id.test_yourself:
//                        intent = new Intent(getApplicationContext(), TestYourselfActivity.class);
//                        startActivity(intent);
//                        Log.d(TAG, "onNavigationItemSelected: Test Yourself");
//                        break;
//                    case R.id.settings:
//                        intent = new Intent(getApplicationContext(), Settings.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.contact_us:
//                        intent = new Intent(getApplicationContext(), TutorsActivity.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.model_set:
//                        intent = new Intent(getApplicationContext(), ModelSetActivity.class);
//                        startActivity(intent);
//                        Log.d(TAG, "onNavigationItemSelected: Model Sets");
//                        break;
//                    case R.id.logout:
//                        mAuth.signOut();
//                        intent = new Intent(getApplicationContext(), Login.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//
//                        break;
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });

        //To access nav_header views i.e. username
        // startVideo(startChapter);
        String id = mAuth.getCurrentUser().getUid();
//        reference_header = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
//                getReference("users/"+id);
        reference = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference();

//        reference_header.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot datas : dataSnapshot.getChildren()) {
//                    View view = navigationView.getHeaderView(0);
//                    if (datas.getKey().equals("fullName")) {
//
//                        String name = datas.getValue().toString();
//                        TextView username = view.findViewById(R.id.name);
//                        username.setText("Welcome " + name);
//                    }
//
//                    CircularImageView userProfilePic = view.findViewById(R.id.imageView);
//                    if (datas.getKey().equals( "image")) {
//                        String image = datas.getValue().toString();
//                        Picasso.get().load(image).into(userProfilePic);
//                    }
//
//                }
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


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

                        playVideo(video);
                        youTubePlayerView.initialize("AIzaSyBnE2yPlBEx5zGIVExrlj26W8enIDuJ3_0",onInitializedListener);

                       /* try{
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
                        }*/


                        Log.i(TAG, "onClick: "+chapter_name+ " "+description);

                        //container.hideShimmer();
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

//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        toggle.syncState();
    }
    @Override
    public void onPause() {
        super.onPause();

      //  exoPlayer.setPlayWhenReady(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void playVideo(String url){
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b)
                youTubePlayer.loadPlaylist(url);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
    }

}
