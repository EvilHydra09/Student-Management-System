package com.example.studentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPage extends AppCompatActivity {
    EditText memail,mpass;
    TextView mforget,msignuptxt;
    CheckBox mremember;
    Button msign;

    FirebaseAuth mauth;
    FirebaseFirestore mstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        memail = findViewById(R.id.emaill);
        mpass = findViewById(R.id.passl);
        mforget = findViewById(R.id.forgetp);
        msignuptxt = findViewById(R.id.signuptxt);
        mforget = findViewById(R.id.forgetp);
        msign = findViewById(R.id.signinbut);
        mremember = findViewById(R.id.remember);
        mauth = FirebaseAuth.getInstance();


        msignuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        msign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = memail.getText().toString().trim().toLowerCase();
                String pass = mpass.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    memail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    mpass.setError("Password is Required");
                }
                mremember.setVisibility(View.VISIBLE);

                mauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Login Sucessful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomePage.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mremember.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });



    }
}