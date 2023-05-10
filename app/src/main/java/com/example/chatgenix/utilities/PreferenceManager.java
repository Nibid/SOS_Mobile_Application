package com.example.chatgenix.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public class PreferenceManager extends Constants{

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE1 = "phone1";
    public static final String KEY_PHONE2 = "phone2";
    public static final String KEY_PHONE3 = "phone3";
    public static final String KEY_PHONE4 = "phone4";
    public static final String KEY_PHONE5 = "phone5";
    private static final String KEY_SERVICE = "service";

    private SharedPreferences sharedPreferences;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public int getId() {
        return sharedPreferences.getInt(encode(KEY_USER_ID), -1);
    }

    public void setId(int id) {
        sharedPreferences.edit().putInt(encode(KEY_USER_ID), id).apply();
    }

    public void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key,null);
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getName() {
        return sharedPreferences.getString(encode(KEY_NAME), null);
    }

    public void setName(String firstName) {
        sharedPreferences.edit().putString(encode(KEY_NAME), firstName).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(encode(KEY_EMAIL), null);
    }

    public void setEmail(String email) {
        sharedPreferences.edit().putString(encode(KEY_EMAIL), email).apply();
    }

    public String getEmergencyContact1() {
        return sharedPreferences.getString(encode(KEY_PHONE1), null);
    }

    public void setEmergencyContact1(String phone) {
        sharedPreferences.edit().putString(encode(KEY_PHONE1), phone).apply();
    }

    public String getEmergencyContact2() {
        return sharedPreferences.getString(encode(KEY_PHONE2), null);
    }

    public void setEmergencyContact2(String phone) {
        sharedPreferences.edit().putString(encode(KEY_PHONE2), phone).apply();
    }

    public String getEmergencyContact3() {
        return sharedPreferences.getString(encode(KEY_PHONE3), null);
    }

    public void setEmergencyContact3(String phone) {
        sharedPreferences.edit().putString(encode(KEY_PHONE3), phone).apply();
    }

    public String getEmergencyContact4() {
        return sharedPreferences.getString(encode(KEY_PHONE4), null);
    }

    public void setEmergencyContact4(String phone) {
        sharedPreferences.edit().putString(encode(KEY_PHONE4), phone).apply();
    }

    public String getEmergencyContact5() {
        return sharedPreferences.getString(encode(KEY_PHONE5), null);
    }

    public void setEmergencyContact5(String phone) {
        sharedPreferences.edit().putString(encode(KEY_PHONE5), phone).apply();
    }

    public boolean getService() {
        return sharedPreferences.getBoolean(encode(KEY_SERVICE), true);
    }

    public void setService(boolean service) {
        sharedPreferences.edit().putBoolean(encode(KEY_SERVICE), service).apply();
    }

    public void clearUser() {
        sharedPreferences.edit().clear().apply();
    }

    private String encode(String data) {
        return Base64.encodeToString(data.getBytes(), Base64.NO_WRAP);
    }
}
