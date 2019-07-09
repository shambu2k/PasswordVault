package com.shambu.passwordvault.Fragments_dir;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shambu.passwordvault.Fragments_dir.passwords_innerFrags.device_fragment;
import com.shambu.passwordvault.Fragments_dir.passwords_innerFrags.google_fragment;
import com.shambu.passwordvault.Fragments_dir.passwords_innerFrags.othermails_fragment;
import com.shambu.passwordvault.Fragments_dir.passwords_innerFrags.otherwebsites_fragment;
import com.shambu.passwordvault.Fragments_dir.passwords_innerFrags.social_media_fragment;
import com.shambu.passwordvault.R;

public class passwords_fragment extends Fragment {

    private CardView b, g, d, s, m, w;
    public static String which_type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.passwords_fragment_layout, container, false);
        b = view.findViewById(R.id.banking_card);
        g = view.findViewById(R.id.google_card);
        d = view.findViewById(R.id.device_card);
        s = view.findViewById(R.id.social_card);
        m = view.findViewById(R.id.mails_card);
        w = view.findViewById(R.id.websites_card);

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which_type = "Social";
               social_media_fragment fragment_social = new social_media_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment_social);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which_type = "Bank";
                social_media_fragment fragment_social = new social_media_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment_social);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which_type = "Google";
                google_fragment fragment_google = new google_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment_google);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which_type = "Device";
                device_fragment fragment_device = new device_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment_device);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which_type = "OtherMails";
                othermails_fragment fragment_othermails = new othermails_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment_othermails);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which_type = "OtherWebsites";
                otherwebsites_fragment fragment_otherwebsites = new otherwebsites_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment_otherwebsites);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;

    }
}
