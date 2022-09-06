package com.example.studentmanagementsystem;


import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.studentmanagementsystem.databinding.ActivityHomePageBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    ActivityHomePageBinding binding;
    Toolbar toolbar;
    DrawerLayout drawlay;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FloatingActionButton button;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter
            ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        button=findViewById(R.id.fab);
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
        recyclerviewinit();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(HomePage.this);
                dialog.setContentView(R.layout.add_class);

                EditText editclassname=dialog.findViewById(R.id.editclassname);
                Button sub_btn=dialog.findViewById(R.id.submitbtn);

                sub_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name=editclassname.getText().toString();
                        if(TextUtils.isEmpty(name)){
                            editclassname.setError("Class name is Required");
                            return;
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }
    private void recyclerviewinit(){
        recyclerView=findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<model> list=new ArrayList<>();

        list.add(new model(" 5 ",R.drawable.ic_icon));
        list.add(new model(" 6 ",R.drawable.ic_icon));


        adapter=new customAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

    }

}