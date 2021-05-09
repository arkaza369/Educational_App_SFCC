package com.example.android.sfcc;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private TextView question,result;
    private int count = 0;
    private LinearLayout mcqLayout,resultLayout;
    String answers[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        mcqes= (List<MCQ>) getIntent().getSerializableExtra("MCQ");
        Log.i("MCQ_QUESTION", String.valueOf(mcqes.size()));
        answers = new String[mcqes.size()];
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        options = findViewById(R.id.options);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        mcqLayout = findViewById(R.id.mcq_layout);
        resultLayout = findViewById(R.id.result_layout);
        result = findViewById(R.id.result);
        question.setText(mcqes.get(count).getQuestion());
        option1.setText(mcqes.get(count).getOptions().get(0));
        option2.setText(mcqes.get(count).getOptions().get(1));
        option3.setText(mcqes.get(count).getOptions().get(2));
        option4.setText(mcqes.get(count).getOptions().get(3));
        prev.setOnClickListener(view -> {
             if(count>0){
                 count--;
                 setMcqView(count);
             }
        });
        next.setOnClickListener(view -> {
              if(count<mcqes.size()-1){
                  answers[count]= getCheckedAnswer(options.getCheckedRadioButtonId());
                  count++;
                  setMcqView(count);
              }else{
                  answers[count]= getCheckedAnswer(options.getCheckedRadioButtonId());
                  mcqLayout.setVisibility(View.GONE);
                  resultLayout.setVisibility(View.VISIBLE);
                  result.setText(String.valueOf(getCorrectAnswer()));
              }
        });
    }

    private int getCorrectAnswer() {
        int correctAns=0;
        for(int i=0;i<mcqes.size();i++){
            if(mcqes.get(i).getAnswer()==answers[i])
                correctAns++;
        }
        return correctAns;
    }

    private void setMcqView(int count) {
        options.clearCheck();
        question.setText(mcqes.get(count).getQuestion());
        option1.setText(mcqes.get(count).getOptions().get(0));
        option2.setText(mcqes.get(count).getOptions().get(1));
        option3.setText(mcqes.get(count).getOptions().get(2));
        option4.setText(mcqes.get(count).getOptions().get(3));
        if(option1.getText() == answers[count])
            options.check(R.id.option_1);
        if(option2.getText() == answers[count])
            options.check(R.id.option_3);
        if(option3.getText() == answers[count])
            options.check(R.id.option_3);
        if(option4.getText() == answers[count])
            options.check(R.id.option_4);
    }

    private String getCheckedAnswer(int checkedRadioButtonId) {
        String answer = "";
        if(checkedRadioButtonId == R.id.option_1)
            answer = (String) option1.getText();
        if(checkedRadioButtonId == R.id.option_2)
            answer = (String) option2.getText();
        if(checkedRadioButtonId == R.id.option_3)
            answer = (String) option3.getText();
        if(checkedRadioButtonId == R.id.option_4)
            answer = (String) option4.getText();
        return answer;
    }
}
