package com.example.android.sfcc;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClassXViewModel extends RecyclerView.ViewHolder{
    private static final String TAG = "ViewHolder";
    View mView;
    DatabaseReference reference_videos ;
    DataSnapshot dataSnap;




    public ClassXViewModel(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        reference_videos = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("course/class_10");


        reference_videos.addValueEventListener(new ValueEventListener() {
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
                Intent i = new Intent(mView.getContext(), ClassXChapterWiseContent.class);
                i.putExtra("CurrentAdapterPosition", lastClickedItemPosition);
                mView.getContext().startActivity(i);
            }
        });
    }

    public void classXSetVideos(final Application ctx, String title ){
        TextView mTextView = mView.findViewById(R.id.class_x_video_title);
        mTextView.setText(title);
    }
}
