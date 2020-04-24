package com.shambu.passwordvault.Views;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.shambu.passwordvault.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    ConstraintLayout rr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rr = findViewById(R.id.mainreallay);

        bottomNav = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.navhost_fragment);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}
