package com.babusaheb.android.sfcc;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ViewHolder";
    View mView;
    SimpleExoPlayer exoPlayer;
    private PlayerView mExoplayerView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setVideos(final Application ctx,String description,String title,final String video ){
        TextView mTextView = mView.findViewById(R.id.video_title);
        TextView mVideoDescription = mView.findViewById(R.id.video_descp);
        mExoplayerView = mView.findViewById(R.id.exoplayer_view);
        mTextView.setText(title);
        mVideoDescription.setText(description);
        try{
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(ctx).build();
          //  BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            LoadControl loadControl = new DefaultLoadControl();
           // SimpleExoPlayer exoPlayer = (SimpleExoPlayer)ExoPlayerFactory.newSimpleInstance(mExoplayerView.getContext(), trackSelector, loadControl);

            exoPlayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(ctx);
            Uri videos= Uri.parse(video);
            DefaultHttpDataSourceFactory dataSourceFactory=new DefaultHttpDataSourceFactory("class_10");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videos,dataSourceFactory,extractorsFactory,null,null);
            mExoplayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
           // exoPlayer.setPlayWhenReady(sh);
            exoPlayer.setPlayWhenReady(true);
        }catch (Exception e){
            Log.e(TAG, "exoPlayerError: "+ e.toString());
        }
    }
}
