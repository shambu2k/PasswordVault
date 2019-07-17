package com.shambu.passwordvault.Adapters_dir;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shambu.passwordvault.Data_classes.BANKING_data;
import com.shambu.passwordvault.Data_classes.DEVICE_data;
import com.shambu.passwordvault.Data_classes.FAV_data;
import com.shambu.passwordvault.Data_classes.GSMOWOM_data;
import com.shambu.passwordvault.MainActivity;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.Sql_dir.FAV_sqlHelper;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FAV_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int TYPE_GSMOWOM = 1;
    private static int TYPE_DEVICE = 2;
    private static int TYPE_BANK = 3;
    private static int currentSelectedIndex = -1;
    private List<FAV_data> adapter_list;
    private ClickAdapterListenerFav listener;
    private SparseBooleanArray selectedItems;
    private Context mContext;
    private FAV_sqlHelper database;
    private boolean flag =true;

    public FAV_adapter(List<FAV_data> adapter_list, Context mContext, ClickAdapterListenerFav listener) {
        this.adapter_list = adapter_list;
        this.mContext = mContext;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
    }

    private void databaseInit(){
        if(flag) {
            SQLiteDatabase.loadLibs(mContext);
            database = new FAV_sqlHelper(mContext);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        if(i==TYPE_GSMOWOM){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gsmowom_rv_item, parent, false);
            return new GSMOWOMFAV_customViewHolder(view);
        }
        else if (i==TYPE_DEVICE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_rv_item, parent, false);
            return new DEVICEFAV_customViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bankdetails_rv_item, parent, false);
            return new BANKINGDETAILSFAV_customViewholder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==TYPE_GSMOWOM){
            GSMOWOM_data data = adapter_list.get(position).getGsmowom_data();
            ((GSMOWOMFAV_customViewHolder) holder).provider_tv.setText(data.getD_provider());
            ((GSMOWOMFAV_customViewHolder) holder).email_tv.setText("Associated mail ID: "+data.getD_assoEmail());
            ((GSMOWOMFAV_customViewHolder) holder).phno_tv.setText("Associated Phone num: "+data.getD_assoPhno());
            ((GSMOWOMFAV_customViewHolder) holder).username_tv.setText("Username: "+data.getD_username());
            ((GSMOWOMFAV_customViewHolder) holder).pass_tv.setText("Password: "+data.getD_pass());
            ((GSMOWOMFAV_customViewHolder) holder).adiInfo_tv.setText("Notes: "+data.getD_adiInfo());
        }
        else if(getItemViewType(position)==TYPE_DEVICE){
            DEVICE_data data = adapter_list.get(position).getDevice_data();
            ((DEVICEFAV_customViewHolder) holder).dtype.setText(data.getDevice_Type());
            ((DEVICEFAV_customViewHolder) holder).dname.setText("Name: "+data.getDevice_name());
            ((DEVICEFAV_customViewHolder) holder).dsecutype.setText("Security Type: "+data.getSecurityType());
            ((DEVICEFAV_customViewHolder) holder).dppp.setText("Unlock code: "+data.getPINorPassorPattern());
        }
        else {
            BANKING_data data = adapter_list.get(position).getBanking_data();
            ((BANKINGDETAILSFAV_customViewholder) holder).actv.setText("Acc no: "+data.getAccountnum());
            if(!data.getAssoBankmail().equals("")){
                ((BANKINGDETAILSFAV_customViewholder) holder).email.setVisibility(View.VISIBLE);
                ((BANKINGDETAILSFAV_customViewholder) holder).email.setText("Email: "+data.getAssoBankmail());
            }
            else{
                ((BANKINGDETAILSFAV_customViewholder) holder).email.setVisibility(View.GONE);
            }
            if(!data.getAssoBankPhno().equals("")){
                ((BANKINGDETAILSFAV_customViewholder) holder).phno.setVisibility(View.VISIBLE);
                ((BANKINGDETAILSFAV_customViewholder) holder).phno.setText("Ph no: "+data.getAssoBankPhno());
            }
            else{
                ((BANKINGDETAILSFAV_customViewholder) holder).phno.setVisibility(View.GONE);
            }
            if(!data.getBankAddress().equals("")){
                ((BANKINGDETAILSFAV_customViewholder) holder).addr.setVisibility(View.VISIBLE);
                ((BANKINGDETAILSFAV_customViewholder) holder).addr.setText("Address: "+data.getBankAddress());
            }
            else{
                ((BANKINGDETAILSFAV_customViewholder) holder).addr.setVisibility(View.GONE);
            }
            if(!data.getNetBankinguserid().equals("")){
                ((BANKINGDETAILSFAV_customViewholder) holder).netbid.setVisibility(View.VISIBLE);
                ((BANKINGDETAILSFAV_customViewholder) holder).netbid.setText("Netbanking userID: "+data.getNetBankinguserid());
            }
            else{
                ((BANKINGDETAILSFAV_customViewholder) holder).netbid.setVisibility(View.GONE);
            }
            if(!data.getNetBankingpass().equals("")){
                ((BANKINGDETAILSFAV_customViewholder) holder).netbpass.setVisibility(View.VISIBLE);
                ((BANKINGDETAILSFAV_customViewholder) holder).netbpass.setText("Netbanking password: "+data.getNetBankingpass());
            }
            else{
                ((BANKINGDETAILSFAV_customViewholder) holder).netbpass.setVisibility(View.GONE);
            }
            if(!data.getCreditcardnum().equals("[]")){
                ((BANKINGDETAILSFAV_customViewholder) holder).ccnos.setVisibility(View.VISIBLE);
                ((BANKINGDETAILSFAV_customViewholder) holder).ccnos.setText("Credit card nos and PIN:\n"+data.getCreditcardnum());
            }
            else{
                ((BANKINGDETAILSFAV_customViewholder) holder).ccnos.setVisibility(View.GONE);
            }
            if(!data.getDebitcardnum().equals("[]")){
                ((BANKINGDETAILSFAV_customViewholder) holder).dcnos.setVisibility(View.VISIBLE);
                ((BANKINGDETAILSFAV_customViewholder) holder).dcnos.setText("Debit card nos and PIN:\n"+data.getDebitcardnum());
            }
            else{
                ((BANKINGDETAILSFAV_customViewholder) holder).dcnos.setVisibility(View.GONE);
            }
            if(!data.getAdiNotes().equals("")){
                ((BANKINGDETAILSFAV_customViewholder) holder).adinotes.setVisibility(View.VISIBLE);
                ((BANKINGDETAILSFAV_customViewholder) holder).adinotes.setText("Notes: "+data.getAdiNotes());
            }
            else{
                ((BANKINGDETAILSFAV_customViewholder) holder).adinotes.setVisibility(View.GONE);
            }
        }
        holder.itemView.setActivated(selectedItems.get(position, false));
        applyClickEvents(holder, position, getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        return adapter_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(adapter_list.get(position).getGsmowom_data()!=null){
            return TYPE_GSMOWOM;
        }
        else if(adapter_list.get(position).getDevice_data()!=null){
            return TYPE_DEVICE;
        }
        else {
            return TYPE_BANK;
        }
    }

    private void applyClickEvents(RecyclerView.ViewHolder holder, final int position, int viewType){
        if(viewType==TYPE_GSMOWOM){
            ((GSMOWOMFAV_customViewHolder) holder).card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRowClicked(position);
                }
            });

            ((GSMOWOMFAV_customViewHolder) holder).card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onRowLongClicked(position);
                    v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                }
            });
        }
        else if(viewType==TYPE_DEVICE){
            ((DEVICEFAV_customViewHolder) holder).card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRowClicked(position);
                }
            });

            ((DEVICEFAV_customViewHolder) holder).card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onRowLongClicked(position);
                    v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                }
            });
        }
        else if(viewType==TYPE_BANK){
            ((BANKINGDETAILSFAV_customViewholder) holder).card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRowClicked(position);
                }
            });

            ((BANKINGDETAILSFAV_customViewholder) holder).card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onRowLongClicked(position);
                    v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                }
            });
        }
    }

    public void FAVtoggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void FAVselectAll() {
        for (int i = 0; i < getItemCount(); i++)
            selectedItems.put(i, true);
        notifyDataSetChanged();
    }

    public void FAVclearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int FAVgetSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> FAVgetSelectedItems() {
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void FAVremoveData(int position) {
        databaseInit();
        flag = false;
        database.removeFAV(adapter_list.get(position).getFAV_sqlID(), database.getWritableDatabase(MainActivity.lepass));
        adapter_list.remove(position);
        resetCurrentIndex();
    }

    public String FAVgetShareData(int position){
        String singleShare = "";
        if(getItemViewType(position)==TYPE_GSMOWOM){
            GSMOWOM_data data = new GSMOWOM_data();
            data = adapter_list.get(position).getGsmowom_data();
            singleShare = data.getD_provider()+" Credentials\n"+
                    "Username: "+data.getD_username()+"\n"+
                    "Password: "+data.getD_pass()+"\n"+
                    "Associated mail: "+data.getD_assoEmail()+"\n"+
                    "Associated phone: "+data.getD_assoPhno()+"\n"+
                    "Notes: "+data.getD_adiInfo()+"\n";
        }
        else if(getItemViewType(position)==TYPE_DEVICE){
            DEVICE_data data = new DEVICE_data();
            data = adapter_list.get(position).getDevice_data();
            singleShare = data.getDevice_Type()+": "+
                    data.getDevice_name()+"\n"+
                    data.getSecurityType()+": "+data.getPINorPassorPattern()+"\n";
        }
        else if(getItemViewType(position)==TYPE_BANK){
            BANKING_data data = new BANKING_data();
            data = adapter_list.get(position).getBanking_data();
            singleShare = "Bank: "+data.getBankName()+"\n"+
                    "Account Number: "+data.getAccountnum()+"\n"+
                    "Associated Mail: "+data.getAssoBankmail()+"\n"+
                    "Associated Phno: "+data.getAssoBankPhno()+"\n"+
                    "Registered Address: "+data.getBankAddress()+"\n"+
                    "NetBanking UserID: "+data.getNetBankinguserid()+"\n"+
                    "NetBanking password: "+data.getNetBankingpass()+"\n"+
                    "Credit card numbers and pin: "+data.getCreditcardnum()+"\n"+
                    "Debit card numbers and pin: "+data.getDebitcardnum()+"\n"+
                    "Notes: "+data.getAdiNotes()+"\n";
        }
        return singleShare;
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public interface ClickAdapterListenerFav {

        void onRowClicked(int position);

        void onRowLongClicked(int position);
    }

    public class GSMOWOMFAV_customViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener{
        private TextView provider_tv, email_tv, phno_tv, pass_tv, username_tv, adiInfo_tv;
        private CardView card;

        public GSMOWOMFAV_customViewHolder(@NonNull View itemView) {
            super(itemView);
            provider_tv = itemView.findViewById(R.id.gsmowom_provider_tv);
            email_tv = itemView.findViewById(R.id.gsmowom_assoEmail_tv);
            phno_tv = itemView.findViewById(R.id.gsmowom_assoPhno_tv);
            pass_tv = itemView.findViewById(R.id.gsmowom_pass_tv);
            username_tv = itemView.findViewById(R.id.gsmowom_username_tv);
            adiInfo_tv = itemView.findViewById(R.id.gsmowom_adiInfo_tv);
            card = itemView.findViewById(R.id.card_item_ofgsmowomRV);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onRowLongClicked(getAdapterPosition());
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    public class DEVICEFAV_customViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener{

        private TextView dtype, dname, dsecutype, dppp;
        private CardView card;

        public DEVICEFAV_customViewHolder(@NonNull View itemView) {
            super(itemView);
            dtype = itemView.findViewById(R.id.devicetype_tv);
            dname = itemView.findViewById(R.id.devicename_tv);
            dsecutype = itemView.findViewById(R.id.securitytype_tv);
            dppp = itemView.findViewById(R.id.pinorpassorpattern_tv);
            card = itemView.findViewById(R.id.card_item_ofdeviceRV);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onRowLongClicked(getAdapterPosition());
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    public class BANKINGDETAILSFAV_customViewholder extends RecyclerView.ViewHolder implements  View.OnLongClickListener{

        TextView actv, email, phno, addr, netbid, netbpass, ccnos, dcnos, adinotes;
        CardView card;

        public BANKINGDETAILSFAV_customViewholder(@NonNull View itemView) {
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
            card = itemView.findViewById(R.id.card_item_ofbankdetailsRV);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onRowLongClicked(getAdapterPosition());
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }


}
