package com.example.popcorn.controllers.fragments;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.popcorn.R;
import com.example.popcorn.models.Content;
import com.example.popcorn.models.Video;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.exoplayer2.ExoPlayerFactory;
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
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class WatchFragment extends Fragment {

    private WebView webViews;
    VideoView video;
    RecyclerView mRecyclerview;
    FirebaseDatabase database;
    DatabaseReference reference;

    public WatchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result =  inflater.inflate(R.layout.fragment_watch, container, false);

        //HTML Ressources
        webViews = (WebView)result.findViewById(R.id.html_res);
        WebSettings ws = webViews.getSettings();
        ws.setJavaScriptEnabled(true);
        webViews.loadUrl("file:///android_asset/header_watch.html");

        mRecyclerview = result.findViewById(R.id.video_list);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        database = FirebaseDatabase.getInstance();
        reference= database.getReference("video");

        return result;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Video, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Video, ViewHolder>(
                        Video.class,
                        R.layout.video_row,
                        ViewHolder.class,
                        reference
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Video video, int i) {
                        viewHolder.setTitle(video.getTitle());
                        viewHolder.setUrl(getContext(), video.getUrl());

                    }
                };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        SimpleExoPlayer exoPlayer;
        private PlayerView mExoplayerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title){

            TextView video_title = (TextView)mView.findViewById(R.id.video_title);
            video_title.setText(title);
        }

        public void setUrl(final Context ctx, final String url){
            TextView mtextView = mView.findViewById(R.id.video_title);
            mExoplayerView = mView.findViewById(R.id.exoplayer_view);

            try {
                BandwidthMeter bandwidthMeter= new DefaultBandwidthMeter.Builder(ctx).build();
                TrackSelector trackSelector= new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                exoPlayer= (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(ctx);
                Uri video = Uri.parse(url);
                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(video, dataSourceFactory, extractorsFactory, null, null );
                mExoplayerView.setPlayer(exoPlayer);
                exoPlayer.prepare(mediaSource);
                exoPlayer.setPlayWhenReady(false);

            }catch (Exception e){
                Log.e("ViewHolder", "exoplayer error" + e.toString());
            }

        }
    }
}