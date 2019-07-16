package com.shambu.passwordvault;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shambu.passwordvault.Fragments_dir.favourites_fragment;
import com.shambu.passwordvault.Fragments_dir.passwords_fragment;
import com.shambu.passwordvault.Fragments_dir.settings_fragment;
import com.shambu.passwordvault.Sql_dir.password_text_sql;

import net.sqlcipher.database.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    private static final int SECURITY_SETTING_REQUEST_CODE = 233;
    private static final int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 1;
    public static String lepass;
    private password_text_sql database;
    FrameLayout fragcont;
    BottomNavigationView bottomNav;
    RelativeLayout rr;
    private Dialog sql_pass_dialog;

    AlertDialog.Builder noLockAlert;
    KeyguardManager mKeyguardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteDatabase.loadLibs(MainActivity.this);
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

        fragcont = findViewById(R.id.fragment_container);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.passwords_frag_menu:
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                                .replace(R.id.fragment_container, new passwords_fragment(), "PASS_FRAG").commit();
                        break;
                    case R.id.favourites_frag_menu:
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                                .replace(R.id.fragment_container, new favourites_fragment(), "FAV_FRAG").commit();
                        break;
                    case R.id.settings_frag_menu:
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                                .replace(R.id.fragment_container, new settings_fragment(), "SET_FRAG").commit();
                        break;
                }

                return true;
            }
        });


        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragment_container, new passwords_fragment(), "PASS_FRAG").commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            if (resultCode == RESULT_OK) {
                sql_pass_dialogCheck();
            } else {
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
            }
        }
    }

    @Override
    public void onBackPressed() {
        favourites_fragment Ffragment = (favourites_fragment) getSupportFragmentManager().findFragmentByTag("FAV_FRAG");
        settings_fragment Sfragment = (settings_fragment) getSupportFragmentManager().findFragmentByTag("SET_FRAG");
        if (Ffragment != null && Ffragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.fragment_container, new passwords_fragment(), "PASS_FRAG").commit();
            bottomNav.getMenu().getItem(0).setChecked(true);
        } else if (Sfragment != null && Sfragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.fragment_container, new passwords_fragment(), "PASS_FRAG").commit();
            bottomNav.getMenu().getItem(0).setChecked(true);
        } else {
            super.onBackPressed();
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

    private void sql_pass_dialogCheck() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        if(pref.getString("FIRSTTIME", "YES").equals("YES")){
            database = new password_text_sql(MainActivity.this);
            sql_pass_dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
            sql_pass_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            sql_pass_dialog.setContentView(R.layout.sql_pass_dialog);
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
                            database.tester(database.getWritableDatabase(pass.getText().toString()));
                            rr.setVisibility(View.VISIBLE);
                            lepass = pass.getText().toString();
                            sql_pass_dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Password seems incorrect", Toast.LENGTH_SHORT).show();
                        }
                        editor.putString("FIRSTTIME", "NO");
                        editor.commit();
                    } else {
                        Toast.makeText(MainActivity.this, "Enter the password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            database = new password_text_sql(MainActivity.this);
            sql_pass_dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
            sql_pass_dialog.setContentView(R.layout.sql_pass_dialog);
            LinearLayout info = sql_pass_dialog.findViewById(R.id.pass_info);
            info.setVisibility(View.GONE);
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
                            database.tester(database.getWritableDatabase(pass.getText().toString()));
                            rr.setVisibility(View.VISIBLE);
                            lepass = pass.getText().toString();
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
