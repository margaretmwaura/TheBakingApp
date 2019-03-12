package com.example.admin.baking;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class StepsFragment extends Fragment implements ExoPlayer.EventListener{


    private Recipe recipe;
    private TextView shortDescription,descriptionView,shortdescripitonLabel,descriptionLabel;
    private List<Step> steps = new ArrayList<>();

    private Step currentStep ;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;
    private String videoUrl,finalVideoUrl;
    private PlaybackStateCompat.Builder mStateBuilder;
    private MediaSessionCompat mMediaSession;
    private ImageButton next,previous;
    private int count;
    private static final String TAG = "This Steps fragment activity ";
    private long currentPlayPosition;


//    This fragment has the details on the steps of the recipe that includes the video
    public StepsFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.activity_steps_fragment,container,false);

//        This is the ID of the recipe that is unique to that recipe
        if(savedInstanceState == null)
        {
            recipe = getArguments().getParcelable("TransferRecipe");
            count = getArguments().getInt("TheCount");
           currentPlayPosition = 0;
        }
        else
        {
            recipe = savedInstanceState.getParcelable("TheRecipe");
            count = savedInstanceState.getInt("TheCurrentStepCount");
            currentPlayPosition = savedInstanceState.getLong("TheCurrentVideoPlayPosition");
        }
//        int id = recipe.getId();
        steps.addAll(recipe.getSteps());

//        The views code
        shortDescription = (TextView) rootView.findViewById(R.id.short_description);
        descriptionView = (TextView) rootView.findViewById(R.id.description);

        shortdescripitonLabel = (TextView) rootView.findViewById(R.id.short_description_label);
        descriptionLabel = (TextView) rootView.findViewById(R.id.description_label);
        mExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.stepVideo);

//        Wire up this two buttons later
        next = (ImageButton) rootView.findViewById(R.id.exo_next);
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("NextButton","The button has been clicked ");
            }
        });
        previous = (ImageButton) rootView.findViewById(R.id.exo_prev);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("PreviousButton","The button has been clicked ");
            }
        });

//        Retrieve the ArrayList of the steps and ingredients



//        Loop through the arrayList of the steps to get the data to set to the textViews
        settingData(count);

//When the next and previous buttons have been clikced

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }

    public void intializePlayer()
    {
//        This code is for setting up the exoPlayer
        if(mExoPlayer == null)
        {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),trackSelector,loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(getActivity().getApplicationContext(),"Baking");

            if(finalVideoUrl != null)
            {
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(finalVideoUrl),
                        new DefaultDataSourceFactory(
                                getActivity().getApplicationContext(), userAgent
                        ),
                        new DefaultExtractorsFactory(), null, null);

                if (currentPlayPosition != 0) {
                    mExoPlayer.seekTo(currentPlayPosition);
                }
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);
            }
        }
    }

    public void settingData(int count)
    {

        if (steps != null) {
            currentStep = new Step();

            if (count >= 0 && count <= (steps.size() - 1))
            {
                currentStep = steps.get(count);

                if (currentStep != null)
                {
                    Log.d("currentStep","The currentStep is not null");
                    String description = currentStep.getDescription();
                    String sDescription = currentStep.getShortDescription();
                    videoUrl = currentStep.getVideoUrl();
                    Log.d("VideoUrlUntrimmed ", "This is the video url " + currentStep.getVideoUrl() + "And this is the description " + description);
                    if (videoUrl != null)
                    {
                        finalVideoUrl = videoUrl.trim();
                        Log.d("VideoUrl", "This is the videoUrl " + finalVideoUrl);
                    }
                        if (description.isEmpty())
                        {
                            descriptionView.setText("No description found");
                        } else {
                            descriptionView.setText(sDescription);
                        }
                        if (sDescription.isEmpty()) {
                            shortDescription.setText("No description found");
                        } else {
                            shortDescription.setText(description);
                        }
//                    This is only needed if I have the external client
//                    initializeMediaSession();
                        intializePlayer();


                }
                else
                {
                    Log.d("CurrentStep","The current Step is null");
                }

            }
            else
            {
                Log.d("CurrentStep","The current Step is null");
            }

        }
    }


