package com.shambu.passwordvault.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDelegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.shambu.passwordvault.R;

public class settings_fragment extends Fragment {

    private Switch drkmode;
    private LinearLayout deleteCard, reportCard, importCard, exportCard;
  //  private AlertDialog.Builder deleteAlert;
  //  private Spinner sortSpinner;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
  //  private ArrayAdapter<CharSequence> sort_spin_adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment_layout, container, false);
        drkmode = view.findViewById(R.id.darkmode_switch);
        reportCard = view.findViewById(R.id.reportissue_card);
    /*    deleteCard = view.findViewById(R.id.deleteall_card);
        importCard = view.findViewById(R.id.import_card);
        exportCard = view.findViewById(R.id.export_card);
        deleteAlert = new AlertDialog.Builder(getContext());
        sortSpinner = view.findViewById(R.id.sort_spinner);
        sortSpinner.setOnItemSelectedListener(new sort_spinner_class());
        sort_spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.SORT_TYPE, android.R.layout.simple_spinner_item);
        sort_spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sort_spin_adapter); */
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
                    getActivity().recreate();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putString("DARKMODE_TOGGLE", "NO");
                    editor.apply();
                    getActivity().recreate();
                }
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

     /*   deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert.setMessage("Do you really want to delete all data?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(getContext(), "not Deleted all saved passwords", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAlert.show().cancel();
                    }
                }).show();
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
        }); */

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

    /*    if(pref.getString("SORT", "Alphabetically / Ascending").equals("Alphabetically / Ascending")){
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
        } */
    }

  /*  private class sort_spinner_class implements AdapterView.OnItemSelectedListener{

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
    } */

}
