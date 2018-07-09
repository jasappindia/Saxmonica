package com.musicapplication.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.musicapplication.R;
import com.musicapplication.constants.Constant;
import com.musicapplication.fragment.Back_TracksFragment;
import com.musicapplication.fragment.FragmentDrawer;
import com.musicapplication.models.Backing_track_Model;
import com.musicapplication.models.GalleryListModel;
import com.musicapplication.networkes.ApiClient;
import com.musicapplication.networkes.ApiInterface;
import com.musicapplication.utils.Utill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, View.OnClickListener {

    public static final String Tag = "MainActivity";
    private static final int REQUEST_PERMISSIONS = 152;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private DrawerLayout drawer;
     public  RelativeLayout rl_relative_layout;
    //  private Chatlocalyshareprefrence chatlocalyshareprefrence;

    // get  email  function
    private Context context;
    private ProgressDialog progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        replaceFragment(new Back_TracksFragment(),Constant.FRAGMENT_BACK_TRACKS);


    }



    private void init() {
        context = MainActivity.this;

        rl_relative_layout=findViewById(R.id.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle("");
        getSupportActionBar().setTitle("");

        //mToolbar.setTitleTextColor(getResources().getColor(R.color.text_color_light));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, drawer, mToolbar);
        drawerFragment.setDrawerListener(this);

    }

    public void setToolbarTitle(String title){
      TextView toolBarTitle=  mToolbar.findViewById(R.id.toolbar_title);
      toolBarTitle.setText(title);

    }


    @Override
    public void onClick(View view) {

        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {


            drawer.closeDrawer(GravityCompat.START);

        }

    }


// permission request application in

    // code for  permission work  in application
    @TargetApi(Build.VERSION_CODES.M)
    public void requestMultiplePermissions() {


        //String readSms = Manifest.permission.READ_SMS;
        String callPhone = Manifest.permission.CALL_PHONE;
        String camera = Manifest.permission.CAMERA;
        String readContact = Manifest.permission.READ_CONTACTS;
        String readStorage = Manifest.permission.READ_EXTERNAL_STORAGE;
        String writeStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;


        // int hasreadSms = context.checkSelfPermission(readSms);
        int hascallPhone = context.checkSelfPermission(callPhone);
        int hasreadcontact = context.checkSelfPermission(readContact);
        int hascamera = context.checkSelfPermission(camera);
        int hasreadstorage = context.checkSelfPermission(readStorage);
        int haswritestorage = context.checkSelfPermission(writeStorage);

        List<String> permissions = new ArrayList<String>();
      /*  if (hasreadSms != PackageManager.PERMISSION_GRANTED) {
            permissions.add(readSms);
        }
       */
        if (hascallPhone != PackageManager.PERMISSION_GRANTED) {
            permissions.add(callPhone);
        }

        if (hasreadcontact != PackageManager.PERMISSION_GRANTED) {
            permissions.add(readContact);
        }
        if (hascamera != PackageManager.PERMISSION_GRANTED) {
            permissions.add(camera);
        }
        if (haswritestorage != PackageManager.PERMISSION_GRANTED) {
            permissions.add(writeStorage);
        }
        if (hasreadstorage != PackageManager.PERMISSION_GRANTED) {
            permissions.add(readStorage);
        }

        if (!permissions.isEmpty()) {
            String[] params = permissions.toArray(new String[permissions.size()]);
            requestPermissions(params, REQUEST_PERMISSIONS);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {


    }


    // get fragment replace

    public void replaceFragment(Fragment inputfragment, String fragmenttitle) {


        Fragment fragment = inputfragment;

       /* if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, fragment).addToBackStack(null).commit();

            //  replaceFragment(fragment,title);

        }

*/
        if (fragment != null) {


            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    Log.e("fragment name", fragmenttitle + " " + getSupportFragmentManager().getBackStackEntryAt(i).getName());
                    if (fragmenttitle.equalsIgnoreCase((getSupportFragmentManager().getBackStackEntryAt(i).getName()))) {
                        int count = getSupportFragmentManager().getBackStackEntryCount();
                        Log.e("fragment name", fragmenttitle + " " + count);

                        getSupportFragmentManager().popBackStack(fragmenttitle, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        int count2 = getSupportFragmentManager().getBackStackEntryCount();
                        Log.e("fragment name2", fragmenttitle + " " + count2);

                    }

                }
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, fragment).addToBackStack(fragmenttitle).commit();

        }

    }


    /// delete data from databse

    public void onBackPressed() {


        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {


                super.onBackPressed();

            } else {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("" + getString(R.string.app_name).toUpperCase());
                alertDialogBuilder.setIcon(R.mipmap.ic_logo);

                alertDialogBuilder
                        .setMessage("" + getString(R.string.str_exitmessage))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.str_yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                MainActivity.this.finish();
                            }
                        })


                        .setNegativeButton(getString(R.string.str_no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }












}









