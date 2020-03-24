package com.shambu.passwordvault.ViewModels.RVadapters;

import android.content.Context;
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
import com.shambu.passwordvault.Model.Entities.DEVICE_data;
import com.shambu.passwordvault.R;

import java.util.ArrayList;
import java.util.List;

public class DEVICE_adapter extends RecyclerView.Adapter<DEVICE_adapter.DEVICE_customViewHolder> {

    private List<DEVICE_data> adapter_list = new ArrayList<>();
    private ClickAdapterListener listener;
    private SparseBooleanArray selectedItems;
    private String msg = DEVICE_adapter.class.getSimpleName();

    private static int currentSelectedIndex = -1;

    public DEVICE_adapter(ClickAdapterListener listener) {
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
    }

    public class DEVICE_customViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener{

        private TextView dtype, dname, dsecutype, dppp;
        private CardView device_cardview;

        public DEVICE_customViewHolder(@NonNull View itemView) {
            super(itemView);
            dtype = itemView.findViewById(R.id.devicetype_tv);
            dname = itemView.findViewById(R.id.devicename_tv);
            dsecutype = itemView.findViewById(R.id.securitytype_tv);
            dppp = itemView.findViewById(R.id.pinorpassorpattern_tv);
            device_cardview = itemView.findViewById(R.id.card_item_ofdeviceRV);
            device_cardview.setOnLongClickListener(this);
            device_cardview.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
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
    public DEVICE_adapter.DEVICE_customViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.device_rv_item, parent, false);

        return new DEVICE_customViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DEVICE_adapter.DEVICE_customViewHolder holder, int position) {
        DEVICE_data data = adapter_list.get(position);
        holder.dtype.setText(data.getDevice_Type());
        holder.dname.setText("Name: "+data.getDevice_name());
        holder.dsecutype.setText("Security Type: "+data.getSecurityType());
        holder.dppp.setText("Unlock code: "+data.getPINorPassorPattern());
        holder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return adapter_list.size();
    }

    public void refreshList(List<DEVICE_data> dataList){
        this.adapter_list = dataList;
        notifyDataSetChanged();
    }

    public DEVICE_data getDataFromList(int position){
        return adapter_list.get(position);
    }

    public void DEVICEtoggleSelection(int pos) {
        Log.d(msg, "toggleSelection method called (DEVICE adapter class)");
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void DEVICEselectAll() {
        Log.d(msg, "selectAll method called (DEVICE adapter class)");
        for (int i = 0; i < getItemCount(); i++)
            selectedItems.put(i, true);
        notifyDataSetChanged();
    }

    public void DEVICEclearSelections() {
        Log.d(msg, "clearSelections method called (DEVICE adapter class)");
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int DEVICEgetSelectedItemCount() {
        Log.d(msg, "getSelectedItemCount method called (DEVICE adapter class)");
        return selectedItems.size();
    }

    public List<Integer> DEVICEgetSelectedItems() {
        Log.d(msg, "getSelectedItems method called (DEVICE adapter class)");
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public String DEVICEgetShareData(int position){
        String singleShare;
        singleShare = adapter_list.get(position).getDevice_Type()+": "+
                adapter_list.get(position).getDevice_name()+"\n"+
                adapter_list.get(position).getSecurityType()+": "+adapter_list.get(position).getPINorPassorPattern()+"\n";
        return singleShare;
    }

    private void resetCurrentIndex() {
        Log.d(msg, "resetCurrentIndex method called (DEVICE adapter class)");
        currentSelectedIndex = -1;
    }

}
