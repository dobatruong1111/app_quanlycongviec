package com.example.myapplication1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication1.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;



public class introHomeScreen extends AppCompatActivity {

    private MaterialButton btn_sign_up, btn_sign_in;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(introHomeScreen.this,homeScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(intent);
            finish();
        }   
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_home_screen);

        btn_sign_in = (MaterialButton) findViewById(R.id.btn_sign_in_intro);
        btn_sign_up = (MaterialButton) findViewById(R.id.btn_sign_up_intro);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                HashMap<String, ArrayList<String>> Data = new HashMap<String, ArrayList<String>>();
                ArrayList<String> data = new ArrayList<>();

                data.add("Manh");
                data.add("Tuan");
                data.add("Truong");
                data.add("Quang");

                Data.put("Name", data);
                FirebaseDatabase.getInstance().getReference("Name").setValue(Data);

                Intent intent = new Intent(introHomeScreen.this, signInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(introHomeScreen.this, signUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });


    }
}