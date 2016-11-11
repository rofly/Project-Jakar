package com.jakarinc.jakar.LocalIO.Impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.jakarinc.jakar.LocalIO.AuthI;
import com.jakarinc.jakar.secure.preferences.SecureFactory;

public class Auth implements AuthI {
    private SharedPreferences preferences;

    public Auth(Context c) {

        preferences = SecureFactory.getPreferences(c, "jakarPref", "jakJunbotronKey");

    }


    @Override
    public String getUserId() {
        String userIdString = preferences.getString("user_id", null);
       // System.out.println("USER ID STRINGGGGG" + userIdString);
        return userIdString;
    }

    @Override
    public void logOut() {
        preferences.edit().remove("user_id").commit();
    }

    @Override
    public void logIn(String userId) {
        preferences.edit().putString("user_id", userId).commit();
    }
}
