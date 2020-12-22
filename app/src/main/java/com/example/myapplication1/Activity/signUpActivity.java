package com.example.myapplication1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.myapplication1.Model.User;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.concurrent.TimeUnit;


public class signUpActivity extends AppCompatActivity {

    public static final String NAME = "Name";
    public static final String PASS = "Password";
    public static final String TAG_PHONE = "Phone Number";
    public static final String TAG_ID = "Code ID OTP";

    private boolean check = true;

    private Dialog dialog, progressbar_verify, verify_auth_success, verify_auth_fail;

    private MaterialButton btn_sign_up;
    private TextInputLayout tip_email_or_phonenumber_sign_up, tip_name_sign_up, tip_password_sign_up, tip_cfpass_sign_up;
    private ImageButton img_btn_sign_up_back_to_intro;
    private Button btn_sign_in;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;
    private DatabaseReference mReference;

    public static String verifycationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();
        mReference = mData.getReference("User");

        // Set Up Input Information To Register By Email or Phone
        tip_email_or_phonenumber_sign_up = (TextInputLayout) findViewById(R.id.tip_email_or_phone_sign_up);
        tip_name_sign_up = (TextInputLayout) findViewById(R.id.tip_name_sign_up);
        tip_password_sign_up = (TextInputLayout) findViewById(R.id.tip_password_sign_up);
        tip_cfpass_sign_up = (TextInputLayout) findViewById(R.id.tip_cfpassword_sign_up);


        tip_email_or_phonenumber_sign_up.setEndIconDrawable(getDrawable(R.drawable.ic_email));
        tip_email_or_phonenumber_sign_up.setHint(signInActivity.TAG_EMAIL);

        tip_email_or_phonenumber_sign_up.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    tip_email_or_phonenumber_sign_up.setEndIconDrawable(getDrawable(R.drawable.ic_phone));
                    tip_email_or_phonenumber_sign_up.setHint(signInActivity.TAG_PHONE);

