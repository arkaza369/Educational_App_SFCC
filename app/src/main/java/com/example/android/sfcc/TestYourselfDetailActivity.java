package com.example.android.sfcc;

import android.os.Bundle;

import com.example.android.sfcc.model.Test;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TestYourselfDetailActivity extends AppCompatActivity {
    private List<Test> tests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_yourself_detail);
        String value = getIntent().getExtras().getString("testName");
        loadTestData(value);
    }

    private void loadTestData(String value) {
        reference = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("users");
        //fill it
    }
}