package com.example.android.sfcc;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.sfcc.model.MCQ;

import java.util.List;

public class MCQActivity extends AppCompatActivity {
    private List<MCQ> mcqes;
    private Button prev,next;
    private RadioGroup options;
    private RadioButton option1,option2,option3,option4;
    private TextView question;
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        mcqes= (List<MCQ>) getIntent().getSerializableExtra("MCQ");
        Log.i("MCQ_QUESTION",mcqes.get(0).getQuestion());
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.prev);
        options = findViewById(R.id.options);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        question.setText(mcqes.get(count).getQuestion());
        option1.setText(mcqes.get(count).getOptions().get(0));
        option2.setText(mcqes.get(count).getOptions().get(1));
        option3.setText(mcqes.get(count).getOptions().get(2));
        option4.setText(mcqes.get(count).getOptions().get(3));
        prev.setOnClickListener(view -> {

        });
        next.setOnClickListener(view -> {

        });
    }
}
