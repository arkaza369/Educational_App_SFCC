package com.example.android.sfcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText getEmail,getPassword;
    private Button register_button,login_button,forgot_password_button;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getEmail = findViewById(R.id.email_auth);
        getPassword = findViewById(R.id.password_auth);

        register_button = findViewById(R.id.register_button);
        login_button = findViewById(R.id.login_button);
        forgot_password_button = findViewById(R.id.forgot_password);

        progressBar = findViewById(R.id.progressbar);

        register_button.setOnClickListener(this);
        login_button.setOnClickListener(this);
        forgot_password_button.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    private void userLogin(){
        String email = getEmail.getText().toString().trim();
        String password = getPassword.getText().toString().trim();

        if (email.isEmpty()) {
            getEmail.setError("Required");
            getEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getEmail.setError("Please enter a valid email");
            getEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            getPassword.setError("Required");
            getPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            getPassword.setError("Minimum lenght of password should be 6");
            getPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_button:
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                break;
            case R.id.login_button:
                userLogin();
                break;
            default:
                break;
        }

    }
}
