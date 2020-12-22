
package com.example.myapplication1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class signInActivity extends AppCompatActivity {

    private TextInputLayout tip_email_or_phone, tip_password_sign_in;
    private ImageButton img_btn_back_sign_in_to_intro;
    private Button forgot_pass, not_have_count_sign_up;
    private MaterialButton btn_sign_in;
    private ProgressBar loading;

    private Boolean check = true;

    public static final String TAG_EMAIL = "Email";
    public static final String TAG_PHONE = "Phone Number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Set up Text Input Layout To Login by Email or Phone Number
        tip_email_or_phone = (TextInputLayout) findViewById(R.id.tip_email_or_phone_login);
        tip_password_sign_in = (TextInputLayout) findViewById(R.id.tip_password_login);

        tip_email_or_phone.setHint(TAG_EMAIL);
        tip_email_or_phone.setEndIconDrawable(getDrawable(R.drawable.ic_email));

        tip_email_or_phone.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    tip_email_or_phone.setHint(TAG_PHONE);
                    tip_email_or_phone.setEndIconDrawable(getDrawable(R.drawable.ic_phone));

                    check = false;
                } else {
                    tip_email_or_phone.setHint(TAG_EMAIL);
                    tip_email_or_phone.setEndIconDrawable(getDrawable(R.drawable.ic_email));

                    check = true;
                }
            }
        });

        // Set up to mapping Button and handle Event
        // Button Back To Intro Home
        img_btn_back_sign_in_to_intro = (ImageButton) findViewById(R.id.btn_sign_in_back_intro);
        img_btn_back_sign_in_to_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signInActivity.this, introHomeScreen.class));
                finish();
            }
        });

        // Button Sign Up
        not_have_count_sign_up = (Button) findViewById(R.id.btn_sign_up);
        not_have_count_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signInActivity.this, signUpActivity.class));
                finish();
            }
        });

        // Button Forgot Pass
        forgot_pass = (Button) findViewById(R.id.btn_forgot_password);
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something else there


            }
        });

        // Button Sign In
        loading = (ProgressBar) findViewById(R.id.loading_login);
        btn_sign_in = (MaterialButton) findViewById(R.id.btn_login);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sign_in.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);

                if (!validateEmaiOrPhoneSignIn() || !validatePass()) {

                    btn_sign_in.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    return;
                }

                String account = tip_email_or_phone.getEditText().getText().toString();
                String pass = tip_password_sign_in.getEditText().getText().toString();

                // When using email to sign in
                if (tip_email_or_phone.getHint().equals(TAG_EMAIL)) {

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(account, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                                    btn_sign_in.setVisibility(View.VISIBLE);
                                    loading.setVisibility(View.GONE);

                                    Intent intent = new Intent(signInActivity.this, homeScreenActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                    startActivity(intent);
                                } else {
                                    btn_sign_in.setVisibility(View.VISIBLE);
                                    loading.setVisibility(View.GONE);
                                    showAlertDialog("Error", "Please verify your email!", null);
                                }
                            } else {
                                btn_sign_in.setVisibility(View.VISIBLE);
                                loading.setVisibility(View.GONE);
                                showAlertDialog("Error", task.getException().getMessage(), null);
                            }
                        }
                    });
                } else {
                    // when using phone to sign in
                    AuthCredential credential = EmailAuthProvider.getCredential(account+"@domain.com", pass);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                btn_sign_in.setVisibility(View.VISIBLE);
                                loading.setVisibility(View.GONE);

                                Intent intent = new Intent(signInActivity.this, homeScreenActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                                finish();
                            }
                            else{
                                btn_sign_in.setVisibility(View.VISIBLE);
                                loading.setVisibility(View.GONE);
                                showAlertDialog("Error", task.getException().getMessage(), null);
                            }
                        }
                    });

                }
            }
        });
    }


    public Boolean validateEmaiOrPhoneSignIn() {

        String val = tip_email_or_phone.getEditText().getText().toString();

        final String emailForm = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String phoneForm = "\\d{10}";

        if (val.isEmpty()) {
            tip_email_or_phone.setError("Field cannot be empty!");
            tip_email_or_phone.setErrorIconDrawable(null);
            tip_email_or_phone.requestFocus();

            return false;
        } else if (tip_email_or_phone.getHint().equals(TAG_EMAIL)) {


            if (!val.matches(emailForm)) {
                tip_email_or_phone.setError("Email is invalid!");
                tip_email_or_phone.setErrorIconDrawable(null);
                tip_email_or_phone.requestFocus();

                return false;
            } else {
                tip_email_or_phone.setError(null);
                tip_email_or_phone.setErrorEnabled(false);

                return true;
            }

        } else {
            if (!val.matches(phoneForm)) {
                tip_email_or_phone.setError("Phone number is invalid!");
                tip_email_or_phone.setErrorIconDrawable(null);
                tip_email_or_phone.requestFocus();

                return false;
            } else {
                tip_email_or_phone.setError(null);
                tip_email_or_phone.setErrorEnabled(false);

                return true;
            }
        }
    }

    public Boolean validatePass() {
        String val = tip_password_sign_in.getEditText().getText().toString();

        if (val.isEmpty()) {
            tip_password_sign_in.setError("Field cannot be emty!");
            tip_password_sign_in.requestFocus();
            tip_password_sign_in.setErrorIconDrawable(null);

            return false;
        } else {
            tip_password_sign_in.setError(null);
            tip_password_sign_in.setErrorEnabled(false);
            return true;
        }
    }

    public void showAlertDialog(String title, String message, Intent intent){
        AlertDialog.Builder builder = new AlertDialog.Builder(signInActivity.this);
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