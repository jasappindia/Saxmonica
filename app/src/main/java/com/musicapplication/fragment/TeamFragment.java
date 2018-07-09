package com.musicapplication.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musicapplication.R;
import com.musicapplication.activity.MainActivity;
import com.musicapplication.adapter.TeamListAdapter;
import com.musicapplication.constants.Constant;
import com.musicapplication.models.TeamListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {

    RecyclerView rView;
    LinearLayoutManager layoutManager;
    TeamListAdapter adapter;
    List<TeamListModel> teamListModelList;
    Context context;
    View view;


    public TeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_team, container, false);
        init(view);
        return view;
    }



    private void init(View view) {

        context=getActivity();
        rView=view.findViewById(R.id.rView);
        teamListModelList=new ArrayList<>();
        adapter=new TeamListAdapter(context,teamListModelList);
        rView.setAdapter(adapter);


    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)context).setToolbarTitle(""+ Constant.FRAGMENT_THE_TEAM_NAME);





    }

}
