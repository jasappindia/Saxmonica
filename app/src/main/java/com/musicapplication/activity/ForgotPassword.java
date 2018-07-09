package com.musicapplication.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.musicapplication.R;
import com.musicapplication.constants.PrefranceKeyConstants;
import com.musicapplication.models.RegistrationModel;
import com.musicapplication.networkes.ApiClient;
import com.musicapplication.networkes.ApiInterface;
import com.musicapplication.prefrance.PreferenceUtil;
import com.musicapplication.utils.Utill;

import java.util.HashMap;

import javax.xml.validation.Validator;

import retrofit2.Call;
import retrofit2.Callback;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    EditText edt_emailId;
    Button btn_submit;
    Context context;
    private String encryptedKey;
    ImageView iv_forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();

    }

    private void init() {
     context=this;
     encryptedKey="kdfsakj";
     edt_emailId=findViewById(R.id.edt_emil);
     btn_submit=findViewById(R.id.btn_submit);
        iv_forgotPass=findViewById(R.id.iv_home);

     btn_submit.setOnClickListener(this);
     iv_forgotPass.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             onBackPressed();

         }
     });


    }

    @Override
    public void onClick(View v) {

        if (edt_emailId.getText().toString() != null && edt_emailId.getText().toString().equalsIgnoreCase("") && edt_emailId.getText().toString().length() < 4) {

            edt_emailId.setError(getString(R.string.errorMessage));

        } else if (!Utill.isValidEmail(edt_emailId.getText().toString())) {
            edt_emailId.setError(getString(R.string.errorEmail));


        }
        else
        {

        sendForgotPass(edt_emailId.getText().toString().trim(),encryptedKey);

        }
    }



    public void sendForgotPass(String email, String accessTocken) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

      /*
                 encrypt_key=rtrtrttr&
                 email=yoges h@jasapp.com&
                 password=123456
                &device_id=dsfgbdsfn&
                device_type=ANDROID
      */
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", accessTocken);
        params.put("email", email);

        Call<RegistrationModel> call = apiService.getForgotPassword(params);
        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, retrofit2.Response<RegistrationModel> response) {
                pDialog.dismiss();

                RegistrationModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {
                        // set text  mobile number

                          context.startActivity(new Intent(context, LoginActivity.class));
                          Utill.showCenteredToast(context, clientModel.getData().getMessage());

                    } else {


                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }




}
