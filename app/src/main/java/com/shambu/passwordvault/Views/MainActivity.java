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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shambu.passwordvault.Model.PassRepository;
import com.shambu.passwordvault.R;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shambu.passwordvault.Constants.REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS;
import static com.shambu.passwordvault.Constants.SECURITY_SETTING_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    private File pkg;

    public static String lepass;
    BottomNavigationView bottomNav;
    ConstraintLayout rr;
    private Dialog sql_pass_dialog;
    private Pattern patternspc = Pattern.compile("[a-zA-Z0-9]*");
    private Matcher matchspc;

    AlertDialog.Builder noLockAlert;
    KeyguardManager mKeyguardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pkg= new File(Environment.getExternalStorageDirectory()+"/PassV");
        if(!pkg.exists()){
            pkg.mkdir();
        }
        settingsChecker();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rr = findViewById(R.id.mainreallay);
        rr.setVisibility(View.GONE);
        mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (!isDeviceSecure()) {
            showAlertifnotsecure();
        }
        Intent intent = mKeyguardManager.createConfirmDeviceCredentialIntent("Unlock Password Vault", "Confirm your screen lock Pattern, PIN or Password");
        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }

        bottomNav = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.navhost_fragment);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            if (resultCode == RESULT_OK) {
                rr.setVisibility(View.VISIBLE);
                //sql_pass_dialogCheck();
            } else {
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
            }
        }
    }

    private boolean isDeviceSecure() {
        if (Build.VERSION.SDK_INT >= 23) {
            return mKeyguardManager.isDeviceSecure();
        } else {
            return mKeyguardManager.isKeyguardSecure();
        }
    }

    private void showAlertifnotsecure() {
        rr.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= 21) {
            noLockAlert = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog);
        } else {
            noLockAlert = new AlertDialog.Builder(MainActivity.this);
        }
        noLockAlert.setTitle("Lock Screen").setMessage("It seems your device doesn't have a secure lock screen. Please set a PIN,Password or Pattern.").setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                try {
                    startActivityForResult(intent, SECURITY_SETTING_REQUEST_CODE);
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "Set screen lock manually", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finishAffinity();
            }
        }).setCancelable(false).show();
    }

    private static boolean checkString(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }

    private void passwordStrengthCheck(String password) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        matchspc = patternspc.matcher(password);
        if(password.length() < 8){
            Toast.makeText(MainActivity.this, "Password should be minimum 8 characters long", Toast.LENGTH_SHORT).show();
        }
        else if(!checkString(password)){
            Toast.makeText(MainActivity.this, "Password should contain atleast one number, lowercase, uppercase and a special character", Toast.LENGTH_LONG).show();
        }
        else if(matchspc.matches()){
            Toast.makeText(MainActivity.this, "Password should contain atleast one special character", Toast.LENGTH_SHORT).show();
        }
        else{
            try {
                lepass = password;
                PassRepository passRepository = new PassRepository(getApplication());
                rr.setVisibility(View.VISIBLE);
                sql_pass_dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Password seems incorrect", Toast.LENGTH_SHORT).show();
            }
            editor.putString("FIRSTTIME", "NO");
            editor.commit();
        }
    }

    private void sql_pass_dialogCheck() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        if(pref.getString("FIRSTTIME", "YES").equals("YES")){
            sql_pass_dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
            sql_pass_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            sql_pass_dialog.setContentView(R.layout.sql_pass_dialog);
            final TextView pass = sql_pass_dialog.findViewById(R.id.sqlpass_edt);
            Button sqlSave = sql_pass_dialog.findViewById(R.id.save_sqlpass_button);
            TextView import_tv = sql_pass_dialog.findViewById(R.id.import_textview);
            import_tv.setPaintFlags(import_tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            sql_pass_dialog.setCancelable(false);
            sql_pass_dialog.setCanceledOnTouchOutside(false);
            sql_pass_dialog.show();

            import_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            sqlSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(pass.getText().toString())) {
                        passwordStrengthCheck(pass.getText().toString());
                    } else {
                        Toast.makeText(MainActivity.this, "Enter the password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            sql_pass_dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
            sql_pass_dialog.setContentView(R.layout.sql_pass_dialog);
            LinearLayout info = sql_pass_dialog.findViewById(R.id.pass_info);
            TextView import_tv = sql_pass_dialog.findViewById(R.id.import_textview);
            info.setVisibility(View.GONE);
            import_tv.setVisibility(View.GONE);
            final TextView pass = sql_pass_dialog.findViewById(R.id.sqlpass_edt);
            Button sqlSave = sql_pass_dialog.findViewById(R.id.save_sqlpass_button);
            sql_pass_dialog.setCancelable(false);
            sql_pass_dialog.setCanceledOnTouchOutside(false);
            sql_pass_dialog.show();

            sqlSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(pass.getText().toString())) {
                        try {
                            lepass = pass.getText().toString();
                            PassRepository passRepository = new PassRepository(getApplication());
                            rr.setVisibility(View.VISIBLE);
                            sql_pass_dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Password seems incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Enter the password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void settingsChecker() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        if (pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


}
