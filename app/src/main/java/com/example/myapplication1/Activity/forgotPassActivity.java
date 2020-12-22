package com.example.myapplication1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication1.Model.User;
import com.example.myapplication1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class forgotPassActivity extends AppCompatActivity {

    private ImageButton img_btn;
    private TextInputLayout tip_email_or_phone;
    private MaterialButton btn_next;
    private ProgressBar progressBar;

    boolean check = true;
    boolean checkAccount = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        progressBar = findViewById(R.id.loading_forgot);

        img_btn = findViewById(R.id.btn_forgot_back_sign_in);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(forgotPassActivity.this, signInActivity.class));
                finish();
            }
        });

        tip_email_or_phone = findViewById(R.id.tip_email_or_phone_forgot);
        tip_email_or_phone.setHint("Email");
        tip_email_or_phone.setEndIconDrawable(getDrawable(R.drawable.ic_email));

        tip_email_or_phone.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    tip_email_or_phone.setHint("Phone");
                    tip_email_or_phone.setEndIconDrawable(getDrawable(R.drawable.ic_phone));
                    check = false;
                } else {
                    tip_email_or_phone.setHint("Email");
                    tip_email_or_phone.setEndIconDrawable(getDrawable(R.drawable.ic_email));
                    check = true;
                }
            }
        });
        btn_next = findViewById(R.id.btn_next_forgot);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.INVISIBLE);

                if (!validateEmailorPhoneForgot()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    btn_next.setVisibility(View.VISIBLE);

                    return;
                }
                String account = tip_email_or_phone.getEditText().getText().toString();
                if (tip_email_or_phone.getHint().equals("Email")) {
                    FirebaseDatabase.getInstance().getReference("User").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                User user = new User();
                                user = snap.getValue(User.class);
                                if(user.getEmail()!= null) {
                                    if (user.getEmail().equals(account)) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        btn_next.setVisibility(View.VISIBLE);

                                        FirebaseAuth.getInstance().sendPasswordResetEmail(account);
                                        checkAccount = false;

                                        Toast.makeText(forgotPassActivity.this, "Please check your email! Email change password was sent!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            if (checkAccount) {

                                progressBar.setVisibility(View.INVISIBLE);
                                btn_next.setVisibility(View.VISIBLE);

                                AlertDialog.Builder builder = new AlertDialog.Builder(forgotPassActivity.this);
                                builder.setTitle("Error")
                                        .setMessage("Your email was not register! Please register this email!")
                                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        })
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(forgotPassActivity.this, signUpActivity.class));
                                                finish();
                                            }
                                        });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    FirebaseDatabase.getInstance().getReference("User").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                User user = snap.getValue(User.class);
                                if(user.getPhone_number() != null) {
                                    if (user.getPhone_number().equals(account)) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        btn_next.setVisibility(View.VISIBLE);

                                        Intent intent = new Intent(forgotPassActivity.this, verifyForgotPassPhoneActivity.class);
                                        intent.putExtra("Phone", account);

                                        startActivity(intent);

                                        checkAccount = false;
                                    }
                                }
                            }

                            if(checkAccount){
                                AlertDialog.Builder builder = new AlertDialog.Builder(forgotPassActivity.this);
                                builder.setTitle("Error")
                                        .setMessage("Your phone number was not register! Please register this phone number!")
                                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        })
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(forgotPassActivity.this, signUpActivity.class));
                                                finish();
                                            }
                                        });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    public Boolean validateEmailorPhoneForgot() {
        String val = tip_email_or_phone.getEditText().getText().toString();

        final String emailForm = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String phoneForm = "\\d{10}";

        if (val.isEmpty()) {
            tip_email_or_phone.setError("Field cannot be empty!");
            tip_email_or_phone.setErrorIconDrawable(null);
            tip_email_or_phone.requestFocus();

            return false;
        } else if (tip_email_or_phone.getHint().equals(signInActivity.TAG_EMAIL)) {

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

}