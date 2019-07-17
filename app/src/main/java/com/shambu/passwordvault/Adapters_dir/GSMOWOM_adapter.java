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

import com.shambu.passwordvault.Data_classes.GSMOWOM_data;
import com.shambu.passwordvault.MainActivity;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.Sql_dir.GSMOWOM_sqlHelper;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GSMOWOM_adapter extends RecyclerView.Adapter<GSMOWOM_adapter.GSMOWOM_customViewHolder> {

    private List<GSMOWOM_data> adapterList;
    private boolean flag = true;
    private Context mContext;
    private ClickAdapterListener listener;
    private SparseBooleanArray selectedItems;
    private GSMOWOM_sqlHelper database;
    private String msg = GSMOWOM_adapter.class.getSimpleName();

    private static int currentSelectedIndex = -1;

    public interface ClickAdapterListener {

        void onRowClicked(int position);

        void onRowLongClicked(int position);
    }

    private void databaseInit(){
        if(flag) {
            SQLiteDatabase.loadLibs(mContext);
            database = new GSMOWOM_sqlHelper(mContext);
        }
    }

    public GSMOWOM_adapter(List<GSMOWOM_data> adapterList, Context context) {
        this.adapterList = adapterList;
        this.mContext = context;
        selectedItems = new SparseBooleanArray();
        Log.d(msg, "list, context Constructor called (GSMOWOM adapter class");
    }

    public GSMOWOM_adapter(List<GSMOWOM_data> adapterList, Context context, ClickAdapterListener listener) {
        this.adapterList = adapterList;
        this.mContext = context;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        Log.d(msg, "list, context, interface Constructor called (GSMOWOM adapter class");
    }

    public class GSMOWOM_customViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView provider_tv, email_tv, phno_tv, pass_tv, username_tv, adiInfo_tv;
        private CardView gsmowom_card;

        public GSMOWOM_customViewHolder(@NonNull View itemView) {
            super(itemView);
            provider_tv = itemView.findViewById(R.id.gsmowom_provider_tv);
            email_tv = itemView.findViewById(R.id.gsmowom_assoEmail_tv);
            phno_tv = itemView.findViewById(R.id.gsmowom_assoPhno_tv);
            pass_tv = itemView.findViewById(R.id.gsmowom_pass_tv);
            username_tv = itemView.findViewById(R.id.gsmowom_username_tv);
            adiInfo_tv = itemView.findViewById(R.id.gsmowom_adiInfo_tv);
            gsmowom_card = itemView.findViewById(R.id.card_item_ofgsmowomRV);
            itemView.setOnLongClickListener(this);
            Log.d(msg, "CustomViewholder class Constructor called (GSMOWOM adapter class)");
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(msg, "CustomViewholder class onLongClick called (GSMOWOM adapter class)");
            listener.onRowLongClicked(getAdapterPosition());
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }
    @NonNull
    @Override
    public GSMOWOM_adapter.GSMOWOM_customViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Log.d(msg, "OnCreate Viewholder method called (GSMOWOM adapter class)");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.gsmowom_rv_item, parent, false);
        return new GSMOWOM_customViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GSMOWOM_adapter.GSMOWOM_customViewHolder holder, int position) {
        Log.d(msg, "OnBindViewholder method called (GSMOWOM adapter class)");
        GSMOWOM_data data = adapterList.get(position);
        holder.provider_tv.setText(data.getD_provider());
        holder.email_tv.setText("Associated mail ID: "+data.getD_assoEmail());
        holder.phno_tv.setText("Associated Phone num: "+data.getD_assoPhno());
        holder.username_tv.setText("Username: "+data.getD_username());
        holder.pass_tv.setText("Password: "+data.getD_pass());
        holder.adiInfo_tv.setText("Notes: "+data.getD_adiInfo());

        holder.itemView.setActivated(selectedItems.get(position, false));

        applyClickEvents(holder, position);

    }

    @Override
    public int getItemCount() {
        Log.d(msg, "getItemCount method called (GSMOWOM adapter class)");
        return adapterList.size();
    }

    private void applyClickEvents(GSMOWOM_customViewHolder holder, final int position){
        Log.d(msg, "applyClickEvents method called (GSMOWOM adapter class)");
        holder.gsmowom_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(msg, "applyClickEvents setOnClickListener method called (GSMOWOM adapter class)");
                listener.onRowClicked(position);
            }
        });

        holder.gsmowom_card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(msg, "applyClickEvents setOnLongClickListener method called (GSMOWOM adapter class)");
                listener.onRowLongClicked(position);
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
    }

    public void toggleSelection(int pos) {
        Log.d(msg, "toggleSelection method called (GSMOWOM adapter class)");
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void selectAll() {
        Log.d(msg, "selectAll method called (GSMOWOM adapter class)");
        for (int i = 0; i < getItemCount(); i++)
            selectedItems.put(i, true);
        notifyDataSetChanged();

    }

    public void clearSelections() {
        Log.d(msg, "clearSelections method called (GSMOWOM adapter class)");
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        Log.d(msg, "getSelectedItemCount method called (GSMOWOM adapter class)");
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        Log.d(msg, "getSelectedItems method called (GSMOWOM adapter class)");
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {

        Log.d(msg, "removeData method called (GSMOWOM adapter class)");
        databaseInit();
        flag = false;
        database.removeRow(adapterList.get(position).getD_ID(), database.getWritableDatabase(MainActivity.lepass));
        adapterList.remove(position);
        resetCurrentIndex();
    }

    public String getShareData(int position){
        String singleShare;
        singleShare = adapterList.get(position).getD_provider()+" Credentials\n"+
                "Username: "+adapterList.get(position).getD_username()+"\n"+
                "Password: "+adapterList.get(position).getD_pass()+"\n"+
                "Associated mail: "+adapterList.get(position).getD_assoEmail()+"\n"+
                "Associated phone: "+adapterList.get(position).getD_assoPhno()+"\n"+
                "Notes: "+adapterList.get(position).getD_adiInfo()+"\n";
        return singleShare;
    }

    private void resetCurrentIndex() {
        Log.d(msg, "resetCurrentIndex method called (GSMOWOM adapter class)");
        currentSelectedIndex = -1;
    }
}
