/*
 * This is the source code of DMAudioStreaming for Android v. 1.0.0.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright @Dibakar_Mistry(dibakar.ece@gmail.com), 2017.
 */
package com.musicapplication.activity;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.SubtitleCollapsingToolbarLayout;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.musicapplication.R;
import com.musicapplication.adapter.AdapterMusic;
import com.musicapplication.adapter.AudioListAdapter;
import com.musicapplication.adapter.SongsListAdapter;
import com.musicapplication.constants.Constant;
import com.musicapplication.constants.PrefranceKeyConstants;
import com.musicapplication.models.Backing_track_Model;
import com.musicapplication.networkes.ApiClient;
import com.musicapplication.networkes.ApiInterface;
import com.musicapplication.other.EndlessScrollListener;
import com.musicapplication.prefrance.PreferenceUtil;
import com.musicapplication.slidinguppanel.SlidingUpPanelLayout;
import com.musicapplication.utils.Utill;
import com.musicapplication.widgets.LineProgress;
import com.musicapplication.widgets.PlayPauseView;
import com.musicapplication.widgets.Slider;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import dm.audiostreamer.AudioStreamingManager;
import dm.audiostreamer.CurrentSessionCallback;
import dm.audiostreamer.Logger;
import dm.audiostreamer.MediaMetaData;
import retrofit2.Call;
import retrofit2.Callback;


public class MusicActivity extends AppCompatActivity implements CurrentSessionCallback, View.OnClickListener, Slider.OnValueChangedListener {

    private static final String TAG = MusicActivity.class.getSimpleName();
    private RecyclerView musicList;
    private AudioListAdapter adapterMusic;

    private PlayPauseView btn_play;
    private ImageView image_songAlbumArt;
    private ImageView img_bottom_albArt;
    private ImageView image_songAlbumArtBlur;
    private TextView time_progress_slide;
    private TextView time_total_slide;
    private TextView time_progress_bottom;
    private TextView time_total_bottom;
    private RelativeLayout pgPlayPauseLayout;
    private LineProgress lineProgress;
    private Slider audioPg;
    private ImageView btn_backward;
    private ImageView btn_forward;
    private TextView text_songName;
    private TextView text_songAlb;
    private TextView txt_bottom_SongName;
    private TextView txt_bottom_SongAlb;

    private SlidingUpPanelLayout mLayout;
    private RelativeLayout slideBottomView;
    private boolean isExpand = false;

    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    //For  Implementation
    private AudioStreamingManager streamingManager;
    private MediaMetaData currentSong;
    private List<MediaMetaData> listOfSongs = new ArrayList<MediaMetaData>();
    private String userId = "0";
    private Toolbar mToolbar;
    private ImageView iv_home, iv_back_image;
    private RecyclerView rView;
    private LinearLayoutManager layoutManager;
    private SongsListAdapter adapter;
    private List songsListModels;
    private Context context;
    private SubtitleCollapsingToolbarLayout collapsingToolbarLayout;
    private String cat_id,cat_title,cat_subtilte;
    private String encryptedkey="";
    private int  limit=20;
    private  int currentpage=1;
    private  int remaining_out=0;

