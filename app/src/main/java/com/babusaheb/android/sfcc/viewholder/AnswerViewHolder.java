package com.babusaheb.android.sfcc.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babusaheb.android.sfcc.R;

public class AnswerViewHolder extends RecyclerView.ViewHolder{
    public CardView answerCard;
    public TextView question,answer;
    public AnswerViewHolder(@NonNull View itemView) {
        super(itemView);
        answerCard = itemView.findViewById(R.id.answer_card);
        question = itemView.findViewById(R.id.question_text);
        answer = itemView.findViewById(R.id.answer_text);
    }
}
