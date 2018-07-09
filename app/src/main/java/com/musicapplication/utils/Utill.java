package com.musicapplication.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.musicapplication.R;
import com.musicapplication.constants.Constant;
import com.musicapplication.constants.UrlConstants;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;


public class Utill {

    public static final String BASE_IMAGE = "http://americankeysupply.com/images/";
    public static String user_id = "", order_token = "";
    public static String radius = "20";
    public static JSONObject jsonObject;
    Dialog dialog;
    Context context;
    AlertDialog.Builder alertDialog = null;

    public static void keyboardHideInFragment(FragmentActivity activity, View view) {

    }

    public static void Raleway(Context context, EditText editText, int typeface) {
        try {
            Typeface font = Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf");
            editText.setTypeface(font, typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    final String CONFIG_CLIENT_ID = "AddiYhvOC8v5Ntcf1uczVu8p4j9ycYQkNkw-ja4nfA3l2_EwimGoeM1Vy5gTGs5wrrwpPU7hTwUuzovu";
    final int REQUEST_CODE_PAYMENT = 1;*/


    /* OpenSansReguladiar*/

    public static void keyboardHide(Context context, EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String decodeString(String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";
        try {

            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } finally {

            return decodedString;
        }
    }

  /*  public static String encodesha512(String key, String data) throws Exception {
       *//* Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512");
        sha512_HMAC.init(secret_key);
        String hashkey=""+sha512_HMAC.doFinal(data.getBytes("UTF-8"));
        Log.v("hashkey encode",""+sha512_HMAC.doFinal(data.getBytes("UTF-8")));
        return new String(Base64.encode(Hex.encodeHex(sha512_HMAC.doFinal(data.getBytes("UTF-8")),Base64.DEFAULT));*//*

        Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512");
        sha512_HMAC.init(secret_key);
        String hash = new String(Hex.encodeHex(sha512_HMAC.doFinal(data.getBytes("UTF-8"))));
        Log.e("hashcode", hash);
        return encodeString(hash);
    }
*/
    public static String encodeString(String s) {
        byte[] data = new byte[0];

        try {
            data = s.getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String base64Encoded = Base64.encodeToString(data, Base64.NO_WRAP);
        Log.e("base64", base64Encoded);
        return base64Encoded;


    }

    public static RequestBody encodeStringInRequest(String s) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);
        /*byte[] data = new byte[0];

        try {
            data = s.getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

            String base64Encoded = Base64.encodeToString(data, Base64.DEFAULT);
*/
        return body;


    }

    /*******************
     * header message
     ***********/
    public static String headerMessage(String method, String url, String time) {

        String methodSend = method.toUpperCase();

        String message = "Method:" + methodSend + "\n" + "Request:/" + url + "\n" + "Time:" + time;
        return message;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String convertFromOneTimeZoneToOhter(String date_time) {

        Date date = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = sdf.parse(date_time);
        } catch (Exception e) {
        }


        TimeZone fromTimezone = TimeZone.getTimeZone("GMT+0");//get Timezone object


        long fromOffset = fromTimezone.getOffset(date.getTime());//get offset
        long toOffset = TimeZone.getDefault().getOffset(date.getTime());

        long convertedTime = date.getTime() - (fromOffset - toOffset);
        Date d2 = new Date(convertedTime);
        return sdf.format(d2);
    }

    public static void fullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    public static void keyboardShow(Context context, EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void showCenteredToast(Context ctx, String msg) {
        try {


            Toast.makeText(ctx, ""+msg, 500).show();
         /* *//*   Toast toast = Toast.makeText(ctx, msg,500);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
        *//*    toast.show();
       */ } catch (Exception e) {

            e.printStackTrace();
        }
    }



    public static void statusBarColor(Context ctx) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = ((Activity) ctx).getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //window.setStatusBarColor(ctx.getResources().getColor(R.color.background_color));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /******************************
     * internet connection
     **********************/
    public static boolean isConnectingToInternet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String SetDate(String date) {
        String date1 = "";
        try {
            date1 = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));

        } catch (ParseException e) {

        }
        return date1;
    }

    public static String SetDate1(String date) {
        String date1 = "";
        try {
            date1 = new SimpleDateFormat("dd.MM.yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));

        } catch (ParseException e) {

        }
        return date1;
    }

    public static String SetCustomText(String text) {
        String custom_text = "";
        custom_text = text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
        return custom_text;
    }
/*

    public static String encode(String key, String data) throws Exception {


        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return new String(Hex.encodeHex(sha256_HMAC.doFinal(data.getBytes("UTF-8"))));
    }

    public static String Decode_Base64(String text) {
        byte[] valueDecoded = org.apache.commons.codec.binary.Base64.decodeBase64(text.getBytes());
        return new String(valueDecoded);
    }
*/

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static int getYear(Date date1, Date date2) {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
        Integer.parseInt(simpleDateformat.format(date1));
        return Integer.parseInt(simpleDateformat.format(date2)) - Integer.parseInt(simpleDateformat.format(date1));
    }

    /***********************************
     * image uplode
     ***************************************/


    public static void imageshow(Context context, ImageView imageView, String imagePath) {

        if(imagePath!=null && !imagePath.equalsIgnoreCase("") ) {
            Glide.with(context)
                    .load(imagePath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.user_icon)
                    .into(imageView);
        }
        Log.e("image ",imagePath);
    }


    public static void customSizeimageshow(Context context, ImageView imageView, String imagePath,int width,int height) {
        Glide.with(context)
                .load( imagePath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(1200,height)
                .into(imageView);

        Log.e("image ",imagePath);
    }


    public static void imageUplodefirebase(Context context, ImageView imageView, String imagePath) {
        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/autopro-75ac3.appspot.com/o" + imagePath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
              //  .placeholder(R.drawable.noimage)
             //   .error(R.drawable.noimage)
                .into(imageView);
    }

    public static String currentTimestamp() {
        Long tsLong = System.currentTimeMillis() / 1000;
        return tsLong.toString();


    }

    public static void sendEmail(Context context, String title, String text) {




        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
// intent.setType("text/plain");
        final PackageManager pm = context.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches) {
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                best = info;
                break;
            }
        }
        if (best != null) {
            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        }
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"feedback@saxmonika.com"});

        context.startActivity(intent);




/*
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"alljasappusers@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, title);
        i.putExtra(Intent.EXTRA_TEXT, text);
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }*/
    }

