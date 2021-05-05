package com.example.android.sfcc.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sfcc.MCQActivity;
import com.example.android.sfcc.R;
import com.example.android.sfcc.model.MCQ;
import com.example.android.sfcc.model.Test;
import com.example.android.sfcc.viewholder.TestViewHolder;

import java.io.Serializable;
import java.util.List;


public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestViewHolder> {
    private List<Test> testData;
    private LayoutInflater mInflater;
    private Context context;
    // data is passed into the constructor
    public TestRecyclerViewAdapter(Context context, List<Test> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.testData = data;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_test_card, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        holder.testTitle.setText(testData.get(position).getTitle());
        holder.testCard.setOnClickListener(view -> {
            startNewActivity(testData.get(position).getMcqes());
        });
    }

    private void startNewActivity(List<MCQ> mcqes) {
        Intent mIntent = new Intent(context, MCQActivity.class);
        mIntent.putExtra("MCQ", (Serializable) mcqes);
        Log.i("MCQ",mcqes.get(0).getQuestion());
        context.startActivity(mIntent);
    }

    @Override
    public int getItemCount() {
        return testData.size();
    }
}
