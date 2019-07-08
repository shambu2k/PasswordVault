package com.shambu.passwordvault;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FrameLayout fragcont;
    BottomNavigationView bottomNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragcont = findViewById(R.id.fragment_container);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.passwords_frag_menu:
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                                .replace(R.id.fragment_container, new passwords_fragment(), "PASS_FRAG").commit();
                        break;
                    case R.id.favourites_frag_menu:
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                                .replace(R.id.fragment_container, new favourites_fragment(), "FAV_FRAG").commit();
                        Snackbar.make(fragcont, "Coming soon!", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.settings_frag_menu:
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                                .replace(R.id.fragment_container, new settings_fragment(), "SET_FRAG").commit();
                        break;
                }

                return true;
            }
        });


        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragment_container, new passwords_fragment(), "PASS_FRAG").commit();
    }


    @Override
    public void onBackPressed() {
        favourites_fragment Ffragment = (favourites_fragment) getSupportFragmentManager().findFragmentByTag("FAV_FRAG");
        settings_fragment Sfragment = (settings_fragment) getSupportFragmentManager().findFragmentByTag("SET_FRAG");
        if(Ffragment!=null && Ffragment.isVisible()){
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.fragment_container, new passwords_fragment(), "PASS_FRAG").commit();
            bottomNav.getMenu().getItem(0).setChecked(true);
        }
        else if(Sfragment!=null && Sfragment.isVisible())
        {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.fragment_container, new passwords_fragment(), "PASS_FRAG").commit();
            bottomNav.getMenu().getItem(0).setChecked(true);
        }
        else{
            super.onBackPressed();
        }


    }
}
