package com.example.android.sfcc.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sfcc.R;

public class TestViewHolder extends RecyclerView.ViewHolder{
    public CardView testCard;
    public TextView testTitle;
    public TestViewHolder(@NonNull View itemView) {
        super(itemView);
        testCard = itemView.findViewById(R.id.test_card);
        testTitle = itemView.findViewById(R.id.test_title);
    }
}
