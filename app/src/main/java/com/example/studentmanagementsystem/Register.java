package com.example.studentmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText names,phones,emails,passw;
    Button register;
    TextView signintxt;
    ProgressBar progressBar;

    // Fire Base DataBase
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        names = findViewById(R.id.name);
        phones = findViewById(R.id.phone);
        emails = findViewById(R.id.email);
        passw = findViewById(R.id.pass);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        signintxt = findViewById(R.id.signintxt);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), HomePage.class));
            finish();
        }

        signintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = names.getText().toString();
                final String email = emails.getText().toString().trim().toLowerCase();
                final String phone = phones.getText().toString().trim();
                String pass = passw.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    names.setError("Enter Your Name First");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    emails.setError("Enter Your Email ");
                    return;
                }
                if (TextUtils.isEmpty(phone)&&phone.length()<9){
                    phones.setError("Enter Your Phone No");
                    return;
                }
                if (pass.length()<6){
                    passw.setError("Plz Enter Atleast 6 Digit");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                // Fire Base Work
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            mUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Fail","Email not Send"+e.getMessage());
                                }
                            });


                            Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();

                            // Data Storing
                            userId = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = mStore.collection("user").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Full name",name);
                            user.put("email",email);
                            user.put("phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("OnSuccess","User Profile is created of "+ userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("OnFailure","User Profile is created of "+ e.toString());
                                }
                            });





                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                        }
                        else {
                            Toast.makeText(Register.this, "Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }


                    }
                });


            }
        });
    }
}