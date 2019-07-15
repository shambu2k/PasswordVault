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

import com.shambu.passwordvault.Data_classes.DEVICE_data;
import com.shambu.passwordvault.MainActivity;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.Sql_dir.DEVICE_sqlHelper;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DEVICE_adapter extends RecyclerView.Adapter<DEVICE_adapter.DEVICE_customViewHolder> {

    private List<DEVICE_data> adapter_list;
    private Context mContext;
    private ClickAdapterListenerDevice listener;
    private SparseBooleanArray selectedItems;
    private DEVICE_sqlHelper database;
    private String msg = DEVICE_adapter.class.getSimpleName();

    private static int currentSelectedIndex = -1;

    public interface ClickAdapterListenerDevice {

        void onRowClicked(int position);

        void onRowLongClicked(int position);
    }

    public DEVICE_adapter(List<DEVICE_data> adapter_list, Context mContext) {
        this.adapter_list = adapter_list;
        this.mContext = mContext;
        selectedItems = new SparseBooleanArray();
    }

    public DEVICE_adapter(List<DEVICE_data> adapter_list, Context mContext, ClickAdapterListenerDevice listener) {
        this.adapter_list = adapter_list;
        this.mContext = mContext;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
    }

    public class DEVICE_customViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener{

        private TextView dtype, dname, dsecutype, dppp;
        private CardView device_cardview;

        public DEVICE_customViewHolder(@NonNull View itemView) {
            super(itemView);
            dtype = itemView.findViewById(R.id.devicetype_tv);
            dname = itemView.findViewById(R.id.devicename_tv);
            dsecutype = itemView.findViewById(R.id.securitytype_tv);
            dppp = itemView.findViewById(R.id.pinorpassorpattern_tv);
            device_cardview = itemView.findViewById(R.id.card_item_ofdeviceRV);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onRowLongClicked(getAdapterPosition());
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
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

        applyClickEvents(holder, position);

    }

    @Override
    public int getItemCount() {
        return adapter_list.size();
    }


    private void applyClickEvents(DEVICE_customViewHolder holder, final int position){
        Log.d(msg, "applyClickEvents method called (DEVICE adapter class)");
        holder.device_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(msg, "applyClickEvents setOnClickListener method called (DEVICE adapter class)");
                listener.onRowClicked(position);
            }
        });

        holder.device_cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(msg, "applyClickEvents setOnLongClickListener method called (DEVICE adapter class)");
                listener.onRowLongClicked(position);
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
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

    public void DEVICEremoveData(int position) {
        SQLiteDatabase.loadLibs(mContext);
        Log.d(msg, "removeData method called (DEVICE adapter class)");
        database = new DEVICE_sqlHelper(mContext);
        SQLiteDatabase dbW = database.getWritableDatabase(MainActivity.lepass);
        database.DEVICEremoveRow(adapter_list.get(position).getSqldeviceID(), dbW);
        adapter_list.remove(position);
        resetCurrentIndex();
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
