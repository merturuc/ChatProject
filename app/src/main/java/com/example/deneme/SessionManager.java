package com.example.deneme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
        SharedPreferences sharedPreferences;
        public SharedPreferences.Editor editor;
        public Context context;
        int PRIVATE_MOD = 0;

        protected static final String PREF_LOGIN ="LOGIN";
        private static final String LOGIN ="IS_LOGIN";
        protected static final String USERNAME="Username";
        protected static final String EMAIL = "Email";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences =context.getSharedPreferences(PREF_LOGIN,PRIVATE_MOD);
        editor = sharedPreferences.edit();

    }

    public void createSession(String username , String email) {
        editor.putBoolean(LOGIN,true);
        editor.putString(USERNAME,username);
        editor.putString(EMAIL,email);
        editor.apply();

    }
    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN,true);
    }
    public void checkLogin() {
        if(!this.isLogin()) {
            Intent i = new Intent(context,LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();

        }
    }
    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(USERNAME,sharedPreferences.getString(USERNAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent intent =new Intent(context,LoginActivity.class);
        context.startActivity(intent);
        ((MainActivity) context).finish();
    }
}
