package com.example.android.sfcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sfcc.R;
import com.example.android.sfcc.model.MCQ;
import com.example.android.sfcc.viewholder.AnswerViewHolder;

import java.util.List;

public class AnswerRecyclerViewAdapter extends RecyclerView.Adapter<AnswerViewHolder> {
    private List<MCQ> mcqData;
    private LayoutInflater mInflater;
    private Context context;
    // data is passed into the constructor
    public AnswerRecyclerViewAdapter(Context context, List<MCQ> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mcqData = data;
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_answer_card, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        holder.question.setText(mcqData.get(position).getQuestion());
        holder.answer.setText(mcqData.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return mcqData.size();
    }
}

