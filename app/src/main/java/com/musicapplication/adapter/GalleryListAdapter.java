package com.musicapplication.adapter;

import android.arch.lifecycle.GeneratedAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.musicapplication.R;
import com.musicapplication.activity.GalleryEventGridActivity;
import com.musicapplication.constants.Constant;
import com.musicapplication.models.GalleryListModel;
import com.musicapplication.models.TeamListModel;
import com.musicapplication.utils.Utill;
import com.musicapplication.views.imageView.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjani on 27/7/17.
 */

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.ViewHolder> {
    Context context;
    List<GalleryListModel.Datadata.GalleryListdata> list;

    public GalleryListAdapter(Context context, List<GalleryListModel.Datadata.GalleryListdata> list) {
        this.context = context;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallerylist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.tv_title.setText("" + list.get(position).getGalleryName());
        holder.tv_description.setText("" + list.get(position).getDescription());
        Utill.imageshow(context, holder.iv_event_image, "" + list.get(position).getMainImage());


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public void notifydatasetChange(List<GalleryListModel.Datadata.GalleryListdata> list) {

        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }


    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_event_image;
        TextView tv_title, tv_description;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_event_image = itemView.findViewById(R.id.iv_event_main_image);
            tv_title = itemView.findViewById(R.id.tv_event_title);
            tv_description = itemView.findViewById(R.id.tv_event_description);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, GalleryEventGridActivity.class);
                    GalleryListModel.Datadata.GalleryListdata galleryListdata=list.get(getAdapterPosition());
                    intent.putExtra(Constant.IMAGE_ARRAY,galleryListdata);
                    context.startActivity(intent);


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

