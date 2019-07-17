package com.shambu.passwordvault.Adapters_dir;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shambu.passwordvault.Data_classes.BANKING_data;
import com.shambu.passwordvault.MainActivity;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.Sql_dir.BANKING_sqlHelper;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BANKINGDETAILS_adapter extends RecyclerView.Adapter<BANKINGDETAILS_adapter.BANKINGDETAILS_customViewholder> {

    private static int currentSelectedIndex = -1;
    private List<BANKING_data> adapter_list;
    private ClickAdapterListenerBankDetails listener;
    private SparseBooleanArray selectedItems;
    private Context mContext;
    private BANKING_sqlHelper database;
    private boolean flag = true;

    private String msg = BANKINGDETAILS_adapter.class.getSimpleName();

    public BANKINGDETAILS_adapter(List<BANKING_data> adapter_list, Context mContext, ClickAdapterListenerBankDetails listener) {
        this.adapter_list = adapter_list;
        this.mContext = mContext;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
    }

    private void databaseInit(){
        if(flag) {
            SQLiteDatabase.loadLibs(mContext);
            database = new BANKING_sqlHelper(mContext);
        }
    }

    @NonNull
    @Override
    public BANKINGDETAILS_adapter.BANKINGDETAILS_customViewholder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bankdetails_rv_item, parent, false);

        return new BANKINGDETAILS_customViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BANKINGDETAILS_adapter.BANKINGDETAILS_customViewholder holder, int position) {
        BANKING_data data = adapter_list.get(position);
        holder.actv.setText("Acc no: "+data.getAccountnum());
        if(!data.getAssoBankmail().equals("")){
            holder.email.setVisibility(View.VISIBLE);
            holder.email.setText("Email: "+data.getAssoBankmail());
        }
        else{
            holder.email.setVisibility(View.GONE);
        }
        if(!data.getAssoBankPhno().equals("")){
            holder.phno.setVisibility(View.VISIBLE);
            holder.phno.setText("Ph no: "+data.getAssoBankPhno());
        }
        else{
            holder.phno.setVisibility(View.GONE);
        }
        if(!data.getBankAddress().equals("")){
            holder.addr.setVisibility(View.VISIBLE);
            holder.addr.setText("Address: "+data.getBankAddress());
        }
        else{
            holder.addr.setVisibility(View.GONE);
        }
        if(!data.getNetBankinguserid().equals("")){
            holder.netbid.setVisibility(View.VISIBLE);
            holder.netbid.setText("Netbanking userID: "+data.getNetBankinguserid());
        }
        else{
            holder.netbid.setVisibility(View.GONE);
        }
        if(!data.getNetBankingpass().equals("")){
            holder.netbpass.setVisibility(View.VISIBLE);
            holder.netbpass.setText("Netbanking password: "+data.getNetBankingpass());
        }
        else{
            holder.netbpass.setVisibility(View.GONE);
        }
        if(!data.getCreditcardnum().equals("[]")){
            holder.ccnos.setVisibility(View.VISIBLE);
            holder.ccnos.setText("Credit card nos and PIN:\n"+data.getCreditcardnum());
        }
        else{
            holder.ccnos.setVisibility(View.GONE);
        }
        if(!data.getDebitcardnum().equals("[]")){
            holder.dcnos.setVisibility(View.VISIBLE);
            holder.dcnos.setText("Debit card nos and PIN:\n"+data.getDebitcardnum());
        }
        else{
            holder.dcnos.setVisibility(View.GONE);
        }
        if(!data.getAdiNotes().equals("")){
            holder.adinotes.setVisibility(View.VISIBLE);
            holder.adinotes.setText("Notes: "+data.getAdiNotes());
        }
        else{
            holder.adinotes.setVisibility(View.GONE);
        }
        holder.itemView.setActivated(selectedItems.get(position, false));

        applyClickEvents(holder, position);

    }

    @Override
    public int getItemCount() {
        return adapter_list.size();
    }

    private void applyClickEvents(BANKINGDETAILS_customViewholder holder, final int position){
        Log.d(msg, "applyClickEvents method called (BANKINGDetails adapter class)");

        holder.bdcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(msg, "applyClickEvents setOnClickListener method called (BANKINGDetails adapter class)");
                listener.onRowClicked(position);
            }
        });

        holder.bdcard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(msg, "applyClickEvents setOnLongClickListener method called (BANKINGDetails adapter class)");
                listener.onRowLongClicked(position);
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
    }

    public void BANKINGDetailstoggleSelection(int pos) {
        Log.d(msg, "toggleSelection method called (BANKINGDetails adapter class)");
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void BANKINGDetailsselectAll() {
        Log.d(msg, "selectAll method called (BANKINGDetails adapter class)");
        for (int i = 0; i < getItemCount(); i++)
            selectedItems.put(i, true);
        notifyDataSetChanged();
    }

    public void BANKINGDetailsclearSelections() {
        Log.d(msg, "clearSelections method called (BANKINGDetails adapter class)");
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int BANKINGDetailsgetSelectedItemCount() {
        Log.d(msg, "getSelectedItemCount method called (BANKINGDetails adapter class)");
        return selectedItems.size();
    }

    public List<Integer> BANKINGDetailsgetSelectedItems() {
        Log.d(msg, "getSelectedItems method called (BANKINGDetails adapter class)");
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    private void resetCurrentIndex() {
        Log.d(msg, "resetCurrentIndex method called (DEVICE adapter class)");
        currentSelectedIndex = -1;
    }

    public void BANKINGDetailsremoveData(int position){
        databaseInit();
        flag = false;
        database.deleteAccountDetails(adapter_list.get(position).getbSwlID(), database.getWritableDatabase(MainActivity.lepass));
        adapter_list.remove(position);
        resetCurrentIndex();
    }

    public String BANKINGDetailsshareData(int position){
        String singleShare = "Bank: "+adapter_list.get(position).getBankName()+"\n"+
                "Account Number: "+adapter_list.get(position).getAccountnum()+"\n"+
                "Associated Mail: "+adapter_list.get(position).getAssoBankmail()+"\n"+
                "Associated Phno: "+adapter_list.get(position).getAssoBankPhno()+"\n"+
                "Registered Address: "+adapter_list.get(position).getBankAddress()+"\n"+
                "NetBanking UserID: "+adapter_list.get(position).getNetBankinguserid()+"\n"+
                "NetBanking password: "+adapter_list.get(position).getNetBankingpass()+"\n"+
                "Credit card numbers and pin: "+adapter_list.get(position).getCreditcardnum()+"\n"+
                "Debit card numbers and pin: "+adapter_list.get(position).getDebitcardnum()+"\n"+
                "Notes: "+adapter_list.get(position).getAdiNotes()+"\n";
        return singleShare;
    }

    public interface ClickAdapterListenerBankDetails {

        void onRowClicked(int position);

        void onRowLongClicked(int position);
    }

    public class BANKINGDETAILS_customViewholder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView actv, email, phno, addr, netbid, netbpass, ccnos, dcnos, adinotes;
        CardView bdcard;

        public BANKINGDETAILS_customViewholder(@NonNull View itemView) {
            super(itemView);
            actv = itemView.findViewById(R.id.accountnum_tv);
            email = itemView.findViewById(R.id.bdassomail_tv);
            phno = itemView.findViewById(R.id.bdassophno_tv);
            addr = itemView.findViewById(R.id.bdaddress_tv);
            netbid = itemView.findViewById(R.id.netbankuid_tv);
            netbpass = itemView.findViewById(R.id.netbankpass_tv);
            ccnos = itemView.findViewById(R.id.cc_tv);
            dcnos = itemView.findViewById(R.id.dc_tv);
            adinotes = itemView.findViewById(R.id.bdadinotes_tv);
            bdcard = itemView.findViewById(R.id.card_item_ofbankdetailsRV);
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onRowLongClicked(getAdapterPosition());
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }
}
