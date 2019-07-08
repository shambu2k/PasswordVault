package com.shambu.passwordvault;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.NoSuchPaddingException;

import devliving.online.securedpreferencestore.DefaultRecoveryHandler;
import devliving.online.securedpreferencestore.SecuredPreferenceStore;

public class Enter_Password extends AppCompatActivity {

    EditText pass;
    Button button;
    ConstraintLayout sbview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__password);
        pass = findViewById(R.id.editText_passenter);
        button = findViewById(R.id.button_enter);
        sbview = findViewById(R.id.enter_pass_layout);

        button.setOnClickListener(new View.OnClickListener() {
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
                SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
                if(true==true){  //prefStore.getString("TKOEKYEN", "").equals(pass.getText().toString())
                    startActivity(new Intent(Enter_Password.this, MainActivity.class));
                    finish();
                }
                else{
                    if(TextUtils.isEmpty(pass.getText().toString())){
                        closeKeyboard();
                        Snackbar.make(sbview, "Enter password", Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        closeKeyboard();
                        Snackbar.make(sbview, "Wrong password", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
