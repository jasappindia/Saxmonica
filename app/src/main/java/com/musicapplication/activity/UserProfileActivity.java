package com.musicapplication.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.musicapplication.R;
import com.musicapplication.constants.PrefranceKeyConstants;
import com.musicapplication.models.CityListModel;
import com.musicapplication.models.CountryListModel;
import com.musicapplication.models.GalleryListModel;
import com.musicapplication.models.ResultModel;
import com.musicapplication.models.StateListModel;
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
import java.util.prefs.Preferences;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    /*************** *******************/
    private static final int REQUEST_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int SELECT_PICTURE = 103;
    public static String selectedImagePath;/*, filepath;*/
    public String filePath;
    String userId = "1", encryptKey = "hahfd";
    CircleImageView euserImageview, userImageview;
private ImageView editimage;
    private Context context;
    private EditText edt_userName, edt_password, edt_email, edt_age;
 private ImageView iv_edit_profile;
    private TextView tv_userName, tv_password, tv_email, tv_age;
    private TextView tv_cityId,tv_gender, tv_countryId, tv_stateId, tv_e_cityId, tv_e_countryId, tv_e_stateId;
    private Button btn_sign_up;
    private String cityId = "0";
    private String countryId = "0";
    private String stateId = "0";
    private List<CityListModel.citydata.CityListData> cityList;
    private List<CountryListModel.Datadata.CountryListdata> countryList;
    private List<StateListModel.Datadata.StateListdata> stateList;
    private Button btn_save, btn_cancel;
    private ViewGroup showProfile, editProfile;
    private String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        init();
        iv_edit_profile=findViewById(R.id.iv_edit_profile);
        edt_email = findViewById(R.id.edt_email_id);
        edt_userName = findViewById(R.id.edt_name);
        edt_age = findViewById(R.id.edt_age);
        euserImageview = findViewById(R.id.ci_usershow);
        editimage = findViewById(R.id.iv_edit_image);
        showProfile = findViewById(R.id.show_profile);
        editProfile = findViewById(R.id.edit_profile);
        tv_e_cityId = findViewById(R.id.tv_e_cityId);
        tv_e_stateId = findViewById(R.id.tv_e_stateId);
        tv_e_countryId = findViewById(R.id.tv_e_countryId);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btn_save);

        /**********************   *********************/
        tv_email = findViewById(R.id.tv_emailid);
        tv_userName = findViewById(R.id.tv_username);
        tv_age = findViewById(R.id.tv_age);
        userImageview = findViewById(R.id.ci_usershow);

        tv_cityId = findViewById(R.id.tv_cityId);
        tv_stateId = findViewById(R.id.tv_stateId);
        tv_countryId = findViewById(R.id.tv_countryId);
        tv_gender = findViewById(R.id.tv_gender);

        iv_edit_profile.setOnClickListener(this);




    }


    private Toolbar mToolbar;
    private TextView toolbarTitle;
    private ImageView iv_home;



    private void init() {

        context = this;
        countryList = new ArrayList<>();
        cityList = new ArrayList<>();
        stateList = new ArrayList<>();
        userId = PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_NAME, context);
        encryptKey = "dfshuhfd";

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        iv_home = mToolbar.findViewById(R.id.iv_home);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle("");
        getSupportActionBar().setTitle("");
        iv_home.setOnClickListener(this);
        toolbarTitle.setText("Profile");



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataProfileShow();

    }

    public void setDataProfileShow() {

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_NAME, context).equalsIgnoreCase(""))
            tv_userName.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_NAME, context));
        else
            tv_userName.setText("" + getString(R.string.no_name));

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.Emai_ID, context).equalsIgnoreCase(""))
            tv_email.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.Emai_ID, context));
        else
            tv_email.setText("" + getString(R.string.no_email));

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_AGE, context).equalsIgnoreCase(""))
            tv_age.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_AGE, context));
        else
            tv_age.setText("" + getString(R.string.no_age));
        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_CITYNAME, context).equalsIgnoreCase(""))
            tv_cityId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_CITYNAME, context));
        else
            tv_cityId.setText("" + getString(R.string.no_city));
        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_STATENAME, context).equalsIgnoreCase(""))
            tv_stateId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_STATENAME, context));
        else
            tv_stateId.setText("" + getString(R.string.no_state));

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_COUNTRY_NAME, context).equalsIgnoreCase(""))
            tv_countryId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_COUNTRY_NAME, context));
        else
            tv_countryId.setText("" + getString(R.string.no_country));
        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_GENDER, context).equalsIgnoreCase(""))
            tv_gender.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_GENDER, context));
        else
            tv_gender.setText("" + getString(R.string.gender));

        //image show
        Utill.imageshow(context, euserImageview, PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_PHOTO, context));


    }


    public void setDataProfileEdit() {


        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_NAME, context).equalsIgnoreCase(""))
            edt_userName.setHint("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_NAME, context));
        else
            edt_userName.setHint("" + getString(R.string.no_name));

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.Emai_ID, context).equalsIgnoreCase(""))
            tv_email.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.Emai_ID, context));
        else
            tv_email.setText("" + getString(R.string.no_email));

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_AGE, context).equalsIgnoreCase(""))
            edt_age.setHint("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_AGE, context));
        else
            edt_age.setHint("" + getString(R.string.no_age));
        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_CITYNAME, context).equalsIgnoreCase(""))
            tv_e_cityId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_CITYNAME, context));
        else
            tv_e_cityId.setText("" + getString(R.string.no_city));
        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_STATENAME, context).equalsIgnoreCase(""))
            tv_e_stateId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_STATENAME, context));
        else
            tv_e_stateId.setText("" + getString(R.string.no_state));

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_COUNTRY_NAME, context).equalsIgnoreCase(""))
            tv_e_countryId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_COUNTRY_NAME, context));
        else
            tv_e_countryId.setText("" + getString(R.string.no_country));


        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_COUNTRY_NAME, context).equalsIgnoreCase(""))
            tv_e_countryId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_COUNTRY_NAME, context));
        else
            tv_e_countryId.setText("" + getString(R.string.no_country));

        if (!PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_COUNTRY_NAME, context).equalsIgnoreCase(""))
            tv_e_countryId.setText("" + PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_COUNTRY_NAME, context));
        else
            tv_e_countryId.setText("" + getString(R.string.no_country));

        //image show
        Utill.imageshow(context, euserImageview, PreferenceUtil.getString(PrefranceKeyConstants.PREFRENCEFILENAME, PrefranceKeyConstants.USER_PHOTO, context));


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
            case R.id.btn_cancel:
                showProfile.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.GONE);
                setDataProfileShow();
                break;
            case R.id.iv_home:
                onBackPressed();
                break;




        }

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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "ChatLocaly" + timeStamp + "_";
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

                //Uri photoURI = FileProvider.getUriForFile(this, Utils.getMetaDataValue(this, MobiComKitConstants.PACKAGE_NAME) + ".provider", photoFile);
                Uri photoURI = FileProvider.getUriForFile(this, "${applicationId}.provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }


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








}
