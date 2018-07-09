package com.musicapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.musicapplication.R;
import com.musicapplication.models.TeamListModel;
import com.musicapplication.models.TutorialListModel;
import com.musicapplication.views.imageView.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjani on 27/7/17.
 */

public class TutorialListAdapter extends RecyclerView.Adapter<TutorialListAdapter.ViewHolder> {
    Context context;
    List<TutorialListModel> list;

    public TutorialListAdapter(Context context, List<TutorialListModel> list) {
        this.context = context;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {






    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void notifydatasetChange(List<TutorialListModel> list) {

        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView user_image;
        TextView tv_userName,tv_address,tv_description;

        public ViewHolder(View itemView) {
            super(itemView);
            user_image=itemView.findViewById(R.id.iv_userImage);
            tv_userName=itemView.findViewById(R.id.tv_username);
            tv_address=itemView.findViewById(R.id.tv_address);
            tv_description=itemView.findViewById(R.id.tv_description);


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

