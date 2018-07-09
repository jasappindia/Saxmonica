package com.musicapplication.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musicapplication.R;
import com.musicapplication.activity.MainActivity;
import com.musicapplication.constants.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    Context context;
    View view;


    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_shop, container, false);
        context=getActivity();
    return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)context).setToolbarTitle(""+ Constant.FRAGMENT_SHOP_NAME);





    }

}
