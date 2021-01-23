package com.example.android.sfcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText getEmail, getPassword;
    private Button register_button, login_button, forgot_password_button;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseUser user;

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
        user = mAuth.getCurrentUser();

    }

    private void userLogin() {
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

        if (password.length() < 7) {
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
        Intent intent;
        switch (v.getId()) {
            case R.id.register_button:
                intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                break;
            case R.id.login_button:
                userLogin();
                break;
            case R.id.forgot_password:
                intent = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

  /*  public void resetPassword(View v){
        final EditText resetPassword = new EditText(v.getContext());
        final AlertDialog.Builder resetPasswordDialog = new AlertDialog.Builder(v.getContext());
        resetPasswordDialog.setTitle("Forgot passord or reset password?");
        resetPasswordDialog.setMessage("Enter new password>6 characters long!");
        resetPasswordDialog.setView(resetPassword);
        resetPasswordDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newPassword = resetPassword.getText().toString();
                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Login.this, "Password Reset Successfully!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Password Reset Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
        resetPasswordDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        resetPasswordDialog.create().show();

    }*/
}
