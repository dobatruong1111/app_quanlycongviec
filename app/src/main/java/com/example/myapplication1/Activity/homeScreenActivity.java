package com.example.myapplication1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication1.Fragment.accountFragment;
import com.example.myapplication1.Fragment.homeFragment;
import com.example.myapplication1.Fragment.notificationFragment;
import com.example.myapplication1.Fragment.projectFragment;
import com.example.myapplication1.Fragment.searchFragment;
import com.example.myapplication1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;




public class homeScreenActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        loadFragment(new homeFragment());

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bottom_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment = new homeFragment();
                        break;

                    case R.id.nav_project:
                        fragment = new projectFragment();
                        break;

                    case R.id.nav_search:
                        fragment = new searchFragment();
                        break;

                    case R.id.nav_notification:
                        fragment = new notificationFragment();
                        break;

                    case R.id.nav_account:
                        fragment = new accountFragment();
                        break;
                }

                loadFragment(fragment);
                return true;
            }
        });

        //Receive data from login user

    }

    public boolean loadFragment(Fragment fragment){

        if(fragment != null){

            getSupportFragmentManager().beginTransaction().replace(R.id.home_screen_frame_layout, fragment).commit();

        }

        return true;
    }
}