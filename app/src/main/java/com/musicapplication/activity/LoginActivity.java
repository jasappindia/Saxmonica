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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.musicapplication.R;
import com.musicapplication.constants.PrefranceKeyConstants;
import com.musicapplication.models.RegistrationModel;
import com.musicapplication.models.ResultModel;
import com.musicapplication.networkes.ApiClient;
import com.musicapplication.networkes.ApiInterface;
import com.musicapplication.prefrance.PreferenceUtil;
import com.musicapplication.utils.Utill;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 1201;
    private static final String TAG = LoginActivity.class.getName();
    Button btn_signin, btn_googleSignup;
    Context context;
    EditText edt_password, edt_email;
    LinearLayout ll_signup;
    GoogleSignInClient mGoogleSignInClient;
    private String encriptedKey = "afdsf";
    private String deviceId = "sdjhfjs";
    private TextView tv_forgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // gmail  ini

         gmailIni();
         init();
         Utill.keyboardHide(context, edt_email);

    }

    private void gmailIni() {

        // google login

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // user already sign in if account is null mean user is not registered already
        //  GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


    }

    private void signWithGmailIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void validation() {
        if (edt_email.getText().toString() != null && edt_email.getText().toString().equalsIgnoreCase("") && edt_email.getText().toString().length() < 4) {

            edt_email.setError(getString(R.string.errorMessage));

        } else if (!Utill.isValidEmail(edt_email.getText().toString())) {
            edt_email.setError(getString(R.string.errorEmail));


        } else if (edt_password.getText().toString() != null && edt_password.getText().toString().equalsIgnoreCase("") && edt_password.getText().toString().length() < 4) {

            edt_password.setError(getString(R.string.errorMessage));


        } else {
            sendUserLogin(edt_email.getText().toString(), "add", "jhjhsa", edt_password.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.ll_signup:
                startActivity(new Intent(context, SignupActivity.class));
                break;
            case R.id.btn_gmail:
                signWithGmailIn();
                break;

            case R.id.btn_signin:
                validation();
                break;

            case R.id.tv_forgot_pass:
                startActivity(new Intent(context,ForgotPassword.class));
                break;

        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.

            sendSocialRegistration(encriptedKey, deviceId, account.getEmail(),account.getDisplayName(),""+account.getPhotoUrl());

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

    private void init() {
        context = this;
        tv_forgot_pass=findViewById(R.id.tv_forgot_pass);

        edt_email = findViewById(R.id.edt_email);
        ll_signup = findViewById(R.id.ll_signup);
        edt_password = findViewById(R.id.edt_pass);
        btn_signin = findViewById(R.id.btn_signin);

        btn_googleSignup = findViewById(R.id.btn_gmail);
        btn_signin.setOnClickListener(this);
        btn_googleSignup.setOnClickListener(this);
        ll_signup.setOnClickListener(this);
         tv_forgot_pass.setOnClickListener(this);

    }

    public void sendSocialRegistration(String accessTocken, String deviceId, String emailid, String userName, final String user_Photo) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);


/*  encrypt_key=rtrtrttr&email=yogeshvishnoi@jasapp.com&password=123456&
        device_id=dsfgbdsfn&device_type=ANDROID&
           &login_type=GOOGLE*/
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", accessTocken);
        params.put("user_name", userName);
     //   params.put("user_name", userName);

        params.put("email", emailid);
        params.put("login_type", "GOOGLE");
        params.put("device_id", deviceId);
        params.put("device_type", "ANDROID");

        Call<RegistrationModel> call = apiService.getRegisterWithSocialData(params);
        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, retrofit2.Response<RegistrationModel> response) {
                pDialog.dismiss();

                RegistrationModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_ID, clientModel.getData().getUserDetails().getUserId(), context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.Emai_ID, clientModel.getData().getUserDetails().getEmail(), context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_NAME, clientModel.getData().getUserDetails().getUserName(), context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_PHOTO,""+user_Photo, context);

                        // set text  mobile number
                        startActivity(new Intent(context, MainActivity.class));
                        Utill.showCenteredToast(context, getString(R.string.login_successful));

                    } else {
                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                //  Utill.showCenteredToast(context, getString(R.string.something_went_wrong));
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }


    public void sendUserLogin(String email, String accessTocken, String deviceId, String password) {
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
        params.put("password", password);
        params.put("device_id", deviceId);
        params.put("device_type", "ANDROID");

        Call<RegistrationModel> call = apiService.getLoginData(params);
        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, retrofit2.Response<RegistrationModel> response) {
                pDialog.dismiss();

                RegistrationModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {
                        // set text  mobile number
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_ID,clientModel.getData().getUserDetails().getUserId(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.Emai_ID,clientModel.getData().getUserDetails().getEmail(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_NAME,clientModel.getData().getUserDetails().getUserName(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_PHOTO,clientModel.getData().getUserDetails().getProfile_image(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_CITYNAME,clientModel.getData().getUserDetails().getCity_name(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_CITYID,""+clientModel.getData().getUserDetails().getCity_id(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_STATENAME,clientModel.getData().getUserDetails().getState_name(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_STATEID,""+clientModel.getData().getUserDetails().getState_id(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_COUNTRY_NAME,clientModel.getData().getUserDetails().getCountry_name(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_COUNTRYID,""+clientModel.getData().getUserDetails().getCountry_id(),context);

                        context.startActivity(new Intent(context, MainActivity.class));
                        Utill.showCenteredToast(context, getString(R.string.login_successful));

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
