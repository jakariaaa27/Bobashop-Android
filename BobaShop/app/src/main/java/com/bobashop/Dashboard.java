package com.bobashop;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bobashop.activity.InformasiFragment;
import com.bobashop.activity.MapsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {

    private InformasiFragment informasigfragment = new InformasiFragment();
    private MapsFragment mapsFragment = new MapsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = mapsFragment;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPrefManager = new SharedPrefManager(this);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);

        fm.beginTransaction().add(R.id.fragment_container, informasigfragment, "2").hide(informasigfragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,mapsFragment, "1").commit();

//        Fragment fragment=new MapsFragment();
//
//        // Open fragment
//        getSupportFragmentManager()
//                .beginTransaction().replace(R.id.frame_layout,fragment)
//                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fm.beginTransaction().hide(active).show(mapsFragment).commit();
                active = mapsFragment;
                return true;

            case R.id.navigation_perkembangan:
                fm.beginTransaction().hide(active).show(informasigfragment).commit();
                active = informasigfragment;
                return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}