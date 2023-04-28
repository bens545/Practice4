package com.benshaner.practice4;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mSignInButton;
    private Button mSignUpButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Find all inputs and button components
        mEditTextEmail = findViewById(R.id.email);
        mEditTextPassword = findViewById(R.id.password);
        mSignInButton = findViewById(R.id.sign_in);
        mSignUpButton = findViewById(R.id.sign_up);

        // Add button listeners
        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
    }

    /**
     * Handle login requests
     * @param view The view the current view
     */
    @Override
    public void onClick(View view) {
        firebaseAuth = FirebaseAuth.getInstance();

        // Get input text values
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();

        // Verify username is provided
        if (TextUtils.isEmpty(email)) {
            mEditTextEmail.setError(getString(R.string.invalid_username));
            return;
        }

        // Verify password is provided
        if (TextUtils.isEmpty(password)) {
            mEditTextPassword.setError(getString(R.string.invalid_password));
            return;
        }

        // Check if new user, or existing
        switch (view.getId()) {
            case R.id.sign_in:
                SignIn(email, password);
                break;
            case R.id.sign_up:
                SignUp(email, password);
                break;
        }
    }

    private void SignIn(String email, String password) {
        // User firebase auth to sign in existing user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Navigate to new activity if successfully logged in
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Display an error message if login failed
                            Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void SignUp(String email, String password) {
        // Create a new user with provided email and password
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Navigate to new activity if successfully created account
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
