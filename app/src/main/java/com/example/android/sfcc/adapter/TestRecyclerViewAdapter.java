package com.example.android.sfcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sfcc.R;
import com.example.android.sfcc.ViewHolder;
import com.example.android.sfcc.model.Test;
import com.example.android.sfcc.viewholder.TestViewHolder;

import java.util.List;

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestViewHolder> {
    private List<Test> testData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public TestRecyclerViewAdapter(Context context, List<Test> data) {
        this.mInflater = LayoutInflater.from(context);
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

    }

    @Override
    public int getItemCount() {
        return testData.size();
    }
}
