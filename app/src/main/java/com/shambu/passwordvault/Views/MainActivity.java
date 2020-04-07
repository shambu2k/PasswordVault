package com.shambu.passwordvault.Views;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shambu.passwordvault.R;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shambu.passwordvault.Constants.REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS;
import static com.shambu.passwordvault.Constants.SECURITY_SETTING_REQUEST_CODE;
import static com.shambu.passwordvault.Encryptor.encryptString;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    ConstraintLayout rr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rr = findViewById(R.id.mainreallay);

        bottomNav = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.navhost_fragment);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}
