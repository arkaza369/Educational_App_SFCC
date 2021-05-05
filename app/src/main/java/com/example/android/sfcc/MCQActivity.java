package com.example.android.sfcc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.sfcc.model.MCQ;

import java.util.List;

public class MCQActivity extends AppCompatActivity {
    List<MCQ> mcqes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        mcqes= (List<MCQ>) getIntent().getSerializableExtra("MCQ");

    }
}
