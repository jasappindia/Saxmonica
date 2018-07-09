package com.musicapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SubtitleCollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.musicapplication.R;
import com.musicapplication.adapter.GalleryGridListAdapter;
import com.musicapplication.adapter.SongsListAdapter;
import com.musicapplication.models.GalleryListModel;

import java.util.List;


public class AudioListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String Tag = AudioListActivity.class.getName();
    private Toolbar mToolbar;
    private ImageView iv_home;
    private RecyclerView rView;
    private LinearLayoutManager layoutManager;
    private SongsListAdapter adapter;
    private List songsListModels;
    private Context context;
    private SubtitleCollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);
        init();

    }

    private void init() {

        context = this;
        collapsingToolbarLayout=findViewById(R.id.collapsing_toolbar_title);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_home = mToolbar.findViewById(R.id.iv_home);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle("");
        getSupportActionBar().setTitle("");
        iv_home.setOnClickListener(this);
         rView = findViewById(R.id.rView);
        layoutManager = new LinearLayoutManager(context);
        rView.setLayoutManager(layoutManager);
        adapter = new SongsListAdapter(context,songsListModels);
        rView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (iv_home == v) {
            onBackPressed();
        }

    }
}
