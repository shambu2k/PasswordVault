package com.shambu.passwordvault.Views;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shambu.passwordvault.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class passwords_fragment extends Fragment {

    @BindView(R.id.banking_card)
    CardView b;

    @BindView(R.id.device_card)
    CardView d;

    @BindView(R.id.social_card)
    CardView s;

    @BindView(R.id.mails_card)
    CardView m;

    @BindView(R.id.websites_card)
    CardView w;

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.passwords_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_passwords_fragment_to_bankdetails_fragment);
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_passwords_fragment_to_device_fragment);
            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_passwords_fragment_to_social_media_fragment);
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_passwords_fragment_to_emails_fragment);
            }
        });
        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_passwords_fragment_to_otherwebsites_fragment);
            }
        });
    }
}
