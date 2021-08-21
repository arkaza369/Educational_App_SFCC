package com.babusaheb.android.sfcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    ProgressBar progressBar;
    Button forgetPassword;
    EditText emailFP;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        progressBar = findViewById(R.id.progressbar_forget_password);
        forgetPassword = findViewById(R.id.reset_password);
        emailFP = findViewById(R.id.email_fpass);

        firebaseAuth = FirebaseAuth.getInstance();

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                try {
                    firebaseAuth.sendPasswordResetEmail(emailFP.getText().toString())
                            .addOnCompleteListener(task -> {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPassword.this,
                                            "reset password link is sent to your email!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ForgetPassword.this,
                                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }catch (Exception ex){
                    Toast.makeText(ForgetPassword.this, ex.getMessage()+" please register with correct email", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });


    }
}