//    private void initializeMediaSession() {
//
//        // Create a MediaSessionCompat.
//        mMediaSession = new MediaSessionCompat(getActivity().getApplicationContext(), TAG);
//
//        // Enable callbacks from MediaButtons and TransportControls.
//        mMediaSession.setFlags(
//                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
//                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
//
//        // Do not let MediaButtons restart the player when the app is not visible.
//        mMediaSession.setMediaButtonReceiver(null);
//
//        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
//        mStateBuilder = new PlaybackStateCompat.Builder()
//                .setActions(
//                        PlaybackStateCompat.ACTION_PLAY |
//                                PlaybackStateCompat.ACTION_PAUSE |
//                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
//                                PlaybackStateCompat.ACTION_PLAY_PAUSE|
//                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT
//                );
//
//        mMediaSession.setPlaybackState(mStateBuilder.build());
//
//
////        // MySessionCallback has methods that handle callbacks from a media controller.
////        mMediaSession.setCallback(new MySessionCallback());
//
//        // Start the Media Session since the activity is active.
//        mMediaSession.setActive(true);
//
//    }


//    This are the methods that have been implemented as a result of implement the ExoPlayer.EventListener
//    Implementing only one the playStateChanged method
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest)
    {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections)
    {

    }

    @Override
    public void onLoadingChanged(boolean isLoading)
    {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState)
    {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
      

        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error)
    {

    }

    @Override
    public void onPositionDiscontinuity()
    {

    }

    private void gettingCurrentPosition()
    {
        if(mExoPlayer!= null)
        {
            currentPlayPosition = mExoPlayer.getCurrentPosition();
        }
    }



//    This is for the external client
//    private class MySessionCallback extends MediaSessionCompat.Callback {
//        @Override
//        public void onPlay() {
//            mExoPlayer.setPlayWhenReady(true);
//        }
//
//        @Override
//        public void onPause() {
//            mExoPlayer.setPlayWhenReady(false);
//        }
//
////        This is where I will write code to move to the previous steps POJO
//        @Override
//        public void onSkipToPrevious()
//        {
//            mExoPlayer.seekTo(0);
//        }
////Will overide this method
//        @Override
//        public void onSkipToNext()
//        {
//            super.onSkipToNext();
//        }
//    }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            shortDescription.setVisibility(View.GONE);
            descriptionView.setVisibility(View.GONE);
            shortdescripitonLabel.setVisibility(View.GONE);
            descriptionLabel.setVisibility(View.GONE);


            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mExoPlayerView.getLayoutParams();
            params.width= width;
            params.height= height;
            mExoPlayerView.setLayoutParams(params);

            Log.d("Landscape","Method has been called ");
        }
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            shortDescription.setVisibility(View.VISIBLE);
            descriptionView.setVisibility(View.VISIBLE);
            shortdescripitonLabel.setVisibility(View.VISIBLE);
            descriptionLabel.setVisibility(View.VISIBLE);

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            int measure = height/2 ;

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mExoPlayerView.getLayoutParams();
            params.width= width;
            params.height= measure;
            mExoPlayerView.setLayoutParams(params);

            Log.d("Portrait","Method has been called ");
        }

    }


//    These are the codes for initializing and releasing the player

    public void releasePlayer()
    {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23) {
            intializePlayer();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();

        if(mExoPlayer != null)
        {
            gettingCurrentPosition();
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onPause() {

        super.onPause();
        if(mExoPlayer != null)
        {
            gettingCurrentPosition();
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }

    }

    @Override
    public void onResume() {

        super.onResume();
        if (Util.SDK_INT <= 23 || mExoPlayer == null) {
            intializePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mExoPlayer != null)
        {
            gettingCurrentPosition();
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        gettingCurrentPosition();
        outState.putInt("TheCurrentStepCount",count);
        outState.putLong("TheCurrentVideoPlayPosition",currentPlayPosition);
        outState.putParcelable("TheRecipe",recipe);

    }
}
