package com.shambu.passwordvault;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BANKINGDETAILS_adapter extends RecyclerView.Adapter<BANKINGDETAILS_adapter.BANKINGDETAILS_customViewholder> {

    private List<BANKING_data> adapter_list;
    private Context mContext;

    public BANKINGDETAILS_adapter(List<BANKING_data> adapter_list, Context mContext) {
        this.adapter_list = adapter_list;
        this.mContext = mContext;
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
        holder.email.setText("Email: "+data.getAssoBankmail());
        holder.phno.setText("Ph no: "+data.getAssoBankPhno());
        holder.addr.setText("Address: "+data.getBankAddress());
        holder.netbid.setText("Netbanking userID: "+data.getNetBankinguserid());
        holder.netbpass.setText("Netbanking password: "+data.getNetBankingpass());
        holder.ccnos.setText("Credit card nos and PIN:\n"+data.getCreditcardnum());
        holder.dcnos.setText("Debit card nos and PIN:\n"+data.getDebitcardnum());
        holder.adinotes.setText("Notes: "+data.getAdiNotes());

    }

    @Override
    public int getItemCount() {
        return adapter_list.size();
    }

    public class BANKINGDETAILS_customViewholder extends RecyclerView.ViewHolder {

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
    }
}
