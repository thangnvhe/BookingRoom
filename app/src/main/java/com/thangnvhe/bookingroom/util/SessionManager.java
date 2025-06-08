package com.thangnvhe.bookingroom.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "LabBookingPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void login(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }
}
