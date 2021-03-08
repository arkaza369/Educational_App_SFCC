package com.example.android.sfcc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
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

public class ClassTen extends AppCompatActivity {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private String TAG = "ClassTenActivity";
    private VideoView videoView;
    //  private PlayerView videoView;
    private TextView sub_heading;
    FirebaseAuth mAuth;
    MediaController mediaController;
    DatabaseReference reference_header,reference_videos,reference;
    FirebaseUser user;
    //int startChapter = 2;
    int startChapter  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_class_ten);
        sub_heading = findViewById(R.id.sub_heading);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SFCC");

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // videoView = findViewById(R.id.video_view);
        videoView = findViewById(R.id.sub_video);
        mAuth = FirebaseAuth.getInstance();
        mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

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
                        Toast.makeText(ClassTen.this, "Contact us", Toast.LENGTH_SHORT).show();
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
        startVideo(startChapter);
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
                    String user_name = datas.child( "/username").getValue().toString();
                    View view = navigationView.getHeaderView(0);
                    TextView username = view.findViewById(R.id.name);
                    username.setText("Welcome " + user_name);
                    CircularImageView userProfilePic = view.findViewById(R.id.imageView);
                    if (datas.hasChild(  "/image")) {
                        String image = datas.child( "/image").getValue().toString();
                        Picasso.get().load(image).into(userProfilePic);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

   /* private void  startVideo(int startSub){
        reference_videos = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("course");
        reference_videos.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 for (DataSnapshot datas : dataSnapshot.getChildren()) {
                     Log.d(TAG, "onDataChange: "+datas);
                     String user_name = datas.child(String.valueOf(startSub)+"/name").getValue().toString();
                     Log.d(TAG, "onDataChange: "+user_name);
                     if(datas.hasChild(String.valueOf(startSub))) {
                         Log.d(TAG, "onDataChange:2 "+datas.child(String.valueOf(startSub)).child("name").getValue().toString());
                         sub_heading.setText(datas.child(String.valueOf(startSub)).child("name").getValue().toString());
                         Uri uri = Uri.parse(datas.child(String.valueOf(startSub)).child("video").getValue().toString());
                         SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(getApplicationContext());
                         videoView.setPlayer(player);

                         DataSource.Factory dataSourceFactory =
                                 new DefaultDataSourceFactory(getApplicationContext(), Util.getUserAgent( getApplicationContext(),"ClassTen"));
                         MediaSource videoSource =
                                 new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
// P
                         player.prepare(videoSource);
                         player.setPlayWhenReady(true);
                     }
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
    }*/

    private void  startVideo(int startSub){
        reference.child("course").child("class_10").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    if(datas.hasChild(String.valueOf(startSub))) {
                        sub_heading.setText(datas.child(String.valueOf(startSub)).child("name").getValue().toString());
                        Uri uri = Uri.parse(datas.child(String.valueOf(startSub)).child("video").getValue().toString());
                        videoView.setMediaController(mediaController);
                        videoView.setVideoURI(uri);
                        videoView.requestFocus();
                        videoView.start();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
