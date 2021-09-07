package com.babusaheb.android.sfcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText enterEmail, enterPassword, enterConfirmPassword, enterMobileNO, enterUsername, enterFullName,enterCityName;
    private Button register, login;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseDatabase root;
    DatabaseReference reference;
    FirebaseUser user;
    TextInputLayout textInputLayout;
    String email;
    String password;
    String confirmPassword;
    String fullName;
    String phoneNo;
    String cityName;
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        enterEmail = findViewById(R.id.email);
        enterPassword = findViewById(R.id.password);
        enterConfirmPassword = findViewById(R.id.confirm_password);
        enterMobileNO = findViewById(R.id.mobile_no);
        enterFullName = findViewById(R.id.full_name);
        enterCityName = findViewById(R.id.city);
// Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(this);
        login.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser() {
        email = enterEmail.getText().toString().trim();
        password = enterPassword.getText().toString().trim();
        confirmPassword = enterConfirmPassword.getText().toString().trim();
        fullName = enterFullName.getText().toString();
        phoneNo = enterMobileNO.getText().toString();
        cityName = enterCityName.getText().toString();

        /*if (phoneNo != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Phone Number Authentication")
                    .setMessage("Do you want to register through phone number verification?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Verify Phone Number
                            Intent intent = new Intent(getApplicationContext(), VerifyPhoneNo.class);
                            intent.putExtra("fullName", fullName);
                            intent.putExtra("Username", username);
                            intent.putExtra("phoneNo", phoneNo);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            emailAuthentication();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
        else*/
        emailAuthentication();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, email);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
    }

    public void emailAuthentication() {
        if (email.isEmpty()) {
            enterEmail.setError("Required");
            enterPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            enterEmail.setError("Please enter a valid email");
            enterEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            enterPassword.setError("Required");
            enterPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            enterPassword.setError("Minimum lenght of password should be 6");
            enterPassword.requestFocus();
            return;
        }
        if (!(enterPassword.getText().toString().equals(enterConfirmPassword.getText().toString()))) {
            enterConfirmPassword.setError("Password didn't matched!");
            enterConfirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    storeUserDataToDatabase();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "Already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                registerUser();
                break;
            case R.id.login:
                finish();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void storeUserDataToDatabase() {
        //Saving user data to firebase
        root = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/");
        reference = root.getReference("users");
        UserModel userData = new UserModel(fullName, email, phoneNo, password,cityName);
        //  String key=reference.push().getKey();
        //reference.child(phoneNo).setValue(userData);
        //reference.setValue(userData);
        user = mAuth.getCurrentUser();
        String uid = user.getUid();
        Log.d(TAG, "storeUserDataToDatabase: " + uid);
        reference.child(uid).setValue(userData);
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}