    public static List<MediaMetaData> getMusicList() {
       /* List<MediaMetaData> listArticle = new ArrayList<>();
        try {
            JSONArray array = new JSONObject(response).getJSONArray(name);
            for (int i = 0; i < array.length(); i++) {
                MediaMetaData infoData = new MediaMetaData();
                JSONObject musicObj = array.getJSONObject(i);
                infoData.setMediaId(musicObj.optString("id"));
                infoData.setMediaUrl("http://184.154.53.181/musicapp/assets/uploads/backingtracks/Maid.mp3");
                infoData.setMediaTitle(musicObj.optString("title"));
                infoData.setMediaArtist(musicObj.optString("artist"));
                infoData.setMediaAlbum(musicObj.optString("album"));
                infoData.setMediaComposer(musicObj.optString(""));
                infoData.setMediaDuration(musicObj.optString("duration"));
                infoData.setMediaArt(musicObj.optString("site") + musicObj.optString("image"));
                listArticle.add(infoData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }*/
        List<MediaMetaData> listArticle = new ArrayList<>();
        try {
            for (int i = 0; i < 5; i++) {
                MediaMetaData infoData = new MediaMetaData();
                // JSONObject musicObj = array.getJSONObject(i);
                infoData.setMediaId("" + i);
                infoData.setMediaUrl("http://184.154.53.181/musicapp/assets/uploads/backingtracks/Maid.mp3");
                infoData.setMediaTitle("devid");
                infoData.setMediaArtist("jdsnfja");
                infoData.setMediaAlbum("jfdsahjkhf");
                infoData.setMediaComposer("kjzkdfsjka");
                infoData.setMediaDuration("269");
                infoData.setMediaArt("kjsfdakljg");
                listArticle.add(infoData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listArticle;
    }

    private static void progressEvent(View v, boolean isShowing) {
        try {
            View parent = (View) ((ImageView) v).getParent();
            ProgressBar pg = (ProgressBar) parent.findViewById(R.id.pg);
            if (pg != null)
                pg.setVisibility(isShowing ? View.GONE : View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        init();
        this.context = MusicActivity.this;
        encryptedkey="dfjkajfdkj";

        // get info

        if(getIntent()!=null)
        {
            if(getIntent().hasExtra(Constant.BACK_TRACKING_CATEGORY_ID)&& getIntent().hasExtra(Constant.BACKING_TRACKING_CATEGORY_TITLE) && getIntent().hasExtra(Constant.BACKING_TRACKING_CATEGORY_SUBTITLE) )

            cat_id=getIntent().getExtras().getString(Constant.BACK_TRACKING_CATEGORY_ID);
            cat_subtilte=getIntent().getExtras().getString(Constant.BACKING_TRACKING_CATEGORY_SUBTITLE);
            cat_title=getIntent().getExtras().getString(Constant.BACKING_TRACKING_CATEGORY_TITLE);
            collapsingToolbarLayout.setTitle(cat_title);
            collapsingToolbarLayout.setSubtitle(cat_subtilte);





        }
        configAudioStreamer();
        uiInitialization();
        // getUserDetails
        userId = PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_NAME, context);

       if(Utill.isConnectingToInternet(context))
        getAudioList(encryptedkey, currentpage, limit, userId, cat_id);
        else
            Utill.showCenteredToast(context,getString(R.string.please_internet));

        // rview

        rView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {


    if(listOfSongs.size()==totalItemsCount)
    {

        if(remaining_out>0)
        {


            if(Utill.isConnectingToInternet(context)){

                currentpage++;
                getAudioList(encryptedkey,currentpage,limit,userId,cat_id);

            }
            else
                Utill.showCenteredToast(context,getString(R.string.please_internet));



        }




    }







            }
        });









    }

    private void init() {

        context = this;
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_title);


        iv_back_image = collapsingToolbarLayout.findViewById(R.id.collapsing_toolbar_image_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_home = mToolbar.findViewById(R.id.iv_home);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle("");
        getSupportActionBar().setTitle("");
        iv_home.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        if (isExpand) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
            overridePendingTransition(0, 0);
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            if (streamingManager != null) {
                streamingManager.subscribesCallBack(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        try {
            if (streamingManager != null) {
                streamingManager.unSubscribeCallBack();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        try {
            if (streamingManager != null) {
                streamingManager.unSubscribeCallBack();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void updatePlaybackState(int state) {
        Logger.e("updatePlaybackState: ", "" + state);
        switch (state) {
            case PlaybackStateCompat.STATE_PLAYING:
                pgPlayPauseLayout.setVisibility(View.INVISIBLE);
                btn_play.Play();
                if (currentSong != null) {
                    currentSong.setPlayState(PlaybackStateCompat.STATE_PLAYING);
                    notifyAdapter(currentSong);
                }
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                pgPlayPauseLayout.setVisibility(View.INVISIBLE);
                btn_play.Pause();
                if (currentSong != null) {
                    currentSong.setPlayState(PlaybackStateCompat.STATE_PAUSED);
                    notifyAdapter(currentSong);
                }
                break;
            case PlaybackStateCompat.STATE_NONE:
                currentSong.setPlayState(PlaybackStateCompat.STATE_NONE);
                notifyAdapter(currentSong);
                break;
            case PlaybackStateCompat.STATE_STOPPED:
                pgPlayPauseLayout.setVisibility(View.INVISIBLE);
                btn_play.Pause();
                audioPg.setValue(0);
                if (currentSong != null) {
                    currentSong.setPlayState(PlaybackStateCompat.STATE_NONE);
                    notifyAdapter(currentSong);
                }
                break;
            case PlaybackStateCompat.STATE_BUFFERING:
                pgPlayPauseLayout.setVisibility(View.VISIBLE);
                if (currentSong != null) {
                    currentSong.setPlayState(PlaybackStateCompat.STATE_NONE);
                    notifyAdapter(currentSong);
                }
                break;
        }
    }

    @Override
    public void playSongComplete() {
        String timeString = "00.00";
        time_total_bottom.setText(timeString);
        time_total_slide.setText(timeString);
        time_progress_bottom.setText(timeString);
        time_progress_slide.setText(timeString);
        Log.e("setTcom",""+timeString);

        lineProgress.setLineProgress(0);
        audioPg.setValue(0);
    }

    @Override
    public void currentSeekBarPosition(int progress) {
        audioPg.setValue(progress);
        setPGTime(progress);
    }

    @Override
    public void playCurrent(int indexP, MediaMetaData currentAudio) {
        showMediaInfo(currentAudio);
        notifyAdapter(currentAudio);
    }

    @Override
    public void playNext(int indexP, MediaMetaData CurrentAudio) {
        showMediaInfo(CurrentAudio);
}

    @Override
    public void playPrevious(int indexP, MediaMetaData currentAudio) {
        showMediaInfo(currentAudio);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_forward:
                streamingManager.onSkipToNext();
                break;
            case R.id.btn_backward:
                streamingManager.onSkipToPrevious();
                break;
            case R.id.btn_play:
                if (currentSong != null) {
                    playPauseEvent(view);
                }
                break;
            case R.id.iv_home:
                onBackPressed();
                break;


        }
    }

    @Override
    public void onValueChanged(int value) {
        streamingManager.onSeekTo(value);
        streamingManager.scheduleSeekBarUpdate();
    }

    private void notifyAdapter(MediaMetaData media) {
        adapterMusic.notifyPlayState(media);
    }

    private void playPauseEvent(View v) {
        if (streamingManager.isPlaying()) {
            streamingManager.onPause();
            ((PlayPauseView) v).Pause();
        } else {
            streamingManager.onPlay(currentSong);

            ((PlayPauseView) v).Play();
        }
    }

    private void playSong(MediaMetaData media) {
        if (streamingManager != null) {
            streamingManager.onPlay(media);
            showMediaInfo(media);
        }
    }

    private void showMediaInfo(MediaMetaData media) {
        currentSong = media;
        audioPg.setValue(0);
        audioPg.setMin(0);
        audioPg.setMax(Integer.valueOf(media.getMediaDuration()) * 1000);
        setPGTime(0);
        setMaxTime();
        loadSongDetails(media);
    }

    private void configAudioStreamer() {
        streamingManager = AudioStreamingManager.getInstance(context);
        //Set PlayMultiple 'true' if want to playing sequentially one by one songs
        // and provide the list of songs else set it 'false'
        streamingManager.setPlayMultiple(true);
        streamingManager.setMediaList(listOfSongs);
        //If you want to show the Player Notification then set ShowPlayerNotification as true
        //and provide the pending intent so that after click on notification it will redirect to an activity
        streamingManager.setShowPlayerNotification(true);
        streamingManager.setPendingIntentAct(getNotificationPendingIntent());
    }

    private void uiInitialization() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        btn_play = (PlayPauseView) findViewById(R.id.btn_play);
        image_songAlbumArtBlur = (ImageView) findViewById(R.id.image_songAlbumArtBlur);
        image_songAlbumArt = (ImageView) findViewById(R.id.image_songAlbumArt);
        img_bottom_albArt = (ImageView) findViewById(R.id.img_bottom_albArt);
        btn_backward = (ImageView) findViewById(R.id.btn_backward);
        btn_forward = (ImageView) findViewById(R.id.btn_forward);
        audioPg = (Slider) findViewById(R.id.audio_progress_control);
        pgPlayPauseLayout = (RelativeLayout) findViewById(R.id.pgPlayPauseLayout);
        lineProgress = (LineProgress) findViewById(R.id.lineProgress);
        time_progress_slide = (TextView) findViewById(R.id.slidepanel_time_progress);
        time_total_slide = (TextView) findViewById(R.id.slidepanel_time_total);
        time_progress_bottom = (TextView) findViewById(R.id.slidepanel_time_progress_bottom);
        time_total_bottom = (TextView) findViewById(R.id.slidepanel_time_total_bottom);

        btn_backward.setOnClickListener(this);
        btn_forward.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        pgPlayPauseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });

        btn_play.Pause();

        changeButtonColor(btn_backward);
        changeButtonColor(btn_forward);

        text_songName = (TextView) findViewById(R.id.text_songName);
        text_songAlb = (TextView) findViewById(R.id.text_songAlb);
        txt_bottom_SongName = (TextView) findViewById(R.id.txt_bottom_SongName);
        txt_bottom_SongAlb = (TextView) findViewById(R.id.txt_bottom_SongAlb);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        slideBottomView = (RelativeLayout) findViewById(R.id.slideBottomView);
        slideBottomView.setVisibility(View.VISIBLE);
        slideBottomView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });

        audioPg.setMax(0);
        audioPg.setOnValueChangedListener(this);

        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset == 0.0f) {
                    isExpand = false;
                    slideBottomView.setVisibility(View.VISIBLE);
                    //slideBottomView.getBackground().setAlpha(0);
                } else if (slideOffset > 0.0f && slideOffset < 1.0f) {
                    //slideBottomView.getBackground().setAlpha((int) slideOffset * 255);
                } else {
                    //slideBottomView.getBackground().setAlpha(100);
                    isExpand = true;
                    slideBottomView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPanelExpanded(View panel) {
                isExpand = true;
            }

            @Override
            public void onPanelCollapsed(View panel) {
                isExpand = false;
            }

            @Override
            public void onPanelAnchored(View panel) {
            }

            @Override
            public void onPanelHidden(View panel) {
            }
        });
        musicList = (RecyclerView) findViewById(R.id.rView);
        adapterMusic = new AudioListAdapter(context, new ArrayList<MediaMetaData>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        adapterMusic.setListItemListener(new AudioListAdapter.ListItemListener() {
            @Override
            public void onItemClickListener(MediaMetaData media) {
                playSong(media);
            }
        });
        musicList.setLayoutManager(layoutManager);
        musicList.setAdapter(adapterMusic);

        this.options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_new_logo_web)
                .showImageForEmptyUri(R.drawable.ic_new_logo_web)
                .showImageOnFail(R.drawable.ic_new_logo_web).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

    }

    public void getAudioList(String accessTocken, final int page, int  limit, String userId, String category_id) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);



/*http://184.154.53.181/musicapp/api/backing_track_list?
encrypt_key=dfgdsfg
&bt_category_id=1
&user_id=1*/
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", accessTocken);
        params.put("user_id", userId);
        params.put("bt_category_id", category_id);
        params.put("current_page", ""+page);
        params.put("limit", ""+limit);