                    check = false;
                } else {
                    tip_email_or_phonenumber_sign_up.setEndIconDrawable(getDrawable(R.drawable.ic_email));
                    tip_email_or_phonenumber_sign_up.setHint(signInActivity.TAG_EMAIL);

                    check = true;
                }
            }
        });

        // Button Sign Up
        btn_sign_up = (MaterialButton) findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEmailOrPhoneSignUp() || !validateName() || !validatePassword() || !validateCfPass()) {
                    return;
                }

                showDialogFragment();
            }
        });

        // Button Back To Intro
        img_btn_sign_up_back_to_intro = (ImageButton) findViewById(R.id.btn_sign_up_back_intro);
        img_btn_sign_up_back_to_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signUpActivity.this, introHomeScreen.class));

                finish();
            }
        });

        //Button Call To Sign In
        btn_sign_in = (Button) findViewById(R.id.btn_sign_in);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signUpActivity.this, signInActivity.class));

                finish();
            }
        });

    }

    public boolean validateEmailOrPhoneSignUp() {
        String val = tip_email_or_phonenumber_sign_up.getEditText().getText().toString();

        final String emailForm = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String phoneForm = "\\d{10}";

        if (val.isEmpty()) {
            tip_email_or_phonenumber_sign_up.setError("Field cannot be empty!");
            tip_email_or_phonenumber_sign_up.setErrorIconDrawable(null);
            tip_email_or_phonenumber_sign_up.requestFocus();

            return false;
        } else if (tip_email_or_phonenumber_sign_up.getHint().equals(signInActivity.TAG_EMAIL)) {

            if (!val.matches(emailForm)) {
                tip_email_or_phonenumber_sign_up.setError("Email is invalid!");
                tip_email_or_phonenumber_sign_up.setErrorIconDrawable(null);
                tip_email_or_phonenumber_sign_up.requestFocus();

                return false;
            } else {
                tip_email_or_phonenumber_sign_up.setError(null);
                tip_email_or_phonenumber_sign_up.setErrorEnabled(false);

                return true;
            }

        } else {
            if (!val.matches(phoneForm)) {
                tip_email_or_phonenumber_sign_up.setError("Phone number is invalid!");
                tip_email_or_phonenumber_sign_up.setErrorIconDrawable(null);
                tip_email_or_phonenumber_sign_up.requestFocus();

                return false;
            } else {
                tip_email_or_phonenumber_sign_up.setError(null);
                tip_email_or_phonenumber_sign_up.setErrorEnabled(false);

                return true;
            }
        }
    }

    public Boolean validateName() {
        String val = tip_name_sign_up.getEditText().getText().toString();

        String nameForm = "^[\\p{L} .'-]+$";

        if (val.isEmpty()) {
            tip_name_sign_up.setError("Field cannot be empty!");
            tip_name_sign_up.requestFocus();
            tip_name_sign_up.setErrorIconDrawable(null);

            return false;
        } else if (!val.matches(nameForm)) {
            tip_name_sign_up.setError("FullName is invalid!");
            tip_name_sign_up.setErrorIconDrawable(null);
            tip_name_sign_up.requestFocus();

            return false;
        } else {
            tip_name_sign_up.setError(null);
            tip_name_sign_up.setErrorEnabled(false);

            return true;
        }
    }

    public Boolean validatePassword() {
        String val = tip_password_sign_up.getEditText().getText().toString();

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
            tip_password_sign_up.setError("Field cannot be empty!");
            tip_password_sign_up.setErrorIconDrawable(null);
            tip_password_sign_up.requestFocus();

            return false;
        } else if (!val.matches(passForm)) {

            tip_password_sign_up.setErrorIconDrawable(null);

            if (val.length() < 8) {
                tip_password_sign_up.setError("Password at least 8 character!");
            } else {
                tip_password_sign_up.setError("Password is invalid!");
            }

            tip_password_sign_up.requestFocus();
            return false;
        } else {

            tip_password_sign_up.setError(null);
            tip_password_sign_up.setErrorEnabled(false);

            return true;
        }
    }

    public Boolean validateCfPass() {
        String cfpass = tip_cfpass_sign_up.getEditText().getText().toString();
        String pass = tip_password_sign_up.getEditText().getText().toString();

        if (!cfpass.equals(pass)) {

            tip_cfpass_sign_up.setError("Confirm password not matches!");
            tip_cfpass_sign_up.setErrorIconDrawable(null);
            tip_cfpass_sign_up.requestFocus();

            return false;
        } else {

            tip_cfpass_sign_up.setError(null);
            tip_cfpass_sign_up.setErrorEnabled(false);

            return true;
        }

    }


    private void showDialogFragment() {
        dialog = new Dialog(signUpActivity.this);

        dialog.setContentView(R.layout.fragment_dialog_verify_sign_up);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_box_verify));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setCanceledOnTouchOutside(true);

        TextView tv_email_or_phone_verify = dialog.findViewById(R.id.email_or_phone_number_verify);
        TextView tv_description_vefity = dialog.findViewById(R.id.tv_description_verify);

        tv_email_or_phone_verify.setText(tip_email_or_phonenumber_sign_up.getEditText().getText().toString());
        if (tip_email_or_phonenumber_sign_up.getHint().equals(signInActivity.TAG_EMAIL)) {
            tv_description_vefity.setText("Please confirm your email address is correct! \n A Confirmation URL Link was sent to your email by us.");
        } else {
            tv_description_vefity.setText("Please confirm your phone number is correct! \n A SMS OTP Code was sent to your phone number by us.");
        }

        dialog.show();

        Button btn_cancel_verify = dialog.findViewById(R.id.btn_cancel_verify);
        Button btn_confirm_verify = dialog.findViewById(R.id.btn_confirm_verify);

        btn_cancel_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_confirm_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sendVerificationLinkEmailorSMSOTPPhone();
            }
        });
    }

    private void sendVerificationLinkEmailorSMSOTPPhone() {

        // Show Registering dialog
        progressbar_verify = new Dialog(signUpActivity.this);

        progressbar_verify.setContentView(R.layout.progress_bar_verify);
        progressbar_verify.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_progress_bar_verify));
        progressbar_verify.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressbar_verify.setCanceledOnTouchOutside(true);

        progressbar_verify.show();

        //Get Infomation Register
        String name = tip_name_sign_up.getEditText().getText().toString();
        String account = tip_email_or_phonenumber_sign_up.getEditText().getText().toString();
        String password = tip_password_sign_up.getEditText().getText().toString();

        // Create User by Email/Password and send link verify then send info to firebase realtime
        if (tip_email_or_phonenumber_sign_up.getHint().equals(signInActivity.TAG_EMAIL)) {
            signUpWithEmailAndPassword(account, password, name);
        } else {
            sendVerificationCodeToUser(account, password, name);
        }

    }

    private void signUpWithEmailAndPassword(String account, String password, String name) {
        mAuth.createUserWithEmailAndPassword(account, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            mAuth.getCurrentUser().sendEmailVerification().
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            progressbar_verify.dismiss();

                                            if (task.isSuccessful()) {

                                                //Show dialog success
                                                showDialogSuccess("Check your email for authentication" + "\n" + "Click OK to Sign In!");

                                                //Send Information register to firebase
                                                User user = new User(name, account, null, password, mAuth.getCurrentUser().getUid());
                                                mReference.child(mAuth.getCurrentUser().getUid()).setValue(user);

                                                //Handle event go to sign in
                                                Button btn_verify_success_ok = verify_auth_success.findViewById(R.id.verify_success_ok);
                                                btn_verify_success_ok.setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {

                                                        verify_auth_success.dismiss();

                                                        Intent intent = new Intent(signUpActivity.this, signInActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME | Intent.FLAG_ACTIVITY_NEW_TASK);

                                                        startActivity(intent);
                                                        finish();
                                                    }

                                                });

                                            } else {

                                                showErrorDialog(task.getException().getMessage());
                                            }
                                        }
                                    });
                        } else {
                            progressbar_verify.dismiss();

                            showErrorDialog(task.getException().getMessage());
                        }
                    }
                });
    }


    private void sendVerificationCodeToUser(String account, String pass, String name) {

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+84" + account)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(signUpActivity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        progressbar_verify.dismiss();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        progressbar_verify.dismiss();
                        showErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        progressbar_verify.dismiss();

                        verifycationId = s;

                        // Check phone is created
                        showDialogSuccess("OTP was sent to your phone number!" + "\n" + "Click OK to continue!");

                        Button btn_verify_success_ok = verify_auth_success.findViewById(R.id.verify_success_ok);
                        btn_verify_success_ok.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                verify_auth_success.dismiss();

                                Intent intent = new Intent(signUpActivity.this, phoneAuthVerifySceen.class);
                                intent.putExtra(TAG_ID, verifycationId);
                                intent.putExtra(TAG_PHONE, account);
                                intent.putExtra(NAME, name);
                                intent.putExtra(PASS, pass);

                                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                                finish();
                            }

                        });
                    }
                })
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void showErrorDialog(String message) {

        verify_auth_fail = new Dialog(signUpActivity.this);

        verify_auth_fail.setContentView(R.layout.dialog_verify_auth_fail);

        TextView tv_description = verify_auth_fail.findViewById(R.id.tv_description_error);
        tv_description.setText(message);

        Button btn_ok_error = verify_auth_fail.findViewById(R.id.verify_fail_ok);
        btn_ok_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify_auth_fail.dismiss();
            }
        });

        verify_auth_fail.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        verify_auth_fail.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_box_verify));

        verify_auth_fail.show();

    }

    private void showDialogSuccess(String message) {

        verify_auth_success = new Dialog(signUpActivity.this);

        verify_auth_success.setContentView(R.layout.dialog_verify_auth_success);
        verify_auth_success.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_box_verify));
        verify_auth_success.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv_desciption = verify_auth_success.findViewById(R.id.tv_description_verify_success);
        tv_desciption.setText(message);

        verify_auth_success.show();
    }

}