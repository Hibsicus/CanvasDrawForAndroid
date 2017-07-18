package com.example.user.graffity.CustomWidget.EmailData;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by PIPOLE_VR19 on 2017/7/18.
 */

public class SPHelper {
    private static final String SHARED_NAME = "sp_data";
    public static final String EMAIL_KEY = "emailkey";

    public static void SaveEmailToSharedPreference(Context context, String email)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(EMAIL_KEY, email);
        editor.apply();
    }

    public static String LoadEmailFromSharedPreference(Context contex)
    {
        SharedPreferences pref = contex.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        return pref.getString(EMAIL_KEY, "INVALID");
    }
}
