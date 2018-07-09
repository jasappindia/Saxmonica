package com.musicapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.musicapplication.R;
import com.musicapplication.constants.PrefranceKeyConstants;
import com.musicapplication.prefrance.PreferenceUtil;

public class SpleshActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh);
        context = this;
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_ID, context).equalsIgnoreCase(""))
                    startActivity(new Intent(SpleshActivity.this, LoginActivity.class));
                else
                    startActivity(new Intent(SpleshActivity.this, MainActivity.class));


            }
        }, 3000);


    }
}