        Call<Backing_track_Model> call = apiService.getAudioSongsList(params);
        call.enqueue(new Callback<Backing_track_Model>() {
            @Override
            public void onResponse(Call<Backing_track_Model> call, retrofit2.Response<Backing_track_Model> response) {

                Backing_track_Model clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null && clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                    List<MediaMetaData> listArticle = new ArrayList<>();
                    try {
                        for (int i = 0; i < clientModel.getData().getBackingTrackList().size(); i++) {
                            MediaMetaData infoData = new MediaMetaData();
                            // JSONObject musicObj = array.getJSONObject(i);
                            infoData.setMediaId("" + clientModel.getData().getBackingTrackList().get(i).getBackingTrackId());
                            infoData.setMediaUrl("" + clientModel.getData().getBackingTrackList().get(i).getBackingTrackUrl());
                            infoData.setMediaTitle("" + clientModel.getData().getBackingTrackList().get(i).getBackingTrackName());
                            infoData.setMediaDuration(""+clientModel.getData().getBackingTrackList().get(i).getDuration());
                            infoData.setMediaArt("" + clientModel.getData().getBackingTrackList().get(i).getBackgroundImage());
                            listArticle.add(infoData);
                        }

                        if(currentpage==1) {

                            listOfSongs = listArticle;
                            collapsingToolbarLayout.setTitle(clientModel.getData().getBtCategoryTitle());
                            collapsingToolbarLayout.setSubtitle(clientModel.getData().getBtCategorySubTitle());

                        }
                            else
                        listOfSongs.addAll(listArticle);
                        if(clientModel.getData().getRemaining_count()>0)
                            loadMusicData();
                        remaining_out=clientModel.getData().getRemaining_count();
                        Utill.imageshow(context,iv_back_image,""+clientModel.getData().getBtCategoryImage());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                } else {

                    Utill.showCenteredToast(context, clientModel.getData().getMessage());
                }

                pDialog.dismiss();


            }


            @Override
            public void onFailure(Call<Backing_track_Model> call, Throwable t) {

               /* String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                //  Utill.showCenteredToast(context, getString(R.string.something_went_wrong));
                // Log error here since request failed
                Log.e("", t.toString());*/
                pDialog.dismiss();
            }
        });
    }

    private void loadMusicData() {


        //  listOfSongs = getMusicList();
        if (listOfSongs.size() > 0) {
            adapterMusic.refresh(listOfSongs);

            configAudioStreamer();
            checkAlreadyPlaying();
        }
        /*MusicBrowser.loadMusic(context, new MusicLoaderListener() {
            @Override
            public void onLoadSuccess(List<MediaMetaData> listMusic) {
                listOfSongs = listMusic;
                adapterMusic.refresh(listMusic);

                configAudioStreamer();
                checkAlreadyPlaying();
            }

            @Override
            public void onLoadFailed() {
                //TODO SHOW FAILED REASON
            }

            @Override
            public void onLoadError() {
                //TODO SHOW ERROR
            }
        });*/
    }

    private void checkAlreadyPlaying() {
        if (streamingManager.isPlaying()) {
            currentSong = streamingManager.getCurrentAudio();
            if (currentSong != null) {
                currentSong.setPlayState(streamingManager.mLastPlaybackState);
                showMediaInfo(currentSong);
                notifyAdapter(currentSong);
            }
        }
    }

    private void loadSongDetails(MediaMetaData metaData) {
        text_songName.setText(metaData.getMediaTitle());
        text_songAlb.setText(metaData.getMediaArtist());
        txt_bottom_SongName.setText(metaData.getMediaTitle());
        txt_bottom_SongAlb.setText(metaData.getMediaArtist());

        imageLoader.displayImage(metaData.getMediaArt(), image_songAlbumArtBlur, options, animateFirstListener);
        imageLoader.displayImage(metaData.getMediaArt(), image_songAlbumArt, options, animateFirstListener);
        imageLoader.displayImage(metaData.getMediaArt(), img_bottom_albArt, options, animateFirstListener);
    }

    private void setPGTime(int progress) {
        try {
            String timeString = "00.00";
            int linePG = 0;
            currentSong = streamingManager.getCurrentAudio();
            if (currentSong != null && progress != Long.parseLong(currentSong.getMediaDuration())) {
                timeString = DateUtils.formatElapsedTime(progress / 1000);
                Long audioDuration = Long.parseLong(currentSong.getMediaDuration());
                linePG = (int) (((progress / 1000) * 100) / audioDuration);
            }
           /* if(linePG==100) {
*//*
                time_progress_bottom.setText(timeString);
                time_progress_slide.setText(timeString);
                time_total_bottom.setText(timeString);
                time_total_slide.setText(timeString);
*//*
            }
            else
            {*/
                time_progress_bottom.setText(timeString);
                time_progress_slide.setText(timeString);
                lineProgress.setLineProgress(linePG);


 //           }
/*
            time_total_bottom.setText(timeString);
            time_total_slide.setText(timeString);
            time_progress_bottom.setText(timeString);
            time_progress_slide.setText(timeString);


         */

            Log.e("setT",""+timeString);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void setMaxTime() {
        try {
            String timeString = DateUtils.formatElapsedTime(Long.parseLong(currentSong.getMediaDuration()));
            time_total_bottom.setText(timeString);
            time_total_slide.setText(timeString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void changeButtonColor(ImageView imageView) {
        try {
            int color = Color.WHITE;
            imageView.setColorFilter(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PendingIntent getNotificationPendingIntent() {
        Intent intent = new Intent(context, MusicActivity.class);
        intent.setAction("openplayer");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        return mPendingIntent;
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            progressEvent(view, false);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            progressEvent(view, true);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 1000);
                    displayedImages.add(imageUri);
                }
            }
            progressEvent(view, true);
        }

    }
}