package com.shambu.passwordvault.Fragments_dir;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.shambu.passwordvault.MainActivity;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.Sql_dir.BANKING_CAT_sqlHelper;
import com.shambu.passwordvault.Sql_dir.BANKING_sqlHelper;
import com.shambu.passwordvault.Sql_dir.DEVICE_sqlHelper;
import com.shambu.passwordvault.Sql_dir.FAV_sqlHelper;
import com.shambu.passwordvault.Sql_dir.GSMOWOM_sqlHelper;

public class settings_fragment extends Fragment {

    private Switch drkmode;
    private CardView deleteCard;
    private AlertDialog.Builder deleteAlert;
    private BANKING_sqlHelper banking_sqlHelper;
    private BANKING_CAT_sqlHelper banking_cat_sqlHelper;
    private DEVICE_sqlHelper device_sqlHelper;
    private FAV_sqlHelper fav_sqlHelper;
    private GSMOWOM_sqlHelper gsmowom_sqlHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment_layout, container, false);
        banking_cat_sqlHelper = new BANKING_CAT_sqlHelper(getContext());
        banking_sqlHelper = new BANKING_sqlHelper(getContext());
        device_sqlHelper = new DEVICE_sqlHelper(getContext());
        fav_sqlHelper = new FAV_sqlHelper(getContext());
        gsmowom_sqlHelper = new GSMOWOM_sqlHelper(getContext());
        drkmode = view.findViewById(R.id.darkmode_switch);
        deleteCard = view.findViewById(R.id.deleteall_card);
        deleteAlert = new AlertDialog.Builder(getContext());


        SharedPreferences pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        if(pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")){
            drkmode.setChecked(true);

        }
        else if(pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")){
            drkmode.setChecked(false);
        }
        else{
            drkmode.setChecked(false);
        }
        drkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(drkmode.isChecked()){
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

        deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert.setMessage("Do you really want to delete all data?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                banking_cat_sqlHelper.deleteTABLE(banking_cat_sqlHelper.getWritableDatabase(MainActivity.lepass));
                                banking_sqlHelper.deleteTABLE(banking_sqlHelper.getWritableDatabase(MainActivity.lepass));
                                device_sqlHelper.deleteTABLE(device_sqlHelper.getWritableDatabase(MainActivity.lepass));
                                gsmowom_sqlHelper.deleteTABLE(gsmowom_sqlHelper.getWritableDatabase(MainActivity.lepass));
                                fav_sqlHelper.deleteTABLE(fav_sqlHelper.getWritableDatabase(MainActivity.lepass));
                                Toast.makeText(getContext(), "Deleted all saved passwords", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAlert.show().cancel();
                    }
                }).show();
            }
        });
        return view;
    }

}
