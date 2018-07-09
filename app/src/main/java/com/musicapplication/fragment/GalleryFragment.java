package com.musicapplication.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musicapplication.R;
import com.musicapplication.activity.MainActivity;
import com.musicapplication.adapter.GalleryListAdapter;
import com.musicapplication.adapter.TeamListAdapter;
import com.musicapplication.constants.Constant;
import com.musicapplication.constants.PrefranceKeyConstants;
import com.musicapplication.models.GalleryListModel;
import com.musicapplication.models.RegistrationModel;
import com.musicapplication.models.TeamListModel;
import com.musicapplication.networkes.ApiClient;
import com.musicapplication.networkes.ApiInterface;
import com.musicapplication.prefrance.PreferenceUtil;
import com.musicapplication.utils.Utill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {


    RecyclerView rView;
    LinearLayoutManager layoutManager;
    GalleryListAdapter adapter;
    List<GalleryListModel.Datadata.GalleryListdata> galleryListModels;
    Context context;
    View view;
    String encryptkey;


    int currentPage=1,limit=20;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_gallery, container, false);
        init(view);
       return view;

    }



    private void init(View view) {
        context=getActivity();
        encryptkey="jadfshfdsah";
        rView=view.findViewById(R.id.rView);
        galleryListModels=new ArrayList<>();
        layoutManager=new LinearLayoutManager(context);
        rView.setLayoutManager(layoutManager);
        adapter=new GalleryListAdapter(context,galleryListModels);
        rView.setAdapter(adapter);
        getGallerylist(encryptkey,""+currentPage,""+limit,PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_ID,context));


    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)context).setToolbarTitle(""+ Constant.FRAGMENT_GALLERY_NAME);
    }

//

    public void getGallerylist(String accessTocken, String currentpage, String limit,String userId) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);


/*  encrypt_key=rtrtrttr&email=yogeshvishnoi@jasapp.com&password=123456&
        device_id=dsfgbdsfn&device_type=ANDROID&
           &login_type=GOOGLE*/
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", accessTocken);
        params.put("user_id", userId);
        //   params.put("user_name", userName);
        params.put("current_page", currentpage);
        params.put("limit", limit);


        Call<GalleryListModel> call = apiService.getGalleryList(params);
        call.enqueue(new Callback<GalleryListModel>() {
            @Override
            public void onResponse(Call<GalleryListModel> call, retrofit2.Response<GalleryListModel> response) {
                pDialog.dismiss();

                GalleryListModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {
                        adapter.notifydatasetChange(clientModel.getData().getGalleryList());
                       if(clientModel.getData().getRemainingCount()>0)
                           currentPage++;

                    } else {
                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<GalleryListModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                //  Utill.showCenteredToast(context, getString(R.string.something_went_wrong));
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }






}
