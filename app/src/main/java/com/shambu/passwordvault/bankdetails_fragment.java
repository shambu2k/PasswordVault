package com.shambu.passwordvault;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class bankdetails_fragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private BANKINGDETAILS_adapter adapter;
    private List<BANKING_data> data_list;
    private BANKING_sqlHelper database;
    private TextView ttv;
    private Dialog addNew;



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
        data_list = database.getAllAccountsofBank(BANKING_CAT_adapter.whichbankname, database.getReadableDatabase(getString(R.string.yek_lsq)));
        adapter = new BANKINGDETAILS_adapter(data_list, getContext());
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
                        if(TextUtils.isEmpty(accno.getText().toString())){
                            addNew.dismiss();
                        }
                        else{
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
                        if(!TextUtils.isEmpty(cc1.getText().toString())) {
                            ccnos.add("["+cc1.getText().toString()+","+((TextUtils.isEmpty(cc1p.getText().toString())) ? "" : cc1p.getText().toString())+"]");
                        }
                        if(!TextUtils.isEmpty(cc2.getText().toString())) {
                            ccnos.add("["+cc2.getText().toString()+","+((TextUtils.isEmpty(cc2p.getText().toString())) ? "" : cc2p.getText().toString())+"]");
                        }
                        if(!TextUtils.isEmpty(cc3.getText().toString())) {
                            ccnos.add("["+cc3.getText().toString()+","+((TextUtils.isEmpty(cc3p.getText().toString())) ? "" : cc3p.getText().toString())+"]");
                        }
                        if(!TextUtils.isEmpty(dc1.getText().toString())) {
                            dcnos.add("["+dc1.getText().toString()+","+((TextUtils.isEmpty(dc1p.getText().toString())) ? "" : dc1p.getText().toString())+"]");
                        }
                        if(!TextUtils.isEmpty(dc2.getText().toString())) {
                            dcnos.add("["+dc2.getText().toString()+","+((TextUtils.isEmpty(dc2p.getText().toString())) ? "" : dc2p.getText().toString())+"]");
                        }
                        if(!TextUtils.isEmpty(dc3.getText().toString())) {
                            dcnos.add("["+dc3.getText().toString()+","+((TextUtils.isEmpty(dc3p.getText().toString())) ? "" : dc3p.getText().toString())+"]");
                        }
                        data.setCreditcardnum(ccnos.toString());
                        data.setDebitcardnum(dcnos.toString());

                        database = new BANKING_sqlHelper(getContext());
                        database.insertBankDetails(data, database.getWritableDatabase(getString(R.string.yek_lsq)));

                        database = new BANKING_sqlHelper(getContext());
                        data_list = database.getAllAccountsofBank(BANKING_CAT_adapter.whichbankname, database.getReadableDatabase(getString(R.string.yek_lsq)));
                        adapter = new BANKINGDETAILS_adapter(data_list, getContext());
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
}
