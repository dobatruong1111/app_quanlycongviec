package com.example.myapplication1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.Model.User;
import com.example.myapplication1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;


public class phoneAuthVerifySceen extends AppCompatActivity {

    private EditText otp_1, otp_2, otp_3, otp_4, otp_5, otp_6;
    private MaterialButton btn_verify_phone;
    private ProgressBar progressBar;
    private Button btn_resend;
    private ImageButton img_btn_back;
    private String verificationID;
    private String code;

    FirebaseDatabase mData;
    DatabaseReference mReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth_verify_sceen);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();
        mReference = mData.getReference("User");

        otp_1 = (EditText) findViewById(R.id.otp_1);
        otp_2 = (EditText) findViewById(R.id.otp_2);
        otp_3 = (EditText) findViewById(R.id.otp_3);
        otp_4 = (EditText) findViewById(R.id.otp_4);
        otp_5 = (EditText) findViewById(R.id.otp_5);
        otp_6 = (EditText) findViewById(R.id.otp_6);

        setupOTPInput();

        //Get ID to prepare sign in by phone with OTP SMS and
        verificationID = getIntent().getStringExtra(signUpActivity.TAG_ID);

        progressBar = (ProgressBar) findViewById(R.id.progress_verify);
        btn_verify_phone = (MaterialButton) findViewById(R.id.btn_verify_phone);
        btn_verify_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_verify_phone.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                if(otp_1.getText().toString().trim().isEmpty()
                || otp_2.getText().toString().trim().isEmpty()
                || otp_3.getText().toString().trim().isEmpty()
                || otp_4.getText().toString().trim().isEmpty()
                || otp_5.getText().toString().trim().isEmpty()
                || otp_6.getText().toString().trim().isEmpty()){
                    Toast.makeText(phoneAuthVerifySceen.this, "Please enter valid code!", Toast.LENGTH_SHORT).show();
                    btn_verify_phone.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                code = otp_1.getText().toString().trim() + otp_2.getText().toString().trim()
                        + otp_3.getText().toString().trim()
                        + otp_4.getText().toString().trim()
                        + otp_5.getText().toString().trim()
                        + otp_6.getText().toString().trim();

                if(code != null){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(
                            verificationID
                            , code);
                    mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            btn_verify_phone.setVisibility(View.VISIBLE);

                            if(task.isSuccessful()){
                                String name = getIntent().getStringExtra(signUpActivity.NAME);
                                String phonenumber = getIntent().getStringExtra(signUpActivity.TAG_PHONE);
                                String pass = getIntent().getStringExtra(signUpActivity.PASS);

                                AuthCredential credential1 = EmailAuthProvider.getCredential(phonenumber +"@domain.com",pass);
                                mAuth.getCurrentUser().linkWithCredential(credential1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Log.d("Phone verify", "link is successfull");
                                        }
                                    }
                                });

                                User user = new User(name, null, phonenumber, pass, mAuth.getCurrentUser().getUid());

                                mReference.child(mAuth.getCurrentUser().getUid()).setValue(user);

                                Intent intent = new Intent(phoneAuthVerifySceen.this, homeScreenActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                                startActivity(intent);

                                finish();
                            }
                            else {
                                showAlertDialog("Error", task.getException().getMessage(),null);
                            }
                        }
                    });
                }
            }
        });

        btn_resend = (Button) findViewById(R.id.btn_resend);
        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + getIntent().getStringExtra(signUpActivity.TAG_PHONE))
                        .setActivity(phoneAuthVerifySceen.this)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                showAlertDialog("Error", e.getMessage(), null);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationID = s;
                            }
                        })
                        .build();
            }
        });

        img_btn_back = (ImageButton) findViewById(R.id.btn_verify_back_to_sign_up);
        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(phoneAuthVerifySceen.this, signUpActivity.class));
                finish();
            }
        });

    }

    private void setupOTPInput(){
        otp_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    otp_2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    otp_3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    otp_4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    otp_5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    otp_6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void showAlertDialog(String title, String message, Intent intent){
        AlertDialog.Builder builder = new AlertDialog.Builder(phoneAuthVerifySceen.this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(intent != null) {
                            startActivity(intent);
                        }
                        else{
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}