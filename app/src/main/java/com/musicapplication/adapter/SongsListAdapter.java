package com.musicapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.musicapplication.R;
import com.musicapplication.activity.AudioListActivity;
import com.musicapplication.activity.GalleryGridShowActivity;
import com.musicapplication.constants.Constant;
import com.musicapplication.models.GalleryListModel;
import com.musicapplication.utils.Utill;

import java.util.ArrayList;
import java.util.List;

import dm.audiostreamer.MediaMetaData;

/**
 * Created by anjani on 27/7/17.
 */

public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.ViewHolder> {
    Context context;
    List<?> list;
    GalleryListModel.Datadata.GalleryListdata info;

    public SongsListAdapter(Context context, List<GalleryListModel.Datadata.GalleryListdata.GalleryImagesdata> list) {
        this.context = context;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audiosongslist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void notifydatasetChange(List<GalleryListModel.Datadata.GalleryListdata.GalleryImagesdata> list) {

        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_audiotitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_audiotitle = itemView.findViewById(R.id.tv_audio_title);





            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //( (AudioListActivity)context).playSong(null);

       }
            });


        }
    }
/*

*//*
public void customDialog(final Context context, final List<CityListModel.citydata.CityListData> list) {
    dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

    dialog.setContentView(R.layout.popupwindowcitylistdialog);

    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    View popupView = layoutInflater.inflate(R.layout.popupwindowcitylistdialog, null);
    RecyclerView recyclerView= (RecyclerView) dialog.findViewById(R.id.rView);

    CityListAdapter adapter=new CityListAdapter(context,list);
    recyclerView.setAdapter(adapter);


    *//****************** ***************************//*

    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerClick_Listener() {
        @Override
        public void onClick(View view, int position) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                tv_cityName.setText(list.get(position).getName());

            }




        }

        @Override
        public void onLongClick(View view, int position) {

        }
    }));



    dialog.show();
    dialog.setCanceledOnTouchOutside(true);
}*/
}

