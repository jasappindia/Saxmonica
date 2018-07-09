package com.musicapplication.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.musicapplication.R;
import com.musicapplication.constants.PrefranceKeyConstants;
import com.musicapplication.models.CityListModel;
import com.musicapplication.models.CountryListModel;
import com.musicapplication.models.RegistrationModel;
import com.musicapplication.models.ResultModel;
import com.musicapplication.models.StateListModel;
import com.musicapplication.networkes.ApiClient;
import com.musicapplication.networkes.ApiInterface;
import com.musicapplication.prefrance.PreferenceUtil;
import com.musicapplication.utils.Utill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    String userId = "1", encryptKey = "hahfd";
    String gender = "";
    String age = "";
    private Context context;
    private EditText edt_userName, edt_password, edt_email;
    private TextView tv_cityId, tv_countryId, tv_stateId, tv_gender, tv_age;
    private Button btn_sign_up;
    private String cityId = "0";
    private String countryId = "0";
    private String stateId = "0";
    private List<CityListModel.citydata.CityListData> cityList;
    private List<CountryListModel.Datadata.CountryListdata> countryList;
    private List<StateListModel.Datadata.StateListdata> stateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        edt_email = findViewById(R.id.edt_email);
        edt_userName = findViewById(R.id.edt_userName);
        edt_password = findViewById(R.id.edt_pass);
        tv_cityId = findViewById(R.id.tv_cityId);
        tv_stateId = findViewById(R.id.tv_stateId);


        tv_gender = findViewById(R.id.tv_gender);
        tv_age = findViewById(R.id.tv_age);

        tv_countryId = findViewById(R.id.tv_countryId);
        btn_sign_up = findViewById(R.id.btn_signup);
        btn_sign_up.setOnClickListener(this);
        tv_countryId.setOnClickListener(this);
        tv_cityId.setOnClickListener(this);
        tv_stateId.setOnClickListener(this);
        tv_age.setOnClickListener(this);
        tv_gender.setOnClickListener(this);

    }

    private void init() {

        context = this;
        countryList = new ArrayList<>();
        cityList = new ArrayList<>();
        stateList = new ArrayList<>();
        getCountryList(userId, encryptKey);

    }

    public void validation() {


        if (edt_userName.getText().toString() != null && edt_userName.getText().toString().equalsIgnoreCase("") && edt_userName.getText().toString().length() < 4) {
            edt_userName.setError(getString(R.string.errorMessage));

        } else if (edt_email.getText().toString() != null && edt_email.getText().toString().equalsIgnoreCase("") && edt_email.getText().toString().length() < 4) {
            edt_email.setError(getString(R.string.errorMessage));

        } else if (!Utill.isValidEmail(edt_email.getText().toString())) {
            edt_email.setError(getString(R.string.errorEmail));

        } else if (edt_password.getText().toString() != null && edt_password.getText().toString().equalsIgnoreCase("") && edt_password.getText().toString().length() < 4) {
            edt_password.setError(getString(R.string.errorMessage));

        } else if (cityId.equalsIgnoreCase("0")) {
            Utill.showCenteredToast(context, "Please select city");
        } else if (stateId.equalsIgnoreCase("0")) {
            Utill.showCenteredToast(context, "Please select state");
        } else if (countryId.equalsIgnoreCase("0")) {


            Utill.showCenteredToast(context, "Please select country");

        } else if (gender.equalsIgnoreCase("")) {


            Utill.showCenteredToast(context, "Please select gender");

        } else if (tv_age.getText().toString().trim().equalsIgnoreCase("")) {


            Utill.showCenteredToast(context, "Please select age");

        } else {
            sendRegistration(edt_userName.getText().toString(), "add", "jhjhsa", edt_password.getText().toString(), "" + cityId, "" + countryId, stateId, edt_email.getText().toString().trim());
        }


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.tv_countryId:
                if (countryList.size() > 0)
                    onCreateDialogcountrySingleChoice().show();
                break;
            case R.id.tv_cityId:

                if (cityList.size() > 0) {
                    onCreateDialogCitySingleChoice().show();
                }
                break;
            case R.id.tv_stateId:

                if (stateList.size() > 0) {
                    onCreateDialogStateSingleChoice().show();
                }
                break;

            case R.id.tv_age:


                onCreateDialogage().show();

                break;
            case R.id.tv_gender:


                onCreateDialogGender().show();

                break;

            case R.id.btn_signup:
                validation();
                break;


        }

    }

    public void sendRegistration(String username, String accessTocken, String deviceId, String password, final String cityid, final String countryid, final String stateid, String emailid) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

              /*  encrypt_key=rtrtrttr&
                user_name=Yogesh&email=yogesh@jasapp.com&
                password=123456
                &country_id=1&city_id=1&
                device_id=dsfgbdsfn
                &device_type=ANDROID
      */
        HashMap<String, String> params = new HashMap<>();
        params.put("encrypt_key", accessTocken);
        params.put("user_name", username);
        params.put("password", password);
        params.put("country_id", countryid);
        params.put("city_id", cityid);
        params.put("email", emailid);
        params.put("state_id", stateid);
        params.put("age", "" + age);
        params.put("gender", "" + gender);


        params.put("device_id", "" + " asjdj");
        params.put("device_type", "ANDROID");

        Call<RegistrationModel> call = apiService.getRegisterData(params);
        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, retrofit2.Response<RegistrationModel> response) {
                pDialog.dismiss();

                RegistrationModel clientModel = response.body();

                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {

                      /*  PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_ID,clientModel.getData().getUserDetails().getUserId(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.Emai_ID,clientModel.getData().getUserDetails().getEmail(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_NAME,clientModel.getData().getUserDetails().getUserName(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_PHOTO,clientModel.getData().getUserDetails().getProfile_image(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_CITYNAME,clientModel.getData().getUserDetails().getCity_name(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_CITYID,""+clientModel.getData().getUserDetails().getCity_id(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_STATENAME,clientModel.getData().getUserDetails().getState_name(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_STATEID,""+clientModel.getData().getUserDetails().getState_id(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_COUNTRY_NAME,clientModel.getData().getUserDetails().getCountry_name(),context);
                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_COUNTRYID,""+clientModel.getData().getUserDetails().getCountry_id(),context);

                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_AGE,""+clientModel.getData().getUserDetails().getAge(),context);


                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_GENDER,""+clientModel.getData().getUserDetails().getGender(),context);
*/
                        // set text  mobile number                        PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_AGE,""+clientModel.getData().getUserDetails().getAge(),context);

                        startActivity(new Intent(context, LoginActivity.class));
                        finish();
                        // Utill.showCenteredToast(context,getString(R.string.login_successful));
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

                //  Utill.showCenteredToast(context, getString(R.string.something_went_wrong));
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });
    }

    public Dialog onCreateDialogcountrySingleChoice() {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> countrylist = new ArrayList<>();
        for (CountryListModel.Datadata.CountryListdata countryListdata : countryList) {
            countrylist.add(countryListdata.getCountryName());
        }


        final String[] stateArray = countrylist.toArray(new String[countryList.size()]);
        builder.setTitle("Select Country")

// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setItems(stateArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
//                        plan=planList.get(which).getSpName();
                        String state = stateArray[which];
//                        post[0] =which;
                        countryId = countryList.get(which).getCountryId();
                        tv_countryId.setText(state);
                        cityId = "0";
                        stateId = "0";
                        tv_cityId.setText("city");
                        tv_stateId.setText("State");
                        stateList.clear();
                        cityList.clear();
                        getStateList(userId, countryId, encryptKey);

                    }
                });

        return builder.create();
    }

    public Dialog onCreateDialogGender() {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> genderlist = new ArrayList<>();
        genderlist.add("Male");
        genderlist.add("Female");
        genderlist.add("Other");


        final String[] stateArray = genderlist.toArray(new String[stateList.size()]);
        builder.setTitle("Select Gender")

// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setItems(stateArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
//                        plan=planList.get(which).getSpName();
                        String other = stateArray[which];
                        gender = other;
                        tv_gender.setText(gender);
//                        post[0] =which;


                    }
                });

        return builder.create();
    }

    public Dialog onCreateDialogage() {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> agelist = new ArrayList<>();
        for (int i = 1; i < 101; i++) {
            agelist.add("" + i);
        }


        final String[] stateArray = agelist.toArray(new String[stateList.size()]);
        builder.setTitle("Select Age")

// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setItems(stateArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
//                        plan=planList.get(which).getSpName();
                        String other = stateArray[which];
                        age = other;
                        tv_age.setText(age);
//                        post[0] =which;


                    }
                });

        return builder.create();
    }


    public Dialog onCreateDialogStateSingleChoice() {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> statelist = new ArrayList<>();
        for (StateListModel.Datadata.StateListdata stateListdata : stateList) {
            statelist.add(stateListdata.getStateName());
        }


        final String[] stateArray = statelist.toArray(new String[stateList.size()]);
        builder.setTitle("Select State")

// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setItems(stateArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
//                        plan=planList.get(which).getSpName();
                        String state = stateArray[which];
//                        post[0] =which;
                        stateId = stateList.get(which).getStateId();
                        tv_stateId.setText(state);
                        tv_cityId.setText("city");
                        cityList.clear();
                        cityId = "0";
                        getCityList(userId, stateId, encryptKey);

                    }
                });

        return builder.create();
    }


    public Dialog onCreateDialogCitySingleChoice() {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> citylist = new ArrayList<>();
        for (CityListModel.citydata.CityListData cityListData : cityList) {
            citylist.add(cityListData.getName());
        }


        final String[] stateArray = citylist.toArray(new String[cityList.size()]);
        builder.setTitle("Select City")

// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setItems(stateArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
//                        plan=planList.get(which).getSpName();
                        String state = stateArray[which];
                        cityId = cityList.get(which).getCityId();
//                        post[0] =which;
                        tv_cityId.setText(state);

                    }
                });

        return builder.create();
    }


    public void getCityList(String user_id, String stateid, final String accessTocken) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("encrypt_key", accessTocken);
        params.put("state_id", stateid);

        Call<CityListModel> call = apiService.getCityList(params);
        call.enqueue(new Callback<CityListModel>() {
            @Override
            public void onResponse(Call<CityListModel> call, Response<CityListModel> response) {

                pDialog.dismiss();

                CityListModel cityListModel = response.body();
                if (response.isSuccessful()) {

                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        cityList.clear();
                        cityList.addAll(cityListModel.getData().getCityList());

                    }

                } else
                    Toast.makeText(context, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CityListModel> call, Throwable t) {
                pDialog.dismiss();


            }
        });
    }

    public void getStateList(String user_id, String country_id, final String accessTocken) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("encrypt_key", accessTocken);
        params.put("country_id", country_id);
        Call<StateListModel> call = apiService.getStateList(params);
        call.enqueue(new Callback<StateListModel>() {
            @Override
            public void onResponse(Call<StateListModel> call, Response<StateListModel> response) {

                pDialog.dismiss();

                StateListModel stateListModel = response.body();
                if (response.isSuccessful()) {

                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        stateList.clear();
                        stateList.addAll(stateListModel.getData().getStateList());

                    }

                } else
                    Toast.makeText(context, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<StateListModel> call, Throwable t) {
                pDialog.dismiss();


            }
        });
    }


    public void getCountryList(String user_id, final String accessTocken) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("encrypt_key", accessTocken);

        Call<CountryListModel> call = apiService.getCountryList(params);
        call.enqueue(new Callback<CountryListModel>() {
            @Override
            public void onResponse(Call<CountryListModel> call, Response<CountryListModel> response) {

                pDialog.dismiss();

                CountryListModel cityListModel = response.body();
                if (response.isSuccessful()) {

                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {
                        countryList.clear();
                        countryList.addAll(cityListModel.getData().getCountryList());
                    }

                } else
                    Toast.makeText(context, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CountryListModel> call, Throwable t) {
                pDialog.dismiss();


            }
        });
    }


}
