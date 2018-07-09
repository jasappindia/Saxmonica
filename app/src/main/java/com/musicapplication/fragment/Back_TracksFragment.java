package com.musicapplication.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musicapplication.R;
import com.musicapplication.activity.MainActivity;
import com.musicapplication.activity.MusicActivity;
import com.musicapplication.constants.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class Back_TracksFragment extends Fragment implements View.OnClickListener {


    View view;
    Context context;

    public Back_TracksFragment() {
        // Required empty public constructor
    }
//
/*
*
* */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_back__tracks, container, false);
        context=getActivity();
        init(view);

    return view;
    }

    private void init(View view) {

        view.findViewById(R.id.ib_cover).setOnClickListener(this);
        view.findViewById(R.id.ib_blue).setOnClickListener(this);
        view.findViewById(R.id.ib_rock).setOnClickListener(this);
        view.findViewById(R.id.ib_electronic).setOnClickListener(this);
        view.findViewById(R.id.ib_reggae).setOnClickListener(this);
        view.findViewById(R.id.jazz).setOnClickListener(this);


    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)context).setToolbarTitle(""+ Constant.FRAGMENT_BACK_TRACKS_NAME);
    }















        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ib_cover:
                    //TODO implement


                    callMusicActivity(Constant.COVER_ID,Constant.COVER_TITLE,Constant.COVER_SUB_TITLE);

                    break;
                case R.id.ib_blue:
                    //TODO implement
                    callMusicActivity(Constant.BLUES_ID,Constant.BLUES_TITLE,Constant.BLUES_SUB_TITLE);
                    break;
                case R.id.ib_rock:
                    //TODO implement
                    callMusicActivity(Constant.ROCK_ID,Constant.ROCK_TITLE,Constant.ROCK_SUB_TITLE);

                    break;
                case R.id.ib_electronic:
                    //TODO implement
                    callMusicActivity(Constant.ELECTRONIC_ID,Constant.ELECTRONIC_TITLE,Constant.ELECTRONIC_SUB_TITLE);

                    break;
                case R.id.ib_reggae:
                    //TODO implement
                    callMusicActivity(Constant.REGGE_ID,Constant.REGGE_TITLE,Constant.REGGE_SUB_TITLE);

                    break;
                case R.id.jazz:

                    callMusicActivity(Constant.SPAC_JAZZ_ID,Constant.SPAC_JAZZ_TITLE,Constant.SPAC_JAZZ_SUB_TITLE);
                    //TODO implement
                    break;
            }
        }

        public void callMusicActivity(String catId,String title,String subtitle)
        {
            Intent intent=new Intent(context, MusicActivity.class);
            intent.putExtra(Constant.BACK_TRACKING_CATEGORY_ID,catId);
            intent.putExtra(Constant.BACKING_TRACKING_CATEGORY_TITLE,title);
            intent.putExtra(Constant.BACKING_TRACKING_CATEGORY_SUBTITLE,subtitle);
            context.startActivity(intent);



        }

    }







