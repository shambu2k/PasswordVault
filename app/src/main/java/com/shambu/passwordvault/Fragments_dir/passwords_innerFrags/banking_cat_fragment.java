package com.shambu.passwordvault.Fragments_dir.passwords_innerFrags;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shambu.passwordvault.Adapters_dir.BANKING_CAT_adapter;
import com.shambu.passwordvault.Data_classes.BANKINGCAT_data;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.Sql_dir.BANKING_CAT_sqlHelper;
import com.shambu.passwordvault.Sql_dir.BANKING_sqlHelper;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.List;

public class banking_cat_fragment extends Fragment implements BANKING_CAT_adapter.ClickAdapterListenerBankingCat {

    private RecyclerView recyclerView;
    private BANKING_CAT_adapter adapter;
    private FloatingActionButton fab;
    private List<BANKINGCAT_data> data_list;
    private Dialog addNew, editDialog;
    private BANKING_CAT_sqlHelper database;
    private BANKING_sqlHelper bankingdetailsdatabase;
    private ProgressDialog progressDialog;
    private ActionMode actionMode;
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.cab_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            MenuItem item = menu.getItem(1);
            if(adapter.BCgetSelectedItemCount() > 1){
                item.setVisible(false);
            }
            else {
                item.setVisible(true);
            }
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {


                case R.id.action_delete:
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
            adapter.BCclearSelections();
            actionMode = null;
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bankingcat_frag_layout, container, false);
        recyclerView = view.findViewById(R.id.banking_categories_rv);
        fab = view.findViewById(R.id.bankingcat_fab);
        SQLiteDatabase.loadLibs(getContext());
        database = new BANKING_CAT_sqlHelper(getContext());
        data_list = database.getAllBnames(database.getReadableDatabase(getString(R.string.yek_lsq)));
        adapter = new BANKING_CAT_adapter(getContext(), data_list, banking_cat_fragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BANKINGCAT_data singleData = new BANKINGCAT_data();
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
                addNew.setContentView(R.layout.add_new_bankingcat_dialog);
                final TextView bname;
                Button bcSave;
                bname = addNew.findViewById(R.id.bankingcat_edt_bankName);
                bcSave = addNew.findViewById(R.id.save_bankingcat_button);
                addNew.show();

                bcSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(bname.getText().toString())){
                            database = new BANKING_CAT_sqlHelper(getContext());
                            singleData.setBank_name(bname.getText().toString());
                            database.insertBankName(singleData, database.getWritableDatabase(getString(R.string.yek_lsq)));

                            database = new BANKING_CAT_sqlHelper(getContext());
                            data_list = database.getAllBnames(database.getReadableDatabase(getString(R.string.yek_lsq)));
                            adapter = new BANKING_CAT_adapter(getContext(), data_list, banking_cat_fragment.this);
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
        adapter.BCtoggleSelection(position);
        int count = adapter.BCgetSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private void selectAll() {
        adapter.BCselectAll();
        int count = adapter.BCgetSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }

        //  actionMode = null;
    }

    private void editSelected() {
        int count = adapter.BCgetSelectedItemCount();
        if(count==0){
            actionMode.finish();
        }
        else{
            final BANKINGCAT_data singleData = new BANKINGCAT_data();
            final List<Integer> selectedItemPositions =
                    adapter.BCgetSelectedItems();
            SharedPreferences pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

            if (pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")) {
                editDialog = new Dialog(getContext(), android.R.style.Theme_Material_NoActionBar);
            } else if (pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")) {
                editDialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
            } else {
                editDialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
            }
            editDialog.setContentView(R.layout.add_new_bankingcat_dialog);

            final TextView bname;
            Button bcSave;
            bname = editDialog.findViewById(R.id.bankingcat_edt_bankName);
            bcSave = editDialog.findViewById(R.id.save_bankingcat_button);
            bname.setText(data_list.get(selectedItemPositions.get(0)).getBank_name());
            editDialog.show();

            bcSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!TextUtils.isEmpty(bname.getText().toString())){
                        database = new BANKING_CAT_sqlHelper(getContext());
                        singleData.setBank_name(bname.getText().toString());
                        database.updatebankName(singleData, database.getWritableDatabase(getString(R.string.yek_lsq)), data_list.get(selectedItemPositions.get(0)).getBcSqlID());

                        bankingdetailsdatabase = new BANKING_sqlHelper(getContext());
                        bankingdetailsdatabase.bankNameupdate(bname.getText().toString(), data_list.get(selectedItemPositions.get(0)).getBank_name(), bankingdetailsdatabase.getWritableDatabase(getString(R.string.yek_lsq)));

                        database = new BANKING_CAT_sqlHelper(getContext());
                        data_list = database.getAllBnames(database.getReadableDatabase(getString(R.string.yek_lsq)));
                        adapter = new BANKING_CAT_adapter(getContext(), data_list, banking_cat_fragment.this);
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
        }
    }

    private void deleteRows(){
        List<Integer> selectedItemPositions =
                adapter.BCgetSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            adapter.BCremoveData(selectedItemPositions.get(i));
        }
        adapter.notifyDataSetChanged();

        actionMode = null;
    }
}
