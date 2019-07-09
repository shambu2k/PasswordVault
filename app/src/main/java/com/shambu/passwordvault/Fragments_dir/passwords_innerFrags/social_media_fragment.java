package com.shambu.passwordvault.Fragments_dir.passwords_innerFrags;

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

import com.shambu.passwordvault.R;

public class social_media_fragment extends Fragment {

    private CardView Facebook, Twitter, Instagram, Reddit, LinkedIn
            ,Tumblr, Pinterest, Medium, Tinder, Others;
    public static String whichprovider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_fragment_layout, container, false);

        Facebook = view.findViewById(R.id.fb_card_social);
        Twitter = view.findViewById(R.id.twitter_card_social);
        Instagram = view.findViewById(R.id.insta_card_social);
        Reddit = view.findViewById(R.id.reddit_card_social);
        LinkedIn = view.findViewById(R.id.linkedIn_card_social);
        Tumblr = view.findViewById(R.id.tubmlr_card_social);
        Pinterest = view.findViewById(R.id.pin_card_social);
        Medium = view.findViewById(R.id.med_card_social);
        Tinder = view.findViewById(R.id.tinder_card_social);
        Others = view.findViewById(R.id.other_card_social);

        Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichprovider = "Facebook";
                GSMOWOM_fragment fragment = new GSMOWOM_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);;
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichprovider = "Twitter";
                GSMOWOM_fragment fragment = new GSMOWOM_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichprovider = "Instagram";
                GSMOWOM_fragment fragment = new GSMOWOM_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Reddit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichprovider = "Reddit";
                GSMOWOM_fragment fragment = new GSMOWOM_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        LinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichprovider = "LinkedIn";
                GSMOWOM_fragment fragment = new GSMOWOM_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Tumblr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichprovider = "Tumblr";
                GSMOWOM_fragment fragment = new GSMOWOM_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Pinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichprovider = "Pinterest";
                GSMOWOM_fragment fragment = new GSMOWOM_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichprovider = "Medium";
                GSMOWOM_fragment fragment = new GSMOWOM_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Tinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichprovider = "Tinder";
                GSMOWOM_fragment fragment = new GSMOWOM_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichprovider = "Others";
                GSMOWOM_fragment fragment = new GSMOWOM_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}