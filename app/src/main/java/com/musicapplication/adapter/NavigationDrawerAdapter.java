package com.musicapplication.adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.musicapplication.R;
import com.musicapplication.activity.MainActivity;
import com.musicapplication.constants.Constant;
import com.musicapplication.fragment.Back_TracksFragment;
import com.musicapplication.fragment.BlogerFragment;
import com.musicapplication.fragment.GalleryFragment;
import com.musicapplication.fragment.ShopFragment;
import com.musicapplication.fragment.TeamFragment;
import com.musicapplication.fragment.TutorialFragment;
import com.musicapplication.models.DrawerItem;

import java.util.Collections;
import java.util.List;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<DrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
   // private Chatlocalyshareprefrence preference;

    public NavigationDrawerAdapter(Context context, List<DrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
         MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        DrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());


//        holder.iv.setImageResource(current.getDrawable());



        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0)
                    ((MainActivity)context).replaceFragment(new BlogerFragment(), Constant.FRAGMENT_BLOG);
                else if (position == 1)
                    ((MainActivity)context).replaceFragment(new Back_TracksFragment(), Constant.FRAGMENT_BACK_TRACKS);

                else if (position == 2) {
                    ((MainActivity)context).replaceFragment(new TutorialFragment(), Constant.FRAGMENT_TUTORIALS);


                } else if (position == 3) {
                    ((MainActivity)context).replaceFragment(new ShopFragment(), Constant.FRAGMENT_SHOP);


                } else if (position == 4)
                    ((MainActivity)context).replaceFragment(new GalleryFragment(), Constant.FRAGMENT_GALLERY);


            }
        });



    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public void notify(List<DrawerItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView iv;
        LinearLayout ll_layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            iv = (ImageView) itemView.findViewById(R.id.ivDrawerIcon);

        }
    }







}