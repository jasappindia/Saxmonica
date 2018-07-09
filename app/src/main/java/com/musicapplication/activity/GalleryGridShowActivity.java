package com.musicapplication.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.musicapplication.R;
import com.musicapplication.adapter.GalleryGridListAdapter;
import com.musicapplication.constants.Constant;
import com.musicapplication.models.GalleryListModel;
import com.musicapplication.utils.Utill;
import com.musicapplication.views.imageView.TouchImageView;

import java.util.List;

/**
 * Created by windows on 1/11/2018.
 */
public class GalleryGridShowActivity extends AppCompatActivity implements View.OnClickListener {
    List<GalleryListModel.Datadata.GalleryListdata.GalleryImagesdata> businessImageList;
    private ViewPager viewpager;
    private BannerShowAdapter bannerShowAdapter;
    private GalleryListModel.Datadata.GalleryListdata info;
    private int position = -1;
    private ViewGroup view;

    private Toolbar mToolbar;
    private TextView toolbarTitle;
    private ImageView iv_home,iv_edite;
    private GalleryListModel.Datadata.GalleryListdata galleryListModels;
    private Context context;


    private void init() {

        context = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        iv_home = mToolbar.findViewById(R.id.iv_home);
         mToolbar.findViewById(R.id.iv_edit_profile).setVisibility(View.INVISIBLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle("");
        getSupportActionBar().setTitle("");
        iv_home.setOnClickListener(this);

        if (galleryListModels.getGalleryName() != null && !galleryListModels.getGalleryName().equalsIgnoreCase(""))
            toolbarTitle.setText("" + galleryListModels.getGalleryName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //    new BasicUtill().CheckStatus(BusinessBannerShowActivity.this,0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallerygridshow);
         context = this;

        Bundle b = getIntent().getExtras();
        if (b != null) {
            info = (GalleryListModel.Datadata.GalleryListdata) b.getSerializable(Constant.IMAGEA_LIST);
            position = b.getInt(Constant.POSITION);
            galleryListModels=info;
            setImages();

        }
        init();



    }

    private void setImages() {
        viewpager = (ViewPager) findViewById(R.id.viewPager);
        bannerShowAdapter = new BannerShowAdapter(context, info);
        viewpager.setAdapter(bannerShowAdapter);
        if (position > -1)
            viewpager.setCurrentItem(position);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:

                onBackPressed();
                break;

        }
    }

    public class BannerShowAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public BannerShowAdapter(Context context,GalleryListModel.Datadata.GalleryListdata info) {
            mContext = context;
            businessImageList = info.getGalleryImages();
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            if (businessImageList != null)
                return businessImageList.size();
            else return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            TouchImageView imageView = (TouchImageView) itemView.findViewById(R.id.iv_product_image);
            //Glide.with(mContext).load(businessImageList.get(position).getImage()).into(imageView);
            Utill.imageshow(mContext,imageView,businessImageList.get(position).getImage());
            container.addView(itemView);
            if (position == 0)
                view = container;
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }

    }


}
