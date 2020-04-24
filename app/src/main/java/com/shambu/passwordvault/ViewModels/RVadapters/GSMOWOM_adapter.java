package com.shambu.passwordvault.ViewModels.RVadapters;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shambu.passwordvault.ClickAdapterListener;
import com.shambu.passwordvault.Model.Entities.GSMOWOM_data;
import com.shambu.passwordvault.R;
import java.util.ArrayList;
import java.util.List;

public class GSMOWOM_adapter extends RecyclerView.Adapter<GSMOWOM_adapter.GSMOWOM_customViewHolder> {

    private List<GSMOWOM_data> adapterList = new ArrayList<>();
    private boolean flag = true;
    private ClickAdapterListener listener;
    private SparseBooleanArray selectedItems;
    private String msg = GSMOWOM_adapter.class.getSimpleName();

    private static int currentSelectedIndex = -1;


    public GSMOWOM_adapter(ClickAdapterListener listener) {
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
    }

    public class GSMOWOM_customViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
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
            gsmowom_card.setOnLongClickListener(this);
            gsmowom_card.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(msg, "CustomViewholder class onLongClick called (GSMOWOM adapter class)");
            listener.onRowLongClicked(getAdapterPosition());
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return false;
        }

        @Override
        public void onClick(View view) {
            listener.onRowClicked(getAdapterPosition());
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
    }

    @Override
    public int getItemCount() {
        Log.d(msg, "getItemCount method called (GSMOWOM adapter class)");
        return adapterList.size();
    }

    public void refreshList(List<GSMOWOM_data> dataList){
        this.adapterList = dataList;
        notifyDataSetChanged();
    }

    public GSMOWOM_data getDataFromList(int position){
        return adapterList.get(position);
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