    /* number formatinga */
    public static String convertFloatToshowTwoDecimelValuse(Object fraction) {
        return new DecimalFormat("#0.00").format(fraction);

    }

    public static void printLog_e(String tag, String message) {
        Log.e(tag, message);

    }

    public static void printLog_v(String tag, String message) {
        Log.v(tag, message);

    }

    public void setTypeFace(TextView tv, String name, Context context) {
        Typeface externalFont = Typeface.createFromAsset(context.getAssets(), "fonts/" + name);
        tv.setTypeface(externalFont);
    }

    public interface ApiListener {

        void onSuccess(JSONObject jsonObject, int id);

    }



  /*  public boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }*/

// send google play page
     public static void sendPlayStorePage(Context context)
    {

        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }

    }

    public static void sendPlayStore(Context context,String appPackageName)
    {

       // final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }

    }
    /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     * */
    public String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        // Add hours if there
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;}

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     * @param currentDuration
     * @param totalDuration
     * */
    public int getProgressPercentage(long currentDuration, long totalDuration){
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage =(((double)currentSeconds)/totalSeconds)*100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     * @param progress -
     * @param totalDuration
     * returns current duration in milliseconds
     * */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double)progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

   public static void appshareCode(String data,Context context){

       Intent sharingIntent = new Intent(Intent.ACTION_SEND);
       sharingIntent.setType("text/plain");
       //sharingIntent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
       sharingIntent.putExtra(Intent.EXTRA_TEXT, data+"\n\n  https://play.google.com/store/apps/details?id="+context.getPackageName());
       context.startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    public static RequestBody createPartFromString(String value) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), value);
        return requestFile;
    }


    public static SimpleDateFormat dateFormate(String data_pattern)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(data_pattern);


        return  simpleDateFormat;
    }



   /* public void datePicker(final int id) {

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                showDate(year, monthOfYear + 1, dayOfMonth, id);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        //dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        dialog.show();

    }

    private void showDate(int year, int month, int day, int id) {
        if (id == 1) {
            tv_orderdate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
            orderdate = new StringBuilder().append(year).append("-")
                    .append(month).append("-").append(day) + "";
        } else if (id == 0) {
            tv_shiftdate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
            shiftdate = new StringBuilder().append(year).append("-")
                    .append(month).append("-").append(day) + "";
        }
    }*/


    // change button text color and background

    public static void chageBackgroudofButtoncolor(Button view, int backgroundcolor_id, int textcolor_id)
    {



        view.setBackgroundColor(backgroundcolor_id);
        view.setTextColor(textcolor_id);

    }
    //

    public static void chage(Button view, int backgroundcolor_id, int textcolor_id)
    {

        view.setBackgroundColor(backgroundcolor_id);
    }


    public static String getApplozicUserId(String user_id)
    {
        return "0C"+user_id;
    }

    public static String getApplozicGroupMemberId(String groupMemberId)
    {


        return "0B"+groupMemberId;
    }



    public  static double getPercentPrice(double price, double discount)
    {
        double discountPrice=(price*discount)/100 ;
        // round off two number
        discountPrice=Math.round(discountPrice*100.00)/100.00;

       double discountedPrice=price-discountPrice;
             discountedPrice=Math.round(discountedPrice*100.00)/100.00;

        return discountedPrice;

    }
    public  static double getRoundOffFunction(double price)
    {
        double roundoffFunction = price;

        // round after decimal digit add
        roundoffFunction=Math.round(roundoffFunction*100.00)/100.00;
        return  roundoffFunction;
    }


    public static Date getDateFormat( long milliseconds )
    {
//        long yourmilliseconds = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");
        Date resultdate = new Date(milliseconds);
        System.out.println(sdf.format(resultdate));
        return resultdate;
    }
    public static String getDate(Long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd/MM/yy");
        return fullDateFormat.format(date);
    }
    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),      view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }







    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public static void sendIntent(Context context) {
        Intent intent = new Intent();
        ((Activity)context).setResult(RESULT_OK, intent);
        ((Activity)context).finish();
    }
    public  static void callIntent(Context context,String phoneNumber)
    {
        if(phoneNumber!=null && phoneNumber.length()>4) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(intent);
        }
        }

    public  static void callMapIntent(Context context,String yourAddress)
    {
        String map = "http://maps.google.co.in/maps?q=" + yourAddress;
        if(yourAddress!=null && yourAddress.length()>4) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
            context.startActivity(intent);
        }
    }

    public static void screenSize(Context context)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((AppCompatActivity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Utill.showCenteredToast(context , " height "+height+" width "+ width);
    }

    public static String capitalize(String s) {
        if (s == null) return null;
        if (s.length() == 1) {
            return s.toUpperCase();
        }
        if (s.length() > 1) {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        return "";
    }

    public static  String getPriceFormatted(String price)
    {
        String priceStr=price.trim();
        double value =Double.parseDouble(priceStr);
        if(value>100000)
        {
            value= Math.round((value/100000)*100)/100;
            priceStr=value+" Lac";
        }else
        {
            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) format).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            ((DecimalFormat) format).setDecimalFormatSymbols(decimalFormatSymbols);
            priceStr =format.format(new BigDecimal(priceStr));



        }
        return priceStr;
    }
    public static void shareImageIntent(View viewGroup,Context context) {
        viewGroup.setDrawingCacheEnabled(true);
        Bitmap bitmap = viewGroup.getDrawingCache();

        File cacha = context.getApplicationContext().getExternalCacheDir();
        File sharefile = new File(cacha, "share.png");
        try {
            FileOutputStream out = new FileOutputStream(sharefile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
/*
        //  create image    from bit map
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("image", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

*/

        // Toast.makeText(context, "Saved in Gallery", Toast.LENGTH_SHORT).show();
        viewGroup.setDrawingCacheEnabled(false);
        //now send it out to share
        Intent share = new Intent(Intent.ACTION_SEND);
        Log.e("image path", Uri.fromFile(sharefile).toString());
        share.setType("*/*");

       // share.putExtra(Intent.EXTRA_TITLE, context.getString(R.string.app_name));
        share.putExtra(Intent.EXTRA_TEXT, "Find this product on ChatLocaly " + "\nhttps://play.google.com/store/apps/details?id=" + context.getPackageName());

        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(sharefile));

        //context.startActivity(Intent.createChooser(share, context.getString(R.string.app_name)));
        viewGroup.setDrawingCacheEnabled(false);

    }

    public static void openUrl(Context context,String url)
    {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);


    }

}











