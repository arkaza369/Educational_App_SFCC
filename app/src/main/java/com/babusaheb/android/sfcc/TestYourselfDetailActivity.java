package com.babusaheb.android.sfcc;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.babusaheb.android.sfcc.adapter.TestRecyclerViewAdapter;
import com.babusaheb.android.sfcc.model.MCQ;
import com.babusaheb.android.sfcc.model.Test;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestYourselfDetailActivity extends AppCompatActivity {
    private List<Test> tests = new ArrayList<>();
    DatabaseReference reference_test;
    RecyclerView testRecycler;
    TestRecyclerViewAdapter adapter;
    private static final String TAG = "TestYourselfDetail";
    private HashMap<String,String> testTitle;
    TextView titleView;
    ShimmerFrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_yourself_detail);
        titleView = (TextView) findViewById(R.id.title);
        testTitle = new HashMap<>();
        testTitle.put("class_10","Class-10 Test");
        String value = getIntent().getExtras().getString("testName");
        Log.d(TAG, "onCreate: " + value);
        loadTestData(value,this);
        titleView.setText(testTitle.get(value));
        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
    }

    private void loadTestData(String value, TestYourselfDetailActivity testYourselfDetailActivity) {

        reference_test = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("test/"+value);

        //fill it

        reference_test.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                // dataSnap = dataSnapshots;
                int index = 1;
                for (DataSnapshot childSnapshot : dataSnapshots.getChildren()) {
                     List<MCQ> mcqes = new ArrayList<MCQ>();
                    String number_of_question = childSnapshot.child("/number_of_question").getValue().toString();
                    int size_of_question = Integer.parseInt(number_of_question);
                    String title = childSnapshot.child("/title").getValue().toString();
                    for (int i = 1; i <= size_of_question; i++) {
                        String answer = childSnapshot.child("/" + i + "/answer").getValue().toString();
                        String options = childSnapshot.child("/" + i + "/option").getValue().toString();
                        String question = childSnapshot.child("/" + i + "/question").getValue().toString();
                        Log.d(TAG, "onDataChange: " + number_of_question + " " + title + " " + answer + " " + options + " " + question);

                        List<String> options_list = new ArrayList<>();
                        String[] options_split = options.split("\\|");
                        for (int j = 0; j < options_split.length; j++) {
                            //  Log.d(TAG, "onDataChange: "+options_split[i]);
                            options_list.add(options_split[j]);
                        }
                        // Log.d(TAG, "onDataChange: "+options_list);
                        mcqes.add(new MCQ(question, answer, options_list));
                    }


                    //retrieving MCQ contents
                    for (int i = 0; i < mcqes.size(); i++) {
                        Log.d(TAG, "onDataChange: mcqs data retrieval" + mcqes.get(i).getQuestion() + " " + mcqes.get(i).getAnswer()
                                + mcqes.get(i).getOptions());

                    }
                    //setting values in "tests" list
                    tests.add(new Test(title, mcqes));
                    testRecycler = findViewById(R.id.test_list);
                    testRecycler.setLayoutManager(new LinearLayoutManager(testYourselfDetailActivity));
                    adapter = new TestRecyclerViewAdapter(testYourselfDetailActivity, tests);
                    testRecycler.setAdapter(adapter);
                    //retrieving Tests contents
                    for (int i = 0; i < tests.size(); i++) {
                        Log.d(TAG, "onDataChange: tests data " + tests.get(i).getTitle());
                        for (int j = 0; j < tests.get(i).getMcqes().size(); j++) {
                            Log.d(TAG, "onDataChange: " + tests.get(i).getMcqes().get(j).getQuestion()
                                    + " " + tests.get(i).getMcqes().get(j).getAnswer() + " " + tests.get(i).getMcqes().get(j).getOptions());
                        }
                    }
                    container.hideShimmer();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}