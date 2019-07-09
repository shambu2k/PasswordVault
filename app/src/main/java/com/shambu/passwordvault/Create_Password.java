package com.shambu.passwordvault;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import devliving.online.securedpreferencestore.DefaultRecoveryHandler;
import devliving.online.securedpreferencestore.EncryptionManager;
import devliving.online.securedpreferencestore.SecuredPreferenceStore;

public class Create_Password extends AppCompatActivity {

    EditText pass;
    Button button_pass;
    ConstraintLayout sbview;
    AlertDialog.Builder noLockAlert;
    KeyguardManager keyguardManager;

    private boolean isDeviceSecureKeyguardManager(){
        if(android.os.Build.VERSION.SDK_INT >= 23){
            keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE );
            return keyguardManager.isDeviceSecure();
        }
        else{
            keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE );
            return keyguardManager.isKeyguardSecure();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!isDeviceSecureKeyguardManager()){
            if(Build.VERSION.SDK_INT >= 21){
                noLockAlert = new AlertDialog.Builder(Create_Password.this, android.R.style.Theme_Material_Light_Dialog_Alert);
            }
            else{
                noLockAlert = new AlertDialog.Builder(Create_Password.this);
            }
            noLockAlert.setTitle("Lock Screen").setMessage("It seems your device doesn't have a secure lock screen. Please set a PIN,Password or Pattern.").setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(new Intent(Settings.ACTION_SECURITY_SETTINGS), 0);
                }
            }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            }).setCancelable(false).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__password);
        sbview=findViewById(R.id.create_pass_layout);
        pass = findViewById(R.id.editText_pass);
        button_pass = findViewById(R.id.buttoncreate);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        if(pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else if(pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        String storeFileName = "securedStore";
        String keyPrefix = "vss";
        byte[] seedKey = "SecuredSeedData".getBytes();
        try {
            SecuredPreferenceStore.init(getApplicationContext(), storeFileName, keyPrefix, seedKey, new DefaultRecoveryHandler());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (SecuredPreferenceStore.MigrationFailedException e) {
            e.printStackTrace();
        }
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        if(prefStore.getString("TKOEKYEN", "").length()!=0){
            startActivity(new Intent(Create_Password.this, Enter_Password.class));
            finish();
        }
        else{
            button_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String storeFileName = "securedStore";
                    String keyPrefix = "vss";
                    byte[] seedKey = "SecuredSeedData".getBytes();
                    try {
                        SecuredPreferenceStore.init(getApplicationContext(), storeFileName, keyPrefix, seedKey, new DefaultRecoveryHandler());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CertificateException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyStoreException e) {
                        e.printStackTrace();
                    } catch (UnrecoverableEntryException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (NoSuchProviderException e) {
                        e.printStackTrace();
                    } catch (SecuredPreferenceStore.MigrationFailedException e) {
                        e.printStackTrace();
                    }
                    SecuredPreferenceStore prefStorez = SecuredPreferenceStore.getSharedInstance();
                    if(pass.length()>=9){
                        prefStorez.edit().putString("TKOEKYEN", pass.getText().toString()).apply();
                        EncryptionManager encryptionManager = SecuredPreferenceStore.getSharedInstance().getEncryptionManager();
                        startActivity(new Intent(Create_Password.this, Enter_Password.class));
                        finish();
                        try {
                            encryptionManager.encrypt(seedKey);
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (NoSuchProviderException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        if(!TextUtils.isEmpty(pass.getText().toString())){
                            closeKeyboard();
                            Snackbar.make(sbview, "Password should have a minimum of 9 characters", Snackbar.LENGTH_LONG).show();
                        }
                        else{
                            Snackbar.make(sbview, "Enter password", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }



    }
    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
