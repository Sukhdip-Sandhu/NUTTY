package com.example.omocha.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";


    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesManager(Context context) {
        String SHARED_PREFERENCES_ID = "OMOCHA";
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_ID, 0);
        editor = sharedPreferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}