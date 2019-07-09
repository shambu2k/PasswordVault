package com.shambu.passwordvault.Fragments_dir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.shambu.passwordvault.MainActivity;
import com.shambu.passwordvault.R;

public class settings_fragment extends Fragment {

    private Switch drkmode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment_layout, container, false);
        drkmode = view.findViewById(R.id.darkmode_switch);


        SharedPreferences pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        if(pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")){
            drkmode.setChecked(true);
            drkmode.setText("Disable Dark Mode");

        }
        else if(pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")){
            drkmode.setChecked(false);
            drkmode.setText("Enable Dark Mode");
        }
        else{
            drkmode.setChecked(false);
            drkmode.setText("Enable Dark Mode");
        }
        drkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(drkmode.isChecked()){
                    drkmode.setText("Disable Dark Mode");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPreferences pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("DARKMODE_TOGGLE", "YES");
                    editor.commit();
                    getActivity().finish();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    drkmode.setText("Enable Dark Mode");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferences pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("DARKMODE_TOGGLE", "NO");
                    editor.commit();
                    getActivity().finish();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

}
