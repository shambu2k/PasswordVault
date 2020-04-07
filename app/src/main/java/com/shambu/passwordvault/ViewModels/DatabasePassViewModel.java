package com.shambu.passwordvault.ViewModels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.AndroidViewModel;

import static com.shambu.passwordvault.Encryptor.encryptString;

public class DatabasePassViewModel extends AndroidViewModel {

    private SharedPreferences pref;

    public DatabasePassViewModel(@NonNull Application application) {
        super(application);
        pref = application.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }

    public Boolean isFirstTime(){
        if(pref.getString("FIRSTTIME", "YES").equals("YES")){
            return true;
        } else {
            return false;
        }
    }

    public void addPassToPref(String password){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("HASH", encryptString(password));
        editor.putString("FIRSTTIME", "NO");
        editor.commit();
    }

    public Boolean passChecker(String password){
        if(encryptString(password).equals(pref.getString("HASH",""))){
            return true;
        } else {
            return false;
        }
    }

    public void settingsChecker() {
        if (pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
