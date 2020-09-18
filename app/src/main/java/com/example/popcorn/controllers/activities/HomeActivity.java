package com.example.popcorn.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.popcorn.R;
import com.example.popcorn.controllers.fragments.HomeFragment;
import com.example.popcorn.controllers.fragments.WatchFragment;
import com.example.popcorn.controllers.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView2);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment =null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectFragment = new HomeFragment();
                    break;
                case R.id.nav_notif:
                    selectFragment = new WatchFragment();
                    break;
                case R.id.nav_setting:
                    selectFragment = new SettingFragment();

                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container ,
                    selectFragment).commit();
            return true;
        }
    };
}