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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shambu.passwordvault.Adapters_dir.BANKINGDETAILS_adapter;
import com.shambu.passwordvault.Adapters_dir.BANKING_CAT_adapter;
import com.shambu.passwordvault.Data_classes.BANKING_data;
import com.shambu.passwordvault.MainActivity;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.Sql_dir.BANKING_sqlHelper;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class bankdetails_fragment extends Fragment implements BANKINGDETAILS_adapter.ClickAdapterListenerBankDetails {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private BANKINGDETAILS_adapter adapter;
    private List<BANKING_data> data_list;
    private BANKING_sqlHelper database;
    private TextView ttv;
    private Dialog addNew, editDialog;
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
            if (adapter.BANKINGDetailsgetSelectedItemCount() > 1) {
                item.setVisible(false);
            } else {
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
            adapter.BANKINGDetailsclearSelections();
            actionMode = null;
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bankdetails_frag_layout, container, false);
        recyclerView = view.findViewById(R.id.bankdetails_rv);
        fab = view.findViewById(R.id.bankdetails_fab);
        ttv = view.findViewById(R.id.toolbar_bankdetails_tv);
        ttv.setText(BANKING_CAT_adapter.whichbankname);
        SQLiteDatabase.loadLibs(getContext());
        database = new BANKING_sqlHelper(getContext());
        data_list = database.getAllAccountsofBank(BANKING_CAT_adapter.whichbankname, database.getReadableDatabase(MainActivity.lepass));
        adapter = new BANKINGDETAILS_adapter(data_list, getContext(), bankdetails_fragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

                if (pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")) {
                    addNew = new Dialog(getContext(), android.R.style.Theme_Material_NoActionBar);
                } else if (pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")) {
                    addNew = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                } else {
                    addNew = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                }
                addNew.setContentView(R.layout.add_new_bankaccount_dialog);
                final TextView accno, mail, phno, addr, netbuid, netbpass, cc1, cc1p, cc2, cc2p, cc3, cc3p, dc1, dc1p, dc2, dc2p, dc3, dc3p,
                        notes;
                Button bdSave;
                accno = addNew.findViewById(R.id.bankdetails_edt_accountnumber);
                mail = addNew.findViewById(R.id.bankdetails_edt_assoEmail);
                phno = addNew.findViewById(R.id.bankdetails_edt_assoPhno);
                addr = addNew.findViewById(R.id.bankdetails_edt_address);
                netbuid = addNew.findViewById(R.id.bankdetails_edt_userid);
                netbpass = addNew.findViewById(R.id.bankdetails_edt_netpass);
                notes = addNew.findViewById(R.id.bankdetails_edt_adiNotes);
                cc1 = addNew.findViewById(R.id.cc1);
                cc1p = addNew.findViewById(R.id.cc1p);
                cc2 = addNew.findViewById(R.id.cc2);
                cc2p = addNew.findViewById(R.id.cc2p);
                cc3 = addNew.findViewById(R.id.cc3);
                cc3p = addNew.findViewById(R.id.cc3p);
                dc1 = addNew.findViewById(R.id.dc1);
                dc1p = addNew.findViewById(R.id.dc1p);
                dc2 = addNew.findViewById(R.id.dc2);
                dc2p = addNew.findViewById(R.id.dc2p);
                dc3 = addNew.findViewById(R.id.dc3);
                dc3p = addNew.findViewById(R.id.dc3p);
                bdSave = addNew.findViewById(R.id.save_bankdetails_button);

                addNew.show();
                bdSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BANKING_data data = new BANKING_data();
                        data.setBankName(BANKING_CAT_adapter.whichbankname);
                        if (TextUtils.isEmpty(accno.getText().toString())) {
                            addNew.dismiss();
                        } else {
                            data.setAccountnum(accno.getText().toString());
                        }
                        data.setAssoBankmail((TextUtils.isEmpty(mail.getText().toString())) ? "" : mail.getText().toString());
                        data.setAssoBankPhno((TextUtils.isEmpty(phno.getText().toString())) ? "" : phno.getText().toString());
                        data.setBankAddress((TextUtils.isEmpty(addr.getText().toString())) ? "" : addr.getText().toString());
                        data.setNetBankinguserid((TextUtils.isEmpty(netbuid.getText().toString())) ? "" : netbuid.getText().toString());
                        data.setNetBankingpass((TextUtils.isEmpty(netbpass.getText().toString())) ? "" : netbpass.getText().toString());
                        data.setAdiNotes((TextUtils.isEmpty(notes.getText().toString())) ? "" : notes.getText().toString());
                        List<String> ccnos = new ArrayList<>();
                        List<String> dcnos = new ArrayList<>();
                        if (!TextUtils.isEmpty(cc1.getText().toString())) {
                            ccnos.add("[" + cc1.getText().toString() + "," + ((TextUtils.isEmpty(cc1p.getText().toString())) ? "" : cc1p.getText().toString()) + "]");
                        }
                        if (!TextUtils.isEmpty(cc2.getText().toString())) {
                            ccnos.add("[" + cc2.getText().toString() + "," + ((TextUtils.isEmpty(cc2p.getText().toString())) ? "" : cc2p.getText().toString()) + "]");
                        }
                        if (!TextUtils.isEmpty(cc3.getText().toString())) {
                            ccnos.add("[" + cc3.getText().toString() + "," + ((TextUtils.isEmpty(cc3p.getText().toString())) ? "" : cc3p.getText().toString()) + "]");
                        }
                        if (!TextUtils.isEmpty(dc1.getText().toString())) {
                            dcnos.add("[" + dc1.getText().toString() + "," + ((TextUtils.isEmpty(dc1p.getText().toString())) ? "" : dc1p.getText().toString()) + "]");
                        }
                        if (!TextUtils.isEmpty(dc2.getText().toString())) {
                            dcnos.add("[" + dc2.getText().toString() + "," + ((TextUtils.isEmpty(dc2p.getText().toString())) ? "" : dc2p.getText().toString()) + "]");
                        }
                        if (!TextUtils.isEmpty(dc3.getText().toString())) {
                            dcnos.add("[" + dc3.getText().toString() + "," + ((TextUtils.isEmpty(dc3p.getText().toString())) ? "" : dc3p.getText().toString()) + "]");
                        }
                        data.setCreditcardnum(ccnos.toString());
                        data.setDebitcardnum(dcnos.toString());

                        database = new BANKING_sqlHelper(getContext());
                        database.insertBankDetails(data, database.getWritableDatabase(MainActivity.lepass));

                        database = new BANKING_sqlHelper(getContext());
                        data_list = database.getAllAccountsofBank(BANKING_CAT_adapter.whichbankname, database.getReadableDatabase(MainActivity.lepass));
                        adapter = new BANKINGDETAILS_adapter(data_list, getContext(), bankdetails_fragment.this);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        addNew.dismiss();
                    }
                });

            }
        });


        return view;
    }

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
        adapter.BANKINGDetailstoggleSelection(position);
        int count = adapter.BANKINGDetailsgetSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private void selectAll() {
        adapter.BANKINGDetailsselectAll();
        int count = adapter.BANKINGDetailsgetSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }

        //  actionMode = null;
    }

 /*   private void deleteRows() {
        List<Integer> selectedItemPositions =
                adapter.BANKINGDetailsgetSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            adapter.BANKINGDetailsremoveData(selectedItemPositions.get(i));
        }
        adapter.notifyDataSetChanged();

        actionMode = null;
    }  */

    private void editSelected() {
        BANKING_data singleData = new BANKING_data();
        final List<Integer> selectedItemPositions =
                adapter.BANKINGDetailsgetSelectedItems();
        SharedPreferences pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        if (pref.getString("DARKMODE_TOGGLE", "NO").equals("YES")) {
            editDialog = new Dialog(getContext(), android.R.style.Theme_Material_NoActionBar);
        } else if (pref.getString("DARKMODE_TOGGLE", "NO").equals("NO")) {
            editDialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        } else {
            editDialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        }
        editDialog.setContentView(R.layout.add_new_bankaccount_dialog);

        final TextView accno, mail, phno, addr, netbuid, netbpass, cc1, cc1p, cc2, cc2p, cc3, cc3p, dc1, dc1p, dc2, dc2p, dc3, dc3p,
                notes;
        Button bdSave;
        accno = editDialog.findViewById(R.id.bankdetails_edt_accountnumber);
        mail = editDialog.findViewById(R.id.bankdetails_edt_assoEmail);
        phno = editDialog.findViewById(R.id.bankdetails_edt_assoPhno);
        addr = editDialog.findViewById(R.id.bankdetails_edt_address);
        netbuid = editDialog.findViewById(R.id.bankdetails_edt_userid);
        netbpass = editDialog.findViewById(R.id.bankdetails_edt_netpass);
        notes = editDialog.findViewById(R.id.bankdetails_edt_adiNotes);
        cc1 = editDialog.findViewById(R.id.cc1);
        cc1p = editDialog.findViewById(R.id.cc1p);
        cc2 = editDialog.findViewById(R.id.cc2);
        cc2p = editDialog.findViewById(R.id.cc2p);
        cc3 = editDialog.findViewById(R.id.cc3);
        cc3p = editDialog.findViewById(R.id.cc3p);
        dc1 = editDialog.findViewById(R.id.dc1);
        dc1p = editDialog.findViewById(R.id.dc1p);
        dc2 = editDialog.findViewById(R.id.dc2);
        dc2p = editDialog.findViewById(R.id.dc2p);
        dc3 = editDialog.findViewById(R.id.dc3);
        dc3p = editDialog.findViewById(R.id.dc3p);
        bdSave = editDialog.findViewById(R.id.save_bankdetails_button);

        accno.setText(data_list.get(selectedItemPositions.get(0)).getAccountnum());
        mail.setText(data_list.get(selectedItemPositions.get(0)).getAssoBankmail());
        phno.setText(data_list.get(selectedItemPositions.get(0)).getAssoBankPhno());
        addr.setText(data_list.get(selectedItemPositions.get(0)).getBankAddress());
        netbuid.setText(data_list.get(selectedItemPositions.get(0)).getNetBankinguserid());
        netbpass.setText(data_list.get(selectedItemPositions.get(0)).getNetBankingpass());
        notes.setText(data_list.get(selectedItemPositions.get(0)).getAdiNotes());

        String cnos = data_list.get(selectedItemPositions.get(0)).getCreditcardnum();
        String dnos = data_list.get(selectedItemPositions.get(0)).getDebitcardnum();
        int comma = 0;
        int nofc;
        int countcomma = 0;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < cnos.length(); i++) {
            if(cnos.charAt(i)==','){
                comma++;
            }
        }

        nofc = comma - ((comma-1)/2);

        for (int i = 0; i < cnos.length(); i++) {
            if(cnos.charAt(i)!='[' && cnos.charAt(i)!=']'){
                if(cnos.charAt(i)!=','){
                    builder.append(cnos.charAt(i));
                    if(i==cnos.length()-3){
                        if(nofc==3){
                            cc3p.setText(builder.toString());
                            break;
                        }
                        if(nofc==2){
                            cc2p.setText(builder.toString());
                            break;
                        }
                        if(nofc==1){
                            cc1p.setText(builder.toString());
                            break;
                        }

                    }
                }
                if(cnos.charAt(i)==','){
                    countcomma++;
                    if(countcomma==1){
                        cc1.setText(builder.toString());
                        builder = new StringBuilder();
                    }
                    if(countcomma==2){
                        cc1p.setText(builder.toString());
                        builder = new StringBuilder();
                    }
                    if(countcomma==3){
                        cc2.setText(builder.toString());
                        builder = new StringBuilder();
                    }
                    if(countcomma==4){
                        cc2p.setText(builder.toString());
                        builder = new StringBuilder();
                    }
                    if(countcomma==5){
                        cc3.setText(builder.toString());
                        builder = new StringBuilder();
                    }
                }

            }
        }
        comma = 0;
        for (int i = 0; i < dnos.length(); i++) {
            if(dnos.charAt(i)==','){
                comma++;
            }
        }
        nofc = comma - ((comma-1)/2);
        countcomma = 0;
        builder = new StringBuilder();

        for (int i = 0; i < dnos.length(); i++) {
            if(dnos.charAt(i)!='[' && dnos.charAt(i)!=']'){
                if(dnos.charAt(i)!=','){
                    builder.append(dnos.charAt(i));
                    if(i==dnos.length()-3){
                        if(nofc==3){
                            dc3p.setText(builder.toString());
                            break;
                        }
                        if(nofc==2){
                            dc2p.setText(builder.toString());
                            break;
                        }
                        if(nofc==1){
                            dc1p.setText(builder.toString());
                            break;
                        }

                    }
                }
                if(dnos.charAt(i)==','){
                    countcomma++;
                    if(countcomma==1){
                        dc1.setText(builder.toString());
                        builder = new StringBuilder();
                    }
                    if(countcomma==2){
                        dc1p.setText(builder.toString());
                        builder = new StringBuilder();
                    }
                    if(countcomma==3){
                        dc2.setText(builder.toString());
                        builder = new StringBuilder();
                    }
                    if(countcomma==4){
                        dc2p.setText(builder.toString());
                        builder = new StringBuilder();
                    }
                    if(countcomma==5){
                        dc3.setText(builder.toString());
                        builder = new StringBuilder();
                    }
                }

            }
        }

        editDialog.show();

        bdSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BANKING_data data = new BANKING_data();
                data.setBankName(BANKING_CAT_adapter.whichbankname);
                if (TextUtils.isEmpty(accno.getText().toString())) {
                    editDialog.dismiss();
                } else {
                    data.setAccountnum(accno.getText().toString());
                }
                data.setAssoBankmail((TextUtils.isEmpty(mail.getText().toString())) ? "" : mail.getText().toString());
                data.setAssoBankPhno((TextUtils.isEmpty(phno.getText().toString())) ? "" : phno.getText().toString());
                data.setBankAddress((TextUtils.isEmpty(addr.getText().toString())) ? "" : addr.getText().toString());
                data.setNetBankinguserid((TextUtils.isEmpty(netbuid.getText().toString())) ? "" : netbuid.getText().toString());
                data.setNetBankingpass((TextUtils.isEmpty(netbpass.getText().toString())) ? "" : netbpass.getText().toString());
                data.setAdiNotes((TextUtils.isEmpty(notes.getText().toString())) ? "" : notes.getText().toString());
                List<String> ccnos = new ArrayList<>();
                List<String> dcnos = new ArrayList<>();
                if (!TextUtils.isEmpty(cc1.getText().toString())) {
                    ccnos.add("[" + cc1.getText().toString() + "," + ((TextUtils.isEmpty(cc1p.getText().toString())) ? "" : cc1p.getText().toString()) + "]");
                }
                if (!TextUtils.isEmpty(cc2.getText().toString())) {
                    ccnos.add("[" + cc2.getText().toString() + "," + ((TextUtils.isEmpty(cc2p.getText().toString())) ? "" : cc2p.getText().toString()) + "]");
                }
                if (!TextUtils.isEmpty(cc3.getText().toString())) {
                    ccnos.add("[" + cc3.getText().toString() + "," + ((TextUtils.isEmpty(cc3p.getText().toString())) ? "" : cc3p.getText().toString()) + "]");
                }
                if (!TextUtils.isEmpty(dc1.getText().toString())) {
                    dcnos.add("[" + dc1.getText().toString() + "," + ((TextUtils.isEmpty(dc1p.getText().toString())) ? "" : dc1p.getText().toString()) + "]");
                }
                if (!TextUtils.isEmpty(dc2.getText().toString())) {
                    dcnos.add("[" + dc2.getText().toString() + "," + ((TextUtils.isEmpty(dc2p.getText().toString())) ? "" : dc2p.getText().toString()) + "]");
                }
                if (!TextUtils.isEmpty(dc3.getText().toString())) {
                    dcnos.add("[" + dc3.getText().toString() + "," + ((TextUtils.isEmpty(dc3p.getText().toString())) ? "" : dc3p.getText().toString()) + "]");
                }
                data.setCreditcardnum(ccnos.toString());
                data.setDebitcardnum(dcnos.toString());

                database = new BANKING_sqlHelper(getContext());
                database.updateBankDetails(data, data_list.get(selectedItemPositions.get(0)).getbSwlID(), database.getWritableDatabase(MainActivity.lepass));
                database = new BANKING_sqlHelper(getContext());
                data_list = database.getAllAccountsofBank(BANKING_CAT_adapter.whichbankname, database.getReadableDatabase(MainActivity.lepass));
                adapter = new BANKINGDETAILS_adapter(data_list, getContext(), bankdetails_fragment.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                editDialog.dismiss();
            }
        });
    }

    private void deleteRows(){
        List<Integer> selectedItemPositions =
                adapter.BANKINGDetailsgetSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            adapter.BANKINGDetailsremoveData(selectedItemPositions.get(i));
        }
        adapter.notifyDataSetChanged();

        actionMode = null;
    }
}
