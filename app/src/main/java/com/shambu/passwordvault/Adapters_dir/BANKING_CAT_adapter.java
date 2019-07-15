package com.shambu.passwordvault.Adapters_dir;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shambu.passwordvault.Data_classes.BANKINGCAT_data;
import com.shambu.passwordvault.Fragments_dir.passwords_innerFrags.bankdetails_fragment;
import com.shambu.passwordvault.MainActivity;
import com.shambu.passwordvault.R;
import com.shambu.passwordvault.Sql_dir.BANKING_CAT_sqlHelper;
import com.shambu.passwordvault.Sql_dir.BANKING_sqlHelper;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BANKING_CAT_adapter extends RecyclerView.Adapter<BANKING_CAT_adapter.BANKING_CAT_CustomViewholder> {

    public static String whichbankname;
    private static int currentSelectedIndex = -1;
    private Context mContext;
    private List<BANKINGCAT_data> name_list;
    private ClickAdapterListenerBankingCat listener;
    private SparseBooleanArray selectedItems;
    private BANKING_sqlHelper banking_sqlHelper;
    private BANKING_CAT_sqlHelper banking_cat_sqlHelper;


    public BANKING_CAT_adapter(Context mContext, List<BANKINGCAT_data> name_list, ClickAdapterListenerBankingCat listener) {
        this.mContext = mContext;
        this.name_list = name_list;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public BANKING_CAT_adapter.BANKING_CAT_CustomViewholder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.banking_cat_rv_item, parent, false);

        final BANKING_CAT_CustomViewholder vHolder = new BANKING_CAT_CustomViewholder(view);

        vHolder.b_cat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichbankname = vHolder.b_name.getText().toString();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new bankdetails_fragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                        .addToBackStack(null).commit();
            }
        });

        return new BANKING_CAT_CustomViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BANKING_CAT_adapter.BANKING_CAT_CustomViewholder holder, int position) {
        String bank_name = name_list.get(position).getBank_name();
        holder.b_name.setText(bank_name);

        holder.itemView.setActivated(selectedItems.get(position, false));

        applyClickEvents(holder, position);
    }

    @Override
    public int getItemCount() {
        return name_list.size();
    }

    private void applyClickEvents(final BANKING_CAT_CustomViewholder holder, final int position){

        holder.b_cat_card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onRowLongClicked(position);
                return true;
            }
        });
    }

    public void BCtoggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void BCselectAll() {
        for (int i = 0; i < getItemCount(); i++)
            selectedItems.put(i, true);
        notifyDataSetChanged();
    }

    public void BCclearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int BCgetSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> BCgetSelectedItems() {
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public void BCremoveData(int position){
        SQLiteDatabase.loadLibs(mContext);
        banking_cat_sqlHelper = new BANKING_CAT_sqlHelper(mContext);
        banking_sqlHelper = new BANKING_sqlHelper(mContext);
        banking_cat_sqlHelper.removebankRow(name_list.get(position).getBcSqlID(), banking_cat_sqlHelper.getWritableDatabase(MainActivity.lepass),
                banking_sqlHelper.getWritableDatabase(MainActivity.lepass), name_list.get(position).getBank_name());
        name_list.remove(position);
        resetCurrentIndex();
    }

    public interface ClickAdapterListenerBankingCat {
        void onRowLongClicked(int position);
    }

    public class BANKING_CAT_CustomViewholder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView b_name;
        CardView b_cat_card;
        public BANKING_CAT_CustomViewholder(@NonNull View itemView) {
            super(itemView);
            b_cat_card = itemView.findViewById(R.id.card_item_of_banking_cat_RV);
            b_name = itemView.findViewById(R.id.bank_name_tv);
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
