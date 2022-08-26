package com.example.studentmanagementsystem;


import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.studentmanagementsystem.databinding.ActivityHomePageBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    ActivityHomePageBinding binding;
    Toolbar toolbar;
    DrawerLayout drawlay;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navigationView=(NavigationView) findViewById(R.id.navbar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (item.getItemId()){
                    case R.id.navhome:
                        Toast.makeText(HomePage.this, "clicked home", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.loginid:
                        Toast.makeText(HomePage.this, "clicked logout" , Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),LoginPage.class));
                        finish();
                        break;
                }
                return false;
            }
        });


    }


}