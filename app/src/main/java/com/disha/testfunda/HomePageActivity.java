package com.disha.testfunda;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static  String mypreference = "mypref";
    private ActionBar toolbar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.verbel:
                    fragment = new Verbal();
                    loadFragment(fragment);
                    return true;
                case R.id.quant:
                    fragment = new Quant();
                    loadFragment(fragment);
                    return true;
                case R.id.di:
                    fragment = new DI();
                    loadFragment(fragment);
                    return true;
                case R.id.settings:
                    fragment = new Settings();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        toolbar = getSupportActionBar();



        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        String val;
        val=sharedpreferences.getString("isLogin","");
        Log.e("val",val);
        // load the store fragment by default
        loadFragment(new Verbal());
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
