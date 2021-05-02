package com.example.android.sfcc;

import android.net.Uri;
import android.os.Bundle;

import com.example.android.sfcc.model.MCQ;
import com.example.android.sfcc.model.Test;
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
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TestYourselfDetailActivity extends AppCompatActivity {
    private List<Test> tests = new ArrayList<>();
    DatabaseReference reference_test;
    FirebaseUser user;
    FirebaseDatabase database;
    private static final String TAG = "TestYourselfDetailActiv";
    private List<MCQ> mcqes = new ArrayList<MCQ>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_yourself_detail);
        String value = getIntent().getExtras().getString("testName");
        Log.d(TAG, "onCreate: " + value);
        loadTestData(value);
    }

    private void loadTestData(String value) {

        reference_test = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("test/class_10");

        //fill it

        reference_test.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                // dataSnap = dataSnapshots;
                int index = 0;
                for (DataSnapshot childSnapshot : dataSnapshots.getChildren()) {
                    //Retrieved data
                    String title = childSnapshot.child("/title").getValue().toString();
                    String answer = childSnapshot.child("/1/answer").getValue().toString();
                    String options = childSnapshot.child("/1/option").getValue().toString();
                    String question = childSnapshot.child("/1/question").getValue().toString();
                    // Log.d(TAG, "onDataChange: "+title+" "+answer+" "+options+" "+question);

                    //setting values in "tests" list
                    List<String> options_list = new ArrayList<>();
                    String[] options_split = options.split("\\|");
                    for (int i = 0; i < options_split.length; i++) {
                        //  Log.d(TAG, "onDataChange: "+options_split[i]);
                        options_list.add(options_split[i]);
                    }
                    // Log.d(TAG, "onDataChange: "+options_list);
                    mcqes.add(new MCQ(question, answer, options_list));
                    //retrieving MCQ contents
                    Log.d(TAG, "onDataChange: mcqs data" + mcqes.get(0).getQuestion() + " " + mcqes.get(0).getAnswer() + mcqes.get(0).getOptions());

                    tests.add(new Test(title, mcqes));
                    //retrieving Tests contents
                    Log.d(TAG, "onDataChange: tests data " + tests.get(0).getTitle() + " " + tests.get(0).getMcqes().get(0).getQuestion()
                            + " " + tests.get(0).getMcqes().get(0).getAnswer() + " " + tests.get(0).getMcqes().get(0).getOptions());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}