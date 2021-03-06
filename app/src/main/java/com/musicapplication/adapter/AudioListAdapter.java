package com.musicapplication.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.musicapplication.R;
import com.musicapplication.models.GalleryListModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import dm.audiostreamer.MediaMetaData;

/**
 * Created by anjani on 27/7/17.
 */

public class AudioListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ListItemListener listItemListener;
    Context context;
    List<?> list;
    private List<MediaMetaData> musicList;
    private Context mContext;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private ColorStateList colorPlay;
    private ColorStateList colorPause;

    public AudioListAdapter(Context context, List<MediaMetaData> authors) {
        this.musicList = authors;
        this.mContext = context;
        this.colorPlay = ColorStateList.valueOf(context.getResources().getColor(R.color.play));
        this.colorPause = ColorStateList.valueOf(context.getResources().getColor(R.color.pause));
        this.options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_new_logo)
                .showImageForEmptyUri(R.mipmap.ic_new_logo)
                .showImageOnFail(R.mipmap.ic_new_logo).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    private static void progressEvent(View v, boolean isShowing) {
        try {
            RelativeLayout rl = (RelativeLayout) ((ImageView) v).getParent();
            ProgressBar pg = (ProgressBar) rl.findViewById(R.id.pg);
            pg.setVisibility(isShowing ? View.GONE : View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    /*    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_allsongsitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
*/


        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case item:
                View v1 = inflater.inflate(R.layout.inflate_allsongsitem, parent, false);
                viewHolder = new ViewHolder(v1);
                break;
            case page:
                View v2 = inflater.inflate(R.layout.paginationlayout, parent, false);
                viewHolder = new Pagination(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.inflate_allsongsitem, parent, false);
                viewHolder = new ViewHolder(v3);

        }

        return viewHolder;
    }





    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, final int position) {



        switch (holder1.getItemViewType()) {
            case item:
                ViewHolder holder = (ViewHolder) holder1;
                MediaMetaData media = musicList.get(position);
                holder.mediaTitle.setText(media.getMediaTitle());
                holder.MediaDesc.setText(media.getMediaArtist());
                holder.playState.setImageDrawable(getDrawableByState(mContext, media.getPlayState()));
                String mediaArt = media.getMediaArt();
                imageLoader.displayImage(mediaArt, holder.mediaArt, options, animateFirstListener);
                break;
            case page:
                Pagination pagination = (Pagination) holder1;
                if (isFooter) {
                    if (position == (list.size() - 1)) {
                        pagination.progressBar.setVisibility(View.VISIBLE);
                    }
                } else {
                    pagination.progressBar.setVisibility(View.GONE);

                }


                break;

        }





    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public void notifydatasetChange(List<GalleryListModel.Datadata.GalleryListdata.GalleryImagesdata> list) {

        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }


    }

    public void refresh(List<MediaMetaData> musicList) {
        if (this.musicList != null) {
            this.musicList.clear();
        }
        this.musicList.addAll(musicList);
        notifyDataSetChanged();
    }

    public void notifyPlayState(MediaMetaData metaData) {
        if (this.musicList != null && metaData != null) {
            int index = this.musicList.indexOf(metaData);
            //TODO SOMETIME INDEX RETURN -1 THOUGH THE OBJECT PRESENT IN THIS LIST
            if (index == -1) {
                for (int i = 0; i < this.musicList.size(); i++) {
                    if (this.musicList.get(i).getMediaId().equalsIgnoreCase(metaData.getMediaId())) {
                        index = i;
                        break;
                    }
                }
            }
            if (index > 0 && index < this.musicList.size()) {
                this.musicList.set(index, metaData);
            }
        }
        notifyDataSetChanged();
    }

    private Drawable getDrawableByState(Context context, int state) {
        switch (state) {
            case PlaybackStateCompat.STATE_NONE:
                Drawable pauseDrawable = ContextCompat.getDrawable(context, R.drawable.ic_play);
                DrawableCompat.setTintList(pauseDrawable, colorPlay);
                return pauseDrawable;
            case PlaybackStateCompat.STATE_PLAYING:
                AnimationDrawable animation = (AnimationDrawable) ContextCompat.getDrawable(context, R.drawable.equalizer);
                DrawableCompat.setTintList(animation, colorPlay);
                animation.start();
                return animation;
            case PlaybackStateCompat.STATE_PAUSED:
                Drawable playDrawable = ContextCompat.getDrawable(context, R.drawable.equalizer);
                DrawableCompat.setTintList(playDrawable, colorPause);
                return playDrawable;
            default:
                Drawable noneDrawable = ContextCompat.getDrawable(context, R.drawable.ic_play);
                DrawableCompat.setTintList(noneDrawable, colorPlay);
                return noneDrawable;
        }
    }

    public void setListItemListener(ListItemListener listItemListener) {
        this.listItemListener = listItemListener;
    }


    public interface ListItemListener {
        void onItemClickListener(MediaMetaData media);
    }


    @Override
    public int getItemViewType(int position) {

        if (isFooter && position == (list.size() - 1)) {
            return page;
        } else {
            return  item;
        }

    }

    public void addFooter() {
        isFooter = true;
        notifyDataSetChanged();
    }

    public void removeFooter() {
        isFooter = false;
        notifyDataSetChanged();
    }



    private boolean isFooter = false;
    public static final int page = 2, item = 3;

    public class Pagination extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public Pagination(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progresspager);


        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mediaArt;
        public ImageView playState;
        public TextView mediaTitle;
        public TextView MediaDesc;


        public ViewHolder(View itemView) {
            super(itemView);

            mediaArt = (ImageView) itemView.findViewById(R.id.img_mediaArt);
            playState = (ImageView) itemView.findViewById(R.id.img_playState);
            mediaTitle = (TextView) itemView.findViewById(R.id.text_mediaTitle);
            MediaDesc = (TextView) itemView.findViewById(R.id.text_mediaDesc);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listItemListener != null) {
                        listItemListener.onItemClickListener(musicList.get(getAdapterPosition()));
                    }
                }
            });


        }
    }

    private class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

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
                    FadeInBitmapDisplayer.animate(imageView, 200);
                    displayedImages.add(imageUri);
                }
            }
            progressEvent(view, true);
        }

    }


}

