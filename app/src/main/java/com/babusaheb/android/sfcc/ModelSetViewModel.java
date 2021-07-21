package com.babusaheb.android.sfcc;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ModelSetViewModel extends RecyclerView.ViewHolder{
    private static final String TAG = "ModelSetViewHolder";
    View mView;
    DatabaseReference reference_pdfs ;
    DataSnapshot dataSnap;



    public ModelSetViewModel(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        reference_pdfs = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("model_set/class_10");


        reference_pdfs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                dataSnap = dataSnapshots;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastClickedItemPosition = getAdapterPosition();
                Log.i(TAG, "onClick: "+lastClickedItemPosition);
                Intent i = new Intent(mView.getContext(), ModelSetClassXChapterWiseContent.class);
                i.putExtra("CurrentAdapterPosition", lastClickedItemPosition);
                mView.getContext().startActivity(i);
            }
        });
    }

    public void classXSetPdf(final Application ctx, String title ,String description){
        TextView mTextView = mView.findViewById(R.id.model_set_class_x_pdf_title);
        TextView mTextViewDescp = mView.findViewById(R.id.model_set_class_x_pdf_descp);
        mTextView.setText(title);
        mTextViewDescp.setText(description);
    }
}
