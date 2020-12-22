package com.example.myapplication1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class verifyForgotPassPhoneActivity extends AppCompatActivity {

    private EditText otp_1, otp_2, otp_3, otp_4, otp_5, otp_6;
    private MaterialButton btn_verify_phone_forgot;
    private ProgressBar prg_verify_forgot;
    private String verifyID;
    private LinearLayout box_code;
    private TextInputLayout tip_new_pass, tip_cf_newpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_forgot_pass_phone);

        box_code = findViewById(R.id.box_code_otp);
        tip_new_pass = findViewById(R.id.tip_new_pass_forgot);
        tip_cf_newpass = findViewById(R.id.tip_cf_new_password);

        otp_1 = (EditText) findViewById(R.id.otp_1);
        otp_2 = (EditText) findViewById(R.id.otp_2);
        otp_3 = (EditText) findViewById(R.id.otp_3);
        otp_4 = (EditText) findViewById(R.id.otp_4);
        otp_5 = (EditText) findViewById(R.id.otp_5);
        otp_6 = (EditText) findViewById(R.id.otp_6);

        setupOTPInput();

        sendVerifyPhoneForgot("+84" + getIntent().getStringExtra("Phone"));


        btn_verify_phone_forgot = findViewById(R.id.btn_verify_phone_forgot);
        prg_verify_forgot = findViewById(R.id.loading_forgot);

        btn_verify_phone_forgot.setVisibility(View.VISIBLE);
        prg_verify_forgot.setVisibility(View.INVISIBLE);

        btn_verify_phone_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_verify_phone_forgot.setVisibility(View.INVISIBLE);
                prg_verify_forgot.setVisibility(View.VISIBLE);

                if(otp_1.getText().toString().trim().isEmpty()
                        || otp_2.getText().toString().trim().isEmpty()
                        || otp_3.getText().toString().trim().isEmpty()
                        || otp_4.getText().toString().trim().isEmpty()
                        || otp_5.getText().toString().trim().isEmpty()
                        || otp_6.getText().toString().trim().isEmpty()){
                    Toast.makeText(verifyForgotPassPhoneActivity.this, "Please enter valid code!", Toast.LENGTH_SHORT).show();

                    btn_verify_phone_forgot.setVisibility(View.VISIBLE);
                    prg_verify_forgot.setVisibility(View.INVISIBLE);

                    return;
                }

                String code = otp_1.getText().toString().trim() + otp_2.getText().toString().trim()
                        + otp_3.getText().toString().trim()
                        + otp_4.getText().toString().trim()
                        + otp_5.getText().toString().trim()
                        + otp_6.getText().toString().trim();

                if(code != null){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyID, code);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                btn_verify_phone_forgot.setVisibility(View.VISIBLE);
                                prg_verify_forgot.setVisibility(View.INVISIBLE);

                                box_code.setVisibility(View.INVISIBLE);
                                tip_cf_newpass.setVisibility(View.VISIBLE);
                                tip_new_pass.setVisibility(View.VISIBLE);
                                btn_verify_phone_forgot.setText("Update");

                                btn_verify_phone_forgot.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(!validatePassword() || validateCfPass()){
                                            return;
                                        }
                                        FirebaseAuth.getInstance().getCurrentUser().updatePassword(tip_new_pass.getEditText().getText().toString());

                                        startActivity(new Intent(verifyForgotPassPhoneActivity.this, homeScreenActivity.class));
                                        finish();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public void sendVerifyPhoneForgot(String account){

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(account)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(verifyForgotPassPhoneActivity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(verifyForgotPassPhoneActivity.this);
                        builder.setTitle("Error").setMessage(e.getMessage())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verifyID = s;
                    }
                })
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
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


    public Boolean validatePassword() {
        String val = tip_new_pass.getEditText().getText().toString();

        String passForm = "^" +
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +
                "(?=.*[0-9])" +         //at least 1 digit//any letter
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$";
        ;

        if (val.isEmpty()) {
            tip_new_pass.setError("Field cannot be empty!");
            tip_new_pass.setErrorIconDrawable(null);
            tip_new_pass.requestFocus();

            return false;
        } else if (!val.matches(passForm)) {

            tip_new_pass.setErrorIconDrawable(null);

            if (val.length() < 8) {
                tip_new_pass.setError("Password at least 8 character!");
            } else {
                tip_new_pass.setError("Password is invalid!");
            }

            tip_new_pass.requestFocus();
            return false;
        } else {

            tip_new_pass.setError(null);
            tip_new_pass.setErrorEnabled(false);

            return true;
        }
    }

    public Boolean validateCfPass() {
        String cfpass = tip_cf_newpass.getEditText().getText().toString();
        String pass = tip_new_pass.getEditText().getText().toString();

        if (!cfpass.equals(pass)) {

            tip_cf_newpass.setError("Confirm password not matches!");
            tip_cf_newpass.setErrorIconDrawable(null);
            tip_cf_newpass.requestFocus();

            return false;
        } else {

            tip_cf_newpass.setError(null);
            tip_cf_newpass.setErrorEnabled(false);

            return true;
        }

    }


}