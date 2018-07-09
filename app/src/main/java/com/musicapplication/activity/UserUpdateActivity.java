package com.musicapplication.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.musicapplication.R;
import com.musicapplication.constants.PrefranceKeyConstants;
import com.musicapplication.models.CityListModel;
import com.musicapplication.models.CountryListModel;
import com.musicapplication.models.RegistrationModel;
import com.musicapplication.models.ResultModel;
import com.musicapplication.models.StateListModel;
import com.musicapplication.models.UpdateProfileModel;
import com.musicapplication.networkes.ApiClient;
import com.musicapplication.networkes.ApiInterface;
import com.musicapplication.prefrance.PreferenceUtil;
import com.musicapplication.utils.Utill;
import com.musicapplication.views.imageView.CircleImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    String userId = "1";
    ImageView iv_editProfile;
    private CircleImageView ciUsershow;
    private ImageView ivEditeName;
    private TextView tvCountryId, tvStateId, tvCityId, tvEmailId, tvAge,tv_gender;
    private EditText edt_name;
    private LinearLayout llCountry;
    private Context context;
    private String cityId = "0";
    private String countryId = "0";
    private String stateId = "0";
    private List<CityListModel.citydata.CityListData> cityList;
    private List<CountryListModel.Datadata.CountryListdata> countryList;
    private List<StateListModel.Datadata.StateListdata> stateList;
    private Button btn_save;
    private String encryptKey;
    private String userName, userAge, countryName, stateName, cityName;
    private Bitmap bitmapUser;
    private String filePath;
    private ImageView ivEditImage;
    private String mCurrentPhotoPath;

    private int IsimageChanged=0;
    private ImageView iv_home;
    private TextView tv_toolbartitle;

   /* private void setData(String name,String countryId,String cityId,String stateId,String age,String email,String imagePath) {

        edt_name.setText(""+name);
        tvCountryId.setText(""+countryId);
        tvCityId.setText(""+cityId);
        tvStateId.setText(""+stateId);
        tvAge.setText(""+age);
        tvEmailId.setText(""+email);
        Utill.imageshow(context,ciUsershow,imagePath);


    }*/

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {


        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(path, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_user_update);
        cityList = new ArrayList<>();
        stateList = new ArrayList<>();
        countryList = new ArrayList<>();

        context = this;
        ciUsershow = (CircleImageView) findViewById(R.id.ci_user_edit);
        llCountry = (LinearLayout) findViewById(R.id.ll_country);
        tvCountryId = (TextView) findViewById(R.id.tv_countryId);
        Toolbar toolbar=findViewById(R.id.toolbar);
        iv_home=toolbar.findViewById(R.id.iv_home);
        tv_toolbartitle=toolbar.findViewById(R.id.toolbar_title);

        tvAge = findViewById(R.id.tv_age);

        tv_gender=findViewById(R.id.tv_gender);
        tvEmailId = (TextView) findViewById(R.id.tv_emailid);
        tvStateId = (TextView) findViewById(R.id.tv_stateId);
        tvCityId = (TextView) findViewById(R.id.tv_cityId);
        ivEditImage = findViewById(R.id.iv_edit_image);
        edt_name = findViewById(R.id.edt_username);
        btn_save=findViewById(R.id.btn_save);
        encryptKey = "hjfsad";
        userId = PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_ID, context);
        //
        tvCityId.setOnClickListener(this);
        tvStateId.setOnClickListener(this);
        tvCountryId.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        tv_gender.setOnClickListener(this);
        tvAge.setOnClickListener(this);

        ivEditImage.setOnClickListener(this);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getCountryList(userId, encryptKey);
        setDataProfileShow();


    }

    public void setDataProfileShow() {

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_NAME, context).equalsIgnoreCase(""))
        {
            String name=PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_NAME, context);
            edt_name.setText("" + name);
            edt_name.setSelection(name.length());

        }

        else
            edt_name.setText("" + getString(R.string.no_name));

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.Emai_ID, context).equalsIgnoreCase(""))
            tvEmailId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.Emai_ID, context));
        else
            tvEmailId.setText("" + getString(R.string.no_email));

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_AGE, context).equalsIgnoreCase(""))
            tvAge.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_AGE, context));
        else
            tvAge.setText("" + getString(R.string.no_age));
        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_CITYNAME, context).equalsIgnoreCase(""))
            tvCityId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_CITYNAME, context));
        else
            tvCityId.setText("" + getString(R.string.no_city));
        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_STATENAME, context).equalsIgnoreCase(""))
            tvStateId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_STATENAME, context));
        else
            tvStateId.setText("" + getString(R.string.no_state));

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_COUNTRY_NAME, context).equalsIgnoreCase(""))
            tvCountryId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_COUNTRY_NAME, context));
        else
            tvCountryId.setText("" + getString(R.string.no_country));
        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_GENDER, context).equalsIgnoreCase(""))
            tv_gender.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_GENDER, context));
        else
            tv_gender.setText("" + getString(R.string.gender));

        //image show
        Utill.imageshow(context, ciUsershow, PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_PHOTO, context));
        //
        countryId=PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_COUNTRYID, context);
        stateId=PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_STATEID, context);
        cityId=PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_CITYID, context);



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
                        tvCountryId.setText(state);
                        cityId = "0";
                        stateId = "0";
                        tvCityId.setText("city");
                        tvStateId.setText("State");
                        stateList.clear();
                        cityList.clear();
                        getStateList(userId, countryId, encryptKey);

                    }
                });

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_edit_profile:
               /* showProfile.setVisibility(View.GONE);
                editProfile.setVisibility(View.VISIBLE);

                setDataProfileEdit();

                if(countryList.size()<1)
                getCountryList(userId,encryptKey);*/

                Intent intent = new Intent(context, UserUpdateActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_save:

                validation();


                break;
            case R.id.tv_countryId:
                if (countryList.size() > 0)
                    onCreateDialogcountrySingleChoice().show();
                else
                    getCountryList(userId,encryptKey);
                break;

            case R.id.tv_stateId:
                if (stateList.size() > 0) {
                    onCreateDialogStateSingleChoice().show();
                }
                else
                    getStateList(userId,countryId,encryptKey);
                break;
            case R.id.tv_cityId:
                if (cityList.size() > 0) {
                    onCreateDialogCitySingleChoice().show();
                }
                else
                    getCityList(userId,stateId,encryptKey);
                break;

            case R.id.iv_edit_image:

                chooserDialog(getString(R.string.app_name)).show();
                break;
            case R.id.tv_age:


                onCreateDialogage().show();

                break;
            case R.id.tv_gender:


                onCreateDialogGender().show();

                break;


        }

    }

    private void validation() {

        if (edt_name.getText().toString() != null && edt_name.getText().toString().equalsIgnoreCase("") && edt_name.getText().toString().length() < 4) {
            edt_name.setError(getString(R.string.errorMessage));

        }


        else if (tvAge.getText().toString().trim().equalsIgnoreCase("")&& !tvAge.getText().toString().trim().equalsIgnoreCase("Age")) {



            Utill.showCenteredToast(context, "Please select age");

        }

        else if (tv_gender.getText().toString().trim().equalsIgnoreCase("")&& !tv_gender.getText().toString().trim().equalsIgnoreCase("Gender")) {



            Utill.showCenteredToast(context, "Please select gender");

        }
        else if (countryId.equalsIgnoreCase("")) {



            Utill.showCenteredToast(context, "Please select country");

        }


        else if (countryId.equalsIgnoreCase("")) {



            Utill.showCenteredToast(context, "Please select country");

        }

        else if (stateId.equalsIgnoreCase("")) {
            Utill.showCenteredToast(context, "Please select state");
        }
        else if (cityId.equalsIgnoreCase("")) {
            Utill.showCenteredToast(context, "Please select city");
        }
        else
        {
            userName=edt_name.getText().toString().trim();

         if(IsimageChanged==0)
             filePath=PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_PHOTO, context);
            updateUserInformation(userId,stateId,cityId,filePath,countryId,encryptKey,userName,userAge);

        }


    }


    String gender="";

    public Dialog onCreateDialogGender() {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> genderlist=new ArrayList<>();
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
                        gender=other;
                        tv_gender.setText(gender);
//                        post[0] =which;


                    }
                });

        return builder.create();
    }

    String age="";

    public Dialog onCreateDialogage() {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> agelist=new ArrayList<>();
        for(int i=1;i<101;i++)
        {
            agelist.add(""+i);
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
                        age=other;
                        tvAge.setText(age);
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
                        tvStateId.setText(state);
                        tvCityId.setText("city");
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
                        tvCityId.setText(state);

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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void getTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                //  Uri photoURI = FileProvider.getUriForFile(this, Utils.getMetaDataValue(this, MobiComKitConstants.PACKAGE_NAME) + ".provider", photoFile);
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }


    /*http://184.154.53.181/musicapp/api/update_profile?
    // encrypt_key=rtrtrttr&user_name=kumarsanu
    // &country_id=2&city_id=2&device_id=dsfgbdsfn&device_type=ANDROID&state_id=2&user_id=18
    */

    public void getImagePickerIntent() {
/*
        Intent intent = new Intent();
        intent.setType("image");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
   */
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_PICTURE);

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

    public void setProfileUpdate(String user_id, String stateid, String cityid, String countryId, final String accessTocken) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(context, "", "Please wait ...", true);

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("encrypt_key", accessTocken);
        params.put("user_name", user_id);
        params.put("device_type", "ANDROID");
        params.put("state_id", stateid);
        params.put("city_id ", cityid);
        params.put("country_id", countryId);


        /*
        *
        *
        *
        * ?encrypt_key=rtrtrttr&
        * user_name=kumarsanu&
        * country_id=2&
        * city_id=2&
        * device_id=dsfgbdsfn&
        * device_type=ANDROID&
        * state_id=2&user_id=18
        *
        * */

        Call<UpdateProfileModel> call = apiService.getProfileUpdate(params);
        call.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {

                pDialog.dismiss();

                UpdateProfileModel profileModel = response.body();
                if (response.isSuccessful()) {

                    if (response.body().getData().getResultCode().equalsIgnoreCase("1")) {


                    }

                } else
                    Toast.makeText(context, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {

                pDialog.dismiss();


            }
        });
    }

    public void takePhoto() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkCallingOrSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                getTakePictureIntent();

            } else {

                requestMultiplePermissions(CAMERA_REQUEST);
            }
        } else {
            getTakePictureIntent();

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestMultiplePermissions(int requestCode) {

        String camera_permission = Manifest.permission.CAMERA;
        int hascampermission = checkSelfPermission(camera_permission);

        String storage_permission_group = Manifest.permission.READ_EXTERNAL_STORAGE;
        int hasStoragePermission = checkSelfPermission(storage_permission_group);

        String storage_writepermission_group = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int hasstroage = checkSelfPermission(storage_permission_group);

        List<String> permissions = new ArrayList<String>();

        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(storage_permission_group);
        }
        if (hascampermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(camera_permission);
        }

        if (hasstroage != PackageManager.PERMISSION_GRANTED) {
            permissions.add(storage_writepermission_group);
        }

        if (!permissions.isEmpty()) {
            String[] params = permissions.toArray(new String[permissions.size()]);
            requestPermissions(params, requestCode);
        }

    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                }
                break;

            case SELECT_PICTURE:
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    chooseFromGallery();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void chooseFromGallery() {
        if (Build.VERSION.SDK_INT >= 23) {


            if (checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                getImagePickerIntent();

            } else {
                requestMultiplePermissions(SELECT_PICTURE);
            }
        } else {
            getImagePickerIntent();

        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {

                if (requestCode == SELECT_PICTURE) {

                    Uri selectedImage = data.getData();
                    ciUsershow.setImageURI(selectedImage);// To display selected image in image view


                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();
                    ciUsershow.setImageBitmap(decodeSampledBitmapFromResource(filePath, 600, 600));
                    IsimageChanged=1;
                 /*   bitmapUser = BitmapFactory.decodeFile(filePath);
                    ivUserProfile.setImageBitmap(bitmapUser);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmapUser.compress(Bitmap.CompressFormat.PNG, 100, stream);
*/
                    //  callImageUpload(filePath,userId,"",encryptKey);

                    //  Glide.with(context).load(stream.toByteArray()).listener(target).into(iv_profilePic);


                } else if (requestCode == CAMERA_REQUEST) {

                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File savefile = new File(mCurrentPhotoPath);
                    Uri contentUri = Uri.fromFile(savefile);
                    mediaScanIntent.setData(contentUri);
                    this.sendBroadcast(mediaScanIntent);
                    ciUsershow.setImageBitmap(decodeSampledBitmapFromResource(savefile.getPath(), 600, 600));
                    filePath = savefile.getPath();
                    IsimageChanged=1;

                   /* if (savefile.exists()) {
                        callImageUpload(savefile.getAbsolutePath(),userId,"",encryptKey);

                    }
*/
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Toast.makeText(getApplicationContext(), " " + getResources().getString(R.string.str_cancelled), Toast.LENGTH_LONG).show();
            } else if (resultCode != Activity.RESULT_CANCELED) {
                if (requestCode == CAMERA_REQUEST) {
                    bitmapUser = (Bitmap) data.getExtras().get("data");
                    ciUsershow.setImageBitmap(bitmapUser);
                    IsimageChanged=1;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUserInformation(String user_id, String stateid, String cityid, String mediaPath, String countryId, final String accessTocken,String userName,String userAge) {

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialogofimaged = ProgressDialog.show(context, "", "Please wait ...", true);
        pDialogofimaged.setCancelable(false);
     //   encrypt_key=rtrtrttr&user_name=kumarsanu&country_id=2
                //&city_id=2&device_id=dsfgbdsfn&device_type=ANDROID&state_id=2&user_id=18
       // if profile image upload send image in profile_image parameter

        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("user_id", Utill.createPartFromString(user_id));

        params.put("encrypt_key", Utill.createPartFromString(accessTocken));
        params.put("user_name", Utill.createPartFromString(userName));
        params.put("device_type",Utill.createPartFromString( "ANDROID"));
        params.put("state_id", Utill.createPartFromString(stateid));
        params.put("city_id",Utill.createPartFromString(cityid));
        params.put("country_id", Utill.createPartFromString(countryId));
     //   params.put("user_age", Utill.createPartFromString(countryId));
        params.put("device_id", Utill.createPartFromString(accessTocken));
        params.put("age", Utill.createPartFromString(tvAge.getText().toString().trim()));
        params.put("gender", Utill.createPartFromString(tv_gender.getText().toString().trim()));


        Call<RegistrationModel> call = null;
        if (mediaPath != null && IsimageChanged==1) {
            File file = new File(mediaPath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            //MultipartBody.Part body = MultipartBody.Part.createFormData("group_image", file.getName(), reqFile);
            //  RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "group_image");
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), mediaPath);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("profile_image", file.getName(), reqFile);
            call = apiService.uploadFile(fileToUpload, filename, params);

        } else
            call = apiService.uploadFile(null, null, params);
        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {

                pDialogofimaged.dismiss();
                RegistrationModel resultModel = response.body();


                if (resultModel != null && resultModel.getData() != null && resultModel.getData().getResultCode().equalsIgnoreCase("1")) {


                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_ID,resultModel.getData().getUserDetails().getUserId(),context);
                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.Emai_ID,resultModel.getData().getUserDetails().getEmail(),context);
                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_NAME,""+resultModel.getData().getUserDetails().getUserName(),context);
                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_PHOTO,resultModel.getData().getUserDetails().getProfile_image(),context);
                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_CITYNAME,resultModel.getData().getUserDetails().getCity_name(),context);
                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_CITYID,""+resultModel.getData().getUserDetails().getCity_id(),context);
                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_STATENAME,resultModel.getData().getUserDetails().getState_name(),context);
                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_STATEID,""+resultModel.getData().getUserDetails().getState_id(),context);
                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_COUNTRY_NAME,resultModel.getData().getUserDetails().getCountry_name(),context);
                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_COUNTRYID,""+resultModel.getData().getUserDetails().getCountry_id(),context);
                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_AGE,""+resultModel.getData().getUserDetails().getAge(),context);
                    PreferenceUtil.setString(PrefranceKeyConstants.PREFRENCEFILENAME,PrefranceKeyConstants.USER_GENDER,""+resultModel.getData().getUserDetails().getGender(),context);



                    Utill.showCenteredToast(context, "" + getString(R.string.nameSuccessFullyupdateed));

                } else {
                    Utill.showCenteredToast(context, "" + resultModel.getData().getMessage());

                }
            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {


                pDialogofimaged.dismiss();
            }
        });


    }

    public String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor;
        if (Build.VERSION.SDK_INT > 19) {
            // Will return "image:x*"
            String wholeID = DocumentsContract.getDocumentId(uri);
            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];
            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, sel, new String[]{id}, null);
        } else {
            cursor = getContentResolver().query(uri, projection, null, null, null);
        }
        String path = null;
        try {
            int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        } catch (NullPointerException e) {

        }
        return path;
    }

    public Dialog chooserDialog(String title) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        String[] array = {"Take Photo", "Choose from Gallery"};

        builder.setTitle(title).setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    // for full image code

                    takePhoto();

                } else if (i == 1) {
                    chooseFromGallery();
                }

            }
        });

        return builder.create();
    }


}
