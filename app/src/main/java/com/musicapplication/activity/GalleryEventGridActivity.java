package com.musicapplication.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.musicapplication.R;
import com.musicapplication.adapter.GalleryGridListAdapter;
import com.musicapplication.adapter.GalleryListAdapter;
import com.musicapplication.constants.Constant;
import com.musicapplication.models.GalleryListModel;
import com.musicapplication.other.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryEventGridActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String Tag = GalleryEventGridActivity.class.getName();
    private Toolbar mToolbar;
    private TextView toolbarTitle;
    private ImageView iv_home;
    private RecyclerView rView;
    private GridLayoutManager layoutManager;
    private GalleryGridListAdapter adapter;
    private GalleryListModel.Datadata.GalleryListdata galleryListModels;
    private Context context;
    private int remaining_count=


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_event_grid);

        if(getIntent().getExtras()!=null)
        {
            galleryListModels= (GalleryListModel.Datadata.GalleryListdata) getIntent().getExtras().getSerializable(Constant.IMAGE_ARRAY);
        }

        init();


    }

    private void init() {

        context = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle=mToolbar.findViewById(R.id.toolbar_title);
        iv_home=mToolbar.findViewById(R.id.iv_home);
        mToolbar.findViewById(R.id.iv_edit_profile).setVisibility(View.INVISIBLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle("");
        getSupportActionBar().setTitle("");
        iv_home.setOnClickListener(this);

       if(galleryListModels.getGalleryName()!=null && !galleryListModels.getGalleryName().equalsIgnoreCase(""))
        toolbarTitle.setText(""+galleryListModels.getGalleryName());
        rView=findViewById(R.id.rView);
        layoutManager=new GridLayoutManager(context,3);
        rView.setLayoutManager(layoutManager);
        adapter=new GalleryGridListAdapter(context,galleryListModels,galleryListModels.getGalleryImages());
        rView.setAdapter(adapter);


        rView.addOnScrollListener(new EndlessScrollListener(layoutManager)
        {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                 if(totalItemsCount==)





            }






        });








    }

    @Override
    public void onClick(View v) {
        if(iv_home==v)
        {
            onBackPressed();
        }

    }
}
