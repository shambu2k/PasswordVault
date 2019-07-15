package com.shambu.passwordvault.Fragments_dir.passwords_innerFrags;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shambu.passwordvault.Adapters_dir.GSMOWOM_adapter;
import com.shambu.passwordvault.Data_classes.GSMOWOM_data;
import com.shambu.passwordvault.Fragments_dir.passwords_fragment;
import com.shambu.passwordvault.MainActivity;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.Sql_dir.GSMOWOM_sqlHelper;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.List;

public class otherwebsites_fragment extends Fragment implements GSMOWOM_adapter.ClickAdapterListener {

    private RecyclerView recyclerView;
    private GSMOWOM_adapter adapter;
    private FloatingActionButton fab;
    private List<GSMOWOM_data> data_list;
    private GSMOWOM_sqlHelper database;
    private Dialog addNew, editDialog;
    private String msg = otherwebsites_fragment.class.getSimpleName();
    private ActionMode actionMode;
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.cab_menu, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            MenuItem item = menu.getItem(1);
            if(adapter.getSelectedItemCount() > 1){
                item.setVisible(false);
            }
            else {
                item.setVisible(true);
            }
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Log.d("API123", "here");
            switch (item.getItemId()) {


                case R.id.action_delete:
                    // delete all the selected rows
                    deleteRows();
                    mode.finish();
                    return true;

                case R.id.action_edit:
                    editSelected();
                    mode.finish();
                    return true;

                case R.id.action_select_all:
                    selectAll();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelections();
            actionMode = null;
        }
    };

    @Override
    public void onRowClicked(int position) {
        enableActionMode(position);
    }

    @Override
    public void onRowLongClicked(int position) {
        enableActionMode(position);
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private void selectAll() {
        adapter.selectAll();
        int count = adapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }

     //   actionMode = null;
    }

    private void deleteRows() {
        List<Integer> selectedItemPositions =
                adapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            adapter.removeData(selectedItemPositions.get(i));
        }
        adapter.notifyDataSetChanged();

        actionMode = null;
    }

    private void editSelected(){
        final List<Integer> selectedItemPositions =
                adapter.getSelectedItems();
        SharedPreferences pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        if(pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")){
            editDialog = new Dialog(getContext(), android.R.style.Theme_Material_NoActionBar);
        }
        else if(pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")){
            editDialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        }
        else{
            editDialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        }
        editDialog.setContentView(R.layout.add_new_gsmowom_dialog);
        final TextView site, mail, phnum, userid, passwrd, notes;
        Button save_butt;
        site = editDialog.findViewById(R.id.gsmowom_edt_siteName);
        mail = editDialog.findViewById(R.id.gsmowom_edt_assoEmail);
        phnum = editDialog.findViewById(R.id.gsmowom_edt_assoPhno);
        userid = editDialog.findViewById(R.id.gsmowom_edt_username);
        passwrd = editDialog.findViewById(R.id.gsmowom_edt_pass);
        notes = editDialog.findViewById(R.id.gsmowom_edt_adiInfo);
        save_butt = editDialog.findViewById(R.id.save_gsmowom_button);
        site.setText(data_list.get(selectedItemPositions.get(0)).getD_provider());
        mail.setText(data_list.get(selectedItemPositions.get(0)).getD_assoEmail());
        phnum.setText(data_list.get(selectedItemPositions.get(0)).getD_assoPhno());
        userid.setText(data_list.get(selectedItemPositions.get(0)).getD_username());
        passwrd.setText(data_list.get(selectedItemPositions.get(0)).getD_pass());
        notes.setText(data_list.get(selectedItemPositions.get(0)).getD_adiInfo());
        editDialog.show();

        save_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GSMOWOM_data sample_data = new GSMOWOM_data(passwords_fragment.which_type,
                        TextUtils.isEmpty(site.getText().toString()) ? "" : site.getText().toString(),
                        TextUtils.isEmpty(mail.getText().toString()) ? "" : mail.getText().toString(),
                        TextUtils.isEmpty(phnum.getText().toString()) ? "" : phnum.getText().toString(),
                        TextUtils.isEmpty(userid.getText().toString()) ? "" : userid.getText().toString(),
                        TextUtils.isEmpty(passwrd.getText().toString()) ? "" : passwrd.getText().toString(),
                        TextUtils.isEmpty(notes.getText().toString()) ? "" : notes.getText().toString());
                if(!sample_data.getD_provider().equals("") ||
                        !sample_data.getD_assoEmail().equals("") ||
                        !sample_data.getD_assoPhno().equals("") ||
                        !sample_data.getD_username().equals("") ||
                        !sample_data.getD_pass().equals("") ||
                        !sample_data.getD_adiInfo().equals("")) {
                    database = new GSMOWOM_sqlHelper(getContext());
                    SQLiteDatabase dbW = database.getWritableDatabase(MainActivity.lepass);
                    database.updateRow(sample_data, data_list.get(selectedItemPositions.get(0)).getD_ID(), dbW);
                    SQLiteDatabase dbR = database.getReadableDatabase(MainActivity.lepass);
                    data_list = database.getData(passwords_fragment.which_type, "OtherWebsites", dbR);
                    Log.d(msg, "type: "+passwords_fragment.which_type+" prov: OtherWebsites");
                    adapter = new GSMOWOM_adapter(data_list, getContext(), otherwebsites_fragment.this);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    editDialog.dismiss();
                }
                else{
                    editDialog.dismiss();
                }

            }
        });
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otherwebsites_frag_layout, container, false);
        SQLiteDatabase.loadLibs(getContext());
        recyclerView = view.findViewById(R.id.otherwebsites_rv);
        fab = view.findViewById(R.id.otherwebsites_fab);
        database = new GSMOWOM_sqlHelper(getContext());
        SQLiteDatabase dbR = database.getReadableDatabase(MainActivity.lepass);
        data_list = database.getData(passwords_fragment.which_type, "OtherWebsites", dbR);
        Log.d(msg, "type: "+passwords_fragment.which_type+" prov: OtherWebsites");
        adapter = new GSMOWOM_adapter(data_list, getContext(), otherwebsites_fragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

                if(pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")){
                    addNew = new Dialog(getContext(), android.R.style.Theme_Material_NoActionBar);
                }
                else if(pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")){
                    addNew = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                }
                else{
                    addNew = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                }
                addNew.setContentView(R.layout.add_new_gsmowom_dialog);
                final TextView site, mail, phnum, userid, passwrd, notes;
                Button save_butt;
                site = addNew.findViewById(R.id.gsmowom_edt_siteName);
                mail = addNew.findViewById(R.id.gsmowom_edt_assoEmail);
                phnum = addNew.findViewById(R.id.gsmowom_edt_assoPhno);
                userid = addNew.findViewById(R.id.gsmowom_edt_username);
                passwrd = addNew.findViewById(R.id.gsmowom_edt_pass);
                notes = addNew.findViewById(R.id.gsmowom_edt_adiInfo);
                save_butt = addNew.findViewById(R.id.save_gsmowom_button);
                addNew.show();
                save_butt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GSMOWOM_data sample_data = new GSMOWOM_data(passwords_fragment.which_type,
                                TextUtils.isEmpty(site.getText().toString()) ? "" : site.getText().toString(),
                                TextUtils.isEmpty(mail.getText().toString()) ? "" : mail.getText().toString(),
                                TextUtils.isEmpty(phnum.getText().toString()) ? "" : phnum.getText().toString(),
                                TextUtils.isEmpty(userid.getText().toString()) ? "" : userid.getText().toString(),
                                TextUtils.isEmpty(passwrd.getText().toString()) ? "" : passwrd.getText().toString(),
                                TextUtils.isEmpty(notes.getText().toString()) ? "" : notes.getText().toString());
                        if(!sample_data.getD_provider().equals("") ||
                                !sample_data.getD_assoEmail().equals("") ||
                                !sample_data.getD_assoPhno().equals("") ||
                                !sample_data.getD_username().equals("") ||
                                !sample_data.getD_pass().equals("") ||
                                !sample_data.getD_adiInfo().equals("")) {
                            database = new GSMOWOM_sqlHelper(getContext());
                            SQLiteDatabase dbW = database.getWritableDatabase(MainActivity.lepass);
                            database.insertData(sample_data, dbW);
                            SQLiteDatabase dbR = database.getReadableDatabase(MainActivity.lepass);
                            data_list = database.getData(passwords_fragment.which_type, sample_data.getD_provider(), dbR);
                            adapter = new GSMOWOM_adapter(data_list, getContext(), otherwebsites_fragment.this);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                            addNew.dismiss();
                        }
                        else{
                            addNew.dismiss();
                        }

                    }
                });

            }
        });

        return view;
    }


}
