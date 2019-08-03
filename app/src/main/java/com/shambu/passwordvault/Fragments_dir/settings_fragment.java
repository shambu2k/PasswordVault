package com.shambu.passwordvault.Fragments_dir;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.shambu.passwordvault.MainActivity;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.Sql_dir.BANKING_CAT_sqlHelper;
import com.shambu.passwordvault.Sql_dir.BANKING_sqlHelper;
import com.shambu.passwordvault.Sql_dir.DEVICE_sqlHelper;
import com.shambu.passwordvault.Sql_dir.FAV_sqlHelper;
import com.shambu.passwordvault.Sql_dir.GSMOWOM_sqlHelper;
import com.shambu.passwordvault.Sql_dir.password_text_sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class settings_fragment extends Fragment {

    private Switch drkmode;
    private CardView deleteCard, reportCard, importCard, exportCard;
    private AlertDialog.Builder deleteAlert;
    private Spinner sortSpinner;
    private BANKING_sqlHelper banking_sqlHelper;
    private BANKING_CAT_sqlHelper banking_cat_sqlHelper;
    private DEVICE_sqlHelper device_sqlHelper;
    private FAV_sqlHelper fav_sqlHelper;
    private GSMOWOM_sqlHelper gsmowom_sqlHelper;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private ArrayAdapter<CharSequence> sort_spin_adapter;


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
        reportCard = view.findViewById(R.id.reportissue_card);
        deleteCard = view.findViewById(R.id.deleteall_card);
        importCard = view.findViewById(R.id.import_card);
        exportCard = view.findViewById(R.id.export_card);
        deleteAlert = new AlertDialog.Builder(getContext());
        sortSpinner = view.findViewById(R.id.sort_spinner);
        sortSpinner.setOnItemSelectedListener(new sort_spinner_class());
        sort_spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.SORT_TYPE, android.R.layout.simple_spinner_item);
        sort_spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sort_spin_adapter);
        pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = pref.edit();

        settingsSavedPref();


        drkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(drkmode.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putString("DARKMODE_TOGGLE", "YES");
                    editor.apply();
                    getActivity().finish();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putString("DARKMODE_TOGGLE", "NO");
                    editor.apply();
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

        reportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] receiver = {"sidharthshambu00@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, receiver);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Password Vault - Issue");
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Choose email client"));
            }
        });

        exportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportDB(BANKING_CAT_sqlHelper.DATABASE_NAME);
                exportDB(BANKING_sqlHelper.DATABASE_NAME);
                exportDB(DEVICE_sqlHelper.DATABASE_NAME);
                exportDB(FAV_sqlHelper.DATABASE_NAME);
                exportDB(GSMOWOM_sqlHelper.DATABASE_NAME);
                exportDB(password_text_sql.DATABASE_NAME);

            }
        });

        return view;
    }

    private void settingsSavedPref(){
        if(pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")){
            drkmode.setChecked(true);

        }
        else if(pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")){
            drkmode.setChecked(false);
        }
        else{
            drkmode.setChecked(false);
        }

        if(pref.getString("SORT", "Alphabetically / Ascending").equals("Alphabetically / Ascending")){
            sortSpinner.setSelection(sort_spin_adapter.getPosition("Alphabetically / Ascending"));
        }
        else if(pref.getString("SORT", "Alphabetically / Ascending").equals("Zalphabetically / Descending")){
            sortSpinner.setSelection(sort_spin_adapter.getPosition("Zalphabetically / Descending"));
        }
        else if(pref.getString("SORT", "Alphabetically / Ascending").equals("Newest first")){
            sortSpinner.setSelection(sort_spin_adapter.getPosition("Newest first"));
        }
        else {
            sortSpinner.setSelection(sort_spin_adapter.getPosition("Oldest first"));
        }
    }

    private class sort_spinner_class implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            editor.putString("SORT", parent.getItemAtPosition(position).toString());
            editor.apply();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void exportDB(String dbname) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+ "com.shambu.passwordvault" +"//databases//"+dbname;
                String backupDBPath = "/PassV/"+dbname;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getContext(), backupDB.toString(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
