package com.musicapplication.prefrance;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Date;


/**
 * Created by Muhammad on 8/14/2016.
 */
public class PreferenceUtil {

    // MARK: Setter Methods
    public static void setBoolean(String fileName, String key, Boolean value, Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static void setString(String fileName, String key, String value, Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static void setInt(String fileName, String key, int value, Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static void setDouble(String fileName, String key, double value, Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat(key, (float) value);
        edit.apply();
    }

    // MARK: Getter Methods
    public static boolean getBoolean(String fileName, String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static String getString(String fileName, String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String name=sp.getString(key, "");
        return name;
    }

    public static int getInt(String fileName, String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    public static double getDouble(String fileName, String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        double value = sp.getFloat(key, 0f);
        value = Double.parseDouble(String.format("%.4f", value));
        return value;
    }

    public static void removeAllPreference(String fileName, Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }


}
