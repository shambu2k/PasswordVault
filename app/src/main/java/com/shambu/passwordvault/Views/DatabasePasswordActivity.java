package com.shambu.passwordvault.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shambu.passwordvault.R;
import com.shambu.passwordvault.ViewModels.DatabasePassViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shambu.passwordvault.Constants.REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS;
import static com.shambu.passwordvault.Constants.SECURITY_SETTING_REQUEST_CODE;

public class DatabasePasswordActivity extends AppCompatActivity {

    public static String lepass;

    private Pattern patternspc = Pattern.compile("[a-zA-Z0-9]*");
    private Matcher matchspc;

    private AlertDialog.Builder noLockAlert;
    private KeyguardManager mKeyguardManager;

    private TextView import_tv;
    private EditText pass_edt;
    private Button save_button;
    private LinearLayout info;

    private DatabasePassViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_password);
        viewModel = new ViewModelProvider(this).get(DatabasePassViewModel.class);
        viewModel.settingsChecker();
        pass_edt = findViewById(R.id.sqlpass_edt);
        save_button = findViewById(R.id.save_sqlpass_button);
        import_tv = findViewById(R.id.import_textview);
        info = findViewById(R.id.pass_info);

        if(!viewModel.isFirstTime()){
            import_tv.setVisibility(View.GONE);
            info.setVisibility(View.GONE);
        }

        mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (!isDeviceSecure()) {
            showAlertIfNotSecure();
        }
        Intent intent = mKeyguardManager.createConfirmDeviceCredentialIntent("Unlock Password Vault", "Confirm your screen lock Pattern, PIN or Password");
        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }

    }

    private boolean isDeviceSecure() {
        if (Build.VERSION.SDK_INT >= 23) {
            return mKeyguardManager.isDeviceSecure();
        } else {
            return mKeyguardManager.isKeyguardSecure();
        }
    }

    private void showAlertIfNotSecure() {
        if (Build.VERSION.SDK_INT >= 21) {
            noLockAlert = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog);
        } else {
            noLockAlert = new AlertDialog.Builder(this);
        }
        noLockAlert.setTitle("Lock Screen").setMessage("It seems your device doesn't have a secure lock screen. Please set a PIN,Password or Pattern.").setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                try {
                    startActivityForResult(intent, SECURITY_SETTING_REQUEST_CODE);
                } catch (Exception ex) {
                    Toast.makeText(DatabasePasswordActivity.this, "Set screen lock manually", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabasePasswordActivity.this.finishAffinity();
            }
        }).setCancelable(false).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            if (resultCode == RESULT_OK) {
                passCheck();
            } else {
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
            }
        } else if(requestCode == SECURITY_SETTING_REQUEST_CODE){
            if(!isDeviceSecure()){
                showAlertIfNotSecure();
            } else {
                Toast.makeText(this, "Lock set", Toast.LENGTH_SHORT).show();
                recreate();
            }
        }
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
        matchspc = patternspc.matcher(password);
        if(password.length() < 8){
            Toast.makeText(this, "Password should be minimum 8 characters long", Toast.LENGTH_SHORT).show();
        }
        else if(!checkString(password)){
            Toast.makeText(this, "Password should contain atleast one number, lowercase, uppercase and a special character", Toast.LENGTH_LONG).show();
        }
        else if(matchspc.matches()){
            Toast.makeText(this, "Password should contain atleast one special character", Toast.LENGTH_SHORT).show();
        }
        else{
            lepass = password;
            viewModel.addPassToPref(password);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void passCheck() {
        if(viewModel.isFirstTime()){
            import_tv.setPaintFlags(import_tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            import_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            save_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(pass_edt.getText().toString())) {
                        passwordStrengthCheck(pass_edt.getText().toString());
                    } else {
                        Toast.makeText(DatabasePasswordActivity.this, "Enter the password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            save_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(pass_edt.getText().toString())) {
                        if(viewModel.passChecker(pass_edt.getText().toString())){
                            lepass = pass_edt.getText().toString();
                            startActivity(new Intent(DatabasePasswordActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(DatabasePasswordActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DatabasePasswordActivity.this, "Enter the password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
