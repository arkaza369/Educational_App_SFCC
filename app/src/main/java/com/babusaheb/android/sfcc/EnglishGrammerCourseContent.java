package com.babusaheb.android.sfcc;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnglishGrammerCourseContent extends YouTubeBaseActivity {

    private String TAG = "EnglishGrammerCourseContent";

    private TextView chapter_title,chapter_descp;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    FirebaseAuth mAuth;
    DatabaseReference reference_videos, reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_grammer_course_content);

        chapter_title = findViewById(R.id.english_grammer_video_title_chapterwise);
        chapter_descp = findViewById(R.id.english_grammer_video_descp_chapterwise);
        youTubePlayerView = findViewById(R.id.english_grammer_youtube_player_view_chapterwise);

        mAuth = FirebaseAuth.getInstance();

        String id = mAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference();

        reference_videos = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("course/english");


        reference_videos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String chapter_name = dataSnapshot.child("/name").getValue().toString();
                String description = dataSnapshot.child("/description").getValue().toString();
                String video = dataSnapshot.child("/video").getValue().toString();
                chapter_title.setText(chapter_name);
                chapter_descp.setText(description);

                playVideo(video);
                youTubePlayerView.initialize("AIzaSyBnE2yPlBEx5zGIVExrlj26W8enIDuJ3_0",onInitializedListener);
                Log.i(TAG, "onClick: "+chapter_name+ " "+description);

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
    }
    @Override
    public void onPause() {
        super.onPause();
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
